package com.origin.cpf_standard.ui.consultar_cpf;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.origin.cpf_standard.model.CPF;
import com.origin.cpf_standard.model.ExceptionsCPF;

public class ConsultarCPFViewModel extends ViewModel {
    public MutableLiveData<String> captcha;
    public MutableLiveData<ExceptionsCPF> erro;
    public MutableLiveData<CPF> sucesso;

    public ConsultarCPFViewModel(){
        captcha = new MutableLiveData<>(null);
        erro = new MutableLiveData<>(null);
        sucesso = new MutableLiveData<>(null);
    }

    public MutableLiveData<String> getCaptcha(){
       return captcha;
    }
    public MutableLiveData<CPF> getSucesso(){
        return sucesso;
    }
    public MutableLiveData<ExceptionsCPF> getErro() { return erro; }
}
