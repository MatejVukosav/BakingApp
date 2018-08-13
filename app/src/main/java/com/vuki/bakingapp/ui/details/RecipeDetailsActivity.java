package com.vuki.bakingapp.ui.details;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.ActivityRecipeDetailsBinding;
import com.vuki.bakingapp.models.ApiIngredient;
import com.vuki.bakingapp.models.ApiReceipt;
import com.vuki.bakingapp.models.ApiSteps;
import com.vuki.bakingapp.ui.BaseActivity;
import com.vuki.bakingapp.ui.step.StepActivity;
import com.vuki.bakingapp.ui.step.StepFragment;

import java.io.Serializable;
import java.util.List;

public class RecipeDetailsActivity
        extends BaseActivity
        implements ReceiptDetailsFragment.OnItemSelectedListener,
        StepFragment.OnChangeStepListener {

    private ActivityRecipeDetailsBinding binding;
    private ApiReceipt receipt;
    private ReceiptDetailsFragment receiptDetailsFragment;
    private int currentStep = 0;
    private StepFragment stepFragment;
    public static String CURRENT_STEP = "current_step";
    public static String EXTRA_RECEIPT = "receipt";
    public static String STEPS = "steps";
    public static String SAVED_INSTANCE_CURRENT_STEP = "saved_instance_current_step";
    public static String SAVED_INSTANCE_RECEIPT = "saved_instance_receipt";
    private static String SAVED_INSTANCE_RECEIPT_DETAILS_FRAGMENT = "receipt_details_fragment";
    public static String SAVED_INSTANCE_STEPS_FRAGMENT = "steps_fragment";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_recipe_details );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            receipt = (ApiReceipt) extras.getSerializable( EXTRA_RECEIPT );
            if ( isReceiptValid() ) {
                setupToolbar( binding.toolbar, receipt.getName() );
                binding.ingredients.setText( extractIngredients( receipt.getIngredients() ) );
            }
        }

        if ( savedInstanceState == null ) {
            if ( getResources().getBoolean( R.bool.isTablet ) ) {
                stepFragment = StepFragment.newInstance( receipt.getSteps().get( 0 ) );
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace( R.id.fragment_step, stepFragment );
                ft.commit();
            }
            receiptDetailsFragment = ReceiptDetailsFragment.newInstance( receipt );
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace( R.id.fragment_receipt_steps, receiptDetailsFragment );
            ft.commit();
        } else {
            if ( getResources().getBoolean( R.bool.isTablet ) ) {
                receiptDetailsFragment = (ReceiptDetailsFragment) getSupportFragmentManager().getFragment( savedInstanceState, SAVED_INSTANCE_RECEIPT_DETAILS_FRAGMENT );
                stepFragment = (StepFragment) getSupportFragmentManager().getFragment( savedInstanceState, SAVED_INSTANCE_STEPS_FRAGMENT );

                currentStep = savedInstanceState.getInt( SAVED_INSTANCE_CURRENT_STEP );
                receipt = (ApiReceipt) savedInstanceState.getSerializable( SAVED_INSTANCE_RECEIPT );
                if ( isReceiptValid() ) {
                    stepFragment.currentStep = receipt.getSteps().get( currentStep );
                    setupToolbar( receipt.getSteps().get( currentStep ) );
                }
            }
        }

    }

    private boolean isReceiptValid() {
        return receipt != null && receipt.getSteps() != null;
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        outState.putInt( SAVED_INSTANCE_CURRENT_STEP, currentStep );
        outState.putSerializable( SAVED_INSTANCE_RECEIPT, receipt );

        if ( getResources().getBoolean( R.bool.isTablet ) ) {
            getSupportFragmentManager().putFragment( outState, SAVED_INSTANCE_RECEIPT_DETAILS_FRAGMENT, receiptDetailsFragment );
            getSupportFragmentManager().putFragment( outState, SAVED_INSTANCE_STEPS_FRAGMENT, stepFragment );
        }

        super.onSaveInstanceState( outState );
    }

    public String extractIngredients( List<ApiIngredient> ingredients ) {
        StringBuilder ingredientsList = new StringBuilder();

        for ( ApiIngredient ingredient : ingredients ) {
            ingredientsList
                    .append( " - " )
                    .append( ingredient.getIngredient() )
                    .append( System.lineSeparator() );
        }

        return ingredientsList.toString();
    }

    @Override
    public void onItemSelected( ApiSteps step, int position ) {
        if ( getResources().getBoolean( R.bool.isTablet ) ) {
            currentStep = position;
            changeStep();
        } else {
            Intent intent = new Intent( RecipeDetailsActivity.this, StepActivity.class );
            Bundle bundle = new Bundle();
            bundle.putSerializable( STEPS, (Serializable) receipt.getSteps() );
            bundle.putInt( CURRENT_STEP, position );
            intent.putExtras( bundle );
            startActivity( intent );
        }
    }

    @Override
    public void previous() {
        if ( currentStep == 0 ) {
            return;
        }
        currentStep--;
        changeStep();
    }

    @Override
    public void next() {
        if ( currentStep == receipt.getSteps().size() - 1 ) {
            return;
        }
        currentStep++;
        changeStep();
    }

    private void changeStep() {
        ApiSteps step = receipt.getSteps().get( currentStep );
        setupToolbar( step );
        stepFragment.populateData( step );
    }

    private void setupToolbar( ApiSteps step ) {
        setupToolbar( binding.toolbar, currentStep == 0
                ? step.getShortDescription()
                : currentStep + ". " + step.getShortDescription() );
    }
}
