package com.beizi.ad.internal.b;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import cn.hutool.core.text.StrPool;
import com.beizi.ad.NativeAdResponse;
import com.beizi.ad.R;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.utilities.DeviceInfo;
import com.beizi.ad.internal.utilities.HTTPGet;
import com.beizi.ad.internal.utilities.HTTPResponse;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.StringUtil;
import com.google.android.exoplayer2.C;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes2.dex */
public abstract class b {

    /* renamed from: j, reason: collision with root package name */
    static HashMap<String, String> f4014j = new HashMap<>();

    /* renamed from: a, reason: collision with root package name */
    protected l f4015a;

    /* renamed from: b, reason: collision with root package name */
    protected com.beizi.ad.b.b f4016b;

    /* renamed from: c, reason: collision with root package name */
    protected com.beizi.ad.internal.b.a f4017c;

    /* renamed from: d, reason: collision with root package name */
    protected com.beizi.ad.internal.b f4018d;

    /* renamed from: f, reason: collision with root package name */
    protected ServerResponse f4020f;

    /* renamed from: l, reason: collision with root package name */
    private SoftReference<com.beizi.ad.internal.e> f4025l;

    /* renamed from: e, reason: collision with root package name */
    protected e f4019e = new e(this);

    /* renamed from: g, reason: collision with root package name */
    protected boolean f4021g = false;

    /* renamed from: h, reason: collision with root package name */
    protected boolean f4022h = false;

    /* renamed from: i, reason: collision with root package name */
    protected boolean f4023i = false;

    /* renamed from: m, reason: collision with root package name */
    private final Handler f4026m = new HandlerC0050b(this);

    /* renamed from: n, reason: collision with root package name */
    private long f4027n = -1;

    /* renamed from: o, reason: collision with root package name */
    private long f4028o = -1;

    /* renamed from: k, reason: collision with root package name */
    protected boolean f4024k = false;

    /* renamed from: com.beizi.ad.internal.b.b$1, reason: invalid class name */
    public class AnonymousClass1 implements com.beizi.ad.internal.network.b {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ b f4029a;

        @Override // com.beizi.ad.internal.network.b
        public l a() {
            return this.f4029a.f4015a;
        }

        @Override // com.beizi.ad.internal.network.b
        public boolean b() {
            return true;
        }

        @Override // com.beizi.ad.internal.network.b
        public com.beizi.ad.internal.view.c c() {
            return this.f4029a.f4019e;
        }

        @Override // com.beizi.ad.internal.network.b
        public NativeAdResponse d() {
            return null;
        }

        @Override // com.beizi.ad.internal.network.b
        public String e() {
            return "";
        }

        @Override // com.beizi.ad.internal.network.b
        public String f() {
            return null;
        }

        @Override // com.beizi.ad.internal.network.b
        public String g() {
            return null;
        }

        @Override // com.beizi.ad.internal.network.b
        public void h() {
            this.f4029a.f4019e.destroy();
        }
    }

    public class a extends HTTPGet {

        /* renamed from: a, reason: collision with root package name */
        WeakReference<com.beizi.ad.internal.e> f4030a;

        /* renamed from: b, reason: collision with root package name */
        final int f4031b;

        /* renamed from: d, reason: collision with root package name */
        private final String f4033d;

        /* renamed from: e, reason: collision with root package name */
        private final HashMap<String, Object> f4034e;

        /* renamed from: f, reason: collision with root package name */
        private final boolean f4035f;

        /* renamed from: g, reason: collision with root package name */
        private final long f4036g;

        /* renamed from: h, reason: collision with root package name */
        private final long f4037h;

        public /* synthetic */ a(b bVar, com.beizi.ad.internal.e eVar, String str, int i2, HashMap map, boolean z2, long j2, long j3, AnonymousClass1 anonymousClass1) {
            this(eVar, str, i2, map, z2, j2, j3);
        }

        @Override // com.beizi.ad.internal.utilities.HTTPGet
        public String getUrl() {
            StringBuilder sb = new StringBuilder(this.f4033d);
            sb.append("&errorCode=");
            sb.append(this.f4031b);
            DeviceInfo deviceInfo = DeviceInfo.getInstance();
            if (!StringUtil.isEmpty(deviceInfo.sdkUID)) {
                sb.append("&sdkuid=");
                sb.append(Uri.encode(deviceInfo.sdkUID));
            }
            if (this.f4036g > 0) {
                sb.append("&latency=");
                sb.append(Uri.encode(String.valueOf(this.f4036g)));
            }
            if (this.f4037h > 0) {
                sb.append("&total_latency=");
                sb.append(Uri.encode(String.valueOf(this.f4037h)));
            }
            return sb.toString();
        }

        private a(com.beizi.ad.internal.e eVar, String str, int i2, HashMap<String, Object> map, boolean z2, long j2, long j3) {
            super(true);
            this.f4030a = new WeakReference<>(eVar);
            this.f4033d = str;
            this.f4031b = i2;
            this.f4034e = map;
            this.f4035f = z2;
            this.f4036g = j2;
            this.f4037h = j3;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.beizi.ad.internal.utilities.HTTPGet, android.os.AsyncTask
        public void onPostExecute(HTTPResponse hTTPResponse) {
            ServerResponse serverResponse;
            if (this.f4035f) {
                HaoboLog.i(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.result_cb_ignored));
                return;
            }
            com.beizi.ad.internal.e eVar = this.f4030a.get();
            if (eVar == null) {
                HaoboLog.w(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.fire_cb_requester_null));
                return;
            }
            if (hTTPResponse == null || !hTTPResponse.getSucceeded()) {
                HaoboLog.w(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.result_cb_bad_response));
                serverResponse = null;
            } else {
                serverResponse = new ServerResponse(hTTPResponse, b.this.f4015a);
                if (this.f4034e.containsKey(ServerResponse.EXTRAS_KEY_ORIENTATION)) {
                    serverResponse.setAdOrientation(this.f4034e.get(ServerResponse.EXTRAS_KEY_ORIENTATION).equals("h") ? 1 : 2);
                }
            }
            eVar.a(serverResponse);
        }
    }

    /* renamed from: com.beizi.ad.internal.b.b$b, reason: collision with other inner class name */
    public static class HandlerC0050b extends Handler {

        /* renamed from: a, reason: collision with root package name */
        WeakReference<b> f4038a;

        public HandlerC0050b(b bVar) {
            this.f4038a = new WeakReference<>(bVar);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            b bVar = this.f4038a.get();
            if (bVar == null || bVar.f4021g) {
                return;
            }
            HaoboLog.w(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediation_timeout));
            try {
                bVar.a(0);
            } catch (IllegalArgumentException unused) {
            } catch (Throwable th) {
                bVar.f4018d = null;
                bVar.f4016b = null;
                bVar.f4017c = null;
                throw th;
            }
            bVar.f4018d = null;
            bVar.f4016b = null;
            bVar.f4017c = null;
        }
    }

    public b(com.beizi.ad.internal.e eVar, com.beizi.ad.internal.b.a aVar, com.beizi.ad.internal.b bVar, l lVar, ServerResponse serverResponse) {
        int i2;
        if (f4014j.isEmpty()) {
            a("1", "Baidu");
            a("4", "GDT");
            a("43", "AFP");
        }
        this.f4020f = serverResponse;
        this.f4025l = new SoftReference<>(eVar);
        this.f4017c = aVar;
        this.f4018d = bVar;
        this.f4015a = lVar;
        if (aVar == null) {
            HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_no_ads));
            i2 = 3;
        } else {
            i2 = !m() ? 2 : -1;
        }
        if (i2 != -1) {
            a(i2);
        }
    }

    private boolean m() {
        String str;
        HaoboLog.d(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.instantiating_class, this.f4017c.a()));
        try {
            String str2 = this.f4017c.a() + StrPool.DOT + this.f4015a.toString();
            String str3 = f4014j.get(str2);
            String str4 = com.beizi.ad.internal.g.a().f4183e.get(str2);
            if (StringUtil.isEmpty(str4)) {
                if (StringUtil.isEmpty(str3)) {
                    str = getClass().getPackage().getName() + StrPool.DOT + str2;
                } else {
                    str = getClass().getPackage().getName() + StrPool.DOT + str3;
                }
                this.f4016b = (com.beizi.ad.b.b) Class.forName(str).newInstance();
            } else {
                this.f4016b = (com.beizi.ad.b.b) Class.forName(getClass().getPackage().getName() + StrPool.DOT + str4).getConstructor(String.class).newInstance(str2);
            }
            return true;
        } catch (ClassCastException e2) {
            a(e2, this.f4017c.a());
            return false;
        } catch (ClassNotFoundException e3) {
            a(e3, this.f4017c.a());
            return false;
        } catch (IllegalAccessException e4) {
            a(e4, this.f4017c.a());
            return false;
        } catch (InstantiationException e5) {
            a(e5, this.f4017c.a());
            return false;
        } catch (LinkageError e6) {
            a(e6, this.f4017c.a());
            return false;
        } catch (NoSuchMethodException e7) {
            a(e7, this.f4017c.a());
            return false;
        } catch (InvocationTargetException e8) {
            a(e8, this.f4017c.a());
            return false;
        }
    }

    private long n() {
        long j2 = this.f4027n;
        if (j2 <= 0) {
            return -1L;
        }
        long j3 = this.f4028o;
        if (j3 > 0) {
            return j3 - j2;
        }
        return -1L;
    }

    public void a(String str, String str2) {
        f4014j.put(str + ".SPLASH", str2 + "Splash");
        f4014j.put(str + ".BANNER", str2 + "Banner");
        f4014j.put(str + ".INTERSTITIAL", str2 + "Interstitial");
        f4014j.put(str + ".NATIVE", str2 + "Native");
        f4014j.put(str + ".REWARDEDVIDEO", str2 + "Interstitial");
    }

    public void b() {
        com.beizi.ad.b.b bVar = this.f4016b;
        if (bVar != null) {
            bVar.c();
        }
        this.f4023i = true;
        this.f4016b = null;
        this.f4017c = null;
        HaoboLog.d(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediation_finish));
    }

    public abstract boolean c();

    public abstract void d();

    public boolean e() {
        return this.f4023i;
    }

    public void f() {
        if (this.f4022h || this.f4021g) {
            return;
        }
        this.f4026m.sendEmptyMessageDelayed(0, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
    }

    public void g() {
        this.f4026m.removeMessages(0);
    }

    public void h() {
        this.f4027n = System.currentTimeMillis();
    }

    public void i() {
        this.f4028o = System.currentTimeMillis();
    }

    public abstract void j();

    public abstract void k();

    public abstract void l();

    public boolean a(Class cls) {
        if (this.f4021g) {
            return false;
        }
        com.beizi.ad.b.b bVar = this.f4016b;
        if (bVar != null && cls != null && cls.isInstance(bVar)) {
            return true;
        }
        HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.instance_exception, cls != null ? cls.getCanonicalName() : "null"));
        a(3);
        return false;
    }

    @SuppressLint({"InlinedApi", "NewApi"})
    private void b(int i2) {
        if (this.f4021g) {
            return;
        }
        com.beizi.ad.internal.e eVar = this.f4025l.get();
        com.beizi.ad.internal.b.a aVar = this.f4017c;
        if (aVar == null || StringUtil.isEmpty(aVar.f())) {
            if (i2 == -1) {
                return;
            }
            HaoboLog.w(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.fire_cb_result_null));
            if (eVar == null) {
                HaoboLog.e(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.fire_cb_requester_null));
                return;
            } else {
                eVar.a((ServerResponse) null);
                return;
            }
        }
        boolean z2 = i2 == -1 ? true : (eVar == null || eVar.b() == null || eVar.b().size() <= 0) ? false : true;
        try {
            new a(this, eVar, this.f4017c.f(), i2, this.f4017c.g(), z2, n(), a(eVar), null).execute(new Void[0]);
        } catch (RejectedExecutionException e2) {
            HaoboLog.e(HaoboLog.baseLogTag, "Concurrent Thread Exception while firing ResultCB: " + e2.getMessage());
        } catch (Exception e3) {
            HaoboLog.e(HaoboLog.baseLogTag, "Exception while firing ResultCB: " + e3.getMessage());
        }
        if (!z2 || i2 == -1 || eVar == null) {
            return;
        }
        eVar.a((ServerResponse) null);
    }

    public com.beizi.ad.b.a a() {
        com.beizi.ad.internal.e eVar = this.f4025l.get();
        if (eVar != null) {
            return eVar.d();
        }
        return null;
    }

    private void a(Throwable th, String str) {
        HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediation_instantiation_failure, th.getClass().getSimpleName()));
        if (StringUtil.isEmpty(str)) {
            return;
        }
        HaoboLog.w(HaoboLog.mediationLogTag, String.format("Adding %s to invalid networks list", str));
        com.beizi.ad.internal.g.a().a(this.f4015a, str);
    }

    public void a(int i2) {
        if (this.f4022h || this.f4021g || this.f4023i) {
            return;
        }
        i();
        g();
        b(i2);
        this.f4021g = true;
        b();
    }

    private long a(com.beizi.ad.internal.e eVar) {
        if (eVar == null) {
            return -1L;
        }
        long j2 = this.f4028o;
        if (j2 > 0) {
            return eVar.a(j2);
        }
        return -1L;
    }

    public void a(boolean z2) {
        this.f4024k = z2;
        if (z2) {
            b();
        }
    }
}
