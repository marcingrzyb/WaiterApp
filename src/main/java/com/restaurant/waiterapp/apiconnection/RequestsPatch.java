package com.restaurant.waiterapp.apiconnection;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class RequestsPatch {
    private RequestsPatch() {
    }

    public static void assignTable(String url) {
        URL loginEndpoint;
        try {
            loginEndpoint = new URL(url);
            HttpURLConnection myConnection;
            myConnection = (HttpURLConnection) loginEndpoint.openConnection();
            myConnection.setRequestMethod("PATCH");
            if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                InputStream responseBody = myConnection.getInputStream();
                String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                Log.d("tables", stringResponse);

            } else {
                Log.d("status", "lipaAssign");
                InputStream responseBody = myConnection.getInputStream();
                String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                Log.d("AssignFailure", stringResponse);
            }

            myConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean finalizeOrder(String url) {
        boolean result=false;
        URL loginEndpoint;
        try {
            loginEndpoint = new URL(url);
            HttpURLConnection myConnection;
            myConnection = (HttpURLConnection) loginEndpoint.openConnection();
            myConnection.setRequestMethod("PATCH");
            if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                InputStream responseBody = myConnection.getInputStream();
                String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                Log.d("tables", stringResponse);
                result=true;

            } else {
                Log.d("status", "lipaFinalize");
                InputStream responseBody = myConnection.getInputStream();
                String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                Log.d("finalizeFailure", stringResponse);
            }
            myConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
