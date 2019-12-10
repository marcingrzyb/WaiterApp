package com.restaurant.waiterapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class OrdersActivity extends AppCompatActivity {
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        // TODO: 29.11.2019 Add fill listview,refreshgesture and refresh after going back to view
    }
    public void onClickOpenTable(View view){
        Intent i = new Intent(getBaseContext(), activity_tablce_choice.class);
        startActivity(i);
    }
}
