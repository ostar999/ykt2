package com.xiaomi.push;

import com.xiaomi.push.jx;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class kh extends jx {

    /* renamed from: b, reason: collision with root package name */
    private static int f25514b = 10000;

    /* renamed from: c, reason: collision with root package name */
    private static int f25515c = 10000;

    /* renamed from: d, reason: collision with root package name */
    private static int f25516d = 10000;

    /* renamed from: e, reason: collision with root package name */
    private static int f25517e = 10485760;

    /* renamed from: f, reason: collision with root package name */
    private static int f25518f = 104857600;

    public static class a extends jx.a {
        public a() {
            super(false, true);
        }

        public a(boolean z2, boolean z3, int i2) {
            super(z2, z3, i2);
        }

        @Override // com.xiaomi.push.jx.a, com.xiaomi.push.kd
        public kb a(kl klVar) {
            kh khVar = new kh(klVar, ((jx.a) this).f926a, this.f25501b);
            int i2 = ((jx.a) this).f25500a;
            if (i2 != 0) {
                khVar.b(i2);
            }
            return khVar;
        }
    }

    public kh(kl klVar, boolean z2, boolean z3) {
        super(klVar, z2, z3);
    }

    @Override // com.xiaomi.push.jx, com.xiaomi.push.kb
    /* renamed from: a */
    public jz mo663a() throws jv {
        byte bA = a();
        int iMo660a = mo660a();
        if (iMo660a <= f25515c) {
            return new jz(bA, iMo660a);
        }
        throw new kc(3, "Thrift list size " + iMo660a + " out of range!");
    }

    @Override // com.xiaomi.push.jx, com.xiaomi.push.kb
    /* renamed from: a */
    public ka mo664a() throws jv {
        byte bA = a();
        byte bA2 = a();
        int iMo660a = mo660a();
        if (iMo660a <= f25514b) {
            return new ka(bA, bA2, iMo660a);
        }
        throw new kc(3, "Thrift map size " + iMo660a + " out of range!");
    }

    @Override // com.xiaomi.push.jx, com.xiaomi.push.kb
    /* renamed from: a */
    public kf mo665a() throws jv {
        byte bA = a();
        int iMo660a = mo660a();
        if (iMo660a <= f25516d) {
            return new kf(bA, iMo660a);
        }
        throw new kc(3, "Thrift set size " + iMo660a + " out of range!");
    }

    @Override // com.xiaomi.push.jx, com.xiaomi.push.kb
    /* renamed from: a */
    public String mo667a() throws jv {
        int iMo660a = mo660a();
        if (iMo660a > f25517e) {
            throw new kc(3, "Thrift string size " + iMo660a + " out of range!");
        }
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

    @Override // com.xiaomi.push.jx, com.xiaomi.push.kb
    /* renamed from: a */
    public ByteBuffer mo668a() throws jv {
        int iMo660a = mo660a();
        if (iMo660a > f25518f) {
            throw new kc(3, "Thrift binary size " + iMo660a + " out of range!");
        }
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
}
