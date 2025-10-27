package com.tencent.smtt.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class m implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public static String f21569a = "TBSFileLock";

    /* renamed from: f, reason: collision with root package name */
    private static Object f21570f = new Object();

    /* renamed from: g, reason: collision with root package name */
    private static Object f21571g = new Object();

    /* renamed from: h, reason: collision with root package name */
    private static HashMap<m, Object> f21572h;

    /* renamed from: i, reason: collision with root package name */
    private static Handler f21573i;

    /* renamed from: b, reason: collision with root package name */
    File f21574b;

    /* renamed from: c, reason: collision with root package name */
    RandomAccessFile f21575c = null;

    /* renamed from: d, reason: collision with root package name */
    FileLock f21576d = null;

    /* renamed from: e, reason: collision with root package name */
    long f21577e = 0;

    public m(File file, String str) {
        this.f21574b = null;
        this.f21574b = new File(file, StrPool.DOT + str + ".lock");
    }

    public Handler a() {
        if (f21573i == null) {
            synchronized (m.class) {
                if (f21573i == null) {
                    HandlerThread handlerThread = new HandlerThread("QBFileLock.Thread");
                    handlerThread.start();
                    f21573i = new Handler(handlerThread.getLooper());
                }
            }
        }
        return f21573i;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x003a A[Catch: all -> 0x0050, TryCatch #0 {, blocks: (B:3:0x0001, B:6:0x0022, B:10:0x002a, B:11:0x002c, B:13:0x0030, B:17:0x0038, B:18:0x003a, B:20:0x003e, B:22:0x0046, B:24:0x004b, B:16:0x0035, B:9:0x0027), top: B:30:0x0001, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004b A[Catch: all -> 0x0050, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:6:0x0022, B:10:0x002a, B:11:0x002c, B:13:0x0030, B:17:0x0038, B:18:0x003a, B:20:0x003e, B:22:0x0046, B:24:0x004b, B:16:0x0035, B:9:0x0027), top: B:30:0x0001, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0030 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void a(boolean r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            java.lang.String r0 = com.tencent.smtt.utils.m.f21569a     // Catch: java.lang.Throwable -> L50
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L50
            r1.<init>()     // Catch: java.lang.Throwable -> L50
            java.lang.String r2 = ">>> release lock: "
            r1.append(r2)     // Catch: java.lang.Throwable -> L50
            java.io.File r2 = r5.f21574b     // Catch: java.lang.Throwable -> L50
            java.lang.String r2 = r2.getName()     // Catch: java.lang.Throwable -> L50
            r1.append(r2)     // Catch: java.lang.Throwable -> L50
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L50
            android.util.Log.d(r0, r1)     // Catch: java.lang.Throwable -> L50
            java.nio.channels.FileLock r0 = r5.f21576d     // Catch: java.lang.Throwable -> L50
            r1 = 0
            if (r0 == 0) goto L2c
            r0.release()     // Catch: java.lang.Exception -> L26 java.lang.Throwable -> L50
            goto L2a
        L26:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L50
        L2a:
            r5.f21576d = r1     // Catch: java.lang.Throwable -> L50
        L2c:
            java.io.RandomAccessFile r0 = r5.f21575c     // Catch: java.lang.Throwable -> L50
            if (r0 == 0) goto L3a
            r0.close()     // Catch: java.lang.Exception -> L34 java.lang.Throwable -> L50
            goto L38
        L34:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L50
        L38:
            r5.f21575c = r1     // Catch: java.lang.Throwable -> L50
        L3a:
            android.os.Handler r0 = com.tencent.smtt.utils.m.f21573i     // Catch: java.lang.Throwable -> L50
            if (r0 == 0) goto L49
            long r1 = r5.f21577e     // Catch: java.lang.Throwable -> L50
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L49
            r0.removeCallbacks(r5)     // Catch: java.lang.Throwable -> L50
        L49:
            if (r6 == 0) goto L4e
            r5.d()     // Catch: java.lang.Throwable -> L50
        L4e:
            monitor-exit(r5)
            return
        L50:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.m.a(boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0096 A[Catch: all -> 0x000e, TRY_LEAVE, TryCatch #2 {, blocks: (B:3:0x0001, B:9:0x0015, B:11:0x0019, B:13:0x001f, B:15:0x0027, B:16:0x0030, B:18:0x0035, B:24:0x0049, B:28:0x0051, B:30:0x0060, B:27:0x004e, B:31:0x0067, B:22:0x003d, B:32:0x0092, B:34:0x0096, B:8:0x0012), top: B:43:0x0001, inners: #0, #1, #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void b() {
        /*
            r8 = this;
            monitor-enter(r8)
            java.io.RandomAccessFile r0 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> Le java.lang.Exception -> L11
            java.io.File r1 = r8.f21574b     // Catch: java.lang.Throwable -> Le java.lang.Exception -> L11
            java.lang.String r2 = "rw"
            r0.<init>(r1, r2)     // Catch: java.lang.Throwable -> Le java.lang.Exception -> L11
            r8.f21575c = r0     // Catch: java.lang.Throwable -> Le java.lang.Exception -> L11
            goto L15
        Le:
            r0 = move-exception
            goto L9b
        L11:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> Le
        L15:
            java.io.RandomAccessFile r0 = r8.f21575c     // Catch: java.lang.Throwable -> Le
            if (r0 == 0) goto L92
            java.nio.channels.FileChannel r0 = r0.getChannel()     // Catch: java.lang.Throwable -> Le
            if (r0 == 0) goto L92
            long r1 = r8.f21577e     // Catch: java.lang.Throwable -> Le
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L30
            android.os.Handler r1 = r8.a()     // Catch: java.lang.Throwable -> Le
            long r2 = r8.f21577e     // Catch: java.lang.Throwable -> Le
            r1.postDelayed(r8, r2)     // Catch: java.lang.Throwable -> Le
        L30:
            long r1 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Le
            r3 = 0
        L35:
            java.nio.channels.FileLock r3 = r0.lock()     // Catch: java.lang.Throwable -> Le java.lang.Exception -> L3c
            if (r3 == 0) goto L47
            goto L67
        L3c:
            r4 = move-exception
            r4.printStackTrace()     // Catch: java.lang.Throwable -> Le
            java.lang.String r4 = com.tencent.smtt.utils.m.f21569a     // Catch: java.lang.Throwable -> Le
            java.lang.String r5 = ">>> lock failed, sleep..."
            android.util.Log.d(r4, r5)     // Catch: java.lang.Throwable -> Le
        L47:
            r4 = 50
            java.lang.Thread.sleep(r4)     // Catch: java.lang.Throwable -> Le java.lang.InterruptedException -> L4d
            goto L51
        L4d:
            r4 = move-exception
            r4.printStackTrace()     // Catch: java.lang.Throwable -> Le
        L51:
            long r4 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Le
            long r4 = r4 - r1
            long r4 = java.lang.Math.abs(r4)     // Catch: java.lang.Throwable -> Le
            r6 = 1000(0x3e8, double:4.94E-321)
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 < 0) goto L35
            java.lang.String r0 = com.tencent.smtt.utils.m.f21569a     // Catch: java.lang.Throwable -> Le
            java.lang.String r4 = ">>> lock timeout, quit..."
            android.util.Log.d(r0, r4)     // Catch: java.lang.Throwable -> Le
        L67:
            r8.f21576d = r3     // Catch: java.lang.Throwable -> Le
            java.lang.String r0 = com.tencent.smtt.utils.m.f21569a     // Catch: java.lang.Throwable -> Le
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Le
            r3.<init>()     // Catch: java.lang.Throwable -> Le
            java.lang.String r4 = ">>> lock ["
            r3.append(r4)     // Catch: java.lang.Throwable -> Le
            java.io.File r4 = r8.f21574b     // Catch: java.lang.Throwable -> Le
            java.lang.String r4 = r4.getName()     // Catch: java.lang.Throwable -> Le
            r3.append(r4)     // Catch: java.lang.Throwable -> Le
            java.lang.String r4 = "] cost: "
            r3.append(r4)     // Catch: java.lang.Throwable -> Le
            long r4 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Le
            long r4 = r4 - r1
            r3.append(r4)     // Catch: java.lang.Throwable -> Le
            java.lang.String r1 = r3.toString()     // Catch: java.lang.Throwable -> Le
            android.util.Log.d(r0, r1)     // Catch: java.lang.Throwable -> Le
        L92:
            java.nio.channels.FileLock r0 = r8.f21576d     // Catch: java.lang.Throwable -> Le
            if (r0 == 0) goto L99
            r8.c()     // Catch: java.lang.Throwable -> Le
        L99:
            monitor-exit(r8)
            return
        L9b:
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.m.b():void");
    }

    public void c() {
        synchronized (f21571g) {
            if (f21572h == null) {
                f21572h = new HashMap<>();
            }
            f21572h.put(this, f21570f);
        }
    }

    public void d() {
        synchronized (f21571g) {
            HashMap<m, Object> map = f21572h;
            if (map == null) {
                return;
            }
            map.remove(this);
        }
    }

    public void e() {
        a(true);
    }

    @Override // java.lang.Runnable
    public void run() {
        Log.d(f21569a, ">>> releaseLock on TimeOut");
        e();
    }
}
