package com.adrianbutler.madcloud;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;

public class MadCloudAmplifyApplication extends Application
{
	public static final String TAG = "MadCloudAmplifyApplication";
	@Override
	public void onCreate()
	{
		super.onCreate();

		try
		{
			Amplify.addPlugin(new AWSApiPlugin());
			Amplify.configure(getApplicationContext());
			Log.i(TAG, "Initialized Amplify successfully");
		}
		catch (AmplifyException amplifyException)
		{
			Log.e(TAG, "Failed to initialize Amplify: " + amplifyException);
		}
	}
}
