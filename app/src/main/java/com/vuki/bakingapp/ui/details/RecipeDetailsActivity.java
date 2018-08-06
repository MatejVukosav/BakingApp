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

import java.io.Serializable;
import java.util.List;

public class RecipeDetailsActivity
        extends BaseActivity
        implements ReceiptDetailsFragment.OnItemSelectedListener {

    ActivityRecipeDetailsBinding binding;
    ApiReceipt receipt;
    private ReceiptDetailsFragment receiptDetailsFragment;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_recipe_details );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            receipt = (ApiReceipt) extras.getSerializable( "receipt" );
            if ( receipt != null && receipt.getSteps() != null ) {

                if ( savedInstanceState == null ) {
                    receiptDetailsFragment = ReceiptDetailsFragment.newInstance( receipt );
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace( R.id.fragment_receipt_steps, receiptDetailsFragment );
                    ft.commit();
                }

                setupToolbar( binding.toolbar, receipt.getName() );

                binding.ingredients.setText( extractIngredients( receipt.getIngredients() ) );
            }
        }

    }

    public String extractIngredients( List<ApiIngredient> ingredients ) {
        StringBuilder ingredientsList = new StringBuilder();

        for ( ApiIngredient ingredient : ingredients ) {
            ingredientsList
                    .append( " - " )
                    .append( ingredient.getIngredient() )
                    .append( System.getProperty( "line.separator" ) );
        }

        return ingredientsList.toString();
    }

    @Override
    public void onItemSelected( ApiSteps step, int position ) {
        Intent intent = new Intent( RecipeDetailsActivity.this, StepActivity.class );
        Bundle bundle = new Bundle();
        bundle.putSerializable( "steps", (Serializable) receipt.getSteps() );
        bundle.putInt( "current_step", position );
        intent.putExtras( bundle );
        startActivity( intent );
    }
}
