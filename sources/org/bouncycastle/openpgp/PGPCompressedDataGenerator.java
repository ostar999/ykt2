package org.bouncycastle.openpgp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import org.bouncycastle.apache.bzip2.CBZip2OutputStream;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;

/* loaded from: classes9.dex */
public class PGPCompressedDataGenerator implements CompressionAlgorithmTags, StreamGenerator {
    private int algorithm;
    private int compression;
    private OutputStream dOut;
    private OutputStream out;
    private BCPGOutputStream pkOut;

    public PGPCompressedDataGenerator(int i2) {
        this(i2, -1);
    }

    public PGPCompressedDataGenerator(int i2, int i3) {
        if (i2 != 0 && i2 != 1 && i2 != 2 && i2 != 3) {
            throw new IllegalArgumentException("unknown compression algorithm");
        }
        if (i3 == -1 || (i3 >= 0 && i3 <= 9)) {
            this.algorithm = i2;
            this.compression = i3;
        } else {
            throw new IllegalArgumentException("unknown compression level: " + i3);
        }
    }

    @Override // org.bouncycastle.openpgp.StreamGenerator
    public void close() throws IOException {
        OutputStream outputStream = this.dOut;
        if (outputStream != null) {
            if (outputStream instanceof DeflaterOutputStream) {
                ((DeflaterOutputStream) outputStream).finish();
            } else if (outputStream instanceof CBZip2OutputStream) {
                ((CBZip2OutputStream) outputStream).finish();
            }
            this.dOut.flush();
            this.pkOut.finish();
            this.pkOut.flush();
            this.out.flush();
            this.dOut = null;
            this.pkOut = null;
            this.out = null;
        }
    }

    public OutputStream open(OutputStream outputStream) throws IOException {
        OutputStream deflaterOutputStream;
        if (this.dOut != null) {
            throw new IllegalStateException("generator already in open state");
        }
        this.out = outputStream;
        int i2 = this.algorithm;
        if (i2 == 0) {
            BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(outputStream, 8);
            this.pkOut = bCPGOutputStream;
            bCPGOutputStream.write(0);
            deflaterOutputStream = this.pkOut;
        } else if (i2 == 1) {
            BCPGOutputStream bCPGOutputStream2 = new BCPGOutputStream(outputStream, 8);
            this.pkOut = bCPGOutputStream2;
            bCPGOutputStream2.write(1);
            deflaterOutputStream = new DeflaterOutputStream(this.pkOut, new Deflater(this.compression, true));
        } else if (i2 == 2) {
            BCPGOutputStream bCPGOutputStream3 = new BCPGOutputStream(outputStream, 8);
            this.pkOut = bCPGOutputStream3;
            bCPGOutputStream3.write(2);
            deflaterOutputStream = new DeflaterOutputStream(this.pkOut, new Deflater(this.compression));
        } else {
            if (i2 != 3) {
                throw new IllegalStateException("generator not initialised");
            }
            BCPGOutputStream bCPGOutputStream4 = new BCPGOutputStream(outputStream, 8);
            this.pkOut = bCPGOutputStream4;
            bCPGOutputStream4.write(3);
            deflaterOutputStream = new CBZip2OutputStream(this.pkOut);
        }
        this.dOut = deflaterOutputStream;
        return new WrappedGeneratorStream(this.dOut, this);
    }

    public OutputStream open(OutputStream outputStream, byte[] bArr) throws IOException, PGPException {
        OutputStream deflaterOutputStream;
        if (this.dOut != null) {
            throw new IllegalStateException("generator already in open state");
        }
        this.out = outputStream;
        int i2 = this.algorithm;
        if (i2 == 0) {
            BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(outputStream, 8, bArr);
            this.pkOut = bCPGOutputStream;
            bCPGOutputStream.write(0);
            deflaterOutputStream = this.pkOut;
        } else if (i2 == 1) {
            BCPGOutputStream bCPGOutputStream2 = new BCPGOutputStream(outputStream, 8, bArr);
            this.pkOut = bCPGOutputStream2;
            bCPGOutputStream2.write(1);
            deflaterOutputStream = new DeflaterOutputStream(this.pkOut, new Deflater(this.compression, true));
        } else if (i2 == 2) {
            BCPGOutputStream bCPGOutputStream3 = new BCPGOutputStream(outputStream, 8, bArr);
            this.pkOut = bCPGOutputStream3;
            bCPGOutputStream3.write(2);
            deflaterOutputStream = new DeflaterOutputStream(this.pkOut, new Deflater(this.compression));
        } else {
            if (i2 != 3) {
                throw new IllegalStateException("generator not initialised");
            }
            BCPGOutputStream bCPGOutputStream4 = new BCPGOutputStream(outputStream, 8, bArr);
            this.pkOut = bCPGOutputStream4;
            bCPGOutputStream4.write(3);
            deflaterOutputStream = new CBZip2OutputStream(this.pkOut);
        }
        this.dOut = deflaterOutputStream;
        return new WrappedGeneratorStream(this.dOut, this);
    }
}
