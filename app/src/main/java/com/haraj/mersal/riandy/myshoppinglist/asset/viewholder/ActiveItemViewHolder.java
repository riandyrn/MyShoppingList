package com.haraj.mersal.riandy.myshoppinglist.asset.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by riandyrn on 6/21/16.
 */
public class ActiveItemViewHolder extends BaseItemViewHolder {

    public TextView itemQuantity;

    public ActiveItemViewHolder(View itemView, CheckBox itemStatus,
                                TextView itemName, ImageView itemAction, TextView itemQuantity) {
        super(itemView, itemStatus, itemName, itemAction);
        this.itemQuantity = itemQuantity;
    }
}
