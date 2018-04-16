package com.example.quickrecipe;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
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
    private boolean proceedToSave;
    private String sentRecipeName;

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

        Bundle bundle = getArguments();
        if(bundle != null){
            sentRecipeName = bundle.getString("recipeName");
        }
        Log.d("qw", "qw");
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        setData();

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
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
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

                    String drawableName = jsonRecipeName.replaceAll(",", "").replaceAll(" ", "_").toLowerCase();
                    Resources resources = getActivity().getResources();
                    int resourceId = resources.getIdentifier(drawableName, "drawable", getActivity().getPackageName());

                    //recipeImage.setImageResource(resourceId);
                    getActivity().setTitle(recipeName.getText().toString());

                    return;
                }

            }

            catch (JSONException e){

            }
        }
    }
}
