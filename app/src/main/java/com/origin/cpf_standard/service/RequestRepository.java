package com.origin.cpf_standard.service;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.origin.cpf_standard.model.CPF;
import com.origin.cpf_standard.model.ExceptionsCPF;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestRepository {
    String captcha;
    OkHttpClient client;
    private final String xpath = "/html/body/div[2]/div[2]/div[1]/div/div/div/div[1]/form/div[2]/div[2]/div/div/div[1]/img";
    private final String paginaInicial = "/servicos/cpf/consultasituacao/ConsultaPublicaSonoro.asp";
    private final String postData = "/servicos/cpf/consultasituacao/ConsultaPublicaExibir.asp";
    private final String URL = "https://servicos.receita.fazenda.gov.br";
    private MutableLiveData<String> imageCaptcha;
    private MutableLiveData<ExceptionsCPF> exceptioncpf;
    private static int repeticoes = 0;

    public RequestRepository() {
        client = new OkHttpClient().newBuilder().cookieJar(new SessionCookieJar()).build();
    }

    public void load(
            final MutableLiveData<String> imageView,
            final MutableLiveData<ExceptionsCPF> exception) {

        this.imageCaptcha = imageView;
        this.exceptioncpf = exception;

        Request retrofit = new Request.Builder()
                .url(URL + paginaInicial)
                .build();

        client.newCall(retrofit).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ExceptionsCPF erro = new ExceptionsCPF(
                        "conexao",
                        "Erro ao se sistema ao Receita Federal. Por favor, " +
                                "verifique a sua internet ou tente novamente mais tarde");
                exception.postValue(erro);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                imageView.postValue(captchaImage(response.body().string()));
            }
        });

    }

    public String captchaImage(String body) {
        Document doc = Jsoup.parse(body);
        Element img = doc.select("img[id=imgCaptcha]").first();
        try{
            captcha = img.attr("src");
            return img.attr("src");
        }catch (Exception e){
            load(this.imageCaptcha,this.exceptioncpf);
            ExceptionsCPF erro = new ExceptionsCPF("input","Talvez você tenha inserido alguma informação incorreta. Por favor, tente novamente.");
            this.exceptioncpf.postValue(erro);
            return null;
        }
    }

    public void postData(
            final String cpf,
            final String nascimento,
            final String captcha,
            @Nullable final MutableLiveData<CPF> livedata,
            @Nullable final MutableLiveData<ExceptionsCPF> exception
            ) {

        Request retrofit = buildPost(
                cpf,
                nascimento,
                captcha);

        client.newCall(retrofit).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                ExceptionsCPF erro = new ExceptionsCPF(
                        "conexao",
                        "Erro ao se conectar ao sistema. Por favor, " +
                                "verifique a sua internet ou tente mais tarde");
                exception.postValue(erro);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                processResponsesucess(
                        response.body().string(),
                        livedata,
                        exception
                );
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



    private void processResponsesucess(
            String html,
            @Nullable final MutableLiveData<CPF> livedata,
            @Nullable final MutableLiveData<ExceptionsCPF> exception
    ) {
        Document doc = Jsoup.parse(html);
        Elements spans = doc.select("span[class=clConteudoDados]");

        if (spans.isEmpty()) {
            processResponseError(this.imageCaptcha,exception);
        } else {
            livedata.postValue(criarCPF(spans));
        }
    }

    private CPF criarCPF(Elements spans){
        CPF cpf = new CPF();
        cpf.setCpf(processCpf(spans.get(0)));
        cpf.setNome(processName(spans.get(1)));
        cpf.setNascimento(processNascimento(spans.get(2)));
        cpf.setSituacao(processSituacao(spans.get(3)));
        cpf.setInscricao(processInscricao(spans.get(4)));
        cpf.setCodigo(processCodigo(spans.get(5)));
        return cpf;
    }

    private void processResponseError(
            @Nullable final MutableLiveData<String> livedata,
            @Nullable final MutableLiveData<ExceptionsCPF> exception
    ){
        load(livedata, exception);
        ExceptionsCPF erro = new ExceptionsCPF("input","Talvez você tenha inserido alguma informação incorreta. Por favor, tente novamente.");
        this.exceptioncpf.postValue(erro);
    }

    private String processCpf(@NonNull Element element){
        return processTag(element,"b");
    }

    private String processName(@NonNull Element element){
        return processTag(element,"b");
    }

    private String processNascimento(@NonNull Element element){
        return processTag(element,"b");
    }

    private String processSituacao(@NonNull Element element){
        return processTag(element,"b");
    }
    private String processInscricao(@NonNull Element element){
        return processTag(element,"b");
    }
    private String processCodigo(@NonNull Element element){
        return processTag(element,"b");
    }

    private String processTag(@NonNull Element element, @NonNull String tag){
        return element.select(tag).text();
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
