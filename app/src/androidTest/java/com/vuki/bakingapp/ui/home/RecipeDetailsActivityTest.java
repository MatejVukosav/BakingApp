package com.vuki.bakingapp.ui.home;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.models.ApiIngredient;
import com.vuki.bakingapp.models.ApiReceipt;
import com.vuki.bakingapp.models.ApiSteps;
import com.vuki.bakingapp.ui.details.RecipeDetailsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {

//    @Rule
//    public IntentsTestRule<RecipeDetailsActivity> intentsTestRule
//            = new IntentsTestRule<>( RecipeDetailsActivity.class );

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> recipeDetailsActivityActivityTestRule =
            new ActivityTestRule<RecipeDetailsActivity>( RecipeDetailsActivity.class ) {

                @Override
                protected Intent getActivityIntent() {
                    return createTestRecipeIntent( createTestRecipe() );
                }
            };

    @Test
    public void toolbar_set_title_from_intent_data() {
        ApiReceipt recipe = createTestRecipe();

        ViewInteraction viewInteraction = onView( ViewMatchers.withId( R.id.toolbar ) );

        viewInteraction.check( matches( ViewMatchers.isDisplayed() ) );
        onView( withText( recipe.getName() ) )
                .check( matches( ViewMatchers.withParent( ViewMatchers.withId( R.id.toolbar ) ) ) );
    }

    //@Test

    /**
     *TODO THIS TEST DOES NOT WORK
     */
    public void toolbar_title_setup_invalid_test() {
        ApiReceipt recipe = createTestRecipe();
        Intent intent = createTestRecipeIntent( recipe );

        // Build the result to return when the activity is launched.
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult( Activity.RESULT_OK, intent );
        intending( hasComponent( RecipeDetailsActivity.class.getName() ) )
                .respondWith( result );

        ViewInteraction viewInteraction = onView( ViewMatchers.withId( R.id.toolbar ) );
        viewInteraction.check( matches( ViewMatchers.isDisplayed() ) );
        //why this does not work if I have toolbar in activity?
        viewInteraction
                .perform( scrollTo() ) //Error performing 'scroll to' on view 'with id: com.vuki.bakingapp:id/toolbar'.
                .check( matches( withText( recipe.getName() ) ) );
    }

    private Intent createTestRecipeIntent( ApiReceipt recipe ) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable( RecipeDetailsActivity.EXTRA_RECEIPT, recipe );
        intent.putExtras( bundle );
        return intent;
    }

    private ApiReceipt createTestRecipe() {
        return new ApiReceipt( 1,
                "test receipt name",
                new ArrayList<ApiIngredient>(),
                new ArrayList<>(
                        Collections.singletonList(
                                new ApiSteps( 1, "short desc", "desc", null, null )
                        )
                ),
                0,
                null );
    }
}
