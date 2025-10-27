package com.unity3d.player;

/* loaded from: classes6.dex */
final class o {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f24169a = false;

    /* renamed from: b, reason: collision with root package name */
    private boolean f24170b = false;

    /* renamed from: c, reason: collision with root package name */
    private boolean f24171c = false;

    /* renamed from: d, reason: collision with root package name */
    private boolean f24172d = true;

    /* renamed from: e, reason: collision with root package name */
    private boolean f24173e = false;

    public static void a() {
        f24169a = true;
    }

    public static void b() {
        f24169a = false;
    }

    public static boolean c() {
        return f24169a;
    }

    public final void a(boolean z2) {
        this.f24170b = z2;
    }

    public final void b(boolean z2) {
        this.f24172d = z2;
    }

    public final void c(boolean z2) {
        this.f24173e = z2;
    }

    public final void d(boolean z2) {
        this.f24171c = z2;
    }

    public final boolean d() {
        return this.f24172d;
    }

    public final boolean e() {
        return this.f24173e;
    }

    public final boolean e(boolean z2) {
        if (f24169a) {
            return ((!z2 && !this.f24170b) || this.f24172d || this.f24171c) ? false : true;
        }
        return false;
    }

    public final boolean f() {
        return this.f24171c;
    }

    public final String toString() {
        return super.toString();
    }
}
