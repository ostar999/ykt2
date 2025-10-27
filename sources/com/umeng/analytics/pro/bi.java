package com.umeng.analytics.pro;

import java.io.Serializable;

/* loaded from: classes6.dex */
public class bi implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    private final boolean f22567a;

    /* renamed from: b, reason: collision with root package name */
    public final byte f22568b;

    /* renamed from: c, reason: collision with root package name */
    private final String f22569c;

    /* renamed from: d, reason: collision with root package name */
    private final boolean f22570d;

    public bi(byte b3, boolean z2) {
        this.f22568b = b3;
        this.f22567a = false;
        this.f22569c = null;
        this.f22570d = z2;
    }

    public boolean a() {
        return this.f22567a;
    }

    public String b() {
        return this.f22569c;
    }

    public boolean c() {
        return this.f22568b == 12;
    }

    public boolean d() {
        byte b3 = this.f22568b;
        return b3 == 15 || b3 == 13 || b3 == 14;
    }

    public boolean e() {
        return this.f22570d;
    }

    public bi(byte b3) {
        this(b3, false);
    }

    public bi(byte b3, String str) {
        this.f22568b = b3;
        this.f22567a = true;
        this.f22569c = str;
        this.f22570d = false;
    }
}
