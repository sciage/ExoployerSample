package com.example.exoployersample;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MyExoplayer extends AppCompatActivity {
    private PlaybackStateListener playbackStateListener;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exoplayer);

        playerView = findViewById(R.id.player_view);
        playbackStateListener = new PlaybackStateListener();


    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (Util.SDK_INT > 23){
            initializePlayer();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUi();
    }

    @Override
    protected void onPause() {
        super.onPause();

//        if (Util.SDK_INT < 23){
            releasePlayer();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();

//        if (Util.SDK_INT > 23){
            releasePlayer();
//        }
    }

    private void initializePlayer() {
        if (player == null){
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
            trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());
            player = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();
        }

        playerView.setPlayer(player);

        Uri uri = Uri.parse("https://storage.googleapis.com/misco-storage/uploads/videos/1602336098026_output_compose_video.mp4");
        MediaSource mediaSource = buildMediaSource(uri);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.addAudioListener(playbackStateListener);
        player.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory datasourceFactory = new DefaultDataSourceFactory(this, "exoplayer-harish");
        DashMediaSource.Factory mediaSourceFactory = new DashMediaSource.Factory(datasourceFactory);

        return mediaSourceFactory.createMediaSource(uri);
    }

    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void releasePlayer() {
        if (player != null){
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.removeListener(playbackStateListener);
            player.release();
            player = null;
        }
    }

    private class PlaybackStateListener implements Player.EventListener, AudioListener {

        public void onPlayerStateChanged(boolean playWhenReady, int playbackState){
            String stateString;
            switch (playbackState){

                case SimpleExoPlayer.STATE_IDLE:
                    stateString = "Exoplayer.STATE.IDLE";
                    break;
                case SimpleExoPlayer.STATE_BUFFERING:
                    stateString = "Exoplayer.STATE.BUFFERING";
                    break;
                case SimpleExoPlayer.STATE_READY:
                    stateString = "Exoplayer.STATE.READY";
                    break;
                case SimpleExoPlayer.STATE_ENDED:
                    stateString = "Exoplayer.STATE.ENDED";
                    break;
                default:
                    stateString = "UNKNOWN_STATE";
                    break;


            }
        }
    }


}