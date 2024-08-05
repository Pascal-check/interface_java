package com.company;

public class Electronique extends Produit {
    private String marque;

    public Electronique(String id, String nom, String marque) {
        super(id, nom);
        this.marque = marque;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }
}