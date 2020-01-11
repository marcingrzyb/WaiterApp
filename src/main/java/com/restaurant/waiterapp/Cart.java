package com.restaurant.waiterapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private List<CartItem> cartList;

    public Cart() {
        cartList=new ArrayList<>();
    }

    public Cart(List<CartItem> cart) {
        cartList = cart;
    }

    public void addItem(CartItem item) {
        cartList.add(item);
    }

    public void deleteItem(CartItem item){
        cartList.remove(item);
    }

    public List<CartItem> getCart() {
        return cartList;
    }
}
