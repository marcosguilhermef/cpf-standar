package com.origin.cpf_standard.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.origin.cpf_standard.R;
import com.origin.cpf_standard.databinding.FragmentAdsBinding;
import com.origin.cpf_standard.utils.AdMob;

public class adsFragment extends Fragment {
    public static AdMob ads;
    private static FragmentAdsBinding binding;
    private static String UNIT;


    public adsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UNIT =  getString(R.string.banner_ad_unit_id) ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ads = new AdMob(getActivity());
        ads.bannerAds(UNIT, binding.adViewF);
        return root;
    }

    public static void destroy(){
        if(ads != null){
            ads.destroy( binding.adViewF );
        }
    }

    public static void call(){
        if(ads != null){
            ads.bannerAds(UNIT, binding.adViewF);
        }
    }
}