package com.alipay.apmobilesecuritysdk.a;

import android.content.Context;
import android.os.Environment;
import com.alipay.apmobilesecuritysdk.d.e;
import com.alipay.apmobilesecuritysdk.e.b;
import com.alipay.apmobilesecuritysdk.e.g;
import com.alipay.apmobilesecuritysdk.e.h;
import com.alipay.apmobilesecuritysdk.e.i;
import com.alipay.apmobilesecuritysdk.otherid.UmidSdkWrapper;
import com.alipay.security.mobile.module.http.model.c;
import com.alipay.security.mobile.module.http.model.d;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private Context f3017a;

    /* renamed from: b, reason: collision with root package name */
    private com.alipay.apmobilesecuritysdk.b.a f3018b = com.alipay.apmobilesecuritysdk.b.a.a();

    /* renamed from: c, reason: collision with root package name */
    private int f3019c = 4;

    public a(Context context) {
        this.f3017a = context;
    }

    public static String a(Context context) {
        String strB = b(context);
        return com.alipay.security.mobile.module.a.a.a(strB) ? h.f(context) : strB;
    }

    public static String a(Context context, String str) {
        try {
            b();
            String strA = i.a(str);
            if (!com.alipay.security.mobile.module.a.a.a(strA)) {
                return strA;
            }
            String strA2 = g.a(context, str);
            i.a(str, strA2);
            return !com.alipay.security.mobile.module.a.a.a(strA2) ? strA2 : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    private static boolean a() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] strArr = {"2017-01-27 2017-01-28", "2017-11-10 2017-11-11", "2017-12-11 2017-12-12"};
        int iRandom = ((int) (Math.random() * 24.0d * 60.0d * 60.0d)) * 1;
        for (int i2 = 0; i2 < 3; i2++) {
            try {
                String[] strArrSplit = strArr[i2].split(" ");
                if (strArrSplit != null && strArrSplit.length == 2) {
                    Date date = new Date();
                    Date date2 = simpleDateFormat.parse(strArrSplit[0] + " 00:00:00");
                    Date date3 = simpleDateFormat.parse(strArrSplit[1] + " 23:59:59");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date3);
                    calendar.add(13, iRandom);
                    Date time = calendar.getTime();
                    if (date.after(date2) && date.before(time)) {
                        return true;
                    }
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private c b(Map<String, String> map) {
        String strC;
        String strA;
        String strC2;
        b bVarB;
        b bVarC;
        String strA2 = "";
        try {
            Context context = this.f3017a;
            d dVar = new d();
            String strA3 = com.alipay.security.mobile.module.a.a.a(map, "appName", "");
            String strA4 = com.alipay.security.mobile.module.a.a.a(map, PLVLinkMicManager.SESSION_ID, "");
            String strA5 = com.alipay.security.mobile.module.a.a.a(map, "rpcVersion", "");
            String strA6 = a(context, strA3);
            String securityToken = UmidSdkWrapper.getSecurityToken(context);
            String strD = h.d(context);
            if (com.alipay.security.mobile.module.a.a.b(strA4)) {
                dVar.f3461c = strA4;
            } else {
                dVar.f3461c = strA6;
            }
            dVar.f3462d = securityToken;
            dVar.f3463e = strD;
            dVar.f3459a = "android";
            com.alipay.apmobilesecuritysdk.e.c cVarC = com.alipay.apmobilesecuritysdk.e.d.c(context);
            if (cVarC != null) {
                strA = cVarC.a();
                strC = cVarC.c();
            } else {
                strC = "";
                strA = strC;
            }
            if (com.alipay.security.mobile.module.a.a.a(strA) && (bVarC = com.alipay.apmobilesecuritysdk.e.a.c(context)) != null) {
                strA = bVarC.a();
                strC = bVarC.c();
            }
            com.alipay.apmobilesecuritysdk.e.c cVarB = com.alipay.apmobilesecuritysdk.e.d.b();
            if (cVarB != null) {
                strA2 = cVarB.a();
                strC2 = cVarB.c();
            } else {
                strC2 = "";
            }
            if (com.alipay.security.mobile.module.a.a.a(strA2) && (bVarB = com.alipay.apmobilesecuritysdk.e.a.b()) != null) {
                strA2 = bVarB.a();
                strC2 = bVarB.c();
            }
            dVar.f3466h = strA;
            dVar.f3465g = strA2;
            dVar.f3468j = strA5;
            if (com.alipay.security.mobile.module.a.a.a(strA)) {
                dVar.f3460b = strA2;
                dVar.f3467i = strC2;
            } else {
                dVar.f3460b = strA;
                dVar.f3467i = strC;
            }
            dVar.f3464f = e.a(context, map);
            return com.alipay.security.mobile.module.http.d.a(this.f3017a, this.f3018b.c()).a(dVar);
        } catch (Throwable th) {
            com.alipay.apmobilesecuritysdk.c.a.a(th);
            return null;
        }
    }

    private static String b(Context context) {
        try {
            String strB = i.b();
            if (!com.alipay.security.mobile.module.a.a.a(strB)) {
                return strB;
            }
            com.alipay.apmobilesecuritysdk.e.c cVarB = com.alipay.apmobilesecuritysdk.e.d.b(context);
            if (cVarB != null) {
                i.a(cVarB);
                String strA = cVarB.a();
                if (com.alipay.security.mobile.module.a.a.b(strA)) {
                    return strA;
                }
            }
            b bVarB = com.alipay.apmobilesecuritysdk.e.a.b(context);
            if (bVarB == null) {
                return "";
            }
            i.a(bVarB);
            String strA2 = bVarB.a();
            return com.alipay.security.mobile.module.a.a.b(strA2) ? strA2 : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    private static void b() {
        try {
            String[] strArr = {"device_feature_file_name", "wallet_times", "wxcasxx_v3", "wxcasxx_v4", "wxxzyy_v1"};
            for (int i2 = 0; i2 < 5; i2++) {
                String str = strArr[i2];
                File file = new File(Environment.getExternalStorageDirectory(), ".SystemConfig/" + str);
                if (file.exists() && file.canWrite()) {
                    file.delete();
                }
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x0218 A[Catch: Exception -> 0x0261, TryCatch #0 {Exception -> 0x0261, blocks: (B:3:0x0006, B:5:0x0037, B:8:0x0040, B:37:0x00be, B:76:0x01fe, B:78:0x0218, B:81:0x0220, B:83:0x0226, B:87:0x022f, B:89:0x0235, B:40:0x00d3, B:42:0x00ed, B:44:0x00f1, B:47:0x00fb, B:53:0x010c, B:54:0x011c, B:56:0x0123, B:60:0x0135, B:63:0x014a, B:65:0x018b, B:67:0x0195, B:69:0x019d, B:71:0x01aa, B:73:0x01b4, B:75:0x01bc, B:74:0x01b8, B:68:0x0199, B:11:0x0055, B:13:0x0063, B:16:0x006e, B:18:0x0074, B:21:0x007f, B:24:0x0088, B:27:0x0095, B:30:0x00a2, B:33:0x00b0), top: B:95:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0220 A[Catch: Exception -> 0x0261, TryCatch #0 {Exception -> 0x0261, blocks: (B:3:0x0006, B:5:0x0037, B:8:0x0040, B:37:0x00be, B:76:0x01fe, B:78:0x0218, B:81:0x0220, B:83:0x0226, B:87:0x022f, B:89:0x0235, B:40:0x00d3, B:42:0x00ed, B:44:0x00f1, B:47:0x00fb, B:53:0x010c, B:54:0x011c, B:56:0x0123, B:60:0x0135, B:63:0x014a, B:65:0x018b, B:67:0x0195, B:69:0x019d, B:71:0x01aa, B:73:0x01b4, B:75:0x01bc, B:74:0x01b8, B:68:0x0199, B:11:0x0055, B:13:0x0063, B:16:0x006e, B:18:0x0074, B:21:0x007f, B:24:0x0088, B:27:0x0095, B:30:0x00a2, B:33:0x00b0), top: B:95:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x022f A[Catch: Exception -> 0x0261, TryCatch #0 {Exception -> 0x0261, blocks: (B:3:0x0006, B:5:0x0037, B:8:0x0040, B:37:0x00be, B:76:0x01fe, B:78:0x0218, B:81:0x0220, B:83:0x0226, B:87:0x022f, B:89:0x0235, B:40:0x00d3, B:42:0x00ed, B:44:0x00f1, B:47:0x00fb, B:53:0x010c, B:54:0x011c, B:56:0x0123, B:60:0x0135, B:63:0x014a, B:65:0x018b, B:67:0x0195, B:69:0x019d, B:71:0x01aa, B:73:0x01b4, B:75:0x01bc, B:74:0x01b8, B:68:0x0199, B:11:0x0055, B:13:0x0063, B:16:0x006e, B:18:0x0074, B:21:0x007f, B:24:0x0088, B:27:0x0095, B:30:0x00a2, B:33:0x00b0), top: B:95:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int a(java.util.Map<java.lang.String, java.lang.String> r12) {
        /*
            Method dump skipped, instructions count: 616
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.apmobilesecuritysdk.a.a.a(java.util.Map):int");
    }
}
