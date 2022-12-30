package com.adrianbutler.madcloud.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.madcloud.MainActivity;
import com.adrianbutler.madcloud.R;
import com.adrianbutler.madcloud.utils.ads.AdManager;

public class GameOver extends AppCompatActivity {
    Button playAgain;
    Button scoreBoard;
    Button homeButton;
    TextView yourScore;

    AdManager adManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        scoreBoard = findViewById(R.id.scoreboard_btn);
        homeButton = findViewById(R.id.home_button);
        yourScore = findViewById(R.id.score_text);

        uiThread();

        playAgain = findViewById(R.id.play_again_btn);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this, GameActivity.class);
                startActivity(intent);
            }
        });


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);
            }
        });


        yourScore.setText(getIntent().getStringExtra("score"));
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