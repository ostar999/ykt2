package com.psychiatrygarden.forum;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import cn.hutool.core.text.StrPool;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.activity.circleactivity.CircleInfoActivity;
import com.psychiatrygarden.activity.material.InformationPreviewAct;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.CircleSearchValueAdp;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.bean.ForumSearchBean;
import com.psychiatrygarden.bean.ForumTopBean;
import com.psychiatrygarden.bean.ForumTopCategoryBean;
import com.psychiatrygarden.bean.HotSearchRankTabItem;
import com.psychiatrygarden.bean.TarGetParamBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.forum.ForumRankingAdp;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.utils.URLImageParser;
import com.psychiatrygarden.widget.ChooseExpDefaultFilterPopuWindow;
import com.psychiatrygarden.widget.ChooseExperienceFilterPopuWindow;
import com.psychiatrygarden.widget.ChooseFilterPopuWindow;
import com.psychiatrygarden.widget.ChooseProjectPopuWindow;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.psychiatrygarden.widget.HistoryLabelsView;
import com.psychiatrygarden.widget.LabelsView;
import com.psychiatrygarden.widget.TagLabelsView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yikaobang.yixue.R;
import com.ykb.ebook.activity.ReadBookActivity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import okhttp3.HttpUrl;
import org.apache.http.cookie.ClientCookie;
import org.eclipse.jetty.servlet.ServletHandler;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ForumSearchAct extends BaseActivity implements OnRefreshLoadMoreListener {
    private CustomEmptyView emptyView;
    private boolean isEditSearch;
    private boolean isExpandHistory;
    private LinearLayout llHotSearch;
    private ImageView mBtnBack;
    private ImageView mBtnDelHistory;
    private TextView mBtnSearch;
    private ForumSearchContentAdp mContentAdp;
    private ClearEditText mEtContent;
    private int mHistoryCollapseHeight;
    private int mHistoryExpandHeight;
    private ImageView mImgMoreArrow;
    private HistoryLabelsView mLbDelHistoryView;
    private LabelsView mLbHistoryView;
    private TagLabelsView mLbHotWordView;
    private LinearLayout mLyHistoryView;
    private LinearLayout mLyHotWord;
    private LinearLayout mLyResultView;
    private LinearLayout mLyShowDelAll;
    private LinearLayout mLyShowMore;
    private RelativeLayout mLyView;
    private TarGetParamBean mParams;
    private ForumRankingAdp mRankAdp;
    private RecyclerView mRankRecycler;
    private RecyclerView mResultRecycler;
    private SmartRefreshLayout mResultRefresh;
    private HotSearchRankTabAdapter mSearchRankTabAdapter;
    private CircleSearchValueAdp mSearchValueAdp;
    private TextView mTvDelAll;
    private TextView mTvFinish;
    private TextView mTvMoreHistory;
    private SearchTypeAdp mTypeAdapter;
    private RecyclerView mTypeRecycler;
    private String mWord;
    private RecyclerView mWordLinkRecyclerView;
    private RecyclerView rvHotSearchRankTab;
    private ViewPager2 viewPager2HotSearchRank;
    private List<String> historyList = new ArrayList();
    private int searchPage = 1;
    private int page = 1;
    private boolean isClickSearchKey = false;
    private String mType = "bbs";
    private String mSearchAppId = "";
    private String mExamYear = "";
    private String mUcid = "";
    private String mPcid = "";
    private String mMcid = "";
    private String mRanking = "";
    private String mTimeRange = "";
    private String mTimeSort = "";
    private String mTimeSortExp = "";
    private String mAchievement = "";
    private String mFileType = "";
    private String mInfoType = "";
    private List<String> mFilterKey = new ArrayList();
    private int mProjectParentPos = 0;
    private int mHistoryMoreHeight = 0;
    private int mHistoryCollHeight = 0;
    private int isFromSerarchType = 0;
    private int height = 0;
    private final ViewPager2.OnPageChangeCallback mPageChangeCallback = new ViewPager2.OnPageChangeCallback() { // from class: com.psychiatrygarden.forum.ForumSearchAct.3
        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            List<HotSearchRankTabItem> data = ForumSearchAct.this.mSearchRankTabAdapter.getData();
            if (data.size() != ForumSearchAct.this.viewPager2HotSearchRank.getAdapter().getItemCount()) {
                return;
            }
            int i2 = 0;
            while (i2 < data.size()) {
                data.get(i2).setSelect(i2 == position);
                i2++;
            }
            ForumSearchAct.this.mSearchRankTabAdapter.notifyDataSetChanged();
            ForumSearchAct.this.rvHotSearchRankTab.scrollToPosition(position);
        }
    };

    /* renamed from: com.psychiatrygarden.forum.ForumSearchAct$7, reason: invalid class name */
    public class AnonymousClass7 extends ForumRankingAdp.OnItemActionLisenter {
        public AnonymousClass7() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setItemClickAction$0() {
            ForumSearchAct.this.mEtContent.clearFocus();
        }

        @Override // com.psychiatrygarden.forum.ForumRankingAdp.OnItemActionLisenter
        public void setItemClickAction(final int position, final ForumFilterBean.FilterDataBean item, final ImageView arrowImg) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            ForumSearchAct.this.hideInputMethod();
            ForumSearchAct.this.mEtContent.post(new Runnable() { // from class: com.psychiatrygarden.forum.g0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f15352c.lambda$setItemClickAction$0();
                }
            });
            ForumSearchAct.this.showOrHiddenArrow(true, arrowImg);
            int measuredHeight = ForumSearchAct.this.mResultRefresh.getMeasuredHeight();
            if (measuredHeight < ForumSearchAct.this.height) {
                measuredHeight = ForumSearchAct.this.height;
            } else {
                ForumSearchAct.this.height = measuredHeight;
            }
            if (TextUtils.isEmpty(item.getType())) {
                item.setType("other");
            }
            if (item.getType().equals("project")) {
                ForumSearchAct forumSearchAct = ForumSearchAct.this;
                new ChooseProjectPopuWindow(forumSearchAct, forumSearchAct.mResultRefresh, ForumSearchAct.this.mRankRecycler, measuredHeight, item.getList(), ForumSearchAct.this.mProjectParentPos, new ChooseProjectPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.forum.ForumSearchAct.7.1
                    @Override // com.psychiatrygarden.widget.ChooseProjectPopuWindow.ProjectChoosedInterface
                    public void mItemDissmissLinsenter() {
                        ForumSearchAct.this.showOrHiddenArrow(false, arrowImg);
                    }

                    @Override // com.psychiatrygarden.widget.ChooseProjectPopuWindow.ProjectChoosedInterface
                    public void mItemLinsenter(int parentPos, int choosePos, ForumFilterBean.FilterDataBean type) {
                        Log.e("choose_filter", "parPos=>" + parentPos + ";childPos==>" + choosePos);
                        item.setTitle(type.getTitle());
                        ForumSearchAct.this.mSearchAppId = type.getApp_id();
                        int i2 = 0;
                        while (i2 < item.getList().size()) {
                            item.getList().get(i2).setSelected(i2 == parentPos);
                            if (i2 == parentPos) {
                                item.getList().get(i2).setSelected(true);
                                int i3 = 0;
                                while (i3 < item.getList().get(i2).getChildren().size()) {
                                    item.getList().get(i2).getChildren().get(i3).setSelected(i3 == choosePos);
                                    i3++;
                                }
                            } else {
                                item.getList().get(i2).setSelected(false);
                            }
                            i2++;
                        }
                        ForumSearchAct.this.mProjectParentPos = parentPos;
                        ForumSearchAct.this.mRankAdp.setmChoosedPos(position);
                        ForumSearchAct.this.mRankAdp.notifyDataSetChanged();
                        ForumSearchAct.this.mResultRecycler.smoothScrollToPosition(0);
                        ForumSearchAct.this.mResultRefresh.autoRefresh();
                    }
                });
            } else if (item.getType().equals("exp")) {
                ForumSearchAct forumSearchAct2 = ForumSearchAct.this;
                new ChooseExperienceFilterPopuWindow(forumSearchAct2, forumSearchAct2.mResultRefresh, ForumSearchAct.this.mRankRecycler, measuredHeight, item.getList(), ForumSearchAct.this.mFilterKey, new ChooseExperienceFilterPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.forum.ForumSearchAct.7.2
                    @Override // com.psychiatrygarden.widget.ChooseExperienceFilterPopuWindow.ProjectChoosedInterface
                    public void mItemDissmissLinsenter() {
                        ForumSearchAct.this.showOrHiddenArrow(false, arrowImg);
                    }

                    @Override // com.psychiatrygarden.widget.ChooseExperienceFilterPopuWindow.ProjectChoosedInterface
                    public void mItemLinsenter(List<ForumFilterBean.FilterDataBean> list) {
                        ForumSearchAct.this.showOrHiddenArrow(false, arrowImg);
                        ForumSearchAct.this.mFilterKey.clear();
                        ForumSearchAct.this.mSearchAppId = "";
                        ForumSearchAct.this.mExamYear = "";
                        ForumSearchAct.this.mUcid = "";
                        ForumSearchAct.this.mPcid = "";
                        ForumSearchAct.this.mMcid = "";
                        for (int i2 = 0; i2 < list.size(); i2++) {
                            String type = list.get(i2).getType();
                            String key = list.get(i2).getKey();
                            String str = list.get(i2).getmTempKey();
                            if (type.equals("search_app_id")) {
                                ForumSearchAct.this.mSearchAppId = key;
                            } else if (type.equals("test_time")) {
                                ForumSearchAct.this.mExamYear = key;
                            } else if (type.equals("u_cid")) {
                                ForumSearchAct.this.mUcid = key;
                            } else if (type.equals("p_cid")) {
                                ForumSearchAct.this.mPcid = key;
                            } else if (type.equals("m_cid")) {
                                ForumSearchAct.this.mMcid = key;
                            }
                            ForumSearchAct.this.mFilterKey.add(str);
                        }
                        if (list.isEmpty()) {
                            ForumSearchAct.this.mFilterKey.clear();
                            ForumSearchAct.this.mRankAdp.setmChoosedPos(-1);
                        } else {
                            ForumSearchAct.this.mRankAdp.setmChoosedPos(position);
                        }
                        ForumSearchAct.this.mRankAdp.notifyDataSetChanged();
                        ForumSearchAct.this.mResultRecycler.smoothScrollToPosition(0);
                        ForumSearchAct.this.mResultRefresh.autoRefresh();
                    }
                });
            } else if (item.getType().equals(ServletHandler.__DEFAULT_SERVLET)) {
                ForumSearchAct forumSearchAct3 = ForumSearchAct.this;
                new ChooseExpDefaultFilterPopuWindow(forumSearchAct3, forumSearchAct3.mResultRefresh, ForumSearchAct.this.mRankRecycler, measuredHeight, item.getList(), new ChooseExpDefaultFilterPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.forum.ForumSearchAct.7.3
                    @Override // com.psychiatrygarden.widget.ChooseExpDefaultFilterPopuWindow.ProjectChoosedInterface
                    public void mItemDissmissLinsenter() {
                        ForumSearchAct.this.showOrHiddenArrow(false, arrowImg);
                    }

                    @Override // com.psychiatrygarden.widget.ChooseExpDefaultFilterPopuWindow.ProjectChoosedInterface
                    public void mItemLinsenter(List<ForumFilterBean.FilterDataBean> list) {
                        ForumSearchAct.this.showOrHiddenArrow(false, arrowImg);
                        if (list.size() > 0) {
                            item.setTitle(list.get(0).getTitle());
                        }
                        for (int i2 = 0; i2 < list.size(); i2++) {
                            String type = list.get(i2).getType();
                            String key = list.get(i2).getKey();
                            if (type.equals("ranking")) {
                                ForumSearchAct.this.mRanking = key;
                                ForumSearchAct.this.mAchievement = "";
                                ForumSearchAct.this.mTimeSortExp = "";
                            } else if (type.equals("score_type")) {
                                ForumSearchAct.this.mAchievement = key;
                                ForumSearchAct.this.mRanking = "";
                                ForumSearchAct.this.mTimeSortExp = "";
                            } else if (type.equals("time_sort")) {
                                ForumSearchAct.this.mTimeSortExp = key;
                                ForumSearchAct.this.mRanking = "";
                                ForumSearchAct.this.mAchievement = "";
                            }
                        }
                        if (list.isEmpty()) {
                            ForumSearchAct.this.mAchievement = "";
                            ForumSearchAct.this.mRanking = "";
                            ForumSearchAct.this.mTimeSortExp = "";
                        }
                        ForumSearchAct.this.mRankAdp.setmChoosedPos(position);
                        ForumSearchAct.this.mRankAdp.notifyDataSetChanged();
                        ForumSearchAct.this.mResultRecycler.smoothScrollToPosition(0);
                        ForumSearchAct.this.mResultRefresh.autoRefresh();
                    }
                });
            } else {
                ForumSearchAct forumSearchAct4 = ForumSearchAct.this;
                new ChooseFilterPopuWindow(forumSearchAct4, forumSearchAct4.mRankRecycler, measuredHeight, item.getList(), new ChooseFilterPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.forum.ForumSearchAct.7.4
                    @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
                    public void mItemDissmissLinsenter() {
                        ForumSearchAct.this.showOrHiddenArrow(false, arrowImg);
                    }

                    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Removed duplicated region for block: B:12:0x003f  */
                    /* JADX WARN: Type inference failed for: r3v0 */
                    /* JADX WARN: Type inference failed for: r3v1 */
                    /* JADX WARN: Type inference failed for: r3v2 */
                    /* JADX WARN: Type inference failed for: r3v3 */
                    /* JADX WARN: Type inference failed for: r3v4 */
                    /* JADX WARN: Type inference failed for: r3v5 */
                    /* JADX WARN: Type inference failed for: r3v6 */
                    /* JADX WARN: Type inference failed for: r3v7 */
                    @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void mItemLinsenter(int r5, com.psychiatrygarden.bean.ForumFilterBean.FilterDataBean r6) {
                        /*
                            Method dump skipped, instructions count: 302
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.forum.ForumSearchAct.AnonymousClass7.AnonymousClass4.mItemLinsenter(int, com.psychiatrygarden.bean.ForumFilterBean$FilterDataBean):void");
                    }
                }, false);
            }
        }
    }

    public static /* synthetic */ int access$810(ForumSearchAct forumSearchAct) {
        int i2 = forumSearchAct.searchPage;
        forumSearchAct.searchPage = i2 - 1;
        return i2;
    }

    private void addHistoryContent(String content) {
        this.historyList.remove(content);
        this.historyList.add(0, content);
        if (this.historyList.size() > 20) {
            this.historyList = this.historyList.subList(0, 20);
        }
        SharePreferencesUtils.writeStrConfig(CommonParameter.getSaveSearchHistoryData(), new Gson().toJson(this.historyList), this);
        initHistory();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSearchValueByKeywords(final String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, key);
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.searchPage);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.wordBySearchKey, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.ForumSearchAct.11
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (ForumSearchAct.this.searchPage > 1) {
                    ForumSearchAct.access$810(ForumSearchAct.this);
                    ForumSearchAct.this.mSearchValueAdp.getLoadMoreModule().loadMoreFail();
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass11) s2);
                try {
                    ForumSearchAct.this.mWordLinkRecyclerView.setVisibility(0);
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<ForumTopBean.KeyWordsData>>() { // from class: com.psychiatrygarden.forum.ForumSearchAct.11.1
                        }.getType());
                        if (list == null || list.size() <= 0) {
                            if (ForumSearchAct.this.searchPage > 1) {
                                ForumSearchAct.access$810(ForumSearchAct.this);
                                ForumSearchAct.this.mSearchValueAdp.getLoadMoreModule().loadMoreEnd();
                            }
                        } else if (ForumSearchAct.this.searchPage == 1) {
                            ForumSearchAct.this.mSearchValueAdp.setSearchContent(key);
                            ForumSearchAct.this.mSearchValueAdp.setList(list);
                            ForumSearchAct.this.mSearchValueAdp.getLoadMoreModule().checkDisableLoadMoreIfNotFullPage();
                        } else {
                            ForumSearchAct.this.mSearchValueAdp.addData((Collection) list);
                            ForumSearchAct.this.mSearchValueAdp.getLoadMoreModule().loadMoreComplete();
                        }
                    } else {
                        ForumSearchAct.this.AlertToast(jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (ForumSearchAct.this.searchPage > 1) {
                        ForumSearchAct.access$810(ForumSearchAct.this);
                        ForumSearchAct.this.mSearchValueAdp.getLoadMoreModule().loadMoreFail();
                    }
                }
            }
        });
    }

    private void getTopCategoryData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.forumSearchCategoryData, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.ForumSearchAct.8
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumSearchAct.this.mLyHotWord.setVisibility(8);
                ForumSearchAct.this.llHotSearch.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass8) s2);
                try {
                    ForumTopCategoryBean forumTopCategoryBean = (ForumTopCategoryBean) new Gson().fromJson(s2, ForumTopCategoryBean.class);
                    if (forumTopCategoryBean.getCode().equals("200")) {
                        int i2 = 0;
                        if (ForumSearchAct.this.isFromSerarchType == 1) {
                            while (i2 < forumTopCategoryBean.getData().size()) {
                                if (forumTopCategoryBean.getData().get(i2).getCode().equals("exp")) {
                                    ForumSearchAct.this.mTypeAdapter.setSelectPos(i2);
                                    ForumSearchAct.this.mType = forumTopCategoryBean.getData().get(i2).getCode();
                                }
                                i2++;
                            }
                        } else if (ForumSearchAct.this.isFromSerarchType == 2) {
                            while (i2 < forumTopCategoryBean.getData().size()) {
                                if (forumTopCategoryBean.getData().get(i2).getCode().equals("file")) {
                                    ForumSearchAct.this.mTypeAdapter.setSelectPos(i2);
                                    ForumSearchAct.this.mType = forumTopCategoryBean.getData().get(i2).getCode();
                                }
                                i2++;
                            }
                        } else if (ForumSearchAct.this.isFromSerarchType == 3) {
                            while (i2 < forumTopCategoryBean.getData().size()) {
                                if (forumTopCategoryBean.getData().get(i2).getCode().equals("ebook")) {
                                    ForumSearchAct.this.mTypeAdapter.setSelectPos(i2);
                                    ForumSearchAct.this.mType = forumTopCategoryBean.getData().get(i2).getCode();
                                }
                                i2++;
                            }
                        } else {
                            ForumSearchAct.this.mType = forumTopCategoryBean.getData().get(0).getCode();
                        }
                        ForumSearchAct.this.mTypeAdapter.setNewData(forumTopCategoryBean.getData());
                        ForumSearchAct forumSearchAct = ForumSearchAct.this;
                        forumSearchAct.getTopCategoryFilterData(forumSearchAct.mType);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getTopCategoryFilterData(String code) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("code", code);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.forumSearchCategoryFilterData, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.ForumSearchAct.9
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumSearchAct.this.mLyHotWord.setVisibility(8);
                ForumSearchAct.this.llHotSearch.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass9) s2);
                try {
                    ForumFilterBean forumFilterBean = (ForumFilterBean) new Gson().fromJson(s2, ForumFilterBean.class);
                    if (forumFilterBean.getCode().equals("200")) {
                        ForumSearchAct.this.mRankAdp.setmChoosedPos(-1);
                        for (int i2 = 0; i2 < forumFilterBean.getData().size(); i2++) {
                            if (!TextUtils.equals("exp", forumFilterBean.getData().get(i2).getType()) || !TextUtils.equals("project", forumFilterBean.getData().get(i2).getType())) {
                                if (TextUtils.equals(ServletHandler.__DEFAULT_SERVLET, forumFilterBean.getData().get(i2).getType())) {
                                    if (forumFilterBean.getData().get(i2).getList() != null && !forumFilterBean.getData().get(i2).getList().isEmpty() && forumFilterBean.getData().get(i2).getList().get(0).getList() != null && !forumFilterBean.getData().get(i2).getList().get(0).getList().isEmpty()) {
                                        forumFilterBean.getData().get(i2).getList().get(0).getList().get(0).setSelected(true);
                                    }
                                } else if (forumFilterBean.getData().get(i2).getList() != null && !forumFilterBean.getData().get(i2).getList().isEmpty()) {
                                    forumFilterBean.getData().get(i2).getList().get(0).setSelected(true);
                                }
                            }
                        }
                        ForumSearchAct.this.mRankAdp.setList(forumFilterBean.getData());
                        if (ForumSearchAct.this.isFromSerarchType == 1) {
                            ForumSearchAct.this.loadSearchData("1");
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void initHistory() {
        this.isExpandHistory = false;
        this.mTvMoreHistory.setText("更多记录");
        this.mImgMoreArrow.setRotation(0.0f);
        this.mLbHistoryView.removeAllViews();
        List<String> list = (List) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.getSaveSearchHistoryData(), this, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI), new TypeToken<List<String>>() { // from class: com.psychiatrygarden.forum.ForumSearchAct.4
        }.getType());
        this.historyList = list;
        if (list.size() <= 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mLbHistoryView.getLayoutParams();
            layoutParams.height = -2;
            this.mLbHistoryView.setLayoutParams(layoutParams);
            this.mLyHistoryView.setVisibility(8);
            return;
        }
        this.mLyHistoryView.setVisibility(0);
        this.mLbHistoryView.setLabels(this.historyList, new LabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.forum.s
            @Override // com.psychiatrygarden.widget.LabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return this.f15399a.lambda$initHistory$4(textView, i2, (String) obj);
            }
        });
        this.mLbHistoryView.post(new Runnable() { // from class: com.psychiatrygarden.forum.t
            @Override // java.lang.Runnable
            public final void run() {
                this.f15402c.lambda$initHistory$6();
            }
        });
        this.mLbHistoryView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.forum.u
            @Override // com.psychiatrygarden.widget.LabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj, int i2) {
                this.f15405a.lambda$initHistory$7(textView, obj, i2);
            }
        });
        this.mLbDelHistoryView.setLabels(this.historyList, new HistoryLabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.forum.v
            @Override // com.psychiatrygarden.widget.HistoryLabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return this.f15408a.lambda$initHistory$8(textView, i2, (String) obj);
            }
        });
        this.mLbDelHistoryView.setOnLabelClickListener(new HistoryLabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.forum.w
            @Override // com.psychiatrygarden.widget.HistoryLabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj, int i2) {
                this.f15411a.lambda$initHistory$9(textView, obj, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initHotWord(final List<ForumTopBean.KeyWordsData> mHotWords) {
        this.mLbHotWordView.setLabels(mHotWords, new TagLabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.forum.d0
            @Override // com.psychiatrygarden.widget.TagLabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return ForumSearchAct.lambda$initHotWord$24(mHotWords, textView, i2, (ForumTopBean.KeyWordsData) obj);
            }
        });
        this.mLbHotWordView.setOnLabelClickListener(new TagLabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.forum.e0
            @Override // com.psychiatrygarden.widget.TagLabelsView.OnLabelClickListener
            public final void onLabelClick(LinearLayout linearLayout, Object obj, int i2) {
                this.f15336a.lambda$initHotWord$25(mHotWords, linearLayout, obj, i2);
            }
        });
        this.mLbHotWordView.post(new Runnable() { // from class: com.psychiatrygarden.forum.f0
            @Override // java.lang.Runnable
            public final void run() {
                this.f15349c.lambda$initHotWord$26();
            }
        });
    }

    private void initSearch() {
        this.mEtContent.post(new Runnable() { // from class: com.psychiatrygarden.forum.y
            @Override // java.lang.Runnable
            public final void run() {
                this.f15419c.lambda$initSearch$10();
            }
        });
        this.mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.forum.z
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f15422c.lambda$initSearch$11(textView, i2, keyEvent);
            }
        });
        this.mEtContent.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.forum.ForumSearchAct.5
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                if (s2.length() > 0) {
                    if (ForumSearchAct.this.isClickSearchKey) {
                        return;
                    }
                    ForumSearchAct.this.searchPage = 1;
                    ForumSearchAct.this.mSearchValueAdp.getData().clear();
                    ForumSearchAct.this.getSearchValueByKeywords(s2.toString());
                    return;
                }
                ForumSearchAct.this.isClickSearchKey = false;
                ForumSearchAct.this.mWordLinkRecyclerView.setVisibility(8);
                ForumSearchAct.this.mLyResultView.setVisibility(8);
                ForumSearchAct.this.llHotSearch.setVisibility(0);
                SharePreferencesUtils.writeStrConfig("searchCircleKeyWords", "", ForumSearchAct.this.mContext);
                ForumSearchAct.this.mContentAdp.getData().clear();
                ForumSearchAct.this.mContentAdp.notifyDataSetChanged();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
    }

    private void initTypeData() {
        this.mTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.forum.g
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15351a.lambda$initTypeData$3(baseQuickAdapter, view, i2);
            }
        });
    }

    private void initWordLinkAdapter() {
        CircleSearchValueAdp circleSearchValueAdp = new CircleSearchValueAdp();
        this.mSearchValueAdp = circleSearchValueAdp;
        this.mWordLinkRecyclerView.setAdapter(circleSearchValueAdp);
        this.mSearchValueAdp.setEmptyView(R.layout.view_empty_search_value);
        this.mSearchValueAdp.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.forum.a0
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(com.chad.library.adapter.base.BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15312c.lambda$initWordLinkAdapter$2(baseQuickAdapter, view, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(com.chad.library.adapter.base.BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        List<HotSearchRankTabItem> data = this.mSearchRankTabAdapter.getData();
        if (data.size() != this.viewPager2HotSearchRank.getAdapter().getItemCount()) {
            return;
        }
        int i3 = 0;
        while (i3 < data.size()) {
            data.get(i3).setSelect(i3 == i2);
            i3++;
        }
        baseQuickAdapter.notifyDataSetChanged();
        this.viewPager2HotSearchRank.setCurrentItem(i2, false);
        this.rvHotSearchRankTab.scrollToPosition(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        onRefresh(this.mResultRefresh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CharSequence lambda$initHistory$4(TextView textView, int i2, String str) {
        return this.historyList.get(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHistory$5(int i2) {
        this.mHistoryMoreHeight = this.mHistoryExpandHeight;
        if (this.mHistoryCollHeight == 0) {
            this.mHistoryCollHeight = this.mLbHistoryView.getMeasuredHeight();
        }
        this.mHistoryCollapseHeight = this.mHistoryCollHeight;
        this.mLbHistoryView.setMaxLines(i2);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mLbHistoryView.getLayoutParams();
        layoutParams.height = this.mHistoryCollapseHeight;
        this.mLbHistoryView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHistory$6() {
        int measuredHeight = this.mLbHistoryView.getMeasuredHeight();
        this.mHistoryExpandHeight = measuredHeight;
        int i2 = this.mHistoryMoreHeight;
        if (i2 > measuredHeight) {
            this.mHistoryExpandHeight = i2;
        }
        Log.e("view_height", "mHistoryExpandHeight高度====》" + this.mHistoryExpandHeight);
        final int lines = this.mLbHistoryView.getLines();
        if (this.mLbHistoryView.getLines() <= 3) {
            this.mLyShowMore.setVisibility(8);
            return;
        }
        this.mLbHistoryView.setMaxLines(3);
        this.mLyShowMore.setVisibility(0);
        this.mLbHistoryView.post(new Runnable() { // from class: com.psychiatrygarden.forum.h
            @Override // java.lang.Runnable
            public final void run() {
                this.f15354c.lambda$initHistory$5(lines);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHistory$7(TextView textView, Object obj, int i2) {
        this.isClickSearchKey = true;
        this.mLyResultView.setVisibility(0);
        this.mResultRefresh.autoRefreshAnimationOnly();
        this.mEtContent.setText(textView.getText().toString());
        ClearEditText clearEditText = this.mEtContent;
        clearEditText.setSelection(clearEditText.getText().toString().length());
        this.mLbDelHistoryView.setVisibility(8);
        this.mLyShowDelAll.setVisibility(8);
        this.mLyShowDelAll.setVisibility(8);
        this.mBtnDelHistory.setVisibility(0);
        this.page = 1;
        this.isEditSearch = false;
        getCircleData("1");
        hideInputMethod();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CharSequence lambda$initHistory$8(TextView textView, int i2, String str) {
        return this.historyList.get(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHistory$9(TextView textView, Object obj, int i2) {
        String str = (String) obj;
        this.mLbDelHistoryView.removeLabelFromDatas(str);
        this.historyList.remove(str);
        SharePreferencesUtils.writeStrConfig(CommonParameter.getSaveSearchHistoryData(), new Gson().toJson(this.historyList), this);
        if (this.historyList.size() == 0) {
            this.mLbDelHistoryView.setVisibility(8);
            this.mLyShowDelAll.setVisibility(8);
            initHistory();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence lambda$initHotWord$24(List list, TextView textView, int i2, ForumTopBean.KeyWordsData keyWordsData) {
        return ((ForumTopBean.KeyWordsData) list.get(i2)).getKeywords();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHotWord$25(List list, LinearLayout linearLayout, Object obj, int i2) {
        if (!((ForumTopBean.KeyWordsData) list.get(i2)).getJpush_type().equals("0")) {
            if (isLogin()) {
                PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(((ForumTopBean.KeyWordsData) list.get(i2)).getTarget_params()));
                return;
            }
            return;
        }
        String keywords = ((ForumTopBean.KeyWordsData) list.get(i2)).getKeywords();
        this.isClickSearchKey = true;
        this.page = 1;
        this.mEtContent.setText(keywords);
        ClearEditText clearEditText = this.mEtContent;
        clearEditText.setSelection(clearEditText.getText().toString().length());
        this.mLyResultView.setVisibility(0);
        this.mBtnDelHistory.setVisibility(0);
        this.mLbDelHistoryView.setVisibility(8);
        this.llHotSearch.setVisibility(8);
        this.mLyShowDelAll.setVisibility(8);
        this.mResultRefresh.autoRefreshAnimationOnly();
        this.isExpandHistory = false;
        this.isEditSearch = false;
        getCircleData("1");
        hideInputMethod();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initHotWord$26() {
        this.mLbHotWordView.setMaxLines(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initSearch$10() {
        this.mEtContent.clearFocus();
        this.mEtContent.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initSearch$11(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        String string = this.mEtContent.getText().toString();
        TarGetParamBean tarGetParamBean = this.mParams;
        if (tarGetParamBean == null || tarGetParamBean.getPush_type().equals("0") || !string.isEmpty()) {
            if (!TextUtils.isEmpty(this.mWord) && this.mEtContent.getText().toString().equals("")) {
                this.mEtContent.setText(this.mWord);
                this.mEtContent.setSelection(this.mWord.length());
            }
            if (!this.mEtContent.getText().toString().equals("")) {
                this.isClickSearchKey = true;
                SharePreferencesUtils.writeStrConfig("searchCircleKeyWords", this.mEtContent.getText().toString().trim(), this.mContext);
                this.mResultRefresh.autoRefreshAnimationOnly();
                this.page = 1;
                this.mLyShowDelAll.setVisibility(8);
                this.mBtnDelHistory.setVisibility(0);
                this.mLbDelHistoryView.setVisibility(8);
                this.llHotSearch.setVisibility(8);
                this.isEditSearch = true;
                getCircleData("0");
                this.mWordLinkRecyclerView.setVisibility(8);
                this.mLyResultView.setVisibility(0);
            }
        } else if (isLogin()) {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(this.mParams));
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initTypeData$3(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        ForumTopBean.TopModule item;
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.mTypeAdapter.setSelectPos(i2);
        this.mTypeAdapter.notifyDataSetChanged();
        item = this.mTypeAdapter.getItem(i2);
        this.mType = item.getCode();
        String code = item.getCode();
        code.hashCode();
        switch (code) {
            case "bbs":
            case "exp":
            case "file":
                this.mRankRecycler.setVisibility(0);
                getTopCategoryFilterData(item.getCode());
                break;
            default:
                this.mRankRecycler.setVisibility(8);
                break;
        }
        this.page = 1;
        this.mResultRefresh.autoRefreshAnimationOnly();
        this.mSearchAppId = "";
        this.mExamYear = "";
        this.mUcid = "";
        this.mPcid = "";
        this.mMcid = "";
        this.mRanking = "";
        this.mTimeRange = "";
        this.mTimeSort = "";
        this.mAchievement = "";
        this.mFileType = "";
        this.mInfoType = "";
        this.mTimeSortExp = "";
        getCircleData("1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initWordLinkAdapter$2(com.chad.library.adapter.base.BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        ForumTopBean.KeyWordsData item = this.mSearchValueAdp.getItem(i2);
        this.isClickSearchKey = true;
        this.mLyResultView.setVisibility(0);
        this.mLbDelHistoryView.setVisibility(8);
        this.mLyShowDelAll.setVisibility(8);
        this.llHotSearch.setVisibility(8);
        this.page = 1;
        this.isEditSearch = false;
        this.mEtContent.setText(item.getKeywords());
        getCircleData("1");
        hideInputMethod();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$12(View view) {
        if (this.mLyResultView.getVisibility() != 0) {
            finish();
            return;
        }
        this.mEtContent.setText("");
        this.mWordLinkRecyclerView.setVisibility(8);
        this.mLyResultView.setVisibility(8);
        this.llHotSearch.setVisibility(0);
        this.mContentAdp.getData().clear();
        this.mContentAdp.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$13(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.mEtContent.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
        String string = this.mEtContent.getText().toString();
        TarGetParamBean tarGetParamBean = this.mParams;
        if (tarGetParamBean == null || tarGetParamBean.getPush_type().equals("0") || !string.isEmpty()) {
            this.isEditSearch = true;
            loadSearchData("0");
        } else if (isLogin()) {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(this.mParams));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$14(ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mLbHistoryView.getLayoutParams();
        layoutParams.height = iIntValue;
        this.mLbHistoryView.setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$15(ValueAnimator valueAnimator) {
        this.mImgMoreArrow.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$16(View view) {
        this.isExpandHistory = !this.isExpandHistory;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(this.isExpandHistory ? new AccelerateInterpolator() : new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(300L);
        int[] iArr = new int[2];
        boolean z2 = this.isExpandHistory;
        iArr[0] = z2 ? this.mHistoryCollapseHeight : this.mHistoryExpandHeight;
        iArr[1] = !z2 ? this.mHistoryCollapseHeight : this.mHistoryExpandHeight;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(iArr);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.forum.p
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f15390c.lambda$setListenerForWidget$14(valueAnimator);
            }
        });
        int[] iArr2 = new int[2];
        boolean z3 = this.isExpandHistory;
        iArr2[0] = z3 ? 0 : 180;
        iArr2[1] = z3 ? 180 : 0;
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(iArr2);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.forum.r
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f15396c.lambda$setListenerForWidget$15(valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt, valueAnimatorOfInt2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.forum.ForumSearchAct.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ForumSearchAct.this.mTvMoreHistory.setText(ForumSearchAct.this.isExpandHistory ? "收起记录" : "更多记录");
            }
        });
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$17(View view) {
        this.mBtnDelHistory.setVisibility(8);
        this.mLbDelHistoryView.setVisibility(0);
        this.mLyShowDelAll.setVisibility(0);
        this.mLyShowMore.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$18(View view) {
        this.historyList.clear();
        SharePreferencesUtils.writeStrConfig(CommonParameter.getSaveSearchHistoryData(), new Gson().toJson(this.historyList), this);
        initHistory();
        this.isExpandHistory = false;
        this.mLyHistoryView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$19(View view) {
        this.mBtnDelHistory.setVisibility(0);
        this.mLyShowDelAll.setVisibility(8);
        this.mLbDelHistoryView.setVisibility(8);
        initHistory();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$20(ForumSearchBean forumSearchBean, int i2) {
        forumSearchBean.getCircleInfo().setIs_rights("1");
        this.mContentAdp.notifyItemChanged(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$setListenerForWidget$21(com.chad.library.adapter.base.BaseQuickAdapter baseQuickAdapter, View view, final int i2) {
        final ForumSearchBean forumSearchBean = (ForumSearchBean) this.mContentAdp.getItem(i2);
        if (isLogin()) {
            int itemType = forumSearchBean.getItemType();
            if (itemType == 1) {
                if ("1".equals(forumSearchBean.getCircleInfo().getNo_access())) {
                    startActivity(new Intent(this, (Class<?>) MemberCenterActivity.class));
                    return;
                }
                if (this.isEditSearch) {
                    submitHotTimes(forumSearchBean.getCircleInfo().getId());
                }
                if (!forumSearchBean.getCircleInfo().getId().equals(forumSearchBean.getCircleInfo().getExp_id())) {
                    Intent intent = new Intent(this.mContext, (Class<?>) CircleInfoActivity.class);
                    intent.putExtra("channel_id", "");
                    intent.putExtra("article_id", forumSearchBean.getCircleInfo().getId());
                    intent.putExtra("module_type", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR);
                    intent.putExtra("app_id", forumSearchBean.getCircleInfo().getApp_id());
                    startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(this, (Class<?>) HandoutsInfoActivity.class);
                intent2.putExtra("cat_id", forumSearchBean.getCircleInfo().getCid());
                intent2.putExtra("article", forumSearchBean.getCircleInfo().getId());
                intent2.putExtra("json_path", forumSearchBean.getCircleInfo().getJson_path());
                intent2.putExtra("html_path", forumSearchBean.getCircleInfo().getHtml_path());
                intent2.putExtra("h5_path", forumSearchBean.getCircleInfo().getH5_path());
                intent2.putExtra("is_rich_text", forumSearchBean.getCircleInfo().getIs_rich_text());
                intent2.putExtra("index", forumSearchBean.getCircleInfo().getCid() + StrPool.UNDERLINE + forumSearchBean.getCircleInfo().getId());
                intent2.putExtra("app_id", forumSearchBean.getCircleInfo().getApp_id());
                startActivity(intent2);
                return;
            }
            if (itemType == 2) {
                if (this.isEditSearch) {
                    submitHotTimes(forumSearchBean.getCircleInfo().getId());
                }
                Intent intent3 = new Intent(this, (Class<?>) HandoutsInfoActivity.class);
                intent3.putExtra("cat_id", forumSearchBean.getCircleInfo().getCid());
                intent3.putExtra("article", forumSearchBean.getCircleInfo().getId());
                intent3.putExtra("json_path", forumSearchBean.getCircleInfo().getJson_path());
                intent3.putExtra("html_path", forumSearchBean.getCircleInfo().getHtml_path());
                intent3.putExtra("h5_path", forumSearchBean.getCircleInfo().getH5_path());
                intent3.putExtra("is_rich_text", forumSearchBean.getCircleInfo().getIs_rich_text());
                intent3.putExtra("index", forumSearchBean.getCircleInfo().getCid() + StrPool.UNDERLINE + forumSearchBean.getCircleInfo().getId());
                intent3.putExtra("app_id", forumSearchBean.getCircleInfo().getApp_id());
                startActivity(intent3);
                return;
            }
            if (itemType != 3) {
                if (itemType == 4) {
                    if (this.isEditSearch) {
                        submitHotTimes(forumSearchBean.getCircleInfo().getId());
                    }
                    startActivity(ReadBookActivity.INSTANCE.newIntent(this.mContext, forumSearchBean.getCircleInfo().getId(), UserConfig.getUserId(), SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"), UserConfig.getInstance().getUser().getAdmin(), UserConfig.getInstance().getUser().getAvatar(), UserConfig.getInstance().getUser().getToken(), UserConfig.getInstance().getUser().getSecret()));
                    return;
                }
                if (itemType != 5) {
                    return;
                }
                if (!TextUtils.isEmpty(forumSearchBean.getCircleInfo().getIs_rights()) && forumSearchBean.getCircleInfo().getIs_rights().equals("1")) {
                    if (this.isEditSearch) {
                        submitHotTimes(forumSearchBean.getCircleInfo().getId());
                    }
                    InformationPreviewAct.newIntent(this, forumSearchBean.getCircleInfo().getId(), forumSearchBean.getCircleInfo().getUrl(), false);
                    return;
                } else {
                    AjaxParams ajaxParams = new AjaxParams();
                    ajaxParams.put("enclosure_id", forumSearchBean.getCircleInfo().getId());
                    MemInterface.getInstance().getFilePermission(this, ajaxParams);
                    MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.forum.x
                        @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                        public final void mUShareListener() {
                            this.f15414a.lambda$setListenerForWidget$20(forumSearchBean, i2);
                        }
                    });
                    return;
                }
            }
            if (forumSearchBean.getCircleInfo().getModule_type().equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR)) {
                if ("1".equals(forumSearchBean.getCircleInfo().getNo_access())) {
                    startActivity(new Intent(this, (Class<?>) MemberCenterActivity.class));
                    return;
                }
                if (this.isEditSearch) {
                    submitHotTimes(forumSearchBean.getCircleInfo().getId());
                }
                Intent intent4 = new Intent(this.mContext, (Class<?>) CircleInfoActivity.class);
                intent4.putExtra("channel_id", "");
                intent4.putExtra("article_id", forumSearchBean.getCircleInfo().getId());
                intent4.putExtra("module_type", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SET_AVATAR);
                intent4.putExtra("app_id", forumSearchBean.getCircleInfo().getApp_id());
                startActivity(intent4);
                return;
            }
            if (this.isEditSearch) {
                submitHotTimes(forumSearchBean.getCircleInfo().getId());
            }
            Intent intent5 = new Intent(this, (Class<?>) HandoutsInfoActivity.class);
            intent5.putExtra("cat_id", forumSearchBean.getCircleInfo().getCid());
            intent5.putExtra("article", forumSearchBean.getCircleInfo().getId());
            intent5.putExtra("json_path", forumSearchBean.getCircleInfo().getJson_path());
            intent5.putExtra("html_path", forumSearchBean.getCircleInfo().getHtml_path());
            intent5.putExtra("h5_path", forumSearchBean.getCircleInfo().getH5_path());
            intent5.putExtra("is_rich_text", forumSearchBean.getCircleInfo().getIs_rich_text());
            intent5.putExtra("index", forumSearchBean.getCircleInfo().getCid() + StrPool.UNDERLINE + forumSearchBean.getCircleInfo().getId());
            intent5.putExtra("app_id", forumSearchBean.getCircleInfo().getApp_id());
            startActivity(intent5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$22(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$23(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadSearchData(String isHot) {
        if (!TextUtils.isEmpty(this.mWord) && this.mEtContent.getText().toString().equals("")) {
            this.mEtContent.setText(this.mWord);
            this.mEtContent.setSelection(this.mWord.length());
        }
        if (this.mEtContent.getText().toString().equals("")) {
            return;
        }
        this.isClickSearchKey = true;
        SharePreferencesUtils.writeStrConfig("searchCircleKeyWords", this.mEtContent.getText().toString().trim(), this.mContext);
        this.mResultRefresh.autoRefreshAnimationOnly();
        this.page = 1;
        getCircleData(isHot);
        this.mBtnDelHistory.setVisibility(0);
        this.mLbDelHistoryView.setVisibility(8);
        this.mLyShowDelAll.setVisibility(8);
        this.mWordLinkRecyclerView.setVisibility(8);
        this.llHotSearch.setVisibility(8);
        this.mLyResultView.setVisibility(0);
    }

    public static void newIntent(Context context, String word, TarGetParamBean params, int isFromExp) {
        Intent intent = new Intent(context, (Class<?>) ForumSearchAct.class);
        intent.putExtra("word", word);
        intent.putExtra("params", params);
        intent.putExtra("isFromExp", isFromExp);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOrHiddenArrow(boolean isShow, final ImageView arrowImg) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        if (isShow) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 180);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.forum.b0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ForumSearchAct.lambda$showOrHiddenArrow$22(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(180, 0);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.forum.c0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ForumSearchAct.lambda$showOrHiddenArrow$23(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    public void getCircleData(String isEditSearch) {
        if (TextUtils.isEmpty(this.mEtContent.getText().toString())) {
            return;
        }
        addHistoryContent(this.mEtContent.getText().toString());
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, this.mEtContent.getText().toString());
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("code", this.mType);
        ajaxParams.put("search_app_id", this.mSearchAppId);
        ajaxParams.put("search_type", "0");
        ajaxParams.put("ranking", this.mRanking);
        ajaxParams.put("time_range", this.mTimeRange);
        ajaxParams.put("score_type", this.mAchievement);
        ajaxParams.put("ctime", this.mTimeSort);
        ajaxParams.put("time_sort", this.mTimeSortExp);
        ajaxParams.put("test_time", this.mExamYear);
        ajaxParams.put("u_cid", this.mUcid);
        ajaxParams.put("p_cid", this.mPcid);
        ajaxParams.put("m_cid", this.mMcid);
        ajaxParams.put("file_type", this.mFileType);
        ajaxParams.put("category_id", this.mInfoType);
        ajaxParams.put("is_hot", isEditSearch);
        ajaxParams.put("limit", "20");
        Log.e("search_page", "page====>" + this.page);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.searchArticleList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.ForumSearchAct.12
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumSearchAct.this.mResultRefresh.finishRefresh();
                if (ForumSearchAct.this.page == 1) {
                    ForumSearchAct.this.emptyView.setLoadFileResUi(ForumSearchAct.this);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                ForumSearchAct.this.mResultRefresh.finishRefresh();
                super.onSuccess((AnonymousClass12) s2);
                ForumSearchAct.this.mContentAdp.getEmptyLayout().setVisibility(0);
                ForumSearchAct.this.emptyView.showEmptyView();
                try {
                    CirclrListBean circlrListBean = (CirclrListBean) new Gson().fromJson(s2, CirclrListBean.class);
                    if (!circlrListBean.getCode().equals("200")) {
                        if (ForumSearchAct.this.page == 1) {
                            ForumSearchAct.this.emptyView.setLoadFileResUi(ForumSearchAct.this);
                            return;
                        }
                        return;
                    }
                    if (circlrListBean.getData() == null || circlrListBean.getData().size() <= 0) {
                        if (ForumSearchAct.this.page == 1) {
                            ForumSearchAct.this.mContentAdp.setList(new ArrayList());
                            ForumSearchAct.this.emptyView.uploadEmptyViewResUi();
                        }
                        ForumSearchAct.this.mResultRefresh.finishLoadMoreWithNoMoreData();
                        return;
                    }
                    ArrayList arrayList = new ArrayList();
                    for (CirclrListBean.DataBean dataBean : circlrListBean.getData()) {
                        ForumSearchBean forumSearchBean = new ForumSearchBean();
                        if (ForumSearchAct.this.mType.equals("bbs")) {
                            forumSearchBean.setType(1);
                        } else if (ForumSearchAct.this.mType.equals(ClientCookie.COMMENT_ATTR)) {
                            forumSearchBean.setType(3);
                        } else if (ForumSearchAct.this.mType.equals("file")) {
                            forumSearchBean.setType(5);
                        } else if (ForumSearchAct.this.mType.equals("ebook")) {
                            forumSearchBean.setType(4);
                        } else if (ForumSearchAct.this.mType.equals("exp")) {
                            forumSearchBean.setType(2);
                        }
                        forumSearchBean.setCircleInfo(dataBean);
                        arrayList.add(forumSearchBean);
                    }
                    if (ForumSearchAct.this.page == 1) {
                        ForumSearchAct.this.mContentAdp.setSearchContent(ForumSearchAct.this.mEtContent.getText().toString());
                        ForumSearchAct.this.mContentAdp.setList(arrayList);
                        if (arrayList.size() < 20) {
                            ForumSearchAct.this.mResultRefresh.finishLoadMoreWithNoMoreData();
                            return;
                        }
                        return;
                    }
                    ForumSearchAct.this.mContentAdp.addData((Collection) arrayList);
                    if (arrayList.size() < 20) {
                        ForumSearchAct.this.mResultRefresh.finishLoadMoreWithNoMoreData();
                    } else {
                        ForumSearchAct.this.mResultRefresh.finishLoadMore();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (ForumSearchAct.this.page == 1) {
                        ForumSearchAct.this.emptyView.setLoadFileResUi(ForumSearchAct.this);
                    }
                }
            }
        });
    }

    public void getImageData(SpannableStringBuilder spannableString, TextView mTextView) {
        try {
            float textSize = mTextView.getPaint().getTextSize();
            Matcher matcher = Pattern.compile("\\[[^\\]]+\\]").matcher(spannableString.toString());
            while (matcher.find()) {
                String strGroup = matcher.group();
                if (strGroup.contains("http")) {
                    spannableString.setSpan(new StickerSpan(new URLImageParser(mTextView, this.mContext, (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1)), 1), matcher.start(), matcher.end(), 33);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        mTextView.setText(spannableString);
    }

    public void getRankData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.msearchRankData, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.ForumSearchAct.10
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                ForumSearchAct.this.mLyHotWord.setVisibility(8);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass10) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optInt("code") == 200) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data"), new TypeToken<List<ForumTopBean.KeyWordsData>>() { // from class: com.psychiatrygarden.forum.ForumSearchAct.10.1
                        }.getType());
                        ForumSearchAct.this.initHotWord(list);
                        ForumSearchAct.this.mLyHotWord.setVisibility(list.size() > 0 ? 0 : 8);
                    } else {
                        ForumSearchAct.this.mLyHotWord.setVisibility(8);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mWord = getIntent().getStringExtra("word");
        this.mParams = (TarGetParamBean) getIntent().getSerializableExtra("params");
        this.isFromSerarchType = getIntent().getIntExtra("isFromExp", 0);
        this.mLyView = (RelativeLayout) findViewById(R.id.ly_view);
        this.mBtnBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mLyHistoryView = (LinearLayout) findViewById(R.id.ly_history);
        this.mLbDelHistoryView = (HistoryLabelsView) findViewById(R.id.lb_del_history);
        this.mLyShowMore = (LinearLayout) findViewById(R.id.ly_show_more);
        this.mImgMoreArrow = (ImageView) findViewById(R.id.img_arrow);
        this.mBtnDelHistory = (ImageView) findViewById(R.id.btn_del_history);
        this.mLbHistoryView = (LabelsView) findViewById(R.id.lb_history);
        this.mLbHotWordView = (TagLabelsView) findViewById(R.id.lb_hot_word);
        this.mBtnSearch = (TextView) findViewById(R.id.btn_search);
        this.rvHotSearchRankTab = (RecyclerView) findViewById(R.id.rvHotSearchRankTab);
        this.mBtnSearch.setTextColor(SkinManager.getCurrentSkinType(this) == 0 ? Color.parseColor("#141516") : Color.parseColor("#7380A9"));
        this.mEtContent = (ClearEditText) findViewById(R.id.ed_search);
        this.mTvMoreHistory = (TextView) findViewById(R.id.tv_more_history);
        this.mLyHotWord = (LinearLayout) findViewById(R.id.ly_hot_word);
        this.mLyResultView = (LinearLayout) findViewById(R.id.ly_result);
        this.mWordLinkRecyclerView = (RecyclerView) findViewById(R.id.rc_search_value);
        this.mTypeRecycler = (RecyclerView) findViewById(R.id.type_recyclerview);
        this.mResultRefresh = (SmartRefreshLayout) findViewById(R.id.result_refresh);
        this.mResultRecycler = (RecyclerView) findViewById(R.id.result_recyclerview);
        this.mRankRecycler = (RecyclerView) findViewById(R.id.rank_recycler);
        this.mTvFinish = (TextView) findViewById(R.id.tv_finish);
        this.mTvDelAll = (TextView) findViewById(R.id.tv_del_all);
        this.mLyShowDelAll = (LinearLayout) findViewById(R.id.ly_show_del_all);
        this.llHotSearch = (LinearLayout) findViewById(R.id.ll_hot_search);
        this.mEtContent.setHint(this.mWord);
        this.mSearchRankTabAdapter = new HotSearchRankTabAdapter();
        String strConfig = SharePreferencesUtils.readStrConfig("top_grid_data", this);
        ArrayList arrayList = new ArrayList();
        ForumTopBean.TopModule topModule = new ForumTopBean.TopModule();
        topModule.setName("帖子热搜榜");
        topModule.setCode("bbs");
        arrayList.add(topModule);
        if (!TextUtils.isEmpty(strConfig)) {
            arrayList.addAll((List) new Gson().fromJson(strConfig, new TypeToken<List<ForumTopBean.TopModule>>() { // from class: com.psychiatrygarden.forum.ForumSearchAct.1
            }.getType()));
        }
        final ArrayList arrayList2 = new ArrayList();
        int i2 = 0;
        while (i2 < arrayList.size()) {
            this.mSearchRankTabAdapter.addData((HotSearchRankTabAdapter) new HotSearchRankTabItem(((ForumTopBean.TopModule) arrayList.get(i2)).getName(), i2 == 0, 1, ((ForumTopBean.TopModule) arrayList.get(i2)).getCode()));
            String code = ((ForumTopBean.TopModule) arrayList.get(i2)).getCode();
            code.hashCode();
            switch (code) {
                case "bbs":
                    arrayList2.add(CircleHotSearchRankFragment.newInstance(1, "bbs", false, i2, true));
                    break;
                case "exp":
                    arrayList2.add(CircleHotSearchRankFragment.newInstance(2, "exp", false, i2, true));
                    break;
                case "file":
                    arrayList2.add(CircleHotSearchRankFragment.newInstance(4, "file", false, i2, true));
                    break;
                case "ebook":
                    arrayList2.add(CircleHotSearchRankFragment.newInstance(3, "ebook", false, i2, true));
                    break;
            }
            i2++;
        }
        ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.viewPager2HotSearchRank);
        this.viewPager2HotSearchRank = viewPager2;
        viewPager2.registerOnPageChangeCallback(this.mPageChangeCallback);
        this.viewPager2HotSearchRank.setOffscreenPageLimit(1);
        this.rvHotSearchRankTab.setAdapter(this.mSearchRankTabAdapter);
        this.mSearchRankTabAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.forum.f
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(com.chad.library.adapter.base.BaseQuickAdapter baseQuickAdapter, View view, int i3) {
                this.f15348c.lambda$init$0(baseQuickAdapter, view, i3);
            }
        });
        this.viewPager2HotSearchRank.setAdapter(new FragmentStateAdapter(getSupportFragmentManager(), getLifecycle()) { // from class: com.psychiatrygarden.forum.ForumSearchAct.2
            @Override // androidx.viewpager2.adapter.FragmentStateAdapter
            @NonNull
            public Fragment createFragment(int position) {
                return (Fragment) arrayList2.get(position);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return arrayList2.size();
            }
        });
        ClearEditText clearEditText = this.mEtContent;
        clearEditText.setSelection(clearEditText.getText().toString().length());
        SearchTypeAdp searchTypeAdp = new SearchTypeAdp();
        this.mTypeAdapter = searchTypeAdp;
        this.mTypeRecycler.setAdapter(searchTypeAdp);
        this.mContentAdp = new ForumSearchContentAdp();
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15393c.lambda$init$1(view);
            }
        });
        this.mContentAdp.setEmptyView(this.emptyView);
        this.mContentAdp.getEmptyLayout().setVisibility(8);
        if (this.isFromSerarchType == 1) {
            this.mWordLinkRecyclerView.setVisibility(8);
            this.mLyResultView.setVisibility(0);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.mRankRecycler.setLayoutManager(linearLayoutManager);
        ForumRankingAdp forumRankingAdp = new ForumRankingAdp();
        this.mRankAdp = forumRankingAdp;
        this.mRankRecycler.setAdapter(forumRankingAdp);
        this.mResultRecycler.setAdapter(this.mContentAdp);
        this.mResultRefresh.setOnRefreshLoadMoreListener(this);
        initSearch();
        initHistory();
        getRankData();
        initTypeData();
        initWordLinkAdapter();
        getTopCategoryData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void initwriteStatusBar() {
        super.initwriteStatusBar();
        if (this.mBaseTheme == 0) {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white_color), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.app_bg_night), 0);
            getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        initwriteStatusBar();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.viewPager2HotSearchRank.unregisterOnPageChangeCallback(this.mPageChangeCallback);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        this.page++;
        getCircleData("1");
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.page = 1;
        this.mResultRefresh.autoRefreshAnimationOnly();
        getCircleData("1");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_forum_search);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15358c.lambda$setListenerForWidget$12(view);
            }
        });
        this.mBtnSearch.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15363c.lambda$setListenerForWidget$13(view);
            }
        });
        this.mLyShowMore.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15368c.lambda$setListenerForWidget$16(view);
            }
        });
        this.mBtnDelHistory.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15374c.lambda$setListenerForWidget$17(view);
            }
        });
        this.mTvDelAll.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15379c.lambda$setListenerForWidget$18(view);
            }
        });
        this.mTvFinish.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15384c.lambda$setListenerForWidget$19(view);
            }
        });
        this.mRankAdp.setOnItemActionLisenter(new AnonymousClass7());
        this.mContentAdp.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.forum.o
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(com.chad.library.adapter.base.BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15387c.lambda$setListenerForWidget$21(baseQuickAdapter, view, i2);
            }
        });
    }

    public void submitHotTimes(String objId) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("module_type", this.mType);
        ajaxParams.put("obj_id", objId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.searchHotTimes, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.ForumSearchAct.13
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                ForumSearchAct.this.mResultRefresh.finishRefresh();
                super.onSuccess((AnonymousClass13) s2);
            }
        });
    }
}
