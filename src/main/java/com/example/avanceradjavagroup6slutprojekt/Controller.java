package com.example.avanceradjavagroup6slutprojekt;

import com.eclipsesource.json.JsonArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import com.eclipsesource.json.JsonObject;

import java.io.IOException;
import java.util.List;

public class Controller {

    // Health parameters
    @FXML
    private CheckBox isVegan;
    @FXML
    private CheckBox isVegetarian;
    @FXML
    private CheckBox isDairyFree;
    @FXML
    private CheckBox isShellfishFree;
    @FXML
    private CheckBox isEggFree;

    // Diet parameters
    @FXML
    private CheckBox isBalanced;
    @FXML
    private CheckBox isHighFiber;
    @FXML
    private CheckBox isHighProtein;
    @FXML
    private CheckBox isLowCarb;
    @FXML
    private CheckBox isLowFat;

    @FXML
    private TextField searchField; // field used when searching for recipes
    @FXML
    private ListView<String> recipeListView; // ListView where recipes are shown
    @FXML
    private TextArea ingredients;
    @FXML
    private TextArea instructions;
    @FXML
    private Label dishLabel;
    @FXML
    private TabPane tabPane;
    @FXML
    private TextField itemName;
    @FXML
    private ListView<String> shoppingListArea; // textarea to display the shopping list

    Edamam controller = new Edamam();
    ShoppingList slc = new ShoppingList();
    private static String searchQuery = "";
    private String[] ingredientsList;
    private String[] instructionsList;
    private String recipeName;

    @FXML
    private void openFilterWindow() throws IOException {
        // Opens the filter window
        Stage filter = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("filter-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        filter.setTitle("Filter");
        filter.setScene(scene);
        filter.show();
    }

    @FXML
    private void submitFilter(ActionEvent e) {
        // Gets the filter parameters and returns a search query
        healthParameters();
        dietParameters();
        ((Stage) (((Button) e.getSource()).getScene().getWindow())).close();
    }

    private void healthParameters() {
        // Checks which parameters is chosen and adds it to search query
        String parameter = "&health=";
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

    private void dietParameters() {
        // Checks which parameters is chosen and adds it to search query
        String parameter = "&diet=";
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

    @FXML
    private void search() {
        // Uses the search query to search recipes
        searchQuery += "&q=" + searchField.getText().replace(" ", "%20");
        controller.searchRecipes(searchQuery, recipeListView);
        searchQuery = "";
    }

    @FXML
    private void getChosenRecipe() {
        // Get name, ingredients and instructions from recipe
        int recipeIndex = recipeListView.getSelectionModel().getSelectedIndex();
        recipeName = recipeListView.getSelectionModel().getSelectedItem();

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
    }

    @FXML
    private void showRecipe(MouseEvent event) {
        // If user double-clicks on a recipe, append instructions and ingredients to text area and switch tab to recipe
        if (event.getButton().equals(MouseButton.PRIMARY))
            if (event.getClickCount() == 2) {
                // Make sure that the TextAreas are "empty"
                getChosenRecipe();
                ingredients.setText("");
                instructions.setText("");
                dishLabel.setText(recipeName);
                for (String s : ingredientsList)
                    ingredients.appendText(s + "\n");
                for (int i = 0; i < instructionsList.length; i++)
                    instructions.appendText(String.format("%d: %s\n",i+1, instructionsList[i]));
                switchTab();
            }
    }

    @FXML
    public void switchTab() {
        // Gets selected tab and switches to unselected tab
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        if (tabIndex == 0)
            tabPane.getSelectionModel().select(1);
        else
            tabPane.getSelectionModel().select(0);
    }

    @FXML
    private void addItemToShoppingList() throws IOException {
        String item = itemName.getText();
        slc.addItemToShoppingList(item);
        shoppingListArea.getItems().add(item);
        itemName.clear();
    }

    @FXML
    private void clearShoppingList() {
        shoppingListArea.getItems().clear();
    }

    @FXML
    private void readOldShoppingList() throws IOException {
        shoppingListArea.getItems().clear();
        List<String> oldShoppingList = slc.readOldShoppingList();

        // Append each item to ListArea
        for (String item : oldShoppingList) {
            shoppingListArea.getItems().add(item);
        }
    }

    @FXML
    private void deleteSelectedItem() {
        String selectedText = shoppingListArea.getSelectionModel().getSelectedItem();
        slc.deleteSelectedItem(selectedText);
    }
}
