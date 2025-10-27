package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.sensorinfo.SensorInfoError;
import com.unity3d.splash.services.core.sensorinfo.SensorInfoListener;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class SensorInfo {
    @WebViewExposed
    public static void getAccelerometerData(WebViewCallback webViewCallback) throws JSONException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        JSONObject accelerometerData = SensorInfoListener.getAccelerometerData();
        if (accelerometerData != null) {
            webViewCallback.invoke(accelerometerData);
        } else {
            webViewCallback.error(SensorInfoError.ACCELEROMETER_DATA_NOT_AVAILABLE, new Object[0]);
        }
    }

    @WebViewExposed
    public static void isAccelerometerActive(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        webViewCallback.invoke(Boolean.valueOf(SensorInfoListener.isAccelerometerListenerActive()));
    }

    @WebViewExposed
    public static void startAccelerometerUpdates(Integer num, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        webViewCallback.invoke(Boolean.valueOf(SensorInfoListener.startAccelerometerListener(num.intValue())));
    }

    @WebViewExposed
    public static void stopAccelerometerUpdates(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        SensorInfoListener.stopAccelerometerListener();
        webViewCallback.invoke(new Object[0]);
    }
}
