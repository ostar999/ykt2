package com.umeng.commonsdk.stateless;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.pro.am;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.UMServerURL;
import com.umeng.commonsdk.statistics.common.ULog;
import java.io.File;
import java.util.LinkedList;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static final int f23264a = 273;

    /* renamed from: b, reason: collision with root package name */
    private static Context f23265b = null;

    /* renamed from: c, reason: collision with root package name */
    private static HandlerThread f23266c = null;

    /* renamed from: d, reason: collision with root package name */
    private static Handler f23267d = null;

    /* renamed from: f, reason: collision with root package name */
    private static final int f23269f = 274;

    /* renamed from: g, reason: collision with root package name */
    private static final int f23270g = 275;

    /* renamed from: h, reason: collision with root package name */
    private static final int f23271h = 512;

    /* renamed from: i, reason: collision with root package name */
    private static a f23272i = null;

    /* renamed from: j, reason: collision with root package name */
    private static IntentFilter f23273j = null;

    /* renamed from: k, reason: collision with root package name */
    private static boolean f23274k = false;

    /* renamed from: e, reason: collision with root package name */
    private static Object f23268e = new Object();

    /* renamed from: l, reason: collision with root package name */
    private static LinkedList<String> f23275l = new LinkedList<>();

    /* renamed from: m, reason: collision with root package name */
    private static BroadcastReceiver f23276m = new BroadcastReceiver() { // from class: com.umeng.commonsdk.stateless.b.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager;
            if (context == null || intent == null) {
                return;
            }
            try {
                if (intent.getAction() != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    Context unused = b.f23265b = context.getApplicationContext();
                    if (b.f23265b != null && (connectivityManager = (ConnectivityManager) b.f23265b.getSystemService("connectivity")) != null) {
                        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>网络断连： 2号数据仓");
                            boolean unused2 = b.f23274k = false;
                        } else {
                            boolean unused3 = b.f23274k = true;
                            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>网络可用： 触发2号数据仓信封消费动作。");
                            b.b(274);
                        }
                    }
                }
            } catch (Throwable th) {
                UMCrashManager.reportCrash(context, th);
            }
        }
    };

    public static class a extends FileObserver {
        public a(String str) {
            super(str);
        }

        @Override // android.os.FileObserver
        public void onEvent(int i2, String str) {
            if ((i2 & 8) != 8) {
                return;
            }
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> envelope file created >>> " + str);
            b.a(274);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00b8 A[Catch: all -> 0x00ba, DONT_GENERATE, TryCatch #1 {, blocks: (B:28:0x00b8, B:27:0x00b5, B:5:0x0008, B:7:0x0010, B:9:0x0014, B:11:0x0024, B:13:0x004b, B:14:0x0055, B:15:0x0066, B:17:0x006a, B:18:0x0077, B:20:0x0081, B:22:0x0093, B:24:0x00a3), top: B:33:0x0008, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public b(android.content.Context r6) {
        /*
            r5 = this;
            r5.<init>()
            java.lang.Object r0 = com.umeng.commonsdk.stateless.b.f23268e
            monitor-enter(r0)
            if (r6 == 0) goto Lb8
            android.content.Context r1 = r6.getApplicationContext()     // Catch: java.lang.Throwable -> Lb4
            com.umeng.commonsdk.stateless.b.f23265b = r1     // Catch: java.lang.Throwable -> Lb4
            if (r1 == 0) goto Lb8
            android.os.HandlerThread r1 = com.umeng.commonsdk.stateless.b.f23266c     // Catch: java.lang.Throwable -> Lb4
            if (r1 != 0) goto Lb8
            android.os.HandlerThread r1 = new android.os.HandlerThread     // Catch: java.lang.Throwable -> Lb4
            java.lang.String r2 = "SL-NetWorkSender"
            r1.<init>(r2)     // Catch: java.lang.Throwable -> Lb4
            com.umeng.commonsdk.stateless.b.f23266c = r1     // Catch: java.lang.Throwable -> Lb4
            r1.start()     // Catch: java.lang.Throwable -> Lb4
            com.umeng.commonsdk.stateless.b$a r1 = com.umeng.commonsdk.stateless.b.f23272i     // Catch: java.lang.Throwable -> Lb4
            if (r1 != 0) goto L66
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb4
            r1.<init>()     // Catch: java.lang.Throwable -> Lb4
            android.content.Context r2 = com.umeng.commonsdk.stateless.b.f23265b     // Catch: java.lang.Throwable -> Lb4
            java.io.File r2 = r2.getFilesDir()     // Catch: java.lang.Throwable -> Lb4
            r1.append(r2)     // Catch: java.lang.Throwable -> Lb4
            java.lang.String r2 = java.io.File.separator     // Catch: java.lang.Throwable -> Lb4
            r1.append(r2)     // Catch: java.lang.Throwable -> Lb4
            java.lang.String r2 = ".emitter"
            r1.append(r2)     // Catch: java.lang.Throwable -> Lb4
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> Lb4
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> Lb4
            r2.<init>(r1)     // Catch: java.lang.Throwable -> Lb4
            boolean r3 = r2.exists()     // Catch: java.lang.Throwable -> Lb4
            if (r3 != 0) goto L55
            java.lang.String r3 = "MobclickRT"
            java.lang.String r4 = "--->>> 2号数据仓目录不存在，创建之。"
            com.umeng.commonsdk.debug.UMRTLog.e(r3, r4)     // Catch: java.lang.Throwable -> Lb4
            r2.mkdir()     // Catch: java.lang.Throwable -> Lb4
        L55:
            com.umeng.commonsdk.stateless.b$a r2 = new com.umeng.commonsdk.stateless.b$a     // Catch: java.lang.Throwable -> Lb4
            r2.<init>(r1)     // Catch: java.lang.Throwable -> Lb4
            com.umeng.commonsdk.stateless.b.f23272i = r2     // Catch: java.lang.Throwable -> Lb4
            r2.startWatching()     // Catch: java.lang.Throwable -> Lb4
            java.lang.String r1 = "MobclickRT"
            java.lang.String r2 = "--->>> 2号数据仓File Monitor启动."
            com.umeng.commonsdk.debug.UMRTLog.e(r1, r2)     // Catch: java.lang.Throwable -> Lb4
        L66:
            android.os.Handler r1 = com.umeng.commonsdk.stateless.b.f23267d     // Catch: java.lang.Throwable -> Lb4
            if (r1 != 0) goto L77
            com.umeng.commonsdk.stateless.b$2 r1 = new com.umeng.commonsdk.stateless.b$2     // Catch: java.lang.Throwable -> Lb4
            android.os.HandlerThread r2 = com.umeng.commonsdk.stateless.b.f23266c     // Catch: java.lang.Throwable -> Lb4
            android.os.Looper r2 = r2.getLooper()     // Catch: java.lang.Throwable -> Lb4
            r1.<init>(r2)     // Catch: java.lang.Throwable -> Lb4
            com.umeng.commonsdk.stateless.b.f23267d = r1     // Catch: java.lang.Throwable -> Lb4
        L77:
            android.content.Context r1 = com.umeng.commonsdk.stateless.b.f23265b     // Catch: java.lang.Throwable -> Lb4
            java.lang.String r2 = "android.permission.ACCESS_NETWORK_STATE"
            boolean r1 = com.umeng.commonsdk.statistics.common.DeviceConfig.checkPermission(r1, r2)     // Catch: java.lang.Throwable -> Lb4
            if (r1 == 0) goto Lb8
            java.lang.String r1 = "walle"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> Lb4
            java.lang.String r3 = "[stateless] begin register receiver"
            r4 = 0
            r2[r4] = r3     // Catch: java.lang.Throwable -> Lb4
            com.umeng.commonsdk.statistics.common.ULog.i(r1, r2)     // Catch: java.lang.Throwable -> Lb4
            android.content.IntentFilter r1 = com.umeng.commonsdk.stateless.b.f23273j     // Catch: java.lang.Throwable -> Lb4
            if (r1 != 0) goto Lb8
            android.content.IntentFilter r1 = new android.content.IntentFilter     // Catch: java.lang.Throwable -> Lb4
            r1.<init>()     // Catch: java.lang.Throwable -> Lb4
            com.umeng.commonsdk.stateless.b.f23273j = r1     // Catch: java.lang.Throwable -> Lb4
            java.lang.String r2 = "android.net.conn.CONNECTIVITY_CHANGE"
            r1.addAction(r2)     // Catch: java.lang.Throwable -> Lb4
            android.content.BroadcastReceiver r1 = com.umeng.commonsdk.stateless.b.f23276m     // Catch: java.lang.Throwable -> Lb4
            if (r1 == 0) goto Lb8
            java.lang.String r1 = "MobclickRT"
            java.lang.String r2 = "--->>> 2号数据仓：注册网络状态监听器。"
            com.umeng.commonsdk.debug.UMRTLog.e(r1, r2)     // Catch: java.lang.Throwable -> Lb4
            android.content.Context r1 = com.umeng.commonsdk.stateless.b.f23265b     // Catch: java.lang.Throwable -> Lb4
            android.content.BroadcastReceiver r2 = com.umeng.commonsdk.stateless.b.f23276m     // Catch: java.lang.Throwable -> Lb4
            android.content.IntentFilter r3 = com.umeng.commonsdk.stateless.b.f23273j     // Catch: java.lang.Throwable -> Lb4
            r1.registerReceiver(r2, r3)     // Catch: java.lang.Throwable -> Lb4
            goto Lb8
        Lb4:
            r1 = move-exception
            com.umeng.commonsdk.internal.crash.UMCrashManager.reportCrash(r6, r1)     // Catch: java.lang.Throwable -> Lba
        Lb8:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lba
            return
        Lba:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lba
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.stateless.b.<init>(android.content.Context):void");
    }

    public static void b(int i2) {
        Handler handler;
        try {
            if (!f23274k || (handler = f23267d) == null || handler.hasMessages(i2)) {
                return;
            }
            Message messageObtainMessage = f23267d.obtainMessage();
            messageObtainMessage.what = i2;
            f23267d.sendMessage(messageObtainMessage);
        } catch (Throwable th) {
            UMCrashManager.reportCrash(f23265b, th);
        }
    }

    public static void c() {
        b(275);
    }

    public static void d() {
        b(512);
    }

    private static void j() {
        File[] fileArrC = d.c(f23265b);
        if (fileArrC != null) {
            if (f23275l.size() > 0) {
                f23275l.clear();
            }
            for (File file : fileArrC) {
                f23275l.add(file.getAbsolutePath());
            }
        }
    }

    private static String k() {
        String str = null;
        try {
            String strPeek = f23275l.peek();
            if (strPeek == null) {
                return strPeek;
            }
            try {
                f23275l.removeFirst();
                return strPeek;
            } catch (Throwable unused) {
                str = strPeek;
                return str;
            }
        } catch (Throwable unused2) {
        }
    }

    private static void l() {
        String strPollFirst;
        byte[] bArrA;
        if (f23275l.size() <= 0) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> todoList无内容，无需处理。");
            return;
        }
        do {
            strPollFirst = f23275l.pollFirst();
            if (!TextUtils.isEmpty(strPollFirst)) {
                File file = new File(strPollFirst);
                if (file.exists()) {
                    c cVar = new c(f23265b);
                    try {
                        bArrA = d.a(strPollFirst);
                    } catch (Exception unused) {
                        bArrA = null;
                    }
                    String name = file.getName();
                    String strSubstring = !TextUtils.isEmpty(name) ? name.substring(0, 1) : am.aG;
                    String strD = d.d(name);
                    String str = com.umeng.commonsdk.stateless.a.f23262j;
                    String strC = d.c(strD);
                    if (com.umeng.commonsdk.vchannel.a.f23546c.equalsIgnoreCase(strC)) {
                        str = com.umeng.commonsdk.vchannel.a.f23544a;
                    }
                    if (cVar.a(bArrA, strC, str, strSubstring) && !file.delete()) {
                        file.delete();
                    }
                } else {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 信封文件不存在，处理下一个文件。");
                }
            }
        } while (strPollFirst != null);
        f23275l.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void m() {
        File fileA;
        byte[] bArrA;
        if (!f23274k || f23265b == null) {
            return;
        }
        do {
            try {
                fileA = d.a(f23265b);
                if (fileA != null && fileA.getParentFile() != null && !TextUtils.isEmpty(fileA.getParentFile().getName())) {
                    c cVar = new c(f23265b);
                    String str = new String(Base64.decode(fileA.getParentFile().getName(), 0));
                    if (com.umeng.commonsdk.internal.a.f23167a.equalsIgnoreCase(str) || com.umeng.commonsdk.internal.a.f23168b.equalsIgnoreCase(str) || com.umeng.commonsdk.internal.a.D.equalsIgnoreCase(str)) {
                        new File(fileA.getAbsolutePath()).delete();
                    } else {
                        ULog.i("walle", "[stateless] handleProcessNext, pathUrl is " + str);
                        try {
                            bArrA = d.a(fileA.getAbsolutePath());
                        } catch (Exception unused) {
                            bArrA = null;
                        }
                        String str2 = com.umeng.commonsdk.vchannel.a.f23546c.equalsIgnoreCase(str) ? com.umeng.commonsdk.vchannel.a.f23544a : "";
                        String str3 = am.aG;
                        if (UMServerURL.PATH_SHARE.equalsIgnoreCase(str)) {
                            str3 = "s";
                        }
                        if (UMServerURL.PATH_PUSH_LAUNCH.equalsIgnoreCase(str) || UMServerURL.PATH_PUSH_REGIST.equalsIgnoreCase(str) || UMServerURL.PATH_PUSH_LOG.equalsIgnoreCase(str)) {
                            str3 = "p";
                        }
                        if (!cVar.a(bArrA, str, str2, str3)) {
                            ULog.i("walle", "[stateless] Send envelope file failed, abandon and wait next trigger!");
                            return;
                        }
                        ULog.i("walle", "[stateless] Send envelope file success, delete it.");
                        File file = new File(fileA.getAbsolutePath());
                        if (!file.delete()) {
                            ULog.i("walle", "[stateless] Failed to delete already processed file. We try again after delete failed.");
                            file.delete();
                        }
                    }
                }
            } catch (Throwable th) {
                UMCrashManager.reportCrash(f23265b, th);
            }
        } while (fileA != null);
        n();
    }

    private static void n() {
        try {
            File file = new File(f23265b.getFilesDir() + File.separator + com.umeng.commonsdk.stateless.a.f23257e);
            if (file.exists() && file.isDirectory()) {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 2号数据仓：删除stateless目录。");
                d.a(file);
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void o() {
        if (!f23274k || f23265b == null) {
            return;
        }
        j();
        l();
        c();
    }

    private static void p() {
        try {
            File file = new File(f23265b.getFilesDir() + File.separator + com.umeng.commonsdk.stateless.a.f23257e);
            if (file.exists() && file.isDirectory()) {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>2号数据仓：检测到stateless目录。");
                b(273);
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void q() {
        p();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void r() {
    }

    public static boolean a() {
        synchronized (f23268e) {
            return f23272i != null;
        }
    }

    public static void b() {
        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>信封构建成功： 触发2号数据仓信封消费动作。");
        b(274);
    }

    public static void a(int i2) {
        Handler handler;
        if (!f23274k || (handler = f23267d) == null) {
            return;
        }
        Message messageObtainMessage = handler.obtainMessage();
        messageObtainMessage.what = i2;
        f23267d.sendMessage(messageObtainMessage);
    }
}
