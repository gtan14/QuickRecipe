package com.example.quickrecipe;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Gerald on 4/14/2018.
 */

public class RecipeViewFragment extends Fragment {

    private SquareImageView recipeImage;
    private TextView recipeName;
    private TextView cookTime;
    private TextView prepTime;
    private TextView ingredients;
    private TextView instructions;
    private String sentRecipeName;
    private NavDrawerActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.recipe_view_layout, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState){
        recipeImage = (SquareImageView) v.findViewById(R.id.recipeImgRecipeView);
        recipeName = (TextView) v.findViewById(R.id.recipeNameRecipeView);
        cookTime = (TextView) v.findViewById(R.id.cookTimeRecipeView);
        prepTime = (TextView) v.findViewById(R.id.prepTimeRecipeView);
        ingredients = (TextView) v.findViewById(R.id.ingredientListRecipeView);
        instructions = (TextView) v.findViewById(R.id.instructionsRecipeView);

        Bundle bundle = getArguments();
        if(bundle != null){
            sentRecipeName = bundle.getString("recipeName");
        }
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

        activity = (NavDrawerActivity) getActivity();
        setData();
    }

    private void setData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("recipes", MODE_PRIVATE);

        Map<String, ?> keys = sharedPreferences.getAll();
        ArrayList<String> recipeIngredientList;
        for (Map.Entry<String, ?> entry : keys.entrySet()) {

            try {
                JSONObject jsonObject = new JSONObject(entry.getValue().toString());
                String jsonRecipeName = jsonObject.getString("recipeName");
                if(jsonRecipeName.equals(sentRecipeName)){
                    JSONArray ingredientJSON = jsonObject.getJSONArray("ingredientList");
                    recipeIngredientList = new ArrayList<>();
                    String jsonCookTime = jsonObject.getString("cookTime");
                    String jsonPrepTime = jsonObject.getString("prepTime");
                    String jsonInstructions = jsonObject.getString("instructions");
                    StringBuilder stringBuilder = new StringBuilder();

                    recipeName.setText(jsonRecipeName);
                    cookTime.setText(jsonCookTime);
                    prepTime.setText(jsonPrepTime);
                    instructions.setText(jsonInstructions);

                    for(int i = 0; i < ingredientJSON.length(); i++){
                        recipeIngredientList.add(ingredientJSON.getString(i));
                    }


                    ingredients.setText("");
                    for(int i = 0; i < recipeIngredientList.size(); i++){
                        boolean changeColor = true;
                        for(int j = 0 ; j < activity.cartArrayList.size(); j++){
                            if(recipeIngredientList.get(i).toLowerCase().contains(activity.cartArrayList.get(j).getIngredient().toLowerCase())){
                                changeColor = false;
                            }
                        }
                        if(changeColor){
                            //SpannableStringBuilder builder = new SpannableStringBuilder();

                            SpannableString redSpannable= new SpannableString(recipeIngredientList.get(i));
                            Log.d("length", String.format("%s", recipeIngredientList.get(i).length()));
                            redSpannable.setSpan(new ForegroundColorSpan(Color.argb(255, 255, 0, 0)), 0, recipeIngredientList.get(i).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            //builder.append(redSpannable + "\n");
                            ingredients.append(redSpannable);
                            ingredients.append("\n");
                            Log.d("red", redSpannable.toString());
                        }

                        else{
                            Log.d("list", recipeIngredientList.get(i));
                            ingredients.append(recipeIngredientList.get(i) + "\n");
                        }
                    }


                    String drawableName = jsonRecipeName.replaceAll(",", "").replaceAll(" ", "_").replaceAll("'", "").toLowerCase();
                    //Resources resources = getActivity().getResources();
                    String file = "/data/user/0/com.example.quickrecipe/app_files/"  + drawableName + ".png";
                    Bitmap bitmap = BitmapFactory.decodeFile(file);
                    //int resourceId = resources.getIdentifier(drawableName, "drawable", getActivity().getPackageName());

                    recipeImage.setImageBitmap(bitmap);
                    getActivity().setTitle(recipeName.getText().toString());

                    return;
                }

            }

            catch (JSONException e){

            }
        }
    }
}
