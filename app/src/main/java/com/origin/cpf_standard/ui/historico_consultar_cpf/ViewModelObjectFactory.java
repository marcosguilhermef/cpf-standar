package com.origin.cpf_standard.ui.historico_consultar_cpf;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelObjectFactory implements ViewModelProvider.Factory{
    private Context context;
    public ViewModelObjectFactory(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new HistoricoConsultaViewModel(this.context);
    }
}
