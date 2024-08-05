package com.company;


import java.util.Scanner;

public class GestionDeProduits {
    public static void main(String[] args) {
        Magasin magasin = new Magasin();
        Scanner scanner = new Scanner(System.in);

        try {
            // Ajouter des produits via l'entrée utilisateur
            while (true) {
                System.out.println("Entrez l'ID du produit (ou 'exit' pour terminer) :");
                String id = scanner.nextLine();
                if (id.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.println("Entrez le nom du produit :");
                String nom = scanner.nextLine();

                magasin.ajouterProduit(new Produit(id, nom));
            }

            // Sauvegarder les produits
            magasin.sauvegarderProduits();

            // Charger les produits
            magasin.chargerProduits();

            // Supprimer un produit
            System.out.println("Entrez l'ID du produit à supprimer :");
            String idASupprimer = scanner.nextLine();
            magasin.supprimerProduit(idASupprimer);

            // Modifier un produit
            System.out.println("Entrez l'ID du produit à modifier :");
            String idAModifier = scanner.nextLine();
            System.out.println("Entrez le nouveau nom du produit :");
            String nouveauNom = scanner.nextLine();
            magasin.modifierProduit(idAModifier, nouveauNom);

            // Rechercher un produit par nom
            System.out.println("Entrez le nom du produit à rechercher :");
            String nomRecherche = scanner.nextLine();
            Produit produitRecherche = magasin.rechercherProduitParNom(nomRecherche);
            System.out.println("Produit recherché: " + produitRecherche.getNom());

            // Lister les produits par lettre
            System.out.println("Entrez la lettre pour lister les produits :");
            char lettre = scanner.nextLine().charAt(0);
            System.out.println("Produits commençant par '" + lettre + "':");
            magasin.listerProduitsParLettre(lettre);

            // Afficher le nombre de produits en stock
            System.out.println("Nombre de produits en stock: " + magasin.nombreDeProduitsEnStock());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}