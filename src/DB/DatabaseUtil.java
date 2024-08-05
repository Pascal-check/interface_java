package DB;

import java.sql.*;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://sql.freedb.tech:3306/freedb_Connectivityjava";
    private static final String USER = "freedb_pascalkong";
    private static final String PASSWORD = "wwU3!!k%ydu#f5V";

    // Méthode pour obtenir une connexion à la base de données
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Méthode pour exécuter une requête SQL
    public static ResultSet executeQuery(String query) throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
    // Méthode pour fermer la connexio
    // n, la déclaration et le résultat
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}