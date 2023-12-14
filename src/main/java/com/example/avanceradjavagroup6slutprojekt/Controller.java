package com.example.avanceradjavagroup6slutprojekt;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// controller class to work with the api
public class Controller {

    private static final String API_ID = "f6891638";
    private static final String API_KEY = "2b61efbfbbfbb9a8e99618f31d0ec561";
    private static final String BASE_URL = "https://api.edamam.com/api/recipes/v2";
    @FXML
    ListView<String> recipeListView = new ListView<>();
    @FXML
    TextField searchField;

    //searches for recipes based on what the user types
    public void searchRecipes(String search, ListView<String> recipeListView) {
        new Thread(() -> {
            try {
                BufferedReader in = getBufferedReader(search);
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // parses the response and updates the GUI
                List<String> recipes = parseRecipes(content.toString());
                javafx.application.Platform.runLater(() -> recipeListView.getItems().setAll(recipes));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static BufferedReader getBufferedReader(String encodedSearch) throws IOException {
        String requestURL = BASE_URL + "?type=public" + encodedSearch + "&app_id=" + API_ID + "&app_key=" + API_KEY;

        // creates a URL and opens the connection
        URL url = new URL(requestURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // reads the response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        return in;
    }

    // parses the response from the api to get the names of recipes
    private List<String> parseRecipes(String jsonResponse) {
        List<String> recipeList = new ArrayList<>();
        JsonObject jsonObject = Json.parse(jsonResponse).asObject();
        JsonArray hits = jsonObject.get("hits").asArray();
        // for each loop to go through each element
        for (JsonValue hit : hits) {
            JsonObject recipe = hit.asObject().get("recipe").asObject();
            String recipeName = recipe.get("label").asString();
            recipeList.add(recipeName);
        }
        return recipeList;
    }
}
