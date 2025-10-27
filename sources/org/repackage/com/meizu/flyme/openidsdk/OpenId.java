package org.repackage.com.meizu.flyme.openidsdk;

/* loaded from: classes9.dex */
class OpenId {

    /* renamed from: a, reason: collision with root package name */
    long f27994a;

    /* renamed from: b, reason: collision with root package name */
    String f27995b;

    /* renamed from: c, reason: collision with root package name */
    String f27996c;

    /* renamed from: d, reason: collision with root package name */
    int f27997d;

    public OpenId(String str) {
        this.f27996c = str;
    }

    public void a(int i2) {
        this.f27997d = i2;
    }

    public void a(long j2) {
        this.f27994a = j2;
    }

    public void a(String str) {
        this.f27995b = str;
    }

    public boolean a() {
        return this.f27994a > System.currentTimeMillis();
    }

    public void b() {
        this.f27994a = 0L;
    }
}
