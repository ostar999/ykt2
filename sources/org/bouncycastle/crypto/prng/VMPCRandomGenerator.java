package org.bouncycastle.crypto.prng;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import kotlin.io.encoding.Base64;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.eclipse.jetty.http.HttpTokens;

/* loaded from: classes9.dex */
public class VMPCRandomGenerator implements RandomGenerator {

    /* renamed from: n, reason: collision with root package name */
    private byte f27883n = 0;
    private byte[] P = {-69, 44, 98, 127, -75, -86, -44, 13, -127, -2, -78, -126, -53, -96, -95, 8, Ascii.CAN, 113, 86, -24, 73, 2, 16, -60, -34, TarConstants.LF_DIR, -91, -20, -128, Ascii.DC2, -72, 105, -38, 47, 117, -52, -94, 9, TarConstants.LF_FIFO, 3, 97, 45, -3, -32, -35, 5, 67, -112, -83, -56, -31, -81, 87, -101, TarConstants.LF_GNUTYPE_LONGNAME, -40, 81, -82, 80, -123, 60, 10, -28, -13, -100, 38, 35, TarConstants.LF_GNUTYPE_SPARSE, -55, -125, -105, 70, -79, -103, 100, TarConstants.LF_LINK, 119, -43, Ascii.GS, -42, TarConstants.LF_PAX_EXTENDED_HEADER_LC, -67, 94, -80, -118, 34, 56, -8, 104, 43, 42, -59, -45, -9, PSSSigner.TRAILER_IMPLICIT, 111, -33, 4, -27, -107, 62, 37, -122, -90, 11, -113, -15, 36, 14, -41, SignedBytes.MAX_POWER_OF_TWO, -77, -49, 126, 6, 21, -102, 77, Ascii.FS, -93, -37, TarConstants.LF_SYMLINK, -110, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 17, 39, -12, 89, -48, 78, 106, 23, 91, -84, -1, 7, -64, 101, 121, -4, -57, -51, 118, 66, 93, -25, HttpTokens.COLON, TarConstants.LF_BLK, 122, TarConstants.LF_NORMAL, 40, 15, 115, 1, -7, -47, -46, Ascii.EM, -23, -111, -71, 90, -19, 65, 109, -76, -61, -98, -65, 99, -6, Ascii.US, TarConstants.LF_CHR, 96, 71, -119, -16, -106, Ascii.SUB, 95, -109, Base64.padSymbol, TarConstants.LF_CONTIG, TarConstants.LF_GNUTYPE_LONGLINK, -39, -88, -63, Ascii.ESC, -10, 57, -117, -73, 12, 32, -50, -120, 110, -74, 116, -114, -115, 22, 41, -14, -121, -11, -21, 112, -29, -5, 85, -97, -58, 68, 74, 69, 125, -30, 107, 92, 108, 102, -87, -116, -18, -124, 19, -89, Ascii.RS, -99, -36, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 72, -70, 46, -26, -92, -85, 124, -108, 0, 33, -17, -22, -66, -54, 114, 79, 82, -104, Utf8.REPLACEMENT_BYTE, -62, Ascii.DC4, 123, HttpTokens.SEMI_COLON, 84};

    /* renamed from: s, reason: collision with root package name */
    private byte f27884s = -66;

    @Override // org.bouncycastle.crypto.prng.RandomGenerator
    public void addSeedMaterial(long j2) {
        addSeedMaterial(new byte[]{(byte) ((j2 & (-16777216)) >> 24), (byte) ((16711680 & j2) >> 16), (byte) ((65280 & j2) >> 8), (byte) (255 & j2)});
    }

    @Override // org.bouncycastle.crypto.prng.RandomGenerator
    public void addSeedMaterial(byte[] bArr) {
        for (byte b3 : bArr) {
            byte[] bArr2 = this.P;
            byte b4 = this.f27884s;
            byte b5 = this.f27883n;
            byte b6 = bArr2[(b4 + bArr2[b5 & 255] + b3) & 255];
            this.f27884s = b6;
            byte b7 = bArr2[b5 & 255];
            bArr2[b5 & 255] = bArr2[b6 & 255];
            bArr2[b6 & 255] = b7;
            this.f27883n = (byte) ((b5 + 1) & 255);
        }
    }

    @Override // org.bouncycastle.crypto.prng.RandomGenerator
    public void nextBytes(byte[] bArr) {
        nextBytes(bArr, 0, bArr.length);
    }

    @Override // org.bouncycastle.crypto.prng.RandomGenerator
    public void nextBytes(byte[] bArr, int i2, int i3) {
        synchronized (this.P) {
            int i4 = i3 + i2;
            while (i2 != i4) {
                byte[] bArr2 = this.P;
                byte b3 = this.f27884s;
                byte b4 = this.f27883n;
                byte b5 = bArr2[(b3 + bArr2[b4 & 255]) & 255];
                this.f27884s = b5;
                bArr[i2] = bArr2[(bArr2[bArr2[b5 & 255] & 255] + 1) & 255];
                byte b6 = bArr2[b4 & 255];
                bArr2[b4 & 255] = bArr2[b5 & 255];
                bArr2[b5 & 255] = b6;
                this.f27883n = (byte) ((b4 + 1) & 255);
                i2++;
            }
        }
    }
}
