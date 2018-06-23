package com.example.user.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity  extends AppCompatActivity {

    private Recipe recipe;
    int position;

    private boolean tabletSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_details );

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra( "recipe" );

        boolean tabletSize = getResources().getBoolean( R.bool.isTablet );

            if (tabletSize) {

                Bundle bundle = new Bundle();
                bundle.putParcelable( "recipe", recipe );
                bundle.putInt( "position", position );

                if (savedInstanceState == null) {

                    RecipeFragment recipeFragment = new RecipeFragment();
                    // Create a new RecipeFragment
                    StepDetailFragment stepDetailFragment = new StepDetailFragment();

                    // set Fragment class Arguments
                    recipeFragment.setArguments( bundle );

                    // Add the fragment to its container using a FragmentManager and a Transaction
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction()
                            .add( R.id.steps_ingredients_container, recipeFragment )
                            .add( R.id.step_detail_container, stepDetailFragment )
                            .commit();}

                } else {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable( "recipe", recipe );

                    if (savedInstanceState == null) {
                        // Create a new RecipeFragment
                        RecipeFragment recipeFragment = new RecipeFragment();

                        // set Fragment class Arguments
                        recipeFragment.setArguments( bundle );

                        // Add the fragment to its container using a FragmentManager and a Transaction
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .add( R.id.steps_ingredients_container, recipeFragment )
                                .commit();

                    }
        }
    }
}

