package com.origin.cpf_standard.service;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.origin.cpf_standard.model.ExceptionsCPF;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestRepository {
    private Request retrofit;
    String body;
    String captcha;
    private final String xpath = "/html/body/div[2]/div[2]/div[1]/div/div/div/div[1]/form/div[2]/div[2]/div/div/div[1]/img";
    private final String paginaInicial = "/servicos/cpf/consultasituacao/ConsultaPublicaSonoro.asp";
    private final String postData = "/servicos/cpf/consultasituacao/ConsultaPublicaExibir.asp";
    OkHttpClient client;
    private final String URL = "https://servicos.receita.fazenda.gov.br";

    public RequestRepository() {
        client = new OkHttpClient().newBuilder().cookieJar(new SessionCookieJar()).build();

        retrofit = new Request.Builder()
                .url(URL + paginaInicial)
                .build();
    }

    public void load(final MutableLiveData<String> success,final MutableLiveData<ExceptionsCPF> exception) {
        client.newCall(retrofit).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ExceptionsCPF erro = new ExceptionsCPF(
                        "conexao",
                        "Erro ao se conectar ao site. Por favor, " +
                                "verifique a sua internet ou tente mais tarde");
                exception.postValue(erro);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                success.postValue(captchaImage(response.body().string()));
            }
        });

    }

    public String captchaImage(String body) {
        Document doc = Jsoup.parse(body);
        Element img = doc.select("img[id=imgCaptcha]").first();
        captcha = img.attr("src");
        return img.attr("src");
    }

    public void postData(
            final String cpf,
            final String nascimento,
            final String captcha,
            @Nullable final MutableLiveData<String> livedata,
            @Nullable final MutableLiveData<ExceptionsCPF> exception
            ) {


        retrofit = buildPost(
                cpf,
                nascimento,
                captcha);

        client.newCall(retrofit).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ExceptionsCPF erro = new ExceptionsCPF(
                        "conexao",
                        "Erro ao se conectar ao site. Por favor, " +
                                "verifique a sua internet ou tente mais tarde");
                exception.postValue(erro);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Log.i("SUCESSO", response.body().string());
            }
        });
    }

    private Request buildPost(
            @NonNull String cpf,
            @NonNull String nascimento,
            @Nullable String captcha){
        return new Request.Builder()
                .url(URL + postData)
                .post(mountBody(cpf,nascimento,captcha))
                .build();
    }

    private RequestBody mountBody(
            @NonNull String cpf,
            @NonNull String nascimento,
            @Nullable String captcha
    ){
        return new FormBody.Builder()
                .add("idCheckedReCaptcha", "false")
                .add("txtCPF", cpf)
                .add("txtDataNascimento", nascimento)
                .add("CPF", "")
                .add("NASCIMENTO", "")
                .add("txtTexto_captcha_serpro_gov_br", captcha)
                .add("Enviar", "Consultar")
                .build();
    }



    private void processResponse(
            String html

    ){

    }


    private static class SessionCookieJar implements CookieJar {

        private List<Cookie> cookies;

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            this.cookies = new ArrayList<>(cookies);
        }


        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (cookies != null) {
                return cookies;
            }
            return Collections.emptyList();
        }
    }

}
