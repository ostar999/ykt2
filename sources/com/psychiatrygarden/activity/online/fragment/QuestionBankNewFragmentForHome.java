package com.psychiatrygarden.activity.online.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.aliyun.vod.common.utils.UriUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.activity.online.AnswerSheetActivity;
import com.psychiatrygarden.activity.online.QuestionWCNHomeActivity;
import com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter;
import com.psychiatrygarden.activity.online.adapter.QuestionTopAdTwoAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import com.psychiatrygarden.activity.online.bean.QuestionListBean;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.BannerQuestionAdsAdapter;
import com.psychiatrygarden.adapter.KingKongAreaItemAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.bean.KingKongItem;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.LocateChapterEvent;
import com.psychiatrygarden.event.NetworkEvent;
import com.psychiatrygarden.event.QuestionBankRefreshEvent;
import com.psychiatrygarden.event.RedoOtherQuestionEvent;
import com.psychiatrygarden.event.RefreshQuestionBankEvent;
import com.psychiatrygarden.event.ShowFilterYearEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.LocalBroadcastManager;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.QuestionYearFilterDialog;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AlphaPageTransformer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import okhttp3.HttpUrl;
import org.apache.http.cookie.ClientCookie;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NotifyDataSetChanged"})
/* loaded from: classes5.dex */
public class QuestionBankNewFragmentForHome extends BaseFragment implements QuestionBankNewAdapter.JumpToQList, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    public QuestionBankNewAdapter adapter;
    private LinearLayout customer_ly_loading;
    public List<QuestionBankNewBean.DataBean> dataList = new ArrayList();
    private boolean isRefresh;
    private boolean isVisibleToUser;
    private String is_show_number;
    private boolean loadDataSuccess;
    private ImageView loadingView;
    private TextView mBtnUpdateYear;
    private String mCategory;
    private String mCategoryId;
    private String mIdentityId;
    private String mIdentityTitle;
    private View mLoadDataFailView;
    private LinearLayout mLyYearFilter;
    private String mModuleType;
    private TextView mTvFilterYear;
    private String mType;
    private String mUnitId;
    private String question_bank_id;
    public RecyclerView recyexpend;

    /* JADX WARN: Removed duplicated region for block: B:130:0x035e  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0388 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.psychiatrygarden.bean.NextChapterInfo checkHasNextChapter(java.lang.String r18, com.chad.library.adapter.base.entity.node.BaseNode r19) {
        /*
            Method dump skipped, instructions count: 906
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragmentForHome.checkHasNextChapter(java.lang.String, com.chad.library.adapter.base.entity.node.BaseNode):com.psychiatrygarden.bean.NextChapterInfo");
    }

    private void extracted(String parent_title) {
        ProjectApp.identity_title = this.mIdentityTitle;
        ProjectApp.unit_title = "";
        ProjectApp.unit_id = "";
        ProjectApp.exam_title = "";
        ProjectApp.chapter_title = "";
        ProjectApp.chapter_id = "";
        ProjectApp.chapter_parent_title = parent_title;
    }

    private String getArgumentValue(String key) {
        Bundle arguments = getArguments();
        return arguments != null ? arguments.getString(key, "") : "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<BaseNode> getEntity(List<QuestionBankNewBean.DataBean> dataList) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < dataList.size(); i2++) {
            ArrayList arrayList2 = new ArrayList();
            for (int i3 = 0; i3 < dataList.get(i2).getChildren().size(); i3++) {
                arrayList2.add(new QuestionBankNewSecondBean(dataList.get(i2).getChildren().get(i3)));
            }
            QuestionBankNewFristBean questionBankNewFristBean = new QuestionBankNewFristBean(arrayList2, dataList.get(i2));
            try {
                if (!this.adapter.getData().isEmpty() && (this.adapter.getData().get(i2) instanceof QuestionBankNewFristBean)) {
                    if (UserConfig.isLogin()) {
                        questionBankNewFristBean.setExpanded(((QuestionBankNewFristBean) this.adapter.getData().get(i2)).getIsExpanded());
                    } else {
                        questionBankNewFristBean.setExpanded(false);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            arrayList.add(questionBankNewFristBean);
        }
        return arrayList;
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
        intent.putExtra(UriUtil.QUERY_CATEGORY, this.mCategory);
        intent.putExtra("module_type", this.mModuleType);
        intent.putExtra("type", type);
        intent.putExtra("export_func_identity_id", getArgumentValue("export_func_identity_id"));
        intent.putExtra("identity_id", this.mIdentityId);
        mContext.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideLoadingView() {
        this.loadingView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.s
            @Override // java.lang.Runnable
            public final void run() {
                this.f13331c.lambda$hideLoadingView$1();
            }
        }, 100L);
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
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.t
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    QuestionBankNewFragmentForHome.lambda$initBanner$5(mContext, mRlQuestionTopAd, view);
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
        banner.setOnBannerListener(new OnBannerListener() { // from class: com.psychiatrygarden.activity.online.fragment.u
            @Override // com.youth.banner.listener.OnBannerListener
            public final void OnBannerClick(Object obj, int i3) {
                this.f13351a.lambda$initBanner$6(dataAd, mContext, (HomepageSmallAdBean.DataDTO.DataAd.AdsDTO) obj, i3);
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
            kingKongAreaItemAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.p
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    this.f13304c.lambda$initKingKongArea$7(kingKongAreaItemAdapter, baseQuickAdapter, view, i2);
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
        questionTopAdTwoAdapter.setOnItemActionLisenter(new QuestionTopAdTwoAdapter.OnItemJumpActionLisenter() { // from class: com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragmentForHome.6
            @Override // com.psychiatrygarden.activity.online.adapter.QuestionTopAdTwoAdapter.OnItemJumpActionLisenter
            public void setItemLisenter(HomepageSmallAdBean.DataDTO.DataAd.AdsDTO.ExtraDTO data) {
                try {
                    QuestionBankNewFragmentForHome.this.pointCount(mRlQuestionTopAdTwo.getContext(), "2");
                    PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(data));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        recyclerView.setAdapter(questionTopAdTwoAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideLoadingView$1() {
        AnimationDrawable animationDrawable = (AnimationDrawable) this.loadingView.getBackground();
        if (animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
        this.customer_ly_loading.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initBanner$5(Context context, RelativeLayout relativeLayout, View view) {
        SharePreferencesUtils.writeLongConfig("DISMESS_TIME_QUESTION_TOP_AD", Long.valueOf(System.currentTimeMillis()), context);
        relativeLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initBanner$6(HomepageSmallAdBean.DataDTO.DataAd dataAd, Context context, HomepageSmallAdBean.DataDTO.DataAd.AdsDTO adsDTO, int i2) {
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
    public /* synthetic */ void lambda$initKingKongArea$7(KingKongAreaItemAdapter kingKongAreaItemAdapter, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (TextUtils.isEmpty(UserConfig.getUserId())) {
            startActivity(new Intent(getContext(), (Class<?>) LoginActivity.class));
        } else {
            getIntentData(kingKongAreaItemAdapter.getItem(i2).getType(), getContext());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) throws JSONException {
        showProgressDialog();
        getQuestionBankData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        showFilterYearDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLoadingView$0() {
        AnimationDrawable animationDrawable = (AnimationDrawable) this.loadingView.getBackground();
        animationDrawable.setVisible(true, true);
        if (animationDrawable.isRunning()) {
            return;
        }
        animationDrawable.start();
    }

    public static QuestionBankNewFragmentForHome newInstance(Bundle args) {
        QuestionBankNewFragmentForHome questionBankNewFragmentForHome = new QuestionBankNewFragmentForHome();
        questionBankNewFragmentForHome.setArguments(args);
        return questionBankNewFragmentForHome;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshYearFilter() {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.FILTER_YEAR_TO_QUETION_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.mCategory, getActivity(), "");
        String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.FILTER_YEAR_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.mCategory, getActivity(), "");
        String strConfig3 = SharePreferencesUtils.readStrConfig(CommonParameter.SEARCH_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.mCategory, getActivity(), new JSONArray().toString());
        StringBuilder sb = new StringBuilder();
        sb.append("筛选条件：");
        sb.append(strConfig3);
        Log.e("json_array", sb.toString());
        if (!UserConfig.isLogin() || TextUtils.isEmpty(strConfig) || TextUtils.isEmpty(strConfig3) || strConfig3.equals(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI)) {
            this.mLyYearFilter.setVisibility(8);
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.SEARCH_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.mCategory, getActivity(), new JSONArray().toString()));
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                String strOptString = jSONArray.optJSONObject(i2).optString("field");
                String strOptString2 = jSONArray.optJSONObject(i2).optString("id");
                if (strOptString.equals("year")) {
                    if (strOptString2.equals("-1")) {
                        ProjectApp.isHaveFilter = false;
                        this.mLyYearFilter.setVisibility(8);
                    } else {
                        ProjectApp.isHaveFilter = true;
                        this.mLyYearFilter.setVisibility(0);
                        this.mTvFilterYear.setText(strConfig2);
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmptyView(boolean isError) {
        QuestionBankNewAdapter questionBankNewAdapter = this.adapter;
        if (questionBankNewAdapter == null) {
            return;
        }
        if (isError) {
            questionBankNewAdapter.setEmptyView(this.mLoadDataFailView);
        } else {
            questionBankNewAdapter.setEmptyView(R.layout.layout_empty_view);
        }
    }

    private void showFilterYearDialog() {
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.FILTER_YEAR_TO_QUETION_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.mCategory, getActivity(), "");
        if (TextUtils.isEmpty(strConfig)) {
            return;
        }
        final QuestionYearFilterDialog questionYearFilterDialog = new QuestionYearFilterDialog(this.mContext, (List) new Gson().fromJson(strConfig, new TypeToken<List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO>>() { // from class: com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragmentForHome.2
        }.getType()));
        questionYearFilterDialog.setOnItemActionLisenter(new QuestionYearFilterDialog.OnItemClickLisenter() { // from class: com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragmentForHome.3
            @Override // com.psychiatrygarden.widget.QuestionYearFilterDialog.OnItemClickLisenter
            public void setOnItemClick(String value, List<QuestionListBean.DataDTO.SearchDTO.SearchDataDTO> yearData, QuestionListBean.DataDTO.SearchDTO.SearchDataDTO item) throws JSONException {
                try {
                    JSONArray jSONArray = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.SEARCH_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) QuestionBankNewFragmentForHome.this).mContext) + QuestionBankNewFragmentForHome.this.mCategory, QuestionBankNewFragmentForHome.this.getActivity(), new JSONArray().toString()));
                    JSONArray jSONArray2 = new JSONArray();
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        String strOptString = jSONArray.optJSONObject(i2).optString("field");
                        String strOptString2 = jSONArray.optJSONObject(i2).optString("id");
                        JSONObject jSONObject = new JSONObject();
                        if (strOptString.equals("year")) {
                            ProjectApp.isHaveFilter = true;
                            if (item.getId().equals("-1")) {
                                ProjectApp.isHaveFilter = false;
                                QuestionBankNewFragmentForHome.this.mLyYearFilter.setVisibility(8);
                            }
                            jSONObject.put("field", "year");
                            jSONObject.put("id", item.getId());
                            if (item.getId().equals("free_year")) {
                                jSONObject.put("free_year", value);
                            }
                            jSONArray2.put(jSONObject);
                        } else {
                            jSONObject.put("field", strOptString);
                            jSONObject.put("id", strOptString2);
                            jSONArray2.put(jSONObject);
                        }
                    }
                    if (!QuestionBankNewFragmentForHome.this.mCategory.equals("unit")) {
                        Log.e("json_array", "jsonArrayNew：" + jSONArray2.toString());
                        SharePreferencesUtils.writeStrConfig(CommonParameter.SEARCH_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) QuestionBankNewFragmentForHome.this).mContext) + QuestionBankNewFragmentForHome.this.mCategory, jSONArray2.toString(), QuestionBankNewFragmentForHome.this.getActivity());
                        QuestionBankNewFragmentForHome.this.getQuestionBankData();
                    }
                    SharePreferencesUtils.writeStrConfig(CommonParameter.FILTER_YEAR_TO_QUETION_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) QuestionBankNewFragmentForHome.this).mContext) + QuestionBankNewFragmentForHome.this.mCategory, new Gson().toJson(yearData), QuestionBankNewFragmentForHome.this.getActivity());
                    SharePreferencesUtils.writeStrConfig(CommonParameter.FILTER_YEAR_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) QuestionBankNewFragmentForHome.this).mContext) + QuestionBankNewFragmentForHome.this.mCategory, value, QuestionBankNewFragmentForHome.this.getActivity());
                    QuestionBankNewFragmentForHome.this.mTvFilterYear.setText(value);
                    questionYearFilterDialog.dismissNoAnimaltion();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        questionYearFilterDialog.show();
    }

    private void showLoadingView() {
        this.customer_ly_loading.setVisibility(0);
        this.loadingView.post(new Runnable() { // from class: com.psychiatrygarden.activity.online.fragment.r
            @Override // java.lang.Runnable
            public final void run() {
                this.f13323c.lambda$showLoadingView$0();
            }
        });
    }

    public void getAdInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, getActivity()));
        YJYHttpUtils.get(getActivity(), NetworkRequestsURL.homePageSmallAdApi, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragmentForHome.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String json) {
                try {
                    HomepageSmallAdBean homepageSmallAdBean = (HomepageSmallAdBean) new Gson().fromJson(json, HomepageSmallAdBean.class);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.QUESTION_BANNER_AD, new Gson().toJson(homepageSmallAdBean.getData().getBanner()), QuestionBankNewFragmentForHome.this.getActivity());
                    SharePreferencesUtils.writeStrConfig(CommonParameter.QUESTION_CARD_AD, new Gson().toJson(homepageSmallAdBean.getData().getBlock_ad()), QuestionBankNewFragmentForHome.this.getActivity());
                    SharePreferencesUtils.writeStrConfig(CommonParameter.QUESTION_QUESTION_AD, new Gson().toJson(homepageSmallAdBean.getData().getQuestion_ad()), QuestionBankNewFragmentForHome.this.getActivity());
                    QuestionBankNewFragmentForHome questionBankNewFragmentForHome = QuestionBankNewFragmentForHome.this;
                    questionBankNewFragmentForHome.initBanner((RelativeLayout) questionBankNewFragmentForHome.getViewHolder().get(R.id.rl_question_top_ad), QuestionBankNewFragmentForHome.this.getContext());
                    QuestionBankNewFragmentForHome questionBankNewFragmentForHome2 = QuestionBankNewFragmentForHome.this;
                    questionBankNewFragmentForHome2.initTopAd((RelativeLayout) questionBankNewFragmentForHome2.getViewHolder().get(R.id.rl_question_top_ad_two));
                    QuestionBankNewFragmentForHome questionBankNewFragmentForHome3 = QuestionBankNewFragmentForHome.this;
                    questionBankNewFragmentForHome3.initKingKongArea((LinearLayout) questionBankNewFragmentForHome3.getViewHolder().get(R.id.ll_question_top));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void getExCo() {
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_question_bank_for_home;
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void getPassData(final String activity_id) {
        if (isLogin()) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + activity_id);
            MemInterface.getInstance().getMemData(getActivity(), ajaxParams, false, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.online.fragment.q
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f13314a.lambda$getPassData$4(activity_id);
                }
            });
        }
    }

    public void getQuestionBankData() throws JSONException {
        JSONArray jSONArray;
        showLoadingView();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(UriUtil.QUERY_CATEGORY, this.mCategory);
        ajaxParams.put("identity_id", this.mIdentityId);
        ajaxParams.put("type", this.mType);
        ajaxParams.put("unit_id", this.mUnitId);
        ajaxParams.put("module_type", this.mModuleType);
        try {
            if ("unit".equals(this.mCategory)) {
                ajaxParams.put("am_pm", "0");
                ajaxParams.put("unit_id", this.mUnitId);
                jSONArray = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.SEARCH_QUESTION_UNIT_ID, getActivity(), new JSONArray().toString()));
            } else {
                ajaxParams.put("am_pm", "" + SharePreferencesUtils.readStrConfig(CommonParameter.am_pm, getActivity()));
                jSONArray = new JSONArray(SharePreferencesUtils.readStrConfig(CommonParameter.SEARCH_QUESTION_ID + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.mCategory, getActivity(), new JSONArray().toString()));
            }
            JSONArray jSONArray2 = new JSONArray();
            Log.e("json_array", "调用接口：" + jSONArray.toString());
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                String strOptString = jSONArray.optJSONObject(i2).optString("field");
                String strOptString2 = jSONArray.optJSONObject(i2).optString("id");
                if (!strOptString.equals("pattern") && !strOptString2.equals("-1")) {
                    if (strOptString2.equals("free_year")) {
                        jSONArray.getJSONObject(i2).put("id", jSONArray.optJSONObject(i2).optString("free_year"));
                    }
                    jSONArray2.put(jSONArray.get(i2));
                }
            }
            ajaxParams.put("search_where", jSONArray2.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        String str = NetworkRequestsURL.getchapterApi;
        if ("unit".equals(this.mCategory)) {
            str = NetworkRequestsURL.getIdentityChapter;
        }
        YJYHttpUtils.post(getActivity(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragmentForHome.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuestionBankNewFragmentForHome.this.hideProgressDialog();
                EventBus.getDefault().post(new QuestionBankRefreshEvent(false));
                QuestionBankNewFragmentForHome.this.setEmptyView(true);
                QuestionBankNewFragmentForHome.this.hideLoadingView();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                String str2;
                String str3;
                super.onSuccess((AnonymousClass4) s2);
                QuestionBankNewFragmentForHome.this.hideProgressDialog();
                try {
                    QuestionBankNewBean questionBankNewBean = (QuestionBankNewBean) new Gson().fromJson(s2, QuestionBankNewBean.class);
                    if (questionBankNewBean.getHave_year().equals("0")) {
                        SharePreferencesUtils.removeConfig(CommonParameter.FILTER_YEAR_TO_QUETION_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) QuestionBankNewFragmentForHome.this).mContext) + QuestionBankNewFragmentForHome.this.mCategory, ((BaseFragment) QuestionBankNewFragmentForHome.this).mContext);
                        SharePreferencesUtils.removeConfig(CommonParameter.FILTER_YEAR_DATA + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) QuestionBankNewFragmentForHome.this).mContext) + QuestionBankNewFragmentForHome.this.mCategory, ((BaseFragment) QuestionBankNewFragmentForHome.this).mContext);
                        SharePreferencesUtils.removeConfig(CommonParameter.SEARCH_QUESTION_ID, ((BaseFragment) QuestionBankNewFragmentForHome.this).mContext);
                        QuestionBankNewFragmentForHome.this.mLyYearFilter.setVisibility(8);
                    }
                    if ("200".equals(questionBankNewBean.getCode())) {
                        QuestionBankNewFragmentForHome.this.loadDataSuccess = true;
                        if (questionBankNewBean.getData() == null || questionBankNewBean.getData().size() <= 0) {
                            QuestionBankNewFragmentForHome.this.setEmptyView(false);
                        } else {
                            QuestionBankNewFragmentForHome.this.dataList = questionBankNewBean.getData();
                            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.LAST_DO_QUESTION + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ((BaseFragment) QuestionBankNewFragmentForHome.this).mContext) + QuestionBankNewFragmentForHome.this.mCategory, ((BaseFragment) QuestionBankNewFragmentForHome.this).mContext, "");
                            if (strConfig.contains("-")) {
                                String[] strArrSplit = strConfig.split("-");
                                str2 = strArrSplit[0];
                                str3 = strArrSplit[1];
                                Log.e("save_question_ids", "parentId=" + str2 + ";chapterId=" + str3 + ";questionId=" + strArrSplit[2]);
                            } else {
                                str2 = "";
                                str3 = str2;
                            }
                            for (int i3 = 0; i3 < QuestionBankNewFragmentForHome.this.dataList.size(); i3++) {
                                QuestionBankNewFragmentForHome.this.dataList.get(i3).question_bank_id = QuestionBankNewFragmentForHome.this.question_bank_id;
                                ArrayList arrayList = new ArrayList();
                                if (QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren() != null && QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren().size() > 0) {
                                    for (int i4 = 0; i4 < QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren().size(); i4++) {
                                        QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren().get(i4).setCategory(QuestionBankNewFragmentForHome.this.mCategory);
                                        QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren().get(i4).question_bank_id = QuestionBankNewFragmentForHome.this.question_bank_id;
                                        QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren().get(i4).setPrimary_p_id(QuestionBankNewFragmentForHome.this.dataList.get(i3).getPrimary_id() + "");
                                        QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren().get(i4).setType(QuestionBankNewFragmentForHome.this.mType);
                                        QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren().get(i4).setGroupPosition(i3);
                                        QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren().get(i4).setParent_title(QuestionBankNewFragmentForHome.this.dataList.get(i3).getTitle());
                                        if (str3.equals(QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren().get(i4).getChapter_id())) {
                                            QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren().get(i4).setShowContinue(true);
                                        }
                                        arrayList.add(QuestionBankNewFragmentForHome.this.dataList.get(i3).getChildren().get(i4));
                                    }
                                    if (QuestionBankNewFragmentForHome.this.dataList.get(i3).getChapter_id().equals(str2)) {
                                        QuestionBankNewFragmentForHome.this.dataList.get(i3).setShowContinue(true);
                                    }
                                } else if (QuestionBankNewFragmentForHome.this.dataList.get(i3).getChapter_id().equals(str3)) {
                                    QuestionBankNewFragmentForHome.this.dataList.get(i3).setShowContinue(true);
                                }
                                QuestionBankNewFragmentForHome.this.dataList.get(i3).setChildren(arrayList);
                                QuestionBankNewFragmentForHome.this.dataList.get(i3).setType(QuestionBankNewFragmentForHome.this.mType);
                                if (!QuestionBankNewFragmentForHome.this.mType.equals("all")) {
                                    QuestionBankNewFragmentForHome.this.dataList.get(i3).setPass("1");
                                }
                            }
                            QuestionBankNewFragmentForHome questionBankNewFragmentForHome = QuestionBankNewFragmentForHome.this;
                            questionBankNewFragmentForHome.adapter.setList(questionBankNewFragmentForHome.getEntity(questionBankNewFragmentForHome.dataList));
                        }
                        EventBus.getDefault().post(new QuestionBankRefreshEvent(true));
                    } else if ("202".equals(questionBankNewBean.getCode())) {
                        if (QuestionBankNewFragmentForHome.this.mCategory.equals("year")) {
                            QuestionBankNewFragmentForHome.this.dataList.clear();
                            QuestionBankNewFragmentForHome questionBankNewFragmentForHome2 = QuestionBankNewFragmentForHome.this;
                            questionBankNewFragmentForHome2.adapter.setList(questionBankNewFragmentForHome2.getEntity(questionBankNewFragmentForHome2.dataList));
                        }
                        QuestionBankNewFragmentForHome.this.setEmptyView(true);
                        EventBus.getDefault().post(new QuestionBankRefreshEvent(false));
                    }
                    QuestionBankNewFragmentForHome.this.hideLoadingView();
                } catch (Exception e3) {
                    e3.printStackTrace();
                    QuestionBankNewFragmentForHome.this.setEmptyView(true);
                    QuestionBankNewFragmentForHome.this.loadDataSuccess = false;
                    QuestionBankNewFragmentForHome.this.hideLoadingView();
                    EventBus.getDefault().post(new QuestionBankRefreshEvent(false));
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.is_show_number = getArgumentValue("is_show_number");
        this.recyexpend = (RecyclerView) holder.get(R.id.recyexpend);
        this.mLyYearFilter = (LinearLayout) holder.get(R.id.ly_year_filter);
        this.mBtnUpdateYear = (TextView) holder.get(R.id.btn_update_year);
        this.mTvFilterYear = (TextView) holder.get(R.id.tv_year);
        this.loadingView = (ImageView) holder.get(R.id.iv_loading);
        this.customer_ly_loading = (LinearLayout) holder.get(R.id.customer_ly_loading);
        if (1 == SkinManager.getCurrentSkinType(getActivity())) {
            this.loadingView.setBackgroundResource(R.drawable.loading_night_customer);
        }
        View viewInflate = View.inflate(this.mContext, R.layout.layout_loaddata_fail_view, null);
        this.mLoadDataFailView = viewInflate;
        ((TextView) viewInflate.findViewById(R.id.btn_reload)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.n
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws JSONException {
                this.f13289c.lambda$initViews$2(view);
            }
        });
        this.mType = getArgumentValue("type");
        this.mCategory = getArgumentValue(UriUtil.QUERY_CATEGORY);
        this.mModuleType = getArgumentValue("module_type");
        this.mIdentityId = getArgumentValue("identity_id");
        this.mIdentityTitle = getArgumentValue("identity_title");
        this.mUnitId = getArgumentValue("unit_id");
        this.mCategoryId = getArgumentValue("category_id");
        this.question_bank_id = getArgumentValue("question_bank_id");
        this.recyexpend.setLayoutManager(new LinearLayoutManager(getActivity()));
        QuestionBankNewAdapter questionBankNewAdapter = new QuestionBankNewAdapter(this);
        this.adapter = questionBankNewAdapter;
        questionBankNewAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom);
        this.recyexpend.setAdapter(this.adapter);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refreshPersonal");
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(new BroadcastReceiver() { // from class: com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragmentForHome.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) throws JSONException {
                if ("refreshPersonal".equals(intent.getAction())) {
                    QuestionBankNewFragmentForHome.this.refreshYearFilter();
                    if (QuestionBankNewFragmentForHome.this.loadDataSuccess) {
                        QuestionBankNewFragmentForHome.this.getQuestionBankData();
                    }
                }
            }
        }, intentFilter);
        refreshYearFilter();
        this.mBtnUpdateYear.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.fragment.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13297c.lambda$initViews$3(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void mChildSelectClumn(int groupPosition, int childPosition) {
        setRefulData(groupPosition);
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void mGroupSelectClumn(int groupPosition) {
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void mJumpToQList(String primary_id, String unit, String parent_title, String title) {
        if (isLogin()) {
            extracted(parent_title);
            Bundle bundle = new Bundle();
            bundle.putString("primary_id", primary_id);
            bundle.putString("question_bank_id", this.question_bank_id);
            bundle.putString("unit", unit);
            bundle.putString("unit_id", "" + this.mUnitId);
            bundle.putString("category_id", this.mCategoryId);
            bundle.putString(UriUtil.QUERY_CATEGORY, "" + this.mCategory);
            bundle.putString("module_type", this.mModuleType);
            bundle.putString("type", this.mType);
            bundle.putString("subject_title", "" + parent_title);
            bundle.putString("chapter_title", "" + title);
            bundle.putString("is_show_number", this.is_show_number);
            bundle.putString("identity_id", this.mIdentityId);
            AnswerSheetActivity.gotoActivity(getActivity(), bundle);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String str) throws JSONException {
        if (str.equals(EventBusConstant.EVENT_PHONE_CHANGE_SUCCESS)) {
            getQuestionBankData();
        }
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
    public void onRefresh() throws JSONException {
        getQuestionBankData();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onResume() throws JSONException {
        super.onResume();
        this.isVisibleToUser = true;
        if (this.loadDataSuccess) {
            return;
        }
        getQuestionBankData();
    }

    /* renamed from: setPassData, reason: merged with bridge method [inline-methods] */
    public void lambda$getPassData$4(String activityid) {
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            if (this.dataList.get(i2) != null) {
                QuestionBankNewBean.DataBean dataBean = this.dataList.get(i2);
                if (activityid.equals(dataBean.getActivity_id() + "")) {
                    dataBean.setPass("1");
                }
            }
        }
        this.adapter.notifyDataSetChanged();
    }

    public void setRefulData(int groupPosition) {
        for (int i2 = 0; i2 < this.dataList.get(groupPosition).getChildren().size(); i2++) {
            if (this.dataList.get(groupPosition).getChildren().get(i2).getIsSelected() != 1) {
                this.dataList.get(groupPosition).setIsSelected(0);
                return;
            }
        }
        this.dataList.get(groupPosition).setIsSelected(1);
        for (int i3 = 0; i3 < this.dataList.size(); i3++) {
            if (this.dataList.get(i3) != null && this.dataList.get(i3).getIsSelected() != 1) {
                return;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NetworkEvent event) throws JSONException {
        if (!this.loadDataSuccess && event.isAvailable() && this.isVisibleToUser) {
            getQuestionBankData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ShowFilterYearEvent event) {
        if (event.category.equals(this.mCategory)) {
            showFilterYearDialog();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RedoOtherQuestionEvent event) {
        String str;
        if (event.getIsLastDo()) {
            String str2 = "";
            String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.LAST_DO_QUESTION + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + this.mCategory, this.mContext, "");
            if (strConfig.contains("-")) {
                String[] strArrSplit = strConfig.split("-");
                str2 = strArrSplit[0];
                str = strArrSplit[1];
                Log.e("save_question_ids", "parentId=" + str2 + ";chapterId=" + str + ";questionId=" + strArrSplit[2]);
            } else {
                str = "";
            }
            for (int i2 = 0; i2 < this.dataList.size(); i2++) {
                if (this.dataList.get(i2).getChapter_id().equals(str2)) {
                    for (int i3 = 0; i3 < this.dataList.get(i2).getChildren().size(); i3++) {
                        if (this.dataList.get(i2).getChildren().get(i3).getChapter_id().equals(str)) {
                            this.dataList.get(i2).getChildren().get(i3).setShowContinue(true);
                        } else {
                            this.dataList.get(i2).getChildren().get(i3).setShowContinue(false);
                        }
                    }
                }
            }
            this.adapter.notifyDataSetChanged();
        }
    }

    @Override // com.psychiatrygarden.activity.online.adapter.QuestionBankNewAdapter.JumpToQList
    public void mJumpToQList(String primary_id, String unit, String parent_title, String title, BaseNode node) {
        extracted(parent_title);
        Bundle bundle = new Bundle();
        ProjectApp.saveNChapterInfo(checkHasNextChapter(primary_id, node));
        bundle.putString("primary_id", primary_id);
        bundle.putString("question_bank_id", this.question_bank_id);
        bundle.putString("unit", unit);
        bundle.putString("unit_id", "" + this.mUnitId);
        bundle.putString("category_id", this.mCategoryId);
        bundle.putString(UriUtil.QUERY_CATEGORY, "" + this.mCategory);
        bundle.putString("module_type", this.mModuleType);
        bundle.putString("type", this.mType);
        bundle.putString("subject_title", "" + parent_title);
        bundle.putString("chapter_title", "" + title);
        bundle.putString("is_show_number", this.is_show_number);
        bundle.putString("identity_id", this.mIdentityId);
        AnswerSheetActivity.gotoActivity(getActivity(), bundle);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0047  */
    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onEventMainThread(com.psychiatrygarden.bean.EventBusMessage r12) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 888
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragmentForHome.onEventMainThread(com.psychiatrygarden.bean.EventBusMessage):void");
    }

    @Subscribe
    public void onEventMainThread(RefreshQuestionBankEvent e2) throws JSONException {
        if (TextUtils.equals(e2.getIdentity_id(), this.mIdentityId)) {
            getQuestionBankData();
        }
    }

    @Subscribe
    public void onEventMainThread(LocateChapterEvent event) {
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list == null || list.isEmpty()) {
            return;
        }
        String primaryId = event.getPrimaryId();
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            List<QuestionBankNewBean.DataBean.ChildrenBean> children = this.dataList.get(i2).getChildren();
            if (children != null && children.size() > 0) {
                Iterator<QuestionBankNewBean.DataBean.ChildrenBean> it = children.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (TextUtils.equals(it.next().primary_id, primaryId)) {
                            this.adapter.expandOrCollapse(i2);
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
