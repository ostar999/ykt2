package androidx.multidex;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.ZipException;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes.dex */
final class ZipUtil {
    private static final int BUFFER_SIZE = 16384;
    private static final int ENDHDR = 22;
    private static final int ENDSIG = 101010256;

    public static class CentralDirectory {
        long offset;
        long size;
    }

    public static long computeCrcOfCentralDir(RandomAccessFile randomAccessFile, CentralDirectory centralDirectory) throws IOException {
        CRC32 crc32 = new CRC32();
        long j2 = centralDirectory.size;
        randomAccessFile.seek(centralDirectory.offset);
        byte[] bArr = new byte[16384];
        int i2 = randomAccessFile.read(bArr, 0, (int) Math.min(16384L, j2));
        while (i2 != -1) {
            crc32.update(bArr, 0, i2);
            j2 -= i2;
            if (j2 == 0) {
                break;
            }
            i2 = randomAccessFile.read(bArr, 0, (int) Math.min(16384L, j2));
        }
        return crc32.getValue();
    }

    public static CentralDirectory findCentralDirectory(RandomAccessFile randomAccessFile) throws IOException {
        long length = randomAccessFile.length() - 22;
        if (length < 0) {
            throw new ZipException("File too short to be a zip file: " + randomAccessFile.length());
        }
        long j2 = length - 65536;
        long j3 = j2 >= 0 ? j2 : 0L;
        int iReverseBytes = Integer.reverseBytes(ENDSIG);
        do {
            randomAccessFile.seek(length);
            if (randomAccessFile.readInt() == iReverseBytes) {
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                CentralDirectory centralDirectory = new CentralDirectory();
                centralDirectory.size = Integer.reverseBytes(randomAccessFile.readInt()) & InternalZipConstants.ZIP_64_LIMIT;
                centralDirectory.offset = Integer.reverseBytes(randomAccessFile.readInt()) & InternalZipConstants.ZIP_64_LIMIT;
                return centralDirectory;
            }
            length--;
        } while (length >= j3);
        throw new ZipException("End Of Central Directory signature not found");
    }

    public static long getZipCrc(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        try {
            return computeCrcOfCentralDir(randomAccessFile, findCentralDirectory(randomAccessFile));
        } finally {
            randomAccessFile.close();
        }
    }
}
