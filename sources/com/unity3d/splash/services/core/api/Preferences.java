package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.preferences.AndroidPreferences;
import com.unity3d.splash.services.core.preferences.PreferencesError;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes6.dex */
public class Preferences {
    @WebViewExposed
    public static void getBoolean(String str, String str2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Boolean bool = AndroidPreferences.getBoolean(str, str2);
        if (bool != null) {
            webViewCallback.invoke(bool);
        } else {
            webViewCallback.error(PreferencesError.COULDNT_GET_VALUE, str, str2);
        }
    }

    @WebViewExposed
    public static void getFloat(String str, String str2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Float f2 = AndroidPreferences.getFloat(str, str2);
        if (f2 != null) {
            webViewCallback.invoke(f2);
        } else {
            webViewCallback.error(PreferencesError.COULDNT_GET_VALUE, str, str2);
        }
    }

    @WebViewExposed
    public static void getInt(String str, String str2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Integer integer = AndroidPreferences.getInteger(str, str2);
        if (integer != null) {
            webViewCallback.invoke(integer);
        } else {
            webViewCallback.error(PreferencesError.COULDNT_GET_VALUE, str, str2);
        }
    }

    @WebViewExposed
    public static void getLong(String str, String str2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Long l2 = AndroidPreferences.getLong(str, str2);
        if (l2 != null) {
            webViewCallback.invoke(l2);
        } else {
            webViewCallback.error(PreferencesError.COULDNT_GET_VALUE, str, str2);
        }
    }

    @WebViewExposed
    public static void getString(String str, String str2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        String string = AndroidPreferences.getString(str, str2);
        if (string != null) {
            webViewCallback.invoke(string);
        } else {
            webViewCallback.error(PreferencesError.COULDNT_GET_VALUE, str, str2);
        }
    }

    @WebViewExposed
    public static void hasKey(String str, String str2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        webViewCallback.invoke(Boolean.valueOf(AndroidPreferences.hasKey(str, str2)));
    }

    @WebViewExposed
    public static void removeKey(String str, String str2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        AndroidPreferences.removeKey(str, str2);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setBoolean(String str, String str2, Boolean bool, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        AndroidPreferences.setBoolean(str, str2, bool);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setFloat(String str, String str2, Double d3, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        AndroidPreferences.setFloat(str, str2, d3);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setInt(String str, String str2, Integer num, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        AndroidPreferences.setInteger(str, str2, num);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setLong(String str, String str2, Long l2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        AndroidPreferences.setLong(str, str2, l2);
        webViewCallback.invoke(new Object[0]);
    }

    @WebViewExposed
    public static void setString(String str, String str2, String str3, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        AndroidPreferences.setString(str, str2, str3);
        webViewCallback.invoke(new Object[0]);
    }
}
