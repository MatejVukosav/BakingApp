package com.vuki.bakingapp.ui.step;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.FragmentStepBinding;
import com.vuki.bakingapp.models.ApiSteps;
import com.vuki.bakingapp.player.SessionCallback;

import static com.vuki.bakingapp.ui.details.RecipeDetailsActivity.SAVED_INSTANCE_CURRENT_STEP;

/**
 * Created by mvukosav
 */
public class StepFragment extends Fragment implements Player.EventListener {

    private static final String TAG = StepFragment.class.getCanonicalName();
    public ApiSteps currentStep;
    private FragmentStepBinding binding;
    public static String ARGUMENT_STEP = "currentStep";
    public static String ARGUMENT_VIDEO_POSITION = "video_position";
    public static String ARGUMENT_PLAY_WHEN_READY = "play_when_ready";

    public static String SAVED_INSTANCE_PLAY_WHEN_READY = "saved_instance_play_when_ready";
    public static String SAVED_INSTANCE_PLAYED_VIDEO_POSITION = "saved_instance_played_video_position";

    private PlaybackStateCompat.Builder mStateBuilder;
    private static MediaSessionCompat mMediaSession;
    private Context context;

    private boolean playWhenReady = false;
    private long videoPosition = 0;

    private ExoPlayerManager exoPlayerManager;

    public static StepFragment newInstance( ApiSteps step, boolean playWhenReady, long videoPosition ) {
        StepFragment fragmentDemo = new StepFragment();
        Bundle args = new Bundle();
        args.putSerializable( ARGUMENT_STEP, step );
        args.putSerializable( ARGUMENT_VIDEO_POSITION, videoPosition );
        args.putSerializable( ARGUMENT_PLAY_WHEN_READY, playWhenReady );
        fragmentDemo.setArguments( args );
        return fragmentDemo;
    }

    @Override
    public void onTimelineChanged( Timeline timeline, @Nullable Object manifest, int reason ) {

    }

    @Override
    public void onTracksChanged( TrackGroupArray trackGroups, TrackSelectionArray trackSelections ) {

    }

    @Override
    public void onLoadingChanged( boolean isLoading ) {

    }

    @Override
    public void onPlayerStateChanged( boolean playWhenReady, int playbackState ) {
        if ( ( playbackState == Player.STATE_READY ) && playWhenReady ) {
            mStateBuilder.setState( PlaybackStateCompat.STATE_PLAYING, exoPlayerManager.getPlayer().getCurrentPosition(), 1f );
        } else if ( ( playbackState == Player.STATE_READY ) ) {
            mStateBuilder.setState( PlaybackStateCompat.STATE_PAUSED, exoPlayerManager.getPlayer().getCurrentPosition(), 1f );
        }
        mMediaSession.setPlaybackState( mStateBuilder.build() );
    }

    @Override
    public void onRepeatModeChanged( int repeatMode ) {

    }

    @Override
    public void onShuffleModeEnabledChanged( boolean shuffleModeEnabled ) {

    }

    @Override
    public void onPlayerError( ExoPlaybackException error ) {

    }

    @Override
    public void onPositionDiscontinuity( int reason ) {

    }

    @Override
    public void onPlaybackParametersChanged( PlaybackParameters playbackParameters ) {

    }

    @Override
    public void onSeekProcessed() {

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

        if ( savedInstanceState == null ) {
            currentStep = (ApiSteps) getArguments().getSerializable( ARGUMENT_STEP );
            playWhenReady = (boolean) getArguments().getSerializable( ARGUMENT_PLAY_WHEN_READY );
            videoPosition = (long) getArguments().getSerializable( ARGUMENT_VIDEO_POSITION );
        } else {
            currentStep = (ApiSteps) savedInstanceState.getSerializable( SAVED_INSTANCE_CURRENT_STEP );
            playWhenReady = savedInstanceState.getBoolean( SAVED_INSTANCE_PLAY_WHEN_READY );
            videoPosition = savedInstanceState.getLong( SAVED_INSTANCE_PLAYED_VIDEO_POSITION );
        }

        this.context = getContext();
        exoPlayerManager = new ExoPlayerManager();

        initializeMediaSession();
    }

    @Override
    public void onSaveInstanceState( @NonNull Bundle outState ) {
        outState.putSerializable( SAVED_INSTANCE_CURRENT_STEP, currentStep );
        outState.putBoolean( SAVED_INSTANCE_PLAY_WHEN_READY, shouldVideoAutoStart() );
        outState.putLong( SAVED_INSTANCE_PLAYED_VIDEO_POSITION, getPlayedVideoLocation() );

        super.onSaveInstanceState( outState );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        exoPlayerManager.releasePlayer();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_step, container, false );
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

        return binding.getRoot();
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        exoPlayerManager.initializePlayer( context, this );
        binding.playerView.setPlayer( exoPlayerManager.getPlayer() );

        //TODO this does not setup right in tablet mode when change screen orientation
        exoPlayerManager.getPlayer().setPlayWhenReady( playWhenReady );
        exoPlayerManager.getPlayer().seekTo( videoPosition );
        populateData( currentStep );
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {
        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat( context, TAG );

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags( MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS );

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver( null );

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE );

        mMediaSession.setPlaybackState( mStateBuilder.build() );

        // SessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback( new SessionCallback( exoPlayerManager.getPlayer() ) );

        // Start the Media Session since the activity is active.
        mMediaSession.setActive( true );
    }

    public void populateData( ApiSteps step ) {
        currentStep = step;
        binding.instructions.setText( currentStep.getDescription() );

        if ( !TextUtils.isEmpty( currentStep.getVideoUrl() ) ) {
            binding.playerView.setVisibility( View.VISIBLE );
            exoPlayerManager.setMediaSource( prepareMediaSource() );
        } else {
            exoPlayerManager.getPlayer().stop();
            binding.playerView.setVisibility( View.GONE );
        }
    }

    private MediaSource prepareMediaSource() {
        String userAgent = Util.getUserAgent( getContext(), getApplicationName( context ) );
        return new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(
                        context,
                        userAgent ) )
                .createMediaSource( Uri.parse( currentStep.getVideoUrl() ) );
    }

    public static String getApplicationName( Context context ) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString( stringId );
    }

    public boolean shouldVideoAutoStart() {
        return exoPlayerManager.getPlayer().getPlayWhenReady();
    }

    public long getPlayedVideoLocation() {
        return Math.max( 0, exoPlayerManager.getPlayer().getContentPosition() );
    }

}
