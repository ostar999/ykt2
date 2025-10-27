package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class hu {

    /* renamed from: a, reason: collision with root package name */
    private static hu f25080a;

    /* renamed from: a, reason: collision with other field name */
    private final Context f548a;

    /* renamed from: a, reason: collision with other field name */
    private Map<String, hv> f549a = new HashMap();

    private hu(Context context) {
        this.f548a = context;
    }

    public static hu a(Context context) {
        if (context == null) {
            com.xiaomi.channel.commonutils.logger.b.d("[TinyDataManager]:mContext is null, TinyDataManager.getInstance(Context) failed.");
            return null;
        }
        if (f25080a == null) {
            synchronized (hu.class) {
                if (f25080a == null) {
                    f25080a = new hu(context);
                }
            }
        }
        return f25080a;
    }

    private boolean a(String str, String str2, String str3, String str4, long j2, String str5) {
        ib ibVar = new ib();
        ibVar.d(str3);
        ibVar.c(str4);
        ibVar.a(j2);
        ibVar.b(str5);
        ibVar.a(true);
        ibVar.a("push_sdk_channel");
        ibVar.e(str2);
        com.xiaomi.channel.commonutils.logger.b.m117a("TinyData TinyDataManager.upload item:" + ibVar.d() + "   ts:" + System.currentTimeMillis());
        return a(ibVar, str);
    }

    public hv a() {
        hv hvVar = this.f549a.get("UPLOADER_PUSH_CHANNEL");
        if (hvVar != null) {
            return hvVar;
        }
        hv hvVar2 = this.f549a.get("UPLOADER_HTTP");
        if (hvVar2 != null) {
            return hvVar2;
        }
        return null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public Map<String, hv> m496a() {
        return this.f549a;
    }

    public void a(hv hvVar, String str) {
        if (hvVar == null) {
            com.xiaomi.channel.commonutils.logger.b.d("[TinyDataManager]: please do not add null mUploader to TinyDataManager.");
        } else if (TextUtils.isEmpty(str)) {
            com.xiaomi.channel.commonutils.logger.b.d("[TinyDataManager]: can not add a provider from unkown resource.");
        } else {
            m496a().put(str, hvVar);
        }
    }

    public boolean a(ib ibVar, String str) {
        if (TextUtils.isEmpty(str)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("pkgName is null or empty, upload ClientUploadDataItem failed.");
            return false;
        }
        if (com.xiaomi.push.service.bl.a(ibVar, false)) {
            return false;
        }
        if (TextUtils.isEmpty(ibVar.d())) {
            ibVar.f(com.xiaomi.push.service.bl.a());
        }
        ibVar.g(str);
        com.xiaomi.push.service.bm.a(this.f548a, ibVar);
        return true;
    }

    public boolean a(String str, String str2, long j2, String str3) {
        return a(this.f548a.getPackageName(), this.f548a.getPackageName(), str, str2, j2, str3);
    }
}
