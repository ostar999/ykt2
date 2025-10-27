package com.easefun.polyv.livecommon.ui.window;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVSafeWebView;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVWebViewContentUtils;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVWebViewHelper;

/* loaded from: classes3.dex */
public abstract class PLVSimpleWebViewActivity extends PLVBaseActivity {
    private ViewGroup parentLy;
    private PLVSafeWebView webView;

    private void initView() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.parent_ly);
        this.parentLy = viewGroup;
        viewGroup.setBackgroundColor(getBackgroundColor());
        PLVSafeWebView pLVSafeWebView = new PLVSafeWebView(this);
        this.webView = pLVSafeWebView;
        pLVSafeWebView.setBackgroundColor(0);
        this.webView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.parentLy.addView(this.webView);
        PLVWebViewHelper.initWebView(this, this.webView, isUseActionView());
        loadWebView();
    }

    private void loadWebView() {
        if (TextUtils.isEmpty(urlOrHtmlText())) {
            return;
        }
        if (isLoadUrl()) {
            this.webView.loadUrl(urlOrHtmlText());
        } else {
            this.webView.loadDataWithBaseURL(null, PLVWebViewContentUtils.toWebViewContent(urlOrHtmlText()), "text/html; charset=UTF-8", null, null);
        }
    }

    public int getBackgroundColor() {
        return 0;
    }

    public abstract boolean isLoadUrl();

    public boolean isUseActionView() {
        return true;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView == null || !pLVSafeWebView.canGoBack()) {
            super.onBackPressed();
        } else {
            this.webView.goBack();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plv_horizontal_linear_layout);
        initView();
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.destroy();
            this.webView = null;
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.onPause();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.onResume();
        }
    }

    public abstract String urlOrHtmlText();
}
