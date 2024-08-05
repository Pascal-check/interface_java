package com.example;

import DB.DatabaseUtil;
import com.example.home.Home;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connexion");

        // Création des composants de l'interface de connexion
        Label lblEmail = new Label("Entrez l'email");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Entrez l'email");

        Label lblPassword = new Label("Mot de passe");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Mot de passe");

        Button btnLogin = new Button("SE CONNECTER");

        // Action du bouton de connexion
        btnLogin.setOnAction(e -> {
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            if (authenticate(email, password)) {
                // Lancer l'interface Home
                try {
                    // Lance une nouvelle instance de Home
                    Home home = new Home();
                    Stage homeStage = new Stage();
                    home.start(homeStage); // Appel de la méthode start de Home
                    primaryStage.close(); // Ferme la fenêtre de login
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                showAlert("Erreur", "Email ou mot de passe incorrect");
            }
        });

        // Panneau de connexion
        VBox loginPanel = new VBox(10);
        loginPanel.getChildren().addAll(lblEmail, txtEmail, lblPassword, txtPassword, btnLogin);
        loginPanel.setAlignment(Pos.CENTER);
        loginPanel.setPadding(new Insets(20));
        loginPanel.getStyleClass().add("login-panel");

        // Image de fond (optionnelle)
         Image image = new Image(getClass().getResourceAsStream("/resources/bang.JPG"));
         ImageView imageView = new ImageView(image);
         imageView.setFitWidth(800);
         imageView.setFitHeight(600);
         imageView.setPreserveRatio(true);

        // Conteneur principal
        StackPane root = new StackPane();
        root.getChildren().addAll(imageView, loginPanel);
        StackPane.setAlignment(loginPanel, Pos.CENTER);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("resources/styles.css"); // Corriger le chemin si nécessaire

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour authentifier l'utilisateur
    private boolean authenticate(String email, String password) {
        String query = "SELECT * FROM utilisateurs WHERE email = ? AND password = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
