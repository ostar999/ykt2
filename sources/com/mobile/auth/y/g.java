package com.mobile.auth.y;

import android.content.Context;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public final class g {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f10600a = false;

    public static boolean a(Context context) {
        try {
            if (f10600a) {
                return true;
            }
            Long lB = h.b(context, "success_limit_time");
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (lB == null) {
                h.a(context, "success_limit_time", Long.valueOf(jCurrentTimeMillis));
                return true;
            }
            if (jCurrentTimeMillis - lB.longValue() > 600000) {
                h.a(context, "success_limit_time", Long.valueOf(jCurrentTimeMillis));
                h.a(context, "success_limit_count", (Long) 0L);
                return true;
            }
            Long lB2 = h.b(context, "success_limit_count");
            if (lB2 != null) {
                return lB2.longValue() <= 50;
            }
            h.a(context, "success_limit_count", (Long) 0L);
            return true;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static void b(Context context) {
        try {
            Long lB = h.b(context, "success_limit_count");
            if (lB == null) {
                h.a(context, "success_limit_count", (Long) 0L);
            } else {
                h.a(context, "success_limit_count", Long.valueOf(lB.longValue() + 1));
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static boolean c(Context context) {
        try {
            if (f10600a) {
                return true;
            }
            Long lB = h.b(context, "failed_limit_time");
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (lB == null) {
                h.a(context, "failed_limit_time", Long.valueOf(jCurrentTimeMillis));
                return true;
            }
            if (jCurrentTimeMillis - lB.longValue() > 600000) {
                h.a(context, "failed_limit_time", Long.valueOf(jCurrentTimeMillis));
                h.a(context, "count_limit_count", (Long) 0L);
                return true;
            }
            Long lB2 = h.b(context, "count_limit_count");
            if (lB2 != null) {
                return lB2.longValue() <= 50;
            }
            h.a(context, "count_limit_count", (Long) 0L);
            return true;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static void d(Context context) {
        try {
            Long lB = h.b(context, "count_limit_count");
            if (lB == null) {
                h.a(context, "count_limit_count", (Long) 0L);
            } else {
                h.a(context, "count_limit_count", Long.valueOf(lB.longValue() + 1));
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
