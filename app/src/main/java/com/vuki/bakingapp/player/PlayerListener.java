package com.vuki.bakingapp.player;

import com.google.android.exoplayer2.Player;

/**
 * Created by mvukosav
 */
public interface PlayerListener extends Player.EventListener {

    @Override
    void onPlayerStateChanged( boolean playWhenReady, int playbackState );


}
