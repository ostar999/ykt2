package org.apache.commons.compress.compressors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
import org.apache.commons.compress.compressors.lzma.LZMAUtils;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorInputStream;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorOutputStream;
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorInputStream;
import org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.apache.commons.compress.compressors.xz.XZUtils;
import org.apache.commons.compress.compressors.z.ZCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

/* loaded from: classes9.dex */
public class CompressorStreamFactory {
    public static final String BZIP2 = "bzip2";
    public static final String DEFLATE = "deflate";
    public static final String GZIP = "gz";
    public static final String LZMA = "lzma";
    public static final String PACK200 = "pack200";
    public static final String SNAPPY_FRAMED = "snappy-framed";
    public static final String SNAPPY_RAW = "snappy-raw";
    public static final String XZ = "xz";
    public static final String Z = "z";
    private volatile boolean decompressConcatenated;
    private final Boolean decompressUntilEOF;

    public CompressorStreamFactory() {
        this.decompressConcatenated = false;
        this.decompressUntilEOF = null;
    }

    public CompressorInputStream createCompressorInputStream(InputStream inputStream) throws CompressorException, IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Stream must not be null.");
        }
        if (!inputStream.markSupported()) {
            throw new IllegalArgumentException("Mark is not supported.");
        }
        byte[] bArr = new byte[12];
        inputStream.mark(12);
        try {
            int fully = IOUtils.readFully(inputStream, bArr);
            inputStream.reset();
            if (BZip2CompressorInputStream.matches(bArr, fully)) {
                return new BZip2CompressorInputStream(inputStream, this.decompressConcatenated);
            }
            if (GzipCompressorInputStream.matches(bArr, fully)) {
                return new GzipCompressorInputStream(inputStream, this.decompressConcatenated);
            }
            if (Pack200CompressorInputStream.matches(bArr, fully)) {
                return new Pack200CompressorInputStream(inputStream);
            }
            if (FramedSnappyCompressorInputStream.matches(bArr, fully)) {
                return new FramedSnappyCompressorInputStream(inputStream);
            }
            if (ZCompressorInputStream.matches(bArr, fully)) {
                return new ZCompressorInputStream(inputStream);
            }
            if (DeflateCompressorInputStream.matches(bArr, fully)) {
                return new DeflateCompressorInputStream(inputStream);
            }
            if (XZUtils.matches(bArr, fully) && XZUtils.isXZCompressionAvailable()) {
                return new XZCompressorInputStream(inputStream, this.decompressConcatenated);
            }
            if (LZMAUtils.matches(bArr, fully) && LZMAUtils.isLZMACompressionAvailable()) {
                return new LZMACompressorInputStream(inputStream);
            }
            throw new CompressorException("No Compressor found for the stream signature.");
        } catch (IOException e2) {
            throw new CompressorException("Failed to detect Compressor from InputStream.", e2);
        }
    }

    public CompressorOutputStream createCompressorOutputStream(String str, OutputStream outputStream) throws CompressorException {
        if (str == null || outputStream == null) {
            throw new IllegalArgumentException("Compressor name and stream must not be null.");
        }
        try {
            if (GZIP.equalsIgnoreCase(str)) {
                return new GzipCompressorOutputStream(outputStream);
            }
            if (BZIP2.equalsIgnoreCase(str)) {
                return new BZip2CompressorOutputStream(outputStream);
            }
            if (XZ.equalsIgnoreCase(str)) {
                return new XZCompressorOutputStream(outputStream);
            }
            if (PACK200.equalsIgnoreCase(str)) {
                return new Pack200CompressorOutputStream(outputStream);
            }
            if (DEFLATE.equalsIgnoreCase(str)) {
                return new DeflateCompressorOutputStream(outputStream);
            }
            throw new CompressorException("Compressor: " + str + " not found.");
        } catch (IOException e2) {
            throw new CompressorException("Could not create CompressorOutputStream", e2);
        }
    }

    public boolean getDecompressConcatenated() {
        return this.decompressConcatenated;
    }

    @Deprecated
    public void setDecompressConcatenated(boolean z2) {
        if (this.decompressUntilEOF != null) {
            throw new IllegalStateException("Cannot override the setting defined by the constructor");
        }
        this.decompressConcatenated = z2;
    }

    public CompressorStreamFactory(boolean z2) {
        this.decompressConcatenated = false;
        this.decompressUntilEOF = Boolean.valueOf(z2);
        this.decompressConcatenated = z2;
    }

    public CompressorInputStream createCompressorInputStream(String str, InputStream inputStream) throws CompressorException {
        if (str != null && inputStream != null) {
            try {
                if (GZIP.equalsIgnoreCase(str)) {
                    return new GzipCompressorInputStream(inputStream, this.decompressConcatenated);
                }
                if (BZIP2.equalsIgnoreCase(str)) {
                    return new BZip2CompressorInputStream(inputStream, this.decompressConcatenated);
                }
                if (XZ.equalsIgnoreCase(str)) {
                    return new XZCompressorInputStream(inputStream, this.decompressConcatenated);
                }
                if (LZMA.equalsIgnoreCase(str)) {
                    return new LZMACompressorInputStream(inputStream);
                }
                if (PACK200.equalsIgnoreCase(str)) {
                    return new Pack200CompressorInputStream(inputStream);
                }
                if (SNAPPY_RAW.equalsIgnoreCase(str)) {
                    return new SnappyCompressorInputStream(inputStream);
                }
                if (SNAPPY_FRAMED.equalsIgnoreCase(str)) {
                    return new FramedSnappyCompressorInputStream(inputStream);
                }
                if ("z".equalsIgnoreCase(str)) {
                    return new ZCompressorInputStream(inputStream);
                }
                if (DEFLATE.equalsIgnoreCase(str)) {
                    return new DeflateCompressorInputStream(inputStream);
                }
                throw new CompressorException("Compressor: " + str + " not found.");
            } catch (IOException e2) {
                throw new CompressorException("Could not create CompressorInputStream.", e2);
            }
        }
        throw new IllegalArgumentException("Compressor name and stream must not be null.");
    }
}
