package com.ct.ksl.mobywallet.ui.token.transfer;

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

    public void getTransfer(long startIndex, int pageSize, String tokenName) {
        mView.showLoadingDialog();

        TronNetwork.getInstance()
                .getTransfers("-timestamp", true, pageSize, startIndex, tokenName)
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
}
