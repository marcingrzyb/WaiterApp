package com.restaurant.waiterapp.api.resources;

import java.io.Serializable;

public class FoodResponse implements Serializable {

    private Long id;
    private String name;
    private FoodType dishOrDrink;
    private Double price;

    public FoodResponse(Long id, String name, FoodType dishOrDrink, Double price) {
        this.id = id;
        this.name = name;
        this.dishOrDrink = dishOrDrink;
        this.price = price;
    }

    public FoodResponse() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "FoodResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dishOrDrink=" + dishOrDrink +
                ", price=" + price +
                '}';
    }
}