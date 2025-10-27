package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.ay;
import com.umeng.analytics.pro.be;
import com.umeng.commonsdk.config.FieldManager;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes6.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    public static final long f23363a = 86400000;

    /* renamed from: b, reason: collision with root package name */
    public static e f23364b = null;

    /* renamed from: c, reason: collision with root package name */
    private static final String f23365c = "umeng_it.cache";

    /* renamed from: j, reason: collision with root package name */
    private static Object f23366j = new Object();

    /* renamed from: d, reason: collision with root package name */
    private File f23367d;

    /* renamed from: f, reason: collision with root package name */
    private long f23369f;

    /* renamed from: i, reason: collision with root package name */
    private a f23372i;

    /* renamed from: e, reason: collision with root package name */
    private com.umeng.commonsdk.statistics.proto.c f23368e = null;

    /* renamed from: h, reason: collision with root package name */
    private Set<com.umeng.commonsdk.statistics.idtracking.a> f23371h = new HashSet();

    /* renamed from: g, reason: collision with root package name */
    private long f23370g = 86400000;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private Context f23373a;

        /* renamed from: b, reason: collision with root package name */
        private Set<String> f23374b = new HashSet();

        public a(Context context) {
            this.f23373a = context;
        }

        public synchronized boolean a(String str) {
            return !this.f23374b.contains(str);
        }

        public synchronized void b(String str) {
            this.f23374b.add(str);
        }

        public void c(String str) {
            this.f23374b.remove(str);
        }

        public synchronized void a() {
            if (!this.f23374b.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                Iterator<String> it = this.f23374b.iterator();
                while (it.hasNext()) {
                    sb.append(it.next());
                    sb.append(',');
                }
                sb.deleteCharAt(sb.length() - 1);
                PreferenceWrapper.getDefault(this.f23373a).edit().putString("invld_id", sb.toString()).commit();
            }
        }

        public synchronized void b() {
            String[] strArrSplit;
            String string = PreferenceWrapper.getDefault(this.f23373a).getString("invld_id", null);
            if (!TextUtils.isEmpty(string) && (strArrSplit = string.split(",")) != null) {
                for (String str : strArrSplit) {
                    if (!TextUtils.isEmpty(str)) {
                        this.f23374b.add(str);
                    }
                }
            }
        }
    }

    public e(Context context) {
        this.f23372i = null;
        this.f23367d = new File(context.getFilesDir(), f23365c);
        a aVar = new a(context);
        this.f23372i = aVar;
        aVar.b();
    }

    public static synchronized void a() {
        e eVar = f23364b;
        if (eVar != null) {
            eVar.e();
            f23364b = null;
        }
    }

    private synchronized void h() {
        com.umeng.commonsdk.statistics.proto.c cVar = new com.umeng.commonsdk.statistics.proto.c();
        HashMap map = new HashMap();
        ArrayList arrayList = new ArrayList();
        for (com.umeng.commonsdk.statistics.idtracking.a aVar : this.f23371h) {
            if (aVar.c()) {
                if (aVar.d() != null) {
                    map.put(aVar.b(), aVar.d());
                }
                if (aVar.e() != null && !aVar.e().isEmpty()) {
                    arrayList.addAll(aVar.e());
                }
            }
        }
        cVar.a(arrayList);
        cVar.a(map);
        synchronized (this) {
            this.f23368e = cVar;
        }
    }

    private com.umeng.commonsdk.statistics.proto.c i() {
        Throwable th;
        FileInputStream fileInputStream;
        synchronized (f23366j) {
            if (!this.f23367d.exists()) {
                return null;
            }
            try {
                fileInputStream = new FileInputStream(this.f23367d);
            } catch (Exception e2) {
                e = e2;
                fileInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = null;
                HelperUtils.safeClose(fileInputStream);
                throw th;
            }
            try {
                try {
                    byte[] streamToByteArray = HelperUtils.readStreamToByteArray(fileInputStream);
                    com.umeng.commonsdk.statistics.proto.c cVar = new com.umeng.commonsdk.statistics.proto.c();
                    new ay().a(cVar, streamToByteArray);
                    HelperUtils.safeClose(fileInputStream);
                    return cVar;
                } catch (Throwable th3) {
                    th = th3;
                    HelperUtils.safeClose(fileInputStream);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                HelperUtils.safeClose(fileInputStream);
                return null;
            }
        }
    }

    public synchronized void b() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.f23369f >= this.f23370g) {
            boolean z2 = false;
            for (com.umeng.commonsdk.statistics.idtracking.a aVar : this.f23371h) {
                if (aVar.c() && aVar.a()) {
                    if (!aVar.c()) {
                        this.f23372i.b(aVar.b());
                    }
                    z2 = true;
                }
            }
            if (z2) {
                h();
                this.f23372i.a();
                g();
            }
            this.f23369f = jCurrentTimeMillis;
        }
    }

    public synchronized com.umeng.commonsdk.statistics.proto.c c() {
        return this.f23368e;
    }

    public String d() {
        return null;
    }

    public synchronized void e() {
        if (f23364b == null) {
            return;
        }
        boolean z2 = false;
        for (com.umeng.commonsdk.statistics.idtracking.a aVar : this.f23371h) {
            if (aVar.c() && aVar.e() != null && !aVar.e().isEmpty()) {
                aVar.a((List<com.umeng.commonsdk.statistics.proto.a>) null);
                z2 = true;
            }
        }
        if (z2) {
            this.f23368e.b(false);
            g();
        }
    }

    public synchronized void f() {
        com.umeng.commonsdk.statistics.proto.c cVarI = i();
        if (cVarI == null) {
            return;
        }
        a(cVarI);
        ArrayList arrayList = new ArrayList(this.f23371h.size());
        synchronized (this) {
            this.f23368e = cVarI;
            for (com.umeng.commonsdk.statistics.idtracking.a aVar : this.f23371h) {
                aVar.a(this.f23368e);
                if (!aVar.c()) {
                    arrayList.add(aVar);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.f23371h.remove((com.umeng.commonsdk.statistics.idtracking.a) it.next());
            }
            h();
        }
    }

    public synchronized void g() {
        com.umeng.commonsdk.statistics.proto.c cVar = this.f23368e;
        if (cVar != null) {
            b(cVar);
        }
    }

    public static synchronized e a(Context context) {
        if (f23364b == null) {
            e eVar = new e(context);
            f23364b = eVar;
            eVar.a(new f(context));
            f23364b.a(new b(context));
            f23364b.a(new j(context));
            f23364b.a(new d(context));
            f23364b.a(new c(context));
            f23364b.a(new g(context));
            f23364b.a(new i());
            if (FieldManager.allow(com.umeng.commonsdk.utils.b.G)) {
                f23364b.a(new h(context));
            }
            f23364b.f();
        }
        return f23364b;
    }

    private void b(com.umeng.commonsdk.statistics.proto.c cVar) {
        byte[] bArrA;
        synchronized (f23366j) {
            if (cVar != null) {
                try {
                    synchronized (this) {
                        a(cVar);
                        bArrA = new be().a(cVar);
                    }
                    if (bArrA != null) {
                        HelperUtils.writeFile(this.f23367d, bArrA);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private boolean a(com.umeng.commonsdk.statistics.idtracking.a aVar) {
        if (this.f23372i.a(aVar.b())) {
            return this.f23371h.add(aVar);
        }
        if (!AnalyticsConstants.UM_DEBUG) {
            return false;
        }
        MLog.w("invalid domain: " + aVar.b());
        return false;
    }

    public void a(long j2) {
        this.f23370g = j2;
    }

    private void a(com.umeng.commonsdk.statistics.proto.c cVar) {
        Map<String, com.umeng.commonsdk.statistics.proto.b> map;
        if (cVar == null || (map = cVar.f23459a) == null) {
            return;
        }
        if (map.containsKey(SocializeProtocolConstants.PROTOCOL_KEY_MAC) && !FieldManager.allow(com.umeng.commonsdk.utils.b.f23525h)) {
            cVar.f23459a.remove(SocializeProtocolConstants.PROTOCOL_KEY_MAC);
        }
        if (cVar.f23459a.containsKey("imei") && !FieldManager.allow(com.umeng.commonsdk.utils.b.f23524g)) {
            cVar.f23459a.remove("imei");
        }
        if (cVar.f23459a.containsKey(SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID) && !FieldManager.allow(com.umeng.commonsdk.utils.b.f23526i)) {
            cVar.f23459a.remove(SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        }
        if (cVar.f23459a.containsKey("serial") && !FieldManager.allow(com.umeng.commonsdk.utils.b.f23527j)) {
            cVar.f23459a.remove("serial");
        }
        if (cVar.f23459a.containsKey("idfa") && !FieldManager.allow(com.umeng.commonsdk.utils.b.f23540w)) {
            cVar.f23459a.remove("idfa");
        }
        if (!cVar.f23459a.containsKey("oaid") || FieldManager.allow(com.umeng.commonsdk.utils.b.G)) {
            return;
        }
        cVar.f23459a.remove("oaid");
    }
}
