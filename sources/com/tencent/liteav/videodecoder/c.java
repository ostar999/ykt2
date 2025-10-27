package com.tencent.liteav.videodecoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes6.dex */
class c {

    /* renamed from: b, reason: collision with root package name */
    int f20137b;

    /* renamed from: c, reason: collision with root package name */
    private InputStream f20138c;

    /* renamed from: d, reason: collision with root package name */
    private int f20139d;

    /* renamed from: e, reason: collision with root package name */
    private int f20140e;

    /* renamed from: f, reason: collision with root package name */
    private final OutputStream f20141f;

    /* renamed from: h, reason: collision with root package name */
    private int f20143h;

    /* renamed from: a, reason: collision with root package name */
    protected a f20136a = new a(50);

    /* renamed from: g, reason: collision with root package name */
    private int[] f20142g = new int[8];

    public c(InputStream inputStream, OutputStream outputStream) throws IOException {
        this.f20138c = inputStream;
        this.f20141f = outputStream;
        this.f20139d = inputStream.read();
        this.f20140e = inputStream.read();
    }

    private void a(String str, String str2) {
    }

    private void d() throws IOException {
        this.f20139d = this.f20140e;
        this.f20140e = this.f20138c.read();
        this.f20137b = 0;
    }

    private int e() throws IOException {
        int i2 = 0;
        while (b(true) == 0) {
            i2++;
        }
        if (i2 <= 0) {
            return 0;
        }
        return (int) (((1 << i2) - 1) + a(i2));
    }

    private void f() throws IOException {
        int i2 = 0;
        while (b(true) == 0) {
            i2++;
        }
        if (i2 > 0) {
            b(i2);
        }
    }

    private void g() throws IOException {
        int[] iArr = this.f20142g;
        this.f20141f.write(iArr[7] | (iArr[0] << 7) | (iArr[1] << 6) | (iArr[2] << 5) | (iArr[3] << 4) | (iArr[4] << 3) | (iArr[5] << 2) | (iArr[6] << 1));
    }

    public boolean a(boolean z2) throws IOException {
        return b(z2) == 1;
    }

    public int b(boolean z2) throws IOException {
        if (this.f20137b == 8) {
            d();
            if (this.f20139d == -1) {
                return -1;
            }
        }
        int i2 = this.f20139d;
        int i3 = this.f20137b;
        int i4 = (i2 >> (7 - i3)) & 1;
        this.f20137b = i3 + 1;
        if (z2 && this.f20141f != null) {
            d(i4);
        }
        return i4;
    }

    public int c(boolean z2) throws IOException {
        int i2 = 0;
        while (b(z2) == 0) {
            i2++;
        }
        if (i2 <= 0) {
            return 0;
        }
        return (int) (((1 << i2) - 1) + a(i2, z2));
    }

    public long a(int i2, boolean z2) throws IOException {
        if (i2 > 64) {
            throw new IllegalArgumentException("Can not readByte more then 64 bit");
        }
        long jB = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            jB = (jB << 1) | b(z2);
        }
        return jB;
    }

    public int c(String str) throws IOException {
        int iE = e();
        int i2 = ((iE >> 1) + (iE & 1)) * ((r1 << 1) - 1);
        a(str, String.valueOf(i2));
        return i2;
    }

    public boolean e(String str) throws IOException {
        boolean zA = a(false);
        a(str, zA ? "1" : "0");
        return zA;
    }

    public long a(int i2) throws IOException {
        if (i2 > 64) {
            throw new IllegalArgumentException("Can not readByte more then 64 bit");
        }
        long jB = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            jB = (jB << 1) | b(true);
        }
        return jB;
    }

    public boolean d(String str) throws IOException {
        boolean zA = a(true);
        a(str, zA ? "1" : "0");
        return zA;
    }

    public void c(int i2) throws IOException {
        int[] iArr = new int[i2];
        int iC = 8;
        int i3 = 8;
        for (int i4 = 0; i4 < i2; i4++) {
            if (iC != 0) {
                iC = ((c("deltaScale") + i3) + 256) % 256;
            }
            if (iC != 0) {
                i3 = iC;
            }
            iArr[i4] = i3;
        }
    }

    public void e(int i2) throws IOException {
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (i4 >= 15) {
                break;
            }
            int i6 = (1 << i4) + i5;
            if (i2 < i6) {
                i3 = i4;
                break;
            } else {
                i4++;
                i5 = i6;
            }
        }
        a(0L, i3);
        d(1);
        a(i2 - i5, i3);
    }

    public long a(int i2, String str) throws IOException {
        long jA = a(i2);
        a(str, String.valueOf(jA));
        return jA;
    }

    public void d(int i2) throws IOException {
        if (this.f20143h == 8) {
            this.f20143h = 0;
            g();
        }
        int[] iArr = this.f20142g;
        int i3 = this.f20143h;
        this.f20143h = i3 + 1;
        iArr[i3] = i2;
    }

    public int a(String str) throws IOException {
        int iE = e();
        a(str, String.valueOf(iE));
        return iE;
    }

    public void b(int i2) throws IOException {
        if (i2 > 64) {
            throw new IllegalArgumentException("Can not skip more then 64 bit");
        }
        for (int i3 = 0; i3 < i2; i3++) {
            b(true);
        }
    }

    public void c(int i2, String str) throws IOException {
        e(i2);
    }

    public void a() throws IOException {
        for (int i2 = this.f20143h; i2 < 8; i2++) {
            this.f20142g[i2] = 0;
        }
        this.f20143h = 0;
        g();
    }

    public void b(int i2, String str) throws IOException {
        b(i2);
        a(str, "skip NBits");
    }

    public void c() throws IOException {
        d(1);
        b();
        a();
    }

    public void b(String str) throws IOException {
        f();
        a(str, "skip UE");
    }

    public void a(long j2, int i2) throws IOException {
        for (int i3 = 0; i3 < i2; i3++) {
            d(((int) (j2 >> ((i2 - i3) - 1))) & 1);
        }
    }

    public void b() throws IOException {
        a(0L, 8 - this.f20143h);
    }

    public void a(boolean z2, String str) throws IOException {
        d(z2 ? 1 : 0);
    }
}
