package com.ct.ksl.mobywallet.ui.blockexplorer.overview;

import com.ct.ksl.tronlib.TronNetwork;
import com.ct.ksl.tronlib.dto.CoinMarketCap;
import com.ct.ksl.tronlib.dto.RichInfo;
import com.ct.ksl.tronlib.dto.RichTotal;
import com.ct.ksl.tronlib.dto.Stat;
import com.ct.ksl.tronlib.dto.TransferStats;
import com.ct.ksl.mobywallet.common.Constants;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by user on 2018. 5. 28..
 */

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

    void chartDataLoad() {
        mView.showLoadingDialog();

        TronNetwork.getInstance().getTopAddressAccounts(13)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mView::overviewDataLoadSuccess,
                        t -> mView.showServerError()
                );

        TronNetwork.getInstance().getAvgBlockSize()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mView::overviewAvgBlockSize,
                        t -> mView.showServerError()
                );

        TronNetwork.getInstance().getSystemStatus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mView::overviewBlockStatus,
                        t -> mView.showServerError()
                );

        TronNetwork.getInstance().getTransferStats()
                .observeOn(AndroidSchedulers.mainThread())
                .map(transferStats -> {
                    for (Stat stat : transferStats.getValue()) {
                        stat.setValue((long) (stat.getValue() / Constants.ONE_TRX));
                    }

                    return transferStats;
                })
                .subscribe(new SingleObserver<TransferStats>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TransferStats transferStats) {
                        mView.overviewTransferPastHour(transferStats.getValue());
                        mView.overviewTransactionPastHour(transferStats.getTotal());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    void richListDataLoad() {
        mView.showLoadingDialog();

        TronNetwork.getInstance().getRichList()
                .map(richData ->  {
                    CoinMarketCap coinMarketCap = TronNetwork.getInstance()
                            .getCoinInfo(Constants.TRON_COINMARKET_NAME).blockingGet().get(0);

                    List<RichItemViewModel> viewModels = new ArrayList<>();
                    RichTotal total = richData.getTotal();
                    for (RichInfo info : richData.getData()) {
                        viewModels.add(new RichItemViewModel(total, info, coinMarketCap));
                    }
                    return viewModels;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mView::richListLoadSuccess,
                        t -> mView.showServerError()
                );
    }
}
