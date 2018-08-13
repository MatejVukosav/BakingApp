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

import static com.vuki.bakingapp.ui.details.RecipeDetailsActivity.SAVED_INSTANCE_CURRENT_STEP;
import static com.vuki.bakingapp.ui.details.RecipeDetailsActivity.SAVED_INSTANCE_STEPS_FRAGMENT;

public class StepActivity extends BaseActivity implements StepFragment.OnChangeStepListener {

    private ActivityStepBinding binding;
    private List<ApiSteps> steps;
    private int currentStepIndex = -1;
    private StepFragment stepFragment;
    private ApiSteps currentStep;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_step );

        Bundle extras = getIntent().getExtras();
        if ( extras == null ) {
            finish();
        } else {
            steps = (List<ApiSteps>) extras.getSerializable( RecipeDetailsActivity.STEPS );
            currentStepIndex = extras.getInt( RecipeDetailsActivity.CURRENT_STEP );
        }

        if ( savedInstanceState == null ) {
            currentStep = steps.get( currentStepIndex );
            stepFragment = StepFragment.newInstance( currentStep, true, 0 );
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace( R.id.fragment, stepFragment );
            ft.commit();
        } else {
            stepFragment = (StepFragment) getSupportFragmentManager().getFragment( savedInstanceState, SAVED_INSTANCE_STEPS_FRAGMENT );
            currentStepIndex = savedInstanceState.getInt( SAVED_INSTANCE_CURRENT_STEP );
            boolean playWhenReady = savedInstanceState.getBoolean( RecipeDetailsActivity.SAVED_INSTANCE_PLAY_WHEN_READY );
            long playedVideoPosition = savedInstanceState.getLong( RecipeDetailsActivity.SAVED_INSTANCE_PLAYED_VIDEO_POSITION );
            currentStep = steps.get( currentStepIndex );
            stepFragment.currentStep = currentStep;
            stepFragment.setPlayWhenReady( playWhenReady );
            stepFragment.setVideoPosition( playedVideoPosition );
        }
        setupToolbar( currentStep );

        if ( this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ) {
            binding.toolbar.setVisibility( View.GONE );
        } else {
            binding.toolbar.setVisibility( View.VISIBLE );
        }
    }

    private void setupToolbar( ApiSteps step ) {
        setupToolbar( binding.toolbar, currentStepIndex == 0
                ? step.getShortDescription()
                : currentStepIndex + ". " + step.getShortDescription() );
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        outState.putInt( SAVED_INSTANCE_CURRENT_STEP, currentStepIndex );
        outState.putBoolean( RecipeDetailsActivity.SAVED_INSTANCE_PLAY_WHEN_READY, stepFragment.shouldVideoAutoStart() );
        outState.putLong( RecipeDetailsActivity.SAVED_INSTANCE_PLAYED_VIDEO_POSITION, stepFragment.getPlayedVideoLocation() );
        getSupportFragmentManager().putFragment( outState, SAVED_INSTANCE_STEPS_FRAGMENT, stepFragment );
        super.onSaveInstanceState( outState );
    }

    @Override
    public void previous() {
        if ( currentStepIndex == 0 ) {
            return;
        }
        currentStepIndex--;
        changeStep();
    }

    @Override
    public void next() {
        if ( currentStepIndex == steps.size() - 1 ) {
            return;
        }
        currentStepIndex++;
        changeStep();
    }

    private void changeStep() {
        currentStep = steps.get( currentStepIndex );
        setupToolbar( currentStep );
        stepFragment.populateData( currentStep );
    }

}
