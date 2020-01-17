package com.restaurant.waiterapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.restaurant.waiterapp.apiconnection.RequestsPost;

import java.net.CookieHandler;
import java.net.CookieManager;

import static com.restaurant.waiterapp.apiconnection.ConnectionConfig.getConnectionConfig;


public class LoginFragment extends AppCompatActivity {
    CookieManager cookieManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiter_login_screen);
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }
    @SuppressLint("StaticFieldLeak")
    public void onClickButtonNext(View view) {
        //triggered after using Button
        final TextInputLayout passwordTextInput = findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = findViewById(R.id.password_edit_text);
        final TextInputEditText username = findViewById(R.id.username);
        // TODO: 17.01.2020 disable button if password or nickname is null
        new AsyncTask<String, Void, Void>() {
            boolean sessionGot=false;
            @Override
            protected Void doInBackground(String... strings) {
                sessionGot=RequestsPost.getSession(strings[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (!sessionGot) {
                    passwordTextInput.setError(getString(R.string.error_password));
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    Intent i = new Intent(getBaseContext(), OrdersActivity.class);
                    i.putExtra("username",username.getText().toString());
                    startActivity(i);
                    finish();
                }
            }

        }.execute(getConnectionConfig()+"/login?username="+username.getText().toString()+"&password="+ passwordEditText.getText().toString());

    }


}
