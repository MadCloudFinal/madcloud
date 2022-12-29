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
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private SoundPool mSoundPool;
    private int mSoundId = 1;
    private int mStreamId;

    private SoundPool soundPool;
    MediaPlayer mediaPlayer;
    private int[] soundEffectsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        //title sound/music
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.thunder);
        mediaPlayer.start();

        
//		playBtn = findViewById(R.id.landing_button_play);

        Button playButton = findViewById(R.id.soundFXBtn);
        playButton.setOnClickListener(onPlayButtonClickListener);
        setupTitleButtons();

        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        mSoundPool.load(this, R.raw.voron, 1);

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
        mediaPlayer.stop();
        mediaPlayer.release();
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

    Button.OnClickListener onPlayButtonClickListener
            = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float leftVolume = curVolume / maxVolume;
            float rightVolume = curVolume / maxVolume;
            int priority = 1;
            int no_loop = 0;
            float normal_playback_rate = 1f;
            mStreamId = mSoundPool.play(mSoundId, leftVolume, rightVolume, priority, no_loop,
                    normal_playback_rate);

            Toast.makeText(getApplicationContext(),
                    "soundPool.play()",
                    Toast.LENGTH_LONG).show();
        }
    };



    private void setupSounds() {
        AudioAttributes audioAttributes = new AudioAttributes
                .Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool
                .Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build();
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        int keyA = soundPool.load(this, R.raw.thunder, 1);
//        int keyB = soundPool.load(this, R.raw.new_key02, 1);
//        int keyC = soundPool.load(this, R.raw.new_key03, 1);
//        int keyD = soundPool.load(this, R.raw.new_key04, 1);
//        int keyE = soundPool.load(this, R.raw.new_key05, 1);
//        int bgSong = soundPool.load(this,R.raw.flowing_rocks_short,1);
        soundEffectsArray = new int[]{keyA};

        soundPool.play(keyA, 1, (float) 0.65, 1, 0, 1);
        Toast.makeText(getApplicationContext(),
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",
                Toast.LENGTH_LONG).show();

//        soundEffectsArray = new int[]{keyA, keyB, keyC, keyD, keyE, bgSong};

    }

}
