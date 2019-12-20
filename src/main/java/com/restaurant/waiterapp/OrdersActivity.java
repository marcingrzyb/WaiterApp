package com.restaurant.waiterapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.waiterapp.api.resources.OrderResponse;
import com.restaurant.waiterapp.api.resources.TableResponse;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

public class OrdersActivity extends AppCompatActivity {
    ListView lv;
    AtomicReference<Boolean> asyncFinished = new AtomicReference<>(false);
    ArrayList<TableResponse> tables = new ArrayList<>();
    String username;
    ArrayList<String> orders=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        lv = findViewById(R.id.ordersListView);
        SwipeRefreshLayout SwipeRefresher = findViewById(R.id.swiperefresh);
        Bundle extras=getIntent().getExtras();
        username=extras.getString("username");
        //username="func@admin.pl";
        Log.d("user",username);
        getTables("http://10.0.2.2:8080/api/waiter/tables");
        while (!asyncFinished.get()) {
            // TODO: 12.12.2019
        }
        asyncFinished.set(false);
        orders=getOrders(tables);
        arrayAdapter = new ArrayAdapter<String>(
                OrdersActivity.this,
                android.R.layout.simple_list_item_1,
                orders);
        lv.setAdapter(arrayAdapter);
        SwipeRefresher.setOnRefreshListener(
                () -> {
                    getTables("http://10.0.2.2:8080/api/waiter/tables");
                    while (!asyncFinished.get()) {
                        // TODO: 12.12.2019
                    }
                    asyncFinished.set(false);
                    orders=getOrders(tables);
                    arrayAdapter.clear();
                    arrayAdapter.addAll(orders);
                    arrayAdapter.notifyDataSetChanged();
                    Log.d("refresh","refreshed");
                    SwipeRefresher.setRefreshing(false);
                }
        );
    }
    public void onClickOpenTable(View view){
        Intent i = new Intent(getBaseContext(), Activity_tablce_choice.class);
        i.putExtra("username",username);
        i.putExtra("tables",tables);
        startActivity(i);
    }

    public void getTables(String url) {
        AsyncTask.execute(() -> {
            URL loginEndpoint;
            try {
                loginEndpoint = new URL(url);
                HttpURLConnection myConnection;
                myConnection = (HttpURLConnection) loginEndpoint.openConnection();
                myConnection.setRequestMethod("GET");

                if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                    Log.d("tables", stringResponse);
                    try {
                        tables = parseJson(stringResponse);
                        Log.d("parsed","parsed");
                    } catch (IllegalStateException | JsonProcessingException exception) {
                        exception.printStackTrace();
                    }
                } else {
                    // TODO: 12.12.2019  
                    Log.d("status", "lipa");
                }
                asyncFinished.set(true);
                myConnection.disconnect();
            } catch (ProtocolException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public ArrayList<TableResponse> parseJson(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<TableResponse> ppl2 = Arrays.asList(mapper.readValue(jsonString, TableResponse[].class));
        return new ArrayList<TableResponse>(ppl2);

    }
    public ArrayList<String> getOrders(ArrayList<TableResponse> tables){
        ArrayList<String> orders=new ArrayList<>();
        if(tables!=null) {
            for (TableResponse table : tables) {
                Log.d("loop","looping");
                if (isNotEmpty(table.getTableReservations()) && table.getTableReservations().get(0).getOrderEntity() != null) {
                    OrderResponse order = table.getTableReservations().get(0).getOrderEntity();
                    String waiter = order.getWaiter();
                    if (Objects.equals(waiter, username) && order.getStage()!=null) {
                        orders.add("Table " + table.getId().toString() + ": " + order.getStage().toString());
                        Log.d("ord",orders.toString());
                    }
                }
            }
        }
        return orders;
    }
    public void waitForAsync(){

    }
}
