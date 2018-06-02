package com.ct.ksl.mobywallet.ui.token.transfer;

import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface TransferView extends IView {

    void finishLoading(long total);
    void showLoadingDialog();
    void showServerError();
}
