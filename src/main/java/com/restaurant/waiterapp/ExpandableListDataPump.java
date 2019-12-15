package com.restaurant.waiterapp;


import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.waiterapp.api.resources.FoodResponse;
import com.restaurant.waiterapp.api.resources.TableResponse;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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

    private static AtomicReference<Boolean> asyncFinished = new AtomicReference<>(false);
    static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        getMenu("http://10.0.2.2:8080/api/waiter/menu");
        while (!asyncFinished.get()) {
            // TODO: 12.12.2019
        }
        asyncFinished.set(false);

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
    public static void getMenu(String url) {
        AsyncTask.execute(() -> {
            URL loginEndpoint;
            try {
                loginEndpoint = new URL(url);
                HttpURLConnection myConnection;
                myConnection = (HttpURLConnection) loginEndpoint.openConnection();
                myConnection.setRequestMethod("GET");

                if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                    Log.d("menu", stringResponse);
                    HashMap<String, List<FoodResponse>> parsed=parseJson(stringResponse);
                    Menu=parsed;

                } else {
                    // TODO: 12.12.2019
                    Log.d("status", "lipa");
                }
                asyncFinished.set(true);
                myConnection.disconnect();
            } catch (ProtocolException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static HashMap<String, List<FoodResponse>> parseJson(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, List<FoodResponse>> ppl2 = mapper.readValue(jsonString,new TypeReference<HashMap<String, List<FoodResponse>>>() {});
        Log.d("mapa",ppl2.toString());
        return ppl2;

    }
}