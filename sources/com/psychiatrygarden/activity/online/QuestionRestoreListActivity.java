package com.psychiatrygarden.activity.online;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.adapter.QuestionRestoreListAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionErroCorrectionBean;
import com.psychiatrygarden.activity.online.popwindow.QuestionRestoreEditPopWindow;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.RestoreBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes5.dex */
public class QuestionRestoreListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 272;
    private static final int pageSize = 10;
    private View addFooterView;
    private ImageView emptyview;
    private QuestionRestoreListAdapter mAdapter;
    private PinnedSectionListView1 mPinnedSecListView;
    private SwipeRefreshLayout refreshLayout;
    private TextView tv_to_edit;
    private String restore_analyze = "";
    private String question_id = "";
    private String content = "";
    private String identity_id = "";
    private String type = "";
    private int page = 1;
    private final List<QuestionErroCorrectionBean.DataDTO> time_lines = new ArrayList();
    private List<QuestionErroCorrectionBean.DataDTO> adopt = new ArrayList();
    private final List<QuestionErroCorrectionBean.DataDTO> commlist = new ArrayList();
    private List<QuestionErroCorrectionBean.DataDTO> time_line = new ArrayList();

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.online.QuestionRestoreListActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 272) {
                QuestionRestoreListActivity.this.page = 1;
                QuestionRestoreListActivity.this.getRestoreListData();
            }
        }
    };
    private String questionColumn = "";

    /* renamed from: com.psychiatrygarden.activity.online.QuestionRestoreListActivity$2, reason: invalid class name */
    public class AnonymousClass2 implements AbsListView.OnScrollListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            QuestionRestoreListActivity.access$008(QuestionRestoreListActivity.this);
            QuestionRestoreListActivity.this.getRestoreListData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && QuestionRestoreListActivity.this.mPinnedSecListView.getFooterViewsCount() <= 0) {
                QuestionRestoreListActivity.this.mPinnedSecListView.addFooterView(QuestionRestoreListActivity.this.addFooterView);
                QuestionRestoreListActivity.this.addFooterView.setVisibility(0);
                if (QuestionRestoreListActivity.this.refreshLayout.isRefreshing()) {
                    QuestionRestoreListActivity.this.refreshLayout.setRefreshing(false);
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.r1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f13476c.lambda$onScrollStateChanged$0();
                        }
                    }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            }
        }
    }

    public static /* synthetic */ int access$008(QuestionRestoreListActivity questionRestoreListActivity) {
        int i2 = questionRestoreListActivity.page;
        questionRestoreListActivity.page = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getRestoreListData() {
        HashMap map = new HashMap();
        map.put("question_id", "" + this.question_id);
        map.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        map.put(DatabaseManager.SIZE, com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        if (this.restore_analyze.equals(CommonParameter.QUESTION_RESTORE)) {
            map.put("question_column", RequestParameters.X_OSS_RESTORE);
            this.questionColumn = RequestParameters.X_OSS_RESTORE;
        } else if (this.restore_analyze.equals(CommonParameter.QUESTION_ANALYZE)) {
            map.put("question_column", "explain");
            this.questionColumn = "explain";
        } else {
            map.put("question_column", "option_analysis");
            this.questionColumn = "option_analysis";
        }
        YJYHttpUtils.post(this, NetworkRequestsURL.errorCorrectionNewListURL, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.online.o1
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f13461c.lambda$getRestoreListData$2((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.online.p1
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f13467c.lambda$getRestoreListData$3(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRestoreListData$2(String str, String str2) {
        try {
            RestoreBean restoreBean = (RestoreBean) new Gson().fromJson(str, RestoreBean.class);
            if (restoreBean.getCode().equals("200")) {
                this.emptyview.setVisibility(8);
                this.adopt = restoreBean.getData().getAdopt();
                this.time_line = restoreBean.getData().getTime_line();
                if (this.page == 1) {
                    this.commlist.clear();
                    this.time_lines.clear();
                    List<QuestionErroCorrectionBean.DataDTO> list = this.adopt;
                    String time_line_total = "0";
                    if (list != null && list.size() > 0) {
                        QuestionErroCorrectionBean.DataDTO dataDTO = new QuestionErroCorrectionBean.DataDTO();
                        dataDTO.setType(1);
                        if (this.questionColumn.equals(RequestParameters.X_OSS_RESTORE)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("采纳(");
                            List<QuestionErroCorrectionBean.DataDTO> list2 = this.adopt;
                            sb.append((list2 == null || list2.isEmpty()) ? "0" : restoreBean.getData().getAdopt_total());
                            sb.append(")");
                            dataDTO.setName(sb.toString());
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("采纳(");
                            List<QuestionErroCorrectionBean.DataDTO> list3 = this.adopt;
                            sb2.append((list3 == null || list3.isEmpty()) ? "0" : restoreBean.getData().getAdopt_total());
                            sb2.append(")");
                            dataDTO.setName(sb2.toString());
                        }
                        this.commlist.add(dataDTO);
                        this.commlist.addAll(this.adopt);
                        if (restoreBean.getData().getAdopt_is_more() != null && "1".equals(restoreBean.getData().getAdopt_is_more())) {
                            QuestionErroCorrectionBean.DataDTO dataDTO2 = new QuestionErroCorrectionBean.DataDTO();
                            dataDTO2.setOtherView(3);
                            if (this.questionColumn.equals(RequestParameters.X_OSS_RESTORE)) {
                                dataDTO2.setName("采纳(" + restoreBean.getData().getAdopt_total() + ")");
                            } else {
                                dataDTO2.setName("采纳(" + restoreBean.getData().getAdopt_total() + ")");
                            }
                            this.commlist.add(dataDTO2);
                        }
                    }
                    List<QuestionErroCorrectionBean.DataDTO> list4 = this.time_line;
                    if (list4 != null && list4.size() > 0) {
                        QuestionErroCorrectionBean.DataDTO dataDTO3 = new QuestionErroCorrectionBean.DataDTO();
                        dataDTO3.setType(1);
                        if (this.questionColumn.equals(RequestParameters.X_OSS_RESTORE)) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append("全部(");
                            List<QuestionErroCorrectionBean.DataDTO> list5 = this.time_line;
                            if (list5 != null && !list5.isEmpty()) {
                                time_line_total = restoreBean.getData().getTime_line_total();
                            }
                            sb3.append(time_line_total);
                            sb3.append(")");
                            dataDTO3.setName(sb3.toString());
                        } else {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("全部(");
                            List<QuestionErroCorrectionBean.DataDTO> list6 = this.time_line;
                            if (list6 != null && !list6.isEmpty()) {
                                time_line_total = restoreBean.getData().getTime_line_total();
                            }
                            sb4.append(time_line_total);
                            sb4.append(")");
                            dataDTO3.setName(sb4.toString());
                        }
                        this.commlist.add(dataDTO3);
                        this.commlist.addAll(this.time_line);
                    }
                    this.time_lines.addAll(this.time_line);
                    QuestionRestoreListAdapter questionRestoreListAdapter = new QuestionRestoreListAdapter(this.mContext, this.commlist, this.time_line, this.questionColumn, this.question_id, this, false);
                    this.mAdapter = questionRestoreListAdapter;
                    this.mPinnedSecListView.setAdapter((ListAdapter) questionRestoreListAdapter);
                    if (this.commlist.size() == 0) {
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
            } else if (this.page == 1 && this.commlist.size() == 0) {
                QuestionRestoreListAdapter questionRestoreListAdapter2 = new QuestionRestoreListAdapter(this.mContext, this.commlist, this);
                this.mAdapter = questionRestoreListAdapter2;
                this.mPinnedSecListView.setAdapter((ListAdapter) questionRestoreListAdapter2);
            } else {
                AlertToast(restoreBean.getMessage());
            }
            if (this.refreshLayout.isRefreshing()) {
                this.refreshLayout.setRefreshing(false);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getRestoreListData$3(VolleyError volleyError, String str) {
        if (this.page == 1) {
            if (this.commlist.size() > 0) {
                AlertToast("请求服务器失败");
                return;
            } else {
                QuestionRestoreListAdapter questionRestoreListAdapter = new QuestionRestoreListAdapter(this.mContext, this.commlist, this);
                this.mAdapter = questionRestoreListAdapter;
                this.mPinnedSecListView.setAdapter((ListAdapter) questionRestoreListAdapter);
            }
        }
        if (this.refreshLayout.isRefreshing()) {
            this.refreshLayout.setRefreshing(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        this.refreshLayout.setRefreshing(true);
        this.page = 1;
        getRestoreListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        new XPopup.Builder(this).asCustom(new QuestionRestoreEditPopWindow(this, new QuestionRestoreEditPopWindow.RestoreClickIml() { // from class: com.psychiatrygarden.activity.online.QuestionRestoreListActivity.3
            @Override // com.psychiatrygarden.activity.online.popwindow.QuestionRestoreEditPopWindow.RestoreClickIml
            public void mEditMethod() {
                Intent intent = new Intent(QuestionRestoreListActivity.this, (Class<?>) QuestionRestoreEditActivity.class);
                intent.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, QuestionRestoreListActivity.this.restore_analyze);
                intent.putExtra("question_id", QuestionRestoreListActivity.this.question_id + "");
                intent.putExtra("content", QuestionRestoreListActivity.this.content + "");
                intent.putExtra("identity_id", QuestionRestoreListActivity.this.identity_id + "");
                intent.putExtra("type", QuestionRestoreListActivity.this.type + "");
                QuestionRestoreListActivity.this.startActivity(intent);
            }

            @Override // com.psychiatrygarden.activity.online.popwindow.QuestionRestoreEditPopWindow.RestoreClickIml
            public void mReEditMethod() {
                Intent intent = new Intent(QuestionRestoreListActivity.this, (Class<?>) QuestionRestoreEditActivity.class);
                intent.putExtra(CommonParameter.QUESTION_RESTORE_ANALYZE, QuestionRestoreListActivity.this.restore_analyze);
                intent.putExtra("question_id", QuestionRestoreListActivity.this.question_id + "");
                intent.putExtra("identity_id", QuestionRestoreListActivity.this.identity_id + "");
                intent.putExtra("type", QuestionRestoreListActivity.this.type + "");
                QuestionRestoreListActivity.this.startActivity(intent);
            }
        })).toggle();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.identity_id = getIntent().getStringExtra("identity_id");
        this.type = getIntent().getStringExtra("type");
        this.content = getIntent().getStringExtra("content");
        this.restore_analyze = getIntent().getStringExtra(CommonParameter.QUESTION_RESTORE_ANALYZE);
        this.question_id = getIntent().getStringExtra("question_id");
        if (this.restore_analyze.equals(CommonParameter.QUESTION_RESTORE)) {
            setTitle("考点还原");
        } else {
            setTitle("答案解析");
        }
        if (SkinManager.getCurrentSkinType(this) == 0) {
            findViewById(R.id.iv_bottom_shadow).setVisibility(0);
        } else {
            findViewById(R.id.iv_bottom_shadow).setVisibility(4);
        }
        this.refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshid);
        this.tv_to_edit = (TextView) findViewById(R.id.tv_to_edit);
        this.emptyview = (ImageView) findViewById(R.id.emptyview);
        PinnedSectionListView1 pinnedSectionListView1 = (PinnedSectionListView1) findViewById(R.id.pinnedSectionListView1);
        this.mPinnedSecListView = pinnedSectionListView1;
        pinnedSectionListView1.setScrollingCacheEnabled(false);
        this.mPinnedSecListView.setAnimationCacheEnabled(false);
        this.addFooterView = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.refreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.refreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.white));
            this.refreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.refreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.input_night_color));
            this.refreshLayout.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.refreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.n1
            @Override // java.lang.Runnable
            public final void run() {
                this.f13455c.lambda$init$0();
            }
        });
        this.mPinnedSecListView.setOnScrollListener(new AnonymousClass2());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initStatusBar() {
        super.initStatusBar();
        if (this.mBaseTheme == 0) {
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusMessage event) {
        if (EventBusConstant.EVENT_QUESTION_ERROR_CORRECTION_OK.equals(event.getKey())) {
            this.page = 1;
            getRestoreListData();
        }
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
        setContentView(R.layout.activity_question_restore_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tv_to_edit.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.q1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13473c.lambda$setListenerForWidget$1(view);
            }
        });
    }
}
