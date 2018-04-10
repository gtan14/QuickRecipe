package com.example.quickrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Gerald on 4/1/2018.
 */

public class LoginActivity extends AppCompatActivity {
    private Button signIn, signUp;
    private EditText username, password;
    private TextInputLayout usernameTxtInputLayout, passTxtInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);
        setTitle("Sign In");

        //  view initialization
        signIn = findViewById(R.id.signInBtn);
        signUp = findViewById(R.id.signUpBtn);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);

        //  on click methods
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, NavDrawerActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
