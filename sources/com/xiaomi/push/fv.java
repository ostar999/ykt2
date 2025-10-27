package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.push.et;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class fv {

    /* renamed from: a, reason: collision with other field name */
    private et.a f449a;

    /* renamed from: a, reason: collision with other field name */
    String f450a;

    /* renamed from: a, reason: collision with other field name */
    private short f451a;

    /* renamed from: b, reason: collision with other field name */
    private byte[] f452b;

    /* renamed from: b, reason: collision with root package name */
    private static String f24907b = he.a(5) + "-";

    /* renamed from: a, reason: collision with root package name */
    private static long f24906a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static final byte[] f448a = new byte[0];

    public fv() {
        this.f451a = (short) 2;
        this.f452b = f448a;
        this.f450a = null;
        this.f449a = new et.a();
    }

    public fv(et.a aVar, short s2, byte[] bArr) {
        this.f450a = null;
        this.f449a = aVar;
        this.f451a = s2;
        this.f452b = bArr;
    }

    @Deprecated
    public static fv a(gt gtVar, String str) {
        int i2;
        fv fvVar = new fv();
        try {
            i2 = Integer.parseInt(gtVar.k());
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m117a("Blob parse chid err " + e2.getMessage());
            i2 = 1;
        }
        fvVar.a(i2);
        fvVar.a(gtVar.j());
        fvVar.c(gtVar.m());
        fvVar.b(gtVar.n());
        fvVar.a("XMLMSG", (String) null);
        try {
            fvVar.a(gtVar.mo468a().getBytes("utf8"), str);
            if (TextUtils.isEmpty(str)) {
                fvVar.a((short) 3);
            } else {
                fvVar.a((short) 2);
                fvVar.a("SECMSG", (String) null);
            }
        } catch (UnsupportedEncodingException e3) {
            com.xiaomi.channel.commonutils.logger.b.m117a("Blob setPayload err： " + e3.getMessage());
        }
        return fvVar;
    }

    public static fv a(ByteBuffer byteBuffer) throws IOException {
        try {
            ByteBuffer byteBufferSlice = byteBuffer.slice();
            short s2 = byteBufferSlice.getShort(0);
            short s3 = byteBufferSlice.getShort(2);
            int i2 = byteBufferSlice.getInt(4);
            et.a aVar = new et.a();
            aVar.a(byteBufferSlice.array(), byteBufferSlice.arrayOffset() + 8, (int) s3);
            byte[] bArr = new byte[i2];
            byteBufferSlice.position(s3 + 8);
            byteBufferSlice.get(bArr, 0, i2);
            return new fv(aVar, s2, bArr);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m117a("read Blob err :" + e2.getMessage());
            throw new IOException("Malformed Input");
        }
    }

    public static synchronized String d() {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append(f24907b);
        long j2 = f24906a;
        f24906a = 1 + j2;
        sb.append(Long.toString(j2));
        return sb.toString();
    }

    public int a() {
        return this.f449a.c();
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m428a() {
        return this.f449a.m351c();
    }

    /* renamed from: a, reason: collision with other method in class */
    public ByteBuffer mo429a(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.allocate(c());
        }
        byteBuffer.putShort(this.f451a);
        byteBuffer.putShort((short) this.f449a.a());
        byteBuffer.putInt(this.f452b.length);
        int iPosition = byteBuffer.position();
        this.f449a.m333a(byteBuffer.array(), byteBuffer.arrayOffset() + iPosition, this.f449a.a());
        byteBuffer.position(iPosition + this.f449a.a());
        byteBuffer.put(this.f452b);
        return byteBuffer;
    }

    /* renamed from: a, reason: collision with other method in class */
    public short m430a() {
        return this.f451a;
    }

    public void a(int i2) {
        this.f449a.a(i2);
    }

    public void a(long j2, String str, String str2) {
        if (j2 != 0) {
            this.f449a.a(j2);
        }
        if (!TextUtils.isEmpty(str)) {
            this.f449a.a(str);
        }
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        this.f449a.b(str2);
    }

    public void a(String str) {
        this.f449a.e(str);
    }

    public void a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("command should not be empty");
        }
        this.f449a.c(str);
        this.f449a.m346a();
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        this.f449a.d(str2);
    }

    public void a(short s2) {
        this.f451a = s2;
    }

    public void a(byte[] bArr, String str) {
        if (TextUtils.isEmpty(str)) {
            this.f449a.c(0);
            this.f452b = bArr;
        } else {
            this.f449a.c(1);
            this.f452b = com.xiaomi.push.service.bc.a(com.xiaomi.push.service.bc.a(str, e()), bArr);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m431a() {
        return this.f449a.j();
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m432a() {
        return this.f452b;
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte[] m433a(String str) {
        if (this.f449a.e() == 1) {
            return com.xiaomi.push.service.bc.a(com.xiaomi.push.service.bc.a(str, e()), this.f452b);
        }
        if (this.f449a.e() == 0) {
            return this.f452b;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("unknow cipher = " + this.f449a.e());
        return this.f452b;
    }

    public int b() {
        return this.f449a.f();
    }

    /* renamed from: b, reason: collision with other method in class */
    public String m434b() {
        return this.f449a.m353d();
    }

    public void b(String str) {
        this.f450a = str;
    }

    public int c() {
        return this.f449a.b() + 8 + this.f452b.length;
    }

    /* renamed from: c, reason: collision with other method in class */
    public String m435c() {
        return this.f449a.m357f();
    }

    public void c(String str) throws NumberFormatException {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        int iIndexOf = str.indexOf("@");
        try {
            long j2 = Long.parseLong(str.substring(0, iIndexOf));
            int iIndexOf2 = str.indexOf("/", iIndexOf);
            String strSubstring = str.substring(iIndexOf + 1, iIndexOf2);
            String strSubstring2 = str.substring(iIndexOf2 + 1);
            this.f449a.a(j2);
            this.f449a.a(strSubstring);
            this.f449a.b(strSubstring2);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m117a("Blob parse user err " + e2.getMessage());
        }
    }

    public String e() {
        String strM355e = this.f449a.m355e();
        if ("ID_NOT_AVAILABLE".equals(strM355e)) {
            return null;
        }
        if (this.f449a.g()) {
            return strM355e;
        }
        String strD = d();
        this.f449a.e(strD);
        return strD;
    }

    public String f() {
        return this.f450a;
    }

    public String g() {
        if (!this.f449a.m350b()) {
            return null;
        }
        return Long.toString(this.f449a.m345a()) + "@" + this.f449a.m347a() + "/" + this.f449a.m349b();
    }

    public String toString() {
        return "Blob [chid=" + a() + "; Id=" + e() + "; cmd=" + m428a() + "; type=" + ((int) m430a()) + "; from=" + g() + " ]";
    }
}
