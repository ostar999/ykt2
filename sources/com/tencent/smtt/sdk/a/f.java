package com.tencent.smtt.sdk.a;

import com.tencent.smtt.utils.TbsLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

/* loaded from: classes6.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static String f21142a = "EmergencyManager";

    /* renamed from: b, reason: collision with root package name */
    private final File f21143b;

    /* renamed from: c, reason: collision with root package name */
    private final FileOutputStream f21144c;

    /* renamed from: d, reason: collision with root package name */
    private final FileLock f21145d;

    private f(File file, FileOutputStream fileOutputStream, FileLock fileLock) {
        this.f21143b = file;
        this.f21144c = fileOutputStream;
        this.f21145d = fileLock;
    }

    public static f a(File file) throws IOException {
        FileOutputStream fileOutputStream;
        String str;
        StringBuilder sb;
        FileLock fileLockTryLock;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th) {
            th = th;
            fileOutputStream = null;
        }
        try {
            fileLockTryLock = fileOutputStream.getChannel().tryLock();
        } catch (Throwable th2) {
            th = th2;
            try {
                TbsLog.e(f21142a, "Failed to try to acquire lock: " + file.getAbsolutePath() + " error: " + th.getMessage());
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e2) {
                        e = e2;
                        str = f21142a;
                        sb = new StringBuilder();
                        sb.append("Failed to close: ");
                        sb.append(e.getMessage());
                        TbsLog.e(str, sb.toString());
                        return null;
                    }
                }
                return null;
            } catch (Throwable th3) {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e3) {
                        TbsLog.e(f21142a, "Failed to close: " + e3.getMessage());
                    }
                }
                throw th3;
            }
        }
        if (fileLockTryLock == null) {
            try {
                fileOutputStream.close();
            } catch (IOException e4) {
                e = e4;
                str = f21142a;
                sb = new StringBuilder();
                sb.append("Failed to close: ");
                sb.append(e.getMessage());
                TbsLog.e(str, sb.toString());
                return null;
            }
            return null;
        }
        TbsLog.i(f21142a, "Created lock file: " + file.getAbsolutePath());
        f fVar = new f(file, fileOutputStream, fileLockTryLock);
        try {
            fileOutputStream.close();
        } catch (IOException e5) {
            TbsLog.e(f21142a, "Failed to close: " + e5.getMessage());
        }
        return fVar;
    }

    public void a() throws IOException {
        TbsLog.i(f21142a, "Deleting lock file: " + this.f21143b.getAbsolutePath());
        this.f21145d.release();
        this.f21144c.close();
        if (this.f21143b.delete()) {
            return;
        }
        throw new IOException("Failed to delete lock file: " + this.f21143b.getAbsolutePath());
    }

    public void b() {
        try {
            a();
        } catch (IOException unused) {
        }
    }
}
