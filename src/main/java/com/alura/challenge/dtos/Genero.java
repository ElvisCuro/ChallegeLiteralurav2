package com.alura.challenge.dtos;

public enum Genero {

    ACCION ("Action"),
    AVENTURA ("Adventure"),
    CRIMEN ("Crime"),
    COMEDIA ("Comedy"),
    DRAMA ("Drama"),
    DESCONOCIDO ("Desconocido"),
    FICCION ("Fiction"),
    ROMANCE ("Romance");


    private String genero;

    Genero(String generoGutendex) {
        this.genero = generoGutendex;
    }

    public static Genero fromString(String text){
        for (Genero generoEnum: Genero.values()){
            if (generoEnum.genero.equals(text)){
                return generoEnum;
            }
        }
        return Genero.DESCONOCIDO;
    }

}