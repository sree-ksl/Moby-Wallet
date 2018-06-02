package com.ct.ksl.mobywallet.ui.intro;

import com.ct.ksl.mobywallet.ui.mvp.IView;

interface IntroView extends IView {

    void startCreateAccountActivity();

    void startLoginActivity();

    void startBackupAccountActivity();
}
