package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.broadcast.BroadcastError;
import com.unity3d.splash.services.core.broadcast.BroadcastMonitor;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class Broadcast {
    @WebViewExposed
    public static void addBroadcastListener(String str, String str2, JSONArray jSONArray, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            if (jSONArray.length() > 0) {
                String[] strArr = new String[jSONArray.length()];
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    strArr[i2] = jSONArray.getString(i2);
                }
                BroadcastMonitor.addBroadcastListener(str, str2, strArr);
            }
            webViewCallback.invoke(new Object[0]);
        } catch (JSONException unused) {
            webViewCallback.error(BroadcastError.JSON_ERROR, new Object[0]);
        }
    }

    @WebViewExposed
    public static void addBroadcastListener(String str, JSONArray jSONArray, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        addBroadcastListener(str, null, jSONArray, webViewCallback);
    }

    @WebViewExposed
    public static void removeAllBroadcastListeners(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        BroadcastMonitor.removeAllBroadcastListeners();
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void removeBroadcastListener(String str, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        BroadcastMonitor.removeBroadcastListener(str);
        webViewCallback.invoke(new Object[0]);
    }
}
