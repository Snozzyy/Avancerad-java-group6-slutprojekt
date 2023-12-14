package com.example.avanceradjavagroup6slutprojekt;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
public class GUI {

    private ListView<String> recipeListView;
    private Controller controller;
    private Scene scene;

    // constructor to run the GUI and controller components
    public GUI() {
        controller = new Controller();
        startGUI();
    }

    // starts the GUI components
    private void startGUI() {
        // text field for entering searches
        TextField searchField = new TextField();

        // button for the search
        Button searchButton = new Button("Search");

        // list view to display search results
        recipeListView = new ListView<>();

        // sets the action for the search button
        searchButton.setOnAction(e -> controller.searchRecipes(searchField.getText(), recipeListView));

        // layout container for the GUI components
        VBox layout = new VBox(10);
        layout.getChildren().addAll(searchField, searchButton, recipeListView);

        // creates a scene with the layout
        scene = new Scene(layout, 400, 300);
    }

    //getter for the scene
    public Scene getScene() {
        return scene;
    }
}
