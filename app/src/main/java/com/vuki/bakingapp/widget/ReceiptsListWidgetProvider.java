package com.vuki.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.helpers.DataHelper;
import com.vuki.bakingapp.models.ApiIngredient;
import com.vuki.bakingapp.models.ApiReceipt;
import com.vuki.bakingapp.prefs.SharedPrefsUtils;
import com.vuki.bakingapp.ui.details.RecipeDetailsActivity;
import com.vuki.bakingapp.ui.home.HomeActivity;

/**
 * Created by mvukosav
 */
public class ReceiptsListWidgetProvider extends AppWidgetProvider {

    public static void updateReceiptIngredientsWidgets( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds ) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );
        int receiptId = defaultSharedPreferences.getInt( SharedPrefsUtils.LAST_OPENED_RECEIPT, DataHelper.INVALID_RECIPE_ID );

        Intent intent;
        if ( receiptId == DataHelper.INVALID_RECIPE_ID ) {
            intent = new Intent( context, HomeActivity.class );
        } else {
            // Set on click to open the corresponding detail activity
            Log.d( ReceiptsListWidgetProvider.class.getCanonicalName(), "receiptId=" + receiptId );
            intent = new Intent( context, RecipeDetailsActivity.class );
            ApiReceipt apiReceipt = DataHelper.getReceiptById( context, receiptId );
            Bundle bundle = new Bundle();
            bundle.putSerializable( "receipt", apiReceipt );
            intent.putExtras( bundle );
        }
        PendingIntent pendingIntent = PendingIntent.getActivity( context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );

        for ( int appWidgetId : appWidgetIds ) {
            RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.widget_receipts );
            views.setTextViewText( R.id.title, getLastOpenedReceiptIngredients( context, receiptId ) );
            views.setOnClickPendingIntent( R.id.title, pendingIntent );
            appWidgetManager.updateAppWidget( appWidgetId, views );
        }
    }

    private static String getLastOpenedReceiptIngredients( Context context, int receiptId ) {
        ApiReceipt receipt = DataHelper.getReceiptById( context, receiptId );
        if ( receipt == null ) {
            return " ";
        }

        StringBuilder builder = new StringBuilder( "" );
        for ( ApiIngredient ingredient : receipt.getIngredients() ) {
            builder.append( ingredient.getIngredient() )
                    .append( System.lineSeparator() );
        }
        return builder.toString();
    }

    @Override
    /**
     * Called the first time an instance of your widget is added to the home screen.
     */
    public void onEnabled( Context context ) {
        super.onEnabled( context );
        ReceiptService.startActionUpdateWidget( context );
    }
}
