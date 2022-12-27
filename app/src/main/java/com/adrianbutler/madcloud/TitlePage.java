package com.adrianbutler.madcloud;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.core.Amplify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.chrono.MinguoChronology;
import java.util.Date;

public class TitlePage extends AppCompatActivity {
    public final String TAG = "main_activity";
    public AuthUser authUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_page);

        // TODO Get the currentAuthUser
//        Amplify.Auth.getCurrentUser(
//                success ->
//                {
//                    Log.i(TAG, "User is authorized OK");
//                    authUser = success;
//                },
//                failure -> Log.w(TAG, "No authenticated User present")
//        );
        setupTitleButtons();
//        setupGreeting();
    }


    private void playAudio(InputStream data) {
//        File mp3File = new File(getCacheDir(), "audio.mp3");
//
//        try (OutputStream out = new FileOutputStream(mp3File)) {
//            Log.i(TAG, "Reading input stream");
//            byte[] buffer = new byte[8 * 1_024];
//            int bytesRead;
//            while ((bytesRead = data.read(buffer)) != -1) {
//                out.write(buffer, 0, bytesRead);
//            }
//            mp.reset();
//            mp.setOnPreparedListener(MediaPlayer::start);
//            mp.setDataSource(new FileInputStream(mp3File).getFD());
//            mp.prepareAsync();
//        } catch (IOException error) {
//            Log.e("MyAmplifyApp", "Error writing audio file", error);
//        }
    }

    @SuppressLint("SetTextI18n")
//    public void setupGreeting() {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String username = preferences.getString(UserProfileActivity.USERNAME_TAG, "No username");
//        ((TextView) findViewById(R.id.Main_TVPersonName)).setText(username + " (" + teamname + ")" + " Tasks"); //Main title
//    }

    public void setupTitleButtons() {

        Button goToGameBtn = this.findViewById(R.id.TitlePlayBtn);
        goToGameBtn.setOnClickListener(view -> {
            Intent goToGame = new Intent(this, MainActivity.class);
            startActivity(goToGame);
        });

        Button goToStatsBtn = findViewById(R.id.TitleStatsBtn);
        goToStatsBtn.setOnClickListener(view -> {
            Intent goToStats = new Intent(this, MainActivity.class);
            startActivity(goToStats);
        });

        Button quitBtn = findViewById(R.id.TitleQuitBtn);
        quitBtn.setOnClickListener(view -> {
            Intent goToLocate = new Intent(this, MainActivity.class);
            Toast.makeText(this, "Quitting!", Toast.LENGTH_SHORT).show();
            startActivity(goToLocate);
        });
    }
}