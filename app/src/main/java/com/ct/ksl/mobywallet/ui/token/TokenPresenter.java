package com.ct.ksl.mobywallet.ui.token;

import android.support.annotation.NonNull;

import com.ct.ksl.tronlib.TronNetwork;
import com.ct.ksl.tronlib.dto.Token;
import com.ct.ksl.tronlib.dto.Tokens;
import com.ct.ksl.mobywallet.common.AdapterDataModel;
import com.ct.ksl.mobywallet.common.WalletAppManager;
import com.ct.ksl.mobywallet.tron.Tron;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;

import org.tron.protos.Protocol;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TokenPresenter extends BasePresenter<TokenView> {

    private AdapterDataModel<Token> mAdapterDataModel;

    public TokenPresenter(TokenView view) {
        super(view);
    }

    public void setAdapterDataModel(AdapterDataModel<Token> adapterDataModel) {
        this.mAdapterDataModel = adapterDataModel;
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

    public void loadItems(long startIndex, int pageSize) {
        mView.showLoadingDialog();

        Single.zip(Tron.getInstance(mContext).queryAccount(Tron.getInstance(mContext).getLoginAddress()),
                TronNetwork.getInstance().getTokens(startIndex, pageSize, "-name", "ico"),
                (account, tokens) -> {
                    AccountInfo accountInfo = new AccountInfo();
                    accountInfo.account = account;
                    accountInfo.tokens = tokens;

                    return accountInfo;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AccountInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AccountInfo accountInfo) {
                        mAdapterDataModel.addAll(accountInfo.tokens.getData());
                        mView.finishLoading(accountInfo.tokens.getTotal(), accountInfo.account);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void participateToken(Token item, long tokenAmount) {
        mView.showLoadingDialog();

        Tron.getInstance(mContext).participateTokens(item.getName(), item.getOwnerAddress(), tokenAmount)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Boolean result) {
                mView.participateTokenResult(result);
            }

            @Override
            public void onError(Throwable e) {
                mView.showServerError();
            }
        });
    }

    public boolean matchPassword(@NonNull String password) {
        return WalletAppManager.getInstance(mContext).login(password) == WalletAppManager.SUCCESS;
    }

    private class AccountInfo {
        Protocol.Account account;
        Tokens tokens;
    }
}
