package com.psychiatrygarden.activity.mine.report;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.ScoreTrendBean;
import com.psychiatrygarden.bean.WeekOrMonthReportBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.ranking.StatisticsMainActivity;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.PointProgressByTaskView;
import com.psychiatrygarden.widget.WeekReportProgressView;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.lang.reflect.InvocationTargetException;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class WeekOrMonthReportAct extends BaseActivity {
    private AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collapse;
    private String id;
    private boolean isWeek;
    private ImageView mBtnStatistics;
    private ImageView mImgBack;
    private ImageView mImgBg;
    private ImageView mImgTred;
    private LinearLayout mLyTrend;
    private RelativeLayout mLyWebView;
    private TextView mNavTitle;
    private TextView mTvAllDoNum;
    private TextView mTvAllUsTime;
    private TextView mTvAllUsTimeMin;
    private TextView mTvPercent;
    private TextView mTvScore;
    private TextView mTvTrendScore;
    private TextView mTvUpdateTime;
    private RelativeLayout mViewWeb;
    private WebView mWebView;
    private WeekReportProgressView pointsProgressView;
    private RelativeLayout rellogview;
    private NestedScrollView scrollView;
    private PointProgressByTaskView taskView;
    private Toolbar toobars1;
    private RelativeLayout toolbars;

    public class JavaScriptInterface {
        Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void getDataType(String dataValue) {
            LogUtils.e("reveice_data_from_js", "获取到的参数====》" + dataValue);
        }

        @JavascriptInterface
        public void scrollEnd(boolean isCharTouch) {
            LogUtils.e("reveice_data_from_js", "获取到的参数====》" + isCharTouch);
            WeekOrMonthReportAct.this.mViewWeb.getParent().getParent().getParent().getParent();
        }
    }

    public class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            WeekOrMonthReportAct.this.mLyWebView.getLayoutParams().height = -2;
            WeekOrMonthReportAct.this.mViewWeb.setVisibility(8);
        }
    }

    private void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("identity_id", SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this, ""));
        ajaxParams.put("part_id", this.id);
        ajaxParams.put("type", this.isWeek ? "1" : "2");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.weekOrMonthReport, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.report.WeekOrMonthReportAct.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws NumberFormatException {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    WeekOrMonthReportBean weekOrMonthReportBean = (WeekOrMonthReportBean) new Gson().fromJson(s2, WeekOrMonthReportBean.class);
                    if (!weekOrMonthReportBean.getCode().equals("200") || weekOrMonthReportBean.getData() == null || weekOrMonthReportBean.getData().getQuestion_total() == null) {
                        return;
                    }
                    if (TextUtils.isEmpty(weekOrMonthReportBean.getData().getQuestion_total().getAll_duration()) || weekOrMonthReportBean.getData().getQuestion_total().getAll_duration().equals("0")) {
                        WeekOrMonthReportAct.this.mTvAllUsTime.setVisibility(8);
                        SpannableString spannableString = new SpannableString("0m");
                        spannableString.setSpan(new AbsoluteSizeSpan(20, true), 0, 1, 33);
                        WeekOrMonthReportAct.this.mTvAllUsTimeMin.setText(spannableString);
                        WeekOrMonthReportAct.this.mTvAllUsTimeMin.setVisibility(0);
                    } else {
                        long j2 = Long.parseLong(weekOrMonthReportBean.getData().getQuestion_total().getAll_duration());
                        int i2 = (int) (j2 / 60);
                        int i3 = (int) (j2 % 60);
                        if (i2 == 0) {
                            WeekOrMonthReportAct.this.mTvAllUsTime.setVisibility(8);
                        } else {
                            String str = i2 + "h";
                            int iIndexOf = str.indexOf("h");
                            SpannableString spannableString2 = new SpannableString(str);
                            spannableString2.setSpan(new AbsoluteSizeSpan(20, true), 0, iIndexOf, 33);
                            WeekOrMonthReportAct.this.mTvAllUsTime.setText(spannableString2);
                            WeekOrMonthReportAct.this.mTvAllUsTime.setVisibility(0);
                        }
                        if (i3 == 0) {
                            WeekOrMonthReportAct.this.mTvAllUsTimeMin.setVisibility(8);
                        } else {
                            String str2 = i3 + "m";
                            int iIndexOf2 = str2.indexOf("m");
                            SpannableString spannableString3 = new SpannableString(str2);
                            spannableString3.setSpan(new AbsoluteSizeSpan(20, true), 0, iIndexOf2, 33);
                            WeekOrMonthReportAct.this.mTvAllUsTimeMin.setText(spannableString3);
                            WeekOrMonthReportAct.this.mTvAllUsTimeMin.setVisibility(0);
                        }
                    }
                    WeekOrMonthReportAct.this.mTvAllDoNum.setText(weekOrMonthReportBean.getData().getQuestion_total().getAll_question_num());
                    WeekOrMonthReportAct.this.mTvPercent.setText(weekOrMonthReportBean.getData().getQuestion_total().getRight_rate());
                    WeekOrMonthReportAct.this.taskView.setData(!TextUtils.isEmpty(weekOrMonthReportBean.getData().getQuestion_total().getOwn_rank()) ? (int) Double.parseDouble(weekOrMonthReportBean.getData().getQuestion_total().getOwn_rank()) : 0, TextUtils.isEmpty(weekOrMonthReportBean.getData().getQuestion_total().getAll_rank()) ? 0 : (int) Double.parseDouble(weekOrMonthReportBean.getData().getQuestion_total().getAll_rank()), weekOrMonthReportBean.getData().getQuestion_total().getRight_rate(), weekOrMonthReportBean.getData().getQuestion_total().getPeople_percent());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(double d3, double d4, int i2) {
        this.pointsProgressView.setCurrentPercent((int) d3, (int) d4, i2 + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(double d3, double d4) {
        this.pointsProgressView.setCurrentPercent((int) d3, (int) d4, "0");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(AppBarLayout appBarLayout, int i2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        float f2 = i2 * 1.0f;
        this.rellogview.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) == 0.0f) {
            this.rellogview.setVisibility(8);
            this.mNavTitle.setTextColor(getColor(R.color.first_txt_color));
            this.toobars1.setBackgroundColor(getColor(R.color.white));
            this.mImgBg.setVisibility(8);
            this.mImgBack.setImageResource(R.mipmap.ic_black_back);
            this.mBtnStatistics.setImageResource(R.drawable.ic_gray_statistics);
            StatusBarCompat.setLightStatusBar(this, true);
            return;
        }
        this.mNavTitle.setTextColor(getColor(R.color.white));
        this.rellogview.setVisibility(0);
        this.toobars1.setBackgroundColor(getColor(R.color.transparent));
        this.mImgBg.setVisibility(0);
        this.mImgBack.setImageResource(R.drawable.icon_left_back);
        this.mBtnStatistics.setImageResource(R.drawable.ic_white_statistics);
        StatusBarCompat.setLightStatusBar(this, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        StatisticsMainActivity.INSTANCE.navigationToStatisticsMain(this.mContext);
    }

    public static Intent newIntent(Context context, boolean isWeek, ScoreTrendBean.TrendItemBean itemBean) {
        Intent intent = new Intent(context, (Class<?>) WeekOrMonthReportAct.class);
        intent.putExtra("isWeek", isWeek);
        intent.putExtra("item", itemBean);
        return intent;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        int aPPVersionCode;
        this.isWeek = getIntent().getBooleanExtra("isWeek", false);
        ScoreTrendBean.TrendItemBean trendItemBean = (ScoreTrendBean.TrendItemBean) getIntent().getSerializableExtra("item");
        this.id = trendItemBean.getId();
        this.appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        this.toobars1 = (Toolbar) findViewById(R.id.toobars1);
        this.toolbars = (RelativeLayout) findViewById(R.id.toolbars);
        this.rellogview = (RelativeLayout) findViewById(R.id.rellogview);
        this.collapse = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mBtnStatistics = (ImageView) findViewById(R.id.btn_statistics);
        this.taskView = (PointProgressByTaskView) findViewById(R.id.point_task_progress);
        this.mTvAllUsTime = (TextView) findViewById(R.id.tv_study_num_two);
        this.mTvAllUsTimeMin = (TextView) findViewById(R.id.tv_study_num_two_min);
        this.mTvAllDoNum = (TextView) findViewById(R.id.tv_all_number);
        this.mTvPercent = (TextView) findViewById(R.id.tv_personal_percent);
        this.mTvUpdateTime = (TextView) findViewById(R.id.tv_update_time);
        this.mTvTrendScore = (TextView) findViewById(R.id.tv_trend_score);
        this.mLyTrend = (LinearLayout) findViewById(R.id.ly_trend);
        this.mImgTred = (ImageView) findViewById(R.id.img_trend);
        this.mWebView = (WebView) findViewById(R.id.webview);
        this.mViewWeb = (RelativeLayout) findViewById(R.id.view_web);
        this.mLyWebView = (RelativeLayout) findViewById(R.id.ly_webview);
        this.pointsProgressView = (WeekReportProgressView) findViewById(R.id.progress_view);
        this.mNavTitle = (TextView) findViewById(R.id.nav_title);
        this.mImgBg = (ImageView) findViewById(R.id.img_bg);
        this.mTvScore = (TextView) findViewById(R.id.tv_score);
        this.scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        this.mNavTitle.setText(this.isWeek ? "周报" : "月报");
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        this.appbarlayout.setSelected(false);
        CollapsingToolbarLayout.LayoutParams layoutParams = new CollapsingToolbarLayout.LayoutParams(this.toobars1.getLayoutParams());
        layoutParams.setMargins(0, statusBarHeight, 0, 0);
        layoutParams.setCollapseMode(1);
        this.toobars1.setLayoutParams(layoutParams);
        CollapsingToolbarLayout.LayoutParams layoutParams2 = (CollapsingToolbarLayout.LayoutParams) this.rellogview.getLayoutParams();
        ((FrameLayout.LayoutParams) layoutParams2).topMargin = statusBarHeight + UIUtil.dip2px(this, 44.0d);
        this.rellogview.setLayoutParams(layoutParams2);
        final AppBarLayout.LayoutParams layoutParams3 = (AppBarLayout.LayoutParams) this.collapse.getLayoutParams();
        this.collapse.post(new Runnable() { // from class: com.psychiatrygarden.activity.mine.report.WeekOrMonthReportAct.1
            @Override // java.lang.Runnable
            public void run() {
                int measuredHeight = WeekOrMonthReportAct.this.collapse.getMeasuredHeight();
                ((LinearLayout.LayoutParams) layoutParams3).height = measuredHeight;
                WeekOrMonthReportAct.this.collapse.setLayoutParams(layoutParams3);
                Log.d("CollapsingToolbarHeight", "Height: " + measuredHeight);
            }
        });
        RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) this.pointsProgressView.getLayoutParams();
        if (UIUtil.getScreenWidth(this) < CommonUtil.dip2px(this, 230.0f)) {
            layoutParams4.width = UIUtil.getScreenWidth(this);
            layoutParams4.height = UIUtil.getScreenWidth(this) / 2;
            this.pointsProgressView.setLayoutParams(layoutParams4);
        }
        final double d3 = TextUtils.isEmpty(trendItemBean.getEstimated_score()) ? 0.0d : Double.parseDouble(trendItemBean.getEstimated_score());
        TextUtils.isEmpty(trendItemBean.getEstimated_score());
        final double d4 = TextUtils.isEmpty(trendItemBean.getAllScore()) ? 600.0d : Integer.parseInt(trendItemBean.getAllScore());
        if (d4 != 0.0d) {
            final int i2 = (int) ((d3 / d4) * 100.0d);
            this.pointsProgressView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.report.n
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13005c.lambda$init$0(d3, d4, i2);
                }
            }, 300L);
        } else {
            this.pointsProgressView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.report.o
                @Override // java.lang.Runnable
                public final void run() {
                    this.f13009c.lambda$init$1(d3, d4);
                }
            }, 300L);
        }
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.mine.report.p
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i3) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
                this.f13012a.lambda$init$2(appBarLayout, i3);
            }
        });
        this.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.mine.report.WeekOrMonthReportAct.2
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public void onScrollChange(@NonNull NestedScrollView v2, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            }
        });
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
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext);
        String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext);
        String secret = UserConfig.getInstance().getUser().getSecret();
        String token = UserConfig.getInstance().getUser().getToken();
        int i3 = this.isWeek ? 1 : 2;
        String imei = AndroidBaseUtils.getIMEI(ProjectApp.instance());
        try {
            aPPVersionCode = AndroidBaseUtils.getAPPVersionCode(this);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            aPPVersionCode = R2.attr.plvDrag;
        }
        this.mWebView.loadUrl(NetworkRequestsURL.statisticalChatInfoUrl + "echartsStudyReport.html?token=" + token + "&app_id=" + strConfig + "&secret=" + secret + "&user_id=" + UserConfig.getUserId() + "&type=" + i3 + "&subject_id=" + strConfig2 + "&part_id=" + this.id + "&uuid=" + imei + "&version=" + aPPVersionCode);
        this.mWebView.setWebViewClient(new MyWebViewClient());
        this.mTvScore.setText(trendItemBean.getEstimated_score());
        this.mTvTrendScore.setText(trendItemBean.getEstimated_score_trend());
        if (trendItemBean.getTrend().equals("1")) {
            this.mTvTrendScore.setTextColor(getColor(R.color.main_theme_color));
            this.mLyTrend.setBackgroundResource(R.drawable.shape_btn_light_red_radius_8);
            this.mImgTred.setImageResource(R.drawable.ic_trend_up);
        } else if (trendItemBean.getTrend().equals("2")) {
            this.mTvTrendScore.setTextColor(getColor(R.color.new_success_color));
            this.mLyTrend.setBackgroundResource(R.drawable.shape_tend_down_radius_8);
            this.mImgTred.setImageResource(R.drawable.ic_trend_down);
        } else {
            this.mLyTrend.setVisibility(8);
        }
        this.mTvUpdateTime.setText("统计时间：" + CommonUtil.getDataByHourStr(trendItemBean.getStart_time()) + " 至 " + CommonUtil.getDataByHourStr(trendItemBean.getEnd_time()));
        getData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        StatusBarCompat.setLightStatusBar(this, false);
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
        setContentView(R.layout.layout_week_month_report);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.report.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13013c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mBtnStatistics.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.report.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13014c.lambda$setListenerForWidget$4(view);
            }
        });
    }
}
