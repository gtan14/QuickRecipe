package com.example.quickrecipe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Gerald on 4/15/2018.
 */

public class AddOrEditRecipeFragment extends Fragment {

    private EditText recipeName;
    private EditText prepTime;
    private EditText cookTime;
    private EditText ingredients;
    private EditText instructions;
    private TextInputLayout recipeNameInputLayout;
    private TextInputLayout prepTimeInputLayout;
    private TextInputLayout cookTimeInputLayout;
    private TextInputLayout ingredientsInputLayout;
    private TextInputLayout instructionsInputLayout;
    private Button cancel;
    private Button save;
    private SquareImageView recipeImgUpload;
    private boolean proceedToSave;
    private String sentRecipeName;
    public static final int GET_FROM_GALLERY = 3;
    private String recipeNameBeforeChange;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.add_edit_recipe_layout, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState){
        recipeName = (EditText) v.findViewById(R.id.recipeNameEditRecipe);
        prepTime = (EditText) v.findViewById(R.id.prepTimeEditRecipe);
        cookTime = (EditText) v.findViewById(R.id.cookTimeEditRecipe);
        ingredients = (EditText) v.findViewById(R.id.ingredientsEditRecipe);
        instructions = (EditText) v.findViewById(R.id.instructionsEditRecipe);
        cancel = (Button) v.findViewById(R.id.cancelRecipeEdit);
        save = (Button) v.findViewById(R.id.saveRecipeEdit);
        recipeNameInputLayout = v.findViewById(R.id.recipeNameTextInputLayout);
        prepTimeInputLayout = v.findViewById(R.id.prepTimeTextInputLayout);
        cookTimeInputLayout = v.findViewById(R.id.cookTimeTextInputLayout);
        ingredientsInputLayout = v.findViewById(R.id.ingredientsTextInputLayout);
        instructionsInputLayout = v.findViewById(R.id.instructionsTextInputLayout);
        recipeImgUpload = v.findViewById(R.id.recipeImageUpload);

        Bundle bundle = getArguments();
        if(bundle != null){
            sentRecipeName = bundle.getString("recipeName");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                recipeImgUpload.setImageBitmap(bitmap);
                recipeImgUpload.setTag(bitmap.describeContents());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        recipeImgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        if(sentRecipeName != null){
            setData();
            recipeImgUpload.setOnClickListener(null);
        }

        else{
            recipeImgUpload.setTag(R.drawable.upload);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForErrors();

                if(proceedToSave) {
                    saveRecipe();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
    }

    private void saveRecipe(){
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("recipes", MODE_PRIVATE).edit();
        Gson gson = new Gson();
        ArrayList<String> ingredientsList = new ArrayList<String>();
        ingredientsList.addAll(Arrays.asList(ingredients.getText().toString().split("\"\\\\W+\"")));
        String savedName = recipeName.getText().toString().replaceAll(",", "").replaceAll(" ", "_").replaceAll("'", "").toLowerCase();
        String json = gson.toJson(new Recipe(recipeName.getText().toString(), prepTime.getText().toString(), cookTime.getText().toString(), instructions.getText().toString(), ingredientsList));
        editor.putString(savedName, json);
        editor.apply();

        if(sentRecipeName != null) {
            if(!recipeNameBeforeChange.equals(recipeName.getText().toString().replaceAll(",", "").replaceAll(" ", "_").replaceAll("'", "").toLowerCase())) {
                Log.d("recipeNameBeforeChange", recipeNameBeforeChange);
                Log.d("newName", recipeName.getText().toString().replaceAll(",", "").replaceAll(" ", "_").replaceAll("'", "").toLowerCase());
                File source = new File("/data/user/0/com.example.quickrecipe/app_files/" + recipeNameBeforeChange + ".png");
                File destination = new File("/data/user/0/com.example.quickrecipe/app_files/" + recipeName.getText().toString().replaceAll(",", "").replaceAll(" ", "_").replaceAll("'", "").toLowerCase() + ".png");
                boolean renamed = source.renameTo(destination);
                if (renamed) {
                    Log.d("renamed", "Renamed");
                }
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("recipes", MODE_PRIVATE);
                sharedPreferences.edit().remove(recipeNameBeforeChange).apply();
            }

        }

        if(recipeName.getText().toString().equals("Farmers' Market Chicken Skillet") || recipeName.getText().toString().equals("Farmers Market Chicken Skillet")
                || recipeName.getText().toString().equals("Farmers' Market Chicken Skillet".toLowerCase()) || recipeName.getText().toString().equals("Farmers Market Chicken Skillet".toLowerCase())) {
            SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences("displayHiddenRecipe", MODE_PRIVATE).edit();
            sharedPreferences.putBoolean("display", true).apply();
        }

        Toast.makeText(getActivity(), "Save successful", Toast.LENGTH_SHORT).show();
    }

    private void checkForErrors(){

        proceedToSave = true;

        if(recipeName.getText().toString().isEmpty()){
            recipeNameInputLayout.setErrorEnabled(true);
            recipeNameInputLayout.setError("Enter recipe name");
            proceedToSave = false;
        }

        if(prepTime.getText().toString().isEmpty()){
            prepTimeInputLayout.setErrorEnabled(true);
            prepTimeInputLayout.setError("Enter prep time");
            proceedToSave = false;
        }

        else if(!prepTime.getText().toString().matches(".*\\d+.*")){
            prepTimeInputLayout.setErrorEnabled(true);
            prepTimeInputLayout.setError("Enter a valid time");
            proceedToSave = false;
        }

        if(cookTime.getText().toString().isEmpty()){
            cookTimeInputLayout.setErrorEnabled(true);
            cookTimeInputLayout.setError("Enter cook time");
            proceedToSave = false;
        }

        else if(!cookTime.getText().toString().matches(".*\\d+.*")){
            cookTimeInputLayout.setErrorEnabled(true);
            cookTimeInputLayout.setError("Enter a valid time");
            proceedToSave = false;
        }

        if(ingredients.getText().toString().isEmpty()){
            ingredientsInputLayout.setErrorEnabled(true);
            ingredientsInputLayout.setError("Enter ingredients");
            proceedToSave = false;
        }

        if(instructions.getText().toString().isEmpty()){
            instructionsInputLayout.setErrorEnabled(true);
            instructionsInputLayout.setError("Enter instructions");
            proceedToSave = false;
        }

        if(sentRecipeName == null) {
            if (((int) recipeImgUpload.getTag()) == (R.drawable.upload)) {
                Toast.makeText(getActivity(), "Upload an image", Toast.LENGTH_SHORT).show();
                proceedToSave = false;
            }
        }
    }

    private void setData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("recipes", MODE_PRIVATE);

        Map<String, ?> keys = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            try {
                JSONObject jsonObject = new JSONObject(entry.getValue().toString());
                String jsonRecipeName = jsonObject.getString("recipeName");
                if(jsonRecipeName.equals(sentRecipeName)){
                    JSONArray ingredientJSON = jsonObject.getJSONArray("ingredientList");
                    String jsonCookTime = jsonObject.getString("cookTime");
                    String jsonPrepTime = jsonObject.getString("prepTime");
                    String jsonInstructions = jsonObject.getString("instructions");
                    StringBuilder ingredientsBuilder = new StringBuilder();

                    recipeName.setText(jsonRecipeName);
                    cookTime.setText(jsonCookTime);
                    prepTime.setText(jsonPrepTime);
                    instructions.setText(jsonInstructions);

                    for(int i = 0; i < ingredientJSON.length(); i++){
                        String result = ingredientJSON.getString(i) + "\n";
                        ingredientsBuilder.append(result);
                    }

                    ingredients.setText(ingredientsBuilder.toString());

                    String drawableName = jsonRecipeName.replaceAll(",", "").replaceAll(" ", "_").replaceAll("'", "").toLowerCase();
                    //Resources resources = getActivity().getResources();
                    String file = "/data/user/0/com.example.quickrecipe/app_files/"  + drawableName + ".png";
                    Bitmap bitmap = BitmapFactory.decodeFile(file);
                    //int resourceId = resources.getIdentifier(drawableName, "drawable", getActivity().getPackageName());

                    recipeImgUpload.setImageBitmap(bitmap);
                    getActivity().setTitle(recipeName.getText().toString());
                    recipeNameBeforeChange = recipeName.getText().toString().replaceAll(",", "").replaceAll(" ", "_").replaceAll("'", "").toLowerCase();

                    return;
                }

            }

            catch (JSONException e){

            }
        }
    }

    private void textListeners(){
        recipeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recipeNameInputLayout.setErrorEnabled(false);
                recipeNameInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        prepTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prepTimeInputLayout.setErrorEnabled(false);
                prepTimeInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cookTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cookTimeInputLayout.setErrorEnabled(false);
                cookTimeInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ingredients.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ingredientsInputLayout.setErrorEnabled(false);
                ingredientsInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        instructions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                instructionsInputLayout.setErrorEnabled(false);
                instructionsInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
