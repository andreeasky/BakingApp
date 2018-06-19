package com.example.user.bakingapp;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

/**
 * Implementation of App WidgetDataProvider functionality.
 */
public class WidgetDataProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
            for (int i = 0; i < appWidgetIds.length; ++i) {

                // Set up the intent that starts the StackViewService, which will
                // provide the views for this collection.
                Intent intent = new Intent(context,WidgetService.class);
                // Add the app widget ID to the intent extras.
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
                intent.setData( Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews remoteViews = new RemoteViews( context.getPackageName(), R.layout.widget_ingredients_list);

                // Set up the collection
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    setRemoteAdapter( context, remoteViews );
                } else {
                    setRemoteAdapterV11( context, remoteViews );
                }

                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget( appWidgetIds, remoteViews );
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param remoteViews RemoteViews to set the RemoteAdapter
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews remoteViews) {
        remoteViews.setRemoteAdapter( R.id.widget_list,
                new Intent( context, WidgetService.class ) );
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param remoteViews RemoteViews to set the RemoteAdapter
     */
    @SuppressWarnings("deprecation")
    private static void setRemoteAdapterV11(Context context, @NonNull final RemoteViews remoteViews) {
        remoteViews.setRemoteAdapter( 0, R.id.widget_list,
                new Intent( context, WidgetService.class ) );
    }
}


