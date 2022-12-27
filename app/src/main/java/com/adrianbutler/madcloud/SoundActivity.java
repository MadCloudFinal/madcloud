package com.adrianbutler.madcloud;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;

public class SoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);
//
//        Log.v(TAG, "Initializing sounds...");

        SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int soundId = soundPool.load(getApplicationContext(), R.raw.thunder, 1);
        // soundId for reuse later on
        soundPool.play(soundId, 1, 1, 0, 0, 1);
        soundPool.release();
        soundPool = null;
    }
}