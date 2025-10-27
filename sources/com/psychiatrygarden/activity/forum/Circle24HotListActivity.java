package com.psychiatrygarden.activity.forum;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import java.util.Collection;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class Circle24HotListActivity extends BaseActivity {
    private CustomEmptyView emptyView;
    private Circle24HotListAdp mAdapter;
    private ImageView mImgBack;
    private ImageView mImgDownload;
    private ImageView mImgSearch;
    private SmartRefreshLayout mRefreshLayout;
    private TextView mTvTitle;
    private RecyclerView rvCircles;
    private int page = 1;
    private boolean isLoadMore = false;
    private int tempSort = 0;

    public static /* synthetic */ int access$112(Circle24HotListActivity circle24HotListActivity, int i2) {
        int i3 = circle24HotListActivity.page + i2;
        circle24HotListActivity.page = i3;
        return i3;
    }

    public static /* synthetic */ int access$308(Circle24HotListActivity circle24HotListActivity) {
        int i2 = circle24HotListActivity.tempSort;
        circle24HotListActivity.tempSort = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$0(View view) {
        this.page = 1;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setContentView$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        CirclrListBean.DataBean item = this.mAdapter.getItem(i2);
        if ("1".equals(item.getNo_access())) {
            startActivity(new Intent(this, (Class<?>) MemberCenterActivity.class));
            return;
        }
        Intent intent = new Intent(this, (Class<?>) CircleInfoActivity.class);
        intent.putExtra("article_id", "" + item.getId());
        intent.putExtra("commentCount", item.getComment_count());
        intent.putExtra("channel_id", "");
        intent.putExtra("module_type", item.getModule_type());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        ajaxParams.put("limit", "20");
        YJYHttpUtils.get(this.mContext.getApplicationContext(), NetworkRequestsURL.hotCircle, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.forum.Circle24HotListActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                Circle24HotListActivity.this.mRefreshLayout.finishRefresh();
                if (Circle24HotListActivity.this.page == 1) {
                    Circle24HotListActivity.this.emptyView.setLoadFileResUi(Circle24HotListActivity.this);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                Circle24HotListActivity.this.emptyView.showEmptyView();
                Circle24HotListActivity.this.mRefreshLayout.finishRefresh();
                try {
                    CirclrListBean circlrListBean = (CirclrListBean) new Gson().fromJson(s2, CirclrListBean.class);
                    if (!circlrListBean.getCode().equals("200")) {
                        Circle24HotListActivity.this.AlertToast(circlrListBean.getMessage());
                        return;
                    }
                    if (circlrListBean.getData() == null || circlrListBean.getData() == null || circlrListBean.getData().isEmpty()) {
                        Circle24HotListActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                        return;
                    }
                    for (int i2 = 0; i2 < circlrListBean.getData().size(); i2++) {
                        if (circlrListBean.getData().get(i2).getHeat_top().equals("1")) {
                            circlrListBean.getData().get(i2).setSort(0);
                        } else {
                            Circle24HotListActivity.access$308(Circle24HotListActivity.this);
                            circlrListBean.getData().get(i2).setSort(Circle24HotListActivity.this.tempSort);
                        }
                    }
                    if (Circle24HotListActivity.this.page == 1) {
                        Circle24HotListActivity.this.mAdapter.setList(circlrListBean.getData());
                        Circle24HotListActivity.this.mAdapter.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
                    } else {
                        Circle24HotListActivity.this.mAdapter.addData((Collection) circlrListBean.getData());
                        Circle24HotListActivity.this.mAdapter.getLoadMoreModule().loadMoreComplete();
                    }
                    if (Circle24HotListActivity.this.page == 1) {
                        Circle24HotListActivity.this.mAdapter.setList(circlrListBean.getData());
                        if (circlrListBean.getData().size() < 10) {
                            Circle24HotListActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                            return;
                        }
                        return;
                    }
                    if (circlrListBean.getData().isEmpty()) {
                        return;
                    }
                    Circle24HotListActivity.this.mAdapter.addData((Collection) circlrListBean.getData());
                    if (circlrListBean.getData().size() < 10) {
                        Circle24HotListActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        Circle24HotListActivity.this.mRefreshLayout.finishLoadMore();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (Circle24HotListActivity.this.page == 1) {
                        Circle24HotListActivity.this.emptyView.setLoadFileResUi(Circle24HotListActivity.this);
                    }
                }
            }
        });
    }

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) Circle24HotListActivity.class));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("24小时热贴");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initwriteStatusBar() {
        if (SkinManager.getCurrentSkinType(this) == 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white_color), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.app_bg_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        SkinManager.onActivityCreateSetSkin(this);
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        initwriteStatusBar();
        this.mContext = this;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        if (SkinManager.getCurrentSkinType(this) == 0) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
        setContentView(R.layout.activity_circle_24_hot_list);
        this.mTvTitle = (TextView) findViewById(R.id.txt_actionbar_title);
        this.rvCircles = (RecyclerView) findViewById(R.id.rvCircles);
        this.mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mImgDownload = (ImageView) findViewById(R.id.iv_download);
        this.mImgSearch = (ImageView) findViewById(R.id.iv_search);
        this.mTvTitle.setText("24小时热贴");
        this.mImgDownload.setVisibility(8);
        this.mImgSearch.setVisibility(8);
        Circle24HotListAdp circle24HotListAdp = new Circle24HotListAdp();
        this.mAdapter = circle24HotListAdp;
        this.rvCircles.setAdapter(circle24HotListAdp);
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12366c.lambda$setContentView$0(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.forum.c
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12367c.lambda$setContentView$1(baseQuickAdapter, view, i2);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.forum.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12365c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.psychiatrygarden.activity.forum.Circle24HotListActivity.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Circle24HotListActivity.this.isLoadMore = true;
                Circle24HotListActivity.access$112(Circle24HotListActivity.this, 1);
                Circle24HotListActivity.this.loadData();
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Circle24HotListActivity.this.page = 1;
                Circle24HotListActivity.this.tempSort = 0;
                Circle24HotListActivity.this.isLoadMore = false;
                Circle24HotListActivity.this.loadData();
            }
        });
        loadData();
    }
}
