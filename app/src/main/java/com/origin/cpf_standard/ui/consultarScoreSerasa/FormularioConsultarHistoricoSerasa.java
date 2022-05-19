package com.origin.cpf_standard.ui.consultarScoreSerasa;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.origin.cpf_standard.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormularioConsultarHistoricoSerasa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormularioConsultarHistoricoSerasa extends Fragment {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_formulario_consultar_score_serasa, container, false);
    }

}