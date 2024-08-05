package com.company;

public class Alimentaire extends Produit {
    private String dateExpiration;

    public Alimentaire(String id, String nom, String dateExpiration) {
        super(id, nom);
        this.dateExpiration = dateExpiration;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
}