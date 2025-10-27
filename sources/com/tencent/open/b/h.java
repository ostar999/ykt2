package com.tencent.open.b;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import com.tencent.open.log.SLog;
import com.tencent.open.utils.j;
import com.tencent.open.utils.k;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    protected static h f20542a;

    /* renamed from: e, reason: collision with root package name */
    protected HandlerThread f20546e;

    /* renamed from: f, reason: collision with root package name */
    protected Handler f20547f;

    /* renamed from: b, reason: collision with root package name */
    protected Random f20543b = new Random();

    /* renamed from: d, reason: collision with root package name */
    protected List<Serializable> f20545d = Collections.synchronizedList(new ArrayList());

    /* renamed from: c, reason: collision with root package name */
    protected List<Serializable> f20544c = Collections.synchronizedList(new ArrayList());

    /* renamed from: g, reason: collision with root package name */
    protected Executor f20548g = j.b();

    /* renamed from: h, reason: collision with root package name */
    protected Executor f20549h = j.b();

    private h() {
        this.f20546e = null;
        if (this.f20546e == null) {
            HandlerThread handlerThread = new HandlerThread("opensdk.report.handlerthread", 10);
            this.f20546e = handlerThread;
            handlerThread.start();
        }
        if (!this.f20546e.isAlive() || this.f20546e.getLooper() == null) {
            return;
        }
        this.f20547f = new Handler(this.f20546e.getLooper()) { // from class: com.tencent.open.b.h.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 == 1000) {
                    h.this.b();
                } else if (i2 == 1001) {
                    h.this.e();
                }
                super.handleMessage(message);
            }
        };
    }

    public static synchronized h a() {
        if (f20542a == null) {
            f20542a = new h();
        }
        return f20542a;
    }

    public void b() {
        if (k.b(com.tencent.open.utils.f.a())) {
            this.f20549h.execute(new Runnable() { // from class: com.tencent.open.b.h.4
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Map<String, String> mapC = h.this.c();
                        if (mapC != null && !mapC.isEmpty()) {
                            int iA = com.tencent.open.utils.g.a(com.tencent.open.utils.f.a(), (String) null).a("Common_HttpRetryCount");
                            if (iA == 0) {
                                iA = 3;
                            }
                            SLog.d("openSDK_LOG.ReportManager", "-->doReportCgi, retryCount: " + iA);
                            boolean z2 = false;
                            int i2 = 0;
                            while (true) {
                                i2++;
                                try {
                                    int iD = com.tencent.open.a.a.a().b("https://wspeed.qq.com/w.cgi", mapC).d();
                                    SLog.i("openSDK_LOG.ReportManager", "-->doReportCgi, statusCode: " + iD);
                                    if (iD != 200) {
                                        break;
                                    }
                                    g.a().b("report_cgi");
                                    z2 = true;
                                    break;
                                } catch (SocketTimeoutException e2) {
                                    SLog.e("openSDK_LOG.ReportManager", "-->doReportCgi, doupload exception", e2);
                                    if (i2 >= iA) {
                                        break;
                                    }
                                } catch (Exception e3) {
                                    SLog.e("openSDK_LOG.ReportManager", "-->doReportCgi, doupload exception", e3);
                                }
                            }
                            if (!z2) {
                                g.a().a("report_cgi", h.this.f20544c);
                            }
                            h.this.f20544c.clear();
                        }
                    } catch (Exception e4) {
                        SLog.e("openSDK_LOG.ReportManager", "-->doReportCgi, doupload exception out.", e4);
                    }
                }
            });
        }
    }

    public Map<String, String> c() {
        if (this.f20544c.size() == 0) {
            return null;
        }
        c cVar = (c) this.f20544c.get(0);
        if (cVar == null) {
            SLog.d("openSDK_LOG.ReportManager", "-->prepareCgiData, the 0th cgireportitem is null.");
            return null;
        }
        String str = cVar.f20536a.get("appid");
        List<Serializable> listA = g.a().a("report_cgi");
        if (listA != null) {
            this.f20544c.addAll(listA);
        }
        SLog.d("openSDK_LOG.ReportManager", "-->prepareCgiData, mCgiList size: " + this.f20544c.size());
        if (this.f20544c.size() == 0) {
            return null;
        }
        HashMap map = new HashMap();
        try {
            map.put("appid", str);
            map.put("releaseversion", Constants.SDK_VERSION_REPORT);
            map.put(com.alipay.sdk.packet.d.f3298n, Build.DEVICE);
            map.put("qua", Constants.SDK_QUA);
            map.put("key", "apn,frequency,commandid,resultcode,tmcost,reqsize,rspsize,detail,touin,deviceinfo");
            for (int i2 = 0; i2 < this.f20544c.size(); i2++) {
                c cVar2 = (c) this.f20544c.get(i2);
                map.put(i2 + "_1", cVar2.f20536a.get("apn"));
                map.put(i2 + "_2", cVar2.f20536a.get("frequency"));
                map.put(i2 + "_3", cVar2.f20536a.get("commandid"));
                map.put(i2 + "_4", cVar2.f20536a.get("resultCode"));
                map.put(i2 + "_5", cVar2.f20536a.get("timeCost"));
                map.put(i2 + "_6", cVar2.f20536a.get("reqSize"));
                map.put(i2 + "_7", cVar2.f20536a.get("rspSize"));
                map.put(i2 + "_8", cVar2.f20536a.get("detail"));
                map.put(i2 + "_9", cVar2.f20536a.get("uin"));
                map.put(i2 + "_10", d.e(com.tencent.open.utils.f.a()) + "&" + cVar2.f20536a.get("deviceInfo"));
            }
            SLog.v("openSDK_LOG.ReportManager", "-->prepareCgiData, end. params: " + map.toString());
            return map;
        } catch (Exception e2) {
            SLog.e("openSDK_LOG.ReportManager", "-->prepareCgiData, exception.", e2);
            return null;
        }
    }

    public Map<String, String> d() throws JSONException {
        List<Serializable> listA = g.a().a("report_via");
        if (listA != null) {
            this.f20545d.addAll(listA);
        }
        SLog.d("openSDK_LOG.ReportManager", "-->prepareViaData, mViaList size: " + this.f20545d.size());
        if (this.f20545d.size() == 0) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (Serializable serializable : this.f20545d) {
            JSONObject jSONObject = new JSONObject();
            c cVar = (c) serializable;
            for (String str : cVar.f20536a.keySet()) {
                try {
                    String str2 = cVar.f20536a.get(str);
                    if (str2 == null) {
                        str2 = "";
                    }
                    jSONObject.put(str, str2);
                } catch (JSONException e2) {
                    SLog.e("openSDK_LOG.ReportManager", "-->prepareViaData, put bundle to json array exception", e2);
                }
            }
            jSONArray.put(jSONObject);
        }
        SLog.v("openSDK_LOG.ReportManager", "-->prepareViaData, JSONArray array: " + jSONArray.toString());
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("data", jSONArray);
            HashMap map = new HashMap();
            map.put("data", jSONObject2.toString());
            return map;
        } catch (JSONException e3) {
            SLog.e("openSDK_LOG.ReportManager", "-->prepareViaData, put bundle to json array exception", e3);
            return null;
        }
    }

    public void e() {
        if (k.b(com.tencent.open.utils.f.a())) {
            this.f20548g.execute(new Runnable() { // from class: com.tencent.open.b.h.5
                /* JADX WARN: Code restructure failed: missing block: B:23:0x008f, code lost:
                
                    r18 = r5;
                    r22 = r9;
                    r20 = r14;
                    r7 = true;
                 */
                /* JADX WARN: Removed duplicated region for block: B:64:0x00c0 A[SYNTHETIC] */
                /* JADX WARN: Removed duplicated region for block: B:68:? A[LOOP:0: B:8:0x002f->B:68:?, LOOP_END, SYNTHETIC] */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void run() throws org.json.JSONException {
                    /*
                        Method dump skipped, instructions count: 281
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.h.AnonymousClass5.run():void");
                }
            });
        }
    }

    public void a(final Bundle bundle, String str, final boolean z2) {
        if (bundle == null) {
            return;
        }
        SLog.v("openSDK_LOG.ReportManager", "-->reportVia, bundle: " + bundle.toString());
        if (a("report_via", str) || z2) {
            this.f20548g.execute(new Runnable() { // from class: com.tencent.open.b.h.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        String strK = k.k(d.b(com.tencent.open.utils.f.a()));
                        String strK2 = k.k(d.c(com.tencent.open.utils.f.a()));
                        String strK3 = k.k(d.a());
                        String strK4 = k.k(d.d(com.tencent.open.utils.f.a()));
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("uin", Constants.DEFAULT_UIN);
                        bundle2.putString("imei", strK);
                        bundle2.putString("imsi", strK2);
                        bundle2.putString(SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID, strK4);
                        bundle2.putString(SocializeProtocolConstants.PROTOCOL_KEY_MAC, strK3);
                        bundle2.putString("platform", "1");
                        bundle2.putString("os_ver", Build.VERSION.RELEASE);
                        bundle2.putString("position", "");
                        bundle2.putString("network", a.a(com.tencent.open.utils.f.a()));
                        bundle2.putString("language", d.b());
                        bundle2.putString("resolution", d.a(com.tencent.open.utils.f.a()));
                        bundle2.putString("apn", a.b(com.tencent.open.utils.f.a()));
                        bundle2.putString(Constants.PARAM_MODEL_NAME, Build.MODEL);
                        bundle2.putString(am.M, TimeZone.getDefault().getID());
                        bundle2.putString("sdk_ver", Constants.SDK_VERSION);
                        bundle2.putString("qz_ver", k.d(com.tencent.open.utils.f.a(), "com.qzone"));
                        bundle2.putString(Constants.PARAM_QQ_VER, k.c(com.tencent.open.utils.f.a(), "com.tencent.mobileqq"));
                        bundle2.putString("qua", k.e(com.tencent.open.utils.f.a(), com.tencent.open.utils.f.b()));
                        bundle2.putString("packagename", com.tencent.open.utils.f.b());
                        bundle2.putString(Constants.PARAM_APP_VER, k.d(com.tencent.open.utils.f.a(), com.tencent.open.utils.f.b()));
                        Bundle bundle3 = bundle;
                        if (bundle3 != null) {
                            bundle2.putAll(bundle3);
                        }
                        h.this.f20545d.add(new c(bundle2));
                        int size = h.this.f20545d.size();
                        int iA = com.tencent.open.utils.g.a(com.tencent.open.utils.f.a(), (String) null).a("Agent_ReportTimeInterval");
                        if (iA == 0) {
                            iA = 10000;
                        }
                        if (!h.this.a("report_via", size) && !z2) {
                            if (h.this.f20547f.hasMessages(1001)) {
                                return;
                            }
                            Message messageObtain = Message.obtain();
                            messageObtain.what = 1001;
                            h.this.f20547f.sendMessageDelayed(messageObtain, iA);
                            return;
                        }
                        h.this.e();
                        h.this.f20547f.removeMessages(1001);
                    } catch (Exception e2) {
                        SLog.e("openSDK_LOG.ReportManager", "--> reporVia, exception in sub thread.", e2);
                    }
                }
            });
        }
    }

    public void a(String str, long j2, long j3, long j4, int i2) {
        a(str, j2, j3, j4, i2, "", false);
    }

    public void a(final String str, final long j2, final long j3, final long j4, final int i2, final String str2, final boolean z2) {
        SLog.v("openSDK_LOG.ReportManager", "-->reportCgi, command: " + str + " | startTime: " + j2 + " | reqSize:" + j3 + " | rspSize: " + j4 + " | responseCode: " + i2 + " | detail: " + str2);
        if (a("report_cgi", "" + i2) || z2) {
            this.f20549h.execute(new Runnable() { // from class: com.tencent.open.b.h.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        long jElapsedRealtime = SystemClock.elapsedRealtime() - j2;
                        Bundle bundle = new Bundle();
                        String strA = a.a(com.tencent.open.utils.f.a());
                        bundle.putString("apn", strA);
                        bundle.putString("appid", "1000067");
                        bundle.putString("commandid", str);
                        bundle.putString("detail", str2);
                        StringBuilder sb = new StringBuilder();
                        sb.append("network=");
                        sb.append(strA);
                        sb.append('&');
                        sb.append("sdcard=");
                        int i3 = 1;
                        sb.append(Environment.getExternalStorageState().equals("mounted") ? 1 : 0);
                        sb.append('&');
                        sb.append("wifi=");
                        sb.append(a.e(com.tencent.open.utils.f.a()));
                        bundle.putString("deviceInfo", sb.toString());
                        int iA = 100 / h.this.a(i2);
                        if (iA > 0) {
                            i3 = iA > 100 ? 100 : iA;
                        }
                        bundle.putString("frequency", i3 + "");
                        bundle.putString("reqSize", j3 + "");
                        bundle.putString("resultCode", i2 + "");
                        bundle.putString("rspSize", j4 + "");
                        bundle.putString("timeCost", jElapsedRealtime + "");
                        bundle.putString("uin", Constants.DEFAULT_UIN);
                        h.this.f20544c.add(new c(bundle));
                        int size = h.this.f20544c.size();
                        int iA2 = com.tencent.open.utils.g.a(com.tencent.open.utils.f.a(), (String) null).a("Agent_ReportTimeInterval");
                        if (iA2 == 0) {
                            iA2 = 10000;
                        }
                        if (h.this.a("report_cgi", size) || z2) {
                            h.this.b();
                            h.this.f20547f.removeMessages(1000);
                        } else if (!h.this.f20547f.hasMessages(1000)) {
                            Message messageObtain = Message.obtain();
                            messageObtain.what = 1000;
                            h.this.f20547f.sendMessageDelayed(messageObtain, iA2);
                        }
                    } catch (Exception e2) {
                        SLog.e("openSDK_LOG.ReportManager", "--> reportCGI, exception in sub thread.", e2);
                    }
                }
            });
        }
    }

    public boolean a(String str, String str2) {
        int iA;
        SLog.d("openSDK_LOG.ReportManager", "-->availableFrequency, report: " + str + " | ext: " + str2);
        boolean z2 = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        int i2 = 100;
        if (str.equals("report_cgi")) {
            try {
                iA = a(Integer.parseInt(str2));
                if (this.f20543b.nextInt(100) < iA) {
                    z2 = true;
                }
            } catch (Exception unused) {
                return false;
            }
        } else {
            if (str.equals("report_via")) {
                iA = f.a(str2);
                if (this.f20543b.nextInt(100) < iA) {
                    i2 = iA;
                    z2 = true;
                }
            }
            SLog.d("openSDK_LOG.ReportManager", "-->availableFrequency, result: " + z2 + " | frequency: " + i2);
            return z2;
        }
        i2 = iA;
        SLog.d("openSDK_LOG.ReportManager", "-->availableFrequency, result: " + z2 + " | frequency: " + i2);
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001d A[PHI: r0
      0x001d: PHI (r0v9 int) = (r0v6 int), (r0v12 int) binds: [B:11:0x0036, B:5:0x001a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(java.lang.String r5, int r6) {
        /*
            r4 = this;
            java.lang.String r0 = "report_cgi"
            boolean r0 = r5.equals(r0)
            r1 = 5
            r2 = 0
            r3 = 0
            if (r0 == 0) goto L1f
            android.content.Context r0 = com.tencent.open.utils.f.a()
            com.tencent.open.utils.g r0 = com.tencent.open.utils.g.a(r0, r2)
            java.lang.String r2 = "Common_CGIReportMaxcount"
            int r0 = r0.a(r2)
            if (r0 != 0) goto L1d
            goto L3a
        L1d:
            r1 = r0
            goto L3a
        L1f:
            java.lang.String r0 = "report_via"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L39
            android.content.Context r0 = com.tencent.open.utils.f.a()
            com.tencent.open.utils.g r0 = com.tencent.open.utils.g.a(r0, r2)
            java.lang.String r2 = "Agent_ReportBatchCount"
            int r0 = r0.a(r2)
            if (r0 != 0) goto L1d
            goto L3a
        L39:
            r1 = r3
        L3a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "-->availableCount, report: "
            r0.append(r2)
            r0.append(r5)
            java.lang.String r5 = " | dataSize: "
            r0.append(r5)
            r0.append(r6)
            java.lang.String r5 = " | maxcount: "
            r0.append(r5)
            r0.append(r1)
            java.lang.String r5 = r0.toString()
            java.lang.String r0 = "openSDK_LOG.ReportManager"
            com.tencent.open.log.SLog.d(r0, r5)
            if (r6 < r1) goto L64
            r5 = 1
            return r5
        L64:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.h.a(java.lang.String, int):boolean");
    }

    public int a(int i2) {
        if (i2 == 0) {
            int iA = com.tencent.open.utils.g.a(com.tencent.open.utils.f.a(), (String) null).a("Common_CGIReportFrequencySuccess");
            if (iA == 0) {
                return 10;
            }
            return iA;
        }
        int iA2 = com.tencent.open.utils.g.a(com.tencent.open.utils.f.a(), (String) null).a("Common_CGIReportFrequencyFailed");
        if (iA2 == 0) {
            return 100;
        }
        return iA2;
    }

    public void a(final String str, final Map<String, String> map) {
        if (k.b(com.tencent.open.utils.f.a())) {
            j.b(new Runnable() { // from class: com.tencent.open.b.h.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        int iA = f.a();
                        if (iA == 0) {
                            iA = 3;
                        }
                        SLog.d("openSDK_LOG.ReportManager", "-->httpRequest, retryCount: " + iA);
                        int i2 = 0;
                        do {
                            i2++;
                            try {
                                SLog.i("openSDK_LOG.ReportManager", "-->httpRequest, statusCode: " + com.tencent.open.a.a.a().a(str, map).d());
                            } catch (SocketTimeoutException e2) {
                                SLog.e("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest SocketTimeoutException:", e2);
                            } catch (Exception e3) {
                                SLog.e("openSDK_LOG.ReportManager", "-->ReportCenter httpRequest Exception:", e3);
                            }
                        } while (i2 < iA);
                    } catch (Exception e4) {
                        SLog.e("openSDK_LOG.ReportManager", "-->httpRequest, exception in serial executor:", e4);
                    }
                }
            });
        }
    }
}
