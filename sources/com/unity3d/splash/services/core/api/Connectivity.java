package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.connectivity.ConnectivityMonitor;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes6.dex */
public class Connectivity {
    @WebViewExposed
    public static void setConnectionMonitoring(Boolean bool, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ConnectivityMonitor.setConnectionMonitoring(bool.booleanValue());
        webViewCallback.invoke(new Object[0]);
    }
}
