package com.ct.ksl.mobywallet.ui.sendtoken;

import com.ct.ksl.mobywallet.ui.mvp.IView;

import org.tron.protos.Protocol;

public interface SendTokenView extends IView {

    void sendTokenResult(boolean result);

    void invalidPassword();

    void displayAccountInfo(Protocol.Account account);

    void invalidAddress();
}
