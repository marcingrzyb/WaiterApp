package com.restaurant.waiterapp.api.resources;

import java.io.Serializable;

public class FoodResponse implements Serializable {

    private String name;
    private FoodType dishOrDrink;
    private Double price;

    public FoodResponse(String name, FoodType dishOrDrink, Double price) {
        this.name = name;
        this.dishOrDrink = dishOrDrink;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FoodType getDishOrDrink() {
        return dishOrDrink;
    }

    public void setDishOrDrink(FoodType dishOrDrink) {
        this.dishOrDrink = dishOrDrink;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
