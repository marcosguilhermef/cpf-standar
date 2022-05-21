package com.origin.cpf_standard.ui.historico_consultar_cpf;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.ViewModel;

import com.origin.cpf_standard.model.CPF;
import com.origin.cpf_standard.model.Db;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class HistoricoConsultaViewModel extends ViewModel {

    static Context context;
    static Db instance;

    public HistoricoConsultaViewModel(Context context) {
        this.context = context;
    }

    public static Db getInstance(){
        if(instance == null){
            instance = Db.getInstance(context);
            return instance;
        }
        return instance;
    }
    public Flowable<List<CPF>> getCPF(){
        Flowable<List<CPF>> cpfs = getInstance().cpfdados().getAll();
        return cpfs;
    }

    static void delete(CPF contact){
        try{
            getInstance().cpfdados().delete(contact);
        }catch (SQLiteConstraintException e){
            e.printStackTrace();
        }
    }

}