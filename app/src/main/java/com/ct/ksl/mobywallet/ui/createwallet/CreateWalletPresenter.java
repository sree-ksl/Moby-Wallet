package com.ct.ksl.mobywallet.ui.createwallet;

import com.ct.ksl.mobywallet.common.Constants;
import com.ct.ksl.mobywallet.common.WalletAppManager;
import com.ct.ksl.mobywallet.tron.Tron;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateWalletPresenter extends BasePresenter<CreateWalletView> {

    public CreateWalletPresenter(CreateWalletView view) {
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

    public void createWallet(String password) {
        Single.fromCallable(() -> {
            int result = WalletAppManager.getInstance(mContext).createWallet(password);

            if (result == WalletAppManager.SUCCESS) {
                result = Tron.getInstance(mContext).registerAccount(Constants.PREFIX_ACCOUNT_NAME, password).blockingGet();
                if (result != Tron.SUCCESS) {
                    return result;
                }

                result = Tron.getInstance(mContext).login(password);

                if (result != Tron.SUCCESS) {
                    return result;
                }

                return Tron.SUCCESS;
            } else if (result == WalletAppManager.ERROR) {
                mView.passwordError();
            }
            return result;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer result) {
                if (result == Tron.ERROR_INVALID_PASSWORD) {
                    mView.registerWalletError();
                } else if (result == Tron.ERROR) {
                    mView.passwordError();
                } else {
                    mView.createdWallet();
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.registerWalletError();
            }
        });
    }
}
