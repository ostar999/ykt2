package com.unity3d.splash.services.core.webview.bridge;

import cn.hutool.core.text.StrPool;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public class NativeCallback {
    private static AtomicInteger _callbackCount = new AtomicInteger(0);
    private Method _callback;
    private String _id;

    public NativeCallback(Method method) {
        this._callback = method;
        this._id = this._callback.getName().toUpperCase(Locale.US) + StrPool.UNDERLINE + _callbackCount.getAndIncrement();
    }

    public String getId() {
        return this._id;
    }

    public void invoke(String str, Object... objArr) {
        Object[] array;
        try {
            CallbackStatus callbackStatusValueOf = CallbackStatus.valueOf(str);
            if (objArr == null) {
                array = new Object[]{callbackStatusValueOf};
            } else {
                ArrayList arrayList = new ArrayList(Arrays.asList(objArr));
                arrayList.add(0, callbackStatusValueOf);
                array = arrayList.toArray();
            }
            this._callback.invoke(null, array);
            WebViewApp.getCurrentApp().removeCallback(this);
        } catch (Exception e2) {
            DeviceLog.error("Illegal status");
            WebViewApp.getCurrentApp().removeCallback(this);
            throw e2;
        }
    }
}
