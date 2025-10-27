package com.sina.weibo.sdk.web.a;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import com.sina.weibo.sdk.auth.AccessTokenHelper;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.b.e;
import com.sina.weibo.sdk.common.UiError;

/* loaded from: classes6.dex */
public final class a extends b {
    public a(Activity activity, com.sina.weibo.sdk.web.a aVar, com.sina.weibo.sdk.web.b.b bVar) {
        super(activity, aVar, bVar);
    }

    private boolean m(String str) {
        Bundle bundleH;
        AuthInfo authInfoB = this.aA.x().b();
        return (authInfoB == null || !str.startsWith(authInfoB.getRedirectUrl()) || (bundleH = e.h(str)) == null || TextUtils.isEmpty(bundleH.getString("access_token"))) ? false : true;
    }

    @Override // com.sina.weibo.sdk.web.a.b, android.webkit.WebViewClient
    public final void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        AuthInfo authInfoB = this.aA.x().b();
        if (authInfoB == null || !str.startsWith(authInfoB.getRedirectUrl())) {
            return;
        }
        String strU = this.aA.x().u();
        if (!TextUtils.isEmpty(strU)) {
            WbAuthListener wbAuthListenerB = this.ax.b(strU);
            this.aB = wbAuthListenerB;
            if (wbAuthListenerB != null) {
                Bundle bundleH = e.h(str);
                if (bundleH != null) {
                    String string = bundleH.getString("error");
                    String string2 = bundleH.getString("error_code");
                    String string3 = bundleH.getString("error_description");
                    if (TextUtils.isEmpty(string) && TextUtils.isEmpty(string2)) {
                        Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(bundleH);
                        AccessTokenHelper.writeAccessToken(this.ay, accessToken);
                        this.aB.onComplete(accessToken);
                    } else {
                        this.aB.onError(new UiError(-1, string2, string3));
                    }
                } else {
                    this.aB.onError(new UiError(-1, "bundle is null", "parse url error"));
                }
                this.ax.c(strU);
            }
        }
        com.sina.weibo.sdk.web.a aVar = this.az;
        if (aVar != null) {
            aVar.t();
        }
    }

    @Override // com.sina.weibo.sdk.web.a.b, android.webkit.WebViewClient
    public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
    }

    @Override // com.sina.weibo.sdk.web.a.b, android.webkit.WebViewClient
    public final boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        return m(webResourceRequest.getUrl().toString());
    }

    @Override // com.sina.weibo.sdk.web.a.b
    public final void t() {
        super.t();
        String strU = this.aA.x().u();
        if (!TextUtils.isEmpty(strU)) {
            WbAuthListener wbAuthListenerB = this.ax.b(strU);
            this.aB = wbAuthListenerB;
            if (wbAuthListenerB != null) {
                wbAuthListenerB.onCancel();
            }
            this.ax.c(strU);
        }
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

    @Override // android.webkit.WebViewClient
    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        return m(str);
    }
}
