package com.psychiatrygarden.aliPlayer.utils;

import android.os.Bundle;
import com.psychiatrygarden.activity.BaseActivity;

/* loaded from: classes5.dex */
public class BaseAppCompatActivity extends BaseActivity {
    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() throws IllegalAccessException, NoSuchFieldException, SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        CleanLeakUtils.fixInputMethodManagerLeak(this);
        CleanLeakUtils.fixHuaWeiMemoryLeak(this);
        CleanLeakUtils.fixTextLineCacheLeak();
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
