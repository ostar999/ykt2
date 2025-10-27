package com.unity3d.splash.services.core.api;

import android.annotation.TargetApi;
import com.unity3d.splash.services.core.lifecycle.LifecycleError;
import com.unity3d.splash.services.core.lifecycle.LifecycleListener;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;

@TargetApi(14)
/* loaded from: classes6.dex */
public class Lifecycle {
    private static LifecycleListener _listener;

    public static LifecycleListener getLifecycleListener() {
        return _listener;
    }

    @WebViewExposed
    public static void register(JSONArray jSONArray, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (ClientProperties.getApplication() == null) {
            webViewCallback.error(LifecycleError.APPLICATION_NULL, new Object[0]);
            return;
        }
        if (getLifecycleListener() != null) {
            webViewCallback.error(LifecycleError.LISTENER_NOT_NULL, new Object[0]);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                arrayList.add((String) jSONArray.get(i2));
            } catch (JSONException unused) {
                webViewCallback.error(LifecycleError.JSON_ERROR, new Object[0]);
                return;
            }
        }
        setLifecycleListener(new LifecycleListener(arrayList));
        ClientProperties.getApplication().registerActivityLifecycleCallbacks(getLifecycleListener());
        webViewCallback.invoke(new Object[0]);
    }

    public static void setLifecycleListener(LifecycleListener lifecycleListener) {
        _listener = lifecycleListener;
    }

    @WebViewExposed
    public static void unregister(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (ClientProperties.getApplication() == null) {
            webViewCallback.error(LifecycleError.APPLICATION_NULL, new Object[0]);
            return;
        }
        if (getLifecycleListener() != null) {
            ClientProperties.getApplication().unregisterActivityLifecycleCallbacks(getLifecycleListener());
            setLifecycleListener(null);
        }
        webViewCallback.invoke(new Object[0]);
    }
}
