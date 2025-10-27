package com.ta.a.e;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes6.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static File f17218a;

    /* renamed from: a, reason: collision with other field name */
    private static FileChannel f75a;

    /* renamed from: a, reason: collision with other field name */
    private static FileLock f76a;

    /* renamed from: b, reason: collision with root package name */
    private static File f17219b;

    /* renamed from: b, reason: collision with other field name */
    private static FileChannel f77b;

    /* renamed from: b, reason: collision with other field name */
    private static FileLock f78b;

    public static synchronized boolean a() {
        h.f();
        if (f17219b == null) {
            f17219b = new File(com.ta.a.d.e.d());
        }
        if (!f17219b.exists()) {
            try {
                f17219b.createNewFile();
            } catch (Exception unused) {
                return false;
            }
        }
        if (f77b == null) {
            try {
                f77b = new RandomAccessFile(f17219b, InternalZipConstants.WRITE_MODE).getChannel();
            } catch (Exception unused2) {
                return false;
            }
        }
        try {
            FileLock fileLockTryLock = f77b.tryLock();
            if (fileLockTryLock != null) {
                f78b = fileLockTryLock;
                return true;
            }
        } catch (Throwable unused3) {
        }
        return false;
    }

    public static synchronized void c() {
        h.f();
        if (f17218a == null) {
            f17218a = new File(com.ta.a.d.e.c());
        }
        if (!f17218a.exists()) {
            try {
                f17218a.createNewFile();
            } catch (Exception unused) {
                return;
            }
        }
        if (f75a == null) {
            try {
                f75a = new RandomAccessFile(f17218a, InternalZipConstants.WRITE_MODE).getChannel();
            } catch (Exception unused2) {
                return;
            }
        }
        try {
            f76a = f75a.lock();
        } catch (Throwable unused3) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0019 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized void d() {
        /*
            java.lang.Class<com.ta.a.e.e> r0 = com.ta.a.e.e.class
            monitor-enter(r0)
            com.ta.a.e.h.f()     // Catch: java.lang.Throwable -> L25
            java.nio.channels.FileLock r1 = com.ta.a.e.e.f76a     // Catch: java.lang.Throwable -> L25
            r2 = 0
            if (r1 == 0) goto L15
            r1.release()     // Catch: java.lang.Exception -> Le java.lang.Throwable -> L11
        Le:
            com.ta.a.e.e.f76a = r2     // Catch: java.lang.Throwable -> L25
            goto L15
        L11:
            r1 = move-exception
            com.ta.a.e.e.f76a = r2     // Catch: java.lang.Throwable -> L25
            throw r1     // Catch: java.lang.Throwable -> L25
        L15:
            java.nio.channels.FileChannel r1 = com.ta.a.e.e.f75a     // Catch: java.lang.Throwable -> L25
            if (r1 == 0) goto L23
            r1.close()     // Catch: java.lang.Exception -> L1c java.lang.Throwable -> L1f
        L1c:
            com.ta.a.e.e.f75a = r2     // Catch: java.lang.Throwable -> L25
            goto L23
        L1f:
            r1 = move-exception
            com.ta.a.e.e.f75a = r2     // Catch: java.lang.Throwable -> L25
            throw r1     // Catch: java.lang.Throwable -> L25
        L23:
            monitor-exit(r0)
            return
        L25:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.a.e.e.d():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0019 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized void e() {
        /*
            java.lang.Class<com.ta.a.e.e> r0 = com.ta.a.e.e.class
            monitor-enter(r0)
            com.ta.a.e.h.f()     // Catch: java.lang.Throwable -> L25
            java.nio.channels.FileLock r1 = com.ta.a.e.e.f78b     // Catch: java.lang.Throwable -> L25
            r2 = 0
            if (r1 == 0) goto L15
            r1.release()     // Catch: java.lang.Exception -> Le java.lang.Throwable -> L11
        Le:
            com.ta.a.e.e.f78b = r2     // Catch: java.lang.Throwable -> L25
            goto L15
        L11:
            r1 = move-exception
            com.ta.a.e.e.f78b = r2     // Catch: java.lang.Throwable -> L25
            throw r1     // Catch: java.lang.Throwable -> L25
        L15:
            java.nio.channels.FileChannel r1 = com.ta.a.e.e.f77b     // Catch: java.lang.Throwable -> L25
            if (r1 == 0) goto L23
            r1.close()     // Catch: java.lang.Exception -> L1c java.lang.Throwable -> L1f
        L1c:
            com.ta.a.e.e.f77b = r2     // Catch: java.lang.Throwable -> L25
            goto L23
        L1f:
            r1 = move-exception
            com.ta.a.e.e.f77b = r2     // Catch: java.lang.Throwable -> L25
            throw r1     // Catch: java.lang.Throwable -> L25
        L23:
            monitor-exit(r0)
            return
        L25:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.a.e.e.e():void");
    }
}
