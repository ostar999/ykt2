package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.service.at;
import com.yikaobang.yixue.R2;
import java.util.Locale;

/* loaded from: classes6.dex */
public class s {

    /* renamed from: a, reason: collision with root package name */
    public final int f25712a;

    /* renamed from: a, reason: collision with other field name */
    public final String f1090a;

    /* renamed from: b, reason: collision with root package name */
    public final String f25713b;

    /* renamed from: c, reason: collision with root package name */
    public final String f25714c;

    /* renamed from: d, reason: collision with root package name */
    public final String f25715d;

    /* renamed from: e, reason: collision with root package name */
    public final String f25716e;

    /* renamed from: f, reason: collision with root package name */
    public final String f25717f;

    public s(String str, String str2, String str3, String str4, String str5, String str6, int i2) {
        this.f1090a = str;
        this.f25713b = str2;
        this.f25714c = str3;
        this.f25715d = str4;
        this.f25716e = str5;
        this.f25717f = str6;
        this.f25712a = i2;
    }

    public static boolean a() {
        try {
            return Class.forName("miui.os.Build").getField("IS_ALPHA_BUILD").getBoolean(null);
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean a(Context context) {
        return "com.xiaomi.xmsf".equals(context.getPackageName()) && a();
    }

    private static boolean b(Context context) {
        return context.getPackageName().equals("com.xiaomi.xmsf");
    }

    public at.b a(XMPushService xMPushService) {
        at.b bVar = new at.b(xMPushService);
        a(bVar, xMPushService, xMPushService.b(), com.umeng.analytics.pro.am.aF);
        return bVar;
    }

    public at.b a(at.b bVar, Context context, d dVar, String str) {
        bVar.f1012a = context.getPackageName();
        bVar.f1015b = this.f1090a;
        bVar.f25598h = this.f25714c;
        bVar.f25593c = this.f25713b;
        bVar.f25597g = "5";
        bVar.f25594d = "XMPUSH-PASS";
        bVar.f1014a = false;
        bVar.f25595e = String.format("%1$s:%2$s,%3$s:%4$s,%5$s:%6$s:%7$s:%8$s", "sdk_ver", 38, "cpvn", "3_6_12", "cpvc", Integer.valueOf(R2.styleable.background_selector_bl_focused_activated), "aapn", b(context) ? com.xiaomi.push.g.b(context) : "");
        bVar.f25596f = String.format("%1$s:%2$s,%3$s:%4$s,%5$s:%6$s,sync:1", "appid", b(context) ? "1000271" : this.f25715d, "locale", Locale.getDefault().toString(), Constants.EXTRA_KEY_MIID, o.a(context).a());
        if (a(context)) {
            bVar.f25596f += String.format(",%1$s:%2$s", "ab", str);
        }
        bVar.f1011a = dVar;
        return bVar;
    }
}
