import DB.DatabaseUtil;
import com.example.LoginForm;
import com.example.home.Home;
import javafx.application.Application;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        Application.launch(Home.class,  args);
        String query = "SELECT * FROM produits";

        try {
            ResultSet rs = DatabaseUtil.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Affiche les noms des colonnes
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Column " + i + ": " + rsmd.getColumnName(i));
            }

            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id"));
                System.out.println("Nom: " + rs.getString("nom"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("Marque: " + rs.getString("marque"));
                System.out.println("Date d'expiration: " + rs.getString("dateExpiration"));
                System.out.println("Taille: " + rs.getString("taille"));
                // Adjust or remove the following line based on actual column names
//                System.out.println("Quantite: " + rs.getInt("quantite"));
            }
            DatabaseUtil.close(rs.getStatement().getConnection(), rs.getStatement(), rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}