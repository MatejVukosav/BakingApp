package com.vuki.bakingapp.ui.step;

import android.content.Context;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

/**
 * Created by mvukosav
 */
public class ExoPlayerManager {

    private SimpleExoPlayer player;

    public void initializePlayer( Context context, Player.EventListener listener ) {
        if ( player == null ) {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory( bandwidthMeter );
            DefaultTrackSelector trackSelector = new DefaultTrackSelector( videoTrackSelectionFactory );
            player = ExoPlayerFactory.newSimpleInstance( context, trackSelector );
            player.addListener( listener );
        }
    }

    public void releasePlayer() {
        player.stop();
        player.release();
        player = null;
    }

    public void setMediaSource( MediaSource source ) {
        player.prepare( source );
        player.setPlayWhenReady( true );
    }

    public SimpleExoPlayer getPlayer() {
        return player;
    }
}
