package com.example.user.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {

    ArrayList<Ingredient> ingredients = new ArrayList<>();
    private String recipeIngredient = "ingredient";
    Context context;


    public IngredientAdapter(ArrayList<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName;

        public IngredientAdapterViewHolder(View view) {
            super(view);
            ingredientName = (TextView) view.findViewById(R.id.ingredient);

        }
    }

    @Override
    public IngredientAdapter.IngredientAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.fragment_ingredient;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new IngredientAdapter.IngredientAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final IngredientAdapterViewHolder ingredientAdapterViewHolder, int position) {
        Ingredient ingredient = ingredients.get( position );
        recipeIngredient = ingredient.getIngredient();
        ingredientAdapterViewHolder.ingredientName.setText( ingredient.getIngredient());
        if (!ingredient.getIngredient().isEmpty()) {
            ingredientAdapterViewHolder.ingredientName.setText( ingredient.getIngredient());
        }

    }

    @Override
    public int getItemCount() {
        if (null == ingredients) return 0;
        return ingredients.size();
    }

    public void addAll(ArrayList<Ingredient> ingredient) {
        this.ingredients = ingredient;
        notifyDataSetChanged();
    }

}


