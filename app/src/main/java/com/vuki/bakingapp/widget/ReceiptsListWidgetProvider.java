package com.vuki.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.vuki.bakingapp.R;

/**
 * Created by mvukosav
 */
public class ReceiptsListWidgetProvider extends AppWidgetProvider {

    @Override
    public void onReceive( Context context, Intent intent ) {
        AppWidgetManager mgr = AppWidgetManager.getInstance( context );
//        Toast.makeText( context, "Touched view " + mgr.toString(), Toast.LENGTH_SHORT ).show();

        super.onReceive( context, intent );
    }

    @Override
    public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds ) {
        super.onUpdate( context, appWidgetManager, appWidgetIds );

        // Perform this loop procedure for each App Widget that belongs to this provider
        for ( int appWidgetId : appWidgetIds ) {
            RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.widget_receipts );
            views.setTextViewText( R.id.title, getLastOpenedReceiptIngredients() );

            appWidgetManager.updateAppWidget( appWidgetId, views );

        }
    }

    public String getLastOpenedReceiptIngredients() {
        String ingredients = "First choose receipt in the app to show ingredients";




        return ingredients;
    }
}
