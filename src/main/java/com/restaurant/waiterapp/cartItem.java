package com.restaurant.waiterapp;

import java.io.Serializable;
import java.util.Objects;

public class cartItem implements Serializable {
    String productName;
    boolean isFood;
    int quantity;

    public cartItem(String productName, boolean isFood, int quantity) {
        this.productName = productName;
        this.isFood = isFood;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        cartItem cartItem = (cartItem) o;
        return isFood == cartItem.isFood &&
                productName.equals(cartItem.productName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(productName, isFood);
    }

    @Override
    public String toString() {
        return "cartItem{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
