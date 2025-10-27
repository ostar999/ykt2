package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import com.catchpig.mvvm.utils.DateUtil;
import com.xiaomi.push.ia;
import com.xiaomi.push.ib;
import com.xiaomi.push.in;
import com.xiaomi.push.je;
import com.xiaomi.push.jp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes6.dex */
public class bl {

    /* renamed from: a, reason: collision with root package name */
    private static String f25658a;

    /* renamed from: a, reason: collision with other field name */
    private static SimpleDateFormat f1044a;

    /* renamed from: a, reason: collision with other field name */
    private static AtomicLong f1045a = new AtomicLong(0);

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.TIME_FROMAT_DAY_WITH_SLASH);
        f1044a = simpleDateFormat;
        f25658a = simpleDateFormat.format(Long.valueOf(System.currentTimeMillis()));
    }

    public static synchronized String a() {
        String str;
        str = f1044a.format(Long.valueOf(System.currentTimeMillis()));
        if (!TextUtils.equals(f25658a, str)) {
            f1045a.set(0L);
            f25658a = str;
        }
        return str + "-" + f1045a.incrementAndGet();
    }

    public static ArrayList<je> a(List<ib> list, String str, String str2, int i2) {
        String str3;
        if (list == null) {
            str3 = "requests can not be null in TinyDataHelper.transToThriftObj().";
        } else {
            if (list.size() != 0) {
                ArrayList<je> arrayList = new ArrayList<>();
                ia iaVar = new ia();
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    ib ibVar = list.get(i4);
                    if (ibVar != null) {
                        int length = jp.a(ibVar).length;
                        if (length > i2) {
                            com.xiaomi.channel.commonutils.logger.b.d("TinyData is too big, ignore upload request item:" + ibVar.d());
                        } else {
                            if (i3 + length > i2) {
                                je jeVar = new je("-1", false);
                                jeVar.d(str);
                                jeVar.b(str2);
                                jeVar.c(in.UploadTinyData.f622a);
                                jeVar.a(com.xiaomi.push.y.a(jp.a(iaVar)));
                                arrayList.add(jeVar);
                                iaVar = new ia();
                                i3 = 0;
                            }
                            iaVar.a(ibVar);
                            i3 += length;
                        }
                    }
                }
                if (iaVar.a() != 0) {
                    je jeVar2 = new je("-1", false);
                    jeVar2.d(str);
                    jeVar2.b(str2);
                    jeVar2.c(in.UploadTinyData.f622a);
                    jeVar2.a(com.xiaomi.push.y.a(jp.a(iaVar)));
                    arrayList.add(jeVar2);
                }
                return arrayList;
            }
            str3 = "requests.length is 0 in TinyDataHelper.transToThriftObj().";
        }
        com.xiaomi.channel.commonutils.logger.b.d(str3);
        return null;
    }

    public static void a(Context context, String str, String str2, long j2, String str3) {
        ib ibVar = new ib();
        ibVar.d(str);
        ibVar.c(str2);
        ibVar.a(j2);
        ibVar.b(str3);
        ibVar.a("push_sdk_channel");
        ibVar.g(context.getPackageName());
        ibVar.e(context.getPackageName());
        ibVar.a(true);
        ibVar.b(System.currentTimeMillis());
        ibVar.f(a());
        bm.a(context, ibVar);
    }

    public static boolean a(ib ibVar, boolean z2) {
        String str;
        if (ibVar == null) {
            str = "item is null, verfiy ClientUploadDataItem failed.";
        } else if (!z2 && TextUtils.isEmpty(ibVar.f564a)) {
            str = "item.channel is null or empty, verfiy ClientUploadDataItem failed.";
        } else if (TextUtils.isEmpty(ibVar.f571d)) {
            str = "item.category is null or empty, verfiy ClientUploadDataItem failed.";
        } else if (TextUtils.isEmpty(ibVar.f570c)) {
            str = "item.name is null or empty, verfiy ClientUploadDataItem failed.";
        } else if (!com.xiaomi.push.ay.m209a(ibVar.f571d)) {
            str = "item.category can only contain ascii char, verfiy ClientUploadDataItem failed.";
        } else if (com.xiaomi.push.ay.m209a(ibVar.f570c)) {
            String str2 = ibVar.f569b;
            if (str2 == null || str2.length() <= 10240) {
                return false;
            }
            str = "item.data is too large(" + ibVar.f569b.length() + "), max size for data is 10240 , verfiy ClientUploadDataItem failed.";
        } else {
            str = "item.name can only contain ascii char, verfiy ClientUploadDataItem failed.";
        }
        com.xiaomi.channel.commonutils.logger.b.m117a(str);
        return true;
    }
}
