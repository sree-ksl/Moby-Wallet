package com.ct.ksl.mobywallet.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ct.ksl.mobywallet.R;
import com.ct.ksl.mobywallet.ui.mvp.BasePresenter;

/**
 * Created by user on 2018. 5. 25..
 */

public abstract class BaseFragment extends Fragment {

    protected BasePresenter mPresenter;
    protected MaterialDialog mMaterialDialog;


    protected abstract void refresh();

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onDestroy() {
        hideDialog();
        super.onDestroy();
    }

    public void showProgressDialog(@Nullable String title, @NonNull String msg) {
        hideDialog();

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        if (!TextUtils.isEmpty(title)) {
            builder.title(title);
        }

        mMaterialDialog = builder
                .titleColorRes(R.color.colorAccent)
                .contentColorRes(R.color.colorAccent)
                .backgroundColorRes(android.R.color.white)
                .content(msg)
                .progress(true,0)
                .canceledOnTouchOutside(false)
                .show();
    }

    protected void hideDialog() {
        if (mMaterialDialog != null) {
            mMaterialDialog.dismiss();
            mMaterialDialog = null;
        }
    }
}
