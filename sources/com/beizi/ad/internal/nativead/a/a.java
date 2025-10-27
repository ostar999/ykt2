package com.beizi.ad.internal.nativead.a;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import com.beizi.ad.NativeAdResponse;
import com.beizi.ad.R;
import com.beizi.ad.internal.e;
import com.beizi.ad.internal.g;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.utilities.DeviceInfo;
import com.beizi.ad.internal.utilities.HTTPGet;
import com.beizi.ad.internal.utilities.HTTPResponse;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.view.c;
import com.google.android.exoplayer2.C;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public ServerResponse f4294a;

    /* renamed from: b, reason: collision with root package name */
    e f4295b;

    /* renamed from: c, reason: collision with root package name */
    com.beizi.ad.internal.b.a f4296c;

    /* renamed from: f, reason: collision with root package name */
    int f4299f;

    /* renamed from: d, reason: collision with root package name */
    boolean f4297d = false;

    /* renamed from: e, reason: collision with root package name */
    boolean f4298e = false;

    /* renamed from: g, reason: collision with root package name */
    private boolean f4300g = false;

    /* renamed from: h, reason: collision with root package name */
    private final Handler f4301h = new b(this);

    /* renamed from: i, reason: collision with root package name */
    private long f4302i = -1;

    /* renamed from: j, reason: collision with root package name */
    private long f4303j = -1;

    /* renamed from: com.beizi.ad.internal.nativead.a.a$1, reason: invalid class name */
    public class AnonymousClass1 implements com.beizi.ad.internal.network.b {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ NativeAdResponse f4304a;

        @Override // com.beizi.ad.internal.network.b
        public l a() {
            return l.NATIVE;
        }

        @Override // com.beizi.ad.internal.network.b
        public boolean b() {
            return true;
        }

        @Override // com.beizi.ad.internal.network.b
        public c c() {
            return null;
        }

        @Override // com.beizi.ad.internal.network.b
        public NativeAdResponse d() {
            return this.f4304a;
        }

        @Override // com.beizi.ad.internal.network.b
        public String e() {
            return "";
        }

        @Override // com.beizi.ad.internal.network.b
        public String f() {
            return this.f4304a.getPrice();
        }

        @Override // com.beizi.ad.internal.network.b
        public String g() {
            return null;
        }

        @Override // com.beizi.ad.internal.network.b
        public void h() {
            this.f4304a.destroy();
        }
    }

    /* renamed from: com.beizi.ad.internal.nativead.a.a$a, reason: collision with other inner class name */
    public class AsyncTaskC0054a extends HTTPGet {

        /* renamed from: a, reason: collision with root package name */
        e f4305a;

        /* renamed from: b, reason: collision with root package name */
        final int f4306b;

        /* renamed from: d, reason: collision with root package name */
        private final String f4308d;

        /* renamed from: e, reason: collision with root package name */
        private final HashMap<String, Object> f4309e;

        /* renamed from: f, reason: collision with root package name */
        private final boolean f4310f;

        /* renamed from: g, reason: collision with root package name */
        private final long f4311g;

        /* renamed from: h, reason: collision with root package name */
        private final long f4312h;

        public /* synthetic */ AsyncTaskC0054a(a aVar, e eVar, String str, int i2, HashMap map, boolean z2, long j2, long j3, AnonymousClass1 anonymousClass1) {
            this(eVar, str, i2, map, z2, j2, j3);
        }

        @Override // com.beizi.ad.internal.utilities.HTTPGet
        public String getUrl() {
            StringBuilder sb = new StringBuilder(this.f4308d);
            sb.append("&reason=");
            sb.append(this.f4306b);
            sb.append("&uid=");
            sb.append(Uri.encode(DeviceInfo.getInstance().sdkUID));
            if (this.f4311g > 0) {
                sb.append("&latency=");
                sb.append(Uri.encode(String.valueOf(this.f4311g)));
            }
            if (this.f4312h > 0) {
                sb.append("&total_latency=");
                sb.append(Uri.encode(String.valueOf(this.f4312h)));
            }
            return sb.toString();
        }

        private AsyncTaskC0054a(e eVar, String str, int i2, HashMap<String, Object> map, boolean z2, long j2, long j3) {
            super(true);
            this.f4305a = eVar;
            this.f4308d = str;
            this.f4306b = i2;
            this.f4309e = map;
            this.f4310f = z2;
            this.f4311g = j2;
            this.f4312h = j3;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.beizi.ad.internal.utilities.HTTPGet, android.os.AsyncTask
        public void onPostExecute(HTTPResponse hTTPResponse) {
            ServerResponse serverResponse;
            if (this.f4310f) {
                HaoboLog.i(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.result_cb_ignored));
                return;
            }
            e eVar = this.f4305a;
            if (eVar == null) {
                HaoboLog.w(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.fire_cb_requester_null));
                return;
            }
            if (hTTPResponse == null || !hTTPResponse.getSucceeded()) {
                HaoboLog.w(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.result_cb_bad_response));
                serverResponse = null;
            } else {
                serverResponse = new ServerResponse(hTTPResponse, l.NATIVE);
                if (this.f4309e.containsKey(ServerResponse.EXTRAS_KEY_ORIENTATION)) {
                    serverResponse.addToExtras(ServerResponse.EXTRAS_KEY_ORIENTATION, this.f4309e.get(ServerResponse.EXTRAS_KEY_ORIENTATION));
                }
            }
            eVar.a(serverResponse);
        }
    }

    public static class b extends Handler {

        /* renamed from: a, reason: collision with root package name */
        WeakReference<a> f4313a;

        public b(a aVar) {
            this.f4313a = new WeakReference<>(aVar);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            a aVar = this.f4313a.get();
            if (aVar == null || aVar.f4298e) {
                return;
            }
            HaoboLog.w(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediation_timeout));
            try {
                aVar.a(0);
            } catch (IllegalArgumentException unused) {
            } catch (Throwable th) {
                aVar.f4296c = null;
                throw th;
            }
            aVar.f4296c = null;
        }
    }

    private a(com.beizi.ad.internal.b.a aVar, e eVar, ServerResponse serverResponse) {
        if (aVar == null) {
            HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediated_no_ads));
            this.f4299f = 3;
        } else {
            HaoboLog.d(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.instantiating_class, aVar.a()));
            this.f4295b = eVar;
            this.f4296c = aVar;
            this.f4294a = serverResponse;
            b();
            d();
            try {
                com.beizi.ad.internal.nativead.a.b bVar = (com.beizi.ad.internal.nativead.a.b) Class.forName(aVar.a()).newInstance();
                if (eVar.c() != null) {
                    bVar.a(eVar.c().b(), aVar.e(), aVar.b(), this, eVar.d());
                } else {
                    this.f4299f = 1;
                }
            } catch (ClassCastException e2) {
                a(e2, aVar.a());
            } catch (ClassNotFoundException e3) {
                a(e3, aVar.a());
            } catch (IllegalAccessException e4) {
                a(e4, aVar.a());
            } catch (InstantiationException e5) {
                a(e5, aVar.a());
            } catch (LinkageError e6) {
                a(e6, aVar.a());
            }
        }
        int i2 = this.f4299f;
        if (i2 != -1) {
            a(i2);
        }
    }

    public static a a(com.beizi.ad.internal.b.a aVar, e eVar, ServerResponse serverResponse) {
        return new a(aVar, eVar, serverResponse);
    }

    @SuppressLint({"InlinedApi", "NewApi"})
    private void b(int i2) {
        if (this.f4298e) {
            return;
        }
        e eVar = this.f4295b;
        com.beizi.ad.internal.b.a aVar = this.f4296c;
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
            new AsyncTaskC0054a(this, eVar, this.f4296c.f(), i2, this.f4296c.g(), z2, f(), a(eVar), null).executeOnExecutor(com.beizi.ad.a.a.c.b().d(), new Void[0]);
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

    private long f() {
        long j2 = this.f4302i;
        if (j2 <= 0) {
            return -1L;
        }
        long j3 = this.f4303j;
        if (j3 > 0) {
            return j3 - j2;
        }
        return -1L;
    }

    public void c() {
        this.f4301h.removeMessages(0);
    }

    public void d() {
        this.f4302i = System.currentTimeMillis();
    }

    public void e() {
        this.f4303j = System.currentTimeMillis();
    }

    private void a(Throwable th, String str) {
        HaoboLog.e(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediation_instantiation_failure, th.getClass().getSimpleName()));
        if (!StringUtil.isEmpty(str)) {
            HaoboLog.w(HaoboLog.mediationLogTag, String.format("Adding %s to invalid networks list", str));
            g.a().a(l.NATIVE, str);
        }
        this.f4299f = 3;
    }

    public void a() {
        this.f4296c = null;
        HaoboLog.d(HaoboLog.mediationLogTag, HaoboLog.getString(R.string.mediation_finish));
    }

    public void a(int i2) {
        if (this.f4297d || this.f4298e) {
            return;
        }
        e();
        c();
        b(i2);
        this.f4298e = true;
        a();
    }

    private long a(e eVar) {
        if (eVar == null) {
            return -1L;
        }
        long j2 = this.f4303j;
        if (j2 > 0) {
            return eVar.a(j2);
        }
        return -1L;
    }

    public void b() {
        if (this.f4297d || this.f4298e) {
            return;
        }
        this.f4301h.sendEmptyMessageDelayed(0, C.DEFAULT_SEEK_FORWARD_INCREMENT_MS);
    }

    public void a(boolean z2) {
        this.f4300g = z2;
        if (z2) {
            a();
        }
    }
}
