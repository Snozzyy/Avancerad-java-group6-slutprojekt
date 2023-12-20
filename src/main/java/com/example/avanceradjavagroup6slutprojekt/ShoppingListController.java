/*
package com.example.avanceradjavagroup6slutprojekt;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ShoppingListController {


    public void openShoppingListWindow() {

*/
/*    public void openShoppingListWindow() {

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
    }*//*


    // updates the shopping list
*/
/*    private void updateShoppingList() {
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
    }*//*


    @FXML
    private TextArea shoppingListArea; // textarea to display the shopping list

    private Firebase firebase = new Firebase();

    @FXML
    TextField textfield; // tom - snackade i discord om den

    @FXML
*/
/*    @FXML
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
    private void addItemToShoppingList() throws IOException {
        String item = itemName.getText();
        itemName.clear();
        firebase.addItemToShoppingList(item);
        shoppingListArea.appendText(item + "\n");
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

    @FXML
    private void deleteSelectedItem() {
        String selectedText = shoppingListArea.getSelectedText();

        if (selectedText != null && !selectedText.isEmpty()) {
            // removes the selected item from the shopping list in the GUI
            shoppingListArea.replaceSelection("");

    }*//*


    @FXML
    public void addItemToShoppingList(String item) throws IOException {
        firebase.addItemToShoppingList(item);
    }

   public List<String> readOldShoppingList() throws IOException {
       return firebase.getShoppingList();
    }

    @FXML
    public void deleteSelectedItem(String selectedText) {
        if (selectedText != null && !selectedText.isEmpty()) {
            // removes the selected item from the shopping list in the GUI
            // deletes the selected item from firebase
            try {
                firebase.deleteItemFromShoppingList(selectedText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
*/
