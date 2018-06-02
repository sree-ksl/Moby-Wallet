package com.ct.ksl.mobywallet.ui.blockexplorer.transaction;

import com.ct.ksl.tronlib.TronNetwork;
import com.ct.ksl.tronlib.dto.Transaction;
import com.ct.ksl.tronlib.dto.Transactions;
import com.ct.ksl.mobywallet.common.AdapterDataModel;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TransactionPresenter extends BasePresenter<TransactionView> {

    private AdapterDataModel<Transaction> mAdapterDataModel;

    public TransactionPresenter(TransactionView view) {
        super(view);
    }

    public void setAdapterDataModel(AdapterDataModel<Transaction> adapterDataModel) {
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

    public void getTransactions(long startIndex, int pageSize) {
        mView.showLoadingDialog();

        TronNetwork.getInstance().getTransactions(startIndex, pageSize, "-timestamp", true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Transactions>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Transactions transactions) {
                        mAdapterDataModel.addAll(transactions.getData());
                        mView.finishLoading(transactions.getTotal());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showServerError();
                    }
                });
    }
}
