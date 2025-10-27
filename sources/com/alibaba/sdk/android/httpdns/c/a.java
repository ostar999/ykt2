package com.alibaba.sdk.android.httpdns.c;

import com.alibaba.sdk.android.httpdns.f.b;
import com.alibaba.sdk.android.httpdns.log.HttpDnsLog;
import java.lang.Thread;

/* loaded from: classes2.dex */
public class a implements Thread.UncaughtExceptionHandler {
    private void a(Throwable th) {
        b bVarA = b.a();
        if (bVarA != null) {
            bVarA.h(th.getMessage());
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        try {
            HttpDnsLog.e("Catch an uncaught exception, " + thread.getName() + ", error message: " + th.getMessage(), th);
            a(th);
            th.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
