package com.adrianbutler.madcloud.auth;

import static android.content.SharedPreferences.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesManager
{
	private static final String USERNAME_TAG = "username";
	private static final String USER_ID_TAG = "userId";
	private static final String USER_HIGH_SCORE_TAG = "highScore";

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
	}

	public void saveUserHighScore(int highScore)
	{
		Editor preferencesEditor = sharedPreferences.edit();

		preferencesEditor.putInt(USER_HIGH_SCORE_TAG, highScore);

		preferencesEditor.apply();
	}

	public String getUsername()
	{
		return sharedPreferences.getString(USERNAME_TAG, null);
	}

	public String getUserId()
	{
		return sharedPreferences.getString(USER_ID_TAG, null);
	}

	public int getUserHighScore()
	{
		return sharedPreferences.getInt(USER_HIGH_SCORE_TAG, 0);
	}

}
