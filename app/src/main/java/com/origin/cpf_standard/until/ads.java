package com.origin.cpf_standard.until;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.origin.cpf_standard.R;

public class ads {
    private static InterstitialAd mInterstitialAd;
    private static int counterClick = 0;
    private final static int PARSER = 3;
    public static void initADS(Context context, InterstitialAdLoadCallback interticialCallBack){
        AdRequest adRequest = new AdRequest.Builder().build();
        if(mInterstitialAd != null & (counterClick % PARSER == 0) ){
            InterstitialAd.load(context, context.getString(R.string.banner_ad_unit_id_intersticial), adRequest, interticialCallBack);
            count();
        }
    }


    private final static void count(){
        counterClick++;
    }

    public static InterstitialAd getAds(){
        return mInterstitialAd;
    }



}
