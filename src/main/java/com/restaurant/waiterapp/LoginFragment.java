package com.restaurant.waiterapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.restaurant.waiterapp.apiconnection.RequestsPost;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.atomic.AtomicReference;


public class LoginFragment extends AppCompatActivity {
    AtomicReference<Boolean> sessionGot= new AtomicReference<>(false);
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
        final TextInputLayout passwordTextInput = findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = findViewById(R.id.password_edit_text);
        final TextInputEditText username = findViewById(R.id.username);
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                sessionGot.set(RequestsPost.getSession(strings[0]));
                Log.d("resulta", sessionGot.get().toString());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
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

        }.execute("http://10.0.2.2:8080/login?username="+username.getText().toString()+"&password="+ passwordEditText.getText().toString());

    }


}
