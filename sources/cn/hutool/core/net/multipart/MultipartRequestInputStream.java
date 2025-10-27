package cn.hutool.core.net.multipart;

import cn.hutool.core.io.FastByteArrayOutputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class MultipartRequestInputStream extends BufferedInputStream {
    protected byte[] boundary;
    protected UploadFileHeader lastHeader;

    public MultipartRequestInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public long copy(OutputStream outputStream) throws IOException {
        long j2 = 0;
        while (true) {
            byte b3 = readByte();
            if (isBoundary(b3)) {
                return j2;
            }
            outputStream.write(b3);
            j2++;
        }
    }

    public UploadFileHeader getLastHeader() {
        return this.lastHeader;
    }

    public boolean isBoundary(byte b3) throws IOException {
        int length = this.boundary.length;
        mark(length + 1);
        int i2 = 0;
        while (b3 == this.boundary[i2]) {
            b3 = readByte();
            i2++;
            if (i2 == length) {
                return true;
            }
        }
        reset();
        return false;
    }

    public byte[] readBoundary() throws IOException {
        byte b3;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        do {
            b3 = readByte();
        } while (b3 <= 32);
        byteArrayOutputStream.write(b3);
        while (true) {
            byte b4 = readByte();
            if (b4 == 13) {
                break;
            }
            byteArrayOutputStream.write(b4);
        }
        if (byteArrayOutputStream.size() == 0) {
            throw new IOException("Problems with parsing request: invalid boundary");
        }
        skipBytes(1L);
        this.boundary = new byte[byteArrayOutputStream.size() + 2];
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byte[] bArr = this.boundary;
        System.arraycopy(byteArray, 0, bArr, 2, bArr.length - 2);
        byte[] bArr2 = this.boundary;
        bArr2[0] = 13;
        bArr2[1] = 10;
        return bArr2;
    }

    public byte readByte() throws IOException {
        int i2 = super.read();
        if (i2 != -1) {
            return (byte) i2;
        }
        throw new IOException("End of HTTP request stream reached");
    }

    public UploadFileHeader readDataHeader(Charset charset) throws IOException {
        String dataHeaderString = readDataHeaderString(charset);
        if (dataHeaderString != null) {
            this.lastHeader = new UploadFileHeader(dataHeaderString);
        } else {
            this.lastHeader = null;
        }
        return this.lastHeader;
    }

    public String readDataHeaderString(Charset charset) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            byte b3 = readByte();
            if (b3 != 13) {
                byteArrayOutputStream.write(b3);
            } else {
                mark(4);
                skipBytes(1L);
                int i2 = read();
                if (i2 == -1) {
                    return null;
                }
                if (i2 == 13) {
                    reset();
                    skipBytes(3L);
                    return charset == null ? byteArrayOutputStream.toString() : byteArrayOutputStream.toString(charset.name());
                }
                reset();
                byteArrayOutputStream.write(b3);
            }
        }
    }

    public String readString(Charset charset) throws IOException {
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        copy(fastByteArrayOutputStream);
        return fastByteArrayOutputStream.toString(charset);
    }

    public void skipBytes(long j2) throws IOException {
        if (super.skip(j2) != j2) {
            throw new IOException("Unable to skip data in HTTP request");
        }
    }

    public long skipToBoundary() throws IOException {
        long j2 = 0;
        do {
            j2++;
        } while (!isBoundary(readByte()));
        return j2;
    }

    public long copy(OutputStream outputStream, long j2) throws IOException {
        long j3 = 0;
        do {
            byte b3 = readByte();
            if (isBoundary(b3)) {
                break;
            }
            outputStream.write(b3);
            j3++;
        } while (j3 <= j2);
        return j3;
    }
}
