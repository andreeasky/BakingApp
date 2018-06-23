package com.example.user.bakingapp;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    private static final String RECIPE_ID = "id";

    private static final String RECIPE_NAME = "name";

    private static final String RECIPE_INGREDIENTS = "ingredients";

    private static final String RECIPE_STEPS = "steps";

    private static final String RECIPE_SERVINGS = "servings";

    private static final String RECIPE_IMAGE = "image";

    private static final String INGREDIENT_QUANTITY = "quantity";

    private static final String INGREDIENT_MEASURE = "measure";

    private static final String INGREDIENT = "ingredient";

    private static final String RECIPE_STEP_ID = "id";

    private static final String RECIPE_SHORT_DESCRIPTION = "shortDescription";

    private static final String RECIPE_DESCRIPTION = "description";

    private static final String RECIPE_VIDEO_URL = "videoURL";

    private static final String RECIPE_THUMBNAIL_URL = "thumbnailURL";


    // URL to fetch data from the Baking App from Udacity
    private static final String BAKING_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL buildURL() {

        URL url = null;

        try {
            url = new URL(BAKING_URL.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in,"UTF-8");
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<Recipe> fetchRecipeData() {

        URL url = buildURL();

        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractRecipesFromJson(jsonResponse);
    }

    private static ArrayList<Recipe> extractRecipesFromJson(String recipeJSON) {

        if (TextUtils.isEmpty(recipeJSON)) {
            return null;
        }

        ArrayList<Recipe> recipes = new ArrayList<>();

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        ArrayList<Step> steps = new ArrayList<>();

        try {

            JSONArray recipesArray = new JSONArray(recipeJSON);

            int i = 0;

            int e = 0;

            int u = 0;

            for (i = 0; i < recipesArray.length(); i++) {

                JSONObject currentRecipe = recipesArray.optJSONObject(i);

                int id;

                String name = "";

                int servings;

                String image = "";

                id = currentRecipe.optInt(  RECIPE_ID );

                name = currentRecipe.optString( RECIPE_NAME  );

                servings = currentRecipe.optInt(RECIPE_SERVINGS );

                image = currentRecipe.optString(RECIPE_IMAGE );

                JSONArray recipeIngredients = currentRecipe.optJSONArray(RECIPE_INGREDIENTS);

                ingredients = new ArrayList<>();

                for (e = 0; e < recipeIngredients.length(); e++) {

                    JSONObject recipeIngredientsObject = recipeIngredients.getJSONObject(e);

                    int quantity;

                    String measure = "";

                    String ingredient = "";

                    quantity =  recipeIngredientsObject.optInt( INGREDIENT_QUANTITY  );

                    measure = recipeIngredientsObject.optString(  INGREDIENT_MEASURE );

                    ingredient = recipeIngredientsObject.optString(  INGREDIENT );

                    Ingredient recipeIngredient = new Ingredient(quantity, measure, ingredient );

                    ingredients.add(recipeIngredient);
                }

                JSONArray recipeSteps = currentRecipe.optJSONArray(RECIPE_STEPS);

                steps = new ArrayList<>();

                for (u = 0; u < recipeSteps.length(); u++) {

                    JSONObject recipeStepsObject = recipeSteps.getJSONObject(u);

                    int recipeId;

                    String shortDescription = "";

                    String description = "";

                    String videoURL = "";

                    String thumbnailURL = "";

                    recipeId = recipeStepsObject.optInt( RECIPE_STEP_ID  );

                    shortDescription = recipeStepsObject.optString( RECIPE_SHORT_DESCRIPTION );

                    description = recipeStepsObject.optString( RECIPE_DESCRIPTION );

                    videoURL = recipeStepsObject.optString( RECIPE_VIDEO_URL );

                    thumbnailURL = recipeStepsObject.optString(  RECIPE_THUMBNAIL_URL );

                    Step step = new Step(recipeId, shortDescription, description, videoURL, thumbnailURL );

                    steps.add(step);
                }

                Recipe recipe = new Recipe(id, name, ingredients, steps, servings, image);

                recipes.add(recipe);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }

}
