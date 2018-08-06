package com.vuki.bakingapp.ui.step;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.ActivityStepBinding;
import com.vuki.bakingapp.models.ApiSteps;
import com.vuki.bakingapp.ui.BaseActivity;
import com.vuki.bakingapp.ui.details.RecipeDetailsActivity;

import java.util.List;

public class StepActivity extends BaseActivity implements StepFragment.OnChangeStepListener {

    private ActivityStepBinding binding;
    private List<ApiSteps> steps;
    private int currentStep = -1;
    private StepFragment stepFragment;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_step );

        Bundle extras = getIntent().getExtras();
        if ( extras == null ) {
            finish();
        }
        steps = (List<ApiSteps>) extras.getSerializable( RecipeDetailsActivity.STEPS );
        currentStep = extras.getInt( RecipeDetailsActivity.CURRENT_STEP );

        if ( savedInstanceState == null ) {
            stepFragment = StepFragment.newInstance( steps.get( currentStep ) );
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace( R.id.fragment, stepFragment );
            ft.commit();
        }

        if ( this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            binding.toolbar.setVisibility( View.GONE );
        } else {
            binding.toolbar.setVisibility( View.VISIBLE );
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
        if ( currentStep == steps.size() - 1 ) {
            return;
        }
        currentStep++;

        changeStep();
    }

    private void changeStep() {
        ApiSteps step = steps.get( currentStep );
        setupToolbar( binding.toolbar, currentStep == 0
                ? step.getShortDescription()
                : currentStep + ". " + step.getShortDescription() );
        stepFragment.populateData( step );

    }
}
