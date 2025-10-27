package com.umeng.analytics.pro;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class bo extends bu {

    /* renamed from: d, reason: collision with root package name */
    private static final bz f22594d = new bz("");

    /* renamed from: e, reason: collision with root package name */
    private static final bp f22595e = new bp("", (byte) 0, 0);

    /* renamed from: f, reason: collision with root package name */
    private static final byte[] f22596f = {0, 0, 1, 3, 7, 0, 4, 0, 5, 0, 6, 8, 12, 11, 10, 9};

    /* renamed from: h, reason: collision with root package name */
    private static final byte f22597h = -126;

    /* renamed from: i, reason: collision with root package name */
    private static final byte f22598i = 1;

    /* renamed from: j, reason: collision with root package name */
    private static final byte f22599j = 31;

    /* renamed from: k, reason: collision with root package name */
    private static final byte f22600k = -32;

    /* renamed from: l, reason: collision with root package name */
    private static final int f22601l = 5;

    /* renamed from: a, reason: collision with root package name */
    byte[] f22602a;

    /* renamed from: b, reason: collision with root package name */
    byte[] f22603b;

    /* renamed from: c, reason: collision with root package name */
    byte[] f22604c;

    /* renamed from: m, reason: collision with root package name */
    private at f22605m;

    /* renamed from: n, reason: collision with root package name */
    private short f22606n;

    /* renamed from: o, reason: collision with root package name */
    private bp f22607o;

    /* renamed from: p, reason: collision with root package name */
    private Boolean f22608p;

    /* renamed from: q, reason: collision with root package name */
    private final long f22609q;

    /* renamed from: r, reason: collision with root package name */
    private byte[] f22610r;

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public static final byte f22612a = 1;

        /* renamed from: b, reason: collision with root package name */
        public static final byte f22613b = 2;

        /* renamed from: c, reason: collision with root package name */
        public static final byte f22614c = 3;

        /* renamed from: d, reason: collision with root package name */
        public static final byte f22615d = 4;

        /* renamed from: e, reason: collision with root package name */
        public static final byte f22616e = 5;

        /* renamed from: f, reason: collision with root package name */
        public static final byte f22617f = 6;

        /* renamed from: g, reason: collision with root package name */
        public static final byte f22618g = 7;

        /* renamed from: h, reason: collision with root package name */
        public static final byte f22619h = 8;

        /* renamed from: i, reason: collision with root package name */
        public static final byte f22620i = 9;

        /* renamed from: j, reason: collision with root package name */
        public static final byte f22621j = 10;

        /* renamed from: k, reason: collision with root package name */
        public static final byte f22622k = 11;

        /* renamed from: l, reason: collision with root package name */
        public static final byte f22623l = 12;

        private b() {
        }
    }

    public bo(ci ciVar, long j2) {
        super(ciVar);
        this.f22605m = new at(15);
        this.f22606n = (short) 0;
        this.f22607o = null;
        this.f22608p = null;
        this.f22602a = new byte[5];
        this.f22603b = new byte[10];
        this.f22610r = new byte[1];
        this.f22604c = new byte[1];
        this.f22609q = j2;
    }

    private int E() throws bb {
        int i2 = 0;
        if (this.f22639g.h() >= 5) {
            byte[] bArrF = this.f22639g.f();
            int iG = this.f22639g.g();
            int i3 = 0;
            int i4 = 0;
            while (true) {
                byte b3 = bArrF[iG + i2];
                i3 |= (b3 & 127) << i4;
                if ((b3 & 128) != 128) {
                    this.f22639g.a(i2 + 1);
                    return i3;
                }
                i4 += 7;
                i2++;
            }
        } else {
            int i5 = 0;
            while (true) {
                byte bU = u();
                i2 |= (bU & 127) << i5;
                if ((bU & 128) != 128) {
                    return i2;
                }
                i5 += 7;
            }
        }
    }

    private long F() throws bb {
        int i2 = 0;
        long j2 = 0;
        if (this.f22639g.h() >= 10) {
            byte[] bArrF = this.f22639g.f();
            int iG = this.f22639g.g();
            long j3 = 0;
            int i3 = 0;
            while (true) {
                j3 |= (r7 & 127) << i3;
                if ((bArrF[iG + i2] & 128) != 128) {
                    this.f22639g.a(i2 + 1);
                    return j3;
                }
                i3 += 7;
                i2++;
            }
        } else {
            while (true) {
                j2 |= (r0 & 127) << i2;
                if ((u() & 128) != 128) {
                    return j2;
                }
                i2 += 7;
            }
        }
    }

    private int c(int i2) {
        return (i2 >> 31) ^ (i2 << 1);
    }

    private long c(long j2) {
        return (j2 >> 63) ^ (j2 << 1);
    }

    private boolean c(byte b3) {
        int i2 = b3 & 15;
        return i2 == 1 || i2 == 2;
    }

    private long d(long j2) {
        return (-(j2 & 1)) ^ (j2 >>> 1);
    }

    private byte[] e(int i2) throws bb {
        if (i2 == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i2];
        this.f22639g.d(bArr, 0, i2);
        return bArr;
    }

    private void f(int i2) throws bv {
        if (i2 < 0) {
            throw new bv("Negative length: " + i2);
        }
        long j2 = this.f22609q;
        if (j2 == -1 || i2 <= j2) {
            return;
        }
        throw new bv("Length exceeded max allowed: " + i2);
    }

    private int g(int i2) {
        return (-(i2 & 1)) ^ (i2 >>> 1);
    }

    @Override // com.umeng.analytics.pro.bu
    public ByteBuffer A() throws bb {
        int iE = E();
        f(iE);
        if (iE == 0) {
            return ByteBuffer.wrap(new byte[0]);
        }
        byte[] bArr = new byte[iE];
        this.f22639g.d(bArr, 0, iE);
        return ByteBuffer.wrap(bArr);
    }

    @Override // com.umeng.analytics.pro.bu
    public void B() {
        this.f22605m.c();
        this.f22606n = (short) 0;
    }

    @Override // com.umeng.analytics.pro.bu
    public void a() throws bb {
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(bs bsVar) throws bb {
        b(f22597h);
        d(((bsVar.f22633b << 5) & (-32)) | 1);
        b(bsVar.f22634c);
        a(bsVar.f22632a);
    }

    @Override // com.umeng.analytics.pro.bu
    public void b() throws bb {
        this.f22606n = this.f22605m.a();
    }

    @Override // com.umeng.analytics.pro.bu
    public void c() throws bb {
    }

    @Override // com.umeng.analytics.pro.bu
    public void d() throws bb {
        b((byte) 0);
    }

    @Override // com.umeng.analytics.pro.bu
    public void e() throws bb {
    }

    @Override // com.umeng.analytics.pro.bu
    public void f() throws bb {
    }

    @Override // com.umeng.analytics.pro.bu
    public void g() throws bb {
    }

    @Override // com.umeng.analytics.pro.bu
    public bs h() throws bb {
        byte bU = u();
        if (bU != -126) {
            throw new bv("Expected protocol id " + Integer.toHexString(-126) + " but got " + Integer.toHexString(bU));
        }
        byte bU2 = u();
        byte b3 = (byte) (bU2 & 31);
        if (b3 == 1) {
            return new bs(z(), (byte) ((bU2 >> 5) & 3), E());
        }
        throw new bv("Expected version 1 but got " + ((int) b3));
    }

    @Override // com.umeng.analytics.pro.bu
    public void i() throws bb {
    }

    @Override // com.umeng.analytics.pro.bu
    public bz j() throws bb {
        this.f22605m.a(this.f22606n);
        this.f22606n = (short) 0;
        return f22594d;
    }

    @Override // com.umeng.analytics.pro.bu
    public void k() throws bb {
        this.f22606n = this.f22605m.a();
    }

    @Override // com.umeng.analytics.pro.bu
    public bp l() throws bb {
        byte bU = u();
        if (bU == 0) {
            return f22595e;
        }
        short s2 = (short) ((bU & 240) >> 4);
        byte b3 = (byte) (bU & 15);
        bp bpVar = new bp("", d(b3), s2 == 0 ? v() : (short) (this.f22606n + s2));
        if (c(bU)) {
            this.f22608p = b3 == 1 ? Boolean.TRUE : Boolean.FALSE;
        }
        this.f22606n = bpVar.f22626c;
        return bpVar;
    }

    @Override // com.umeng.analytics.pro.bu
    public void m() throws bb {
    }

    @Override // com.umeng.analytics.pro.bu
    public br n() throws bb {
        int iE = E();
        byte bU = iE == 0 ? (byte) 0 : u();
        return new br(d((byte) (bU >> 4)), d((byte) (bU & 15)), iE);
    }

    @Override // com.umeng.analytics.pro.bu
    public void o() throws bb {
    }

    @Override // com.umeng.analytics.pro.bu
    public bq p() throws bb {
        byte bU = u();
        int iE = (bU >> 4) & 15;
        if (iE == 15) {
            iE = E();
        }
        return new bq(d(bU), iE);
    }

    @Override // com.umeng.analytics.pro.bu
    public void q() throws bb {
    }

    @Override // com.umeng.analytics.pro.bu
    public by r() throws bb {
        return new by(p());
    }

    @Override // com.umeng.analytics.pro.bu
    public void s() throws bb {
    }

    @Override // com.umeng.analytics.pro.bu
    public boolean t() throws bb {
        Boolean bool = this.f22608p;
        if (bool == null) {
            return u() == 1;
        }
        boolean zBooleanValue = bool.booleanValue();
        this.f22608p = null;
        return zBooleanValue;
    }

    @Override // com.umeng.analytics.pro.bu
    public byte u() throws bb {
        if (this.f22639g.h() <= 0) {
            this.f22639g.d(this.f22604c, 0, 1);
            return this.f22604c[0];
        }
        byte b3 = this.f22639g.f()[this.f22639g.g()];
        this.f22639g.a(1);
        return b3;
    }

    @Override // com.umeng.analytics.pro.bu
    public short v() throws bb {
        return (short) g(E());
    }

    @Override // com.umeng.analytics.pro.bu
    public int w() throws bb {
        return g(E());
    }

    @Override // com.umeng.analytics.pro.bu
    public long x() throws bb {
        return d(F());
    }

    @Override // com.umeng.analytics.pro.bu
    public double y() throws bb {
        byte[] bArr = new byte[8];
        this.f22639g.d(bArr, 0, 8);
        return Double.longBitsToDouble(a(bArr));
    }

    @Override // com.umeng.analytics.pro.bu
    public String z() throws bb {
        int iE = E();
        f(iE);
        if (iE == 0) {
            return "";
        }
        try {
            if (this.f22639g.h() < iE) {
                return new String(e(iE), "UTF-8");
            }
            String str = new String(this.f22639g.f(), this.f22639g.g(), iE, "UTF-8");
            this.f22639g.a(iE);
            return str;
        } catch (UnsupportedEncodingException unused) {
            throw new bb("UTF-8 not supported!");
        }
    }

    public static class a implements bw {

        /* renamed from: a, reason: collision with root package name */
        private final long f22611a;

        public a() {
            this.f22611a = -1L;
        }

        @Override // com.umeng.analytics.pro.bw
        public bu a(ci ciVar) {
            return new bo(ciVar, this.f22611a);
        }

        public a(int i2) {
            this.f22611a = i2;
        }
    }

    private void b(int i2) throws bb {
        int i3 = 0;
        while ((i2 & (-128)) != 0) {
            this.f22602a[i3] = (byte) ((i2 & 127) | 128);
            i2 >>>= 7;
            i3++;
        }
        byte[] bArr = this.f22602a;
        bArr[i3] = (byte) i2;
        this.f22639g.b(bArr, 0, i3 + 1);
    }

    private void d(int i2) throws bb {
        b((byte) i2);
    }

    private byte d(byte b3) throws bv {
        byte b4 = (byte) (b3 & 15);
        switch (b4) {
            case 0:
                return (byte) 0;
            case 1:
            case 2:
                return (byte) 2;
            case 3:
                return (byte) 3;
            case 4:
                return (byte) 6;
            case 5:
                return (byte) 8;
            case 6:
                return (byte) 10;
            case 7:
                return (byte) 4;
            case 8:
                return (byte) 11;
            case 9:
                return (byte) 15;
            case 10:
                return (byte) 14;
            case 11:
                return (byte) 13;
            case 12:
                return (byte) 12;
            default:
                throw new bv("don't know what type: " + ((int) b4));
        }
    }

    private byte e(byte b3) {
        return f22596f[b3];
    }

    private void b(long j2) throws bb {
        int i2 = 0;
        while (((-128) & j2) != 0) {
            this.f22603b[i2] = (byte) ((127 & j2) | 128);
            j2 >>>= 7;
            i2++;
        }
        byte[] bArr = this.f22603b;
        bArr[i2] = (byte) j2;
        this.f22639g.b(bArr, 0, i2 + 1);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(bz bzVar) throws bb {
        this.f22605m.a(this.f22606n);
        this.f22606n = (short) 0;
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(bp bpVar) throws bb {
        if (bpVar.f22625b == 2) {
            this.f22607o = bpVar;
        } else {
            a(bpVar, (byte) -1);
        }
    }

    private void b(byte b3) throws bb {
        byte[] bArr = this.f22610r;
        bArr[0] = b3;
        this.f22639g.b(bArr);
    }

    private void a(bp bpVar, byte b3) throws bb {
        if (b3 == -1) {
            b3 = e(bpVar.f22625b);
        }
        short s2 = bpVar.f22626c;
        short s3 = this.f22606n;
        if (s2 > s3 && s2 - s3 <= 15) {
            d(b3 | ((s2 - s3) << 4));
        } else {
            b(b3);
            a(bpVar.f22626c);
        }
        this.f22606n = bpVar.f22626c;
    }

    public bo(ci ciVar) {
        this(ciVar, -1L);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(br brVar) throws bb {
        int i2 = brVar.f22631c;
        if (i2 == 0) {
            d(0);
            return;
        }
        b(i2);
        d(e(brVar.f22630b) | (e(brVar.f22629a) << 4));
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(bq bqVar) throws bb {
        a(bqVar.f22627a, bqVar.f22628b);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(by byVar) throws bb {
        a(byVar.f22649a, byVar.f22650b);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(boolean z2) throws bb {
        bp bpVar = this.f22607o;
        if (bpVar != null) {
            a(bpVar, z2 ? (byte) 1 : (byte) 2);
            this.f22607o = null;
        } else {
            b(z2 ? (byte) 1 : (byte) 2);
        }
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(byte b3) throws bb {
        b(b3);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(short s2) throws bb {
        b(c((int) s2));
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(int i2) throws bb {
        b(c(i2));
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(long j2) throws bb {
        b(c(j2));
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(double d3) throws bb {
        byte[] bArr = {0, 0, 0, 0, 0, 0, 0, 0};
        a(Double.doubleToLongBits(d3), bArr, 0);
        this.f22639g.b(bArr);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(String str) throws bb {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            a(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException unused) {
            throw new bb("UTF-8 not supported!");
        }
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(ByteBuffer byteBuffer) throws bb {
        a(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position());
    }

    private void a(byte[] bArr, int i2, int i3) throws bb {
        b(i3);
        this.f22639g.b(bArr, i2, i3);
    }

    public void a(byte b3, int i2) throws bb {
        if (i2 <= 14) {
            d(e(b3) | (i2 << 4));
        } else {
            d(e(b3) | 240);
            b(i2);
        }
    }

    private void a(long j2, byte[] bArr, int i2) {
        bArr[i2 + 0] = (byte) (j2 & 255);
        bArr[i2 + 1] = (byte) ((j2 >> 8) & 255);
        bArr[i2 + 2] = (byte) ((j2 >> 16) & 255);
        bArr[i2 + 3] = (byte) ((j2 >> 24) & 255);
        bArr[i2 + 4] = (byte) ((j2 >> 32) & 255);
        bArr[i2 + 5] = (byte) ((j2 >> 40) & 255);
        bArr[i2 + 6] = (byte) ((j2 >> 48) & 255);
        bArr[i2 + 7] = (byte) ((j2 >> 56) & 255);
    }

    private long a(byte[] bArr) {
        return ((bArr[7] & 255) << 56) | ((bArr[6] & 255) << 48) | ((bArr[5] & 255) << 40) | ((bArr[4] & 255) << 32) | ((bArr[3] & 255) << 24) | ((bArr[2] & 255) << 16) | ((bArr[1] & 255) << 8) | (255 & bArr[0]);
    }
}
