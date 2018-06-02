package com.ct.ksl.mobywallet.ui.blockdetail.fragment;

import android.support.annotation.NonNull;

import com.ct.ksl.tronlib.dto.Block;
import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface BlockInfoView extends IView {
    void showLoadingDialog();
    void showServerError();
    void finishLoading(@NonNull Block block);
}
