package objet;

import DB.DatabaseUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Magasin {
    private List<Produit> produits;

    public Magasin() {
        this.produits = new ArrayList<>();
    }

    public boolean supprimerProduit(Produit produit) {
        return produits.remove(produit);
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void ajouterProduit(Produit produit) {
        produits.add(produit);
    }

    public Produit getProduitById(int id) {
        for (Produit produit : produits) {
            if (produit.getId() == id) {
                return produit;
            }
        }
        return null;
    }

    public void chargerProduits() {
        String query = "SELECT * FROM produits";
        try (Connection connection = DatabaseUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                double prix = resultSet.getDouble("prix");
                int quantite = resultSet.getInt("quantite");
                String type = resultSet.getString("type");

                Produit produit;
                switch (type.toLowerCase()) {
                    case "alimentaire":
                        produit = new Alimentaire(id, nom, prix, quantite, type);
                        break;
                    case "electronique":
                        produit = new Electronique(id, nom, prix, quantite, type);
                        break;
                    case "vestimentaire":
                        produit = new Vestimentaire(id, nom, prix, quantite, type);
                        break;
                    default:
                        produit = new Produit(id, nom, prix, quantite, type);
                        break;
                }
                ajouterProduit(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Magasin{" +
                "produits=" + produits +
                '}';
    }
}