package org.apache.commons.compress.archivers.dump;

import androidx.core.view.MotionEventCompat;
import com.google.common.base.Ascii;
import java.io.IOException;
import org.apache.commons.compress.archivers.zip.ZipEncoding;

/* loaded from: classes9.dex */
class DumpArchiveUtil {
    private DumpArchiveUtil() {
    }

    public static int calculateChecksum(byte[] bArr) {
        int iConvert32 = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            iConvert32 += convert32(bArr, i2 * 4);
        }
        return DumpArchiveConstants.CHECKSUM - (iConvert32 - convert32(bArr, 28));
    }

    public static final int convert16(byte[] bArr, int i2) {
        return ((bArr[i2 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + 0 + (bArr[i2] & 255);
    }

    public static final int convert32(byte[] bArr, int i2) {
        return (bArr[i2 + 3] << Ascii.CAN) + ((bArr[i2 + 2] << 16) & 16711680) + ((bArr[i2 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) + (bArr[i2] & 255);
    }

    public static final long convert64(byte[] bArr, int i2) {
        return (bArr[i2 + 7] << 56) + 0 + ((bArr[i2 + 6] << 48) & 71776119061217280L) + ((bArr[i2 + 5] << 40) & 280375465082880L) + ((bArr[i2 + 4] << 32) & 1095216660480L) + ((bArr[i2 + 3] << 24) & 4278190080L) + ((bArr[i2 + 2] << 16) & 16711680) + ((bArr[i2 + 1] << 8) & 65280) + (bArr[i2] & 255);
    }

    public static String decode(ZipEncoding zipEncoding, byte[] bArr, int i2, int i3) throws IOException {
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        return zipEncoding.decode(bArr2);
    }

    public static final int getIno(byte[] bArr) {
        return convert32(bArr, 20);
    }

    public static final boolean verify(byte[] bArr) {
        return convert32(bArr, 24) == 60012 && convert32(bArr, 28) == calculateChecksum(bArr);
    }
}
