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
import java.util.List;


public class HomeFragment extends Fragment {

    private SearchView searchView;
    private GridView gridView;
    private Context context;
    private Category category;
    private NavDrawerActivity activity;
    private SharedPreferences sharedPreferences;
    private MainCategoryAdapter mainCategoryAdapter;
    private boolean searching;
    private List<Integer> imgCopy;
    private ArrayList<String> ingredientsCopy;
    private ArrayList<String> copyCheckedList;


    public static ArrayList<String> searchIngredientList = new ArrayList<String>(Arrays.asList("Agave nectar", "Alfredo sauce", "Almond", "Almond oil", "American cheese", "Apple", "Apple juice", "Apple sauce", "Artichoke", "Artificial sweetener", "Asparagus", "Avocado", "Bacon", "Balsamic vinegar", "Banana", "Barbeque sauce", "Basil", "Bay leaf", "Beef steak", "Beet", "Bell pepper", "Biscuit", "Black bean", "Blackberry", "Blueberry", "Bread", "Broccoli", "Brown Sugar", "Butter", "Buttermilk", "Cabbage", "Cake", "Candy", "Canola oil", "Cantaloupe", "Cashew", "Cauliflower", "Celery", "Cereal", "Cheddar cheese", "Cherry", "Chicken", "Chicken broth", "Chickpea", "Chips", "Chocolate", "Chocolate milk", "Chorizo", "Cilantro", "Cinnamon", "Clam", "Cocktail sauce", "Coconut", "Coconut oil", "Cod", "Coffee", "Condensed milk", "Confectioners Sugar", "Cookies", "Corn", "Corn oil", "Crab", "Cranberry", "Cucumber", "Cumin", "Date", "Eggplant", "Eggs", "Flour", "Garlic", "Garlic powder", "Ginger", "Grape", "Grapefruit", "Grapeseed oil", "Green bean", "Ground beef", "Ground turkey", "Guava", "Half and half evaporated milk", "Ham", "Hazelnut", "Honey", "Hot dogs", "Hot sauce", "Hummus", "Italian dressing", "Ketchup", "Kidney bean", "Kiwi", "Lamb", "Lard", "Lemon", "Lemonade", "Lentils", "Lobster", "Maple syrup", "Marinara sauce", "Mayonnaise", "Milk", "Mint", "Mozzarella cheese", "Mushroom", "Mushroom gravy", "Mustard", "Mustard oil", "Olive", "Olive oil", "Onion", "Onion soup", "Orange", "Orange juice", "Oregano", "Oyster", "Papaya", "Parsley", "Pasta", "Peach", "Peanut butter", "Peanut oil", "Pear", "Pecan", "Pepperoni", "Pesto", "Pineapple", "Plum", "Popcorn", "Pork chops", "Raisin", "Ranch", "Raspberry", "Red snapper", "Red wine", "Rice", "Salami", "Salmon", "Salsa", "Sausage", "Scallion", "Sesame oil", "Shortening", "Shrimp", "Sour cream", "Soy sauce", "Soya bean oil", "Spinach", "Strawberry", "Sugar", "Tahini", "Tartar sauce", "Tequila", "Teriyaki", "Thyme", "Tilapia", "Tomato", "Tomato paste", "Tomato sauce", "Tomato soup", "Tuna", "Turkey", "Turmeric", "Vegetable oil", "Vinegar", "Walnut", "Watermelon", "Whipped cream", "Whiskey", "White wine", "Yogurt", "Zucchini"));
    public static int[] searchImageInt = {R.drawable.agave_nectar, R.drawable.alfredo_sauce, R.drawable.almond, R.drawable.almond_oil, R.drawable.american_cheese, R.drawable.apple, R.drawable.apple_juice, R.drawable.apple_sauce, R.drawable.artichoke, R.drawable.artificial_sweetener, R.drawable.asparagus, R.drawable.avocado, R.drawable.bacon, R.drawable.balsamic_vinegar, R.drawable.banana, R.drawable.barbeque_sauce, R.drawable.basil, R.drawable.bay_leaf, R.drawable.beef_steak, R.drawable.beet, R.drawable.bell_pepper, R.drawable.biscuit, R.drawable.black_bean, R.drawable.blackberry, R.drawable.blueberry, R.drawable.bread, R.drawable.broccoli, R.drawable.brown_sugar, R.drawable.butter, R.drawable.buttermilk, R.drawable.cabbage, R.drawable.cake, R.drawable.candy, R.drawable.canola_oil, R.drawable.cantaloupe, R.drawable.cashew, R.drawable.cauliflower, R.drawable.celery, R.drawable.cereal, R.drawable.cheddar_cheese, R.drawable.cherry, R.drawable.chicken, R.drawable.chicken_broth, R.drawable.chickpea, R.drawable.chips, R.drawable.chocolate, R.drawable.chocolate_milk, R.drawable.chorizo, R.drawable.cilantro, R.drawable.cinnamon, R.drawable.clam, R.drawable.cocktail_sauce, R.drawable.coconut, R.drawable.coconut_oil, R.drawable.cod, R.drawable.coffee, R.drawable.condensed_milk, R.drawable.confectioners_sugar, R.drawable.cookies, R.drawable.corn, R.drawable.corn_oil, R.drawable.crab, R.drawable.cranberry, R.drawable.cucumber, R.drawable.cumin, R.drawable.date, R.drawable.eggplant, R.drawable.eggs, R.drawable.flour, R.drawable.garlic, R.drawable.garlic_powder, R.drawable.ginger, R.drawable.grape, R.drawable.grapefuit, R.drawable.grapeseed_oil, R.drawable.green_bean, R.drawable.ground_beef, R.drawable.ground_turkey, R.drawable.guava, R.drawable.half_and_half_evaporated_milk, R.drawable.ham, R.drawable.hazelnut, R.drawable.honey, R.drawable.hot_dog, R.drawable.hot_sauce, R.drawable.hummus, R.drawable.italian_dressing, R.drawable.ketchup, R.drawable.kidney_bean, R.drawable.kiwi, R.drawable.lamb, R.drawable.lard, R.drawable.lemon, R.drawable.lemonade, R.drawable.lentils, R.drawable.lobster, R.drawable.maple_syrup, R.drawable.marinara_sauce, R.drawable.mayonnaise, R.drawable.milk, R.drawable.mint, R.drawable.mozzarella_cheese, R.drawable.mushroom, R.drawable.mushroom_gravy, R.drawable.mustard, R.drawable.mustard_oil, R.drawable.olive, R.drawable.olive_oil, R.drawable.onion, R.drawable.onion_soup, R.drawable.orange, R.drawable.orange_juice, R.drawable.oregano, R.drawable.oyster, R.drawable.papaya, R.drawable.parsley, R.drawable.pasta, R.drawable.peach, R.drawable.peanut_butter, R.drawable.peanut_oil, R.drawable.pear, R.drawable.pecan, R.drawable.pepperoni, R.drawable.pesto, R.drawable.pineapple, R.drawable.plum, R.drawable.popcorn, R.drawable.pork_chop, R.drawable.raisin, R.drawable.ranch, R.drawable.raspberry, R.drawable.red_snapper, R.drawable.red_wine, R.drawable.rice, R.drawable.salami, R.drawable.salmon, R.drawable.salsa, R.drawable.sausage, R.drawable.scallion, R.drawable.sesame_oil, R.drawable.shortening, R.drawable.shrimp, R.drawable.sour_cream, R.drawable.soy_sauce, R.drawable.soya_oil, R.drawable.spinach, R.drawable.strawberry, R.drawable.sugar, R.drawable.tahini, R.drawable.tartar_sauce, R.drawable.tequila, R.drawable.teriyaki, R.drawable.thyme, R.drawable.tilapia, R.drawable.tomato, R.drawable.tomato_paste, R.drawable.tomato_sauce, R.drawable.tomato_soup, R.drawable.tuna, R.drawable.turkey, R.drawable.turmeric, R.drawable.vegetable_oil, R.drawable.vinegar, R.drawable.walnut, R.drawable.watermelon, R.drawable.whipped_cream, R.drawable.whiskey, R.drawable.white_wine, R.drawable.yogurt, R.drawable.zucchini};
    public static ArrayList<String> categoryList = new ArrayList<String>(Arrays.asList("Added sweeteners",
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
            "Vegetables"));

    public static int[] imageInt = {
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

    public static List<Integer> imageList;
    public static List<Integer> searchImageList;


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

        imageList = new ArrayList<Integer>();
        searchImageList = new ArrayList<>();
        category = new Category();

        if(imageList.isEmpty()) {
            for (int i : imageInt) {
                imageList.add(i);
            }
        }

        if(searchImageList.isEmpty()){
            for(int i : searchImageInt){
                searchImageList.add(i);
            }
        }

        activity = (NavDrawerActivity) getActivity();
        getActivity().setTitle("Home");

        sharedPreferences = getActivity().getSharedPreferences("search", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("searching", false).apply();

        //  initialize categories
        category.setCategories(categoryList);
        category.setImgList(imageList);
        ArrayList<String> arrayList = new ArrayList<>();

        for(int i = 0; i < categoryList.size(); i++){
            arrayList.add(i, "0");
        }

        category.setCheckedList(arrayList);

        //  checks the cart to see if any ingredients are added
        //  if there are, get the category position and set checked list at category position to 1
        //  this makes a checkmark appear for the category of the ingredient in the cart
        for(int i = 0; i < activity.cartArrayList.size(); i++){
            Cart cart = activity.cartArrayList.get(i);
            if(cart.getCategory() > 0) {
                category.getCheckedList().set(cart.getCategory(), "1");
            }
        }

        mainCategoryAdapter = new MainCategoryAdapter(this, category);
        gridView.setAdapter(mainCategoryAdapter);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sharedPreferences.edit().putBoolean("searching", true).apply();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sharedPreferences.edit().putBoolean("searching", true).apply();

                if(newText.length() == 0){
                    searching = false;
                    sharedPreferences.edit().putBoolean("searching", false).apply();
                    //  initialize categories
                    category.setCategories(categoryList);
                    category.setImgList(imageList);
                    ArrayList<String> arrayList = new ArrayList<>();
                    for(int i = 0; i < categoryList.size(); i++){
                        arrayList.add(i, "0");
                    }
                    category.setCheckedList(arrayList);

                    //  checks the cart to see if any ingredients are added
                    //  if there are, get the category position and set checked list at category position to 1
                    //  this makes a checkmark appear for the category of the ingredient in the cart
                    for(int i = 0; i < activity.cartArrayList.size(); i++){
                        Cart cart = activity.cartArrayList.get(i);

                        if(cart.getCategory() > 0) {
                            category.getCheckedList().set(cart.getCategory(), "1");
                        }
                    }
                }

                else {

                    //if(!searching) {
                        imgCopy = new ArrayList<>();
                        ingredientsCopy = new ArrayList<>();
                    //}

                    //category = new Category();
                    for (int i = 0; i < searchIngredientList.size(); i++) {

                        if(searchIngredientList.get(i).toLowerCase().startsWith(newText)){
                            ingredientsCopy.add(searchIngredientList.get(i));
                            imgCopy.add(searchImageList.get(i));
                        }
                    }

                    copyCheckedList = new ArrayList<>();
                    for(int i = 0; i < ingredientsCopy.size(); i++){
                        copyCheckedList.add(i, "0");
                    }


                    sharedPreferences.edit().putBoolean("searching", true).apply();
                    category.setCategories(ingredientsCopy);
                    category.setImgList(imgCopy);
                    category.setCheckedList(copyCheckedList);
                    searching = true;
                }

                mainCategoryAdapter.notifyDataSetChanged();

                return true;
            }
        });



        //  gets the ingredients and pictures that correspond to the category clicked and creates a subcategory class with those as values
        //  an arraylist the size of the ingredients list is created which is responsible for defaulting the check marks to invisible
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainCategoryAdapter.Holder holder = (MainCategoryAdapter.Holder) view.getTag();
                String catName = holder.categoryName.getText().toString();

                boolean searching = sharedPreferences.getBoolean("searching", false);

                if(searching){
                    Log.d("catNAme", catName);
                    if(holder.checkbox.isShown()){
                        category.getCheckedList().set(position, "0");
                        mainCategoryAdapter.notifyDataSetChanged();
                        updateCartQuantity(false, catName, holder.categoryImage);
                    }
                    else {
                        category.getCheckedList().set(position, "1");
                        mainCategoryAdapter.notifyDataSetChanged();
                        updateCartQuantity(true, catName, holder.categoryImage);
                    }

                }

                else {
                    SubCategory subCategory = new SubCategory();

                    if (catName.equals("Added sweeteners")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.sweeteners);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.sweeteners_imgs));
                    } else if (catName.equals("Alcohol")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.alcohol);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.alcohol_img));
                    } else if (catName.equals("Beverages")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.beverages);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.beverages_imgs));
                    } else if (catName.equals("Condiments")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.condiments);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.condiments_imgs));
                    } else if (catName.equals("Dairy")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.dairy);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.dairy_imgs));
                    } else if (catName.equals("Desserts and snacks")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.deserts_snacks);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.deserts_snacks_img));
                    } else if (catName.equals("Fruits")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.fruits);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.fruit_imgs));
                    } else if (catName.equals("Grains")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.baking_grains);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.baking_grains_imgs));
                    } else if (catName.equals("Legumes")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.legumes);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.legumes_img));
                    } else if (catName.equals("Meats")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.meats);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.meats_imgs));
                    } else if (catName.equals("Nuts")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.nuts);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.nuts_img));
                    } else if (catName.equals("Oils")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.oils);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.oils_img));
                    } else if (catName.equals("Sauces")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.sauces);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.sauces_imgs));
                    } else if (catName.equals("Seafood")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.seafood);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.seafood_imgs));
                    } else if (catName.equals("Soup")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.soup);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.soup_img));
                    } else if (catName.equals("Spices")) {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.spices);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.spices_imgs));
                    } else {
                        String[] sortedIngredients = getActivity().getResources().getStringArray(R.array.vegetables);
                        Arrays.sort(sortedIngredients);
                        subCategory.setIngredientList(sortedIngredients);
                        subCategory.setImgs(getActivity().getResources().obtainTypedArray(R.array.vegetable_imgs));
                    }

                    //  all the ingredients are defaulted as unchecked when going into ingredient fragment
                    //  string "0" represents unchecked
                    ArrayList<String> checked = new ArrayList<>();
                    for (int i = 0; i < subCategory.getIngredientList().length; i++) {
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

    //  method that updates cart quantity when ingredient is selected and adds ingredient and image to cart
    private void updateCartQuantity(boolean increment, String ingredient, ImageView ingredientImg){
        String quantityString = activity.cartQuantity.getText().toString();
        int quantityInt = Integer.parseInt(quantityString);

        if(increment) {
            quantityInt++;

            Log.d("qwieno", "Qweio");
            //  add new cart item
            Cart cart = new Cart(-1, ingredient, (Integer) ingredientImg.getTag());
            activity.cartArrayList.add(cart);
            Log.d("s", String.format("%s", activity.cartArrayList.size()));
        }
        else{
            quantityInt--;

            //  checks the cart array list to see if the ingredient that is unchecked is contained in the array list
            //  if it is, it is removed from the array list
            for(int i = 0; i < activity.cartArrayList.size(); i++){
                if(activity.cartArrayList.get(i).getIngredient().equals(ingredient)){
                    activity.cartArrayList.remove(i);
                }
            }
        }

        activity.cartQuantity.setText(String.format("%s", quantityInt));
    }




}
