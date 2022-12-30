package com.adrianbutler.madcloud.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.madcloud.MainActivity;
import com.adrianbutler.madcloud.R;
import com.adrianbutler.madcloud.leaderboard.LeaderboardActivity;
import com.adrianbutler.madcloud.utils.ads.AdManager;
import com.adrianbutler.madcloud.utils.api.GraphQLManager;
import com.adrianbutler.madcloud.utils.auth.SharedPreferencesManager;

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
        playAgain = findViewById(R.id.play_again_btn);

        uiThread();

        scoreBoard.setOnClickListener(view ->
        {
            Intent intent = new Intent(this, LeaderboardActivity.class);
            startActivity(intent);
        });


        playAgain.setOnClickListener(view ->
        {
            Intent intent = new Intent(GameOver.this, GameActivity.class);
            startActivity(intent);
        });


        homeButton.setOnClickListener(view ->
        {
            Intent intent = new Intent(GameOver.this, MainActivity.class);
            startActivity(intent);
        });

        yourScore.setText(getIntent().getStringExtra("score"));

        int score = Integer.parseInt(getIntent().getStringExtra("score"));

        Log.i("GameOverActivity", "Saving user score");

        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);

        sharedPreferencesManager.saveUserHighScore(score);

        String userId = sharedPreferencesManager.getUserId();

        GraphQLManager.updateUsersHighScore(userId, score);
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