package com.ct.ksl.mobywallet.ui.backupaccount;

import android.support.annotation.NonNull;

import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface BackupAccountView extends IView {

    void displayAccountInfo(@NonNull String address, @NonNull String privateKey);

    void startMainActivity();
}
