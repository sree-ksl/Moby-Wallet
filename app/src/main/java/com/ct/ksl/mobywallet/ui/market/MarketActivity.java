package com.ct.ksl.mobywallet.ui.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.ct.ksl.tronlib.dto.Market;
import com.ct.ksl.mobywallet.R;
import com.ct.ksl.mobywallet.common.CommonActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018. 5. 24..
 */

public class MarketActivity extends CommonActivity implements MarketView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MarketAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        ButterKnife.bind(this);

        initUi();

        mPresenter = new MarketPresenter(this);
        mPresenter.onCreate();
    }

    private void initUi() {
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_markets);
        }

        mAdapter = new MarketAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void marketDataLoadSuccess(List<Market> markets) {
        hideDialog();
        if (mAdapter != null) {
            mAdapter.refresh(markets);
        }
    }

    @Override
    public void showLoadingDialog() {
        showProgressDialog(null, getString(R.string.loading_msg));
    }

    @Override
    public void showServerError() {
        if (!isFinishing()) {
            hideDialog();
            Toast.makeText(this, getString(R.string.connection_error_msg), Toast.LENGTH_SHORT).show();
        }
    }
}
