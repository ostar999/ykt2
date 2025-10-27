package org.bouncycastle.util.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public final class Streams {
    private static int BUFFER_SIZE = 512;

    public static void drain(InputStream inputStream) throws IOException {
        int i2 = BUFFER_SIZE;
        while (inputStream.read(new byte[i2], 0, i2) >= 0) {
        }
    }

    public static void pipeAll(InputStream inputStream, OutputStream outputStream) throws IOException {
        int i2 = BUFFER_SIZE;
        byte[] bArr = new byte[i2];
        while (true) {
            int i3 = inputStream.read(bArr, 0, i2);
            if (i3 < 0) {
                return;
            } else {
                outputStream.write(bArr, 0, i3);
            }
        }
    }

    public static long pipeAllLimited(InputStream inputStream, long j2, OutputStream outputStream) throws IOException {
        int i2 = BUFFER_SIZE;
        byte[] bArr = new byte[i2];
        long j3 = 0;
        while (true) {
            int i3 = inputStream.read(bArr, 0, i2);
            if (i3 < 0) {
                return j3;
            }
            j3 += i3;
            if (j3 > j2) {
                throw new StreamOverflowException("Data Overflow");
            }
            outputStream.write(bArr, 0, i3);
        }
    }

    public static byte[] readAll(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pipeAll(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] readAllLimited(InputStream inputStream, int i2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pipeAllLimited(inputStream, i2, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static int readFully(InputStream inputStream, byte[] bArr) throws IOException {
        return readFully(inputStream, bArr, 0, bArr.length);
    }

    public static int readFully(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        int i4 = 0;
        while (i4 < i3) {
            int i5 = inputStream.read(bArr, i2 + i4, i3 - i4);
            if (i5 < 0) {
                break;
            }
            i4 += i5;
        }
        return i4;
    }
}
