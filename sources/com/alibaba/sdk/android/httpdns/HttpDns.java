package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import com.alibaba.sdk.android.httpdns.d.f;
import com.alibaba.sdk.android.httpdns.d.h;

/* loaded from: classes2.dex */
public class HttpDns {
    private static final f holder = new f(new h());

    public static synchronized HttpDnsService getService(Context context) {
        return holder.b(context, com.alibaba.sdk.android.httpdns.j.a.c(context), com.alibaba.sdk.android.httpdns.j.a.d(context));
    }

    public static synchronized HttpDnsService getService(Context context, String str) {
        return holder.b(context, str, null);
    }

    public static synchronized HttpDnsService getService(Context context, String str, String str2) {
        return holder.b(context, str, str2);
    }

    @Deprecated
    public static synchronized void switchDnsService(boolean z2) {
    }
}
