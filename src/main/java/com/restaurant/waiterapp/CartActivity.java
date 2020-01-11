package com.restaurant.waiterapp;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.restaurant.waiterapp.apiconnection.RequestsPost;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartActivity extends AppCompatActivity {
    private static final Logger LOGGER = Logger.getLogger( CartActivity.class.getName() );
    Cart cart;
    TextView cartSum;
    Boolean sendOrderResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView lv;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lv = findViewById(R.id.cartItemListView);
        cartSum= findViewById(R.id.cartSum);
        if(cart==null) {
            cart = (Cart) getIntent().getSerializableExtra("Passed cart");
        }
        final ArrayAdapter<CartItem> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                cart.getCart() );
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener((parent, v, position, id) -> {
            Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
            showDialog(cart.getCart().get(position).foodResponse.getName(),position,arrayAdapter);

        });
        cartSum.setText(getCartSum(cart));
    }
    @SuppressLint("StaticFieldLeak")
    public void onClickSendCart(View view){
        String orderRequest=prepareOrderRequest(cart);
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                sendOrderResult= RequestsPost.sendPost(strings[0],strings[1]);
                return null;
            }
        }.execute("http://10.0.2.2:8080/api/waiter/order",orderRequest);

    }
    public String prepareOrderRequest(Cart cart){
        String jsonOrderRequest="";
        ArrayList<Long> dishes=new ArrayList<>();
        ArrayList<Long> beverages=new ArrayList<>();
        Long reservationId=(long) getIntent().getIntExtra("reservationID", 0);
        for(CartItem item: cart.getCart()) {
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
            LOGGER.log(Level.SEVERE, e.toString());
        }
        return jsonOrderRequest;
    }

    public String getCartSum(Cart cart){
        Double sum=0.0;
        for (CartItem item:cart.getCart()) {
            sum+=item.quantity*item.foodResponse.getPrice();
        }
        return sum.toString();
    }
    public void showDialog(final String msg, final int position, final ArrayAdapter<CartItem> arrayAdapter){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_dialog);


        TextView editTextDishName = dialog.findViewById(R.id.showDishId);
        editTextDishName.setText(msg);

        final TextView editTextQuantity = dialog.findViewById(R.id.editTextQuantity);

        Button addButton = dialog.findViewById(R.id.addButton);
        addButton.setText("update");
        addButton.setEnabled(false);
        editTextQuantity.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //not neccesery
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                //not neccesery
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(!s.toString().matches("^\\d+$")){
                    addButton.setEnabled(false);
                }
                else{
                    addButton.setEnabled(true);
                }

            }
        });
        addButton.setOnClickListener(v -> {
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
        });


        Button cancelAddingButton = dialog.findViewById(R.id.cancelAddingButton);
        cancelAddingButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

    }
}
