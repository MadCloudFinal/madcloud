package com.adrianbutler.madcloud.leaderboard;

import android.content.pm.ActivityInfo;
import android.graphics.Outline;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adrianbutler.madcloud.R;
import com.adrianbutler.madcloud.utils.api.GraphQLManager;
import com.adrianbutler.madcloud.utils.sound.AudioPlay;
import com.amplifyframework.datastore.generated.model.User;

import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_leaderboard);

//		LinearLayout ll = (LinearLayout) findViewById(R.id.LeaderboardCardLinearLayout);
//		ll.setAlpha((float)0.4);
        setupRecyclerView();
        ImageView ravenFly = (ImageView) findViewById(R.id.flyRaven);
        ravenFly.setBackgroundResource(R.drawable.fly);
        AnimationDrawable animation = (AnimationDrawable) ravenFly.getBackground();
        animation.start();
        AudioPlay.playAudio(getApplicationContext(), R.raw.triumph);
    }

    protected void onPause() {
        super.onPause();
        AudioPlay.stopAudio();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.LeaderboardRecyclerView);
        // give recycler view rounded borders

        recyclerView.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                final int radius = 12;
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
            }
        });

        recyclerView.setClipToOutline(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<User> users = GraphQLManager.getUsersByDescendingScore();
        LeaderboardRecyclerViewAdapter adapter = new LeaderboardRecyclerViewAdapter(users);
        recyclerView.setAdapter(adapter);
    }
}