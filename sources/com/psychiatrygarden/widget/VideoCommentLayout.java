package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import cn.hutool.core.lang.RegexPool;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.adapter.CircleInfoAdapter;
import com.psychiatrygarden.bean.CommentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.UpdateVideoCommentNote;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class VideoCommentLayout extends LinearLayout implements OnRefreshLoadMoreListener, CircleInfoAdapter.CircleInfoClickIml, View.OnClickListener {
    private static final String commentType = "2";
    private View anchorView;
    private DiscussStatus commentEnum;
    private String courseId;
    private CustomEmptyView empty_view;
    private boolean hasHot;
    private boolean hasHotSize;
    private String hotTotal;
    private boolean initView;
    private boolean isLastPage;
    private boolean isLoading;
    private boolean isRefresh;
    private boolean landScape;
    private LinearLayout llHotNew;
    private CircleInfoAdapter mAdapter;
    private HotNewChangeListener mChangeListener;
    private final List<CommentBean.DataBean.HotBean> mCommentList;
    private boolean mHasConfigAd;
    private CommentInfoListener mInfoListener;
    private boolean mIsSdkAd;
    private PinnedSectionListView1 mListView;
    private OnGetCommentListListener mListener;
    private SmartRefreshLayout mRefreshLayout;
    private String moduleType;
    private String objId;
    private int page;
    private int positionTab;
    private final List<CommentBean.DataBean.HotBean> timeLineList;
    private String timeLineTotal;
    private TextView tvReMen;
    private TextView tvZuiXin;
    private String videoType;

    /* renamed from: com.psychiatrygarden.widget.VideoCommentLayout$4, reason: invalid class name */
    public class AnonymousClass4 extends AjaxCallBack<String> {
        public AnonymousClass4() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            VideoCommentLayout.this.getCommentList();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            ToastUtil.shortToast(VideoCommentLayout.this.getContext(), "请求服务器超时！");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass4) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (jSONObject.optString("code").equals("200")) {
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    NewToast.showShort(ProjectApp.instance(), "评论成功");
                    int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.COMMENT_COUNT, VideoCommentLayout.this.getContext(), 0);
                    int intConfig2 = SharePreferencesUtils.readIntConfig(CommonParameter.NOTE_COUNT, VideoCommentLayout.this.getContext(), 0);
                    int i2 = intConfig + 1;
                    SharePreferencesUtils.writeIntConfig(CommonParameter.COMMENT_COUNT, i2, VideoCommentLayout.this.getContext());
                    EventBus.getDefault().post(new UpdateVideoCommentNote(intConfig2, i2));
                    VideoCommentLayout.this.page = 1;
                    new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.dj
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f16414c.lambda$onSuccess$0();
                        }
                    }, 500L);
                } else {
                    ToastUtil.shortToast(VideoCommentLayout.this.getContext(), jSONObject.optString("message"));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public interface CommentInfoListener {
        void commentChange(int commentCount);
    }

    public interface HotNewChangeListener {
        void onChange(boolean isNew, boolean isHot);

        void showHotNew(boolean show);
    }

    public interface OnGetCommentListListener {
        void onFinish(int totalCommentCount);
    }

    public VideoCommentLayout(Context context) {
        this(context, null);
    }

    public static /* synthetic */ int access$404(VideoCommentLayout videoCommentLayout) {
        int i2 = videoCommentLayout.page + 1;
        videoCommentLayout.page = i2;
        return i2;
    }

    public static /* synthetic */ int access$410(VideoCommentLayout videoCommentLayout) {
        int i2 = videoCommentLayout.page;
        videoCommentLayout.page = i2 - 1;
        return i2;
    }

    private void getAdConfig() {
        AjaxParams ajaxParams = new AjaxParams();
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getContext());
        if (!TextUtils.isEmpty(strConfig)) {
            ajaxParams.put("app_id", strConfig);
        }
        YJYHttpUtils.post(getContext(), NetworkRequestsURL.QUESTION_AD_CONFIG, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.VideoCommentLayout.1
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
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (TextUtils.equals("200", jSONObject.optString("code", "0"))) {
                        JSONArray jSONArray = jSONObject.getJSONArray("data");
                        if (jSONArray.length() > 0) {
                            int length = jSONArray.length();
                            for (int i2 = 0; i2 < length; i2++) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                                if (CommonParameter.AD_CONFIG.equals(jSONObject2.optString("key", ""))) {
                                    SharePreferencesUtils.writeStrConfig(CommonParameter.AD_CONFIG, jSONObject2.optString("value", ""), VideoCommentLayout.this.getContext());
                                    VideoCommentLayout.this.mIsSdkAd = false;
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

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentAd() {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getContext());
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getContext()));
        if (!TextUtils.isEmpty(strConfig)) {
            ajaxParams.put("app_id", strConfig);
        }
        YJYHttpUtils.get(getContext(), NetworkRequestsURL.adsInCommentApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.VideoCommentLayout.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (t2 != null) {
                    t2.printStackTrace();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String json) {
                try {
                    JSONObject jSONObject = new JSONObject(json);
                    if (jSONObject.optString("code").equals("200")) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.length() > 0) {
                            if ((SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, VideoCommentLayout.this.getContext(), 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, VideoCommentLayout.this.getContext(), 0L).longValue()) / 1000) - jSONObjectOptJSONObject.optLong("time_interval", 0L) : 0L) < 0) {
                                VideoCommentLayout.this.mAdapter.setShowAd(false, false);
                                VideoCommentLayout.this.mAdapter.notifyDataSetChanged();
                                return;
                            }
                            if (VideoCommentLayout.this.mCommentList.size() <= 8 || TextUtils.isEmpty(jSONObjectOptJSONObject.optString("img"))) {
                                VideoCommentLayout.this.mAdapter.setShowAd(false, false);
                                VideoCommentLayout.this.mAdapter.notifyDataSetChanged();
                                return;
                            }
                            CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                            hotBean.setAds(jSONObject.optString("data"));
                            if (VideoCommentLayout.this.hasHotSize) {
                                hotBean.setHot(true);
                                VideoCommentLayout.this.mCommentList.add(9, hotBean);
                                VideoCommentLayout.this.mHasConfigAd = true;
                                VideoCommentLayout.this.mAdapter.setShowAd(VideoCommentLayout.this.mHasConfigAd, true);
                                VideoCommentLayout.this.mAdapter.setList(VideoCommentLayout.this.mCommentList);
                                VideoCommentLayout.this.mAdapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        }
                        VideoCommentLayout.this.mAdapter.setShowAd(false, false);
                        VideoCommentLayout.this.mAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCommentList() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page));
        ajaxParams.put("comment_type", "2");
        ajaxParams.put("module_type", this.moduleType);
        ajaxParams.put("obj_id", this.objId);
        ajaxParams.put("break_point", String.valueOf(System.currentTimeMillis() / 1000));
        YJYHttpUtils.post(getContext(), this.commentEnum.getUrl(), ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.VideoCommentLayout.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (VideoCommentLayout.this.page == 1) {
                    VideoCommentLayout.this.mRefreshLayout.finishRefresh(false);
                } else {
                    if (VideoCommentLayout.this.page > 1) {
                        VideoCommentLayout.access$410(VideoCommentLayout.this);
                    }
                    VideoCommentLayout.this.mRefreshLayout.finishLoadMore(false);
                }
                if (VideoCommentLayout.this.mAdapter.getCount() == 0) {
                    VideoCommentLayout.this.empty_view.setVisibility(0);
                    VideoCommentLayout.this.empty_view.showEmptyView();
                } else {
                    VideoCommentLayout.this.empty_view.setVisibility(8);
                }
                int count = VideoCommentLayout.this.mAdapter.getCount();
                if (VideoCommentLayout.this.mInfoListener != null) {
                    VideoCommentLayout.this.mInfoListener.commentChange(count);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                VideoCommentLayout.this.mRefreshLayout.finishRefresh();
                VideoCommentLayout.this.mRefreshLayout.finishLoadMore();
                try {
                    CommentBean commentBean = (CommentBean) new Gson().fromJson(s2, CommentBean.class);
                    boolean z2 = true;
                    if ("200".equals(commentBean.getCode())) {
                        CommentBean.DataBean data = commentBean.getData();
                        VideoCommentLayout.this.hotTotal = data.getHot_total();
                        VideoCommentLayout.this.timeLineTotal = data.getTime_line_total();
                        List<CommentBean.DataBean.HotBean> time_line = data.getTime_line();
                        List<CommentBean.DataBean.HotBean> hot = data.getHot();
                        if (VideoCommentLayout.this.page == 1) {
                            VideoCommentLayout.this.hasHotSize = hot != null && hot.size() >= 8;
                            VideoCommentLayout.this.mCommentList.clear();
                            if (data.getHot().isEmpty()) {
                                VideoCommentLayout.this.llHotNew.setVisibility(8);
                            } else {
                                CommentBean.DataBean.HotBean hotBean = new CommentBean.DataBean.HotBean();
                                hotBean.setType(1);
                                hotBean.setName(String.format("最热评论(%s)", VideoCommentLayout.this.hotTotal));
                                hotBean.setTypeName(String.format("最热评论(%s)", VideoCommentLayout.this.hotTotal));
                                VideoCommentLayout.this.mCommentList.add(hotBean);
                                for (int i2 = 0; i2 < commentBean.getData().getHot().size(); i2++) {
                                    data.getHot().get(i2).setTypeName("最热评论");
                                }
                                VideoCommentLayout.this.mCommentList.addAll(data.getHot());
                                if (!VideoCommentLayout.this.landScape) {
                                    VideoCommentLayout.this.llHotNew.setVisibility(0);
                                }
                                if (VideoCommentLayout.this.mChangeListener != null) {
                                    VideoCommentLayout.this.mChangeListener.showHotNew(true);
                                }
                                VideoCommentLayout.this.hasHot = true;
                                hot = data.getHot();
                                VideoCommentLayout.this.positionTab = hot.size();
                            }
                            if (time_line != null && !time_line.isEmpty()) {
                                CommentBean.DataBean.HotBean hotBean2 = new CommentBean.DataBean.HotBean();
                                hotBean2.setType(1);
                                hotBean2.setName(String.format("最新评论(%s)", VideoCommentLayout.this.timeLineTotal));
                                VideoCommentLayout.this.mCommentList.add(hotBean2);
                                VideoCommentLayout.this.mCommentList.addAll(time_line);
                                VideoCommentLayout.this.timeLineList.addAll(time_line);
                            }
                            VideoCommentLayout.this.mAdapter.setShowAd(VideoCommentLayout.this.mHasConfigAd && hot != null && hot.size() >= 8, !VideoCommentLayout.this.mIsSdkAd);
                            if (hot != null && hot.size() > 0) {
                                VideoCommentLayout.this.isSelect(true, false);
                            }
                            VideoCommentLayout.this.mAdapter.setList(VideoCommentLayout.this.mCommentList);
                            VideoCommentLayout.this.mAdapter.notifyDataSetChanged();
                            if (VideoCommentLayout.this.hotTotal != null && VideoCommentLayout.this.timeLineTotal != null) {
                                int i3 = VideoCommentLayout.this.timeLineTotal.matches(RegexPool.NUMBERS) ? Integer.parseInt(VideoCommentLayout.this.timeLineTotal) + 0 : 0;
                                SharePreferencesUtils.writeIntConfig(CommonParameter.COMMENT_COUNT, i3, VideoCommentLayout.this.getContext());
                                EventBus.getDefault().post(new UpdateVideoCommentNote(SharePreferencesUtils.readIntConfig(CommonParameter.NOTE_COUNT, VideoCommentLayout.this.getContext(), 0), i3));
                            }
                            if (VideoCommentLayout.this.mListener != null) {
                                VideoCommentLayout.this.mListener.onFinish(SharePreferencesUtils.readIntConfig(CommonParameter.COMMENT_COUNT, VideoCommentLayout.this.getContext(), 0));
                            }
                            if (VideoCommentLayout.this.isRefresh) {
                                VideoCommentLayout.this.isRefresh = false;
                                VideoCommentLayout.this.doSectionClick(1);
                            }
                            if (hot != null && hot.size() >= 8 && !VideoCommentLayout.this.mIsSdkAd) {
                                VideoCommentLayout.this.getCommentAd();
                            }
                        } else if (time_line == null || time_line.isEmpty()) {
                            VideoCommentLayout.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                        } else {
                            VideoCommentLayout.this.mCommentList.addAll(time_line);
                            VideoCommentLayout.this.timeLineList.addAll(time_line);
                            VideoCommentLayout.this.mAdapter.notifyDataSetChanged();
                        }
                        if (VideoCommentLayout.this.isLoading) {
                            VideoCommentLayout.this.isLoading = false;
                            VideoCommentLayout videoCommentLayout = VideoCommentLayout.this;
                            if ("1".equals(commentBean.getData().getMore())) {
                                z2 = false;
                            }
                            videoCommentLayout.isLastPage = z2;
                        }
                    } else {
                        if (VideoCommentLayout.this.page > 1) {
                            VideoCommentLayout.access$410(VideoCommentLayout.this);
                        }
                        String message = commentBean.getMessage();
                        if (!TextUtils.isEmpty(message)) {
                            ToastUtil.shortToast(VideoCommentLayout.this.getContext(), message);
                        }
                    }
                    if (VideoCommentLayout.this.mAdapter.getCount() == 0) {
                        VideoCommentLayout.this.empty_view.setVisibility(0);
                        VideoCommentLayout.this.empty_view.showEmptyView();
                    } else {
                        VideoCommentLayout.this.empty_view.setVisibility(8);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (VideoCommentLayout.this.mAdapter.getCount() == 0) {
                        VideoCommentLayout.this.empty_view.setVisibility(0);
                        VideoCommentLayout.this.empty_view.showEmptyView();
                    } else {
                        VideoCommentLayout.this.empty_view.setVisibility(8);
                    }
                }
                int count = VideoCommentLayout.this.mAdapter.getCount();
                if (VideoCommentLayout.this.mInfoListener != null) {
                    VideoCommentLayout.this.mInfoListener.commentChange(count);
                }
            }
        });
    }

    private boolean isNight() {
        return 1 == SkinManager.getCurrentSkinType(getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isSelect(boolean hotTrue, boolean latestTrue) {
        HotNewChangeListener hotNewChangeListener = this.mChangeListener;
        if (hotNewChangeListener != null) {
            hotNewChangeListener.onChange(latestTrue, hotTrue);
        } else {
            if (hotTrue) {
                this.tvReMen.setTypeface(Typeface.defaultFromStyle(1));
                this.tvReMen.setTextColor(isNight() ? getContext().getColor(R.color.first_txt_color_night) : getContext().getColor(R.color.first_txt_color));
                this.tvReMen.setBackground(getContext().getDrawable(R.drawable.shape_remne_bg));
            } else {
                this.tvReMen.setTypeface(Typeface.defaultFromStyle(0));
                this.tvReMen.setTextColor(isNight() ? getContext().getColor(R.color.forth_txt_color_night) : getContext().getColor(R.color.forth_txt_color));
                this.tvReMen.setBackground(getContext().getDrawable(R.drawable.shape_transparent_bg));
            }
            if (latestTrue) {
                this.tvZuiXin.setTypeface(Typeface.defaultFromStyle(1));
                this.tvZuiXin.setTextColor(isNight() ? getContext().getColor(R.color.first_txt_color_night) : getContext().getColor(R.color.first_txt_color));
                this.tvZuiXin.setBackground(getContext().getDrawable(R.drawable.shape_remne_bg));
            } else {
                this.tvZuiXin.setTypeface(Typeface.defaultFromStyle(0));
                this.tvZuiXin.setTextColor(isNight() ? getContext().getColor(R.color.forth_txt_color_night) : getContext().getColor(R.color.forth_txt_color));
                this.tvZuiXin.setBackground(getContext().getDrawable(R.drawable.shape_transparent_bg));
            }
        }
        LogUtils.d("isSelect", "是最新评论 " + latestTrue + " 是最热评论 " + hotTrue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doSectionClick$0(int i2) {
        this.mListView.setSelection(i2);
    }

    @Override // com.psychiatrygarden.adapter.CircleInfoAdapter.CircleInfoClickIml
    public void doSectionClick(int type) {
        final int i2 = 0;
        if (type == 0) {
            this.mListView.setSelection(0);
            return;
        }
        int i3 = 0;
        while (true) {
            if (i3 >= this.mCommentList.size()) {
                break;
            }
            CommentBean.DataBean.HotBean hotBean = this.mCommentList.get(i3);
            if (hotBean.getType() == 1 && !hotBean.isHot() && hotBean.getName().contains("最新")) {
                i2 = i3;
                break;
            }
            i3++;
        }
        this.mListView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.cj
            @Override // java.lang.Runnable
            public final void run() {
                this.f16383c.lambda$doSectionClick$0(i2);
            }
        }, 100L);
    }

    public void initParams(String objId, String moduleType, DiscussStatus discussStatus, boolean newVideo) {
        this.objId = objId;
        this.commentEnum = discussStatus;
        this.moduleType = moduleType;
        this.page = 1;
        if (newVideo && this.initView) {
            this.mRefreshLayout.autoRefresh();
        }
    }

    public void initView() {
        if (this.initView) {
            return;
        }
        View.inflate(getContext(), R.layout.layout_video_comment, this);
        int color = getContext().getColor(R.color.new_bg_one_color);
        new ColorDrawable(getContext().getColor(R.color.transparent));
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(SizeUtil.dp2px(getContext(), 100));
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(color);
        gradientDrawable.setStroke(SizeUtil.dp2px(getContext(), 1), color);
        this.tvReMen = (TextView) findViewById(R.id.tvReMen);
        this.tvZuiXin = (TextView) findViewById(R.id.tvZuiXin);
        this.tvReMen.setOnClickListener(this);
        this.tvZuiXin.setOnClickListener(this);
        this.empty_view = (CustomEmptyView) findViewById(R.id.empty_view);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lineselect2);
        this.llHotNew = linearLayout;
        if (this.landScape) {
            linearLayout.setBackground(getContext().getDrawable(R.drawable.shape_hot_video_bg));
            CheckedTextView checkedTextView = (CheckedTextView) findViewById(R.id.remen2);
            CheckedTextView checkedTextView2 = (CheckedTextView) findViewById(R.id.zuixin2);
            checkedTextView.setBackground(getContext().getDrawable(R.drawable.hot_video_select_bg));
            checkedTextView2.setBackground(getContext().getDrawable(R.drawable.hot_video_select_bg));
            checkedTextView.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.text_new2_color));
            checkedTextView2.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.text_new2_color));
            this.empty_view.findViewById(R.id.ly_empty_view).setBackground(new ColorDrawable(Color.parseColor("#141414")));
        }
        findViewById(R.id.remen2).setOnClickListener(this);
        findViewById(R.id.zuixin2).setOnClickListener(this);
        this.mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        PinnedSectionListView1 pinnedSectionListView1 = (PinnedSectionListView1) findViewById(R.id.listView);
        this.mListView = pinnedSectionListView1;
        pinnedSectionListView1.setIsPlanCanvas(0);
        this.mRefreshLayout.setOnRefreshLoadMoreListener(this);
        ClassicsFooter classicsFooter = (ClassicsFooter) findViewById(R.id.footer);
        ClassicsHeader classicsHeader = (ClassicsHeader) findViewById(R.id.header);
        if (this.landScape) {
            classicsFooter.setAccentColor(Color.parseColor("#909499"));
            classicsHeader.setAccentColor(Color.parseColor("#909499"));
        }
        CircleInfoAdapter circleInfoAdapter = new CircleInfoAdapter(getContext(), this.mCommentList, this.timeLineList, Integer.parseInt(this.moduleType), "2", this.objId, (BaseActivity) getContext(), "", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getContext()), this);
        this.mAdapter = circleInfoAdapter;
        circleInfoAdapter.setLandScape(this.landScape);
        this.mAdapter.setFromVideo(true);
        this.mAdapter.setCourseId(this.courseId);
        View view = this.anchorView;
        if (view != null && this.landScape) {
            this.mAdapter.setAnchorView(view);
        }
        this.initView = true;
        this.isLoading = true;
        this.mListView.setAdapter((ListAdapter) this.mAdapter);
        this.mListView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.widget.VideoCommentLayout.2
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view2, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (VideoCommentLayout.this.mCommentList.size() - view2.getLastVisiblePosition() <= 3 && !VideoCommentLayout.this.isLoading && !VideoCommentLayout.this.isLastPage) {
                    VideoCommentLayout.this.isLoading = true;
                    VideoCommentLayout.access$404(VideoCommentLayout.this);
                    VideoCommentLayout.this.getCommentList();
                }
                if (VideoCommentLayout.this.hasHot) {
                    if (firstVisibleItem > VideoCommentLayout.this.positionTab) {
                        VideoCommentLayout.this.isSelect(false, true);
                    } else if (firstVisibleItem < VideoCommentLayout.this.positionTab) {
                        VideoCommentLayout.this.isSelect(true, false);
                    }
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view2, int scrollState) {
            }
        });
        this.mRefreshLayout.autoRefresh();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        switch (v2.getId()) {
            case R.id.remen2 /* 2131366424 */:
                doSectionClick(0);
                isSelect(true, false);
                break;
            case R.id.tvReMen /* 2131367561 */:
                isSelect(true, false);
                doSectionClick(0);
                break;
            case R.id.tvZuiXin /* 2131367648 */:
                isSelect(false, true);
                doSectionClick(1);
                break;
            case R.id.zuixin2 /* 2131369271 */:
                doSectionClick(1);
                isSelect(false, true);
                break;
        }
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        this.page++;
        getCommentList();
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.page = 1;
        getCommentList();
    }

    public void putComment(Bundle b3) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", b3.getString("content"));
        ajaxParams.put("course_id", b3.getString("course_id"));
        ajaxParams.put("module_type", String.valueOf(b3.getInt("module_type", 8)));
        if (TextUtils.isEmpty(this.videoType)) {
            ajaxParams.put("video_type", b3.getString("video_type", "1"));
        } else {
            ajaxParams.put("video_type", this.videoType);
        }
        ajaxParams.put("comment_type", "2");
        ajaxParams.put("obj_id", this.objId);
        String string = b3.getString("b_img");
        String string2 = b3.getString("s_img");
        if (string != null) {
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
        if (!TextUtils.isEmpty(SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getContext()))) {
            ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getContext()));
        }
        YJYHttpUtils.post(getContext(), NetworkRequestsURL.mPutComment, ajaxParams, new AnonymousClass4());
    }

    public void putVideoComment(Bundle b3) {
        this.mAdapter.getputData(b3);
    }

    public void refresh() {
        if (this.initView) {
            this.page = 1;
            this.isRefresh = true;
            getCommentList();
        }
    }

    public void setAnchorView(View v2) {
        this.anchorView = v2;
    }

    public void setCommentInfoListener(CommentInfoListener listener) {
        this.mInfoListener = listener;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setHotNewChangeListener(HotNewChangeListener l2) {
        this.mChangeListener = l2;
    }

    public void setLandScape(boolean landScape) {
        this.landScape = landScape;
    }

    public void setOnGetCommentListListener(OnGetCommentListListener l2) {
        this.mListener = l2;
    }

    public VideoCommentLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoCommentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.page = 1;
        this.mHasConfigAd = false;
        this.mCommentList = new ArrayList();
        this.timeLineList = new ArrayList();
        this.isRefresh = false;
        getAdConfig();
    }

    public void initParams(String objId, String moduleType, DiscussStatus discussStatus, boolean newVideo, String videoType) {
        this.objId = objId;
        this.commentEnum = discussStatus;
        this.moduleType = moduleType;
        this.videoType = videoType;
        this.page = 1;
        if (newVideo && this.initView) {
            this.mRefreshLayout.autoRefresh();
        }
    }
}
