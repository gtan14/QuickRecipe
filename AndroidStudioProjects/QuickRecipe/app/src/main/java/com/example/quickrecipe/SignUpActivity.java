package com.example.quickrecipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by Gerald on 4/1/2018.
 */

public class SignUpActivity extends AppCompatActivity {
    private EditText firstName, username, password, reenterPass;
    private TextInputLayout firstNameTextLayout, usernameTextLayout, passwordTextLayout, reenterPassTextLayout;
    private Button register;
    private boolean proceedWithSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_layout);
        setTitle("Register");

        //  initializes the back arrow
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //  view initialization
        firstName = findViewById(R.id.firstNameEditTextSignUp);
        username = findViewById(R.id.usernameEditTxtSignUp);
        password = findViewById(R.id.passEditTxtSignUp);
        reenterPass = findViewById(R.id.reenterPassEditTxtSignUp);
        register = findViewById(R.id.registerSignUpBtn);
        firstNameTextLayout = findViewById(R.id.firstNameSignUpTxtInputLayout);
        usernameTextLayout = findViewById(R.id.usernameSignUpTxtInputLayout);
        passwordTextLayout = findViewById(R.id.passwordSignupTxtInputLayout);
        reenterPassTextLayout = findViewById(R.id.reenterPassSignUpTxtInputLayout);

        textListeners();

        //  on click method
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkErrors();
                if(proceedWithSignUp) {
                    saveUserAccount();
                    Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);

                }
            }
        });

    }

    private void saveUserAccount(){
        SharedPreferences.Editor editor = getSharedPreferences("users", MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String user = gson.toJson(new UserAccount(firstName.getText().toString(), username.getText().toString(), password.getText().toString()));
        editor.putString(username.getText().toString(), user);
        editor.apply();
    }

    private void textListeners(){
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstNameTextLayout.setErrorEnabled(false);
                firstNameTextLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                usernameTextLayout.setErrorEnabled(false);
                usernameTextLayout.setError(null);
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
                passwordTextLayout.setErrorEnabled(false);
                passwordTextLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        reenterPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reenterPassTextLayout.setErrorEnabled(false);
                reenterPassTextLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkErrors(){
        proceedWithSignUp = true;

        if(firstName.getText().toString().isEmpty()){
            firstNameTextLayout.setErrorEnabled(true);
            firstNameTextLayout.setError("Enter first name");
            proceedWithSignUp = false;
        }

        else if(firstName.getText().toString().matches(".*\\d+.*")){
            firstNameTextLayout.setErrorEnabled(true);
            firstNameTextLayout.setError("Enter valid first name");
            proceedWithSignUp = false;
        }

        if(username.getText().toString().isEmpty()){
            usernameTextLayout.setErrorEnabled(true);
            usernameTextLayout.setError("Enter username");
            proceedWithSignUp = false;
        }

        if(password.getText().toString().isEmpty()){
            passwordTextLayout.setErrorEnabled(true);
            passwordTextLayout.setError("Enter password");
            proceedWithSignUp = false;
        }

        if(reenterPass.getText().toString().isEmpty()){
            reenterPassTextLayout.setErrorEnabled(true);
            reenterPassTextLayout.setError("Re-enter password");
            proceedWithSignUp = false;
        }

        else if(!reenterPass.getText().toString().equals(password.getText().toString())){
            reenterPassTextLayout.setErrorEnabled(true);
            reenterPassTextLayout.setError("Password does not match");
            proceedWithSignUp = false;
        }
    }

    //  allows users to navigate back to sign in screen by pressing upper left arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
