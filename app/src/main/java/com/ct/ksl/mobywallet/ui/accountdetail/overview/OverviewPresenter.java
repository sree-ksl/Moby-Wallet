package com.ct.ksl.mobywallet.ui.accountdetail.overview;

import android.support.annotation.NonNull;

import com.ct.ksl.tronlib.TronNetwork;
import com.ct.ksl.tronlib.dto.Account;
import com.ct.ksl.tronlib.dto.TransactionStats;
import com.ct.ksl.mobywallet.common.Constants;
import com.ct.ksl.mobywallet.ui.main.dto.Asset;
import com.ct.ksl.mobywallet.ui.main.dto.Frozen;
import com.ct.ksl.mobywallet.ui.main.dto.TronAccount;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

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

    public void getAccount(@NonNull String address) {
        mView.showLoadingDialog();

        TronNetwork.getInstance().getAccount(address)
                .map((account -> {
                    List<Frozen> frozenList = new ArrayList<>();

                    for (Account.FrozenTrx frozen : account.getFrozen().getBalances()) {
                        frozenList.add(Frozen.builder()
                                .frozenBalance(frozen.getAmount())
                                .expireTime(frozen.getExpires())
                                .build());
                    }

                    List<Asset> assetList = new ArrayList<>();

                    for (Account.Balance balance : account.getTokenBalances()) {
                        if (Constants.TRON_SYMBOL.equalsIgnoreCase(balance.getName())) {
                            continue;
                        }

                        assetList.add(Asset.builder()
                                .name(balance.getName())
                                .balance(balance.getBalance())
                                .build());
                    }

                    TransactionStats transactionStats = TronNetwork.getInstance().getTransactionStats(address).blockingGet();

                    return TronAccount.builder()
                            .balance(account.getBalance())
                            .bandwidth(account.getBandwidth().getNetRemaining())
                            .assetList(assetList)
                            .frozenList(frozenList)
                            .transactions(transactionStats.getTransactions())
                            .transactionIn(transactionStats.getTransactionsIn())
                            .transactionOut(transactionStats.getTransactionsOut())
                            .account(account)
                            .build();
                }))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<TronAccount>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TronAccount account) {
                        mView.finishLoading(account);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        // todo - error msg
                        if (e instanceof ConnectException) {
                            // internet error
                        }

                        mView.showServerError();
                    }
                });
    }
}
