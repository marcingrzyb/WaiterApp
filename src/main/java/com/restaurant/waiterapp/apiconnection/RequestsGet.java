package com.restaurant.waiterapp.apiconnection;

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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RequestsGet {
    private RequestsGet() {
    }

    public static List<TableResponse> getTables(String url) {
        URL loginEndpoint;
        String result="error";
        List<TableResponse> tables = null;
        try {
            loginEndpoint = new URL(url);
            HttpURLConnection myConnection;
            myConnection = (HttpURLConnection) loginEndpoint.openConnection();
            myConnection.setRequestMethod("GET");
            if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                InputStream responseBody = myConnection.getInputStream();
                String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                Log.d("tables", stringResponse);
                result=stringResponse;
            }
            myConnection.disconnect();
            tables=parseJsonTables(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tables ;
    }
    public static Map<String, List<FoodResponse>> getMenu(String url) {
        URL loginEndpoint;
        Map<String, List<FoodResponse>> parsed=new HashMap<>();
        try {
            loginEndpoint = new URL(url);
            HttpURLConnection myConnection;
            myConnection = (HttpURLConnection) loginEndpoint.openConnection();
            myConnection.setRequestMethod("GET");
            if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                InputStream responseBody = myConnection.getInputStream();
                String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                Log.d("menu", stringResponse);
                parsed=parseJsonMenu(stringResponse);
            } else {
                Log.d("status", "lipa");
            }
            myConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parsed;
    }

    public static Map<String, List<FoodResponse>> parseJsonMenu(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, List<FoodResponse>> ppl2 = mapper.readValue(jsonString,new TypeReference<HashMap<String, List<FoodResponse>>>() {});
        Log.d("mapa",ppl2.toString());
        return ppl2;

    }

    public static List<TableResponse> parseJsonTables(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<TableResponse> ppl2 = Arrays.asList(mapper.readValue(jsonString, TableResponse[].class));
        return new ArrayList<>(ppl2);

    }
}
