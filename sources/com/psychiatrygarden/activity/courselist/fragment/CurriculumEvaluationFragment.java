package com.psychiatrygarden.activity.courselist.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListAdapter;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.hutool.core.text.StrPool;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.adapter.CommentListAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.CustomDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CurriculumEvaluationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 272;
    private static int pageNum = 1;
    private View addFooterView;
    private String course_id;
    public CommentListAdapter mCommListAdapter;
    public PinnedSectionListView1 mPinnedSecListView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private int module_type;
    private List<CommentBean.DataBean.HotBean> hot = new ArrayList();
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> time_lines = new ArrayList();
    private final String comment_type = "2";

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.courselist.fragment.CurriculumEvaluationFragment.1
        @Override // android.os.Handler
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
            if (i2 == 2) {
                Bundle bundle = (Bundle) msg.obj;
                bundle.putInt("result", 1);
                if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                    CurriculumEvaluationFragment.this.putComment(bundle);
                    return;
                }
                Intent intent = new Intent(((BaseFragment) CurriculumEvaluationFragment.this).mContext, (Class<?>) CorpCupActivity.class);
                intent.putExtra("bundleIntent", bundle);
                CurriculumEvaluationFragment.this.startActivityForResult(intent, 1);
                return;
            }
            if (i2 == 272) {
                int unused = CurriculumEvaluationFragment.pageNum = 1;
                CurriculumEvaluationFragment.this.getCommentListData();
            } else if (i2 == 4) {
                CurriculumEvaluationFragment.this.editComment(msg);
            } else {
                if (i2 != 5) {
                    return;
                }
                CurriculumEvaluationFragment.this.showDeleteDialog(msg);
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.courselist.fragment.CurriculumEvaluationFragment$2, reason: invalid class name */
    public class AnonymousClass2 implements AbsListView.OnScrollListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            CurriculumEvaluationFragment.access$308();
            CurriculumEvaluationFragment.this.getCommentListData();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && CurriculumEvaluationFragment.this.mPinnedSecListView.getFooterViewsCount() <= 0) {
                CurriculumEvaluationFragment curriculumEvaluationFragment = CurriculumEvaluationFragment.this;
                curriculumEvaluationFragment.mPinnedSecListView.addFooterView(curriculumEvaluationFragment.addFooterView);
                CurriculumEvaluationFragment.this.addFooterView.setVisibility(0);
                if (CurriculumEvaluationFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                    CurriculumEvaluationFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.fragment.f3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f11949c.lambda$onScrollStateChanged$0();
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
        YJYHttpUtils.post(this.mContext.getApplicationContext(), "", map, new Response.Listener() { // from class: com.psychiatrygarden.activity.courselist.fragment.b3
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f11929c.lambda$deleteComment$11((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.c3
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                CurriculumEvaluationFragment.lambda$deleteComment$12(volleyError, str);
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
        YJYHttpUtils.post(this.mContext.getApplicationContext(), "", map, new Response.Listener() { // from class: com.psychiatrygarden.activity.courselist.fragment.u2
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f12049c.lambda$editComment$7((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.v2
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                CurriculumEvaluationFragment.lambda$editComment$8(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentListData() {
        HashMap map = new HashMap();
        map.put("module_type", this.module_type + "");
        map.put("comment_type", "2");
        map.put("obj_id", this.course_id + "");
        map.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + pageNum);
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mCommentList, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.courselist.fragment.s2
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f12037c.lambda$getCommentListData$3((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.w2
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                this.f12061c.lambda$getCommentListData$4(volleyError, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteComment$11(String str, String str2) {
        try {
            AlertToast(new JSONObject(str).optString("message"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteComment$12(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editComment$7(String str, String str2) {
        try {
            AlertToast(new JSONObject(str).optString("message"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$editComment$8(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCommentListData$3(String str, String str2) {
        try {
            CommentBean commentBean = (CommentBean) new Gson().fromJson(str, CommentBean.class);
            if (commentBean.getCode().equals("200")) {
                this.hot = commentBean.getData().getHot();
                this.time_line = commentBean.getData().getTime_line();
                if (pageNum == 1) {
                    this.commlist.clear();
                    this.time_lines.clear();
                    CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                    hotBean.setType(1);
                    hotBean.setName("最热评论");
                    this.commlist.add(hotBean);
                    if (this.hot.size() > 0) {
                        this.commlist.addAll(this.hot);
                    } else {
                        CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                        hotBean2.setId("");
                        hotBean2.setContent("暂无最热评论");
                        this.commlist.add(hotBean2);
                    }
                    CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
                    hotBean3.setType(1);
                    hotBean3.setName("最新评论");
                    this.commlist.add(hotBean3);
                    if (this.time_line.size() > 0) {
                        this.commlist.addAll(this.time_line);
                    } else {
                        CommentBean.DataBean.HotBean hotBean4 = new CommentBean.DataBean.HotBean();
                        hotBean4.setId("");
                        hotBean4.setContent("暂无最新评论");
                        this.commlist.add(hotBean4);
                    }
                    this.time_lines.addAll(this.time_line);
                    CommentListAdapter commentListAdapter = new CommentListAdapter(this.mContext, this.commlist, this.time_line, this.module_type, "2", this.course_id + "", (BaseActivity) getActivity(), false);
                    this.mCommListAdapter = commentListAdapter;
                    this.mPinnedSecListView.setAdapter((ListAdapter) commentListAdapter);
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
                CommentBean.DataBean.HotBean hotBean5 = new CommentBean.DataBean.HotBean();
                hotBean5.setType(1);
                hotBean5.setName("最热评论");
                this.commlist.add(hotBean5);
                CommentBean.DataBean.HotBean hotBean6 = new CommentBean.DataBean.HotBean();
                hotBean6.setId("");
                hotBean6.setContent("暂无最热评论");
                this.commlist.add(hotBean6);
                CommentBean.DataBean.HotBean hotBean7 = new CommentBean.DataBean.HotBean();
                hotBean7.setType(1);
                hotBean7.setName("最新评论");
                this.commlist.add(hotBean7);
                CommentBean.DataBean.HotBean hotBean8 = new CommentBean.DataBean.HotBean();
                hotBean8.setId("");
                hotBean8.setContent("暂无最新评论");
                this.commlist.add(hotBean8);
                CommentListAdapter commentListAdapter2 = new CommentListAdapter(this.mContext, this.commlist, getActivity());
                this.mCommListAdapter = commentListAdapter2;
                this.mPinnedSecListView.setAdapter((ListAdapter) commentListAdapter2);
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
    public /* synthetic */ void lambda$getCommentListData$4(VolleyError volleyError, String str) {
        if (pageNum == 1) {
            if (this.commlist.size() > 0) {
                AlertToast("请求服务器失败");
                return;
            }
            CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
            hotBean.setType(1);
            hotBean.setName("最热评论");
            this.commlist.add(hotBean);
            CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
            hotBean2.setId("");
            hotBean2.setContent("暂无最热评论");
            this.commlist.add(hotBean2);
            CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
            hotBean3.setType(1);
            hotBean3.setName("最新评论");
            this.commlist.add(hotBean3);
            CommentBean.DataBean.HotBean hotBean4 = new CommentBean.DataBean.HotBean();
            hotBean4.setId("");
            hotBean4.setContent("暂无最新评论");
            this.commlist.add(hotBean4);
            CommentListAdapter commentListAdapter = new CommentListAdapter(this.mContext, this.commlist, getActivity());
            this.mCommListAdapter = commentListAdapter;
            this.mPinnedSecListView.setAdapter((ListAdapter) commentListAdapter);
        }
        if (this.mSwipeRefreshLayout.isRefreshing()) {
            this.mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(String str, String str2, String str3) {
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
    public /* synthetic */ void lambda$initViews$1(View view) {
        new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.d3
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f11938a.lambda$initViews$0(str, str2, str3);
            }
        }, false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2() {
        this.mSwipeRefreshLayout.setRefreshing(true);
        pageNum = 1;
        getCommentListData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putComment$5(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.optString("code").equals("200")) {
                if (jSONObject.optString("code").equals("401")) {
                    new CusomNewDialog(this.mContext).setMessage(jSONObject.optString("message")).show();
                    return;
                } else {
                    AlertToast(jSONObject.optString("message"));
                    return;
                }
            }
            NewToast.showShort(ProjectApp.instance(), "评论成功");
            if (!StrPool.EMPTY_JSON.equals(jSONObject.optString("data"))) {
                CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) new Gson().fromJson(jSONObject.optString("data"), CommentBean.DataBean.HotBean.class);
                this.commlist.removeAll(this.time_lines);
                if (this.time_lines.size() == 0) {
                    this.commlist.remove(3);
                }
                this.time_lines.add(0, hotBean);
                this.commlist.addAll(this.time_lines);
                this.mCommListAdapter.notifyDataSetChanged();
            }
            EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
            CommonUtil.showFristDialog(jSONObject);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$putComment$6(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDeleteDialog$10(String str, CustomDialog customDialog, View view) {
        deleteComment(str);
        customDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteDialog(final Message msg) {
        final String string = msg.getData().getString("comment_id");
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.setMessage(R.string.confirm_delete_comment);
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.x2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                customDialog.dismiss();
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.y2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12077c.lambda$showDeleteDialog$10(string, customDialog, view);
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_culum;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.module_type = getArguments().getInt("module_type");
        this.course_id = getArguments().getString("category_id");
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) holder.get(R.id.mSwipeLayput);
        ((Button) holder.get(R.id.bt_comment_play)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.z2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12084c.lambda$initViews$1(view);
            }
        });
        PinnedSectionListView1 pinnedSectionListView1 = (PinnedSectionListView1) holder.get(R.id.pinnedSectionListView1);
        this.mPinnedSecListView = pinnedSectionListView1;
        pinnedSectionListView1.setScrollingCacheEnabled(false);
        this.mPinnedSecListView.setAnimationCacheEnabled(false);
        this.addFooterView = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        if (SkinManager.getCurrentSkinType(this.mContext) == 0) {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(this.mContext.getResources().getColor(R.color.white));
            this.mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light, android.R.color.holo_red_light);
        } else {
            this.mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(this.mContext.getResources().getColor(R.color.input_night_color));
            this.mSwipeRefreshLayout.setColorSchemeResources(R.color.question_color_night, R.color.question_color_night, R.color.question_color_night, R.color.question_color_night);
        }
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.courselist.fragment.a3
            @Override // java.lang.Runnable
            public final void run() {
                this.f11920c.lambda$initViews$2();
            }
        });
        this.mPinnedSecListView.setOnScrollListener(new AnonymousClass2());
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
        map.put("comment_type", "2");
        map.put("obj_id", this.course_id + "");
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
        YJYHttpUtils.post(this.mContext.getApplicationContext(), NetworkRequestsURL.mPutComment, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.courselist.fragment.e3
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f11944c.lambda$putComment$5((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.courselist.fragment.t2
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                CurriculumEvaluationFragment.lambda$putComment$6(volleyError, str);
            }
        });
    }
}
