package com.salesianostriana.dam.realestatev2.models;

public enum Tipo {
    CASA("CASA"),
    PISO("PISO");

    private String valor;

    private Tipo(String valor){
        this.valor = valor;
    }
    public String getValor(){
        return valor;
    }
    public void setValor(String valor){
        this.valor = valor;
    }
}

