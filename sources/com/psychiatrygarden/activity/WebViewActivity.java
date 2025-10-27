package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.utils.SkinManager;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class WebViewActivity extends BaseActivity {
    private WebView mWebview;
    private String url = "";

    public class webViewClient extends WebViewClient {
        private webViewClient() {
        }

        @Override // com.tencent.smtt.sdk.WebViewClient
        public void onPageFinished(final WebView view, String url) {
            super.onPageFinished(view, url);
            WebViewActivity.this.changeBg();
            view.postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.gr
                @Override // java.lang.Runnable
                public final void run() {
                    view.setVisibility(0);
                }
            }, 300L);
        }

        @Override // com.tencent.smtt.sdk.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            WebViewActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            WebViewActivity.this.finish();
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    public void changeBg() {
        if (this.mWebview == null || SkinManager.getCurrentSkinType(this.mContext) != 1) {
            return;
        }
        this.mWebview.evaluateJavascript("document.body.style.backgroundColor='#121622';document.body.style.color='#7380A9';", null);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == 24 || keyCode == 25) {
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    @SuppressLint({"NewApi"})
    public void init() {
        TextView textView = (TextView) findViewById(R.id.title2);
        ((ImageView) findViewById(R.id.backimg2)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fr
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12419c.lambda$init$0(view);
            }
        });
        this.mWebview = (WebView) findViewById(R.id.web);
        textView.setText(getIntent().getStringExtra("title") + "");
        try {
            if (getIntent().getExtras().getString("web_url") != null) {
                this.url = getIntent().getStringExtra("web_url");
            } else {
                this.url = getIntent().getStringExtra("url");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        WebSettings settings = this.mWebview.getSettings();
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

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && this.mWebview.canGoBack()) {
            this.mWebview.goBack();
            return true;
        }
        finish();
        return false;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        this.mWebview.reload();
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_webview);
        setTitle(getIntent().getStringExtra("title") + "");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
