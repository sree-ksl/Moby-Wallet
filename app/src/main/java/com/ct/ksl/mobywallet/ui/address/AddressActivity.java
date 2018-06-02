package com.ct.ksl.mobywallet.ui.address;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ct.ksl.mobywallet.R;
import com.ct.ksl.mobywallet.common.CommonActivity;
import com.ct.ksl.mobywallet.tron.Tron;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends CommonActivity implements AddressView {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.iv_qrcode)
    public ImageView mQrcodeImageView;

    @BindView(R.id.tv_address)
    public TextView mAddressTextView;

    @BindView(R.id.btn_copy_address)
    public Button mBtnCopyAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_my_address);
        }

        mPresenter = new AddressPresenter(this);
        mPresenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.address_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareMyAddress();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addressResult(@Nullable AddressPresenter.AddressInfo addressInfo) {
        if (addressInfo == null) {
            Toast.makeText(AddressActivity.this, getString(R.string.account_does_not_exist),
                    Toast.LENGTH_SHORT).show();
            finishActivity();
        } else {
            mQrcodeImageView.setImageBitmap(addressInfo.addressBitmap);
            mAddressTextView.setText(addressInfo.address);
            mBtnCopyAddress.setEnabled(true);
        }

    }

    @OnClick(R.id.btn_copy_address)
    public void onCopyAddressClick() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", mAddressTextView.getText());
        clipboard.setPrimaryClip(clip);

        Toast.makeText(AddressActivity.this, getString(R.string.copy_address_msg),
                Toast.LENGTH_SHORT)
                .show();
    }

    private void shareMyAddress() {
        String address = Tron.getInstance(AddressActivity.this).getLoginAddress();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, address);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.choice_share)));
    }
}
