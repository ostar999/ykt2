package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import java.lang.Thread;

/* loaded from: classes6.dex */
public final class av implements Thread.UncaughtExceptionHandler {

    /* renamed from: h, reason: collision with root package name */
    private static String f17626h;

    /* renamed from: i, reason: collision with root package name */
    private static final Object f17627i = new Object();

    /* renamed from: a, reason: collision with root package name */
    protected final Context f17628a;

    /* renamed from: b, reason: collision with root package name */
    protected final as f17629b;

    /* renamed from: c, reason: collision with root package name */
    protected final ac f17630c;

    /* renamed from: d, reason: collision with root package name */
    protected final aa f17631d;

    /* renamed from: e, reason: collision with root package name */
    protected Thread.UncaughtExceptionHandler f17632e;

    /* renamed from: f, reason: collision with root package name */
    protected Thread.UncaughtExceptionHandler f17633f;

    /* renamed from: g, reason: collision with root package name */
    protected boolean f17634g = false;

    /* renamed from: j, reason: collision with root package name */
    private int f17635j;

    public av(Context context, as asVar, ac acVar, aa aaVar) {
        this.f17628a = context;
        this.f17629b = asVar;
        this.f17630c = acVar;
        this.f17631d = aaVar;
    }

    private static void c() {
        al.e("current process die", new Object[0]);
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    public final synchronized void a() {
        if (this.f17635j >= 10) {
            al.a("java crash handler over %d, no need set.", 10);
            return;
        }
        this.f17634g = true;
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler != null) {
            if (av.class.getName().equals(defaultUncaughtExceptionHandler.getClass().getName())) {
                return;
            }
            if ("com.android.internal.os.RuntimeInit$UncaughtHandler".equals(defaultUncaughtExceptionHandler.getClass().getName())) {
                al.a("backup system java handler: %s", defaultUncaughtExceptionHandler.toString());
                this.f17633f = defaultUncaughtExceptionHandler;
                this.f17632e = defaultUncaughtExceptionHandler;
            } else {
                al.a("backup java handler: %s", defaultUncaughtExceptionHandler.toString());
                this.f17632e = defaultUncaughtExceptionHandler;
            }
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.f17635j++;
        al.a("registered java monitor: %s", toString());
    }

    public final synchronized void b() {
        this.f17634g = false;
        al.a("close java monitor!", new Object[0]);
        if ("bugly".equals(Thread.getDefaultUncaughtExceptionHandler().getClass().getName())) {
            al.a("Java monitor to unregister: %s", toString());
            Thread.setDefaultUncaughtExceptionHandler(this.f17632e);
            this.f17635j--;
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final void uncaughtException(Thread thread, Throwable th) {
        synchronized (f17627i) {
            a(thread, th, true, null, null, this.f17631d.Q);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0105  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.tencent.bugly.crashreport.crash.CrashDetailBean b(java.lang.Thread r7, java.lang.Throwable r8, boolean r9, java.lang.String r10, byte[] r11, boolean r12) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.av.b(java.lang.Thread, java.lang.Throwable, boolean, java.lang.String, byte[], boolean):com.tencent.bugly.crashreport.crash.CrashDetailBean");
    }

    private static void a(CrashDetailBean crashDetailBean, Throwable th, boolean z2) {
        String strA;
        String name = th.getClass().getName();
        String strA2 = a(th);
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(th.getStackTrace().length);
        objArr[1] = Boolean.valueOf(th.getCause() != null);
        al.e("stack frame :%d, has cause %b", objArr);
        String str = "";
        String string = th.getStackTrace().length > 0 ? th.getStackTrace()[0].toString() : "";
        Throwable cause = th;
        while (cause != null && cause.getCause() != null) {
            cause = cause.getCause();
        }
        if (cause != null && cause != th) {
            crashDetailBean.f17372n = cause.getClass().getName();
            crashDetailBean.f17373o = a(cause);
            if (cause.getStackTrace().length > 0) {
                crashDetailBean.f17374p = cause.getStackTrace()[0].toString();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(":");
            sb.append(strA2);
            sb.append("\n");
            sb.append(string);
            sb.append("\n......");
            sb.append("\nCaused by:\n");
            sb.append(crashDetailBean.f17372n);
            sb.append(":");
            sb.append(crashDetailBean.f17373o);
            sb.append("\n");
            strA = a(cause, at.f17583h);
            sb.append(strA);
            crashDetailBean.f17375q = sb.toString();
        } else {
            crashDetailBean.f17372n = name;
            if (at.a().i() && z2) {
                al.e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
                str = " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]";
            }
            crashDetailBean.f17373o = strA2 + str;
            crashDetailBean.f17374p = string;
            strA = a(th, at.f17583h);
            crashDetailBean.f17375q = strA;
        }
        crashDetailBean.f17379u = ap.c(crashDetailBean.f17375q.getBytes());
        crashDetailBean.f17384z.put(crashDetailBean.B, strA);
    }

    private static boolean a(Thread thread) {
        synchronized (f17627i) {
            if (f17626h != null && thread.getName().equals(f17626h)) {
                return true;
            }
            f17626h = thread.getName();
            return false;
        }
    }

    public final void a(Thread thread, Throwable th, boolean z2, String str, byte[] bArr, boolean z3) {
        if (z2) {
            al.e("Java Crash Happen cause by %s(%d)", thread.getName(), Long.valueOf(thread.getId()));
            if (a(thread)) {
                al.a("this class has handled this exception", new Object[0]);
                if (this.f17633f != null) {
                    al.a("call system handler", new Object[0]);
                    this.f17633f.uncaughtException(thread, th);
                } else {
                    c();
                }
            }
        } else {
            al.e("Java Catch Happen", new Object[0]);
        }
        try {
            if (!this.f17634g) {
                al.c("Java crash handler is disable. Just return.", new Object[0]);
                if (z2) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f17632e;
                    if (uncaughtExceptionHandler != null && a(uncaughtExceptionHandler)) {
                        al.e("sys default last handle start!", new Object[0]);
                        this.f17632e.uncaughtException(thread, th);
                        al.e("sys default last handle end!", new Object[0]);
                        return;
                    } else if (this.f17633f != null) {
                        al.e("system handle start!", new Object[0]);
                        this.f17633f.uncaughtException(thread, th);
                        al.e("system handle end!", new Object[0]);
                        return;
                    } else {
                        al.e("crashreport last handle start!", new Object[0]);
                        c();
                        al.e("crashreport last handle end!", new Object[0]);
                        return;
                    }
                }
                return;
            }
            if (!this.f17630c.b()) {
                al.d("no remote but still store!", new Object[0]);
            }
            if (!this.f17630c.c().f17341f && this.f17630c.b()) {
                al.e("crash report was closed by remote , will not upload to Bugly , print local for helpful!", new Object[0]);
                as.a(z2 ? "JAVA_CRASH" : "JAVA_CATCH", ap.a(), this.f17631d.f17418d, thread.getName(), ap.a(th), null);
                if (z2) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler2 = this.f17632e;
                    if (uncaughtExceptionHandler2 != null && a(uncaughtExceptionHandler2)) {
                        al.e("sys default last handle start!", new Object[0]);
                        this.f17632e.uncaughtException(thread, th);
                        al.e("sys default last handle end!", new Object[0]);
                        return;
                    } else if (this.f17633f != null) {
                        al.e("system handle start!", new Object[0]);
                        this.f17633f.uncaughtException(thread, th);
                        al.e("system handle end!", new Object[0]);
                        return;
                    } else {
                        al.e("crashreport last handle start!", new Object[0]);
                        c();
                        al.e("crashreport last handle end!", new Object[0]);
                        return;
                    }
                }
                return;
            }
            CrashDetailBean crashDetailBeanB = b(thread, th, z2, str, bArr, z3);
            if (crashDetailBeanB == null) {
                al.e("pkg crash datas fail!", new Object[0]);
                if (z2) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler3 = this.f17632e;
                    if (uncaughtExceptionHandler3 != null && a(uncaughtExceptionHandler3)) {
                        al.e("sys default last handle start!", new Object[0]);
                        this.f17632e.uncaughtException(thread, th);
                        al.e("sys default last handle end!", new Object[0]);
                        return;
                    } else if (this.f17633f != null) {
                        al.e("system handle start!", new Object[0]);
                        this.f17633f.uncaughtException(thread, th);
                        al.e("system handle end!", new Object[0]);
                        return;
                    } else {
                        al.e("crashreport last handle start!", new Object[0]);
                        c();
                        al.e("crashreport last handle end!", new Object[0]);
                        return;
                    }
                }
                return;
            }
            as.a(z2 ? "JAVA_CRASH" : "JAVA_CATCH", ap.a(), this.f17631d.f17418d, thread.getName(), ap.a(th), crashDetailBeanB);
            if (!this.f17629b.a(crashDetailBeanB, z2)) {
                this.f17629b.b(crashDetailBeanB, z2);
            }
            if (z2) {
                this.f17629b.a(crashDetailBeanB);
            }
            if (z2) {
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler4 = this.f17632e;
                if (uncaughtExceptionHandler4 != null && a(uncaughtExceptionHandler4)) {
                    al.e("sys default last handle start!", new Object[0]);
                    this.f17632e.uncaughtException(thread, th);
                    al.e("sys default last handle end!", new Object[0]);
                } else if (this.f17633f != null) {
                    al.e("system handle start!", new Object[0]);
                    this.f17633f.uncaughtException(thread, th);
                    al.e("system handle end!", new Object[0]);
                } else {
                    al.e("crashreport last handle start!", new Object[0]);
                    c();
                    al.e("crashreport last handle end!", new Object[0]);
                }
            }
        } catch (Throwable th2) {
            try {
                if (!al.a(th2)) {
                    th2.printStackTrace();
                }
                if (z2) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler5 = this.f17632e;
                    if (uncaughtExceptionHandler5 != null && a(uncaughtExceptionHandler5)) {
                        al.e("sys default last handle start!", new Object[0]);
                        this.f17632e.uncaughtException(thread, th);
                        al.e("sys default last handle end!", new Object[0]);
                    } else if (this.f17633f != null) {
                        al.e("system handle start!", new Object[0]);
                        this.f17633f.uncaughtException(thread, th);
                        al.e("system handle end!", new Object[0]);
                    } else {
                        al.e("crashreport last handle start!", new Object[0]);
                        c();
                        al.e("crashreport last handle end!", new Object[0]);
                    }
                }
            } catch (Throwable th3) {
                if (z2) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler6 = this.f17632e;
                    if (uncaughtExceptionHandler6 != null && a(uncaughtExceptionHandler6)) {
                        al.e("sys default last handle start!", new Object[0]);
                        this.f17632e.uncaughtException(thread, th);
                        al.e("sys default last handle end!", new Object[0]);
                    } else if (this.f17633f != null) {
                        al.e("system handle start!", new Object[0]);
                        this.f17633f.uncaughtException(thread, th);
                        al.e("system handle end!", new Object[0]);
                    } else {
                        al.e("crashreport last handle start!", new Object[0]);
                        c();
                        al.e("crashreport last handle end!", new Object[0]);
                    }
                }
                throw th3;
            }
        }
    }

    private static boolean a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        if (uncaughtExceptionHandler == null) {
            return true;
        }
        String name = uncaughtExceptionHandler.getClass().getName();
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            if (name.equals(className) && "uncaughtException".equals(methodName)) {
                return false;
            }
        }
        return true;
    }

    public final synchronized void a(StrategyBean strategyBean) {
        if (strategyBean != null) {
            boolean z2 = strategyBean.f17341f;
            if (z2 != this.f17634g) {
                al.a("java changed to %b", Boolean.valueOf(z2));
                if (strategyBean.f17341f) {
                    a();
                    return;
                }
                b();
            }
        }
    }

    private static String a(Throwable th, int i2) {
        if (th == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try {
            if (th.getStackTrace() != null) {
                for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                    if (i2 > 0 && sb.length() >= i2) {
                        sb.append("\n[Stack over limit size :" + i2 + " , has been cutted !]");
                        return sb.toString();
                    }
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
            }
        } catch (Throwable th2) {
            al.e("gen stack error %s", th2.toString());
        }
        return sb.toString();
    }

    private static String a(Throwable th) {
        String message = th.getMessage();
        if (message == null) {
            return "";
        }
        if (message.length() <= 1000) {
            return message;
        }
        return message.substring(0, 1000) + "\n[Message over limit size:1000, has been cutted!]";
    }
}
