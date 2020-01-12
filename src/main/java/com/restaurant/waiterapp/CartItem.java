package com.restaurant.waiterapp;

import com.restaurant.waiterapp.api.resources.FoodResponse;

import java.io.Serializable;
import java.util.Objects;

public class CartItem implements Serializable {
    FoodResponse foodResponse;
    int quantity;

    public CartItem(FoodResponse foodResponse, int quantity) {
        this.foodResponse = foodResponse;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity &&
                Objects.equals(foodResponse, cartItem.foodResponse);
    }

    public FoodResponse getFoodResponse() {
        return foodResponse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodResponse, quantity);
    }

    @Override
    public String toString() {
        return
                foodResponse.getName() +
                ", quantity=" + quantity+", price: "+quantity*foodResponse.getPrice();
    }
}
