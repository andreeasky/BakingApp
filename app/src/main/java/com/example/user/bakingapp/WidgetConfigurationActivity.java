package com.example.user.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

public class WidgetConfigurationActivity extends AppCompatActivity {

    private Context context;
    private Recipe recipe;
    ArrayList<Recipe> recipes = new ArrayList<>();
    ArrayList<Ingredient> ingredient = new ArrayList<>();
    private int appWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.widget_ingredients_list );

        final Button widgetButtonNutellaPie = (Button) findViewById( R.id.button);
        final Button widgetButtonBrownies = (Button) findViewById( R.id.button2);
        final Button widgetButtonYellowCake = (Button) findViewById( R.id.button3);
        final Button widgetButtonCheesecake = (Button) findViewById( R.id.button4);

        Bundle bundle = new Bundle();
        bundle.putParcelable( "recipe", recipe );

        if (bundle != null) {
            appWidgetId = bundle.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID );
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( context );


        RemoteViews views = new RemoteViews( context.getPackageName(),
                R.layout.widget_ingredients_list );
        appWidgetManager.updateAppWidget( appWidgetId, views );

        Intent resultValue = new Intent();
        resultValue.putExtra( AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId );
        setResult( RESULT_OK, resultValue );
        finish();

        widgetButtonNutellaPie.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                showWidgetIngredient();

            }
        } );

        widgetButtonBrownies.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                showWidgetIngredient();

            }
        } );

        widgetButtonYellowCake.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                showWidgetIngredient();
            }
        } );

        widgetButtonCheesecake.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                showWidgetIngredient();

            }
        } );

        Ingredient widgetIngredient = new Ingredient();
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        String widgetRecipeIngredient = recipe.to(widgetIngredient);
        prefsEditor.putString("Ingredient", );
        prefsEditor.commit();


    }

    public void showWidgetIngredient()
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable( "recipe", recipe );
        ingredient = recipe.getRecipeIngredients();
        Intent intent = new Intent(WidgetConfigurationActivity.this, WidgetRemoteViews.class);
        startActivity(intent);
    }

    public void setOnClick(RecipeAdapter.OnItemClicked onClick)
    {
    }

    private OnItemClicked onClick;

    public void setOnClick() {
    }

    public interface OnItemClicked {

        void onItemClick(int position);
    }

}
