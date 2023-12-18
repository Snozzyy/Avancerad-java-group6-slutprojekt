package com.example.avanceradjavagroup6slutprojekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecipeController {
    @FXML
    TextArea ingredients;
    @FXML
    TextArea instructions;
    private Stage stage;
    private Scene scene;
    private Parent root;
    String[] ingredientList;
    String[] instructionList;

    RecipeController(String[] ingredientList, String[] instructionList) throws IOException {
        this.ingredientList = ingredientList;
        this.instructionList = instructionList;
    }

    public void recipeScene(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("recipe-window.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void searchScene(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("main-window.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
