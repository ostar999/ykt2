package com.nostra13.universalimageloader.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public final class IoUtils {
    public static final int CONTINUE_LOADING_PERCENTAGE = 75;
    public static final int DEFAULT_BUFFER_SIZE = 32768;
    public static final int DEFAULT_IMAGE_TOTAL_SIZE = 512000;

    public interface CopyListener {
        boolean onBytesCopied(int i2, int i3);
    }

    private IoUtils() {
    }

    public static void closeSilently(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static boolean copyStream(InputStream inputStream, OutputStream outputStream, CopyListener copyListener) throws IOException {
        return copyStream(inputStream, outputStream, copyListener, 32768);
    }

    public static void readAndCloseStream(InputStream inputStream) throws IOException {
        do {
            try {
            } catch (IOException unused) {
            } catch (Throwable th) {
                closeSilently(inputStream);
                throw th;
            }
        } while (inputStream.read(new byte[32768], 0, 32768) != -1);
        closeSilently(inputStream);
    }

    private static boolean shouldStopLoading(CopyListener copyListener, int i2, int i3) {
        return (copyListener == null || copyListener.onBytesCopied(i2, i3) || (i2 * 100) / i3 >= 75) ? false : true;
    }

    public static boolean copyStream(InputStream inputStream, OutputStream outputStream, CopyListener copyListener, int i2) throws IOException {
        int iAvailable = inputStream.available();
        if (iAvailable <= 0) {
            iAvailable = DEFAULT_IMAGE_TOTAL_SIZE;
        }
        byte[] bArr = new byte[i2];
        if (shouldStopLoading(copyListener, 0, iAvailable)) {
            return false;
        }
        int i3 = 0;
        do {
            int i4 = inputStream.read(bArr, 0, i2);
            if (i4 == -1) {
                outputStream.flush();
                return true;
            }
            outputStream.write(bArr, 0, i4);
            i3 += i4;
        } while (!shouldStopLoading(copyListener, i3, iAvailable));
        return false;
    }
}
