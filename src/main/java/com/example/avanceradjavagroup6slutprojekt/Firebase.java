package com.example.avanceradjavagroup6slutprojekt;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Firebase {
    // url for firebase database
    private static final String FIREBASE_DB_URL = "https://api-project-fe4e6-default-rtdb.europe-west1.firebasedatabase.app/";

    // method to add an item to the database in firebase
    public void addItemToShoppingList(String item) throws IOException {
        URL url = new URL(FIREBASE_DB_URL + "shoppingList.json");
        // sets up the HTTP connection for the POST request
        HttpURLConnection connection = setupConnection(url, "POST");

        try (OutputStream outputStream = connection.getOutputStream()) {
            // writes the item to the output stream and sends it
            outputStream.write(("{\"item\":\"" + item + "\"}").getBytes());
            outputStream.flush();
        }

        // reads the response from the database
        readResponse(connection);
    }

    // method to get the shopping list from firebase
    public List<String> getShoppingList() throws IOException {
        URL url = new URL(FIREBASE_DB_URL + "shoppingList.json");
        HttpURLConnection connection = setupConnection(url, "GET");

        // reads the response and parses it into a list
        String response = readResponse(connection);
        return parseShoppingList(response);
    }

    // method to set up an HTTP connection
    private HttpURLConnection setupConnection(URL url, String method) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method); // sets the HTTP method (GET or POST)
        connection.setDoOutput(true); // allows sending a request body
        connection.setRequestProperty("Content-Type", "application/json"); // sets the content type to JSON
        connection.setRequestProperty("Accept", "application/json"); // sets the response format to JSON
        return connection;
    }

    // method to read the response from the database
    private String readResponse(HttpURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line); // appends each line of the response
            }
        }
        return response.toString();
    }

    // method to parse the JSON response into a list of shopping items
    private List<String> parseShoppingList(String jsonResponse) {
        List<String> shoppingList = new ArrayList<>();
        JsonObject jsonObject = Json.parse(jsonResponse).asObject();

        // for each loop over each key-value pair in the JSON object
        for (String key : jsonObject.names()) {
            JsonValue value = jsonObject.get(key);
            if (value.isObject()) {
                // takes the item field from each object
                String item = value.asObject().getString("item", null);
                if (item != null) {
                    shoppingList.add(item); // adds the item to the list
                }
            }
        }

        return shoppingList;
    }

    // method to get the key of an item by its name from Firebase
    private String getItemKeyByName(String itemName) throws IOException {
        URL url = new URL(FIREBASE_DB_URL + "shoppingList.json");
        HttpURLConnection connection = setupConnection(url, "GET");

        // reads the response and parse the item keys
        String response = readResponse(connection);
        JsonObject jsonObject = Json.parse(response).asObject();

        // loops through the JSON object to find the key that matches the item name
        for (String key : jsonObject.names()) {
            String item = jsonObject.get(key).asObject().getString("item", null);
            if (item != null && item.equals(itemName)) {
                return key;
            }
        }
        System.out.println("Key not found");
        return null; // if not found
    }

    public void deleteItemFromShoppingList(String item) throws IOException {
        String itemKey = getItemKeyByName(item); // gets the key of the item to delete

        if (itemKey != null) {
            URL url = new URL(FIREBASE_DB_URL + "shoppingList/" + itemKey + ".json");
            // sets up the HTTP connection for the DELETE request
            HttpURLConnection connection = setupConnection(url, "DELETE");

            // sends the delete request to firebase
            connection.connect();

            // checks the response code to see if the item was deleted successfully
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Item deleted successfully.");
            } else {
                System.err.println("Failed to delete item. Response code: " + responseCode);
            }
        }
    }
}