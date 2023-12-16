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

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.io.IOException;
import java.util.Arrays;
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
    public void getChosenRecipe(MouseEvent event) {
        // Makes sure code is only run on double-click
        if (event.getButton().equals(MouseButton.PRIMARY))
            if (event.getClickCount() == 2){
                int recipeIndex = recipeListView.getSelectionModel().getSelectedIndex();
                JsonArray recipes = controller.getHits();
                JsonObject recipeJson = recipes.get(recipeIndex).asObject().get("recipe").asObject();

                String recipeName = recipeListView.getSelectionModel().getSelectedItem();
                String name = recipeJson.get("label").asString(); // Ta bort, bara för test
                System.out.println(recipeName);

                // {"recipe":{"uri":"http://www.edamam.com/ontologies/edamam.owl#recipe_f2de3b23411c7bb4f49b623e80b109ac","label":"All-Purpose Croutons","image":"https://edamam-product-images.s3.amazonaws.com/web-img/ff1/ff14b678699c5e2cc9380c072a5419c4.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEPz%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJGMEQCIDjtAFQ34ujshUW%2FkV1BuJ7YrkQiIvCPcJelgB1OTxwKAiAPwEuaCVGivQJI%2F0oruCb7uIGNsG%2BPaaepWQCRRsSgqyq5BQh1EAAaDDE4NzAxNzE1MDk4NiIMR%2BpOeHj0w7tLVjFjKpYFlpmoEZXVoiLueHTyrRaPRZp2TiD0df9MtZn1pCjiAlceisaW%2F%2FqoqsPOe8xKhWCQQVFq4AUFF%2FdeCh9N6OqyVeDZmd5AB%2BuyqSwOqU2SFewiB%2F6EwrIU0FVZwqzHpAK04tH%2Be8fniQj%2FGSXSIVoRbWiAVkmOX8U14JGZXVowC4XqMEF5Z4koveJSZ%2Fu165hXhrUkcHHjR0%2BWHVI%2BzQcQhx%2BBhrw1j5YDlDMNekWJDTxFDycwprMjaugIO9rg9BtWonV8rMv0GnqznhzmoqxShJZDfJraRpVjpVpCVLTpTND4bDv9pFznDxKpo4ru9BrugNZERuB27MamREKCzipBWlAxA3iu%2FIRZ%2B%2BKTXyjbYmoQfOP6VoAX2BNo3yi7E674eaBfcOJvKloGgHypnZr3QkeViMpfZSuJWGlk09UMS2LO8MGAfZMj3Y%2FvR6vKvM9K%2FurvJwM7smJUn91hzgTJeLIleDvyF5ON%2BkWqt%2FgMf39%2FrR2OR3DwtgXxHTWAfsLSpXAyEqt6nJ6XV6k7Ws3HnC%2FKpixdPn5%2BKiSTtiuzqprasCg4bt%2B47lO4aHxhBwIq90%2FkYn07lBgCQaSze3XFmhJFFP6VyCMsyAinBoP7jwTWjOKLrwLyHU6PQP4oEFNSJfIaz4Jov01UCphkwbxsMtv0R%2BXex6AbVXeQiPXkRXm4Jmf8Ol9hVcQjsjMG8MiSbSHEARfFlCnmefx13wTrfduAHSqMS9fKmdzefO7OvblM852Ky2gWt1jPpsRzQ6vYHU%2FiCrw4aSnl4riBIgKwwS3GuxfcLAfdckVATanWI1wM8BV1hLSQPMz1HUhpM0oRwPl%2Bwgbk%2FNHUCBF1ddSRWEt%2BCOYYh%2BGQYP9B1du6%2B2IhCHP9%2BvgwgfDwqwY6sgHfA99dWe6%2B0fzfNSVR1pBg7JW9WXNgabQgFs%2B9TUNzEV3nst6BgfhY75eZ2FYf3WgzEoEEeP%2F8qoMvcixdW8uF5Isg1v7uPoivopSIREw9RE7AFsY2oRy%2BvwKpYoR9rGU%2BBWkFEUmDBhkczoFnVw%2FxKlL4AUQ2iJsiKRuAmo4p4ZIzCQO%2Fkw244eUTPgWlUCkHyQgZ%2BRh9rGZqyYZ1r%2Bhbt%2FvUaztSWvfZIbsMoHLEsHb%2B&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231215T124758Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFOUYCVPYX%2F20231215%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=9b2dcb270b0fb581de73d30b518364c920dd47bbf18a44db55a82901204eb7c8","images":{"THUMBNAIL":{"url":"https://edamam-product-images.s3.amazonaws.com/web-img/ff1/ff14b678699c5e2cc9380c072a5419c4-s.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEPz%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJGMEQCIDjtAFQ34ujshUW%2FkV1BuJ7YrkQiIvCPcJelgB1OTxwKAiAPwEuaCVGivQJI%2F0oruCb7uIGNsG%2BPaaepWQCRRsSgqyq5BQh1EAAaDDE4NzAxNzE1MDk4NiIMR%2BpOeHj0w7tLVjFjKpYFlpmoEZXVoiLueHTyrRaPRZp2TiD0df9MtZn1pCjiAlceisaW%2F%2FqoqsPOe8xKhWCQQVFq4AUFF%2FdeCh9N6OqyVeDZmd5AB%2BuyqSwOqU2SFewiB%2F6EwrIU0FVZwqzHpAK04tH%2Be8fniQj%2FGSXSIVoRbWiAVkmOX8U14JGZXVowC4XqMEF5Z4koveJSZ%2Fu165hXhrUkcHHjR0%2BWHVI%2BzQcQhx%2BBhrw1j5YDlDMNekWJDTxFDycwprMjaugIO9rg9BtWonV8rMv0GnqznhzmoqxShJZDfJraRpVjpVpCVLTpTND4bDv9pFznDxKpo4ru9BrugNZERuB27MamREKCzipBWlAxA3iu%2FIRZ%2B%2BKTXyjbYmoQfOP6VoAX2BNo3yi7E674eaBfcOJvKloGgHypnZr3QkeViMpfZSuJWGlk09UMS2LO8MGAfZMj3Y%2FvR6vKvM9K%2FurvJwM7smJUn91hzgTJeLIleDvyF5ON%2BkWqt%2FgMf39%2FrR2OR3DwtgXxHTWAfsLSpXAyEqt6nJ6XV6k7Ws3HnC%2FKpixdPn5%2BKiSTtiuzqprasCg4bt%2B47lO4aHxhBwIq90%2FkYn07lBgCQaSze3XFmhJFFP6VyCMsyAinBoP7jwTWjOKLrwLyHU6PQP4oEFNSJfIaz4Jov01UCphkwbxsMtv0R%2BXex6AbVXeQiPXkRXm4Jmf8Ol9hVcQjsjMG8MiSbSHEARfFlCnmefx13wTrfduAHSqMS9fKmdzefO7OvblM852Ky2gWt1jPpsRzQ6vYHU%2FiCrw4aSnl4riBIgKwwS3GuxfcLAfdckVATanWI1wM8BV1hLSQPMz1HUhpM0oRwPl%2Bwgbk%2FNHUCBF1ddSRWEt%2BCOYYh%2BGQYP9B1du6%2B2IhCHP9%2BvgwgfDwqwY6sgHfA99dWe6%2B0fzfNSVR1pBg7JW9WXNgabQgFs%2B9TUNzEV3nst6BgfhY75eZ2FYf3WgzEoEEeP%2F8qoMvcixdW8uF5Isg1v7uPoivopSIREw9RE7AFsY2oRy%2BvwKpYoR9rGU%2BBWkFEUmDBhkczoFnVw%2FxKlL4AUQ2iJsiKRuAmo4p4ZIzCQO%2Fkw244eUTPgWlUCkHyQgZ%2BRh9rGZqyYZ1r%2Bhbt%2FvUaztSWvfZIbsMoHLEsHb%2B&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231215T124758Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFOUYCVPYX%2F20231215%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=606eaa9953918eaa6befb0407e3144ad9d9cd6b8f491c2fded6245bd77f88e11","width":100,"height":100},"SMALL":{"url":"https://edamam-product-images.s3.amazonaws.com/web-img/ff1/ff14b678699c5e2cc9380c072a5419c4-m.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEPz%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJGMEQCIDjtAFQ34ujshUW%2FkV1BuJ7YrkQiIvCPcJelgB1OTxwKAiAPwEuaCVGivQJI%2F0oruCb7uIGNsG%2BPaaepWQCRRsSgqyq5BQh1EAAaDDE4NzAxNzE1MDk4NiIMR%2BpOeHj0w7tLVjFjKpYFlpmoEZXVoiLueHTyrRaPRZp2TiD0df9MtZn1pCjiAlceisaW%2F%2FqoqsPOe8xKhWCQQVFq4AUFF%2FdeCh9N6OqyVeDZmd5AB%2BuyqSwOqU2SFewiB%2F6EwrIU0FVZwqzHpAK04tH%2Be8fniQj%2FGSXSIVoRbWiAVkmOX8U14JGZXVowC4XqMEF5Z4koveJSZ%2Fu165hXhrUkcHHjR0%2BWHVI%2BzQcQhx%2BBhrw1j5YDlDMNekWJDTxFDycwprMjaugIO9rg9BtWonV8rMv0GnqznhzmoqxShJZDfJraRpVjpVpCVLTpTND4bDv9pFznDxKpo4ru9BrugNZERuB27MamREKCzipBWlAxA3iu%2FIRZ%2B%2BKTXyjbYmoQfOP6VoAX2BNo3yi7E674eaBfcOJvKloGgHypnZr3QkeViMpfZSuJWGlk09UMS2LO8MGAfZMj3Y%2FvR6vKvM9K%2FurvJwM7smJUn91hzgTJeLIleDvyF5ON%2BkWqt%2FgMf39%2FrR2OR3DwtgXxHTWAfsLSpXAyEqt6nJ6XV6k7Ws3HnC%2FKpixdPn5%2BKiSTtiuzqprasCg4bt%2B47lO4aHxhBwIq90%2FkYn07lBgCQaSze3XFmhJFFP6VyCMsyAinBoP7jwTWjOKLrwLyHU6PQP4oEFNSJfIaz4Jov01UCphkwbxsMtv0R%2BXex6AbVXeQiPXkRXm4Jmf8Ol9hVcQjsjMG8MiSbSHEARfFlCnmefx13wTrfduAHSqMS9fKmdzefO7OvblM852Ky2gWt1jPpsRzQ6vYHU%2FiCrw4aSnl4riBIgKwwS3GuxfcLAfdckVATanWI1wM8BV1hLSQPMz1HUhpM0oRwPl%2Bwgbk%2FNHUCBF1ddSRWEt%2BCOYYh%2BGQYP9B1du6%2B2IhCHP9%2BvgwgfDwqwY6sgHfA99dWe6%2B0fzfNSVR1pBg7JW9WXNgabQgFs%2B9TUNzEV3nst6BgfhY75eZ2FYf3WgzEoEEeP%2F8qoMvcixdW8uF5Isg1v7uPoivopSIREw9RE7AFsY2oRy%2BvwKpYoR9rGU%2BBWkFEUmDBhkczoFnVw%2FxKlL4AUQ2iJsiKRuAmo4p4ZIzCQO%2Fkw244eUTPgWlUCkHyQgZ%2BRh9rGZqyYZ1r%2Bhbt%2FvUaztSWvfZIbsMoHLEsHb%2B&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231215T124758Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFOUYCVPYX%2F20231215%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=15f7ed2a12d5716c40e85e728be6f30195c03f7c5e40a0425ae7764224eaa0e8","width":200,"height":200},"REGULAR":{"url":"https://edamam-product-images.s3.amazonaws.com/web-img/ff1/ff14b678699c5e2cc9380c072a5419c4.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEPz%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJGMEQCIDjtAFQ34ujshUW%2FkV1BuJ7YrkQiIvCPcJelgB1OTxwKAiAPwEuaCVGivQJI%2F0oruCb7uIGNsG%2BPaaepWQCRRsSgqyq5BQh1EAAaDDE4NzAxNzE1MDk4NiIMR%2BpOeHj0w7tLVjFjKpYFlpmoEZXVoiLueHTyrRaPRZp2TiD0df9MtZn1pCjiAlceisaW%2F%2FqoqsPOe8xKhWCQQVFq4AUFF%2FdeCh9N6OqyVeDZmd5AB%2BuyqSwOqU2SFewiB%2F6EwrIU0FVZwqzHpAK04tH%2Be8fniQj%2FGSXSIVoRbWiAVkmOX8U14JGZXVowC4XqMEF5Z4koveJSZ%2Fu165hXhrUkcHHjR0%2BWHVI%2BzQcQhx%2BBhrw1j5YDlDMNekWJDTxFDycwprMjaugIO9rg9BtWonV8rMv0GnqznhzmoqxShJZDfJraRpVjpVpCVLTpTND4bDv9pFznDxKpo4ru9BrugNZERuB27MamREKCzipBWlAxA3iu%2FIRZ%2B%2BKTXyjbYmoQfOP6VoAX2BNo3yi7E674eaBfcOJvKloGgHypnZr3QkeViMpfZSuJWGlk09UMS2LO8MGAfZMj3Y%2FvR6vKvM9K%2FurvJwM7smJUn91hzgTJeLIleDvyF5ON%2BkWqt%2FgMf39%2FrR2OR3DwtgXxHTWAfsLSpXAyEqt6nJ6XV6k7Ws3HnC%2FKpixdPn5%2BKiSTtiuzqprasCg4bt%2B47lO4aHxhBwIq90%2FkYn07lBgCQaSze3XFmhJFFP6VyCMsyAinBoP7jwTWjOKLrwLyHU6PQP4oEFNSJfIaz4Jov01UCphkwbxsMtv0R%2BXex6AbVXeQiPXkRXm4Jmf8Ol9hVcQjsjMG8MiSbSHEARfFlCnmefx13wTrfduAHSqMS9fKmdzefO7OvblM852Ky2gWt1jPpsRzQ6vYHU%2FiCrw4aSnl4riBIgKwwS3GuxfcLAfdckVATanWI1wM8BV1hLSQPMz1HUhpM0oRwPl%2Bwgbk%2FNHUCBF1ddSRWEt%2BCOYYh%2BGQYP9B1du6%2B2IhCHP9%2BvgwgfDwqwY6sgHfA99dWe6%2B0fzfNSVR1pBg7JW9WXNgabQgFs%2B9TUNzEV3nst6BgfhY75eZ2FYf3WgzEoEEeP%2F8qoMvcixdW8uF5Isg1v7uPoivopSIREw9RE7AFsY2oRy%2BvwKpYoR9rGU%2BBWkFEUmDBhkczoFnVw%2FxKlL4AUQ2iJsiKRuAmo4p4ZIzCQO%2Fkw244eUTPgWlUCkHyQgZ%2BRh9rGZqyYZ1r%2Bhbt%2FvUaztSWvfZIbsMoHLEsHb%2B&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231215T124758Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFOUYCVPYX%2F20231215%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=9b2dcb270b0fb581de73d30b518364c920dd47bbf18a44db55a82901204eb7c8","width":300,"height":300}},"source":"Delish","url":"http://www.delish.com/cooking/recipe-ideas/recipes/a16276/all-purpose-croutons-recipe-rs0711/","shareAs":"http://www.edamam.com/recipe/all-purpose-croutons-f2de3b23411c7bb4f49b623e80b109ac/hotdog","yield":4.0,"dietLabels":["Low-Sodium"],"healthLabels":["Sugar-Conscious","Low Potassium","Kidney-Friendly","Egg-Free","Peanut-Free","Tree-Nut-Free","Soy-Free","Fish-Free","Shellfish-Free","Pork-Free","Crustacean-Free","Celery-Free","Mustard-Free","Sesame-Free","Lupine-Free","Mollusk-Free","Alcohol-Free","Sulfite-Free","Kosher"],"cautions":[],"ingredientLines":["2 hamburger or hotdog buns","3 tbsp. melted unsalted butter","kosher salt","2 tsp. dried herbs (such as oregano, thyme, basil, or an Italian herb mix)"],"ingredients":[{"text":"2 hamburger or hotdog buns","quantity":2.0,"measure":"<unit>","food":"hotdog buns","weight":88.0,"foodCategory":"bread, rolls and tortillas","foodId":"food_acohft1a7s5cgaa0ki2gjbbp1btv","image":"https://www.edamam.com/food-img/dbe/dbe3d135d1336ed19703d359e33d58dd.jpg"},{"text":"3 tbsp. melted unsalted butter","quantity":3.0,"measure":"tablespoon","food":"unsalted butter","weight":42.599999999999994,"foodCategory":"Dairy","foodId":"food_awz3iefajbk1fwahq9logahmgltj","image":"https://www.edamam.com/food-img/713/71397239b670d88c04faa8d05035cab4.jpg"},{"text":"kosher salt","quantity":0.0,"measure":null,"food":"kosher salt","weight":0.7956,"foodCategory":"Condiments and sauces","foodId":"food_a1vgrj1bs8rd1majvmd9ubz8ttkg","image":"https://www.edamam.com/food-img/694/6943ea510918c6025795e8dc6e6eaaeb.jpg"},{"text":"2 tsp. dried herbs (such as oregano, thyme, basil, or an Italian herb mix)","quantity":2.0,"measure":"teaspoon","food":"herbs","weight":2.0,"foodCategory":"Condiments and sauces","foodId":"food_avsq22zadwyb5cb5sl1byaa4mbo8","image":"https://www.edamam.com/food-img/89b/89b37a04e46e052671d73addcb84aa51.jpg"}],"calories":556.482,"totalCO2Emissions":1165.6432414139997,"co2EmissionsClass":"C","totalWeight":132.6,"totalTime":20.0,"cuisineType":["american"],"mealType":["lunch/dinner"],"dishType":["starter"],"totalNutrients":{"ENERC_KCAL":{"label":"Energy","quantity":556.482,"unit":"kcal"},"FAT":{"label":"Fat","quantity":38.138,"unit":"g"},"FASAT":{"label":"Saturated","quantity":22.30856,"unit":"g"},"FATRN":{"label":"Trans","quantity":0.02376,"unit":"g"},"FAMS":{"label":"Monounsaturated","quantity":10.636039999999996,"unit":"g"},"FAPU":{"label":"Polyunsaturated","quantity":2.87246,"unit":"g"},"CHOCDF":{"label":"Carbs","quantity":45.39156,"unit":"g"},"CHOCDF.net":{"label":"Carbohydrates (net)","quantity":43.06756,"unit":"g"},"FIBTG":{"label":"Fiber","quantity":2.324,"unit":"g"},"SUGAR":{"label":"Sugars","quantity":6.46616,"unit":"g"},"PROCNT":{"label":"Protein","quantity":9.1419,"unit":"g"},"CHOLE":{"label":"Cholesterol","quantity":91.58999999999999,"unit":"mg"},"NA":{"label":"Sodium","quantity":440.50600000000003,"unit":"mg"},"CA":{"label":"Calcium","quantity":174.744,"unit":"mg"},"MG":{"label":"Magnesium","quantity":25.491999999999997,"unit":"mg"},"K":{"label":"Potassium","quantity":133.864,"unit":"mg"},"FE":{"label":"Iron","quantity":5.50692,"unit":"mg"},"ZN":{"label":"Zinc","quantity":0.80434,"unit":"mg"},"P":{"label":"Phosphorus","quantity":103.124,"unit":"mg"},"VITA_RAE":{"label":"Vitamin A","quantity":320.70399999999995,"unit":"µg"},"VITC":{"label":"Vitamin C","quantity":2.144,"unit":"mg"},"THIA":{"label":"Thiamin (B1)","quantity":0.49023000000000005,"unit":"mg"},"RIBF":{"label":"Riboflavin (B2)","quantity":0.28382399999999997,"unit":"mg"},"NIA":{"label":"Niacin (B3)","quantity":3.795092,"unit":"mg"},"VITB6A":{"label":"Vitamin B6","quantity":0.067718,"unit":"mg"},"FOLDFE":{"label":"Folate equivalent (total)","quantity":122.03800000000001,"unit":"µg"},"FOLFD":{"label":"Folate (food)","quantity":42.837999999999994,"unit":"µg"},"FOLAC":{"label":"Folic acid","quantity":46.64,"unit":"µg"},"VITB12":{"label":"Vitamin B12","quantity":0.24842000000000003,"unit":"µg"},"VITD":{"label":"Vitamin D","quantity":0.0,"unit":"µg"},"TOCPHA":{"label":"Vitamin E","quantity":1.3755199999999996,"unit":"mg"},"VITK1":{"label":"Vitamin K","quantity":41.406000000000006,"unit":"µg"},"WATER":{"label":"Water","quantity":37.24099999999999,"unit":"g"}},"totalDaily":{"ENERC_KCAL":{"label":"Energy","quantity":27.824099999999998,"unit":"%"},"FAT":{"label":"Fat","quantity":58.67384615384615,"unit":"%"},"FASAT":{"label":"Saturated","quantity":111.54279999999999,"unit":"%"},"CHOCDF":{"label":"Carbs","quantity":15.13052,"unit":"%"},"FIBTG":{"label":"Fiber","quantity":9.296,"unit":"%"},"PROCNT":{"label":"Protein","quantity":18.2838,"unit":"%"},"CHOLE":{"label":"Cholesterol","quantity":30.529999999999994,"unit":"%"},"NA":{"label":"Sodium","quantity":18.35441666666667,"unit":"%"},"CA":{"label":"Calcium","quantity":17.474400000000003,"unit":"%"},"MG":{"label":"Magnesium","quantity":6.069523809523809,"unit":"%"},"K":{"label":"Potassium","quantity":2.8481702127659574,"unit":"%"},"FE":{"label":"Iron","quantity":30.594,"unit":"%"},"ZN":{"label":"Zinc","quantity":7.312181818181819,"unit":"%"},"P":{"label":"Phosphorus","quantity":14.732,"unit":"%"},"VITA_RAE":{"label":"Vitamin A","quantity":35.63377777777777,"unit":"%"},"VITC":{"label":"Vitamin C","quantity":2.3822222222222225,"unit":"%"},"THIA":{"label":"Thiamin (B1)","quantity":40.852500000000006,"unit":"%"},"RIBF":{"label":"Riboflavin (B2)","quantity":21.83261538461538,"unit":"%"},"NIA":{"label":"Niacin (B3)","quantity":23.719324999999998,"unit":"%"},"VITB6A":{"label":"Vitamin B6","quantity":5.209076923076923,"unit":"%"},"FOLDFE":{"label":"Folate equivalent (total)","quantity":30.509500000000003,"unit":"%"},"VITB12":{"label":"Vitamin B12","quantity":10.350833333333334,"unit":"%"},"VITD":{"label":"Vitamin D","quantity":0.0,"unit":"%"},"TOCPHA":{"label":"Vitamin E","quantity":9.17013333333333,"unit":"%"},"VITK1":{"label":"Vitamin K","quantity":34.505,"unit":"%"}},"digest":[{"label":"Fat","tag":"FAT","schemaOrgTag":"fatContent","total":38.138,"hasRDI":true,"daily":58.67384615384615,"unit":"g","sub":[{"label":"Saturated","tag":"FASAT","schemaOrgTag":"saturatedFatContent","total":22.30856,"hasRDI":true,"daily":111.54279999999999,"unit":"g"},{"label":"Trans","tag":"FATRN","schemaOrgTag":"transFatContent","total":0.02376,"hasRDI":false,"daily":0.0,"unit":"g"},{"label":"Monounsaturated","tag":"FAMS","schemaOrgTag":null,"total":10.636039999999996,"hasRDI":false,"daily":0.0,"unit":"g"},{"label":"Polyunsaturated","tag":"FAPU","schemaOrgTag":null,"total":2.87246,"hasRDI":false,"daily":0.0,"unit":"g"}]},{"label":"Carbs","tag":"CHOCDF","schemaOrgTag":"carbohydrateContent","total":45.39156,"hasRDI":true,"daily":15.13052,"unit":"g","sub":[{"label":"Carbs (net)","tag":"CHOCDF.net","schemaOrgTag":null,"total":43.06756,"hasRDI":false,"daily":0.0,"unit":"g"},{"label":"Fiber","tag":"FIBTG","schemaOrgTag":"fiberContent","total":2.324,"hasRDI":true,"daily":9.296,"unit":"g"},{"label":"Sugars","tag":"SUGAR","schemaOrgTag":"sugarContent","total":6.46616,"hasRDI":false,"daily":0.0,"unit":"g"},{"label":"Sugars, added","tag":"SUGAR.added","schemaOrgTag":null,"total":0.0,"hasRDI":false,"daily":0.0,"unit":"g"}]},{"label":"Protein","tag":"PROCNT","schemaOrgTag":"proteinContent","total":9.1419,"hasRDI":true,"daily":18.2838,"unit":"g"},{"label":"Cholesterol","tag":"CHOLE","schemaOrgTag":"cholesterolContent","total":91.58999999999999,"hasRDI":true,"daily":30.529999999999994,"unit":"mg"},{"label":"Sodium","tag":"NA","schemaOrgTag":"sodiumContent","total":440.50600000000003,"hasRDI":true,"daily":18.35441666666667,"unit":"mg"},{"label":"Calcium","tag":"CA","schemaOrgTag":null,"total":174.744,"hasRDI":true,"daily":17.474400000000003,"unit":"mg"},{"label":"Magnesium","tag":"MG","schemaOrgTag":null,"total":25.491999999999997,"hasRDI":true,"daily":6.069523809523809,"unit":"mg"},{"label":"Potassium","tag":"K","schemaOrgTag":null,"total":133.864,"hasRDI":true,"daily":2.8481702127659574,"unit":"mg"},{"label":"Iron","tag":"FE","schemaOrgTag":null,"total":5.50692,"hasRDI":true,"daily":30.594,"unit":"mg"},{"label":"Zinc","tag":"ZN","schemaOrgTag":null,"total":0.80434,"hasRDI":true,"daily":7.312181818181819,"unit":"mg"},{"label":"Phosphorus","tag":"P","schemaOrgTag":null,"total":103.124,"hasRDI":true,"daily":14.732,"unit":"mg"},{"label":"Vitamin A","tag":"VITA_RAE","schemaOrgTag":null,"total":320.70399999999995,"hasRDI":true,"daily":35.63377777777777,"unit":"µg"},{"label":"Vitamin C","tag":"VITC","schemaOrgTag":null,"total":2.144,"hasRDI":true,"daily":2.3822222222222225,"unit":"mg"},{"label":"Thiamin (B1)","tag":"THIA","schemaOrgTag":null,"total":0.49023000000000005,"hasRDI":true,"daily":40.852500000000006,"unit":"mg"},{"label":"Riboflavin (B2)","tag":"RIBF","schemaOrgTag":null,"total":0.28382399999999997,"hasRDI":true,"daily":21.83261538461538,"unit":"mg"},{"label":"Niacin (B3)","tag":"NIA","schemaOrgTag":null,"total":3.795092,"hasRDI":true,"daily":23.719324999999998,"unit":"mg"},{"label":"Vitamin B6","tag":"VITB6A","schemaOrgTag":null,"total":0.067718,"hasRDI":true,"daily":5.209076923076923,"unit":"mg"},{"label":"Folate equivalent (total)","tag":"FOLDFE","schemaOrgTag":null,"total":122.03800000000001,"hasRDI":true,"daily":30.509500000000003,"unit":"µg"},{"label":"Folate (food)","tag":"FOLFD","schemaOrgTag":null,"total":42.837999999999994,"hasRDI":false,"daily":0.0,"unit":"µg"},{"label":"Folic acid","tag":"FOLAC","schemaOrgTag":null,"total":46.64,"hasRDI":false,"daily":0.0,"unit":"µg"},{"label":"Vitamin B12","tag":"VITB12","schemaOrgTag":null,"total":0.24842000000000003,"hasRDI":true,"daily":10.350833333333334,"unit":"µg"},{"label":"Vitamin D","tag":"VITD","schemaOrgTag":null,"total":0.0,"hasRDI":true,"daily":0.0,"unit":"µg"},{"label":"Vitamin E","tag":"TOCPHA","schemaOrgTag":null,"total":1.3755199999999996,"hasRDI":true,"daily":9.17013333333333,"unit":"mg"},{"label":"Vitamin K","tag":"VITK1","schemaOrgTag":null,"total":41.406000000000006,"hasRDI":true,"daily":34.505,"unit":"µg"},{"label":"Sugar alcohols","tag":"Sugar.alcohol","schemaOrgTag":null,"total":0.0,"hasRDI":false,"daily":0.0,"unit":"g"},{"label":"Water","tag":"WATER","schemaOrgTag":null,"total":37.24099999999999,"hasRDI":false,"daily":0.0,"unit":"g"}],"instructionLines":["Heat oven to 375 degrees F.","Cut the buns into 1-inch pieces and place on a baking sheet lined with parchment paper or aluminum foil.","Drizzle with the butter, sprinkle with 1/2 teaspoon salt and the herbs, and toss.","Bake, turning once, until crisp and golden, about 15 minutes.","Transfer the sheet of parchment or foil and croutons off the pan to a counter to cool. (You can store the croutons in a resealable plastic bag in the refrigerator for up to 5 days or in the freezer for up to 3 months.)"]},"_links":{"self":{"href":"https://api.edamam.com/api/recipes/v2/f2de3b23411c7bb4f49b623e80b109ac?type=public&app_id=f6891638&app_key=2b61efbfbbfbb9a8e99618f31d0ec561","title":"Self"}}}
            }
    }
}