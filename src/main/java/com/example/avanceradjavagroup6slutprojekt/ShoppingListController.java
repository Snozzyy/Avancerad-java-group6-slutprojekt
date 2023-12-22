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

    private Firebase firebase = new Firebase();

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