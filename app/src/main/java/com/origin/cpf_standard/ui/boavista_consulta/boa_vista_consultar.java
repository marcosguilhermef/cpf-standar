package com.origin.cpf_standard.ui.boavista_consulta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.origin.cpf_standard.R;
import com.origin.cpf_standard.databinding.FragmentBoaVistaConsultarBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link boa_vista_consultar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class boa_vista_consultar extends Fragment {


    private FragmentBoaVistaConsultarBinding binding;


    private final String url = "https://painel.consumidorpositivo.com.br";
    private final String create = "https://painel.consumidorpositivo.com.br/cadastro";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public boa_vista_consultar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment boa_vista_consultar.
     */
    // TODO: Rename and change types and number of parameters
    public static boa_vista_consultar newInstance(String param1, String param2) {
        boa_vista_consultar fragment = new boa_vista_consultar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBoaVistaConsultarBinding.inflate(inflater,container,false);

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