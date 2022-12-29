package com.adrianbutler.madcloud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adrianbutler.madcloud.game.GameActivity;
import com.adrianbutler.madcloud.game.utils.AudioPlay;
import com.adrianbutler.madcloud.game.utils.SoundHelper;

public class MainActivity extends AppCompatActivity {
    SoundHelper sfx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sfx = new SoundHelper(getApplicationContext());
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        // Get the shared preferences object
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        // Get the saved username from shared preferences
        String username = sharedPreferences.getString("username", "");

        // If a username is saved in shared preferences, prefill the username input field with it
        EditText usernameInput = findViewById(R.id.username_input);
        if (!username.equals("")) {
            usernameInput.setText(username);
        }
        //titlestrike sound/music
        AudioPlay.playAudio(getApplicationContext(), R.raw.thunder_roll);

//		playBtn = findViewById(R.id.landing_button_play);


        setupTitleButtons();
//
//        Button playButton = findViewById(R.id.soundFXBtn);
//        playButton.setOnClickListener(onPlayButtonClickListener);
//        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
//        mSoundPool.load(this, R.raw.crow_short, 1);

        //raven
        ImageView ravenFly = (ImageView) findViewById(R.id.flyRaven);
        ravenFly.setBackgroundResource(R.drawable.fly);
        AnimationDrawable animation = (AnimationDrawable) ravenFly.getBackground();
        //Start the animation:
        animation.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setupTitleButtons() {
        EditText usernameInput = findViewById(R.id.username_input);
        TextView usernamePrompt = findViewById(R.id.username_prompt);
        TextView usernameDisplay = findViewById(R.id.username_display);
        Button goToGameBtn = findViewById(R.id.TitlePlayBtn);

        // Get the shared preferences object
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        // Get the saved username from shared preferences
        final String[] username = {sharedPreferences.getString("username", "")};

        // If a username is saved in shared preferences, display it in place of the EditText and TextView widgets
        if (!username[0].equals("")) {
            usernameInput.setVisibility(View.GONE);
            usernamePrompt.setVisibility(View.GONE);
            usernameDisplay.setText(username[0]);
            usernameDisplay.setVisibility(View.VISIBLE);
            goToGameBtn.setEnabled(true);
        } else {
            // Otherwise, set an OnEditorActionListener on the EditText widget to save the inputted username to shared preferences
            usernameInput.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Set the text of the username display TextView to the input from the EditText widget
                    usernameDisplay.setText(usernameInput.getText().toString());
                    // Make the username display TextView visible
                    usernameDisplay.setVisibility(View.VISIBLE);
                    // Make the username input EditText widget gone
                    usernameInput.setVisibility(View.GONE);
                    // Set TextView widget gone
                    usernamePrompt.setVisibility(View.GONE);
                    // Enable the play button
                    goToGameBtn.setEnabled(true);

                    // Save the inputted username to shared preferences
                    username[0] = usernameInput.getText().toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username[0]);
                    editor.apply();

                    return true;
                }
                return false;
            });
        }

        goToGameBtn.setOnClickListener(view -> {
            Intent goToGame = new Intent(this, GameActivity.class);
            sfx.triggerSFX("crow");
            startActivity(goToGame);
        });

        Button goToStatsBtn = findViewById(R.id.TitleStatsBtn);
        goToStatsBtn.setOnClickListener(view -> {
            Intent goToStats = new Intent(this, MainActivity.class);
            startActivity(goToStats);
        });

        Button quitBtn = findViewById(R.id.TitleQuitBtn);
        quitBtn.setOnClickListener(view -> {
            Toast.makeText(this, "Quitting!", Toast.LENGTH_SHORT).show();
            finishAndRemoveTask();
        });
    }
}
