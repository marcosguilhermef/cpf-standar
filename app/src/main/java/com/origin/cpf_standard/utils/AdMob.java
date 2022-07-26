package com.origin.cpf_standard.utils;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.jetbrains.annotations.NotNull;

public class AdMob implements AdMobService {
    private AdView adView;
    private Activity activity;
    private InterstitialAd mInterstitialAd;
    private AppOpenAd appOpenAd = null;

    public AdMob(FragmentActivity activity) {
        this.activity = activity;
    }

    public AdMob(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void bannerAds(@NotNull String unit, @NonNull AdView adResource) {
        try {
            adResource.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder().build();
            adResource.loadAd(adRequest);
        } catch (Exception e) {
            Log.i("FALHA:", "------------------------------------------------------------------------------------------------------------------------");
            e.printStackTrace();
        }

    }

    @Override
    public void initializationAds(@NotNull String unit) {
        final AppOpenAd appOpenAd = null;
        AdRequest adRequest = new AdRequest.Builder().build();
        AppOpenAd.AppOpenAdLoadCallback loadCallback = new  AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(AppOpenAd ad) {
                AdMob.this.appOpenAd = ad;
            }
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.i("ERRO","----------------------------------------------------------------------------------------");
                Log.i("ERRO", String.valueOf(loadAdError.getCause()));
                Log.i("ERRO",loadAdError.getMessage());
            }
        };
        AdRequest request = adRequest;
        AppOpenAd.load(activity, unit, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
        //this.appOpenAd.show(activity);
    }


    @Override
    public void intersticialAds(@NotNull String unit) {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity, unit, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.d("Ads TAG", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Log.d("Ads TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {

                                mInterstitialAd = null;
                                Log.d("Ads TAG", "The ad was shown.");
                            }
                        });
                        mInterstitialAd.show(activity);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("Ads", "mInterstitialAd " + loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

    public void destroy(@NonNull AdView adResource) {
        try {
            adResource.destroy();
            adResource.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
