package com.huawei.hms.aaid.plugin;

/* loaded from: classes4.dex */
public class ProxyCenter {
    public PushProxy proxy;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public static ProxyCenter f7419a = new ProxyCenter();
    }

    public static ProxyCenter getInstance() {
        return a.f7419a;
    }

    public static PushProxy getProxy() {
        return getInstance().proxy;
    }

    public static void register(PushProxy pushProxy) {
        getInstance().proxy = pushProxy;
    }
}
