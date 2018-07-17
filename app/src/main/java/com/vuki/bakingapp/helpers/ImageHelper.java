package com.vuki.bakingapp.helpers;

import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by mvukosav
 */
public class ImageHelper {

    public static void fetchFromNetwork( ImageView target, String url ) {
        if ( TextUtils.isEmpty( url.trim() ) ) {
            return;
        }
        Picasso picasso = Picasso.get();
//        picasso.setIndicatorsEnabled( true );
        picasso.load( url )
                .placeholder( android.R.drawable.ic_menu_report_image )
                .error( android.R.drawable.stat_notify_error )
                .into( target );
    }
}
