package com.ct.ksl.mobywallet.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ct.ksl.tronlib.dto.CoinMarketCap;
import com.ct.ksl.mobywallet.R;
import com.ct.ksl.mobywallet.common.AdapterView;
import com.ct.ksl.mobywallet.common.CommonActivity;
import com.ct.ksl.mobywallet.common.Constants;
import com.ct.ksl.mobywallet.common.DividerItemDecoration;
import com.ct.ksl.mobywallet.database.model.AccountModel;
import com.ct.ksl.mobywallet.ui.address.AddressActivity;
import com.ct.ksl.mobywallet.ui.blockexplorer.BlockExplorerActivity;
import com.ct.ksl.mobywallet.ui.login.LoginActivity;
import com.ct.ksl.mobywallet.ui.main.adapter.MyTokenListAdapter;
import com.ct.ksl.mobywallet.ui.main.dto.Frozen;
import com.ct.ksl.mobywallet.ui.main.dto.TronAccount;
import com.ct.ksl.mobywallet.ui.more.MoreActivity;
import com.ct.ksl.mobywallet.ui.myaccount.MyAccountActivity;
import com.ct.ksl.mobywallet.ui.qrscan.QrScanActivity;
import com.ct.ksl.mobywallet.ui.requestcoin.RequestCoinActivity;
import com.ct.ksl.mobywallet.ui.sendtoken.SendTokenActivity;
import com.ct.ksl.mobywallet.ui.token.TokenActivity;
import com.ct.ksl.mobywallet.ui.mytransfer.TransferActivity;
import com.ct.ksl.mobywallet.ui.vote.VoteActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends CommonActivity implements MainView, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.appbar_layout)
    public AppBarLayout mAppBarLayout;

    @BindView(R.id.toolbar_layout)
    public CollapsingToolbarLayout mToolbarLayout;

    @BindView(R.id.nav_view)
    NavigationView mSideMenu;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.login_account_name_text)
    TextView mMainTitleText;

    @BindView(R.id.login_account_balance_text)
    TextView mLoginAccountBalanceText;

    @BindView(R.id.login_account_price_text)
    TextView mLoginAccountPriceText;

    @BindView(R.id.login_frozen_balance_text)
    TextView mLoginFrozenBalanceText;

    @BindView(R.id.login_bandwidth_text)
    TextView mLoginBandwidthText;

    @BindView(R.id.price_help_image)
    ImageView mPriceHelpImage;

    @BindView(R.id.edit_account_name_image)
    ImageView mEditAccountNameImage;

    @BindView(R.id.fab_menu)
    FloatingActionMenu mFloatingActionMenu;

    @BindView(R.id.no_token_layout)
    LinearLayout mNoTokenLayout;

    @BindView(R.id.my_token_listview)
    RecyclerView mMyTokenListView;

    FloatingActionButton mFloatingActionMenuRequestCoin;

    FloatingActionButton mFloatingActionMenuSendCoin;

    Spinner mAccountSpinner;

    TextView mNavHeaderText;

    String mLoginAccountName;

    private TronAccount mLoginTronAccount;

    private CoinMarketCap mCoinMarketCapPriceInfo;

    private ArrayAdapter<AccountModel> mAccountAdapter;

    private LinearLayoutManager mLayoutManager;
    private AdapterView mAdapterView;
    private MyTokenListAdapter mMyTokenListAdapter;

    private boolean mLoadingAccountInfo;

    private boolean mDoubleBackToExitPressedOnce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setupDrawerLayout();

        mToolbarLayout.setTitle("");
        mToolbar.setTitle("");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        mFloatingActionMenuRequestCoin = (FloatingActionButton) mFloatingActionMenu.findViewById(R.id.fab_menu_request_coin);
        mFloatingActionMenuSendCoin = (FloatingActionButton) mFloatingActionMenu.findViewById(R.id.fab_menu_send_coin);

        mFloatingActionMenuRequestCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RequestCoinActivity.class);
            }
        });

        mFloatingActionMenuSendCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QrScanActivity.class);
                intent.putExtra(QrScanActivity.EXTRA_FROM_TRON_PAY_MENU, true);
                startActivity(intent);
            }
        });

        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMyTokenListView.setLayoutManager(mLayoutManager);
        mMyTokenListView.addItemDecoration(new DividerItemDecoration(0));
        mMyTokenListView.setNestedScrollingEnabled(false);

        mMyTokenListAdapter = new MyTokenListAdapter(MainActivity.this);
        mMyTokenListView.setAdapter(mMyTokenListAdapter);
        mAdapterView = mMyTokenListAdapter;

        mPresenter = new MainPresenter(this);
        ((MainPresenter) mPresenter).setAdapterDataModel(mMyTokenListAdapter);
        mPresenter.onCreate();

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mToolbarLayout.setTitle(mLoginAccountName);
                    mMainTitleText.setVisibility(View.GONE);
                    mLoginAccountBalanceText.setVisibility(View.GONE);
                    mLoginAccountPriceText.setVisibility(View.GONE);
                    mPriceHelpImage.setVisibility(View.GONE);
                    mEditAccountNameImage.setVisibility(View.GONE);
                    mLoginFrozenBalanceText.setVisibility(View.GONE);
                    mLoginBandwidthText.setVisibility(View.GONE);
                    isShow = true;
                } else if(isShow) {
                    mToolbarLayout.setTitle("");
                    mMainTitleText.setVisibility(View.VISIBLE);
                    mLoginAccountBalanceText.setVisibility(View.VISIBLE);
                    mLoginAccountPriceText.setVisibility(View.VISIBLE);
                    mPriceHelpImage.setVisibility(View.VISIBLE);
                    mEditAccountNameImage.setVisibility(View.VISIBLE);
                    mLoginFrozenBalanceText.setVisibility(View.VISIBLE);
                    mLoginBandwidthText.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });

        initAccountList(false);
    }

    private void setupDrawerLayout() {
        View header = LayoutInflater.from(this).inflate(R.layout.navigation_header, mSideMenu);

        mNavHeaderText = (TextView) header.findViewById(R.id.headerTitleText);
        mAccountSpinner = header.findViewById(R.id.account_spinner);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawer.addDrawerListener(toggle);

        toggle.syncState();

        mSideMenu.setNavigationItemSelectedListener(this);
        mSideMenu.inflateMenu(R.menu.navigation_logged_in_menu);
    }

    private void initAccountList(boolean isCreateOrImport) {
        ((MainPresenter) mPresenter).getAccountList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new SingleObserver<List<AccountModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<AccountModel> accountModelList) {
                initAccountList(accountModelList);
                if (isCreateOrImport) {
                    mAccountSpinner.setSelection(mAccountAdapter.getCount() - 1);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                initAccountList(new ArrayList<>());
            }
        });
    }

    private void initAccountList(List<AccountModel> accountModelList) {
        mAccountAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item,
                accountModelList);

        mAccountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAccountSpinner.setAdapter(mAccountAdapter);
        mAccountSpinner.setOnItemSelectedListener(mAccountItemSelectedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLoginState();
    }

    private void checkLoginState() {
        if (mLoadingAccountInfo) {
            return;
        }

        if (((MainPresenter) mPresenter).isLogin()) {
            mLoadingAccountInfo = true;
            // get account info
            ((MainPresenter) mPresenter).getMyAccountInfo();

            Single.fromCallable(() -> ((MainPresenter) mPresenter).getLoginAccount())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<AccountModel>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(AccountModel loginAccount) {
                    if (loginAccount == null) {
                        mNavHeaderText.setText(R.string.navigation_header_title);
                    } else {
                        mLoginAccountName = loginAccount.getName();
                        mNavHeaderText.setText(mLoginAccountName);
                        mMainTitleText.setText(mLoginAccountName);
                    }
                }

                @Override
                public void onError(Throwable e) {

                }
            });

            ((MainPresenter) mPresenter).getAccountList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new SingleObserver<List<AccountModel>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(List<AccountModel> accountModelList) {
                    int id = ((MainPresenter) mPresenter).getLoginAccountIndex();

                    int size = accountModelList.size();

                    for (int i = 0; i < size; i++) {
                        if (id == accountModelList.get(i).getId()) {
                            if (mAccountSpinner.getSelectedItemPosition() != i) {
                                mAccountSpinner.setSelection(i);
                                return;
                            }
                            break;
                        }
                     }

                     mAccountAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(Throwable e) {

                }
            });
        } else {
            finishActivity();
            startActivity(LoginActivity.class);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_account:
                createAccount();
                break;
            case R.id.action_import_account:
                importAccount();
                break;
            case R.id.action_refresh_account:
                checkLoginState();
                break;
            case R.id.action_transaction_history:
                startActivity(new Intent(this, TransferActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawers();
        } else {
            if (mDoubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.mDoubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.activity_main_back_exit, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(() -> mDoubleBackToExitPressedOnce = false, 2000);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_item_my_account:
                startActivity(MyAccountActivity.class);
                break;
            case R.id.drawer_item_my_address:
                startActivity(AddressActivity.class);
                break;
            case R.id.drawer_item_send_tron:
                startActivity(SendTokenActivity.class);
                break;
            case R.id.drawer_item_vote:
                startActivity(VoteActivity.class);
                break;
            case R.id.drawer_item_tokens:
                startActivity(TokenActivity.class);
                break;
            case R.id.drawer_item_block_explorer:
                startActivity(BlockExplorerActivity.class);
                break;
            case R.id.drawer_item_more:
                 startActivity(MoreActivity.class);
                break;
            case R.id.drawer_item_logout:
                logout();
                break;
        }
        return false;
    }

    public void logout() {
        ((MainPresenter) mPresenter).logout();
        checkLoginState();
    }

    private void createAccount() {
        showProgressDialog(null, getString(R.string.loading_msg));
        ((MainPresenter) mPresenter).createAccount(Constants.PREFIX_ACCOUNT_NAME);
    }

    private void importAccount() {
        new MaterialDialog.Builder(this)
                .title(R.string.title_import_account)
                .titleColorRes(R.color.colorAccent)
                .contentColorRes(R.color.colorAccent)
                .backgroundColorRes(android.R.color.white)
                .inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input(getString(R.string.import_account_hint), "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        dialog.dismiss();
                        String privateKey = input.toString();

                        if (!TextUtils.isEmpty(privateKey)) {
                            ((MainPresenter) mPresenter).importAccount(Constants.PREFIX_ACCOUNT_NAME, privateKey);
                        }
                    }
                }).show();
    }

    @Override
    public void displayAccountInfo(@NonNull TronAccount account) {
        mLoginTronAccount = account;

        if (mLoginTronAccount.getAssetList().isEmpty()) {
            mNoTokenLayout.setVisibility(View.VISIBLE);
            mMyTokenListView.setVisibility(View.GONE);
        } else {
            mNoTokenLayout.setVisibility(View.GONE);
            mMyTokenListView.setVisibility(View.VISIBLE);
        }

        double balance = ((double) account.getBalance()) / Constants.ONE_TRX;
        long frozenBalance = 0;

        for (int i = 0; i < account.getFrozenList().size(); i++) {
            Frozen frozen = account.getFrozenList().get(i);

            frozenBalance += frozen.getFrozenBalance();
        }

        double fz = frozenBalance / Constants.ONE_TRX;
        double bandwidth = account.getBandwidth();

        mLoginAccountBalanceText.setText(Constants.tronBalanceFormat.format(balance) + " " + getString(R.string.currency_text));
        mLoginFrozenBalanceText.setText(Constants.numberFormat.format(fz));
        mLoginBandwidthText.setText(Constants.numberFormat.format(bandwidth));

        mLoadingAccountInfo = false;

        ((MainPresenter) mPresenter).getTronMarketInfo();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setTronMarketInfo(CoinMarketCap coinMarketCap) {
        double balance = ((double) mLoginTronAccount.getBalance()) / Constants.ONE_TRX;

        mLoginAccountPriceText.setText("(" + Constants.usdFormat.format(balance * Double.parseDouble(coinMarketCap.getPriceUsd()))
                + " " + getString(R.string.price_text) + ")");

        mCoinMarketCapPriceInfo = coinMarketCap;

        mPriceHelpImage.setVisibility(View.VISIBLE);
        mLoginAccountPriceText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInvalidPasswordMsg() {
        hideDialog();
        Toast.makeText(MainActivity.this, getString(R.string.invalid_password),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successCreateAccount() {
        hideDialog();
        new MaterialDialog.Builder(MainActivity.this)
                .title(getString(R.string.backup_title))
                .content(getString(R.string.create_account_backup_msg))
                .titleColorRes(R.color.colorAccent)
                .contentColorRes(android.R.color.black)
                .backgroundColorRes(android.R.color.white)
                .positiveText(R.string.close_text)
                .autoDismiss(true)
                .build()
                .show();

        initAccountList(true);
    }

    @Override
    public void successImportAccount() {
        hideDialog();
        initAccountList(true);
    }

    @Override
    public void failCreateAccount() {
        hideDialog();
        Toast.makeText(MainActivity.this, getString(R.string.invalid_private_key),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void duplicatedAccount() {
        hideDialog();
        Toast.makeText(MainActivity.this, getString(R.string.already_exist_account),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectionError() {
        mLoadingAccountInfo = false;
        Toast.makeText(MainActivity.this, getString(R.string.connection_error_msg),
                Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fab_refresh)
    public void onHistoryClick() {
        checkLoginState();
    }

    @OnClick({ R.id.login_account_price_layout })
    public void onPriceHelpImageClick() {
        StringBuilder sb = new StringBuilder();

        if (mCoinMarketCapPriceInfo == null) {
            return;
        }

        Date updated = new Date(Long.parseLong(mCoinMarketCapPriceInfo.getLastUpdated()) * 1_000);

        sb.append("Price : ")
                .append(mCoinMarketCapPriceInfo.getPriceUsd())
                .append(" USD (")
                .append("-".equals(mCoinMarketCapPriceInfo.getPercentChange24h().substring(0, 1)) ?
                        mCoinMarketCapPriceInfo.getPercentChange24h() :
                        "+" + mCoinMarketCapPriceInfo.getPercentChange24h()
                )
                .append("%)\nLast updated : ")
                .append(Constants.sdf.format(updated))
                .append("\nFrom CoinMarketCap");

        hideDialog();

        new MaterialDialog.Builder(MainActivity.this)
                .title(getString(R.string.tron_price_title))
                .content(sb.toString())
                .titleColorRes(android.R.color.black)
                .contentColorRes(android.R.color.black)
                .backgroundColorRes(android.R.color.white)
                .positiveText(R.string.close_text)
                .autoDismiss(true)
                .build()
                .show();
    }

    @OnClick(R.id.edit_account_name_image)
    public void onEditAccountNameImageClick() {
        new MaterialDialog.Builder(this)
                .title(R.string.title_rename_account)
                .titleColorRes(R.color.colorAccent)
                .contentColorRes(R.color.colorAccent)
                .backgroundColorRes(android.R.color.white)
                .inputRangeRes(2, 20, android.R.color.white)
                .input(getString(R.string.rename_account_hint), mLoginAccountName, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        dialog.dismiss();
                        String accountName = input.toString();

                        if (!TextUtils.isEmpty(accountName)) {
                            ((MainPresenter) mPresenter).changeLoginAccountName(accountName)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<Boolean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(Boolean result) {
                                    if (result) {
                                        checkLoginState();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                        }
                    }
                }).show();
    }

    private android.widget.AdapterView.OnItemSelectedListener mAccountItemSelectedListener = new android.widget.AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(android.widget.AdapterView<?> adapterView, View view, int pos, long id) {
            AccountModel accountModel = mAccountAdapter.getItem(pos);
            ((MainPresenter) mPresenter).changeLoginAccount(accountModel);
            mMainTitleText.setText(accountModel.getName());
            mDrawer.closeDrawers();
            checkLoginState();
        }

        @Override
        public void onNothingSelected(android.widget.AdapterView<?> adapterView) {

        }
    };

    @OnClick(R.id.get_token_button)
    public void onGetTokenClick() {
        startActivity(TokenActivity.class);
    }
}
