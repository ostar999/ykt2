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
import com.psychiatrygarden.activity.online.adapter.QuestionRestoreListAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionErroCorrectionBean;
import com.psychiatrygarden.bean.RestoreBean;
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
public class AllRestoreAndExplainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 272;
    private static int page = 1;
    private String _id;
    private View addFooterView;
    private ImageView emptyview;
    public QuestionRestoreListAdapter mAdapter;
    public PinnedSectionListView1 mPinnedSecListView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private final List<QuestionErroCorrectionBean.DataDTO> time_lines = new ArrayList();
    private final List<QuestionErroCorrectionBean.DataDTO> commlist = new ArrayList();
    private List<QuestionErroCorrectionBean.DataDTO> time_line = new ArrayList();
    private String question_id = "";
    private String question_column = "";

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.AllRestoreAndExplainActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 272) {
                int unused = AllRestoreAndExplainActivity.page = 1;
                AllRestoreAndExplainActivity.this.getCommentListData();
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.AllRestoreAndExplainActivity$2, reason: invalid class name */
    public class AnonymousClass2 implements AbsListView.OnScrollListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            AllRestoreAndExplainActivity.access$008();
            AllRestoreAndExplainActivity.this.getCommentListData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && AllRestoreAndExplainActivity.this.mPinnedSecListView.getFooterViewsCount() <= 0) {
                AllRestoreAndExplainActivity allRestoreAndExplainActivity = AllRestoreAndExplainActivity.this;
                allRestoreAndExplainActivity.mPinnedSecListView.addFooterView(allRestoreAndExplainActivity.addFooterView);
                AllRestoreAndExplainActivity.this.addFooterView.setVisibility(0);
                if (AllRestoreAndExplainActivity.this.mSwipeRefreshLayout.isRefreshing()) {
                    AllRestoreAndExplainActivity.this.mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.z0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f14229c.lambda$onScrollStateChanged$0();
                        }
                    }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            }
        }
    }

    public static /* synthetic */ int access$008() {
        int i2 = page;
        page = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentListData() {
        HashMap map = new HashMap();
        map.put("question_id", "" + this.question_id);
        map.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + page);
        map.put("question_column", this.question_column);
        map.put("type", "adopt");
        YJYHttpUtils.post(this, NetworkRequestsURL.errorCorrectionNewListURL, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.x0
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f14167c.lambda$getCommentListData$2((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.y0
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f14200c.lambda$getCommentListData$3(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCommentListData$2(String str, String str2) {
        try {
            RestoreBean restoreBean = (RestoreBean) new Gson().fromJson(str, RestoreBean.class);
            if (restoreBean.getCode().equals("200")) {
                this.emptyview.setVisibility(8);
                this.time_line = restoreBean.getData().getTime_line();
                if (page == 1) {
                    this.commlist.clear();
                    this.time_lines.clear();
                    if (this.time_line.size() > 0) {
                        this.commlist.addAll(this.time_line);
                    }
                    this.time_lines.addAll(this.time_line);
                    QuestionRestoreListAdapter questionRestoreListAdapter = new QuestionRestoreListAdapter(this.mContext, this.commlist, this.time_line, this.question_column, this.question_id, this, false);
                    this.mAdapter = questionRestoreListAdapter;
                    this.mPinnedSecListView.setAdapter((ListAdapter) questionRestoreListAdapter);
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
                        this.time_lines.addAll(this.time_line);
                        this.mAdapter.setRefeault(this.time_line);
                        this.mAdapter.notifyDataSetChanged();
                    }
                }
            } else if (page == 1 && this.commlist.size() == 0) {
                QuestionRestoreListAdapter questionRestoreListAdapter2 = new QuestionRestoreListAdapter(this.mContext, this.commlist, this);
                this.mAdapter = questionRestoreListAdapter2;
                this.mPinnedSecListView.setAdapter((ListAdapter) questionRestoreListAdapter2);
            } else {
                AlertToast(restoreBean.getMessage());
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
        if (page == 1) {
            if (this.commlist.size() > 0) {
                AlertToast("请求服务器失败");
                return;
            } else {
                QuestionRestoreListAdapter questionRestoreListAdapter = new QuestionRestoreListAdapter(this.mContext, this.commlist, this);
                this.mAdapter = questionRestoreListAdapter;
                this.mPinnedSecListView.setAdapter((ListAdapter) questionRestoreListAdapter);
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
        page = 1;
        getCommentListData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.question_id = getIntent().getStringExtra("question_id");
        this.question_column = getIntent().getStringExtra("question_column");
        String stringExtra = getIntent().getStringExtra("title");
        findViewById(R.id.include_title).setVisibility(0);
        ((ImageView) findViewById(R.id.include_btn_left)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.v0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14005c.lambda$init$0(view);
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
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.w0
            @Override // java.lang.Runnable
            public final void run() {
                this.f14134c.lambda$init$1();
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
