package com.ct.ksl.mobywallet.ui.createwallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ct.ksl.mobywallet.R;
import com.ct.ksl.mobywallet.common.CommonActivity;
import com.ct.ksl.mobywallet.common.WalletAppManager;
import com.ct.ksl.mobywallet.ui.backupaccount.BackupAccountActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateWalletActivity extends CommonActivity implements CreateWalletView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.input_password)
    EditText mInputPassword;

    @BindView(R.id.btn_create_wallet)
    Button mCreateWalletButton;

    @BindView(R.id.agree_lost_password)
    CheckBox mChkLostPassword;

    @BindView(R.id.agree_lost_password_recover)
    CheckBox mChkLostPasswordRecover;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wallet);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_create_wallet);
        }

        mPresenter = new CreateWalletPresenter(this);
        mPresenter.onCreate();

        mInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= WalletAppManager.MIN_PASSWORD_LENGTH) {
                    mCreateWalletButton.setEnabled(true);
                } else {
                    mCreateWalletButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.btn_create_wallet)
    public void onCreateAccountClick() {
        if (!mChkLostPassword.isChecked()
                || !mChkLostPasswordRecover.isChecked()) {
            Toast.makeText(CreateWalletActivity.this, getString(R.string.need_all_agree),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog(null, getString(R.string.loading_msg));
        ((CreateWalletPresenter) mPresenter).createWallet(mInputPassword.getText().toString());
    }

    @Override
    public void createdWallet() {
        hideDialog();
        startActivity(BackupAccountActivity.class);
        finishActivity();
    }

    @Override
    public void passwordError() {
        hideDialog();
        Toast.makeText(CreateWalletActivity.this, getString(R.string.password_error),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerWalletError() {
        hideDialog();
        Toast.makeText(CreateWalletActivity.this, getString(R.string.register_wallet_error),
                Toast.LENGTH_SHORT).show();
    }
}
