package com.restaurant.waiterapp.apiconnection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestsPost {
    private static final Logger LOGGER = Logger.getLogger( RequestsPost.class.getName() );
    private RequestsPost() {
    }

    public static Boolean getSession(String url){
        //builds and sends request which authorizes session
       Boolean result=false;
        URL loginEndpoint;
        try {
            loginEndpoint = new URL(url);
            HttpURLConnection myConnection;
            myConnection=(HttpURLConnection) loginEndpoint.openConnection();
            myConnection.setRequestMethod("POST");
            if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                result=true;
            }
            myConnection.disconnect();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
        return result;
    }

    public static Boolean sendPost(String url, String stringRequest) {
        //builds and sends Post request
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
            writeToOutputStream(stringRequest, myConnection);
            if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                result=true;
            }
            myConnection.disconnect();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
        return result;
    }

    private static void writeToOutputStream(String feedback, HttpURLConnection myConnection) {
        //writes data to Output Stream
        try (OutputStream os = myConnection.getOutputStream()) {
            byte[] input = feedback.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }
}