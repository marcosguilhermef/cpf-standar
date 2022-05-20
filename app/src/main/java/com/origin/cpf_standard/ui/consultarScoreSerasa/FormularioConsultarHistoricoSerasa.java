package com.origin.cpf_standard.ui.consultarScoreSerasa;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.origin.cpf_standard.R;
import com.origin.cpf_standard.databinding.FragmentFormularioConsultarScoreSerasaBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormularioConsultarHistoricoSerasa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormularioConsultarHistoricoSerasa extends Fragment {

    private FragmentFormularioConsultarScoreSerasaBinding binding;
    private final String url = "https://www.serasa.com.br/entrar?product=portal&redirectUrl=%2Fmeu-perfil";
    private final String create = "https://www.serasa.com.br/cadastrar?product=portal&redirectUrl=%2Fmeu-perfil";
    public FormularioConsultarHistoricoSerasa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment formulario_consultar_score_serasa.
     */
    // TODO: Rename and change types and number of parameters
    public static FormularioConsultarHistoricoSerasa newInstance(String param1, String param2) {
        FormularioConsultarHistoricoSerasa fragment = new FormularioConsultarHistoricoSerasa();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =  FragmentFormularioConsultarScoreSerasaBinding.inflate(inflater,container, false);
        binding.botao1.setOnClickListener( (v) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);

        });
        binding.botao2.setOnClickListener( (v) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(create));
            startActivity(browserIntent);
        });
        return binding.getRoot();
    }

}