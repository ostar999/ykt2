package org.apache.commons.compress.archivers.cpio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.ArchiveUtils;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes9.dex */
public class CpioArchiveInputStream extends ArchiveInputStream implements CpioConstants {
    private final byte[] FOUR_BYTES_BUF;
    private final byte[] SIX_BYTES_BUF;
    private final byte[] TWO_BYTES_BUF;
    private final int blockSize;
    private boolean closed;
    private long crc;
    final String encoding;
    private CpioArchiveEntry entry;
    private long entryBytesRead;
    private boolean entryEOF;
    private final InputStream in;
    private final byte[] tmpbuf;
    private final ZipEncoding zipEncoding;

    public CpioArchiveInputStream(InputStream inputStream) {
        this(inputStream, 512, "US-ASCII");
    }

    private void closeEntry() throws IOException {
        while (skip(2147483647L) == 2147483647L) {
        }
    }

    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }

    public static boolean matches(byte[] bArr, int i2) {
        if (i2 < 6) {
            return false;
        }
        byte b3 = bArr[0];
        if (b3 == 113 && (bArr[1] & 255) == 199) {
            return true;
        }
        byte b4 = bArr[1];
        if (b4 == 113 && (b3 & 255) == 199) {
            return true;
        }
        if (b3 != 48 || b4 != 55 || bArr[2] != 48 || bArr[3] != 55 || bArr[4] != 48) {
            return false;
        }
        byte b5 = bArr[5];
        return b5 == 49 || b5 == 50 || b5 == 55;
    }

    private long readAsciiLong(int i2, int i3) throws IOException {
        byte[] bArr = new byte[i2];
        readFully(bArr, 0, i2);
        return Long.parseLong(ArchiveUtils.toAsciiString(bArr), i3);
    }

    private long readBinaryLong(int i2, boolean z2) throws IOException {
        byte[] bArr = new byte[i2];
        readFully(bArr, 0, i2);
        return CpioUtil.byteArray2long(bArr, z2);
    }

    private String readCString(int i2) throws IOException {
        int i3 = i2 - 1;
        byte[] bArr = new byte[i3];
        readFully(bArr, 0, i3);
        this.in.read();
        return this.zipEncoding.decode(bArr);
    }

    private final int readFully(byte[] bArr, int i2, int i3) throws IOException {
        int fully = IOUtils.readFully(this.in, bArr, i2, i3);
        count(fully);
        if (fully >= i3) {
            return fully;
        }
        throw new EOFException();
    }

    private CpioArchiveEntry readNewEntry(boolean z2) throws IOException {
        CpioArchiveEntry cpioArchiveEntry = z2 ? new CpioArchiveEntry((short) 2) : new CpioArchiveEntry((short) 1);
        cpioArchiveEntry.setInode(readAsciiLong(8, 16));
        long asciiLong = readAsciiLong(8, 16);
        if (CpioUtil.fileType(asciiLong) != 0) {
            cpioArchiveEntry.setMode(asciiLong);
        }
        cpioArchiveEntry.setUID(readAsciiLong(8, 16));
        cpioArchiveEntry.setGID(readAsciiLong(8, 16));
        cpioArchiveEntry.setNumberOfLinks(readAsciiLong(8, 16));
        cpioArchiveEntry.setTime(readAsciiLong(8, 16));
        cpioArchiveEntry.setSize(readAsciiLong(8, 16));
        cpioArchiveEntry.setDeviceMaj(readAsciiLong(8, 16));
        cpioArchiveEntry.setDeviceMin(readAsciiLong(8, 16));
        cpioArchiveEntry.setRemoteDeviceMaj(readAsciiLong(8, 16));
        cpioArchiveEntry.setRemoteDeviceMin(readAsciiLong(8, 16));
        long asciiLong2 = readAsciiLong(8, 16);
        cpioArchiveEntry.setChksum(readAsciiLong(8, 16));
        String cString = readCString((int) asciiLong2);
        cpioArchiveEntry.setName(cString);
        if (CpioUtil.fileType(asciiLong) != 0 || cString.equals(CpioConstants.CPIO_TRAILER)) {
            skip(cpioArchiveEntry.getHeaderPadCount());
            return cpioArchiveEntry;
        }
        throw new IOException("Mode 0 only allowed in the trailer. Found entry name: " + ArchiveUtils.sanitize(cString) + " Occured at byte: " + getBytesRead());
    }

    private CpioArchiveEntry readOldAsciiEntry() throws IOException {
        CpioArchiveEntry cpioArchiveEntry = new CpioArchiveEntry((short) 4);
        cpioArchiveEntry.setDevice(readAsciiLong(6, 8));
        cpioArchiveEntry.setInode(readAsciiLong(6, 8));
        long asciiLong = readAsciiLong(6, 8);
        if (CpioUtil.fileType(asciiLong) != 0) {
            cpioArchiveEntry.setMode(asciiLong);
        }
        cpioArchiveEntry.setUID(readAsciiLong(6, 8));
        cpioArchiveEntry.setGID(readAsciiLong(6, 8));
        cpioArchiveEntry.setNumberOfLinks(readAsciiLong(6, 8));
        cpioArchiveEntry.setRemoteDevice(readAsciiLong(6, 8));
        cpioArchiveEntry.setTime(readAsciiLong(11, 8));
        long asciiLong2 = readAsciiLong(6, 8);
        cpioArchiveEntry.setSize(readAsciiLong(11, 8));
        String cString = readCString((int) asciiLong2);
        cpioArchiveEntry.setName(cString);
        if (CpioUtil.fileType(asciiLong) != 0 || cString.equals(CpioConstants.CPIO_TRAILER)) {
            return cpioArchiveEntry;
        }
        throw new IOException("Mode 0 only allowed in the trailer. Found entry: " + ArchiveUtils.sanitize(cString) + " Occured at byte: " + getBytesRead());
    }

    private CpioArchiveEntry readOldBinaryEntry(boolean z2) throws IOException {
        CpioArchiveEntry cpioArchiveEntry = new CpioArchiveEntry((short) 8);
        cpioArchiveEntry.setDevice(readBinaryLong(2, z2));
        cpioArchiveEntry.setInode(readBinaryLong(2, z2));
        long binaryLong = readBinaryLong(2, z2);
        if (CpioUtil.fileType(binaryLong) != 0) {
            cpioArchiveEntry.setMode(binaryLong);
        }
        cpioArchiveEntry.setUID(readBinaryLong(2, z2));
        cpioArchiveEntry.setGID(readBinaryLong(2, z2));
        cpioArchiveEntry.setNumberOfLinks(readBinaryLong(2, z2));
        cpioArchiveEntry.setRemoteDevice(readBinaryLong(2, z2));
        cpioArchiveEntry.setTime(readBinaryLong(4, z2));
        long binaryLong2 = readBinaryLong(2, z2);
        cpioArchiveEntry.setSize(readBinaryLong(4, z2));
        String cString = readCString((int) binaryLong2);
        cpioArchiveEntry.setName(cString);
        if (CpioUtil.fileType(binaryLong) != 0 || cString.equals(CpioConstants.CPIO_TRAILER)) {
            skip(cpioArchiveEntry.getHeaderPadCount());
            return cpioArchiveEntry;
        }
        throw new IOException("Mode 0 only allowed in the trailer. Found entry: " + ArchiveUtils.sanitize(cString) + "Occured at byte: " + getBytesRead());
    }

    private void skip(int i2) throws IOException {
        if (i2 > 0) {
            readFully(this.FOUR_BYTES_BUF, 0, i2);
        }
    }

    private void skipRemainderOfLastBlock() throws IOException {
        long bytesRead = getBytesRead();
        int i2 = this.blockSize;
        long j2 = bytesRead % i2;
        long j3 = j2 == 0 ? 0L : i2 - j2;
        while (j3 > 0) {
            long jSkip = skip(this.blockSize - j2);
            if (jSkip <= 0) {
                return;
            } else {
                j3 -= jSkip;
            }
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        ensureOpen();
        return this.entryEOF ? 0 : 1;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.in.close();
        this.closed = true;
    }

    public CpioArchiveEntry getNextCPIOEntry() throws IOException {
        ensureOpen();
        if (this.entry != null) {
            closeEntry();
        }
        byte[] bArr = this.TWO_BYTES_BUF;
        readFully(bArr, 0, bArr.length);
        if (CpioUtil.byteArray2long(this.TWO_BYTES_BUF, false) == 29127) {
            this.entry = readOldBinaryEntry(false);
        } else if (CpioUtil.byteArray2long(this.TWO_BYTES_BUF, true) == 29127) {
            this.entry = readOldBinaryEntry(true);
        } else {
            byte[] bArr2 = this.TWO_BYTES_BUF;
            System.arraycopy(bArr2, 0, this.SIX_BYTES_BUF, 0, bArr2.length);
            readFully(this.SIX_BYTES_BUF, this.TWO_BYTES_BUF.length, this.FOUR_BYTES_BUF.length);
            String asciiString = ArchiveUtils.toAsciiString(this.SIX_BYTES_BUF);
            if (asciiString.equals(CpioConstants.MAGIC_NEW)) {
                this.entry = readNewEntry(false);
            } else if (asciiString.equals(CpioConstants.MAGIC_NEW_CRC)) {
                this.entry = readNewEntry(true);
            } else {
                if (!asciiString.equals(CpioConstants.MAGIC_OLD_ASCII)) {
                    throw new IOException("Unknown magic [" + asciiString + "]. Occured at byte: " + getBytesRead());
                }
                this.entry = readOldAsciiEntry();
            }
        }
        this.entryBytesRead = 0L;
        this.entryEOF = false;
        this.crc = 0L;
        if (!this.entry.getName().equals(CpioConstants.CPIO_TRAILER)) {
            return this.entry;
        }
        this.entryEOF = true;
        skipRemainderOfLastBlock();
        return null;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveInputStream
    public ArchiveEntry getNextEntry() throws IOException {
        return getNextCPIOEntry();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        ensureOpen();
        if (i2 < 0 || i3 < 0 || i2 > bArr.length - i3) {
            throw new IndexOutOfBoundsException();
        }
        if (i3 == 0) {
            return 0;
        }
        CpioArchiveEntry cpioArchiveEntry = this.entry;
        if (cpioArchiveEntry == null || this.entryEOF) {
            return -1;
        }
        if (this.entryBytesRead == cpioArchiveEntry.getSize()) {
            skip(this.entry.getDataPadCount());
            this.entryEOF = true;
            if (this.entry.getFormat() != 2 || this.crc == this.entry.getChksum()) {
                return -1;
            }
            throw new IOException("CRC Error. Occured at byte: " + getBytesRead());
        }
        int iMin = (int) Math.min(i3, this.entry.getSize() - this.entryBytesRead);
        if (iMin < 0) {
            return -1;
        }
        int fully = readFully(bArr, i2, iMin);
        if (this.entry.getFormat() == 2) {
            for (int i4 = 0; i4 < fully; i4++) {
                this.crc += bArr[i4] & 255;
            }
        }
        this.entryBytesRead += fully;
        return fully;
    }

    public CpioArchiveInputStream(InputStream inputStream, String str) {
        this(inputStream, 512, str);
    }

    @Override // java.io.InputStream
    public long skip(long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("negative skip length");
        }
        ensureOpen();
        int iMin = (int) Math.min(j2, 2147483647L);
        int i2 = 0;
        while (true) {
            if (i2 >= iMin) {
                break;
            }
            int length = iMin - i2;
            byte[] bArr = this.tmpbuf;
            if (length > bArr.length) {
                length = bArr.length;
            }
            int i3 = read(bArr, 0, length);
            if (i3 == -1) {
                this.entryEOF = true;
                break;
            }
            i2 += i3;
        }
        return i2;
    }

    public CpioArchiveInputStream(InputStream inputStream, int i2) {
        this(inputStream, i2, "US-ASCII");
    }

    public CpioArchiveInputStream(InputStream inputStream, int i2, String str) {
        this.closed = false;
        this.entryBytesRead = 0L;
        this.entryEOF = false;
        this.tmpbuf = new byte[4096];
        this.crc = 0L;
        this.TWO_BYTES_BUF = new byte[2];
        this.FOUR_BYTES_BUF = new byte[4];
        this.SIX_BYTES_BUF = new byte[6];
        this.in = inputStream;
        this.blockSize = i2;
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
    }
}
