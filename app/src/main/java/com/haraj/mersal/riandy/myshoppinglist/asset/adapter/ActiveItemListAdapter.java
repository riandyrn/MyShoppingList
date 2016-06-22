package com.haraj.mersal.riandy.myshoppinglist.asset.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.haraj.mersal.riandy.myshoppinglist.R;
import com.haraj.mersal.riandy.myshoppinglist.asset.adapter.base.BaseItemListAdapter;
import com.haraj.mersal.riandy.myshoppinglist.asset.adapter.listener.ItemActionListener;
import com.haraj.mersal.riandy.myshoppinglist.asset.adapter.listener.ItemCheckedChangeListener;
import com.haraj.mersal.riandy.myshoppinglist.asset.model.ShoppingItem;
import com.haraj.mersal.riandy.myshoppinglist.asset.viewholder.ActiveItemViewHolder;
import com.haraj.mersal.riandy.myshoppinglist.asset.viewholder.BaseItemViewHolder;

import java.util.List;

/**
 * Created by riandyrn on 6/22/16.
 */
public class ActiveItemListAdapter extends BaseItemListAdapter {

    private ItemCheckedChangeListener itemCheckedChangeListener;

    public ActiveItemListAdapter(LayoutInflater layoutInflater, List<ShoppingItem> dataSet,
                                 ItemActionListener itemActionListener,
                                 ItemCheckedChangeListener itemCheckedChangeListener) {

        super(layoutInflater, dataSet, itemActionListener);
        this.itemCheckedChangeListener = itemCheckedChangeListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = layoutInflater.inflate(R.layout.active_item, parent, false);
        return new ActiveItemViewHolder(v,
                (CheckBox) v.findViewById(R.id.item_status),
                (TextView) v.findViewById(R.id.item_name),
                (ImageView) v.findViewById(R.id.item_action),
                (TextView) v.findViewById(R.id.item_quantity));
    }

    @Override
    public void onBindViewHolder(final ShoppingItem currentItem, BaseItemViewHolder viewHolder) {

        viewHolder.itemStatus.setChecked(false);
        viewHolder.itemStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                    itemCheckedChangeListener.onCheckedChanged(currentItem);
            }
        });
        ((ActiveItemViewHolder) viewHolder).itemQuantity.setText(currentItem.getQuantity());
    }
}
