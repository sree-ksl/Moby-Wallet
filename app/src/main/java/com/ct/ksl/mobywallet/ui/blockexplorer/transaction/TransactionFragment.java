package com.ct.ksl.mobywallet.ui.blockexplorer.transaction;

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
import com.ct.ksl.mobywallet.ui.accountdetail.adapter.AccountTransactionAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionFragment extends BaseFragment implements TransactionView {

    private static final int PAGE_SIZE = 25;

    @BindView(R.id.recycler_view)
    RecyclerView mListView;

    private LinearLayoutManager mLayoutManager;
    private AdapterView mAdapterView;
    private AccountTransactionAdapter mAdapter;

    private long mStartIndex = 0;

    private boolean mIsLoading;

    private boolean mIsLastPage;

    public static BaseFragment newInstance() {
        TransactionFragment fragment = new TransactionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        ButterKnife.bind(this, view);
        initUi();

        mPresenter = new TransactionPresenter(this);
        ((TransactionPresenter) mPresenter).setAdapterDataModel(mAdapter);
        mPresenter.onCreate();

        return view;
    }

    private void initUi() {
        mAdapter = new AccountTransactionAdapter(getActivity(), mOnItemClickListener, true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mListView.setLayoutManager(mLayoutManager);
        mListView.addItemDecoration(new DividerItemDecoration(0));
        mListView.setAdapter(mAdapter);
        mListView.addOnScrollListener(mRecyclerViewOnScrollListener);

        mAdapterView = mAdapter;
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
                    ((TransactionPresenter) mPresenter).getTransactions(mStartIndex, PAGE_SIZE);
                }
            }
        }
    };

    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //int pos = mListView.getChildLayoutPosition(v);
            //Transaction item = mAdapter.getItem(pos);
            // todo
        }
    };

    @Override
    protected void refresh() {
        if (!mIsLastPage && isAdded()) {
            ((TransactionPresenter) mPresenter).getTransactions(mStartIndex, PAGE_SIZE);
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
        if (isAdded()) {
            hideDialog();
            Toast.makeText(getActivity(), getString(R.string.connection_error_msg),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
