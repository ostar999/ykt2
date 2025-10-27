package com.heytap.mcssdk.a;

import com.google.common.base.Ascii;
import java.math.BigInteger;
import kotlin.io.encoding.Base64;
import okio.Utf8;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes4.dex */
public class a extends b {

    /* renamed from: m, reason: collision with root package name */
    private static final int f7088m = 6;

    /* renamed from: n, reason: collision with root package name */
    private static final int f7089n = 3;

    /* renamed from: o, reason: collision with root package name */
    private static final int f7090o = 4;

    /* renamed from: s, reason: collision with root package name */
    private static final int f7094s = 63;

    /* renamed from: t, reason: collision with root package name */
    private final byte[] f7095t;

    /* renamed from: u, reason: collision with root package name */
    private final byte[] f7096u;

    /* renamed from: v, reason: collision with root package name */
    private final byte[] f7097v;

    /* renamed from: w, reason: collision with root package name */
    private final int f7098w;

    /* renamed from: x, reason: collision with root package name */
    private final int f7099x;

    /* renamed from: y, reason: collision with root package name */
    private int f7100y;

    /* renamed from: a, reason: collision with root package name */
    static final byte[] f7087a = {13, 10};

    /* renamed from: p, reason: collision with root package name */
    private static final byte[] f7091p = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 43, 47};

    /* renamed from: q, reason: collision with root package name */
    private static final byte[] f7092q = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 45, 95};

    /* renamed from: r, reason: collision with root package name */
    private static final byte[] f7093r = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, Utf8.REPLACEMENT_BYTE, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, HttpTokens.COLON, HttpTokens.SEMI_COLON, 60, Base64.padSymbol, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, Ascii.DC2, 19, Ascii.DC4, 21, 22, 23, Ascii.CAN, Ascii.EM, -1, -1, -1, -1, Utf8.REPLACEMENT_BYTE, -1, Ascii.SUB, Ascii.ESC, Ascii.FS, Ascii.GS, Ascii.RS, Ascii.US, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR};

    public a() {
        this(0);
    }

    public a(int i2) {
        this(i2, f7087a);
    }

    public a(int i2, byte[] bArr) {
        this(i2, bArr, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public a(int r5, byte[] r6, boolean r7) {
        /*
            r4 = this;
            r0 = 0
            if (r6 != 0) goto L5
            r1 = r0
            goto L6
        L5:
            int r1 = r6.length
        L6:
            r2 = 3
            r3 = 4
            r4.<init>(r2, r3, r5, r1)
            byte[] r1 = com.heytap.mcssdk.a.a.f7093r
            r4.f7096u = r1
            r1 = 0
            if (r6 == 0) goto L48
            boolean r2 = r4.l(r6)
            if (r2 != 0) goto L28
            if (r5 <= 0) goto L48
            int r5 = r6.length
            int r5 = r5 + r3
            r4.f7099x = r5
            int r5 = r6.length
            byte[] r5 = new byte[r5]
            r4.f7097v = r5
            int r1 = r6.length
            java.lang.System.arraycopy(r6, r0, r5, r0, r1)
            goto L4c
        L28:
            java.lang.String r5 = org.apache.commons.codec.binary.StringUtils.newStringUtf8(r6)
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r0 = "lineSeparator must not contain base64 characters: ["
            r7.append(r0)
            r7.append(r5)
            java.lang.String r5 = "]"
            r7.append(r5)
            java.lang.String r5 = r7.toString()
            r6.<init>(r5)
            throw r6
        L48:
            r4.f7099x = r3
            r4.f7097v = r1
        L4c:
            int r5 = r4.f7099x
            int r5 = r5 + (-1)
            r4.f7098w = r5
            if (r7 == 0) goto L57
            byte[] r5 = com.heytap.mcssdk.a.a.f7092q
            goto L59
        L57:
            byte[] r5 = com.heytap.mcssdk.a.a.f7091p
        L59:
            r4.f7095t = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.heytap.mcssdk.a.a.<init>(int, byte[], boolean):void");
    }

    public a(boolean z2) {
        this(76, f7087a, z2);
    }

    public static boolean a(byte b3) {
        if (b3 != 61) {
            if (b3 >= 0) {
                byte[] bArr = f7093r;
                if (b3 >= bArr.length || bArr[b3] == -1) {
                }
            }
            return false;
        }
        return true;
    }

    public static boolean a(String str) {
        return b(StringUtils.getBytesUtf8(str));
    }

    public static boolean a(byte[] bArr) {
        return b(bArr);
    }

    public static byte[] a(BigInteger bigInteger) {
        if (bigInteger != null) {
            return a(b(bigInteger), false);
        }
        throw new NullPointerException("encodeInteger called with null parameter");
    }

    public static byte[] a(byte[] bArr, boolean z2) {
        return a(bArr, z2, false);
    }

    public static byte[] a(byte[] bArr, boolean z2, boolean z3) {
        return a(bArr, z2, z3, Integer.MAX_VALUE);
    }

    public static byte[] a(byte[] bArr, boolean z2, boolean z3, int i2) {
        if (bArr == null || bArr.length == 0) {
            return bArr;
        }
        a aVar = z2 ? new a(z3) : new a(0, f7087a, z3);
        long jM = aVar.m(bArr);
        if (jM <= i2) {
            return aVar.encode(bArr);
        }
        throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + jM + ") than the specified maximum size of " + i2);
    }

    public static boolean b(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (!a(bArr[i2]) && !b.c(bArr[i2])) {
                return false;
            }
        }
        return true;
    }

    public static byte[] b(String str) {
        return new a().c(str);
    }

    public static byte[] b(BigInteger bigInteger) {
        int iBitLength = ((bigInteger.bitLength() + 7) >> 3) << 3;
        byte[] byteArray = bigInteger.toByteArray();
        int i2 = 1;
        if (bigInteger.bitLength() % 8 != 0 && (bigInteger.bitLength() / 8) + 1 == iBitLength / 8) {
            return byteArray;
        }
        int length = byteArray.length;
        if (bigInteger.bitLength() % 8 == 0) {
            length--;
        } else {
            i2 = 0;
        }
        int i3 = iBitLength / 8;
        int i4 = i3 - length;
        byte[] bArr = new byte[i3];
        System.arraycopy(byteArray, i2, bArr, i4, length);
        return bArr;
    }

    public static byte[] c(byte[] bArr) {
        return a(bArr, false);
    }

    public static String d(byte[] bArr) {
        return StringUtils.newStringUtf8(a(bArr, false));
    }

    public static byte[] e(byte[] bArr) {
        return a(bArr, false, true);
    }

    public static String f(byte[] bArr) {
        return StringUtils.newStringUtf8(a(bArr, false, true));
    }

    public static byte[] g(byte[] bArr) {
        return a(bArr, true);
    }

    public static byte[] h(byte[] bArr) {
        return new a().decode(bArr);
    }

    public static BigInteger i(byte[] bArr) {
        return new BigInteger(1, h(bArr));
    }

    @Override // com.heytap.mcssdk.a.b
    public void a(byte[] bArr, int i2, int i3) {
        if (this.f7111j) {
            return;
        }
        if (i3 >= 0) {
            int i4 = 0;
            while (i4 < i3) {
                a(this.f7099x);
                int i5 = (this.f7113l + 1) % 3;
                this.f7113l = i5;
                int i6 = i2 + 1;
                int i7 = bArr[i2];
                if (i7 < 0) {
                    i7 += 256;
                }
                int i8 = (this.f7100y << 8) + i7;
                this.f7100y = i8;
                if (i5 == 0) {
                    byte[] bArr2 = this.f7109h;
                    int i9 = this.f7110i;
                    int i10 = i9 + 1;
                    byte[] bArr3 = this.f7095t;
                    bArr2[i9] = bArr3[(i8 >> 18) & 63];
                    int i11 = i10 + 1;
                    bArr2[i10] = bArr3[(i8 >> 12) & 63];
                    int i12 = i11 + 1;
                    bArr2[i11] = bArr3[(i8 >> 6) & 63];
                    int i13 = i12 + 1;
                    this.f7110i = i13;
                    bArr2[i12] = bArr3[i8 & 63];
                    int i14 = this.f7112k + 4;
                    this.f7112k = i14;
                    int i15 = this.f7108g;
                    if (i15 > 0 && i15 <= i14) {
                        byte[] bArr4 = this.f7097v;
                        System.arraycopy(bArr4, 0, bArr2, i13, bArr4.length);
                        this.f7110i += this.f7097v.length;
                        this.f7112k = 0;
                    }
                }
                i4++;
                i2 = i6;
            }
            return;
        }
        this.f7111j = true;
        if (this.f7113l == 0 && this.f7108g == 0) {
            return;
        }
        a(this.f7099x);
        int i16 = this.f7110i;
        int i17 = this.f7113l;
        if (i17 == 1) {
            byte[] bArr5 = this.f7109h;
            int i18 = i16 + 1;
            byte[] bArr6 = this.f7095t;
            int i19 = this.f7100y;
            bArr5[i16] = bArr6[(i19 >> 2) & 63];
            int i20 = i18 + 1;
            this.f7110i = i20;
            bArr5[i18] = bArr6[(i19 << 4) & 63];
            if (bArr6 == f7091p) {
                int i21 = i20 + 1;
                bArr5[i20] = Base64.padSymbol;
                this.f7110i = i21 + 1;
                bArr5[i21] = Base64.padSymbol;
            }
        } else if (i17 == 2) {
            byte[] bArr7 = this.f7109h;
            int i22 = i16 + 1;
            byte[] bArr8 = this.f7095t;
            int i23 = this.f7100y;
            bArr7[i16] = bArr8[(i23 >> 10) & 63];
            int i24 = i22 + 1;
            bArr7[i22] = bArr8[(i23 >> 4) & 63];
            int i25 = i24 + 1;
            this.f7110i = i25;
            bArr7[i24] = bArr8[(i23 << 2) & 63];
            if (bArr8 == f7091p) {
                this.f7110i = i25 + 1;
                bArr7[i25] = Base64.padSymbol;
            }
        }
        int i26 = this.f7112k;
        int i27 = this.f7110i;
        int i28 = i26 + (i27 - i16);
        this.f7112k = i28;
        if (this.f7108g <= 0 || i28 <= 0) {
            return;
        }
        byte[] bArr9 = this.f7097v;
        System.arraycopy(bArr9, 0, this.f7109h, i27, bArr9.length);
        this.f7110i += this.f7097v.length;
    }

    public boolean a() {
        return this.f7095t == f7092q;
    }

    @Override // com.heytap.mcssdk.a.b
    public void b(byte[] bArr, int i2, int i3) {
        byte b3;
        if (this.f7111j) {
            return;
        }
        if (i3 < 0) {
            this.f7111j = true;
        }
        int i4 = 0;
        while (true) {
            if (i4 >= i3) {
                break;
            }
            a(this.f7098w);
            int i5 = i2 + 1;
            byte b4 = bArr[i2];
            if (b4 == 61) {
                this.f7111j = true;
                break;
            }
            if (b4 >= 0) {
                byte[] bArr2 = f7093r;
                if (b4 < bArr2.length && (b3 = bArr2[b4]) >= 0) {
                    int i6 = (this.f7113l + 1) % 4;
                    this.f7113l = i6;
                    int i7 = (this.f7100y << 6) + b3;
                    this.f7100y = i7;
                    if (i6 == 0) {
                        byte[] bArr3 = this.f7109h;
                        int i8 = this.f7110i;
                        int i9 = i8 + 1;
                        bArr3[i8] = (byte) ((i7 >> 16) & 255);
                        int i10 = i9 + 1;
                        bArr3[i9] = (byte) ((i7 >> 8) & 255);
                        this.f7110i = i10 + 1;
                        bArr3[i10] = (byte) (i7 & 255);
                    }
                }
            }
            i4++;
            i2 = i5;
        }
        if (!this.f7111j || this.f7113l == 0) {
            return;
        }
        a(this.f7098w);
        int i11 = this.f7113l;
        if (i11 == 2) {
            int i12 = this.f7100y >> 4;
            this.f7100y = i12;
            byte[] bArr4 = this.f7109h;
            int i13 = this.f7110i;
            this.f7110i = i13 + 1;
            bArr4[i13] = (byte) (i12 & 255);
            return;
        }
        if (i11 != 3) {
            return;
        }
        int i14 = this.f7100y >> 2;
        this.f7100y = i14;
        byte[] bArr5 = this.f7109h;
        int i15 = this.f7110i;
        int i16 = i15 + 1;
        bArr5[i15] = (byte) ((i14 >> 8) & 255);
        this.f7110i = i16 + 1;
        bArr5[i16] = (byte) (i14 & 255);
    }

    @Override // com.heytap.mcssdk.a.b
    public boolean b(byte b3) {
        if (b3 >= 0) {
            byte[] bArr = this.f7096u;
            if (b3 < bArr.length && bArr[b3] != -1) {
                return true;
            }
        }
        return false;
    }
}
