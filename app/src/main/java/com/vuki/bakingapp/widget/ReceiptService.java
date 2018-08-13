package com.vuki.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.vuki.bakingapp.R;

/**
 * Created by mvukosav
 */
public class ReceiptService extends IntentService {

    public static final String ACTION_UPDATE_LAST_OPENED_RECEIPT = "receipt.service.action.update.last.opened.receipt";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * Used to name the worker thread, important only for debugging.
     */
    public ReceiptService() {
        super( "ReceiptService" );
    }

    public static void startActionUpdateWidget( Context context ) {
        Intent intent = new Intent( context, ReceiptService.class );
        intent.setAction( ACTION_UPDATE_LAST_OPENED_RECEIPT );
        context.startService( intent );
    }


    @Override
    protected void onHandleIntent( @Nullable Intent intent ) {
        if ( intent != null ) {
            final String action = intent.getAction();
            if ( ACTION_UPDATE_LAST_OPENED_RECEIPT.equals( action ) ) {
                handleActionUpdateWidget();
            }
        }
    }

    private void handleActionUpdateWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance( this );
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds( new ComponentName( this, ReceiptsListWidgetProvider.class ) );
        appWidgetManager.notifyAppWidgetViewDataChanged( appWidgetIds, R.id.title );
        ReceiptsListWidgetProvider.updateReceiptIngredientsWidgets( this, appWidgetManager, appWidgetIds );
    }

}
