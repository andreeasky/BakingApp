package com.example.user.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable{

        /** Property id */
        int id;

        /** Property name */
        String name;

        /** Property ingredients */
        ArrayList <Ingredient> ingredients = new ArrayList<>(  );

        /** Property steps */
        ArrayList <Step> steps = new ArrayList<>(  );

        /** Property servings */
        int servings;

        /** Property image */
        String image;

        /**
         * Constructor
         */
        public Recipe(int id, String name, ArrayList <Ingredient> ingredients, ArrayList <Step> steps, int servings, String image) {

            this.id = id;
            this.name = name;
            this.ingredients = ingredients;
            this.steps = steps;
            this.servings = servings;
            this.image = image;

        }

        /**
         * Constructor
         * @param position
         */
        public Recipe(int position) {
        }

        public Recipe(Parcel in) {
             id = in.readInt();
            name = in.readString();
            in.readTypedList( ingredients ,Ingredient.CREATOR );
            in.readTypedList( steps ,Step.CREATOR );
            servings = in.readInt();
            image = in.readString();

        }

        /**
         * Gets the Recipe id
         *
         */
        public int getRecipeId() {return this.id;
        }

        /**
         * Sets the Recipe id
         */
        public void setRecipeId() {this.id = id;
        }

        /**
         * Gets the Recipe name
         */
        public String getRecipeName() {
            return this.name;
        }

        /**
         * Sets the Recipe name
         */
        public void setRecipeName() {
            this.name = name;
        }

        /**
         * Gets the Recipe ingredients
         */
        public ArrayList <Ingredient> getRecipeIngredients() {
            return this.ingredients;
        }

        /**
         * Sets the Recipe ingredients
         */
        public void setRecipeIngredients(ArrayList <Ingredient> ingredients ) {
            this.ingredients = (ArrayList<Ingredient>) ingredients;
        }

        /**
         * Gets the Recipe steps
         */
        public ArrayList <Step> getRecipeSteps() {
            return this.steps;
        }

        /**
         * Sets the Recipe steps
         */
        public void setRecipeSteps(ArrayList <Step> steps) {
            this.steps = (ArrayList<Step>) steps;
        }

        /**
        * Gets the Recipe servings
        *
        */
        public int getRecipeServings() {return this.servings;
        }

        /**
        * Sets the Recipe servings
        */
        public void setRecipeServings() {this.servings = servings;
        }

        /**
         * Gets the Recipe image
         */
        public String getRecipeImage() {
            return this.image;
        }

        /**
         * Sets the Recipe image
         */
        public void setRecipeImage() {
            this.image = image;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {

            dest.writeInt(id);
            dest.writeString(name);
            dest.writeTypedList(ingredients);
            dest.writeTypedList(steps);
            dest.writeInt(servings);
            dest.writeString(image);

        }

        public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>()
        {
            public Recipe createFromParcel(Parcel in)
            {
                return new Recipe(in);
            }
            public Recipe[] newArray(int size)
            {
                return new Recipe[size];
            }
        };
    }


