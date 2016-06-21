package com.haraj.mersal.riandy.myshoppinglist.app.main;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.haraj.mersal.riandy.myshoppinglist.asset.model.ShoppingItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by riandyrn on 6/21/16.
 */
public class MainPresenter extends MvpBasePresenter<MainView> {

    private Realm realm;
    private List<ShoppingItem> activeItemDataSet;
    private List<ShoppingItem> inactiveItemDataSet;

    public MainPresenter(Realm realm, List<ShoppingItem> activeItemDataSet, List<ShoppingItem> inactiveItemDataSet) {
        this.realm = realm;
        this.activeItemDataSet = activeItemDataSet;
        this.inactiveItemDataSet = inactiveItemDataSet;
    }

    public void loadShoppingList() {

        RealmResults<ShoppingItem> activeItems = realm.where(ShoppingItem.class)
                .equalTo("completed", false)
                .findAllSorted("timestamp", Sort.DESCENDING);

        RealmResults<ShoppingItem> inactiveItems = realm.where(ShoppingItem.class)
                .equalTo("completed", true)
                .findAllSorted("timestamp", Sort.DESCENDING);

        for(ShoppingItem item: activeItems)
            activeItemDataSet.add(item);

        for(ShoppingItem item: inactiveItems)
            inactiveItemDataSet.add(item);

        getView().refreshShoppingList();
    }

    public void markComplete(ShoppingItem shoppingItem) {

        realm.beginTransaction();

        shoppingItem.setCompleted(true);
        shoppingItem.setTimestamp(System.currentTimeMillis());

        realm.commitTransaction();

        getView().addInactiveItem(shoppingItem);
    }

    public void undoComplete(ShoppingItem shoppingItem) {

        realm.beginTransaction();

        shoppingItem.setCompleted(false);
        shoppingItem.setTimestamp(System.currentTimeMillis());

        realm.commitTransaction();

        getView().addReactivatedItem(shoppingItem);
    }

    public void openAddForm() {

        getView().startAddItemActivity();
    }

    public void openEditForm(ShoppingItem shoppingItem) {

        getView().startEditItemActivity(shoppingItem);
    }

    public void addItem(String itemId) {

        ShoppingItem shoppingItem = realm.where(ShoppingItem.class)
                                        .equalTo("id", itemId).findFirst();

        getView().addActiveItem(shoppingItem);
    }

    public void editItem(String itemId, int position) {

        ShoppingItem shoppingItem = realm.where(ShoppingItem.class)
                                        .equalTo("id", itemId).findFirst();

        getView().editItem(shoppingItem, position);
    }
}
