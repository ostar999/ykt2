package com.just.agentweb;

import android.webkit.WebSettings;
import android.webkit.WebView;

/* loaded from: classes4.dex */
public interface IAgentWebSettings<T extends WebSettings> {
    T getWebSettings();

    IAgentWebSettings toSetting(WebView webView);
}
