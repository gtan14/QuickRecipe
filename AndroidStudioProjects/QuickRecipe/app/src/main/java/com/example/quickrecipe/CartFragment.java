package com.example.quickrecipe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Gerald on 4/12/2018.
 */

public class CartFragment extends Fragment {

    private NavDrawerActivity navDrawerActivity;
    private LinearLayout linearLayout;
    private Button getRecipeBtn;
    private ArrayList<String> ingredientList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.cart_layout, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState){
        linearLayout = v.findViewById(R.id.cart_parent_container);
        getRecipeBtn = v.findViewById(R.id.getRecipeBtn);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        LayoutInflater layoutInflater = getLayoutInflater();
        ingredientList = new ArrayList<>();

        for(int i = 0; i < navDrawerActivity.cartArrayList.size(); i++) {

            View cartLayout = layoutInflater.inflate(R.layout.ingredient_cart_item, null);
            final TextView ingredient = cartLayout.findViewById(R.id.ingredient_name_cart_text_view);
            ImageView ingredientImg = cartLayout.findViewById(R.id.ingredient_image_cart_item);
            ImageView deleteIngredient = cartLayout.findViewById(R.id.delete_cart_item);

            ingredient.setText(navDrawerActivity.cartArrayList.get(i).getIngredient());
            ingredientList.add(ingredient.getText().toString());
            ingredientImg.setImageResource(navDrawerActivity.cartArrayList.get(i).getIngredientImg());

            deleteIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(navDrawerActivity);
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            linearLayout.removeView((View) v.getParent());
                            String quantity = navDrawerActivity.cartQuantity.getText().toString();
                            int quantityInt = Integer.parseInt(quantity);
                            quantityInt--;
                            navDrawerActivity.cartQuantity.setText(String.format("%s", quantityInt));

                            for(int i = 0; i < navDrawerActivity.cartArrayList.size(); i++){
                                Cart cart = navDrawerActivity.cartArrayList.get(i);
                                String ingredientCart = cart.getIngredient();
                                if(ingredient.getText().toString().equals(ingredientCart)){
                                    navDrawerActivity.cartArrayList.remove(i);
                                    ingredientList.remove(ingredient.getText().toString());
                                }
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setMessage("Are you sure you want to delete this ingredient?");
                    builder.show();
                }
            });

            linearLayout.addView(cartLayout, 0);
        }

        getRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayout.getChildCount() > 0) {
                    Fragment recipeListFragment = new RecipeListFragment();
                    FragmentTransaction fragmentTransaction = navDrawerActivity.getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("ingredients", ingredientList);
                    recipeListFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content_frame, recipeListFragment);
                    fragmentTransaction.commit();
                }

                else{
                    Toast.makeText(getActivity(), "Add ingredients to cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navDrawerActivity = (NavDrawerActivity) getActivity();
    }
}
