package com.psychiatrygarden.ranking;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.google.gson.Gson;
import com.huawei.hms.push.HmsMessageService;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ChapterStatisticsBean;
import com.psychiatrygarden.bean.StatisticsTopBean;
import com.psychiatrygarden.bean.WeekOrMonthReportBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.widget.ChapterStatisticsItemView;
import com.psychiatrygarden.widget.RankUserItemView;
import com.psychiatrygarden.widget.StatisticsProgressView;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class StatisticsFragment extends BaseFragment {
    private boolean isKnowledge;
    private boolean isParentCanScroll;
    private TextView mBtnMonth;
    private TextView mBtnToday;
    private TextView mBtnWeek;
    private String mCategoryId;
    private float mDownX;
    private float mDownY;
    private String mIdentityId;
    private ImageView mImgAllPercent;
    private ImageView mImgPercent;
    private View mLineChapter;
    private LinearLayout mLyAddUserView;
    private LinearLayout mLyAddView;
    private LinearLayout mLyAllPercentView;
    private RelativeLayout mLyAllView;
    private LinearLayout mLyChapterView;
    private LinearLayout mLyHistory;
    private LinearLayout mLyPercentView;
    private LinearLayout mLyProgressView;
    private LinearLayout mLyToRank;
    private LinearLayout mLyUser;
    private RelativeLayout mLyWebView;
    private TextView mTvAllDisPercent;
    private TextView mTvAllDoNumber;
    private TextView mTvAllEndPercent;
    private TextView mTvAllFirstPercent;
    private TextView mTvAllPercent;
    private TextView mTvAllQuestionNumber;
    private TextView mTvAllUseTime;
    private TextView mTvDisPercent;
    private TextView mTvEndPercent;
    private TextView mTvFirstPercent;
    private TextView mTvHistoryAllCount;
    private TextView mTvHistoryAllCountChild;
    private TextView mTvHistoryPercent;
    private TextView mTvHistoryPercentChild;
    private TextView mTvHistoryTime;
    private TextView mTvHistoryTimeChild;
    private TextView mTvHistoryTimeMinChild;
    private RelativeLayout mViewWeb;
    private WebView mWebView;
    private StatisticsProgressView progressView;
    private NestedScrollView scrollview;
    private String mDataValue = "1";
    private int mType = 1;
    View.OnTouchListener listener = new View.OnTouchListener() { // from class: com.psychiatrygarden.ranking.StatisticsFragment.1
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v2, MotionEvent event) {
            ViewParent parent = v2.getParent().getParent().getParent().getParent();
            float x2 = event.getX();
            float y2 = event.getY();
            int action = event.getAction();
            if (action == 0) {
                StatisticsFragment.this.mDownX = x2;
                StatisticsFragment.this.mDownY = y2;
            } else if (action == 2) {
                float fAbs = Math.abs(x2 - StatisticsFragment.this.mDownX);
                float fAbs2 = Math.abs(y2 - StatisticsFragment.this.mDownY);
                LogUtils.e("scroll_view", "web_scroll===isParentCanScroll>" + StatisticsFragment.this.isParentCanScroll + ";isChatCanScroll：" + StatisticsFragment.this.isChatCanScroll);
                if (fAbs <= fAbs2 || fAbs2 >= 30.0f) {
                    if (fAbs < fAbs2) {
                        if (fAbs2 < 30.0f) {
                            v2.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            v2.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else if (!StatisticsFragment.this.isParentCanScroll) {
                    parent.requestDisallowInterceptTouchEvent(true);
                    v2.getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }
            }
            return false;
        }
    };
    private boolean isChatCanScroll = true;
    private String mDirection = "";

    /* renamed from: com.psychiatrygarden.ranking.StatisticsFragment$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(int i2, int i3, int i4) {
            StatisticsFragment.this.progressView.setCurrentPercent(i2, i3, i4 + "", StatisticsFragment.this.mCategoryId.equals("0"));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1(int i2, int i3) {
            StatisticsFragment.this.progressView.setCurrentPercent(i2, i3, "0", StatisticsFragment.this.mCategoryId.equals("0"));
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
            StatisticsTopBean statisticsTopBean;
            super.onSuccess((AnonymousClass2) s2);
            try {
                JSONObject jSONObject = new JSONObject(s2);
                if (!jSONObject.optString("code").equals("200") || (statisticsTopBean = (StatisticsTopBean) new Gson().fromJson(jSONObject.optString("data"), StatisticsTopBean.class)) == null) {
                    return;
                }
                StatisticsFragment.this.mTvAllFirstPercent.setText(statisticsTopBean.getFirst_right() + "%");
                StatisticsFragment.this.mTvAllEndPercent.setText(statisticsTopBean.getEnd_right() + "%");
                StatisticsFragment.this.mTvFirstPercent.setText(statisticsTopBean.getFirst_right() + "%");
                StatisticsFragment.this.mTvEndPercent.setText(statisticsTopBean.getEnd_right() + "%");
                if (TextUtils.isEmpty(statisticsTopBean.getTotal_duration()) || statisticsTopBean.getTotal_duration().equals("0")) {
                    SpannableString spannableString = new SpannableString("0m");
                    spannableString.setSpan(new AbsoluteSizeSpan(12, true), 1, 2, 33);
                    if (StatisticsFragment.this.mCategoryId.equals("0")) {
                        StatisticsFragment.this.mTvAllUseTime.setText(spannableString);
                    } else {
                        StatisticsFragment.this.mTvHistoryTime.setText(spannableString);
                    }
                } else {
                    long j2 = Long.parseLong(statisticsTopBean.getTotal_duration());
                    int i2 = (int) (j2 / 60);
                    int i3 = (int) (j2 % 60);
                    String str = "";
                    if (i2 != 0) {
                        str = i2 + "h";
                    }
                    if (i3 != 0) {
                        str = str + i3 + "m";
                    }
                    int iIndexOf = str.indexOf("h");
                    int iIndexOf2 = str.indexOf("m");
                    SpannableString spannableString2 = new SpannableString(str);
                    if (iIndexOf != -1) {
                        spannableString2.setSpan(new AbsoluteSizeSpan(12, true), iIndexOf, iIndexOf + 1, 33);
                    }
                    if (iIndexOf2 != -1) {
                        spannableString2.setSpan(new AbsoluteSizeSpan(12, true), iIndexOf2, iIndexOf2 + 1, 33);
                    }
                    if (StatisticsFragment.this.mCategoryId.equals("0")) {
                        StatisticsFragment.this.mTvAllUseTime.setText(spannableString2);
                    } else {
                        StatisticsFragment.this.mTvHistoryTime.setText(spannableString2);
                    }
                }
                String answer_total = statisticsTopBean.getAnswer_total();
                if (answer_total.indexOf("次") == -1) {
                    answer_total = answer_total + "次";
                }
                int iIndexOf3 = answer_total.indexOf("次");
                SpannableString spannableString3 = new SpannableString(answer_total);
                spannableString3.setSpan(new AbsoluteSizeSpan(12, true), iIndexOf3, iIndexOf3 + 1, 33);
                StatisticsFragment.this.mTvHistoryAllCount.setText(spannableString3);
                String is_right = statisticsTopBean.getIs_right();
                if (is_right.indexOf("%") == -1) {
                    is_right = statisticsTopBean.getIs_right() + "%";
                }
                int iIndexOf4 = is_right.indexOf("%");
                SpannableString spannableString4 = new SpannableString(is_right);
                spannableString4.setSpan(new AbsoluteSizeSpan(12, true), iIndexOf4, iIndexOf4 + 1, 33);
                if (StatisticsFragment.this.mCategoryId.equals("0")) {
                    StatisticsFragment.this.mTvAllPercent.setText(spannableString4);
                } else {
                    StatisticsFragment.this.mTvHistoryPercent.setText(spannableString4);
                }
                final int i4 = 0;
                int i5 = TextUtils.isEmpty(statisticsTopBean.getFirst_right()) ? 0 : Integer.parseInt(statisticsTopBean.getFirst_right());
                int i6 = TextUtils.isEmpty(statisticsTopBean.getEnd_right()) ? 0 : Integer.parseInt(statisticsTopBean.getEnd_right());
                if (i5 > i6) {
                    StatisticsFragment.this.mLyAllPercentView.setVisibility(0);
                    StatisticsFragment.this.mLyPercentView.setVisibility(0);
                    StatisticsFragment.this.mTvDisPercent.setTextColor(Color.parseColor("#81CB30"));
                    StatisticsFragment.this.mImgPercent.setImageResource(R.mipmap.ic_statistics_down);
                    TextView textView = StatisticsFragment.this.mTvDisPercent;
                    StringBuilder sb = new StringBuilder();
                    int i7 = i5 - i6;
                    sb.append(i7);
                    sb.append("%");
                    textView.setText(sb.toString());
                    StatisticsFragment.this.mTvAllDisPercent.setTextColor(Color.parseColor("#81CB30"));
                    StatisticsFragment.this.mImgAllPercent.setImageResource(R.mipmap.ic_statistics_down);
                    StatisticsFragment.this.mTvAllDisPercent.setText(i7 + "%");
                } else if (i5 < i6) {
                    StatisticsFragment.this.mLyPercentView.setVisibility(0);
                    StatisticsFragment.this.mLyAllPercentView.setVisibility(0);
                    StatisticsFragment.this.mTvDisPercent.setTextColor(Color.parseColor("#f95843"));
                    StatisticsFragment.this.mImgPercent.setImageResource(R.mipmap.ic_statistics_up);
                    TextView textView2 = StatisticsFragment.this.mTvDisPercent;
                    StringBuilder sb2 = new StringBuilder();
                    int i8 = i6 - i5;
                    sb2.append(i8);
                    sb2.append("%");
                    textView2.setText(sb2.toString());
                    StatisticsFragment.this.mTvAllDisPercent.setTextColor(Color.parseColor("#f95843"));
                    StatisticsFragment.this.mImgAllPercent.setImageResource(R.mipmap.ic_statistics_up);
                    StatisticsFragment.this.mTvAllDisPercent.setText(i8 + "%");
                } else {
                    StatisticsFragment.this.mLyPercentView.setVisibility(8);
                    StatisticsFragment.this.mLyAllPercentView.setVisibility(8);
                }
                final int i9 = TextUtils.isEmpty(statisticsTopBean.getAnswer_count()) ? 0 : Integer.parseInt(statisticsTopBean.getAnswer_count());
                if (!TextUtils.isEmpty(statisticsTopBean.getQuestion_count())) {
                    i4 = Integer.parseInt(statisticsTopBean.getQuestion_count());
                }
                String answer_total2 = statisticsTopBean.getAnswer_total();
                if (answer_total2.indexOf("次") == -1) {
                    answer_total2 = answer_total2 + "次";
                }
                int iIndexOf5 = answer_total2.indexOf("次");
                SpannableString spannableString5 = new SpannableString(answer_total2);
                spannableString5.setSpan(new AbsoluteSizeSpan(12, true), iIndexOf5, iIndexOf5 + 1, 33);
                StatisticsFragment.this.mTvAllQuestionNumber.setText(spannableString5);
                String answer_count = statisticsTopBean.getAnswer_count();
                if (answer_count.indexOf("题") == -1) {
                    answer_count = answer_count + "题";
                }
                int iIndexOf6 = answer_count.indexOf("题");
                SpannableString spannableString6 = new SpannableString(answer_count);
                spannableString6.setSpan(new AbsoluteSizeSpan(12, true), iIndexOf6, iIndexOf6 + 1, 33);
                StatisticsFragment.this.mTvAllDoNumber.setText(spannableString6);
                if (i4 != 0) {
                    final int i10 = (i9 * 100) / i4;
                    if (!StatisticsFragment.this.mCategoryId.equals("0")) {
                        StatisticsFragment.this.progressView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.ranking.w
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f16220c.lambda$onSuccess$0(i9, i4, i10);
                            }
                        }, 300L);
                    }
                } else if (!StatisticsFragment.this.mCategoryId.equals("0")) {
                    StatisticsFragment.this.progressView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.ranking.x
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f16224c.lambda$onSuccess$1(i9, i4);
                        }
                    }, 300L);
                }
                if (!TextUtils.isEmpty(statisticsTopBean.getIs_right())) {
                    Integer.parseInt(statisticsTopBean.getIs_right());
                }
                if (!TextUtils.isEmpty(statisticsTopBean.getAll_right_rate())) {
                    Integer.parseInt(statisticsTopBean.getAll_right_rate());
                }
                statisticsTopBean.getIs_right();
                statisticsTopBean.getPeople_percent();
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

        @JavascriptInterface
        public void getDataType(String dataValue) {
            StatisticsFragment.this.mDataValue = dataValue;
            LogUtils.e("reveice_data_from_js", "获取到的参数====》" + dataValue);
            StatisticsFragment.this.getChapterInfo();
        }

        @JavascriptInterface
        public void scrollEnd(boolean isCharTouch) {
            LogUtils.e("reveice_data_from_js", "获取到的参数====》" + isCharTouch);
            ViewParent parent = StatisticsFragment.this.mViewWeb.getParent().getParent().getParent().getParent();
            if (isCharTouch) {
                if (StatisticsFragment.this.isParentCanScroll) {
                    StatisticsFragment.this.isParentCanScroll = false;
                    parent.requestDisallowInterceptTouchEvent(true);
                    return;
                }
                return;
            }
            if (StatisticsFragment.this.isParentCanScroll) {
                return;
            }
            StatisticsFragment.this.isParentCanScroll = true;
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    public class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            StatisticsFragment.this.mLyWebView.getLayoutParams().height = -2;
            StatisticsFragment.this.mViewWeb.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getChapterInfo() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put(HmsMessageService.SUBJECT_ID, "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext));
        ajaxParams.put("identity_id", this.mIdentityId);
        ajaxParams.put("date_type", this.mDataValue);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getQuestionAndUnitSheet, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.ranking.StatisticsFragment.5
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
                super.onSuccess((AnonymousClass5) s2);
                try {
                    ChapterStatisticsBean chapterStatisticsBean = (ChapterStatisticsBean) new Gson().fromJson(s2, ChapterStatisticsBean.class);
                    if (chapterStatisticsBean.getCode().equals("200")) {
                        if (chapterStatisticsBean.getData() == null || chapterStatisticsBean.getData().isEmpty()) {
                            StatisticsFragment.this.mLyChapterView.setVisibility(8);
                            StatisticsFragment.this.mLineChapter.setVisibility(8);
                            return;
                        }
                        if (TextUtils.isEmpty(StatisticsFragment.this.mIdentityId) || StatisticsFragment.this.mIdentityId.equals("-2")) {
                            StatisticsFragment.this.mLyChapterView.setVisibility(8);
                            StatisticsFragment.this.mLineChapter.setVisibility(8);
                            return;
                        }
                        int i2 = 0;
                        StatisticsFragment.this.mLyChapterView.setVisibility(0);
                        StatisticsFragment.this.mLineChapter.setVisibility(0);
                        StatisticsFragment.this.mLyAddView.removeAllViews();
                        while (i2 < chapterStatisticsBean.getData().size()) {
                            ChapterStatisticsItemView chapterStatisticsItemView = new ChapterStatisticsItemView(StatisticsFragment.this.getActivity());
                            int i3 = i2 + 1;
                            chapterStatisticsItemView.setData(i3, StatisticsFragment.this.mIdentityId.equals("-1"), chapterStatisticsBean.data.get(i2));
                            StatisticsFragment.this.mLyAddView.addView(chapterStatisticsItemView);
                            i2 = i3;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getPercentInfoData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_category_id", this.mCategoryId);
        if (this.isKnowledge) {
            ajaxParams.put("knowledge_tree_id", this.mIdentityId);
        } else {
            ajaxParams.put("statistics_identity_id", this.mIdentityId);
        }
        ajaxParams.put("type", this.mType + "");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getStatisticalPercentInfo, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.ranking.StatisticsFragment.4
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
                WeekOrMonthReportBean.ReportDataBean reportDataBean;
                super.onSuccess((AnonymousClass4) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200") || (reportDataBean = (WeekOrMonthReportBean.ReportDataBean) new Gson().fromJson(jSONObject.optString("data"), WeekOrMonthReportBean.ReportDataBean.class)) == null) {
                        return;
                    }
                    reportDataBean.getRight_rate();
                    if (TextUtils.isEmpty(reportDataBean.getAll_duration()) || reportDataBean.getAll_duration().equals("0")) {
                        StatisticsFragment.this.mTvHistoryTimeChild.setVisibility(8);
                        SpannableString spannableString = new SpannableString("0m");
                        spannableString.setSpan(new AbsoluteSizeSpan(20, true), 0, 1, 33);
                        StatisticsFragment.this.mTvHistoryTimeMinChild.setText(spannableString);
                        StatisticsFragment.this.mTvHistoryTimeMinChild.setVisibility(0);
                    } else {
                        long j2 = Long.parseLong(reportDataBean.getAll_duration());
                        int i2 = (int) (j2 / 60);
                        int i3 = (int) (j2 % 60);
                        if (i2 == 0) {
                            StatisticsFragment.this.mTvHistoryTimeChild.setVisibility(8);
                        } else {
                            String str = i2 + "h";
                            int iIndexOf = str.indexOf("h");
                            SpannableString spannableString2 = new SpannableString(str);
                            spannableString2.setSpan(new AbsoluteSizeSpan(20, true), 0, iIndexOf, 33);
                            StatisticsFragment.this.mTvHistoryTimeChild.setText(spannableString2);
                            StatisticsFragment.this.mTvHistoryTimeChild.setVisibility(0);
                        }
                        if (i3 == 0) {
                            StatisticsFragment.this.mTvHistoryTimeMinChild.setVisibility(8);
                        } else {
                            String str2 = i3 + "m";
                            int iIndexOf2 = str2.indexOf("m");
                            SpannableString spannableString3 = new SpannableString(str2);
                            spannableString3.setSpan(new AbsoluteSizeSpan(20, true), 0, iIndexOf2, 33);
                            StatisticsFragment.this.mTvHistoryTimeMinChild.setText(spannableString3);
                            StatisticsFragment.this.mTvHistoryTimeMinChild.setVisibility(0);
                        }
                    }
                    StatisticsFragment.this.mTvHistoryAllCountChild.setText(reportDataBean.getAll_question_num());
                    StatisticsFragment.this.mTvHistoryPercentChild.setText(reportDataBean.getRight_rate());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getRankData() {
        YJYHttpUtils.get(this.mContext, NetworkRequestsURL.getStatisticalRankData, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.ranking.StatisticsFragment.3
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
                StatisticsTopBean statisticsTopBean;
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (!jSONObject.optString("code").equals("200") || (statisticsTopBean = (StatisticsTopBean) new Gson().fromJson(jSONObject.optString("data"), StatisticsTopBean.class)) == null) {
                        return;
                    }
                    if (statisticsTopBean.getUser_sheet_total() == null || statisticsTopBean.getUser_sheet_total().isEmpty()) {
                        StatisticsFragment.this.mLyUser.setVisibility(8);
                        return;
                    }
                    int i2 = 0;
                    StatisticsFragment.this.mLyUser.setVisibility(0);
                    while (i2 < statisticsTopBean.getUser_sheet_total().size()) {
                        RankUserItemView rankUserItemView = new RankUserItemView(StatisticsFragment.this.getActivity());
                        int i3 = i2 + 1;
                        rankUserItemView.setData(i3, statisticsTopBean.getUser_sheet_total().get(i2));
                        StatisticsFragment.this.mLyAddUserView.addView(rankUserItemView);
                        i2 = i3;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private void getTopData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_category_id", this.mCategoryId);
        if (this.isKnowledge) {
            ajaxParams.put("knowledge_tree_id", this.mIdentityId);
        } else {
            ajaxParams.put("statistics_identity_id", this.mIdentityId);
        }
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getStatisticalTopData, ajaxParams, new AnonymousClass2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClickLisenter$0(View view) {
        CommentActionRankingAct.newIntent(getContext(), "question", 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClickLisenter$1(View view) {
        this.mType = 1;
        this.mBtnToday.setTypeface(Typeface.DEFAULT_BOLD);
        this.mBtnWeek.setTypeface(Typeface.DEFAULT);
        this.mBtnMonth.setTypeface(Typeface.DEFAULT);
        this.mBtnToday.setTextColor(ContextCompat.getColor(this.mContext, R.color.first_txt_color));
        this.mBtnWeek.setTextColor(ContextCompat.getColor(this.mContext, R.color.third_txt_color));
        this.mBtnMonth.setTextColor(ContextCompat.getColor(this.mContext, R.color.third_txt_color));
        this.mBtnToday.setBackgroundResource(R.drawable.shape_new_bg_one_color_no_night_corners);
        this.mBtnWeek.setBackgroundResource(R.color.transparent);
        this.mBtnMonth.setBackgroundResource(R.color.transparent);
        submitValutToH5(this.mType + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClickLisenter$2(View view) {
        this.mType = 2;
        this.mBtnToday.setTypeface(Typeface.DEFAULT);
        this.mBtnWeek.setTypeface(Typeface.DEFAULT_BOLD);
        this.mBtnMonth.setTypeface(Typeface.DEFAULT);
        this.mBtnWeek.setBackgroundResource(R.drawable.shape_new_bg_one_color_no_night_corners);
        this.mBtnToday.setBackgroundResource(R.color.transparent);
        this.mBtnMonth.setBackgroundResource(R.color.transparent);
        this.mBtnWeek.setTextColor(ContextCompat.getColor(this.mContext, R.color.first_txt_color));
        this.mBtnToday.setTextColor(ContextCompat.getColor(this.mContext, R.color.third_txt_color));
        this.mBtnMonth.setTextColor(ContextCompat.getColor(this.mContext, R.color.third_txt_color));
        submitValutToH5(this.mType + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClickLisenter$3(View view) {
        this.mType = 3;
        this.mBtnMonth.setTypeface(Typeface.DEFAULT_BOLD);
        this.mBtnWeek.setTypeface(Typeface.DEFAULT);
        this.mBtnToday.setTypeface(Typeface.DEFAULT);
        this.mBtnMonth.setBackgroundResource(R.drawable.shape_new_bg_one_color_no_night_corners);
        this.mBtnWeek.setBackgroundResource(R.color.transparent);
        this.mBtnToday.setBackgroundResource(R.color.transparent);
        this.mBtnMonth.setTextColor(ContextCompat.getColor(this.mContext, R.color.first_txt_color));
        this.mBtnToday.setTextColor(ContextCompat.getColor(this.mContext, R.color.third_txt_color));
        this.mBtnWeek.setTextColor(ContextCompat.getColor(this.mContext, R.color.third_txt_color));
        submitValutToH5(this.mType + "");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$submitValutToH5$4(String str) {
        LogUtils.e("reveice_data_from_js", "获取到的参数====》" + str);
    }

    public static StatisticsFragment newInstance() {
        Bundle bundle = new Bundle();
        StatisticsFragment statisticsFragment = new StatisticsFragment();
        statisticsFragment.setArguments(bundle);
        return statisticsFragment;
    }

    private void onClickLisenter() {
        this.mLyToRank.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.s
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16216c.lambda$onClickLisenter$0(view);
            }
        });
        this.mBtnToday.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.t
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16217c.lambda$onClickLisenter$1(view);
            }
        });
        this.mBtnWeek.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16218c.lambda$onClickLisenter$2(view);
            }
        });
        this.mBtnMonth.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.ranking.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16219c.lambda$onClickLisenter$3(view);
            }
        });
    }

    private void submitValutToH5(String type) {
        this.mWebView.evaluateJavascript("javascript:changeDateEvt(" + type + ")", new ValueCallback() { // from class: com.psychiatrygarden.ranking.r
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                StatisticsFragment.lambda$submitValutToH5$4((String) obj);
            }
        });
        getPercentInfoData();
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.frag_statistics;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        this.mCategoryId = getArguments().getString("categoryId");
        this.isKnowledge = getArguments().getBoolean("isKnowledge");
        String string = getArguments().getString("identity_id");
        this.mIdentityId = string;
        if (TextUtils.isEmpty(string) || this.mIdentityId.equals("null")) {
            this.mIdentityId = "0";
        }
        this.mLyAddView = (LinearLayout) holder.get(R.id.ly_add_view);
        this.mLyAddUserView = (LinearLayout) holder.get(R.id.ly_add_user_view);
        this.mLyUser = (LinearLayout) holder.get(R.id.ly_user);
        this.mLyToRank = (LinearLayout) holder.get(R.id.ly_to_rank);
        this.mLyAllView = (RelativeLayout) holder.get(R.id.ly_all_view);
        this.mLyProgressView = (LinearLayout) holder.get(R.id.ly_progress_view);
        this.progressView = (StatisticsProgressView) holder.get(R.id.progress_view);
        this.mWebView = (WebView) holder.get(R.id.webview);
        this.mViewWeb = (RelativeLayout) holder.get(R.id.view_web);
        TextView textView = (TextView) holder.get(R.id.tv_chapter_name);
        TextView textView2 = (TextView) holder.get(R.id.tv_question_num);
        TextView textView3 = (TextView) holder.get(R.id.tv_do_question_num);
        TextView textView4 = (TextView) holder.get(R.id.tv_percent);
        this.scrollview = (NestedScrollView) holder.get(R.id.scrollview);
        this.mLyWebView = (RelativeLayout) holder.get(R.id.ly_webview);
        this.mTvFirstPercent = (TextView) holder.get(R.id.tv_first_percent);
        this.mTvEndPercent = (TextView) holder.get(R.id.tv_end_percent);
        this.mTvHistoryTime = (TextView) holder.get(R.id.tv_history_time);
        this.mTvHistoryAllCount = (TextView) holder.get(R.id.tv_history_all_count);
        this.mTvHistoryPercent = (TextView) holder.get(R.id.tv_history_percent);
        this.mTvHistoryTimeChild = (TextView) holder.get(R.id.tv_history_time_child);
        this.mTvHistoryTimeMinChild = (TextView) holder.get(R.id.tv_history_time_min_child);
        this.mTvHistoryAllCountChild = (TextView) holder.get(R.id.tv_history_all_count_child);
        this.mTvHistoryPercentChild = (TextView) holder.get(R.id.tv_history_percent_child);
        this.mLyPercentView = (LinearLayout) holder.get(R.id.ly_percent_view);
        this.mImgPercent = (ImageView) holder.get(R.id.img_percent);
        this.mTvDisPercent = (TextView) holder.get(R.id.tv_dis_percent);
        this.mLyChapterView = (LinearLayout) holder.get(R.id.ly_chapter_view);
        this.mLineChapter = holder.get(R.id.line_chapter);
        this.mBtnToday = (TextView) holder.get(R.id.btn_today);
        this.mBtnWeek = (TextView) holder.get(R.id.btn_week);
        this.mBtnMonth = (TextView) holder.get(R.id.btn_month);
        this.mTvAllQuestionNumber = (TextView) holder.get(R.id.tv_all_question_num);
        this.mTvAllFirstPercent = (TextView) holder.get(R.id.tv_all_first_percent);
        this.mTvAllEndPercent = (TextView) holder.get(R.id.tv_all_end_percent);
        this.mLyAllPercentView = (LinearLayout) holder.get(R.id.ly_all_percent_view);
        this.mImgAllPercent = (ImageView) holder.get(R.id.img_all_percent);
        this.mTvAllDisPercent = (TextView) holder.get(R.id.tv_all_dis_percent);
        this.mLyHistory = (LinearLayout) holder.get(R.id.ly_history);
        this.mTvAllUseTime = (TextView) holder.get(R.id.tv_all_use_time);
        this.mTvAllPercent = (TextView) holder.get(R.id.tv_all_percent);
        this.mTvAllDoNumber = (TextView) holder.get(R.id.tv_all_do_question_num);
        if (this.mCategoryId.equals("0")) {
            this.mLyAllView.setVisibility(0);
            this.mLyProgressView.setVisibility(8);
            this.mLyHistory.setVisibility(8);
        } else {
            this.mLyAllView.setVisibility(8);
            this.mLyProgressView.setVisibility(0);
            this.mLyHistory.setVisibility(0);
        }
        int screenWidth = (UIUtil.getScreenWidth(getContext()) - UIUtil.dip2px(getContext(), 32.0d)) / 5;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.width = screenWidth * 2;
        textView.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) textView2.getLayoutParams();
        layoutParams2.width = screenWidth;
        textView2.setLayoutParams(layoutParams2);
        layoutParams2.width = screenWidth;
        textView3.setLayoutParams(layoutParams2);
        layoutParams2.width = screenWidth;
        textView4.setLayoutParams(layoutParams2);
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.progressView.getLayoutParams();
        if (UIUtil.getScreenWidth(getActivity()) < CommonUtil.dip2px(getActivity(), 320.0f)) {
            layoutParams3.width = UIUtil.getScreenWidth(getActivity());
            layoutParams3.height = UIUtil.getScreenWidth(getActivity()) / 2;
            this.progressView.setLayoutParams(layoutParams3);
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
        WebView.setWebContentsDebuggingEnabled(true);
        String strConfig = SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext);
        String strConfig2 = SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, this.mContext);
        String secret = UserConfig.getInstance().getUser().getSecret();
        String token = UserConfig.getInstance().getUser().getToken();
        boolean z2 = this.isKnowledge;
        String str = z2 ? this.mIdentityId : "0";
        this.mWebView.loadUrl(NetworkRequestsURL.statisticalChatInfoUrl + "questionStatistics.html?app_id=" + strConfig + "&statistics_identity_id=" + (z2 ? "0" : this.mIdentityId) + "&subject_id=" + strConfig2 + "&secret=" + secret + "&token=" + token + "&user_id=" + UserConfig.getUserId() + "&knowledge_tree_id=" + str + "&type=" + this.mType + "&question_category_id=" + this.mCategoryId);
        this.mWebView.setWebViewClient(new MyWebViewClient());
        this.mWebView.setOnTouchListener(this.listener);
        getTopData();
        getPercentInfoData();
        getRankData();
        onClickLisenter();
        this.scrollview.scrollTo(0, 0);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment, cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
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

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            return;
        }
        this.isParentCanScroll = false;
        if (this.mWebView != null) {
            this.mViewWeb.getParent().getParent().getParent().getParent().requestDisallowInterceptTouchEvent(false);
            LogUtils.e("on_page_hidden", "页面不可见=》" + isVisibleToUser + com.alipay.sdk.util.h.f3376b + this.mIdentityId);
        }
    }
}
