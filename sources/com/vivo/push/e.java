package com.vivo.push;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import com.google.android.exoplayer2.ExoPlayer;
import com.vivo.push.sdk.PushMessageCallback;
import com.vivo.push.util.ContextDelegate;
import com.vivo.push.util.VivoPushException;
import com.vivo.push.util.t;
import com.vivo.push.util.w;
import com.vivo.push.util.z;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    private static volatile e f24348a;

    /* renamed from: h, reason: collision with root package name */
    private Context f24355h;

    /* renamed from: j, reason: collision with root package name */
    private com.vivo.push.util.b f24357j;

    /* renamed from: k, reason: collision with root package name */
    private String f24358k;

    /* renamed from: l, reason: collision with root package name */
    private String f24359l;

    /* renamed from: o, reason: collision with root package name */
    private Boolean f24362o;

    /* renamed from: p, reason: collision with root package name */
    private Long f24363p;

    /* renamed from: q, reason: collision with root package name */
    private boolean f24364q;

    /* renamed from: s, reason: collision with root package name */
    private int f24366s;

    /* renamed from: b, reason: collision with root package name */
    private long f24349b = -1;

    /* renamed from: c, reason: collision with root package name */
    private long f24350c = -1;

    /* renamed from: d, reason: collision with root package name */
    private long f24351d = -1;

    /* renamed from: e, reason: collision with root package name */
    private long f24352e = -1;

    /* renamed from: f, reason: collision with root package name */
    private long f24353f = -1;

    /* renamed from: g, reason: collision with root package name */
    private long f24354g = -1;

    /* renamed from: i, reason: collision with root package name */
    private boolean f24356i = true;

    /* renamed from: m, reason: collision with root package name */
    private SparseArray<a> f24360m = new SparseArray<>();

    /* renamed from: n, reason: collision with root package name */
    private int f24361n = 0;

    /* renamed from: r, reason: collision with root package name */
    private IPushClientFactory f24365r = new d();

    private e() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void m() {
        this.f24359l = null;
        this.f24357j.b("APP_ALIAS");
    }

    private boolean n() {
        if (this.f24362o == null) {
            this.f24362o = Boolean.valueOf(l() >= 1230 && z.d(this.f24355h));
        }
        return this.f24362o.booleanValue();
    }

    public final boolean d() {
        if (this.f24355h == null) {
            com.vivo.push.util.p.d("PushClientManager", "support:context is null");
            return false;
        }
        Boolean boolValueOf = Boolean.valueOf(n());
        this.f24362o = boolValueOf;
        return boolValueOf.booleanValue();
    }

    public final boolean e() {
        return this.f24364q;
    }

    public final String f() {
        if (!TextUtils.isEmpty(this.f24358k)) {
            return this.f24358k;
        }
        com.vivo.push.util.b bVar = this.f24357j;
        String strB = bVar != null ? bVar.b("APP_TOKEN", (String) null) : "";
        c(strB);
        return strB;
    }

    public final boolean g() {
        return this.f24356i;
    }

    public final Context h() {
        return this.f24355h;
    }

    public final void i() {
        this.f24357j.a();
    }

    public final String j() {
        return this.f24359l;
    }

    public final int k() {
        return this.f24366s;
    }

    public final long l() {
        Context context = this.f24355h;
        if (context == null) {
            return -1L;
        }
        if (this.f24363p == null) {
            this.f24363p = Long.valueOf(z.a(context));
        }
        return this.f24363p.longValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(String str) {
        m.a(new k(this, str));
    }

    public static synchronized e a() {
        if (f24348a == null) {
            f24348a = new e();
        }
        return f24348a;
    }

    public final void b() throws PackageManager.NameNotFoundException, VivoPushException {
        Context context = this.f24355h;
        if (context != null) {
            z.b(context);
        }
    }

    public final List<String> c() {
        String strB = this.f24357j.b("APP_TAGS", (String) null);
        ArrayList arrayList = new ArrayList();
        try {
        } catch (JSONException unused) {
            this.f24357j.b("APP_TAGS");
            arrayList.clear();
            com.vivo.push.util.p.d("PushClientManager", "getTags error");
        }
        if (TextUtils.isEmpty(strB)) {
            return arrayList;
        }
        Iterator<String> itKeys = new JSONObject(strB).keys();
        while (itKeys.hasNext()) {
            arrayList.add(itKeys.next());
        }
        return arrayList;
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private IPushActionListener f24369a;

        /* renamed from: b, reason: collision with root package name */
        private com.vivo.push.b.c f24370b;

        /* renamed from: c, reason: collision with root package name */
        private IPushActionListener f24371c;

        /* renamed from: d, reason: collision with root package name */
        private Runnable f24372d;

        /* renamed from: e, reason: collision with root package name */
        private Object[] f24373e;

        public a(com.vivo.push.b.c cVar, IPushActionListener iPushActionListener) {
            this.f24370b = cVar;
            this.f24369a = iPushActionListener;
        }

        public final void a(int i2, Object... objArr) {
            this.f24373e = objArr;
            IPushActionListener iPushActionListener = this.f24371c;
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(i2);
            }
            IPushActionListener iPushActionListener2 = this.f24369a;
            if (iPushActionListener2 != null) {
                iPushActionListener2.onStateChanged(i2);
            }
        }

        public final Object[] b() {
            return this.f24373e;
        }

        public final void a(Runnable runnable) {
            this.f24372d = runnable;
        }

        public final void a() {
            Runnable runnable = this.f24372d;
            if (runnable == null) {
                com.vivo.push.util.p.a("PushClientManager", "task is null");
            } else {
                runnable.run();
            }
        }

        public final void a(IPushActionListener iPushActionListener) {
            this.f24371c = iPushActionListener;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized a d(String str) {
        if (str != null) {
            try {
                int i2 = Integer.parseInt(str);
                a aVar = this.f24360m.get(i2);
                this.f24360m.delete(i2);
                return aVar;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public final void b(List<String> list) {
        JSONObject jSONObject;
        try {
            if (list.size() <= 0) {
                return;
            }
            String strB = this.f24357j.b("APP_TAGS", (String) null);
            if (TextUtils.isEmpty(strB)) {
                jSONObject = new JSONObject();
            } else {
                jSONObject = new JSONObject(strB);
            }
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                jSONObject.remove(it.next());
            }
            String string = jSONObject.toString();
            if (TextUtils.isEmpty(string)) {
                this.f24357j.b("APP_TAGS");
            } else {
                this.f24357j.a("APP_TAGS", string);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            this.f24357j.b("APP_TAGS");
        }
    }

    public final synchronized void a(Context context) {
        if (this.f24355h == null) {
            this.f24355h = ContextDelegate.getContext(context);
            this.f24364q = t.c(context, context.getPackageName());
            w.b().a(this.f24355h);
            a(new com.vivo.push.b.g());
            com.vivo.push.util.b bVar = new com.vivo.push.util.b();
            this.f24357j = bVar;
            bVar.a(this.f24355h, "com.vivo.push_preferences.appconfig_v1");
            this.f24358k = f();
            this.f24359l = this.f24357j.b("APP_ALIAS", (String) null);
        }
    }

    public final void c(List<String> list) {
        if (list.contains(this.f24359l)) {
            m();
        }
    }

    private void c(String str) {
        m.c(new f(this, str));
    }

    public final void a(List<String> list) throws JSONException {
        JSONObject jSONObject;
        try {
            if (list.size() <= 0) {
                return;
            }
            String strB = this.f24357j.b("APP_TAGS", (String) null);
            if (TextUtils.isEmpty(strB)) {
                jSONObject = new JSONObject();
            } else {
                jSONObject = new JSONObject(strB);
            }
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                jSONObject.put(it.next(), System.currentTimeMillis());
            }
            String string = jSONObject.toString();
            if (TextUtils.isEmpty(string)) {
                this.f24357j.b("APP_TAGS");
            } else {
                this.f24357j.a("APP_TAGS", string);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            this.f24357j.b("APP_TAGS");
        }
    }

    public final void b(IPushActionListener iPushActionListener) {
        if (this.f24355h == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        if ("".equals(this.f24358k)) {
            iPushActionListener.onStateChanged(0);
            return;
        }
        if (!a(this.f24350c)) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(1002);
                return;
            }
            return;
        }
        this.f24350c = SystemClock.elapsedRealtime();
        String packageName = this.f24355h.getPackageName();
        a aVarA = null;
        if (this.f24355h != null) {
            com.vivo.push.b.b bVar = new com.vivo.push.b.b(false, packageName);
            bVar.d();
            bVar.e();
            bVar.g();
            bVar.a(100);
            if (this.f24364q) {
                if (n()) {
                    aVarA = new a(bVar, iPushActionListener);
                    String strA = a(aVarA);
                    bVar.b(strA);
                    aVarA.a(new j(this, bVar, strA));
                } else if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                }
            } else if (bVar.a(this.f24355h) == 2) {
                aVarA = a(bVar, iPushActionListener);
            } else {
                a(bVar);
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(0);
                }
            }
        } else if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(102);
        }
        if (aVarA == null) {
            return;
        }
        aVarA.a(new i(this));
        aVarA.a();
    }

    public final void a(String str) {
        this.f24358k = str;
        this.f24357j.a("APP_TOKEN", str);
    }

    public final void a(boolean z2) {
        this.f24356i = z2;
    }

    public final void a(IPushActionListener iPushActionListener) {
        if (this.f24355h == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        String strF = f();
        this.f24358k = strF;
        if (!TextUtils.isEmpty(strF)) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(0);
                return;
            }
            return;
        }
        if (!a(this.f24349b)) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(1002);
                return;
            }
            return;
        }
        this.f24349b = SystemClock.elapsedRealtime();
        String packageName = this.f24355h.getPackageName();
        a aVarA = null;
        if (this.f24355h != null) {
            com.vivo.push.b.b bVar = new com.vivo.push.b.b(true, packageName);
            bVar.g();
            bVar.d();
            bVar.e();
            bVar.a(100);
            if (this.f24364q) {
                if (n()) {
                    aVarA = a(bVar, iPushActionListener);
                } else if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                }
            } else if (bVar.a(this.f24355h) == 2) {
                aVarA = a(bVar, iPushActionListener);
            } else {
                a(bVar);
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(0);
                }
            }
        } else if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(102);
        }
        if (aVarA == null) {
            return;
        }
        aVarA.a(new g(this, aVarA));
        aVarA.a();
    }

    public final void b(String str, IPushActionListener iPushActionListener) {
        if (this.f24355h == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        if (TextUtils.isEmpty(this.f24359l)) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(0);
                return;
            }
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        com.vivo.push.b.a aVar = new com.vivo.push.b.a(false, this.f24355h.getPackageName(), arrayList);
        aVar.a(100);
        if (this.f24364q) {
            if (!n()) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                    return;
                }
                return;
            }
            if (!a(this.f24352e)) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(1002);
                    return;
                }
                return;
            }
            this.f24352e = SystemClock.elapsedRealtime();
            String strA = a(new a(aVar, iPushActionListener));
            aVar.b(strA);
            if (TextUtils.isEmpty(this.f24358k)) {
                a(strA, 30001);
                return;
            }
            if (TextUtils.isEmpty(str)) {
                a(strA, 30002);
                return;
            } else if (str.length() > 70) {
                a(strA, 30003);
                return;
            } else {
                a(aVar);
                e(strA);
                return;
            }
        }
        a(aVar);
        if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(0);
        }
    }

    private a a(com.vivo.push.b.b bVar, IPushActionListener iPushActionListener) {
        a aVar = new a(bVar, iPushActionListener);
        String strA = a(aVar);
        bVar.b(strA);
        aVar.a(new h(this, bVar, strA));
        return aVar;
    }

    public final void a(String str, int i2, Object... objArr) {
        a aVarD = d(str);
        if (aVarD != null) {
            aVarD.a(i2, objArr);
        } else {
            com.vivo.push.util.p.d("PushClientManager", "notifyApp token is null");
        }
    }

    public final void a(String str, IPushActionListener iPushActionListener) {
        if (this.f24355h == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        if (!TextUtils.isEmpty(this.f24359l) && this.f24359l.equals(str)) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(0);
                return;
            }
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        com.vivo.push.b.a aVar = new com.vivo.push.b.a(true, this.f24355h.getPackageName(), arrayList);
        aVar.a(100);
        if (this.f24364q) {
            if (!n()) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                    return;
                }
                return;
            }
            if (!a(this.f24351d)) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(1002);
                    return;
                }
                return;
            }
            this.f24351d = SystemClock.elapsedRealtime();
            String strA = a(new a(aVar, iPushActionListener));
            aVar.b(strA);
            if (TextUtils.isEmpty(this.f24358k)) {
                a(strA, 30001);
                return;
            }
            if (TextUtils.isEmpty(str)) {
                a(strA, 30002);
                return;
            } else if (str.length() > 70) {
                a(strA, 30003);
                return;
            } else {
                a(aVar);
                e(strA);
                return;
            }
        }
        a(aVar);
        if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(0);
        }
    }

    public final void b(String str) {
        this.f24359l = str;
        this.f24357j.a("APP_ALIAS", str);
    }

    public final void b(ArrayList<String> arrayList, IPushActionListener iPushActionListener) {
        Context context = this.f24355h;
        if (context == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        com.vivo.push.b.z zVar = new com.vivo.push.b.z(false, context.getPackageName(), arrayList);
        zVar.a(500);
        if (this.f24364q) {
            if (!n()) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                    return;
                }
                return;
            }
            if (!a(this.f24354g)) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(1002);
                    return;
                }
                return;
            }
            this.f24354g = SystemClock.elapsedRealtime();
            String strA = a(new a(zVar, iPushActionListener));
            zVar.b(strA);
            if (TextUtils.isEmpty(this.f24358k)) {
                a(strA, 20001);
                return;
            }
            if (arrayList.size() < 0) {
                a(strA, 20002);
                return;
            }
            if (arrayList.size() > 500) {
                a(strA, 20004);
                return;
            }
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                if (it.next().length() > 70) {
                    a(strA, 20003);
                    return;
                }
            }
            a(zVar);
            e(strA);
            return;
        }
        a(zVar);
        if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(0);
        }
    }

    private static boolean a(long j2) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        return j2 == -1 || jElapsedRealtime <= j2 || jElapsedRealtime >= j2 + ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS;
    }

    public final void a(String str, int i2) {
        a aVarD = d(str);
        if (aVarD != null) {
            aVarD.a(i2, new Object[0]);
        } else {
            com.vivo.push.util.p.d("PushClientManager", "notifyStatusChanged token is null");
        }
    }

    private synchronized String a(a aVar) {
        int i2;
        this.f24360m.put(this.f24361n, aVar);
        i2 = this.f24361n;
        this.f24361n = i2 + 1;
        return Integer.toString(i2);
    }

    public final void a(ArrayList<String> arrayList, IPushActionListener iPushActionListener) {
        Context context = this.f24355h;
        if (context == null) {
            if (iPushActionListener != null) {
                iPushActionListener.onStateChanged(102);
                return;
            }
            return;
        }
        com.vivo.push.b.z zVar = new com.vivo.push.b.z(true, context.getPackageName(), arrayList);
        zVar.a(500);
        if (this.f24364q) {
            if (!n()) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(101);
                    return;
                }
                return;
            }
            if (!a(this.f24353f)) {
                if (iPushActionListener != null) {
                    iPushActionListener.onStateChanged(1002);
                    return;
                }
                return;
            }
            this.f24353f = SystemClock.elapsedRealtime();
            String strA = a(new a(zVar, iPushActionListener));
            zVar.b(strA);
            if (TextUtils.isEmpty(this.f24358k)) {
                a(strA, 20001);
                return;
            }
            if (arrayList.size() < 0) {
                a(strA, 20002);
                return;
            }
            if (arrayList.size() + c().size() > 500) {
                a(strA, 20004);
                return;
            }
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                if (it.next().length() > 70) {
                    a(strA, 20003);
                    return;
                }
            }
            a(zVar);
            e(strA);
            return;
        }
        a(zVar);
        if (iPushActionListener != null) {
            iPushActionListener.onStateChanged(0);
        }
    }

    public final void a(Intent intent, PushMessageCallback pushMessageCallback) {
        o oVarCreateReceiverCommand = this.f24365r.createReceiverCommand(intent);
        Context context = a().f24355h;
        if (oVarCreateReceiverCommand == null) {
            com.vivo.push.util.p.a("PushClientManager", "sendCommand, null command!");
            if (context != null) {
                com.vivo.push.util.p.c(context, "[执行指令失败]指令空！");
                return;
            }
            return;
        }
        com.vivo.push.d.z zVarCreateReceiveTask = this.f24365r.createReceiveTask(oVarCreateReceiverCommand);
        if (zVarCreateReceiveTask == null) {
            com.vivo.push.util.p.a("PushClientManager", "sendCommand, null command task! pushCommand = ".concat(String.valueOf(oVarCreateReceiverCommand)));
            if (context != null) {
                com.vivo.push.util.p.c(context, "[执行指令失败]指令" + oVarCreateReceiverCommand + "任务空！");
                return;
            }
            return;
        }
        if (context != null && !(oVarCreateReceiverCommand instanceof com.vivo.push.b.n)) {
            com.vivo.push.util.p.a(context, "[接收指令]".concat(String.valueOf(oVarCreateReceiverCommand)));
        }
        zVarCreateReceiveTask.a(pushMessageCallback);
        m.a((l) zVarCreateReceiveTask);
    }

    public final void a(o oVar) {
        Context context = a().f24355h;
        if (oVar == null) {
            com.vivo.push.util.p.a("PushClientManager", "sendCommand, null command!");
            if (context != null) {
                com.vivo.push.util.p.c(context, "[执行指令失败]指令空！");
                return;
            }
            return;
        }
        l lVarCreateTask = this.f24365r.createTask(oVar);
        if (lVarCreateTask == null) {
            com.vivo.push.util.p.a("PushClientManager", "sendCommand, null command task! pushCommand = ".concat(String.valueOf(oVar)));
            if (context != null) {
                com.vivo.push.util.p.c(context, "[执行指令失败]指令" + oVar + "任务空！");
                return;
            }
            return;
        }
        com.vivo.push.util.p.d("PushClientManager", "client--sendCommand, command = ".concat(String.valueOf(oVar)));
        m.a(lVarCreateTask);
    }
}
