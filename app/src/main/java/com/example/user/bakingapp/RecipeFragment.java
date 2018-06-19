package com.example.user.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import junit.framework.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment implements StepAdapter.OnItemClicked {

    public static final String INGREDIENT = "ingredient";
    public static final String RECIPE_STEPS = "recipe_steps";
    private RecyclerView ingredientRecyclerView;
    private RecyclerView stepsRecyclerView;
    ArrayList<Ingredient> ingredient = new ArrayList<>();
    ArrayList<Step> steps = new ArrayList<>();
    ArrayList<Recipe> recipes = new ArrayList<>();
    private Recipe recipe;
    private Step step;
    private RecipeAdapter recipeAdapter;
    private IngredientAdapter ingredientAdapter;
    private StepAdapter stepAdapter;
    Context context;
    public NestedScrollView nestedScrollView;

    // Tag for logging
    private static final String TAG = "RecipeFragment";

    private boolean isTablet;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public RecipeFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the text to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the Recipe fragment layout
        View rootView = inflater.inflate( R.layout.fragment_recipe, container, false );

        nestedScrollView = rootView.findViewById( R.id.scroll_view );

        ingredientRecyclerView = (RecyclerView) rootView.findViewById( R.id.recycler_view_ingredient );

        stepsRecyclerView = (RecyclerView) rootView.findViewById( R.id.recycler_view_steps );

        if (savedInstanceState != null) {

            final int[] position = savedInstanceState.getIntArray("SCROLL_POSITION");
            if(position != null)
                nestedScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nestedScrollView.scrollTo(position[0], position[1]);
                    }
                }, 300);
        }
        else{
            Log.v(TAG, "no data" );
        }

            if (getArguments() != null) {

                Recipe recipe = getArguments().getParcelable( "recipe" );

                ingredient = recipe.getRecipeIngredients();
                ingredientAdapter = new IngredientAdapter( ingredient, context );

                steps = recipe.getRecipeSteps();
                stepAdapter = new StepAdapter( steps, context, this );

                ingredientRecyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );// set LayoutManager to RecyclerView
                ingredientRecyclerView.setAdapter( ingredientAdapter );
                stepsRecyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
                stepsRecyclerView.setAdapter( stepAdapter );
                stepAdapter.setOnClick( this ); // Bind the listener

            } else {
                //Do nothing
            }

        // Return the rootView
        return rootView;
    }

    // This is where you have to start the next Activity in phones
    // Or load the selected step in a Fragment in tablets, in the right side
    // When the user selects a step, this method will be called
    @Override
    public void onItemClick(int position) {

        boolean tabletSize = getResources().getBoolean( R.bool.isTablet );
        if (tabletSize) {

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList( "step",  steps );
            bundle.putInt("position", position);

            // Create a new RecipeFragment
            StepDetailFragment stepDetailFragment = new StepDetailFragment();

            // set Fragment class Arguments
            stepDetailFragment.setArguments( bundle );

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction()
                    .add( R.id.step_detail_container,stepDetailFragment )
                    .commit();

        } else {

            // The onClick implementation of the RecyclerView item click
            step = steps.get( position );
            Intent intent = new Intent( getActivity(), StepDetailActivity.class );
            intent.putParcelableArrayListExtra( "step", steps );
            intent.putExtra( "position", position );
            startActivity( intent );

        }

    }

    // Fires when a configuration change occurs and fragment needs to save state
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("SCROLL_POSITION",
                new int[]{  nestedScrollView.getScrollX(),  nestedScrollView.getScrollY()});

    }

}





