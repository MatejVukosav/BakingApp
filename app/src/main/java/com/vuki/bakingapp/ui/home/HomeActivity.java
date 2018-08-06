package com.vuki.bakingapp.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.ActivityMainBinding;
import com.vuki.bakingapp.helpers.DataHelper;
import com.vuki.bakingapp.models.ApiReceipt;
import com.vuki.bakingapp.models.ApiReceipts;
import com.vuki.bakingapp.ui.details.RecipeDetailsActivity;

public class HomeActivity extends AppCompatActivity
        implements HomeActivityContract.View, HomeRecyclerViewAdapter.OnItemClickListener {

    private ActivityMainBinding binding;
    private HomeRecyclerViewAdapter adapter;
    private ApiReceipts items = null;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        items = DataHelper.getReceiptsFromDatabase(this);
        assert items != null;

        adapter = new HomeRecyclerViewAdapter( this, items.getReceipts(), this );
        binding.recyclerView.setAdapter( adapter );
        if ( getResources().getBoolean( R.bool.isTablet ) ) {
            binding.recyclerView.setLayoutManager( new GridLayoutManager( this, 3 ) );
        } else {
            binding.recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        }

    }

    @Override
    public void onItemClick( int position ) {
        Intent intent = new Intent( HomeActivity.this, RecipeDetailsActivity.class );
        ApiReceipt apiReceipt = items.getReceipts().get( position );
        Bundle bundle = new Bundle();
        bundle.putSerializable( "receipt", apiReceipt );
        intent.putExtras( bundle );
        startActivity( intent );
    }
}
