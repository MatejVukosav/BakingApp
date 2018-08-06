package com.vuki.bakingapp.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

/**
 * Created by mvukosav
 */
public abstract class BaseActivity extends AppCompatActivity {

    public void setupToolbar( Toolbar toolbar, String title ) {
        if ( toolbar != null ) {
            setSupportActionBar( toolbar );
            ActionBar actionBar = getSupportActionBar();
            if ( actionBar != null ) {
                actionBar.setDisplayHomeAsUpEnabled( true );
                actionBar.setDisplayShowHomeEnabled( true );
                actionBar.setTitle( TextUtils.isEmpty( title ) ? " " : title );
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
