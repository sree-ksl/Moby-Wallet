package com.ct.ksl.mobywallet.ui.main;

import android.support.annotation.NonNull;

import com.ct.ksl.tronlib.dto.CoinMarketCap;
import com.ct.ksl.mobywallet.ui.main.dto.TronAccount;
import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface MainView extends IView {

    void displayAccountInfo(@NonNull TronAccount account);

    void setTronMarketInfo(CoinMarketCap coinMarketCap);

    void showInvalidPasswordMsg();

    void successCreateAccount();

    void successImportAccount();

    void failCreateAccount();

    void duplicatedAccount();

    void connectionError();
}
