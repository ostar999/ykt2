package com.tencent.bugly.crashreport.crash.jni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.proguard.aa;
import com.tencent.bugly.proguard.ab;
import com.tencent.bugly.proguard.ac;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.al;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.as;
import com.tencent.bugly.proguard.at;
import com.tencent.bugly.proguard.bd;
import com.tencent.bugly.proguard.be;
import com.tencent.bugly.proguard.q;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import k.a;

/* loaded from: classes6.dex */
public class NativeCrashHandler implements q {

    /* renamed from: a, reason: collision with root package name */
    static String f17399a = null;

    /* renamed from: b, reason: collision with root package name */
    private static NativeCrashHandler f17400b = null;

    /* renamed from: c, reason: collision with root package name */
    private static int f17401c = 1;

    /* renamed from: n, reason: collision with root package name */
    private static boolean f17402n = true;

    /* renamed from: d, reason: collision with root package name */
    private final Context f17403d;

    /* renamed from: e, reason: collision with root package name */
    private final aa f17404e;

    /* renamed from: f, reason: collision with root package name */
    private final ak f17405f;

    /* renamed from: g, reason: collision with root package name */
    private NativeExceptionHandler f17406g;

    /* renamed from: h, reason: collision with root package name */
    private final boolean f17407h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f17408i = false;

    /* renamed from: j, reason: collision with root package name */
    private boolean f17409j = false;

    /* renamed from: k, reason: collision with root package name */
    private boolean f17410k = false;

    /* renamed from: l, reason: collision with root package name */
    private boolean f17411l = false;

    /* renamed from: m, reason: collision with root package name */
    private as f17412m;

    @SuppressLint({"SdCardPath"})
    private NativeCrashHandler(Context context, aa aaVar, as asVar, ak akVar, boolean z2, String str) {
        this.f17403d = ap.a(context);
        if (ap.b(f17399a)) {
            try {
                if (ap.b(str)) {
                    str = context.getDir("bugly", 0).getAbsolutePath();
                }
            } catch (Throwable unused) {
                str = "/data/data/" + aa.a(context).f17417c + "/app_bugly";
            }
            f17399a = str;
        }
        this.f17412m = asVar;
        this.f17404e = aaVar;
        this.f17405f = akVar;
        this.f17407h = z2;
        this.f17406g = new bd(context, aaVar, asVar, ac.a());
    }

    public static synchronized String getDumpFilePath() {
        return f17399a;
    }

    public static synchronized NativeCrashHandler getInstance(Context context, aa aaVar, as asVar, ac acVar, ak akVar, boolean z2, String str) {
        if (f17400b == null) {
            f17400b = new NativeCrashHandler(context, aaVar, asVar, akVar, z2, str);
        }
        return f17400b;
    }

    private native String getProperties(String str);

    private native String getSoCpuAbi();

    public static boolean isShouldHandleInJava() {
        return f17402n;
    }

    public static synchronized void setDumpFilePath(String str) {
        f17399a = str;
    }

    public static void setShouldHandleInJava(boolean z2) {
        f17402n = z2;
        NativeCrashHandler nativeCrashHandler = f17400b;
        if (nativeCrashHandler != null) {
            nativeCrashHandler.a(999, String.valueOf(z2));
        }
    }

    @Override // com.tencent.bugly.proguard.q
    public boolean appendLogToNative(String str, String str2, String str3) {
        if ((this.f17408i || this.f17409j) && str != null && str2 != null && str3 != null) {
            try {
                if (this.f17409j) {
                    return appendNativeLog(str, str2, str3);
                }
                Boolean bool = (Boolean) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "appendNativeLog", new Class[]{String.class, String.class, String.class}, new Object[]{str, str2, str3});
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } catch (UnsatisfiedLinkError unused) {
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }

    public native boolean appendNativeLog(String str, String str2, String str3);

    public native boolean appendWholeNativeLog(String str);

    public void checkUploadRecordCrash() {
        this.f17405f.a(new Runnable() { // from class: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.1
            @Override // java.lang.Runnable
            public final void run() {
                int i2;
                if (!ap.a(NativeCrashHandler.this.f17403d, "native_record_lock")) {
                    al.a("[Native] Failed to lock file for handling native crash record.", new Object[0]);
                    return;
                }
                if (!NativeCrashHandler.f17402n) {
                    NativeCrashHandler.a(NativeCrashHandler.this, a.f27524v);
                }
                CrashDetailBean crashDetailBeanA = be.a(NativeCrashHandler.this.f17403d, NativeCrashHandler.f17399a, NativeCrashHandler.this.f17406g);
                if (crashDetailBeanA != null) {
                    al.a("[Native] Get crash from native record.", new Object[0]);
                    if (!NativeCrashHandler.this.f17412m.a(crashDetailBeanA, true)) {
                        NativeCrashHandler.this.f17412m.b(crashDetailBeanA, false);
                    }
                    be.a(false, NativeCrashHandler.f17399a);
                }
                final NativeCrashHandler nativeCrashHandler = NativeCrashHandler.this;
                long jB = ap.b() - at.f17585j;
                long jB2 = ap.b() + 86400000;
                File file = new File(NativeCrashHandler.f17399a);
                if (file.exists() && file.isDirectory()) {
                    try {
                        File[] fileArrListFiles = file.listFiles();
                        if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                            Arrays.sort(fileArrListFiles, new Comparator<File>() { // from class: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.2
                                @Override // java.util.Comparator
                                public final /* synthetic */ int compare(File file2, File file3) {
                                    return Long.compare(file3.lastModified(), file2.lastModified());
                                }
                            });
                            int length = fileArrListFiles.length;
                            long length2 = 0;
                            int i3 = 0;
                            int i4 = 0;
                            int i5 = 0;
                            while (i3 < length) {
                                File file2 = fileArrListFiles[i3];
                                long jLastModified = file2.lastModified();
                                length2 += file2.length();
                                if (jLastModified < jB || jLastModified >= jB2 || length2 >= at.f17584i) {
                                    i2 = length;
                                    al.a("[Native] Delete record file: %s", file2.getAbsolutePath());
                                    i4++;
                                    if (file2.delete()) {
                                        i5++;
                                    }
                                } else {
                                    i2 = length;
                                }
                                i3++;
                                length = i2;
                            }
                            al.c("[Native] Number of record files overdue: %d, has deleted: %d", Integer.valueOf(i4), Integer.valueOf(i5));
                        }
                    } catch (Throwable th) {
                        al.a(th);
                    }
                }
                ap.b(NativeCrashHandler.this.f17403d, "native_record_lock");
            }
        });
    }

    public void disableCatchAnrTrace() {
        f17401c = 1;
    }

    public void dumpAnrNativeStack() {
        a(19, "1");
    }

    public void enableCatchAnrTrace() {
        f17401c |= 2;
    }

    public boolean filterSigabrtSysLog() {
        return a(998, a.f27523u);
    }

    @Override // com.tencent.bugly.proguard.q
    public String getLogFromNative() {
        if (!this.f17408i && !this.f17409j) {
            return null;
        }
        try {
            return this.f17409j ? getNativeLog() : (String) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "getNativeLog", null, null);
        } catch (UnsatisfiedLinkError unused) {
            return null;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public NativeExceptionHandler getNativeExceptionHandler() {
        return this.f17406g;
    }

    public native String getNativeKeyValueList();

    public native String getNativeLog();

    public String getRunningCpuAbi() {
        try {
            return getSoCpuAbi();
        } catch (Throwable unused) {
            al.d("get so cpu abi failed，please upgrade bugly so version", new Object[0]);
            return "";
        }
    }

    public String getSystemProperty(String str) {
        return (this.f17409j || this.f17408i) ? getProperties(str) : "fail";
    }

    public boolean isEnableCatchAnrTrace() {
        return (f17401c & 2) == 2;
    }

    public synchronized boolean isUserOpened() {
        return this.f17411l;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0030 A[Catch: all -> 0x0042, TRY_LEAVE, TryCatch #0 {, blocks: (B:5:0x0005, B:7:0x000b, B:8:0x0019, B:10:0x0025, B:14:0x002c, B:16:0x0030), top: B:22:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void onStrategyChanged(com.tencent.bugly.crashreport.common.strategy.StrategyBean r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            r0 = 1
            r1 = 0
            if (r5 == 0) goto L19
            boolean r5 = r5.f17341f     // Catch: java.lang.Throwable -> L42
            boolean r2 = r4.f17410k     // Catch: java.lang.Throwable -> L42
            if (r5 == r2) goto L19
            java.lang.String r2 = "server native changed to %b"
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L42
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch: java.lang.Throwable -> L42
            r3[r1] = r5     // Catch: java.lang.Throwable -> L42
            com.tencent.bugly.proguard.al.d(r2, r3)     // Catch: java.lang.Throwable -> L42
        L19:
            com.tencent.bugly.proguard.ac r5 = com.tencent.bugly.proguard.ac.a()     // Catch: java.lang.Throwable -> L42
            com.tencent.bugly.crashreport.common.strategy.StrategyBean r5 = r5.c()     // Catch: java.lang.Throwable -> L42
            boolean r5 = r5.f17341f     // Catch: java.lang.Throwable -> L42
            if (r5 == 0) goto L2b
            boolean r5 = r4.f17411l     // Catch: java.lang.Throwable -> L42
            if (r5 == 0) goto L2b
            r5 = r0
            goto L2c
        L2b:
            r5 = r1
        L2c:
            boolean r2 = r4.f17410k     // Catch: java.lang.Throwable -> L42
            if (r5 == r2) goto L40
            java.lang.String r2 = "native changed to %b"
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L42
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r5)     // Catch: java.lang.Throwable -> L42
            r0[r1] = r3     // Catch: java.lang.Throwable -> L42
            com.tencent.bugly.proguard.al.a(r2, r0)     // Catch: java.lang.Throwable -> L42
            r4.b(r5)     // Catch: java.lang.Throwable -> L42
        L40:
            monitor-exit(r4)
            return
        L42:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.onStrategyChanged(com.tencent.bugly.crashreport.common.strategy.StrategyBean):void");
    }

    public boolean putKeyValueToNative(String str, String str2) {
        if ((this.f17408i || this.f17409j) && str != null && str2 != null) {
            try {
                if (this.f17409j) {
                    return putNativeKeyValue(str, str2);
                }
                Boolean bool = (Boolean) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "putNativeKeyValue", new Class[]{String.class, String.class}, new Object[]{str, str2});
                if (bool != null) {
                    return bool.booleanValue();
                }
                return false;
            } catch (UnsatisfiedLinkError unused) {
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }

    public native boolean putNativeKeyValue(String str, String str2);

    public native String regist(String str, boolean z2, int i2);

    public void removeEmptyNativeRecordFiles() {
        be.c(f17399a);
    }

    public native String removeNativeKeyValue(String str);

    public void resendSigquit() {
        a(20, "");
    }

    public boolean setNativeAppChannel(String str) {
        return a(12, str);
    }

    public boolean setNativeAppPackage(String str) {
        return a(13, str);
    }

    public boolean setNativeAppVersion(String str) {
        return a(10, str);
    }

    public native void setNativeInfo(int i2, String str);

    @Override // com.tencent.bugly.proguard.q
    public boolean setNativeIsAppForeground(boolean z2) {
        return a(14, z2 ? a.f27523u : a.f27524v);
    }

    public boolean setNativeLaunchTime(long j2) {
        try {
            return a(15, String.valueOf(j2));
        } catch (NumberFormatException e2) {
            if (al.a(e2)) {
                return false;
            }
            e2.printStackTrace();
            return false;
        }
    }

    public boolean setNativeUserId(String str) {
        return a(11, str);
    }

    public synchronized void setUserOpened(boolean z2) {
        c(z2);
        boolean zIsUserOpened = isUserOpened();
        ac acVarA = ac.a();
        if (acVarA != null) {
            zIsUserOpened = zIsUserOpened && acVarA.c().f17341f;
        }
        if (zIsUserOpened != this.f17410k) {
            al.a("native changed to %b", Boolean.valueOf(zIsUserOpened));
            b(zIsUserOpened);
        }
    }

    public synchronized void startNativeMonitor() {
        if (!this.f17409j && !this.f17408i) {
            boolean z2 = !ap.b(this.f17404e.f17434t);
            if (at.f17578b) {
                boolean zA = a(z2 ? this.f17404e.f17434t : "Bugly_Native", z2);
                this.f17409j = zA;
                if (!zA && !z2) {
                    this.f17408i = a("NativeRQD", false);
                }
            } else {
                String str = "Bugly_Native";
                aa aaVar = this.f17404e;
                String str2 = aaVar.f17434t;
                if (z2) {
                    str = str2;
                } else {
                    aaVar.getClass();
                }
                this.f17409j = a(str, z2);
            }
            if (this.f17409j || this.f17408i) {
                a(this.f17407h);
                setNativeAppVersion(this.f17404e.f17429o);
                setNativeAppChannel(this.f17404e.f17433s);
                setNativeAppPackage(this.f17404e.f17417c);
                setNativeUserId(this.f17404e.f());
                setNativeIsAppForeground(this.f17404e.a());
                setNativeLaunchTime(this.f17404e.f17415a);
                return;
            }
            return;
        }
        a(this.f17407h);
    }

    public native void testCrash();

    public void testNativeCrash() {
        if (this.f17409j) {
            testCrash();
        } else {
            al.d("[Native] Bugly SO file has not been load.", new Object[0]);
        }
    }

    public void unBlockSigquit(boolean z2) {
        if (z2) {
            a(21, a.f27523u);
        } else {
            a(21, a.f27524v);
        }
    }

    public native String unregist();

    private synchronized void c() {
        if (!this.f17410k) {
            al.d("[Native] Native crash report has already unregistered.", new Object[0]);
            return;
        }
        try {
        } catch (Throwable unused) {
            al.c("[Native] Failed to close native crash report.", new Object[0]);
        }
        if (unregist() != null) {
            al.a("[Native] Successfully closed native crash report.", new Object[0]);
            this.f17410k = false;
            return;
        }
        try {
            ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", new Class[]{Boolean.TYPE}, new Object[]{Boolean.FALSE});
            this.f17410k = false;
            al.a("[Native] Successfully closed native crash report.", new Object[0]);
            return;
        } catch (Throwable unused2) {
            al.c("[Native] Failed to close native crash report.", new Object[0]);
            this.f17409j = false;
            this.f17408i = false;
            return;
        }
    }

    public static /* synthetic */ boolean a(NativeCrashHandler nativeCrashHandler, String str) {
        return nativeCrashHandler.a(999, str);
    }

    private synchronized void b(boolean z2) {
        if (z2) {
            startNativeMonitor();
        } else {
            c();
        }
    }

    private synchronized void a(boolean z2) {
        if (this.f17410k) {
            al.d("[Native] Native crash report has already registered.", new Object[0]);
            return;
        }
        if (this.f17409j) {
            try {
                String strRegist = regist(f17399a, z2, f17401c);
                if (strRegist != null) {
                    al.a("[Native] Native Crash Report enable.", new Object[0]);
                    this.f17404e.f17435u = strRegist;
                    String strConcat = "-".concat(strRegist);
                    if (!at.f17578b && !this.f17404e.f17422h.contains(strConcat)) {
                        aa aaVar = this.f17404e;
                        aaVar.f17422h = aaVar.f17422h.concat("-").concat(this.f17404e.f17435u);
                    }
                    al.a("comInfo.sdkVersion %s", this.f17404e.f17422h);
                    this.f17410k = true;
                    String runningCpuAbi = getRunningCpuAbi();
                    if (!TextUtils.isEmpty(runningCpuAbi)) {
                        this.f17404e.e(runningCpuAbi);
                    }
                    return;
                }
            } catch (Throwable unused) {
                al.c("[Native] Failed to load Bugly SO file.", new Object[0]);
            }
        } else if (this.f17408i) {
            try {
                Class cls = Integer.TYPE;
                Class[] clsArr = {String.class, String.class, cls, cls};
                Object[] objArr = new Object[4];
                objArr[0] = f17399a;
                objArr[1] = ab.d();
                objArr[2] = Integer.valueOf(z2 ? 1 : 5);
                objArr[3] = 1;
                String str = (String) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler2", clsArr, objArr);
                if (str == null) {
                    aa.b();
                    str = (String) ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "registNativeExceptionHandler", new Class[]{String.class, String.class, cls}, new Object[]{f17399a, ab.d(), Integer.valueOf(aa.B())});
                }
                if (str != null) {
                    this.f17410k = true;
                    this.f17404e.f17435u = str;
                    ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", new Class[]{Boolean.TYPE}, new Object[]{Boolean.TRUE});
                    ap.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "setLogMode", new Class[]{cls}, new Object[]{Integer.valueOf(z2 ? 1 : 5)});
                    String runningCpuAbi2 = getRunningCpuAbi();
                    if (!TextUtils.isEmpty(runningCpuAbi2)) {
                        this.f17404e.e(runningCpuAbi2);
                    }
                    return;
                }
            } catch (Throwable unused2) {
            }
        }
        this.f17409j = false;
        this.f17408i = false;
    }

    public static synchronized NativeCrashHandler getInstance() {
        return f17400b;
    }

    public void testNativeCrash(boolean z2, boolean z3, boolean z4) {
        a(16, String.valueOf(z2));
        a(17, String.valueOf(z3));
        a(18, String.valueOf(z4));
        testNativeCrash();
    }

    private synchronized void c(boolean z2) {
        if (this.f17411l != z2) {
            al.a("user change native %b", Boolean.valueOf(z2));
            this.f17411l = z2;
        }
    }

    private static boolean a(String str, boolean z2) {
        boolean z3;
        try {
            al.a("[Native] Trying to load so: %s", str);
            if (z2) {
                System.load(str);
            } else {
                System.loadLibrary(str);
            }
            try {
                al.a("[Native] Successfully loaded SO: %s", str);
                return true;
            } catch (Throwable th) {
                th = th;
                z3 = true;
                al.d(th.getMessage(), new Object[0]);
                al.d("[Native] Failed to load so: %s", str);
                return z3;
            }
        } catch (Throwable th2) {
            th = th2;
            z3 = false;
        }
    }

    private boolean a(int i2, String str) {
        if (!this.f17409j) {
            return false;
        }
        try {
            setNativeInfo(i2, str);
            return true;
        } catch (UnsatisfiedLinkError unused) {
            return false;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }
}
