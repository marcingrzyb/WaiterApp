/*
 *
 *   Copyright 2020 Marcin Grzyb
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

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
