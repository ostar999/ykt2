package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.xiaomi.push.ic;
import com.yikaobang.yixue.R2;
import java.lang.Thread;

/* loaded from: classes6.dex */
class z implements Thread.UncaughtExceptionHandler {

    /* renamed from: a, reason: collision with root package name */
    private static final Object f24585a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private static final String[] f172a = {"com.xiaomi.channel.commonutils", "com.xiaomi.common.logger", "com.xiaomi.measite.smack", "com.xiaomi.metoknlp", "com.xiaomi.mipush.sdk", "com.xiaomi.network", "com.xiaomi.push", "com.xiaomi.slim", "com.xiaomi.smack", "com.xiaomi.stats", "com.xiaomi.tinyData", "com.xiaomi.xmpush.thrift", "com.xiaomi.clientreport"};

    /* renamed from: a, reason: collision with other field name */
    private Context f173a;

    /* renamed from: a, reason: collision with other field name */
    private SharedPreferences f174a;

    /* renamed from: a, reason: collision with other field name */
    private Thread.UncaughtExceptionHandler f175a;

    public z(Context context) {
        this(context, Thread.getDefaultUncaughtExceptionHandler());
    }

    public z(Context context, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.f173a = context;
        this.f175a = uncaughtExceptionHandler;
    }

    private String a(Throwable th) {
        StackTraceElement[] stackTrace = th.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < Math.min(3, stackTrace.length); i2++) {
            sb.append(stackTrace[i2].toString() + "\r\n");
        }
        String string = sb.toString();
        return TextUtils.isEmpty(string) ? "" : com.xiaomi.push.ay.a(string);
    }

    /* renamed from: a, reason: collision with other method in class */
    private void m187a() {
        com.xiaomi.push.ai.a(this.f173a).a(new aa(this));
    }

    /* renamed from: a, reason: collision with other method in class */
    private void m189a(Throwable th) {
        String strB = b(th);
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        String strA = a(th);
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        s.a(this.f173a).a(strB, strA);
        if (m190a()) {
            m187a();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m190a() {
        this.f174a = this.f173a.getSharedPreferences("mipush_extra", 4);
        if (com.xiaomi.push.as.e(this.f173a)) {
            if (com.xiaomi.push.service.ao.a(this.f173a).a(ic.Crash4GUploadSwitch.a(), true)) {
                return ((float) Math.abs((System.currentTimeMillis() / 1000) - this.f174a.getLong("last_crash_upload_time_stamp", 0L))) >= ((float) Math.max(3600, com.xiaomi.push.service.ao.a(this.f173a).a(ic.Crash4GUploadFrequency.a(), 3600))) * 0.9f;
            }
            return false;
        }
        if (com.xiaomi.push.as.d(this.f173a)) {
            return Math.abs((System.currentTimeMillis() / 1000) - this.f174a.getLong("last_crash_upload_time_stamp", 0L)) >= ((long) Math.max(60, com.xiaomi.push.service.ao.a(this.f173a).a(ic.CrashWIFIUploadFrequency.a(), R2.attr.ic_knowledge_chart_data)));
        }
        return true;
    }

    private boolean a(boolean z2, String str) {
        for (String str2 : f172a) {
            if (str.contains(str2)) {
                return true;
            }
        }
        return z2;
    }

    private String b(Throwable th) {
        StackTraceElement[] stackTrace = th.getStackTrace();
        StringBuilder sb = new StringBuilder(th.toString());
        sb.append("\r\n");
        boolean zA = false;
        for (StackTraceElement stackTraceElement : stackTrace) {
            String string = stackTraceElement.toString();
            zA = a(zA, string);
            sb.append(string);
            sb.append("\r\n");
        }
        return zA ? sb.toString() : "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        SharedPreferences sharedPreferences = this.f173a.getSharedPreferences("mipush_extra", 4);
        this.f174a = sharedPreferences;
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putLong("last_crash_upload_time_stamp", System.currentTimeMillis() / 1000);
        com.xiaomi.push.t.a(editorEdit);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        m189a(th);
        Object obj = f24585a;
        synchronized (obj) {
            try {
                obj.wait(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
            } catch (InterruptedException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
            }
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f175a;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        } else {
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    }
}
