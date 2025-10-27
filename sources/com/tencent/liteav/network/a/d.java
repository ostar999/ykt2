package com.tencent.liteav.network.a;

/* loaded from: classes6.dex */
public final class d {

    /* renamed from: a, reason: collision with root package name */
    public static final d f19509a = new d(a.NO_NETWORK, 0);

    /* renamed from: b, reason: collision with root package name */
    public static final d f19510b = new d(a.WIFI, 0);

    /* renamed from: c, reason: collision with root package name */
    public final int f19511c;

    /* renamed from: d, reason: collision with root package name */
    public final a f19512d;

    public enum a {
        NO_NETWORK,
        WIFI,
        MOBILE
    }

    public d(a aVar, int i2) {
        this.f19512d = aVar;
        this.f19511c = i2;
    }
}
