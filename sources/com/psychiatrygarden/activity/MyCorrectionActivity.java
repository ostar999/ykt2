package com.psychiatrygarden.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.psychiatrygarden.fragmenthome.CommentFragment;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class MyCorrectionActivity extends BaseActivity {
    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("module_type", 25);
        bundle.putString(com.umeng.analytics.pro.aq.f22519d, "" + getIntent().getExtras().getString(com.umeng.analytics.pro.aq.f22519d));
        commentFragment.setArguments(bundle);
        loadRootFragment(R.id.fragment, commentFragment);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof CommentFragment) {
                    ((CommentFragment) fragment).onFragmentResult(requestCode, resultCode, data.getBundleExtra("bundleIntent"));
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_correct);
        setTitle("我的纠错");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
