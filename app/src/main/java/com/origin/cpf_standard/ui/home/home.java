package com.origin.cpf_standard.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.origin.cpf_standard.R;
import com.origin.cpf_standard.databinding.FragmentHomeBinding;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {
    private FragmentHomeBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =  FragmentHomeBinding.inflate(inflater,container, false);
        binding.consultarCpf.card.setOnClickListener(click("consultarCpf"));
        //binding.consultarCnpj.card.setOnClickListener(click("consultarCnpj"));
        binding.consultarHistorico.card.setOnClickListener(click("consultarHistorico"));
        binding.consultarScoreSerasa.card.setOnClickListener(click("consultarScoreSerasa"));
        binding.consultarBoaVista.card.setOnClickListener(click("consultar_boa_vista"));
        return binding.getRoot();
    }

    private View.OnClickListener click(String viewer){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (viewer){
                    case "consultarCpf": Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.navigation_consultar);
                    break;
                    case "consultarScoreSerasa": Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.navigation_consultar_serasa_formulario);
                    break;
                    case "consultarCnpj": Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.navigation_consultar_cnpj);
                    break;
                    case "consultarHistorico": Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.navigation_consultar_historico);
                    break;
                    case "consultar_boa_vista": Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.navigation_boa_vista);
                    break;
                }
            }
        };
    }

}