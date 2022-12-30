package com.adrianbutler.madcloud.game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.madcloud.R;
import com.adrianbutler.madcloud.ads.AdManager;
import com.adrianbutler.madcloud.game.background.BackgroundView;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
     volatile AdManager adManager;
    Intent intent;

    MediaPlayer mediaPlayer;
//    RelativeLayout relativeLayout = findViewById(R.id.game_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        adManager = new AdManager(GameActivity.this, false);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // gets display as an object
        Display display =  getWindowManager().getDefaultDisplay();

        //Gets the screen resolution as point objects
        Point size = new Point();
        display.getSize(size);


        gameView = new GameView(this, size.x, size.y, GameActivity.this);
//background sound/music

//        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wind);
//        mediaPlayer.start();
        intent = new Intent(GameActivity.this, GameOver.class);
        setContentView(gameView);
    }

    public void uiThread(){

        intent.putExtra("score",gameView.getScore());
        startActivity(intent);

        try {
            gameView.gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameView.stopped();
    }


    //this on resume will set the playing variable to true and start the game loop
    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();
    }
}
