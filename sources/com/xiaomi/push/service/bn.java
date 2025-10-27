package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.ib;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes6.dex */
final class bn implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f25660a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ ib f1046a;

    public bn(Context context, ib ibVar) {
        this.f25660a = context;
        this.f1046a = ibVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        RandomAccessFile randomAccessFile;
        synchronized (bm.f25659a) {
            FileLock fileLockLock = null;
            try {
                File file = new File(this.f25660a.getFilesDir(), "tiny_data.lock");
                com.xiaomi.push.y.m774a(file);
                randomAccessFile = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
            } catch (Exception e2) {
                e = e2;
                randomAccessFile = null;
            } catch (Throwable th) {
                th = th;
                randomAccessFile = null;
                if (fileLockLock != null) {
                    try {
                        fileLockLock.release();
                    } catch (IOException e3) {
                        com.xiaomi.channel.commonutils.logger.b.a(e3);
                    }
                }
                com.xiaomi.push.y.a(randomAccessFile);
                throw th;
            }
            try {
                try {
                    fileLockLock = randomAccessFile.getChannel().lock();
                    bm.c(this.f25660a, this.f1046a);
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        try {
                            fileLockLock.release();
                        } catch (IOException e4) {
                            com.xiaomi.channel.commonutils.logger.b.a(e4);
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileLockLock != null && fileLockLock.isValid()) {
                        fileLockLock.release();
                    }
                    com.xiaomi.push.y.a(randomAccessFile);
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                com.xiaomi.channel.commonutils.logger.b.a(e);
                if (fileLockLock != null && fileLockLock.isValid()) {
                    try {
                        fileLockLock.release();
                    } catch (IOException e6) {
                        com.xiaomi.channel.commonutils.logger.b.a(e6);
                    }
                }
                com.xiaomi.push.y.a(randomAccessFile);
            }
            com.xiaomi.push.y.a(randomAccessFile);
        }
    }
}
