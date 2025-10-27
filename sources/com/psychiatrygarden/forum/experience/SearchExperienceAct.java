package com.psychiatrygarden.forum.experience;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.HandoutsInfoActivity;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.forum.ForumRankingAdp;
import com.psychiatrygarden.forum.ForumSearchAct;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.ChooseExpDefaultFilterPopuWindow;
import com.psychiatrygarden.widget.ChooseExperienceFilterPopuWindow;
import com.psychiatrygarden.widget.ChooseFilterPopuWindow;
import com.psychiatrygarden.widget.ChooseProjectPopuWindow;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.CustomEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes5.dex */
public class SearchExperienceAct extends BaseActivity implements OnRefreshListener {
    private View addFooterView;
    private CustomEmptyView emptyView;
    private boolean isCanLoadNextPage;
    private SearchExperienceAdp mAdapter;
    private ImageView mBtnBack;
    private TextView mBtnSearch;
    private ClearEditText mEtContent;
    private RelativeLayout mLyLoading;
    private LinearLayout mLyView;
    private ForumRankingAdp mRankAdp;
    private RecyclerView mRankRecycler;
    private RecyclerView mRecycler;
    private SmartRefreshLayout mRefresh;
    private TextView mTvNoData;
    private int mChooseProjectPos = 0;
    private int mChooseTimePos = 0;
    private int page = 1;
    private String mSearchAppId = "";
    private String mExamYear = "";
    private String mUcid = "";
    private String mPcid = "";
    private String mMcid = "";
    private String mRanking = "";
    private String mAchievement = "";
    private String mTimeSort = "";
    private String mFileType = "";
    private String mInfoType = "";
    private String mTimeRange = "";
    private String mTimeSortExp = "";
    private List<String> mFilterKey = new ArrayList();
    private int mProjectParentPos = 0;
    private int height = 0;
    private boolean isNoMoreData = false;

    public static /* synthetic */ int access$312(SearchExperienceAct searchExperienceAct, int i2) {
        int i3 = searchExperienceAct.page + i2;
        searchExperienceAct.page = i3;
        return i3;
    }

    private void getTopCategoryFilterData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("code", "exp");
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.forumSearchCategoryFilterData, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.experience.SearchExperienceAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    ForumFilterBean forumFilterBean = (ForumFilterBean) new Gson().fromJson(s2, ForumFilterBean.class);
                    if (forumFilterBean.getCode().equals("200")) {
                        for (int i2 = 0; i2 < forumFilterBean.getData().size(); i2++) {
                            if (forumFilterBean.getData().get(i2).getType().equals(ServletHandler.__DEFAULT_SERVLET)) {
                                if (forumFilterBean.getData().get(i2).getList() != null && !forumFilterBean.getData().get(i2).getList().isEmpty() && forumFilterBean.getData().get(i2).getList().get(0).getList() != null && !forumFilterBean.getData().get(i2).getList().get(0).getList().isEmpty()) {
                                    forumFilterBean.getData().get(i2).getList().get(0).getList().get(0).setSelected(true);
                                }
                            } else if ((!TextUtils.equals("exp", forumFilterBean.getData().get(i2).getType()) || !TextUtils.equals("project", forumFilterBean.getData().get(i2).getType())) && forumFilterBean.getData().get(i2).getList() != null && !forumFilterBean.getData().get(i2).getList().isEmpty()) {
                                forumFilterBean.getData().get(i2).getList().get(0).setSelected(true);
                            }
                        }
                        SearchExperienceAct.this.mRankAdp.setList(forumFilterBean.getData());
                        SearchExperienceAct.this.getData();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        onRefresh(this.mRefresh);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        CirclrListBean.DataBean item;
        String id;
        if (!isLogin() || (id = (item = this.mAdapter.getItem(i2)).getId()) == null) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) HandoutsInfoActivity.class);
        intent.putExtra("article", id);
        intent.putExtra("json_path", item.getJson_path());
        intent.putExtra("html_path", item.getHtml_path());
        intent.putExtra("h5_path", item.getH5_path());
        intent.putExtra("is_rich_text", item.getIs_rich_text());
        intent.putExtra("app_id", item.getApp_id());
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$3(TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        if (!this.mEtContent.getText().toString().trim().equals("")) {
            ForumSearchAct.newIntent(this.mContext, this.mEtContent.getText().toString().trim(), null, 1);
            hideInputMethod();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        String strTrim = this.mEtContent.getText().toString().trim();
        if (TextUtils.isEmpty(strTrim)) {
            return;
        }
        ForumSearchAct.newIntent(this.mContext, strTrim, null, 1);
        hideInputMethod();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$5(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showOrHiddenArrow$6(ImageView imageView, ValueAnimator valueAnimator) {
        imageView.setRotation(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) SearchExperienceAct.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOrHiddenArrow(boolean isShow, final ImageView arrowImg) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        if (isShow) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 180);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.forum.experience.d
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SearchExperienceAct.lambda$showOrHiddenArrow$5(arrowImg, valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfInt);
            animatorSet.start();
            return;
        }
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(180, 0);
        valueAnimatorOfInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.forum.experience.e
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SearchExperienceAct.lambda$showOrHiddenArrow$6(arrowImg, valueAnimator);
            }
        });
        animatorSet.playTogether(valueAnimatorOfInt2);
        animatorSet.start();
    }

    public void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        String string = this.mEtContent.getText().toString();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_KEYWORD, string);
        if (TextUtils.isEmpty(string)) {
            ajaxParams.put("search_type", "2");
        }
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "" + this.page);
        ajaxParams.put("limit", "20");
        ajaxParams.put("code", "exp");
        ajaxParams.put("search_app_id", this.mSearchAppId);
        ajaxParams.put("ranking", this.mRanking);
        ajaxParams.put("time_range", this.mTimeRange);
        ajaxParams.put("time_sort", this.mTimeSortExp);
        ajaxParams.put("score_type", this.mAchievement);
        ajaxParams.put("ctime", this.mTimeSort);
        ajaxParams.put("test_time", this.mExamYear);
        ajaxParams.put("u_cid", this.mUcid);
        ajaxParams.put("p_cid", this.mPcid);
        ajaxParams.put("m_cid", this.mMcid);
        ajaxParams.put("file_type", this.mFileType);
        ajaxParams.put("category_id", this.mInfoType);
        if (this.page <= 1) {
            this.mAdapter.removeFooterView(this.addFooterView);
            this.mLyLoading.setVisibility(0);
            this.mTvNoData.setVisibility(8);
        } else if (this.mAdapter.getFooterLayout() == null) {
            this.mAdapter.addFooterView(this.addFooterView);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.searchArticleList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.experience.SearchExperienceAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SearchExperienceAct.this.mRefresh.finishRefresh(false);
                if (SearchExperienceAct.this.page == 1) {
                    SearchExperienceAct.this.emptyView.setIsShowReloadBtn(true, "点击刷新页面");
                    SearchExperienceAct.this.mAdapter.removeFooterView(SearchExperienceAct.this.addFooterView);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                SearchExperienceAct.this.mRefresh.finishRefresh();
                SearchExperienceAct.this.emptyView.showEmptyView();
                super.onSuccess((AnonymousClass4) s2);
                try {
                    CirclrListBean circlrListBean = (CirclrListBean) new Gson().fromJson(s2, CirclrListBean.class);
                    if (circlrListBean.getCode().equals("200")) {
                        if (circlrListBean.getData() == null || circlrListBean.getData().size() <= 0) {
                            SearchExperienceAct.this.isNoMoreData = true;
                            SearchExperienceAct.this.mLyLoading.setVisibility(8);
                            SearchExperienceAct.this.mTvNoData.setVisibility(0);
                            if (SearchExperienceAct.this.page == 1) {
                                SearchExperienceAct.this.mAdapter.setList(new ArrayList());
                            }
                            SearchExperienceAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                        } else if (SearchExperienceAct.this.page == 1) {
                            SearchExperienceAct.this.mAdapter.setList(circlrListBean.getData());
                            if (circlrListBean.getData().size() < 20) {
                                SearchExperienceAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                            }
                        } else if (!circlrListBean.getData().isEmpty()) {
                            SearchExperienceAct.this.mAdapter.addData((Collection) circlrListBean.getData());
                            if (circlrListBean.getData().size() < 20) {
                                SearchExperienceAct.this.mRefresh.finishLoadMoreWithNoMoreData();
                            } else {
                                SearchExperienceAct.this.mRefresh.finishLoadMore();
                            }
                        }
                        SearchExperienceAct.this.isCanLoadNextPage = false;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (SearchExperienceAct.this.page == 1) {
                        SearchExperienceAct.this.emptyView.setLoadFileResUi(SearchExperienceAct.this);
                    }
                    SearchExperienceAct.this.mAdapter.removeFooterView(SearchExperienceAct.this.addFooterView);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mBtnBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mBtnSearch = (TextView) findViewById(R.id.btn_search);
        this.mLyView = (LinearLayout) findViewById(R.id.ly_view);
        this.mEtContent = (ClearEditText) findViewById(R.id.ed_search);
        this.mRefresh = (SmartRefreshLayout) findViewById(R.id.refresh);
        this.mRecycler = (RecyclerView) findViewById(R.id.recyclerview);
        this.mRankRecycler = (RecyclerView) findViewById(R.id.rank_recycler);
        this.mEtContent.setHint("搜经验");
        SearchExperienceAdp searchExperienceAdp = new SearchExperienceAdp();
        this.mAdapter = searchExperienceAdp;
        this.mRecycler.setAdapter(searchExperienceAdp);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.mRankRecycler.setLayoutManager(linearLayoutManager);
        ForumRankingAdp forumRankingAdp = new ForumRankingAdp();
        this.mRankAdp = forumRankingAdp;
        this.mRankRecycler.setAdapter(forumRankingAdp);
        this.mRefresh.setOnRefreshListener(this);
        this.mRefresh.setEnableLoadMore(false);
        this.mRefresh.setEnableFooterFollowWhenNoMoreData(true);
        CustomEmptyView customEmptyView = new CustomEmptyView(this, 0, "");
        this.emptyView = customEmptyView;
        customEmptyView.setOnReloadData(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.experience.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15346c.lambda$init$0(view);
            }
        });
        this.mAdapter.setEmptyView(this.emptyView);
        View viewInflate = getLayoutInflater().inflate(R.layout.activity_hideview, (ViewGroup) null);
        this.addFooterView = viewInflate;
        this.mTvNoData = (TextView) viewInflate.findViewById(R.id.tv_no_more_data);
        this.mLyLoading = (RelativeLayout) this.addFooterView.findViewById(R.id.hide_sub_floor_content);
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.forum.experience.g
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15347c.lambda$init$1(baseQuickAdapter, view, i2);
            }
        });
        this.mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.forum.experience.SearchExperienceAct.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager2 = (LinearLayoutManager) SearchExperienceAct.this.mRecycler.getLayoutManager();
                int iFindFirstVisibleItemPosition = linearLayoutManager2.findFirstVisibleItemPosition();
                if (dy <= 0 || linearLayoutManager2.getItemCount() - iFindFirstVisibleItemPosition > 11 || SearchExperienceAct.this.isCanLoadNextPage || SearchExperienceAct.this.isNoMoreData) {
                    return;
                }
                SearchExperienceAct.this.isCanLoadNextPage = true;
                SearchExperienceAct.access$312(SearchExperienceAct.this, 1);
                SearchExperienceAct.this.getData();
                LogUtils.e("load_next", "加载下一页数据:" + SearchExperienceAct.this.page);
            }
        });
        getTopCategoryFilterData();
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

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        this.page = 1;
        this.isNoMoreData = false;
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_search_experience);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.experience.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15341c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.forum.experience.b
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                return this.f15342c.lambda$setListenerForWidget$3(textView, i2, keyEvent);
            }
        });
        this.mBtnSearch.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.experience.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15343c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mRankAdp.setOnItemActionLisenter(new ForumRankingAdp.OnItemActionLisenter() { // from class: com.psychiatrygarden.forum.experience.SearchExperienceAct.2
            @Override // com.psychiatrygarden.forum.ForumRankingAdp.OnItemActionLisenter
            public void setItemClickAction(final int position, final ForumFilterBean.FilterDataBean item, final ImageView arrowImg) {
                if (CommonUtil.isFastClick()) {
                    return;
                }
                SearchExperienceAct.this.hideInputMethod();
                int measuredHeight = SearchExperienceAct.this.mRefresh.getMeasuredHeight();
                if (measuredHeight < SearchExperienceAct.this.height) {
                    measuredHeight = SearchExperienceAct.this.height;
                } else {
                    SearchExperienceAct.this.height = measuredHeight;
                }
                SearchExperienceAct.this.showOrHiddenArrow(true, arrowImg);
                if (TextUtils.isEmpty(item.getType())) {
                    item.setType("other");
                }
                if (item.getType().equals("project")) {
                    SearchExperienceAct searchExperienceAct = SearchExperienceAct.this;
                    new ChooseProjectPopuWindow(searchExperienceAct, searchExperienceAct.mRefresh, SearchExperienceAct.this.mRankRecycler, measuredHeight, item.getList(), SearchExperienceAct.this.mProjectParentPos, new ChooseProjectPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.forum.experience.SearchExperienceAct.2.1
                        @Override // com.psychiatrygarden.widget.ChooseProjectPopuWindow.ProjectChoosedInterface
                        public void mItemDissmissLinsenter() {
                            SearchExperienceAct.this.showOrHiddenArrow(false, arrowImg);
                        }

                        @Override // com.psychiatrygarden.widget.ChooseProjectPopuWindow.ProjectChoosedInterface
                        public void mItemLinsenter(int parentPos, int choosePos, ForumFilterBean.FilterDataBean chooseItem) {
                            item.setTitle(chooseItem.getTitle());
                            SearchExperienceAct.this.mSearchAppId = chooseItem.getApp_id();
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
                            SearchExperienceAct.this.mProjectParentPos = parentPos;
                            SearchExperienceAct.this.showOrHiddenArrow(false, arrowImg);
                            SearchExperienceAct.this.mRankAdp.setmChoosedPos(position);
                            SearchExperienceAct.this.mRankAdp.notifyDataSetChanged();
                            SearchExperienceAct.this.mRecycler.smoothScrollToPosition(0);
                            SearchExperienceAct.this.mRefresh.autoRefresh();
                        }
                    });
                } else if (item.getType().equals("exp")) {
                    SearchExperienceAct searchExperienceAct2 = SearchExperienceAct.this;
                    new ChooseExperienceFilterPopuWindow(searchExperienceAct2, searchExperienceAct2.mRefresh, SearchExperienceAct.this.mRankRecycler, measuredHeight, item.getList(), SearchExperienceAct.this.mFilterKey, new ChooseExperienceFilterPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.forum.experience.SearchExperienceAct.2.2
                        @Override // com.psychiatrygarden.widget.ChooseExperienceFilterPopuWindow.ProjectChoosedInterface
                        public void mItemDissmissLinsenter() {
                            SearchExperienceAct.this.showOrHiddenArrow(false, arrowImg);
                        }

                        @Override // com.psychiatrygarden.widget.ChooseExperienceFilterPopuWindow.ProjectChoosedInterface
                        public void mItemLinsenter(List<ForumFilterBean.FilterDataBean> list) {
                            SearchExperienceAct.this.showOrHiddenArrow(false, arrowImg);
                            SearchExperienceAct.this.mFilterKey.clear();
                            SearchExperienceAct.this.mSearchAppId = "";
                            SearchExperienceAct.this.mExamYear = "";
                            SearchExperienceAct.this.mUcid = "";
                            SearchExperienceAct.this.mPcid = "";
                            SearchExperienceAct.this.mMcid = "";
                            for (int i2 = 0; i2 < list.size(); i2++) {
                                String type = list.get(i2).getType();
                                String key = list.get(i2).getKey();
                                String str = list.get(i2).getmTempKey();
                                if (type.equals("search_app_id")) {
                                    SearchExperienceAct.this.mSearchAppId = key;
                                } else if (type.equals("test_time")) {
                                    SearchExperienceAct.this.mExamYear = key;
                                } else if (type.equals("u_cid")) {
                                    SearchExperienceAct.this.mUcid = key;
                                } else if (type.equals("p_cid")) {
                                    SearchExperienceAct.this.mPcid = key;
                                } else if (type.equals("m_cid")) {
                                    SearchExperienceAct.this.mMcid = key;
                                }
                                SearchExperienceAct.this.mFilterKey.add(str);
                            }
                            if (list.isEmpty()) {
                                SearchExperienceAct.this.mFilterKey.clear();
                                SearchExperienceAct.this.mRankAdp.setmChoosedPos(-1);
                            } else {
                                SearchExperienceAct.this.mRankAdp.setmChoosedPos(position);
                            }
                            SearchExperienceAct.this.mRankAdp.notifyDataSetChanged();
                            SearchExperienceAct.this.mRecycler.smoothScrollToPosition(0);
                            SearchExperienceAct.this.mRefresh.autoRefresh();
                        }
                    });
                } else if (item.getType().equals(ServletHandler.__DEFAULT_SERVLET)) {
                    SearchExperienceAct searchExperienceAct3 = SearchExperienceAct.this;
                    new ChooseExpDefaultFilterPopuWindow(searchExperienceAct3, searchExperienceAct3.mRefresh, SearchExperienceAct.this.mRankRecycler, measuredHeight, item.getList(), new ChooseExpDefaultFilterPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.forum.experience.SearchExperienceAct.2.3
                        @Override // com.psychiatrygarden.widget.ChooseExpDefaultFilterPopuWindow.ProjectChoosedInterface
                        public void mItemDissmissLinsenter() {
                            SearchExperienceAct.this.showOrHiddenArrow(false, arrowImg);
                        }

                        @Override // com.psychiatrygarden.widget.ChooseExpDefaultFilterPopuWindow.ProjectChoosedInterface
                        public void mItemLinsenter(List<ForumFilterBean.FilterDataBean> list) {
                            SearchExperienceAct.this.showOrHiddenArrow(false, arrowImg);
                            item.setTitle(list.get(0).getTitle());
                            for (int i2 = 0; i2 < list.size(); i2++) {
                                String type = list.get(i2).getType();
                                String key = list.get(i2).getKey();
                                if (type.equals("ranking")) {
                                    SearchExperienceAct.this.mRanking = key;
                                    SearchExperienceAct.this.mAchievement = "";
                                    SearchExperienceAct.this.mTimeSortExp = "";
                                } else if (type.equals("score_type")) {
                                    SearchExperienceAct.this.mAchievement = key;
                                    SearchExperienceAct.this.mRanking = "";
                                    SearchExperienceAct.this.mTimeSortExp = "";
                                } else if (type.equals("time_sort")) {
                                    SearchExperienceAct.this.mTimeSortExp = key;
                                    SearchExperienceAct.this.mRanking = "";
                                    SearchExperienceAct.this.mAchievement = "";
                                }
                            }
                            if (list.isEmpty()) {
                                SearchExperienceAct.this.mAchievement = "";
                                SearchExperienceAct.this.mRanking = "";
                                SearchExperienceAct.this.mTimeSortExp = "";
                            }
                            SearchExperienceAct.this.mRankAdp.setmChoosedPos(position);
                            SearchExperienceAct.this.mRankAdp.notifyDataSetChanged();
                            SearchExperienceAct.this.mRecycler.smoothScrollToPosition(0);
                            SearchExperienceAct.this.mRefresh.autoRefresh();
                        }
                    });
                } else {
                    SearchExperienceAct searchExperienceAct4 = SearchExperienceAct.this;
                    new ChooseFilterPopuWindow(searchExperienceAct4, searchExperienceAct4.mRankRecycler, measuredHeight, item.getList(), new ChooseFilterPopuWindow.ProjectChoosedInterface() { // from class: com.psychiatrygarden.forum.experience.SearchExperienceAct.2.4
                        @Override // com.psychiatrygarden.widget.ChooseFilterPopuWindow.ProjectChoosedInterface
                        public void mItemDissmissLinsenter() {
                            SearchExperienceAct.this.showOrHiddenArrow(false, arrowImg);
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
                            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.forum.experience.SearchExperienceAct.AnonymousClass2.AnonymousClass4.mItemLinsenter(int, com.psychiatrygarden.bean.ForumFilterBean$FilterDataBean):void");
                        }
                    }, false);
                }
            }
        });
    }
}
