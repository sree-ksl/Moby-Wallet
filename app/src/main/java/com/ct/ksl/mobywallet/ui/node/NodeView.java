package com.ct.ksl.mobywallet.ui.node;

import com.ct.ksl.mobywallet.ui.mvp.IView;

public interface NodeView extends IView {

    void displayNodeList(int count);
    void errorNodeList();

}
