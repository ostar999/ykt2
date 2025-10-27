package com.huawei.hms.push.utils.ha;

/* loaded from: classes4.dex */
public class PushAnalyticsCenter {

    /* renamed from: a, reason: collision with root package name */
    public PushBaseAnalytics f8050a;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public static PushAnalyticsCenter f8051a = new PushAnalyticsCenter();
    }

    public static PushAnalyticsCenter getInstance() {
        return a.f8051a;
    }

    public PushBaseAnalytics getPushAnalytics() {
        return this.f8050a;
    }

    public void register(PushBaseAnalytics pushBaseAnalytics) {
        this.f8050a = pushBaseAnalytics;
    }
}
