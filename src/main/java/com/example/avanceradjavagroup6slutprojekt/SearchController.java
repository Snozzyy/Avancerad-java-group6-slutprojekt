package com.example.avanceradjavagroup6slutprojekt;

import com.eclipsesource.json.JsonArray;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.eclipsesource.json.JsonObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SearchController {

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
    @FXML
    Label dishLabel;
    @FXML
    TabPane tabPane;

    Controller controller = new Controller();
    private static String searchQuery = "";
    private String[] ingredientsList;
    private String[] instructionsList;
    private Stage stage;
    private Scene scene;
    String recipeName;

    public SearchController() {
        instructions = new TextArea("");
        ingredients = new TextArea("");
    }

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

    // If user double-clicks on a recipe, append instructions and ingredients to text area and switch tab to recipe
    public void showRecipe(MouseEvent event) throws IOException {
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

    // Gets selected tab and switches to unselected tab
    public void switchTab() {
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        if (tabIndex == 0)
            tabPane.getSelectionModel().select(1);
        else
            tabPane.getSelectionModel().select(0);
    }

    public void openShoppingListWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/avanceradjavagroup6slutprojekt/shopping-list.fxml"));
            VBox root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Shopping List");
            stage.setScene(scene);
            stage.show();

            // loads and displays the current shopping list from firebase database
            updateShoppingList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // updates the shopping list
    private void updateShoppingList() {
        Platform.runLater(() -> {
            try {
                // gets the shopping list from Firebase
                List<String> shoppingList = firebase.getShoppingList();
                shoppingListArea.clear();

                // adds each item from the shopping list to the TextArea
                for (String item : shoppingList) {
                    shoppingListArea.appendText(item + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private TextArea shoppingListArea; // textarea to display the shopping list

    private Firebase firebase = new Firebase();

    @FXML
    TextField textfield; // tom - snackade i discord om den

    @FXML
    public void saveShoppingList() {
        String shoppingListText = shoppingListArea.getText();

        // splits the text into individual items
        String[] items = shoppingListText.split("\\r?\\n");

        // for each loop to loop each item and save it to firebase
        for (String item : items) {
            if (!item.trim().isEmpty()) { // skips empty lines
                try {
                    firebase.addItemToShoppingList(item.trim());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // clears the textarea after saving
        shoppingListArea.clear();
    }

    @FXML
    private void addItemToShoppingList() {
        // tom - egentligen ganska meningslös - tas bort?
    }

    @FXML
    private void clearShoppingList() {
        // clears the shopping list textarea
        shoppingListArea.clear();
    }

    @FXML
    private void closeShoppingListWindow() {
        // closes the shopping list window
        Stage stage = (Stage) shoppingListArea.getScene().getWindow();
        stage.close();
    }

    public void readOldShoppingList(ActionEvent actionEvent) {
        try {
            // gets the old shopping list from firebase
            List<String> oldShoppingList = firebase.getShoppingList();
            shoppingListArea.clear();

            // displays the old shopping list in the textarea
            for (String item : oldShoppingList) {
                shoppingListArea.appendText(item + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

