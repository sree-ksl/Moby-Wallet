package com.ct.ksl.mobywallet.ui.accountdetail.overview;

import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface VoteView extends IView {

    void finishLoading(long totalVotes, long total);
    void showLoadingDialog();
    void showServerError();
}
