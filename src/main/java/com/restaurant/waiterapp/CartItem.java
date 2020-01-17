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
