package com.example.exoployersample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class MainActivity extends AppCompatActivity {
    SimpleExoPlayer player;
    StyledPlayerView styled_player_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise Exoplayer
        player = new SimpleExoPlayer.Builder(this).build();
        // Initialise PlayerView
        styled_player_view = findViewById(R.id.styled_player);
        // Attach Exoplayer with Playview
        styled_player_view.setPlayer(player);

        // Adding Video using MediaItem
        MediaItem mediaItem = MediaItem.fromUri("https://storage.googleapis.com/misco-storage/uploads/videos/1602336098026_output_compose_video.mp4");
        // set mediaItem to the player to be played
        player.setMediaItem(mediaItem);
        // prepare the player
        player.prepare();
        // start the player
        player.play();

    }
}