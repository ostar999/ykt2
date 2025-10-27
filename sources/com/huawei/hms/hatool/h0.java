package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import java.util.HashMap;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class h0 {

    /* renamed from: b, reason: collision with root package name */
    public static h0 f7738b;

    /* renamed from: a, reason: collision with root package name */
    public Context f7739a;

    static {
        new HashMap();
    }

    public static h0 a() {
        return b();
    }

    public static synchronized h0 b() {
        if (f7738b == null) {
            f7738b = new h0();
        }
        return f7738b;
    }

    public void a(Context context) {
        this.f7739a = context;
        b(context);
        i.c().b().h(f.a());
    }

    public void a(String str, int i2) {
        if (this.f7739a == null) {
            y.e("hmsSdk", "onReport() null context or SDK was not init.");
        } else {
            y.c("hmsSdk", "onReport: Before calling runtaskhandler()");
            a(str, u0.a(i2), b.g());
        }
    }

    public void a(String str, int i2, String str2, JSONObject jSONObject) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (2 == i2) {
            jCurrentTimeMillis = u0.a("yyyy-MM-dd", jCurrentTimeMillis);
        }
        o0.c().a(new j0(str2, jSONObject, str, u0.a(i2), jCurrentTimeMillis));
    }

    public void a(String str, String str2) {
        if (!c.a(str, str2)) {
            y.c("hmsSdk", "auto report is closed tag:" + str);
            return;
        }
        long j2 = c.j(str, str2);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - j2 <= 30000) {
            y.f("hmsSdk", "autoReport timeout. interval < 30s ");
            return;
        }
        y.a("hmsSdk", "begin to call onReport!");
        c.a(str, str2, jCurrentTimeMillis);
        a(str, str2, b.g());
    }

    public void a(String str, String str2, String str3) {
        Context context = this.f7739a;
        if (context == null) {
            y.e("hmsSdk", "onReport() null context or SDK was not init.");
            return;
        }
        String strA = h.a(context);
        if (c.e(str, str2) && !"WIFI".equals(strA)) {
            y.c("hmsSdk", "strNetworkType is :" + strA);
            return;
        }
        if (TextUtils.isEmpty(strA) || "2G".equals(strA)) {
            y.e("hmsSdk", "The network is bad.");
        } else {
            o0.c().a(new k0(str, str2, str3));
        }
    }

    public final void b(Context context) {
        String str;
        String strD = f.d(context);
        b.a(strD);
        if (w0.b().a()) {
            String strA = g0.a(context, "global_v2", Constants.PARAM_APP_VER, "");
            g0.b(context, "global_v2", Constants.PARAM_APP_VER, strD);
            b.b(strA);
            if (!TextUtils.isEmpty(strA)) {
                if (strA.equals(strD)) {
                    return;
                }
                y.c("hmsSdk", "the appVers are different!");
                a().a("", "alltype", strA);
                return;
            }
            str = "app ver is first save!";
        } else {
            str = "userManager.isUserUnlocked() == false";
        }
        y.c("hmsSdk", str);
    }
}
