package com.vuki.bakingapp.ui.details;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.FragmentReceiptDetailsBinding;
import com.vuki.bakingapp.models.ApiReceipt;
import com.vuki.bakingapp.models.ApiSteps;

/**
 * Created by mvukosav
 */
public class ReceiptDetailsFragment
        extends android.support.v4.app.Fragment
        implements ReceiptDetailsActivityRecyclerViewAdapter.OnItemClickListener {

    private ApiReceipt receipt;
    private ReceiptDetailsActivityRecyclerViewAdapter adapter;
    private FragmentReceiptDetailsBinding binding;

    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        void onItemSelected( ApiSteps step, int position );
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if ( context instanceof OnItemSelectedListener ) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException( context.toString()
                    + " must implement ReceiptDetailsFragment.OnItemSelectedListener" );
        }
    }

    public static ReceiptDetailsFragment newInstance( ApiReceipt item ) {
        ReceiptDetailsFragment fragmentDemo = new ReceiptDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable( "receipt", item );
        fragmentDemo.setArguments( args );
        return fragmentDemo;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        receipt = (ApiReceipt) getArguments().getSerializable( RecipeDetailsActivity.EXTRA_RECEIPT );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_receipt_details, container, false );
        adapter = new ReceiptDetailsActivityRecyclerViewAdapter( inflater.getContext(), receipt.getSteps(), this );
        binding.recyclerView.setLayoutManager( new LinearLayoutManager( inflater.getContext() ) );
        binding.recyclerView.setAdapter( adapter );
        binding.recyclerView.addItemDecoration(
                new DividerItemDecoration( binding.recyclerView.getContext(),
                        DividerItemDecoration.VERTICAL ) );


        return binding.getRoot();
    }

    @Override
    public void onItemClick( int position ) {
        listener.onItemSelected( receipt.getSteps().get( position ), position );
    }
}
