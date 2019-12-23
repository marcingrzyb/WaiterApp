package com.restaurant.waiterapp.apiConnection;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class requestsPATCH {
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
                    // TODO: 12.12.2019
                    Log.d("status", "lipaAssign");
                    InputStream responseBody = myConnection.getInputStream();
                    String stringResponse = IOUtils.toString(responseBody, StandardCharsets.UTF_8);
                    Log.d("AssignFailure", stringResponse);
                }

                myConnection.disconnect();
            } catch (ProtocolException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
