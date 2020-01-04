package com.restaurant.waiterapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.restaurant.waiterapp.api.resources.OrderResponse;
import com.restaurant.waiterapp.api.resources.TableResponse;
import com.restaurant.waiterapp.apiConnection.requestsGET;

import java.util.ArrayList;
import java.util.Objects;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

public class OrdersActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<TableResponse> tables = new ArrayList<>();
    String username;
    ArrayList<String> orders=new ArrayList<>();
    ArrayList<OrderResponse> ordersObj=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String requestURLgetOrders="http://10.0.2.2:8080/api/waiter/tables";
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        lv = findViewById(R.id.ordersListView);
        SwipeRefreshLayout SwipeRefresher = findViewById(R.id.swiperefresh);
        Bundle extras=getIntent().getExtras();
        username=extras.getString("username");
        Log.d("user",username);
        new AsyncTask<String, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                orders=getOrders(tables);
                arrayAdapter = new ArrayAdapter<String>(
                        OrdersActivity.this,
                        android.R.layout.simple_list_item_1,
                        orders);
                lv.setAdapter(arrayAdapter);
            }
            @Override
            protected Void doInBackground(String... strings) {
                tables=requestsGET.getTables(strings[0]);
                return null;
            }
        }.execute(requestURLgetOrders);

        SwipeRefresher.setOnRefreshListener(
                () -> new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... strings) {
                        tables=requestsGET.getTables(strings[0]);
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
                        SwipeRefresher.setRefreshing(false);
                    }
                }.execute(requestURLgetOrders));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                Long orderid=ordersObj.get(position).getId();
                if(orderid!=null) {
                    Intent i = new Intent(getBaseContext(), RateActivity.class);
                    i.putExtra("orderid", orderid.toString());
                    startActivity(i);
                }
            }
        });
    }
    public void onClickOpenTable(View view){
        Intent i = new Intent(getBaseContext(), Activity_tablce_choice.class);
        i.putExtra("username",username);
        i.putExtra("tables",tables);
        startActivity(i);
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
                        ordersObj.add(order);
                        Log.d("ord",orders.toString());
                    }
                }
            }
        }
        return orders;
    }
}
