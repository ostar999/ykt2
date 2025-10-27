package com.sina.weibo.sdk.web.a;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import com.sina.weibo.sdk.b.e;

/* loaded from: classes6.dex */
public final class d extends b {
    public d(Activity activity, com.sina.weibo.sdk.web.a aVar, com.sina.weibo.sdk.web.b.b bVar) {
        super(activity, aVar, bVar);
    }

    @Override // com.sina.weibo.sdk.web.a.b, android.webkit.WebViewClient
    public final void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
    }

    @Override // com.sina.weibo.sdk.web.a.b, android.webkit.WebViewClient
    public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
    }

    @Override // com.sina.weibo.sdk.web.a.b
    public final void q(String str) {
        o(str);
    }

    @Override // android.webkit.WebViewClient
    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (TextUtils.isEmpty(str) || !str.startsWith("sinaweibo://browser/close")) {
            return false;
        }
        Bundle bundleI = e.i(str);
        if (bundleI != null) {
            String string = bundleI.getString("code");
            String string2 = bundleI.getString("msg");
            if ("0".equals(string)) {
                n(string2);
            } else {
                o(string2);
            }
        } else {
            o("bundle is null!!!");
        }
        com.sina.weibo.sdk.web.a aVar = this.az;
        if (aVar == null) {
            return true;
        }
        aVar.t();
        return true;
    }

    @Override // com.sina.weibo.sdk.web.a.b
    public final void t() {
        p("cancel share!!!");
        com.sina.weibo.sdk.web.a aVar = this.az;
        if (aVar != null) {
            aVar.t();
        }
    }

    @Override // com.sina.weibo.sdk.web.a.b
    public final boolean v() {
        t();
        return true;
    }

    @Override // com.sina.weibo.sdk.web.a.b, android.webkit.WebViewClient
    @TargetApi(21)
    public final boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        String string = webResourceRequest.getUrl().toString();
        if (TextUtils.isEmpty(string) || !string.startsWith("sinaweibo://browser/close")) {
            return false;
        }
        Bundle bundleI = e.i(string);
        if (bundleI != null) {
            String string2 = bundleI.getString("code");
            String string3 = bundleI.getString("msg");
            if (TextUtils.isEmpty(string2)) {
                p("code is null!!!");
            } else if ("0".equals(string2)) {
                n(string3);
            } else {
                o(string3);
            }
        } else {
            o("bundle is null!!!");
        }
        com.sina.weibo.sdk.web.a aVar = this.az;
        if (aVar == null) {
            return true;
        }
        aVar.t();
        return true;
    }
}
