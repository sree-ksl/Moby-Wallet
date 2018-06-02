package com.ct.ksl.mobywallet.ui.token.overview;

import android.support.annotation.NonNull;

import com.ct.ksl.tronlib.dto.Token;
import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface OverviewView extends IView {

    void tokenInfoLoadSuccess(@NonNull Token token);
    void showLoadingDialog();
    void showServerError();
}
