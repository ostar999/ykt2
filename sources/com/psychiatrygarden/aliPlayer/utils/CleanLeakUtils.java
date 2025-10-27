package com.psychiatrygarden.aliPlayer.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import java.lang.reflect.Field;

/* loaded from: classes5.dex */
public class CleanLeakUtils {
    public static void fixHuaWeiMemoryLeak(Activity activity) {
        try {
            Class<?> cls = Class.forName("android.gestureboost.GestureBoostManager");
            Field declaredField = cls.getDeclaredField("sGestureBoostManager");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(cls);
            Field declaredField2 = cls.getDeclaredField("mContext");
            declaredField2.setAccessible(true);
            if (declaredField2.get(obj) == activity) {
                declaredField2.set(obj, null);
            }
        } catch (ClassNotFoundException unused) {
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        InputMethodManager inputMethodManager;
        if (destContext == null || (inputMethodManager = (InputMethodManager) destContext.getApplicationContext().getSystemService("input_method")) == null) {
            return;
        }
        String[] strArr = {"mCurRootView", "mServedView", "mNextServedView"};
        for (int i2 = 0; i2 < 3; i2++) {
            try {
                Field declaredField = inputMethodManager.getClass().getDeclaredField(strArr[i2]);
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                Object obj = declaredField.get(inputMethodManager);
                if (obj != null && (obj instanceof View)) {
                    if (((View) obj).getContext() != destContext) {
                        return;
                    } else {
                        declaredField.set(inputMethodManager, null);
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x001c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void fixTextLineCacheLeak() throws java.lang.IllegalAccessException, java.lang.NoSuchFieldException, java.lang.SecurityException, java.lang.ArrayIndexOutOfBoundsException, java.lang.IllegalArgumentException {
        /*
            r0 = 0
            java.lang.String r1 = "android.text.TextLine"
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch: java.lang.Exception -> L14
            java.lang.String r2 = "sCached"
            java.lang.reflect.Field r1 = r1.getDeclaredField(r2)     // Catch: java.lang.Exception -> L14
            r2 = 1
            r1.setAccessible(r2)     // Catch: java.lang.Exception -> L12
            goto L19
        L12:
            r2 = move-exception
            goto L16
        L14:
            r2 = move-exception
            r1 = r0
        L16:
            r2.printStackTrace()
        L19:
            if (r1 != 0) goto L1c
            return
        L1c:
            java.lang.Object r1 = r1.get(r0)     // Catch: java.lang.Exception -> L21
            goto L26
        L21:
            r1 = move-exception
            r1.printStackTrace()
            r1 = r0
        L26:
            if (r1 == 0) goto L35
            int r2 = java.lang.reflect.Array.getLength(r1)
            r3 = 0
        L2d:
            if (r3 >= r2) goto L35
            java.lang.reflect.Array.set(r1, r3, r0)
            int r3 = r3 + 1
            goto L2d
        L35:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.aliPlayer.utils.CleanLeakUtils.fixTextLineCacheLeak():void");
    }

    public static void freeWebview(WebView webView) {
        ViewParent parent = webView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(webView);
        }
        webView.stopLoading();
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearHistory();
        webView.clearView();
        webView.removeAllViews();
        try {
            webView.destroy();
        } catch (Throwable unused) {
        }
    }
}
