package com.huawei.hms.framework.common;

import android.annotation.SuppressLint;
import android.content.Context;

/* loaded from: classes4.dex */
public class ContextHolder {
    private static final String TAG = "ContextHolder";

    @SuppressLint({"StaticFieldLeak"})
    private static Context sAppContext;

    @SuppressLint({"StaticFieldLeak"})
    private static Context sKitContext;

    public static Context getAppContext() {
        return sAppContext;
    }

    public static Context getKitContext() {
        return sKitContext;
    }

    public static Context getResourceContext() {
        return getKitContext() != null ? getKitContext() : getAppContext();
    }

    public static void setAppContext(Context context) {
        CheckParamUtils.checkNotNull(context, "sAppContext == null");
        sAppContext = context.getApplicationContext();
    }

    public static void setKitContext(Context context) {
        CheckParamUtils.checkNotNull(context, "sKitContext == null");
        sKitContext = context;
    }
}
