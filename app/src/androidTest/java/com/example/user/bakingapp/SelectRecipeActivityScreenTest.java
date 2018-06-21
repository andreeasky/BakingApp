package com.example.user.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static com.example.user.bakingapp.TestUtils.atPosition;

@RunWith(AndroidJUnit4.class)

public class SelectRecipeActivityScreenTest {

    // Add the rule that provides functional testing of a single activity
    public static final String RECIPE_NAME = "Nutella Pie";

    // Finish writing this test which will click on a cardView recipe item and verify that
    // the DetailsActivity opens up.
    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly* access the activity during the test.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>( MainActivity.class );

    /**
     * Clicks on a CardView item and checks if it opens up the DetailsActivity with the correct details.
     */
    @Test
    public void clickRecyclerViewItem_OpensDetailsActivity() {

        onView( withId( R.id.recycler_view ) )
                .check( matches( atPosition( 0, hasDescendant( withText( RECIPE_NAME ) ) )) );

        // Checks that the DetailsActivity opens
        onView( withId( R.id.recycler_view ) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0, click() ) );
    }

}
