package com.adrianbutler.madcloud.utils.sound;

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
    int[] fxArray;
    private final int POOL_MAX = 10;
    private final Context mContext;
    private static SoundHelper soundHelper;
    private SoundPool soundPool = null;
    private final int rcv_id = -1;
	private final int snd_id = -1;
    private int keyA, keyB, keyC, keyD, keyE;


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
        keyA = soundPool.load(mContext, R.raw.strike, 1);
        keyB = soundPool.load(mContext, R.raw.crow_short, 1);
        keyC = soundPool.load(mContext, R.raw.owl, 1);
        keyD = soundPool.load(mContext, R.raw.nevermore, 1);
        fxArray = new int[]{keyA, keyB, keyC, keyD};
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

    public void triggerSFX(String name) {
        switch (name) {
            case "strike":
                if (fxArray[0] != -1) {
                    float volume = getVolume();

                    soundPool.play(fxArray[0], volume, volume, 1, 0, 1f);
                    Log.e(TAG, "strike vol:" + volume);
                    break;
                }
            case "crow":
                if (fxArray[1] != -1) {
                    float volume = getVolume();
                    soundPool.play(fxArray[1], volume, volume, 1, 0, 1f);

//                    soundPool.play(fxArray[3], volume, volume, 1, 0, 1f);
                    Log.e(TAG, "crow vol:" + volume);
                    break;
                }
            case "raven":
                if (fxArray[1] != -1) {
                    float volume = getVolume();
                    soundPool.play(fxArray[1], volume, volume, 1, 0, 0.6f);

//                    soundPool.play(fxArray[3], volume, volume, 1, 0, 1f);
                    Log.e(TAG, "crow vol:" + volume);
                    break;
                }
            case "owl":
                if (fxArray[2] != -1) {
                    float volume = getVolume();
                    soundPool.play(fxArray[2], volume, volume, 1, 0, 1f);
                    Log.e(TAG, "owl vol:" + volume);
                    break;
                }
//            default:

        }
    }

//    public void playThunder() {
//        if (fxArray[0] != -1) {
//            float volume = getVolume();
//            soundPool.play(fxArray[0], volume, volume, 1, 0, 1f);
//            Log.e(TAG, "playReceiveSound vol:" + volume);
//        }

    //    }
//    public void playCrow() {
//        if (fxArray[1] != -1) {
//            float volume = getVolume();
//            soundPool.play(fxArray[1], volume, volume, 1, 0, 1f);
//            Log.e(TAG, "playSendSound vol:" + volume);
//        }
//    }
    public void playOwl() {

//    }

    }
}