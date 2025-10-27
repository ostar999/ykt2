package com.just.agentweb;

import android.annotation.TargetApi;
import android.webkit.WebView;
import androidx.collection.ArrayMap;
import com.just.agentweb.AgentWeb;

/* loaded from: classes4.dex */
public class WebSecurityLogicImpl implements WebSecurityCheckLogic {
    private String TAG = getClass().getSimpleName();
    private int webviewType;

    public WebSecurityLogicImpl(int i2) {
        this.webviewType = i2;
    }

    public static WebSecurityLogicImpl getInstance(int i2) {
        return new WebSecurityLogicImpl(i2);
    }

    @Override // com.just.agentweb.WebSecurityCheckLogic
    @TargetApi(11)
    public void dealHoneyComb(WebView webView) {
    }

    @Override // com.just.agentweb.WebSecurityCheckLogic
    public void dealJsInterface(ArrayMap<String, Object> arrayMap, AgentWeb.SecurityType securityType) {
        AgentWeb.SecurityType securityType2 = AgentWeb.SecurityType.STRICT_CHECK;
    }
}
