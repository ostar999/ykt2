package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.vod.common.utils.UriUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.CombineQuestionActivity;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.online.AnswerSheetActivity;
import com.psychiatrygarden.activity.online.QuestionWCNHomeActivity;
import com.psychiatrygarden.activity.online.adapter.QuestionTopAdTwoAdapter;
import com.psychiatrygarden.adapter.BannerQuestionAdsAdapter;
import com.psychiatrygarden.adapter.HomeCombineQuestionListAdapter;
import com.psychiatrygarden.adapter.KingKongAreaItemAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.CombineQuestionRecordItem;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.bean.KingKongItem;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.NetworkEvent;
import com.psychiatrygarden.event.QuestionCombineDataEvent;
import com.psychiatrygarden.event.RefreshHomePaperListEvent;
import com.psychiatrygarden.event.RefreshPaperListEvent;
import com.psychiatrygarden.event.ShowQuestionCombineEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AlphaPageTransformer;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SelfCombineQuestionFragment extends BaseFragment {
    private boolean hasEmptyView;
    private String identity_id;
    private boolean isLoadMore;
    private boolean isScrolled;
    private boolean isVisibleToUser;
    private boolean loadDataSuccess;
    private HomeCombineQuestionListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private int page = 1;
    private boolean show = false;
    private boolean showShortCut;

    /* renamed from: com.psychiatrygarden.fragmenthome.SelfCombineQuestionFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            EventBus.getDefault().post(new ShowQuestionCombineEvent(!SelfCombineQuestionFragment.this.mAdapter.getData().isEmpty(), SelfCombineQuestionFragment.this.isScrolled && SelfCombineQuestionFragment.this.isDataMoreOneScreen()));
            EventBus.getDefault().post(new QuestionCombineDataEvent(!SelfCombineQuestionFragment.this.mAdapter.getData().isEmpty()));
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            SelfCombineQuestionFragment.this.loadDataSuccess = false;
            if (SelfCombineQuestionFragment.this.isLoadMore) {
                SelfCombineQuestionFragment.this.mRefreshLayout.finishLoadMore(false);
            } else {
                SelfCombineQuestionFragment.this.mRefreshLayout.finishRefresh(false);
            }
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (TextUtils.equals(jSONObject.optString("code", ""), "200")) {
                    if (SelfCombineQuestionFragment.this.isLoadMore) {
                        SelfCombineQuestionFragment.this.mRefreshLayout.finishLoadMore(true);
                    } else {
                        SelfCombineQuestionFragment.this.mRefreshLayout.finishRefresh(true);
                    }
                    SelfCombineQuestionFragment.this.loadDataSuccess = true;
                    JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("data");
                    if (jSONArrayOptJSONArray == null) {
                        jSONArrayOptJSONArray = new JSONArray();
                    }
                    List list = (List) new Gson().fromJson(jSONArrayOptJSONArray.toString(), new TypeToken<List<CombineQuestionRecordItem>>() { // from class: com.psychiatrygarden.fragmenthome.SelfCombineQuestionFragment.2.1
                    }.getType());
                    if (list == null || list.isEmpty()) {
                        if (SelfCombineQuestionFragment.this.isLoadMore) {
                            SelfCombineQuestionFragment.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                            SelfCombineQuestionFragment.access$810(SelfCombineQuestionFragment.this);
                        } else {
                            SelfCombineQuestionFragment.this.mAdapter.setList(new ArrayList());
                        }
                    } else if (SelfCombineQuestionFragment.this.isLoadMore) {
                        SelfCombineQuestionFragment.this.mAdapter.addData((Collection) list);
                    } else {
                        SelfCombineQuestionFragment.this.mAdapter.setList(list);
                    }
                } else if (SelfCombineQuestionFragment.this.isLoadMore) {
                    SelfCombineQuestionFragment.this.mRefreshLayout.finishLoadMore(false);
                    SelfCombineQuestionFragment.access$810(SelfCombineQuestionFragment.this);
                } else {
                    SelfCombineQuestionFragment.this.loadDataSuccess = false;
                    SelfCombineQuestionFragment.this.mRefreshLayout.finishRefresh(false);
                }
                SelfCombineQuestionFragment.this.mRecyclerView.post(new Runnable() { // from class: com.psychiatrygarden.fragmenthome.gd
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f15627c.lambda$onSuccess$0();
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
                SelfCombineQuestionFragment.this.loadDataSuccess = false;
                if (!SelfCombineQuestionFragment.this.isLoadMore) {
                    SelfCombineQuestionFragment.this.mRefreshLayout.finishRefresh(false);
                    return;
                }
                SelfCombineQuestionFragment.this.mRefreshLayout.finishLoadMore(false);
                SelfCombineQuestionFragment.access$810(SelfCombineQuestionFragment.this);
                if (SelfCombineQuestionFragment.this.page < 1) {
                    SelfCombineQuestionFragment.this.page = 1;
                }
            }
        }
    }

    public static /* synthetic */ int access$810(SelfCombineQuestionFragment selfCombineQuestionFragment) {
        int i2 = selfCombineQuestionFragment.page;
        selfCombineQuestionFragment.page = i2 - 1;
        return i2;
    }

    public static SelfCombineQuestionFragment getInstance(Bundle args) {
        SelfCombineQuestionFragment selfCombineQuestionFragment = new SelfCombineQuestionFragment();
        selfCombineQuestionFragment.setArguments(args);
        return selfCombineQuestionFragment;
    }

    private void getIntentData(String type, Context mContext) {
        if (TextUtils.isEmpty(UserConfig.getUserId())) {
            return;
        }
        if ("error".equals(type)) {
            pointCount(mContext, "5");
        } else if ("collection".equals(type)) {
            pointCount(mContext, "7");
        } else if ("note".equals(type)) {
            pointCount(mContext, Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
        } else if ("praise".equals(type)) {
            pointCount(mContext, Constants.VIA_REPORT_TYPE_JOININ_GROUP);
        } else if (ClientCookie.COMMENT_ATTR.equals(type)) {
            pointCount(mContext, Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
        }
        Intent intent = new Intent(mContext, (Class<?>) QuestionWCNHomeActivity.class);
        intent.putExtra(UriUtil.QUERY_CATEGORY, getArguments().getString(UriUtil.QUERY_CATEGORY));
        intent.putExtra("module_type", getArguments().getString("module_type"));
        intent.putExtra("type", type);
        intent.putExtra("identity_id", this.identity_id);
        mContext.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBanner(final RelativeLayout mRlQuestionTopAd, final Context mContext) {
        final HomepageSmallAdBean.DataDTO.DataAd dataAd = (HomepageSmallAdBean.DataDTO.DataAd) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.QUESTION_BANNER_AD, mContext, ""), HomepageSmallAdBean.DataDTO.DataAd.class);
        if (dataAd == null || dataAd.getAds().isEmpty()) {
            mRlQuestionTopAd.setVisibility(8);
            return;
        }
        if ((SharePreferencesUtils.readLongConfig("DISMESS_TIME_QUESTION_TOP_AD", mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig("DISMESS_TIME_QUESTION_TOP_AD", mContext, 0L).longValue()) / 1000) - dataAd.getTime_interval() : 0L) < 0) {
            return;
        }
        mRlQuestionTopAd.setVisibility(0);
        Banner banner = (Banner) mRlQuestionTopAd.findViewById(R.id.banner);
        ImageView imageView = (ImageView) mRlQuestionTopAd.findViewById(R.id.iv_ad_close);
        if (dataAd.getForce().equals("1")) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.dd
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SelfCombineQuestionFragment.lambda$initBanner$4(mContext, mRlQuestionTopAd, view);
                }
            });
        }
        int pxByDp = mContext.getResources().getDisplayMetrics().widthPixels - ScreenUtil.getPxByDp(mContext, 40);
        int i2 = pxByDp / 3;
        if (AndroidBaseUtils.isPad(mContext) && AndroidBaseUtils.isCurOriLand(mContext)) {
            i2 = pxByDp / 5;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mContext.getResources().getDisplayMetrics().widthPixels, i2);
        layoutParams.setMargins(0, ScreenUtil.getPxByDp(mContext, 10), 0, 0);
        banner.setLayoutParams(layoutParams);
        banner.addBannerLifecycleObserver(this).setLoopTime(5000L).setAdapter(new BannerQuestionAdsAdapter(dataAd.getAds())).setPageTransformer(new AlphaPageTransformer()).setIndicator(new CircleIndicator(mContext)).setIndicatorRadius(ScreenUtil.getPxByDp(mContext, 4)).setIndicatorNormalColor(Color.parseColor("#88FFFFFF")).setIndicatorSelectedColor(ContextCompat.getColor(mContext, R.color.app_theme_red));
        banner.setOnBannerListener(new OnBannerListener() { // from class: com.psychiatrygarden.fragmenthome.ed
            @Override // com.youth.banner.listener.OnBannerListener
            public final void OnBannerClick(Object obj, int i3) {
                this.f15571a.lambda$initBanner$5(dataAd, mContext, (HomepageSmallAdBean.DataDTO.DataAd.AdsDTO) obj, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initKingKongArea(LinearLayout llKingKongArea) {
        llKingKongArea.setVisibility(0);
        RecyclerView recyclerView = (RecyclerView) llKingKongArea.findViewById(R.id.rvKingKong);
        if (recyclerView.getAdapter() == null) {
            final KingKongAreaItemAdapter kingKongAreaItemAdapter = new KingKongAreaItemAdapter(R.layout.item_kingkong_area);
            ArrayList arrayList = new ArrayList();
            arrayList.add(KingKongItem.WRONG_QUESTION);
            arrayList.add(KingKongItem.COLLECT_QUESTION);
            arrayList.add(KingKongItem.NOTE_QUESTION);
            arrayList.add(KingKongItem.COMMENT_QUESTION);
            arrayList.add(KingKongItem.LIKE_QUESTION);
            recyclerView.setAdapter(kingKongAreaItemAdapter);
            kingKongAreaItemAdapter.setList(arrayList);
            kingKongAreaItemAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.fd
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    this.f15598c.lambda$initKingKongArea$6(kingKongAreaItemAdapter, baseQuickAdapter, view, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTopAd(final RelativeLayout mRlQuestionTopAdTwo) {
        HomepageSmallAdBean.DataDTO.BlackAds blackAds = (HomepageSmallAdBean.DataDTO.BlackAds) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.QUESTION_CARD_AD, mRlQuestionTopAdTwo.getContext(), ""), HomepageSmallAdBean.DataDTO.BlackAds.class);
        if (blackAds == null || blackAds.getAds() == null || blackAds.getAds().size() == 0) {
            mRlQuestionTopAdTwo.setVisibility(8);
            return;
        }
        mRlQuestionTopAdTwo.setVisibility(0);
        RecyclerView recyclerView = (RecyclerView) mRlQuestionTopAdTwo.findViewById(R.id.rv_question_ad_two);
        if (AndroidBaseUtils.isPad(mRlQuestionTopAdTwo.getContext()) && AndroidBaseUtils.isCurOriLand(mRlQuestionTopAdTwo.getContext())) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        QuestionTopAdTwoAdapter questionTopAdTwoAdapter = new QuestionTopAdTwoAdapter(blackAds.getAds());
        questionTopAdTwoAdapter.setOnItemActionLisenter(new QuestionTopAdTwoAdapter.OnItemJumpActionLisenter() { // from class: com.psychiatrygarden.fragmenthome.SelfCombineQuestionFragment.3
            @Override // com.psychiatrygarden.activity.online.adapter.QuestionTopAdTwoAdapter.OnItemJumpActionLisenter
            public void setItemLisenter(HomepageSmallAdBean.DataDTO.DataAd.AdsDTO.ExtraDTO data) {
                try {
                    SelfCombineQuestionFragment.this.pointCount(mRlQuestionTopAdTwo.getContext(), "2");
                    PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(data));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        recyclerView.setAdapter(questionTopAdTwoAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDataMoreOneScreen() {
        return this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollVertically(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initBanner$4(Context context, RelativeLayout relativeLayout, View view) {
        SharePreferencesUtils.writeLongConfig("DISMESS_TIME_QUESTION_TOP_AD", Long.valueOf(System.currentTimeMillis()), context);
        relativeLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBanner$5(HomepageSmallAdBean.DataDTO.DataAd dataAd, Context context, HomepageSmallAdBean.DataDTO.DataAd.AdsDTO adsDTO, int i2) {
        if (dataAd.getAds().isEmpty()) {
            return;
        }
        pointCount(context, "1");
        try {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(dataAd.getAds().get(i2).getExtra()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initKingKongArea$6(KingKongAreaItemAdapter kingKongAreaItemAdapter, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (TextUtils.isEmpty(UserConfig.getUserId())) {
            startActivity(new Intent(getContext(), (Class<?>) LoginActivity.class));
        } else {
            getIntentData(kingKongAreaItemAdapter.getItem(i2).getType(), getContext());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (isLogin()) {
            CombineQuestionRecordItem item = this.mAdapter.getItem(i2);
            Bundle bundle = new Bundle();
            bundle.putBoolean("fromQuestionCombine", true);
            bundle.putString("title", item.getTitle());
            bundle.putString("paperId", item.getPaperId());
            AnswerSheetActivity.gotoActivity(getContext(), bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(RefreshLayout refreshLayout) {
        this.page = 1;
        this.isLoadMore = false;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(RefreshLayout refreshLayout) {
        this.page++;
        this.isLoadMore = true;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setEmptyView$3(View view) {
        if (isLogin()) {
            startActivity(new Intent(requireContext(), (Class<?>) CombineQuestionActivity.class));
        }
    }

    private void loadData() {
        if (!UserConfig.isLogin()) {
            EventBus.getDefault().post(new ShowQuestionCombineEvent(false, isDataMoreOneScreen()));
            return;
        }
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page));
        ajaxParams.put("parent_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getContext()));
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, getContext(), "1"));
        YJYHttpUtils.get(getContext(), NetworkRequestsURL.questionCombineList, ajaxParams, new AnonymousClass2());
    }

    private void setEmptyView() {
        if (this.hasEmptyView) {
            return;
        }
        this.hasEmptyView = true;
        View viewInflate = View.inflate(getContext(), R.layout.layout_empty_combine_question, null);
        viewInflate.findViewById(R.id.iv_icon).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.zc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16168c.lambda$setEmptyView$3(view);
            }
        });
        this.mAdapter.setEmptyView(viewInflate);
    }

    public void getAdInfo() {
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.homePageSmallAdApi, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.SelfCombineQuestionFragment.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String json) {
                try {
                    HomepageSmallAdBean homepageSmallAdBean = (HomepageSmallAdBean) new Gson().fromJson(json, HomepageSmallAdBean.class);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.QUESTION_BANNER_AD, new Gson().toJson(homepageSmallAdBean.getData().getBanner()), SelfCombineQuestionFragment.this.getActivity());
                    SharePreferencesUtils.writeStrConfig(CommonParameter.QUESTION_CARD_AD, new Gson().toJson(homepageSmallAdBean.getData().getBlock_ad()), SelfCombineQuestionFragment.this.getActivity());
                    SharePreferencesUtils.writeStrConfig(CommonParameter.QUESTION_QUESTION_AD, new Gson().toJson(homepageSmallAdBean.getData().getQuestion_ad()), SelfCombineQuestionFragment.this.getActivity());
                    SelfCombineQuestionFragment selfCombineQuestionFragment = SelfCombineQuestionFragment.this;
                    selfCombineQuestionFragment.initBanner((RelativeLayout) selfCombineQuestionFragment.getViewHolder().get(R.id.rl_question_top_ad), SelfCombineQuestionFragment.this.getContext());
                    SelfCombineQuestionFragment selfCombineQuestionFragment2 = SelfCombineQuestionFragment.this;
                    selfCombineQuestionFragment2.initTopAd((RelativeLayout) selfCombineQuestionFragment2.getViewHolder().get(R.id.rl_question_top_ad_two));
                    SelfCombineQuestionFragment selfCombineQuestionFragment3 = SelfCombineQuestionFragment.this;
                    selfCombineQuestionFragment3.initKingKongArea((LinearLayout) selfCombineQuestionFragment3.getViewHolder().get(R.id.ll_question_top));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_self_combine_question;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.mAdapter = new HomeCombineQuestionListAdapter();
        if (getArguments() != null) {
            this.identity_id = getArguments().getString("identity_id", null);
        }
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.fragmenthome.ad
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f15445c.lambda$initViews$0(baseQuickAdapter, view, i2);
            }
        });
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.srl_refresh);
        this.mRefreshLayout = smartRefreshLayout;
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        this.mRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.fragmenthome.bd
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                this.f15480c.lambda$initViews$1(refreshLayout);
            }
        });
        this.mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.psychiatrygarden.fragmenthome.cd
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                this.f15511c.lambda$initViews$2(refreshLayout);
            }
        });
        RecyclerView recyclerView = (RecyclerView) holder.get(R.id.rvList);
        this.mRecyclerView = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.mRecyclerView.setAdapter(this.mAdapter);
        setEmptyView();
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.fragmenthome.SelfCombineQuestionFragment.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView2, int newState) {
                super.onScrollStateChanged(recyclerView2, newState);
                if (newState != 1 || SelfCombineQuestionFragment.this.isScrolled) {
                    return;
                }
                SelfCombineQuestionFragment.this.isScrolled = true;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView2, int dx, int dy) {
                super.onScrolled(recyclerView2, dx, dy);
                if (!SelfCombineQuestionFragment.this.mAdapter.getData().isEmpty() && SelfCombineQuestionFragment.this.isDataMoreOneScreen()) {
                    SelfCombineQuestionFragment.this.showShortCut = dy > 10;
                    if (dy <= 10 && dy >= -10) {
                        SelfCombineQuestionFragment.this.show = false;
                    } else {
                        SelfCombineQuestionFragment.this.show = true;
                        EventBus.getDefault().post(new ShowQuestionCombineEvent(true, dy > 10));
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NetworkEvent event) {
        if (!this.loadDataSuccess && event.isAvailable() && this.isVisibleToUser) {
            this.page = 1;
            loadData();
        }
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.isVisibleToUser = false;
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.isVisibleToUser = true;
        if (this.loadDataSuccess) {
            return;
        }
        this.isLoadMore = false;
        this.page = 1;
        loadData();
    }

    @Subscribe
    public void onEventMainThread(RefreshHomePaperListEvent event) {
        this.page = 1;
        this.isLoadMore = false;
        loadData();
    }

    @Subscribe
    public void onEventMainThread(RefreshPaperListEvent event) {
        List<CombineQuestionRecordItem> data = this.mAdapter.getData();
        if (data.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < data.size(); i2++) {
            if (TextUtils.equals(data.get(i2).getPaperId(), event.getPaperId())) {
                data.get(i2).setErrorCount(event.getErrorCount());
                data.get(i2).setRightCount(event.getRightCount());
                this.mAdapter.notifyItemChanged(i2);
                return;
            }
        }
    }
}
