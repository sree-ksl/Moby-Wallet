package com.ct.ksl.mobywallet.ui.vote;

import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface VoteView extends IView {

    void showLoadingDialog();

    void showServerError();

    void displayVoteInfo(long totalVotes, long voteItemCount, long myVotePoint, long totalMyVotes);

    void successVote();

    void refreshList();
}
