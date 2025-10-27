package com.psychiatrygarden.activity.ebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.bean.BookStackBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.forum.ForumSearchAct;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.PopupShowManager;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReadBookActivity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ProjectBookStoreActivity extends BaseActivity implements OnRefreshLoadMoreListener {
    private CustomEmptyView emptyView;
    private ProjectBookStoreAdp mAdapter;
    private RecyclerView mBookRecycler;
    private ClearEditText mEtSearch;
    private ImageView mImgBack;
    private ImageView mImgFilter;
    private SmartRefreshLayout mRefresh;
    private BookStoreAdp mTopAdapter;
    private RecyclerView mTopRecycler;
    private TextView mTvSearch;
    private View recyclerLine;
    private int page = 1;
    private String mAppId = "";
    private String mId = "";
    private List<BookStackBean.BookStackData> topList = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$1(BookStackBean.BookStackData bookStackData) {
        Activity resumeActivity = ProjectApp.instance.getResumeActivity();
        if (resumeActivity != null) {
            PopupShowManager.getInstance(resumeActivity).checkShowCoupon(resumeActivity, "ENTER_BOOK_READ", "1", "4", bookStackData.getId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (!UserConfig.isLogin()) {
            startActivity(new Intent(this, (Class<?>) LoginActivity.class));
            return;
        }
        final BookStackBean.BookStackData item = this.mAdapter.getItem(i2);
        startActivity(ReadBookActivity.INSTANCE.newIntent(this, item.getId(), UserConfig.getUserId(), SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"), UserConfig.getInstance().getUser().getAdmin(), UserConfig.getInstance().getUser().getAvatar(), UserConfig.getInstance().getUser().getToken(), UserConfig.getInstance().getUser().getSecret()));
        view.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.ebook.z
            @Override // java.lang.Runnable
            public final void run() {
                ProjectBookStoreActivity.lambda$init$1(item);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$3(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        newIntent(this, this.mTopAdapter.getItem(i2).getId(), this.mAppId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        BookShelfActivity.newIntent(this, this.mId, this.mAppId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        ForumSearchAct.newIntent(this.mContext, "", null, 3);
    }

    private void loadData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("search_app_id", this.mAppId);
        ajaxParams.put("parent_id", this.mId);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, this.page + "");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.proBookStackList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ebook.ProjectBookStoreActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ProjectBookStoreActivity.this.mRefresh.finishRefresh();
                if (ProjectBookStoreActivity.this.page == 1) {
                    ProjectBookStoreActivity.this.emptyView.setLoadFileResUi(ProjectBookStoreActivity.this);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                ProjectBookStoreActivity.this.mRefresh.finishRefresh();
                try {
                    BookStackBean bookStackBean = (BookStackBean) new Gson().fromJson(s2, BookStackBean.class);
                    if (!bookStackBean.getCode().equals("200")) {
                        ProjectBookStoreActivity.this.emptyView.stopAnim();
                        ProjectBookStoreActivity.this.AlertToast(bookStackBean.getMessage());
                        return;
                    }
                    if (bookStackBean.getData() == null || bookStackBean.getData() == null || bookStackBean.getData().isEmpty()) {
                        ProjectBookStoreActivity.this.mRefresh.finishLoadMoreWithNoMoreData();
                    } else {
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = new ArrayList();
                        for (int i2 = 0; i2 < bookStackBean.getData().size(); i2++) {
                            if (bookStackBean.getData().get(i2).getFile_type().equals("2")) {
                                arrayList.add(bookStackBean.getData().get(i2));
                            } else {
                                arrayList2.add(bookStackBean.getData().get(i2));
                            }
                        }
                        if (ProjectBookStoreActivity.this.page == 1) {
                            if (!arrayList2.isEmpty()) {
                                ProjectBookStoreActivity.this.mAdapter.setList(arrayList2);
                            }
                            if (!arrayList.isEmpty()) {
                                ProjectBookStoreActivity.this.mTopAdapter.setList(arrayList);
                            }
                            if (bookStackBean.getData().size() < 10) {
                                ProjectBookStoreActivity.this.mRefresh.finishLoadMoreWithNoMoreData();
                            }
                            ProjectBookStoreActivity.this.recyclerLine.setVisibility(arrayList.isEmpty() ? 8 : 0);
                        } else if (!bookStackBean.getData().isEmpty()) {
                            if (!arrayList2.isEmpty()) {
                                ProjectBookStoreActivity.this.mAdapter.addData((Collection) arrayList2);
                            }
                            if (!arrayList.isEmpty()) {
                                ProjectBookStoreActivity.this.mTopAdapter.addData((Collection) arrayList);
                            }
                            if (bookStackBean.getData().size() < 10) {
                                ProjectBookStoreActivity.this.mRefresh.finishLoadMoreWithNoMoreData();
                            } else {
                                ProjectBookStoreActivity.this.mRefresh.finishLoadMore();
                            }
                        }
                    }
                    ProjectBookStoreActivity.this.emptyView.stopAnim();
                    ProjectBookStoreActivity.this.emptyView.setVisibility(8);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (ProjectBookStoreActivity.this.page == 1) {
                        ProjectBookStoreActivity.this.emptyView.setLoadFileResUi(ProjectBookStoreActivity.this);
                    }
                }
            }
        });
    }

    public static void newIntent(Context context, String id, String appId) {
        Intent intent = new Intent(context, (Class<?>) ProjectBookStoreActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("appId", appId);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
        this.mAppId = getIntent().getStringExtra("appId");
        this.mId = getIntent().getStringExtra("id");
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mBookRecycler = (RecyclerView) findViewById(R.id.book_recycler);
        this.mTopRecycler = (RecyclerView) findViewById(R.id.top_recycler);
        this.mImgFilter = (ImageView) findViewById(R.id.iv_book_shelf);
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mEtSearch = (ClearEditText) findViewById(R.id.et_search);
        this.mTvSearch = (TextView) findViewById(R.id.tv_search_area);
        this.recyclerLine = findViewById(R.id.recyclerLine);
        this.mEtSearch.setVisibility(8);
        this.mTvSearch.setVisibility(0);
        ProjectBookStoreAdp projectBookStoreAdp = new ProjectBookStoreAdp();
        this.mAdapter = projectBookStoreAdp;
        this.mBookRecycler.setAdapter(projectBookStoreAdp);
        BookStoreAdp bookStoreAdp = new BookStoreAdp();
        this.mTopAdapter = bookStoreAdp;
        this.mTopRecycler.setAdapter(bookStoreAdp);
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12282c.lambda$init$0(view);
            }
        });
        this.emptyView.changeEmptyViewWriteBg();
        this.mAdapter.setEmptyView(this.emptyView);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.ebook.b0
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12285c.lambda$init$2(baseQuickAdapter, view, i2);
            }
        });
        this.mTopAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.ebook.c0
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12287c.lambda$init$3(baseQuickAdapter, view, i2);
            }
        });
        this.mRefresh.setOnRefreshLoadMoreListener(this);
        loadData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        this.page++;
        loadData();
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.page = 1;
        loadData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_project_book_stack);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12307c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mImgFilter.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.x
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12308c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mTvSearch.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ebook.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12309c.lambda$setListenerForWidget$6(view);
            }
        });
    }
}
