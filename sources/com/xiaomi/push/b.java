package com.xiaomi.push;

import com.google.common.base.Ascii;
import java.io.InputStream;
import java.util.Vector;

/* loaded from: classes6.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private int f24622a;

    /* renamed from: a, reason: collision with other field name */
    private final InputStream f203a;

    /* renamed from: a, reason: collision with other field name */
    private final byte[] f204a;

    /* renamed from: b, reason: collision with root package name */
    private int f24623b;

    /* renamed from: c, reason: collision with root package name */
    private int f24624c;

    /* renamed from: d, reason: collision with root package name */
    private int f24625d;

    /* renamed from: e, reason: collision with root package name */
    private int f24626e;

    /* renamed from: f, reason: collision with root package name */
    private int f24627f;

    /* renamed from: g, reason: collision with root package name */
    private int f24628g;

    /* renamed from: h, reason: collision with root package name */
    private int f24629h;

    /* renamed from: i, reason: collision with root package name */
    private int f24630i;

    private b(InputStream inputStream) {
        this.f24627f = Integer.MAX_VALUE;
        this.f24629h = 64;
        this.f24630i = 67108864;
        this.f204a = new byte[4096];
        this.f24622a = 0;
        this.f24624c = 0;
        this.f203a = inputStream;
    }

    private b(byte[] bArr, int i2, int i3) {
        this.f24627f = Integer.MAX_VALUE;
        this.f24629h = 64;
        this.f24630i = 67108864;
        this.f204a = bArr;
        this.f24622a = i3 + i2;
        this.f24624c = i2;
        this.f203a = null;
    }

    public static b a(InputStream inputStream) {
        return new b(inputStream);
    }

    public static b a(byte[] bArr, int i2, int i3) {
        return new b(bArr, i2, i3);
    }

    private boolean a(boolean z2) throws d {
        int i2 = this.f24624c;
        int i3 = this.f24622a;
        if (i2 < i3) {
            throw new IllegalStateException("refillBuffer() called when buffer wasn't empty.");
        }
        int i4 = this.f24626e;
        if (i4 + i3 == this.f24627f) {
            if (z2) {
                throw d.a();
            }
            return false;
        }
        this.f24626e = i4 + i3;
        this.f24624c = 0;
        InputStream inputStream = this.f203a;
        int i5 = inputStream == null ? -1 : inputStream.read(this.f204a);
        this.f24622a = i5;
        if (i5 == 0 || i5 < -1) {
            throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + this.f24622a + "\nThe InputStream implementation is buggy.");
        }
        if (i5 == -1) {
            this.f24622a = 0;
            if (z2) {
                throw d.a();
            }
            return false;
        }
        b();
        int i6 = this.f24626e + this.f24622a + this.f24623b;
        if (i6 > this.f24630i || i6 < 0) {
            throw d.h();
        }
        return true;
    }

    private void b() {
        int i2 = this.f24622a + this.f24623b;
        this.f24622a = i2;
        int i3 = this.f24626e + i2;
        int i4 = this.f24627f;
        if (i3 <= i4) {
            this.f24623b = 0;
            return;
        }
        int i5 = i3 - i4;
        this.f24623b = i5;
        this.f24622a = i2 - i5;
    }

    public byte a() throws d {
        if (this.f24624c == this.f24622a) {
            a(true);
        }
        byte[] bArr = this.f204a;
        int i2 = this.f24624c;
        this.f24624c = i2 + 1;
        return bArr[i2];
    }

    /* renamed from: a, reason: collision with other method in class */
    public int m213a() throws d {
        if (m224b()) {
            this.f24625d = 0;
            return 0;
        }
        int iD = d();
        this.f24625d = iD;
        if (iD != 0) {
            return iD;
        }
        throw d.d();
    }

    public int a(int i2) throws d {
        if (i2 < 0) {
            throw d.b();
        }
        int i3 = i2 + this.f24626e + this.f24624c;
        int i4 = this.f24627f;
        if (i3 > i4) {
            throw d.a();
        }
        this.f24627f = i3;
        b();
        return i4;
    }

    /* renamed from: a, reason: collision with other method in class */
    public long m214a() {
        return m225c();
    }

    /* renamed from: a, reason: collision with other method in class */
    public a m215a() throws d {
        int iD = d();
        int i2 = this.f24622a;
        int i3 = this.f24624c;
        if (iD > i2 - i3 || iD <= 0) {
            return a.a(m221a(iD));
        }
        a aVarA = a.a(this.f204a, i3, iD);
        this.f24624c += iD;
        return aVarA;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m216a() throws d {
        int iD = d();
        int i2 = this.f24622a;
        int i3 = this.f24624c;
        if (iD > i2 - i3 || iD <= 0) {
            return new String(m221a(iD), "UTF-8");
        }
        String str = new String(this.f204a, i3, iD, "UTF-8");
        this.f24624c += iD;
        return str;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m217a() throws d {
        int iM213a;
        do {
            iM213a = m213a();
            if (iM213a == 0) {
                return;
            }
        } while (m220a(iM213a));
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m218a(int i2) throws d {
        if (this.f24625d != i2) {
            throw d.e();
        }
    }

    public void a(e eVar) throws d {
        int iD = d();
        if (this.f24628g >= this.f24629h) {
            throw d.g();
        }
        int iA = a(iD);
        this.f24628g++;
        eVar.a(this);
        m218a(0);
        this.f24628g--;
        b(iA);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m219a() {
        return d() != 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m220a(int i2) throws d {
        int iA = f.a(i2);
        if (iA == 0) {
            m222b();
            return true;
        }
        if (iA == 1) {
            m226d();
            return true;
        }
        if (iA == 2) {
            c(d());
            return true;
        }
        if (iA == 3) {
            m217a();
            m218a(f.a(f.b(i2), 4));
            return true;
        }
        if (iA == 4) {
            return false;
        }
        if (iA != 5) {
            throw d.f();
        }
        e();
        return true;
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m221a(int i2) throws d {
        if (i2 < 0) {
            throw d.b();
        }
        int i3 = this.f24626e;
        int i4 = this.f24624c;
        int i5 = i3 + i4 + i2;
        int i6 = this.f24627f;
        if (i5 > i6) {
            c((i6 - i3) - i4);
            throw d.a();
        }
        int i7 = this.f24622a;
        if (i2 <= i7 - i4) {
            byte[] bArr = new byte[i2];
            System.arraycopy(this.f204a, i4, bArr, 0, i2);
            this.f24624c += i2;
            return bArr;
        }
        if (i2 >= 4096) {
            this.f24626e = i3 + i7;
            this.f24624c = 0;
            this.f24622a = 0;
            int length = i7 - i4;
            int i8 = i2 - length;
            Vector vector = new Vector();
            while (i8 > 0) {
                int iMin = Math.min(i8, 4096);
                byte[] bArr2 = new byte[iMin];
                int i9 = 0;
                while (i9 < iMin) {
                    InputStream inputStream = this.f203a;
                    int i10 = inputStream == null ? -1 : inputStream.read(bArr2, i9, iMin - i9);
                    if (i10 == -1) {
                        throw d.a();
                    }
                    this.f24626e += i10;
                    i9 += i10;
                }
                i8 -= iMin;
                vector.addElement(bArr2);
            }
            byte[] bArr3 = new byte[i2];
            System.arraycopy(this.f204a, i4, bArr3, 0, length);
            for (int i11 = 0; i11 < vector.size(); i11++) {
                byte[] bArr4 = (byte[]) vector.elementAt(i11);
                System.arraycopy(bArr4, 0, bArr3, length, bArr4.length);
                length += bArr4.length;
            }
            return bArr3;
        }
        byte[] bArr5 = new byte[i2];
        int i12 = i7 - i4;
        System.arraycopy(this.f204a, i4, bArr5, 0, i12);
        this.f24624c = this.f24622a;
        while (true) {
            a(true);
            int i13 = i2 - i12;
            int i14 = this.f24622a;
            if (i13 <= i14) {
                System.arraycopy(this.f204a, 0, bArr5, i12, i13);
                this.f24624c = i13;
                return bArr5;
            }
            System.arraycopy(this.f204a, 0, bArr5, i12, i14);
            int i15 = this.f24622a;
            i12 += i15;
            this.f24624c = i15;
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public int m222b() {
        return d();
    }

    /* renamed from: b, reason: collision with other method in class */
    public long m223b() {
        return m225c();
    }

    public void b(int i2) {
        this.f24627f = i2;
        b();
    }

    /* renamed from: b, reason: collision with other method in class */
    public boolean m224b() {
        return this.f24624c == this.f24622a && !a(false);
    }

    public int c() {
        return d();
    }

    /* renamed from: c, reason: collision with other method in class */
    public long m225c() throws d {
        long j2 = 0;
        for (int i2 = 0; i2 < 64; i2 += 7) {
            j2 |= (r3 & 127) << i2;
            if ((a() & 128) == 0) {
                return j2;
            }
        }
        throw d.c();
    }

    public void c(int i2) throws d {
        if (i2 < 0) {
            throw d.b();
        }
        int i3 = this.f24626e;
        int i4 = this.f24624c;
        int i5 = i3 + i4 + i2;
        int i6 = this.f24627f;
        if (i5 > i6) {
            c((i6 - i3) - i4);
            throw d.a();
        }
        int i7 = this.f24622a;
        if (i2 <= i7 - i4) {
            this.f24624c = i4 + i2;
            return;
        }
        int i8 = i7 - i4;
        this.f24626e = i3 + i7;
        this.f24624c = 0;
        this.f24622a = 0;
        while (i8 < i2) {
            InputStream inputStream = this.f203a;
            int iSkip = inputStream == null ? -1 : (int) inputStream.skip(i2 - i8);
            if (iSkip <= 0) {
                throw d.a();
            }
            i8 += iSkip;
            this.f24626e += iSkip;
        }
    }

    public int d() throws d {
        int i2;
        byte bA = a();
        if (bA >= 0) {
            return bA;
        }
        int i3 = bA & 127;
        byte bA2 = a();
        if (bA2 >= 0) {
            i2 = bA2 << 7;
        } else {
            i3 |= (bA2 & 127) << 7;
            byte bA3 = a();
            if (bA3 >= 0) {
                i2 = bA3 << 14;
            } else {
                i3 |= (bA3 & 127) << 14;
                byte bA4 = a();
                if (bA4 < 0) {
                    int i4 = i3 | ((bA4 & 127) << 21);
                    byte bA5 = a();
                    int i5 = i4 | (bA5 << Ascii.FS);
                    if (bA5 >= 0) {
                        return i5;
                    }
                    for (int i6 = 0; i6 < 5; i6++) {
                        if (a() >= 0) {
                            return i5;
                        }
                    }
                    throw d.c();
                }
                i2 = bA4 << 21;
            }
        }
        return i3 | i2;
    }

    /* renamed from: d, reason: collision with other method in class */
    public long m226d() throws d {
        return ((a() & 255) << 8) | (a() & 255) | ((a() & 255) << 16) | ((a() & 255) << 24) | ((a() & 255) << 32) | ((a() & 255) << 40) | ((a() & 255) << 48) | ((a() & 255) << 56);
    }

    public int e() throws d {
        return (a() & 255) | ((a() & 255) << 8) | ((a() & 255) << 16) | ((a() & 255) << 24);
    }
}
