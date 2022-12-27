package com.adrianbutler.madcloud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SoundPool mSoundPool;
    private int mSoundId = 1;
    private int mStreamId;

    private SoundPool soundPool;
    private int[] soundEffectsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        mSoundPool.load(this, R.raw.thunder, 1);


        Button playButton = findViewById(R.id.soundFXBtn);
        Button pauseButton = findViewById(R.id.soundFXBtn2);
        playButton.setOnClickListener(onPlayButtonClickListener);
        pauseButton.setOnClickListener(onPlay2);
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

    Button.OnClickListener onPlay2
            = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            setupSounds();
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

        soundPool.play(keyA, 1, (float) 0.65,1,0, 1);
        Toast.makeText(getApplicationContext(),
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",
                Toast.LENGTH_LONG).show();

//        soundEffectsArray = new int[]{keyA, keyB, keyC, keyD, keyE, bgSong};

    }
}
