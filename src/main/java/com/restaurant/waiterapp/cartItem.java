package com.restaurant.waiterapp;

import java.io.Serializable;
import java.util.Objects;

public class cartItem implements Serializable {
    String productName;

    int quantity;

    public cartItem(String productName,  int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        cartItem cartItem = (cartItem) o;
        return quantity == cartItem.quantity &&
                Objects.equals(productName, cartItem.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName);
    }

    @Override
    public String toString() {
        return
                "productName='" + productName + '\'' +
                ", quantity=" + quantity;
    }
}
