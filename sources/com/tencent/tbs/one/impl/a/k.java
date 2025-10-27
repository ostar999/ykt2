package com.tencent.tbs.one.impl.a;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.concurrent.Callable;

/* loaded from: classes6.dex */
public final class k {

    /* renamed from: a, reason: collision with root package name */
    public final File f21753a;

    /* renamed from: b, reason: collision with root package name */
    public final FileOutputStream f21754b;

    /* renamed from: c, reason: collision with root package name */
    public final FileLock f21755c;

    public k(File file, FileOutputStream fileOutputStream, FileLock fileLock) {
        this.f21753a = file;
        this.f21754b = fileOutputStream;
        this.f21755c = fileLock;
    }

    public static k a(File file) throws IOException {
        Object th;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
        try {
            FileLock fileLockTryLock = fileOutputStream.getChannel().tryLock();
            if (fileLockTryLock != null) {
                g.a("Created lock file: %s", file.getAbsolutePath());
                return new k(file, fileOutputStream, fileLockTryLock);
            }
        } catch (Throwable th3) {
            th = th3;
            g.c("Failed to try to acquire lock %s", file.getAbsolutePath(), th);
            d.a(fileOutputStream);
            return null;
        }
        d.a(fileOutputStream);
        return null;
    }

    public static <T> T a(Callable<T> callable, File file, long j2) throws Exception {
        for (int i2 = 0; i2 < j2 / 200; i2++) {
            try {
                T tCall = callable.call();
                if (tCall != null) {
                    if (i2 > 0) {
                        g.a("Finished waiting on lock file: %s", file.getAbsolutePath());
                    }
                    return tCall;
                }
                if (i2 == 0) {
                    g.a("Waiting on lock file: %s", file.getAbsolutePath());
                }
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException unused) {
                }
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
        throw new RuntimeException("Timed out waiting for lock file: " + file.getAbsolutePath());
    }

    public final void a() throws IOException {
        try {
            g.a("Deleting lock file: %s", this.f21753a.getAbsolutePath());
            this.f21755c.release();
            this.f21754b.close();
            if (this.f21753a.delete()) {
                return;
            }
            throw new IOException("Failed to delete lock file: " + this.f21753a.getAbsolutePath());
        } catch (IOException e2) {
            g.c("Failed to release process lock file %s", this.f21753a.getAbsolutePath(), e2);
        }
    }
}
