package com.huawei.hms.framework.network.grs.h;

import com.huawei.hms.framework.common.Logger;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static final String f7700a = "e";

    public static boolean a(Long l2) {
        if (l2 == null) {
            Logger.v(f7700a, "Method isTimeExpire input param expireTime is null.");
            return true;
        }
        try {
        } catch (NumberFormatException unused) {
            Logger.v(f7700a, "isSpExpire spValue NumberFormatException.");
        }
        if (l2.longValue() - System.currentTimeMillis() >= 0) {
            Logger.i(f7700a, "isSpExpire false.");
            return false;
        }
        Logger.i(f7700a, "isSpExpire true.");
        return true;
    }

    public static boolean a(Long l2, long j2) {
        if (l2 == null) {
            Logger.v(f7700a, "Method isTimeWillExpire input param expireTime is null.");
            return true;
        }
        try {
            if (l2.longValue() - (System.currentTimeMillis() + j2) >= 0) {
                Logger.v(f7700a, "isSpExpire false.");
                return false;
            }
        } catch (NumberFormatException unused) {
            Logger.v(f7700a, "isSpExpire spValue NumberFormatException.");
        }
        return true;
    }
}
