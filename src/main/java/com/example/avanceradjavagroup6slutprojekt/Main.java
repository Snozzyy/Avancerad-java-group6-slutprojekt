package com.example.avanceradjavagroup6slutprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // creates gui
        GUI gui = new GUI();

        // sets the scene for the primary stage
        primaryStage.setScene(gui.getScene());

        // title of the window
        primaryStage.setTitle("Recipe Search");

        // displays the primary stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        // launches the JavaFX application
        launch(args);
    }
}

