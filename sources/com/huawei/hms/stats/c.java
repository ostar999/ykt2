package com.huawei.hms.stats;

import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public static final Object f8059a = new Object();

    /* renamed from: b, reason: collision with root package name */
    public static boolean f8060b = false;

    /* renamed from: c, reason: collision with root package name */
    public static boolean f8061c = false;

    public static boolean a() {
        synchronized (f8059a) {
            if (!f8060b) {
                try {
                    Class.forName("com.huawei.hianalytics.process.HiAnalyticsInstance");
                } catch (ClassNotFoundException unused) {
                    HMSLog.i("HianalyticsExist", "In isHianalyticsExist, Failed to find class HiAnalyticsConfig.");
                }
                f8060b = true;
                HMSLog.i("HianalyticsExist", "hianalytics exist: " + f8061c);
            }
        }
        return f8061c;
    }
}
