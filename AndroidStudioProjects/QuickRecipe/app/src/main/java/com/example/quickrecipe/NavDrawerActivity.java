package com.example.quickrecipe;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public TextView cartQuantity;
    public ArrayList<Cart> cartArrayList;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("recipes", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //  initialize the cart array list
        cartArrayList = new ArrayList<Cart>();

        new RecipeAsyncTask().execute();

        //  creates the home fragment
        Fragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.content_frame, homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        MenuItem item = menu.findItem(R.id.cartItem);

        item.setActionView(R.layout.cart_image_layout);

        //  default cart quantity is 0
        FrameLayout view = (FrameLayout) item.getActionView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment cartFragment = new CartFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.content_frame, cartFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        cartQuantity = (TextView) view.findViewById(R.id.cart_badge);
        cartQuantity.setText("0");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cartItem) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Fragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.content_frame, homeFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_recipes) {
            Fragment recipeListFragment = new RecipeListFragment();
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            recipeListFragment.setArguments(new Bundle());
            fragmentTransaction.replace(R.id.content_frame, recipeListFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout){

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initializeRecipeDate(){

    }

    private static class RecipeAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Void doInBackground(Void... params){
            SharedPreferences sharedPreferences = context.getSharedPreferences("recipes", MODE_PRIVATE);
            if(!sharedPreferences.contains("shrimpTomatoSpinachPasta")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();


                String[] shrimpTomatoArray = context.getResources().getStringArray(R.array.shrimp_tomato_pasta_ingredients);
                ArrayList<String> shrimpTomatoArrayList = new ArrayList<>(Arrays.asList(shrimpTomatoArray));
                String shrimpTomatoSpinachPasta = gson.toJson(new Recipe("Shrimp, tomato, and spinach pasta", "20 mins", "30 mins", context.getString(R.string.shrimp_tomato_pasta), shrimpTomatoArrayList));

                String[] porkChopArray = context.getResources().getStringArray(R.array.pork_chop_zucchini_ingredients);
                ArrayList<String> porkChopArrayList = new ArrayList<>(Arrays.asList(porkChopArray));
                String porkChopZucchini = gson.toJson(new Recipe("Pork chops with zucchini, tomatoes, and mozzarella cheese", "15 mins", "40 mins", context.getString(R.string.pork_chop_zucchini), porkChopArrayList));

                String[] bakedSalmonArray = context.getResources().getStringArray(R.array.lemon_baked_salmon_ingredients);
                ArrayList<String> bakedSalmonArrayList = new ArrayList<>(Arrays.asList(bakedSalmonArray));
                String lemonBakedSalmon = gson.toJson(new Recipe("Tomato lemon baked salmon and asparagus", "5 mins", "20 mins", context.getString(R.string.lemon_baked_salmon), bakedSalmonArrayList));

                String[] bakedChickenArray = context.getResources().getStringArray(R.array.baked_chicken);
                ArrayList<String> bakedChickenArrayList = new ArrayList<>(Arrays.asList(bakedChickenArray));
                String bakedChicken = gson.toJson(new Recipe("Baked chicken with peppers and mushrooms", "10 mins", "30 mins", context.getString(R.string.baked_chicken), bakedChickenArrayList));

                String[] beefSkilletArray = context.getResources().getStringArray(R.array.ground_beef_skillet);
                ArrayList<String> beefSkilletArrayList = new ArrayList<>(Arrays.asList(beefSkilletArray));
                String groundBeefSkillet = gson.toJson(new Recipe("Skillet of ground beef, tomatoes, and parsley topped with egg and lemon juice", "5 mins", "20 mins", context.getString(R.string.ground_beef_skillet), beefSkilletArrayList));

                editor.putString("shrimp_tomato_and_spinach_pasta", shrimpTomatoSpinachPasta);
                editor.putString("pork_chops_with_zucchini_tomatoes_and_mozzarella_cheese", porkChopZucchini);
                editor.putString("tomato_lemon_baked_salmon_and_asparagus", lemonBakedSalmon);
                editor.putString("baked_chicken_with_peppers_and_mushrooms", bakedChicken);
                editor.putString("skillet_of_ground_beef_tomatoes_and_parsley_topped_with_egg_and_lemon_juice", groundBeefSkillet);

                editor.apply();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
        }
    }
}
