package com.example.quickrecipe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {

    private NavDrawerActivity activity;
    private LinearLayout allergiesLayout;
    private LinearLayout favoritesLayout;
    private TextView firstName;
    private Button addAllergies;
    private LayoutInflater layoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.account_layout, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState){
        allergiesLayout = v.findViewById(R.id.allergiesLinearLayout);
        favoritesLayout = v.findViewById(R.id.favoritesLinearLayout);
        firstName = v.findViewById(R.id.firstNameAccount);
        addAllergies = v.findViewById(R.id.addAllergiesBtn);
    }

    @Override
    public void onPause(){
        super.onPause();
        Gson gson = new Gson();
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < allergiesLayout.getChildCount(); i++){
            View view = allergiesLayout.getChildAt(i);
            EditText allergy = view.findViewById(R.id.allergyEditText);
            if(allergy != null) {
                list.add(allergy.getText().toString());
            }
        }
        Allergies allergies = new Allergies();
        allergies.setAllergiesList(list);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("allergies", MODE_PRIVATE);
        String allergy = gson.toJson(allergies);
        sharedPreferences.edit().putString("allergy", allergy).apply();
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        getActivity().setTitle("Account");

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("signedInUser", MODE_PRIVATE);
        String user = sharedPreferences1.getString("user", "");
        firstName.setText(user);

        layoutInflater = getLayoutInflater();
        setUpFavorites();
        setUpAllergies();

        addAllergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View allergyView = layoutInflater.inflate(R.layout.allergy_layout, null);
                ImageView deleteAllergy = allergyView.findViewById(R.id.deleteAllergy);

                deleteAllergy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Delete allergy?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                allergiesLayout.removeView(allergyView);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                });

                allergiesLayout.addView(allergyView, 0);
            }
        });
    }

    private void setUpFavorites(){
        ArrayList<String> names = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("favorites", MODE_PRIVATE);
        Map<String, ?> keys = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            if(entry.getValue().toString().equals("true")){
                names.add(entry.getKey());
            }
        }


        SharedPreferences sp = getActivity().getSharedPreferences("recipes", MODE_PRIVATE);
        LayoutInflater layoutInflater = getLayoutInflater();

        Map<String, ?> key = sp.getAll();
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("displayHiddenRecipe", MODE_PRIVATE);
        boolean displayHiddenRecipe = sharedPreferences1.getBoolean("display", false);
        for(Map.Entry<String, ?> entry : key.entrySet()){
            try {
                JSONObject jsonObject = new JSONObject(entry.getValue().toString());
                final String recipeName = jsonObject.getString("recipeName");

                if((recipeName.equals("Farmers' Market Chicken Skillet") || recipeName.equals("Farmers Market Chicken Skillet")
                        || recipeName.equals("Farmers' Market Chicken Skillet".toLowerCase()) || recipeName.equals("Farmers Market Chicken Skillet".toLowerCase()))
                        && !displayHiddenRecipe){
                    continue;
                }

                for(int i = 0; i < names.size(); i++){
                    if(names.get(i).equals(recipeName)){
                        String cookTime = jsonObject.getString("cookTime");
                        String prepTime = jsonObject.getString("prepTime");


                            View view = layoutInflater.inflate(R.layout.recipe_list_item, favoritesLayout, false);

                            SquareImageView recipeImg = view.findViewById(R.id.recipeImgListItem);
                            final AppCompatTextView recipeNameTV = view.findViewById(R.id.recipeNameListItem);
                            TextView prepTimeTV = view.findViewById(R.id.prepTimeListItem);
                            TextView cookTimeTV = view.findViewById(R.id.cookTimeListItem);
                            ImageView editRecipe = view.findViewById(R.id.editRecipeImageView);
                            ImageView favoriteRecipe = view.findViewById(R.id.recipeFavorite);

                            recipeNameTV.setText(recipeName);


                            if(recipeName != null) {
                                String drawableName = recipeName.replaceAll(",", "").replaceAll(" ", "_").replaceAll("'", "").toLowerCase();
                                //Resources resources = getActivity().getResources();
                                //int resourceId = resources.getIdentifier(drawableName, "drawable", getActivity().getPackageName());
                                String file = "/data/user/0/com.example.quickrecipe/app_files/" + drawableName + ".png";
                                Bitmap bitmap = BitmapFactory.decodeFile(file);
                                recipeImg.setImageBitmap(bitmap);
                            }

                            prepTimeTV.setText("Prep time:\n" + prepTime);
                            cookTimeTV.setText("Cook time:\n" + cookTime);

                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Fragment recipeViewFragment = new RecipeViewFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("recipeName", recipeNameTV.getText().toString());
                                    recipeViewFragment.setArguments(bundle);
                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.content_frame, recipeViewFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            });

                            editRecipe.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Fragment editRecipeFragment = new AddOrEditRecipeFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("recipeName", recipeNameTV.getText().toString());
                                    editRecipeFragment.setArguments(bundle);
                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.content_frame, editRecipeFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            });

                            favoriteRecipe.setColorFilter(Color.argb(255, 255, 0, 0));
                            favoritesLayout.addView(view);
                        }
                }

            }
            catch (JSONException e){

            }
        }


    }

    private void setUpAllergies() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("allergies", MODE_PRIVATE);
        Map<String, ?> keys = sharedPreferences.getAll();
        ArrayList<String> allergyList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {

            try {
                JSONObject jsonObject = new JSONObject(entry.getValue().toString());
                JSONArray ingredientJSON = jsonObject.getJSONArray("allergiesList");
                for(int i = 0; i < ingredientJSON.length(); i++){
                    allergyList.add(ingredientJSON.getString(i));
                }
            } catch (JSONException e) {

            }
        }
        if(allergyList.size() > 0){
            for(int i = 0; i< allergyList.size(); i++){
                LayoutInflater layoutInflater = getLayoutInflater();
                final View view = layoutInflater.inflate(R.layout.allergy_layout, null);
                EditText allergy = view.findViewById(R.id.allergyEditText);
                ImageView deleteAllergy = view.findViewById(R.id.deleteAllergy);
                allergy.setText(allergyList.get(i));

                deleteAllergy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Delete allergy?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                allergiesLayout.removeView(view);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                });
                allergiesLayout.addView(view, 0);
            }
        }
    }
}
