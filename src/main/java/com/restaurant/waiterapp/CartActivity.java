package com.restaurant.waiterapp;


import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.waiterapp.api.resources.FoodType;
import com.restaurant.waiterapp.api.resources.OrderRequest;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    private ListView lv;
    Cart cart;
    TextView cartSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lv = (ListView) findViewById(R.id.cartItemListView);
        cartSum=(TextView) findViewById(R.id.cartSum);
        if(cart==null) {
            cart = (Cart) getIntent().getSerializableExtra("Passed cart");
        }
        final ArrayAdapter<cartItem> arrayAdapter = new ArrayAdapter<cartItem>(
                this,
                android.R.layout.simple_list_item_1,
                cart.getCart() );
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                showDialog(cart.getCart().get(position).foodResponse.getName(),position,arrayAdapter);

            }
        });
        cartSum.setText(getCartSum(cart));
    }
    public void onClickSendCart(View view){
        String orderRequest=prepareOrderRequest(cart);
        sendOrder("http://10.0.2.2:8080/api/waiter/order",orderRequest);
    }
    public String prepareOrderRequest(Cart cart){
        String jsonOrderRequest="";
        ArrayList<Long> dishes=new ArrayList<>();
        ArrayList<Long> beverages=new ArrayList<>();
        Long reservationId=(long) getIntent().getIntExtra("reservationID", 0);
        for(cartItem item: cart.getCart()) {
            if(item.getFoodResponse().getDishOrDrink()== FoodType.DISH){
                for (int i=0;i<item.quantity;i++) {
                    dishes.add(item.getFoodResponse().getId());
                }
            }
            else {
                for (int i=0;i<item.quantity;i++) {
                    beverages.add(item.getFoodResponse().getId());
                }
            }
        }
        Log.d("food",dishes.toString());
        Log.d("drinks",beverages.toString());
        OrderRequest orderRequest= new OrderRequest(dishes,beverages, reservationId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonOrderRequest = mapper.writeValueAsString(orderRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonOrderRequest;
    }
    public void sendOrder(String url,String orderRequest) {
        AsyncTask.execute(() -> {
            URL loginEndpoint;
            try {
                loginEndpoint = new URL(url);
                HttpURLConnection myConnection;
                myConnection = (HttpURLConnection) loginEndpoint.openConnection();
                myConnection.setRequestMethod("POST");
                myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                myConnection.setDoOutput(true); //this is to enable writing
                myConnection.setDoInput(true);  //this is to enable reading
                try(OutputStream os = myConnection.getOutputStream()) {
                    byte[] input = orderRequest.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }


                if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                    Log.d("tables", stringResponse);


                } else {
                    // TODO: 12.12.2019
                    Log.d("status", "lipaSend");
                    InputStream responseBody = myConnection.getInputStream();
                    String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                    Log.d("sendFailure", stringResponse);

                }

                myConnection.disconnect();
            } catch (ProtocolException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public String getCartSum(Cart cart){
        Double sum=0.0;
        for (cartItem item:cart.getCart()) {
            sum+=item.quantity*item.foodResponse.getPrice();
        }
        return sum.toString();
    }
    public void showDialog(final String msg, final int position, final ArrayAdapter<cartItem> arrayAdapter){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_dialog);


        TextView editTextDishName = (TextView) dialog.findViewById(R.id.showDishId);
        editTextDishName.setText(msg);

        final TextView editTextQuantity = (TextView) dialog.findViewById(R.id.editTextQuantity);

        Button addButton = (Button) dialog.findViewById(R.id.addButton);
        addButton.setText("update");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity=Integer.parseInt(editTextQuantity.getText().toString());
                if(quantity>0) {
                    cart.getCart().get(position).quantity=quantity;
                    cartSum.setText(getCartSum(cart));
                    arrayAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else {
                    cart.getCart().remove(position);
                    cartSum.setText(getCartSum(cart));
                    arrayAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });


        Button cancelAddingButton = (Button) dialog.findViewById(R.id.cancelAddingButton);
        cancelAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
