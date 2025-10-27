package com.psychiatrygarden.activity.mine;

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
import com.psychiatrygarden.bean.BookShelfBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.MyBookExcerptAct;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class MyBookAct extends BaseActivity {
    private CustomEmptyView emptyView;
    private MyBookAdp mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private int page = 1;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        MyBookExcerptAct.INSTANCE.newIntent(this, "", "", "", "");
    }

    private void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("page_size", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.readHistoryList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.MyBookAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                MyBookAct.this.mRefreshLayout.finishRefresh();
                if (MyBookAct.this.page == 1) {
                    MyBookAct.this.emptyView.setLoadFileResUi(MyBookAct.this);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                MyBookAct.this.emptyView.showEmptyView();
                MyBookAct.this.mRefreshLayout.finishRefresh();
                try {
                    BookShelfBean bookShelfBean = (BookShelfBean) new Gson().fromJson(s2, BookShelfBean.class);
                    if (bookShelfBean.getCode().equals("200")) {
                        if (bookShelfBean.getData() == null || bookShelfBean.getData() == null || bookShelfBean.getData().isEmpty()) {
                            MyBookAct.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                        } else if (MyBookAct.this.page == 1) {
                            MyBookAct.this.mAdapter.setList(bookShelfBean.getData());
                            if (bookShelfBean.getData().size() < 10) {
                                MyBookAct.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                            }
                        } else if (!bookShelfBean.getData().isEmpty()) {
                            MyBookAct.this.mAdapter.addData((Collection) bookShelfBean.getData());
                            if (bookShelfBean.getData().size() < 10) {
                                MyBookAct.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                            } else {
                                MyBookAct.this.mRefreshLayout.finishLoadMore();
                            }
                        }
                        MyBookAct.this.findViewById(R.id.tv_actionbar_right).setVisibility(MyBookAct.this.mAdapter.getData().isEmpty() ? 8 : 0);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (MyBookAct.this.page == 1) {
                        MyBookAct.this.emptyView.setLoadFileResUi(MyBookAct.this);
                    }
                }
            }
        });
    }

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) MyBookAct.class));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        ImageView imageView = (ImageView) findViewById(R.id.iv_actionbar_back);
        textView.setText("我的书摘");
        this.mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
        this.mAdapter = new MyBookAdp();
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12863c.lambda$init$0(view);
            }
        });
        this.emptyView.changeEmptyViewWriteBg();
        this.mAdapter.setEmptyView(this.emptyView);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12864c.lambda$init$1(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_book_read_record);
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.mine.f
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12862c.lambda$setListenerForWidget$2(baseQuickAdapter, view, i2);
            }
        });
    }
}
