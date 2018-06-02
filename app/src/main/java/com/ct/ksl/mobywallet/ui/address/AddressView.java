package com.ct.ksl.mobywallet.ui.address;

import android.support.annotation.Nullable;

import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface AddressView extends IView {

    void addressResult(@Nullable AddressPresenter.AddressInfo addressInfo);

}
