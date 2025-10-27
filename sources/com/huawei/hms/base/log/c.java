package com.huawei.hms.base.log;

import android.os.Process;
import android.util.Log;
import cn.hutool.core.date.DatePattern;
import com.tencent.rtmp.sharp.jni.QLog;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: b, reason: collision with root package name */
    public String f7537b;

    /* renamed from: c, reason: collision with root package name */
    public String f7538c;

    /* renamed from: d, reason: collision with root package name */
    public int f7539d;

    /* renamed from: g, reason: collision with root package name */
    public String f7542g;

    /* renamed from: h, reason: collision with root package name */
    public int f7543h;

    /* renamed from: i, reason: collision with root package name */
    public int f7544i;

    /* renamed from: j, reason: collision with root package name */
    public int f7545j;

    /* renamed from: a, reason: collision with root package name */
    public final StringBuilder f7536a = new StringBuilder();

    /* renamed from: e, reason: collision with root package name */
    public long f7540e = 0;

    /* renamed from: f, reason: collision with root package name */
    public long f7541f = 0;

    public c(int i2, String str, int i3, String str2) {
        this.f7538c = "HMS";
        this.f7545j = i2;
        this.f7537b = str;
        this.f7539d = i3;
        if (str2 != null) {
            this.f7538c = str2;
        }
        b();
    }

    public static String a(int i2) {
        return i2 != 3 ? i2 != 4 ? i2 != 5 ? i2 != 6 ? String.valueOf(i2) : "E" : "W" : "I" : QLog.TAG_REPORTLEVEL_DEVELOPER;
    }

    public final c b() {
        this.f7540e = System.currentTimeMillis();
        Thread threadCurrentThread = Thread.currentThread();
        this.f7541f = threadCurrentThread.getId();
        this.f7543h = Process.myPid();
        StackTraceElement[] stackTrace = threadCurrentThread.getStackTrace();
        int length = stackTrace.length;
        int i2 = this.f7545j;
        if (length > i2) {
            StackTraceElement stackTraceElement = stackTrace[i2];
            this.f7542g = stackTraceElement.getFileName();
            this.f7544i = stackTraceElement.getLineNumber();
        }
        return this;
    }

    public String c() {
        StringBuilder sb = new StringBuilder();
        b(sb);
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        b(sb);
        a(sb);
        return sb.toString();
    }

    public <T> c a(T t2) {
        this.f7536a.append(t2);
        return this;
    }

    public c a(Throwable th) {
        a((c) '\n').a((c) Log.getStackTraceString(th));
        return this;
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        a(sb);
        return sb.toString();
    }

    public final StringBuilder a(StringBuilder sb) {
        sb.append(' ');
        sb.append(this.f7536a.toString());
        return sb;
    }

    public final StringBuilder b(StringBuilder sb) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.NORM_DATETIME_MS_PATTERN, Locale.getDefault());
        sb.append('[');
        sb.append(simpleDateFormat.format(Long.valueOf(this.f7540e)));
        String strA = a(this.f7539d);
        sb.append(' ');
        sb.append(strA);
        sb.append('/');
        sb.append(this.f7538c);
        sb.append('/');
        sb.append(this.f7537b);
        sb.append(' ');
        sb.append(this.f7543h);
        sb.append(':');
        sb.append(this.f7541f);
        sb.append(' ');
        sb.append(this.f7542g);
        sb.append(':');
        sb.append(this.f7544i);
        sb.append(']');
        return sb;
    }
}
