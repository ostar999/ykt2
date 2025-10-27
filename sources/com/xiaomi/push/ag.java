package com.xiaomi.push;

import android.content.Context;
import android.os.Environment;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes6.dex */
public class ag {

    /* renamed from: a, reason: collision with root package name */
    private static final String f24596a;

    /* renamed from: b, reason: collision with root package name */
    private static final String f24597b;

    /* renamed from: c, reason: collision with root package name */
    private static final String f24598c;

    static {
        String str = Environment.getExternalStorageDirectory().getPath() + "/mipush/";
        f24596a = str;
        f24597b = str + "lcfp";
        f24598c = str + "lcfp.lock";
    }

    public static boolean a(Context context, String str, long j2) throws Throwable {
        RandomAccessFile randomAccessFile;
        FileLock fileLockLock = null;
        try {
            try {
                File file = new File(f24598c);
                y.m774a(file);
                randomAccessFile = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
            } catch (IOException e2) {
                e = e2;
                randomAccessFile = null;
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    try {
                        fileLockLock.release();
                    } catch (IOException unused) {
                    }
                }
                y.a((Closeable) null);
                throw th;
            }
            try {
                fileLockLock = randomAccessFile.getChannel().lock();
                boolean zB = b(context, str, j2);
                if (fileLockLock != null && fileLockLock.isValid()) {
                    try {
                        fileLockLock.release();
                    } catch (IOException unused2) {
                    }
                }
                y.a(randomAccessFile);
                return zB;
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                if (fileLockLock != null && fileLockLock.isValid()) {
                    try {
                        fileLockLock.release();
                    } catch (IOException unused3) {
                    }
                }
                y.a(randomAccessFile);
                return true;
            }
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0 && fileLockLock.isValid()) {
                fileLockLock.release();
            }
            y.a((Closeable) null);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00d3 A[Catch: all -> 0x00e7, IOException -> 0x00ea, LOOP:0: B:42:0x00cd->B:44:0x00d3, LOOP_END, TRY_LEAVE, TryCatch #9 {IOException -> 0x00ea, all -> 0x00e7, blocks: (B:41:0x00c9, B:42:0x00cd, B:44:0x00d3), top: B:67:0x00c9 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean b(android.content.Context r16, java.lang.String r17, long r18) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.ag.b(android.content.Context, java.lang.String, long):boolean");
    }
}
