package com.psychiatrygarden.activity.material;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.provider.FontsContractCompat;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.CommentNewActivity;
import com.psychiatrygarden.activity.CorpCupActivity;
import com.psychiatrygarden.adapter.InformationCommentListAdapter;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.MyNightUtil;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CusomNewDialog;
import com.psychiatrygarden.widget.DialogInput;
import com.psychiatrygarden.widget.PinnedSectionListView1;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.open.SocialConstants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class InformationCommentListAct extends BaseActivity implements OnRefreshLoadMoreListener {
    private static final int REFRESH_COMPLETE = 272;
    private View addFooterView;
    private TextView comment_lou;
    private View emptyView;
    private boolean hasHotComment;
    private boolean isLoadMore;
    private boolean isRefreshing;
    public ImageView iv_topic_detail_msg;
    private LinearLayout lineselect;
    public InformationCommentListAdapter mCommListAdapter;
    private ImageView mImgCollection;
    private ImageView mImgComment;
    private ImageView mImgPraise;
    private boolean mIsSdkAd;
    private LinearLayout mLyCollection;
    private RelativeLayout mLyCommentCount;
    private LinearLayout mLyCommentMsg;
    private LinearLayout mLyPraise;
    public PinnedSectionListView1 mPinnedSecListView;
    public SmartRefreshLayout mSwipeRefreshLayout;
    private TextView mTvCommentCount;
    public ImageView pinglunlog;
    private long question_id;
    private CheckedTextView remen;
    public TextView tv_topic_detail_add_comment;
    private CheckedTextView zuixin;
    private List<CommentBean.DataBean.HotBean> hot = new ArrayList();
    private List<CommentBean.DataBean.HotBean> time_line = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> commlist = new ArrayList();
    private final List<CommentBean.DataBean.HotBean> time_lines = new ArrayList();
    private int positionTab = 0;
    private int posInvate = 1;
    private int pageNum = 1;
    private String only_author = "0";
    private boolean isNewComzantong = false;
    private String fromType = "";
    private boolean mHasConfigAd = false;
    private boolean isHUAdong = false;
    private boolean isCollection = false;

    @SuppressLint({"HandlerLeak"})
    private final Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.material.InformationCommentListAct.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 272) {
                InformationCommentListAct.this.getCommentListData(false, 0);
            }
        }
    };

    /* renamed from: com.psychiatrygarden.activity.material.InformationCommentListAct$2, reason: invalid class name */
    public class AnonymousClass2 implements AbsListView.OnScrollListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0() {
            InformationCommentListAct informationCommentListAct = InformationCommentListAct.this;
            informationCommentListAct.mPinnedSecListView.setSelection(informationCommentListAct.posInvate);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$1() {
            InformationCommentListAct.access$408(InformationCommentListAct.this);
            InformationCommentListAct.this.getCommentListData(false, 0);
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (InformationCommentListAct.this.positionTab <= 0) {
                if (firstVisibleItem >= 1) {
                    InformationCommentListAct.this.isSelect(false, true);
                    return;
                } else {
                    InformationCommentListAct.this.isSelect(true, false);
                    return;
                }
            }
            if (firstVisibleItem >= InformationCommentListAct.this.positionTab + 1) {
                InformationCommentListAct.this.isSelect(false, true);
            } else if (firstVisibleItem < 1 || firstVisibleItem >= InformationCommentListAct.this.positionTab + 1) {
                InformationCommentListAct.this.isSelect(true, false);
            } else {
                InformationCommentListAct.this.isSelect(true, false);
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0) {
                if (InformationCommentListAct.this.isHUAdong) {
                    InformationCommentListAct.this.isHUAdong = false;
                    InformationCommentListAct.this.mPinnedSecListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.material.l
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f12727c.lambda$onScrollStateChanged$0();
                        }
                    }, 500L);
                } else {
                    if (view.getLastVisiblePosition() != view.getCount() - 1 || InformationCommentListAct.this.mPinnedSecListView.getFooterViewsCount() > 0 || InformationCommentListAct.this.time_line == null || InformationCommentListAct.this.time_line.size() <= 0) {
                        return;
                    }
                    InformationCommentListAct informationCommentListAct = InformationCommentListAct.this;
                    informationCommentListAct.mPinnedSecListView.addFooterView(informationCommentListAct.addFooterView);
                    InformationCommentListAct.this.addFooterView.setVisibility(0);
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.material.m
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f12730c.lambda$onScrollStateChanged$1();
                        }
                    }, 1000L);
                }
            }
        }
    }

    public static /* synthetic */ int access$408(InformationCommentListAct informationCommentListAct) {
        int i2 = informationCommentListAct.pageNum;
        informationCommentListAct.pageNum = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int access$512(InformationCommentListAct informationCommentListAct, int i2) {
        int i3 = informationCommentListAct.posInvate + i2;
        informationCommentListAct.posInvate = i3;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void collectionMsgUI(boolean isCollection) {
        if (isCollection) {
            this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.icon_collect_yes_night : R.drawable.icon_collect_yes);
        } else {
            this.mImgCollection.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.icon_collect_no_night : R.drawable.icon_collect_no);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void commentMsgUI(boolean isComment) {
        if (isComment) {
            this.mImgComment.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.youpinglun_night : R.drawable.youpinglun);
        } else {
            this.mImgComment.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.question_msg_night : R.drawable.question_msg);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getAdConfig() {
        YJYHttpUtils.post(getApplicationContext(), NetworkRequestsURL.QUESTION_AD_CONFIG, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationCommentListAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (TextUtils.equals("200", jSONObject.optString("code", "0"))) {
                        JSONArray jSONArray = jSONObject.getJSONArray("data");
                        if (jSONArray.length() > 0) {
                            int length = jSONArray.length();
                            for (int i2 = 0; i2 < length; i2++) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                                if (CommonParameter.AD_CONFIG.equals(jSONObject2.optString("key", ""))) {
                                    SharePreferencesUtils.writeStrConfig(CommonParameter.AD_CONFIG, jSONObject2.optString("value", ""), InformationCommentListAct.this.getApplicationContext());
                                    InformationCommentListAct.this.mIsSdkAd = false;
                                    return;
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getBottomInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.question_id + "");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.soutceStatusInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationCommentListAct.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws NumberFormatException {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        String strOptString = new JSONObject(s2).optJSONObject("data").optString("is_collect");
                        String strOptString2 = new JSONObject(s2).optJSONObject("data").optString("is_support");
                        String strOptString3 = new JSONObject(s2).optJSONObject("data").optString("is_review");
                        String strOptString4 = new JSONObject(s2).optJSONObject("data").optString("review_count");
                        InformationCommentListAct.this.isCollection = (TextUtils.isEmpty(strOptString) || strOptString.equals("0")) ? false : true;
                        boolean z2 = (TextUtils.isEmpty(strOptString2) || strOptString2.equals("0")) ? false : true;
                        boolean z3 = (TextUtils.isEmpty(strOptString3) || strOptString3.equals("0")) ? false : true;
                        InformationCommentListAct.this.praiseMsgUI(z2);
                        InformationCommentListAct informationCommentListAct = InformationCommentListAct.this;
                        informationCommentListAct.collectionMsgUI(informationCommentListAct.isCollection);
                        InformationCommentListAct.this.commentMsgUI(z3);
                        if (TextUtils.isEmpty(strOptString4)) {
                            InformationCommentListAct.this.mTvCommentCount.setText("0");
                            return;
                        }
                        int i2 = Integer.parseInt(strOptString4);
                        if (i2 > 10000) {
                            InformationCommentListAct.this.mTvCommentCount.setText(String.format(Locale.CHINA, "%d.%d万", Integer.valueOf(i2 / 10000), Integer.valueOf((i2 % 10000) / 1000)));
                        } else {
                            InformationCommentListAct.this.mTvCommentCount.setText(String.valueOf(i2));
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentAd() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.adsInCommentApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationCommentListAct.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (t2 != null) {
                    t2.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String json) {
                JSONObject jSONObjectOptJSONObject;
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (jSONObject.optString("code").equals("200") && (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) != null && jSONObjectOptJSONObject.length() > 0) {
                        if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, InformationCommentListAct.this.mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, InformationCommentListAct.this.mContext, 0L).longValue()) / 1000) - jSONObjectOptJSONObject.optLong("time_interval", 0L) : 0L) < 0 || InformationCommentListAct.this.commlist.size() <= 8 || TextUtils.isEmpty(jSONObjectOptJSONObject.optString("img"))) {
                            return;
                        }
                        CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                        hotBean.setAds(jSONObject.optString("data"));
                        if (InformationCommentListAct.this.hot == null || InformationCommentListAct.this.hot.size() < 8) {
                            return;
                        }
                        hotBean.setHot(true);
                        InformationCommentListAct.this.commlist.add(9, hotBean);
                        InformationCommentListAct.this.mHasConfigAd = true;
                        InformationCommentListAct informationCommentListAct = InformationCommentListAct.this;
                        informationCommentListAct.mCommListAdapter.setList(informationCommentListAct.commlist);
                        InformationCommentListAct.this.mCommListAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNewPositions() {
        for (int i2 = 0; i2 < this.commlist.size(); i2++) {
            CommentBean.DataBean.HotBean hotBean = this.commlist.get(i2);
            int type = hotBean.getType();
            boolean zIsHot = hotBean.isHot();
            if (type == 1 && !zIsHot && hotBean.getName() != null && hotBean.getName().contains("最新")) {
                return i2 + 1;
            }
        }
        return 0;
    }

    private void goToCommentList(String type) {
        Intent intent = new Intent(this.mContext, (Class<?>) CommentNewActivity.class);
        intent.putExtra("question_id", this.question_id);
        intent.putExtra(com.alipay.sdk.authjs.a.f3174g, SocialConstants.PARAM_SOURCE);
        intent.putExtra("search_type", type);
        startActivity(intent);
    }

    private void infoCollectionAction() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.question_id + "");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.infoCollection, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationCommentListAct.7
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass7) s2);
                try {
                    if (new JSONObject(s2).optString("code").equals("200")) {
                        InformationCommentListAct informationCommentListAct = InformationCommentListAct.this;
                        informationCommentListAct.isCollection = !informationCommentListAct.isCollection;
                        InformationCommentListAct informationCommentListAct2 = InformationCommentListAct.this;
                        informationCommentListAct2.collectionMsgUI(informationCommentListAct2.isCollection);
                        EventBus.getDefault().post("refresh_praise");
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(RefreshLayout refreshLayout) {
        this.pageNum = 1;
        this.isRefreshing = true;
        this.mHandler.sendEmptyMessageDelayed(272, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        setRemen(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        setRemen(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString("content", str);
        bundle.putInt("result", 1);
        bundle.putString("b_img", str2);
        bundle.putString("s_img", str3);
        if (!UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            putComment(bundle);
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) CorpCupActivity.class);
        intent.putExtra("bundleIntent", bundle);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        new DialogInput(this.mContext, new onDialogClickListener() { // from class: com.psychiatrygarden.activity.material.a
            @Override // com.psychiatrygarden.interfaceclass.onDialogClickListener
            public final void onclickStringBack(String str, String str2, String str3) {
                this.f12694a.lambda$setListenerForWidget$3(str, str2, str3);
            }
        }, false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        goToCommentList("1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        goToCommentList("2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(View view) {
        infoCollectionAction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$8() {
        this.isHUAdong = true;
        if (this.hasHotComment) {
            setRemen(0);
        } else {
            setRemen(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$9(View view) {
        this.mPinnedSecListView.post(new Runnable() { // from class: com.psychiatrygarden.activity.material.c
            @Override // java.lang.Runnable
            public final void run() {
                this.f12699c.lambda$setListenerForWidget$8();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void praiseMsgUI(boolean isPraise) {
        if (isPraise) {
            this.mImgPraise.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.youdianzan_night : R.drawable.youdianzan);
        } else {
            this.mImgPraise.setImageResource(SkinManager.getCurrentSkinType(this) == 1 ? R.drawable.dianzancourse_night : R.drawable.dianzancourse);
        }
    }

    public void getCommentListData(final boolean isAddComment, int position) {
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.soutceCommentList;
        ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.question_id + "");
        ajaxParams.put("search_type", getIntent().getStringExtra("search_type"));
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.pageNum);
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationCommentListAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                InformationCommentListAct.this.mSwipeRefreshLayout.finishRefresh();
                if (InformationCommentListAct.this.isLoadMore) {
                    InformationCommentListAct.this.mSwipeRefreshLayout.finishLoadMore(false);
                } else {
                    InformationCommentListAct.this.mSwipeRefreshLayout.finishRefresh();
                }
                if (InformationCommentListAct.this.pageNum == 1) {
                    if (InformationCommentListAct.this.commlist.size() > 0) {
                        InformationCommentListAct.this.AlertToast("请求服务器失败");
                        return;
                    }
                    InformationCommentListAct informationCommentListAct = InformationCommentListAct.this;
                    InformationCommentListAct informationCommentListAct2 = InformationCommentListAct.this;
                    Context context = informationCommentListAct2.mContext;
                    List list = informationCommentListAct2.commlist;
                    InformationCommentListAct informationCommentListAct3 = InformationCommentListAct.this;
                    informationCommentListAct.mCommListAdapter = new InformationCommentListAdapter(context, list, informationCommentListAct3, informationCommentListAct3.fromType.equals(SocialConstants.PARAM_SOURCE));
                    InformationCommentListAct informationCommentListAct4 = InformationCommentListAct.this;
                    informationCommentListAct4.mPinnedSecListView.setAdapter((ListAdapter) informationCommentListAct4.mCommListAdapter);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String arg0) {
                super.onSuccess((AnonymousClass3) arg0);
                try {
                    CommentBean commentBean = (CommentBean) new Gson().fromJson(arg0, CommentBean.class);
                    if (commentBean.getCode().equals("200")) {
                        InformationCommentListAct.this.hot = commentBean.getData().getHot();
                        InformationCommentListAct informationCommentListAct = InformationCommentListAct.this;
                        informationCommentListAct.hasHotComment = informationCommentListAct.hot != null && InformationCommentListAct.this.hot.size() > 0;
                        InformationCommentListAct.this.time_line = commentBean.getData().getTime_line();
                        if (InformationCommentListAct.this.pageNum == 1) {
                            InformationCommentListAct informationCommentListAct2 = InformationCommentListAct.this;
                            informationCommentListAct2.positionTab = informationCommentListAct2.hot.size();
                            InformationCommentListAct.this.commlist.clear();
                            InformationCommentListAct.this.time_lines.clear();
                            if (InformationCommentListAct.this.hot.size() > 0) {
                                CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                                hotBean.setType(1);
                                hotBean.setHot(true);
                                hotBean.setName("最热评论(" + InformationCommentListAct.this.hot.size() + ")");
                                InformationCommentListAct.this.commlist.add(hotBean);
                                InformationCommentListAct.this.commlist.addAll(InformationCommentListAct.this.hot);
                                InformationCommentListAct.this.posInvate = 1;
                                InformationCommentListAct informationCommentListAct3 = InformationCommentListAct.this;
                                InformationCommentListAct.access$512(informationCommentListAct3, informationCommentListAct3.commlist.size());
                                InformationCommentListAct.this.lineselect.setVisibility(0);
                            } else {
                                InformationCommentListAct.this.lineselect.setVisibility(8);
                            }
                            CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                            hotBean2.setType(1);
                            StringBuilder sb = new StringBuilder();
                            sb.append("最新评论（");
                            sb.append((InformationCommentListAct.this.time_line == null || InformationCommentListAct.this.time_line.isEmpty()) ? "0" : commentBean.getData().getTime_line_total());
                            sb.append("）");
                            hotBean2.setName(sb.toString());
                            InformationCommentListAct.this.commlist.add(hotBean2);
                            if (InformationCommentListAct.this.time_line.size() > 0) {
                                InformationCommentListAct.this.commlist.addAll(InformationCommentListAct.this.time_line);
                            } else {
                                CommentBean.DataBean.HotBean hotBean3 = new CommentBean.DataBean.HotBean();
                                hotBean3.setId("");
                                hotBean3.setContent("暂无评论");
                                InformationCommentListAct.this.commlist.add(hotBean3);
                            }
                            InformationCommentListAct.this.time_lines.addAll(InformationCommentListAct.this.time_line);
                            InformationCommentListAct informationCommentListAct4 = InformationCommentListAct.this;
                            InformationCommentListAct informationCommentListAct5 = InformationCommentListAct.this;
                            Context context = informationCommentListAct5.mContext;
                            List list = informationCommentListAct5.commlist;
                            List list2 = InformationCommentListAct.this.time_line;
                            String str2 = InformationCommentListAct.this.question_id + "";
                            InformationCommentListAct informationCommentListAct6 = InformationCommentListAct.this;
                            informationCommentListAct4.mCommListAdapter = new InformationCommentListAdapter(context, list, list2, 0, "", str2, informationCommentListAct6, informationCommentListAct6.isNewComzantong, true, InformationCommentListAct.this.fromType.equals(SocialConstants.PARAM_SOURCE));
                            InformationCommentListAct informationCommentListAct7 = InformationCommentListAct.this;
                            informationCommentListAct7.mPinnedSecListView.setAdapter((ListAdapter) informationCommentListAct7.mCommListAdapter);
                            if (InformationCommentListAct.this.hot.size() >= 8 && !InformationCommentListAct.this.mIsSdkAd) {
                                InformationCommentListAct.this.getCommentAd();
                            }
                        } else {
                            InformationCommentListAct informationCommentListAct8 = InformationCommentListAct.this;
                            informationCommentListAct8.mPinnedSecListView.removeFooterView(informationCommentListAct8.addFooterView);
                            InformationCommentListAct.this.addFooterView.setVisibility(8);
                            InformationCommentListAct.this.mPinnedSecListView.invalidateViews();
                            if (InformationCommentListAct.this.time_line.size() == 0) {
                                InformationCommentListAct.this.AlertToast("已经是最后一条");
                            } else {
                                InformationCommentListAct.this.commlist.addAll(InformationCommentListAct.this.time_line);
                                InformationCommentListAct.this.time_lines.addAll(InformationCommentListAct.this.time_line);
                                InformationCommentListAct informationCommentListAct9 = InformationCommentListAct.this;
                                informationCommentListAct9.mCommListAdapter.setRefeault(informationCommentListAct9.time_line);
                                InformationCommentListAct.this.mCommListAdapter.notifyDataSetChanged();
                            }
                        }
                        if (isAddComment) {
                            InformationCommentListAct.this.zuixin.performClick();
                        }
                    } else if (InformationCommentListAct.this.pageNum == 1) {
                        InformationCommentListAct.this.commlist.clear();
                        InformationCommentListAct.this.time_lines.clear();
                        if (InformationCommentListAct.this.commlist.size() == 0) {
                            CommentBean.DataBean.HotBean hotBean4 = new CommentBean.DataBean.HotBean();
                            hotBean4.setType(1);
                            hotBean4.setName("最新评论(0)");
                            InformationCommentListAct.this.commlist.add(hotBean4);
                            CommentBean.DataBean.HotBean hotBean5 = new CommentBean.DataBean.HotBean();
                            hotBean5.setId("");
                            hotBean5.setContent("暂无评论");
                            InformationCommentListAct.this.commlist.add(hotBean5);
                            InformationCommentListAct informationCommentListAct10 = InformationCommentListAct.this;
                            InformationCommentListAct informationCommentListAct11 = InformationCommentListAct.this;
                            Context context2 = informationCommentListAct11.mContext;
                            List list3 = informationCommentListAct11.commlist;
                            InformationCommentListAct informationCommentListAct12 = InformationCommentListAct.this;
                            informationCommentListAct10.mCommListAdapter = new InformationCommentListAdapter(context2, list3, informationCommentListAct12, informationCommentListAct12.fromType.equals(SocialConstants.PARAM_SOURCE));
                            InformationCommentListAct informationCommentListAct13 = InformationCommentListAct.this;
                            informationCommentListAct13.mPinnedSecListView.setAdapter((ListAdapter) informationCommentListAct13.mCommListAdapter);
                        } else {
                            InformationCommentListAct.this.AlertToast(commentBean.getMessage());
                        }
                    } else {
                        InformationCommentListAct.this.AlertToast(commentBean.getMessage());
                        InformationCommentListAct informationCommentListAct14 = InformationCommentListAct.this;
                        informationCommentListAct14.mPinnedSecListView.removeFooterView(informationCommentListAct14.addFooterView);
                        InformationCommentListAct.this.addFooterView.setVisibility(8);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    InformationCommentListAct.this.mSwipeRefreshLayout.finishRefresh();
                    InformationCommentListAct.this.mSwipeRefreshLayout.finishLoadMore(false);
                }
                InformationCommentListAct.this.mSwipeRefreshLayout.finishRefresh();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("评论");
        this.comment_lou = (TextView) findViewById(R.id.comment_lou);
        this.emptyView = findViewById(R.id.empty_view);
        this.pinglunlog = (ImageView) findViewById(R.id.pinglunlog);
        this.remen = (CheckedTextView) findViewById(R.id.remen2);
        this.zuixin = (CheckedTextView) findViewById(R.id.zuixin2);
        this.lineselect = (LinearLayout) findViewById(R.id.lineselect2);
        this.mLyCollection = (LinearLayout) findViewById(R.id.ly_collection);
        this.mLyPraise = (LinearLayout) findViewById(R.id.ly_praise);
        this.mLyCommentMsg = (LinearLayout) findViewById(R.id.ly_comment_msg);
        this.mImgCollection = (ImageView) findViewById(R.id.img_collection);
        this.mImgPraise = (ImageView) findViewById(R.id.img_praise);
        this.mImgComment = (ImageView) findViewById(R.id.img_comment);
        this.mTvCommentCount = (TextView) findViewById(R.id.questiondetails_btn_commentNum);
        this.mLyCommentCount = (RelativeLayout) findViewById(R.id.ll_question_comment);
        this.emptyView.setVisibility(8);
        PinnedSectionListView1 pinnedSectionListView1 = (PinnedSectionListView1) findViewById(R.id.pinnedSectionListView1);
        this.mPinnedSecListView = pinnedSectionListView1;
        pinnedSectionListView1.setScrollingCacheEnabled(false);
        this.mPinnedSecListView.setAnimationCacheEnabled(false);
        this.tv_topic_detail_add_comment = (TextView) findViewById(R.id.btn_comment);
        this.iv_topic_detail_msg = (ImageView) findViewById(R.id.iv_topic_detail_msg);
        this.addFooterView = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.mSwipeLayput);
        this.mSwipeRefreshLayout = smartRefreshLayout;
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        this.mSwipeRefreshLayout.setOnRefreshLoadMoreListener(this);
        this.addFooterView = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.mSwipeRefreshLayout.setEnableLoadMore(false);
        this.mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.material.i
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f12718c.lambda$init$0(refreshLayout);
            }
        });
        this.mSwipeRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.material.j
            @Override // java.lang.Runnable
            public final void run() {
                this.f12721c.getAdConfig();
            }
        });
        isSelect(true, false);
        this.remen.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12724c.lambda$init$1(view);
            }
        });
        this.zuixin.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12696c.lambda$init$2(view);
            }
        });
        getCommentListData(false, 0);
        this.mPinnedSecListView.setOnScrollListener(new AnonymousClass2());
        this.pinglunlog.setVisibility(8);
        getBottomInfo();
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

    public void isSelect(boolean renmenTrue, boolean zuixinTrue) {
        this.remen.setChecked(renmenTrue);
        this.zuixin.setChecked(zuixinTrue);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            this.mCommListAdapter.getputData(data.getBundleExtra("bundleIntent"));
        } else {
            if (requestCode != 1) {
                return;
            }
            putComment(data.getBundleExtra("bundleIntent"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(CommonParameter.SYStem_Red_Dot)) {
            if (SharePreferencesUtils.readBooleanConfig(CommonParameter.Experience_comment_Red_Dot, false, this.mContext)) {
                this.iv_topic_detail_msg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg_new));
                return;
            } else {
                this.iv_topic_detail_msg.setBackground(MyNightUtil.getDrawable(this.mContext, R.drawable.question_msg));
                return;
            }
        }
        if (str.equals(CommonParameter.COMMENT_BG_SHOW)) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.alpha = 0.4f;
            getWindow().addFlags(2);
            getWindow().setAttributes(attributes);
            return;
        }
        if (str.equals(CommonParameter.COMMENT_BG_DISMISS)) {
            WindowManager.LayoutParams attributes2 = getWindow().getAttributes();
            attributes2.alpha = 1.0f;
            getWindow().addFlags(2);
            getWindow().setAttributes(attributes2);
            return;
        }
        if ("delReplyAndLoadData".equals(str)) {
            this.pageNum = 1;
            getCommentListData(false, 0);
            getBottomInfo();
        } else if ("mCommentResult".equals(str)) {
            this.pageNum = 1;
            getCommentListData(false, 0);
        }
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.mHandler.sendEmptyMessageDelayed(272, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    public void putComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        String str = NetworkRequestsURL.mPutComment;
        String str2 = NetworkRequestsURL.submitComment;
        ajaxParams.put(FontsContractCompat.Columns.FILE_ID, this.question_id + "");
        ajaxParams.put("content", b3.getString("content"));
        String string = b3.getString("b_img");
        String string2 = b3.getString("s_img");
        if (!TextUtils.isEmpty(string)) {
            if (string.contains("http")) {
                ajaxParams.put("b_img", string);
                ajaxParams.put("s_img", string2);
            } else {
                ajaxParams.put("video_id", b3.getString("b_img"));
            }
        }
        if (UserConfig.getInstance().getUser().getAdmin().equals("1")) {
            ajaxParams.put("virtual_user_id", b3.getString("virtual_user_id"));
        }
        YJYHttpUtils.post(this.mContext.getApplicationContext(), str2, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.material.InformationCommentListAct.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    boolean z2 = false;
                    if (!jSONObject.optString("code").equals("200")) {
                        if (jSONObject.optString("code").equals("401")) {
                            new CusomNewDialog(InformationCommentListAct.this.mContext).setMessage(jSONObject.optString("message")).show();
                            return;
                        } else {
                            NewToast.showShort(InformationCommentListAct.this.mContext, jSONObject.optString("message"), 0).show();
                            return;
                        }
                    }
                    ProjectApp.comment_content = "";
                    ProjectApp.comment_b_img = "";
                    ProjectApp.comment_s_img = "";
                    ProjectApp.commentvideoPath = "";
                    ProjectApp.commentvideoImage = "";
                    ProjectApp.commentvideoId = "";
                    ProjectApp.commentvideoImagePath = "";
                    NewToast.showShort(ProjectApp.instance(), "评论成功");
                    if (!StrPool.EMPTY_JSON.equals(jSONObject.optString("data"))) {
                        CommentBean.DataBean.HotBean hotBean = (CommentBean.DataBean.HotBean) new Gson().fromJson(jSONObject.optString("data"), CommentBean.DataBean.HotBean.class);
                        InformationCommentListAct.this.commlist.removeAll(InformationCommentListAct.this.time_lines);
                        if (InformationCommentListAct.this.time_lines.size() == 0 && InformationCommentListAct.this.commlist.size() > 2) {
                            InformationCommentListAct.this.commlist.remove(3);
                        }
                        InformationCommentListAct.this.time_lines.add(0, hotBean);
                        InformationCommentListAct.this.commlist.addAll(InformationCommentListAct.this.time_lines);
                        InformationCommentListAct informationCommentListAct = InformationCommentListAct.this;
                        int i2 = 8;
                        informationCommentListAct.mPinnedSecListView.setVisibility(informationCommentListAct.time_lines.size() > 0 ? 0 : 8);
                        View view = InformationCommentListAct.this.emptyView;
                        if (InformationCommentListAct.this.time_lines.size() <= 0) {
                            i2 = 0;
                        }
                        view.setVisibility(i2);
                        InformationCommentListAct.this.mCommListAdapter.notifyDataSetChanged();
                    }
                    InformationCommentListAct.this.pageNum = 1;
                    if (jSONObject.has("is_collection")) {
                        String string3 = jSONObject.getString("is_collection");
                        InformationCommentListAct.this.isCollection = (TextUtils.isEmpty(string3) || string3.equals("0")) ? false : true;
                        InformationCommentListAct informationCommentListAct2 = InformationCommentListAct.this;
                        informationCommentListAct2.collectionMsgUI(informationCommentListAct2.isCollection);
                    }
                    if (jSONObject.has("is_praise")) {
                        String string4 = jSONObject.getString("is_praise");
                        if (!TextUtils.isEmpty(string4) && !string4.equals("0")) {
                            z2 = true;
                        }
                        InformationCommentListAct.this.praiseMsgUI(z2);
                    }
                    InformationCommentListAct informationCommentListAct3 = InformationCommentListAct.this;
                    informationCommentListAct3.getCommentListData(true, informationCommentListAct3.getNewPositions());
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    EventBus.getDefault().post(EventBusConstant.EVENT_REFRESH_COMMENT_NUM);
                    CommonUtil.showFristDialog(jSONObject);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setNewStyleStatusBarColor();
        setNavBarColor();
        setContentView(R.layout.layout_information_comment);
        setTitle("评论");
        this.fromType = getIntent().getExtras().getString(com.alipay.sdk.authjs.a.f3174g);
        this.question_id = getIntent().getLongExtra("question_id", 0L);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.tv_topic_detail_add_comment.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12702c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mLyCommentMsg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12705c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mLyPraise.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12708c.lambda$setListenerForWidget$6(view);
            }
        });
        this.mLyCollection.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12711c.lambda$setListenerForWidget$7(view);
            }
        });
        this.mLyCommentCount.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.material.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12714c.lambda$setListenerForWidget$9(view);
            }
        });
    }

    public void setRemen(int type) {
        if (type == 0) {
            this.mPinnedSecListView.setSelection(0);
            return;
        }
        isSelect(false, true);
        int i2 = this.positionTab;
        if (i2 > 0) {
            this.mPinnedSecListView.setSelection(i2 + 1);
        } else {
            this.mPinnedSecListView.setSelection(i2);
        }
    }
}
