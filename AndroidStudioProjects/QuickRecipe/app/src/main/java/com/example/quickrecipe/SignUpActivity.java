package com.example.quickrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Gerald on 4/1/2018.
 */

public class SignUpActivity extends AppCompatActivity {
    private EditText firstName, username, password, reenterPass;
    private Button register;

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

        //  on click method
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

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
