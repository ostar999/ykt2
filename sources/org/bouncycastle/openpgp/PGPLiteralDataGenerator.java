package org.bouncycastle.openpgp;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public class PGPLiteralDataGenerator implements StreamGenerator {
    public static final char BINARY = 'b';
    public static final String CONSOLE = "_CONSOLE";
    public static final Date NOW = PGPLiteralData.NOW;
    public static final char TEXT = 't';
    public static final char UTF8 = 'u';
    private boolean oldFormat;
    private BCPGOutputStream pkOut;

    public PGPLiteralDataGenerator() {
        this.oldFormat = false;
    }

    public PGPLiteralDataGenerator(boolean z2) {
        this.oldFormat = z2;
    }

    private void writeHeader(OutputStream outputStream, char c3, String str, long j2) throws IOException {
        outputStream.write(c3);
        byte[] uTF8ByteArray = Strings.toUTF8ByteArray(str);
        outputStream.write((byte) uTF8ByteArray.length);
        for (int i2 = 0; i2 != uTF8ByteArray.length; i2++) {
            outputStream.write(uTF8ByteArray[i2]);
        }
        outputStream.write((byte) (r5 >> 24));
        outputStream.write((byte) (r5 >> 16));
        outputStream.write((byte) (r5 >> 8));
        outputStream.write((byte) (j2 / 1000));
    }

    @Override // org.bouncycastle.openpgp.StreamGenerator
    public void close() throws IOException {
        BCPGOutputStream bCPGOutputStream = this.pkOut;
        if (bCPGOutputStream != null) {
            bCPGOutputStream.finish();
            this.pkOut.flush();
            this.pkOut = null;
        }
    }

    public OutputStream open(OutputStream outputStream, char c3, File file) throws IOException {
        if (this.pkOut != null) {
            throw new IllegalStateException("generator already in open state");
        }
        BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(outputStream, 11, 4 + file.length() + 2 + file.getName().length(), this.oldFormat);
        this.pkOut = bCPGOutputStream;
        writeHeader(bCPGOutputStream, c3, file.getName(), file.lastModified());
        return new WrappedGeneratorStream(this.pkOut, this);
    }

    public OutputStream open(OutputStream outputStream, char c3, String str, long j2, Date date) throws IOException {
        if (this.pkOut != null) {
            throw new IllegalStateException("generator already in open state");
        }
        BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(outputStream, 11, j2 + 2 + str.length() + 4, this.oldFormat);
        this.pkOut = bCPGOutputStream;
        writeHeader(bCPGOutputStream, c3, str, date.getTime());
        return new WrappedGeneratorStream(this.pkOut, this);
    }

    public OutputStream open(OutputStream outputStream, char c3, String str, Date date, byte[] bArr) throws IOException {
        if (this.pkOut != null) {
            throw new IllegalStateException("generator already in open state");
        }
        BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(outputStream, 11, bArr);
        this.pkOut = bCPGOutputStream;
        writeHeader(bCPGOutputStream, c3, str, date.getTime());
        return new WrappedGeneratorStream(this.pkOut, this);
    }
}
