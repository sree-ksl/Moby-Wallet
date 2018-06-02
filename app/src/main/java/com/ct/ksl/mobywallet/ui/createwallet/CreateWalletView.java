package com.ct.ksl.mobywallet.ui.createwallet;

import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface CreateWalletView extends IView {

    void createdWallet();

    void passwordError();

    void registerWalletError();
}
