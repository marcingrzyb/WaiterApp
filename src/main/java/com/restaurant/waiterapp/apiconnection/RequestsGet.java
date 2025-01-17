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
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestsGet {
    private static final Logger LOGGER = Logger.getLogger( RequestsGet.class.getName() );
    private RequestsGet() {
    }

    public static List<TableResponse> getTables(String url) {
        //builds and sends request which gets List of Tables
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
                result = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
            }
            myConnection.disconnect();
            tables=parseJsonTables(result);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
        return tables ;
    }
    public static Map<String, List<FoodResponse>> getMenu(String url) {
        //builds and sends request which gets Menu
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
            }
            myConnection.disconnect();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
        return parsed;
    }

    public static Map<String, List<FoodResponse>> parseJsonMenu(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString,new TypeReference<HashMap<String, List<FoodResponse>>>() {});

    }

    public static List<TableResponse> parseJsonTables(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<TableResponse> ppl2 = Arrays.asList(mapper.readValue(jsonString, TableResponse[].class));
        return new ArrayList<>(ppl2);

    }
}
