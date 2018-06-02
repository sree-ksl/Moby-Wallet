package com.ct.ksl.mobywallet.ui.accountdetail.transaction;

import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface TransactionView extends IView {

    void finishLoading(long total);
    void showLoadingDialog();
    void showServerError();
}
