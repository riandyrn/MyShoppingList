package com.haraj.mersal.riandy.myshoppinglist.asset.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by riandyrn on 6/21/16.
 */
public class BaseItemViewHolder extends RecyclerView.ViewHolder{

    public CheckBox itemStatus;
    public TextView itemName;
    public ImageView itemAction;

    public BaseItemViewHolder(View itemView, CheckBox itemStatus, TextView itemName, ImageView itemAction) {
        super(itemView);
        this.itemStatus = itemStatus;
        this.itemName = itemName;
        this.itemAction = itemAction;
    }
}
