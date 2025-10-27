package com.beizi.ad.internal.network;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.beizi.ad.R;
import com.beizi.ad.a.a.h;
import com.beizi.ad.a.a.i;
import com.beizi.ad.a.a.j;
import com.beizi.ad.a.a.m;
import com.beizi.ad.c.a;
import com.beizi.ad.c.b;
import com.beizi.ad.c.d;
import com.beizi.ad.c.e;
import com.beizi.ad.internal.d;
import com.beizi.ad.internal.e;
import com.beizi.ad.internal.g;
import com.beizi.ad.internal.l;
import com.beizi.ad.internal.utilities.DeviceInfo;
import com.beizi.ad.internal.utilities.DeviceInfoUtil;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.HashingFunctions;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.utilities.UserEnvInfo;
import com.beizi.ad.internal.utilities.UserEnvInfoUtil;
import com.beizi.ad.internal.utilities.WebviewUtil;
import com.psychiatrygarden.utils.MimeTypes;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public class a extends AsyncTask<Void, Integer, ServerResponse> {

    /* renamed from: a, reason: collision with root package name */
    private SoftReference<e> f4364a;

    /* renamed from: d, reason: collision with root package name */
    private final Set<String> f4365d;

    /* renamed from: e, reason: collision with root package name */
    private final Bundle f4366e;

    /* renamed from: f, reason: collision with root package name */
    private final Set<String> f4367f;

    /* renamed from: g, reason: collision with root package name */
    private Date f4368g;

    /* renamed from: h, reason: collision with root package name */
    private String f4369h;

    /* renamed from: i, reason: collision with root package name */
    private int f4370i;

    /* renamed from: j, reason: collision with root package name */
    private boolean f4371j;

    /* renamed from: k, reason: collision with root package name */
    private String f4372k;

    /* renamed from: l, reason: collision with root package name */
    private int f4373l;

    /* renamed from: m, reason: collision with root package name */
    private boolean f4374m;

    /* renamed from: c, reason: collision with root package name */
    private static final ServerResponse f4363c = new ServerResponse(true);

    /* renamed from: b, reason: collision with root package name */
    public static final String f4362b = HashingFunctions.md5("emulator");

    /* renamed from: com.beizi.ad.internal.network.a$a, reason: collision with other inner class name */
    public static final class C0055a {

        /* renamed from: d, reason: collision with root package name */
        private Date f4378d;

        /* renamed from: e, reason: collision with root package name */
        private String f4379e;

        /* renamed from: h, reason: collision with root package name */
        private String f4382h;

        /* renamed from: j, reason: collision with root package name */
        private boolean f4384j;

        /* renamed from: a, reason: collision with root package name */
        private final HashSet<String> f4375a = new HashSet<>();

        /* renamed from: b, reason: collision with root package name */
        private final Bundle f4376b = new Bundle();

        /* renamed from: c, reason: collision with root package name */
        private final HashSet<String> f4377c = new HashSet<>();

        /* renamed from: f, reason: collision with root package name */
        private int f4380f = -1;

        /* renamed from: g, reason: collision with root package name */
        private boolean f4381g = false;

        /* renamed from: i, reason: collision with root package name */
        private int f4383i = -1;

        public void a(String str) {
            this.f4375a.add(str);
        }

        public void b(String str) {
            this.f4377c.add(str);
        }

        public void c(String str) {
            this.f4379e = str;
        }

        public void d(String str) {
            this.f4382h = str;
        }

        public com.beizi.ad.b.a e() {
            return new com.beizi.ad.b.a(this.f4378d, this.f4380f, this.f4375a, false);
        }

        public void a(Class<? extends com.beizi.ad.b.b> cls, Bundle bundle) {
            this.f4376b.putBundle(cls.getName(), bundle);
        }

        public void b(boolean z2) {
            this.f4384j = z2;
        }

        public int c() {
            return this.f4380f;
        }

        public Set<String> d() {
            return this.f4375a;
        }

        public void a(Date date) {
            this.f4378d = date;
        }

        public String b() {
            return this.f4379e;
        }

        public void a(int i2) {
            this.f4380f = i2;
        }

        public void a(boolean z2) {
            this.f4383i = z2 ? 1 : 0;
        }

        public Date a() {
            return this.f4378d;
        }

        public Bundle a(Class<? extends com.beizi.ad.b.b> cls) {
            return this.f4376b.getBundle(cls.getName());
        }
    }

    public a() {
        this.f4365d = new HashSet();
        this.f4366e = null;
        this.f4367f = new HashSet();
    }

    private boolean b(int i2) {
        if (i2 == 200) {
            return true;
        }
        HaoboLog.i(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.http_bad_status, i2));
        return false;
    }

    public void a(e eVar) {
        this.f4364a = new SoftReference<>(eVar);
        d dVarC = eVar.c();
        if (dVarC == null || dVarC.b() == null) {
            a(0);
            cancel(true);
            return;
        }
        DeviceInfoUtil.retrieveDeviceInfo(dVarC.b().getApplicationContext());
        UserEnvInfoUtil.retrieveUserEnvInfo(dVarC.b().getApplicationContext());
        if (c.a(dVarC.b().getApplicationContext()).b(dVarC.b())) {
            return;
        }
        a(2);
        cancel(true);
    }

    @Override // android.os.AsyncTask
    @TargetApi(11)
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public void onCancelled(ServerResponse serverResponse) {
        super.onCancelled(serverResponse);
        HaoboLog.w(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.cancel_request));
    }

    public a(C0055a c0055a) {
        this.f4368g = c0055a.f4378d;
        this.f4369h = c0055a.f4379e;
        this.f4370i = c0055a.f4380f;
        this.f4365d = Collections.unmodifiableSet(c0055a.f4375a);
        this.f4366e = c0055a.f4376b;
        this.f4367f = Collections.unmodifiableSet(c0055a.f4377c);
        this.f4371j = c0055a.f4381g;
        this.f4372k = c0055a.f4382h;
        this.f4373l = c0055a.f4383i;
        this.f4374m = c0055a.f4384j;
    }

    private void a(int i2) {
        e eVar = this.f4364a.get();
        if (eVar != null) {
            eVar.a(i2);
        }
        HaoboLog.clearLastResponse();
    }

    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public ServerResponse doInBackground(Void... voidArr) throws IOException {
        d dVarC;
        e eVar = this.f4364a.get();
        if (eVar == null || (dVarC = eVar.c()) == null) {
            return null;
        }
        try {
            boolean z2 = dVarC.i() == l.PREFETCH;
            g gVarA = g.a();
            DeviceInfo deviceInfo = DeviceInfo.getInstance();
            d.a aVarA = new d.a.C0048a().a(deviceInfo.sdkUID).j(DeviceInfo.density).l(j.a(g.a().f4184i)).m(j.b(g.a().f4184i)).n(deviceInfo.bootMark).o(deviceInfo.updateMark).b("").c(deviceInfo.os).a(e.EnumC0049e.PLATFORM_ANDROID).a(deviceInfo.devType).d(deviceInfo.brand).e(deviceInfo.model).f(deviceInfo.manufacturer).g(deviceInfo.resolution).h(deviceInfo.screenSize).i(deviceInfo.language).k(deviceInfo.root).p(deviceInfo.agVercode).a(deviceInfo.wxInstalled).q(DeviceInfo.physicalMemoryByte).r(DeviceInfo.harddiskSizeByte).a();
            UserEnvInfo userEnvInfo = UserEnvInfo.getInstance();
            a.b.C0045a c0045aC = new a.b.C0045a().a("3.4.20.26").a(e.i.SRC_APP).c(UserEnvInfoUtil.getVersionName(g.a().f4184i)).a(z2 ? e.g.REQ_WIFI_PRELOAD : e.g.REQ_AD).a(m.c()).b(gVarA.d()).a(aVarA).a(new d.c.a().a(userEnvInfo.f4406net).a(userEnvInfo.isp).a()).c(m.d(dVarC.b())).d(m.c(dVarC.b())).b(m.a(dVarC.b())).c(m.b(dVarC.b()));
            if (z2) {
                for (String str : g.a().l()) {
                    if (!StringUtil.isEmpty(str)) {
                        c0045aC.a(new a.C0043a.C0044a().a(str).c(dVarC.a()).a());
                    }
                }
            } else {
                c0045aC.a(new a.C0043a.C0044a().a(dVarC.c()).c(dVarC.a()).b(dVarC.k()).a());
            }
            a.b bVarA = c0045aC.a();
            byte[] bytes = com.beizi.ad.a.a.a.a(h.a(), bVarA.toString()).getBytes();
            i.d("lance", "sdkRequest:" + bVarA.toString());
            String strN = g.a().n();
            i.d("lance", "getRequestBaseUrl:" + strN);
            HaoboLog.setLastRequest(bVarA.toString());
            HaoboLog.i(HaoboLog.httpReqLogTag, HaoboLog.getString(R.string.fetch_url, HaoboLog.getLastRequest()));
            HttpURLConnection httpURLConnectionA = a(new URL(strN));
            a(httpURLConnectionA, bytes);
            httpURLConnectionA.connect();
            if (!b(httpURLConnectionA.getResponseCode())) {
                return f4363c;
            }
            if (httpURLConnectionA.getContentLength() == 0) {
                HaoboLog.e(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.response_blank));
            }
            InputStream inputStream = httpURLConnectionA.getInputStream();
            b.i iVarA = b.i.a(inputStream);
            inputStream.close();
            return new ServerResponse(iVarA, httpURLConnectionA.getHeaderFields(), dVarC.i());
        } catch (MalformedURLException unused) {
            HaoboLog.e(HaoboLog.httpReqLogTag, HaoboLog.getString(R.string.http_url_malformed));
            return f4363c;
        } catch (IOException unused2) {
            HaoboLog.e(HaoboLog.httpReqLogTag, HaoboLog.getString(R.string.http_io));
            return f4363c;
        } catch (IllegalArgumentException unused3) {
            HaoboLog.e(HaoboLog.httpReqLogTag, HaoboLog.getString(R.string.http_unknown));
            return f4363c;
        } catch (SecurityException unused4) {
            HaoboLog.e(HaoboLog.httpReqLogTag, HaoboLog.getString(R.string.permissions_internet));
            return f4363c;
        } catch (Exception e2) {
            e2.printStackTrace();
            HaoboLog.e(HaoboLog.httpReqLogTag, Log.getStackTraceString(e2));
            HaoboLog.e(HaoboLog.httpReqLogTag, HaoboLog.getString(R.string.unknown_exception));
            return f4363c;
        }
    }

    private HttpURLConnection a(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod("POST");
        return httpURLConnection;
    }

    private void a(HttpURLConnection httpURLConnection, byte[] bArr) throws IOException {
        httpURLConnection.setRequestProperty("User-Agent", g.a().f4182d);
        httpURLConnection.setRequestProperty("Content-Type", MimeTypes.APP_JSON);
        httpURLConnection.setRequestProperty("Accept", MimeTypes.APP_JSON);
        String cookie = WebviewUtil.getCookie();
        if (!TextUtils.isEmpty(cookie)) {
            httpURLConnection.setRequestProperty("Cookie", cookie);
        }
        httpURLConnection.setRequestProperty("Connect-Length", Integer.toString(bArr.length));
        httpURLConnection.setFixedLengthStreamingMode(bArr.length);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        outputStream.write(bArr);
        outputStream.flush();
        outputStream.close();
    }

    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(ServerResponse serverResponse) {
        if (serverResponse == null) {
            HaoboLog.v(HaoboLog.httpRespLogTag, HaoboLog.getString(R.string.no_response));
            a(2);
        } else {
            if (serverResponse.a()) {
                a(2);
                return;
            }
            com.beizi.ad.internal.e eVar = this.f4364a.get();
            if (eVar != null) {
                eVar.a(serverResponse);
            }
        }
    }
}
