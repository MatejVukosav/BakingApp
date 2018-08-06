package com.vuki.bakingapp.ui.details;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.ItemStepBinding;
import com.vuki.bakingapp.models.ApiSteps;

import java.util.List;

/**
 * Created by mvukosav
 */
public class ReceiptDetailsActivityRecyclerViewAdapter extends RecyclerView.Adapter<ReceiptDetailsActivityRecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick( int position );
    }

    private Context context;
    private List<ApiSteps> list;
    private OnItemClickListener onItemClickListener;

    public ReceiptDetailsActivityRecyclerViewAdapter( Context context, List<ApiSteps> list,
                                                      OnItemClickListener onItemClickListener ) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        final ItemStepBinding binding = DataBindingUtil
                .inflate( LayoutInflater.from( parent.getContext() ),
                        R.layout.item_step, parent, false );
        return new ViewHolder( binding );
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position ) {
        ApiSteps item = list.get( position );
        holder.bind( item, onItemClickListener );

        holder.binding.title.setText( position == 0 ? item.getShortDescription() : position+". " + item.getShortDescription() );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemStepBinding binding;

        public ViewHolder( ItemStepBinding binding ) {
            super( binding.getRoot() );
            this.binding = binding;
        }

        public void bind( final ApiSteps model,
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