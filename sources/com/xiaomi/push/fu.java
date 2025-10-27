package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.push.et;
import com.xiaomi.push.service.at;
import java.util.HashMap;

/* loaded from: classes6.dex */
class fu {
    public static void a(at.b bVar, String str, gc gcVar) throws NumberFormatException {
        String strA;
        et.c cVar = new et.c();
        if (!TextUtils.isEmpty(bVar.f25593c)) {
            cVar.a(bVar.f25593c);
        }
        if (!TextUtils.isEmpty(bVar.f25595e)) {
            cVar.d(bVar.f25595e);
        }
        if (!TextUtils.isEmpty(bVar.f25596f)) {
            cVar.e(bVar.f25596f);
        }
        cVar.b(bVar.f1014a ? "1" : "0");
        if (TextUtils.isEmpty(bVar.f25594d)) {
            cVar.c("XIAOMI-SASL");
        } else {
            cVar.c(bVar.f25594d);
        }
        fv fvVar = new fv();
        fvVar.c(bVar.f1015b);
        fvVar.a(Integer.parseInt(bVar.f25597g));
        fvVar.b(bVar.f1012a);
        fvVar.a("BIND", (String) null);
        fvVar.a(fvVar.e());
        com.xiaomi.channel.commonutils.logger.b.m117a("[Slim]: bind id=" + fvVar.e());
        HashMap map = new HashMap();
        map.put("challenge", str);
        map.put("token", bVar.f25593c);
        map.put("chid", bVar.f25597g);
        map.put("from", bVar.f1015b);
        map.put("id", fvVar.e());
        map.put("to", "xiaomi.com");
        if (bVar.f1014a) {
            map.put("kick", "1");
        } else {
            map.put("kick", "0");
        }
        if (TextUtils.isEmpty(bVar.f25595e)) {
            map.put("client_attrs", "");
        } else {
            map.put("client_attrs", bVar.f25595e);
        }
        if (TextUtils.isEmpty(bVar.f25596f)) {
            map.put("cloud_attrs", "");
        } else {
            map.put("cloud_attrs", bVar.f25596f);
        }
        if (bVar.f25594d.equals("XIAOMI-PASS") || bVar.f25594d.equals("XMPUSH-PASS")) {
            strA = aw.a(bVar.f25594d, null, map, bVar.f25598h);
        } else {
            bVar.f25594d.equals("XIAOMI-SASL");
            strA = null;
        }
        cVar.f(strA);
        fvVar.a(cVar.m334a(), (String) null);
        gcVar.b(fvVar);
    }

    public static void a(String str, String str2, gc gcVar) throws NumberFormatException {
        fv fvVar = new fv();
        fvVar.c(str2);
        fvVar.a(Integer.parseInt(str));
        fvVar.a("UBND", (String) null);
        gcVar.b(fvVar);
    }
}
