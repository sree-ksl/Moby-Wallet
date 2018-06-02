package com.ct.ksl.mobywallet.ui.blockexplorer.block;


import com.ct.ksl.tronlib.dto.Blocks;
import com.ct.ksl.mobywallet.ui.mvp.IView;

/**
 * Created by user on 2018. 5. 25..
 */

public interface BlockView extends IView {
    void blockDataLoadSuccess(Blocks blocks, boolean added);
    void showLoadingDialog();
    void showServerError();
}
