package com.restaurant.waiterapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
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
    List<FoodResponse> DrinksObj=new ArrayList<FoodResponse>();
    List<FoodResponse> FoodObj=new ArrayList<FoodResponse>();
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activty);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                expandableListDetail = ExpandableListDataPump.getData();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                FoodObj=ExpandableListDataPump.getFoodObj();
                Log.d("food",FoodObj.toString());
                DrinksObj=ExpandableListDataPump.getDrinksObj();
                Log.d("drinks",DrinksObj.toString());
                expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                expandableListAdapter = new CustomExpandableListAdapter(MenuActivty.this.getBaseContext(), expandableListTitle, (HashMap<String, List<String>>) expandableListDetail);
                expandableListView.setAdapter(expandableListAdapter);
            }
        }.execute();

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });
        // TODO: 23.11.2019
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.d("group position",Integer.toString(groupPosition));
                Log.d("child position",Integer.toString(childPosition));
                //String tmp= expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                FoodResponse foodTmp;
                if(groupPosition==0){
                    foodTmp=DrinksObj.get(childPosition);
                }
                else {
                    foodTmp = FoodObj.get(childPosition);
                }
                showDialog(foodTmp);
                return false;
            }
        });
    }
    public void onClickShowCart(View v){

        Intent i = new Intent(getBaseContext(), CartActivity.class);
        i.putExtra("Passed cart", cart);
        i.putExtra("reservationID",getIntent().getIntExtra("reservationID",0));
        startActivity(i);

    }
    public void showDialog(final FoodResponse foodResponse){
        final Dialog dialog = new Dialog(MenuActivty.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_dialog);


        TextView editTextDishName = (TextView) dialog.findViewById(R.id.showDishId);
        editTextDishName.setText(foodResponse.getName());

        final TextView editTextQuantity = (TextView) dialog.findViewById(R.id.editTextQuantity);

        Button addButton = (Button) dialog.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.addItem(new cartItem(foodResponse,Integer.parseInt(editTextQuantity.getText().toString())));
                dialog.dismiss();
            }
        });


        Button cancelAddingButton = (Button) dialog.findViewById(R.id.cancelAddingButton);
        cancelAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


}
