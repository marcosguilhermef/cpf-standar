package com.origin.cpf_standard.ui.consultar_cpf;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.origin.cpf_standard.databinding.FragmentConsultarCpfBinding;
import com.origin.cpf_standard.service.RequestRepository;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsultarCpf#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultarCpf extends Fragment {
    private FragmentConsultarCpfBinding binding;
    private ConsultarCPFViewModel ViewModel;
    private RequestRepository request;
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
        formatCpf();
        formatNascimento();
        load();

        ViewModel.getCaptcha().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String ImagteString) {
                if (ImagteString != null) {
                    String removeBASE64 = ImagteString.replace("data:image/png;base64", "");
                    byte[] decodedString = Base64.decode(removeBASE64, Base64.DEFAULT);
                    Bitmap bitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    binding.captchaImage.setImageBitmap(bitMap);
                }
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
}