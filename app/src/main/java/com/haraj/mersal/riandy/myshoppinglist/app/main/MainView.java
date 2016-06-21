package com.haraj.mersal.riandy.myshoppinglist.app.main;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.haraj.mersal.riandy.myshoppinglist.asset.model.ShoppingItem;

/**
 * Created by riandyrn on 6/21/16.
 */
public interface MainView extends MvpView {

    void addActiveItem(ShoppingItem shoppingItem);

    void addReactivatedItem(ShoppingItem shoppingItem);

    void addInactiveItem(ShoppingItem shoppingItem);

    void editItem(ShoppingItem shoppingItem, int position);

    void refreshShoppingList();

    void startAddItemActivity();

    void startEditItemActivity(ShoppingItem shoppingItem);
}
