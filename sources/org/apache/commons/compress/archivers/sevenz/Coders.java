package org.apache.commons.compress.archivers.sevenz;

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.tukaani.xz.ARMOptions;
import org.tukaani.xz.ARMThumbOptions;
import org.tukaani.xz.FilterOptions;
import org.tukaani.xz.FinishableWrapperOutputStream;
import org.tukaani.xz.IA64Options;
import org.tukaani.xz.LZMAInputStream;
import org.tukaani.xz.PowerPCOptions;
import org.tukaani.xz.SPARCOptions;
import org.tukaani.xz.X86Options;

/* loaded from: classes9.dex */
class Coders {
    private static final Map<SevenZMethod, CoderBase> CODER_MAP = new HashMap<SevenZMethod, CoderBase>() { // from class: org.apache.commons.compress.archivers.sevenz.Coders.1
        private static final long serialVersionUID = 1664829131806520867L;

        {
            put(SevenZMethod.COPY, new CopyDecoder());
            put(SevenZMethod.LZMA, new LZMADecoder());
            put(SevenZMethod.LZMA2, new LZMA2Decoder());
            put(SevenZMethod.DEFLATE, new DeflateDecoder());
            put(SevenZMethod.BZIP2, new BZIP2Decoder());
            put(SevenZMethod.AES256SHA256, new AES256SHA256Decoder());
            put(SevenZMethod.BCJ_X86_FILTER, new BCJDecoder(new X86Options()));
            put(SevenZMethod.BCJ_PPC_FILTER, new BCJDecoder(new PowerPCOptions()));
            put(SevenZMethod.BCJ_IA64_FILTER, new BCJDecoder(new IA64Options()));
            put(SevenZMethod.BCJ_ARM_FILTER, new BCJDecoder(new ARMOptions()));
            put(SevenZMethod.BCJ_ARM_THUMB_FILTER, new BCJDecoder(new ARMThumbOptions()));
            put(SevenZMethod.BCJ_SPARC_FILTER, new BCJDecoder(new SPARCOptions()));
            put(SevenZMethod.DELTA_FILTER, new DeltaDecoder());
        }
    };

    public static class BCJDecoder extends CoderBase {
        private final FilterOptions opts;

        public BCJDecoder(FilterOptions filterOptions) {
            super(new Class[0]);
            this.opts = filterOptions;
        }

        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public InputStream decode(String str, InputStream inputStream, long j2, Coder coder, byte[] bArr) throws IOException {
            try {
                return this.opts.getInputStream(inputStream);
            } catch (AssertionError e2) {
                throw new IOException("BCJ filter used in " + str + " needs XZ for Java > 1.4 - see http://commons.apache.org/proper/commons-compress/limitations.html#7Z", e2);
            }
        }

        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public OutputStream encode(OutputStream outputStream, Object obj) {
            return new FilterOutputStream(this.opts.getOutputStream(new FinishableWrapperOutputStream(outputStream))) { // from class: org.apache.commons.compress.archivers.sevenz.Coders.BCJDecoder.1
                @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
                public void flush() {
                }
            };
        }
    }

    public static class BZIP2Decoder extends CoderBase {
        public BZIP2Decoder() {
            super(Number.class);
        }

        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public InputStream decode(String str, InputStream inputStream, long j2, Coder coder, byte[] bArr) throws IOException {
            return new BZip2CompressorInputStream(inputStream);
        }

        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public OutputStream encode(OutputStream outputStream, Object obj) throws IOException {
            return new BZip2CompressorOutputStream(outputStream, CoderBase.numberOptionOrDefault(obj, 9));
        }
    }

    public static class CopyDecoder extends CoderBase {
        public CopyDecoder() {
            super(new Class[0]);
        }

        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public InputStream decode(String str, InputStream inputStream, long j2, Coder coder, byte[] bArr) throws IOException {
            return inputStream;
        }

        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public OutputStream encode(OutputStream outputStream, Object obj) {
            return outputStream;
        }
    }

    public static class DeflateDecoder extends CoderBase {
        public DeflateDecoder() {
            super(Number.class);
        }

        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public InputStream decode(String str, InputStream inputStream, long j2, Coder coder, byte[] bArr) throws IOException {
            final Inflater inflater = new Inflater(true);
            final InflaterInputStream inflaterInputStream = new InflaterInputStream(new DummyByteAddingInputStream(inputStream), inflater);
            return new InputStream() { // from class: org.apache.commons.compress.archivers.sevenz.Coders.DeflateDecoder.1
                @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    try {
                        inflaterInputStream.close();
                    } finally {
                        inflater.end();
                    }
                }

                @Override // java.io.InputStream
                public int read() throws IOException {
                    return inflaterInputStream.read();
                }

                @Override // java.io.InputStream
                public int read(byte[] bArr2, int i2, int i3) throws IOException {
                    return inflaterInputStream.read(bArr2, i2, i3);
                }

                @Override // java.io.InputStream
                public int read(byte[] bArr2) throws IOException {
                    return inflaterInputStream.read(bArr2);
                }
            };
        }

        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public OutputStream encode(OutputStream outputStream, Object obj) {
            final Deflater deflater = new Deflater(CoderBase.numberOptionOrDefault(obj, 9), true);
            final DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputStream, deflater);
            return new OutputStream() { // from class: org.apache.commons.compress.archivers.sevenz.Coders.DeflateDecoder.2
                @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                    try {
                        deflaterOutputStream.close();
                    } finally {
                        deflater.end();
                    }
                }

                @Override // java.io.OutputStream
                public void write(int i2) throws IOException {
                    deflaterOutputStream.write(i2);
                }

                @Override // java.io.OutputStream
                public void write(byte[] bArr) throws IOException {
                    deflaterOutputStream.write(bArr);
                }

                @Override // java.io.OutputStream
                public void write(byte[] bArr, int i2, int i3) throws IOException {
                    deflaterOutputStream.write(bArr, i2, i3);
                }
            };
        }
    }

    public static class DummyByteAddingInputStream extends FilterInputStream {
        private boolean addDummyByte;

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws IOException {
            int i2 = super.read();
            if (i2 != -1 || !this.addDummyByte) {
                return i2;
            }
            this.addDummyByte = false;
            return 0;
        }

        private DummyByteAddingInputStream(InputStream inputStream) {
            super(inputStream);
            this.addDummyByte = true;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) throws IOException {
            int i4 = super.read(bArr, i2, i3);
            if (i4 != -1 || !this.addDummyByte) {
                return i4;
            }
            this.addDummyByte = false;
            bArr[i2] = 0;
            return 1;
        }
    }

    public static class LZMADecoder extends CoderBase {
        public LZMADecoder() {
            super(new Class[0]);
        }

        @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
        public InputStream decode(String str, InputStream inputStream, long j2, Coder coder, byte[] bArr) throws IOException {
            byte[] bArr2 = coder.properties;
            byte b3 = bArr2[0];
            long j3 = bArr2[1];
            for (int i2 = 1; i2 < 4; i2++) {
                j3 |= (coder.properties[r5] & 255) << (i2 * 8);
            }
            if (j3 <= 2147483632) {
                return new LZMAInputStream(inputStream, j2, b3, (int) j3);
            }
            throw new IOException("Dictionary larger than 4GiB maximum size used in " + str);
        }
    }

    public static InputStream addDecoder(String str, InputStream inputStream, long j2, Coder coder, byte[] bArr) throws IOException {
        CoderBase coderBaseFindByMethod = findByMethod(SevenZMethod.byId(coder.decompressionMethodId));
        if (coderBaseFindByMethod != null) {
            return coderBaseFindByMethod.decode(str, inputStream, j2, coder, bArr);
        }
        throw new IOException("Unsupported compression method " + Arrays.toString(coder.decompressionMethodId) + " used in " + str);
    }

    public static OutputStream addEncoder(OutputStream outputStream, SevenZMethod sevenZMethod, Object obj) throws IOException {
        CoderBase coderBaseFindByMethod = findByMethod(sevenZMethod);
        if (coderBaseFindByMethod != null) {
            return coderBaseFindByMethod.encode(outputStream, obj);
        }
        throw new IOException("Unsupported compression method " + sevenZMethod);
    }

    public static CoderBase findByMethod(SevenZMethod sevenZMethod) {
        return CODER_MAP.get(sevenZMethod);
    }
}
