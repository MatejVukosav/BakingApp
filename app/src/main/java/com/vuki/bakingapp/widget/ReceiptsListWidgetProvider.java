package com.vuki.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.helpers.DataHelper;
import com.vuki.bakingapp.models.ApiReceipt;
import com.vuki.bakingapp.models.ApiReceipts;

import java.util.List;

/**
 * Created by mvukosav
 */
public class ReceiptsListWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds ) {
        super.onUpdate( context, appWidgetManager, appWidgetIds );

        // Perform this loop procedure for each App Widget that belongs to this provider
        for ( int appWidgetId : appWidgetIds ) {
            RemoteViews views = new RemoteViews( context.getPackageName(), R.layout.widget_receipts );
            views.setTextViewText( R.id.title, getLastOpenedReceiptIngredients( context ) );
            appWidgetManager.updateAppWidget( appWidgetId, views );
        }
    }

    public String getLastOpenedReceiptIngredients( Context context ) {
        ApiReceipts receiptsFromDatabase = DataHelper.getReceiptsFromDatabase( context );
        List<ApiReceipt> receipts = receiptsFromDatabase.getReceipts();
        StringBuilder builder = new StringBuilder( "" );
        for ( ApiReceipt receipt : receipts ) {
            builder.append( receipt.getName() )
                    .append( System.lineSeparator() );
        }
        return builder.toString();
    }
}
