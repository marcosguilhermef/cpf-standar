package com.origin.cpf_standard.ui.historico_consultar_cpf;

import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.origin.cpf_standard.R;
import com.origin.cpf_standard.databinding.FragmentHistoricoConsultaCpfBinding;
import com.origin.cpf_standard.model.CPF;
import com.origin.cpf_standard.ui.historico_consultar_cpf.HistoricoConsultaViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HistoricoConsultaCpf extends Fragment {

    private FragmentHistoricoConsultaCpfBinding binding;
    private HistoricoConsultaViewModel viewModel;

    public HistoricoConsultaCpf() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoricoConsultaCpfBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this, new ViewModelObjectFactory(getContext())).get(HistoricoConsultaViewModel.class);

        ListView listView = binding.listView;

        viewModel.getCPF()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((e) -> {
                    setAdapter(e);
                }, (e) -> {
                    e.printStackTrace();
                });
        return binding.getRoot();
    }



    public void setAdapter(List<CPF> e) {
        binding.listView.setAdapter(new CpfsAdapter(getActivity(),getContext(), e));
    }
}