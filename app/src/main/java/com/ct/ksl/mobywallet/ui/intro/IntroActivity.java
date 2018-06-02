package com.ct.ksl.mobywallet.ui.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ct.ksl.mobywallet.R;
import com.ct.ksl.mobywallet.common.CommonActivity;
import com.ct.ksl.mobywallet.ui.backupaccount.BackupAccountActivity;
import com.ct.ksl.mobywallet.ui.createwallet.CreateWalletActivity;
import com.ct.ksl.mobywallet.ui.login.LoginActivity;

public class IntroActivity extends CommonActivity implements IntroView {

    private boolean mIsBackClick = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mPresenter = new IntroPresenter(this);
        mPresenter.onCreate();
    }

    @Override
    public void startCreateAccountActivity() {
        if (!mIsBackClick) {
            startActivity(CreateWalletActivity.class);
        }
        finishActivity();
    }

    @Override
    public void startLoginActivity() {
        if (!mIsBackClick) {
            startActivity(LoginActivity.class);
        }
        finishActivity();
    }

    @Override
    public void startBackupAccountActivity() {
        if (!mIsBackClick) {
            startActivity(BackupAccountActivity.class);
        }
        finishActivity();
    }

    @Override
    public void onBackPressed() {
        mIsBackClick = true;

        super.onBackPressed();
    }
}
