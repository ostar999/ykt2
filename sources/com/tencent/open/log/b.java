package com.tencent.open.log;

import android.text.TextUtils;
import com.tencent.open.log.d;
import com.tencent.open.utils.k;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static SimpleDateFormat f20611a = d.C0351d.a("yy.MM.dd.HH");

    /* renamed from: g, reason: collision with root package name */
    private File f20617g;

    /* renamed from: b, reason: collision with root package name */
    private String f20612b = "Tracer.File";

    /* renamed from: c, reason: collision with root package name */
    private int f20613c = Integer.MAX_VALUE;

    /* renamed from: d, reason: collision with root package name */
    private int f20614d = Integer.MAX_VALUE;

    /* renamed from: e, reason: collision with root package name */
    private int f20615e = 4096;

    /* renamed from: f, reason: collision with root package name */
    private long f20616f = com.heytap.mcssdk.constant.a.f7153q;

    /* renamed from: h, reason: collision with root package name */
    private int f20618h = 10;

    /* renamed from: i, reason: collision with root package name */
    private String f20619i = ".log";

    /* renamed from: j, reason: collision with root package name */
    private long f20620j = Long.MAX_VALUE;

    public b(File file, int i2, int i3, int i4, String str, long j2, int i5, String str2, long j3) {
        a(file);
        b(i2);
        a(i3);
        c(i4);
        a(str);
        a(j2);
        d(i5);
        b(str2);
        b(j3);
    }

    private File[] c(long j2) {
        File file;
        File fileB = b();
        String strC = c(d(j2));
        try {
            fileB = new File(fileB, strC);
        } catch (Throwable th) {
            SLog.e(SLog.TAG, "getWorkFile,get old sdcard file exception:", th);
        }
        String strB = k.b();
        if (TextUtils.isEmpty(strB) && strB == null) {
            file = null;
        } else {
            try {
                File file2 = new File(strB, c.f20635o);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                file = new File(file2, strC);
            } catch (Exception e2) {
                SLog.e(SLog.TAG, "getWorkFile,get app specific file exception:", e2);
            }
        }
        return new File[]{fileB, file};
    }

    private String d(long j2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j2);
        return new SimpleDateFormat("yy.MM.dd.HH").format(calendar.getTime());
    }

    public File[] a() {
        return c(System.currentTimeMillis());
    }

    public File b() {
        File fileE = e();
        if (fileE != null) {
            fileE.mkdirs();
        }
        return fileE;
    }

    public File e() {
        return this.f20617g;
    }

    public int f() {
        return this.f20618h;
    }

    public void a(String str) {
        this.f20612b = str;
    }

    public void a(int i2) {
        this.f20613c = i2;
    }

    public void b(int i2) {
        this.f20614d = i2;
    }

    public void a(long j2) {
        this.f20616f = j2;
    }

    public void b(String str) {
        this.f20619i = str;
    }

    public void a(File file) {
        this.f20617g = file;
    }

    public void b(long j2) {
        this.f20620j = j2;
    }

    public int d() {
        return this.f20615e;
    }

    public void d(int i2) {
        this.f20618h = i2;
    }

    private String c(String str) {
        return "com.tencent.mobileqq_connectSdk." + str + ".log";
    }

    public String c() {
        return this.f20612b;
    }

    public void c(int i2) {
        this.f20615e = i2;
    }
}
