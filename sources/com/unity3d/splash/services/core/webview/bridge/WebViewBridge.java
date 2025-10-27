package com.unity3d.splash.services.core.webview.bridge;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class WebViewBridge {
    private static HashMap _classTable;

    private static Method findMethod(String str, String str2, Object[] objArr) throws NoSuchMethodException {
        if (!_classTable.containsKey(str)) {
            throw new NoSuchMethodException();
        }
        HashMap map = (HashMap) _classTable.get(str);
        if (map.containsKey(str2)) {
            return (Method) ((HashMap) map.get(str2)).get(Integer.valueOf(Arrays.deepHashCode(getTypes(objArr))));
        }
        throw new NoSuchMethodException();
    }

    private static Class[] getTypes(Object[] objArr) {
        Class[] clsArr = objArr == null ? new Class[1] : new Class[objArr.length + 1];
        if (objArr != null) {
            for (int i2 = 0; i2 < objArr.length; i2++) {
                clsArr[i2] = objArr[i2].getClass();
            }
        }
        clsArr[clsArr.length - 1] = WebViewCallback.class;
        return clsArr;
    }

    private static Object[] getValues(Object[] objArr, WebViewCallback webViewCallback) {
        Object[] objArr2;
        if (objArr != null) {
            objArr2 = new Object[objArr.length + (webViewCallback != null ? 1 : 0)];
        } else {
            if (webViewCallback == null) {
                return null;
            }
            objArr2 = new Object[1];
        }
        if (objArr != null) {
            System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
        }
        if (webViewCallback != null) {
            objArr2[objArr2.length - 1] = webViewCallback;
        }
        return objArr2;
    }

    public static void handleCallback(String str, String str2, Object[] objArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            WebViewApp.getCurrentApp().getCallback(str).invoke(str2, getValues(objArr, null));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | JSONException e2) {
            DeviceLog.error("Error while invoking method");
            throw e2;
        }
    }

    public static void handleInvocation(String str, String str2, Object[] objArr, WebViewCallback webViewCallback) {
        try {
            try {
                findMethod(str, str2, objArr).invoke(null, getValues(objArr, webViewCallback));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | JSONException e2) {
                webViewCallback.error(WebViewBridgeError.INVOCATION_FAILED, str, str2, objArr, e2.getMessage());
                throw e2;
            }
        } catch (NoSuchMethodException | JSONException e3) {
            webViewCallback.error(WebViewBridgeError.METHOD_NOT_FOUND, str, str2, objArr);
            throw e3;
        }
    }

    public static void setClassTable(Class[] clsArr) {
        if (clsArr == null) {
            return;
        }
        _classTable = new HashMap();
        for (Class cls : clsArr) {
            if (cls != null && (cls.getPackage().getName().startsWith("com.unity3d.splash.services") || cls.getPackage().getName().startsWith("com.unity3d.splash.test"))) {
                HashMap map = new HashMap();
                for (Method method : cls.getMethods()) {
                    if (method.getAnnotation(WebViewExposed.class) != null) {
                        String name = method.getName();
                        HashMap map2 = map.containsKey(name) ? (HashMap) map.get(name) : new HashMap();
                        map2.put(Integer.valueOf(Arrays.deepHashCode(method.getParameterTypes())), method);
                        map.put(name, map2);
                    }
                }
                _classTable.put(cls.getName(), map);
            }
        }
    }
}
