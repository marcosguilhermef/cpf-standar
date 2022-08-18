package com.origin.cpf_standard.model;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Entity
public class CPF {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String cpf;
    private String nome;
    private String nascimento;
    private String situacao;
    private String inscricao;
    private String dgVerificador;
    private String comprovante;
    private String codigo;
    private String created_ad;

    public CPF(){
        generateCreated_ad();
    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public String getDgVerificador() {
        return dgVerificador;
    }

    public void setDgVerificador(String dgVerificador) {
        this.dgVerificador = dgVerificador;
    }

    public String getComprovante() {
        return comprovante;
    }

    public void setComprovante(String comprovante) {
        this.comprovante = comprovante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public String getCreated_ad() {
        return created_ad;
    }

    public void setCreated_ad(String created_ad) {
        this.created_ad = created_ad;
    }

    public void generateCreated_ad() {
        SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy 'as' HH:mm");
        Date date = new Date(System.currentTimeMillis());
        setCreated_ad(formatter.format(date));
    }
}
