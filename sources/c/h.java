package c;

import android.os.Environment;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import d.b;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    public static final String f2249a = "---rtc_log---";

    /* renamed from: b, reason: collision with root package name */
    public static final int f2250b = 2;

    /* renamed from: i, reason: collision with root package name */
    public static final long f2257i = 15000000;

    /* renamed from: j, reason: collision with root package name */
    public static b f2258j;

    /* renamed from: k, reason: collision with root package name */
    public static d f2259k;

    /* renamed from: d, reason: collision with root package name */
    public static final String f2252d = Environment.getExternalStorageDirectory() + "/rtcsdk/";

    /* renamed from: e, reason: collision with root package name */
    public static final String f2253e = d.b.a().getExternalFilesDir("") + "/rtcsdk/log/";

    /* renamed from: f, reason: collision with root package name */
    public static String f2254f = StrPool.UNDERLINE + f.e.f26884m + ".log";

    /* renamed from: g, reason: collision with root package name */
    public static String f2255g = "";

    /* renamed from: h, reason: collision with root package name */
    public static final String f2256h = System.getProperty("line.separator");

    /* renamed from: l, reason: collision with root package name */
    public static boolean f2260l = true;

    /* renamed from: m, reason: collision with root package name */
    public static int f2261m = 1;

    /* renamed from: c, reason: collision with root package name */
    public static final SimpleDateFormat f2251c = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

    public static /* synthetic */ class a {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f2262a;

        static {
            int[] iArr = new int[c.values().length];
            f2262a = iArr;
            try {
                iArr[c.BUNDLE_JOIN_ROOM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2262a[c.BUNDLE_LEAVE_ROOM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class b extends Thread {
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            synchronized (h.class) {
                try {
                    Log.d(h.f2249a, "delete result: " + h.b(new File(h.f2252d)));
                } catch (Exception e2) {
                    e2.printStackTrace();
                    Log.d(h.f2249a, "delete oldLogFile failed: " + e2.getMessage());
                }
                File file = new File(h.f2253e);
                long jC = h.c(file);
                Log.d(h.f2249a, "check log dir size: " + jC);
                if (jC > b.a.f26724j) {
                    Log.d(h.f2249a, "check log dir size: " + jC + "> SDK_LOG_SIZE: " + b.a.f26724j);
                    boolean zB = h.b(file);
                    StringBuilder sb = new StringBuilder();
                    sb.append("check delete result: ");
                    sb.append(zB);
                    Log.d(h.f2249a, sb.toString());
                    if (zB) {
                        if (h.f2259k != null) {
                            Log.d(h.f2249a, "check delete success start write thread ");
                            h.f2259k.start();
                        }
                    } else if (h.f2259k != null) {
                        Log.d(h.f2249a, "check delete failed,abandon write log");
                        d unused = h.f2259k = null;
                    }
                } else {
                    Log.d(h.f2249a, "do not need to delete log,start write thread");
                    if (h.f2259k != null) {
                        h.f2259k.start();
                    }
                }
            }
        }
    }

    public enum c {
        BUNDLE_JOIN_ROOM,
        BUNDLE_LEAVE_ROOM
    }

    public static void d(String str, String str2) {
        if (f2261m >= 2) {
            if (f2260l) {
                Log.i(f2249a + d(), str2);
            }
            a(h.b.a() + str + ":" + str2);
        }
    }

    public static void e() {
        f2255g = f2251c.format(new Date()) + f2254f;
        Log.d(f2249a, "startLog: " + f2255g);
        f2259k = new d();
        b bVar = new b();
        f2258j = bVar;
        bVar.start();
    }

    public static void f() {
        d dVar = f2259k;
        if (dVar != null) {
            dVar.interrupt();
            Log.d(f2249a, "want to stopLog: " + f2255g);
            f2259k = null;
        }
    }

    public static boolean b(File file) {
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return file.delete();
        }
        boolean zB = false;
        for (File file2 : file.listFiles()) {
            zB = b(file2);
        }
        return file.listFiles().length == 0 ? file.delete() : zB;
    }

    public static long c(File file) {
        if (file.isFile()) {
            return file.length();
        }
        File[] fileArrListFiles = file.listFiles();
        long j2 = 0;
        if (fileArrListFiles != null) {
            Log.d(f2249a, "getDirSize: file list size: " + fileArrListFiles.length);
            for (File file2 : fileArrListFiles) {
                long jC = c(file2);
                Log.d(f2249a, "child: " + file2.getPath() + "child size: " + jC);
                j2 += jC;
            }
        }
        return j2;
    }

    public static class d extends Thread {

        /* renamed from: a, reason: collision with root package name */
        public boolean f2266a;

        /* renamed from: b, reason: collision with root package name */
        public String f2267b;

        /* renamed from: c, reason: collision with root package name */
        public Object f2268c = new Object();

        /* renamed from: d, reason: collision with root package name */
        public ConcurrentLinkedQueue<String> f2269d = new ConcurrentLinkedQueue<>();

        public d() {
            this.f2266a = false;
            this.f2267b = null;
            String strD = d();
            Log.d(h.f2249a, "WriteThread: ctor " + strD);
            if (strD != null) {
                this.f2267b = strD + h.f2255g;
            }
            this.f2266a = true;
        }

        public void a(String str) {
            this.f2269d.add(str);
            if (f()) {
                return;
            }
            a();
        }

        public void b(String str) throws IOException {
            File file = new File(this.f2267b);
            if (file.exists() && file.isFile() && file.length() > h.f2257i) {
                boolean zDelete = file.delete();
                Log.i(h.f2249a + h.d(), "Log file size: " + file.length() + "delete result: " + zDelete);
            }
            if (!file.exists()) {
                try {
                    file.getParentFile().mkdirs();
                    Log.i(h.f2249a + h.d(), "create rtclog file: " + file.getAbsoluteFile());
                    boolean zCreateNewFile = file.createNewFile();
                    Log.i(h.f2249a + h.d(), "create rtclog file " + zCreateNewFile);
                } catch (IOException e2) {
                    Log.i(h.f2249a + h.d(), "create rtc log failed for " + e2.getMessage());
                    e2.printStackTrace();
                }
            }
            try {
                FileWriter fileWriter = new FileWriter(file, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(str);
                bufferedWriter.newLine();
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }

        public boolean c() {
            return Environment.getExternalStorageState().equals("mounted");
        }

        public String d() {
            if (c()) {
                return h.f2253e;
            }
            return null;
        }

        public boolean e() {
            File file = new File(this.f2267b);
            return file.exists() && file.length() > 3;
        }

        public boolean f() {
            return this.f2266a;
        }

        public void g() {
            this.f2266a = false;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                synchronized (this.f2268c) {
                    this.f2266a = true;
                    while (!this.f2269d.isEmpty()) {
                        try {
                            b(this.f2269d.poll());
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        Log.d(h.f2249a, "write thread receive interrupted,stop record log");
                        return;
                    }
                    this.f2266a = false;
                    try {
                        this.f2268c.wait();
                    } catch (InterruptedException e3) {
                        e3.printStackTrace();
                        Log.d(h.f2249a, "write thread receive interrupted in wait,stop record log");
                    }
                }
            }
        }

        public void a() {
            synchronized (this.f2268c) {
                this.f2268c.notify();
            }
        }

        public void b() {
            File file = new File(this.f2267b);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static void a(boolean z2) {
        f2260l = z2;
    }

    public static void a(int i2) {
        f2261m = i2;
    }

    public static String d() {
        try {
            Exception exc = new Exception();
            if (exc.getStackTrace() != null && exc.getStackTrace().length > 2) {
                StackTraceElement stackTraceElement = exc.getStackTrace()[2];
                String className = stackTraceElement.getClassName();
                int iLastIndexOf = className.lastIndexOf(StrPool.DOT);
                if (iLastIndexOf > 0) {
                    className = className.substring(iLastIndexOf + 1);
                }
                return className + StrPool.UNDERLINE + stackTraceElement.getMethodName() + StrPool.UNDERLINE + stackTraceElement.getLineNumber();
            }
            return "***";
        } catch (Throwable th) {
            th.printStackTrace();
            return "***";
        }
    }

    public static void a(c cVar, String str) {
        int i2 = a.f2262a[cVar.ordinal()];
        if (i2 == 1) {
            b("sdk join room", str);
        } else {
            if (i2 != 2) {
                return;
            }
            b("sdk leave room", str);
        }
    }

    public static void e(String str, String str2) {
        if (f2261m >= 3) {
            if (f2260l) {
                Log.w(f2249a + d(), str2);
            }
            a(h.b.a() + str + ":" + str2);
        }
    }

    public static void a(String str, String str2) {
        if (f2261m >= 1) {
            if (f2260l) {
                Log.d(f2249a + d(), str2);
            }
            a(h.b.a() + str + ":" + str2);
        }
    }

    public static void c(String str, String str2) {
        if (f2261m >= 4) {
            if (f2260l) {
                Log.e(f2249a + d(), str2);
            }
            a(h.b.a() + str + ":" + str2);
        }
    }

    public static void b(String str, String str2) {
        if (f2261m >= 1) {
            if (f2260l) {
                Log.d(f2249a + d(), "*****************************" + str2 + "*****************************");
            }
            b(str + ":" + str2);
        }
    }

    public static synchronized void a(String str) {
        String str2 = f2251c.format(new Date());
        d dVar = f2259k;
        if (dVar != null) {
            dVar.a(str2 + " " + str);
        }
    }

    public static synchronized void b(String str) {
        String str2 = f2251c.format(new Date());
        d dVar = f2259k;
        if (dVar != null) {
            dVar.a("*****************************" + str2 + " " + str + "*****************************");
        }
    }
}
