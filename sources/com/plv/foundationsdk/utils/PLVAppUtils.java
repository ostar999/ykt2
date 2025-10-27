package com.plv.foundationsdk.utils;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class PLVAppUtils {
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
    private static final String TAG = "PLVAppUtils";
    private static Application application;

    @Nullable
    public static Application getApp() throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Application application2 = application;
        if (application2 != null) {
            return application2;
        }
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Application application3 = (Application) cls.getMethod("getApplication", new Class[0]).invoke(cls.getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]), new Object[0]);
            init(application3);
            return application3;
        } catch (Exception e2) {
            PLVCommonLog.e(TAG, e2.getMessage());
            return null;
        }
    }

    @Nullable
    public static String getString(@StringRes int i2) {
        if (getApp() == null) {
            return null;
        }
        return getApp().getString(i2);
    }

    public static void init(Application application2) {
        if (application == null) {
            application = application2;
            Utils.init(application2);
            LogUtils.getConfig().setConsoleSwitch(false);
        }
    }

    public static void postToMainThread(@NonNull Runnable runnable) {
        MAIN_HANDLER.post(runnable);
    }
}
