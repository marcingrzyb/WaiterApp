package com.restaurant.waiterapp.apiconnection;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestsPatch {
    private static final Logger LOGGER = Logger.getLogger( RequestsPatch.class.getName() );
    private RequestsPatch() {
    }
    public static boolean patchRequest(String url) {
        //builds and sends Patch request
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
                Log.d("status", "lipa");
                InputStream responseBody = myConnection.getInputStream();
                String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                Log.d("Failure", stringResponse);
            }
            myConnection.disconnect();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
        return result;
    }

}
