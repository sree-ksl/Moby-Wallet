package com.ct.ksl.mobywallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by sreek on 31/05/18.
 */

class VotingAdapter extends BaseAdapter {

    Context context;
    String address1List[];
    LayoutInflater inflter;

    public VotingAdapter(Context applicationContext, String[] address1List) {
        this.context = applicationContext;
        this.address1List = address1List;
        inflter = (LayoutInflater.from(applicationContext));

    }

    @Override
    public int getCount() {
        return address1List.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.vote_list_item, null);
        TextView address = view.findViewById(R.id.address1);
        address.setText(address1List[i]);
        return view;
    }
}
