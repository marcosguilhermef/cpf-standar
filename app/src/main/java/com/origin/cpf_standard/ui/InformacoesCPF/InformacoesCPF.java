package com.origin.cpf_standard.ui.InformacoesCPF;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.origin.cpf_standard.R;
import com.origin.cpf_standard.databinding.FragmentInformacoesCPFBinding;

public class InformacoesCPF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
}