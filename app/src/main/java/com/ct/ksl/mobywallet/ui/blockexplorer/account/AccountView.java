package com.ct.ksl.mobywallet.ui.blockexplorer.account;

import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface AccountView extends IView {

    void finishLoading(long total);
    void showLoadingDialog();
    void showServerError();
}
