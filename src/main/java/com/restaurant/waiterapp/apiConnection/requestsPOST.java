package com.restaurant.waiterapp.apiConnection;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class requestsPOST {

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
                result=false;
                Log.d("resultfail",result.toString());
            }
            myConnection.disconnect();
        } catch (ProtocolException | MalformedURLException e) {
            e.printStackTrace();
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
                try(OutputStream os = myConnection.getOutputStream()) {
                    byte[] input = orderRequest.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                    Log.d("tables", stringResponse);
                    result=true;
                    Log.d("rezult",result.toString());
                } else {
                    // TODO: 12.12.2019
                    Log.d("status", "lipaSend");
                    InputStream responseBody = myConnection.getInputStream();
                    String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                    Log.d("sendFailure", stringResponse);

                }

                myConnection.disconnect();
            } catch (ProtocolException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return result;
    }
}
