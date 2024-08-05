package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

class DatabaseGestion {
    private static final String DB_URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Connectivityjava";
    private static final String USER = "freedb_pascalkong";
    private static final String PASS = "wwU3!!k%ydu#f5V";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void creerTables() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS produits (" +
                "id VARCHAR(255) PRIMARY KEY, " +
                "nom VARCHAR(255), " +
                "type VARCHAR(255), " +
                "marque VARCHAR(255), " +
                "dateExpiration VARCHAR(255), " +
                "taille VARCHAR(255))";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableQuery);
        }
    }

    public static void sauvegarderProduits(Map<String, Produit> produits) throws SQLException {
        creerTables(); // Créer les tables avant d'insérer les données

        String insertQuery = "INSERT INTO produits (id, nom, type, marque, dateExpiration, taille) VALUES (?, ?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE produits SET nom = ?, type = ?, marque = ?, dateExpiration = ?, taille = ? WHERE id = ?";

        try (Connection connection = getConnection()) {
            for (Produit produit : produits.values()) {
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                     PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    if (produit instanceof Electronique) {
                        Electronique electronique = (Electronique) produit;
                        updateStatement.setString(1, electronique.getNom());
                        updateStatement.setString(2, "Electronique");
                        updateStatement.setString(3, electronique.getMarque());
                        updateStatement.setNull(4, Types.VARCHAR);
                        updateStatement.setNull(5, Types.VARCHAR);
                        updateStatement.setString(6, electronique.getId());
                        if (updateStatement.executeUpdate() == 0) {
                            insertStatement.setString(1, electronique.getId());
                            insertStatement.setString(2, electronique.getNom());
                            insertStatement.setString(3, "Electronique");
                            insertStatement.setString(4, electronique.getMarque());
                            insertStatement.setNull(5, Types.VARCHAR);
                            insertStatement.setNull(6, Types.VARCHAR);
                            insertStatement.executeUpdate();
                        }
                    } else if (produit instanceof Alimentaire) {
                        Alimentaire alimentaire = (Alimentaire) produit;
                        updateStatement.setString(1, alimentaire.getNom());
                        updateStatement.setString(2, "Alimentaire");
                        updateStatement.setNull(3, Types.VARCHAR);
                        updateStatement.setString(4, alimentaire.getDateExpiration());
                        updateStatement.setNull(5, Types.VARCHAR);
                        updateStatement.setString(6, alimentaire.getId());
                        if (updateStatement.executeUpdate() == 0) {
                            insertStatement.setString(1, alimentaire.getId());
                            insertStatement.setString(2, alimentaire.getNom());
                            insertStatement.setString(3, "Alimentaire");
                            insertStatement.setNull(4, Types.VARCHAR);
                            insertStatement.setString(5, alimentaire.getDateExpiration());
                            insertStatement.setNull(6, Types.VARCHAR);
                            insertStatement.executeUpdate();
                        }
                    } else if (produit instanceof Vestimentaire) {
                        Vestimentaire vestimentaire = (Vestimentaire) produit;
                        updateStatement.setString(1, vestimentaire.getNom());
                        updateStatement.setString(2, "Vestimentaire");
                        updateStatement.setNull(3, Types.VARCHAR);
                        updateStatement.setNull(4, Types.VARCHAR);
                        updateStatement.setString(5, vestimentaire.getTaille());
                        updateStatement.setString(6, vestimentaire.getId());
                        if (updateStatement.executeUpdate() == 0) {
                            insertStatement.setString(1, vestimentaire.getId());
                            insertStatement.setString(2, vestimentaire.getNom());
                            insertStatement.setString(3, "Vestimentaire");
                            insertStatement.setNull(4, Types.VARCHAR);
                            insertStatement.setNull(5, Types.VARCHAR);
                            insertStatement.setString(6, vestimentaire.getTaille());
                            insertStatement.executeUpdate();
                        }
                    }
                }
            }
        }
    }

    public static Map<String, Produit> chargerProduits() throws SQLException {
        Map<String, Produit> produits = new HashMap<>();
        String query = "SELECT id, nom, type, marque, dateExpiration, taille FROM produits";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nom = resultSet.getString("nom");
                String type = resultSet.getString("type");
                String marque = resultSet.getString("marque");
                String dateExpiration = resultSet.getString("dateExpiration");
                String taille = resultSet.getString("taille");

                Produit produit = null;
                switch (type) {
                    case "Electronique":
                        produit = new Electronique(id, nom, marque);
                        break;
                    case "Alimentaire":
                        produit = new Alimentaire(id, nom, dateExpiration);
                        break;
                    case "Vestimentaire":
                        produit = new Vestimentaire(id, nom, taille);
                        break;
                }

                if (produit != null) {
                    produits.put(id, produit);
                }
            }
        }
        return produits;
    }
}