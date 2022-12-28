package com.adrianbutler.madcloud.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import com.adrianbutler.madcloud.R;

public class UserName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_user_name);
        setupButtons();

    }


        public void setupButtons () {
            Button goToHomeBtn = this.findViewById(R.id.UserHomeButton);
            goToHomeBtn.setOnClickListener(view -> {
                Intent goToHome = new Intent(this, GameActivity.class);
                startActivity(goToHome);
            });

        }
    }

