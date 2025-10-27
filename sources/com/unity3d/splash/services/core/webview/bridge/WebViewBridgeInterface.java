package com.unity3d.splash.services.core.webview.bridge;

import android.webkit.JavascriptInterface;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONArray;

/* loaded from: classes6.dex */
public class WebViewBridgeInterface {
    private Object[] getParameters(JSONArray jSONArray) {
        Object[] objArr = new Object[jSONArray.length()];
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            objArr[i2] = jSONArray.get(i2);
        }
        return objArr;
    }

    @JavascriptInterface
    public void handleCallback(String str, String str2, String str3) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Object[] objArr;
        DeviceLog.debug("handleCallback " + str + " " + str2 + " " + str3);
        JSONArray jSONArray = new JSONArray(str3);
        if (jSONArray.length() > 0) {
            objArr = new Object[jSONArray.length()];
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                objArr[i2] = jSONArray.get(i2);
            }
        } else {
            objArr = null;
        }
        WebViewBridge.handleCallback(str, str2, objArr);
    }

    @JavascriptInterface
    public void handleInvocation(String str) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.debug("handleInvocation " + str);
        JSONArray jSONArray = new JSONArray(str);
        Invocation invocation = new Invocation();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONArray jSONArray2 = (JSONArray) jSONArray.get(i2);
            invocation.addInvocation((String) jSONArray2.get(0), (String) jSONArray2.get(1), getParameters((JSONArray) jSONArray2.get(2)), new WebViewCallback((String) jSONArray2.get(3), invocation.getId()));
        }
        for (int i3 = 0; i3 < jSONArray.length(); i3++) {
            invocation.nextInvocation();
        }
        invocation.sendInvocationCallback();
    }
}
