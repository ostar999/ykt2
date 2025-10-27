package com.aliyun.vod.common.utils;

import android.os.Looper;

/* loaded from: classes2.dex */
public class ProcessUtil {
    public static final boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
