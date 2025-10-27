package com.umeng.commonsdk;

import android.content.Context;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.utils.UMUtils;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static Class<?> f23034a;

    /* renamed from: b, reason: collision with root package name */
    private static Method f23035b;

    static {
        try {
            Class<?> cls = Class.forName("com.umeng.commonsdk.UMInnerImpl");
            f23034a = cls;
            Method declaredMethod = cls.getDeclaredMethod("initAndSendInternal", Context.class);
            if (declaredMethod != null) {
                f23035b = declaredMethod;
            }
        } catch (Throwable unused) {
        }
    }

    public static void a(Context context) {
        Method method;
        if (context == null || !UMUtils.isMainProgress(context)) {
            return;
        }
        if (SdkVersion.SDK_TYPE == 1) {
            UMConfigureInternation.sendInternal(context);
            return;
        }
        Class<?> cls = f23034a;
        if (cls == null || (method = f23035b) == null) {
            return;
        }
        try {
            method.invoke(cls, context);
        } catch (Throwable unused) {
        }
    }
}
