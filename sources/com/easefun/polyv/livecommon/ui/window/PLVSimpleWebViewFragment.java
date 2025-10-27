package com.easefun.polyv.livecommon.ui.window;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVSafeWebView;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVWebViewContentUtils;
import com.easefun.polyv.livecommon.ui.widget.webview.PLVWebViewHelper;

/* loaded from: classes3.dex */
public abstract class PLVSimpleWebViewFragment extends PLVBaseFragment {
    private ViewGroup parentLy;
    private PLVSafeWebView webView;

    private void initView() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.parent_ly);
        this.parentLy = viewGroup;
        viewGroup.setBackgroundColor(getBackgroundColor());
        PLVSafeWebView pLVSafeWebView = new PLVSafeWebView(getContext());
        this.webView = pLVSafeWebView;
        pLVSafeWebView.setBackgroundColor(0);
        this.webView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.parentLy.addView(this.webView);
        PLVWebViewHelper.initWebView(getContext(), this.webView, isUseActionView());
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

    public boolean onBackPressed() {
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView == null || !pLVSafeWebView.canGoBack()) {
            return false;
        }
        this.webView.goBack();
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.plv_horizontal_linear_layout, (ViewGroup) null);
        initView();
        return this.view;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.destroy();
            this.webView = null;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.onPause();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        PLVSafeWebView pLVSafeWebView = this.webView;
        if (pLVSafeWebView != null) {
            pLVSafeWebView.onResume();
        }
    }

    public abstract String urlOrHtmlText();
}
