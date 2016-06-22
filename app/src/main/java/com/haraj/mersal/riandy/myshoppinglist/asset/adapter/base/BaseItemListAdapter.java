package com.haraj.mersal.riandy.myshoppinglist.asset.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.haraj.mersal.riandy.myshoppinglist.asset.adapter.listener.ItemActionListener;
import com.haraj.mersal.riandy.myshoppinglist.asset.model.ShoppingItem;
import com.haraj.mersal.riandy.myshoppinglist.asset.viewholder.BaseItemViewHolder;

import java.util.List;

/**
 * Created by riandyrn on 6/21/16.
 */
public abstract class BaseItemListAdapter extends RecyclerView.Adapter {

    protected LayoutInflater layoutInflater;
    protected List<ShoppingItem> dataSet;
    protected ItemActionListener itemActionListener;

    public BaseItemListAdapter(LayoutInflater layoutInflater, List<ShoppingItem> dataSet,
                               ItemActionListener itemActionListener) {
        this.layoutInflater = layoutInflater;
        this.dataSet = dataSet;
        this.itemActionListener = itemActionListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ShoppingItem currentItem = dataSet.get(position);
        BaseItemViewHolder viewHolder = (BaseItemViewHolder) holder;

        viewHolder.itemName.setText(currentItem.getName());
        viewHolder.itemAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemActionListener.onClick(currentItem);
            }
        });

        onBindViewHolder(currentItem, viewHolder);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public abstract void onBindViewHolder(ShoppingItem currentItem, BaseItemViewHolder viewHolder);
}
