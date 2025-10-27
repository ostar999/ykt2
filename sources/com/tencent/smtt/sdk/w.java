package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.utils.FileUtil;
import com.tencent.smtt.utils.TbsLog;
import java.nio.channels.FileLock;

/* loaded from: classes6.dex */
class w {

    /* renamed from: a, reason: collision with root package name */
    private static w f21391a;

    /* renamed from: e, reason: collision with root package name */
    private static FileLock f21392e;

    /* renamed from: b, reason: collision with root package name */
    private x f21393b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f21394c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f21395d;

    private w() {
    }

    public static w a() {
        if (f21391a == null) {
            synchronized (w.class) {
                if (f21391a == null) {
                    f21391a = new w();
                }
            }
        }
        return f21391a;
    }

    public x a(boolean z2) {
        return z2 ? this.f21393b : c();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00d7 A[Catch: all -> 0x024f, TRY_LEAVE, TryCatch #3 {, blocks: (B:3:0x0001, B:6:0x002c, B:8:0x0030, B:17:0x0074, B:19:0x008e, B:22:0x00a5, B:24:0x00d7, B:40:0x0150, B:42:0x015c, B:43:0x0196, B:52:0x024b, B:44:0x019b, B:39:0x0147, B:45:0x01d6, B:47:0x01dc, B:48:0x0218, B:49:0x022c, B:51:0x0248, B:14:0x0051, B:15:0x006c, B:20:0x0096, B:28:0x00e8, B:30:0x00ee, B:31:0x00fa, B:33:0x00fe, B:34:0x012d, B:36:0x0131, B:9:0x0042, B:11:0x004a), top: B:60:0x0001, inners: #2, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x022c A[Catch: all -> 0x024f, TryCatch #3 {, blocks: (B:3:0x0001, B:6:0x002c, B:8:0x0030, B:17:0x0074, B:19:0x008e, B:22:0x00a5, B:24:0x00d7, B:40:0x0150, B:42:0x015c, B:43:0x0196, B:52:0x024b, B:44:0x019b, B:39:0x0147, B:45:0x01d6, B:47:0x01dc, B:48:0x0218, B:49:0x022c, B:51:0x0248, B:14:0x0051, B:15:0x006c, B:20:0x0096, B:28:0x00e8, B:30:0x00ee, B:31:0x00fa, B:33:0x00fe, B:34:0x012d, B:36:0x0131, B:9:0x0042, B:11:0x004a), top: B:60:0x0001, inners: #2, #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void a(android.content.Context r12) {
        /*
            Method dump skipped, instructions count: 594
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.w.a(android.content.Context):void");
    }

    public FileLock b(Context context) {
        String str;
        String str2;
        TbsLog.i("X5CoreEngine", "tryTbsCoreLoadFileLock ##");
        FileLock fileLock = f21392e;
        if (fileLock != null) {
            return fileLock;
        }
        synchronized (w.class) {
            if (f21392e == null) {
                FileLock fileLockD = FileUtil.d(context);
                f21392e = fileLockD;
                if (fileLockD == null) {
                    str = "X5CoreEngine";
                    str2 = "init -- sTbsCoreLoadFileLock failed!";
                } else {
                    str = "X5CoreEngine";
                    str2 = "init -- sTbsCoreLoadFileLock succeeded: " + f21392e;
                }
                TbsLog.i(str, str2);
            }
        }
        return f21392e;
    }

    public boolean b() {
        if (QbSdk.f20817a) {
            return false;
        }
        return this.f21394c;
    }

    public x c() {
        if (QbSdk.f20817a) {
            return null;
        }
        return this.f21393b;
    }

    public boolean d() {
        return this.f21395d;
    }
}
