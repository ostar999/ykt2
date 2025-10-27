package com.psychiatrygarden.fragmenthome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.MyCorrectionActivity;
import com.psychiatrygarden.adapter.ErrorCorrectionAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.umeng.analytics.pro.aq;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ErrorCorrectionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 272;
    private static int pageNum = 1;
    private String _id;
    private View addFooterView;
    private View emptyview;
    public ErrorCorrectionAdapter mCommListAdapter;
    public PinnedSectionListView1 mPinnedSecListView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private int module_type;
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private List<CommentBean.DataBean.HotBean> adopt = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> time_lines = new ArrayList();
    private String comment_type = "1";

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.ErrorCorrectionFragment.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
            if (i2 == 2) {
                Bundle bundle = (Bundle) msg.obj;
                bundle.putInt("result", 1);
                if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                    ErrorCorrectionFragment.this.putComment(bundle);
                    return;
                }
                Intent intent = new Intent(((BaseFragment) ErrorCorrectionFragment.this).mContext, (Class<?>) CorpCupActivity.class);
                intent.putExtra("bundleIntent", bundle);
                ErrorCorrectionFragment.this.startActivityForResult(intent, 1);
                return;
            }
            if (i2 == 272) {
                int unused = ErrorCorrectionFragment.pageNum = 1;
                ErrorCorrectionFragment.this.getCommentListData();
            } else if (i2 == 4) {
                ErrorCorrectionFragment.this.editComment(msg);
            } else {
                if (i2 != 5) {
                    return;
                }
                ErrorCorrectionFragment.this.showDeleteDialog(msg);
            }
        }
    };

    /* renamed from: com.psychiatrygarden.fragmenthome.ErrorCorrectionFragment$2, reason: invalid class name */
    public class AnonymousClass2 implements AbsListView.OnScrollListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            ErrorCorrectionFragment.access$308();
            ErrorCorrectionFragment.this.getCommentListData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && ErrorCorrectionFragment.this.mPinnedSecListView.getFooterViewsCount() <= 0) {
                ErrorCorrectionFragment errorCorrectionFragment = ErrorCorrectionFragment.this;
                errorCorrectionFragment.mPinnedSecListView.addFooterView(errorCorrectionFragment.addFooterView);
                ErrorCorrectionFragment.this.addFooterView.setVisibility(0);
                if (ErrorCorrectionFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                    ErrorCorrectionFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.p5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f15909c.lambda$onScrollStateChanged$0();
                        }
                    }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }
            }
        }
    }

    public static /* synthetic */ int access$308() {
        int i2 = pageNum;
        pageNum = i2 + 1;
        return i2;
    }

    private void deleteComment(String comment_id) {
        HashMap map = new HashMap();
        map.put("token", UserConfig.getInstance().getUser().getToken());
        map.put("secret", UserConfig.getInstance().getUser().getSecret());
        map.put("comment_id", comment_id);
        YJYHttpUtils.post(getActivity(), "", map, new Response.Listener() { // from class: com.psychiatrygarden.fragmenthome.l5
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f15807c.lambda$deleteComment$12((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.fragmenthome.m5
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                ErrorCorrectionFragment.lambda$deleteComment$13(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void editComment(final Message msg) {
        String string = msg.getData().getString("content");
        HashMap map = new HashMap();
        map.put("token", UserConfig.getInstance().getUser().getToken());
        map.put("secret", UserConfig.getInstance().getUser().getSecret());
        map.put("comment_id", msg.getData().getString("comment_id"));
        map.put("content", string);
        YJYHttpUtils.post(getActivity(), "", map, new Response.Listener() { // from class: com.psychiatrygarden.fragmenthome.b5
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f15460c.lambda$editComment$8((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.fragmenthome.g5
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                ErrorCorrectionFragment.lambda$editComment$9(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentListData() {
        String str;
        HashMap map = new HashMap();
        map.put("module_type", this.module_type + "");
        map.put("comment_type", this.comment_type);
        if (getActivity() instanceof MyCorrectionActivity) {
            map.put("target_user_id", UserConfig.getUserId());
            str = NetworkRequestsURL.myCommentOriginApi;
        } else {
            map.put("obj_id", this._id + "");
            str = NetworkRequestsURL.mCommentList;
        }
        map.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + pageNum);
        YJYHttpUtils.post(getActivity(), str, map, new Response.Listener() { // from class: com.psychiatrygarden.fragmenthome.h5
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str2) {
                this.f15639c.lambda$getCommentListData$4((String) obj, str2);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.fragmenthome.i5
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str2) {
                this.f15662c.lambda$getCommentListData$5(volleyError, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteComment$12(String str, String str2) {
        try {
            AlertToast(new JSONObject(str).optString("message"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteComment$13(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editComment$8(String str, String str2) {
        try {
            AlertToast(new JSONObject(str).optString("message"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$editComment$9(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCommentListData$4(String str, String str2) {
        try {
            CommentBean commentBean = (CommentBean) new Gson().fromJson(str, CommentBean.class);
            if (commentBean.getCode().equals("200")) {
                this.emptyview.setVisibility(8);
                this.adopt = commentBean.getData().getAdopt();
                this.time_line = commentBean.getData().getTime_line();
                if (pageNum == 1) {
                    this.commlist.clear();
                    this.time_lines.clear();
                    List<CommentBean.DataBean.HotBean> list = this.adopt;
                    String time_line_total = "0";
                    if (list != null && list.size() > 0) {
                        CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                        hotBean.setType(1);
                        StringBuilder sb = new StringBuilder();
                        sb.append("采纳纠错(");
                        List<CommentBean.DataBean.HotBean> list2 = this.adopt;
                        sb.append((list2 == null || list2.isEmpty()) ? "0" : commentBean.getData().getAdopt_total());
                        sb.append(")");
                        hotBean.setName(sb.toString());
                        this.commlist.add(hotBean);
                        this.commlist.addAll(this.adopt);
                        if (commentBean.getData().getAdopt_is_more() != null && "1".equals(commentBean.getData().getAdopt_is_more())) {
                            CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                            hotBean2.setOtherView(3);
                            hotBean2.setName("采纳纠错(" + commentBean.getData().getAdopt_total() + ")");
                            this.commlist.add(hotBean2);
                        }
                    }
                    List<CommentBean.DataBean.HotBean> list3 = this.time_line;
                    if (list3 != null && list3.size() > 0) {
                        CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
                        hotBean3.setType(1);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("全部纠错(");
                        List<CommentBean.DataBean.HotBean> list4 = this.time_line;
                        if (list4 != null && !list4.isEmpty()) {
                            time_line_total = commentBean.getData().getTime_line_total();
                        }
                        sb2.append(time_line_total);
                        sb2.append(")");
                        hotBean3.setName(sb2.toString());
                        this.commlist.add(hotBean3);
                        this.commlist.addAll(this.time_line);
                    }
                    this.time_lines.addAll(this.time_line);
                    ErrorCorrectionAdapter errorCorrectionAdapter = new ErrorCorrectionAdapter(this.mContext, this.commlist, this.time_line, this.module_type, this.comment_type, this._id + "", (BaseActivity) getActivity(), false);
                    this.mCommListAdapter = errorCorrectionAdapter;
                    this.mPinnedSecListView.setAdapter((ListAdapter) errorCorrectionAdapter);
                    if (this.commlist.size() <= 0) {
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
                        this.mCommListAdapter.setRefeault(this.time_line);
                        this.mCommListAdapter.notifyDataSetChanged();
                    }
                }
            } else if (pageNum == 1 && this.commlist.size() == 0) {
                ErrorCorrectionAdapter errorCorrectionAdapter2 = new ErrorCorrectionAdapter(this.mContext, this.commlist, getActivity());
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
    public /* synthetic */ void lambda$getCommentListData$5(VolleyError volleyError, String str) {
        if (pageNum == 1) {
            if (this.commlist.size() > 0) {
                AlertToast("请求服务器失败");
                return;
            } else {
                ErrorCorrectionAdapter errorCorrectionAdapter = new ErrorCorrectionAdapter(this.mContext, this.commlist, getActivity());
                this.mCommListAdapter = errorCorrectionAdapter;
                this.mPinnedSecListView.setAdapter((ListAdapter) errorCorrectionAdapter);
            }
        }
        if (this.mSwipeRefreshLayout.isRefreshing()) {
            this.mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(View view) {
        Intent intent = new Intent(getActivity(), (Class<?>) MyCorrectionActivity.class);
        intent.putExtra(aq.f22519d, "" + this._id);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        Message message = new Message();
        message.what = 2;
        message.obj = bundle;
        this.mHandler.sendMessage(message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.fragmenthome.n5
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f15865a.lambda$initViews$1(str, str2, str3);
            }
        }, "", "写纠错", false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3() {
        this.mSwipeRefreshLayout.setRefreshing(true);
        pageNum = 1;
        getCommentListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putComment$6(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optString("code").equals("200")) {
                AlertToast(jSONObject.optString("message"));
                EventBus.getDefault().post("mCommentResult");
                EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
            } else if (jSONObject.optString("code").equals("401")) {
                new CusomNewDialog(this.mContext).setMessage(jSONObject.optString("message")).show();
            } else {
                AlertToast(jSONObject.optString("message"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$putComment$7(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDeleteDialog$11(String str, CustomDialog customDialog, View view) {
        deleteComment(str);
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteDialog(final Message msg) {
        final String string = msg.getData().getString("comment_id");
        msg.getData().getInt("position");
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.setMessage(R.string.confirm_delete_comment);
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.e5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                customDialog.dismiss();
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.f5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15585c.lambda$showDeleteDialog$11(string, customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_error_correction;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.module_type = getArguments().getInt("module_type");
        this._id = getArguments().getString(aq.f22519d);
        this.emptyview = holder.get(R.id.emptyview);
        ((LinearLayout) holder.get(R.id.ly_questiondetails_btn_edit)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.o5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15888c.lambda$initViews$0(view);
            }
        });
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) holder.get(R.id.mSwipeLayput);
        ((TextView) holder.get(R.id.bt_comment_play)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.c5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15499c.lambda$initViews$2(view);
            }
        });
        PinnedSectionListView1 pinnedSectionListView1 = (PinnedSectionListView1) holder.get(R.id.pinnedSectionListView1);
        this.mPinnedSecListView = pinnedSectionListView1;
        pinnedSectionListView1.setScrollingCacheEnabled(false);
        this.mPinnedSecListView.setAnimationCacheEnabled(false);
        this.addFooterView = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.white));
            this.mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this.mContext, R.color.input_night_color));
            this.mSwipeRefreshLayout.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.d5
            @Override // java.lang.Runnable
            public final void run() {
                this.f15536c.lambda$initViews$3();
            }
        });
        this.mPinnedSecListView.setOnScrollListener(new AnonymousClass2());
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        if ("mCommentResult".equals(str) || "delReplyAndLoadData".equals(str)) {
            this.mSwipeRefreshLayout.setRefreshing(true);
            pageNum = 1;
            getCommentListData();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, cn.webdemo.com.supporfragment.ISupportFragment
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == 0) {
            this.mCommListAdapter.getputData(data);
        } else {
            if (resultCode != 1) {
                return;
            }
            putComment(data);
        }
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() {
        this.mHandler.sendEmptyMessageDelayed(272, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    public void putComment(Bundle b3) {
        HashMap map = new HashMap();
        map.put("content", b3.getString("content"));
        map.put("module_type", this.module_type + "");
        map.put("comment_type", this.comment_type);
        map.put("obj_id", this._id + "");
        String string = b3.getString("b_img");
        String string2 = b3.getString("s_img");
        if (!TextUtils.isEmpty(string)) {
            if (string.contains("http")) {
                map.put("b_img", string);
                map.put("s_img", string2);
            } else {
                map.put("video_id", b3.getString("b_img"));
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            map.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mPutComment, map, new Response.Listener() { // from class: com.psychiatrygarden.fragmenthome.j5
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f15685c.lambda$putComment$6((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.fragmenthome.k5
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                ErrorCorrectionFragment.lambda$putComment$7(volleyError, str);
            }
        });
    }
}
