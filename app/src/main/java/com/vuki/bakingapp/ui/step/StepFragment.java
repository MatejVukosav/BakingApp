package com.vuki.bakingapp.ui.step;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.FragmentStepBinding;
import com.vuki.bakingapp.models.ApiSteps;

/**
 * Created by mvukosav
 */
public class StepFragment extends Fragment {

    public ApiSteps step;
    private MediaController mediaController;
    private FragmentStepBinding binding;
    public static String ARGUMENT_STEP = "step";

    public static StepFragment newInstance( ApiSteps step ) {
        StepFragment fragmentDemo = new StepFragment();
        Bundle args = new Bundle();
        args.putSerializable( ARGUMENT_STEP, step );
        fragmentDemo.setArguments( args );
        return fragmentDemo;
    }

    public interface OnChangeStepListener {
        void previous();

        void next();
    }

    private OnChangeStepListener listener;

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if ( context instanceof OnChangeStepListener ) {
            listener = (OnChangeStepListener) context;
        } else {
            throw new ClassCastException( context.toString()
                    + " must implement ReceiptDetailsFragment.OnItemSelectedListener" );
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        step = (ApiSteps) getArguments().getSerializable( ARGUMENT_STEP );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_step, container, false );
        populateData( step );
        binding.next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                listener.next();
            }
        } );

        binding.previous.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                listener.previous();

            }
        } );
        binding.video.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared( MediaPlayer mp ) {
                mp.setOnVideoSizeChangedListener( new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged( MediaPlayer mp, int width, int height ) {
                        mediaController = new MediaController( getActivity() );
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

        return binding.getRoot();
    }

    public void populateData( ApiSteps step ) {
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
