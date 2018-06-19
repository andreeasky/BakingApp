package com.example.user.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable{

    /** Property quantity */
    int quantity;

    /** Property measure */
    String measure;

    /** Property ingredient */
    String ingredient;

    /**
     * Constructor
     */
    public Ingredient(int quantity, String measure, String ingredient) {

        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;

    }

    /**
     * Constructor
     * @param position
     */
    public Ingredient(int position) {
    }

    public Ingredient(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();

    }

    /**
     * Gets the Ingredient quantity
     *
     */
    public int getIngredientQuantity() {return this.quantity;
    }

    /**
     * Sets the Ingredient quantity
     */
    public void setIngredientQuantity() {this.quantity = quantity;
    }

    /**
     * Gets the Ingredient measure
     */
    public String getIngredientMeasure() {
        return this.measure;
    }

    /**
     * Sets the Ingredient measure
     */
    public void setIngredientMeasure() {
        this.measure = measure;
    }

    /**
     * Gets the ingredient of the Ingredient class
     */
    public String getIngredient() {
        return this.ingredient;
    }

    /**
     * Sets the ingredient of the Ingredient class
     */
    public void setIngredient() {
        this.ingredient = ingredient;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);

    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>()
    {
        public Ingredient createFromParcel(Parcel in)
        {
            return new Ingredient(in);
        }
        public Ingredient[] newArray(int size)
        {
            return new Ingredient[size];
        }
    };
}
