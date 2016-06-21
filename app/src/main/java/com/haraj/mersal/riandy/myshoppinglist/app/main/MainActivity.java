package com.haraj.mersal.riandy.myshoppinglist.app.main;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.haraj.mersal.riandy.myshoppinglist.R;
import com.haraj.mersal.riandy.myshoppinglist.app.item.ItemActivity;
import com.haraj.mersal.riandy.myshoppinglist.asset.model.ShoppingItem;
import com.haraj.mersal.riandy.myshoppinglist.asset.viewholder.ActiveItemViewHolder;
import com.haraj.mersal.riandy.myshoppinglist.asset.viewholder.InactiveItemViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView {

    private static final int ADD_REQUEST_CODE = 1;
    private static final int EDIT_REQUEST_CODE = 2;

    private Realm realm;

    private List<ShoppingItem> activeItemDataSet = new ArrayList<>();
    private List<ShoppingItem> inactiveItemDataSet = new ArrayList<>();

    @BindView(R.id.activeItemList) RecyclerView activeItemList;
    @BindView(R.id.inactiveItemList) RecyclerView inactiveItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        activeItemList.setLayoutManager(new LinearLayoutManager(this));
        inactiveItemList.setLayoutManager(new LinearLayoutManager(this));

        activeItemList.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.active_item, parent, false);
                return new ActiveItemViewHolder(v,
                        (CheckBox) v.findViewById(R.id.item_status),
                        (TextView) v.findViewById(R.id.item_name),
                        (ImageView) v.findViewById(R.id.item_action),
                        (TextView) v.findViewById(R.id.item_quantity));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                final ShoppingItem currentItem = activeItemDataSet.get(position);
                ActiveItemViewHolder currentHolder = (ActiveItemViewHolder) holder;

                currentHolder.itemName.setText(currentItem.getName());
                currentHolder.itemQuantity.setText(currentItem.getQuantity());

                currentHolder.itemStatus.setChecked(false);
                currentHolder.itemStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(isChecked)
                            getPresenter().markComplete(currentItem);
                    }
                });
                currentHolder.itemAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getPresenter().openEditForm(currentItem);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return activeItemDataSet.size();
            }
        });
        inactiveItemList.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = getLayoutInflater().inflate(R.layout.inactive_item, parent, false);

                return new InactiveItemViewHolder(v,
                        (CheckBox) v.findViewById(R.id.item_status),
                        (TextView) v.findViewById(R.id.item_name),
                        (ImageView) v.findViewById(R.id.item_action));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                final ShoppingItem currentItem = inactiveItemDataSet.get(position);
                InactiveItemViewHolder currentViewHolder = (InactiveItemViewHolder) holder;

                currentViewHolder.itemName.setText(currentItem.getName());
                currentViewHolder.itemName.setPaintFlags(currentViewHolder.itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                currentViewHolder.itemAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getPresenter().undoComplete(currentItem);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return inactiveItemDataSet.size();
            }
        });

        getPresenter().loadShoppingList();
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        getPresenter().openAddForm();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();

        return new MainPresenter(realm, activeItemDataSet, inactiveItemDataSet);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            String itemId = data.getStringExtra("ITEM_ID");

            if(requestCode == ADD_REQUEST_CODE) {
                getPresenter().addItem(itemId);
            } else if(requestCode == EDIT_REQUEST_CODE) {
                getPresenter().editItem(itemId, data.getIntExtra("POSITION", 0));
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void addActiveItem(ShoppingItem shoppingItem) {

        activeItemDataSet.add(0, shoppingItem);
        refreshShoppingList();
    }

    @Override
    public void addReactivatedItem(ShoppingItem shoppingItem) {

        int index = inactiveItemDataSet.indexOf(shoppingItem);
        inactiveItemDataSet.remove(index);

        addActiveItem(shoppingItem);
    }

    @Override
    public void addInactiveItem(ShoppingItem shoppingItem) {

        int itemIndex = activeItemDataSet.indexOf(shoppingItem);
        activeItemDataSet.remove(itemIndex);

        inactiveItemDataSet.add(0, shoppingItem);
        refreshShoppingList();
    }

    @Override
    public void editItem(ShoppingItem shoppingItem, int position) {

        activeItemDataSet.set(position, shoppingItem);
        refreshShoppingList();
    }

    @Override
    public void refreshShoppingList() {

        activeItemList.getAdapter().notifyDataSetChanged();
        inactiveItemList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void startAddItemActivity() {

        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("TITLE", "Add item");

        startActivityForResult(intent, ADD_REQUEST_CODE);
    }

    @Override
    public void startEditItemActivity(ShoppingItem shoppingItem) {

        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("TITLE", "Edit item");
        intent.putExtra("ITEM_ID", shoppingItem.getId());
        intent.putExtra("ITEM_NAME", shoppingItem.getName());
        intent.putExtra("ITEM_QUANTITY", shoppingItem.getQuantity());
        intent.putExtra("POSITION", activeItemDataSet.indexOf(shoppingItem));

        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }
}
