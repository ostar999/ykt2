package com.alibaba.sdk.android.httpdns.f;

import android.text.TextUtils;
import java.net.SocketTimeoutException;

/* loaded from: classes2.dex */
public class c {
    public static int a(Throwable th) {
        return th instanceof com.alibaba.sdk.android.httpdns.g.b ? ((com.alibaba.sdk.android.httpdns.g.b) th).getCode() : th instanceof SocketTimeoutException ? 10001 : 10000;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m49a(Throwable th) {
        return (th == null || TextUtils.isEmpty(th.getMessage())) ? th instanceof SocketTimeoutException ? "time out exception" : "default error" : th.getMessage();
    }
}
