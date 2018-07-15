package com.vuki.bakingapp.ui.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.ItemRecipeBinding;
import com.vuki.bakingapp.models.ApiReceipt;

import java.util.List;

/**
 * Created by mvukosav
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick( int position );
    }

    private Context context;
    private List<ApiReceipt> list;
    private OnItemClickListener onItemClickListener;

    public HomeRecyclerViewAdapter( Context context, List<ApiReceipt> list,
                                    OnItemClickListener onItemClickListener ) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        final ItemRecipeBinding binding = DataBindingUtil
                .inflate( LayoutInflater.from( parent.getContext() ),
                        R.layout.item_recipe, parent, false );
        return new ViewHolder( binding );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position ) {
        ApiReceipt item = list.get( position );
        holder.bind( item, onItemClickListener );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemRecipeBinding binding;

        public ViewHolder( ItemRecipeBinding binding ) {
            super( binding.getRoot() );
            this.binding = binding;
        }

        public void bind( final ApiReceipt model,
                          final OnItemClickListener listener ) {
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    listener.onItemClick( getLayoutPosition() );

                }
            } );
        }
    }

}