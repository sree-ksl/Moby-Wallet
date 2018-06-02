package com.ct.ksl.mobywallet.ui.accountdetail.overview;

import android.support.annotation.NonNull;

import com.ct.ksl.tronlib.TronNetwork;
import com.ct.ksl.tronlib.dto.AccountVote;
import com.ct.ksl.tronlib.dto.AccountVotes;
import com.ct.ksl.mobywallet.common.AdapterDataModel;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class VotePresenter extends BasePresenter<VoteView> {

    private AdapterDataModel<AccountVote> mAdapterDataModel;

    public VotePresenter(VoteView view) {
        super(view);
    }

    public void setAdapterDataModel(AdapterDataModel<AccountVote> adapterDataModel) {
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

    public void getVotes(@NonNull String address, long startIndex, int pageSize) {
        mView.showLoadingDialog();

        TronNetwork.getInstance().getAccountVotes(address, startIndex, pageSize, "-votes")
                .observeOn(AndroidSchedulers.mainThread())
                .map(accountVotes -> {
                    for (AccountVote accountVote : accountVotes.getData()) {
                        accountVote.setTotalVotes(accountVotes.getTotalVotes());
                    }

                    return accountVotes;
                })
                .subscribe(new SingleObserver<AccountVotes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AccountVotes accountVotes) {
                        mAdapterDataModel.addAll(accountVotes.getData());
                        mView.finishLoading(accountVotes.getTotalVotes(), accountVotes.getTotal());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
