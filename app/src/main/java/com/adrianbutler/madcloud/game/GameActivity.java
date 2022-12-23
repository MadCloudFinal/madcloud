package com.adrianbutler.madcloud.game;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // gets display as an object
        Display display =  getWindowManager().getDefaultDisplay();

        //Gets the screen resolution as point objects
        Point size = new Point();
        display.getSize(size);

        gameView = new GameView(this, size.x, size.y);

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
