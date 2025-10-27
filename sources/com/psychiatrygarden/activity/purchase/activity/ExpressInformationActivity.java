package com.psychiatrygarden.activity.purchase.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.psychiatrygarden.activity.BaseActivity;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ExpressInformationActivity extends BaseActivity {
    private WebView mWebview;
    String url;

    public class webViewClient extends WebViewClient {
        private webViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        WebView webView = (WebView) findViewById(R.id.web);
        this.mWebview = webView;
        WebSettings settings = webView.getSettings();
        this.mWebview.removeJavascriptInterface("searchBoxJavaBredge_");
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setMixedContentMode(0);
        this.mWebview.loadUrl(this.url);
        this.mWebview.setWebViewClient(new webViewClient());
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mWebview.loadUrl("about:blank");
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        this.mWebview.reload();
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setTitle("查看物流");
        setContentView(R.layout.fragment_goods_info);
        this.url = getIntent().getExtras().getString("url");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
