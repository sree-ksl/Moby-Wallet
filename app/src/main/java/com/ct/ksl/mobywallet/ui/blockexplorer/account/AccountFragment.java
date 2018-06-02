package com.ct.ksl.mobywallet.ui.blockexplorer.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ct.ksl.mobywallet.R;
import com.ct.ksl.mobywallet.common.AdapterView;
import com.ct.ksl.mobywallet.common.BaseFragment;
import com.ct.ksl.mobywallet.common.DividerItemDecoration;
import com.ct.ksl.mobywallet.ui.blockexplorer.adapter.TronAccountAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018. 5. 24..
 */

public class AccountFragment extends BaseFragment implements AccountView {

    private static final int PAGE_SIZE = 25;

    @BindView(R.id.recycler_view)
    RecyclerView mAccountListView;

    private LinearLayoutManager mLayoutManager;
    private AdapterView mAdapterView;
    private TronAccountAdapter mTronAccountAdapter;

    private long mStartIndex = 0;

    private boolean mIsLoading;

    private boolean mIsLastPage;

    public static BaseFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        initUi();

        mPresenter = new AccountPresenter(this);
        ((AccountPresenter) mPresenter).setAdapterDataModel(mTronAccountAdapter);
        mPresenter.onCreate();

        return view;
    }

    private void initUi() {
        mTronAccountAdapter = new TronAccountAdapter(getActivity(), mOnItemClickListener);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mAccountListView.setLayoutManager(mLayoutManager);
        mAccountListView.addItemDecoration(new DividerItemDecoration(0));
        mAccountListView.setAdapter(mTronAccountAdapter);
        mAccountListView.addOnScrollListener(mRecyclerViewOnScrollListener);

        mAdapterView = mTronAccountAdapter;
    }

    private RecyclerView.OnScrollListener mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            if (!mIsLoading && !mIsLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    mIsLoading = true;
                    ((AccountPresenter) mPresenter).getTronAccounts(mStartIndex, PAGE_SIZE);
                }
            }
        }
    };

    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    protected void refresh() {
        if (!mIsLastPage && isAdded()) {
            ((AccountPresenter) mPresenter).getTronAccounts(mStartIndex, PAGE_SIZE);
        }
    }

    @Override
    public void finishLoading(long total) {
        if (!isAdded()) {
            return;
        }
        mStartIndex += PAGE_SIZE;

        if (mStartIndex >= total) {
            mIsLastPage = true;
        }

        mIsLoading = false;
        mAdapterView.refresh();

        hideDialog();
    }

    @Override
    public void showLoadingDialog() {
        showProgressDialog(null, getString(R.string.loading_msg));
    }

    @Override
    public void showServerError() {
        mIsLoading = false;
        if (isAdded()) {
            hideDialog();
            Toast.makeText(getActivity(), getString(R.string.connection_error_msg),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
