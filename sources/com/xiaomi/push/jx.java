package com.xiaomi.push;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class jx extends kb {

    /* renamed from: a, reason: collision with root package name */
    private static final kg f25492a = new kg();

    /* renamed from: a, reason: collision with other field name */
    protected int f921a;

    /* renamed from: a, reason: collision with other field name */
    protected boolean f922a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f923a;

    /* renamed from: b, reason: collision with root package name */
    protected boolean f25493b;

    /* renamed from: b, reason: collision with other field name */
    private byte[] f924b;

    /* renamed from: c, reason: collision with root package name */
    protected boolean f25494c;

    /* renamed from: c, reason: collision with other field name */
    private byte[] f925c;

    /* renamed from: d, reason: collision with root package name */
    private byte[] f25495d;

    /* renamed from: e, reason: collision with root package name */
    private byte[] f25496e;

    /* renamed from: f, reason: collision with root package name */
    private byte[] f25497f;

    /* renamed from: g, reason: collision with root package name */
    private byte[] f25498g;

    /* renamed from: h, reason: collision with root package name */
    private byte[] f25499h;

    public static class a implements kd {

        /* renamed from: a, reason: collision with root package name */
        protected int f25500a;

        /* renamed from: a, reason: collision with other field name */
        protected boolean f926a;

        /* renamed from: b, reason: collision with root package name */
        protected boolean f25501b;

        public a() {
            this(false, true);
        }

        public a(boolean z2, boolean z3) {
            this(z2, z3, 0);
        }

        public a(boolean z2, boolean z3, int i2) {
            this.f926a = z2;
            this.f25501b = z3;
            this.f25500a = i2;
        }

        @Override // com.xiaomi.push.kd
        public kb a(kl klVar) {
            jx jxVar = new jx(klVar, this.f926a, this.f25501b);
            int i2 = this.f25500a;
            if (i2 != 0) {
                jxVar.b(i2);
            }
            return jxVar;
        }
    }

    public jx(kl klVar, boolean z2, boolean z3) {
        super(klVar);
        this.f25494c = false;
        this.f923a = new byte[1];
        this.f924b = new byte[2];
        this.f925c = new byte[4];
        this.f25495d = new byte[8];
        this.f25496e = new byte[1];
        this.f25497f = new byte[2];
        this.f25498g = new byte[4];
        this.f25499h = new byte[8];
        this.f922a = z2;
        this.f25493b = z3;
    }

    private int a(byte[] bArr, int i2, int i3) throws jv {
        c(i3);
        return ((kb) this).f25509a.b(bArr, i2, i3);
    }

    @Override // com.xiaomi.push.kb
    public byte a() throws jv {
        if (((kb) this).f25509a.b() < 1) {
            a(this.f25496e, 0, 1);
            return this.f25496e[0];
        }
        byte b3 = ((kb) this).f25509a.mo674a()[((kb) this).f25509a.a()];
        ((kb) this).f25509a.a(1);
        return b3;
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public double mo659a() {
        return Double.longBitsToDouble(mo661a());
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public int mo660a() throws jv {
        int iA;
        byte[] bArrMo674a = this.f25498g;
        if (((kb) this).f25509a.b() >= 4) {
            bArrMo674a = ((kb) this).f25509a.mo674a();
            iA = ((kb) this).f25509a.a();
            ((kb) this).f25509a.a(4);
        } else {
            a(this.f25498g, 0, 4);
            iA = 0;
        }
        return (bArrMo674a[iA + 3] & 255) | ((bArrMo674a[iA] & 255) << 24) | ((bArrMo674a[iA + 1] & 255) << 16) | ((bArrMo674a[iA + 2] & 255) << 8);
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public long mo661a() throws jv {
        int iA;
        byte[] bArrMo674a = this.f25499h;
        if (((kb) this).f25509a.b() >= 8) {
            bArrMo674a = ((kb) this).f25509a.mo674a();
            iA = ((kb) this).f25509a.a();
            ((kb) this).f25509a.a(8);
        } else {
            a(this.f25499h, 0, 8);
            iA = 0;
        }
        return (bArrMo674a[iA + 7] & 255) | ((bArrMo674a[iA] & 255) << 56) | ((bArrMo674a[iA + 1] & 255) << 48) | ((bArrMo674a[iA + 2] & 255) << 40) | ((bArrMo674a[iA + 3] & 255) << 32) | ((bArrMo674a[iA + 4] & 255) << 24) | ((bArrMo674a[iA + 5] & 255) << 16) | ((bArrMo674a[iA + 6] & 255) << 8);
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public jy mo662a() throws jv {
        byte bA = a();
        return new jy("", bA, bA == 0 ? (short) 0 : mo669a());
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public jz mo663a() {
        return new jz(a(), mo660a());
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public ka mo664a() {
        return new ka(a(), a(), mo660a());
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public kf mo665a() {
        return new kf(a(), mo660a());
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public kg mo666a() {
        return f25492a;
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public String mo667a() throws jv {
        int iMo660a = mo660a();
        if (((kb) this).f25509a.b() < iMo660a) {
            return a(iMo660a);
        }
        try {
            String str = new String(((kb) this).f25509a.mo674a(), ((kb) this).f25509a.a(), iMo660a, "UTF-8");
            ((kb) this).f25509a.a(iMo660a);
            return str;
        } catch (UnsupportedEncodingException unused) {
            throw new jv("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    public String a(int i2) throws jv {
        try {
            c(i2);
            byte[] bArr = new byte[i2];
            ((kb) this).f25509a.b(bArr, 0, i2);
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            throw new jv("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public ByteBuffer mo668a() throws jv {
        int iMo660a = mo660a();
        c(iMo660a);
        if (((kb) this).f25509a.b() >= iMo660a) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(((kb) this).f25509a.mo674a(), ((kb) this).f25509a.a(), iMo660a);
            ((kb) this).f25509a.a(iMo660a);
            return byteBufferWrap;
        }
        byte[] bArr = new byte[iMo660a];
        ((kb) this).f25509a.b(bArr, 0, iMo660a);
        return ByteBuffer.wrap(bArr);
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public short mo669a() throws jv {
        int iA;
        byte[] bArrMo674a = this.f25497f;
        if (((kb) this).f25509a.b() >= 2) {
            bArrMo674a = ((kb) this).f25509a.mo674a();
            iA = ((kb) this).f25509a.a();
            ((kb) this).f25509a.a(2);
        } else {
            a(this.f25497f, 0, 2);
            iA = 0;
        }
        return (short) ((bArrMo674a[iA + 1] & 255) | ((bArrMo674a[iA] & 255) << 8));
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public void mo670a() {
    }

    @Override // com.xiaomi.push.kb
    public void a(byte b3) {
        byte[] bArr = this.f923a;
        bArr[0] = b3;
        ((kb) this).f25509a.mo673a(bArr, 0, 1);
    }

    @Override // com.xiaomi.push.kb
    public void a(double d3) {
        a(Double.doubleToLongBits(d3));
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public void mo671a(int i2) {
        byte[] bArr = this.f925c;
        bArr[0] = (byte) ((i2 >> 24) & 255);
        bArr[1] = (byte) ((i2 >> 16) & 255);
        bArr[2] = (byte) ((i2 >> 8) & 255);
        bArr[3] = (byte) (i2 & 255);
        ((kb) this).f25509a.mo673a(bArr, 0, 4);
    }

    @Override // com.xiaomi.push.kb
    public void a(long j2) {
        byte[] bArr = this.f25495d;
        bArr[0] = (byte) ((j2 >> 56) & 255);
        bArr[1] = (byte) ((j2 >> 48) & 255);
        bArr[2] = (byte) ((j2 >> 40) & 255);
        bArr[3] = (byte) ((j2 >> 32) & 255);
        bArr[4] = (byte) ((j2 >> 24) & 255);
        bArr[5] = (byte) ((j2 >> 16) & 255);
        bArr[6] = (byte) ((j2 >> 8) & 255);
        bArr[7] = (byte) (j2 & 255);
        ((kb) this).f25509a.mo673a(bArr, 0, 8);
    }

    @Override // com.xiaomi.push.kb
    public void a(jy jyVar) {
        a(jyVar.f25502a);
        a(jyVar.f928a);
    }

    @Override // com.xiaomi.push.kb
    public void a(jz jzVar) {
        a(jzVar.f25503a);
        mo671a(jzVar.f929a);
    }

    @Override // com.xiaomi.push.kb
    public void a(ka kaVar) {
        a(kaVar.f25507a);
        a(kaVar.f25508b);
        mo671a(kaVar.f932a);
    }

    @Override // com.xiaomi.push.kb
    public void a(kf kfVar) {
        a(kfVar.f25512a);
        mo671a(kfVar.f933a);
    }

    @Override // com.xiaomi.push.kb
    public void a(kg kgVar) {
    }

    @Override // com.xiaomi.push.kb
    public void a(String str) throws jv, UnsupportedEncodingException {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            mo671a(bytes.length);
            ((kb) this).f25509a.mo673a(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException unused) {
            throw new jv("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // com.xiaomi.push.kb
    public void a(ByteBuffer byteBuffer) {
        int iLimit = (byteBuffer.limit() - byteBuffer.position()) - byteBuffer.arrayOffset();
        mo671a(iLimit);
        ((kb) this).f25509a.mo673a(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), iLimit);
    }

    @Override // com.xiaomi.push.kb
    public void a(short s2) {
        byte[] bArr = this.f924b;
        bArr[0] = (byte) ((s2 >> 8) & 255);
        bArr[1] = (byte) (s2 & 255);
        ((kb) this).f25509a.mo673a(bArr, 0, 2);
    }

    @Override // com.xiaomi.push.kb
    public void a(boolean z2) {
        a(z2 ? (byte) 1 : (byte) 0);
    }

    @Override // com.xiaomi.push.kb
    /* renamed from: a, reason: collision with other method in class */
    public boolean mo672a() {
        return a() == 1;
    }

    @Override // com.xiaomi.push.kb
    public void b() {
    }

    public void b(int i2) {
        this.f921a = i2;
        this.f25494c = true;
    }

    @Override // com.xiaomi.push.kb
    public void c() {
        a((byte) 0);
    }

    public void c(int i2) throws jv {
        if (i2 < 0) {
            throw new jv("Negative length: " + i2);
        }
        if (this.f25494c) {
            int i3 = this.f921a - i2;
            this.f921a = i3;
            if (i3 >= 0) {
                return;
            }
            throw new jv("Message length exceeded: " + i2);
        }
    }

    @Override // com.xiaomi.push.kb
    public void d() {
    }

    @Override // com.xiaomi.push.kb
    public void e() {
    }

    @Override // com.xiaomi.push.kb
    public void f() {
    }

    @Override // com.xiaomi.push.kb
    public void g() {
    }

    @Override // com.xiaomi.push.kb
    public void h() {
    }

    @Override // com.xiaomi.push.kb
    public void i() {
    }

    @Override // com.xiaomi.push.kb
    public void j() {
    }

    @Override // com.xiaomi.push.kb
    public void k() {
    }
}
