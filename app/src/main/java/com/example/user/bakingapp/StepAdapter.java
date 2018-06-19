package com.example.user.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

        ArrayList<Step> steps = new ArrayList<>();
        private String recipeStep = "shortDescription";
        private OnItemClicked onClick;
        Context context;


        public StepAdapter (ArrayList<Step> steps, Context context, StepAdapter.OnItemClicked onItemClicked ) {
            this.steps = steps;
            this.context = context;
            // The third parameter here is the interface
            onClick = onItemClicked;
        }


        public void setOnClick() {
        }

        public interface OnItemClicked {

            void onItemClick(int position);
        }

        public class StepAdapterViewHolder extends RecyclerView.ViewHolder {

            TextView stepDescription;

            public StepAdapterViewHolder(View view) {
                super(view);
                stepDescription = (TextView) view.findViewById(R.id.step);

            }
        }

        @Override
        public StepAdapter.StepAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            Context context = viewGroup.getContext();
            int layoutIdForListItem = R.layout.fragment_step;
            LayoutInflater inflater = LayoutInflater.from(context);
            boolean shouldAttachToParentImmediately = false;

            View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
            return new StepAdapter.StepAdapterViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final StepAdapter.StepAdapterViewHolder stepAdapterViewHolder, final int position) {
            final Step step = steps.get( position );
            recipeStep = step.getStepShortDescription();

            if (!step.getStepShortDescription().isEmpty()) {
                stepAdapterViewHolder.stepDescription.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        // Here we are calling the onItemClick() method in the Fragment
                        // Because onClick has a reference to the RecipeFragment
                        onClick.onItemClick( position );
                    }
                });
                stepAdapterViewHolder.stepDescription.setText(step.getStepShortDescription());
            }
        }

        @Override
        public int getItemCount() {
            if (null == steps) return 0;
            return steps.size();
        }

        public void addAll(ArrayList<Step> steps) {
            this.steps = steps;
            notifyDataSetChanged();
        }

        public void setOnClick(StepAdapter.OnItemClicked onClick)
        {
            this.onClick=onClick;
        }

}
