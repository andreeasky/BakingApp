package com.example.user.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {

    TextView recipeStep;
    private Step step;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail_step );

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        Intent intent = getIntent();
        ArrayList<Step> steps = intent.getParcelableArrayListExtra("step");
        position = intent.getIntExtra("position", position);
        step = steps.get( position );

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList( "step",  steps );
        bundle.putInt("position", position);

        if (savedInstanceState == null) {
            // Create a new RecipeFragment
            StepDetailFragment stepFragment = new StepDetailFragment();
            // set Fragment class Arguments
            stepFragment.setArguments( bundle );

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add( R.id.step_container, stepFragment )
                    .commit();

        }
    }
}
