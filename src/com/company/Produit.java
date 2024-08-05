package com.company;

import java.io.*;
import java.util.Map;

public class Produit implements Serializable {
    private String id;
    private String nom;

    public Produit(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public static void sauvegarderProduits(Map<String, Produit> produits, String nomFichier) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomFichier))) {
            oos.writeObject(produits);
        }
    }

    public static Map<String, Produit> chargerProduits(String nomFichier) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomFichier))) {
            return (Map<String, Produit>) ois.readObject();
        }
    }
}