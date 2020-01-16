package com.restaurant.waiterapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.restaurant.waiterapp.api.resources.TableResponse;
import com.restaurant.waiterapp.apiconnection.RequestsPatch;

import java.util.ArrayList;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;


public class ActivityTableChoice extends AppCompatActivity {
    ListView lv;
    ArrayList<TableResponse> tables = new ArrayList<>();
    String username;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablce_choice);
        lv = findViewById(R.id.tableList);
        tables=(ArrayList<TableResponse>)getIntent().getSerializableExtra("tables");
        if (tables != null) {
            Log.d("tables",tables.toString());
        }
        username=getIntent().getStringExtra("username");
        Log.d("username",username);
        final ArrayAdapter<TableResponse> arrayAdapter = new ArrayAdapter<>(
                ActivityTableChoice.this,
                android.R.layout.simple_list_item_1,
                tables);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener((parent, v, position, id) -> {
                int reservationid=getReservationId(tables.get(position));
                Log.d("resid",String.valueOf(reservationid));
                if (reservationid != -1) {
                    new AsyncTask<String, Void, Void>() {
                        @Override
                        protected Void doInBackground(String... strings) {
                            RequestsPatch.patchRequest(strings[0]);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Intent i = new Intent(getBaseContext(), MenuActivty.class);
                            i.putExtra("reservationID", reservationid);
                            startActivity(i);
                            finish();
                        }
                    }.execute("http://10.0.2.2:8080/api/waiter/assign?reservationId="+ reservationid);
                }else{
                    Toast.makeText(getBaseContext(), "empty reservation", Toast.LENGTH_LONG).show();
                }

        });
    }

    protected int getReservationId(TableResponse table) {
        //extracts reservation that is closest to actual hour
        if(isNotEmpty(table.getTableReservations())) {
            return Integer.parseInt(table.getTableReservations().get(0).getId().toString());
        }else return -1;
    }

}
