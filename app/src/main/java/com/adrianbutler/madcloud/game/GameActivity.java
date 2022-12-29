package com.adrianbutler.madcloud.game;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

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


//        relativeLayout.addView(new GameView(this, size.x, size.y));
//        relativeLayout.addView(new BackgroundView(this));

        gameView = new GameView(this, size.x, size.y);
//        setContentView(R.layout.game_rel);
        setContentView(gameView);

        Button button = new Button(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 0;
        params.gravity = Gravity.TOP | Gravity.RIGHT;

        button.setText("OFF");
        addContentView(button, params);
        // setContentView(tv
        //background music logic
        AudioPlay.playAudio(getApplicationContext(), R.raw.bolero);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AudioPlay.isplayingAudio) {
                    AudioPlay.stopAudio();
                    button.setText("ON");
                } else {
                    AudioPlay.playAudio(getApplicationContext(), R.raw.bolero);
                    button.setText("OFF");
                }
            }
        });

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
