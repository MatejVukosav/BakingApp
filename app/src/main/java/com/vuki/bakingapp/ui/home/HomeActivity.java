package com.vuki.bakingapp.ui.home;

import android.content.Context;
import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.moshi.Moshi;
import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.ActivityMainBinding;
import com.vuki.bakingapp.models.ApiReceipt;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements HomeActivityContract.View, HomeRecyclerViewAdapter.OnItemClickListener {

    private ActivityMainBinding binding;
    private HomeRecyclerViewAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        Moshi moshi = new Moshi.Builder().build();

        String jsonLocation = "";
        try {
            jsonLocation = AssetJSONFile( "recipts_example", this );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        List<ApiReceipt> items = null;
        try {
            items = moshi.adapter( List.class ).fromJson( jsonLocation );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        adapter = new HomeRecyclerViewAdapter( this, items, this );
        binding.recyclerView.setAdapter( adapter );
    }

    public static String AssetJSONFile( String filename, Context context ) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open( filename );
        byte[] formArray = new byte[file.available()];
        file.read( formArray );
        file.close();

        return new String( formArray );
    }

    @Override
    public void onItemClick( int position ) {

    }
}
