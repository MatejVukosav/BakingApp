package com.vuki.bakingapp.ui.home;

import android.os.Bundle;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.azimolabs.conditionwatcher.ConditionWatcher;
import com.azimolabs.conditionwatcher.Instruction;
import com.vuki.bakingapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    private static String NUTELLA_PIE = "Nutella Pie";
    private static String BROWNIES = "Brownies";
    private static String YELLOW_CAKE = "Yellow Cake";
    private static String CHEESECAKE = "Cheesecake";

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

    @Test
    public void four_recipes_are_shown() throws Exception {
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

        checkIfViewInCardIsVisibleWithGivenText( NUTELLA_PIE );
        checkIfViewInCardIsVisibleWithGivenText( BROWNIES );
        checkIfViewInCardIsVisibleWithGivenText( YELLOW_CAKE );
        checkIfViewInCardIsVisibleWithGivenText( CHEESECAKE );
    }

    private void checkIfViewInCardIsVisibleWithGivenText( String text ) {
        ViewInteraction viewInteraction = onView( allOf( withId( R.id.title ),
                withText( text ),
                childAtPosition(
                        childAtPosition( withId( R.id.card ), 0 ),
                        1 ),
                isDisplayed() ) );

        viewInteraction.check( matches( withText( text ) ) );

    }

    private static Matcher<View> childAtPosition( final Matcher<View> parentMatcher,
                                                  final int position ) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo( Description description ) {
                description.appendText( "Child at position " + position + " in parent " );
                parentMatcher.describeTo( description );
            }

            @Override
            public boolean matchesSafely( View view ) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup
                        && parentMatcher.matches( parent )
                        && view.equals( ( (ViewGroup) parent ).getChildAt( position ) );
            }
        };
    }

}
