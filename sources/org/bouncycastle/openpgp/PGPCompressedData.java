package org.bouncycastle.openpgp;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.bouncycastle.apache.bzip2.CBZip2InputStream;
import org.bouncycastle.bcpg.BCPGInputStream;
import org.bouncycastle.bcpg.CompressedDataPacket;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;

/* loaded from: classes9.dex */
public class PGPCompressedData implements CompressionAlgorithmTags {
    CompressedDataPacket data;

    public PGPCompressedData(BCPGInputStream bCPGInputStream) throws IOException {
        this.data = (CompressedDataPacket) bCPGInputStream.readPacket();
    }

    public int getAlgorithm() {
        return this.data.getAlgorithm();
    }

    public InputStream getDataStream() throws PGPException {
        if (getAlgorithm() == 0) {
            return getInputStream();
        }
        if (getAlgorithm() == 1) {
            return new InflaterInputStream(getInputStream(), new Inflater(true)) { // from class: org.bouncycastle.openpgp.PGPCompressedData.1
                private boolean eof = false;

                @Override // java.util.zip.InflaterInputStream
                public void fill() throws IOException {
                    if (this.eof) {
                        throw new EOFException("Unexpected end of ZIP input stream");
                    }
                    InputStream inputStream = ((InflaterInputStream) this).in;
                    byte[] bArr = ((InflaterInputStream) this).buf;
                    int i2 = inputStream.read(bArr, 0, bArr.length);
                    ((InflaterInputStream) this).len = i2;
                    if (i2 == -1) {
                        ((InflaterInputStream) this).buf[0] = 0;
                        ((InflaterInputStream) this).len = 1;
                        this.eof = true;
                    }
                    ((InflaterInputStream) this).inf.setInput(((InflaterInputStream) this).buf, 0, ((InflaterInputStream) this).len);
                }
            };
        }
        if (getAlgorithm() == 2) {
            return new InflaterInputStream(getInputStream()) { // from class: org.bouncycastle.openpgp.PGPCompressedData.2
                private boolean eof = false;

                @Override // java.util.zip.InflaterInputStream
                public void fill() throws IOException {
                    if (this.eof) {
                        throw new EOFException("Unexpected end of ZIP input stream");
                    }
                    InputStream inputStream = ((InflaterInputStream) this).in;
                    byte[] bArr = ((InflaterInputStream) this).buf;
                    int i2 = inputStream.read(bArr, 0, bArr.length);
                    ((InflaterInputStream) this).len = i2;
                    if (i2 == -1) {
                        ((InflaterInputStream) this).buf[0] = 0;
                        ((InflaterInputStream) this).len = 1;
                        this.eof = true;
                    }
                    ((InflaterInputStream) this).inf.setInput(((InflaterInputStream) this).buf, 0, ((InflaterInputStream) this).len);
                }
            };
        }
        if (getAlgorithm() != 3) {
            throw new PGPException("can't recognise compression algorithm: " + getAlgorithm());
        }
        try {
            return new CBZip2InputStream(getInputStream());
        } catch (IOException e2) {
            throw new PGPException("I/O problem with stream: " + e2, e2);
        }
    }

    public InputStream getInputStream() {
        return this.data.getInputStream();
    }
}
