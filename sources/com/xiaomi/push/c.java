package com.xiaomi.push;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/* loaded from: classes6.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    private final int f24658a;

    /* renamed from: a, reason: collision with other field name */
    private final OutputStream f240a;

    /* renamed from: a, reason: collision with other field name */
    private final byte[] f241a;

    /* renamed from: b, reason: collision with root package name */
    private int f24659b;

    public static class a extends IOException {
        public a() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }
    }

    private c(OutputStream outputStream, byte[] bArr) {
        this.f240a = outputStream;
        this.f241a = bArr;
        this.f24659b = 0;
        this.f24658a = bArr.length;
    }

    private c(byte[] bArr, int i2, int i3) {
        this.f240a = null;
        this.f241a = bArr;
        this.f24659b = i2;
        this.f24658a = i2 + i3;
    }

    public static int a(int i2) {
        if (i2 >= 0) {
            return d(i2);
        }
        return 10;
    }

    public static int a(int i2, int i3) {
        return c(i2) + a(i3);
    }

    public static int a(int i2, long j2) {
        return c(i2) + a(j2);
    }

    public static int a(int i2, com.xiaomi.push.a aVar) {
        return c(i2) + a(aVar);
    }

    public static int a(int i2, e eVar) {
        return c(i2) + a(eVar);
    }

    public static int a(int i2, String str) {
        return c(i2) + a(str);
    }

    public static int a(int i2, boolean z2) {
        return c(i2) + a(z2);
    }

    public static int a(long j2) {
        return c(j2);
    }

    public static int a(com.xiaomi.push.a aVar) {
        return d(aVar.a()) + aVar.a();
    }

    public static int a(e eVar) {
        int iB = eVar.b();
        return d(iB) + iB;
    }

    public static int a(String str) throws UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            return d(bytes.length) + bytes.length;
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException("UTF-8 not supported.");
        }
    }

    public static int a(boolean z2) {
        return 1;
    }

    public static c a(OutputStream outputStream) {
        return a(outputStream, 4096);
    }

    public static c a(OutputStream outputStream, int i2) {
        return new c(outputStream, new byte[i2]);
    }

    public static c a(byte[] bArr, int i2, int i3) {
        return new c(bArr, i2, i3);
    }

    public static int b(int i2) {
        return d(i2);
    }

    public static int b(int i2, int i3) {
        return c(i2) + b(i3);
    }

    public static int b(int i2, long j2) {
        return c(i2) + b(j2);
    }

    public static int b(long j2) {
        return c(j2);
    }

    public static int c(int i2) {
        return d(f.a(i2, 0));
    }

    public static int c(long j2) {
        if (((-128) & j2) == 0) {
            return 1;
        }
        if (((-16384) & j2) == 0) {
            return 2;
        }
        if (((-2097152) & j2) == 0) {
            return 3;
        }
        if (((-268435456) & j2) == 0) {
            return 4;
        }
        if (((-34359738368L) & j2) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j2) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j2) == 0) {
            return 7;
        }
        if (((-72057594037927936L) & j2) == 0) {
            return 8;
        }
        return (j2 & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    private void c() throws IOException {
        OutputStream outputStream = this.f240a;
        if (outputStream == null) {
            throw new a();
        }
        outputStream.write(this.f241a, 0, this.f24659b);
        this.f24659b = 0;
    }

    public static int d(int i2) {
        if ((i2 & (-128)) == 0) {
            return 1;
        }
        if ((i2 & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i2) == 0) {
            return 3;
        }
        return (i2 & (-268435456)) == 0 ? 4 : 5;
    }

    public int a() {
        if (this.f240a == null) {
            return this.f24658a - this.f24659b;
        }
        throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array.");
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m253a() throws IOException {
        if (this.f240a != null) {
            c();
        }
    }

    public void a(byte b3) throws IOException {
        if (this.f24659b == this.f24658a) {
            c();
        }
        byte[] bArr = this.f241a;
        int i2 = this.f24659b;
        this.f24659b = i2 + 1;
        bArr[i2] = b3;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m254a(int i2) throws IOException {
        if (i2 >= 0) {
            m273d(i2);
        } else {
            m272c(i2);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m255a(int i2, int i3) throws IOException {
        c(i2, 0);
        m254a(i3);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m256a(int i2, long j2) throws IOException {
        c(i2, 0);
        m261a(j2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m257a(int i2, com.xiaomi.push.a aVar) throws IOException {
        c(i2, 2);
        m262a(aVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m258a(int i2, e eVar) throws IOException {
        c(i2, 2);
        m263a(eVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m259a(int i2, String str) throws IOException {
        c(i2, 2);
        m264a(str);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m260a(int i2, boolean z2) throws IOException {
        c(i2, 0);
        m265a(z2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m261a(long j2) throws IOException {
        m272c(j2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m262a(com.xiaomi.push.a aVar) throws IOException {
        byte[] bArrM191a = aVar.m191a();
        m273d(bArrM191a.length);
        a(bArrM191a);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m263a(e eVar) throws IOException {
        m273d(eVar.a());
        eVar.a(this);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m264a(String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        m273d(bytes.length);
        a(bytes);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m265a(boolean z2) throws IOException {
        m271c(z2 ? 1 : 0);
    }

    public void a(byte[] bArr) throws IOException {
        m266a(bArr, 0, bArr.length);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m266a(byte[] bArr, int i2, int i3) throws IOException {
        int i4 = this.f24658a;
        int i5 = this.f24659b;
        if (i4 - i5 >= i3) {
            System.arraycopy(bArr, i2, this.f241a, i5, i3);
            this.f24659b += i3;
            return;
        }
        int i6 = i4 - i5;
        System.arraycopy(bArr, i2, this.f241a, i5, i6);
        int i7 = i2 + i6;
        int i8 = i3 - i6;
        this.f24659b = this.f24658a;
        c();
        if (i8 > this.f24658a) {
            this.f240a.write(bArr, i7, i8);
        } else {
            System.arraycopy(bArr, i7, this.f241a, 0, i8);
            this.f24659b = i8;
        }
    }

    public void b() {
        if (a() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m267b(int i2) throws IOException {
        m273d(i2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m268b(int i2, int i3) throws IOException {
        c(i2, 0);
        m267b(i3);
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m269b(int i2, long j2) throws IOException {
        c(i2, 0);
        m270b(j2);
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m270b(long j2) throws IOException {
        m272c(j2);
    }

    /* renamed from: c, reason: collision with other method in class */
    public void m271c(int i2) throws IOException {
        a((byte) i2);
    }

    public void c(int i2, int i3) throws IOException {
        m273d(f.a(i2, i3));
    }

    /* renamed from: c, reason: collision with other method in class */
    public void m272c(long j2) throws IOException {
        while (((-128) & j2) != 0) {
            m271c((((int) j2) & 127) | 128);
            j2 >>>= 7;
        }
        m271c((int) j2);
    }

    /* renamed from: d, reason: collision with other method in class */
    public void m273d(int i2) throws IOException {
        while ((i2 & (-128)) != 0) {
            m271c((i2 & 127) | 128);
            i2 >>>= 7;
        }
        m271c(i2);
    }
}
