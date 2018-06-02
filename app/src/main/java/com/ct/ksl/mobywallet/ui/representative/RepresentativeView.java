package com.ct.ksl.mobywallet.ui.representative;

import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface RepresentativeView extends IView {

    void showLoadingDialog();

    void displayRepresentativeInfo(int witnessCount, long highestVotes);

    void showServerError();
}
