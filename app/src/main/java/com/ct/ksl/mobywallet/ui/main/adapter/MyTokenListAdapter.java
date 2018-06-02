package com.ct.ksl.mobywallet.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ct.ksl.mobywallet.R;
import com.ct.ksl.mobywallet.common.AdapterDataModel;
import com.ct.ksl.mobywallet.common.AdapterView;
import com.ct.ksl.mobywallet.common.Constants;
import com.ct.ksl.mobywallet.ui.main.dto.Asset;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTokenListAdapter extends RecyclerView.Adapter<MyTokenListAdapter.MyTokenViewHolder> implements AdapterDataModel<Asset>, AdapterView {

    private List<Asset> mList;

    private Context mContext;

    public MyTokenListAdapter(Context mContext) {
        this.mList = new ArrayList<>();
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyTokenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_token, null);
        v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        return new MyTokenViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTokenViewHolder holder, int position) {
        Asset item = mList.get(position);

        holder.tokenNameText.setText(item.getName());
        holder.tokenAmountText.setText(Constants.tronBalanceFormat.format(item.getBalance()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void add(Asset model) {
        mList.add(model);
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public void addAll(List<Asset> list) {
        mList.addAll(list);
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public void remove(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public Asset getModel(int position) {
        return mList.get(position);
    }

    @Override
    public int getSize() {
        return mList.size();
    }

    @Override
    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    public class MyTokenViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.token_name_text)
        TextView tokenNameText;

        @BindView(R.id.token_amount_text)
        TextView tokenAmountText;

        public MyTokenViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
