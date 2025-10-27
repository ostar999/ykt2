package com.psychiatrygarden.activity.material;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.FragmentTransaction;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.fragmenthome.material.MaterialSearchFragment;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class MaterialSearchActivity extends BaseActivity {
    public static void newIntent(Context context, String appId) {
        Intent intent = new Intent(context, (Class<?>) MaterialSearchActivity.class);
        intent.putExtra("appId", appId);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        String stringExtra = getIntent().getStringExtra("appId");
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.fl_container, MaterialSearchFragment.getInstance(true, stringExtra));
        fragmentTransactionBeginTransaction.commit();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_material_search);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
