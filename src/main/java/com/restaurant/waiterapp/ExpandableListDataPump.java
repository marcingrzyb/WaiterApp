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