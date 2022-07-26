package com.origin.cpf_standard.utils;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdView;

import org.jetbrains.annotations.NotNull;

public interface AdMobService {
    static void init() {
    }

    void bannerAds(@NotNull String unit,
                   @NonNull AdView adResource);

    void initializationAds(@NotNull String unit);

    void intersticialAds(@NotNull String unit);

    void destroy(@NonNull AdView adResource);
}
