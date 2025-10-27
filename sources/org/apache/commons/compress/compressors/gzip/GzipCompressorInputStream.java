package org.apache.commons.compress.compressors.gzip;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.compress.compressors.CompressorInputStream;

/* loaded from: classes9.dex */
public class GzipCompressorInputStream extends CompressorInputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int FCOMMENT = 16;
    private static final int FEXTRA = 4;
    private static final int FHCRC = 2;
    private static final int FNAME = 8;
    private static final int FRESERVED = 224;
    private final byte[] buf;
    private int bufUsed;
    private final CRC32 crc;
    private final boolean decompressConcatenated;
    private boolean endReached;
    private final InputStream in;
    private Inflater inf;
    private final byte[] oneByte;
    private final GzipParameters parameters;

    public GzipCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    private boolean init(boolean z2) throws IOException {
        int i2 = this.in.read();
        int i3 = this.in.read();
        if (i2 == -1 && !z2) {
            return false;
        }
        if (i2 != 31 || i3 != 139) {
            throw new IOException(z2 ? "Input is not in the .gz format" : "Garbage after a valid .gz stream");
        }
        DataInputStream dataInputStream = new DataInputStream(this.in);
        int unsignedByte = dataInputStream.readUnsignedByte();
        if (unsignedByte != 8) {
            throw new IOException("Unsupported compression method " + unsignedByte + " in the .gz header");
        }
        int unsignedByte2 = dataInputStream.readUnsignedByte();
        if ((unsignedByte2 & 224) != 0) {
            throw new IOException("Reserved flags are set in the .gz header");
        }
        this.parameters.setModificationTime(readLittleEndianInt(dataInputStream) * 1000);
        int unsignedByte3 = dataInputStream.readUnsignedByte();
        if (unsignedByte3 == 2) {
            this.parameters.setCompressionLevel(9);
        } else if (unsignedByte3 == 4) {
            this.parameters.setCompressionLevel(1);
        }
        this.parameters.setOperatingSystem(dataInputStream.readUnsignedByte());
        if ((unsignedByte2 & 4) != 0) {
            int unsignedByte4 = (dataInputStream.readUnsignedByte() << 8) | dataInputStream.readUnsignedByte();
            while (true) {
                int i4 = unsignedByte4 - 1;
                if (unsignedByte4 <= 0) {
                    break;
                }
                dataInputStream.readUnsignedByte();
                unsignedByte4 = i4;
            }
        }
        if ((unsignedByte2 & 8) != 0) {
            this.parameters.setFilename(new String(readToNull(dataInputStream), "ISO-8859-1"));
        }
        if ((unsignedByte2 & 16) != 0) {
            this.parameters.setComment(new String(readToNull(dataInputStream), "ISO-8859-1"));
        }
        if ((unsignedByte2 & 2) != 0) {
            dataInputStream.readShort();
        }
        this.inf.reset();
        this.crc.reset();
        return true;
    }

    public static boolean matches(byte[] bArr, int i2) {
        return i2 >= 2 && bArr[0] == 31 && bArr[1] == -117;
    }

    private long readLittleEndianInt(DataInputStream dataInputStream) throws IOException {
        return dataInputStream.readUnsignedByte() | (dataInputStream.readUnsignedByte() << 8) | (dataInputStream.readUnsignedByte() << 16) | (dataInputStream.readUnsignedByte() << 24);
    }

    private byte[] readToNull(DataInputStream dataInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int unsignedByte = dataInputStream.readUnsignedByte();
            if (unsignedByte == 0) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(unsignedByte);
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Inflater inflater = this.inf;
        if (inflater != null) {
            inflater.end();
            this.inf = null;
        }
        InputStream inputStream = this.in;
        if (inputStream != System.in) {
            inputStream.close();
        }
    }

    public GzipParameters getMetaData() {
        return this.parameters;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (read(this.oneByte, 0, 1) == -1) {
            return -1;
        }
        return this.oneByte[0] & 255;
    }

    public GzipCompressorInputStream(InputStream inputStream, boolean z2) throws IOException {
        this.buf = new byte[8192];
        this.bufUsed = 0;
        this.inf = new Inflater(true);
        this.crc = new CRC32();
        this.endReached = false;
        this.oneByte = new byte[1];
        this.parameters = new GzipParameters();
        if (inputStream.markSupported()) {
            this.in = inputStream;
        } else {
            this.in = new BufferedInputStream(inputStream);
        }
        this.decompressConcatenated = z2;
        init(true);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) throws DataFormatException, IOException {
        if (this.endReached) {
            return -1;
        }
        int i4 = 0;
        while (i3 > 0) {
            if (this.inf.needsInput()) {
                this.in.mark(this.buf.length);
                int i5 = this.in.read(this.buf);
                this.bufUsed = i5;
                if (i5 == -1) {
                    throw new EOFException();
                }
                this.inf.setInput(this.buf, 0, i5);
            }
            try {
                int iInflate = this.inf.inflate(bArr, i2, i3);
                this.crc.update(bArr, i2, iInflate);
                i2 += iInflate;
                i3 -= iInflate;
                i4 += iInflate;
                count(iInflate);
                if (this.inf.finished()) {
                    this.in.reset();
                    long remaining = this.bufUsed - this.inf.getRemaining();
                    if (this.in.skip(remaining) != remaining) {
                        throw new IOException();
                    }
                    this.bufUsed = 0;
                    DataInputStream dataInputStream = new DataInputStream(this.in);
                    if (readLittleEndianInt(dataInputStream) != this.crc.getValue()) {
                        throw new IOException("Gzip-compressed data is corrupt (CRC32 error)");
                    }
                    if (readLittleEndianInt(dataInputStream) != (this.inf.getBytesWritten() & InternalZipConstants.ZIP_64_LIMIT)) {
                        throw new IOException("Gzip-compressed data is corrupt(uncompressed size mismatch)");
                    }
                    if (!this.decompressConcatenated || !init(false)) {
                        this.inf.end();
                        this.inf = null;
                        this.endReached = true;
                        if (i4 == 0) {
                            return -1;
                        }
                        return i4;
                    }
                }
            } catch (DataFormatException unused) {
                throw new IOException("Gzip-compressed data is corrupt");
            }
        }
        return i4;
    }
}
