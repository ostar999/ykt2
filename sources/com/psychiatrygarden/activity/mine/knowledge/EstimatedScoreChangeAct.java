package com.psychiatrygarden.activity.mine.knowledge;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.ScoreTrendBean;
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
import com.psychiatrygarden.widget.EstimatedScoreChangeItemView;
import com.psychiatrygarden.widget.WeekReportProgressView;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.lang.reflect.InvocationTargetException;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class EstimatedScoreChangeAct extends BaseActivity {
    private AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collapse;
    private boolean isLoadData = false;
    private boolean isWeek;
    private TextView mBtnMonth;
    private ImageView mBtnStatistics;
    private TextView mBtnWeek;
    private ImageView mImgBack;
    private ImageView mImgBg;
    private ImageView mImgTred;
    private LinearLayout mLyAddChangeView;
    private LinearLayout mLyTrend;
    private RelativeLayout mLyWebView;
    private TextView mNavTitle;
    private TextView mTvDesc;
    private TextView mTvScore;
    private TextView mTvTrendScore;
    private TextView mTvUpdateTime;
    private RelativeLayout mViewWeb;
    private WebView mWebView;
    private WeekReportProgressView progressView;
    private RelativeLayout rellogview;
    private Toolbar toobars1;
    private RelativeLayout toolbars;
    private WebView webView;

    /* renamed from: com.psychiatrygarden.activity.mine.knowledge.EstimatedScoreChangeAct$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(double d3, double d4, int i2) {
            EstimatedScoreChangeAct.this.progressView.setCurrentPercent((int) d3, (int) d4, i2 + "");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(double d3, double d4) {
            EstimatedScoreChangeAct.this.progressView.setCurrentPercent((int) d3, (int) d4, "0");
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass2) s2);
            try {
                ScoreTrendBean scoreTrendBean = (ScoreTrendBean) new Gson().fromJson(s2, ScoreTrendBean.class);
                if (!scoreTrendBean.getCode().equals("200") || scoreTrendBean.getData() == null) {
                    return;
                }
                if (!EstimatedScoreChangeAct.this.isLoadData) {
                    EstimatedScoreChangeAct.this.isLoadData = true;
                    EstimatedScoreChangeAct.this.mTvUpdateTime.setText("更新时间：" + scoreTrendBean.getData().getTime());
                    if (TextUtils.isEmpty(scoreTrendBean.getData().getDescribe())) {
                        EstimatedScoreChangeAct.this.mTvDesc.setVisibility(8);
                    } else {
                        EstimatedScoreChangeAct.this.mTvDesc.setVisibility(0);
                        EstimatedScoreChangeAct.this.mTvDesc.setText(scoreTrendBean.getData().getDescribe());
                    }
                    EstimatedScoreChangeAct.this.mTvScore.setText(scoreTrendBean.getData().getEstimated_score());
                    EstimatedScoreChangeAct.this.mTvTrendScore.setText(scoreTrendBean.getData().getEstimated_score_trend());
                    if (scoreTrendBean.getData().getTrend().equals("1")) {
                        EstimatedScoreChangeAct.this.mTvTrendScore.setTextColor(EstimatedScoreChangeAct.this.getColor(R.color.main_theme_color));
                        EstimatedScoreChangeAct.this.mLyTrend.setBackgroundResource(R.drawable.shape_btn_light_red_radius_8);
                        EstimatedScoreChangeAct.this.mImgTred.setImageResource(R.drawable.ic_trend_up);
                    } else if (scoreTrendBean.getData().getTrend().equals("2")) {
                        EstimatedScoreChangeAct.this.mTvTrendScore.setTextColor(EstimatedScoreChangeAct.this.getColor(R.color.new_success_color));
                        EstimatedScoreChangeAct.this.mLyTrend.setBackgroundResource(R.drawable.shape_tend_down_radius_8);
                        EstimatedScoreChangeAct.this.mImgTred.setImageResource(R.drawable.ic_trend_down);
                    } else {
                        EstimatedScoreChangeAct.this.mLyTrend.setVisibility(8);
                    }
                    final double d3 = TextUtils.isEmpty(scoreTrendBean.getData().getEstimated_score()) ? 0.0d : Double.parseDouble(scoreTrendBean.getData().getEstimated_score());
                    TextUtils.isEmpty(scoreTrendBean.getData().getEstimated_score());
                    final double d4 = TextUtils.isEmpty(scoreTrendBean.getData().getAll_score()) ? 600.0d : Integer.parseInt(scoreTrendBean.getData().getAll_score());
                    if (d4 != 0.0d) {
                        final int i2 = (int) ((d3 / d4) * 100.0d);
                        EstimatedScoreChangeAct.this.progressView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.knowledge.g
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f12879c.lambda$onSuccess$0(d3, d4, i2);
                            }
                        }, 300L);
                    } else {
                        EstimatedScoreChangeAct.this.progressView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.mine.knowledge.h
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f12884c.lambda$onSuccess$1(d3, d4);
                            }
                        }, 300L);
                    }
                }
                EstimatedScoreChangeAct.this.mLyAddChangeView.removeAllViews();
                if (scoreTrendBean.getData().getWeek() == null || scoreTrendBean.getData().getWeek().size() <= 0) {
                    return;
                }
                for (int i3 = 0; i3 < scoreTrendBean.getData().getWeek().size(); i3++) {
                    EstimatedScoreChangeItemView estimatedScoreChangeItemView = new EstimatedScoreChangeItemView(EstimatedScoreChangeAct.this);
                    estimatedScoreChangeItemView.setData(scoreTrendBean.getData().getWeek().get(i3), EstimatedScoreChangeAct.this.isWeek, scoreTrendBean.getData().getAll_score());
                    EstimatedScoreChangeAct.this.mLyAddChangeView.addView(estimatedScoreChangeItemView);
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
            EstimatedScoreChangeAct.this.mLyWebView.getLayoutParams().height = -2;
            EstimatedScoreChangeAct.this.mViewWeb.setVisibility(8);
        }
    }

    private void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", this.isWeek ? "1" : "2");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.estimatedChange, ajaxParams, new AnonymousClass2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(AppBarLayout appBarLayout, int i2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
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
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        StatisticsMainActivity.INSTANCE.navigationToStatisticsMain(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.isWeek = true;
        setUpUi(true);
        submitValutToH5("1");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.isWeek = false;
        setUpUi(false);
        submitValutToH5("2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$submitValutToH5$5(String str) {
        LogUtils.e("reveice_data_from_js", "获取到的参数====》" + str);
    }

    public static Intent newIntent(Context context, boolean isWeek) {
        Intent intent = new Intent(context, (Class<?>) EstimatedScoreChangeAct.class);
        intent.putExtra("isWeek", isWeek);
        return intent;
    }

    private void setUpUi(boolean isWeek) {
        if (isWeek) {
            this.mBtnWeek.setTypeface(Typeface.DEFAULT_BOLD);
            this.mBtnWeek.setBackgroundResource(R.drawable.shape_new_bg_one_color_no_night_corners);
            this.mBtnMonth.setBackgroundResource(R.color.transparent);
            this.mBtnMonth.setTypeface(Typeface.DEFAULT);
            this.mBtnWeek.setTextColor(ContextCompat.getColor(this, R.color.first_txt_color));
            this.mBtnMonth.setTextColor(ContextCompat.getColor(this, R.color.third_txt_color));
            return;
        }
        this.mBtnWeek.setTypeface(Typeface.DEFAULT);
        this.mBtnMonth.setBackgroundResource(R.drawable.shape_new_bg_one_color_no_night_corners);
        this.mBtnWeek.setBackgroundResource(R.color.transparent);
        this.mBtnMonth.setTypeface(Typeface.DEFAULT_BOLD);
        this.mBtnMonth.setTextColor(ContextCompat.getColor(this, R.color.first_txt_color));
        this.mBtnWeek.setTextColor(ContextCompat.getColor(this, R.color.third_txt_color));
    }

    private void submitValutToH5(String type) {
        getData();
        this.mWebView.evaluateJavascript("javascript:changeDateEvt(" + type + ")", new ValueCallback() { // from class: com.psychiatrygarden.activity.mine.knowledge.e
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                EstimatedScoreChangeAct.lambda$submitValutToH5$5((String) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        int aPPVersionCode;
        this.isWeek = getIntent().getBooleanExtra("isWeek", true);
        this.appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        this.toobars1 = (Toolbar) findViewById(R.id.toobars1);
        this.mNavTitle = (TextView) findViewById(R.id.nav_title);
        this.toolbars = (RelativeLayout) findViewById(R.id.toolbars);
        this.rellogview = (RelativeLayout) findViewById(R.id.rellogview);
        this.collapse = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mBtnStatistics = (ImageView) findViewById(R.id.btn_statistics);
        this.mImgBg = (ImageView) findViewById(R.id.img_bg);
        this.mLyAddChangeView = (LinearLayout) findViewById(R.id.ly_add_change_view);
        this.progressView = (WeekReportProgressView) findViewById(R.id.progress_view);
        this.mTvUpdateTime = (TextView) findViewById(R.id.tv_update_time);
        this.mBtnWeek = (TextView) findViewById(R.id.btn_week);
        this.mBtnMonth = (TextView) findViewById(R.id.btn_month);
        this.webView = (WebView) findViewById(R.id.webview);
        this.mTvDesc = (TextView) findViewById(R.id.tv_desc);
        this.mTvTrendScore = (TextView) findViewById(R.id.tv_trend_score);
        this.mLyTrend = (LinearLayout) findViewById(R.id.ly_trend);
        this.mImgTred = (ImageView) findViewById(R.id.img_trend);
        this.mWebView = (WebView) findViewById(R.id.webview);
        this.mViewWeb = (RelativeLayout) findViewById(R.id.view_web);
        this.mLyWebView = (RelativeLayout) findViewById(R.id.ly_webview);
        this.mTvScore = (TextView) findViewById(R.id.tv_score);
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
        ((FrameLayout.LayoutParams) layoutParams3).topMargin = statusBarHeight + UIUtil.dip2px(this, 44.0d);
        this.rellogview.setLayoutParams(layoutParams3);
        final AppBarLayout.LayoutParams layoutParams4 = (AppBarLayout.LayoutParams) this.collapse.getLayoutParams();
        this.collapse.post(new Runnable() { // from class: com.psychiatrygarden.activity.mine.knowledge.EstimatedScoreChangeAct.1
            @Override // java.lang.Runnable
            public void run() {
                int measuredHeight = EstimatedScoreChangeAct.this.collapse.getMeasuredHeight();
                ((LinearLayout.LayoutParams) layoutParams4).height = measuredHeight;
                EstimatedScoreChangeAct.this.collapse.setLayoutParams(layoutParams4);
                Log.d("CollapsingToolbarHeight", "Height: " + measuredHeight);
            }
        });
        RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) this.progressView.getLayoutParams();
        if (UIUtil.getScreenWidth(this) < CommonUtil.dip2px(this, 320.0f)) {
            layoutParams5.width = UIUtil.getScreenWidth(this);
            layoutParams5.height = UIUtil.getScreenWidth(this) / 2;
            this.progressView.setLayoutParams(layoutParams5);
        }
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
        String imei = AndroidBaseUtils.getIMEI(ProjectApp.instance());
        try {
            aPPVersionCode = AndroidBaseUtils.getAPPVersionCode(this);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            aPPVersionCode = R2.attr.plvDrag;
        }
        this.mWebView.loadUrl(NetworkRequestsURL.statisticalChatInfoUrl + "echartsOneLine.html?token=" + token + "&app_id=" + strConfig + "&secret=" + secret + "&user_id=" + UserConfig.getUserId() + "&type=" + (this.isWeek ? 1 : 2) + "&subject_id=" + strConfig2 + "&uuid=" + imei + "&version=" + aPPVersionCode);
        this.mWebView.setWebViewClient(new MyWebViewClient());
        setUpUi(this.isWeek);
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.f
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
                this.f12876a.lambda$init$0(appBarLayout, i2);
            }
        });
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
        setContentView(R.layout.layout_estimated_score_change);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12866c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mBtnStatistics.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12868c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mBtnWeek.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12870c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mBtnMonth.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12872c.lambda$setListenerForWidget$4(view);
            }
        });
    }
}
