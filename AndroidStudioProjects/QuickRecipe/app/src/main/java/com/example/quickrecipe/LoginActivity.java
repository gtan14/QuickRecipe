package com.example.quickrecipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Gerald on 4/1/2018.
 */

public class LoginActivity extends AppCompatActivity {
    private Button signIn, signUp;
    private EditText username, password;
    private TextInputLayout usernameTxtInputLayout, passTxtInputLayout;
    private boolean proceedSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);
        setTitle("Sign In");

        SharedPreferences sharedPreferences = getSharedPreferences("recipes", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        //  view initialization
        signIn = findViewById(R.id.signInBtn);
        signUp = findViewById(R.id.signUpBtn);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        usernameTxtInputLayout = findViewById(R.id.usernameTextInputLayout);
        passTxtInputLayout = findViewById(R.id.passwordTextInputLayout);

        proceedSignIn = false;

        //  on click methods
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkErrors();
                signInIfUserExists();
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

    private void checkErrors(){
        if(username.getText().toString().isEmpty()){
            usernameTxtInputLayout.setErrorEnabled(true);
            usernameTxtInputLayout.setError("Enter username");
            proceedSignIn = false;
        }

        if(password.getText().toString().isEmpty()){
            passTxtInputLayout.setErrorEnabled(true);
            passTxtInputLayout.setError("Enter password");
            proceedSignIn = false;
        }

    }

    private void signInIfUserExists() {
        SharedPreferences sharedPreferences = getSharedPreferences("users", MODE_PRIVATE);

        Map<String, ?> keys = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {

            try {
                JSONObject jsonObject = new JSONObject(entry.getValue().toString());
                String usernameJSON = jsonObject.getString("username");
                String passwordJSON = jsonObject.getString("password");

                if(usernameJSON.equals(username.getText().toString()) && passwordJSON.equals(password.getText().toString())){
                    proceedSignIn = true;
                }
            }

            catch (JSONException e){

            }
        }

        if(proceedSignIn){
            Intent intent = new Intent(LoginActivity.this, NavDrawerActivity.class);
            startActivity(intent);
        }
    }
}
