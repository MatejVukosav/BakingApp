package com.vuki.bakingapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.vuki.bakingapp.R;
import com.vuki.bakingapp.helpers.DataHelper;
import com.vuki.bakingapp.models.ApiReceipt;
import com.vuki.bakingapp.network.ApiManager;
import com.vuki.bakingapp.ui.details.RecipeDetailsActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.OkHttpClient;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

//    @Rule
//    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>( HomeActivity.class );

    @Rule
    public IntentsTestRule<HomeActivity> intentsTestRule
            = new IntentsTestRule<>( HomeActivity.class );

    private static int EXTRA_POSITION_0 = 1;

    @Before
    public void setUp() throws Exception {
        OkHttpClient client = ApiManager.getClient();
        IdlingResource resource = OkHttp3IdlingResource.create( "OkHttp", client );
        IdlingRegistry.getInstance().register( resource );

    }

    @Test
    public void recipeClickOpensDetailsActivity() {

//        onData( Matchers.anything() )
//                .inAdapterView( withId( R.id.recycler_view ) )
//                .atPosition( 0 )
//                .perform( ViewActions.click() );
//
//        onView( withId( R.id.toolbar ) )
//                .check( ViewAssertions.matches( withText( "Nutella Pie" ) ) );

        onView( withId( R.id.recycler_view ) )
                .perform( RecyclerViewActions.scrollToPosition( 0 ) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0, ViewActions.click() ) );


        Intents.intended(
                IntentMatchers.hasExtra( RecipeDetailsActivity.EXTRA_RECEIPT, Matchers.any( ApiReceipt.class ) )
        );
    }

}
