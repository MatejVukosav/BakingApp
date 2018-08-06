package com.vuki.bakingapp.ui.step;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.MediaController;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.ActivityStepBinding;
import com.vuki.bakingapp.models.ApiSteps;
import com.vuki.bakingapp.ui.BaseActivity;

import java.util.List;

public class StepActivity extends BaseActivity {

    ActivityStepBinding binding;
    List<ApiSteps> steps;
    int currentStep = -1;
    MediaController mediaController;
    Context context;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_step );

        context = this;

        Bundle extras = getIntent().getExtras();
        if ( extras == null ) {
            finish();
        }
        steps = (List<ApiSteps>) extras.getSerializable( "steps" );
        currentStep = extras.getInt( "current_step" );

        populateData( steps.get( currentStep ) );

        binding.next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( currentStep == steps.size() - 1 ) {
                    return;
                }
                currentStep++;
                populateData( steps.get( currentStep ) );
            }
        } );

        binding.previous.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if ( currentStep == 0 ) {
                    return;
                }
                currentStep--;
                populateData( steps.get( currentStep ) );
            }
        } );
        binding.video.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared( MediaPlayer mp ) {
                mp.setOnVideoSizeChangedListener( new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged( MediaPlayer mp, int width, int height ) {
                        mediaController = new MediaController( context );
                        binding.video.setMediaController( mediaController );
                        mediaController.setAnchorView( binding.video );
                    }
                } );
            }
        } );

        binding.video.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                //binding.video.start();
            }
        } );

    }

    private void populateData( ApiSteps step ) {

        setupToolbar( binding.toolbar, currentStep == 0 ? step.getShortDescription() : currentStep + ". " + step.getShortDescription() );

        binding.instructions.setText( step.getDescription() );
        if ( binding.video.isPlaying() ) {
            binding.video.stopPlayback();
        }

        if ( !TextUtils.isEmpty( step.getVideoUrl() ) ) {
            binding.video.setVisibility( View.VISIBLE );
            binding.video.setVideoURI( Uri.parse( step.getVideoUrl() ) );
            binding.video.start();
        } else {
            binding.video.setVisibility( View.GONE );
        }
    }
}
