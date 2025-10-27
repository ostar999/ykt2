package com.tencent.liteav.basic.license;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;

/* loaded from: classes6.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    public static final byte[] f18471a = "#PART#".getBytes();

    public static byte[] a(byte[] bArr, byte[] bArr2) throws Exception {
        PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bArr2));
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(2, privateKeyGeneratePrivate);
        return cipher.doFinal(bArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0087 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] b(byte[] r12, byte[] r13) throws java.lang.Exception {
        /*
            byte[] r0 = com.tencent.liteav.basic.license.h.f18471a
            int r0 = r0.length
            if (r0 > 0) goto La
            byte[] r12 = a(r12, r13)
            return r12
        La:
            int r1 = r12.length
            java.util.ArrayList r2 = new java.util.ArrayList
            r3 = 1024(0x400, float:1.435E-42)
            r2.<init>(r3)
            r3 = 0
            r4 = r3
            r5 = r4
        L15:
            if (r4 >= r1) goto L89
            r6 = r12[r4]
            int r7 = r1 + (-1)
            r8 = 1
            if (r4 != r7) goto L3e
            int r6 = r1 - r5
            byte[] r7 = new byte[r6]
            java.lang.System.arraycopy(r12, r5, r7, r3, r6)
            byte[] r5 = a(r7, r13)
            int r6 = r5.length
            r7 = r3
        L2b:
            if (r7 >= r6) goto L39
            r9 = r5[r7]
            java.lang.Byte r9 = java.lang.Byte.valueOf(r9)
            r2.add(r9)
            int r7 = r7 + 1
            goto L2b
        L39:
            int r5 = r4 + r0
            int r4 = r5 + (-1)
            goto L63
        L3e:
            byte[] r7 = com.tencent.liteav.basic.license.h.f18471a
            r7 = r7[r3]
            if (r6 != r7) goto L63
            if (r0 <= r8) goto L61
            int r6 = r4 + r0
            if (r6 >= r1) goto L63
            r7 = r3
            r6 = r8
        L4c:
            if (r6 >= r0) goto L64
            byte[] r9 = com.tencent.liteav.basic.license.h.f18471a
            r9 = r9[r6]
            int r10 = r4 + r6
            r10 = r12[r10]
            if (r9 == r10) goto L59
            goto L64
        L59:
            int r9 = r0 + (-1)
            if (r6 != r9) goto L5e
            r7 = r8
        L5e:
            int r6 = r6 + 1
            goto L4c
        L61:
            r7 = r8
            goto L64
        L63:
            r7 = r3
        L64:
            if (r7 == 0) goto L87
            int r6 = r4 - r5
            byte[] r7 = new byte[r6]
            java.lang.System.arraycopy(r12, r5, r7, r3, r6)
            byte[] r5 = a(r7, r13)
            int r6 = r5.length
            r7 = r3
        L73:
            if (r7 >= r6) goto L81
            r9 = r5[r7]
            java.lang.Byte r9 = java.lang.Byte.valueOf(r9)
            r2.add(r9)
            int r7 = r7 + 1
            goto L73
        L81:
            int r4 = r4 + r0
            int r5 = r4 + (-1)
            r11 = r5
            r5 = r4
            r4 = r11
        L87:
            int r4 = r4 + r8
            goto L15
        L89:
            int r12 = r2.size()
            byte[] r12 = new byte[r12]
            java.util.Iterator r13 = r2.iterator()
        L93:
            boolean r0 = r13.hasNext()
            if (r0 == 0) goto La9
            java.lang.Object r0 = r13.next()
            java.lang.Byte r0 = (java.lang.Byte) r0
            int r1 = r3 + 1
            byte r0 = r0.byteValue()
            r12[r3] = r0
            r3 = r1
            goto L93
        La9:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.license.h.b(byte[], byte[]):byte[]");
    }
}
