package com.origin.cpf_standard.model;

public class ExceptionsCPF {
    private String field;
    private String mensage;

    public ExceptionsCPF(String field, String Mensage){
        this.field = field;
        this.mensage = Mensage;
    }

    public String getField(){
        return field;
    }

    public String getMensage(){
        return mensage;
    }
}
