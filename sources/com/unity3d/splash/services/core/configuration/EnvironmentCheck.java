package com.unity3d.splash.services.core.configuration;

import android.webkit.JavascriptInterface;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.SdkProperties;
import com.unity3d.splash.services.core.webview.bridge.WebViewBridgeInterface;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class EnvironmentCheck {
    private static boolean hasJavascriptInterface(Method method) {
        Annotation[] annotations = method.getAnnotations();
        if (annotations != null) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof JavascriptInterface) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isEnvironmentOk() {
        return testProGuard() && testCacheDirectory();
    }

    public static boolean testCacheDirectory() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (SdkProperties.getCacheDirectory() != null) {
            DeviceLog.debug("Unity Ads cache directory check OK");
            return true;
        }
        DeviceLog.error("Unity Ads cache directory check fail: no working cache directory available");
        return false;
    }

    public static boolean testProGuard() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        String str;
        try {
            Method method = WebViewBridgeInterface.class.getMethod("handleInvocation", String.class);
            Method method2 = WebViewBridgeInterface.class.getMethod("handleCallback", String.class, String.class, String.class);
            if (hasJavascriptInterface(method) && hasJavascriptInterface(method2)) {
                DeviceLog.debug("Unity Ads ProGuard check OK");
                return true;
            }
            DeviceLog.error("Unity Ads ProGuard check fail: missing @JavascriptInterface annotations in Unity Ads web bridge");
            return false;
        } catch (ClassNotFoundException e2) {
            e = e2;
            str = "Unity Ads ProGuard check fail: Unity Ads web bridge class not found";
            DeviceLog.exception(str, e);
            return false;
        } catch (NoSuchMethodException e3) {
            e = e3;
            str = "Unity Ads ProGuard check fail: Unity Ads web bridge methods not found";
            DeviceLog.exception(str, e);
            return false;
        } catch (Exception e4) {
            DeviceLog.exception("Unknown exception during Unity Ads ProGuard check: " + e4.getMessage(), e4);
            return true;
        }
    }
}
