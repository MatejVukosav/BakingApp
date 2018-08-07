package com.vuki.bakingapp.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.ActivityMainBinding;
import com.vuki.bakingapp.models.ApiReceipt;
import com.vuki.bakingapp.network.ApiManager;
import com.vuki.bakingapp.ui.details.RecipeDetailsActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements HomeActivityContract.View, HomeRecyclerViewAdapter.OnItemClickListener {

    private ActivityMainBinding binding;
    private HomeRecyclerViewAdapter adapter;
    private List<ApiReceipt> items = null;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        Call<List<ApiReceipt>> listCall = ApiManager.getInstance().getService().getReceipts();
        listCall.enqueue( new Callback<List<ApiReceipt>>() {
            @Override
            public void onResponse( @NonNull Call<List<ApiReceipt>> call, @NonNull Response<List<ApiReceipt>> response ) {
                items = response.body();
                adapter = new HomeRecyclerViewAdapter( HomeActivity.this, items, HomeActivity.this );
                binding.recyclerView.setAdapter( adapter );
            }

            @Override
            public void onFailure( @NonNull Call<List<ApiReceipt>> call, Throwable t ) {
                Toast.makeText( HomeActivity.this, "Error has occur: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT ).show();
                t.printStackTrace();
            }
        } );

        if ( getResources().getBoolean( R.bool.isTablet ) ) {
            binding.recyclerView.setLayoutManager( new GridLayoutManager( this, 3 ) );
        } else {
            binding.recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        }

    }

    @Override
    public void onItemClick( int position ) {
        Intent intent = new Intent( HomeActivity.this, RecipeDetailsActivity.class );
        ApiReceipt apiReceipt = items.get( position );
        Bundle bundle = new Bundle();
        bundle.putSerializable( "receipt", apiReceipt );
        intent.putExtras( bundle );
        startActivity( intent );
    }
}
