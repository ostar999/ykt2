package com.aliyun.private_service;

import android.content.Context;
import com.aliyun.utils.f;

/* loaded from: classes2.dex */
public class PrivateService {
    static {
        f.b();
    }

    @Deprecated
    public static void initService(Context context, String str) {
        if (context != null) {
            nInitService(context.getApplicationContext(), str);
        }
    }

    public static void initService(Context context, byte[] bArr) {
        if (context != null) {
            nInitService(context.getApplicationContext(), bArr);
        }
    }

    public static void loadClass() {
    }

    private static native void nInitService(Object obj, String str);

    private static native void nInitService(Object obj, byte[] bArr);

    private static native void nPreInitService(Object obj);

    public static void preInitService(Context context) {
        if (context != null) {
            nPreInitService(context.getApplicationContext());
        }
    }
}
