package net.tsz.afinal.bitmap.download;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/* loaded from: classes9.dex */
public class SimpleDownloader implements Downloader {
    private static final int IO_BUFFER_SIZE = 8192;
    private static final String TAG = "SimpleDownloader";

    public class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public long skip(long j2) throws IOException {
            long j3 = 0;
            while (j3 < j2) {
                long jSkip = ((FilterInputStream) this).in.skip(j2 - j3);
                if (jSkip == 0) {
                    if (read() < 0) {
                        break;
                    }
                    jSkip = 1;
                }
                j3 += jSkip;
            }
            return j3;
        }
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x0051: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]) (LINE:82), block:B:24:0x0051 */
    private byte[] getFromFile(File file) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        FileInputStream fileInputStream3 = null;
        try {
            if (file == null) {
                return null;
            }
            try {
                fileInputStream2 = new FileInputStream(file);
            } catch (Exception e2) {
                e = e2;
                fileInputStream2 = null;
            } catch (Throwable th) {
                th = th;
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (IOException unused) {
                    }
                }
                throw th;
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int i2 = fileInputStream2.read(bArr);
                    if (i2 == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i2);
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    fileInputStream2.close();
                } catch (IOException unused2) {
                }
                return byteArray;
            } catch (Exception e3) {
                e = e3;
                Log.e(TAG, "Error in read from file - " + file + " : " + e);
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException unused3) {
                    }
                }
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream3 = fileInputStream;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0077 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private byte[] getFromHttp(java.lang.String r8) throws java.lang.Throwable {
        /*
            r7 = this;
            r0 = 0
            java.net.URL r1 = new java.net.URL     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            r1.<init>(r8)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            java.net.URLConnection r1 = r1.openConnection()     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            java.net.HttpURLConnection r1 = (java.net.HttpURLConnection) r1     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L42
            net.tsz.afinal.bitmap.download.SimpleDownloader$FlushedInputStream r2 = new net.tsz.afinal.bitmap.download.SimpleDownloader$FlushedInputStream     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            java.io.InputStream r4 = r1.getInputStream()     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            r5 = 8192(0x2000, float:1.148E-41)
            r3.<init>(r4, r5)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L3c
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L6e
            r3.<init>()     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L6e
        L21:
            int r4 = r2.read()     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L6e
            r5 = -1
            if (r4 == r5) goto L2c
            r3.write(r4)     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L6e
            goto L21
        L2c:
            byte[] r8 = r3.toByteArray()     // Catch: java.io.IOException -> L37 java.lang.Throwable -> L6e
            r1.disconnect()
            r2.close()     // Catch: java.io.IOException -> L36
        L36:
            return r8
        L37:
            r3 = move-exception
            goto L45
        L39:
            r8 = move-exception
            r2 = r0
            goto L6f
        L3c:
            r3 = move-exception
            r2 = r0
            goto L45
        L3f:
            r8 = move-exception
            r2 = r0
            goto L70
        L42:
            r3 = move-exception
            r1 = r0
            r2 = r1
        L45:
            java.lang.String r4 = net.tsz.afinal.bitmap.download.SimpleDownloader.TAG     // Catch: java.lang.Throwable -> L6e
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6e
            r5.<init>()     // Catch: java.lang.Throwable -> L6e
            java.lang.String r6 = "Error in downloadBitmap - "
            r5.append(r6)     // Catch: java.lang.Throwable -> L6e
            r5.append(r8)     // Catch: java.lang.Throwable -> L6e
            java.lang.String r8 = " : "
            r5.append(r8)     // Catch: java.lang.Throwable -> L6e
            r5.append(r3)     // Catch: java.lang.Throwable -> L6e
            java.lang.String r8 = r5.toString()     // Catch: java.lang.Throwable -> L6e
            android.util.Log.e(r4, r8)     // Catch: java.lang.Throwable -> L6e
            if (r1 == 0) goto L68
            r1.disconnect()
        L68:
            if (r2 == 0) goto L6d
            r2.close()     // Catch: java.io.IOException -> L6d
        L6d:
            return r0
        L6e:
            r8 = move-exception
        L6f:
            r0 = r1
        L70:
            if (r0 == 0) goto L75
            r0.disconnect()
        L75:
            if (r2 == 0) goto L7a
            r2.close()     // Catch: java.io.IOException -> L7a
        L7a:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: net.tsz.afinal.bitmap.download.SimpleDownloader.getFromHttp(java.lang.String):byte[]");
    }

    @Override // net.tsz.afinal.bitmap.download.Downloader
    public byte[] download(String str) {
        if (str == null) {
            return null;
        }
        if (str.trim().toLowerCase().startsWith("http")) {
            return getFromHttp(str);
        }
        if (str.trim().toLowerCase().startsWith("file:")) {
            try {
                File file = new File(new URI(str));
                if (file.exists() && file.canRead()) {
                    return getFromFile(file);
                }
            } catch (URISyntaxException e2) {
                Log.e(TAG, "Error in read from file - " + str + " : " + e2);
            }
        } else {
            File file2 = new File(str);
            if (file2.exists() && file2.canRead()) {
                return getFromFile(file2);
            }
        }
        return null;
    }
}
