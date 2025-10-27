package com.psychiatrygarden.activity.mine.report;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
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
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.text.StrPool;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.hyphenate.easeui.utils.StatusBarCompat;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.mine.knowledge.EstimatedScoreChangeAct;
import com.psychiatrygarden.activity.vip.MemberCenterActivity;
import com.psychiatrygarden.bean.WeekOrMonthReportBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.event.BuyVipSuccessEvent;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.PointProgressByTaskView;
import com.psychiatrygarden.widget.ScoreTrendChangeDialog;
import com.psychiatrygarden.widget.ScoreTrendInfoDialog;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.greenrobot.eventbus.Subscribe;

/* loaded from: classes5.dex */
public class StudyReportAct extends BaseActivity {
    private AppBarLayout appbarlayout;
    private CollapsingToolbarLayout collapse;
    private boolean isClickExport;
    private ImageView mBtnExport;
    private TextView mBtnMonth;
    private TextView mBtnOldDetail;
    private TextView mBtnOpenVip;
    private TextView mBtnToday;
    private TextView mBtnWeek;
    private float mDownX;
    private float mDownY;
    private ImageView mImgBack;
    private ImageView mImgBg;
    private ImageView mImgTred;
    private ImageView mImgVip;
    private boolean mIsVip;
    private RelativeLayout mLyExport;
    private LinearLayout mLyOldDetail;
    private LinearLayout mLyOpenVip;
    private LinearLayout mLyScoreInfo;
    private LinearLayout mLyTrend;
    private LinearLayout mLyTrendScore;
    private RelativeLayout mLyTrendView;
    private RelativeLayout mLyWebView;
    private RelativeLayout mLyWebViewScore;
    private TextView mNavTitle;
    private TextView mTvAllDoNum;
    private TextView mTvAllUsTime;
    private TextView mTvAllUsTimeMin;
    private TextView mTvPercent;
    private TextView mTvRefreshTime;
    private TextView mTvScore;
    private TextView mTvTrendScore;
    private TextView mTvUpdateTime;
    private RelativeLayout mViewWeb;
    private RelativeLayout mViewWebScore;
    private WebView mWebViewScore;
    private WebView mWebViewScoreDay;
    private WebView mWebViewScoreMonth;
    private WebView mWebViewScoreWeek;
    private String pdfDownloadUrl;
    private LinearLayout rellogview;
    private String scoreDescTwo;
    private NestedScrollView scrollview;
    private PointProgressByTaskView taskView;
    private Toolbar toobars1;
    private RelativeLayout toolbars;
    private int mType = 2;
    View.OnTouchListener listener = new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.mine.report.StudyReportAct.3
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v2, MotionEvent event) {
            ViewParent parent = v2.getParent().getParent().getParent().getParent();
            float x2 = event.getX();
            float y2 = event.getY();
            int action = event.getAction();
            if (action == 0) {
                StudyReportAct.this.mDownX = x2;
                StudyReportAct.this.mDownY = y2;
            } else if (action == 2) {
                float fAbs = Math.abs(x2 - StudyReportAct.this.mDownX);
                float fAbs2 = Math.abs(y2 - StudyReportAct.this.mDownY);
                if (fAbs > fAbs2 && fAbs2 < 30.0f) {
                    parent.requestDisallowInterceptTouchEvent(true);
                    v2.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                } else if (fAbs < fAbs2) {
                    if (fAbs2 < 30.0f) {
                        v2.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        v2.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
            }
            return false;
        }
    };

    public class JavaScriptInterface {
        Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void openVip(String dataValue) {
            Intent intent = new Intent(StudyReportAct.this, (Class<?>) MemberCenterActivity.class);
            intent.putExtra("psotision", 0);
            StudyReportAct.this.startActivity(intent);
        }

        @JavascriptInterface
        public void pdfHandler(String pdfUrl) {
            if (TextUtils.isEmpty(pdfUrl)) {
                return;
            }
            StudyReportAct.this.pdfDownloadUrl = pdfUrl;
            if (StudyReportAct.this.isClickExport) {
                StudyReportAct.this.downloadPdf();
            }
        }
    }

    public class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            StudyReportAct.this.mLyWebView.getLayoutParams().height = -2;
            StudyReportAct.this.mViewWeb.setVisibility(8);
        }
    }

    public class ScoreWebViewClient extends WebViewClient {
        private ScoreWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            StudyReportAct.this.mLyWebViewScore.getLayoutParams().height = -2;
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String s2, Bitmap bitmap) {
            super.onPageStarted(webView, s2, bitmap);
        }
    }

    private void createReport() {
        int aPPVersionCode;
        String str = ProjectApp.instance().getCurrentEnvH5Url() + "pdfHtml.html?";
        try {
            aPPVersionCode = AndroidBaseUtils.getAPPVersionCode(this);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            aPPVersionCode = R2.attr.plvDrag;
        }
        String str2 = str + ("token=" + UserConfig.getInstance().getUser().getToken() + "&secret=" + UserConfig.getInstance().getUser().getSecret() + "&app_id=" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1") + "&version=" + aPPVersionCode + "&uuid=" + AndroidBaseUtils.getIMEI(ProjectApp.instance()) + "&user_id=" + UserConfig.getUserId() + "&subject_id=" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this));
        WebView webView = (WebView) findViewById(R.id.report_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setSavePassword(false);
        webView.getSettings().setCacheMode(2);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new JavaScriptInterface(this.mContext), "AndroidInterface");
        webView.setWebViewClient(new WebViewClient() { // from class: com.psychiatrygarden.activity.mine.report.StudyReportAct.4
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtils.d("pdf_file", "----获取链接完成----");
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtils.d("pdf_file", "----获取pdf报告链接----");
            }
        });
        webView.loadUrl(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadPdf() {
        if (TextUtils.isEmpty(this.pdfDownloadUrl)) {
            this.isClickExport = true;
            showProgressDialog("报告导出中...");
            createReport();
            return;
        }
        if (!this.isClickExport) {
            showProgressDialog("报告导出中...");
        }
        File file = new File(Build.VERSION.SDK_INT >= 29 ? getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getPath() : Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath());
        try {
            if (!file.exists()) {
                file.mkdir();
            }
            String str = UserConfig.getInstance().getUser().getNickname() + "的学习报告" + new SimpleDateFormat(DatePattern.CHINESE_DATE_PATTERN).format(new Date()) + this.pdfDownloadUrl.substring(this.pdfDownloadUrl.lastIndexOf(StrPool.DOT));
            YJYHttpUtils.getFinalHttp().download(this.pdfDownloadUrl, file.getAbsolutePath() + File.separator + str, new AjaxCallBack<File>() { // from class: com.psychiatrygarden.activity.mine.report.StudyReportAct.7
                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onFailure(Throwable t2, int errorNo, String strMsg) {
                    super.onFailure(t2, errorNo, strMsg);
                    StudyReportAct.this.hideProgressDialog();
                }

                @Override // net.tsz.afinal.http.AjaxCallBack
                public void onSuccess(File file2) {
                    StudyReportAct.this.hideProgressDialog();
                    if (file2 != null) {
                        StudyReportAct.this.isClickExport = false;
                        CommonUtil.showDialog(StudyReportAct.this, file2.getAbsolutePath(), file2.getAbsolutePath().substring(file2.getAbsolutePath().lastIndexOf(StrPool.DOT) + 1));
                    }
                }
            });
        } catch (Exception e2) {
            hideProgressDialog();
            e2.printStackTrace();
        }
    }

    private void getData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", this.mType + "");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyReport, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.report.StudyReportAct.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws NumberFormatException {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    WeekOrMonthReportBean weekOrMonthReportBean = (WeekOrMonthReportBean) new Gson().fromJson(s2, WeekOrMonthReportBean.class);
                    if (!weekOrMonthReportBean.getCode().equals("200") || weekOrMonthReportBean.getData() == null) {
                        return;
                    }
                    if (weekOrMonthReportBean.getData().getQuestion_total() != null) {
                        StudyReportAct.this.mIsVip = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, weekOrMonthReportBean.getData().getQuestion_total().getIs_vip()).equals("1");
                        StudyReportAct studyReportAct = StudyReportAct.this;
                        studyReportAct.showBtnByVip(studyReportAct.mType);
                        if (TextUtils.isEmpty(weekOrMonthReportBean.getData().getQuestion_total().getStart_time())) {
                            StudyReportAct.this.mTvRefreshTime.setVisibility(8);
                        } else {
                            StudyReportAct.this.mTvRefreshTime.setVisibility(0);
                            StudyReportAct.this.mTvRefreshTime.setText(weekOrMonthReportBean.getData().getQuestion_total().getStart_time());
                        }
                        if (TextUtils.isEmpty(weekOrMonthReportBean.getData().getQuestion_total().getAll_duration()) || weekOrMonthReportBean.getData().getQuestion_total().getAll_duration().equals("0")) {
                            StudyReportAct.this.mTvAllUsTime.setVisibility(8);
                            SpannableString spannableString = new SpannableString("0m");
                            spannableString.setSpan(new AbsoluteSizeSpan(20, true), 0, 1, 33);
                            StudyReportAct.this.mTvAllUsTimeMin.setText(spannableString);
                            StudyReportAct.this.mTvAllUsTimeMin.setVisibility(0);
                        } else {
                            long j2 = Long.parseLong(weekOrMonthReportBean.getData().getQuestion_total().getAll_duration());
                            int i2 = (int) (j2 / 60);
                            int i3 = (int) (j2 % 60);
                            if (i2 == 0) {
                                StudyReportAct.this.mTvAllUsTime.setVisibility(8);
                            } else {
                                String str = i2 + "h";
                                int iIndexOf = str.indexOf("h");
                                SpannableString spannableString2 = new SpannableString(str);
                                spannableString2.setSpan(new AbsoluteSizeSpan(20, true), 0, iIndexOf, 33);
                                StudyReportAct.this.mTvAllUsTime.setText(spannableString2);
                                StudyReportAct.this.mTvAllUsTime.setVisibility(0);
                            }
                            if (i3 == 0) {
                                StudyReportAct.this.mTvAllUsTimeMin.setVisibility(8);
                            } else {
                                String str2 = i3 + "m";
                                int iIndexOf2 = str2.indexOf("m");
                                SpannableString spannableString3 = new SpannableString(str2);
                                spannableString3.setSpan(new AbsoluteSizeSpan(20, true), 0, iIndexOf2, 33);
                                StudyReportAct.this.mTvAllUsTimeMin.setText(spannableString3);
                                StudyReportAct.this.mTvAllUsTimeMin.setVisibility(0);
                            }
                        }
                        StudyReportAct.this.mTvAllDoNum.setText(weekOrMonthReportBean.getData().getQuestion_total().getAll_question_num());
                        StudyReportAct.this.mTvPercent.setText(weekOrMonthReportBean.getData().getQuestion_total().getRight_rate());
                        StudyReportAct.this.taskView.setData(!TextUtils.isEmpty(weekOrMonthReportBean.getData().getQuestion_total().getRight_rate()) ? Integer.parseInt(weekOrMonthReportBean.getData().getQuestion_total().getRight_rate()) : 0, !TextUtils.isEmpty(weekOrMonthReportBean.getData().getQuestion_total().getAll_rank()) ? Integer.parseInt(weekOrMonthReportBean.getData().getQuestion_total().getAll_rank()) : 0, weekOrMonthReportBean.getData().getQuestion_total().getOwn_rank(), weekOrMonthReportBean.getData().getQuestion_total().getPeople_percent());
                    }
                    if (weekOrMonthReportBean.getData().getEstimated_score_detail() != null) {
                        WeekOrMonthReportBean.EstimatedScoreDataBean estimated_score_detail = weekOrMonthReportBean.getData().getEstimated_score_detail();
                        StudyReportAct.this.scoreDescTwo = estimated_score_detail.getEstimated_score_title();
                        int i4 = (TextUtils.isEmpty(estimated_score_detail.getAll_score()) || estimated_score_detail.getAll_score().equals("0")) ? 600 : Integer.parseInt(estimated_score_detail.getAll_score());
                        StudyReportAct.this.mWebViewScore.loadUrl(NetworkRequestsURL.statisticalChatInfoUrl + "estimatedPoints.html?end_num=" + (i4 + "&percent=" + (!TextUtils.isEmpty(estimated_score_detail.getToday_score()) ? (Float.parseFloat(estimated_score_detail.getToday_score()) / i4) * 100.0f : 0.0f) + "&out_text=24年平均分" + estimated_score_detail.getLast_year_score() + "&inside_text=及格分" + estimated_score_detail.getPass_score() + "&out_precent=" + (!TextUtils.isEmpty(estimated_score_detail.getLast_year_score()) ? (Float.parseFloat(estimated_score_detail.getLast_year_score()) / i4) * 100.0f : 0.0f) + "&inside_precent=" + (TextUtils.isEmpty(estimated_score_detail.getPass_score()) ? 0.0f : (Float.parseFloat(estimated_score_detail.getPass_score()) / i4) * 100.0f)));
                        StudyReportAct.this.mTvScore.setText(estimated_score_detail.getToday_score());
                        StudyReportAct.this.mTvTrendScore.setText(estimated_score_detail.getEstimated_score_trend());
                        if (estimated_score_detail.getTrend().equals("0")) {
                            StudyReportAct.this.mLyTrend.setVisibility(8);
                        } else {
                            StudyReportAct.this.mLyTrend.setVisibility(0);
                            if (!SharePreferencesUtils.readStrConfig("scoreChangeDay", StudyReportAct.this, "").equals(CommonUtil.getCurrentData()) && StudyReportAct.this.mIsVip) {
                                StudyReportAct.this.showScoreChangeDialog(Integer.parseInt(estimated_score_detail.getYesterday_score()), Integer.parseInt(estimated_score_detail.getToday_score()), estimated_score_detail.getTitle(), estimated_score_detail.getReport_img());
                            }
                            if (estimated_score_detail.getTrend().equals("1")) {
                                StudyReportAct.this.mTvTrendScore.setTextColor(StudyReportAct.this.getColor(R.color.main_theme_color));
                                StudyReportAct.this.mLyTrend.setBackgroundResource(R.drawable.shape_btn_light_red_radius_8);
                                StudyReportAct.this.mImgTred.setImageResource(R.drawable.ic_trend_up);
                            } else {
                                StudyReportAct.this.mTvTrendScore.setTextColor(StudyReportAct.this.getColor(R.color.new_success_color));
                                StudyReportAct.this.mLyTrend.setBackgroundResource(R.drawable.shape_tend_down_radius_8);
                                StudyReportAct.this.mImgTred.setImageResource(R.drawable.ic_trend_down);
                            }
                        }
                        StudyReportAct.this.mTvUpdateTime.setText("更新时间：" + weekOrMonthReportBean.getData().getEstimated_score_detail().getUpdate_time());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getFilterData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("type", this.mType + "");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.studyReport, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.report.StudyReportAct.6
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws NumberFormatException {
                super.onSuccess((AnonymousClass6) s2);
                try {
                    WeekOrMonthReportBean weekOrMonthReportBean = (WeekOrMonthReportBean) new Gson().fromJson(s2, WeekOrMonthReportBean.class);
                    if (!weekOrMonthReportBean.getCode().equals("200") || weekOrMonthReportBean.getData() == null || weekOrMonthReportBean.getData().getQuestion_total() == null) {
                        return;
                    }
                    if (TextUtils.isEmpty(weekOrMonthReportBean.getData().getQuestion_total().getAll_duration()) || weekOrMonthReportBean.getData().getQuestion_total().getAll_duration().equals("0")) {
                        StudyReportAct.this.mTvAllUsTime.setVisibility(8);
                        SpannableString spannableString = new SpannableString("0m");
                        spannableString.setSpan(new AbsoluteSizeSpan(20, true), 0, 1, 33);
                        StudyReportAct.this.mTvAllUsTimeMin.setText(spannableString);
                        StudyReportAct.this.mTvAllUsTimeMin.setVisibility(0);
                    } else {
                        long j2 = Long.parseLong(weekOrMonthReportBean.getData().getQuestion_total().getAll_duration());
                        int i2 = (int) (j2 / 60);
                        int i3 = (int) (j2 % 60);
                        if (i2 == 0) {
                            StudyReportAct.this.mTvAllUsTime.setVisibility(8);
                        } else {
                            String str = i2 + "h";
                            int iIndexOf = str.indexOf("h");
                            SpannableString spannableString2 = new SpannableString(str);
                            spannableString2.setSpan(new AbsoluteSizeSpan(20, true), 0, iIndexOf, 33);
                            StudyReportAct.this.mTvAllUsTime.setText(spannableString2);
                            StudyReportAct.this.mTvAllUsTime.setVisibility(0);
                        }
                        if (i3 == 0) {
                            StudyReportAct.this.mTvAllUsTimeMin.setVisibility(8);
                        } else {
                            String str2 = i3 + "m";
                            int iIndexOf2 = str2.indexOf("m");
                            SpannableString spannableString3 = new SpannableString(str2);
                            spannableString3.setSpan(new AbsoluteSizeSpan(20, true), 0, iIndexOf2, 33);
                            StudyReportAct.this.mTvAllUsTimeMin.setText(spannableString3);
                            StudyReportAct.this.mTvAllUsTimeMin.setVisibility(0);
                        }
                    }
                    StudyReportAct.this.mTvAllDoNum.setText(weekOrMonthReportBean.getData().getQuestion_total().getAll_question_num());
                    StudyReportAct.this.mTvPercent.setText(weekOrMonthReportBean.getData().getQuestion_total().getRight_rate());
                    StudyReportAct.this.taskView.setData(!TextUtils.isEmpty(weekOrMonthReportBean.getData().getQuestion_total().getOwn_rank()) ? Integer.parseInt(weekOrMonthReportBean.getData().getQuestion_total().getOwn_rank()) : 0, TextUtils.isEmpty(weekOrMonthReportBean.getData().getQuestion_total().getAll_rank()) ? 0 : Integer.parseInt(weekOrMonthReportBean.getData().getQuestion_total().getAll_rank()), weekOrMonthReportBean.getData().getQuestion_total().getRight_rate(), weekOrMonthReportBean.getData().getQuestion_total().getPeople_percent());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private String getWebUrl(int type) {
        int aPPVersionCode;
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
        return NetworkRequestsURL.statisticalChatInfoUrl + "echartsStudyReport.html?token=" + token + "&app_id=" + strConfig + "&secret=" + secret + "&user_id=" + UserConfig.getUserId() + "&type=" + type + "&subject_id=" + strConfig2 + "&uuid=" + imei + "&version=" + aPPVersionCode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(AppBarLayout appBarLayout, int i2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        float f2 = i2 * 1.0f;
        this.rellogview.setAlpha(1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()));
        if (1.0f - Math.abs(f2 / appBarLayout.getTotalScrollRange()) == 0.0f) {
            this.mNavTitle.setTextColor(this.mContext.getColor(R.color.first_txt_color));
            this.mImgBack.setImageResource(R.mipmap.ic_black_back);
            this.mBtnExport.setImageResource(R.drawable.ic_gray_export);
            this.mImgBg.setVisibility(8);
            StatusBarCompat.setLightStatusBar(this, true);
            return;
        }
        this.mTvUpdateTime.setAlpha(1.0f);
        this.mNavTitle.setTextColor(this.mContext.getColor(R.color.white));
        this.mImgBack.setImageResource(R.drawable.icon_left_back);
        this.mBtnExport.setImageResource(R.drawable.ic_white_export);
        this.mImgBg.setVisibility(0);
        StatusBarCompat.setLightStatusBar(this, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.mIsVip) {
            downloadPdf();
            return;
        }
        Intent intent = new Intent(this, (Class<?>) MemberCenterActivity.class);
        intent.putExtra("psotision", 0);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEventMainThread$10(String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEventMainThread$11(String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onEventMainThread$12(String str) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        new XPopup.Builder(this).popupAnimation(null).asCustom(new ScoreTrendInfoDialog(this, this.scoreDescTwo, "预估分说明")).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$4(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.mIsVip) {
            startActivity(EstimatedScoreChangeAct.newIntent(this, true));
            return;
        }
        Intent intent = new Intent(this, (Class<?>) MemberCenterActivity.class);
        intent.putExtra("psotision", 0);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$5(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.mType = 1;
        this.mBtnToday.setTypeface(Typeface.DEFAULT_BOLD);
        this.mBtnWeek.setTypeface(Typeface.DEFAULT);
        this.mBtnMonth.setTypeface(Typeface.DEFAULT);
        this.mBtnToday.setTextColor(ContextCompat.getColor(this, R.color.first_txt_color));
        this.mBtnWeek.setTextColor(ContextCompat.getColor(this, R.color.third_txt_color));
        this.mBtnMonth.setTextColor(ContextCompat.getColor(this, R.color.third_txt_color));
        this.mBtnToday.setBackgroundResource(R.drawable.shape_new_bg_one_color_no_night_corners);
        this.mBtnWeek.setBackgroundResource(R.color.transparent);
        this.mBtnMonth.setBackgroundResource(R.color.transparent);
        this.mWebViewScoreWeek.setVisibility(8);
        this.mWebViewScoreDay.setVisibility(0);
        this.mWebViewScoreMonth.setVisibility(8);
        submitValutToH5(this.mType + "");
        showBtnByVip(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$6(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.mType = 2;
        this.mBtnToday.setTypeface(Typeface.DEFAULT);
        this.mBtnWeek.setTypeface(Typeface.DEFAULT_BOLD);
        this.mBtnMonth.setTypeface(Typeface.DEFAULT);
        this.mBtnWeek.setBackgroundResource(R.drawable.shape_new_bg_one_color_no_night_corners);
        this.mBtnToday.setBackgroundResource(R.color.transparent);
        this.mBtnMonth.setBackgroundResource(R.color.transparent);
        this.mBtnWeek.setTextColor(ContextCompat.getColor(this, R.color.first_txt_color));
        this.mBtnToday.setTextColor(ContextCompat.getColor(this, R.color.third_txt_color));
        this.mBtnMonth.setTextColor(ContextCompat.getColor(this, R.color.third_txt_color));
        this.mWebViewScoreWeek.setVisibility(0);
        this.mWebViewScoreDay.setVisibility(8);
        this.mWebViewScoreMonth.setVisibility(8);
        submitValutToH5(this.mType + "");
        this.mBtnOldDetail.setText("往期周报");
        showBtnByVip(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$7(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.mType = 3;
        this.mBtnToday.setTypeface(Typeface.DEFAULT);
        this.mBtnWeek.setTypeface(Typeface.DEFAULT);
        this.mBtnMonth.setTypeface(Typeface.DEFAULT_BOLD);
        this.mBtnMonth.setBackgroundResource(R.drawable.shape_new_bg_one_color_no_night_corners);
        this.mBtnWeek.setBackgroundResource(R.color.transparent);
        this.mBtnToday.setBackgroundResource(R.color.transparent);
        this.mBtnMonth.setTextColor(ContextCompat.getColor(this, R.color.first_txt_color));
        this.mBtnToday.setTextColor(ContextCompat.getColor(this, R.color.third_txt_color));
        this.mBtnWeek.setTextColor(ContextCompat.getColor(this, R.color.third_txt_color));
        this.mWebViewScoreWeek.setVisibility(8);
        this.mWebViewScoreDay.setVisibility(8);
        this.mWebViewScoreMonth.setVisibility(0);
        this.mBtnOldDetail.setText("往期月报");
        submitValutToH5(this.mType + "");
        showBtnByVip(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$8(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.mType == 2) {
            startActivity(EstimatedScoreChangeAct.newIntent(this, true));
        } else {
            startActivity(EstimatedScoreChangeAct.newIntent(this, false));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$9(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) MemberCenterActivity.class);
        intent.putExtra("psotision", 0);
        startActivity(intent);
    }

    public static void newIntent(Context context) {
        context.startActivity(new Intent(context, (Class<?>) StudyReportAct.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBtnByVip(int type) {
        if (type == 1) {
            this.mLyOldDetail.setVisibility(8);
            this.mLyOpenVip.setVisibility(this.mIsVip ? 8 : 0);
        } else {
            this.mLyOldDetail.setVisibility(this.mIsVip ? 0 : 8);
            this.mLyOpenVip.setVisibility(this.mIsVip ? 8 : 0);
        }
        this.mImgVip.setVisibility(this.mIsVip ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showScoreChangeDialog(int yesterdayScore, int score, String desc, String img) {
        SharePreferencesUtils.writeStrConfig("scoreChangeDay", CommonUtil.getCurrentData(), this);
        new XPopup.Builder(this).popupAnimation(null).asCustom(new ScoreTrendChangeDialog(this, yesterdayScore, score, img, desc)).show();
    }

    private void submitValutToH5(String type) {
        getFilterData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mLyScoreInfo = (LinearLayout) findViewById(R.id.ly_score_info);
        this.appbarlayout = (AppBarLayout) findViewById(R.id.appbarlayout);
        this.toobars1 = (Toolbar) findViewById(R.id.toobars1);
        this.toolbars = (RelativeLayout) findViewById(R.id.toolbars);
        this.rellogview = (LinearLayout) findViewById(R.id.rellogview);
        this.collapse = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        this.mNavTitle = (TextView) findViewById(R.id.nav_title);
        this.mLyTrendView = (RelativeLayout) findViewById(R.id.ly_change_view);
        this.taskView = (PointProgressByTaskView) findViewById(R.id.point_task_progress);
        this.mTvUpdateTime = (TextView) findViewById(R.id.tv_update_time);
        this.mTvAllUsTime = (TextView) findViewById(R.id.tv_study_num_two);
        this.mTvAllUsTimeMin = (TextView) findViewById(R.id.tv_study_num_two_min);
        this.mTvAllDoNum = (TextView) findViewById(R.id.tv_all_number);
        this.mTvPercent = (TextView) findViewById(R.id.tv_personal_percent);
        this.mImgBack = (ImageView) findViewById(R.id.iv_actionbar_back);
        this.mBtnToday = (TextView) findViewById(R.id.btn_today);
        this.mBtnWeek = (TextView) findViewById(R.id.btn_week);
        this.mBtnMonth = (TextView) findViewById(R.id.btn_month);
        this.mWebViewScore = (WebView) findViewById(R.id.webview_score);
        this.mViewWeb = (RelativeLayout) findViewById(R.id.view_web);
        this.mLyWebView = (RelativeLayout) findViewById(R.id.ly_webview);
        this.mTvTrendScore = (TextView) findViewById(R.id.tv_trend_score);
        this.mLyTrend = (LinearLayout) findViewById(R.id.ly_trend);
        this.mImgTred = (ImageView) findViewById(R.id.img_trend);
        this.mTvScore = (TextView) findViewById(R.id.tv_score);
        this.mLyTrendScore = (LinearLayout) findViewById(R.id.linearLayout2);
        this.mWebViewScoreDay = (WebView) findViewById(R.id.webview_score_day);
        this.mWebViewScoreWeek = (WebView) findViewById(R.id.webview_score_week);
        this.mWebViewScoreMonth = (WebView) findViewById(R.id.webview_score_month);
        this.mViewWebScore = (RelativeLayout) findViewById(R.id.view_web_score);
        this.mLyWebViewScore = (RelativeLayout) findViewById(R.id.ly_webview_score);
        this.mLyExport = (RelativeLayout) findViewById(R.id.btn_export);
        this.mBtnExport = (ImageView) findViewById(R.id.img_export);
        this.mImgVip = (ImageView) findViewById(R.id.img_vip);
        this.mImgBg = (ImageView) findViewById(R.id.img_bg);
        this.mBtnOldDetail = (TextView) findViewById(R.id.btn_old_detail);
        this.mLyOldDetail = (LinearLayout) findViewById(R.id.ly_old_detail);
        this.mLyOpenVip = (LinearLayout) findViewById(R.id.ly_open_vip);
        this.mBtnOpenVip = (TextView) findViewById(R.id.btn_open_vip);
        this.mTvRefreshTime = (TextView) findViewById(R.id.tv_refresh_time);
        this.scrollview = (NestedScrollView) findViewById(R.id.scrollview);
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(this.mContext);
        this.appbarlayout.setSelected(false);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mLyTrendScore.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTvUpdateTime.getLayoutParams();
        if (ScreenUtil.isTablet(this)) {
            this.mLyWebViewScore.getLayoutParams().height = ScreenUtil.getPxByDp(this.mContext, 215);
            layoutParams.bottomMargin = ScreenUtil.getPxByDp(this.mContext, 45);
            layoutParams2.topMargin = ScreenUtil.getPxByDp(this.mContext, 0);
        } else {
            this.mLyWebViewScore.getLayoutParams().height = ScreenUtil.getPxByDp(this.mContext, 200);
            layoutParams.bottomMargin = ScreenUtil.getPxByDp(this.mContext, 30);
            layoutParams2.topMargin = ScreenUtil.getPxByDp(this.mContext, 10);
        }
        this.mLyTrendScore.setLayoutParams(layoutParams);
        this.mTvUpdateTime.setLayoutParams(layoutParams2);
        CollapsingToolbarLayout.LayoutParams layoutParams3 = new CollapsingToolbarLayout.LayoutParams(this.toobars1.getLayoutParams());
        layoutParams3.setMargins(0, statusBarHeight, 0, 0);
        layoutParams3.setCollapseMode(1);
        this.toobars1.setLayoutParams(layoutParams3);
        CollapsingToolbarLayout.LayoutParams layoutParams4 = (CollapsingToolbarLayout.LayoutParams) this.rellogview.getLayoutParams();
        ((FrameLayout.LayoutParams) layoutParams4).topMargin = statusBarHeight + UIUtil.dip2px(this, 60.0d);
        this.rellogview.setLayoutParams(layoutParams4);
        final AppBarLayout.LayoutParams layoutParams5 = (AppBarLayout.LayoutParams) this.collapse.getLayoutParams();
        this.collapse.post(new Runnable() { // from class: com.psychiatrygarden.activity.mine.report.StudyReportAct.1
            @Override // java.lang.Runnable
            public void run() {
                int measuredHeight = StudyReportAct.this.collapse.getMeasuredHeight();
                ((LinearLayout.LayoutParams) layoutParams5).height = measuredHeight;
                StudyReportAct.this.collapse.setLayoutParams(layoutParams5);
                Log.d("CollapsingToolbarHeight", "Height: " + measuredHeight);
            }
        });
        this.appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.psychiatrygarden.activity.mine.report.g
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
                this.f12998a.lambda$init$0(appBarLayout, i2);
            }
        });
        this.mWebViewScore.getSettings().setJavaScriptEnabled(true);
        this.mWebViewScore.getSettings().setUseWideViewPort(true);
        this.mWebViewScore.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.mWebViewScore.getSettings().setDisplayZoomControls(false);
        this.mWebViewScore.getSettings().setAllowFileAccess(true);
        this.mWebViewScore.getSettings().setBuiltInZoomControls(true);
        this.mWebViewScore.getSettings().setLoadWithOverviewMode(true);
        this.mWebViewScore.setHorizontalScrollBarEnabled(true);
        this.mWebViewScore.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebViewScore.setVerticalScrollBarEnabled(false);
        this.mWebViewScore.getSettings().setSavePassword(false);
        this.mWebViewScore.getSettings().setCacheMode(2);
        this.mWebViewScore.getSettings().setDomStorageEnabled(true);
        this.mWebViewScore.addJavascriptInterface(new JavaScriptInterface(this.mContext), "Android");
        this.mWebViewScore.setWebViewClient(new ScoreWebViewClient());
        WebView.setWebContentsDebuggingEnabled(true);
        this.mWebViewScoreWeek.loadUrl(getWebUrl(this.mType));
        this.mWebViewScoreWeek.getSettings().setJavaScriptEnabled(true);
        this.mWebViewScoreWeek.getSettings().setUseWideViewPort(true);
        this.mWebViewScoreWeek.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.mWebViewScoreWeek.getSettings().setDisplayZoomControls(false);
        this.mWebViewScoreWeek.getSettings().setAllowFileAccess(true);
        this.mWebViewScoreWeek.getSettings().setBuiltInZoomControls(true);
        this.mWebViewScoreWeek.getSettings().setLoadWithOverviewMode(true);
        this.mWebViewScoreWeek.setHorizontalScrollBarEnabled(true);
        this.mWebViewScoreWeek.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebViewScoreWeek.setVerticalScrollBarEnabled(false);
        this.mWebViewScoreWeek.getSettings().setSavePassword(false);
        this.mWebViewScoreWeek.getSettings().setCacheMode(2);
        this.mWebViewScoreWeek.getSettings().setDomStorageEnabled(true);
        this.mWebViewScoreWeek.addJavascriptInterface(new JavaScriptInterface(this.mContext), "Android");
        this.mWebViewScoreWeek.setWebViewClient(new MyWebViewClient());
        WebView.setWebContentsDebuggingEnabled(true);
        this.mWebViewScoreDay.loadUrl(getWebUrl(1));
        this.mWebViewScoreDay.getSettings().setJavaScriptEnabled(true);
        this.mWebViewScoreDay.getSettings().setUseWideViewPort(true);
        this.mWebViewScoreDay.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.mWebViewScoreDay.getSettings().setDisplayZoomControls(false);
        this.mWebViewScoreDay.getSettings().setAllowFileAccess(true);
        this.mWebViewScoreDay.getSettings().setBuiltInZoomControls(true);
        this.mWebViewScoreDay.getSettings().setLoadWithOverviewMode(true);
        this.mWebViewScoreDay.setHorizontalScrollBarEnabled(true);
        this.mWebViewScoreDay.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebViewScoreDay.setVerticalScrollBarEnabled(false);
        this.mWebViewScoreDay.getSettings().setSavePassword(false);
        this.mWebViewScoreDay.getSettings().setCacheMode(2);
        this.mWebViewScoreDay.getSettings().setDomStorageEnabled(true);
        this.mWebViewScoreDay.addJavascriptInterface(new JavaScriptInterface(this.mContext), "Android");
        this.mWebViewScoreDay.setWebViewClient(new MyWebViewClient());
        WebView.setWebContentsDebuggingEnabled(true);
        this.mWebViewScoreMonth.loadUrl(getWebUrl(3));
        this.mWebViewScoreMonth.getSettings().setJavaScriptEnabled(true);
        this.mWebViewScoreMonth.getSettings().setUseWideViewPort(true);
        this.mWebViewScoreMonth.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.mWebViewScoreMonth.getSettings().setDisplayZoomControls(false);
        this.mWebViewScoreMonth.getSettings().setAllowFileAccess(true);
        this.mWebViewScoreMonth.getSettings().setBuiltInZoomControls(true);
        this.mWebViewScoreMonth.getSettings().setLoadWithOverviewMode(true);
        this.mWebViewScoreMonth.setHorizontalScrollBarEnabled(true);
        this.mWebViewScoreMonth.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebViewScoreMonth.setVerticalScrollBarEnabled(false);
        this.mWebViewScoreMonth.getSettings().setSavePassword(false);
        this.mWebViewScoreMonth.getSettings().setCacheMode(2);
        this.mWebViewScoreMonth.getSettings().setDomStorageEnabled(true);
        this.mWebViewScoreMonth.addJavascriptInterface(new JavaScriptInterface(this.mContext), "Android");
        this.mWebViewScoreMonth.setWebViewClient(new MyWebViewClient());
        WebView.setWebContentsDebuggingEnabled(true);
        this.scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.mine.report.StudyReportAct.2
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public void onScrollChange(@NonNull NestedScrollView v2, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            }
        });
        this.mLyExport.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.report.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12999c.lambda$init$1(view);
            }
        });
        this.mWebViewScoreDay.setOnTouchListener(this.listener);
        this.mWebViewScoreWeek.setOnTouchListener(this.listener);
        this.mWebViewScoreMonth.setOnTouchListener(this.listener);
        getData();
        createReport();
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
        WebView webView = this.mWebViewScoreWeek;
        if (webView != null) {
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(this.mWebViewScoreWeek);
            }
            this.mWebViewScoreWeek.stopLoading();
            this.mWebViewScoreWeek.getSettings().setJavaScriptEnabled(false);
            this.mWebViewScoreWeek.clearHistory();
            this.mWebViewScoreWeek.clearView();
            this.mWebViewScoreWeek.removeAllViews();
            this.mWebViewScoreWeek.destroy();
            this.mWebViewScoreWeek = null;
        }
        WebView webView2 = this.mWebViewScoreDay;
        if (webView2 != null) {
            ViewParent parent2 = webView2.getParent();
            if (parent2 != null) {
                ((ViewGroup) parent2).removeView(this.mWebViewScoreDay);
            }
            this.mWebViewScoreDay.stopLoading();
            this.mWebViewScoreDay.getSettings().setJavaScriptEnabled(false);
            this.mWebViewScoreDay.clearHistory();
            this.mWebViewScoreDay.clearView();
            this.mWebViewScoreDay.removeAllViews();
            this.mWebViewScoreDay.destroy();
            this.mWebViewScoreDay = null;
        }
        WebView webView3 = this.mWebViewScoreMonth;
        if (webView3 != null) {
            ViewParent parent3 = webView3.getParent();
            if (parent3 != null) {
                ((ViewGroup) parent3).removeView(this.mWebViewScoreMonth);
            }
            this.mWebViewScoreMonth.stopLoading();
            this.mWebViewScoreMonth.getSettings().setJavaScriptEnabled(false);
            this.mWebViewScoreMonth.clearHistory();
            this.mWebViewScoreMonth.clearView();
            this.mWebViewScoreMonth.removeAllViews();
            this.mWebViewScoreMonth.destroy();
            this.mWebViewScoreMonth = null;
        }
        WebView webView4 = this.mWebViewScore;
        if (webView4 != null) {
            ViewParent parent4 = webView4.getParent();
            if (parent4 != null) {
                ((ViewGroup) parent4).removeView(this.mWebViewScore);
            }
            this.mWebViewScore.stopLoading();
            this.mWebViewScore.getSettings().setJavaScriptEnabled(false);
            this.mWebViewScore.clearHistory();
            this.mWebViewScore.clearView();
            this.mWebViewScore.removeAllViews();
            this.mWebViewScore.destroy();
            this.mWebViewScore = null;
        }
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(BuyVipSuccessEvent event) {
        if (event.getSuccess()) {
            UserConfig.getInstance().getUser().setIs_vip("1");
            this.mIsVip = true;
            this.mImgVip.setVisibility(8);
            showBtnByVip(this.mType);
            String str = "javascript:checkVipEvt(" + this.mIsVip + ")";
            this.mWebViewScoreWeek.evaluateJavascript(str, new ValueCallback() { // from class: com.psychiatrygarden.activity.mine.report.a
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    StudyReportAct.lambda$onEventMainThread$10((String) obj);
                }
            });
            this.mWebViewScoreDay.evaluateJavascript(str, new ValueCallback() { // from class: com.psychiatrygarden.activity.mine.report.e
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    StudyReportAct.lambda$onEventMainThread$11((String) obj);
                }
            });
            this.mWebViewScoreMonth.evaluateJavascript(str, new ValueCallback() { // from class: com.psychiatrygarden.activity.mine.report.f
                @Override // android.webkit.ValueCallback
                public final void onReceiveValue(Object obj) {
                    StudyReportAct.lambda$onEventMainThread$12((String) obj);
                }
            });
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_study_report);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mImgBack.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.report.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13000c.lambda$setListenerForWidget$2(view);
            }
        });
        this.mLyScoreInfo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.report.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13001c.lambda$setListenerForWidget$3(view);
            }
        });
        this.mLyTrendView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.report.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13002c.lambda$setListenerForWidget$4(view);
            }
        });
        this.mBtnToday.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.report.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13003c.lambda$setListenerForWidget$5(view);
            }
        });
        this.mBtnWeek.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.report.m
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13004c.lambda$setListenerForWidget$6(view);
            }
        });
        this.mBtnMonth.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.report.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12995c.lambda$setListenerForWidget$7(view);
            }
        });
        this.mBtnOldDetail.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.report.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12996c.lambda$setListenerForWidget$8(view);
            }
        });
        this.mBtnOpenVip.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.report.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12997c.lambda$setListenerForWidget$9(view);
            }
        });
    }
}
