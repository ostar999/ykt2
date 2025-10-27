package com.psychiatrygarden.activity.mine.errorquestion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.common.utils.UriUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.mine.errorquestion.adapter.QuestionBankNew2Adapter;
import com.psychiatrygarden.activity.online.AnswerSheetActivity;
import com.psychiatrygarden.activity.online.QuestionWCNHomeActivity;
import com.psychiatrygarden.activity.online.adapter.QuestionTopAdTwoAdapter;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewFristBean;
import com.psychiatrygarden.activity.online.bean.QuestionBankNewSecondBean;
import com.psychiatrygarden.activity.online.fragment.QuestionBankNewFragment;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.adapter.BannerQuestionAdsAdapter;
import com.psychiatrygarden.adapter.KingKongAreaItemAdapter;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.EventBusMessage;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.bean.KingKongItem;
import com.psychiatrygarden.bean.NextChapterInfo;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.NetworkEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.yikaobang.yixue.R;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AlphaPageTransformer;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.helper.StringUtil;

@SuppressLint({"NotifyDataSetChanged"})
/* loaded from: classes5.dex */
public class QuestionBankNew2Fragment extends BaseFragment implements QuestionBankNew2Adapter.JumpToQList, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    public QuestionBankNew2Adapter adapter;
    private ImageView checkeddown;
    private TextView exportTxt;
    private boolean isVisibleToUser;
    private String is_show_number;
    private ImageView ivBack;
    private RelativeLayout layoutSelectExport;
    private LinearLayout linexportview;
    private LinearLayout ll_question_top;
    private boolean loadDataSuccess;
    private String mCategory;
    private String mCategoryId;
    private String mExportFuncIdentityId;
    private String mIdentityId;
    private View mLoadDataFailView;
    private String mModuleType;
    public SmartRefreshLayout mRefresh;
    private TextView mTvChecked;
    private String mType;
    private String mUnitId;
    public RecyclerView recyexpend;
    private RelativeLayout rl_question_top_ad;
    private RelativeLayout rl_question_top_ad_two;
    private TextView tvAllSelect;
    private TextView tvTitle;
    public List<QuestionBankNewBean.DataBean> dataList = new ArrayList();
    private int selectCount = 0;
    private int allCount = 0;

    /* JADX WARN: Removed duplicated region for block: B:131:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0390 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.psychiatrygarden.bean.NextChapterInfo checkHasNextChapter(java.lang.String r17, com.chad.library.adapter.base.entity.node.BaseNode r18) {
        /*
            Method dump skipped, instructions count: 914
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.mine.errorquestion.QuestionBankNew2Fragment.checkHasNextChapter(java.lang.String, com.chad.library.adapter.base.entity.node.BaseNode):com.psychiatrygarden.bean.NextChapterInfo");
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
                    questionBankNewFristBean.setExpanded(((QuestionBankNewFristBean) this.adapter.getData().get(i2)).getIsExpanded());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            arrayList.add(questionBankNewFristBean);
        }
        return arrayList;
    }

    private void getSelectCount() {
        List<QuestionBankNewBean.DataBean> list;
        this.allCount = 0;
        this.selectCount = 0;
        if ((this.mType.contains("error") || this.mType.contains("collection") || this.mType.contains("note")) && (list = this.dataList) != null && !list.isEmpty()) {
            for (int i2 = 0; i2 < this.dataList.size(); i2++) {
                if (this.dataList.get(i2) != null) {
                    QuestionBankNewBean.DataBean dataBean = this.dataList.get(i2);
                    if (dataBean.getChildren() != null && !dataBean.getChildren().isEmpty()) {
                        for (int i3 = 0; i3 < dataBean.getChildren().size(); i3++) {
                            QuestionBankNewBean.DataBean.ChildrenBean childrenBean = dataBean.getChildren().get(i3);
                            if (childrenBean.isSelected == 1) {
                                this.selectCount += childrenBean.count;
                            }
                            this.allCount += childrenBean.count;
                        }
                    } else if (dataBean.isSelected == 1) {
                        int i4 = this.selectCount;
                        int i5 = dataBean.count;
                        this.selectCount = i4 + i5;
                        this.allCount += i5;
                    }
                }
            }
            this.adapter.notifyDataSetChanged();
        }
        updateSelectCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addADView$9(HomepageSmallAdBean.DataDTO.DataAd dataAd, HomepageSmallAdBean.DataDTO.DataAd.AdsDTO adsDTO, int i2) {
        if (dataAd.getAds().isEmpty()) {
            return;
        }
        try {
            PublicMethodActivity.getInstance().mToActivity(new Gson().toJson(dataAd.getAds().get(i2).getExtra()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addView$8(KingKongAreaItemAdapter kingKongAreaItemAdapter, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        KingKongItem item = kingKongAreaItemAdapter.getItem(i2);
        if (item.getType().equals("COMBINATION")) {
            return;
        }
        getIntentData(item.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        this.mRefresh.autoRefreshAnimationOnly();
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.errorquestion.g
            @Override // java.lang.Runnable
            public final void run() throws JSONException {
                this.f12854c.getQuestionBankData();
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(RefreshLayout refreshLayout) throws JSONException {
        getQuestionBankData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$4(View view) {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$5(View view) {
        if ("全选".contentEquals(this.tvAllSelect.getText())) {
            this.tvAllSelect.setText("取消全选");
            setAllRefulData(1);
        } else {
            this.tvAllSelect.setText("全选");
            setAllRefulData(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$6(View view) {
        this.checkeddown.setSelected(!r3.isSelected());
        if (this.checkeddown.isSelected()) {
            this.mTvChecked.setText("取消全选");
            setAllRefulData(1);
        } else {
            this.mTvChecked.setText("全选");
            setAllRefulData(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$7(View view) {
        getChapterIdData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$lazyLoad$0() throws JSONException {
        SmartRefreshLayout smartRefreshLayout = this.mRefresh;
        if (smartRefreshLayout == null) {
            getQuestionBankData();
        } else {
            smartRefreshLayout.setEnableRefresh(true);
            getQuestionBankData();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$lazyLoad$1() throws JSONException {
        this.mRefresh.setEnableRefresh(true);
        getQuestionBankData();
    }

    private void lazyLoad() {
        SmartRefreshLayout smartRefreshLayout = this.mRefresh;
        if (smartRefreshLayout == null) {
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.errorquestion.b
                @Override // java.lang.Runnable
                public final void run() throws JSONException {
                    this.f12848c.lambda$lazyLoad$0();
                }
            }, 500L);
        } else {
            smartRefreshLayout.post(new Runnable() { // from class: com.psychiatrygarden.activity.mine.errorquestion.e
                @Override // java.lang.Runnable
                public final void run() throws JSONException {
                    this.f12852c.lambda$lazyLoad$1();
                }
            });
        }
    }

    public static QuestionBankNewFragment newInstance() {
        Bundle bundle = new Bundle();
        QuestionBankNewFragment questionBankNewFragment = new QuestionBankNewFragment();
        questionBankNewFragment.setArguments(bundle);
        return questionBankNewFragment;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setEmptyView(boolean isError) {
        if (isError) {
            this.adapter.setEmptyView(this.mLoadDataFailView);
        } else {
            this.adapter.setEmptyView(R.layout.layout_empty_view);
        }
    }

    private void updateCheckBox(boolean isCheck) {
    }

    private void updateSelectCount() {
        if (this.mType.contains("error")) {
            this.tvTitle.setText("已选择" + this.selectCount + "个错题");
        } else if (this.mType.contains("collection")) {
            this.tvTitle.setText("已选择" + this.selectCount + "个收藏");
        } else if (this.mType.contains("note")) {
            this.tvTitle.setText("已选择" + this.selectCount + "个笔记");
        }
        this.exportTxt.setEnabled(this.selectCount > 0);
        if (this.selectCount == this.allCount) {
            this.tvAllSelect.setText("取消全选");
        } else {
            this.tvAllSelect.setText("全选");
        }
    }

    public void addADTwoView() {
        HomepageSmallAdBean.DataDTO.BlackAds blackAds = (HomepageSmallAdBean.DataDTO.BlackAds) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.QUESTION_CARD_AD, getActivity(), ""), HomepageSmallAdBean.DataDTO.BlackAds.class);
        if (blackAds == null || blackAds.getAds().isEmpty()) {
            this.rl_question_top_ad_two.setVisibility(8);
            return;
        }
        this.rl_question_top_ad_two.setVisibility(0);
        RecyclerView recyclerView = (RecyclerView) this.rl_question_top_ad_two.findViewById(R.id.rv_question_ad_two);
        if (AndroidBaseUtils.isPad(this.mContext) && AndroidBaseUtils.isCurOriLand(getActivity())) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        recyclerView.setAdapter(new QuestionTopAdTwoAdapter(blackAds.getAds()));
    }

    public void addADView() {
        final HomepageSmallAdBean.DataDTO.DataAd dataAd = (HomepageSmallAdBean.DataDTO.DataAd) new Gson().fromJson(SharePreferencesUtils.readStrConfig(CommonParameter.QUESTION_BANNER_AD, getActivity(), ""), HomepageSmallAdBean.DataDTO.DataAd.class);
        if (dataAd == null || dataAd.getAds().isEmpty()) {
            this.rl_question_top_ad.setVisibility(8);
            return;
        }
        if ((SharePreferencesUtils.readLongConfig("DISMESS_TIME_QUESTION_TOP_AD", this.mContext, 0L).longValue() != 0 ? ((System.currentTimeMillis() - SharePreferencesUtils.readLongConfig("DISMESS_TIME_QUESTION_TOP_AD", this.mContext, 0L).longValue()) / 1000) - dataAd.getTime_interval() : 0L) < 0) {
            return;
        }
        this.rl_question_top_ad.setVisibility(0);
        Banner banner = (Banner) this.rl_question_top_ad.findViewById(R.id.banner);
        ImageView imageView = (ImageView) this.rl_question_top_ad.findViewById(R.id.iv_ad_close);
        if (dataAd.getForce().equals("1")) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setOnClickListener(this);
        }
        int pxByDp = getResources().getDisplayMetrics().widthPixels - ScreenUtil.getPxByDp(requireContext(), 40);
        int i2 = pxByDp / 3;
        if (AndroidBaseUtils.isPad(requireContext()) && AndroidBaseUtils.isCurOriLand(getContext())) {
            i2 = pxByDp / 5;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ScreenUtil.getScreenWidth((Activity) requireContext()), i2);
        layoutParams.setMargins(0, ScreenUtil.getPxByDp(requireContext(), 10), 0, 0);
        banner.setLayoutParams(layoutParams);
        banner.addBannerLifecycleObserver(this).setLoopTime(5000L).setAdapter(new BannerQuestionAdsAdapter(dataAd.getAds())).setPageTransformer(new AlphaPageTransformer()).setIndicator(new CircleIndicator(getActivity())).setIndicatorRadius(ScreenUtil.getPxByDp(requireContext(), 4)).setIndicatorNormalColor(Color.parseColor("#88FFFFFF")).setIndicatorSelectedColor(ContextCompat.getColor(requireContext(), R.color.app_theme_red));
        banner.setOnBannerListener(new OnBannerListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.f
            @Override // com.youth.banner.listener.OnBannerListener
            public final void OnBannerClick(Object obj, int i3) {
                QuestionBankNew2Fragment.lambda$addADView$9(dataAd, (HomepageSmallAdBean.DataDTO.DataAd.AdsDTO) obj, i3);
            }
        });
    }

    public void addView() {
        this.ll_question_top.setVisibility(0);
        RecyclerView recyclerView = (RecyclerView) this.ll_question_top.findViewById(R.id.rvKingKong);
        final KingKongAreaItemAdapter kingKongAreaItemAdapter = new KingKongAreaItemAdapter(R.layout.item_kingkong_area);
        ArrayList arrayList = new ArrayList();
        arrayList.add(KingKongItem.WRONG_QUESTION);
        arrayList.add(KingKongItem.COLLECT_QUESTION);
        arrayList.add(KingKongItem.NOTE_QUESTION);
        arrayList.add(KingKongItem.COMMENT_QUESTION);
        arrayList.add(KingKongItem.LIKE_QUESTION);
        arrayList.add(KingKongItem.COMBINATION_QUESTION);
        recyclerView.setAdapter(kingKongAreaItemAdapter);
        kingKongAreaItemAdapter.setList(arrayList);
        kingKongAreaItemAdapter.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.h
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12855c.lambda$addView$8(kingKongAreaItemAdapter, baseQuickAdapter, view, i2);
            }
        });
    }

    public void getChapterIdData() {
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list != null && list.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (QuestionBankNewBean.DataBean dataBean : this.dataList) {
                List<QuestionBankNewBean.DataBean.ChildrenBean> children = dataBean.getChildren();
                if (children != null && !children.isEmpty()) {
                    for (QuestionBankNewBean.DataBean.ChildrenBean childrenBean : children) {
                        if (childrenBean.isSelected == 1) {
                            arrayList.add(childrenBean.getPrimary_id());
                        }
                    }
                } else if (dataBean.isSelected == 1) {
                    arrayList.add(dataBean.getPrimary_id());
                }
            }
            String strJoin = arrayList.size() > 0 ? StringUtil.join(arrayList, ",") : "";
            if (getActivity() instanceof ExportQuestionNewActivity) {
                ((ExportQuestionNewActivity) getActivity()).getChapterIdData(strJoin);
            }
        }
        if (this.mType.contains("error")) {
            AliyunEvent aliyunEvent = AliyunEvent.ErrorExport;
            CommonUtil.addLog(aliyunEvent.getKey(), aliyunEvent.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2");
            return;
        }
        if (this.mType.contains("collection")) {
            AliyunEvent aliyunEvent2 = AliyunEvent.CollectionExport;
            CommonUtil.addLog(aliyunEvent2.getKey(), aliyunEvent2.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2");
            return;
        }
        if (this.mType.contains("note")) {
            AliyunEvent aliyunEvent3 = AliyunEvent.NoteExport;
            CommonUtil.addLog(aliyunEvent3.getKey(), aliyunEvent3.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2");
        }
    }

    @Override // com.psychiatrygarden.activity.mine.errorquestion.adapter.QuestionBankNew2Adapter.JumpToQList
    public void getExCo() {
    }

    public void getIntentData(String type) {
        if (isLogin()) {
            Intent intent = new Intent(getActivity(), (Class<?>) QuestionWCNHomeActivity.class);
            intent.putExtra(UriUtil.QUERY_CATEGORY, this.mCategory);
            intent.putExtra("module_type", this.mModuleType);
            intent.putExtra("is_show_number", this.is_show_number);
            intent.putExtra("type", type);
            intent.putExtra("export_func_identity_id", this.mExportFuncIdentityId);
            intent.putExtra("identity_id", this.mIdentityId);
            startActivity(intent);
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.fragment_question_bank_new2;
    }

    @Override // com.psychiatrygarden.activity.mine.errorquestion.adapter.QuestionBankNew2Adapter.JumpToQList
    public void getPassData(final String activity_id) {
        if (isLogin()) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "" + activity_id);
            MemInterface.getInstance().getMemData(getActivity(), ajaxParams, false, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.d
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f12850a.lambda$getPassData$10(activity_id);
                }
            });
        }
    }

    public void getQuestionBankData() throws JSONException {
        JSONArray jSONArray;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(UriUtil.QUERY_CATEGORY, this.mCategory);
        ajaxParams.put("identity_id", this.mIdentityId);
        if (this.mType.contains("export_")) {
            ajaxParams.put("type", this.mType.split(StrPool.UNDERLINE)[1]);
        } else {
            ajaxParams.put("type", this.mType);
        }
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
        YJYHttpUtils.post(getActivity(), str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.errorquestion.QuestionBankNew2Fragment.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                QuestionBankNew2Fragment.this.mRefresh.finishRefresh(false);
                QuestionBankNew2Fragment.this.setEmptyView(true);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                QuestionBankNew2Fragment.this.loadDataSuccess = true;
                try {
                    QuestionBankNewBean questionBankNewBean = (QuestionBankNewBean) new Gson().fromJson(s2, QuestionBankNewBean.class);
                    QuestionBankNew2Fragment.this.mRefresh.finishRefresh("200".equals(questionBankNewBean.getCode()));
                    if (!"200".equals(questionBankNewBean.getCode())) {
                        if ("202".equals(questionBankNewBean.getCode())) {
                            if (QuestionBankNew2Fragment.this.mCategory.equals("year")) {
                                QuestionBankNew2Fragment.this.dataList.clear();
                                QuestionBankNew2Fragment questionBankNew2Fragment = QuestionBankNew2Fragment.this;
                                questionBankNew2Fragment.adapter.setList(questionBankNew2Fragment.getEntity(questionBankNew2Fragment.dataList));
                            }
                            QuestionBankNew2Fragment.this.setEmptyView(true);
                            return;
                        }
                        return;
                    }
                    if (questionBankNewBean.getData() == null || questionBankNewBean.getData().size() <= 0) {
                        if (QuestionBankNew2Fragment.this.mType.contains("export_")) {
                            QuestionBankNew2Fragment.this.linexportview.setVisibility(8);
                        }
                        QuestionBankNew2Fragment.this.dataList.clear();
                        QuestionBankNew2Fragment questionBankNew2Fragment2 = QuestionBankNew2Fragment.this;
                        questionBankNew2Fragment2.adapter.setList(questionBankNew2Fragment2.getEntity(questionBankNew2Fragment2.dataList));
                        QuestionBankNew2Fragment.this.setEmptyView(false);
                        return;
                    }
                    QuestionBankNew2Fragment.this.dataList = questionBankNewBean.getData();
                    for (int i3 = 0; i3 < QuestionBankNew2Fragment.this.dataList.size(); i3++) {
                        ArrayList arrayList = new ArrayList();
                        if (QuestionBankNew2Fragment.this.dataList.get(i3).getChildren() != null && QuestionBankNew2Fragment.this.dataList.get(i3).getChildren().size() > 0) {
                            for (int i4 = 0; i4 < QuestionBankNew2Fragment.this.dataList.get(i3).getChildren().size(); i4++) {
                                QuestionBankNew2Fragment.this.dataList.get(i3).getChildren().get(i4).setCategory(QuestionBankNew2Fragment.this.mCategory);
                                QuestionBankNew2Fragment.this.dataList.get(i3).getChildren().get(i4).setPrimary_p_id(QuestionBankNew2Fragment.this.dataList.get(i3).getPrimary_id() + "");
                                QuestionBankNew2Fragment.this.dataList.get(i3).getChildren().get(i4).setType(QuestionBankNew2Fragment.this.mType);
                                QuestionBankNew2Fragment.this.dataList.get(i3).getChildren().get(i4).setGroupPosition(i3);
                                QuestionBankNew2Fragment.this.dataList.get(i3).getChildren().get(i4).setParent_title(QuestionBankNew2Fragment.this.dataList.get(i3).getTitle());
                                arrayList.add(QuestionBankNew2Fragment.this.dataList.get(i3).getChildren().get(i4));
                            }
                        }
                        QuestionBankNew2Fragment.this.dataList.get(i3).setChildren(arrayList);
                        QuestionBankNew2Fragment.this.dataList.get(i3).setType(QuestionBankNew2Fragment.this.mType);
                        if (!QuestionBankNew2Fragment.this.mType.equals("all")) {
                            QuestionBankNew2Fragment.this.dataList.get(i3).setPass("1");
                        }
                    }
                    QuestionBankNew2Fragment questionBankNew2Fragment3 = QuestionBankNew2Fragment.this;
                    questionBankNew2Fragment3.adapter.setList(questionBankNew2Fragment3.getEntity(questionBankNew2Fragment3.dataList));
                    try {
                        if (!"all".equals(QuestionBankNew2Fragment.this.mType) || "unit".equals(QuestionBankNew2Fragment.this.mCategory)) {
                            return;
                        }
                        QuestionBankNew2Fragment.this.addADView();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                    QuestionBankNew2Fragment.this.setEmptyView(true);
                    QuestionBankNew2Fragment.this.mRefresh.finishRefresh(false);
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.is_show_number = getArgumentValue("is_show_number");
        this.layoutSelectExport = (RelativeLayout) holder.get(R.id.layoutSelectExport);
        this.tvAllSelect = (TextView) holder.get(R.id.tv_export_all_select);
        this.tvTitle = (TextView) holder.get(R.id.tv_export_title);
        this.ivBack = (ImageView) holder.get(R.id.iv_export_back);
        this.rl_question_top_ad = (RelativeLayout) holder.get(R.id.rl_question_top_ad);
        this.rl_question_top_ad_two = (RelativeLayout) holder.get(R.id.rl_question_top_ad_two);
        this.ll_question_top = (LinearLayout) holder.get(R.id.ll_question_top);
        this.linexportview = (LinearLayout) holder.get(R.id.linexportview);
        this.checkeddown = (ImageView) holder.get(R.id.checkeddown);
        LinearLayout linearLayout = (LinearLayout) holder.get(R.id.ly_checked);
        this.mTvChecked = (TextView) holder.get(R.id.tv_checked);
        this.exportTxt = (TextView) holder.get(R.id.exportTxt);
        this.mRefresh = (SmartRefreshLayout) holder.get(R.id.mSwipeLayput);
        this.recyexpend = (RecyclerView) holder.get(R.id.recyexpend);
        View viewInflate = View.inflate(this.mContext, R.layout.layout_loaddata_fail_view, null);
        this.mLoadDataFailView = viewInflate;
        ((TextView) viewInflate.findViewById(R.id.btn_reload)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12857c.lambda$initViews$2(view);
            }
        });
        this.mType = getArgumentValue("type");
        this.mCategory = getArgumentValue(UriUtil.QUERY_CATEGORY);
        this.mModuleType = getArgumentValue("module_type");
        this.mExportFuncIdentityId = getArgumentValue("export_func_identity_id");
        this.mIdentityId = getArgumentValue("identity_id");
        this.mUnitId = getArgumentValue("unit_id");
        this.mCategoryId = getArgumentValue("category_id");
        this.recyexpend.setLayoutManager(new LinearLayoutManager(getActivity()));
        QuestionBankNew2Adapter questionBankNew2Adapter = new QuestionBankNew2Adapter(this);
        this.adapter = questionBankNew2Adapter;
        questionBankNew2Adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.AlphaIn);
        this.recyexpend.setAdapter(this.adapter);
        this.mRefresh.autoRefreshAnimationOnly();
        this.mRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.j
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) throws JSONException {
                this.f12858c.lambda$initViews$3(refreshLayout);
            }
        });
        if (!"all".equals(this.mType)) {
            if ("unit".equals(this.mCategory)) {
                lazyLoad();
            }
            if (this.mType.contains("export_")) {
                if (SkinManager.getCurrentSkinType(getActivity()) == 1 && getActivity() != null) {
                    getActivity().getWindow().setNavigationBarColor(Color.parseColor("#1C2134"));
                }
                this.linexportview.setVisibility(0);
                this.layoutSelectExport.setVisibility(0);
                lazyLoad();
            }
        } else if ("unit".equals(this.mCategory)) {
            lazyLoad();
        } else {
            addADTwoView();
            addADView();
            addView();
        }
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12859c.lambda$initViews$4(view);
            }
        });
        this.tvAllSelect.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12860c.lambda$initViews$5(view);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12861c.lambda$initViews$6(view);
            }
        });
        this.exportTxt.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.errorquestion.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12849c.lambda$initViews$7(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.mine.errorquestion.adapter.QuestionBankNew2Adapter.JumpToQList
    public void mChildSelectClumn(int groupPosition, int childPosition) {
        setRefulData(groupPosition);
        setDownTextColor();
    }

    @Override // com.psychiatrygarden.activity.mine.errorquestion.adapter.QuestionBankNew2Adapter.JumpToQList
    public void mGroupSelectClumn(int groupPosition) {
        getSelectCount();
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            if (this.dataList.get(i2) != null && this.dataList.get(i2).getIsSelected() != 1) {
                setDownTextColor();
                this.checkeddown.setSelected(false);
                return;
            }
        }
        this.checkeddown.setSelected(true);
        setDownTextColor();
    }

    @Override // com.psychiatrygarden.activity.mine.errorquestion.adapter.QuestionBankNew2Adapter.JumpToQList
    public void mJumpToQList(String primary_id, String unit, String parent_title, String title) {
        if (isLogin()) {
            Bundle bundle = new Bundle();
            bundle.putString("primary_id", primary_id);
            bundle.putString("unit", unit);
            bundle.putString("unit_id", this.mUnitId);
            bundle.putString("category_id", this.mCategoryId);
            bundle.putString(UriUtil.QUERY_CATEGORY, this.mCategory);
            bundle.putString("module_type", this.mModuleType);
            bundle.putString("type", this.mType);
            bundle.putString("subject_title", parent_title);
            bundle.putString("chapter_title", title);
            bundle.putString("is_show_number", this.is_show_number);
            bundle.putString("identity_id", this.mIdentityId);
            AnswerSheetActivity.gotoActivity(getActivity(), bundle);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (v2.getId() != R.id.iv_ad_close || this.rl_question_top_ad == null) {
            return;
        }
        SharePreferencesUtils.writeLongConfig("DISMESS_TIME_QUESTION_TOP_AD", Long.valueOf(System.currentTimeMillis()), this.mContext);
        this.rl_question_top_ad.setVisibility(8);
        EventBus.getDefault().post(new EventBusMessage(EventBusConstant.EVENT_QUESTION_TOP_AD_DISSMISS, ""));
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try {
            if (!"all".equals(this.mType) || "unit".equals(this.mCategory)) {
                return;
            }
            addADTwoView();
            addADView();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
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

    public void setAllRefulData(int select) {
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list != null && list.size() > 0) {
            for (int i2 = 0; i2 < this.dataList.size(); i2++) {
                if (this.dataList.get(i2) != null) {
                    QuestionBankNewBean.DataBean dataBean = this.dataList.get(i2);
                    dataBean.setIsSelected(select);
                    if (dataBean.getChildren() != null && dataBean.getChildren().size() > 0) {
                        for (int i3 = 0; i3 < dataBean.getChildren().size(); i3++) {
                            dataBean.getChildren().get(i3).setIsSelected(select);
                        }
                    }
                }
            }
            this.adapter.notifyDataSetChanged();
        }
        getSelectCount();
    }

    public void setDownTextColor() {
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list == null || list.size() <= 0) {
            return;
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            QuestionBankNewBean.DataBean dataBean = this.dataList.get(i2);
            if (dataBean.getChildren() == null || dataBean.getChildren().isEmpty()) {
                if (dataBean.isSelected == 1 && !z2) {
                    z2 = true;
                    break;
                }
            } else {
                for (int i3 = 0; i3 < dataBean.getChildren().size(); i3++) {
                    if (this.dataList.get(i2).getChildren().get(i3).getIsSelected() == 1) {
                        z2 = true;
                        break;
                        break;
                    }
                }
            }
        }
        this.exportTxt.setEnabled(z2);
    }

    /* renamed from: setPassData, reason: merged with bridge method [inline-methods] */
    public void lambda$getPassData$10(String activityid) {
        List<QuestionBankNewBean.DataBean> list = this.dataList;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < this.dataList.size(); i2++) {
            if (this.dataList.get(i2) != null) {
                QuestionBankNewBean.DataBean dataBean = this.dataList.get(i2);
                if (activityid.equals(dataBean.getActivity_id())) {
                    dataBean.setPass("1");
                }
            }
        }
        this.adapter.notifyDataSetChanged();
    }

    public void setRefulData(int groupPosition) {
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.dataList.get(groupPosition).getChildren().size(); i4++) {
            if (this.dataList.get(groupPosition).getChildren().get(i4).getIsSelected() != 1) {
                i3++;
                this.checkeddown.setSelected(false);
            } else {
                i2++;
            }
        }
        if (i2 > 0 && i3 == 0) {
            this.dataList.get(groupPosition).setIsSelected(1);
        }
        if (i2 > 0 && i3 > 0) {
            this.dataList.get(groupPosition).setIsSelected(2);
        }
        if (i2 == 0) {
            this.dataList.get(groupPosition).setIsSelected(0);
        }
        getSelectCount();
        for (int i5 = 0; i5 < this.dataList.size(); i5++) {
            if (this.dataList.get(i5) != null && this.dataList.get(i5).getIsSelected() != 1) {
                return;
            }
        }
        this.checkeddown.setSelected(true);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (!isVisibleToUser || this.loadDataSuccess) {
            return;
        }
        lazyLoad();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NetworkEvent event) throws JSONException {
        if (!this.loadDataSuccess && event.isAvailable() && this.isVisibleToUser) {
            getQuestionBankData();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0017  */
    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onEventMainThread(com.psychiatrygarden.bean.EventBusMessage r12) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 960
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.mine.errorquestion.QuestionBankNew2Fragment.onEventMainThread(com.psychiatrygarden.bean.EventBusMessage):void");
    }

    @Override // com.psychiatrygarden.activity.mine.errorquestion.adapter.QuestionBankNew2Adapter.JumpToQList
    public void mJumpToQList(String primary_id, String unit, String parent_title, String title, BaseNode node) {
        if (isLogin()) {
            Bundle bundle = new Bundle();
            NextChapterInfo nextChapterInfoCheckHasNextChapter = checkHasNextChapter(primary_id, node);
            ProjectApp.saveNChapterInfo(nextChapterInfoCheckHasNextChapter);
            if (nextChapterInfoCheckHasNextChapter == null) {
                return;
            }
            LogUtils.d("chapter_info", "是否有下一章节:" + nextChapterInfoCheckHasNextChapter.isHasNextChapter() + "，下一章节是否解锁：" + nextChapterInfoCheckHasNextChapter.isNextChapterIsUnlock());
            bundle.putBoolean("hasNextChapter", nextChapterInfoCheckHasNextChapter.isHasNextChapter());
            bundle.putBoolean("nextChapterUnlock", nextChapterInfoCheckHasNextChapter.isNextChapterIsUnlock());
            bundle.putStringArrayList("chapterIds", nextChapterInfoCheckHasNextChapter.getRemainChapterIds());
            bundle.putBooleanArray("chaptersUnlockArr", nextChapterInfoCheckHasNextChapter.getUnlockArr());
            bundle.putString("primary_id", primary_id);
            bundle.putString("unit", unit);
            bundle.putString("unit_id", "" + this.mUnitId);
            bundle.putString("category_id", this.mCategoryId);
            bundle.putString(UriUtil.QUERY_CATEGORY, "" + this.mCategory);
            bundle.putString("module_type", this.mModuleType);
            bundle.putString("type", this.mType);
            bundle.putString("subject_title", parent_title);
            bundle.putString("chapter_title", title);
            bundle.putString("is_show_number", this.is_show_number);
            bundle.putString("identity_id", this.mIdentityId);
            AnswerSheetActivity.gotoActivity(getActivity(), bundle);
        }
    }
}
