package com.psychiatrygarden.activity.ebook;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.BookStackBean;
import com.psychiatrygarden.forum.ForumSearchAct;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.PopupShowManager;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class BookStoreActivity extends BaseActivity {
    private CustomEmptyView emptyView;
    private BookStoreAdp mAdapter;
    private ClearEditText mEtSearch;
    private ImageView mImgBack;
    private ImageView mImgFilter;
    private TextView mTvSearch;
    private RecyclerView recyclerView;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        ProjectBookStoreActivity.newIntent(this, "", this.mAdapter.getItem(i2).getApp_id());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        BookShelfActivity.newIntent(this, "", "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        ForumSearchAct.newIntent(this.mContext, "", null, 3);
    }

    private void loadData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.bookStackList, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ebook.BookStoreActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                BookStoreActivity.this.emptyView.setLoadFileResUi(BookStoreActivity.this);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    BookStackBean bookStackBean = (BookStackBean) new Gson().fromJson(s2, BookStackBean.class);
                    if (!bookStackBean.getCode().equals("200") || bookStackBean.getData() == null || bookStackBean.getData() == null || bookStackBean.getData().isEmpty()) {
                        BookStoreActivity.this.emptyView.uploadEmptyViewResUi();
                    } else {
                        BookStoreActivity.this.mAdapter.setList(bookStackBean.getData());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    BookStoreActivity.this.emptyView.setLoadFileResUi(BookStoreActivity.this);
                }
            }
        });
    }

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) BookStoreActivity.class));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.recyclerView = (RecyclerView) findViewById(R.id.rvList);
        this.mImgFilter = (ImageView) findViewById(R.id.iv_book_shelf);
        this.mEtSearch = (ClearEditText) findViewById(R.id.et_search);
        this.mTvSearch = (TextView) findViewById(R.id.tv_search_area);
        this.mEtSearch.setVisibility(8);
        this.mTvSearch.setVisibility(0);
        BookStoreAdp bookStoreAdp = new BookStoreAdp();
        this.mAdapter = bookStoreAdp;
        this.recyclerView.setAdapter(bookStoreAdp);
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12296c.lambda$init$0(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.ebook.m
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12297c.lambda$init$1(baseQuickAdapter, view, i2);
            }
        });
        loadData();
        PopupShowManager.getInstance(this).checkShowCoupon(this, "ENTER_BOOK_HOME", "2", "4", null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_book_store);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12298c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mImgFilter.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12299c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mTvSearch.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12300c.lambda$setListenerForWidget$4(view);
            }
        });
    }
}
