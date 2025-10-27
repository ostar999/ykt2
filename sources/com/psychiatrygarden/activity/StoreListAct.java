package com.psychiatrygarden.activity;

import androidx.fragment.app.FragmentTransaction;
import com.psychiatrygarden.fragmenthome.StoreNewFragment;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class StoreListAct extends BaseActivity {
    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.add(R.id.fl_container, StoreNewFragment.newInstance(true));
        fragmentTransactionBeginTransaction.commit();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_store_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
