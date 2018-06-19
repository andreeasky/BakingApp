package com.example.user.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class StepDetailFragment extends Fragment {

    private Step step;
    private RecyclerView stepsRecyclerView;
    ArrayList<Recipe> recipes = new ArrayList<>();
    ArrayList<Step> steps = new ArrayList<>();
    private StepAdapter stepAdapter;
    private TextView stepDescription;
    private String stepVideo;
    private String recipeStepDescription;
    Context context;
    private int stepIndex;
    Button previousStep;
    Button nextStep;
    int value;
    int position;
    private View rootView;
    private PlayerView stepExoPlayer;
    private SimpleExoPlayer player;
    ImageView errorImageStep;
    private String stepVideoUrl;
    long playerPosition;
    long playbackState;
    private int currentWindow;
    private boolean playWhenReady = true;

    // Tag for logging
    private static final String TAG = "StepDetailFragment";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public StepDetailFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the text to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the Step fragment layout
        rootView = inflater.inflate( R.layout.fragment_step_detail, container, false );

        stepExoPlayer = (PlayerView) rootView.findViewById( R.id.video );
        stepDescription = (TextView) rootView.findViewById( R.id.detail_step );
        previousStep = (Button) rootView.findViewById( R.id.previous_step );
        nextStep = (Button) rootView.findViewById( R.id.next_step );
        errorImageStep = (ImageView) rootView.findViewById( R.id.error_image_step );

        playWhenReady = true;
        if (savedInstanceState != null) {

            playerPosition = savedInstanceState.getLong("VIDEO");
            playWhenReady = savedInstanceState.getBoolean("PLAYBACK_STATE");

        }
        else{
            Log.v(TAG, "no data" );
        }

        if (getArguments() != null) {
            steps = getArguments().getParcelableArrayList( "step" ); // That gets the Bundle
            position = getArguments().getInt( "position" );
            step = steps.get( position );
            stepIndex = steps.indexOf( step );
            stepVideoUrl = step.getStepsVideoURL();

            if (step != null) {
                stepDescription.setText( step.getStepDescription() );

            }

            if (stepIndex <= 0) {
                previousStep.setEnabled( false );
                nextStep.setEnabled( true );
            }

            previousStep.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (stepIndex <= 0) {
                        previousStep.setEnabled( false );
                        nextStep.setEnabled( true );
                    } else {
                        stepIndex--;
                        selectStep( stepIndex );
                        previousStep.setEnabled( true );
                        nextStep.setEnabled( true );
                    }

                }
            } );

            if (stepIndex >= steps.size() - 1) {
                nextStep.setEnabled( false );
                previousStep.setEnabled( true );
            }

            nextStep.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (stepIndex >= steps.size() - 1) {
                        nextStep.setEnabled( false );
                        previousStep.setEnabled( true );
                    } else {
                        stepIndex++;
                        selectStep( stepIndex );
                        nextStep.setEnabled( true );
                        previousStep.setEnabled( true );
                    }

                }
            } );


        } else {
            //Do nothing
        }
        return rootView;
    }

    public void selectStep(int stepIndex) {

        if (stepIndex >= 0 && stepIndex < steps.size()) {
            step = steps.get( stepIndex );
            stepDescription.setText( step.getStepDescription() );
            releaseExoPlayer();
            if (!step.getStepsVideoURL().isEmpty()) {
                errorImageStep.setVisibility( View.GONE );
                stepVideoUrl = step.getStepsVideoURL();
                stepExoPlayer.setVisibility( View.VISIBLE );
                initializeExoPlayer();

            } else {
                stepExoPlayer.setVisibility( View.GONE );
                errorImageStep.setVisibility( View.VISIBLE );
                Picasso.with( getContext() ).load( R.drawable.error_image ).into( errorImageStep );
            }
        }

    }

    private void initializeExoPlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance( new DefaultRenderersFactory( getContext() ),
                    new DefaultTrackSelector(), new DefaultLoadControl() );
            stepExoPlayer.setPlayer( player );
        }
        MediaSource mediaSource = buildMediaSource( Uri.parse(  stepVideoUrl ) );
        player.prepare( mediaSource, true, false );
        player.seekTo(playerPosition);
        player.setPlayWhenReady( playWhenReady );

    }

    private void releaseExoPlayer() {
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory( new DefaultHttpDataSourceFactory( "exoplayer-bakingapp" ) )
                .createMediaSource( uri );
    }

    private void hideSystemUi() {
        stepExoPlayer.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );
    }

    @Override
    public void onStart() {
        super.onStart();
        if ((Util.SDK_INT > 23) && (stepVideoUrl != null)) {
            initializeExoPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null) && stepVideoUrl != null) {
            initializeExoPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releaseExoPlayer();
        }
}

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releaseExoPlayer();
        }
    }

    // Fires when a configuration change occurs and fragment needs to save state
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("VIDEO", playerPosition);
        outState.putBoolean("PLAYBACK_STATE", playWhenReady);
    }

}



