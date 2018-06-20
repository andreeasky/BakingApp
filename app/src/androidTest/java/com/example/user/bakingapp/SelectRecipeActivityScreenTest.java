package com.example.user.bakingapp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)

public class SelectRecipeActivityScreenTest {

    // Add the rule that provides functional testing of a single activity
    public static final String RECIPE_NAME = "Nutella Pie";

// Finish writing this test which will click on a gridView Tea item and verify that
    // the OrderActivity opens up with the correct name displayed.
    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly* access the activity during the test.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>( MainActivity.class );

    /**
     * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
     *
     */
    @Test
    public void clickGridViewItem_OpensDetailsActivity() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // gridview item and clicks it.
        onData( anything() ).inAdapterView( withId( R.id.card_view) ).atPosition( 1 ).perform( click() );

        // Checks that the OrderActivity opens with the correct tea name displayed
        onView( withId( R.id. recipe_name ) ).check( matches( withText( RECIPE_NAME ) ) );

    }

    private class ActivityTestRule<T> {
        public ActivityTestRule(Class<T> mainActivityClass) {
        }
    }
}
