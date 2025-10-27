package j;

/* loaded from: classes8.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    public int f27512a;

    /* renamed from: b, reason: collision with root package name */
    public int f27513b;

    /* renamed from: c, reason: collision with root package name */
    public int f27514c;

    /* renamed from: d, reason: collision with root package name */
    public int f27515d;

    /* renamed from: e, reason: collision with root package name */
    public int f27516e;

    /* renamed from: f, reason: collision with root package name */
    public int f27517f;

    public h(int i2, int i3, int i4, int i5, int i6, int i7) {
        this.f27512a = i2;
        this.f27513b = i3;
        this.f27514c = i4;
        this.f27517f = i5;
        this.f27516e = i7;
        this.f27515d = i6;
    }

    public int a() {
        return this.f27514c;
    }

    public int b() {
        return this.f27513b;
    }

    public int c() {
        return this.f27516e;
    }

    public int d() {
        return this.f27517f;
    }

    public int e() {
        return this.f27515d;
    }

    public int f() {
        return this.f27512a;
    }

    public String toString() {
        return "VideoConfig{m_width=" + this.f27512a + ", m_height=" + this.f27513b + ", m_fps=" + this.f27514c + ", m_startBitrate=" + this.f27515d + ", m_maxBitrate=" + this.f27516e + ", m_minBitrate=" + this.f27517f + '}';
    }

    public void a(int i2) {
        this.f27514c = i2;
    }

    public void b(int i2) {
        this.f27513b = i2;
    }

    public void c(int i2) {
        this.f27516e = i2;
    }

    public void d(int i2) {
        this.f27517f = i2;
    }

    public void e(int i2) {
        this.f27515d = i2;
    }

    public void f(int i2) {
        this.f27512a = i2;
    }

    public h(h hVar) {
        this.f27512a = hVar.f27512a;
        this.f27513b = hVar.f27513b;
        this.f27514c = hVar.f27514c;
        this.f27517f = hVar.f27517f;
        this.f27516e = hVar.f27516e;
        this.f27515d = hVar.f27515d;
    }

    public h() {
        this.f27512a = 320;
        this.f27513b = 240;
        this.f27514c = 20;
        this.f27517f = 100;
        this.f27516e = 300;
        this.f27515d = 200;
    }
}
