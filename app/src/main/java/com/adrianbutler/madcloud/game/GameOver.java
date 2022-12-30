package com.adrianbutler.madcloud.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adrianbutler.madcloud.MainActivity;
import com.adrianbutler.madcloud.R;
import com.adrianbutler.madcloud.ads.AdManager;

public class GameOver extends AppCompatActivity {
    Button playAgain;
    Button scoreBoard = findViewById(R.id.scoreboard_btn);
    Button homeButton = findViewById(R.id.home_button);
    TextView yourScore = findViewById(R.id.score_text);

    AdManager adManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        adManager = new AdManager(GameOver.this, false);
        uiThread();
//        yourScore.setText(getIntent().getStringExtra("score"));

        playAgain = findViewById(R.id.play_again_btn);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this, GameActivity.class);
                startActivity(intent);
            }
        });

//        scoreBoard.setOnClickListener();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);
            }
        });

        setContentView(R.layout.activity_game_over);
    }


    public void uiThread() {
        runOnUiThread(() -> {
            adManager = new AdManager(GameOver.this, false);
            adManager.loadRewardedAd(() -> {
                adManager.showRewardedAd(this, (() -> {
                }));
            });
        });
    }
}