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
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.restaurant.waiterapp.api.resources.FoodResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivty extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    Map<String, List<String>> expandableListDetail;
    Cart cart = new Cart();
    List<FoodResponse> drinksObj =new ArrayList<>();
    List<FoodResponse> foodObj =new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activty);
        expandableListView = findViewById(R.id.expandableListView);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                expandableListDetail = ExpandableListDataPump.getData();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                foodObj =ExpandableListDataPump.getFoodObj();
                drinksObj =ExpandableListDataPump.getDrinksObj();
                expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                expandableListAdapter = new CustomExpandableListAdapter(MenuActivty.this.getBaseContext(), expandableListTitle, (HashMap<String, List<String>>) expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);
            }
        }.execute();

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            FoodResponse foodTmp;
            if(groupPosition==0){
                foodTmp= drinksObj.get(childPosition);
            }
            else {
                foodTmp = foodObj.get(childPosition);
            }
            showDialog(foodTmp);
            return false;
        });
    }
    public void onClickShowCart(View v){
        //triggered after using Button
        Intent i = new Intent(getBaseContext(), CartActivity.class);
        i.putExtra("Passed cart", cart);
        i.putExtra("reservationID",getIntent().getIntExtra("reservationID",0));
        startActivity(i);

    }
    public void showDialog(final FoodResponse foodResponse){
        //Displays Dialog window that allows adding item to Cart
        final Dialog dialog = new Dialog(MenuActivty.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_dialog);


        TextView editTextDishName = dialog.findViewById(R.id.showDishId);
        editTextDishName.setText(foodResponse.getName());

        final TextView editTextQuantity = dialog.findViewById(R.id.editTextQuantity);

        Button addButton = dialog.findViewById(R.id.addButton);
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
            cart.addItem(new CartItem(foodResponse,Integer.parseInt(editTextQuantity.getText().toString())));
            dialog.dismiss();
        });


        Button cancelAddingButton = dialog.findViewById(R.id.cancelAddingButton);
        cancelAddingButton.setOnClickListener(v -> dialog.dismiss());
        dialog.show();

    }


}
