package com.restaurant.waiterapp.api.resources;

import java.io.Serializable;
import java.util.List;

public class OrderResponse implements Serializable {

    private String waiter;

    private String chef;

    private String bartender;

    private List<FoodResponse> dishes;

    private List<FoodResponse> beverages;

    public OrderResponse(String waiter, String chef, String bartender, List<FoodResponse> dishes, List<FoodResponse> beverages) {
        this.waiter = waiter;
        this.chef = chef;
        this.bartender = bartender;
        this.dishes = dishes;
        this.beverages = beverages;
    }

    public String getWaiter() {
        return waiter;
    }

    public void setWaiter(String waiter) {
        this.waiter = waiter;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    public String getBartender() {
        return bartender;
    }

    public void setBartender(String bartender) {
        this.bartender = bartender;
    }

    public List<FoodResponse> getDishes() {
        return dishes;
    }

    public void setDishes(List<FoodResponse> dishes) {
        this.dishes = dishes;
    }

    public List<FoodResponse> getBeverages() {
        return beverages;
    }

    public void setBeverages(List<FoodResponse> beverages) {
        this.beverages = beverages;
    }
}
