package com.example.user.bakingapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

    /**
     * WidgetRemoteViews acts as the adapter for the collection view widget,
     * providing RemoteViews to the widget in the getViewAt method.
     */
    public class WidgetRemoteViews implements RemoteViewsService.RemoteViewsFactory {

        private static final String TAG = "WidgetRemoteViews";

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Context mContext = null;

        public WidgetRemoteViews(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {
            initData();
        }

        @Override
        public void onDataSetChanged() {
            initData();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews view = new RemoteViews(mContext.getPackageName(),
                    R.layout.widget_ingredient);
            view.setTextViewText(R.id.widget_ingredient, (CharSequence) ingredients.get(position) );
            return view;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        private void initData() {
            ingredients.clear();
            for (int i = 1; i <= 10; i++) {
                ingredients.add();

            }
        }


    }

