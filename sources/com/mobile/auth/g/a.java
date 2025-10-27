package com.mobile.auth.g;

import android.text.TextUtils;
import android.util.Log;
import com.mobile.auth.i.d;
import com.mobile.auth.i.g;
import com.mobile.auth.j.c;
import com.mobile.auth.l.q;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.util.security.Constraint;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private String f9752a;

    /* renamed from: b, reason: collision with root package name */
    private String f9753b;

    private c a(String str, String str2, String str3, g gVar) {
        c cVar = new c(str, gVar, str3, str2);
        if (str3.equals("GET")) {
            cVar.a("Content-Type", "application/x-www-form-urlencoded");
        }
        return cVar;
    }

    public c a(c cVar, com.mobile.auth.k.b bVar, com.cmic.sso.sdk.a aVar) {
        List<String> list;
        Map<String, List<String>> mapB = bVar.b();
        if (TextUtils.isEmpty(this.f9752a) && (list = mapB.get("pplocation")) != null && list.size() > 0) {
            this.f9752a = list.get(0);
        }
        q.b(aVar, String.valueOf(bVar.a()));
        List<String> list2 = mapB.get("Location");
        if (list2 == null || list2.isEmpty()) {
            list2 = mapB.get("Location".toLowerCase());
        }
        if (list2 != null && list2.size() > 0) {
            String str = list2.get(0);
            this.f9753b = str;
            if (!TextUtils.isEmpty(str)) {
                String strB = aVar.b("operatortype", "0");
                q.a(aVar, "2".equals(strB) ? "getUnicomMobile" : "3".equals(strB) ? "getTelecomMobile" : Constraint.NONE);
            }
        }
        Log.d("Location", this.f9753b);
        c cVarA = a(this.f9753b, cVar.f(), "GET", new com.mobile.auth.i.c(cVar.k().a()));
        cVarA.a(cVar.h());
        return cVarA;
    }

    public String a() {
        return this.f9752a;
    }

    public c b(c cVar, com.mobile.auth.k.b bVar, com.cmic.sso.sdk.a aVar) {
        String strB = aVar.b("operatortype", "0");
        q.a(aVar, "2".equals(strB) ? "getNewUnicomPhoneNumberNotify" : "3".equals(strB) ? "getNewTelecomPhoneNumberNotify" : Constraint.NONE);
        q.b(aVar, String.valueOf(bVar.a()));
        d dVar = new d(cVar.k().a(), "1.0", bVar.c());
        dVar.c(aVar.b("userCapaid"));
        dVar.b(aVar.c("logintype") != 3 ? "authz" : "pre");
        c cVarA = a(this.f9752a, cVar.f(), "POST", dVar);
        cVarA.a(cVar.h());
        this.f9752a = null;
        return cVarA;
    }
}
