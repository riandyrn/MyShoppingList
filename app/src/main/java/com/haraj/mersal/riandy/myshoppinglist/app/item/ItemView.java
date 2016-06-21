package com.haraj.mersal.riandy.myshoppinglist.app.item;

import android.content.Intent;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by riandyrn on 6/21/16.
 */
public interface ItemView extends MvpView {

    void close(Intent data);
}
