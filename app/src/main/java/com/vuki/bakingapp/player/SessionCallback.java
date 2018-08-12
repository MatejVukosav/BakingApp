package com.vuki.bakingapp.player;

import android.support.v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.ExoPlayer;

/**
 * Created by mvukosav
 * Media Session Callbacks, where all external clients control the player.
 */
public class SessionCallback extends MediaSessionCompat.Callback {

    private ExoPlayer player;

    public SessionCallback( ExoPlayer player ) {
        this.player = player;
    }

    @Override
    public void onPlay() {
        player.setPlayWhenReady( true );
    }

    @Override
    public void onPause() {
        player.setPlayWhenReady( false );
    }

    @Override
    public void onSkipToPrevious() {
        player.seekTo( 0 );
    }
}