package com.psychiatrygarden.activity.mine.knowledge;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.online.RecommendQuestionVideoAct;
import com.psychiatrygarden.bean.ChartFilterBean;
import com.psychiatrygarden.bean.KnowledgePointBean;
import com.psychiatrygarden.bean.WeekOrMonthReportBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.DialogShare;
import com.psychiatrygarden.widget.KnowledgePointStatisticsProgressView;
import com.psychiatrygarden.widget.PointProgressByTaskView;
import com.psychiatrygarden.widget.PointStatisticeChildItemView;
import com.psychiatrygarden.widget.PopKnowledgeChartFilter;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class KnowledgePointStatisticsAct extends BaseActivity {
    private AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collapse;
    private String desc;
    private KnowledgeFilterItemAdp filterItemAdp;
    private String id;
    private boolean isShowFilter;
    private ImageView mBtnFilter;
    private TextView mBtnToQuestion;
    private TextView mBtnToStudy;
    private DialogShare mDialogShare;
    private ImageView mImgBack;
    private ImageView mImgBg;
    private View mLyPointTitle;
    private LinearLayout mLyPointView;
    private LinearLayout mLyToQuestion;
    private LinearLayout mLyToStudy;
    private RelativeLayout mLyWebView;
    private TextView mNavTitle;
    private TextView mTvAvgTime;
    private TextView mTvDoQuestionNumber;
    private TextView mTvFilterDesc;
    private TextView mTvHistoryTime;
    private TextView mTvHistoryTimeMin;
    private TextView mTvProjectName;
    private RelativeLayout mViewWeb;
    private WebView mWebView;
    private String nodeTitle;
    private KnowledgePointStatisticsProgressView pointsProgressView;
    private LinearLayout rellogview;
    private RecyclerView rvFilter;
    private PointProgressByTaskView taskView;
    private Toolbar toobars1;
    private RelativeLayout toolbars;
    private List<ChartFilterBean> chartFilterList = new ArrayList();
    private ArrayMap<String, List<String>> defaultFilterMap = new ArrayMap<>();

    /* renamed from: com.psychiatrygarden.activity.mine.knowledge.KnowledgePointStatisticsAct$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(int i2) {
            KnowledgePointStatisticsAct.this.pointsProgressView.setCurrentPercent(i2, 100, i2 + "");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) throws NumberFormatException {
            super.onSuccess((AnonymousClass2) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code").equals("200")) {
                    ToastUtil.shortToast(KnowledgePointStatisticsAct.this, jSONObject.optString("message"));
                    return;
                }
                WeekOrMonthReportBean.ReportDataBean reportDataBean = (WeekOrMonthReportBean.ReportDataBean) new Gson().fromJson(jSONObject.optString("data"), WeekOrMonthReportBean.ReportDataBean.class);
                if (reportDataBean != null) {
                    KnowledgePointStatisticsAct.this.nodeTitle = reportDataBean.getNode_title();
                    KnowledgePointStatisticsAct.this.mNavTitle.setText(KnowledgePointStatisticsAct.this.nodeTitle);
                    KnowledgePointStatisticsAct.this.mTvProjectName.setText(reportDataBean.getTitle());
                    if (TextUtils.isEmpty(KnowledgePointStatisticsAct.this.desc)) {
                        KnowledgePointStatisticsAct.this.mTvFilterDesc.setText(reportDataBean.getFilter_desc());
                    }
                    if (!TextUtils.isEmpty(reportDataBean.getAvg_duration())) {
                        KnowledgePointStatisticsAct.this.mTvAvgTime.setText(reportDataBean.getAvg_duration());
                    }
                    KnowledgePointStatisticsAct.this.mTvDoQuestionNumber.setText(reportDataBean.getAll_question_num());
                    if (TextUtils.isEmpty(reportDataBean.getAll_duration()) || reportDataBean.getAll_duration().equals("0")) {
                        KnowledgePointStatisticsAct.this.mTvHistoryTime.setVisibility(8);
                        SpannableString spannableString = new SpannableString("0m");
                        spannableString.setSpan(new AbsoluteSizeSpan(18, true), 0, 1, 33);
                        KnowledgePointStatisticsAct.this.mTvHistoryTimeMin.setText(spannableString);
                        KnowledgePointStatisticsAct.this.mTvHistoryTimeMin.setVisibility(0);
                    } else {
                        long j2 = Long.parseLong(reportDataBean.getAll_duration()) / 60;
                        int i2 = (int) (j2 / 60);
                        int i3 = (int) (j2 % 60);
                        if (i2 == 0) {
                            KnowledgePointStatisticsAct.this.mTvHistoryTime.setVisibility(8);
                        } else {
                            String str = i2 + "h";
                            int iIndexOf = str.indexOf("h");
                            SpannableString spannableString2 = new SpannableString(str);
                            spannableString2.setSpan(new AbsoluteSizeSpan(18, true), 0, iIndexOf, 33);
                            KnowledgePointStatisticsAct.this.mTvHistoryTime.setText(spannableString2);
                            KnowledgePointStatisticsAct.this.mTvHistoryTime.setVisibility(0);
                        }
                        String str2 = i3 + "m";
                        int iIndexOf2 = str2.indexOf("m");
                        SpannableString spannableString3 = new SpannableString(str2);
                        spannableString3.setSpan(new AbsoluteSizeSpan(18, true), 0, iIndexOf2, 33);
                        KnowledgePointStatisticsAct.this.mTvHistoryTimeMin.setText(spannableString3);
                        KnowledgePointStatisticsAct.this.mTvHistoryTimeMin.setVisibility(0);
                    }
                    final int i4 = !TextUtils.isEmpty(reportDataBean.getUser_right_rate()) ? (int) Double.parseDouble(reportDataBean.getUser_right_rate()) : 0;
                    KnowledgePointStatisticsAct.this.pointsProgressView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.knowledge.f0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f12877c.lambda$onSuccess$0(i4);
                        }
                    }, 300L);
                    if (!TextUtils.isEmpty(reportDataBean.getAll_right_rate())) {
                        Double.parseDouble(reportDataBean.getAll_right_rate());
                    }
                    reportDataBean.getUser_right_rate();
                    reportDataBean.getPeople_percent();
                    if (TextUtils.isEmpty(reportDataBean.getHas_recommend_question()) || !reportDataBean.getHas_recommend_question().equals("1")) {
                        KnowledgePointStatisticsAct.this.mLyToQuestion.setVisibility(8);
                    } else {
                        KnowledgePointStatisticsAct.this.mLyToQuestion.setVisibility(0);
                    }
                    if (TextUtils.isEmpty(reportDataBean.getHas_recommend_video()) || !reportDataBean.getHas_recommend_video().equals("1")) {
                        KnowledgePointStatisticsAct.this.mLyToStudy.setVisibility(8);
                    } else {
                        KnowledgePointStatisticsAct.this.mLyToStudy.setVisibility(0);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class JavaScriptInterface {
        Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }
    }

    public class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            KnowledgePointStatisticsAct.this.mLyWebView.getLayoutParams().height = -2;
            KnowledgePointStatisticsAct.this.mViewWeb.setVisibility(8);
        }
    }

    private void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("knowledge_id", this.id);
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.knowlwdgePointTopData, ajaxParams, new AnonymousClass2());
    }

    private void getFilterData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.questionChartFilter, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.knowledge.KnowledgePointStatisticsAct.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObjectOptJSONObject = new JSONObject(s2).optJSONObject("data");
                    String strOptString = jSONObjectOptJSONObject.optString("avatar");
                    String strOptString2 = jSONObjectOptJSONObject.optString("describe");
                    String strOptString3 = jSONObjectOptJSONObject.optString("knowledge_img_dark");
                    String strOptString4 = jSONObjectOptJSONObject.optString("knowledge_img");
                    String strOptString5 = jSONObjectOptJSONObject.optString("detail_img");
                    String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, KnowledgePointStatisticsAct.this);
                    if (!TextUtils.isEmpty(strOptString5)) {
                        SharePreferencesUtils.writeStrConfig(CommonParameter.FILTER_OPTION_DETAIL_IMG + strConfig, strOptString5, KnowledgePointStatisticsAct.this);
                    }
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_ICON, strOptString, KnowledgePointStatisticsAct.this);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_TEXT, strOptString2, KnowledgePointStatisticsAct.this);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_DESC, strOptString4, KnowledgePointStatisticsAct.this);
                    SharePreferencesUtils.writeStrConfig(CommonParameter.KNOWLEDGE_FILTER_TIPS_DESC_DARK, strOptString3, KnowledgePointStatisticsAct.this);
                    List list = (List) new Gson().fromJson(jSONObjectOptJSONObject.optString("list"), new TypeToken<List<ChartFilterBean>>() { // from class: com.psychiatrygarden.activity.mine.knowledge.KnowledgePointStatisticsAct.4.1
                    }.getType());
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        List<ChartFilterBean.ChartFilterValue> value = ((ChartFilterBean) list.get(i2)).getValue();
                        String type = ((ChartFilterBean) list.get(i2)).getType();
                        for (int i3 = 0; i3 < value.size(); i3++) {
                            value.get(i3).setType(type);
                        }
                    }
                    KnowledgePointStatisticsAct.this.chartFilterList.addAll(list);
                    KnowledgePointStatisticsAct.this.showFilterPoP();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getPointDetail(String search) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("knowledge_id", this.id);
        if (!TextUtils.isEmpty(search)) {
            ajaxParams.put("search", search);
        }
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.knowlwdgePointDetail, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.knowledge.KnowledgePointStatisticsAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    KnowledgePointBean knowledgePointBean = (KnowledgePointBean) new Gson().fromJson(s2, KnowledgePointBean.class);
                    if (knowledgePointBean.getCode().equals("200")) {
                        if (knowledgePointBean.getData() == null || knowledgePointBean.getData().size() <= 0) {
                            if (KnowledgePointStatisticsAct.this.mLyPointView.getChildCount() > 0) {
                                KnowledgePointStatisticsAct.this.mLyPointView.removeAllViews();
                            }
                            if (KnowledgePointStatisticsAct.this.isShowFilter) {
                                return;
                            }
                            KnowledgePointStatisticsAct.this.isShowFilter = true;
                            KnowledgePointStatisticsAct.this.mLyPointTitle.setVisibility(8);
                            return;
                        }
                        if (!KnowledgePointStatisticsAct.this.isShowFilter) {
                            KnowledgePointStatisticsAct.this.isShowFilter = true;
                            KnowledgePointStatisticsAct.this.mLyPointTitle.setVisibility(0);
                        }
                        KnowledgePointStatisticsAct.this.mLyPointView.removeAllViews();
                        for (int i2 = 0; i2 < knowledgePointBean.getData().size(); i2++) {
                            PointStatisticeChildItemView pointStatisticeChildItemView = new PointStatisticeChildItemView(KnowledgePointStatisticsAct.this);
                            pointStatisticeChildItemView.setData(knowledgePointBean.getData().get(i2), knowledgePointBean.getData().get(0).getGlobal_have().equals("1"));
                            KnowledgePointStatisticsAct.this.mLyPointView.addView(pointStatisticeChildItemView);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(int i2, AppBarLayout appBarLayout, int i3) {
        if (Math.abs(i3) >= i2) {
            this.appbarlayout.setBackgroundColor(-1);
            this.toobars1.setBackgroundColor(-1);
            this.rellogview.setBackgroundColor(-1);
            this.mNavTitle.setText(this.mTvProjectName.getText().toString());
            this.mImgBg.setVisibility(8);
            return;
        }
        this.mNavTitle.setText(this.nodeTitle);
        this.appbarlayout.setBackgroundColor(0);
        this.rellogview.setBackgroundColor(0);
        this.toobars1.setBackgroundColor(0);
        this.mImgBg.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.chartFilterList.size() == 0) {
            getFilterData();
        } else {
            showFilterPoP();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        RecommendQuestionVideoAct.INSTANCE.newIntent(this, this.id, 0, true, this.mLyToStudy.getVisibility() == 0, this.mTvProjectName.getText().toString(), getIntent().getStringExtra("question_bank_id"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        RecommendQuestionVideoAct.INSTANCE.newIntent(this, this.id, 1, this.mLyToQuestion.getVisibility() == 0, true, this.mTvProjectName.getText().toString(), getIntent().getStringExtra("question_bank_id"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFilterPoP$5(Map map) throws JSONException {
        String str;
        this.defaultFilterMap.clear();
        if (map.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (Map.Entry entry : map.entrySet()) {
                String str2 = (String) entry.getKey();
                String str3 = (String) entry.getValue();
                ArrayList arrayList2 = new ArrayList();
                if (str3.contains(",")) {
                    arrayList2.addAll(Arrays.asList(str3.split(",")));
                } else {
                    arrayList2.add(str3);
                }
                this.defaultFilterMap.put(str2, arrayList2);
                for (ChartFilterBean chartFilterBean : this.chartFilterList) {
                    if (chartFilterBean.getType().equals(str2)) {
                        if (str3.contains(",")) {
                            String[] strArrSplit = str3.split(",");
                            ArrayList arrayList3 = new ArrayList();
                            String str4 = "";
                            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                                String str5 = strArrSplit[i2];
                                for (ChartFilterBean.ChartFilterValue chartFilterValue : chartFilterBean.getValue()) {
                                    if (chartFilterValue.getKey().equals(str5)) {
                                        StringBuilder sb = new StringBuilder();
                                        str = str5;
                                        sb.append(chartFilterValue.getName());
                                        sb.append("/");
                                        String string = sb.toString();
                                        if (i2 == strArrSplit.length - 1) {
                                            string = chartFilterValue.getName() + chartFilterValue.getTitle();
                                        }
                                        str4 = str4 + string;
                                    } else {
                                        str = str5;
                                    }
                                    str5 = str;
                                }
                                LogUtils.e("filter_value", "value_list====>" + new Gson().toJson(arrayList3));
                            }
                            arrayList.add(str4);
                        } else {
                            for (int i3 = 0; i3 < chartFilterBean.getValue().size(); i3++) {
                                if (chartFilterBean.getValue().get(i3).getKey().equals(str3)) {
                                    String str6 = chartFilterBean.getValue().get(i3).getName() + chartFilterBean.getValue().get(i3).getTitle();
                                    LogUtils.e("filter_value", "value====>" + str6);
                                    arrayList.add(str6);
                                }
                            }
                        }
                    }
                }
            }
            this.filterItemAdp.setNewData(arrayList);
        } else {
            this.filterItemAdp.setNewData(new ArrayList());
            this.defaultFilterMap.clear();
        }
        if (this.filterItemAdp.getData().isEmpty()) {
            this.rvFilter.setVisibility(8);
            this.mTvFilterDesc.setVisibility(0);
            this.mTvFilterDesc.requestFocus();
            this.mTvFilterDesc.setSelected(true);
        } else {
            this.rvFilter.setVisibility(0);
            this.mTvFilterDesc.setVisibility(8);
        }
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry entry2 : map.entrySet()) {
            try {
                jSONObject.put((String) entry2.getKey(), (String) entry2.getValue());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (jSONObject.length() > 0) {
            getPointDetail(jSONObject.toString());
        } else {
            getPointDetail("");
        }
    }

    public static void newIntent(Context context, String id, String desc, String question_bank_id) {
        Intent intent = new Intent(context, (Class<?>) KnowledgePointStatisticsAct.class);
        intent.putExtra("id", id);
        intent.putExtra("question_bank_id", question_bank_id);
        intent.putExtra("desc", desc);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFilterPoP() {
        new XPopup.Builder(this.mContext).asCustom(new PopKnowledgeChartFilter(this.mContext, this.chartFilterList, "", this.defaultFilterMap, new PopKnowledgeChartFilter.ConfirmListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.e0
            @Override // com.psychiatrygarden.widget.PopKnowledgeChartFilter.ConfirmListener
            public final void onConfirm(Map map) throws JSONException {
                this.f12875a.lambda$showFilterPoP$5(map);
            }
        })).show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        int aPPVersionCode;
        this.id = getIntent().getStringExtra("id");
        this.desc = getIntent().getStringExtra("desc");
        this.appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        this.toobars1 = (Toolbar) findViewById(R.id.toobars1);
        this.mNavTitle = (TextView) findViewById(R.id.nav_title);
        this.toolbars = (RelativeLayout) findViewById(R.id.toolbars);
        this.rellogview = (LinearLayout) findViewById(R.id.rellogview);
        this.collapse = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mLyPointView = (LinearLayout) findViewById(R.id.ly_point_view);
        this.mLyToQuestion = (LinearLayout) findViewById(R.id.ly_to_question);
        this.mLyToStudy = (LinearLayout) findViewById(R.id.ly_to_study);
        this.mBtnToQuestion = (TextView) findViewById(R.id.btn_to_question);
        this.mBtnToStudy = (TextView) findViewById(R.id.btn_to_study);
        this.mBtnFilter = (ImageView) findViewById(R.id.btn_filter);
        this.mImgBg = (ImageView) findViewById(R.id.img_bg);
        this.pointsProgressView = (KnowledgePointStatisticsProgressView) findViewById(R.id.progress_view);
        this.taskView = (PointProgressByTaskView) findViewById(R.id.point_task_progress);
        this.mTvAvgTime = (TextView) findViewById(R.id.tv_avg_time);
        this.mTvDoQuestionNumber = (TextView) findViewById(R.id.tv_do_question_num);
        this.mTvHistoryTime = (TextView) findViewById(R.id.tv_history_time);
        this.mTvHistoryTimeMin = (TextView) findViewById(R.id.tv_history_time_min);
        this.rvFilter = (RecyclerView) findViewById(R.id.rvFilter);
        this.mTvFilterDesc = (TextView) findViewById(R.id.tv_kc_desc);
        this.mWebView = (WebView) findViewById(R.id.webview);
        this.mViewWeb = (RelativeLayout) findViewById(R.id.view_web);
        this.mLyWebView = (RelativeLayout) findViewById(R.id.ly_webview);
        this.mTvProjectName = (TextView) findViewById(R.id.tv_project_name);
        this.mLyPointTitle = findViewById(R.id.ly_point_title);
        this.mTvFilterDesc.setText(this.desc);
        if (!TextUtils.isEmpty(this.desc)) {
            this.mTvFilterDesc.setVisibility(0);
            this.mTvFilterDesc.requestFocus();
            this.mTvFilterDesc.setSelected(true);
        }
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        this.appbarlayout.setSelected(false);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(this.toobars1.getLayoutParams());
        ((FrameLayout.LayoutParams) layoutParams).height = UIUtil.dip2px(this.mContext, 44.0d) + statusBarHeight;
        layoutParams.setCollapseMode(1);
        this.toobars1.setLayoutParams(layoutParams);
        Toolbar.LayoutParams layoutParams2 = new Toolbar.LayoutParams(this.toolbars.getLayoutParams());
        layoutParams2.setMargins(0, statusBarHeight, 0, 0);
        this.toolbars.setLayoutParams(layoutParams2);
        CollapsingToolbarLayout.LayoutParams layoutParams3 = (CollapsingToolbarLayout.LayoutParams) this.rellogview.getLayoutParams();
        ((FrameLayout.LayoutParams) layoutParams3).topMargin = statusBarHeight + UIUtil.dip2px(this, 60.0d);
        this.rellogview.setLayoutParams(layoutParams3);
        final AppBarLayout.LayoutParams layoutParams4 = (AppBarLayout.LayoutParams) this.collapse.getLayoutParams();
        this.collapse.post(new Runnable() { // from class: com.psychiatrygarden.activity.mine.knowledge.KnowledgePointStatisticsAct.1
            @Override // java.lang.Runnable
            public void run() {
                int measuredHeight = KnowledgePointStatisticsAct.this.collapse.getMeasuredHeight();
                ((LinearLayout.LayoutParams) layoutParams4).height = measuredHeight;
                KnowledgePointStatisticsAct.this.collapse.setLayoutParams(layoutParams4);
                Log.d("CollapsingToolbarHeight", "Height: " + measuredHeight);
            }
        });
        LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) this.pointsProgressView.getLayoutParams();
        if (UIUtil.getScreenWidth(this) < CommonUtil.dip2px(this, 320.0f)) {
            layoutParams5.width = UIUtil.getScreenWidth(this);
            layoutParams5.height = UIUtil.getScreenWidth(this) / 2;
            this.pointsProgressView.setLayoutParams(layoutParams5);
        }
        final int iDip2px = UIUtil.dip2px(this.mContext, 44.0d);
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.d0
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                this.f12873a.lambda$init$0(iDip2px, appBarLayout, i2);
            }
        });
        KnowledgeFilterItemAdp knowledgeFilterItemAdp = new KnowledgeFilterItemAdp();
        this.filterItemAdp = knowledgeFilterItemAdp;
        this.rvFilter.setAdapter(knowledgeFilterItemAdp);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setUseWideViewPort(true);
        this.mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.mWebView.getSettings().setDisplayZoomControls(false);
        this.mWebView.getSettings().setAllowFileAccess(true);
        this.mWebView.getSettings().setBuiltInZoomControls(true);
        this.mWebView.getSettings().setLoadWithOverviewMode(true);
        this.mWebView.setHorizontalScrollBarEnabled(true);
        this.mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebView.setVerticalScrollBarEnabled(false);
        this.mWebView.getSettings().setSavePassword(false);
        this.mWebView.getSettings().setCacheMode(2);
        this.mWebView.getSettings().setDomStorageEnabled(true);
        this.mWebView.addJavascriptInterface(new JavaScriptInterface(this.mContext), "Android");
        SkinManager.getCurrentSkinType(this.mContext);
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext);
        String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext);
        String secret = UserConfig.getInstance().getUser().getSecret();
        String token = UserConfig.getInstance().getUser().getToken();
        String imei = AndroidBaseUtils.getIMEI(ProjectApp.instance());
        try {
            aPPVersionCode = AndroidBaseUtils.getAPPVersionCode(this);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            aPPVersionCode = R2.attr.plvDrag;
        }
        this.mWebView.loadUrl(NetworkRequestsURL.statisticalChatInfoUrl + "echartsKnowledgeStatistics.html?token=" + token + "&app_id=" + strConfig + "&secret=" + secret + "&user_id=" + UserConfig.getUserId() + "&knowledge_id=" + this.id + "&subject_id=" + strConfig2 + "&uuid=" + imei + "&version=" + aPPVersionCode);
        this.mWebView.setWebViewClient(new MyWebViewClient());
        getData();
        getPointDetail("");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, true);
        this.mActionBar.hide();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        WebView webView = this.mWebView;
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(this.mWebView);
            }
            this.mWebView.stopLoading();
            this.mWebView.getSettings().setJavaScriptEnabled(false);
            this.mWebView.clearHistory();
            this.mWebView.clearView();
            this.mWebView.removeAllViews();
            this.mWebView.destroy();
            this.mWebView = null;
        }
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_knowledge_point_statistics);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12917c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mBtnFilter.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12867c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mBtnToQuestion.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.b0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12869c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mBtnToStudy.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.c0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12871c.lambda$setListenerForWidget$4(view);
            }
        });
    }

    public static void newIntent(Context context, String id, String desc, String question_bank_id, boolean newTag) {
        Intent intent = new Intent(context, (Class<?>) KnowledgePointStatisticsAct.class);
        intent.putExtra("id", id);
        intent.putExtra("question_bank_id", question_bank_id);
        intent.putExtra("desc", desc);
        if (newTag) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }
}
