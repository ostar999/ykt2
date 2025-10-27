package com.psychiatrygarden.activity.ebook;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.FragmentTransaction;
import com.psychiatrygarden.activity.BaseActivity;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class BookStoreSearchActivity extends BaseActivity {
    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) BookStoreSearchActivity.class));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.fl_container, BookStoreFragment.getInstance(true, true));
        fragmentTransactionBeginTransaction.commit();
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_book_search);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
