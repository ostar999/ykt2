package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import cn.hutool.core.text.StrPool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes6.dex */
public final class ao {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f17518a = true;

    /* renamed from: b, reason: collision with root package name */
    public static boolean f17519b = true;

    /* renamed from: c, reason: collision with root package name */
    private static SimpleDateFormat f17520c = null;

    /* renamed from: d, reason: collision with root package name */
    private static int f17521d = 30720;

    /* renamed from: e, reason: collision with root package name */
    private static StringBuilder f17522e = null;

    /* renamed from: f, reason: collision with root package name */
    private static StringBuilder f17523f = null;

    /* renamed from: g, reason: collision with root package name */
    private static boolean f17524g = false;

    /* renamed from: h, reason: collision with root package name */
    private static a f17525h = null;

    /* renamed from: i, reason: collision with root package name */
    private static String f17526i = null;

    /* renamed from: j, reason: collision with root package name */
    private static String f17527j = null;

    /* renamed from: k, reason: collision with root package name */
    private static Context f17528k = null;

    /* renamed from: l, reason: collision with root package name */
    private static String f17529l = null;

    /* renamed from: m, reason: collision with root package name */
    private static boolean f17530m = false;

    /* renamed from: n, reason: collision with root package name */
    private static boolean f17531n = false;

    /* renamed from: o, reason: collision with root package name */
    private static ExecutorService f17532o;

    /* renamed from: p, reason: collision with root package name */
    private static int f17533p;

    /* renamed from: q, reason: collision with root package name */
    private static final Object f17534q = new Object();

    static {
        try {
            f17520c = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
            al.b(th.getCause());
        }
    }

    public static synchronized void a(Context context) {
        if (f17530m || context == null || !f17519b) {
            return;
        }
        try {
            f17532o = Executors.newSingleThreadExecutor();
            f17523f = new StringBuilder(0);
            f17522e = new StringBuilder(0);
            f17528k = context;
            f17526i = aa.a(context).f17418d;
            f17527j = "";
            f17529l = f17528k.getFilesDir().getPath() + "/buglylog_" + f17526i + StrPool.UNDERLINE + f17527j + ".txt";
            f17533p = Process.myPid();
        } catch (Throwable unused) {
        }
        f17530m = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean d(String str, String str2, String str3) {
        q qVar;
        try {
            aa aaVarB = aa.b();
            if (aaVarB == null || (qVar = aaVarB.N) == null) {
                return false;
            }
            return qVar.appendLogToNative(str, str2, str3);
        } catch (Throwable th) {
            if (al.a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void e(String str, String str2, String str3) {
        if (f17518a) {
            f(str, str2, str3);
        } else {
            g(str, str2, str3);
        }
    }

    private static synchronized void f(String str, String str2, String str3) {
        String strA = a(str, str2, str3, Process.myTid());
        synchronized (f17534q) {
            try {
                f17523f.append(strA);
                if (f17523f.length() >= f17521d) {
                    StringBuilder sb = f17523f;
                    f17523f = sb.delete(0, sb.indexOf("\u0001\r\n") + 1);
                }
            } finally {
            }
        }
    }

    private static synchronized void g(String str, String str2, String str3) {
        String strA = a(str, str2, str3, Process.myTid());
        synchronized (f17534q) {
            try {
                f17523f.append(strA);
            } catch (Throwable unused) {
            }
            if (f17523f.length() <= f17521d) {
                return;
            }
            if (f17524g) {
                return;
            }
            f17524g = true;
            a aVar = f17525h;
            if (aVar == null) {
                f17525h = new a(f17529l);
            } else {
                File file = aVar.f17542b;
                if (file == null || file.length() + f17523f.length() > f17525h.f17543c) {
                    f17525h.a();
                }
            }
            if (f17525h.a(f17523f.toString())) {
                f17523f.setLength(0);
                f17524g = false;
            }
        }
    }

    private static String b() {
        q qVar;
        try {
            aa aaVarB = aa.b();
            if (aaVarB == null || (qVar = aaVarB.N) == null) {
                return null;
            }
            return qVar.getLogFromNative();
        } catch (Throwable th) {
            if (al.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    private static byte[] c() {
        File file;
        if (!f17519b) {
            return null;
        }
        if (f17531n) {
            al.a("[LogUtil] Get user log from native.", new Object[0]);
            String strB = b();
            if (strB != null) {
                al.a("[LogUtil] Got user log from native: %d bytes", Integer.valueOf(strB.length()));
                return ap.a(strB, "BuglyNativeLog.txt");
            }
        }
        StringBuilder sb = new StringBuilder();
        synchronized (f17534q) {
            a aVar = f17525h;
            if (aVar != null && aVar.f17541a && (file = aVar.f17542b) != null && file.length() > 0) {
                sb.append(ap.a(f17525h.f17542b, 30720, true));
            }
            StringBuilder sb2 = f17523f;
            if (sb2 != null && sb2.length() > 0) {
                sb.append(f17523f.toString());
            }
        }
        return ap.a(sb.toString(), "BuglyLog.txt");
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        boolean f17541a;

        /* renamed from: b, reason: collision with root package name */
        File f17542b;

        /* renamed from: c, reason: collision with root package name */
        long f17543c = 30720;

        /* renamed from: d, reason: collision with root package name */
        private String f17544d;

        /* renamed from: e, reason: collision with root package name */
        private long f17545e;

        public a(String str) {
            if (str == null || str.equals("")) {
                return;
            }
            this.f17544d = str;
            this.f17541a = a();
        }

        public final boolean a() {
            try {
                File file = new File(this.f17544d);
                this.f17542b = file;
                if (file.exists() && !this.f17542b.delete()) {
                    this.f17541a = false;
                    return false;
                }
                if (this.f17542b.createNewFile()) {
                    return true;
                }
                this.f17541a = false;
                return false;
            } catch (Throwable th) {
                al.a(th);
                this.f17541a = false;
                return false;
            }
        }

        public final boolean a(String str) throws IOException {
            FileOutputStream fileOutputStream;
            if (!this.f17541a) {
                return false;
            }
            FileOutputStream fileOutputStream2 = null;
            try {
                fileOutputStream = new FileOutputStream(this.f17542b, true);
            } catch (Throwable th) {
                th = th;
            }
            try {
                fileOutputStream.write(str.getBytes("UTF-8"));
                fileOutputStream.flush();
                fileOutputStream.close();
                this.f17545e += r10.length;
                this.f17541a = true;
                try {
                    fileOutputStream.close();
                } catch (IOException unused) {
                }
                return true;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream2 = fileOutputStream;
                try {
                    al.a(th);
                    this.f17541a = false;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    return false;
                } catch (Throwable th3) {
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused3) {
                        }
                    }
                    throw th3;
                }
            }
        }
    }

    public static void a(int i2) {
        synchronized (f17534q) {
            f17521d = i2;
            if (i2 < 0) {
                f17521d = 0;
            } else if (i2 > 30720) {
                f17521d = 30720;
            }
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (th == null) {
            return;
        }
        String message = th.getMessage();
        if (message == null) {
            message = "";
        }
        a(str, str2, message + '\n' + ap.b(th));
    }

    public static synchronized void a(final String str, final String str2, final String str3) {
        if (f17530m && f17519b) {
            try {
                if (f17531n) {
                    f17532o.execute(new Runnable() { // from class: com.tencent.bugly.proguard.ao.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ao.d(str, str2, str3);
                        }
                    });
                } else {
                    f17532o.execute(new Runnable() { // from class: com.tencent.bugly.proguard.ao.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            ao.e(str, str2, str3);
                        }
                    });
                }
            } catch (Exception e2) {
                al.b(e2);
            }
        }
    }

    private static String a(String str, String str2, String str3, long j2) {
        String string;
        f17522e.setLength(0);
        if (str3.length() > 30720) {
            str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = f17520c;
        if (simpleDateFormat != null) {
            string = simpleDateFormat.format(date);
        } else {
            string = date.toString();
        }
        StringBuilder sb = f17522e;
        sb.append(string);
        sb.append(" ");
        sb.append(f17533p);
        sb.append(" ");
        sb.append(j2);
        sb.append(" ");
        sb.append(str);
        sb.append(" ");
        sb.append(str2);
        sb.append(": ");
        sb.append(str3);
        sb.append("\u0001\r\n");
        return f17522e.toString();
    }

    public static byte[] a() {
        if (f17518a) {
            if (f17519b) {
                return ap.a(f17523f.toString(), "BuglyLog.txt");
            }
            return null;
        }
        return c();
    }
}
