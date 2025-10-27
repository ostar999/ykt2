package c;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;
import okhttp3.internal.ws.WebSocketProtocol;

/* loaded from: classes.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    public static final int f2233a = 0;

    /* renamed from: b, reason: collision with root package name */
    public static final int f2234b = 1;

    /* renamed from: c, reason: collision with root package name */
    public static final int f2235c = 2;

    /* renamed from: d, reason: collision with root package name */
    public static final int f2236d = 3;

    /* renamed from: e, reason: collision with root package name */
    public static final int f2237e = 4;

    /* renamed from: f, reason: collision with root package name */
    public static final int f2238f = 5;

    /* renamed from: g, reason: collision with root package name */
    public static final int f2239g = 6;

    /* renamed from: h, reason: collision with root package name */
    public static SimpleDateFormat f2240h = new SimpleDateFormat("yyyyMMddHHmmss");

    /* renamed from: i, reason: collision with root package name */
    public static long f2241i = 100000000000L;

    /* renamed from: j, reason: collision with root package name */
    public static Context f2242j = null;

    /* renamed from: k, reason: collision with root package name */
    public static int f2243k = 1;

    /* renamed from: l, reason: collision with root package name */
    public static String f2244l = "";

    /* renamed from: m, reason: collision with root package name */
    public static String f2245m = "https://urtc.com.cn/uteach";

    /* renamed from: n, reason: collision with root package name */
    public static String f2246n = "https://log.urtc.com.cn:443";

    public static synchronized String a() {
        String str;
        long j2;
        str = f2240h.format(new Date());
        j2 = f2241i + 1;
        f2241i = j2;
        return String.format("%s_%s_%s", f.e.f26885n, str, String.valueOf(j2));
    }

    public static Context b() {
        return f2242j;
    }

    public static String b(int i2) {
        switch (i2) {
            case 1:
                return "wifi";
            case 2:
                return "2g";
            case 3:
                return "3g";
            case 4:
                return "4g";
            case 5:
                return "5g";
            case 6:
                return "mobile";
            default:
                return "unkown";
        }
    }

    public static void c(int i2) {
        f2243k = i2;
    }

    public static String d() {
        return f2246n;
    }

    public static void e(String str) {
        f2244l = str;
    }

    public static int f() {
        return f2243k;
    }

    public static String g() {
        return f2244l;
    }

    public static int h() {
        if (Build.VERSION.SDK_INT >= 24) {
            return SubscriptionManager.getDefaultDataSubscriptionId();
        }
        return -1;
    }

    public static boolean b(String str) {
        return !TextUtils.isEmpty(str) && (str.contains("nrState=NOT_RESTRICTED") || str.contains("nrState=CONNECTED"));
    }

    public static String c() {
        return f2245m;
    }

    public static void d(String str) {
        f2246n = str;
    }

    public static int e() {
        NetworkInfo activeNetworkInfo;
        NetworkInfo.State state;
        ConnectivityManager connectivityManager = (ConnectivityManager) f2242j.getSystemService("connectivity");
        if (connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isAvailable()) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
            if (networkInfo != null && (state = networkInfo.getState()) != null && (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING)) {
                return 1;
            }
            NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
            if (networkInfo2 != null) {
                NetworkInfo.State state2 = networkInfo2.getState();
                String subtypeName = networkInfo2.getSubtypeName();
                if (state2 != null && (state2 == NetworkInfo.State.CONNECTED || state2 == NetworkInfo.State.CONNECTING)) {
                    int subtype = activeNetworkInfo.getSubtype();
                    h.d("RTC-COMMON", "TelephonyManager type is: " + subtype + " SubTypeName is: " + subtypeName);
                    if (subtype == 13) {
                        subtype = a(f2242j, subtype);
                    }
                    switch (subtype) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                        case 16:
                            return 2;
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                        case 17:
                            return 3;
                        case 13:
                        case 18:
                            return 4;
                        case 19:
                        default:
                            return (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) ? 3 : 6;
                        case 20:
                            return 5;
                    }
                }
            }
        }
        return 0;
    }

    public static void c(String str) {
        f2245m = str;
    }

    public static void a(Context context) {
        f2242j = context;
    }

    public static String a(String str, String str2, String str3, String str4) {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        long jCurrentTimeMillis2 = System.currentTimeMillis() / 1000;
        h.d("RTC-COMMON", "ran " + jCurrentTimeMillis + "  " + jCurrentTimeMillis2);
        try {
            return k.a(str, str3, str4, str2, (int) jCurrentTimeMillis2, (int) jCurrentTimeMillis);
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static f.k a(int i2, int i3) {
        int i4 = i2 * i3;
        if (i4 <= 230400) {
            return f.k.VP_SD;
        }
        if (i4 > 230400 && i4 <= 921600) {
            return f.k.VP_HD;
        }
        return f.k.VP_HD_PLUS;
    }

    public static String a(int i2) {
        return String.valueOf(i2 >>> 24) + StrPool.DOT + String.valueOf((16777215 & i2) >>> 16) + StrPool.DOT + String.valueOf((65535 & i2) >>> 8) + StrPool.DOT + String.valueOf(i2 & 255);
    }

    public static String a(Long l2) {
        return String.valueOf(l2.longValue() >>> 24) + StrPool.DOT + String.valueOf((l2.longValue() & 16777215) >>> 16) + StrPool.DOT + String.valueOf((l2.longValue() & WebSocketProtocol.PAYLOAD_SHORT_MAX) >>> 8) + StrPool.DOT + String.valueOf(l2.longValue() & 255);
    }

    public static boolean a(String str) {
        boolean zMatches = Pattern.compile("(((http)?://)?([a-z0-9]+[.])|(www.))\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;一-龥]]+)+)?([.][a-z0-9]{0,}+|/?)").matcher(str.trim()).matches();
        if (zMatches) {
            return true;
        }
        return zMatches;
    }

    public static boolean a(Context context, String str) {
        Iterator<ActivityManager.RunningServiceInfo> it = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningServices(100).iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String a(Throwable th) {
        if (th == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            th.printStackTrace(new PrintStream(byteArrayOutputStream));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return byteArrayOutputStream.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0052 A[Catch: all -> 0x005f, TRY_LEAVE, TryCatch #1 {all -> 0x005f, blocks: (B:6:0x000e, B:8:0x001d, B:18:0x0052, B:15:0x004b, B:13:0x0045, B:10:0x0022), top: B:26:0x000e, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(android.content.Context r7, int r8) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 29
            if (r0 < r1) goto L63
            java.lang.String r0 = "android.permission.READ_PHONE_STATE"
            int r0 = r7.checkSelfPermission(r0)
            if (r0 != 0) goto L63
            java.lang.String r0 = "phone"
            java.lang.Object r7 = r7.getSystemService(r0)     // Catch: java.lang.Throwable -> L5f
            android.telephony.TelephonyManager r7 = (android.telephony.TelephonyManager) r7     // Catch: java.lang.Throwable -> L5f
            int r0 = h()     // Catch: java.lang.Throwable -> L5f
            r1 = -1
            if (r0 != r1) goto L22
            android.telephony.ServiceState r7 = q.a.a(r7)     // Catch: java.lang.Throwable -> L5f
            goto L4f
        L22:
            java.lang.Class<android.telephony.TelephonyManager> r1 = android.telephony.TelephonyManager.class
            java.lang.String r2 = "getServiceStateForSubscriber"
            r3 = 1
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch: java.lang.Throwable -> L44
            java.lang.Class r5 = java.lang.Integer.TYPE     // Catch: java.lang.Throwable -> L44
            r6 = 0
            r4[r6] = r5     // Catch: java.lang.Throwable -> L44
            java.lang.reflect.Method r1 = r1.getDeclaredMethod(r2, r4)     // Catch: java.lang.Throwable -> L44
            r1.setAccessible(r3)     // Catch: java.lang.Throwable -> L44
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L44
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Throwable -> L44
            r2[r6] = r0     // Catch: java.lang.Throwable -> L44
            java.lang.Object r0 = r1.invoke(r7, r2)     // Catch: java.lang.Throwable -> L44
            android.telephony.ServiceState r0 = (android.telephony.ServiceState) r0     // Catch: java.lang.Throwable -> L44
            goto L49
        L44:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L5f
            r0 = 0
        L49:
            if (r0 != 0) goto L50
            android.telephony.ServiceState r7 = q.a.a(r7)     // Catch: java.lang.Throwable -> L5f
        L4f:
            r0 = r7
        L50:
            if (r0 == 0) goto L63
            java.lang.String r7 = r0.toString()     // Catch: java.lang.Throwable -> L5f
            boolean r7 = b(r7)     // Catch: java.lang.Throwable -> L5f
            if (r7 == 0) goto L63
            r8 = 20
            goto L63
        L5f:
            r7 = move-exception
            r7.printStackTrace()
        L63:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: c.e.a(android.content.Context, int):int");
    }
}
