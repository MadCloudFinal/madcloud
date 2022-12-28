package com.adrianbutler.madcloud.game;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import com.adrianbutler.madcloud.R;

public class SoundHelper {
    private final String TAG = "SoundHelper";

    private final int POOL_MAX = 10;
    private Context mContext;
    private static SoundHelper soundHelper;
    private SoundPool soundPool = null;
    private int rcv_id = -1, snd_id = -1;

    public SoundHelper get(Context context) {
        if (soundHelper == null) {
            soundHelper = new SoundHelper(context);
        }
        return soundHelper;
    }

    public SoundHelper(Context mContext) {
        this.mContext = mContext;
        buildSoundPool();
    }

    private void buildSoundPool() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        } else {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attr)
                    .setMaxStreams(POOL_MAX)
                    .build();
        }
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.d(TAG, "onLoadComplete");
            }
        });
        // load sound
        rcv_id = soundPool.load(mContext, R.raw.thunder, 1);
        snd_id = soundPool.load(mContext, R.raw.voron, 1);
    }

    private float getVolume() {
        // get volume
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Activity.AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        return actualVolume / maxVolume;
    }

    public void playCrowe() {
        if (snd_id != -1) {
            float volume = getVolume();
            soundPool.play(snd_id, volume, volume, 1, 0, 1f);
            Log.e(TAG, "playSendSound vol:" + volume);
        }
    }

    public void playThunder() {
        if (rcv_id != -1) {
            float volume = getVolume();
            soundPool.play(rcv_id, volume, volume, 1, 0, 1f);
            Log.e(TAG, "playReceiveSound vol:" + volume);
        }
    }
}