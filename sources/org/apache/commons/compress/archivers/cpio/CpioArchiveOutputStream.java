package org.apache.commons.compress.archivers.cpio;

import android.support.v4.media.session.PlaybackStateCompat;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import okhttp3.internal.ws.WebSocketProtocol;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.utils.ArchiveUtils;

/* loaded from: classes9.dex */
public class CpioArchiveOutputStream extends ArchiveOutputStream implements CpioConstants {
    private final int blockSize;
    private boolean closed;
    private long crc;
    final String encoding;
    private CpioArchiveEntry entry;
    private final short entryFormat;
    private boolean finished;
    private final HashMap<String, CpioArchiveEntry> names;
    private long nextArtificalDeviceAndInode;
    private final OutputStream out;
    private long written;
    private final ZipEncoding zipEncoding;

    public CpioArchiveOutputStream(OutputStream outputStream, short s2) {
        this(outputStream, s2, 512, "US-ASCII");
    }

    private void ensureOpen() throws IOException {
        if (this.closed) {
            throw new IOException("Stream closed");
        }
    }

    private void pad(int i2) throws IOException {
        if (i2 > 0) {
            this.out.write(new byte[i2]);
            count(i2);
        }
    }

    private void writeAsciiLong(long j2, int i2, int i3) throws IOException {
        String strSubstring;
        StringBuilder sb = new StringBuilder();
        if (i3 == 16) {
            sb.append(Long.toHexString(j2));
        } else if (i3 == 8) {
            sb.append(Long.toOctalString(j2));
        } else {
            sb.append(Long.toString(j2));
        }
        if (sb.length() <= i2) {
            long length = i2 - sb.length();
            for (int i4 = 0; i4 < length; i4++) {
                sb.insert(0, "0");
            }
            strSubstring = sb.toString();
        } else {
            strSubstring = sb.substring(sb.length() - i2);
        }
        byte[] asciiBytes = ArchiveUtils.toAsciiBytes(strSubstring);
        this.out.write(asciiBytes);
        count(asciiBytes.length);
    }

    private void writeBinaryLong(long j2, int i2, boolean z2) throws IOException {
        byte[] bArrLong2byteArray = CpioUtil.long2byteArray(j2, i2, z2);
        this.out.write(bArrLong2byteArray);
        count(bArrLong2byteArray.length);
    }

    private void writeCString(String str) throws IOException {
        ByteBuffer byteBufferEncode = this.zipEncoding.encode(str);
        int iLimit = byteBufferEncode.limit() - byteBufferEncode.position();
        this.out.write(byteBufferEncode.array(), byteBufferEncode.arrayOffset(), iLimit);
        this.out.write(0);
        count(iLimit + 1);
    }

    private void writeHeader(CpioArchiveEntry cpioArchiveEntry) throws IOException {
        short format = cpioArchiveEntry.getFormat();
        if (format == 1) {
            this.out.write(ArchiveUtils.toAsciiBytes(CpioConstants.MAGIC_NEW));
            count(6);
            writeNewEntry(cpioArchiveEntry);
            return;
        }
        if (format == 2) {
            this.out.write(ArchiveUtils.toAsciiBytes(CpioConstants.MAGIC_NEW_CRC));
            count(6);
            writeNewEntry(cpioArchiveEntry);
        } else if (format == 4) {
            this.out.write(ArchiveUtils.toAsciiBytes(CpioConstants.MAGIC_OLD_ASCII));
            count(6);
            writeOldAsciiEntry(cpioArchiveEntry);
        } else if (format == 8) {
            writeBinaryLong(29127L, 2, true);
            writeOldBinaryEntry(cpioArchiveEntry, true);
        } else {
            throw new IOException("unknown format " + ((int) cpioArchiveEntry.getFormat()));
        }
    }

    private void writeNewEntry(CpioArchiveEntry cpioArchiveEntry) throws IOException {
        long inode = cpioArchiveEntry.getInode();
        long deviceMin = cpioArchiveEntry.getDeviceMin();
        if (CpioConstants.CPIO_TRAILER.equals(cpioArchiveEntry.getName())) {
            inode = 0;
            deviceMin = 0;
        } else if (inode == 0 && deviceMin == 0) {
            long j2 = this.nextArtificalDeviceAndInode;
            this.nextArtificalDeviceAndInode = 1 + j2;
            deviceMin = (-1) & (j2 >> 32);
            inode = j2 & (-1);
        } else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, (IjkMediaMeta.AV_CH_WIDE_RIGHT * deviceMin) + inode) + 1;
        }
        writeAsciiLong(inode, 8, 16);
        writeAsciiLong(cpioArchiveEntry.getMode(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getUID(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getGID(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getNumberOfLinks(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getTime(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getSize(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getDeviceMaj(), 8, 16);
        writeAsciiLong(deviceMin, 8, 16);
        writeAsciiLong(cpioArchiveEntry.getRemoteDeviceMaj(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getRemoteDeviceMin(), 8, 16);
        writeAsciiLong(cpioArchiveEntry.getName().length() + 1, 8, 16);
        writeAsciiLong(cpioArchiveEntry.getChksum(), 8, 16);
        writeCString(cpioArchiveEntry.getName());
        pad(cpioArchiveEntry.getHeaderPadCount());
    }

    private void writeOldAsciiEntry(CpioArchiveEntry cpioArchiveEntry) throws IOException {
        long inode = cpioArchiveEntry.getInode();
        long device = cpioArchiveEntry.getDevice();
        if (CpioConstants.CPIO_TRAILER.equals(cpioArchiveEntry.getName())) {
            inode = 0;
            device = 0;
        } else if (inode == 0 && device == 0) {
            long j2 = this.nextArtificalDeviceAndInode;
            this.nextArtificalDeviceAndInode = 1 + j2;
            device = 262143 & (j2 >> 18);
            inode = j2 & 262143;
        } else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, (PlaybackStateCompat.ACTION_SET_REPEAT_MODE * device) + inode) + 1;
        }
        writeAsciiLong(device, 6, 8);
        writeAsciiLong(inode, 6, 8);
        writeAsciiLong(cpioArchiveEntry.getMode(), 6, 8);
        writeAsciiLong(cpioArchiveEntry.getUID(), 6, 8);
        writeAsciiLong(cpioArchiveEntry.getGID(), 6, 8);
        writeAsciiLong(cpioArchiveEntry.getNumberOfLinks(), 6, 8);
        writeAsciiLong(cpioArchiveEntry.getRemoteDevice(), 6, 8);
        writeAsciiLong(cpioArchiveEntry.getTime(), 11, 8);
        writeAsciiLong(cpioArchiveEntry.getName().length() + 1, 6, 8);
        writeAsciiLong(cpioArchiveEntry.getSize(), 11, 8);
        writeCString(cpioArchiveEntry.getName());
    }

    private void writeOldBinaryEntry(CpioArchiveEntry cpioArchiveEntry, boolean z2) throws IOException {
        long inode = cpioArchiveEntry.getInode();
        long device = cpioArchiveEntry.getDevice();
        if (CpioConstants.CPIO_TRAILER.equals(cpioArchiveEntry.getName())) {
            inode = 0;
            device = 0;
        } else if (inode == 0 && device == 0) {
            long j2 = this.nextArtificalDeviceAndInode;
            long j3 = j2 & WebSocketProtocol.PAYLOAD_SHORT_MAX;
            this.nextArtificalDeviceAndInode = 1 + j2;
            device = WebSocketProtocol.PAYLOAD_SHORT_MAX & (j2 >> 16);
            inode = j3;
        } else {
            this.nextArtificalDeviceAndInode = Math.max(this.nextArtificalDeviceAndInode, (65536 * device) + inode) + 1;
        }
        writeBinaryLong(device, 2, z2);
        writeBinaryLong(inode, 2, z2);
        writeBinaryLong(cpioArchiveEntry.getMode(), 2, z2);
        writeBinaryLong(cpioArchiveEntry.getUID(), 2, z2);
        writeBinaryLong(cpioArchiveEntry.getGID(), 2, z2);
        writeBinaryLong(cpioArchiveEntry.getNumberOfLinks(), 2, z2);
        writeBinaryLong(cpioArchiveEntry.getRemoteDevice(), 2, z2);
        writeBinaryLong(cpioArchiveEntry.getTime(), 4, z2);
        writeBinaryLong(cpioArchiveEntry.getName().length() + 1, 2, z2);
        writeBinaryLong(cpioArchiveEntry.getSize(), 4, z2);
        writeCString(cpioArchiveEntry.getName());
        pad(cpioArchiveEntry.getHeaderPadCount());
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        if (this.closed) {
            return;
        }
        this.out.close();
        this.closed = true;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        ensureOpen();
        CpioArchiveEntry cpioArchiveEntry = this.entry;
        if (cpioArchiveEntry == null) {
            throw new IOException("Trying to close non-existent entry");
        }
        if (cpioArchiveEntry.getSize() != this.written) {
            throw new IOException("invalid entry size (expected " + this.entry.getSize() + " but got " + this.written + " bytes)");
        }
        pad(this.entry.getDataPadCount());
        if (this.entry.getFormat() == 2 && this.crc != this.entry.getChksum()) {
            throw new IOException("CRC Error");
        }
        this.entry = null;
        this.crc = 0L;
        this.written = 0L;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public ArchiveEntry createArchiveEntry(File file, String str) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new CpioArchiveEntry(file, str);
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void finish() throws IOException {
        ensureOpen();
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        if (this.entry != null) {
            throw new IOException("This archive contains unclosed entries.");
        }
        CpioArchiveEntry cpioArchiveEntry = new CpioArchiveEntry(this.entryFormat);
        this.entry = cpioArchiveEntry;
        cpioArchiveEntry.setName(CpioConstants.CPIO_TRAILER);
        this.entry.setNumberOfLinks(1L);
        writeHeader(this.entry);
        closeArchiveEntry();
        long bytesWritten = getBytesWritten();
        int i2 = this.blockSize;
        int i3 = (int) (bytesWritten % i2);
        if (i3 != 0) {
            pad(i2 - i3);
        }
        this.finished = true;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        CpioArchiveEntry cpioArchiveEntry = (CpioArchiveEntry) archiveEntry;
        ensureOpen();
        if (this.entry != null) {
            closeArchiveEntry();
        }
        if (cpioArchiveEntry.getTime() == -1) {
            cpioArchiveEntry.setTime(System.currentTimeMillis() / 1000);
        }
        short format = cpioArchiveEntry.getFormat();
        if (format != this.entryFormat) {
            throw new IOException("Header format: " + ((int) format) + " does not match existing format: " + ((int) this.entryFormat));
        }
        if (this.names.put(cpioArchiveEntry.getName(), cpioArchiveEntry) == null) {
            writeHeader(cpioArchiveEntry);
            this.entry = cpioArchiveEntry;
            this.written = 0L;
        } else {
            throw new IOException("duplicate entry: " + cpioArchiveEntry.getName());
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) throws IOException {
        ensureOpen();
        if (i2 < 0 || i3 < 0 || i2 > bArr.length - i3) {
            throw new IndexOutOfBoundsException();
        }
        if (i3 == 0) {
            return;
        }
        CpioArchiveEntry cpioArchiveEntry = this.entry;
        if (cpioArchiveEntry == null) {
            throw new IOException("no current CPIO entry");
        }
        long j2 = i3;
        if (this.written + j2 > cpioArchiveEntry.getSize()) {
            throw new IOException("attempt to write past end of STORED entry");
        }
        this.out.write(bArr, i2, i3);
        this.written += j2;
        if (this.entry.getFormat() == 2) {
            for (int i4 = 0; i4 < i3; i4++) {
                this.crc += bArr[i4] & 255;
            }
        }
        count(i3);
    }

    public CpioArchiveOutputStream(OutputStream outputStream, short s2, int i2) {
        this(outputStream, s2, i2, "US-ASCII");
    }

    public CpioArchiveOutputStream(OutputStream outputStream, short s2, int i2, String str) {
        this.closed = false;
        this.names = new HashMap<>();
        this.crc = 0L;
        this.nextArtificalDeviceAndInode = 1L;
        this.out = outputStream;
        if (s2 != 1 && s2 != 2 && s2 != 4 && s2 != 8) {
            throw new IllegalArgumentException("Unknown format: " + ((int) s2));
        }
        this.entryFormat = s2;
        this.blockSize = i2;
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
    }

    public CpioArchiveOutputStream(OutputStream outputStream) {
        this(outputStream, (short) 1);
    }

    public CpioArchiveOutputStream(OutputStream outputStream, String str) {
        this(outputStream, (short) 1, 512, str);
    }
}
