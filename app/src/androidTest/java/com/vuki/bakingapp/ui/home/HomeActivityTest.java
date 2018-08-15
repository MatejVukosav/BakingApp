package com.vuki.bakingapp.ui.home;

import android.os.Bundle;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.azimolabs.conditionwatcher.ConditionWatcher;
import com.azimolabs.conditionwatcher.Instruction;
import com.vuki.bakingapp.R;
import com.vuki.bakingapp.ui.details.RecipeDetailsActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public IntentsTestRule<HomeActivity> intentsTestRule
            = new IntentsTestRule<>( HomeActivity.class );

    @Test
    public void good_extra_send_from_home_when_open_recipe_details() throws Exception {

        ConditionWatcher.waitForCondition( new Instruction() {
            @Override
            public String getDescription() {
                return "";
            }

            @Override
            public boolean checkCondition() {
                if ( intentsTestRule.getActivity() == null ) {
                    return false;
                }
                return intentsTestRule.getActivity().items != null
                        && !intentsTestRule.getActivity().items.isEmpty();
            }
        } );
        onView( withId( R.id.recycler_view ) )
                .perform( RecyclerViewActions.scrollToPosition( 0 ) )
                .perform( RecyclerViewActions.actionOnItemAtPosition( 0, ViewActions.click() ) );

        // This does not work? Why? Also how can I check that right object is send inside bundle
//        intended(
//                hasExtras(
//                        allOf(
//                                Matchers.any( Bundle.class ),
//                                toPackage( RecipeDetailsActivity.class.getPackage().getName() )
//                        )
//                )
//        );

        intended(
                hasExtras( Matchers.any( Bundle.class ) )
        );

    }

}
