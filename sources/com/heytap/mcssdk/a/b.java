package com.heytap.mcssdk.a;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;

/* loaded from: classes4.dex */
public abstract class b implements BinaryDecoder, BinaryEncoder {

    /* renamed from: a, reason: collision with root package name */
    private static final int f7101a = 2;

    /* renamed from: b, reason: collision with root package name */
    public static final int f7102b = 76;

    /* renamed from: c, reason: collision with root package name */
    public static final int f7103c = 64;

    /* renamed from: d, reason: collision with root package name */
    protected static final int f7104d = 255;

    /* renamed from: e, reason: collision with root package name */
    protected static final byte f7105e = 61;

    /* renamed from: m, reason: collision with root package name */
    private static final int f7106m = 8192;

    /* renamed from: f, reason: collision with root package name */
    protected final byte f7107f = 61;

    /* renamed from: g, reason: collision with root package name */
    protected final int f7108g;

    /* renamed from: h, reason: collision with root package name */
    protected byte[] f7109h;

    /* renamed from: i, reason: collision with root package name */
    protected int f7110i;

    /* renamed from: j, reason: collision with root package name */
    protected boolean f7111j;

    /* renamed from: k, reason: collision with root package name */
    protected int f7112k;

    /* renamed from: l, reason: collision with root package name */
    protected int f7113l;

    /* renamed from: n, reason: collision with root package name */
    private final int f7114n;

    /* renamed from: o, reason: collision with root package name */
    private final int f7115o;

    /* renamed from: p, reason: collision with root package name */
    private final int f7116p;

    /* renamed from: q, reason: collision with root package name */
    private int f7117q;

    public b(int i2, int i3, int i4, int i5) {
        this.f7114n = i2;
        this.f7115o = i3;
        this.f7108g = (i4 <= 0 || i5 <= 0) ? 0 : (i4 / i3) * i3;
        this.f7116p = i5;
    }

    private void a() {
        byte[] bArr = this.f7109h;
        if (bArr == null) {
            this.f7109h = new byte[d()];
            this.f7110i = 0;
            this.f7117q = 0;
        } else {
            byte[] bArr2 = new byte[bArr.length * 2];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            this.f7109h = bArr2;
        }
    }

    public static boolean c(byte b3) {
        return b3 == 9 || b3 == 10 || b3 == 13 || b3 == 32;
    }

    private void e() {
        this.f7109h = null;
        this.f7110i = 0;
        this.f7117q = 0;
        this.f7112k = 0;
        this.f7113l = 0;
        this.f7111j = false;
    }

    public void a(int i2) {
        byte[] bArr = this.f7109h;
        if (bArr == null || bArr.length < this.f7110i + i2) {
            a();
        }
    }

    public abstract void a(byte[] bArr, int i2, int i3);

    public abstract void b(byte[] bArr, int i2, int i3);

    public boolean b() {
        return this.f7109h != null;
    }

    public abstract boolean b(byte b3);

    public boolean b(byte[] bArr, boolean z2) {
        byte b3;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (!b(bArr[i2]) && (!z2 || ((b3 = bArr[i2]) != 61 && !c(b3)))) {
                return false;
            }
        }
        return true;
    }

    public int c() {
        if (this.f7109h != null) {
            return this.f7110i - this.f7117q;
        }
        return 0;
    }

    public int c(byte[] bArr, int i2, int i3) {
        if (this.f7109h == null) {
            return this.f7111j ? -1 : 0;
        }
        int iMin = Math.min(c(), i3);
        System.arraycopy(this.f7109h, this.f7117q, bArr, i2, iMin);
        int i4 = this.f7117q + iMin;
        this.f7117q = i4;
        if (i4 >= this.f7110i) {
            this.f7109h = null;
        }
        return iMin;
    }

    public byte[] c(String str) {
        return decode(StringUtils.getBytesUtf8(str));
    }

    public int d() {
        return 8192;
    }

    public boolean d(String str) {
        return b(StringUtils.getBytesUtf8(str), true);
    }

    @Override // org.apache.commons.codec.Decoder
    public Object decode(Object obj) throws DecoderException {
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        }
        if (obj instanceof String) {
            return c((String) obj);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }

    @Override // org.apache.commons.codec.BinaryDecoder
    public byte[] decode(byte[] bArr) {
        e();
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        b(bArr, 0, bArr.length);
        b(bArr, 0, -1);
        int i2 = this.f7110i;
        byte[] bArr2 = new byte[i2];
        c(bArr2, 0, i2);
        return bArr2;
    }

    @Override // org.apache.commons.codec.Encoder
    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof byte[]) {
            return encode((byte[]) obj);
        }
        throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
    }

    @Override // org.apache.commons.codec.BinaryEncoder
    public byte[] encode(byte[] bArr) {
        e();
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        a(bArr, 0, bArr.length);
        a(bArr, 0, -1);
        int i2 = this.f7110i - this.f7117q;
        byte[] bArr2 = new byte[i2];
        c(bArr2, 0, i2);
        return bArr2;
    }

    public String j(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    public String k(byte[] bArr) {
        return StringUtils.newStringUtf8(encode(bArr));
    }

    public boolean l(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (byte b3 : bArr) {
            if (61 == b3 || b(b3)) {
                return true;
            }
        }
        return false;
    }

    public long m(byte[] bArr) {
        int length = bArr.length;
        int i2 = this.f7114n;
        long j2 = (((length + i2) - 1) / i2) * this.f7115o;
        int i3 = this.f7108g;
        return i3 > 0 ? j2 + ((((i3 + j2) - 1) / i3) * this.f7116p) : j2;
    }
}
