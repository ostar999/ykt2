package io.crossbar.autobahn.websocket.types;

/* loaded from: classes8.dex */
public class WebSocketOptions {

    /* renamed from: a, reason: collision with root package name */
    public int f27257a;

    /* renamed from: b, reason: collision with root package name */
    public int f27258b;

    /* renamed from: c, reason: collision with root package name */
    public boolean f27259c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f27260d;

    /* renamed from: e, reason: collision with root package name */
    public int f27261e;

    /* renamed from: f, reason: collision with root package name */
    public int f27262f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f27263g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f27264h;

    /* renamed from: i, reason: collision with root package name */
    public int f27265i;

    /* renamed from: j, reason: collision with root package name */
    public String[] f27266j;

    /* renamed from: k, reason: collision with root package name */
    public int f27267k;

    /* renamed from: l, reason: collision with root package name */
    public int f27268l;

    public WebSocketOptions() {
        this.f27257a = 131072;
        this.f27258b = 131072;
        this.f27259c = false;
        this.f27260d = true;
        this.f27261e = 0;
        this.f27262f = 6000;
        this.f27263g = true;
        this.f27264h = true;
        this.f27265i = 0;
        this.f27266j = null;
        this.f27267k = 10;
        this.f27268l = 5;
    }

    public void a(boolean z2) {
        this.f27264h = z2;
    }

    public void b(boolean z2) {
        this.f27259c = z2;
    }

    public void c(int i2) {
        if (i2 > 0) {
            this.f27257a = i2;
            if (this.f27258b < i2) {
                this.f27258b = i2;
            }
        }
    }

    public int d() {
        return this.f27257a;
    }

    public int e() {
        return this.f27258b;
    }

    public boolean f() {
        return this.f27259c;
    }

    public void g(int i2) {
        if (i2 >= 0) {
            this.f27261e = i2;
        }
    }

    public int h() {
        return this.f27262f;
    }

    public int i() {
        return this.f27261e;
    }

    public String[] j() {
        return this.f27266j;
    }

    public boolean k() {
        return this.f27260d;
    }

    public boolean l() {
        return this.f27263g;
    }

    public void a(String[] strArr) {
        this.f27266j = strArr;
    }

    public void b(int i2) {
        this.f27268l = i2;
    }

    public void d(int i2) {
        if (i2 > 0) {
            this.f27258b = i2;
            if (i2 < this.f27257a) {
                this.f27257a = i2;
            }
        }
    }

    public void e(int i2) {
        this.f27265i = i2;
    }

    public void f(int i2) {
        if (i2 >= 0) {
            this.f27262f = i2;
        }
    }

    public int g() {
        return this.f27265i;
    }

    public void a(int i2) {
        this.f27267k = i2;
    }

    public int b() {
        return this.f27268l;
    }

    public int a() {
        return this.f27267k;
    }

    public void c(boolean z2) {
        this.f27260d = z2;
    }

    public boolean c() {
        return this.f27264h;
    }

    public void d(boolean z2) {
        this.f27263g = z2;
    }

    public WebSocketOptions(WebSocketOptions webSocketOptions) {
        this.f27257a = webSocketOptions.f27257a;
        this.f27258b = webSocketOptions.f27258b;
        this.f27259c = webSocketOptions.f27259c;
        this.f27260d = webSocketOptions.f27260d;
        this.f27261e = webSocketOptions.f27261e;
        this.f27262f = webSocketOptions.f27262f;
        this.f27263g = webSocketOptions.f27263g;
        this.f27264h = webSocketOptions.f27264h;
        this.f27265i = webSocketOptions.f27265i;
        this.f27266j = webSocketOptions.f27266j;
        this.f27267k = webSocketOptions.f27267k;
        this.f27268l = webSocketOptions.f27268l;
    }
}
