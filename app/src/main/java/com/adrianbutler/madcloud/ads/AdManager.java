package com.adrianbutler.madcloud.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdManager
{
	private static final String TAG = "AdManager";

	//TODO implement the ability to change these
	private static final String INTERSTITIAL_TEST_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"; // use this while testing
	private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-4107669880849603/7026078398"; // ONLY USE IN PRODUCTION!
	private final String CURRENTLY_USED_INTERSTITIAL_AD_UNIT_ID;

	private static final String REWARDED_TEST_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"; // use while testing
	private static final String REWARDED_AD_UNIT_ID = "ca-app-pub-4107669880849603/8393633401"; // ONLY USE IN PRODUCTION!
	private final String CURRENTLY_USED_REWARDED_AD_UNIT_ID;

	private final Context context;

	private InterstitialAd interstitialAd;
	private RewardedAd rewardedAd;

	public AdManager(Context context, boolean inProduction)
	{
		this.context = context;

		if (inProduction) // if app is in inProduction use product ad unit ids
		{
			Log.i(TAG, "Using inProduction ad unit ids");

			CURRENTLY_USED_INTERSTITIAL_AD_UNIT_ID = INTERSTITIAL_AD_UNIT_ID;
			CURRENTLY_USED_REWARDED_AD_UNIT_ID = REWARDED_AD_UNIT_ID;
		}
		else
		{
			Log.i(TAG, "Using testing ad unit ids");

			CURRENTLY_USED_INTERSTITIAL_AD_UNIT_ID = INTERSTITIAL_TEST_AD_UNIT_ID;
			CURRENTLY_USED_REWARDED_AD_UNIT_ID = REWARDED_TEST_AD_UNIT_ID;
		}

		MobileAds.initialize(context);

		Log.i(TAG, "Created AdManager successfully");
	}

	public void loadRewardedAd(Runnable adLoadCallback)
	{
		Log.i(TAG, "Attempting to load rewarded ad");

		AdRequest adRequest = new AdRequest.Builder().build();
		RewardedAd.load(context, CURRENTLY_USED_REWARDED_AD_UNIT_ID, adRequest, new RewardedAdLoadCallback()
		{
			@Override
			public void onAdLoaded(@NonNull RewardedAd rewardedAd)
			{
				super.onAdLoaded(rewardedAd);
				setRewardedAd(rewardedAd);

				Log.i(TAG, "Loaded interstitial ad successfully");

				adLoadCallback.run();
			}

			@Override
			public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
			{
				super.onAdFailedToLoad(loadAdError);

				Log.w(TAG, "Failed to load rewarded ad: " + loadAdError);
			}
		});
	}

	public void showRewardedAd(Activity activity, Runnable userEarnedRewardCallback)
	{
		if (rewardedAd != null)
		{
			Log.i(TAG, "Showing rewarded ad");
			rewardedAd.show(activity, rewardItem ->
			{
				Log.i(TAG, "User finished the rewarded ad");
				userEarnedRewardCallback.run();
			});
		} else
		{
			Log.d(TAG, "Attempted to show rewarded ad but ad was not loaded");
		}
	}

	private void setRewardedAd(RewardedAd rewardedAd)
	{
		rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback()
		{
			@Override
			public void onAdDismissedFullScreenContent()
			{
				super.onAdDismissedFullScreenContent();
				clearRewardedAd();
			}
		});

		this.rewardedAd = rewardedAd;
	}

	private void clearRewardedAd()
	{
		rewardedAd = null;
		Log.i(TAG, "Cleared rewarded ad");
	}

	public void loadInterstitialAd(Runnable adLoadCallback)
	{
		Log.i(TAG, "Attempting to load interstitial ad");

		AdRequest adRequest = new AdRequest.Builder().build();

		InterstitialAd.load(context, CURRENTLY_USED_INTERSTITIAL_AD_UNIT_ID, adRequest, new InterstitialAdLoadCallback()
		{
			@Override
			public void onAdLoaded(@NonNull InterstitialAd interstitialAd)
			{
				super.onAdLoaded(interstitialAd);
				setInterstitialAd(interstitialAd);

				Log.i(TAG, "Loaded interstitial ad successfully");

				adLoadCallback.run();
			}

			@Override
			public void onAdFailedToLoad(@NonNull LoadAdError loadAdError)
			{
				super.onAdFailedToLoad(loadAdError);

				Log.w(TAG, "Failed to load interstitial ad: " + loadAdError);
			}
		});
	}

	public void showInterstitialAd(Activity activity)
	{
		if (interstitialAd != null)
		{
			Log.i(TAG, "Showing interstitial ad");
			interstitialAd.show(activity);
		} else
		{
			Log.d(TAG, "Attempted to show interstitial ad but ad was not loaded");
		}
	}

	private void setInterstitialAd(InterstitialAd interstitialAd)
	{
		interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback()
		{
			@Override
			public void onAdDismissedFullScreenContent()
			{
				super.onAdDismissedFullScreenContent();
				clearInterstitialAd();
			}
		});

		this.interstitialAd = interstitialAd;
	}

	private void clearInterstitialAd()
	{
		interstitialAd = null;
		Log.i(TAG, "Clearing interstitial ad");
	}


}
