package com.origin.cpf_standard.ui.historico_consultar_cpf;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.navigation.Navigation;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.origin.cpf_standard.R;
import com.origin.cpf_standard.model.CPF;

import java.util.List;

public class CpfsAdapter extends BaseAdapter {

    private List<CPF> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private Holder holder;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Activity activity;


    public CpfsAdapter(Activity activity, Context aContext, List<CPF> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        this.activity = activity;
        //mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public CPF getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup   ) {
        view = layoutInflater.inflate(R.layout.cpf, null);
        holder = new Holder();
        view.findViewById(R.id.card).setOnClickListener( (v) -> {
            CPF cpf = getItem(i);
            Toast.makeText(context, cpf.getCpf(), Toast.LENGTH_LONG).show();
            Bundle bundle = new Bundle();
            bundle.putString("cpf",cpf.getCpf());
            bundle.putString("nome",cpf.getNome());
            bundle.putString("status",cpf.getSituacao());
            bundle.putString("nascimento",cpf.getNascimento());
            bundle.putString("inscricao",cpf.getInscricao());
            bundle.putString("verificador",cpf.getDgVerificador());
            Navigation.findNavController(this.activity, R.id.nav_host_fragment).navigate(R.id.navigation_informacoesCPF, bundle);
        });
        TextView cpf = (TextView) view.findViewById(R.id.title_cpf);
        TextView data = (TextView) view.findViewById(R.id.text_description);
        cpf.setText(getItem(i).getCpf());
        data.setText(getItem(i).getCreated_ad());
        return view;
    }


    class Holder {
        TextView cpf;
        TextView data;
        long id;
    }

}
