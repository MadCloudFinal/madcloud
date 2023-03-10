package com.adrianbutler.madcloud;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.madcloud.game.GameActivity;
import com.adrianbutler.madcloud.leaderboard.LeaderboardActivity;
import com.adrianbutler.madcloud.utils.api.GraphQLManager;
import com.adrianbutler.madcloud.utils.auth.SharedPreferencesManager;
import com.adrianbutler.madcloud.utils.sound.AudioPlay;
import com.adrianbutler.madcloud.utils.sound.SoundHelper;

public class MainActivity extends AppCompatActivity
{
	SoundHelper sfx;
	private SharedPreferencesManager sharedPreferencesManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		sfx = new SoundHelper(getApplicationContext());
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		AudioPlay.playAudio(getApplicationContext(),R.raw.thunder_roll);

		setContentView(R.layout.activity_main);

		// Get the shared preferences object
		sharedPreferencesManager = new SharedPreferencesManager(this);

		// Get the saved username from shared preferences
		String username = sharedPreferencesManager.getUsername();

		// If a username is saved in shared preferences, prefill the username input field with it
		EditText usernameInput = findViewById(R.id.username_input);
		if (username != null)
		{
			usernameInput.setText(username);
		}

		setupTitleButtons();

		ImageView ravenFly = (ImageView) findViewById(R.id.flyRaven);
		ravenFly.setBackgroundResource(R.drawable.fly);
		AnimationDrawable animation = (AnimationDrawable) ravenFly.getBackground();

		ravenFly.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				sfx.triggerSFX("raven");
			}
		});

		animation.start();

		SeekBar volumeSeekBar = findViewById(R.id.seekBarVol);
		volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
				int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
				float volume = (float) progress / (float) maxVolume;
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// Do nothing
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// Do nothing
			}
		});
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		if(AudioPlay.isplayingAudio){
			AudioPlay.stopAudio();
		}
	}

	public void setupTitleButtons()
	{
		EditText usernameInput = findViewById(R.id.username_input);
		TextView usernamePrompt = findViewById(R.id.username_prompt);
		TextView usernameDisplay = findViewById(R.id.username_display);
		Button goToGameBtn = findViewById(R.id.TitlePlayBtn);

		// If a username is saved in shared preferences, display it in place of the EditText and TextView widgets

		// Get the saved username from shared preferences
		final String[] usernameReference = {sharedPreferencesManager.getUsername()};

		// If a username is saved in shared preferences, display it in place of the EditText and TextView widgets
		if (usernameReference[0] != null)
		{
			usernameInput.setVisibility(View.GONE);
			usernamePrompt.setVisibility(View.GONE);
			usernameDisplay.setText(usernameReference[0]);
			usernameDisplay.setVisibility(View.VISIBLE);
			goToGameBtn.setEnabled(true);
		} else
		{
			// Otherwise, set an OnEditorActionListener on the EditText widget to save the inputted username to shared preferences
			usernameInput.setOnEditorActionListener((v, actionId, event) ->
			{
				if (actionId == EditorInfo.IME_ACTION_DONE)
				{
					usernameReference[0] = usernameInput.getText().toString();
					String username = usernameReference[0];
					String userId = GraphQLManager.createUser(username);
					if (userId == null) // user already exists in db
					{
						Toast usernameTakenToast = new Toast(this);
						usernameTakenToast.setText("Username is already taken");
						usernameTakenToast.show();
						return false;
					}

					sharedPreferencesManager.saveUser(username, userId);

					// Set the text of the username display TextView to the input from the EditText widget
					usernameDisplay.setText(usernameInput.getText().toString());
					// Make the username display TextView visible
					usernameDisplay.setVisibility(View.VISIBLE);
					// Make the username input EditText widget gone
					usernameInput.setVisibility(View.GONE);
					// Set TextView widget gone
					usernamePrompt.setVisibility(View.GONE);
					// Enable the play button
					goToGameBtn.setEnabled(true);

					// Save the inputted username to shared preferences

					return true;
				}
				return false;
			});
		}

		goToGameBtn.setOnClickListener(view ->
		{
			Intent goToGame = new Intent(this, GameActivity.class);
			sfx.triggerSFX("crow");
			startActivity(goToGame);
		});

		Button goToStatsBtn = findViewById(R.id.TitleStatsBtn);
		goToStatsBtn.setOnClickListener(view ->
		{
			Intent goToStats = new Intent(this, LeaderboardActivity.class);
			sfx.triggerSFX("magic");
			startActivity(goToStats);
		});

		Button quitBtn = findViewById(R.id.TitleQuitBtn);
		quitBtn.setOnClickListener(view ->
		{
			sfx.triggerSFX("quit");
			Toast.makeText(this, "Quitting!", Toast.LENGTH_SHORT).show();
			finishAndRemoveTask();
			System.exit(0);
		});
	}
}
