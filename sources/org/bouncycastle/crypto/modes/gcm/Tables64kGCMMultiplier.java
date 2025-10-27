package org.bouncycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import org.bouncycastle.crypto.util.Pack;

/* loaded from: classes9.dex */
public class Tables64kGCMMultiplier implements GCMMultiplier {
    private final int[][][] M = (int[][][]) Array.newInstance((Class<?>) int[].class, 16, 256);

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void init(byte[] bArr) {
        int[][][] iArr = this.M;
        iArr[0][0] = new int[4];
        iArr[0][128] = GCMUtil.asInts(bArr);
        for (int i2 = 64; i2 >= 1; i2 >>= 1) {
            int[] iArr2 = new int[4];
            System.arraycopy(this.M[0][i2 + i2], 0, iArr2, 0, 4);
            GCMUtil.multiplyP(iArr2);
            this.M[0][i2] = iArr2;
        }
        int i3 = 0;
        while (true) {
            for (int i4 = 2; i4 < 256; i4 += i4) {
                for (int i5 = 1; i5 < i4; i5++) {
                    int[] iArr3 = new int[4];
                    System.arraycopy(this.M[i3][i4], 0, iArr3, 0, 4);
                    GCMUtil.xor(iArr3, this.M[i3][i5]);
                    this.M[i3][i4 + i5] = iArr3;
                }
            }
            i3++;
            if (i3 == 16) {
                return;
            }
            this.M[i3][0] = new int[4];
            for (int i6 = 128; i6 > 0; i6 >>= 1) {
                int[] iArr4 = new int[4];
                System.arraycopy(this.M[i3 - 1][i6], 0, iArr4, 0, 4);
                GCMUtil.multiplyP8(iArr4);
                this.M[i3][i6] = iArr4;
            }
        }
    }

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void multiplyH(byte[] bArr) {
        int[] iArr = new int[4];
        for (int i2 = 15; i2 >= 0; i2--) {
            int[] iArr2 = this.M[i2][bArr[i2] & 255];
            iArr[0] = iArr[0] ^ iArr2[0];
            iArr[1] = iArr[1] ^ iArr2[1];
            iArr[2] = iArr[2] ^ iArr2[2];
            iArr[3] = iArr[3] ^ iArr2[3];
        }
        Pack.intToBigEndian(iArr[0], bArr, 0);
        Pack.intToBigEndian(iArr[1], bArr, 4);
        Pack.intToBigEndian(iArr[2], bArr, 8);
        Pack.intToBigEndian(iArr[3], bArr, 12);
    }
}
