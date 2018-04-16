package com.example.quickrecipe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;


public class HomeFragment extends Fragment {

    private SearchView searchView;
    private GridView gridView;
    private Context context;
    private Category category;
    private NavDrawerActivity activity;

    public static String[] categoryList = {
            "Added sweeteners",
            "Alcohol",
            "Beverages",
            "Condiments",
            "Dairy",
            "Desserts and snacks",
            "Fruits",
            "Grains",
            "Legumes",
            "Meats",
            "Nuts",
            "Oils",
            "Sauces",
            "Seafood",
            "Soup",
            "Spices",
            "Vegetables"
    };

    public static int[] imageList = {
            R.drawable.sweetener,
            R.drawable.alcohol,
            R.drawable.beverages,
            R.drawable.condiments,
            R.drawable.dairy,
            R.drawable.desserts,
            R.drawable.fruits,
            R.drawable.grains,
            R.drawable.legumes,
            R.drawable.meat,
            R.drawable.nuts,
            R.drawable.oil,
            R.drawable.sauces,
            R.drawable.seafood,
            R.drawable.soup,
            R.drawable.spices,
            R.drawable.vegetable
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState){
        searchView = (SearchView) v.findViewById(R.id.ingredientSearchView);
        gridView = (GridView) v.findViewById(R.id.mainCategoryGridView);
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);

        activity = (NavDrawerActivity) getActivity();
        getActivity().setTitle("Home");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        //  initialize categories
        category = new Category();
        category.setCategories(categoryList);
        category.setImgList(imageList);
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i < categoryList.length; i++){
            arrayList.add(i, "0");
        }
        category.setCheckedList(arrayList);

        //  checks the cart to see if any ingredients are added
        //  if there are, get the category position and set checked list at category position to 1
        //  this makes a checkmark appear for the category of the ingredient in the cart
        for(int i = 0; i < activity.cartArrayList.size(); i++){
            Cart cart = activity.cartArrayList.get(i);
            category.getCheckedList().set(cart.getCategory(), "1");
        }

        gridView.setAdapter(new MainCategoryAdapter(this, category));

        //  gets the ingredients and pictures that correspond to the category clicked and creates a subcategory class with those as values
        //  an arraylist the size of the ingredients list is created which is responsible for defaulting the check marks to invisible
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainCategoryAdapter.Holder holder = (MainCategoryAdapter.Holder) view.getTag();
                String catName = holder.categoryName.getText().toString();
                SubCategory subCategory = new SubCategory();


                if(catName.equals("Added sweeteners")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.sweeteners);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.sweeteners_imgs));
                }

                else if(catName.equals("Alcohol")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.alcohol);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.alcohol_img));
                }

                else if(catName.equals("Beverages")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.beverages);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.beverages_imgs));
                }

                else if(catName.equals("Condiments")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.condiments);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.condiments_imgs));
                }

                else if(catName.equals("Dairy")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.dairy);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.dairy_imgs));
                }

                else if(catName.equals("Desserts and snacks")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.deserts_snacks);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.deserts_snacks_img));
                }

                else if(catName.equals("Fruits")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.fruits);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.fruit_imgs));
                }

                else if(catName.equals("Grains")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.baking_grains);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.baking_grains_imgs));
                }

                else if(catName.equals("Legumes")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.legumes);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.legumes_img));
                }

                else if(catName.equals("Meats")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.meats);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.meats_imgs));
                }

                else if(catName.equals("Nuts")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.nuts);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.nuts_img));
                }

                else if(catName.equals("Oils")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.oils);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.oils_img));
                }

                else if(catName.equals("Sauces")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.sauces);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.sauces_imgs));
                }

                else if(catName.equals("Seafood")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.seafood);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.seafood_imgs));
                }

                else if(catName.equals("Soup")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.soup);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.soup_img));
                }

                else if(catName.equals("Spices")){
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.spices);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.spices_imgs));
                }

                else{
                    String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.vegetables);
                    Arrays.sort(sortedIngredients);
                    subCategory.setIngredientList(sortedIngredients);
                    subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.vegetable_imgs));
                }

                //  all the ingredients are defaulted as unchecked when going into ingredient fragment
                //  string "0" represents unchecked
                ArrayList<String> checked = new ArrayList<>();
                for(int i = 0; i < subCategory.getIngredientList().length; i++){
                    checked.add(i, "0");
                }

                subCategory.setIngredientChecked(checked);
                subCategory.setCategory(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("subCategory", subCategory);

                Fragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.content_frame, ingredientsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //  sets up the recipe data
    private void filter(){
        
    }


}
