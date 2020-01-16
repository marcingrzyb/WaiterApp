package com.restaurant.waiterapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.restaurant.waiterapp.api.resources.OrderResponse;
import com.restaurant.waiterapp.api.resources.TableResponse;
import com.restaurant.waiterapp.apiconnection.RequestsGet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

public class OrdersActivity extends AppCompatActivity {
    ListView lv;
    List<TableResponse> tables = new ArrayList<>();
    String username;
    List<String> orders=new ArrayList<>();
    ArrayList<OrderResponse> ordersObj;
    ArrayAdapter<String> arrayAdapter;
    String requestURLgetOrders="http://10.0.2.2:8080/api/waiter/tables";
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        lv = findViewById(R.id.ordersListView);
        SwipeRefreshLayout swipeRefresher = findViewById(R.id.swiperefresh);
        Bundle extras=getIntent().getExtras();
        if (extras != null) {
            username=extras.getString("username");
        }
        Log.d("user",username);
        new AsyncTask<String, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                orders=getOrders(tables);
                arrayAdapter = new ArrayAdapter<>(
                        OrdersActivity.this,
                        android.R.layout.simple_list_item_1,
                        orders);
                lv.setAdapter(arrayAdapter);
            }
            @Override
            protected Void doInBackground(String... strings) {
                tables= RequestsGet.getTables(strings[0]);
                return null;
            }
        }.execute(requestURLgetOrders);

        swipeRefresher.setOnRefreshListener(
                () -> new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... strings) {
                        tables= RequestsGet.getTables(strings[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        orders=getOrders(tables);
                        arrayAdapter.clear();
                        arrayAdapter.addAll(orders);
                        arrayAdapter.notifyDataSetChanged();
                        Log.d("refresh","refreshed");
                        swipeRefresher.setRefreshing(false);
                    }
                }.execute(requestURLgetOrders));
        lv.setOnItemClickListener((parent, v, position, id) -> {
            Long orderid=ordersObj.get(position).getId();
            Log.d("orderid", String.valueOf(orderid));
            if(orderid!=null) {
                Intent i = new Intent(getBaseContext(), RateActivity.class);
                i.putExtra("orderid", orderid.toString());
                startActivity(i);
            }
        });
    }
    @Override
    @SuppressLint("StaticFieldLeak")
    protected void onResume() {
        super.onResume();
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                tables= RequestsGet.getTables(strings[0]);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                orders=getOrders(tables);
                arrayAdapter.clear();
                arrayAdapter.addAll(orders);
                arrayAdapter.notifyDataSetChanged();
                Log.d("refresh","refreshed");
            }
        }.execute(requestURLgetOrders);

    }
    public void onClickOpenTable(View view){
        //triggered after using Button
        Intent i = new Intent(getBaseContext(), ActivityTableChoice.class);
        i.putExtra("username",username);
        i.putExtra("tables", (ArrayList) tables);
        startActivity(i);
    }

    public List<String> getOrders(List<TableResponse> tables){
        //Extracting Orders from List of Tables
        List<String> ordersResult=new ArrayList<>();
        ordersObj=new ArrayList<>();
        if(tables!=null) {
            for (TableResponse table : tables) {
                Log.d("loop","looping");
                if (isNotEmpty(table.getTableReservations()) && table.getTableReservations().get(0).getOrderEntity() != null) {
                    OrderResponse order = table.getTableReservations().get(0).getOrderEntity();
                    String waiter = order.getWaiter();
                    if (Objects.equals(waiter, username) && order.getStage()!=null) {
                        ordersResult.add("Table " + table.getId().toString() + ": " + order.getStage().toString());
                        ordersObj.add(order);
                        Log.d("ord",ordersResult.toString());
                    }
                }
            }
        }
        return ordersResult;
    }
}
