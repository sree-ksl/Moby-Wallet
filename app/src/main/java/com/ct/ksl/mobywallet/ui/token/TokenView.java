package com.ct.ksl.mobywallet.ui.token;

import com.ct.ksl.mobywallet.ui.mvp.IView;

import org.tron.protos.Protocol;

public interface TokenView extends IView {

    void showLoadingDialog();

    void showServerError();

    void finishLoading(int total, Protocol.Account account);

    void participateTokenResult(boolean result);
}
