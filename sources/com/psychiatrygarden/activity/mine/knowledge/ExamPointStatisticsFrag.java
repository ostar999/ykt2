package com.psychiatrygarden.activity.mine.knowledge;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.SubQuestionCeshiDaActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.baseview.ViewHolder;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.bean.MockStatisticsTreeBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.MockPointStatisticeChildItemView;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class ExamPointStatisticsFrag extends BaseFragment {
    private String examId;
    private String isEstimate;
    private String isSchoolRank;
    private float mDownX;
    private float mDownY;
    private LinearLayout mLyAddView;
    private RelativeLayout mLyWebView;
    private RecyclerView mRecyclerView;
    private ExamPointStatisticsAdp mTabAdp;
    private RelativeLayout mViewWeb;
    private WebView mWebView;
    private String score;
    private NestedScrollView scrollView;
    private String title;
    private List<ExesQuestionBean> questBeans = new ArrayList();
    private int currentTabPos = 0;
    View.OnTouchListener listener = new View.OnTouchListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.ExamPointStatisticsFrag.6
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v2, MotionEvent event) {
            ViewParent parent = v2.getParent().getParent().getParent().getParent();
            float x2 = event.getX();
            float y2 = event.getY();
            int action = event.getAction();
            if (action == 0) {
                ExamPointStatisticsFrag.this.mDownX = x2;
                ExamPointStatisticsFrag.this.mDownY = y2;
            } else if (action == 2) {
                float fAbs = Math.abs(x2 - ExamPointStatisticsFrag.this.mDownX);
                float fAbs2 = Math.abs(y2 - ExamPointStatisticsFrag.this.mDownY);
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
    }

    public class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            ExamPointStatisticsFrag examPointStatisticsFrag = ExamPointStatisticsFrag.this;
            examPointStatisticsFrag.submitValutToH5(examPointStatisticsFrag.initData());
            ExamPointStatisticsFrag.this.mLyWebView.getLayoutParams().height = -2;
            ExamPointStatisticsFrag.this.mViewWeb.setVisibility(8);
        }
    }

    private void getData(String answer) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", this.examId);
        ajaxParams.put("answer", answer);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mockStatisticsList, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.knowledge.ExamPointStatisticsFrag.4
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass4) s2);
                try {
                    MockStatisticsTreeBean mockStatisticsTreeBean = (MockStatisticsTreeBean) new Gson().fromJson(s2, MockStatisticsTreeBean.class);
                    if (!mockStatisticsTreeBean.getCode().equals("200")) {
                        ToastUtil.shortToast(((BaseFragment) ExamPointStatisticsFrag.this).mContext, mockStatisticsTreeBean.getMessage());
                        return;
                    }
                    if (mockStatisticsTreeBean.getData() == null || mockStatisticsTreeBean.getData().size() <= 0) {
                        return;
                    }
                    ExamPointStatisticsFrag.this.mTabAdp.setNewData(mockStatisticsTreeBean.getData());
                    ExamPointStatisticsFrag.this.mLyAddView.removeAllViews();
                    MockStatisticsTreeBean.MockStatisticsData mockStatisticsData = mockStatisticsTreeBean.getData().get(0);
                    for (int i2 = 0; i2 < mockStatisticsData.getChildren().size(); i2++) {
                        MockPointStatisticeChildItemView mockPointStatisticeChildItemView = new MockPointStatisticeChildItemView(((BaseFragment) ExamPointStatisticsFrag.this).mContext);
                        mockPointStatisticeChildItemView.setOnItemActionLisenter(new MockPointStatisticeChildItemView.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.mine.knowledge.ExamPointStatisticsFrag.4.1
                            @Override // com.psychiatrygarden.widget.MockPointStatisticeChildItemView.OnItemActionLisenter
                            public void jumpToQuestionDetail(String knowId) {
                                ExamPointStatisticsFrag.this.getQuestionList(knowId);
                            }
                        });
                        mockPointStatisticeChildItemView.setData(mockStatisticsData.getChildren().get(i2), mockStatisticsData.getHave().equals("1"), 1);
                        ExamPointStatisticsFrag.this.mLyAddView.addView(mockPointStatisticeChildItemView);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getQuestionList(String knowledgeId) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", this.examId);
        ajaxParams.put("knowledge_id", knowledgeId);
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getKnowledgeQuestion, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.mine.knowledge.ExamPointStatisticsFrag.5
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass5) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_VERIFY, new JSONObject(jSONObject.optString("data")).optString("data"));
                        ExamPointStatisticsFrag.this.questBeans = (List) new Gson().fromJson(strDecode, new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.mine.knowledge.ExamPointStatisticsFrag.5.1
                        }.getType());
                        Collections.sort(ExamPointStatisticsFrag.this.questBeans);
                        ProjectApp.questExamDataList.clear();
                        ProjectApp.questExamDataList.addAll(ExamPointStatisticsFrag.this.questBeans);
                        ExamPointStatisticsFrag.this.jumpToDetails();
                    } else {
                        ToastUtil.shortToast(((BaseFragment) ExamPointStatisticsFrag.this).mContext, jSONObject.optString("message"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String initData() throws JSONException {
        this.questBeans = (List) new Gson().fromJson(new Gson().toJson(ProjectApp.questExamList), new TypeToken<List<ExesQuestionBean>>() { // from class: com.psychiatrygarden.activity.mine.knowledge.ExamPointStatisticsFrag.3
        }.getType());
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < this.questBeans.size(); i2++) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("question_id", this.questBeans.get(i2).getQuestion_id());
                jSONObject.put("score", this.questBeans.get(i2).getScore());
                jSONObject.put("is_right", this.questBeans.get(i2).getIsRight());
                jSONObject.put("chapter_id", this.questBeans.get(i2).getChapter_id());
                jSONObject.put("knowledge_id", TextUtils.isEmpty(this.questBeans.get(i2).getKnowledge_id()) ? "" : this.questBeans.get(i2).getKnowledge_id());
                jSONArray.put(jSONObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return jSONArray.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void jumpToDetails() {
        Intent intent = new Intent(getContext(), (Class<?>) SubQuestionCeshiDaActivity.class);
        intent.putExtra("exam_id", this.examId);
        intent.putExtra("question_file", "");
        intent.putExtra("title", this.title);
        intent.putExtra("user_exam_time", "");
        intent.putExtra("score", this.score);
        intent.putExtra("istongji", true);
        intent.putExtra("is_esaydta", this.isEstimate);
        intent.putExtra("is_school_rank", this.isSchoolRank);
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$0(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        if (this.currentTabPos == i2) {
            return;
        }
        this.currentTabPos = i2;
        this.mTabAdp.setCurrentPos(i2);
        this.mTabAdp.notifyDataSetChanged();
        this.mLyAddView.removeAllViews();
        MockStatisticsTreeBean.MockStatisticsData mockStatisticsData = this.mTabAdp.getData().get(i2);
        for (int i3 = 0; i3 < mockStatisticsData.getChildren().size(); i3++) {
            MockPointStatisticeChildItemView mockPointStatisticeChildItemView = new MockPointStatisticeChildItemView(this.mContext);
            mockPointStatisticeChildItemView.setOnItemActionLisenter(new MockPointStatisticeChildItemView.OnItemActionLisenter() { // from class: com.psychiatrygarden.activity.mine.knowledge.ExamPointStatisticsFrag.1
                @Override // com.psychiatrygarden.widget.MockPointStatisticeChildItemView.OnItemActionLisenter
                public void jumpToQuestionDetail(String knowId) {
                    ExamPointStatisticsFrag.this.getQuestionList(knowId);
                }
            });
            mockPointStatisticeChildItemView.setData(mockStatisticsData.getChildren().get(i3), mockStatisticsData.getHave().equals("1"), 1);
            this.mLyAddView.addView(mockPointStatisticeChildItemView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$submitValutToH5$1(String str) {
        LogUtils.e("reveice_data_from_js", "获取到的参数====》" + str);
    }

    public static ExamPointStatisticsFrag newInstance(String examId, String title, String isEstimate, String score, String isSchoolRank) {
        Bundle bundle = new Bundle();
        bundle.putString("examId", examId);
        bundle.putString("title", title);
        bundle.putString("isEstimate", isEstimate);
        bundle.putString("score", score);
        bundle.putString("isSchoolRank", isSchoolRank);
        ExamPointStatisticsFrag examPointStatisticsFrag = new ExamPointStatisticsFrag();
        examPointStatisticsFrag.setArguments(bundle);
        return examPointStatisticsFrag;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void submitValutToH5(String jsonStr) {
        this.mWebView.evaluateJavascript("javascript:initData(" + jsonStr + ")", new ValueCallback() { // from class: com.psychiatrygarden.activity.mine.knowledge.j
            @Override // android.webkit.ValueCallback
            public final void onReceiveValue(Object obj) {
                ExamPointStatisticsFrag.lambda$submitValutToH5$1((String) obj);
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.frag_exam_point_sttistics;
    }

    public Bitmap getRootView() {
        int height = this.scrollView.getHeight();
        for (int i2 = 0; i2 < this.scrollView.getChildCount(); i2++) {
            height += this.scrollView.getChildAt(i2).getHeight();
        }
        LogUtils.e("point_view_height", "totalHeight=" + height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.scrollView.getWidth(), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        int height2 = 0;
        for (int i3 = 0; i3 < this.scrollView.getChildCount(); i3++) {
            View childAt = this.scrollView.getChildAt(i3);
            childAt.layout(0, height2, childAt.getWidth(), childAt.getHeight() + height2);
            childAt.draw(canvas);
            height2 += childAt.getHeight();
        }
        return bitmapCreateBitmap;
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(ViewHolder holder, View root) {
        int aPPVersionCode;
        this.examId = getArguments().getString("examId");
        this.title = getArguments().getString("title");
        this.isEstimate = getArguments().getString("isEstimate");
        this.isSchoolRank = getArguments().getString("isSchoolRank");
        this.score = getArguments().getString("score");
        this.mWebView = (WebView) holder.get(R.id.webview);
        this.mViewWeb = (RelativeLayout) holder.get(R.id.view_web);
        this.mLyWebView = (RelativeLayout) holder.get(R.id.ly_webview);
        this.mRecyclerView = (RecyclerView) holder.get(R.id.recycler_view);
        this.mLyAddView = (LinearLayout) holder.get(R.id.ly_add_view);
        this.scrollView = (NestedScrollView) holder.get(R.id.scorllview);
        ExamPointStatisticsAdp examPointStatisticsAdp = new ExamPointStatisticsAdp();
        this.mTabAdp = examPointStatisticsAdp;
        this.mRecyclerView.setAdapter(examPointStatisticsAdp);
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
            aPPVersionCode = AndroidBaseUtils.getAPPVersionCode(this.mContext);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            aPPVersionCode = R2.attr.plvDrag;
        }
        this.mWebView.loadUrl(NetworkRequestsURL.statisticalChatInfoUrl + "echartsKnowledgeStatistics2.html?token=" + token + "&app_id=" + strConfig + "&secret=" + secret + "&user_id=" + UserConfig.getUserId() + "&knowledge_id=15282&subject_id=" + strConfig2 + "&uuid=" + imei + "&version=" + aPPVersionCode + "&exam_id=" + this.examId);
        this.mWebView.setWebViewClient(new MyWebViewClient());
        this.mWebView.setOnTouchListener(this.listener);
        this.mTabAdp.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.i
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                this.f12888a.lambda$initViews$0(baseQuickAdapter, view, i2);
            }
        });
        this.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.activity.mine.knowledge.ExamPointStatisticsFrag.2
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public void onScrollChange(@NonNull NestedScrollView v2, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            }
        });
        getData(initData());
    }
}
