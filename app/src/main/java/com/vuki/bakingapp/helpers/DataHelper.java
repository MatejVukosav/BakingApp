package com.vuki.bakingapp.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.Nullable;

import com.squareup.moshi.Moshi;
import com.vuki.bakingapp.models.ApiReceipt;
import com.vuki.bakingapp.models.ApiReceipts;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mvukosav
 */
public class DataHelper {

    public static String DATA_FILE = "recipts_example";
    public static int INVALID_RECIPE_ID = -1;

    public static String AssetJSONFile( String filename, Context context ) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open( filename );
        byte[] formArray = new byte[file.available()];
        file.read( formArray );
        file.close();

        return new String( formArray );
    }

    public static ApiReceipts getReceiptsFromDatabase( Context context ) {

        Moshi moshi = new Moshi.Builder().build();
        ApiReceipts receipts = null;
        String jsonLocation = "";
        try {
            jsonLocation = DataHelper.AssetJSONFile( DataHelper.DATA_FILE, context );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        try {
            receipts = moshi.adapter( ApiReceipts.class ).fromJson( jsonLocation );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return receipts;
    }

    public static @Nullable
    ApiReceipt getReceiptById( Context context, int receiptId ) {
        ApiReceipts receipts = getReceiptsFromNetwork( context );
        for ( ApiReceipt receipt : receipts.getReceipts() ) {
            if ( receipt.getId() == receiptId ) {
                return receipt;
            }
        }
        return null;
    }

    public static ApiReceipts getReceiptsFromNetwork( Context context ) {

        Moshi moshi = new Moshi.Builder().build();
        ApiReceipts receipts = null;
        String jsonLocation = "";
        try {
            jsonLocation = DataHelper.AssetJSONFile( DataHelper.DATA_FILE, context );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        try {
            receipts = moshi.adapter( ApiReceipts.class ).fromJson( jsonLocation );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return receipts;
    }
}
