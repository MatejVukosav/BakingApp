package com.vuki.bakingapp.ui.home;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {

    @Rule
    public IntentsTestRule<RecipeDetailsActivity> intentsTestRule
            = new IntentsTestRule<>( RecipeDetailsActivity.class );

    @Test
    public void is_toolbar_title_correct_setup() throws Exception {
        ApiReceipt recipe = createTestRecipe();
        Intent intent = createTestRecipeIntent( recipe );

        // Build the result to return when the activity is launched.
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult( Activity.RESULT_OK, intent );
        intending( hasComponent( RecipeDetailsActivity.class.getName() ) )
                .respondWith( result );

        onView( ViewMatchers.withId( R.id.toolbar ) )
                .perform( ViewActions.scrollTo() ) //Error performing 'scroll to' on view 'with id: com.vuki.bakingapp:id/toolbar'.
                .check( ViewAssertions.matches( ViewMatchers.withText( recipe.getName() ) ) );
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
                new ArrayList<ApiSteps>(),
                0,
                null );
    }
}
