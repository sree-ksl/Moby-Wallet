package com.ct.ksl.mobywallet.ui.market;

import com.ct.ksl.tronlib.dto.Market;
import com.ct.ksl.mobywallet.ui.mvp.IView;

import java.util.List;

/**
 * Created by user on 2018. 5. 24..
 */

public interface MarketView extends IView {
    void marketDataLoadSuccess(List<Market> markets);
    void showLoadingDialog();
    void showServerError();
}
