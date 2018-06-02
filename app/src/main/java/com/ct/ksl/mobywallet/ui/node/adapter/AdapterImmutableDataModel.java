package com.ct.ksl.mobywallet.ui.node.adapter;

public interface AdapterImmutableDataModel<W,O> {
    O getModel(int position);
    int getSize();
    void setModelList(W w);
    void clear();
}
