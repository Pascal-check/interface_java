package com.company;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class Magasin {
    private Map<String, Produit> produits = new HashMap<>();

    public void ajouterProduit(Produit produit) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null.");
        }
        produits.put(produit.getId(), produit);
    }

    public void supprimerProduit(String id) throws ProduitNonTrouveException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("L'ID du produit ne peut pas être null ou vide.");
        }
        if (produits.remove(id) == null) {
            throw new ProduitNonTrouveException("Produit avec l'ID " + id + " non trouvé.");
        }
    }

    public void modifierProduit(String id, String nouveauNom) throws ProduitNonTrouveException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("L'ID du produit ne peut pas être null ou vide.");
        }
        if (nouveauNom == null || nouveauNom.isEmpty()) {
            throw new IllegalArgumentException("Le nouveau nom ne peut pas être null ou vide.");
        }
        Produit produit = produits.get(id);
        if (produit == null) {
            throw new ProduitNonTrouveException("Produit avec l'ID " + id + " non trouvé.");
        }
        produit.setNom(nouveauNom);
    }

    public Produit rechercherProduitParNom(String nom) throws ProduitNonTrouveException {
        if (nom == null || nom.isEmpty()) {
            throw new IllegalArgumentException("Le nom du produit ne peut pas être null ou vide.");
        }
        for (Produit produit : produits.values()) {
            if (produit.getNom().equalsIgnoreCase(nom)) {
                return produit;
            }
        }
        throw new ProduitNonTrouveException("Produit avec le nom " + nom + " non trouvé.");
    }

    public void listerProduitsParLettre(char lettre) {
        for (Produit produit : produits.values()) {
            if (produit.getNom().charAt(0) == lettre) {
                System.out.println(produit);
            }
        }
    }

    public int nombreDeProduitsEnStock() {
        return produits.size();
    }

    public void sauvegarderProduits() throws SQLException {
        try {
            DatabaseGestion.sauvegarderProduits(produits);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde des produits: " + e.getMessage());
            throw e;
        }
    }

    public void chargerProduits() throws SQLException {
        try {
            produits = DatabaseGestion.chargerProduits();
        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des produits: " + e.getMessage());
            throw e;
        }
    }
}