package com.restaurant.waiterapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.io.IOUtils;

public class LoginFragment extends AppCompatActivity {
    AtomicReference<Boolean> sessionGot= new AtomicReference<>(false);
    String result="";
    CookieManager cookieManager;
    AtomicReference<Boolean> asyncFinished= new AtomicReference<>(false);
    protected void setResult(String r){
        Log.d("r",r);
        result=r;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiter_login_screen);
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }
    // Set an error if the password is less than 8 characters.
    public void onClickButtonNext(View view) {
        final TextInputLayout passwordTextInput = (TextInputLayout) findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = (TextInputEditText) findViewById(R.id.password_edit_text);
        final TextInputEditText username = (TextInputEditText) findViewById(R.id.username);
        getSessionId("http://10.0.2.2:8080/login", username.getText().toString(), passwordEditText.getText().toString());
        Log.d("resulta", sessionGot.get().toString());
        while (!asyncFinished.get()) {
            // TODO: 12.12.2019
        }
        if (!sessionGot.get()) {
            passwordTextInput.setError(getString(R.string.error_password));
        } else {
            passwordTextInput.setError(null); // Clear the error
            Intent i = new Intent(getBaseContext(), OrdersActivity.class);
            i.putExtra("username",username.getText().toString());
            startActivity(i);
            finish();
        }
    }
    private void getSessionId(String url,String username,String password){
        AsyncTask.execute(() -> {
            URL loginEndpoint;
            try {
                loginEndpoint = new URL(url+"?username="+username+"&password="+password);
                HttpURLConnection myConnection;
                myConnection=(HttpURLConnection) loginEndpoint.openConnection();
                myConnection.setRequestMethod("POST");

                if (Objects.requireNonNull(myConnection).getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    setResult(IOUtils.toString(responseBody, StandardCharsets.UTF_8));
                    Log.d("resulttrue",result);
                    String cookie = cookieManager.getCookieStore().getCookies().toString();
                    Log.d("cook",cookie);
                    sessionGot.set(true);
                } else {
                    setResult("error");
                    Log.d("resultfail",result);
                    sessionGot.set(false);
                }
                asyncFinished.set(true);
                myConnection.disconnect();
            } catch (ProtocolException | MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
