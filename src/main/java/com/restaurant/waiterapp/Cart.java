package com.restaurant.waiterapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
    private ArrayList<cartItem> CartList;

    public Cart() {
        CartList=new ArrayList<>();
    }

    public Cart(ArrayList<cartItem> cart) {
        CartList = cart;
    }

    public void addItem(cartItem item) {
        CartList.add(item);
    }

    public void deleteItem(cartItem item){
        CartList.remove(item);
    }

    public ArrayList<cartItem> getCart() {
        return CartList;
    }
}
