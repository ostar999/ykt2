package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.psychiatrygarden.adapter.ErrorCorrectionAdapter;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes5.dex */
public class AllErrorCorrectionActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 272;
    private static int pageNum = 1;
    private String _id;
    private View addFooterView;
    private ImageView emptyview;
    public ErrorCorrectionAdapter mCommListAdapter;
    public PinnedSectionListView1 mPinnedSecListView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private int module_type;
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private String comment_type = "1";

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.AllErrorCorrectionActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 272) {
                int unused = AllErrorCorrectionActivity.pageNum = 1;
                AllErrorCorrectionActivity.this.getCommentListData();
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.AllErrorCorrectionActivity$2, reason: invalid class name */
    public class AnonymousClass2 implements AbsListView.OnScrollListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            AllErrorCorrectionActivity.access$008();
            AllErrorCorrectionActivity.this.getCommentListData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && AllErrorCorrectionActivity.this.mPinnedSecListView.getFooterViewsCount() <= 0) {
                AllErrorCorrectionActivity allErrorCorrectionActivity = AllErrorCorrectionActivity.this;
                allErrorCorrectionActivity.mPinnedSecListView.addFooterView(allErrorCorrectionActivity.addFooterView);
                AllErrorCorrectionActivity.this.addFooterView.setVisibility(0);
                if (AllErrorCorrectionActivity.this.mSwipeRefreshLayout.isRefreshing()) {
                    AllErrorCorrectionActivity.this.mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.u0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13969c.lambda$onScrollStateChanged$0();
                        }
                    }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            }
        }
    }

    public static /* synthetic */ int access$008() {
        int i2 = pageNum;
        pageNum = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentListData() {
        HashMap map = new HashMap();
        map.put("module_type", this.module_type + "");
        map.put("comment_type", this.comment_type);
        map.put("obj_id", this._id + "");
        map.put("type", "adopt");
        map.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + pageNum);
        YJYHttpUtils.post(this, NetworkRequestsURL.mCommentList, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.s0
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f13812c.lambda$getCommentListData$2((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.t0
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f13936c.lambda$getCommentListData$3(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCommentListData$2(String str, String str2) {
        try {
            CommentBean commentBean = (CommentBean) new Gson().fromJson(str, CommentBean.class);
            if (commentBean.getCode().equals("200")) {
                this.emptyview.setVisibility(8);
                this.time_line = commentBean.getData().getTime_line();
                if (pageNum == 1) {
                    this.commlist.clear();
                    if (this.time_line.size() > 0) {
                        this.commlist.addAll(this.time_line);
                    }
                    ErrorCorrectionAdapter errorCorrectionAdapter = new ErrorCorrectionAdapter(this.mContext, this.commlist, this.time_line, this.module_type, this.comment_type, this._id + "", this, false);
                    this.mCommListAdapter = errorCorrectionAdapter;
                    this.mPinnedSecListView.setAdapter((ListAdapter) errorCorrectionAdapter);
                    if (this.commlist.isEmpty()) {
                        this.emptyview.setVisibility(0);
                    }
                } else {
                    this.mPinnedSecListView.removeFooterView(this.addFooterView);
                    this.addFooterView.setVisibility(8);
                    this.mPinnedSecListView.invalidateViews();
                    if (this.time_line.size() == 0) {
                        AlertToast("已经是最后一条");
                    } else {
                        this.commlist.addAll(this.time_line);
                        this.mCommListAdapter.setRefeault(this.time_line);
                        this.mCommListAdapter.notifyDataSetChanged();
                    }
                }
            } else if (pageNum == 1 && this.commlist.size() == 0) {
                ErrorCorrectionAdapter errorCorrectionAdapter2 = new ErrorCorrectionAdapter(this.mContext, this.commlist, this);
                this.mCommListAdapter = errorCorrectionAdapter2;
                this.mPinnedSecListView.setAdapter((ListAdapter) errorCorrectionAdapter2);
            } else {
                AlertToast(commentBean.getMessage());
            }
            if (this.mSwipeRefreshLayout.isRefreshing()) {
                this.mSwipeRefreshLayout.setRefreshing(false);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCommentListData$3(VolleyError volleyError, String str) {
        if (pageNum == 1) {
            if (this.commlist.size() > 0) {
                AlertToast("请求服务器失败");
                return;
            } else {
                ErrorCorrectionAdapter errorCorrectionAdapter = new ErrorCorrectionAdapter(this.mContext, this.commlist, this);
                this.mCommListAdapter = errorCorrectionAdapter;
                this.mPinnedSecListView.setAdapter((ListAdapter) errorCorrectionAdapter);
            }
        }
        if (this.mSwipeRefreshLayout.isRefreshing()) {
            this.mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1() {
        this.mSwipeRefreshLayout.setRefreshing(true);
        pageNum = 1;
        getCommentListData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.module_type = getIntent().getIntExtra("module_type", 0);
        this._id = getIntent().getStringExtra(com.umeng.analytics.pro.aq.f22519d);
        String stringExtra = getIntent().getStringExtra("title");
        findViewById(R.id.include_title).setVisibility(0);
        ((ImageView) findViewById(R.id.include_btn_left)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.q0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13724c.lambda$init$0(view);
            }
        });
        ((TextView) findViewById(R.id.include_title_center)).setText(stringExtra);
        this.emptyview = (ImageView) findViewById(R.id.emptyview);
        ((LinearLayout) findViewById(R.id.llay_comment)).setVisibility(8);
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeLayput);
        PinnedSectionListView1 pinnedSectionListView1 = (PinnedSectionListView1) findViewById(R.id.pinnedSectionListView1);
        this.mPinnedSecListView = pinnedSectionListView1;
        pinnedSectionListView1.setScrollingCacheEnabled(false);
        this.addFooterView = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.white));
            this.mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.input_night_color));
            this.mSwipeRefreshLayout.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.r0
            @Override // java.lang.Runnable
            public final void run() {
                this.f13753c.lambda$init$1();
            }
        });
        this.mPinnedSecListView.setOnScrollListener(new AnonymousClass2());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.mHandler.sendEmptyMessageDelayed(272, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.fragment_error_correction);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
