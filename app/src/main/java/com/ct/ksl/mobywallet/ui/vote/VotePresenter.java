package com.ct.ksl.mobywallet.ui.vote;

import android.support.annotation.NonNull;

import com.ct.ksl.tronlib.TronNetwork;
import com.ct.ksl.tronlib.dto.Representative;
import com.ct.ksl.mobywallet.common.AdapterDataModel;
import com.ct.ksl.mobywallet.common.Constants;
import com.ct.ksl.mobywallet.common.WalletAppManager;
import com.ct.ksl.mobywallet.tron.AccountManager;
import com.ct.ksl.mobywallet.tron.Tron;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;
import com.ct.ksl.mobywallet.ui.vote.dto.VoteItem;
import com.ct.ksl.mobywallet.ui.vote.dto.VoteItemList;

import org.tron.protos.Protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VotePresenter extends BasePresenter<VoteView> {

    private AdapterDataModel<VoteItem> mAdapterDataModel;

    private List<VoteItem> mAllVotes;

    private List<VoteItem> mMyVotes;

    public VotePresenter(VoteView view) {
        super(view);
    }

    public void setAdapterDataModel(AdapterDataModel<VoteItem> adapterDataModel) {
        this.mAdapterDataModel = adapterDataModel;
    }

    @Override
    public void onCreate() {
        getRepresentativeList(false);
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

    public void getRepresentativeList(boolean isMyVotes) {
        mView.showLoadingDialog();

        TronNetwork.getInstance().getVoteCurrentCycle()
        .map(votes -> {
            List<VoteItem> representatives = new ArrayList<>();

            int cnt = votes.getVotesList().size();

            long totalMyVotes = 0;

            Protocol.Account myAccount = Tron.getInstance(mContext).queryAccount(Tron.getInstance(mContext).getLoginAddress()).blockingGet();

            for (int i = 0; i < cnt; i++) {
                Representative representative = votes.getVotesList().get(i);

                long myVoteCount = 0;

                for (Protocol.Vote vote : myAccount.getVotesList()) {
                    if (AccountManager.encode58Check(vote.getVoteAddress().toByteArray()).equals(representative.getAddress())) {
                        myVoteCount = vote.getVoteCount();
                        totalMyVotes += myVoteCount;
                        break;
                    }
                }

                representatives.add(VoteItem.builder()
                        .address(representative.getAddress())
                        .url(representative.getUrl())
                        .totalVoteCount(votes.getTotalVotes())
                        .voteCount(representative.getVotes())
                        .hasTeamPage(representative.isHasPage())
                        .myVoteCount(myVoteCount)
                        .build());
            }

            Descending descending = new Descending();
            Collections.sort(representatives, descending);

            for (int i = 0; i < cnt; i++) {
                VoteItem representative = representatives.get(i);
                representative.setNo(i + 1);
                representative.setTotalVoteCount(votes.getTotalVotes());
            }

            long myVotePoint = 0;

            for (Protocol.Account.Frozen frozen : myAccount.getFrozenList()) {
                myVotePoint += frozen.getFrozenBalance();
            }

            myVotePoint = (long) (myVotePoint / Constants.ONE_TRX);

            mAllVotes = new ArrayList<>();
            mMyVotes = new ArrayList<>();
            mAllVotes.addAll(representatives);

            for (int i = 0; i < mAllVotes.size(); i++) {
                VoteItem voteItem = mAllVotes.get(i);

                if (voteItem.getMyVoteCount() > 0) {
                    mMyVotes.add(voteItem);
                }
            }

            return VoteItemList.builder()
                    .voteItemList(representatives)
                    .voteItemCount(cnt)
                    .totalVotes(votes.getTotalVotes())
                    .totalMyVotes(totalMyVotes)
                    .myVotePoint(myVotePoint)
                    .build();
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SingleObserver<VoteItemList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(VoteItemList voteItemList) {
                mView.displayVoteInfo(voteItemList.getTotalVotes(), voteItemList.getVoteItemCount(),
                        voteItemList.getMyVotePoint(), voteItemList.getTotalMyVotes());
                showOnlyMyVotes(isMyVotes);
            }

            @Override
            public void onError(Throwable e) {
                mView.showServerError();
            }
        });
    }

    public void showOnlyMyVotes(boolean isMyVotes) {
        mAdapterDataModel.clear();

        if (isMyVotes) {
            mAdapterDataModel.addAll(mMyVotes);
        } else {
            mAdapterDataModel.addAll(mAllVotes);
        }

        mView.refreshList();
    }

    public boolean matchPassword(@NonNull String password) {
        return WalletAppManager.getInstance(mContext).login(password) == WalletAppManager.SUCCESS;
    }

    public void voteRepresentative(String address, long vote) {
        mView.showLoadingDialog();

        Single.fromCallable(() -> {
            Map<String, String> witness = new HashMap<>();

            for (int i = 0; i < mMyVotes.size(); i++) {
                VoteItem voteItem = mMyVotes.get(i);

                witness.put(voteItem.getAddress(), String.valueOf(voteItem.getMyVoteCount()));
            }

            witness.put(address, String.valueOf(vote));

            return Tron.getInstance(mContext).voteWitness(witness).blockingGet();
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    mView.successVote();
                } else {
                    mView.showServerError();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.showServerError();
            }
        });
    }

    class Descending implements Comparator<VoteItem> {

        @Override
        public int compare(VoteItem o1, VoteItem o2) {
            return o2.getVoteCount().compareTo(o1.getVoteCount());
        }
    }
}
