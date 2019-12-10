package com.restaurant.waiterapp.api.resources;

public enum FoodType {
    DISH("DISH"),DRINK("DRINK");

    private final String type;

    FoodType(String type) {
        this.type = type;
    }
}