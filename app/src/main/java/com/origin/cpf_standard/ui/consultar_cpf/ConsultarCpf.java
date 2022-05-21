package com.origin.cpf_standard.ui.consultar_cpf;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.origin.cpf_standard.R;
import com.origin.cpf_standard.databinding.FragmentConsultarCpfBinding;
import com.origin.cpf_standard.model.CPF;
import com.origin.cpf_standard.model.Db;
import com.origin.cpf_standard.model.ExceptionsCPF;
import com.origin.cpf_standard.service.RequestRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.origin.cpf_standard.until.ads;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsultarCpf#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultarCpf extends Fragment {
    private FragmentConsultarCpfBinding binding;
    private ConsultarCPFViewModel ViewModel;
    private RequestRepository request;
    private InterstitialAd mInterstitialAd;

    public ConsultarCpf() {
        // Required empty public constructor
    }


    public static ConsultarCpf newInstance() {
        ConsultarCpf fragment = new ConsultarCpf();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModel = new ViewModelProvider(this).get(ConsultarCPFViewModel.class);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =  FragmentConsultarCpfBinding.inflate(inflater,container, false);
        //ads.initADS(getContext(),fullScreeeCallBack());
        //ads.getAds().show(getActivity());
        formatCpf();
        formatNascimento();
        load();

        ViewModel.getCaptcha().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@NotNull String ImagteString) {
                if (ImagteString != null) {
                    String removeBASE64 = ImagteString.replace("data:image/png;base64", "");
                    byte[] decodedString = Base64.decode(removeBASE64, Base64.DEFAULT);
                    Bitmap bitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    binding.captchaImage.setImageBitmap(bitMap);
                }
            }
        });

        ViewModel.getSucesso().observe(getViewLifecycleOwner(), new Observer<CPF>() {
            @Override
            public void onChanged(CPF cpf) {
                if(cpf != null){
                    save(cpf);
                    Bundle bundle = new Bundle();
                    bundle.putString("cpf",cpf.getCpf());
                    bundle.putString("nome",cpf.getNome());
                    bundle.putString("status",cpf.getSituacao());
                    bundle.putString("nascimento",cpf.getNascimento());
                    bundle.putString("inscricao",cpf.getInscricao());
                    bundle.putString("verificador",cpf.getDgVerificador());
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.navigation_informacoesCPF, bundle);
                }
            }
            public void save(CPF Valuescpf){
                Db instance = Db.getInstance(getContext());
                CPF cpf = new CPF();
                cpf.setCpf(Valuescpf.getCpf());
                cpf.setNome(Valuescpf.getNome());
                cpf.setSituacao(Valuescpf.getSituacao());
                cpf.setNascimento(Valuescpf.getNascimento());
                cpf.setInscricao(Valuescpf.getInscricao());
                instance.cpfdados().insert(cpf);
            }
        });

        ViewModel.getErro().observe(getViewLifecycleOwner(), new Observer<ExceptionsCPF>() {
            @Override
            public void onChanged(ExceptionsCPF exceptionsCPF) {
                if(exceptionsCPF != null){
                    switch (exceptionsCPF.getField()){
                        case "conexao": conexao(exceptionsCPF);
                        break;
                        case "input": input(exceptionsCPF);
                        break;
                    }
                }

            }

            public void conexao(ExceptionsCPF exceptionsCPF){
                    new MaterialAlertDialogBuilder(getContext(),R.style.MaterialAlertDialog_MaterialComponents)
                            .setMessage(exceptionsCPF.getMensage())
                            .setNegativeButton("Sair", (d,v) -> d.cancel())
                            .setPositiveButton("Tentar de novo", (d,v) -> load())
                            .show();

            }

            public void input(ExceptionsCPF exceptionsCPF){
                new MaterialAlertDialogBuilder(getContext(),R.style.MaterialAlertDialog_MaterialComponents)
                        .setMessage(exceptionsCPF.getMensage())
                        .setNegativeButton("Ok", (d,v) -> d.cancel())
                        .show();

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        binding.button.setOnClickListener( (v) ->{
            request.postData(
                    binding.cpfText.getText().toString(),
                    binding.nascimentoText.getText().toString(),
                    binding.captchaText.getText().toString(),
                    ViewModel.getSucesso(),
                    ViewModel.getErro()
            );
        });
    }

    private FullScreenContentCallback fullScreeeCallBack(){
        return new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
            }
        };
    }

    private void load(){
        request = new RequestRepository();
        request.load(ViewModel.getCaptcha(), ViewModel.getErro());
    }

    private void formatCpf() {
        EditText editText = binding.cpfText;
        SimpleMaskFormatter mask = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(editText, mask);
        editText.addTextChangedListener(mtw);
    }
    private void formatNascimento(){
        EditText editText = binding.nascimentoText;
        SimpleMaskFormatter mask = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(editText, mask);
        editText.addTextChangedListener(mtw);
    }
    @Override
    public void onStop() {
        super.onStop();
        ViewModel.getSucesso().setValue(null);
        ViewModel.getErro().setValue(null);
        ViewModel.getCaptcha().setValue(null);
    }
}