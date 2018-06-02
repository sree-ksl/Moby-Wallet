package com.ct.ksl.mobywallet.ui.vote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ct.ksl.mobywallet.R;
import com.ct.ksl.mobywallet.common.AdapterView;
import com.ct.ksl.mobywallet.common.CommonActivity;
import com.ct.ksl.mobywallet.common.Constants;
import com.ct.ksl.mobywallet.common.DividerItemDecoration;
import com.ct.ksl.mobywallet.ui.accountdetail.AccountDetailActivity;
import com.ct.ksl.mobywallet.ui.vote.adapter.VoteListAdapter;
import com.ct.ksl.mobywallet.ui.vote.dto.VoteItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoteActivity extends CommonActivity implements VoteView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.appbar_layout)
    public AppBarLayout mAppBarLayout;

    @BindView(R.id.toolbar_layout)
    public CollapsingToolbarLayout mToolbarLayout;

    @BindView(R.id.listview)
    RecyclerView mVoteListView;

    @BindView(R.id.votes_remaining_count_title_text)
    TextView mVoteRemainingCountTitleText;

    @BindView(R.id.representative_title_text)
    TextView mRepresentativeTitleText;

    @BindView(R.id.representative_count_title_text)
    TextView mRepresentativeCountTitleText;

    @BindView(R.id.total_votes_title_text)
    TextView mTotalVotesTitleText;

    @BindView(R.id.representative_count_text)
    TextView mRepresentativeCountText;

    @BindView(R.id.total_votes_text)
    TextView mTotalVotesText;

    @BindView(R.id.votes_remaining_count_text)
    TextView mVoteRemainingCountText;

    @BindView(R.id.check_my_votes)
    CheckBox mCheckMyVotes;

    @BindView(R.id.retry_button)
    Button mRetryButton;

    private long mRemainVotePoint;

    private LinearLayoutManager mLayoutManager;
    private AdapterView mAdapterView;
    private VoteListAdapter mVoteListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mToolbarLayout.setTitle("");
        mToolbar.setTitle("");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        mLayoutManager = new LinearLayoutManager(VoteActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mVoteListView.setLayoutManager(mLayoutManager);
        mVoteListView.addItemDecoration(new DividerItemDecoration(0));
        mVoteListView.setNestedScrollingEnabled(false);

        mVoteListAdapter = new VoteListAdapter(VoteActivity.this, mViewClickListener, mVoteClickListener);
        mVoteListView.setAdapter(mVoteListAdapter);
        mAdapterView = mVoteListAdapter;

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mToolbarLayout.setTitle(getString(R.string.title_vote));
                    mRepresentativeTitleText.setVisibility(View.GONE);
                    mRepresentativeCountTitleText.setVisibility(View.GONE);
                    mTotalVotesTitleText.setVisibility(View.GONE);
                    mRepresentativeCountText.setVisibility(View.GONE);
                    mTotalVotesText.setVisibility(View.GONE);
                    mVoteRemainingCountTitleText.setVisibility(View.GONE);
                    mVoteRemainingCountText.setVisibility(View.GONE);
                    isShow = true;
                } else if(isShow) {
                    mToolbarLayout.setTitle("");
                    mRepresentativeTitleText.setVisibility(View.VISIBLE);
                    mRepresentativeCountTitleText.setVisibility(View.VISIBLE);
                    mTotalVotesTitleText.setVisibility(View.VISIBLE);
                    mRepresentativeCountText.setVisibility(View.VISIBLE);
                    mTotalVotesText.setVisibility(View.VISIBLE);
                    mVoteRemainingCountTitleText.setVisibility(View.VISIBLE);
                    mVoteRemainingCountText.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });

        mCheckMyVotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((VotePresenter) mPresenter).showOnlyMyVotes(mCheckMyVotes.isChecked());
            }
        });

        mPresenter = new VotePresenter(this);
        ((VotePresenter) mPresenter).setAdapterDataModel(mVoteListAdapter);
        mPresenter.onCreate();
    }

    @Override
    public void showLoadingDialog() {
        showProgressDialog(null, getString(R.string.loading_msg));
    }

    @Override
    public void showServerError() {
        mRetryButton.setVisibility(View.VISIBLE);
        hideDialog();
        Toast.makeText(VoteActivity.this, getString(R.string.connection_error_msg), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayVoteInfo(long totalVotes, long voteItemCount, long myVotePoint, long totalMyVotes) {
        mRetryButton.setVisibility(View.GONE);

        if (myVotePoint == 0) {
            new MaterialDialog.Builder(VoteActivity.this)
                    .title(getString(R.string.votes_help_text))
                    .content(getString(R.string.votes_help_msg_text))
                    .titleColorRes(R.color.colorAccent)
                    .contentColorRes(android.R.color.black)
                    .backgroundColorRes(android.R.color.white)
                    .positiveText(R.string.close_text)
                    .autoDismiss(true)
                    .build()
                    .show();
        }

        mRemainVotePoint = myVotePoint - totalMyVotes;

        mTotalVotesText.setText(Constants.numberFormat.format(totalVotes));
        mRepresentativeCountText.setText(Constants.numberFormat.format(voteItemCount));
        mVoteRemainingCountText.setText(Constants.numberFormat.format(mRemainVotePoint));

        mRepresentativeTitleText.setVisibility(View.VISIBLE);
        mRepresentativeCountTitleText.setVisibility(View.VISIBLE);
        mTotalVotesTitleText.setVisibility(View.VISIBLE);
        mRepresentativeCountText.setVisibility(View.VISIBLE);
        mTotalVotesText.setVisibility(View.VISIBLE);
        mVoteRemainingCountTitleText.setVisibility(View.VISIBLE);
        mVoteRemainingCountText.setVisibility(View.VISIBLE);

        hideDialog();
    }

    @Override
    public void successVote() {
        hideDialog();
        ((VotePresenter) mPresenter).getRepresentativeList(mCheckMyVotes.isChecked());
    }

    @Override
    public void refreshList() {
        mVoteListView.post(new Runnable() {
            @Override
            public void run() {
                mAdapterView.refresh();
            }
        });
    }

    @OnClick(R.id.votes_remaining_layout)
    public void onVoteRemainingHelpClick() {
        new MaterialDialog.Builder(VoteActivity.this)
                .title(getString(R.string.votes_remaining_text))
                .content(getString(R.string.votes_remaining_help_text))
                .titleColorRes(android.R.color.black)
                .contentColorRes(android.R.color.black)
                .backgroundColorRes(android.R.color.white)
                .positiveText(R.string.close_text)
                .autoDismiss(true)
                .build()
                .show();
    }

    @OnClick(R.id.retry_button)
    public void onRetryClick() {
        ((VotePresenter) mPresenter).getRepresentativeList(mCheckMyVotes.isChecked());
    }

    private View.OnClickListener mViewClickListener = view -> {
        int pos = mVoteListView.getChildLayoutPosition(view);
        VoteItem item = mVoteListAdapter.getItem(pos);
        Intent intent = new Intent(view.getContext(), AccountDetailActivity.class);
        intent.putExtra(AccountDetailActivity.EXTRA_ADDRESS, item.getAddress());
        startActivity(intent);
    };

    private View.OnClickListener mVoteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            VoteItem item = (VoteItem) v.getTag();

            if (item != null) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(VoteActivity.this)
                        .title(R.string.title_vote)
                        .titleColorRes(R.color.colorAccent)
                        .contentColorRes(R.color.colorAccent)
                        .backgroundColorRes(android.R.color.white)
                        .customView(R.layout.dialog_vote, false);

                MaterialDialog dialog = builder.build();

                TextView voteUrlText = (TextView) dialog.getCustomView().findViewById(R.id.vote_url_text);
                TextView voteAddressText = (TextView) dialog.getCustomView().findViewById(R.id.vote_address_text);
                Button voteButton = (Button) dialog.getCustomView().findViewById(R.id.btn_vote);
                CheckBox agreeVoteCheckBox = (CheckBox) dialog.getCustomView().findViewById(R.id.agree_vote);
                EditText inputVote = (EditText) dialog.getCustomView().findViewById(R.id.input_vote);
                EditText inputPassword = (EditText) dialog.getCustomView().findViewById(R.id.input_password);

                inputVote.setText(String.valueOf(item.getMyVoteCount()));
                voteUrlText.setText(item.getUrl());
                voteAddressText.setText(item.getAddress());

                voteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long voteBalance = 0;

                        try {
                            voteBalance = Long.parseLong(inputVote.getText().toString());
                        } catch (NumberFormatException e) {
                            Toast.makeText(VoteActivity.this, getString(R.string.invalid_vote),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (voteBalance <= 0 || voteBalance > mRemainVotePoint) {
                            Toast.makeText(VoteActivity.this, getString(R.string.invalid_vote),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String password = inputPassword.getText().toString();
                        if (TextUtils.isEmpty(password) || !((VotePresenter) mPresenter).matchPassword(password)) {
                            Toast.makeText(VoteActivity.this, getString(R.string.invalid_password),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        boolean agree = agreeVoteCheckBox.isChecked();

                        if (!agree) {
                            Toast.makeText(VoteActivity.this, getString(R.string.need_all_agree),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        dialog.dismiss();
                        ((VotePresenter) mPresenter).voteRepresentative(item.getAddress(), voteBalance);
                    }
                });

                dialog.show();
            }
        }
    };
}
