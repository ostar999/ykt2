package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.sdk.util.H5PayResultModel;
import com.alipay.sdk.util.e;
import com.alipay.sdk.util.l;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class PayTask {

    /* renamed from: a, reason: collision with root package name */
    static final Object f3067a = com.alipay.sdk.util.e.class;

    /* renamed from: h, reason: collision with root package name */
    private static final long f3068h = 3000;

    /* renamed from: i, reason: collision with root package name */
    private static long f3069i = -1;

    /* renamed from: b, reason: collision with root package name */
    private Activity f3070b;

    /* renamed from: c, reason: collision with root package name */
    private com.alipay.sdk.widget.a f3071c;

    /* renamed from: d, reason: collision with root package name */
    private String f3072d = "wappaygw.alipay.com/service/rest.htm";

    /* renamed from: e, reason: collision with root package name */
    private String f3073e = "mclient.alipay.com/service/rest.htm";

    /* renamed from: f, reason: collision with root package name */
    private String f3074f = "mclient.alipay.com/home/exterfaceAssign.htm";

    /* renamed from: g, reason: collision with root package name */
    private Map<String, a> f3075g = new HashMap();

    public class a {

        /* renamed from: a, reason: collision with root package name */
        String f3076a;

        /* renamed from: b, reason: collision with root package name */
        String f3077b;

        private a() {
            this.f3076a = "";
            this.f3077b = "";
        }

        private String a() {
            return this.f3076a;
        }

        private String b() {
            return this.f3077b;
        }

        private void a(String str) {
            this.f3076a = str;
        }

        private void b(String str) {
            this.f3077b = str;
        }

        public /* synthetic */ a(PayTask payTask, byte b3) {
            this();
        }
    }

    public PayTask(Activity activity) {
        this.f3070b = activity;
        com.alipay.sdk.sys.b bVarA = com.alipay.sdk.sys.b.a();
        Activity activity2 = this.f3070b;
        com.alipay.sdk.data.c.a();
        bVarA.a(activity2);
        com.alipay.sdk.app.statistic.a.a(activity);
        this.f3071c = new com.alipay.sdk.widget.a(activity, com.alipay.sdk.widget.a.f3398b);
    }

    private void b() {
        com.alipay.sdk.widget.a aVar = this.f3071c;
        if (aVar != null) {
            aVar.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        com.alipay.sdk.widget.a aVar = this.f3071c;
        if (aVar != null) {
            aVar.b();
            this.f3071c = null;
        }
    }

    private static boolean d() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime - f3069i < 3000) {
            return true;
        }
        f3069i = jElapsedRealtime;
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00c8 A[Catch: all -> 0x0333, TryCatch #1 {all -> 0x0333, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x0021, B:14:0x009c, B:16:0x00b2, B:23:0x012d, B:25:0x0143, B:36:0x01c0, B:38:0x01d1, B:40:0x01df, B:42:0x01fc, B:44:0x0227, B:54:0x025c, B:60:0x0290, B:47:0x0238, B:49:0x023e, B:51:0x024c, B:63:0x02ee, B:65:0x02f6, B:67:0x02fc, B:69:0x0304, B:27:0x0159, B:29:0x0161, B:31:0x0169, B:18:0x00c8, B:20:0x00ed, B:9:0x0037, B:11:0x005c), top: B:82:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0159 A[Catch: all -> 0x0333, TryCatch #1 {all -> 0x0333, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x0021, B:14:0x009c, B:16:0x00b2, B:23:0x012d, B:25:0x0143, B:36:0x01c0, B:38:0x01d1, B:40:0x01df, B:42:0x01fc, B:44:0x0227, B:54:0x025c, B:60:0x0290, B:47:0x0238, B:49:0x023e, B:51:0x024c, B:63:0x02ee, B:65:0x02f6, B:67:0x02fc, B:69:0x0304, B:27:0x0159, B:29:0x0161, B:31:0x0169, B:18:0x00c8, B:20:0x00ed, B:9:0x0037, B:11:0x005c), top: B:82:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0037 A[Catch: all -> 0x0333, TryCatch #1 {all -> 0x0333, blocks: (B:3:0x0001, B:5:0x0007, B:7:0x0021, B:14:0x009c, B:16:0x00b2, B:23:0x012d, B:25:0x0143, B:36:0x01c0, B:38:0x01d1, B:40:0x01df, B:42:0x01fc, B:44:0x0227, B:54:0x025c, B:60:0x0290, B:47:0x0238, B:49:0x023e, B:51:0x024c, B:63:0x02ee, B:65:0x02f6, B:67:0x02fc, B:69:0x0304, B:27:0x0159, B:29:0x0161, B:31:0x0169, B:18:0x00c8, B:20:0x00ed, B:9:0x0037, B:11:0x005c), top: B:82:0x0001 }] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized java.lang.String fetchOrderInfoFromH5PayUrl(java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 826
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.fetchOrderInfoFromH5PayUrl(java.lang.String):java.lang.String");
    }

    public synchronized String fetchTradeToken() {
        return com.alipay.sdk.util.i.b(this.f3070b.getApplicationContext(), com.alipay.sdk.util.h.f3375a, "");
    }

    public String getVersion() {
        return com.alipay.sdk.cons.a.f3200f;
    }

    public synchronized H5PayResultModel h5Pay(String str, boolean z2) {
        H5PayResultModel h5PayResultModel = new H5PayResultModel();
        try {
            str.trim();
            String[] strArrSplit = pay(str, z2).split(com.alipay.sdk.util.h.f3376b);
            HashMap map = new HashMap();
            for (String str2 : strArrSplit) {
                String strSubstring = str2.substring(0, str2.indexOf("={"));
                String str3 = strSubstring + "={";
                map.put(strSubstring, str2.substring(str2.indexOf(str3) + str3.length(), str2.lastIndexOf("}")));
            }
            if (map.containsKey(com.alipay.sdk.util.j.f3383a)) {
                h5PayResultModel.setResultCode((String) map.get(com.alipay.sdk.util.j.f3383a));
            }
            if (map.containsKey("callBackUrl")) {
                h5PayResultModel.setReturnUrl((String) map.get("callBackUrl"));
            } else if (map.containsKey("result")) {
                String str4 = (String) map.get("result");
                if (str4.length() > 15) {
                    a aVar = this.f3075g.get(str);
                    if (aVar != null) {
                        if (TextUtils.isEmpty(aVar.f3077b)) {
                            h5PayResultModel.setReturnUrl(aVar.f3076a);
                        } else {
                            h5PayResultModel.setReturnUrl(com.alipay.sdk.data.a.b().f3250j.replace("$OrderId$", aVar.f3077b));
                        }
                        this.f3075g.remove(str);
                        return h5PayResultModel;
                    }
                    String strA = l.a("&callBackUrl=\"", "\"", str4);
                    if (TextUtils.isEmpty(strA)) {
                        strA = l.a("&call_back_url=\"", "\"", str4);
                        if (TextUtils.isEmpty(strA)) {
                            strA = l.a(com.alipay.sdk.cons.a.f3209o, "\"", str4);
                            if (TextUtils.isEmpty(strA)) {
                                strA = URLDecoder.decode(l.a(com.alipay.sdk.cons.a.f3210p, "&", str4), "utf-8");
                                if (TextUtils.isEmpty(strA)) {
                                    strA = URLDecoder.decode(l.a("&callBackUrl=", "&", str4), "utf-8");
                                }
                            }
                        }
                    }
                    if (TextUtils.isEmpty(strA) && !TextUtils.isEmpty(str4) && str4.contains("call_back_url")) {
                        strA = l.b("call_back_url=\"", "\"", str4);
                    }
                    if (TextUtils.isEmpty(strA)) {
                        strA = com.alipay.sdk.data.a.b().f3250j;
                    }
                    h5PayResultModel.setReturnUrl(strA);
                } else {
                    a aVar2 = this.f3075g.get(str);
                    if (aVar2 != null) {
                        h5PayResultModel.setReturnUrl(aVar2.f3076a);
                        this.f3075g.remove(str);
                        return h5PayResultModel;
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return h5PayResultModel;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:(12:28|(2:30|(1:32))|34|79|35|(1:37)(3:38|(4:41|(1:86)(2:45|(2:46|(1:1)(2:48|(2:53|(3:91|55|89)(1:56))(3:90|52|88))))|57|39)|84)|58|(1:60)|64|65|70|71)|79|35|(0)(0)|58|(0)|64|65|70|71) */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0137, code lost:
    
        r12 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0138, code lost:
    
        com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.f3135y, r12);
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00bb A[Catch: all -> 0x0137, TryCatch #2 {all -> 0x0137, blocks: (B:35:0x00b3, B:58:0x012b, B:60:0x0131, B:38:0x00bb, B:39:0x00c2, B:41:0x00c5, B:43:0x00cf, B:45:0x00d9, B:46:0x00ed, B:48:0x00f0, B:50:0x00fa, B:52:0x0104, B:53:0x0112, B:55:0x011c, B:56:0x0125, B:57:0x0128), top: B:79:0x00b3, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0131 A[Catch: all -> 0x0137, TRY_LEAVE, TryCatch #2 {all -> 0x0137, blocks: (B:35:0x00b3, B:58:0x012b, B:60:0x0131, B:38:0x00bb, B:39:0x00c2, B:41:0x00c5, B:43:0x00cf, B:45:0x00d9, B:46:0x00ed, B:48:0x00f0, B:50:0x00fa, B:52:0x0104, B:53:0x0112, B:55:0x011c, B:56:0x0125, B:57:0x0128), top: B:79:0x00b3, outer: #3 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized java.lang.String pay(java.lang.String r11, boolean r12) {
        /*
            Method dump skipped, instructions count: 404
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.PayTask.pay(java.lang.String, boolean):java.lang.String");
    }

    public synchronized boolean payInterceptorWithUrl(String str, boolean z2, H5PayCallback h5PayCallback) {
        String strFetchOrderInfoFromH5PayUrl;
        strFetchOrderInfoFromH5PayUrl = fetchOrderInfoFromH5PayUrl(str);
        if (!TextUtils.isEmpty(strFetchOrderInfoFromH5PayUrl)) {
            new Thread(new g(this, strFetchOrderInfoFromH5PayUrl, z2, h5PayCallback)).start();
        }
        return !TextUtils.isEmpty(strFetchOrderInfoFromH5PayUrl);
    }

    public synchronized Map<String, String> payV2(String str, boolean z2) {
        return com.alipay.sdk.util.j.a(pay(str, z2));
    }

    private static boolean a(boolean z2, boolean z3, String str, StringBuilder sb, Map<String, String> map, String... strArr) {
        String str2;
        int length = strArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                str2 = "";
                break;
            }
            String str3 = strArr[i2];
            if (!TextUtils.isEmpty(map.get(str3))) {
                str2 = map.get(str3);
                break;
            }
            i2++;
        }
        if (TextUtils.isEmpty(str2)) {
            return !z3;
        }
        if (!z2) {
            sb.append(str);
            sb.append("=\"");
            sb.append(str2);
            sb.append("\"");
            return true;
        }
        sb.append("&");
        sb.append(str);
        sb.append("=\"");
        sb.append(str2);
        sb.append("\"");
        return true;
    }

    private String b(String str) {
        j jVarA;
        b();
        try {
            try {
                List<com.alipay.sdk.protocol.b> listA = com.alipay.sdk.protocol.b.a(new com.alipay.sdk.packet.impl.d().a(this.f3070b.getApplicationContext(), str).a().optJSONObject(com.alipay.sdk.cons.c.f3228c).optJSONObject(com.alipay.sdk.cons.c.f3229d));
                for (int i2 = 0; i2 < listA.size(); i2++) {
                    if (listA.get(i2).f3313a == com.alipay.sdk.protocol.a.Update) {
                        String[] strArr = listA.get(i2).f3314b;
                        if (strArr.length == 3 && TextUtils.equals(com.alipay.sdk.cons.b.f3217c, strArr[0])) {
                            Context context = com.alipay.sdk.sys.b.a().f3333a;
                            com.alipay.sdk.tid.b bVarA = com.alipay.sdk.tid.b.a();
                            if (!TextUtils.isEmpty(strArr[1]) && !TextUtils.isEmpty(strArr[2])) {
                                bVarA.f3338a = strArr[1];
                                bVarA.f3339b = strArr[2];
                                com.alipay.sdk.tid.a aVar = new com.alipay.sdk.tid.a(context);
                                try {
                                    aVar.a(com.alipay.sdk.util.a.a(context).a(), com.alipay.sdk.util.a.a(context).b(), bVarA.f3338a, bVarA.f3339b);
                                } catch (Exception unused) {
                                } catch (Throwable th) {
                                    aVar.close();
                                    throw th;
                                }
                                aVar.close();
                            }
                        }
                    }
                }
                for (int i3 = 0; i3 < listA.size(); i3++) {
                    if (listA.get(i3).f3313a == com.alipay.sdk.protocol.a.WapPay) {
                        return a(listA.get(i3));
                    }
                }
            } catch (IOException e2) {
                j jVarA2 = j.a(j.NETWORK_ERROR.f3105h);
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3111a, e2);
                c();
                jVarA = jVarA2;
            } catch (Throwable th2) {
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.f3128r, th2);
            }
            c();
            jVarA = null;
            if (jVarA == null) {
                jVarA = j.a(j.FAILED.f3105h);
            }
            return i.a(jVarA.f3105h, jVarA.f3106i, "");
        } finally {
            c();
        }
    }

    private static String a(String str, String str2) {
        String str3 = str2 + "={";
        return str.substring(str.indexOf(str3) + str3.length(), str.lastIndexOf("}"));
    }

    private e.a a() {
        return new h(this);
    }

    private String a(String str) {
        String strA = new com.alipay.sdk.sys.a(this.f3070b).a(str);
        if (strA.contains("paymethod=\"expressGateway\"")) {
            return b(strA);
        }
        if (l.c(this.f3070b)) {
            com.alipay.sdk.util.e eVar = new com.alipay.sdk.util.e(this.f3070b, new h(this));
            String strA2 = eVar.a(strA);
            eVar.f3366a = null;
            if (TextUtils.equals(strA2, com.alipay.sdk.util.e.f3365b)) {
                return b(strA);
            }
            return TextUtils.isEmpty(strA2) ? i.a() : strA2;
        }
        return b(strA);
    }

    private String a(com.alipay.sdk.protocol.b bVar) {
        String[] strArr = bVar.f3314b;
        Intent intent = new Intent(this.f3070b, (Class<?>) H5PayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", strArr[0]);
        if (strArr.length == 2) {
            bundle.putString("cookie", strArr[1]);
        }
        intent.putExtras(bundle);
        this.f3070b.startActivity(intent);
        Object obj = f3067a;
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException unused) {
                return i.a();
            }
        }
        String str = i.f3096a;
        return TextUtils.isEmpty(str) ? i.a() : str;
    }
}
