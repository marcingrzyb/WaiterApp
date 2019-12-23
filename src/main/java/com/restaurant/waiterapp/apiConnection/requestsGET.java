package com.restaurant.waiterapp.apiConnection;

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
import java.util.Objects;

public class requestsGET {
    public static ArrayList<TableResponse> getTables(String url) {
        URL loginEndpoint;
        String result="error";
        ArrayList<TableResponse> tables = null;
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
            } else {
                result="error";
            }
            myConnection.disconnect();
            tables=parseJsonTables(result);
        } catch (ProtocolException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tables ;
    }
    public static HashMap<String, List<FoodResponse>> getMenu(String url) {
        URL loginEndpoint;
        HashMap<String, List<FoodResponse>> parsed=new HashMap<String, List<FoodResponse>>();
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
        } catch (ProtocolException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parsed;
    }

    public static HashMap<String, List<FoodResponse>> parseJsonMenu(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, List<FoodResponse>> ppl2 = mapper.readValue(jsonString,new TypeReference<HashMap<String, List<FoodResponse>>>() {});
        Log.d("mapa",ppl2.toString());
        return ppl2;

    }

    public static ArrayList<TableResponse> parseJsonTables(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<TableResponse> ppl2 = Arrays.asList(mapper.readValue(jsonString, TableResponse[].class));
        return new ArrayList<TableResponse>(ppl2);

    }
}
