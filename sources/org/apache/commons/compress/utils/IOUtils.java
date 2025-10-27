package org.apache.commons.compress.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public final class IOUtils {
    private static final int COPY_BUF_SIZE = 8024;
    private static final byte[] SKIP_BUF = new byte[4096];
    private static final int SKIP_BUF_SIZE = 4096;

    private IOUtils() {
    }

    public static void closeQuietly(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, 8024);
    }

    public static int readFully(InputStream inputStream, byte[] bArr) throws IOException {
        return readFully(inputStream, bArr, 0, bArr.length);
    }

    public static long skip(InputStream inputStream, long j2) throws IOException {
        int fully;
        long j3 = j2;
        while (j3 > 0) {
            long jSkip = inputStream.skip(j3);
            if (jSkip == 0) {
                break;
            }
            j3 -= jSkip;
        }
        while (j3 > 0 && (fully = readFully(inputStream, SKIP_BUF, 0, (int) Math.min(j3, 4096L))) >= 1) {
            j3 -= fully;
        }
        return j2 - j3;
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        long j2 = 0;
        while (true) {
            int i3 = inputStream.read(bArr);
            if (-1 == i3) {
                return j2;
            }
            outputStream.write(bArr, 0, i3);
            j2 += i3;
        }
    }

    public static int readFully(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        if (i3 < 0 || i2 < 0 || i3 + i2 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        int i4 = 0;
        while (i4 != i3) {
            int i5 = inputStream.read(bArr, i2 + i4, i3 - i4);
            if (i5 == -1) {
                break;
            }
            i4 += i5;
        }
        return i4;
    }
}
