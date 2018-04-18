package com.example.quickrecipe;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Gerald on 4/1/2018.
 */

public class LoginActivity extends AppCompatActivity {
    private Button signIn, signUp;
    private EditText username, password;
    private CheckBox rememberMe;
    public static String fileDir;
    private TextInputLayout usernameTxtInputLayout, passTxtInputLayout;
    private boolean proceedSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences1 = getSharedPreferences("favorites", MODE_PRIVATE);
        sharedPreferences1.edit().clear().apply();

        SharedPreferences sharedPreferences = getSharedPreferences("rememberMe", MODE_PRIVATE);
        boolean skipLoginPage = sharedPreferences.getBoolean("check", false);

        SharedPreferences.Editor sp = getSharedPreferences("displayHiddenRecipe", MODE_PRIVATE).edit();
        sp.clear().apply();
        createFiles();


        if(skipLoginPage){
            Intent intent = new Intent(LoginActivity.this, NavDrawerActivity.class);
            startActivity(intent);
        }


        setContentView(R.layout.login_layout);
        setTitle("Sign In");

        //sharedPreferences.edit().clear().apply();

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
                    SharedPreferences sharedPreferences1 = getSharedPreferences("signedInUser", MODE_PRIVATE);
                    sharedPreferences1.edit().putString("user", jsonObject.getString("firstName")).apply();
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

    private void createFiles(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("qweio", "weqi");
                Bitmap groundBeefSkillet = BitmapFactory.decodeResource(LoginActivity.this.getResources(), R.drawable.skillet_of_ground_beef_tomatoes_and_parsley_topped_with_egg_and_lemon_juice);
                Bitmap shrimpTomatoPasta = BitmapFactory.decodeResource(LoginActivity.this.getResources(), R.drawable.shrimp_tomato_and_spinach_pasta);
                Bitmap porkChopsWithZucchini = BitmapFactory.decodeResource(LoginActivity.this.getResources(), R.drawable.pork_chops_with_zucchini_tomatoes_and_mozzarella_cheese);
                Bitmap lemonBakedSalmon = BitmapFactory.decodeResource(LoginActivity.this.getResources(), R.drawable.tomato_lemon_baked_salmon_and_asparagus);
                Bitmap chickenSkillet = BitmapFactory.decodeResource(LoginActivity.this.getResources(), R.drawable.farmers_market_chicken_skillet);
                Bitmap bakedChicken = BitmapFactory.decodeResource(LoginActivity.this.getResources(), R.drawable.baked_chicken_with_peppers_and_mushrooms);

                ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
                File dir = contextWrapper.getDir(getFilesDir().getName(), Context.MODE_PRIVATE);

                fileDir = dir.getPath();
                boolean doSave = true;
                if (!dir.exists()) {
                    doSave = dir.mkdirs();
                }

                if (doSave) {
                    saveBitmapToFile(dir,"skillet_of_ground_beef_tomatoes_and_parsley_topped_with_egg_and_lemon_juice.png",groundBeefSkillet,Bitmap.CompressFormat.PNG,100);
                    saveBitmapToFile(dir,"shrimp_tomato_and_spinach_pasta.png",shrimpTomatoPasta,Bitmap.CompressFormat.PNG,100);
                    saveBitmapToFile(dir,"pork_chops_with_zucchini_tomatoes_and_mozzarella_cheese.png",porkChopsWithZucchini,Bitmap.CompressFormat.PNG,100);
                    saveBitmapToFile(dir,"tomato_lemon_baked_salmon_and_asparagus.png",lemonBakedSalmon,Bitmap.CompressFormat.PNG,100);
                    saveBitmapToFile(dir,"farmers_market_chicken_skillet.png",chickenSkillet,Bitmap.CompressFormat.PNG,100);
                    saveBitmapToFile(dir,"baked_chicken_with_peppers_and_mushrooms.png",bakedChicken,Bitmap.CompressFormat.PNG,100);
                }
                else {
                    Log.e("app","Couldn't create target directory.");
                }
            }
        }, 1000);
    }

    private boolean saveBitmapToFile(File dir, String fileName, Bitmap bm,
                             Bitmap.CompressFormat format, int quality) {

        File imageFile = new File(dir,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            bm.compress(format,quality,fos);

            fos.close();

            return true;
        }
        catch (IOException e) {
            Log.e("app",e.getMessage());
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }
}
