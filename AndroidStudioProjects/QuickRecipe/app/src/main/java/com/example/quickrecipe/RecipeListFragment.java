package com.example.quickrecipe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.recipe_list_layout, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState){
        linearLayout = (LinearLayout) v.findViewById(R.id.recipe_list_linear_layout);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        Log.d("qw", "qw");
        Bundle b = this.getArguments();
        cartIngredients = b.getStringArrayList("ingredients");

        if(cartIngredients != null) {
            getRecipes(true);
        }

        else{
            getRecipes(false);
        }
    }

    private void getRecipes(boolean filterIngredients){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("recipes", MODE_PRIVATE);
        LayoutInflater layoutInflater = getLayoutInflater();

        Map<String, ?> keys = sharedPreferences.getAll();
        ArrayList<String> recipeIngredientList;
        int numRecipesFound = 0;
        for(Map.Entry<String, ?> entry : keys.entrySet()){

            try {
                JSONObject jsonObject = new JSONObject(entry.getValue().toString());
                JSONArray ingredientJSON = jsonObject.getJSONArray("ingredientList");
                recipeIngredientList = new ArrayList<>();
                String recipeName = jsonObject.getString("recipeName");
                String cookTime = jsonObject.getString("cookTime");
                String prepTime = jsonObject.getString("prepTime");


                for(int i = 0; i < ingredientJSON.length(); i++){
                    recipeIngredientList.add(ingredientJSON.getString(i));
                }

                boolean cont = true;

                if (filterIngredients) {
                    int numMissingIngredients = 0;
                    for (int i = 0; i < recipeIngredientList.size(); i++) {
                        Log.d("recipeIngredientList", recipeIngredientList.get(i));
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
                    View view = layoutInflater.inflate(R.layout.recipe_list_item, null);

                    SquareImageView recipeImg = view.findViewById(R.id.recipeImgListItem);
                    AppCompatTextView recipeNameTV = view.findViewById(R.id.recipeNameListItem);
                    TextView prepTimeTV = view.findViewById(R.id.prepTimeListItem);
                    TextView cookTimeTV = view.findViewById(R.id.cookTimeListItem);

                    recipeNameTV.setText(recipeName);


                    if(recipeName != null) {
                        if (recipeName.equals("Shrimp, tomato, and spinach pasta")) {
                            recipeImg.setImageResource(R.drawable.shrimp_tomato_spinach_pasta);
                        } else if (recipeName.equals("Pork chops with zucchini, tomatoes, and mozzarella cheese")) {
                            recipeImg.setImageResource(R.drawable.pork_chop_zucchini);
                        } else if (recipeName.equals("Tomato lemon baked salmon and asparagus")) {
                            recipeImg.setImageResource(R.drawable.lemon_baked_salmon);
                        } else if (recipeName.equals("Baked chicken with peppers and mushrooms")) {
                            recipeImg.setImageResource(R.drawable.baked_chicken);
                        } else if (recipeName.equals("Skillet of ground beef, tomatoes, and parsley topped with egg and lemon juice")) {
                            recipeImg.setImageResource(R.drawable.ground_beef_skillet);
                        } else {

                        }
                    }

                    prepTimeTV.setText("Prep time: \n" + prepTime);
                    cookTimeTV.setText("Cook time: \n" + cookTime);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

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

    }


}
