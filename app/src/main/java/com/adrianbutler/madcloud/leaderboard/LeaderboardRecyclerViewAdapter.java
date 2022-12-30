package com.adrianbutler.madcloud.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adrianbutler.madcloud.R;
import com.amplifyframework.datastore.generated.model.User;

import java.util.List;


public class LeaderboardRecyclerViewAdapter extends RecyclerView.Adapter<LeaderboardRecyclerViewAdapter.ScoreViewHolder>
{
	private final List<User> users;

	public LeaderboardRecyclerViewAdapter(List<User> users)
	{
		this.users = users;
	}

	@NonNull
	@Override
	public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View userCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_card, parent, false);
		return new ScoreViewHolder(userCard);
	}

	@Override
	public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position)
	{
		User user = users.get(position);
		holder.nameTextView.setText(user.getUsername());
		holder.scoreTextView.setText(user.getHighScore().toString());
	}

	@Override
	public int getItemCount()
	{
		return users.size();
	}

	protected static class ScoreViewHolder extends RecyclerView.ViewHolder
	{
		TextView nameTextView;
		TextView scoreTextView;

		public ScoreViewHolder(@NonNull View itemView)
		{
			super(itemView);
			nameTextView = itemView.findViewById(R.id.LeaderboardCardNameView);
			scoreTextView = itemView.findViewById(R.id.LeaderboardCardScoreView);
		}
	}
}
