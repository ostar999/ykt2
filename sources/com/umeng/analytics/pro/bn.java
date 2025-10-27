package com.umeng.analytics.pro;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class bn extends bu {

    /* renamed from: a, reason: collision with root package name */
    protected static final int f22576a = -65536;

    /* renamed from: b, reason: collision with root package name */
    protected static final int f22577b = -2147418112;

    /* renamed from: h, reason: collision with root package name */
    private static final bz f22578h = new bz();

    /* renamed from: c, reason: collision with root package name */
    protected boolean f22579c;

    /* renamed from: d, reason: collision with root package name */
    protected boolean f22580d;

    /* renamed from: e, reason: collision with root package name */
    protected int f22581e;

    /* renamed from: f, reason: collision with root package name */
    protected boolean f22582f;

    /* renamed from: i, reason: collision with root package name */
    private byte[] f22583i;

    /* renamed from: j, reason: collision with root package name */
    private byte[] f22584j;

    /* renamed from: k, reason: collision with root package name */
    private byte[] f22585k;

    /* renamed from: l, reason: collision with root package name */
    private byte[] f22586l;

    /* renamed from: m, reason: collision with root package name */
    private byte[] f22587m;

    /* renamed from: n, reason: collision with root package name */
    private byte[] f22588n;

    /* renamed from: o, reason: collision with root package name */
    private byte[] f22589o;

    /* renamed from: p, reason: collision with root package name */
    private byte[] f22590p;

    public static class a implements bw {

        /* renamed from: a, reason: collision with root package name */
        protected boolean f22591a;

        /* renamed from: b, reason: collision with root package name */
        protected boolean f22592b;

        /* renamed from: c, reason: collision with root package name */
        protected int f22593c;

        public a() {
            this(false, true);
        }

        @Override // com.umeng.analytics.pro.bw
        public bu a(ci ciVar) {
            bn bnVar = new bn(ciVar, this.f22591a, this.f22592b);
            int i2 = this.f22593c;
            if (i2 != 0) {
                bnVar.c(i2);
            }
            return bnVar;
        }

        public a(boolean z2, boolean z3) {
            this(z2, z3, 0);
        }

        public a(boolean z2, boolean z3, int i2) {
            this.f22591a = z2;
            this.f22592b = z3;
            this.f22593c = i2;
        }
    }

    public bn(ci ciVar) {
        this(ciVar, false, true);
    }

    @Override // com.umeng.analytics.pro.bu
    public ByteBuffer A() throws bb {
        int iW = w();
        d(iW);
        if (this.f22639g.h() >= iW) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(this.f22639g.f(), this.f22639g.g(), iW);
            this.f22639g.a(iW);
            return byteBufferWrap;
        }
        byte[] bArr = new byte[iW];
        this.f22639g.d(bArr, 0, iW);
        return ByteBuffer.wrap(bArr);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a() {
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(bs bsVar) throws bb, UnsupportedEncodingException {
        if (this.f22580d) {
            a(bsVar.f22633b | f22577b);
            a(bsVar.f22632a);
            a(bsVar.f22634c);
        } else {
            a(bsVar.f22632a);
            a(bsVar.f22633b);
            a(bsVar.f22634c);
        }
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(bz bzVar) {
    }

    public String b(int i2) throws bb {
        try {
            d(i2);
            byte[] bArr = new byte[i2];
            this.f22639g.d(bArr, 0, i2);
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            throw new bb("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // com.umeng.analytics.pro.bu
    public void b() {
    }

    @Override // com.umeng.analytics.pro.bu
    public void c() {
    }

    public void c(int i2) {
        this.f22581e = i2;
        this.f22582f = true;
    }

    @Override // com.umeng.analytics.pro.bu
    public void d() throws bb {
        a((byte) 0);
    }

    @Override // com.umeng.analytics.pro.bu
    public void e() {
    }

    @Override // com.umeng.analytics.pro.bu
    public void f() {
    }

    @Override // com.umeng.analytics.pro.bu
    public void g() {
    }

    @Override // com.umeng.analytics.pro.bu
    public bs h() throws bb {
        int iW = w();
        if (iW < 0) {
            if (((-65536) & iW) == f22577b) {
                return new bs(z(), (byte) (iW & 255), w());
            }
            throw new bv(4, "Bad version in readMessageBegin");
        }
        if (this.f22579c) {
            throw new bv(4, "Missing version in readMessageBegin, old client?");
        }
        return new bs(b(iW), u(), w());
    }

    @Override // com.umeng.analytics.pro.bu
    public void i() {
    }

    @Override // com.umeng.analytics.pro.bu
    public bz j() {
        return f22578h;
    }

    @Override // com.umeng.analytics.pro.bu
    public void k() {
    }

    @Override // com.umeng.analytics.pro.bu
    public bp l() throws bb {
        byte bU = u();
        return new bp("", bU, bU == 0 ? (short) 0 : v());
    }

    @Override // com.umeng.analytics.pro.bu
    public void m() {
    }

    @Override // com.umeng.analytics.pro.bu
    public br n() throws bb {
        return new br(u(), u(), w());
    }

    @Override // com.umeng.analytics.pro.bu
    public void o() {
    }

    @Override // com.umeng.analytics.pro.bu
    public bq p() throws bb {
        return new bq(u(), w());
    }

    @Override // com.umeng.analytics.pro.bu
    public void q() {
    }

    @Override // com.umeng.analytics.pro.bu
    public by r() throws bb {
        return new by(u(), w());
    }

    @Override // com.umeng.analytics.pro.bu
    public void s() {
    }

    @Override // com.umeng.analytics.pro.bu
    public boolean t() throws bb {
        return u() == 1;
    }

    @Override // com.umeng.analytics.pro.bu
    public byte u() throws bb {
        if (this.f22639g.h() < 1) {
            a(this.f22587m, 0, 1);
            return this.f22587m[0];
        }
        byte b3 = this.f22639g.f()[this.f22639g.g()];
        this.f22639g.a(1);
        return b3;
    }

    @Override // com.umeng.analytics.pro.bu
    public short v() throws bb {
        int iG;
        byte[] bArrF = this.f22588n;
        if (this.f22639g.h() >= 2) {
            bArrF = this.f22639g.f();
            iG = this.f22639g.g();
            this.f22639g.a(2);
        } else {
            a(this.f22588n, 0, 2);
            iG = 0;
        }
        return (short) ((bArrF[iG + 1] & 255) | ((bArrF[iG] & 255) << 8));
    }

    @Override // com.umeng.analytics.pro.bu
    public int w() throws bb {
        int iG;
        byte[] bArrF = this.f22589o;
        if (this.f22639g.h() >= 4) {
            bArrF = this.f22639g.f();
            iG = this.f22639g.g();
            this.f22639g.a(4);
        } else {
            a(this.f22589o, 0, 4);
            iG = 0;
        }
        return (bArrF[iG + 3] & 255) | ((bArrF[iG] & 255) << 24) | ((bArrF[iG + 1] & 255) << 16) | ((bArrF[iG + 2] & 255) << 8);
    }

    @Override // com.umeng.analytics.pro.bu
    public long x() throws bb {
        int iG;
        byte[] bArrF = this.f22590p;
        if (this.f22639g.h() >= 8) {
            bArrF = this.f22639g.f();
            iG = this.f22639g.g();
            this.f22639g.a(8);
        } else {
            a(this.f22590p, 0, 8);
            iG = 0;
        }
        return (bArrF[iG + 7] & 255) | ((bArrF[iG] & 255) << 56) | ((bArrF[iG + 1] & 255) << 48) | ((bArrF[iG + 2] & 255) << 40) | ((bArrF[iG + 3] & 255) << 32) | ((bArrF[iG + 4] & 255) << 24) | ((bArrF[iG + 5] & 255) << 16) | ((bArrF[iG + 6] & 255) << 8);
    }

    @Override // com.umeng.analytics.pro.bu
    public double y() throws bb {
        return Double.longBitsToDouble(x());
    }

    @Override // com.umeng.analytics.pro.bu
    public String z() throws bb {
        int iW = w();
        if (this.f22639g.h() < iW) {
            return b(iW);
        }
        try {
            String str = new String(this.f22639g.f(), this.f22639g.g(), iW, "UTF-8");
            this.f22639g.a(iW);
            return str;
        } catch (UnsupportedEncodingException unused) {
            throw new bb("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    public bn(ci ciVar, boolean z2, boolean z3) {
        super(ciVar);
        this.f22582f = false;
        this.f22583i = new byte[1];
        this.f22584j = new byte[2];
        this.f22585k = new byte[4];
        this.f22586l = new byte[8];
        this.f22587m = new byte[1];
        this.f22588n = new byte[2];
        this.f22589o = new byte[4];
        this.f22590p = new byte[8];
        this.f22579c = z2;
        this.f22580d = z3;
    }

    public void d(int i2) throws bb {
        if (i2 < 0) {
            throw new bv("Negative length: " + i2);
        }
        if (this.f22582f) {
            int i3 = this.f22581e - i2;
            this.f22581e = i3;
            if (i3 >= 0) {
                return;
            }
            throw new bv("Message length exceeded: " + i2);
        }
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(bp bpVar) throws bb {
        a(bpVar.f22625b);
        a(bpVar.f22626c);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(br brVar) throws bb {
        a(brVar.f22629a);
        a(brVar.f22630b);
        a(brVar.f22631c);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(bq bqVar) throws bb {
        a(bqVar.f22627a);
        a(bqVar.f22628b);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(by byVar) throws bb {
        a(byVar.f22649a);
        a(byVar.f22650b);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(boolean z2) throws bb {
        a(z2 ? (byte) 1 : (byte) 0);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(byte b3) throws bb {
        byte[] bArr = this.f22583i;
        bArr[0] = b3;
        this.f22639g.b(bArr, 0, 1);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(short s2) throws bb {
        byte[] bArr = this.f22584j;
        bArr[0] = (byte) ((s2 >> 8) & 255);
        bArr[1] = (byte) (s2 & 255);
        this.f22639g.b(bArr, 0, 2);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(int i2) throws bb {
        byte[] bArr = this.f22585k;
        bArr[0] = (byte) ((i2 >> 24) & 255);
        bArr[1] = (byte) ((i2 >> 16) & 255);
        bArr[2] = (byte) ((i2 >> 8) & 255);
        bArr[3] = (byte) (i2 & 255);
        this.f22639g.b(bArr, 0, 4);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(long j2) throws bb {
        byte[] bArr = this.f22586l;
        bArr[0] = (byte) ((j2 >> 56) & 255);
        bArr[1] = (byte) ((j2 >> 48) & 255);
        bArr[2] = (byte) ((j2 >> 40) & 255);
        bArr[3] = (byte) ((j2 >> 32) & 255);
        bArr[4] = (byte) ((j2 >> 24) & 255);
        bArr[5] = (byte) ((j2 >> 16) & 255);
        bArr[6] = (byte) ((j2 >> 8) & 255);
        bArr[7] = (byte) (j2 & 255);
        this.f22639g.b(bArr, 0, 8);
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(double d3) throws bb {
        a(Double.doubleToLongBits(d3));
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(String str) throws bb, UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            a(bytes.length);
            this.f22639g.b(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException unused) {
            throw new bb("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // com.umeng.analytics.pro.bu
    public void a(ByteBuffer byteBuffer) throws bb {
        int iLimit = byteBuffer.limit() - byteBuffer.position();
        a(iLimit);
        this.f22639g.b(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), iLimit);
    }

    private int a(byte[] bArr, int i2, int i3) throws bb {
        d(i3);
        return this.f22639g.d(bArr, i2, i3);
    }
}
