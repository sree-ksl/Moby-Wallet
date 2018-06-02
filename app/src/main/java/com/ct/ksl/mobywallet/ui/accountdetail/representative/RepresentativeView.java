package com.ct.ksl.mobywallet.ui.accountdetail.representative;

import com.ct.ksl.mobywallet.ui.accountdetail.representative.model.BaseModel;
import com.ct.ksl.mobywallet.ui.mvp.IView;

import java.util.List;

public interface RepresentativeView extends IView {
    void dataLoadSuccess(List<BaseModel> viewModels);
    void showLoadingDialog();
    void showServerError();
}
