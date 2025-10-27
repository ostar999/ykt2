package com.tencent.open.web.security;

import android.webkit.WebView;
import com.github.lzyzsd.jsbridge.BridgeUtil;
import com.tencent.open.a;
import com.tencent.open.log.SLog;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class c extends a.C0347a {

    /* renamed from: d, reason: collision with root package name */
    private String f20700d;

    public c(WebView webView, long j2, String str, String str2) {
        super(webView, j2, str);
        this.f20700d = str2;
    }

    private void b(String str) {
        WebView webView = this.f20505a.get();
        if (webView != null) {
            StringBuffer stringBuffer = new StringBuffer(BridgeUtil.JAVASCRIPT_STR);
            stringBuffer.append("if(!!");
            stringBuffer.append(this.f20700d);
            stringBuffer.append("){");
            stringBuffer.append(this.f20700d);
            stringBuffer.append("(");
            stringBuffer.append(str);
            stringBuffer.append(")}");
            String string = stringBuffer.toString();
            SLog.v("openSDK_LOG.SecureJsListener", "-->callback, callback: " + string);
            webView.loadUrl(string);
        }
    }

    @Override // com.tencent.open.a.C0347a
    public void a(Object obj) {
        SLog.v("openSDK_LOG.SecureJsListener", "-->onComplete, result: " + obj);
    }

    @Override // com.tencent.open.a.C0347a
    public void a() {
        SLog.d("openSDK_LOG.SecureJsListener", "-->onNoMatchMethod...");
    }

    @Override // com.tencent.open.a.C0347a
    public void a(String str) throws JSONException {
        SLog.v("openSDK_LOG.SecureJsListener", "-->onCustomCallback, js: " + str);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("result", !com.tencent.open.c.c.f20582a ? -4 : 0);
            jSONObject.put("sn", this.f20506b);
            jSONObject.put("data", str);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        b(jSONObject.toString());
    }
}
