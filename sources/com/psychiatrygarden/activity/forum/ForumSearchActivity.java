package com.psychiatrygarden.activity.forum;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.fragment.app.FragmentTransaction;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.forum.fragment.ForumSectionFragmment;
import com.psychiatrygarden.widget.ClearEditText;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ForumSearchActivity extends BaseActivity {
    public ClearEditText ed_search;
    public TextView searchTxt;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        if (this.ed_search.getText().toString().equals("")) {
            return;
        }
        showSchoolData(this.ed_search.getText().toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$1(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (this.ed_search.getText().toString().equals("")) {
            return true;
        }
        showSchoolData(this.ed_search.getText().toString());
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.ed_search = (ClearEditText) findViewById(R.id.ed_search);
        TextView textView = (TextView) findViewById(R.id.searchTxt);
        this.searchTxt = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12406c.lambda$init$0(view);
            }
        });
        this.ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.activity.forum.m
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView2, int i2, KeyEvent keyEvent) {
                return this.f12407c.lambda$init$1(textView2, i2, keyEvent);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(131072);
        getWindow().setSoftInputMode(5);
        setTitle("版块搜索");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_forum_search);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void showSchoolData(String keywords) {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.fragment, ForumSectionFragmment.getInstent("0", keywords));
        fragmentTransactionBeginTransaction.commitNowAllowingStateLoss();
    }
}
