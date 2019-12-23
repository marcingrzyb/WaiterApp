package com.restaurant.waiterapp;

import android.util.Log;
import com.restaurant.waiterapp.api.resources.FoodResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.restaurant.waiterapp.apiConnection.requestsGET.getMenu;

public class ExpandableListDataPump {
    private static HashMap<String, List<FoodResponse>> Menu;
    private static List<FoodResponse> FoodObj;
    private static List<FoodResponse> DrinksObj;

    public static List<FoodResponse> getFoodObj() {
        return FoodObj;
    }

    public static List<FoodResponse> getDrinksObj() {
        return DrinksObj;
    }

    static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        Menu=getMenu("http://10.0.2.2:8080/api/waiter/menu");

        FoodObj = Menu.get("DISH");
        Log.d("food",FoodObj.toString());
        DrinksObj = Menu.get("DRINK");
        List<String> Food=new ArrayList<>(FoodObj.size());
        for (FoodResponse object : FoodObj) {
            Food.add(object.toString());
        }
        List<String> Drinks=new ArrayList<>(DrinksObj.size());
        for (FoodResponse object : DrinksObj) {
            Drinks.add(object.toString());
        }
        expandableListDetail.put("Food", Food);
        expandableListDetail.put("Drinks", Drinks);
        return expandableListDetail;
    }
}