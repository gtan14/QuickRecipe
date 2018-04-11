package com.example.quickrecipe;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;

/**
 * Created by Gerald on 4/10/2018.
 */

public class IngredientsFragment extends Fragment {

    private SearchView searchView;
    private GridView gridView;
    private String[] ingredientList;
    private TypedArray typedArray;
    private Context context;
    private SubCategory subCategory;
    private IngredientsAdapter ingredientsAdapter;
    private NavDrawerActivity activity;
    Bundle savedState;


    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d("out", "out");
        outState.putSerializable("saveIngredients", subCategory);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  check if data received is null or not
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey("subCategory")) {
            subCategory = (SubCategory) getArguments().getSerializable("subCategory");
            if(subCategory != null){
                ingredientList = subCategory.getIngredientList();
                typedArray = subCategory.getImgs();
            }
        }


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

        //  setup adapter
        activity.setTitle("Home");
        ingredientsAdapter = new IngredientsAdapter(this, ingredientList, typedArray);
        gridView.setAdapter(ingredientsAdapter);

        //  on click listener for grid view items
        //  displays check mark if item does not currently have check mark, and vice versa
        //  updates the cart quantity
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IngredientsAdapter.Holder holder = (IngredientsAdapter.Holder) view.getTag();

                if(!holder.ingredientChecked){

                    //  updates grid view to show check mark
                    holder.ingredientChecked = true;
                    ingredientsAdapter.notifyDataSetChanged();

                    updateCartQuantity(true);
                }

                else{
                    holder.ingredientChecked = false;
                    ingredientsAdapter.notifyDataSetChanged();

                    updateCartQuantity(false);
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

    //  method that updates cart quantity when ingredient is selected
    private void updateCartQuantity(boolean increment){
        String quantityString = activity.cartQuantity.getText().toString();
        int quantityInt = Integer.parseInt(quantityString);

        if(increment) {
            quantityInt++;
        }
        else{
            quantityInt--;
        }
        activity.cartQuantity.setText(String.format("%s", quantityInt));
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d("destry", "d");
        //savedState.putSerializable("saveIngredients", subCategory);
    }

}
