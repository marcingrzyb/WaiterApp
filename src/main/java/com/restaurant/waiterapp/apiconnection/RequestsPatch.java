package com.restaurant.waiterapp.apiconnection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
                result=true;
            }
            myConnection.disconnect();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        }
        return result;
    }

}
