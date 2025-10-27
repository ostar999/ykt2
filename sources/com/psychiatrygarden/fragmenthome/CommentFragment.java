package com.psychiatrygarden.fragmenthome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.activity.MyCorrectionActivity;
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
public class CommentFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REFRESH_COMPLETE = 272;
    private static int pageNum = 1;
    private String _id;
    private View addFooterView;
    private View emptyview;
    public CommentListAdapter mCommListAdapter;
    public PinnedSectionListView1 mPinnedSecListView;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private int module_type;
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> time_lines = new ArrayList();
    private String comment_type = "1";

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.fragmenthome.CommentFragment.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i2 = msg.what;
            if (i2 == 2) {
                Bundle bundle = (Bundle) msg.obj;
                bundle.putInt("result", 1);
                if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
                    CommentFragment.this.putComment(bundle);
                    return;
                }
                Intent intent = new Intent(((BaseFragment) CommentFragment.this).mContext, (Class<?>) CorpCupActivity.class);
                intent.putExtra("bundleIntent", bundle);
                CommentFragment.this.startActivityForResult(intent, 1);
                return;
            }
            if (i2 == 272) {
                int unused = CommentFragment.pageNum = 1;
                CommentFragment.this.getCommentListData();
            } else if (i2 == 4) {
                CommentFragment.this.editComment(msg);
            } else {
                if (i2 != 5) {
                    return;
                }
                CommentFragment.this.showDeleteDialog(msg);
            }
        }
    };

    public static /* synthetic */ int access$308() {
        int i2 = pageNum;
        pageNum = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteComment(String comment_id) {
        HashMap map = new HashMap();
        map.put("token", UserConfig.getInstance().getUser().getToken());
        map.put("secret", UserConfig.getInstance().getUser().getSecret());
        map.put("comment_id", comment_id);
        YJYHttpUtils.post(getActivity(), "", map, new Response.Listener() { // from class: com.psychiatrygarden.fragmenthome.m1
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f15822c.lambda$deleteComment$8((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.fragmenthome.n1
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                CommentFragment.lambda$deleteComment$9(volleyError, str);
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
        YJYHttpUtils.post(getActivity(), "", map, new Response.Listener() { // from class: com.psychiatrygarden.fragmenthome.s1
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f15979c.lambda$editComment$6((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.fragmenthome.t1
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                CommentFragment.lambda$editComment$7(volleyError, str);
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
        YJYHttpUtils.post(getActivity(), str, map, new Response.Listener() { // from class: com.psychiatrygarden.fragmenthome.u1
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str2) {
                this.f16029c.lambda$getCommentListData$4((String) obj, str2);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.fragmenthome.CommentFragment.3
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError arg0, String arg1) {
                if (CommentFragment.pageNum == 1) {
                    if (CommentFragment.this.commlist.size() > 0) {
                        CommentFragment.this.AlertToast("请求服务器失败");
                        return;
                    }
                    CommentFragment.this.mCommListAdapter = new CommentListAdapter(((BaseFragment) CommentFragment.this).mContext, CommentFragment.this.commlist, CommentFragment.this.getActivity());
                    CommentFragment commentFragment = CommentFragment.this;
                    commentFragment.mPinnedSecListView.setAdapter((ListAdapter) commentFragment.mCommListAdapter);
                }
                if (CommentFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                    CommentFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteComment$8(String str, String str2) {
        try {
            AlertToast(new JSONObject(str).optString("message"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$deleteComment$9(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$editComment$6(String str, String str2) {
        try {
            AlertToast(new JSONObject(str).optString("message"));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$editComment$7(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCommentListData$4(String str, String str2) {
        try {
            CommentBean commentBean = (CommentBean) new Gson().fromJson(str, CommentBean.class);
            if (commentBean.getCode().equals("200")) {
                this.emptyview.setVisibility(8);
                this.time_line = commentBean.getData().getTime_line();
                if (pageNum == 1) {
                    this.commlist.clear();
                    this.time_lines.clear();
                    if (this.time_line.size() > 0) {
                        this.commlist.addAll(this.time_line);
                    }
                    this.time_lines.addAll(this.time_line);
                    CommentListAdapter commentListAdapter = new CommentListAdapter(this.mContext, this.commlist, this.time_line, this.module_type, this.comment_type, this._id + "", (BaseActivity) getActivity(), false);
                    this.mCommListAdapter = commentListAdapter;
                    this.mPinnedSecListView.setAdapter((ListAdapter) commentListAdapter);
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
        new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.fragmenthome.v1
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f16058a.lambda$initViews$1(str, str2, str3);
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
    public static /* synthetic */ void lambda$putComment$5(VolleyError volleyError, String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDeleteDialog(final Message msg) {
        final String string = msg.getData().getString("comment_id");
        msg.getData().getInt("position");
        final CustomDialog customDialog = new CustomDialog(this.mContext, 2);
        customDialog.setCancelable(false);
        customDialog.setMessage(R.string.confirm_delete_comment);
        customDialog.setNegativeBtn(R.string.cancel, new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.CommentFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                customDialog.dismiss();
            }
        });
        customDialog.setPositiveBtn(R.string.ok, new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.CommentFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                CommentFragment.this.deleteComment(string);
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_course_culum;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) throws Resources.NotFoundException {
        this.module_type = getArguments().getInt("module_type");
        this._id = getArguments().getString(aq.f22519d);
        this.emptyview = holder.get(R.id.emptyview);
        LinearLayout linearLayout = (LinearLayout) holder.get(R.id.llay_comment);
        LinearLayout linearLayout2 = (LinearLayout) holder.get(R.id.ly_questiondetails_btn_edit);
        if (getActivity() instanceof MyCorrectionActivity) {
            linearLayout.setVisibility(8);
        } else {
            linearLayout.setVisibility(0);
            linearLayout2.setVisibility(0);
        }
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.p1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15904c.lambda$initViews$0(view);
            }
        });
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) holder.get(R.id.mSwipeLayput);
        Button button = (Button) holder.get(R.id.bt_comment_play);
        button.setText("我要纠错");
        button.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.shape_gray_bg));
        CommonUtil.mDoDrawable(getActivity(), button, R.drawable.jiucuoimg, 0);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.q1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15928c.lambda$initViews$2(view);
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
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.r1
            @Override // java.lang.Runnable
            public final void run() {
                this.f15955c.lambda$initViews$3();
            }
        });
        this.mPinnedSecListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.fragmenthome.CommentFragment.2
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0 && view.getLastVisiblePosition() == view.getCount() - 1 && CommentFragment.this.mPinnedSecListView.getFooterViewsCount() <= 0) {
                    CommentFragment commentFragment = CommentFragment.this;
                    commentFragment.mPinnedSecListView.addFooterView(commentFragment.addFooterView);
                    CommentFragment.this.addFooterView.setVisibility(0);
                    if (CommentFragment.this.mSwipeRefreshLayout.isRefreshing()) {
                        CommentFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                    } else {
                        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.CommentFragment.2.1
                            @Override // java.lang.Runnable
                            public void run() {
                                CommentFragment.access$308();
                                CommentFragment.this.getCommentListData();
                            }
                        }, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                    }
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        super.onEventMainThread(str);
        if ("mCommentResult".equals(str)) {
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
        YJYHttpUtils.post(getActivity(), NetworkRequestsURL.mPutComment, map, new Response.Listener<String>() { // from class: com.psychiatrygarden.fragmenthome.CommentFragment.4
            @Override // com.android.volley.Response.Listener
            public void onResponse(String arg0, String arg1) {
                try {
                    JSONObject jSONObject = new JSONObject(arg0);
                    if (jSONObject.optString("code").equals("200")) {
                        CommentFragment.this.AlertToast(jSONObject.optString("message"));
                        EventBus.getDefault().post("mCommentResult");
                        EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                        CommonUtil.showFristDialog(jSONObject);
                    } else if (jSONObject.optString("code").equals("401")) {
                        new CusomNewDialog(((BaseFragment) CommentFragment.this).mContext).setMessage(jSONObject.optString("message")).show();
                    } else {
                        CommentFragment.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.fragmenthome.o1
            @Override // com.android.volley.Response.ErrorListener
            public final void onErrorResponse(VolleyError volleyError, String str) {
                CommentFragment.lambda$putComment$5(volleyError, str);
            }
        });
    }
}
