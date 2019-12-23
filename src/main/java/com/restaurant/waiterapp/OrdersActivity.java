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
import java.util.ArrayList;
import java.util.Objects;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import com.restaurant.waiterapp.apiConnection.requestsGET;

public class OrdersActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<TableResponse> tables = new ArrayList<>();
    String username;
    ArrayList<String> orders=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String requestURL="http://10.0.2.2:8080/api/waiter/tables";
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
        }.execute(requestURL);

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
                }.execute(requestURL));
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
                        Log.d("ord",orders.toString());
                    }
                }
            }
        }
        return orders;
    }
}
