package com.example.avanceradjavagroup6slutprojekt;

import com.eclipsesource.json.JsonArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import com.eclipsesource.json.JsonObject;

import java.io.IOException;
import java.util.Arrays;

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
    @FXML
    TextArea ingredients;
    @FXML
    TextArea instructions;

    Controller controller = new Controller();
    private String[] ingredientsList;
    private String[] instructionsList;
    private Stage stage;
    private Scene scene;
    private Parent root;


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
        ((Stage) (((Button) e.getSource()).getScene().getWindow())).close();
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
    public void search() {
        searchQuery += "&q=" + searchField.getText().replace(" ", "%20");
        controller.searchRecipes(searchQuery, recipeListView);
        searchQuery = "";
    }

    // Get name, ingredients and instructions from recipe
    public void getChosenRecipe() throws IOException {
        // Makes sure code is only run on double-click
        int recipeIndex = recipeListView.getSelectionModel().getSelectedIndex();
        String recipeName = recipeListView.getSelectionModel().getSelectedItem();

        JsonObject selectedRecipe = controller.getHits().get(recipeIndex).asObject().get("recipe").asObject();
        JsonArray ingredientsInformation = selectedRecipe.get("ingredients").asArray();
        JsonArray instructions = selectedRecipe.get("instructionLines").asArray();

        ingredientsList = new String[ingredientsInformation.size()];

        // Get all the ingredients in the recipe and add to an array
        for (int i = 0; i < ingredientsInformation.size(); i++)
            ingredientsList[i] = ingredientsInformation.get(i).asObject().get("text").asString();

        // Get all the instructions and add to an array
        instructionsList = new String[instructions.size()];
        for (int i = 0;  i < instructionsList.length; i++)
            instructionsList[i] = instructions.get(i).asString();

        System.out.println(recipeName + "\n" + Arrays.toString(ingredientsList) + "\n" +
                Arrays.toString(instructionsList));
    }

    // Show the recipe when user double-clicks on a recipe in ListView
    public void switchToRecipe(MouseEvent event) throws IOException {
        if (event.getButton().equals(MouseButton.PRIMARY))
            if (event.getClickCount() == 2) {
                getChosenRecipe();
                Parent root = FXMLLoader.load(getClass().getResource("recipe-window.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                showRecipe(ingredientsList, instructionsList);
            }
    }

    // Go back to main-window scene
    public void switchToSearch(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-window.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showRecipe(String[] ingredientsList, String[] instructionsList) {
        // TODO
        //  Kolla varför TextArea är null, fixa det!!!!
        for (String s : ingredientsList)
            ingredients.appendText(s);
        for (String s : instructionsList)
            instructions.appendText(s);
    }
}
