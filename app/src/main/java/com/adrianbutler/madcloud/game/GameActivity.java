package com.adrianbutler.madcloud.game;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.madcloud.R;
import com.adrianbutler.madcloud.game.background.BackgroundView;
import com.adrianbutler.madcloud.game.utils.AudioPlay;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    BackgroundView backgroundView;

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
        //bg music goes here


//        relativeLayout.addView(new GameView(this, size.x, size.y));
//        relativeLayout.addView(new BackgroundView(this));

        gameView = new GameView(this, size.x, size.y);
//        setContentView(R.layout.game_rel);
        setContentView(gameView);
        AudioPlay.playAudio(getApplicationContext(), R.raw.bolero);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.stopped();
        AudioPlay.stopAudio();
    }

    //this on resume will set the playing variable to true and start the game loop
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
