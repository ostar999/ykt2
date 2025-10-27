package j;

import cn.hutool.core.text.CharPool;

/* loaded from: classes8.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    public String f27496a;

    /* renamed from: b, reason: collision with root package name */
    public int f27497b;

    /* renamed from: c, reason: collision with root package name */
    public int f27498c;

    /* renamed from: d, reason: collision with root package name */
    public int f27499d;

    /* renamed from: e, reason: collision with root package name */
    public int f27500e;

    public int a() {
        return this.f27499d;
    }

    public int b() {
        return this.f27500e;
    }

    public int c() {
        return this.f27498c;
    }

    public void d(int i2) {
        this.f27497b = i2;
    }

    public int e() {
        return this.f27497b;
    }

    public String toString() {
        return "PeerNetQuality{mStreamId='" + this.f27496a + CharPool.SINGLE_QUOTE + ", mStreamType=" + this.f27497b + ", mRtt=" + this.f27498c + ", mDelay=" + this.f27499d + ", mLost=" + this.f27500e + '}';
    }

    public void a(int i2) {
        this.f27499d = i2;
    }

    public void b(int i2) {
        this.f27500e = i2;
    }

    public void c(int i2) {
        this.f27498c = i2;
    }

    public String d() {
        return this.f27496a;
    }

    public void a(String str) {
        this.f27496a = str;
    }

    public boolean a(f fVar) {
        if (fVar != null) {
            if (this.f27497b == 1) {
                if (fVar.e() < this.f27500e || fVar.f() < this.f27498c) {
                    return true;
                }
            } else if (fVar.b() < this.f27500e || fVar.c() < this.f27499d) {
                return true;
            }
        }
        return false;
    }
}
