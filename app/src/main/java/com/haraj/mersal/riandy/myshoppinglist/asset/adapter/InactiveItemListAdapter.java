package com.haraj.mersal.riandy.myshoppinglist.asset.adapter;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.haraj.mersal.riandy.myshoppinglist.R;
import com.haraj.mersal.riandy.myshoppinglist.asset.adapter.base.BaseItemListAdapter;
import com.haraj.mersal.riandy.myshoppinglist.asset.adapter.listener.ItemActionListener;
import com.haraj.mersal.riandy.myshoppinglist.asset.model.ShoppingItem;
import com.haraj.mersal.riandy.myshoppinglist.asset.viewholder.BaseItemViewHolder;
import com.haraj.mersal.riandy.myshoppinglist.asset.viewholder.InactiveItemViewHolder;

import java.util.List;

/**
 * Created by riandyrn on 6/22/16.
 */
public class InactiveItemListAdapter extends BaseItemListAdapter {

    public InactiveItemListAdapter(LayoutInflater layoutInflater, List<ShoppingItem> dataSet,
                                   ItemActionListener itemActionListener) {
        super(layoutInflater, dataSet, itemActionListener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.inactive_item, parent, false);

        return new InactiveItemViewHolder(v,
                (CheckBox) v.findViewById(R.id.item_status),
                (TextView) v.findViewById(R.id.item_name),
                (ImageView) v.findViewById(R.id.item_action));
    }

    @Override
    public void onBindViewHolder(ShoppingItem currentItem, BaseItemViewHolder viewHolder) {

        viewHolder.itemName
                .setPaintFlags(viewHolder.itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
