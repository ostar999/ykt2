package org.bouncycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes9.dex */
public class HexEncoder implements Encoder {
    protected final byte[] encodingTable = {TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 97, 98, 99, 100, 101, 102};
    protected final byte[] decodingTable = new byte[128];

    public HexEncoder() {
        initialiseDecodingTable();
    }

    private boolean ignore(char c3) {
        return c3 == '\n' || c3 == '\r' || c3 == '\t' || c3 == ' ';
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int decode(String str, OutputStream outputStream) throws IOException {
        int length = str.length();
        while (length > 0 && ignore(str.charAt(length - 1))) {
            length--;
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            while (i2 < length && ignore(str.charAt(i2))) {
                i2++;
            }
            int i4 = i2 + 1;
            byte b3 = this.decodingTable[str.charAt(i2)];
            while (i4 < length && ignore(str.charAt(i4))) {
                i4++;
            }
            outputStream.write((b3 << 4) | this.decodingTable[str.charAt(i4)]);
            i3++;
            i2 = i4 + 1;
        }
        return i3;
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int decode(byte[] bArr, int i2, int i3, OutputStream outputStream) throws IOException {
        int i4 = i3 + i2;
        while (i4 > i2 && ignore((char) bArr[i4 - 1])) {
            i4--;
        }
        int i5 = 0;
        while (i2 < i4) {
            while (i2 < i4 && ignore((char) bArr[i2])) {
                i2++;
            }
            int i6 = i2 + 1;
            byte b3 = this.decodingTable[bArr[i2]];
            while (i6 < i4 && ignore((char) bArr[i6])) {
                i6++;
            }
            outputStream.write((b3 << 4) | this.decodingTable[bArr[i6]]);
            i5++;
            i2 = i6 + 1;
        }
        return i5;
    }

    @Override // org.bouncycastle.util.encoders.Encoder
    public int encode(byte[] bArr, int i2, int i3, OutputStream outputStream) throws IOException {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            int i5 = bArr[i4] & 255;
            outputStream.write(this.encodingTable[i5 >>> 4]);
            outputStream.write(this.encodingTable[i5 & 15]);
        }
        return i3 * 2;
    }

    public void initialiseDecodingTable() {
        int i2 = 0;
        while (true) {
            byte[] bArr = this.encodingTable;
            if (i2 >= bArr.length) {
                byte[] bArr2 = this.decodingTable;
                bArr2[65] = bArr2[97];
                bArr2[66] = bArr2[98];
                bArr2[67] = bArr2[99];
                bArr2[68] = bArr2[100];
                bArr2[69] = bArr2[101];
                bArr2[70] = bArr2[102];
                return;
            }
            this.decodingTable[bArr[i2]] = (byte) i2;
            i2++;
        }
    }
}
