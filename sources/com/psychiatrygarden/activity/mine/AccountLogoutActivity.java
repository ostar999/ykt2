package com.psychiatrygarden.activity.mine;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.IdentityConfirmActivity;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class AccountLogoutActivity extends BaseActivity implements View.OnClickListener {
    private CheckBox checkBox;
    private WebView mWebView;
    private TextView tv_logout;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(CompoundButton compoundButton, boolean z2) {
        this.tv_logout.setBackground(ContextCompat.getDrawable(this.mContext, z2 ? R.drawable.shape_app_theme_corners_12 : R.drawable.shape_round_red_default));
    }

    public void changeBg() {
        if (this.mWebView == null || SkinManager.getCurrentSkinType(this.mContext) != 1) {
            return;
        }
        this.mWebView.evaluateJavascript("document.body.style.backgroundColor='#121622';document.body.style.color='#7380A9';", null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        ImageView imageView = (ImageView) findViewById(R.id.backview);
        this.checkBox = (CheckBox) findViewById(R.id.checkBox);
        this.tv_logout = (TextView) findViewById(R.id.tv_logout);
        this.mWebView = (WebView) findViewById(R.id.webview);
        imageView.setOnClickListener(this);
        this.tv_logout.setOnClickListener(this);
        this.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.activity.mine.a
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f12768c.lambda$init$0(compoundButton, z2);
            }
        });
        initData();
    }

    public void initData() {
        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(2);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setMixedContentMode(0);
        this.mWebView.removeJavascriptInterface("searchBoxJavaBredge_");
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.psychiatrygarden.activity.mine.AccountLogoutActivity.1
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                AccountLogoutActivity.this.changeBg();
            }

            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                AccountLogoutActivity.this.mWebView.loadUrl(url);
                return true;
            }
        });
        this.mWebView.loadUrl("https://api.kaoyanhui.com.cn/web/logout.html?type=ykb");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.backview) {
            finish();
            return;
        }
        if (id != R.id.tv_logout) {
            return;
        }
        if (!this.checkBox.isChecked()) {
            ToastUtil.shortToast(this, "请勾选同意协议");
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this.mContext, IdentityConfirmActivity.class);
        intent.putExtra("identityType", 2);
        startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_account_logout);
        setTitle("注销账户");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
