package com.adrianbutler.madcloud;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;

public class Sound2 extends AppCompatActivity {
    private SoundPool soundPool;
    private int[] soundEffectsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound2);
    }

    public void setupSounds(MainActivity context) {
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

        int keyA = soundPool.load(context, R.raw.thunder, 1);
//        int keyB = soundPool.load(this, R.raw.new_key02, 1);
//        int keyC = soundPool.load(this, R.raw.new_key03, 1);
//        int keyD = soundPool.load(this, R.raw.new_key04, 1);
//        int keyE = soundPool.load(this, R.raw.new_key05, 1);
//        int bgSong = soundPool.load(this, R.raw.flowing_rocks_short, 1);
//        soundEffectsArray = new int[]{keyA, keyB, keyC, keyD, keyE, bgSong};
        soundEffectsArray = new int[]{keyA};
        soundPool.play(soundEffectsArray[0], (float) 0.65, (float) 0.65, 1, 0, 1);
    }



}