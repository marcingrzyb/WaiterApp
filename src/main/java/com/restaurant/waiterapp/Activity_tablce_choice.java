package com.restaurant.waiterapp;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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


public class Activity_tablce_choice extends AppCompatActivity {
    ListView lv;
    ArrayList<TableResponse> tables = new ArrayList<>();
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablce_choice);
        lv = findViewById(R.id.tableList);
        tables=(ArrayList<TableResponse>)getIntent().getSerializableExtra("tables");
        username=getIntent().getStringExtra("username");
        Log.d("username",username);
        final ArrayAdapter<TableResponse> arrayAdapter = new ArrayAdapter<TableResponse>(
                Activity_tablce_choice.this,
                android.R.layout.simple_list_item_1,
                tables);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                int reservationid=getReservationId(tables.get(position));
                if (reservationid != -1) {
                    assignTable("http://10.0.2.2:8080/api/waiter/assign?reservationId="+ reservationid);
                    Intent i = new Intent(getBaseContext(), MenuActivty.class);
                    i.putExtra("reservationID", reservationid);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(getBaseContext(), "empty reservation", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected int getReservationId(TableResponse table) {
        if(isNotEmpty(table.getTableReservations())) {
            return Integer.parseInt(table.getTableReservations().get(0).getId().toString());
        }else return -1;
    }

    public void assignTable(String url) {
        AsyncTask.execute(() -> {
            URL loginEndpoint;
            try {
                loginEndpoint = new URL(url);
                HttpURLConnection myConnection;
                myConnection = (HttpURLConnection) loginEndpoint.openConnection();
                myConnection.setRequestMethod("PATCH");

                if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                    Log.d("tables", stringResponse);

                } else {
                    // TODO: 12.12.2019
                    Log.d("status", "lipaAssign");
                    InputStream responseBody = myConnection.getInputStream();
                    String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                    Log.d("AssignFailure", stringResponse);
                }

                myConnection.disconnect();
            } catch (ProtocolException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
