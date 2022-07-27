package com.origin.cpf_standard.ui.InformacoesCPF;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.appodeal.ads.Appodeal;
//import com.appodeal.ads.InterstitialCallbacks;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.origin.cpf_standard.R;
import com.origin.cpf_standard.databinding.FragmentInformacoesCPFBinding;
import com.origin.cpf_standard.ui.adsFragment;

import java.util.Objects;

public class InformacoesCPF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private InterstitialAd mInterstitialAd;
    private static int counterClick = 0;
    private final static int PARSER = 1;
    private ActionBar actionBar;


    private static final String cpf = "cpf";
    private static final String nome = "nome";
    private static final String status = "status";
    private static final String nascimento = "nascimento";
    private static final String inscricao = "inscricao";
    private static final String verificador = "verificador";


    private String Pcpf;
    private String Pnome;
    private String Pstatus;
    private String Pnascimento;
    private String Pinscricao;
    private String Pverificador;

    private FragmentInformacoesCPFBinding binding;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Pcpf = getArguments().getString(cpf);
            Pnome = getArguments().getString(nome);
            Pstatus = getArguments().getString(status);
            Pnascimento = getArguments().getString(nascimento);
            Pinscricao = getArguments().getString(inscricao);
            Pverificador = getArguments().getString(verificador);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =  FragmentInformacoesCPFBinding.inflate(inflater,container, false);
        adsFragment.destroy();
        initADS();
        //initAppOdeal();

        binding.cpfField.setText(Pcpf);
        binding.nascimentoField.setText(Pnascimento);
        if("REGULAR".replace(" ","").equals(Pstatus)){
            binding.status.setBackgroundColor(getResources().getColor(R.color.green));
        }else{
            binding.status.setBackgroundColor(getResources().getColor(R.color.red));
        }
        binding.status.setText(Pstatus);
        binding.nomeField.setText(prepareName(Pnome));
        binding.dataInscricaoField.setText(Pinscricao);
        binding.verificadorField.setText(Pverificador);
        return binding.getRoot();
    }

    private String prepareName(String name){
        String newName = "";
        for(int i = 1; i <= name.length(); i++){
            if((i % 15) == 0){
                String ch = name.charAt(i-1) + "\n";
                newName += ch;
                Log.i("CHARACTER: ", newName);
            }else{
                String ch = String.valueOf(name.charAt(i-1));
                newName += ch;
                Log.i("CHARACTER: ", newName);
            }
        }
        return newName;
    }

    private void initADS(){
        fullscreeam();
        AdRequest adRequest = new AdRequest.Builder().build();

        if(mInterstitialAd != null || (counterClick % PARSER == 0) ){
            fullscreeam();
            InterstitialAd.load(getContext(), getString(R.string.intersticial_ad_unit_id), adRequest, interticialCallBack());
            count();
        }else{
            binding.progressCircular.setVisibility(View.GONE);
            count();
        }
    }
    private void count(){
        counterClick++;
    }

    private InterstitialAdLoadCallback interticialCallBack(){
        return new InterstitialAdLoadCallback(){
            public void onAdFailedToLoad( LoadAdError loadAdError) {
                binding.progressCircular.setVisibility(View.GONE);
                mInterstitialAd = null;
                showscream();
                hideProgressBar();
            }

            public void onAdLoaded(InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                mInterstitialAd.show(getActivity());
                mInterstitialAd.setFullScreenContentCallback(fullScreeeCallBack());
            }
        };
    }

    private FullScreenContentCallback fullScreeeCallBack(){
        return new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                binding.progressCircular.setVisibility(View.GONE);
                hideProgressBar();
                showscream();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
                binding.progressCircular.setVisibility(View.GONE);
                hideProgressBar();
                showscream();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                binding.progressCircular.setVisibility(View.GONE);
                hideProgressBar();
                showscream();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
                binding.progressCircular.setVisibility(View.GONE);
                showscream();
            }
        };
    }

    /*private void initAppOdeal(){
        Log.w("Appodeal", "initAppOdeal");
        fullscreeam();

        if(!Appodeal.isInitialized(Appodeal.INTERSTITIAL)){
            Log.w("Appodeal", "Appodeal.initialize");
        }else{
            Log.w("Appodeal", "else");
            showProgressBar();
            fullscreeam();
        }

        if(Appodeal.isLoaded(Appodeal.INTERSTITIAL)){
            Appodeal.show(getActivity(), Appodeal.INTERSTITIAL);
        }

        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            @Override
            public void onInterstitialLoaded(boolean isPrecache) {
                hideProgressBar();
                showscream();
            }
            @Override
            public void onInterstitialFailedToLoad() {
                Log.w("Appodeal", "onInterstitialFailedToLoad");
                hideProgressBar();
                showscream();
            }
            @Override
            public void onInterstitialShown() {
                hideProgressBar();
            }
            @Override
            public void onInterstitialShowFailed() {
                hideProgressBar();
            }
            @Override
            public void onInterstitialClicked() {
                hideProgressBar();
            }
            @Override
            public void onInterstitialClosed() {
                hideProgressBar();
            }
            @Override
            public void onInterstitialExpired()  {
                hideProgressBar();
            }
        });

    }*/

    private void showscream(){
        ((AppCompatActivity) requireActivity()).getSupportActionBar().show();

    }

    private void hideProgressBar(){
        binding.progressCircular.setVisibility(View.GONE);
        adsFragment.call();
    }

    private void showProgressBar(){
        binding.progressCircular.setVisibility(View.VISIBLE);
    }

    private void fullscreeam(){
        ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();
    }
}