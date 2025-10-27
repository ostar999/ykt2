package com.tencent.bugly.proguard;

import java.nio.ByteBuffer;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes6.dex */
public final class n {

    /* renamed from: a, reason: collision with root package name */
    private static final byte[] f17847a;

    /* renamed from: b, reason: collision with root package name */
    private static final byte[] f17848b;

    static {
        byte[] bArr = {TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 65, 66, 67, 68, 69, 70};
        byte[] bArr2 = new byte[256];
        byte[] bArr3 = new byte[256];
        for (int i2 = 0; i2 < 256; i2++) {
            bArr2[i2] = bArr[i2 >>> 4];
            bArr3[i2] = bArr[i2 & 15];
        }
        f17847a = bArr2;
        f17848b = bArr3;
    }

    public static boolean a(int i2, int i3) {
        return i2 == i3;
    }

    public static boolean a(long j2, long j3) {
        return j2 == j3;
    }

    public static boolean a(Object obj, Object obj2) {
        return obj.equals(obj2);
    }

    public static boolean a(boolean z2, boolean z3) {
        return z2 == z3;
    }

    public static byte[] a(ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        byte[] bArr = new byte[iPosition];
        System.arraycopy(byteBuffer.array(), 0, bArr, 0, iPosition);
        return bArr;
    }
}
