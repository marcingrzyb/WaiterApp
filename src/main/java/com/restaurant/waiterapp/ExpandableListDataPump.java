package com.restaurant.waiterapp;

import android.util.Log;
import com.restaurant.waiterapp.api.resources.FoodResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.restaurant.waiterapp.apiconnection.RequestsGet.getMenu;

public class ExpandableListDataPump {
    private static List<FoodResponse> foodObj;
    private static List<FoodResponse> drinksObj;

    private ExpandableListDataPump() {
    }

    public static List<FoodResponse> getFoodObj() {
        return foodObj;
    }

    public static List<FoodResponse> getDrinksObj() {
        return drinksObj;
    }

    static Map<String, List<String>> getData() {
        Map<String, List<String>> expandableListDetail = new HashMap<>();

        Map<String, List<FoodResponse>> menu = getMenu("http://10.0.2.2:8080/api/waiter/menu");

        foodObj = menu.get("DISH");
        Log.d("food", foodObj.toString());
        drinksObj = menu.get("DRINK");
        List<String> food=new ArrayList<>(foodObj.size());
        for (FoodResponse object : foodObj) {
            food.add(object.toString());
        }
        List<String> drinks=new ArrayList<>(drinksObj.size());
        for (FoodResponse object : drinksObj) {
            drinks.add(object.toString());
        }
        expandableListDetail.put("Food", food);
        expandableListDetail.put("Drinks", drinks);
        return expandableListDetail;
    }
}