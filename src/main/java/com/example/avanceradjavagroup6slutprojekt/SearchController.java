package com.example.avanceradjavagroup6slutprojekt;

import com.eclipsesource.json.JsonArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SearchController {
    private static String searchQuery = "";

    // Health parameters
    @FXML
    CheckBox isVegan;
    @FXML
    CheckBox isVegetarian;
    @FXML
    CheckBox isDairyFree;
    @FXML
    CheckBox isShellfishFree;
    @FXML
    CheckBox isEggFree;

    // Diet parameters
    @FXML
    CheckBox isBalanced;
    @FXML
    CheckBox isHighFiber;
    @FXML
    CheckBox isHighProtein;
    @FXML
    CheckBox isLowCarb;
    @FXML
    CheckBox isLowFat;

    @FXML
    TextField searchField;
    @FXML
    ListView<String> recipeListView;

    Controller controller = new Controller();

    // Opens the filter window
    public void openFilterWindow() throws IOException {
        Stage filter = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("filter-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        filter.setTitle("Filter");
        filter.setScene(scene);
        filter.show();
    }

    // Gets the filter parameters and returns a search query
    public void submitFilter(ActionEvent e) {
        healthParameters();
        dietParameters();
        ((Stage)(((Button)e.getSource()).getScene().getWindow())).close();
    }

    // Checks which parameters is chosen and adds it to search query
    private void healthParameters() {
        String parameter = "&health=";
        // Check if possible to optimize with for-loop
        if (isVegan.isSelected())
            searchQuery += parameter + "vegan";
        if (isVegetarian.isSelected())
            searchQuery += parameter + "vegetarian";
        if (isDairyFree.isSelected())
            searchQuery += parameter + "dairy-free";
        if (isShellfishFree.isSelected())
            searchQuery += parameter + "shellfish-free";
        if (isEggFree.isSelected())
            searchQuery += parameter + "egg-free";
    }

    // Checks which parameters is chosen and adds it to search query
    private void dietParameters() {
        String parameter = "&diet=";
        // Check if possible to optimize with for-loop
        if (isBalanced.isSelected())
            searchQuery += parameter + "balanced";
        if (isHighFiber.isSelected())
            searchQuery += parameter + "high-fiber";
        if (isHighProtein.isSelected())
            searchQuery += parameter + "high-protein";
        if (isLowCarb.isSelected())
            searchQuery += parameter + "low-carb";
        if (isLowFat.isSelected())
            searchQuery += parameter + "low-fat";
    }

    // Uses the search query to search recipes
    public void search(){
        searchQuery += "&q=" + searchField.getText().replace(" ", "%20");
        controller.searchRecipes(searchQuery, recipeListView);
        searchQuery = "";
    }

    // Gets the index value of chosen recipe
    public void getChosenRecipes(MouseEvent event) {
        // Makes sure code is only run on double-click
        if (event.getButton().equals(MouseButton.PRIMARY))
            if (event.getClickCount() == 2){
                // Skriv kod här
                int index = recipeListView.getSelectionModel().getSelectedIndex();
                System.out.println(index);
            }
    }
}