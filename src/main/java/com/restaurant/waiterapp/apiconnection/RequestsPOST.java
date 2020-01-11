package com.restaurant.waiterapp.apiconnection;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class RequestsPOST {
    private RequestsPOST() {
    }

    public static Boolean getSession(String url){
       Boolean result=false;
        URL loginEndpoint;
        try {
            loginEndpoint = new URL(url);
            HttpURLConnection myConnection;
            myConnection=(HttpURLConnection) loginEndpoint.openConnection();
            myConnection.setRequestMethod("POST");
            if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                InputStream responseBody = myConnection.getInputStream();
                String response=(IOUtils.toString(responseBody, StandardCharsets.UTF_8));
                Log.d("cook",response);
                result=true;
            } else {
                Log.d("resultfail",result.toString());
            }
            myConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Boolean sendOrder(String url, String orderRequest) {
            URL loginEndpoint;
            Boolean result=false;
            try {
                loginEndpoint = new URL(url);
                HttpURLConnection myConnection;
                myConnection = (HttpURLConnection) loginEndpoint.openConnection();
                myConnection.setRequestMethod("POST");
                myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                myConnection.setDoOutput(true); //this is to enable writing
                myConnection.setDoInput(true);  //this is to enable reading
                writeToOutputStream(orderRequest, myConnection);
                if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                    Log.d("tables", stringResponse);
                    result=true;
                    Log.d("rezult",result.toString());
                } else {
                    Log.d("statusNegative", "lipaSend");
                    InputStream responseBody = myConnection.getInputStream();
                    String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                    Log.d("sendFailure", stringResponse);

                }

                myConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return result;
    }
    public static Boolean sendFeedback(String url, String feedback) {
        URL loginEndpoint;
        Boolean result=false;
        try {
            loginEndpoint = new URL(url);
            HttpURLConnection myConnection;
            myConnection = (HttpURLConnection) loginEndpoint.openConnection();
            myConnection.setRequestMethod("POST");
            myConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            myConnection.setRequestProperty("Accept", "application/json");
            myConnection.setDoOutput(true); //this is to enable writing
            myConnection.setDoInput(true);  //this is to enable reading
            writeToOutputStream(feedback, myConnection);
            Log.d("status",String.valueOf(Objects.requireNonNull(myConnection).getResponseCode()));
            if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                InputStream responseBody = myConnection.getInputStream();
                String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                Log.d("response", stringResponse);
                result=true;
                Log.d("rezult",result.toString());
            } else {

                Log.d("status", "lipaSend");
                InputStream responseBody = myConnection.getInputStream();
                String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                Log.d("sendFailure", stringResponse);

            }

            myConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void writeToOutputStream(String feedback, HttpURLConnection myConnection) {
        try (OutputStream os = myConnection.getOutputStream()) {
            byte[] input = feedback.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
