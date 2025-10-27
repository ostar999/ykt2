package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.configuration.InitializationNotificationCenter;
import com.unity3d.splash.services.core.configuration.InitializeThread;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/* loaded from: classes6.dex */
public class Sdk {
    @WebViewExposed
    public static void getDebugMode(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        webViewCallback.invoke(Boolean.valueOf(SdkProperties.getDebugMode()));
    }

    @WebViewExposed
    public static void initComplete(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.info("Web Application initialized at " + new Date().getTime());
        SdkProperties.setInitialized(true);
        WebViewApp.getCurrentApp().setWebAppInitialized(true);
        InitializationNotificationCenter.getInstance().triggerOnSdkInitialized();
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void initError(String str, int i2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        InitializationNotificationCenter.getInstance().triggerOnSdkInitializationFailed(str, i2);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void loadComplete(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.info("Web Application loaded at " + new Date().getTime());
        WebViewApp.getCurrentApp().setWebAppLoaded(true);
        webViewCallback.invoke(ClientProperties.getGameId(), Boolean.valueOf(SdkProperties.isTestMode()), ClientProperties.getAppName(), ClientProperties.getAppVersion(), Integer.valueOf(SdkProperties.getVersionCode()), SdkProperties.getVersionName(), Boolean.valueOf(ClientProperties.isAppDebuggable()), WebViewApp.getCurrentApp().getConfiguration().getConfigUrl(), WebViewApp.getCurrentApp().getConfiguration().getWebViewUrl(), WebViewApp.getCurrentApp().getConfiguration().getWebViewHash(), WebViewApp.getCurrentApp().getConfiguration().getWebViewVersion(), Long.valueOf(SdkProperties.getInitializationTime()), Boolean.valueOf(SdkProperties.isReinitialized()), Boolean.valueOf(SdkProperties.isPerPlacementLoadEnabled()));
    }

    @WebViewExposed
    public static void logDebug(String str, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.debug(str);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void logError(String str, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.error(str);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void logInfo(String str, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.info(str);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void logWarning(String str, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.warning(str);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void reinitialize(WebViewCallback webViewCallback) {
        SdkProperties.setReinitialized(true);
        InitializeThread.initialize(WebViewApp.getCurrentApp().getConfiguration());
    }

    @WebViewExposed
    public static void setDebugMode(Boolean bool, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        SdkProperties.setDebugMode(bool.booleanValue());
        webViewCallback.invoke(new Object[0]);
    }
}
