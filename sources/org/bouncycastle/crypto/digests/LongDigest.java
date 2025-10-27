package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.ExtendedDigest;
import org.bouncycastle.crypto.util.Pack;

/* loaded from: classes9.dex */
public abstract class LongDigest implements ExtendedDigest {
    private static final int BYTE_LENGTH = 128;
    static final long[] K = {4794697086780616226L, 8158064640168781261L, -5349999486874862801L, -1606136188198331460L, 4131703408338449720L, 6480981068601479193L, -7908458776815382629L, -6116909921290321640L, -2880145864133508542L, 1334009975649890238L, 2608012711638119052L, 6128411473006802146L, 8268148722764581231L, -9160688886553864527L, -7215885187991268811L, -4495734319001033068L, -1973867731355612462L, -1171420211273849373L, 1135362057144423861L, 2597628984639134821L, 3308224258029322869L, 5365058923640841347L, 6679025012923562964L, 8573033837759648693L, -7476448914759557205L, -6327057829258317296L, -5763719355590565569L, -4658551843659510044L, -4116276920077217854L, -3051310485924567259L, 489312712824947311L, 1452737877330783856L, 2861767655752347644L, 3322285676063803686L, 5560940570517711597L, 5996557281743188959L, 7280758554555802590L, 8532644243296465576L, -9096487096722542874L, -7894198246740708037L, -6719396339535248540L, -6333637450476146687L, -4446306890439682159L, -4076793802049405392L, -3345356375505022440L, -2983346525034927856L, -860691631967231958L, 1182934255886127544L, 1847814050463011016L, 2177327727835720531L, 2830643537854262169L, 3796741975233480872L, 4115178125766777443L, 5681478168544905931L, 6601373596472566643L, 7507060721942968483L, 8399075790359081724L, 8693463985226723168L, -8878714635349349518L, -8302665154208450068L, -8016688836872298968L, -6606660893046293015L, -4685533653050689259L, -4147400797238176981L, -3880063495543823972L, -3348786107499101689L, -1523767162380948706L, -757361751448694408L, 500013540394364858L, 748580250866718886L, 1242879168328830382L, 1977374033974150939L, 2944078676154940804L, 3659926193048069267L, 4368137639120453308L, 4836135668995329356L, 5532061633213252278L, 6448918945643986474L, 6902733635092675308L, 7801388544844847127L};
    protected long H1;
    protected long H2;
    protected long H3;
    protected long H4;
    protected long H5;
    protected long H6;
    protected long H7;
    protected long H8;
    private long[] W;
    private long byteCount1;
    private long byteCount2;
    private int wOff;
    private byte[] xBuf;
    private int xBufOff;

    public LongDigest() {
        this.W = new long[80];
        this.xBuf = new byte[8];
        this.xBufOff = 0;
        reset();
    }

    public LongDigest(LongDigest longDigest) {
        this.W = new long[80];
        byte[] bArr = new byte[longDigest.xBuf.length];
        this.xBuf = bArr;
        byte[] bArr2 = longDigest.xBuf;
        System.arraycopy(bArr2, 0, bArr, 0, bArr2.length);
        this.xBufOff = longDigest.xBufOff;
        this.byteCount1 = longDigest.byteCount1;
        this.byteCount2 = longDigest.byteCount2;
        this.H1 = longDigest.H1;
        this.H2 = longDigest.H2;
        this.H3 = longDigest.H3;
        this.H4 = longDigest.H4;
        this.H5 = longDigest.H5;
        this.H6 = longDigest.H6;
        this.H7 = longDigest.H7;
        this.H8 = longDigest.H8;
        long[] jArr = longDigest.W;
        System.arraycopy(jArr, 0, this.W, 0, jArr.length);
        this.wOff = longDigest.wOff;
    }

    private long Ch(long j2, long j3, long j4) {
        return ((~j2) & j4) ^ (j3 & j2);
    }

    private long Maj(long j2, long j3, long j4) {
        return ((j2 & j4) ^ (j2 & j3)) ^ (j3 & j4);
    }

    private long Sigma0(long j2) {
        return (j2 >>> 7) ^ (((j2 << 63) | (j2 >>> 1)) ^ ((j2 << 56) | (j2 >>> 8)));
    }

    private long Sigma1(long j2) {
        return (j2 >>> 6) ^ (((j2 << 45) | (j2 >>> 19)) ^ ((j2 << 3) | (j2 >>> 61)));
    }

    private long Sum0(long j2) {
        return ((j2 >>> 39) | (j2 << 25)) ^ (((j2 << 36) | (j2 >>> 28)) ^ ((j2 << 30) | (j2 >>> 34)));
    }

    private long Sum1(long j2) {
        return ((j2 >>> 41) | (j2 << 23)) ^ (((j2 << 50) | (j2 >>> 14)) ^ ((j2 << 46) | (j2 >>> 18)));
    }

    private void adjustByteCounts() {
        long j2 = this.byteCount1;
        if (j2 > 2305843009213693951L) {
            this.byteCount2 += j2 >>> 61;
            this.byteCount1 = j2 & 2305843009213693951L;
        }
    }

    public void finish() {
        adjustByteCounts();
        long j2 = this.byteCount1 << 3;
        long j3 = this.byteCount2;
        byte b3 = -128;
        while (true) {
            update(b3);
            if (this.xBufOff == 0) {
                processLength(j2, j3);
                processBlock();
                return;
            }
            b3 = 0;
        }
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return 128;
    }

    public void processBlock() {
        adjustByteCounts();
        for (int i2 = 16; i2 <= 79; i2++) {
            long[] jArr = this.W;
            long jSigma1 = Sigma1(jArr[i2 - 2]);
            long[] jArr2 = this.W;
            jArr[i2] = jSigma1 + jArr2[i2 - 7] + Sigma0(jArr2[i2 - 15]) + this.W[i2 - 16];
        }
        long j2 = this.H1;
        long j3 = this.H2;
        long j4 = this.H3;
        long j5 = this.H4;
        long j6 = this.H5;
        long j7 = this.H6;
        long j8 = this.H7;
        long j9 = j7;
        long j10 = j5;
        int i3 = 0;
        long jSum0 = j3;
        long j11 = j4;
        long j12 = j6;
        int i4 = 0;
        long j13 = this.H8;
        long j14 = j2;
        long j15 = j8;
        while (i4 < 10) {
            long j16 = j12;
            long jSum1 = Sum1(j12) + Ch(j12, j9, j15);
            long[] jArr3 = K;
            int i5 = i3 + 1;
            long j17 = j13 + jSum1 + jArr3[i3] + this.W[i3];
            long j18 = j10 + j17;
            long jSum02 = j17 + Sum0(j14) + Maj(j14, jSum0, j11);
            int i6 = i5 + 1;
            long jSum12 = j15 + Sum1(j18) + Ch(j18, j16, j9) + jArr3[i5] + this.W[i5];
            long j19 = j11 + jSum12;
            long jSum03 = jSum12 + Sum0(jSum02) + Maj(jSum02, j14, jSum0);
            int i7 = i6 + 1;
            long jSum13 = j9 + Sum1(j19) + Ch(j19, j18, j16) + jArr3[i6] + this.W[i6];
            long j20 = jSum0 + jSum13;
            long jSum04 = jSum13 + Sum0(jSum03) + Maj(jSum03, jSum02, j14);
            int i8 = i7 + 1;
            long jSum14 = j16 + Sum1(j20) + Ch(j20, j19, j18) + jArr3[i7] + this.W[i7];
            long j21 = j14 + jSum14;
            long jSum05 = jSum14 + Sum0(jSum04) + Maj(jSum04, jSum03, jSum02);
            int i9 = i8 + 1;
            long jSum15 = j18 + Sum1(j21) + Ch(j21, j20, j19) + jArr3[i8] + this.W[i8];
            long j22 = jSum02 + jSum15;
            long jSum06 = jSum15 + Sum0(jSum05) + Maj(jSum05, jSum04, jSum03);
            int i10 = i9 + 1;
            long jSum16 = j19 + Sum1(j22) + Ch(j22, j21, j20) + jArr3[i9] + this.W[i9];
            long j23 = jSum03 + jSum16;
            long jSum07 = jSum16 + Sum0(jSum06) + Maj(jSum06, jSum05, jSum04);
            j15 = j23;
            int i11 = i10 + 1;
            long jSum17 = j20 + Sum1(j23) + Ch(j23, j22, j21) + jArr3[i10] + this.W[i10];
            long j24 = jSum04 + jSum17;
            j9 = j24;
            jSum0 = jSum17 + Sum0(jSum07) + Maj(jSum07, jSum06, jSum05);
            long jSum18 = j21 + Sum1(j24) + Ch(j24, j15, j22) + jArr3[i11] + this.W[i11];
            long jSum08 = jSum18 + Sum0(jSum0) + Maj(jSum0, jSum07, jSum06);
            i4++;
            j12 = jSum05 + jSum18;
            j11 = jSum07;
            j13 = j22;
            j10 = jSum06;
            i3 = i11 + 1;
            j14 = jSum08;
        }
        this.H1 += j14;
        this.H2 += jSum0;
        this.H3 += j11;
        this.H4 += j10;
        this.H5 += j12;
        this.H6 += j9;
        this.H7 += j15;
        this.H8 += j13;
        this.wOff = 0;
        for (int i12 = 0; i12 < 16; i12++) {
            this.W[i12] = 0;
        }
    }

    public void processLength(long j2, long j3) {
        if (this.wOff > 14) {
            processBlock();
        }
        long[] jArr = this.W;
        jArr[14] = j3;
        jArr[15] = j2;
    }

    public void processWord(byte[] bArr, int i2) {
        this.W[this.wOff] = Pack.bigEndianToLong(bArr, i2);
        int i3 = this.wOff + 1;
        this.wOff = i3;
        if (i3 == 16) {
            processBlock();
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.byteCount1 = 0L;
        this.byteCount2 = 0L;
        int i2 = 0;
        this.xBufOff = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.xBuf;
            if (i3 >= bArr.length) {
                break;
            }
            bArr[i3] = 0;
            i3++;
        }
        this.wOff = 0;
        while (true) {
            long[] jArr = this.W;
            if (i2 == jArr.length) {
                return;
            }
            jArr[i2] = 0;
            i2++;
        }
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b3) {
        byte[] bArr = this.xBuf;
        int i2 = this.xBufOff;
        int i3 = i2 + 1;
        this.xBufOff = i3;
        bArr[i2] = b3;
        if (i3 == bArr.length) {
            processWord(bArr, 0);
            this.xBufOff = 0;
        }
        this.byteCount1++;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        while (this.xBufOff != 0 && i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
        while (i3 > this.xBuf.length) {
            processWord(bArr, i2);
            byte[] bArr2 = this.xBuf;
            i2 += bArr2.length;
            i3 -= bArr2.length;
            this.byteCount1 += bArr2.length;
        }
        while (i3 > 0) {
            update(bArr[i2]);
            i2++;
            i3--;
        }
    }
}
