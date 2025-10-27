package org.bouncycastle.util.encoders;

import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes9.dex */
public class HexTranslator implements Translator {
    private static final byte[] hexTable = {TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 97, 98, 99, 100, 101, 102};

    @Override // org.bouncycastle.util.encoders.Translator
    public int decode(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5 = i3 / 2;
        for (int i6 = 0; i6 < i5; i6++) {
            int i7 = (i6 * 2) + i2;
            byte b3 = bArr[i7];
            byte b4 = bArr[i7 + 1];
            if (b3 < 97) {
                bArr2[i4] = (byte) ((b3 - 48) << 4);
            } else {
                bArr2[i4] = (byte) (((b3 - 97) + 10) << 4);
            }
            if (b4 < 97) {
                bArr2[i4] = (byte) (bArr2[i4] + ((byte) (b4 - 48)));
            } else {
                bArr2[i4] = (byte) (bArr2[i4] + ((byte) ((b4 - 97) + 10)));
            }
            i4++;
        }
        return i5;
    }

    @Override // org.bouncycastle.util.encoders.Translator
    public int encode(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5 = 0;
        int i6 = 0;
        while (i5 < i3) {
            int i7 = i4 + i6;
            byte[] bArr3 = hexTable;
            bArr2[i7] = bArr3[(bArr[i2] >> 4) & 15];
            bArr2[i7 + 1] = bArr3[bArr[i2] & 15];
            i2++;
            i5++;
            i6 += 2;
        }
        return i3 * 2;
    }

    @Override // org.bouncycastle.util.encoders.Translator
    public int getDecodedBlockSize() {
        return 1;
    }

    @Override // org.bouncycastle.util.encoders.Translator
    public int getEncodedBlockSize() {
        return 2;
    }
}
