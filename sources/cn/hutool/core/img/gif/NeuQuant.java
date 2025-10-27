package cn.hutool.core.img.gif;

import com.yikaobang.yixue.R2;

/* loaded from: classes.dex */
public class NeuQuant {
    protected static final int ALPHABIASSHIFT = 10;
    protected static final int ALPHARADBIAS = 262144;
    protected static final int ALPHARADBSHIFT = 18;
    protected static final int BETA = 64;
    protected static final int BETAGAMMA = 65536;
    protected static final int BETASHIFT = 10;
    protected static final int GAMMA = 1024;
    protected static final int GAMMASHIFT = 10;
    protected static final int INITALPHA = 1024;
    protected static final int INITRAD = 32;
    protected static final int INITRADIUS = 2048;
    protected static final int INTBIAS = 65536;
    protected static final int INTBIASSHIFT = 16;
    protected static final int MAXNETPOS = 255;
    protected static final int MINPICTUREBYTES = 1509;
    protected static final int NCYCLES = 100;
    protected static final int NETBIASSHIFT = 4;
    protected static final int NETSIZE = 256;
    protected static final int PRIME1 = 499;
    protected static final int PRIME2 = 491;
    protected static final int PRIME3 = 487;
    protected static final int PRIME4 = 503;
    protected static final int RADBIAS = 256;
    protected static final int RADBIASSHIFT = 8;
    protected static final int RADIUSBIAS = 64;
    protected static final int RADIUSBIASSHIFT = 6;
    protected static final int RADIUSDEC = 30;
    protected int alphadec;
    protected int lengthcount;
    protected int samplefac;
    protected byte[] thepicture;
    protected int[] netindex = new int[256];
    protected int[] bias = new int[256];
    protected int[] freq = new int[256];
    protected int[] radpower = new int[32];
    protected int[][] network = new int[256][];

    public NeuQuant(byte[] bArr, int i2, int i3) {
        this.thepicture = bArr;
        this.lengthcount = i2;
        this.samplefac = i3;
        for (int i4 = 0; i4 < 256; i4++) {
            this.network[i4] = new int[]{i, i, i, 0};
            int i5 = (i4 << 12) / 256;
            this.freq[i4] = 256;
            this.bias[i4] = 0;
        }
    }

    public void alterneigh(int i2, int i3, int i4, int i5, int i6) {
        int i7 = i3 - i2;
        if (i7 < -1) {
            i7 = -1;
        }
        int i8 = i3 + i2;
        if (i8 > 256) {
            i8 = 256;
        }
        int i9 = i3 + 1;
        int i10 = i3 - 1;
        int i11 = 1;
        while (true) {
            if (i9 >= i8 && i10 <= i7) {
                return;
            }
            int i12 = i11 + 1;
            int i13 = this.radpower[i11];
            if (i9 < i8) {
                int i14 = i9 + 1;
                int[] iArr = this.network[i9];
                try {
                    int i15 = iArr[0];
                    iArr[0] = i15 - (((i15 - i4) * i13) / 262144);
                    int i16 = iArr[1];
                    iArr[1] = i16 - (((i16 - i5) * i13) / 262144);
                    int i17 = iArr[2];
                    iArr[2] = i17 - (((i17 - i6) * i13) / 262144);
                } catch (Exception unused) {
                }
                i9 = i14;
            }
            if (i10 > i7) {
                int i18 = i10 - 1;
                int[] iArr2 = this.network[i10];
                try {
                    int i19 = iArr2[0];
                    iArr2[0] = i19 - (((i19 - i4) * i13) / 262144);
                    int i20 = iArr2[1];
                    iArr2[1] = i20 - (((i20 - i5) * i13) / 262144);
                    int i21 = iArr2[2];
                    iArr2[2] = i21 - ((i13 * (i21 - i6)) / 262144);
                } catch (Exception unused2) {
                }
                i11 = i12;
                i10 = i18;
            } else {
                i11 = i12;
            }
        }
    }

    public void altersingle(int i2, int i3, int i4, int i5, int i6) {
        int[] iArr = this.network[i3];
        int i7 = iArr[0];
        iArr[0] = i7 - (((i7 - i4) * i2) / 1024);
        int i8 = iArr[1];
        iArr[1] = i8 - (((i8 - i5) * i2) / 1024);
        int i9 = iArr[2];
        iArr[2] = i9 - ((i2 * (i9 - i6)) / 1024);
    }

    public byte[] colorMap() {
        byte[] bArr = new byte[R2.attr.bl_unSelected_gradient_useLevel];
        int[] iArr = new int[256];
        for (int i2 = 0; i2 < 256; i2++) {
            iArr[this.network[i2][3]] = i2;
        }
        int i3 = 0;
        int i4 = 0;
        while (i3 < 256) {
            int i5 = i4 + 1;
            int[] iArr2 = this.network[iArr[i3]];
            bArr[i4] = (byte) iArr2[0];
            int i6 = i5 + 1;
            bArr[i5] = (byte) iArr2[1];
            bArr[i6] = (byte) iArr2[2];
            i3++;
            i4 = i6 + 1;
        }
        return bArr;
    }

    public int contest(int i2, int i3, int i4) {
        int i5 = Integer.MAX_VALUE;
        int i6 = -1;
        int i7 = -1;
        int i8 = Integer.MAX_VALUE;
        for (int i9 = 0; i9 < 256; i9++) {
            int[] iArr = this.network[i9];
            int i10 = iArr[0] - i2;
            if (i10 < 0) {
                i10 = -i10;
            }
            int i11 = iArr[1] - i3;
            if (i11 < 0) {
                i11 = -i11;
            }
            int i12 = i10 + i11;
            int i13 = iArr[2] - i4;
            if (i13 < 0) {
                i13 = -i13;
            }
            int i14 = i12 + i13;
            if (i14 < i5) {
                i6 = i9;
                i5 = i14;
            }
            int[] iArr2 = this.bias;
            int i15 = i14 - (iArr2[i9] >> 12);
            if (i15 < i8) {
                i7 = i9;
                i8 = i15;
            }
            int[] iArr3 = this.freq;
            int i16 = iArr3[i9];
            int i17 = i16 >> 10;
            iArr3[i9] = i16 - i17;
            iArr2[i9] = iArr2[i9] + (i17 << 10);
        }
        int[] iArr4 = this.freq;
        iArr4[i6] = iArr4[i6] + 64;
        int[] iArr5 = this.bias;
        iArr5[i6] = iArr5[i6] - 65536;
        return i7;
    }

    public void inxbuild() {
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < 256) {
            int[] iArr = this.network[i2];
            int i5 = iArr[1];
            int i6 = i2 + 1;
            int i7 = i2;
            for (int i8 = i6; i8 < 256; i8++) {
                int i9 = this.network[i8][1];
                if (i9 < i5) {
                    i7 = i8;
                    i5 = i9;
                }
            }
            int[] iArr2 = this.network[i7];
            if (i2 != i7) {
                int i10 = iArr2[0];
                iArr2[0] = iArr[0];
                iArr[0] = i10;
                int i11 = iArr2[1];
                iArr2[1] = iArr[1];
                iArr[1] = i11;
                int i12 = iArr2[2];
                iArr2[2] = iArr[2];
                iArr[2] = i12;
                int i13 = iArr2[3];
                iArr2[3] = iArr[3];
                iArr[3] = i13;
            }
            if (i5 != i3) {
                this.netindex[i3] = (i4 + i2) >> 1;
                while (true) {
                    i3++;
                    if (i3 >= i5) {
                        break;
                    } else {
                        this.netindex[i3] = i2;
                    }
                }
                i4 = i2;
                i3 = i5;
            }
            i2 = i6;
        }
        this.netindex[i3] = (i4 + 255) >> 1;
        for (int i14 = i3 + 1; i14 < 256; i14++) {
            this.netindex[i14] = 255;
        }
    }

    public void learn() {
        int i2;
        int i3 = this.lengthcount;
        int i4 = 1509;
        if (i3 < 1509) {
            this.samplefac = 1;
        }
        int i5 = this.samplefac;
        this.alphadec = ((i5 - 1) / 3) + 30;
        byte[] bArr = this.thepicture;
        int i6 = i3 / (i5 * 3);
        int i7 = i6 / 100;
        for (int i8 = 0; i8 < 32; i8++) {
            this.radpower[i8] = 1024 * (((1024 - (i8 * i8)) * 256) / 1024);
        }
        int i9 = this.lengthcount;
        if (i9 < 1509) {
            i2 = 3;
        } else {
            if (i9 % 499 != 0) {
                i4 = R2.attr.entryValues;
            } else if (i9 % 491 != 0) {
                i4 = R2.attr.elevation;
            } else if (i9 % 487 != 0) {
                i4 = R2.attr.edge_flag;
            }
            i2 = i4;
        }
        int i10 = 2048;
        int i11 = i7;
        int i12 = 1024;
        int i13 = 32;
        int i14 = 0;
        int i15 = 0;
        while (i14 < i6) {
            int i16 = (bArr[i15] & 255) << 4;
            int i17 = (bArr[i15 + 1] & 255) << 4;
            int i18 = (bArr[i15 + 2] & 255) << 4;
            int iContest = contest(i16, i17, i18);
            int i19 = i14;
            altersingle(i12, iContest, i16, i17, i18);
            if (i13 != 0) {
                alterneigh(i13, iContest, i16, i17, i18);
            }
            int i20 = i15 + i2;
            if (i20 >= i3) {
                i20 -= this.lengthcount;
            }
            i15 = i20;
            i14 = i19 + 1;
            if (i11 == 0) {
                i11 = 1;
            }
            if (i14 % i11 == 0) {
                i12 -= i12 / this.alphadec;
                i10 -= i10 / 30;
                int i21 = i10 >> 6;
                if (i21 <= 1) {
                    i21 = 0;
                }
                for (int i22 = 0; i22 < i21; i22++) {
                    int i23 = i21 * i21;
                    this.radpower[i22] = (((i23 - (i22 * i22)) * 256) / i23) * i12;
                }
                i13 = i21;
            }
        }
    }

    public int map(int i2, int i3, int i4) {
        int i5 = this.netindex[i3];
        int i6 = i5 - 1;
        int i7 = 1000;
        int i8 = -1;
        while (true) {
            if (i5 >= 256 && i6 < 0) {
                return i8;
            }
            if (i5 < 256) {
                int[] iArr = this.network[i5];
                int i9 = iArr[1] - i3;
                if (i9 >= i7) {
                    i5 = 256;
                } else {
                    i5++;
                    if (i9 < 0) {
                        i9 = -i9;
                    }
                    int i10 = iArr[0] - i2;
                    if (i10 < 0) {
                        i10 = -i10;
                    }
                    int i11 = i9 + i10;
                    if (i11 < i7) {
                        int i12 = iArr[2] - i4;
                        if (i12 < 0) {
                            i12 = -i12;
                        }
                        int i13 = i11 + i12;
                        if (i13 < i7) {
                            i8 = iArr[3];
                            i7 = i13;
                        }
                    }
                }
            }
            if (i6 >= 0) {
                int[] iArr2 = this.network[i6];
                int i14 = i3 - iArr2[1];
                if (i14 >= i7) {
                    i6 = -1;
                } else {
                    i6--;
                    if (i14 < 0) {
                        i14 = -i14;
                    }
                    int i15 = iArr2[0] - i2;
                    if (i15 < 0) {
                        i15 = -i15;
                    }
                    int i16 = i14 + i15;
                    if (i16 < i7) {
                        int i17 = iArr2[2] - i4;
                        if (i17 < 0) {
                            i17 = -i17;
                        }
                        int i18 = i17 + i16;
                        if (i18 < i7) {
                            i8 = iArr2[3];
                            i7 = i18;
                        }
                    }
                }
            }
        }
    }

    public byte[] process() {
        learn();
        unbiasnet();
        inxbuild();
        return colorMap();
    }

    public void unbiasnet() {
        for (int i2 = 0; i2 < 256; i2++) {
            int[] iArr = this.network[i2];
            iArr[0] = iArr[0] >> 4;
            iArr[1] = iArr[1] >> 4;
            iArr[2] = iArr[2] >> 4;
            iArr[3] = i2;
        }
    }
}
