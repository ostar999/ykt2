package com.xiaomi.push;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.aliyun.vod.log.core.AliyunLogCommon;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jetty.http.HttpHeaderValues;

/* loaded from: classes6.dex */
public class hh {

    /* renamed from: a, reason: collision with other field name */
    private static al f520a = new al(true);

    /* renamed from: a, reason: collision with root package name */
    private static int f25057a = -1;

    /* renamed from: a, reason: collision with other field name */
    private static long f519a = System.currentTimeMillis();

    /* renamed from: a, reason: collision with other field name */
    private static final Object f522a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private static List<a> f524a = Collections.synchronizedList(new ArrayList());

    /* renamed from: a, reason: collision with other field name */
    private static String f523a = "";

    /* renamed from: a, reason: collision with other field name */
    private static com.xiaomi.push.providers.a f521a = null;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public int f25058a;

        /* renamed from: a, reason: collision with other field name */
        public long f525a;

        /* renamed from: a, reason: collision with other field name */
        public String f526a;

        /* renamed from: b, reason: collision with root package name */
        public int f25059b;

        /* renamed from: b, reason: collision with other field name */
        public long f527b;

        /* renamed from: b, reason: collision with other field name */
        public String f528b;

        public a(String str, long j2, int i2, int i3, String str2, long j3) {
            this.f526a = str;
            this.f525a = j2;
            this.f25058a = i2;
            this.f25059b = i3;
            this.f528b = str2;
            this.f527b = j3;
        }

        public boolean a(a aVar) {
            return TextUtils.equals(aVar.f526a, this.f526a) && TextUtils.equals(aVar.f528b, this.f528b) && aVar.f25058a == this.f25058a && aVar.f25059b == this.f25059b && Math.abs(aVar.f525a - this.f525a) <= 5000;
        }
    }

    public static int a(Context context) {
        if (f25057a == -1) {
            f25057a = b(context);
        }
        return f25057a;
    }

    public static int a(String str) {
        try {
            return str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes().length;
        }
    }

    private static long a(int i2, long j2, boolean z2, long j3, boolean z3) {
        if (z2 && z3) {
            long j4 = f519a;
            f519a = j3;
            if (j3 - j4 > 30000 && j2 > 1024) {
                return j2 * 2;
            }
        }
        return (j2 * (i2 == 0 ? 13 : 11)) / 10;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static com.xiaomi.push.providers.a m481a(Context context) {
        com.xiaomi.push.providers.a aVar = f521a;
        if (aVar != null) {
            return aVar;
        }
        com.xiaomi.push.providers.a aVar2 = new com.xiaomi.push.providers.a(context);
        f521a = aVar2;
        return aVar2;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static synchronized String m482a(Context context) {
        if (n.d()) {
            return "";
        }
        if (!TextUtils.isEmpty(f523a)) {
            return f523a;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
            if (telephonyManager != null) {
                f523a = telephonyManager.getSubscriberId();
            }
        } catch (Exception unused) {
        }
        return f523a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m484a(Context context) {
        f25057a = b(context);
    }

    private static void a(Context context, String str, long j2, boolean z2, long j3) {
        int iA;
        boolean zIsEmpty;
        if (context == null || TextUtils.isEmpty(str) || !"com.xiaomi.xmsf".equals(context.getPackageName()) || "com.xiaomi.xmsf".equals(str) || -1 == (iA = a(context))) {
            return;
        }
        synchronized (f522a) {
            zIsEmpty = f524a.isEmpty();
            a(new a(str, j3, iA, z2 ? 1 : 0, iA == 0 ? m482a(context) : "", j2));
        }
        if (zIsEmpty) {
            f520a.a(new hi(context), 5000L);
        }
    }

    public static void a(Context context, String str, long j2, boolean z2, boolean z3, long j3) {
        a(context, str, a(a(context), j2, z2, j3, z3), z2, j3);
    }

    private static void a(a aVar) {
        for (a aVar2 : f524a) {
            if (aVar2.a(aVar)) {
                aVar2.f527b += aVar.f527b;
                return;
            }
        }
        f524a.add(aVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static synchronized void m485a(String str) {
        if (!n.d() && !TextUtils.isEmpty(str)) {
            f523a = str;
        }
    }

    private static int b(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return -1;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return -1;
            }
            return activeNetworkInfo.getType();
        } catch (Exception unused) {
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, List<a> list) {
        try {
            synchronized (com.xiaomi.push.providers.a.f940a) {
                SQLiteDatabase writableDatabase = m481a(context).getWritableDatabase();
                writableDatabase.beginTransaction();
                try {
                    for (a aVar : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("package_name", aVar.f526a);
                        contentValues.put("message_ts", Long.valueOf(aVar.f525a));
                        contentValues.put(com.umeng.analytics.pro.am.T, Integer.valueOf(aVar.f25058a));
                        contentValues.put(HttpHeaderValues.BYTES, Long.valueOf(aVar.f527b));
                        contentValues.put("rcv", Integer.valueOf(aVar.f25059b));
                        contentValues.put("imsi", aVar.f528b);
                        writableDatabase.insert(com.umeng.analytics.pro.d.F, null, contentValues);
                    }
                    writableDatabase.setTransactionSuccessful();
                } finally {
                    writableDatabase.endTransaction();
                }
            }
        } catch (SQLiteException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }
}
