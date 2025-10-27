package com.tencent.bugly.proguard;

import android.app.ActivityManager;
import android.content.Context;
import android.os.FileObserver;
import android.os.Looper;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.anr.TraceFileHelper;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes6.dex */
public final class ay {

    /* renamed from: f, reason: collision with root package name */
    public static ay f17643f;

    /* renamed from: b, reason: collision with root package name */
    public final ActivityManager f17645b;

    /* renamed from: c, reason: collision with root package name */
    final aa f17646c;

    /* renamed from: d, reason: collision with root package name */
    final ak f17647d;

    /* renamed from: e, reason: collision with root package name */
    String f17648e;

    /* renamed from: g, reason: collision with root package name */
    private final Context f17649g;

    /* renamed from: h, reason: collision with root package name */
    private final ac f17650h;

    /* renamed from: i, reason: collision with root package name */
    private final as f17651i;

    /* renamed from: k, reason: collision with root package name */
    private FileObserver f17653k;

    /* renamed from: m, reason: collision with root package name */
    private bg f17655m;

    /* renamed from: n, reason: collision with root package name */
    private int f17656n;

    /* renamed from: a, reason: collision with root package name */
    public final AtomicBoolean f17644a = new AtomicBoolean(false);

    /* renamed from: j, reason: collision with root package name */
    private final Object f17652j = new Object();

    /* renamed from: l, reason: collision with root package name */
    private boolean f17654l = true;

    /* renamed from: o, reason: collision with root package name */
    private long f17657o = 0;

    public ay(Context context, ac acVar, aa aaVar, ak akVar, as asVar) {
        Context contextA = ap.a(context);
        this.f17649g = contextA;
        this.f17645b = (ActivityManager) contextA.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        if (ap.b(NativeCrashHandler.getDumpFilePath())) {
            this.f17648e = context.getDir("bugly", 0).getAbsolutePath();
        } else {
            this.f17648e = NativeCrashHandler.getDumpFilePath();
        }
        this.f17646c = aaVar;
        this.f17647d = akVar;
        this.f17650h = acVar;
        this.f17651i = asVar;
    }

    private synchronized void c() {
        if (e()) {
            al.d("start when started!", new Object[0]);
            return;
        }
        FileObserver fileObserver = new FileObserver("/data/anr/") { // from class: com.tencent.bugly.proguard.ay.1
            @Override // android.os.FileObserver
            public final void onEvent(int i2, String str) {
                if (str == null) {
                    return;
                }
                final String strConcat = "/data/anr/".concat(str);
                al.d("watching file %s", strConcat);
                if (strConcat.contains("trace")) {
                    ay.this.f17647d.a(new Runnable() { // from class: com.tencent.bugly.proguard.ay.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ay ayVar = ay.this;
                            String str2 = strConcat;
                            if (ayVar.a(true)) {
                                try {
                                    al.c("read trace first dump for create time!", new Object[0]);
                                    TraceFileHelper.a firstDumpInfo = TraceFileHelper.readFirstDumpInfo(str2, false);
                                    long jCurrentTimeMillis = firstDumpInfo != null ? firstDumpInfo.f17392c : -1L;
                                    if (jCurrentTimeMillis == -1) {
                                        al.d("trace dump fail could not get time!", new Object[0]);
                                        jCurrentTimeMillis = System.currentTimeMillis();
                                    }
                                    if (ayVar.a(jCurrentTimeMillis)) {
                                        return;
                                    }
                                    ayVar.a(jCurrentTimeMillis, str2);
                                } catch (Throwable th) {
                                    if (!al.a(th)) {
                                        th.printStackTrace();
                                    }
                                    al.e("handle anr error %s", th.getClass().toString());
                                }
                            }
                        }
                    });
                } else {
                    al.d("not anr file %s", strConcat);
                }
            }
        };
        this.f17653k = fileObserver;
        try {
            fileObserver.startWatching();
            al.a("start anr monitor!", new Object[0]);
            this.f17647d.a(new Runnable() { // from class: com.tencent.bugly.proguard.ay.2
                @Override // java.lang.Runnable
                public final void run() {
                    ay.a(ay.this);
                }
            });
        } catch (Throwable th) {
            this.f17653k = null;
            al.d("start anr monitor failed!", new Object[0]);
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private synchronized void d() {
        if (!e()) {
            al.d("close when closed!", new Object[0]);
            return;
        }
        try {
            this.f17653k.stopWatching();
            this.f17653k = null;
            al.d("close anr monitor!", new Object[0]);
        } catch (Throwable th) {
            al.d("stop anr monitor failed!", new Object[0]);
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private synchronized boolean e() {
        return this.f17653k != null;
    }

    private synchronized boolean f() {
        return this.f17654l;
    }

    private synchronized void g() {
        if (e()) {
            al.d("start when started!", new Object[0]);
            return;
        }
        if (TextUtils.isEmpty(this.f17648e)) {
            return;
        }
        synchronized (this.f17652j) {
            bg bgVar = this.f17655m;
            if (bgVar == null || !bgVar.isAlive()) {
                bg bgVar2 = new bg();
                this.f17655m = bgVar2;
                boolean z2 = this.f17646c.S;
                bgVar2.f17695b = z2;
                al.c("set record stack trace enable:".concat(String.valueOf(z2)), new Object[0]);
                bg bgVar3 = this.f17655m;
                StringBuilder sb = new StringBuilder("Bugly-ThreadMonitor");
                int i2 = this.f17656n;
                this.f17656n = i2 + 1;
                sb.append(i2);
                bgVar3.setName(sb.toString());
                this.f17655m.b();
            }
        }
        FileObserver fileObserver = new FileObserver(this.f17648e) { // from class: com.tencent.bugly.proguard.ay.3
            @Override // android.os.FileObserver
            public final void onEvent(int i3, String str) {
                if (str == null) {
                    return;
                }
                al.d("observe file, dir:%s fileName:%s", ay.this.f17648e, str);
                if (!(str.startsWith("manual_bugly_trace_") && str.endsWith(".txt"))) {
                    al.c("not manual trace file, ignore.", new Object[0]);
                    return;
                }
                if (!ay.this.f17644a.get()) {
                    al.c("proc is not in anr, just ignore", new Object[0]);
                    return;
                }
                if (ay.this.f17646c.a()) {
                    al.c("Found foreground anr, resend sigquit immediately.", new Object[0]);
                    NativeCrashHandler.getInstance().resendSigquit();
                    long jA = am.a(str, "manual_bugly_trace_", ".txt");
                    ay.this.a(jA, ay.this.f17648e + "/" + str);
                    al.c("Finish handling one anr.", new Object[0]);
                    return;
                }
                al.c("Found background anr, resend sigquit later.", new Object[0]);
                long jA2 = am.a(str, "manual_bugly_trace_", ".txt");
                ay.this.a(jA2, ay.this.f17648e + "/" + str);
                al.c("Finish handling one anr, now resend sigquit.", new Object[0]);
                NativeCrashHandler.getInstance().resendSigquit();
            }
        };
        this.f17653k = fileObserver;
        try {
            fileObserver.startWatching();
            al.a("startWatchingPrivateAnrDir! dumFilePath is %s", this.f17648e);
            this.f17647d.a(new Runnable() { // from class: com.tencent.bugly.proguard.ay.4
                @Override // java.lang.Runnable
                public final void run() {
                    ay.a(ay.this);
                }
            });
        } catch (Throwable th) {
            this.f17653k = null;
            al.d("startWatchingPrivateAnrDir failed!", new Object[0]);
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private synchronized void h() {
        if (!e()) {
            al.d("close when closed!", new Object[0]);
            return;
        }
        synchronized (this.f17652j) {
            bg bgVar = this.f17655m;
            if (bgVar != null) {
                bgVar.a();
                this.f17655m = null;
            }
        }
        al.a("stopWatchingPrivateAnrDir", new Object[0]);
        try {
            this.f17653k.stopWatching();
            this.f17653k = null;
            al.d("close anr monitor!", new Object[0]);
        } catch (Throwable th) {
            al.d("stop anr monitor failed!", new Object[0]);
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    public final void b(boolean z2) {
        d(z2);
        boolean zF = f();
        ac acVarA = ac.a();
        if (acVarA != null) {
            zF = zF && acVarA.c().f17341f;
        }
        if (zF != e()) {
            al.a("anr changed to %b", Boolean.valueOf(zF));
            c(zF);
        }
    }

    public static synchronized ay a() {
        return f17643f;
    }

    private CrashDetailBean a(ax axVar) {
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        try {
            crashDetailBean.C = ab.j();
            crashDetailBean.D = ab.f();
            crashDetailBean.E = ab.l();
            crashDetailBean.F = this.f17646c.k();
            crashDetailBean.G = this.f17646c.j();
            crashDetailBean.H = this.f17646c.l();
            crashDetailBean.I = ab.b(this.f17649g);
            crashDetailBean.J = ab.g();
            crashDetailBean.K = ab.h();
            crashDetailBean.f17360b = 3;
            crashDetailBean.f17363e = this.f17646c.g();
            aa aaVar = this.f17646c;
            crashDetailBean.f17364f = aaVar.f17429o;
            crashDetailBean.f17365g = aaVar.q();
            crashDetailBean.f17371m = this.f17646c.f();
            crashDetailBean.f17372n = "ANR_EXCEPTION";
            crashDetailBean.f17373o = axVar.f17641f;
            crashDetailBean.f17375q = axVar.f17642g;
            HashMap map = new HashMap();
            crashDetailBean.T = map;
            map.put("BUGLY_CR_01", axVar.f17640e);
            String str = crashDetailBean.f17375q;
            int iIndexOf = str != null ? str.indexOf("\n") : -1;
            crashDetailBean.f17374p = iIndexOf > 0 ? crashDetailBean.f17375q.substring(0, iIndexOf) : "GET_FAIL";
            crashDetailBean.f17376r = axVar.f17638c;
            String str2 = crashDetailBean.f17375q;
            if (str2 != null) {
                crashDetailBean.f17379u = ap.c(str2.getBytes());
            }
            crashDetailBean.f17384z = axVar.f17637b;
            crashDetailBean.A = axVar.f17636a;
            crashDetailBean.B = "main(1)";
            crashDetailBean.L = this.f17646c.s();
            crashDetailBean.f17366h = this.f17646c.p();
            crashDetailBean.f17367i = this.f17646c.A();
            crashDetailBean.f17380v = axVar.f17639d;
            aa aaVar2 = this.f17646c;
            crashDetailBean.P = aaVar2.f17435u;
            crashDetailBean.Q = aaVar2.f17415a;
            crashDetailBean.R = aaVar2.a();
            crashDetailBean.U = this.f17646c.z();
            aa aaVar3 = this.f17646c;
            crashDetailBean.V = aaVar3.f17438x;
            crashDetailBean.W = aaVar3.t();
            crashDetailBean.X = this.f17646c.y();
            crashDetailBean.f17383y = ao.a();
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
        }
        return crashDetailBean;
    }

    public final synchronized void b() {
        al.d("customer decides whether to open or close.", new Object[0]);
    }

    private synchronized void d(boolean z2) {
        if (this.f17654l != z2) {
            al.a("user change anr %b", Boolean.valueOf(z2));
            this.f17654l = z2;
        }
    }

    private synchronized void c(boolean z2) {
        if (z2) {
            g();
        } else {
            h();
        }
    }

    private static boolean a(String str, String str2, String str3) throws Throwable {
        Map<String, String[]> map;
        TraceFileHelper.a targetDumpInfo = TraceFileHelper.readTargetDumpInfo(str3, str, true);
        if (targetDumpInfo != null && (map = targetDumpInfo.f17393d) != null && !map.isEmpty()) {
            StringBuilder sb = new StringBuilder(1024);
            String[] strArr = targetDumpInfo.f17393d.get("main");
            if (strArr != null && strArr.length >= 3) {
                sb.append("\"main\" tid=");
                sb.append(strArr[2]);
                sb.append(" :\n");
                sb.append(strArr[0]);
                sb.append("\n");
                sb.append(strArr[1]);
                sb.append("\n\n");
            }
            for (Map.Entry<String, String[]> entry : targetDumpInfo.f17393d.entrySet()) {
                if (!entry.getKey().equals("main") && entry.getValue() != null && entry.getValue().length >= 3) {
                    sb.append("\"");
                    sb.append(entry.getKey());
                    sb.append("\" tid=");
                    sb.append(entry.getValue()[2]);
                    sb.append(" :\n");
                    sb.append(entry.getValue()[0]);
                    sb.append("\n");
                    sb.append(entry.getValue()[1]);
                    sb.append("\n\n");
                }
            }
            return am.a(str2, sb.toString(), sb.length() * 2);
        }
        al.e("not found trace dump for %s", str3);
        return false;
    }

    private static String a(List<ba> list, long j2) {
        if (list == null || list.isEmpty()) {
            return "main thread stack not enable";
        }
        StringBuilder sb = new StringBuilder(4096);
        sb.append("\n>>>>> 以下为anr过程中主线程堆栈记录，可根据堆栈出现次数推测在该堆栈阻塞的时间，出现次数越多对anr贡献越大，越可能是造成anr的原因 >>>>>\n");
        sb.append("\n>>>>> Thread Stack Traces Records Start >>>>>\n");
        for (int i2 = 0; i2 < list.size(); i2++) {
            ba baVar = list.get(i2);
            sb.append("Thread name:");
            sb.append(baVar.f17664a);
            sb.append("\n");
            long j3 = baVar.f17665b - j2;
            String str = j3 <= 0 ? "before " : "after ";
            sb.append("Got ");
            sb.append(str);
            sb.append("anr:");
            sb.append(Math.abs(j3));
            sb.append("ms\n");
            sb.append(baVar.f17666c);
            sb.append("\n");
            if (sb.length() * 2 >= 101376) {
                break;
            }
        }
        sb.append("\n<<<<< Thread Stack Traces Records End <<<<<\n");
        return sb.toString();
    }

    public final boolean a(boolean z2) {
        boolean zCompareAndSet = this.f17644a.compareAndSet(!z2, z2);
        al.c("tryChangeAnrState to %s, success:%s", Boolean.valueOf(z2), Boolean.valueOf(zCompareAndSet));
        return zCompareAndSet;
    }

    public final void a(long j2, String str) {
        ActivityManager.ProcessErrorStateInfo processErrorStateInfoA;
        List<ba> listC;
        try {
            al.c("anr time:%s", Long.valueOf(j2));
            synchronized (this.f17652j) {
                if (this.f17655m != null) {
                    al.c("Disable record main stack trace.", new Object[0]);
                    this.f17655m.c();
                }
            }
            String strA = ap.a(Looper.getMainLooper().getThread());
            Map<String, String> mapA = ap.a(this.f17646c.R, at.f17583h);
            if (this.f17646c.a()) {
                boolean z2 = ab.o() || ab.p();
                al.c("isAnrCrashDevice:%s", Boolean.valueOf(z2));
                if (!z2) {
                    processErrorStateInfoA = az.a(this.f17645b, 21000L);
                } else {
                    processErrorStateInfoA = az.a(this.f17645b, 0L);
                }
            } else {
                processErrorStateInfoA = az.a(this.f17645b, 0L);
            }
            if (processErrorStateInfoA == null) {
                al.c("proc state is invisible or not my proc!", new Object[0]);
                return;
            }
            ax axVar = new ax();
            axVar.f17638c = j2;
            axVar.f17636a = processErrorStateInfoA.processName;
            axVar.f17641f = processErrorStateInfoA.shortMsg;
            axVar.f17640e = processErrorStateInfoA.longMsg;
            axVar.f17637b = mapA;
            axVar.f17642g = strA;
            if (TextUtils.isEmpty(strA)) {
                axVar.f17642g = "main stack is null , some error may be encountered.";
            }
            Object[] objArr = new Object[7];
            objArr[0] = Long.valueOf(axVar.f17638c);
            objArr[1] = axVar.f17639d;
            objArr[2] = axVar.f17636a;
            objArr[3] = axVar.f17642g;
            objArr[4] = axVar.f17641f;
            objArr[5] = axVar.f17640e;
            Map<String, String> map = axVar.f17637b;
            objArr[6] = Integer.valueOf(map == null ? 0 : map.size());
            al.c("anr time:%d\ntrace file:%s\nproc:%s\nmain stack:%s\nshort msg:%s\nlong msg:%s\n threads:%d", objArr);
            al.a("found visible anr , start to upload!", new Object[0]);
            al.c("trace file:%s", str);
            if (!TextUtils.isEmpty(str) && new File(str).exists()) {
                File file = new File(this.f17648e, "bugly_trace_" + j2 + ".txt");
                al.c("trace file exists", new Object[0]);
                if (str.startsWith("/data/anr/")) {
                    al.a("backup trace isOK:%s", Boolean.valueOf(a(str, file.getAbsolutePath(), axVar.f17636a)));
                } else {
                    al.a("trace file rename :%s", Boolean.valueOf(new File(str).renameTo(file)));
                }
                synchronized (this.f17652j) {
                    bg bgVar = this.f17655m;
                    listC = bgVar != null ? bgVar.f17694a.c() : null;
                }
                if (listC != null) {
                    String strA2 = a(listC, j2);
                    al.c("save main stack trace", new Object[0]);
                    am.a(file, strA2, 2147483647L, true);
                }
                axVar.f17639d = file.getAbsolutePath();
            } else {
                al.c("trace file is null or not exists, just ignore", new Object[0]);
            }
            CrashDetailBean crashDetailBeanA = a(axVar);
            at.a().a(crashDetailBeanA);
            if (crashDetailBeanA.f17359a >= 0) {
                al.a("backup anr record success!", new Object[0]);
            } else {
                al.d("backup anr record fail!", new Object[0]);
            }
            as.a("ANR", ap.a(j2), axVar.f17636a, "main", axVar.f17642g, crashDetailBeanA);
            if (!this.f17651i.a(crashDetailBeanA, !ab.r())) {
                this.f17651i.b(crashDetailBeanA, true);
            }
            this.f17651i.a(crashDetailBeanA);
            synchronized (this.f17652j) {
                if (this.f17655m != null) {
                    al.c("Finish anr process.", new Object[0]);
                    this.f17655m.d();
                }
            }
        } catch (Throwable th) {
            try {
                al.b(th);
            } finally {
                a(false);
            }
        }
    }

    public final boolean a(long j2) {
        if (Math.abs(j2 - this.f17657o) < com.heytap.mcssdk.constant.a.f7153q) {
            al.d("should not process ANR too Fre in %dms", 10000);
            return true;
        }
        this.f17657o = j2;
        return false;
    }

    public static /* synthetic */ void a(ay ayVar) {
        long jCurrentTimeMillis = (at.f17585j + System.currentTimeMillis()) - ap.b();
        am.a(ayVar.f17648e, "bugly_trace_", ".txt", jCurrentTimeMillis);
        am.a(ayVar.f17648e, "manual_bugly_trace_", ".txt", jCurrentTimeMillis);
        am.a(ayVar.f17648e, "main_stack_record_", ".txt", jCurrentTimeMillis);
        am.a(ayVar.f17648e, "main_stack_record_", ".txt.merged", jCurrentTimeMillis);
    }
}
