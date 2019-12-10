package com.restaurant.waiterapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiter_login_screen);
    }
    // Set an error if the password is less than 8 characters.
    public void onClickButtonNext(View view){
        final TextInputLayout passwordTextInput = (TextInputLayout)findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = (TextInputEditText)findViewById(R.id.password_edit_text);
        final TextInputEditText username = (TextInputEditText)findViewById(R.id.username);

        if (!isPasswordValid(passwordEditText.getText())) {
            passwordTextInput.setError(getString(R.string.error_password));
        } else {
            passwordTextInput.setError(null); // Clear the error
            Intent i = new Intent(getBaseContext(), OrdersActivity.class);
            startActivity(i);
            finish();
        }
    }
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 3;
    }
}
