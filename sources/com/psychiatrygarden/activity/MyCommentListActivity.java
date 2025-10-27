package com.psychiatrygarden.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.adapter.MyCommentAdapter;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class MyCommentListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private View addFooterView;
    private String break_point;
    private String comment_type;
    private String isProhibit;
    private boolean is_callMe;
    private ListView listView;
    private MyCommentAdapter mAdapter;
    private int module_type;
    private SwipeRefreshLayout swipe;
    private int type;
    private int pageNum = 1;
    private List<CommentBean.DataBean.HotBean> commlist = new ArrayList();

    /* renamed from: com.psychiatrygarden.activity.MyCommentListActivity$1, reason: invalid class name */
    public class AnonymousClass1 implements AbsListView.OnScrollListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            MyCommentListActivity.access$408(MyCommentListActivity.this);
            MyCommentListActivity.this.getCommentListData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && MyCommentListActivity.this.listView.getFooterViewsCount() <= 0) {
                if (MyCommentListActivity.this.commlist == null || MyCommentListActivity.this.commlist.size() >= 2) {
                    MyCommentListActivity.this.listView.addFooterView(MyCommentListActivity.this.addFooterView);
                    MyCommentListActivity.this.addFooterView.setVisibility(0);
                    if (MyCommentListActivity.this.swipe.isRefreshing()) {
                        MyCommentListActivity.this.swipe.setRefreshing(false);
                    } else {
                        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.yd
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f14214c.lambda$onScrollStateChanged$0();
                            }
                        }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                    }
                }
            }
        }
    }

    public static /* synthetic */ int access$408(MyCommentListActivity myCommentListActivity) {
        int i2 = myCommentListActivity.pageNum;
        myCommentListActivity.pageNum = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0() {
        this.swipe.setRefreshing(true);
        getCommentListData();
    }

    public void getCommentListData() {
        String str;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_type", this.module_type + "");
        ajaxParams.put("comment_type", this.comment_type);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.pageNum);
        if (this.pageNum == 1) {
            this.break_point = (System.currentTimeMillis() / 1000) + "";
        }
        ajaxParams.put("break_point", this.break_point);
        if (getIntent().getBooleanExtra("is_other", false)) {
            ajaxParams.put("target_user_id", getIntent().getExtras().getString("target_user_id"));
        }
        if (this.type == 0) {
            str = NetworkRequestsURL.mYComment;
        } else {
            str = NetworkRequestsURL.singleCommentFloorApi;
            ajaxParams.put("first_line_comment_id", "" + getIntent().getIntExtra("id", 0));
        }
        YJYHttpUtils.post(this.mContext, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.MyCommentListActivity.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (MyCommentListActivity.this.swipe.isRefreshing()) {
                    MyCommentListActivity.this.swipe.setRefreshing(false);
                    MyCommentListActivity.this.AlertToast("加载失败");
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject == null) {
                            return;
                        }
                        if (MyCommentListActivity.this.pageNum == 1) {
                            if (MyCommentListActivity.this.commlist != null && MyCommentListActivity.this.commlist.size() > 0) {
                                MyCommentListActivity.this.commlist.clear();
                            }
                            MyCommentListActivity.this.commlist = (List) new Gson().fromJson(jSONObjectOptJSONObject.optString("time_line"), new TypeToken<List<CommentBean.DataBean.HotBean>>() { // from class: com.psychiatrygarden.activity.MyCommentListActivity.2.1
                            }.getType());
                            MyCommentListActivity myCommentListActivity = MyCommentListActivity.this;
                            MyCommentListActivity myCommentListActivity2 = MyCommentListActivity.this;
                            List list = myCommentListActivity2.commlist;
                            MyCommentListActivity myCommentListActivity3 = MyCommentListActivity.this;
                            myCommentListActivity.mAdapter = new MyCommentAdapter(myCommentListActivity2, list, myCommentListActivity3.mContext, myCommentListActivity3.comment_type, MyCommentListActivity.this.is_callMe, false, MyCommentListActivity.this.isProhibit);
                            MyCommentListActivity.this.listView.setAdapter((ListAdapter) MyCommentListActivity.this.mAdapter);
                        } else {
                            MyCommentListActivity.this.listView.removeFooterView(MyCommentListActivity.this.addFooterView);
                            MyCommentListActivity.this.addFooterView.setVisibility(8);
                            List list2 = (List) new Gson().fromJson(jSONObjectOptJSONObject.optString("time_line"), new TypeToken<List<CommentBean.DataBean.HotBean>>() { // from class: com.psychiatrygarden.activity.MyCommentListActivity.2.2
                            }.getType());
                            if (list2.size() == 0) {
                                MyCommentListActivity.this.AlertToast("已经是最后一条");
                                return;
                            } else {
                                MyCommentListActivity.this.commlist.addAll(list2);
                                MyCommentListActivity.this.mAdapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        MyCommentListActivity.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (MyCommentListActivity.this.swipe.isRefreshing()) {
                    MyCommentListActivity.this.swipe.setRefreshing(false);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment_list);
        this.type = getIntent().getExtras().getInt("type", 0);
        this.is_callMe = getIntent().getBooleanExtra("is_callMe", false);
        setTitle(getIntent().getExtras().getString("title"));
        this.module_type = getIntent().getIntExtra("module_type", 0);
        this.comment_type = getIntent().getExtras().getString("comment_type");
        String stringExtra = getIntent().getStringExtra("isProhibit");
        this.isProhibit = stringExtra;
        if (TextUtils.isEmpty(stringExtra)) {
            this.isProhibit = "0";
        }
        this.swipe = (SwipeRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.listView = (ListView) findViewById(R.id.listView);
        this.addFooterView = View.inflate(this, R.layout.activity_hideview, null);
        this.swipe.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.swipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.white));
            this.swipe.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.swipe.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.input_night_color));
            this.swipe.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.swipe.post(new Runnable() { // from class: com.psychiatrygarden.activity.xd
            @Override // java.lang.Runnable
            public final void run() {
                this.f14180c.lambda$onCreate$0();
            }
        });
        this.listView.setOnScrollListener(new AnonymousClass1());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if ("mCommentResult".equals(str)) {
            getCommentListData();
        } else if ("delReplyAndLoadData".equals(str)) {
            onRefresh();
        }
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.pageNum = 1;
        getCommentListData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
