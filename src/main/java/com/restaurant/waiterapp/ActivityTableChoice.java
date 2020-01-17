/*
 *
 *   Copyright 2020 Marcin Grzyb
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.restaurant.waiterapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.restaurant.waiterapp.api.resources.TableResponse;
import com.restaurant.waiterapp.apiconnection.RequestsPatch;

import java.util.ArrayList;

import static com.restaurant.waiterapp.apiconnection.ConnectionConfig.getConnectionConfig;
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
        username=getIntent().getStringExtra("username");
        final ArrayAdapter<TableResponse> arrayAdapter = new ArrayAdapter<>(
                ActivityTableChoice.this,
                android.R.layout.simple_list_item_1,
                tables);
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener((parent, v, position, id) -> {
                int reservationid=getReservationId(tables.get(position));
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
                    }.execute(getConnectionConfig()+"/api/waiter/assign?reservationId="+ reservationid);
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
