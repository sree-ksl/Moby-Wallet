package com.ct.ksl.mobywallet.ui.login;

import android.support.annotation.Nullable;

import com.ct.ksl.mobywallet.common.WalletAppManager;
import com.ct.ksl.mobywallet.tron.Tron;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(LoginView view) {
        super(view);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void loginWallet(@Nullable String password) {
        Single.fromCallable(() -> {
            int result = WalletAppManager.getInstance(mContext).login(password);

            if (result == WalletAppManager.SUCCESS) {
                int res = Tron.getInstance(mContext).login(password);
                if (res != Tron.SUCCESS) {
                    return WalletAppManager.ERROR;
                }
            }

            return result;
        })
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer result) {
                mView.loginResult(result);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.loginResult(Tron.ERROR_LOGIN);
            }
        });
    }
}
