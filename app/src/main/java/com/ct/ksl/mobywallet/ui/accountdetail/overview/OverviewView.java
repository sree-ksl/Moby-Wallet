package com.ct.ksl.mobywallet.ui.accountdetail.overview;

import android.support.annotation.NonNull;

import com.ct.ksl.mobywallet.ui.main.dto.TronAccount;
import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface OverviewView extends IView {

    void showLoadingDialog();
    void showServerError();
    void finishLoading(@NonNull TronAccount account);
}
