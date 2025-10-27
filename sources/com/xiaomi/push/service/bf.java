package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class bf {

    /* renamed from: a, reason: collision with root package name */
    private static bf f25650a;

    /* renamed from: a, reason: collision with other field name */
    private static String f1030a;

    /* renamed from: a, reason: collision with other field name */
    private Context f1031a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f1034a;

    /* renamed from: b, reason: collision with root package name */
    private Messenger f25651b;

    /* renamed from: a, reason: collision with other field name */
    private List<Message> f1033a = new ArrayList();

    /* renamed from: b, reason: collision with other field name */
    private boolean f1035b = false;

    /* renamed from: a, reason: collision with other field name */
    private Messenger f1032a = new Messenger(new bg(this, Looper.getMainLooper()));

    private bf(Context context) {
        this.f1034a = false;
        this.f1031a = context.getApplicationContext();
        if (a()) {
            com.xiaomi.channel.commonutils.logger.b.c("use miui push service");
            this.f1034a = true;
        }
    }

    private Message a(Intent intent) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 17;
        messageObtain.obj = intent;
        return messageObtain;
    }

    public static bf a(Context context) {
        if (f25650a == null) {
            f25650a = new bf(context);
        }
        return f25650a;
    }

    /* renamed from: a, reason: collision with other method in class */
    private synchronized void m729a(Intent intent) {
        if (this.f1035b) {
            Message messageA = a(intent);
            if (this.f1033a.size() >= 50) {
                this.f1033a.remove(0);
            }
            this.f1033a.add(messageA);
            return;
        }
        if (this.f25651b == null) {
            this.f1031a.bindService(intent, new bh(this), 1);
            this.f1035b = true;
            this.f1033a.clear();
            this.f1033a.add(a(intent));
        } else {
            try {
                this.f25651b.send(a(intent));
            } catch (RemoteException unused) {
                this.f25651b = null;
                this.f1035b = false;
            }
        }
    }

    private boolean a() throws PackageManager.NameNotFoundException {
        if (com.xiaomi.push.ab.f24591e) {
            return false;
        }
        try {
            PackageInfo packageInfo = this.f1031a.getPackageManager().getPackageInfo("com.xiaomi.xmsf", 4);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode >= 104;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m730a(Intent intent) {
        try {
            if (com.xiaomi.push.n.m679a() || Build.VERSION.SDK_INT < 26) {
                this.f1031a.startService(intent);
                return true;
            }
            m729a(intent);
            return true;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }
}
