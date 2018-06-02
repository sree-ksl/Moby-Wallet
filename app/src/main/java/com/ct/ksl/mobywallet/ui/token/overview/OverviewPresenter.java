package com.ct.ksl.mobywallet.ui.token.overview;

import android.support.annotation.NonNull;

import com.ct.ksl.tronlib.TronNetwork;
import com.ct.ksl.tronlib.dto.Token;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class OverviewPresenter extends BasePresenter<OverviewView> {


    public OverviewPresenter(OverviewView view) {
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

    public void loadTokenInfo(@NonNull String tokenName) {
        mView.showLoadingDialog();

        TronNetwork.getInstance()
                .getTokenDetail(tokenName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Token>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Token token) {
                        mView.tokenInfoLoadSuccess(token);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showServerError();
                    }
                });
    }
}
