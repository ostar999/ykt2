package com.psychiatrygarden.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.CommMentList2Activity;
import com.psychiatrygarden.activity.QuestionCorrectionActivity;
import com.psychiatrygarden.bean.SubjectiveListBean;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.widget.CustomWebView;
import com.yikaobang.yixue.R;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SubjectiveQuestionAnswerAdapter extends BaseQuickAdapter<SubjectiveListBean.DataBean, BaseViewHolder> {
    private Handler mHandler;

    public class mSubJs {
        public Context mContext;
        public String question_id;

        public mSubJs(Context mContext, String question_id) {
            this.mContext = mContext;
            this.question_id = question_id;
        }

        @JavascriptInterface
        public void postMessage(String data) {
            try {
                JSONObject jSONObject = new JSONObject(data);
                int i2 = 0;
                if (jSONObject.optString("method").equals("rightBtnClick")) {
                    SubjectiveQuestionAnswerAdapter.this.subjectAnswer(this.question_id, "1");
                    SubjectiveQuestionAnswerAdapter.this.mHandler.sendEmptyMessage(1);
                    ProjectApp.isDoSubjective = true;
                    while (i2 < ProjectApp.dataList.size()) {
                        if (ProjectApp.dataList.get(i2).getQuestion_id().equals(this.question_id)) {
                            ProjectApp.dataList.get(i2).setAnswer_status("1");
                        }
                        i2++;
                    }
                    return;
                }
                if (jSONObject.optString("method").equals("errorBtnClick")) {
                    SubjectiveQuestionAnswerAdapter.this.subjectAnswer(this.question_id, "0");
                    SubjectiveQuestionAnswerAdapter.this.mHandler.sendEmptyMessage(1);
                    ProjectApp.isDoSubjective = true;
                    while (i2 < ProjectApp.dataList.size()) {
                        if (ProjectApp.dataList.get(i2).getQuestion_id().equals(this.question_id)) {
                            ProjectApp.dataList.get(i2).setAnswer_status("2");
                        }
                        i2++;
                    }
                    return;
                }
                if (jSONObject.optString("method").equals(ClientCookie.COMMENT_ATTR)) {
                    Intent intent = new Intent(this.mContext, (Class<?>) CommMentList2Activity.class);
                    intent.putExtra("question_id", Long.parseLong(this.question_id));
                    intent.putExtra("module_type", 1);
                    intent.putExtra("comment_type", "2");
                    intent.putExtra("isCommentTrue", false);
                    intent.putExtra("isZantongTrue", false);
                    intent.putExtra("isEXamId", true);
                    this.mContext.startActivity(intent);
                    return;
                }
                if (jSONObject.optString("method").equals("errorCorrection")) {
                    Intent intent2 = new Intent(this.mContext, (Class<?>) QuestionCorrectionActivity.class);
                    intent2.putExtra("question_id", this.question_id + "");
                    this.mContext.startActivity(intent2);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public SubjectiveQuestionAnswerAdapter(int layoutResId, @Nullable List<SubjectiveListBean.DataBean> data, Handler mHandler) {
        super(layoutResId, data);
        this.mHandler = mHandler;
    }

    public void subjectAnswer(String question_id, String answer) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("question_id", "" + question_id);
        ajaxParams.put("answer", "" + answer);
        YJYHttpUtils.post(getContext(), NetworkRequestsURL.subjectAnswerUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.adapter.SubjectiveQuestionAnswerAdapter.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass2) s2);
                try {
                    new JSONObject(s2).optString("code").equals("200");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    @SuppressLint({"JavascriptInterface"})
    public void convert(BaseViewHolder helper, final SubjectiveListBean.DataBean item) {
        CustomWebView customWebView = (CustomWebView) helper.getView(R.id.webview);
        WebSettings settings = customWebView.getSettings();
        settings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setCacheMode(2);
        settings.setMixedContentMode(0);
        customWebView.addJavascriptInterface(new mSubJs(getContext(), item.getQuestion_id()), "jsToNative");
        customWebView.setWebChromeClient(new WebChromeClient() { // from class: com.psychiatrygarden.adapter.SubjectiveQuestionAnswerAdapter.1
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        customWebView.loadUrl(item.getQuestionurl());
        customWebView.setTag(item.getQuestionurl());
        if (TextUtils.equals(item.getAnswer_status(), "0")) {
            this.mHandler.sendEmptyMessage(2);
        } else {
            this.mHandler.sendEmptyMessage(1);
        }
    }
}
