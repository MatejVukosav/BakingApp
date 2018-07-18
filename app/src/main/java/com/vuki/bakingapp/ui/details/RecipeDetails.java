package com.vuki.bakingapp.ui.details;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.ActivityRecipeDetailsBinding;
import com.vuki.bakingapp.models.ApiReceipt;
import com.vuki.bakingapp.ui.step.StepActivity;

import java.io.Serializable;

public class RecipeDetails extends AppCompatActivity implements ReceiptDetailsActivityRecyclerViewAdapter.OnItemClickListener {

    ActivityRecipeDetailsBinding binding;
    ReceiptDetailsActivityRecyclerViewAdapter adapter;
    ApiReceipt receipt;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_recipe_details );

        Bundle extras = getIntent().getExtras();
        if ( extras != null ) {
            receipt = (ApiReceipt) extras.getSerializable( "receipt" );
            if ( receipt.getSteps() != null ) {
                adapter = new ReceiptDetailsActivityRecyclerViewAdapter( this, receipt.getSteps(), this );
                binding.recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
                binding.recyclerView.setAdapter( adapter );
            }
        }
    }

    @Override
    public void onItemClick( int position ) {
        Intent intent = new Intent( RecipeDetails.this, StepActivity.class );
        Bundle bundle = new Bundle();
        bundle.putSerializable( "steps", (Serializable) receipt.getSteps() );
        bundle.putInt( "current_step", position );
        intent.putExtras( bundle );
        startActivity( intent );
    }
}
