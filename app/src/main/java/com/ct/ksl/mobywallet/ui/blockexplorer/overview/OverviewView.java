package com.ct.ksl.mobywallet.ui.blockexplorer.overview;

import com.ct.ksl.tronlib.dto.Stat;
import com.ct.ksl.tronlib.dto.SystemStatus;
import com.ct.ksl.tronlib.dto.TopAddressAccounts;
import com.ct.ksl.mobywallet.ui.mvp.IView;

import java.util.List;

/**
 * Created by user on 2018. 5. 28..
 */

public interface OverviewView extends IView {

    void overviewBlockStatus(SystemStatus systemStatus);
    void overviewDataLoadSuccess(TopAddressAccounts topAddressAccounts);
    void overviewTransferPastHour(List<Stat> stats);
    void overviewTransactionPastHour(List<Stat> stats);
    void overviewAvgBlockSize(List<Stat> stats);
    void richListLoadSuccess(List<RichItemViewModel> viewModels);
    void showLoadingDialog();
    void showServerError();

}
