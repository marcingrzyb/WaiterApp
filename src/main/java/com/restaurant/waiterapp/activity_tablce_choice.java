package com.restaurant.waiterapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.restaurant.waiterapp.api.resources.TableResponse;

import java.util.ArrayList;


public class activity_tablce_choice extends AppCompatActivity {
    ListView lv;
    ArrayList<TableResponse> tables;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablce_choice);
        lv=findViewById(R.id.tableList);
        final ArrayAdapter<TableResponse> arrayAdapter = new ArrayAdapter<TableResponse>(
                activity_tablce_choice.this,
                android.R.layout.simple_list_item_1,
                tables );
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                Intent i = new Intent(getBaseContext(), MenuActivty.class);
                // TODO: 04.12.2019 fix extra argument pass
                i.putExtra("reservationID",1);
                startActivity(i);
                finish();
            }
        });
    }
    // TODO: 04.12.2019 getreservationid
}
