package com.adrianbutler.madcloud.leaderboard;

import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adrianbutler.madcloud.R;
import com.adrianbutler.madcloud.utils.api.GraphQLManager;
import com.amplifyframework.datastore.generated.model.User;

import java.util.List;

public class LeaderboardActivity extends AppCompatActivity
{
	private RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);
		setupRecyclerView();
	}

	private void setupRecyclerView()
	{
		recyclerView = findViewById(R.id.LeaderboardRecyclerView);
		// give recycler view rounded borders

		recyclerView.setOutlineProvider(new ViewOutlineProvider()
		{
			@Override
			public void getOutline(View view, Outline outline)
			{
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