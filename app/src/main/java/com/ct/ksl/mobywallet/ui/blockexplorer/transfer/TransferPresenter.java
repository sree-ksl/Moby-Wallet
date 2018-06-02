package com.ct.ksl.mobywallet.ui.blockexplorer.transfer;

import com.ct.ksl.tronlib.TronNetwork;
import com.ct.ksl.tronlib.dto.Transfer;
import com.ct.ksl.tronlib.dto.Transfers;
import com.ct.ksl.mobywallet.common.AdapterDataModel;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TransferPresenter extends BasePresenter<TransferView> {

    private AdapterDataModel<Transfer> mAdapterDataModel;

    public TransferPresenter(TransferView view) {
        super(view);
    }

    public void setAdapterDataModel(AdapterDataModel<Transfer> adapterDataModel) {
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

    public void getTransfer(long startIndex, int pageSize) {
        mView.showLoadingDialog();

        TronNetwork.getInstance()
                .getTransfers(startIndex, pageSize, "-timestamp", true)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Transfers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Transfers transactions) {
                        mAdapterDataModel.addAll(transactions.getData());
                        mView.finishLoading(transactions.getTotal());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showServerError();
                    }
                });
    }

    public void getTransfer(long block, long startIndex, int pageSize) {
        mView.showLoadingDialog();
        TronNetwork.getInstance()
                .getTransfers("-timestamp", true, pageSize, startIndex, block)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Transfers>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Transfers transfers) {
                        mAdapterDataModel.addAll(transfers.getData());
                        mView.finishLoading(transfers.getTotal());
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showServerError();
                    }
                });
    }
}
