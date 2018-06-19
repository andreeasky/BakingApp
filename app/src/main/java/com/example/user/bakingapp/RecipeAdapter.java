package com.example.user.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private Recipe recipe;
    private String recipeName = "name";
    Palette.Swatch swatch;
    int recipeImage;
    private OnItemClicked onClick;

    public  RecipeAdapter( ArrayList<Recipe> recipes, Context context, OnItemClicked onItemClicked ) {
            this.recipes = recipes;
            this.context = context;
            onClick = onItemClicked;
        }

        public void setOnClick() {
        }

        public interface OnItemClicked {

            void onItemClick(int position);
        }

        ArrayList<Recipe> recipes = new ArrayList<>();

        Context context;

        public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder {

            TextView  recipeNameTextView;
            ImageView recipeImageView;
            CardView cardView;

            public RecipeAdapterViewHolder(View view) {
                super(view);
                recipeNameTextView = (TextView) view.findViewById(R.id.recipe_name);
                recipeImageView = (ImageView) view.findViewById(R.id.recipe_image);
                cardView = (CardView)view.findViewById(R.id.card_view);
            }
        }

        @Override
        public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            Context context = viewGroup.getContext();
            int layoutIdForListItem = R.layout.recipe_list_item;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;

            View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
            return new RecipeAdapterViewHolder(view);
        }

    @Override
    public void onBindViewHolder(final RecipeAdapterViewHolder recipeAdapterViewHolder, int position) {
        Recipe recipe = recipes.get( position );
        recipeName = recipe.getRecipeName();
        recipeAdapterViewHolder.recipeNameTextView.setText( recipe.getRecipeName() );
        if (!recipe.getRecipeImage().isEmpty()) {
            Picasso.with( context ).load( recipe.getRecipeImage() ).into( recipeAdapterViewHolder.recipeImageView );
            Log.i( "Recipe image", "" + recipe.getRecipeImage() );

               /* Picasso.with( context )
                        .load( recipeImage )
                        .into( recipeAdapterViewHolder.recipeImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Bitmap bitmap = ((BitmapDrawable) recipeAdapterViewHolder.recipeImageView.getDrawable()).getBitmap();
                                Palette.from(bitmap).generate( new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(@NonNull Palette palette) {
                                        swatch = palette.getVibrantSwatch();
                                        if(swatch != null) {
                                            recipeAdapterViewHolder.itemView.setBackgroundColor( swatch.getRgb() );
                                        } else {
                                            swatch = palette.getLightMutedSwatch();
                                            recipeAdapterViewHolder.itemView.setBackgroundColor( swatch.getRgb() );
                                        }
                                    }
                                } );
                            }

                            @Override
                            public void onError() {

                            }
                        } );*/

            }

            else {

                if (recipeName.equals( "Nutella Pie" )) {
                    recipeImage = R.drawable.nutella_pie;
                } else if (recipeName.equals( "Brownies" )) {
                    recipeImage = R.drawable.brownies;
                } else if (recipeName.equals( "Yellow Cake" )) {
                    recipeImage = R.drawable.yellow_cake;
                } else if (recipeName.equals( "Cheesecake" )) {
                    recipeImage = R.drawable.cheesecake;
                }

                Picasso.with( context ).load( recipeImage ).into( recipeAdapterViewHolder.recipeImageView );


        }
        recipeAdapterViewHolder.cardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick( recipeAdapterViewHolder.getAdapterPosition() );
            }
        } );
    }

        @Override
        public int getItemCount() {
            if (null == recipes) return 0;
            return recipes.size();
        }

        public void addAll(ArrayList<Recipe>recipes) {
            this.recipes = recipes;
            notifyDataSetChanged();
        }

        public void setOnClick(OnItemClicked onClick)
        {
            this.onClick=onClick;
        }

    }


