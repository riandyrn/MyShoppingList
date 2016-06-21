package com.haraj.mersal.riandy.myshoppinglist.app.item;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.haraj.mersal.riandy.myshoppinglist.R;
import com.haraj.mersal.riandy.myshoppinglist.asset.model.ShoppingItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class ItemActivity extends MvpActivity<ItemView, ItemPresenter> implements ItemView {

    @BindView(R.id.item_input_name) TextInputEditText itemInputName;
    @BindView(R.id.item_input_quantity) TextInputEditText itemInputQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        ButterKnife.bind(this);

        if(getIntent().hasExtra("TITLE")) {
            setTitle(getIntent().getStringExtra("TITLE"));
        }

        if(getIntent().hasExtra("ITEM_ID")) {

            itemInputName.setText(getIntent().getStringExtra("ITEM_NAME"));
            itemInputQuantity.setText(getIntent().getStringExtra("ITEM_QUANTITY"));
        }
    }

    @NonNull
    @Override
    public ItemPresenter createPresenter() {

        Realm realm = Realm.getDefaultInstance();
        return new ItemPresenter(getIntent(), realm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.save_action) {

            getPresenter().save(itemInputName.getText().toString(),
                    itemInputQuantity.getText().toString());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void close(Intent data) {

        setResult(RESULT_OK, data);
        finish();
    }
}
