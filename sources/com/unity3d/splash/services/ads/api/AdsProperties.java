package com.unity3d.splash.services.ads.api;

import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes6.dex */
public class AdsProperties {
    @WebViewExposed
    public static void setShowTimeout(Integer num, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        com.unity3d.splash.services.ads.properties.AdsProperties.setShowTimeout(num.intValue());
        webViewCallback.invoke(new Object[0]);
    }
}
