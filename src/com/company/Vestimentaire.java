package com.company;

public class Vestimentaire extends Produit {
    private String taille;

    public Vestimentaire(String id, String nom, String taille) {
        super(id, nom);
        this.taille = taille;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }
}