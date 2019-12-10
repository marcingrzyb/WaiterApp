package com.restaurant.waiterapp;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {
    private ListView lv;
    Cart cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        lv = (ListView) findViewById(R.id.cartItemListView);
        cart= (Cart) getIntent().getSerializableExtra("Passed cart");
        final ArrayAdapter<cartItem> arrayAdapter = new ArrayAdapter<cartItem>(
                this,
                android.R.layout.simple_list_item_1,
                cart.getCart() );
        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                showDialog(cart.getCart().get(position).productName,position,arrayAdapter);

            }
        });
    }

    public void showDialog(final String msg, final int position, final ArrayAdapter<cartItem> arrayAdapter){
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_dialog);


        TextView editTextDishName = (TextView) dialog.findViewById(R.id.showDishId);
        editTextDishName.setText(msg);

        final TextView editTextQuantity = (TextView) dialog.findViewById(R.id.editTextQuantity);

        Button addButton = (Button) dialog.findViewById(R.id.addButton);
        addButton.setText("update");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 23.11.2019 edit isFood
                int quantity=Integer.parseInt(editTextQuantity.getText().toString());
                if(quantity>0) {
                    cart.getCart().get(position).quantity=quantity;
                    arrayAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else {
                    cart.deleteItem(new cartItem(msg, true, quantity));
                    arrayAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
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
