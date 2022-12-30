package com.adrianbutler.madcloud.utils.auth;

import static android.content.SharedPreferences.Editor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class SharedPreferencesManager
{
	private static final String USERNAME_TAG = "username";
	private static final String USER_ID_TAG = "userId";
	private static final String USER_HIGH_SCORE_TAG = "highScore";

	private static final String TAG = "SharedPreferencesManager";

	private final SharedPreferences sharedPreferences;

	public SharedPreferencesManager(Context context)
	{
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public void saveUser(String username, String userId)
	{
		Editor preferencesEditor = sharedPreferences.edit();

		preferencesEditor.putString(USERNAME_TAG, username);
		preferencesEditor.putString(USER_ID_TAG, userId);

		preferencesEditor.apply();

		Log.i(TAG, "Saved user to shared preferences");
	}

	public void saveUserHighScore(int highScore)
	{
		Editor preferencesEditor = sharedPreferences.edit();

		preferencesEditor.putInt(USER_HIGH_SCORE_TAG, highScore);

		preferencesEditor.apply();

		Log.i(TAG, "Saved users high score to shared preferences");
	}

	public String getUsername()
	{
		Log.i(TAG, "Retrieving username from shared preferences");

		return sharedPreferences.getString(USERNAME_TAG, null);
	}

	public String getUserId()
	{
		Log.i(TAG, "Retrieving user id from shared preferences");

		return sharedPreferences.getString(USER_ID_TAG, null);
	}

	public int getUserHighScore()
	{
		Log.i(TAG, "Retrieving user high score from shared preferences");

		return sharedPreferences.getInt(USER_HIGH_SCORE_TAG, 0);
	}

}
