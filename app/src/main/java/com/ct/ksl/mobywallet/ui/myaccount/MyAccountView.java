package com.ct.ksl.mobywallet.ui.myaccount;

import android.support.annotation.NonNull;

import com.ct.ksl.mobywallet.ui.main.dto.TronAccount;
import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface MyAccountView extends IView {

    void displayAccountInfo(@NonNull String address, @NonNull TronAccount account);

    void showLoadingDialog();

    void hideDialog();

    void showServerError();

    void successFreezeBalance();

    void unableToUnfreeze();

    void changePasswordResult(boolean result);
}
