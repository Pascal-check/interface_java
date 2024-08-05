package com.example.home;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objet.Alimentaire;
import objet.Electronique;
import objet.Produit;
import objet.Vestimentaire;
import objet.Magasin;

import java.util.List;

public class Home extends Application {

    private final Magasin magasin = new Magasin();
    private TableView<Produit> tableView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        magasin.chargerProduits();

        primaryStage.setTitle("gestion de produit");

        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        GridPane dashboard = createDashboard();
        root.add(dashboard, 0, 0);
        GridPane.setHgrow(dashboard, Priority.ALWAYS);
        GridPane.setVgrow(dashboard, Priority.ALWAYS);

        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add("resources/styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createDashboard() {
        GridPane dashboard = new GridPane();
        dashboard.setPadding(new Insets(10));
        dashboard.setHgap(10);
        dashboard.setVgap(10);

        VBox projectsSection = createProjectsSection();

        dashboard.add(projectsSection, 0, 0);

        GridPane.setVgrow(projectsSection, Priority.ALWAYS);
        GridPane.setHgrow(projectsSection, Priority.ALWAYS);

        GridPane.setVgrow(dashboard, Priority.ALWAYS);
        GridPane.setHgrow(dashboard, Priority.ALWAYS);

        return dashboard;
    }

    private VBox createSection(String title) {
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #2e63c6;");
        VBox sectionBox = new VBox(5, titleLabel, new Separator());
        sectionBox.setPadding(new Insets(10));
        sectionBox.setStyle("-fx-background-color: #2e2e2e; -fx-background-radius: 10;");
        sectionBox.setPrefSize(300, 200);
        VBox.setVgrow(sectionBox, Priority.ALWAYS);
        return sectionBox;
    }

    private VBox createProjectsSection() {
        VBox projectsBox = createSection("Produits");

        tableView = new TableView<>();
        tableView.setItems(FXCollections.observableArrayList(magasin.getProduits()));

        TableColumn<Produit, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Produit, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Produit, Double> priceColumn = new TableColumn<>("Prix");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

        TableColumn<Produit, Integer> quantityColumn = new TableColumn<>("Quantité");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        TableColumn<Produit, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Replace empty fields with "-"
        idColumn.setCellFactory(column -> createCellFactory());
        nameColumn.setCellFactory(column -> createCellFactory());
        priceColumn.setCellFactory(column -> createCellFactory());
        quantityColumn.setCellFactory(column -> createCellFactory());
        typeColumn.setCellFactory(column -> createCellFactory());

        tableView.getColumns().addAll(idColumn, nameColumn, priceColumn, quantityColumn, typeColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox.setVgrow(tableView, Priority.ALWAYS);

        projectsBox.getChildren().add(tableView);
        VBox.setVgrow(projectsBox, Priority.ALWAYS);

        HBox buttonBox = createButtonBox();
        projectsBox.getChildren().add(buttonBox);

        return projectsBox;
    }

    private <T> TableCell<Produit, T> createCellFactory() {
        return new TableCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("-");
                } else {
                    setText(item.toString());
                }
            }
        };
    }

    private HBox createButtonBox() {
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER); // Center the buttons

        Button addButton = new Button("Ajouter Produit");
        addButton.setOnAction(e -> showAddProductDialog());

        Button deleteButton = new Button("Supprimer Produit");
        deleteButton.setOnAction(e -> showDeleteProductDialog());

        Button modifyButton = new Button("Modifier Produit");
        modifyButton.setOnAction(e -> showModifyProductDialog());

        Button searchButton = new Button("Rechercher Produit");
        searchButton.setOnAction(e -> showSearchProductDialog());

        Button listButton = new Button("Lister Produits par Lettre");
        listButton.setOnAction(e -> showListProductsByLetterDialog());

        buttonBox.getChildren().addAll(addButton, deleteButton, modifyButton, searchButton, listButton);
        return buttonBox;
    }

    private void showAddProductDialog() {
        Dialog<Produit> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un produit");
        ButtonType addButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = createProductGrid();

        dialog.getDialogPane().setContent(grid);

        TextField idField = (TextField) grid.getChildren().get(1);
        TextField nomField = (TextField) grid.getChildren().get(3);
        TextField prixField = (TextField) grid.getChildren().get(5);
        TextField quantiteField = (TextField) grid.getChildren().get(7);
        ComboBox<String> typeComboBox = (ComboBox<String>) grid.getChildren().get(9);

        Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String type = typeComboBox.getValue();
                if (type.equals("Alimentaire")) {
                    return new Alimentaire(Integer.parseInt(idField.getText()), nomField.getText(), Double.parseDouble(prixField.getText()), Integer.parseInt(quantiteField.getText()), type);
                } else if (type.equals("Electronique")) {
                    return new Electronique(Integer.parseInt(idField.getText()), nomField.getText(), Double.parseDouble(prixField.getText()), Integer.parseInt(quantiteField.getText()), type);
                } else if (type.equals("Vestimentaire")) {
                    return new Vestimentaire(Integer.parseInt(idField.getText()), nomField.getText(), Double.parseDouble(prixField.getText()), Integer.parseInt(quantiteField.getText()), type);
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(produit -> {
            magasin.ajouterProduit(produit);
            tableView.getItems().add(produit);
            showAlert("Success", "Produit ajouté avec succès!", Alert.AlertType.INFORMATION);
        });
    }

    private void showDeleteProductDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Supprimer un produit");

        ButtonType deleteButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("ID");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);

        dialog.getDialogPane().setContent(grid);

        Node deleteButton = dialog.getDialogPane().lookupButton(deleteButtonType);
        deleteButton.setDisable(true);

        idField.textProperty().addListener((observable, oldValue, newValue) -> {
            deleteButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonType) {
                return idField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(id -> {
            Produit produit = magasin.getProduitById(Integer.parseInt(id));
            if (produit != null) {
                magasin.supprimerProduit(produit);
                tableView.getItems().remove(produit);
                showAlert("Success", "Produit supprimé avec succès!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Produit non trouvé!", Alert.AlertType.ERROR);
            }
        });
    }

    private void showModifyProductDialog() {
        // Implementation of modify dialog goes here
    }

    private void showSearchProductDialog() {
        // Implementation of search dialog goes here
    }

    private void showListProductsByLetterDialog() {
        // Implementation of list by letter dialog goes here
    }

    private GridPane createProductGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        TextField prixField = new TextField();
        prixField.setPromptText("Prix");
        TextField quantiteField = new TextField();
        quantiteField.setPromptText("Quantité");

        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Alimentaire", "Electronique", "Vestimentaire");
        typeComboBox.setPromptText("Type");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(nomField, 1, 1);
        grid.add(new Label("Prix:"), 0, 2);
        grid.add(prixField, 1, 2);
        grid.add(new Label("Quantité:"), 0, 3);
        grid.add(quantiteField, 1, 3);
        grid.add(new Label("Type:"), 0, 4);
        grid.add(typeComboBox, 1, 4);

        return grid;
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
