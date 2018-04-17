package com.example.quickrecipe;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Gerald on 4/13/2018.
 */

public class RecipeListFragment extends Fragment {

    private LinearLayout linearLayout;
    private ArrayList<String> cartIngredients;
    private FloatingActionButton addRecipe;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.recipe_list_layout, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState){
        linearLayout = (LinearLayout) v.findViewById(R.id.recipe_list_linear_layout);
        addRecipe = (FloatingActionButton) v.findViewById(R.id.addRecipeFAB);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        Bundle b = this.getArguments();
        cartIngredients = b.getStringArrayList("ingredients");

        if(cartIngredients != null) {
            getRecipes(true);
            addRecipe.setVisibility(View.INVISIBLE);
        }

        else{
            addRecipe.setVisibility(View.VISIBLE);
            getRecipes(false);
        }

        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Add a new recipe?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Fragment addRecipeFragment = new AddOrEditRecipeFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, addRecipeFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
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
    }

    private void getRecipes(boolean filterIngredients){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("recipes", MODE_PRIVATE);
        LayoutInflater layoutInflater = getLayoutInflater();

        Map<String, ?> keys = sharedPreferences.getAll();
        ArrayList<String> recipeIngredientList;
        int numRecipesFound = 0;
        SharedPreferences sp = getActivity().getSharedPreferences("displayHiddenRecipe", MODE_PRIVATE);
        boolean displayHiddenRecipe = sp.getBoolean("display", false);
        for(Map.Entry<String, ?> entry : keys.entrySet()){

            try {
                JSONObject jsonObject = new JSONObject(entry.getValue().toString());
                JSONArray ingredientJSON = jsonObject.getJSONArray("ingredientList");
                recipeIngredientList = new ArrayList<>();
                String recipeName = jsonObject.getString("recipeName");

                if((recipeName.equals("Farmers' Market Chicken Skillet") || recipeName.equals("Farmers Market Chicken Skillet")
                    || recipeName.equals("Farmers' Market Chicken Skillet".toLowerCase()) || recipeName.equals("Farmers Market Chicken Skillet".toLowerCase()))
                        && !displayHiddenRecipe){
                    continue;
                }

                String cookTime = jsonObject.getString("cookTime");
                String prepTime = jsonObject.getString("prepTime");


                for(int i = 0; i < ingredientJSON.length(); i++){
                    recipeIngredientList.add(ingredientJSON.getString(i));
                }

                boolean cont = true;

                if (filterIngredients) {
                    int numMissingIngredients = 0;
                    for (int i = 0; i < recipeIngredientList.size(); i++) {
                        boolean notInCart = false;
                        for (int j = 0; j < cartIngredients.size(); j++) {
                            if (recipeIngredientList.get(i).contains(cartIngredients.get(j).toLowerCase())) {
                                notInCart = true;
                            }
                        }

                        if(!notInCart){
                            numMissingIngredients++;
                        }
                    }

                    if (numMissingIngredients <= 2) {
                        cont = true;
                    } else {
                        cont = false;
                    }
                }

                if (cont) {
                    if(filterIngredients){
                        numRecipesFound++;
                    }
                    View view = layoutInflater.inflate(R.layout.recipe_list_item, linearLayout, false);

                    SquareImageView recipeImg = view.findViewById(R.id.recipeImgListItem);
                    final AppCompatTextView recipeNameTV = view.findViewById(R.id.recipeNameListItem);
                    TextView prepTimeTV = view.findViewById(R.id.prepTimeListItem);
                    TextView cookTimeTV = view.findViewById(R.id.cookTimeListItem);
                    ImageView editRecipe = view.findViewById(R.id.editRecipeImageView);

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
                    linearLayout.addView(view);
                }
            }
            catch (JSONException e){

            }
        }

        if(cartIngredients != null){
            getActivity().setTitle("Recipes found(" + numRecipesFound + ")");
        }

        else{
            getActivity().setTitle("Recipes");
        }

    }


}
