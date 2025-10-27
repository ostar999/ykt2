package org.bouncycastle.crypto.modes.gcm;

import java.lang.reflect.Array;
import org.bouncycastle.crypto.util.Pack;

/* loaded from: classes9.dex */
public class Tables8kGCMMultiplier implements GCMMultiplier {
    private final int[][][] M = (int[][][]) Array.newInstance((Class<?>) int[].class, 32, 16);

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void init(byte[] bArr) {
        int[][][] iArr = this.M;
        iArr[0][0] = new int[4];
        int[][] iArr2 = iArr[1];
        iArr2[0] = new int[4];
        iArr2[8] = GCMUtil.asInts(bArr);
        for (int i2 = 4; i2 >= 1; i2 >>= 1) {
            int[] iArr3 = new int[4];
            System.arraycopy(this.M[1][i2 + i2], 0, iArr3, 0, 4);
            GCMUtil.multiplyP(iArr3);
            this.M[1][i2] = iArr3;
        }
        int[] iArr4 = new int[4];
        System.arraycopy(this.M[1][1], 0, iArr4, 0, 4);
        GCMUtil.multiplyP(iArr4);
        this.M[0][8] = iArr4;
        for (int i3 = 4; i3 >= 1; i3 >>= 1) {
            int[] iArr5 = new int[4];
            System.arraycopy(this.M[0][i3 + i3], 0, iArr5, 0, 4);
            GCMUtil.multiplyP(iArr5);
            this.M[0][i3] = iArr5;
        }
        int i4 = 0;
        while (true) {
            for (int i5 = 2; i5 < 16; i5 += i5) {
                for (int i6 = 1; i6 < i5; i6++) {
                    int[] iArr6 = new int[4];
                    System.arraycopy(this.M[i4][i5], 0, iArr6, 0, 4);
                    GCMUtil.xor(iArr6, this.M[i4][i6]);
                    this.M[i4][i5 + i6] = iArr6;
                }
            }
            i4++;
            if (i4 == 32) {
                return;
            }
            if (i4 > 1) {
                this.M[i4][0] = new int[4];
                for (int i7 = 8; i7 > 0; i7 >>= 1) {
                    int[] iArr7 = new int[4];
                    System.arraycopy(this.M[i4 - 2][i7], 0, iArr7, 0, 4);
                    GCMUtil.multiplyP8(iArr7);
                    this.M[i4][i7] = iArr7;
                }
            }
        }
    }

    @Override // org.bouncycastle.crypto.modes.gcm.GCMMultiplier
    public void multiplyH(byte[] bArr) {
        int[] iArr = new int[4];
        for (int i2 = 15; i2 >= 0; i2--) {
            int[][][] iArr2 = this.M;
            int i3 = i2 + i2;
            int[][] iArr3 = iArr2[i3];
            byte b3 = bArr[i2];
            int[] iArr4 = iArr3[b3 & 15];
            int i4 = iArr[0] ^ iArr4[0];
            iArr[0] = i4;
            int i5 = iArr[1] ^ iArr4[1];
            iArr[1] = i5;
            int i6 = iArr[2] ^ iArr4[2];
            iArr[2] = i6;
            int i7 = iArr[3] ^ iArr4[3];
            iArr[3] = i7;
            int[] iArr5 = iArr2[i3 + 1][(b3 & 240) >>> 4];
            iArr[0] = iArr5[0] ^ i4;
            iArr[1] = iArr5[1] ^ i5;
            iArr[2] = iArr5[2] ^ i6;
            iArr[3] = iArr5[3] ^ i7;
        }
        Pack.intToBigEndian(iArr[0], bArr, 0);
        Pack.intToBigEndian(iArr[1], bArr, 4);
        Pack.intToBigEndian(iArr[2], bArr, 8);
        Pack.intToBigEndian(iArr[3], bArr, 12);
    }
}
