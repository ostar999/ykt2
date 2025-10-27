package com.hjq.toast;

import android.app.Application;
import android.content.res.Resources;
import com.hjq.toast.config.IToastInterceptor;
import com.hjq.toast.config.IToastStrategy;
import com.hjq.toast.config.IToastStyle;
import com.hjq.toast.style.BlackToastStyle;
import com.hjq.toast.style.CustomToastStyle;
import com.hjq.toast.style.LocationToastStyle;

/* loaded from: classes4.dex */
public final class Toaster {
    private static Application sApplication;
    private static Boolean sDebugMode;
    private static IToastInterceptor sToastInterceptor;
    private static IToastStrategy sToastStrategy;
    private static IToastStyle<?> sToastStyle;

    private Toaster() {
    }

    public static void cancel() {
        sToastStrategy.cancelToast();
    }

    private static void checkInitStatus() {
        if (sApplication == null) {
            throw new IllegalStateException("Toaster has not been initialized");
        }
    }

    public static void debugShow(int i2) {
        debugShow(stringIdToCharSequence(i2));
    }

    public static void delayedShow(int i2, long j2) {
        delayedShow(stringIdToCharSequence(i2), j2);
    }

    public static IToastInterceptor getInterceptor() {
        return sToastInterceptor;
    }

    public static IToastStrategy getStrategy() {
        return sToastStrategy;
    }

    public static IToastStyle<?> getStyle() {
        return sToastStyle;
    }

    public static void init(Application application) {
        init(application, sToastStyle);
    }

    public static boolean isDebugMode() {
        if (sDebugMode == null) {
            checkInitStatus();
            sDebugMode = Boolean.valueOf((sApplication.getApplicationInfo().flags & 2) != 0);
        }
        return sDebugMode.booleanValue();
    }

    public static boolean isInit() {
        return (sApplication == null || sToastStrategy == null || sToastStyle == null) ? false : true;
    }

    private static CharSequence objectToCharSequence(Object obj) {
        return obj != null ? obj.toString() : "null";
    }

    public static void setDebugMode(boolean z2) {
        sDebugMode = Boolean.valueOf(z2);
    }

    public static void setGravity(int i2) {
        setGravity(i2, 0, 0);
    }

    public static void setInterceptor(IToastInterceptor iToastInterceptor) {
        sToastInterceptor = iToastInterceptor;
    }

    public static void setStrategy(IToastStrategy iToastStrategy) {
        sToastStrategy = iToastStrategy;
        iToastStrategy.registerStrategy(sApplication);
    }

    public static void setStyle(IToastStyle<?> iToastStyle) {
        sToastStyle = iToastStyle;
    }

    public static void setView(int i2) {
        if (i2 <= 0) {
            return;
        }
        setStyle(new CustomToastStyle(i2, sToastStyle.getGravity(), sToastStyle.getXOffset(), sToastStyle.getYOffset(), sToastStyle.getHorizontalMargin(), sToastStyle.getVerticalMargin()));
    }

    public static void show(int i2) {
        show(stringIdToCharSequence(i2));
    }

    public static void showLong(int i2) {
        showLong(stringIdToCharSequence(i2));
    }

    public static void showShort(int i2) {
        showShort(stringIdToCharSequence(i2));
    }

    private static CharSequence stringIdToCharSequence(int i2) {
        checkInitStatus();
        try {
            return sApplication.getResources().getText(i2);
        } catch (Resources.NotFoundException unused) {
            return String.valueOf(i2);
        }
    }

    public static void debugShow(Object obj) {
        debugShow(objectToCharSequence(obj));
    }

    public static void delayedShow(Object obj, long j2) {
        delayedShow(objectToCharSequence(obj), j2);
    }

    public static void init(Application application, IToastStrategy iToastStrategy) {
        init(application, iToastStrategy, null);
    }

    public static void setGravity(int i2, int i3, int i4) {
        setGravity(i2, i3, i4, 0.0f, 0.0f);
    }

    public static void show(Object obj) {
        show(objectToCharSequence(obj));
    }

    public static void showLong(Object obj) {
        showLong(objectToCharSequence(obj));
    }

    public static void showShort(Object obj) {
        showShort(objectToCharSequence(obj));
    }

    public static void debugShow(CharSequence charSequence) {
        if (isDebugMode()) {
            ToastParams toastParams = new ToastParams();
            toastParams.text = charSequence;
            show(toastParams);
        }
    }

    public static void delayedShow(CharSequence charSequence, long j2) {
        ToastParams toastParams = new ToastParams();
        toastParams.text = charSequence;
        toastParams.delayMillis = j2;
        show(toastParams);
    }

    public static void init(Application application, IToastStyle<?> iToastStyle) {
        init(application, null, iToastStyle);
    }

    public static void setGravity(int i2, int i3, int i4, float f2, float f3) {
        sToastStyle = new LocationToastStyle(sToastStyle, i2, i3, i4, f2, f3);
    }

    public static void show(CharSequence charSequence) {
        ToastParams toastParams = new ToastParams();
        toastParams.text = charSequence;
        show(toastParams);
    }

    public static void showLong(CharSequence charSequence) {
        ToastParams toastParams = new ToastParams();
        toastParams.text = charSequence;
        toastParams.duration = 1;
        show(toastParams);
    }

    public static void showShort(CharSequence charSequence) {
        ToastParams toastParams = new ToastParams();
        toastParams.text = charSequence;
        toastParams.duration = 0;
        show(toastParams);
    }

    public static void init(Application application, IToastStrategy iToastStrategy, IToastStyle<?> iToastStyle) {
        sApplication = application;
        if (iToastStrategy == null) {
            iToastStrategy = new ToastStrategy();
        }
        setStrategy(iToastStrategy);
        if (iToastStyle == null) {
            iToastStyle = new BlackToastStyle();
        }
        setStyle(iToastStyle);
    }

    public static void show(ToastParams toastParams) {
        checkInitStatus();
        CharSequence charSequence = toastParams.text;
        if (charSequence == null || charSequence.length() == 0) {
            return;
        }
        if (toastParams.strategy == null) {
            toastParams.strategy = sToastStrategy;
        }
        if (toastParams.interceptor == null) {
            if (sToastInterceptor == null) {
                sToastInterceptor = new ToastLogInterceptor();
            }
            toastParams.interceptor = sToastInterceptor;
        }
        if (toastParams.style == null) {
            toastParams.style = sToastStyle;
        }
        if (toastParams.interceptor.intercept(toastParams)) {
            return;
        }
        if (toastParams.duration == -1) {
            toastParams.duration = toastParams.text.length() > 20 ? 1 : 0;
        }
        toastParams.strategy.showToast(toastParams);
    }
}
