package com.salesianostriana.dam.realestatev2.models;

public enum Estado {
    COMPRA("COMPRA"),
    ALQUILER("ALQUILER"),
    COMPARTIR("COMPARTIR"),
    NUEVA_OBRA("NUEVA_OBRA");

    private String valor;

    private Estado(String valor){
        this.valor =valor;
    }
    public String getValor(){
        return valor;
    }
    public void setValor(String valor){
        this.valor =valor;
    }
}

