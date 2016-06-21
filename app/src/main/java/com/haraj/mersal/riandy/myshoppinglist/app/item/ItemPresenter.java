package com.haraj.mersal.riandy.myshoppinglist.app.item;

import android.content.Intent;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.haraj.mersal.riandy.myshoppinglist.asset.model.ShoppingItem;

import java.util.UUID;

import io.realm.Realm;

/**
 * Created by riandyrn on 6/21/16.
 */
public class ItemPresenter extends MvpBasePresenter<ItemView> {

    private Realm realm;

    private boolean editMode = false;
    private Intent itemIntent;

    public ItemPresenter(Intent itemIntent, Realm realm) {
        this.itemIntent = itemIntent;
        this.realm = realm;

        if(itemIntent.hasExtra("ITEM_ID")) {
            editMode = true;
        }
    }

    public void save(String changedItemName, String changedItemQuantity) {

        ShoppingItem shoppingItem;
        String itemId;

        if(!editMode) {

            itemId = UUID.randomUUID().toString();

            realm.beginTransaction();

            shoppingItem = realm.createObject(ShoppingItem.class);
            shoppingItem.setId(itemId);
            shoppingItem.setName(changedItemName);
            shoppingItem.setQuantity(changedItemQuantity);
            shoppingItem.setCompleted(false);
            shoppingItem.setTimestamp(System.currentTimeMillis());

            realm.commitTransaction();

        } else {

            itemId = itemIntent.getStringExtra("ITEM_ID");

            realm.beginTransaction();

            shoppingItem = realm.where(ShoppingItem.class).equalTo("id", itemId).findFirst();
            shoppingItem.setName(changedItemName);
            shoppingItem.setQuantity(changedItemQuantity);

            realm.commitTransaction();
        }

        getView().close(itemIntent.putExtra("ITEM_ID", itemId));
    }
}
