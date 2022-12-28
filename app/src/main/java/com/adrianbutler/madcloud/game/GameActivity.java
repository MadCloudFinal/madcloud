package com.adrianbutler.madcloud.game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.madcloud.R;
import com.adrianbutler.madcloud.game.background.BackgroundView;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    BackgroundView backgroundView;
//    RelativeLayout relativeLayout = findViewById(R.id.game_view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // gets display as an object
        Display display =  getWindowManager().getDefaultDisplay();

        //Gets the screen resolution as point objects
        Point size = new Point();
        display.getSize(size);

//        relativeLayout.addView(new GameView(this, size.x, size.y));
//        relativeLayout.addView(new BackgroundView(this));

        gameView = new GameView(this, size.x, size.y);
//        setContentView(R.layout.game_rel);
        setContentView(gameView);
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
