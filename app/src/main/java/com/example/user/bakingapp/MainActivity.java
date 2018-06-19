package com.example.user.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnItemClicked {

    private RecipeAdapter recipeAdapter;
    ArrayList<Recipe> recipes = new ArrayList<>();
    private RecyclerView recipesRecyclerView;
    private String recipeId = "id";
    private Recipe recipe;
    private String recipeName = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        recipesRecyclerView = (RecyclerView) findViewById( R.id.recycler_view);

        recipeAdapter = new RecipeAdapter( recipes, this, this );

        recipesRecyclerView.setAdapter( recipeAdapter );

        // set a GridLayoutManager with default vertical orientation and 1 column in Portrait mode and 2 columns in Landscape mode
        GridLayoutManager gridLayoutManager = new GridLayoutManager( this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 1 : 2 );
        recipesRecyclerView.setLayoutManager( gridLayoutManager ); // set LayoutManager to RecyclerView
        recipeAdapter.setOnClick( this ); // Bind the listener

        new RecipesAsyncTask().execute();

        Utils.buildURL();

    }

    @Override
    public void onItemClick(int position) {

        Recipe recipe = recipes.get( position );
        Intent intent = new Intent( this, DetailsActivity.class );
        intent.putExtra( "recipe", recipe );
        startActivity( intent );
    }

    private class RecipesAsyncTask extends AsyncTask<String, Void, ArrayList<Recipe>> {

        @Override
        protected ArrayList<Recipe> doInBackground(String... urls) {
            ArrayList<Recipe> recipeResult = Utils.fetchRecipeData();


            return recipeResult;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {

            if (recipes != null && !recipes.isEmpty()) {
                MainActivity.this.recipes = recipes;
                recipeAdapter.addAll( recipes );

            }
        }
    }
}
