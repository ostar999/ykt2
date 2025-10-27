package com.just.agentweb;

import android.webkit.WebView;
import android.widget.FrameLayout;

/* loaded from: classes4.dex */
public interface WebCreator extends IWebIndicator {
    WebCreator create();

    FrameLayout getWebParentLayout();

    WebView getWebView();

    int getWebViewType();
}
