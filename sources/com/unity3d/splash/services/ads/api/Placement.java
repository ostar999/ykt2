package com.unity3d.splash.services.ads.api;

import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes6.dex */
public class Placement {
    @WebViewExposed
    public static void setDefaultBannerPlacement(String str, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        com.unity3d.splash.services.ads.placement.Placement.setDefaultBannerPlacement(str);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setDefaultPlacement(String str, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        com.unity3d.splash.services.ads.placement.Placement.setDefaultPlacement(str);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setPlacementState(String str, String str2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        com.unity3d.splash.services.ads.placement.Placement.setPlacementState(str, str2);
        webViewCallback.invoke(new Object[0]);
    }
}
