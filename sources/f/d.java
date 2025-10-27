package f;

import cn.hutool.core.text.CharPool;

/* loaded from: classes8.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public String f26874a = "";

    /* renamed from: b, reason: collision with root package name */
    public String f26875b = "";

    /* renamed from: c, reason: collision with root package name */
    public boolean f26876c = false;

    /* renamed from: d, reason: collision with root package name */
    public boolean f26877d = false;

    /* renamed from: e, reason: collision with root package name */
    public boolean f26878e = false;

    /* renamed from: f, reason: collision with root package name */
    public boolean f26879f = false;

    /* renamed from: g, reason: collision with root package name */
    public boolean f26880g = false;

    /* renamed from: h, reason: collision with root package name */
    public int f26881h = 0;

    /* renamed from: i, reason: collision with root package name */
    public int f26882i = 0;

    public void a(String str) {
        this.f26875b = str;
    }

    public void b(String str) {
        this.f26874a = str;
    }

    public void c(boolean z2) {
        this.f26876c = z2;
    }

    public String d() {
        return this.f26874a;
    }

    public boolean e() {
        return this.f26877d;
    }

    public boolean f() {
        return this.f26878e;
    }

    public boolean g() {
        return this.f26876c;
    }

    public boolean h() {
        return this.f26879f;
    }

    public boolean i() {
        return this.f26880g;
    }

    public String toString() {
        return "LogicStreamInfo{mUid='" + this.f26874a + CharPool.SINGLE_QUOTE + ", mStreamId='" + this.f26875b + CharPool.SINGLE_QUOTE + ", mEnableVideo=" + this.f26876c + ", mEnableAudio=" + this.f26877d + ", mEnableData=" + this.f26878e + ", mMuteAudio=" + this.f26879f + ", mMuteVideo=" + this.f26880g + ", mStreamType=" + this.f26881h + ", mMediaType=" + this.f26882i + '}';
    }

    public void a(boolean z2) {
        this.f26877d = z2;
    }

    public String b() {
        return this.f26875b;
    }

    public int c() {
        return this.f26881h;
    }

    public void d(boolean z2) {
        this.f26879f = z2;
    }

    public void e(boolean z2) {
        this.f26880g = z2;
    }

    public int a() {
        return this.f26882i;
    }

    public void b(boolean z2) {
        this.f26878e = z2;
    }

    public void a(int i2) {
        this.f26882i = i2;
    }

    public void b(int i2) {
        this.f26881h = i2;
    }
}
