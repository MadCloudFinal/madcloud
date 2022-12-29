package com.adrianbutler.madcloud.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.amplifyframework.datastore.generated.model.User;

public class SharedPreferencesManager
{
	private final Context context;
	private final SharedPreferences sharedPreferences;

	public SharedPreferencesManager(Context context)
	{
		this.context = context;
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public void saveUser(String username, String userId)
	{

	}

	public void saveUserHighScore(int highScore)
	{

	}

	public String getUsername()
	{
	}

	public String getUserId()
	{

	}

	public int getUserHighScore()
	{

	}




}
