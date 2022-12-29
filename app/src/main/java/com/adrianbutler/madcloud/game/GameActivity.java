package com.adrianbutler.madcloud.game;

import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.madcloud.R;
import com.adrianbutler.madcloud.game.background.BackgroundView;

import java.io.IOException;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    BackgroundView backgroundView;
    MediaPlayer mediaPlayer;
    SoundPool sp;
    int nowPlaying = -1;
    int idFX1 = -1;
    float volume = 1;// Volumes range from 0 through 1
//    RelativeLayout relativeLayout = findViewById(R.id.game_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // gets display as an object
        Display display = getWindowManager().getDefaultDisplay();

        //Gets the screen resolution as point objects
        Point size = new Point();
        display.getSize(size);

//        relativeLayout.addView(new GameView(this, size.x, size.y));
//        relativeLayout.addView(new BackgroundView(this));

        gameView = new GameView(this, size.x, size.y);
//        setContentView(R.layout.game_rel);

                        //background sound/music
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wind);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.stopped();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    //this on resume will set the playing variable to true and start the game loop
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
