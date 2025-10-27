package com.psychiatrygarden.activity;

import com.google.gson.Gson;
import com.psychiatrygarden.bean.QuestionDetailBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.tencent.smtt.sdk.WebView;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class QuestionDetailActivity extends BaseActivity {
    public String id;
    public WebView webview;

    public void getQuestionDetail() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("id", "" + this.id);
        YJYHttpUtils.get(this, NetworkRequestsURL.issdetailUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.QuestionDetailActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    QuestionDetailBean questionDetailBean = (QuestionDetailBean) new Gson().fromJson(s2, QuestionDetailBean.class);
                    if (questionDetailBean.getCode().equals("200")) {
                        String strReplaceAll = questionDetailBean.getData().get(0).getContent().replaceAll("\n", "<br/>").replaceAll("<html.*?>", "").replaceAll("</html>", "").replaceAll("rgb", "oldrgb");
                        if (SkinManager.getCurrentSkinType(QuestionDetailActivity.this) == 1) {
                            strReplaceAll = strReplaceAll.replace("color:black", "");
                        }
                        QuestionDetailActivity.this.webview.loadDataWithBaseURL(null, CommonUtil.getHtmlData(QuestionDetailActivity.this, strReplaceAll), "text/html", "utf-8", null);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.id = getIntent().getExtras().getString("id", "");
        this.webview = (WebView) findViewById(R.id.webview);
        if (SkinManager.getCurrentSkinType(this) == 1) {
            this.webview.setBackgroundColor(this.mContext.getResources().getColor(R.color.app_bg_night));
        }
        this.webview.getSettings().setMixedContentMode(0);
        getQuestionDetail();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_question_detail);
        setTitle("问题详情");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
