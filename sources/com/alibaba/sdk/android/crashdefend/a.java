package com.alibaba.sdk.android.crashdefend;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.crashdefend.a.b;
import com.tencent.tbs.one.BuildConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static volatile a f2653a;

    /* renamed from: b, reason: collision with root package name */
    private final Context f2654b;

    /* renamed from: d, reason: collision with root package name */
    private b f2656d;

    /* renamed from: c, reason: collision with root package name */
    private final com.alibaba.sdk.android.crashdefend.a.a f2655c = new com.alibaba.sdk.android.crashdefend.a.a();

    /* renamed from: f, reason: collision with root package name */
    private final Map<String, String> f2658f = new HashMap();

    /* renamed from: g, reason: collision with root package name */
    private final int[] f2659g = new int[5];

    /* renamed from: h, reason: collision with root package name */
    private final List<b> f2660h = new ArrayList();

    /* renamed from: e, reason: collision with root package name */
    private final ExecutorService f2657e = new com.alibaba.sdk.android.crashdefend.b.a().a();

    /* renamed from: com.alibaba.sdk.android.crashdefend.a$a, reason: collision with other inner class name */
    public class RunnableC0017a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private b f2663b;

        /* renamed from: c, reason: collision with root package name */
        private int f2664c;

        public RunnableC0017a(b bVar, int i2) {
            this.f2663b = bVar;
            this.f2664c = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i2;
            do {
                try {
                    Thread.sleep(1000L);
                    i2 = this.f2664c - 1;
                    this.f2664c = i2;
                } catch (InterruptedException unused) {
                    return;
                } catch (Exception e2) {
                    Log.d("CrashDefend", e2.getMessage(), e2);
                }
            } while (i2 > 0);
            if (i2 <= 0) {
                a.this.c(this.f2663b);
                com.alibaba.sdk.android.crashdefend.c.a.a(a.this.f2654b, a.this.f2655c, a.this.f2660h);
                return;
            }
            return;
        }
    }

    private a(Context context) {
        this.f2654b = context.getApplicationContext();
        for (int i2 = 0; i2 < 5; i2++) {
            this.f2659g[i2] = (i2 * 5) + 5;
        }
        this.f2658f.put("sdkId", "crashdefend");
        this.f2658f.put(com.heytap.mcssdk.constant.b.C, BuildConfig.VERSION_NAME);
        try {
            a();
            b();
        } catch (Exception e2) {
            Log.d("CrashDefend", e2.getMessage(), e2);
        }
    }

    public static a a(Context context) {
        if (f2653a == null) {
            synchronized (a.class) {
                if (f2653a == null) {
                    f2653a = new a(context);
                }
            }
        }
        return f2653a;
    }

    private void a() {
        if (!com.alibaba.sdk.android.crashdefend.c.a.b(this.f2654b, this.f2655c, this.f2660h)) {
            this.f2655c.f2661a = 1L;
        } else {
            this.f2655c.f2661a++;
        }
    }

    private boolean a(b bVar) {
        if (bVar.f2668d >= bVar.f2667c) {
            b bVar2 = this.f2656d;
            if (bVar2 == null || !bVar2.f2665a.equals(bVar.f2665a)) {
                return false;
            }
            bVar.f2668d = bVar.f2667c - 1;
        }
        bVar.f2671g = bVar.f2670f;
        return true;
    }

    private boolean a(b bVar, CrashDefendCallback crashDefendCallback) {
        b bVarB;
        String str;
        if (bVar != null && crashDefendCallback != null) {
            try {
                if (TextUtils.isEmpty(bVar.f2666b) || TextUtils.isEmpty(bVar.f2665a) || (bVarB = b(bVar, crashDefendCallback)) == null) {
                    return false;
                }
                boolean zA = a(bVarB);
                bVarB.f2668d++;
                com.alibaba.sdk.android.crashdefend.c.a.a(this.f2654b, this.f2655c, this.f2660h);
                if (zA) {
                    b(bVarB);
                    str = "START:" + bVarB.f2665a + " --- limit:" + bVarB.f2667c + "  count:" + (bVarB.f2668d - 1) + "  restore:" + bVarB.f2672h + "  startSerialNumber:" + bVarB.f2671g + "  registerSerialNumber:" + bVarB.f2670f;
                } else {
                    int i2 = bVarB.f2672h;
                    if (i2 >= 5) {
                        crashDefendCallback.onSdkClosed(i2);
                        str = "CLOSED: " + bVarB.f2665a + " --- restored " + bVarB.f2672h + ", has more than retry limit, so closed it";
                    } else {
                        crashDefendCallback.onSdkStop(bVarB.f2667c, bVarB.f2668d - 1, i2, bVarB.f2673i);
                        str = "STOP:" + bVarB.f2665a + " --- limit:" + bVarB.f2667c + "  count:" + (bVarB.f2668d - 1) + "  restore:" + bVarB.f2672h + "  startSerialNumber:" + bVarB.f2671g + "  registerSerialNumber:" + bVarB.f2670f;
                    }
                }
                com.alibaba.sdk.android.crashdefend.c.b.b("CrashDefend", str);
                return true;
            } catch (Exception e2) {
                Log.d("CrashDefend", e2.getMessage(), e2);
            }
        }
        return false;
    }

    private synchronized b b(b bVar, CrashDefendCallback crashDefendCallback) {
        b bVar2 = null;
        if (this.f2660h.size() > 0) {
            Iterator<b> it = this.f2660h.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                b next = it.next();
                if (next != null && next.f2665a.equals(bVar.f2665a)) {
                    if (!next.f2666b.equals(bVar.f2666b)) {
                        next.f2666b = bVar.f2666b;
                        next.f2667c = bVar.f2667c;
                        next.f2669e = bVar.f2669e;
                        next.f2668d = 0;
                        next.f2672h = 0;
                        next.f2673i = 0L;
                    }
                    if (next.f2674j) {
                        com.alibaba.sdk.android.crashdefend.c.b.b("CrashDefend", "SDK " + bVar.f2665a + " has been registered");
                        return null;
                    }
                    next.f2674j = true;
                    next.f2675k = crashDefendCallback;
                    next.f2670f = this.f2655c.f2661a;
                    bVar2 = next;
                }
            }
        }
        if (bVar2 == null) {
            bVar2 = (b) bVar.clone();
            bVar2.f2674j = true;
            bVar2.f2675k = crashDefendCallback;
            bVar2.f2668d = 0;
            bVar2.f2670f = this.f2655c.f2661a;
            this.f2660h.add(bVar2);
        }
        return bVar2;
    }

    private void b() {
        String str;
        String str2;
        this.f2656d = null;
        ArrayList arrayList = new ArrayList();
        synchronized (this.f2660h) {
            for (b bVar : this.f2660h) {
                if (bVar.f2668d >= bVar.f2667c) {
                    arrayList.add(bVar);
                }
            }
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                b bVar2 = (b) it.next();
                if (bVar2.f2672h < 5) {
                    long j2 = this.f2655c.f2661a - this.f2659g[r3];
                    long j3 = (bVar2.f2671g - j2) + 1;
                    com.alibaba.sdk.android.crashdefend.c.b.a("CrashDefend", "after restart " + j3 + " times, sdk will be restore");
                    bVar2.f2673i = j3;
                    if (bVar2.f2671g < j2) {
                        this.f2656d = bVar2;
                        break;
                    }
                } else {
                    com.alibaba.sdk.android.crashdefend.c.b.b("CrashDefend", "SDK " + bVar2.f2665a + " has been closed");
                }
            }
            b bVar3 = this.f2656d;
            if (bVar3 == null) {
                str = "CrashDefend";
                str2 = "NO SDK restore";
            } else {
                bVar3.f2672h++;
                str = "CrashDefend";
                str2 = this.f2656d.f2665a + " will restore --- startSerialNumber:" + this.f2656d.f2671g + "   crashCount:" + this.f2656d.f2668d;
            }
            com.alibaba.sdk.android.crashdefend.c.b.b(str, str2);
        }
    }

    private void b(b bVar) {
        if (bVar == null) {
            return;
        }
        d(bVar);
        CrashDefendCallback crashDefendCallback = bVar.f2675k;
        if (crashDefendCallback != null) {
            crashDefendCallback.onSdkStart(bVar.f2667c, bVar.f2668d - 1, bVar.f2672h);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(b bVar) {
        if (bVar == null) {
            return;
        }
        bVar.f2668d = 0;
        bVar.f2672h = 0;
    }

    private void d(b bVar) {
        if (bVar == null) {
            return;
        }
        this.f2657e.execute(new RunnableC0017a(bVar, bVar.f2669e));
    }

    public boolean a(String str, String str2, int i2, int i3, CrashDefendCallback crashDefendCallback) {
        b bVar = new b();
        bVar.f2665a = str;
        bVar.f2666b = str2;
        bVar.f2667c = i2;
        bVar.f2669e = i3;
        return a(bVar, crashDefendCallback);
    }
}
