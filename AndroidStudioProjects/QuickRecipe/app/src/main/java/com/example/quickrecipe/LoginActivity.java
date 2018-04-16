package com.example.quickrecipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

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
    private CheckBox rememberMe;
    private TextInputLayout usernameTxtInputLayout, passTxtInputLayout;
    private boolean proceedSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("rememberMe", MODE_PRIVATE);
        boolean skipLoginPage = sharedPreferences.getBoolean("check", false);

        if(skipLoginPage){
            Intent intent = new Intent(LoginActivity.this, NavDrawerActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.login_layout);
        setTitle("Sign In");

        //  view initialization
        signIn = findViewById(R.id.signInBtn);
        signUp = findViewById(R.id.signUpBtn);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        usernameTxtInputLayout = findViewById(R.id.usernameTextInputLayout);
        passTxtInputLayout = findViewById(R.id.passwordTextInputLayout);
        rememberMe = findViewById(R.id.rememberMeChkBox);



        proceedSignIn = false;

        textListeners();

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences1 = getSharedPreferences("rememberMe", MODE_PRIVATE);
                sharedPreferences1.edit().putBoolean("check", isChecked).apply();
            }
        });

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

    private void textListeners(){
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameTxtInputLayout.setError(null);
                usernameTxtInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passTxtInputLayout.setError(null);
                passTxtInputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

        else{
            if(username.getText().toString().length() > 0 && password.getText().toString().length() > 0){
                usernameTxtInputLayout.setErrorEnabled(true);
                usernameTxtInputLayout.setError("Re-enter username");
                passTxtInputLayout.setErrorEnabled(true);
                passTxtInputLayout.setError("Re-enter password");
                Toast.makeText(this, "Username or password does not exist", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
