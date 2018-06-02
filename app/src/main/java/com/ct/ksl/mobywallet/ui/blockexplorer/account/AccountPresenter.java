package com.ct.ksl.mobywallet.ui.blockexplorer.account;

import com.ct.ksl.tronlib.TronNetwork;
import com.ct.ksl.tronlib.dto.CoinMarketCap;
import com.ct.ksl.tronlib.dto.TronAccount;
import com.ct.ksl.tronlib.dto.TronAccounts;
import com.ct.ksl.mobywallet.common.AdapterDataModel;
import com.ct.ksl.mobywallet.common.Constants;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class AccountPresenter extends BasePresenter<AccountView> {

    private AdapterDataModel<TronAccount> mAdapterDataModel;

    public AccountPresenter(AccountView view) {
        super(view);
    }

    public void setAdapterDataModel(AdapterDataModel<TronAccount> adapterDataModel) {
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

    public void getTronAccounts(long startIndex, int pageSize) {
        mView.showLoadingDialog();

        TronNetwork.getInstance()
                .getAccounts(startIndex, pageSize, "-balance")
                .map((TronAccounts tronAccounts) -> {
                    CoinMarketCap coinMarketCap = TronNetwork.getInstance().getCoinInfo(Constants.TRON_COINMARKET_NAME).blockingGet().get(0);

                    for (TronAccount tronAccount : tronAccounts.getData()) {
                        tronAccount.setTotalSupply((long) Double.parseDouble(coinMarketCap.getTotalSupply()));
                        tronAccount.setAvailableSypply((long) Double.parseDouble(coinMarketCap.getAvailableSupply()));
                        tronAccount.setBalancePercent(((double) tronAccount.getBalance() / Constants.ONE_TRX / (double) tronAccount.getAvailableSypply()) * 100f);
                    }

                    return tronAccounts;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<TronAccounts>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TronAccounts tronAccounts) {
                        mAdapterDataModel.addAll(tronAccounts.getData());
                        mView.finishLoading(tronAccounts.getTotal());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showServerError();
                    }
                });
    }
}
