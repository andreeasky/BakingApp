package com.example.user.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WidgetConfigurationActivity extends AppCompatActivity implements RecipeAdapter.OnItemClicked {

    private Recipe recipe;
    ArrayList<Recipe> recipes = new ArrayList<>();
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    private int appWidgetId;
    private RecipeAdapter recipeAdapter;
    private RecyclerView recipesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        recipesRecyclerView = (RecyclerView) findViewById( R.id.recycler_view );

        recipeAdapter = new RecipeAdapter( recipes, this, this );

        recipesRecyclerView.setAdapter( recipeAdapter );

        // set a GridLayoutManager with default vertical orientation and 1 column in Portrait mode and 2 columns in Landscape mode
        GridLayoutManager gridLayoutManager = new GridLayoutManager( this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 1 : 2 );
        recipesRecyclerView.setLayoutManager( gridLayoutManager ); // set LayoutManager to RecyclerView
        recipeAdapter.setOnClick( this ); // Bind the listener

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID );
        }

        new RecipesAsyncTask().execute();

    }

    @Override
    public void onItemClick(int position) {

        Recipe recipe = recipes.get( position );
        ingredients = recipe.getRecipeIngredients();

        // Convert the ingredients to a json String
        Gson gson = new Gson();
        String jsonIngredients = gson.toJson( ingredients );

        // Save the ingredients to preferences
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences( this.getApplicationContext() );
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.putString( "Ingredients", jsonIngredients );
        preferencesEditor.commit();

        // Update the widget
        Intent intent = new Intent( this, WidgetService.class );

        // Add the app widget ID to the intent extras.
        intent.putExtra( AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId );
        intent.setData( Uri.parse( intent.toUri( Intent.URI_INTENT_SCHEME ) ) );

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( this );
        RemoteViews views = new RemoteViews( getPackageName(),
                R.layout.widget_ingredients_list );

        views.setRemoteAdapter( R.id.widget_list, intent );
        appWidgetManager.updateAppWidget( appWidgetId, views );

        // Finish Activity
        Intent resultValue = new Intent();
        resultValue.putExtra( AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId );
        setResult( RESULT_OK, resultValue );
        finish();

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
                WidgetConfigurationActivity.this.recipes = recipes;
                recipeAdapter.addAll( recipes );

            }
        }

        public void setOnClick(RecipeAdapter.OnItemClicked onClick) {
        }

        private RecipeAdapter.OnItemClicked onClick;

        public void setOnClick() {
        }
    }
}
