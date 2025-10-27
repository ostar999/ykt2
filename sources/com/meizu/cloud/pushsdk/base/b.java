package com.meizu.cloud.pushsdk.base;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import com.tencent.rtmp.sharp.jni.QLog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/* loaded from: classes4.dex */
class b implements g {

    /* renamed from: d, reason: collision with root package name */
    private long f9235d = 60;

    /* renamed from: e, reason: collision with root package name */
    private int f9236e = 10;

    /* renamed from: i, reason: collision with root package name */
    private boolean f9240i = false;

    /* renamed from: a, reason: collision with root package name */
    private SimpleDateFormat f9232a = new SimpleDateFormat("MM-dd HH:mm:ss");

    /* renamed from: b, reason: collision with root package name */
    private List<a> f9233b = Collections.synchronizedList(new ArrayList());

    /* renamed from: c, reason: collision with root package name */
    private Handler f9234c = new Handler(Looper.getMainLooper());

    /* renamed from: g, reason: collision with root package name */
    private String f9238g = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/pushSdk/defaultLog";

    /* renamed from: f, reason: collision with root package name */
    private e f9237f = new e();

    /* renamed from: h, reason: collision with root package name */
    private String f9239h = String.valueOf(Process.myPid());

    public class a {

        /* renamed from: a, reason: collision with root package name */
        String f9243a;

        /* renamed from: b, reason: collision with root package name */
        String f9244b;

        /* renamed from: c, reason: collision with root package name */
        String f9245c;

        public a(String str, String str2, String str3) {
            StringBuffer stringBuffer = new StringBuffer(b.this.f9232a.format(new Date()));
            stringBuffer.append(" ");
            stringBuffer.append(b.this.f9239h);
            stringBuffer.append("-");
            stringBuffer.append(String.valueOf(Thread.currentThread().getId()));
            stringBuffer.append(" ");
            stringBuffer.append(str);
            stringBuffer.append("/");
            this.f9243a = stringBuffer.toString();
            this.f9244b = str2;
            this.f9245c = str3;
        }
    }

    private void a(a aVar) {
        try {
            this.f9233b.add(aVar);
        } catch (Exception e2) {
            Log.e("Logger", "add logInfo error " + e2.getMessage());
        }
    }

    private void b() {
        if (this.f9233b.size() == 0) {
            this.f9234c.postDelayed(new Runnable() { // from class: com.meizu.cloud.pushsdk.base.b.1
                @Override // java.lang.Runnable
                public void run() {
                    b.this.a(true);
                }
            }, this.f9235d * 1000);
        }
    }

    private void c() {
        if (this.f9233b.size() == this.f9236e) {
            a(true);
        }
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void a(String str) {
        this.f9238g = str;
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void a(String str, String str2) {
        if (this.f9240i) {
            Log.d(str, str2);
        }
        synchronized (this.f9233b) {
            b();
            a(new a(QLog.TAG_REPORTLEVEL_DEVELOPER, str, str2));
            c();
        }
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void a(String str, String str2, Throwable th) {
        if (this.f9240i) {
            Log.e(str, str2, th);
        }
        synchronized (this.f9233b) {
            b();
            a(new a("E", str, str2 + "\n" + Log.getStackTraceString(th)));
            c();
        }
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void a(boolean z2) {
        Runnable runnable = new Runnable() { // from class: com.meizu.cloud.pushsdk.base.b.2
            @Override // java.lang.Runnable
            public void run() {
                b bVar;
                ArrayList<a> arrayList = new ArrayList();
                synchronized (b.this.f9233b) {
                    b.this.f9234c.removeCallbacksAndMessages(null);
                    arrayList.addAll(b.this.f9233b);
                    b.this.f9233b.clear();
                }
                try {
                    try {
                        b.this.f9237f.a(b.this.f9238g);
                        for (a aVar : arrayList) {
                            b.this.f9237f.a(aVar.f9243a, aVar.f9244b, aVar.f9245c);
                        }
                        bVar = b.this;
                    } catch (Exception unused) {
                        bVar = b.this;
                    } catch (Throwable th) {
                        try {
                            b.this.f9237f.a();
                        } catch (Exception unused2) {
                        }
                        throw th;
                    }
                    bVar.f9237f.a();
                } catch (Exception unused3) {
                }
            }
        };
        if (z2) {
            f.a().execute(runnable);
        } else {
            runnable.run();
        }
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public boolean a() {
        return this.f9240i;
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void b(String str, String str2) {
        if (this.f9240i) {
            Log.i(str, str2);
        }
        synchronized (this.f9233b) {
            b();
            a(new a("I", str, str2));
            c();
        }
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void b(boolean z2) {
        this.f9240i = z2;
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void c(String str, String str2) {
        if (this.f9240i) {
            Log.w(str, str2);
        }
        synchronized (this.f9233b) {
            b();
            a(new a("W", str, str2));
            c();
        }
    }

    @Override // com.meizu.cloud.pushsdk.base.g
    public void d(String str, String str2) {
        if (this.f9240i) {
            Log.e(str, str2);
        }
        synchronized (this.f9233b) {
            b();
            a(new a("E", str, str2));
            c();
        }
    }
}
