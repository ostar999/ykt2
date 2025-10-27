package com.xiaomi.push;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.aliyun.vod.common.utils.UriUtil;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class be {
    public static int a(Context context) {
        if (!com.xiaomi.clientreport.manager.a.a(context).a().isPerfUploadSwitchOpen()) {
            return -1;
        }
        int perfUploadFrequency = (int) com.xiaomi.clientreport.manager.a.a(context).a().getPerfUploadFrequency();
        int iCurrentTimeMillis = (int) ((System.currentTimeMillis() - bh.a(context).a("sp_client_report_status", "perf_last_upload_time", 0L)) / 1000);
        com.xiaomi.channel.commonutils.logger.b.c(context.getPackageName() + " start perf upload timeDiff " + iCurrentTimeMillis);
        if (iCurrentTimeMillis >= perfUploadFrequency - 5) {
            return 0;
        }
        return iCurrentTimeMillis;
    }

    public static String a() {
        return Build.VERSION.RELEASE + "-" + Build.VERSION.INCREMENTAL;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m227a(Context context) {
        String strA = bh.a(context).a("sp_client_report_status", "sp_client_report_key", "");
        if (!TextUtils.isEmpty(strA)) {
            return strA;
        }
        String strA2 = ay.a(20);
        bh.a(context).m232a("sp_client_report_status", "sp_client_report_key", strA2);
        return strA2;
    }

    public static void a(Context context, String str) {
        Intent intent = new Intent("com.xiaomi.xmsf.push.XMSF_UPLOAD_ACTIVE");
        intent.putExtra("pkgname", context.getPackageName());
        intent.putExtra(UriUtil.QUERY_CATEGORY, "category_client_report_data");
        intent.putExtra("name", "quality_support");
        intent.putExtra("data", str);
        context.sendBroadcast(intent, "com.xiaomi.xmsf.permission.USE_XMSF_UPLOAD");
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0122 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x011f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(android.content.Context r11, java.lang.String r12, java.lang.String r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 295
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.be.a(android.content.Context, java.lang.String, java.lang.String):void");
    }

    public static void a(Context context, List<String> list) {
        if (list == null || list.size() <= 0 || !m228a(context)) {
            return;
        }
        for (String str : list) {
            if (!TextUtils.isEmpty(str)) {
                a(context, str);
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m228a(Context context) {
        try {
            return context.getApplicationContext().getPackageManager().getPackageInfo("com.xiaomi.xmsf", 0).versionCode >= 108;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m229a(Context context, String str) {
        File file = new File(str);
        long maxFileLength = com.xiaomi.clientreport.manager.a.a(context).a().getMaxFileLength();
        if (!file.exists()) {
            y.m774a(file);
            return true;
        }
        try {
            if (file.length() <= maxFileLength) {
                return true;
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
        return false;
    }

    @TargetApi(9)
    public static byte[] a(String str) {
        byte[] bArrCopyOf = Arrays.copyOf(av.a(str), 16);
        bArrCopyOf[0] = 68;
        bArrCopyOf[15] = 84;
        return bArrCopyOf;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static File[] m230a(Context context, String str) {
        File externalFilesDir = context.getExternalFilesDir(str);
        if (externalFilesDir != null) {
            return externalFilesDir.listFiles(new bg());
        }
        return null;
    }

    public static int b(Context context) {
        if (!com.xiaomi.clientreport.manager.a.a(context).a().isEventUploadSwitchOpen()) {
            return -1;
        }
        int eventUploadFrequency = (int) com.xiaomi.clientreport.manager.a.a(context).a().getEventUploadFrequency();
        int iCurrentTimeMillis = (int) ((System.currentTimeMillis() - bh.a(context).a("sp_client_report_status", "event_last_upload_time", 0L)) / 1000);
        com.xiaomi.channel.commonutils.logger.b.c(context.getPackageName() + " start event upload timeDiff " + iCurrentTimeMillis);
        if (iCurrentTimeMillis >= eventUploadFrequency - 5) {
            return 0;
        }
        return iCurrentTimeMillis;
    }
}
