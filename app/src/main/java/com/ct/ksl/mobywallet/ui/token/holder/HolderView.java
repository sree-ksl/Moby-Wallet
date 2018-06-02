package com.ct.ksl.mobywallet.ui.token.holder;

import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface HolderView extends IView {

    void finishLoading(long total);
    void showLoadingDialog();
    void showServerError();
}
