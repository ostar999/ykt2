package com.huawei.hms.hatool;

import android.os.Build;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class v {
    public static e1 a(String str, String str2) {
        e1 e1Var = new e1();
        e1Var.a(z0.a().a(str, str2));
        return e1Var;
    }

    public static f1 a(String str, String str2, String str3, String str4) {
        f1 f1Var = new f1();
        f1Var.a(str);
        f1Var.b(b.f());
        f1Var.e(str2);
        f1Var.c(str4);
        StringBuffer stringBuffer = new StringBuffer("hmshi");
        stringBuffer.append(str3);
        stringBuffer.append("qrt");
        f1Var.d(stringBuffer.toString());
        return f1Var;
    }

    public static g1 a(String str, String str2, String str3) {
        g1 g1Var = new g1();
        g1Var.c(b.j());
        g1Var.e(b.l());
        g1Var.a(str3);
        g1Var.b(z0.a().b(str2, str));
        return g1Var;
    }

    public static C0549r b(String str, String str2) {
        y.c("hmsSdk", "generate UploadData EventModelHandlerBase");
        x.d().a(str, str2);
        if (!TextUtils.isEmpty(x.d().a())) {
            return new C0549r(x.d().c());
        }
        y.f("hmsSdk", "event chifer is empty");
        return null;
    }

    public static Map<String, String> c(String str, String str2) {
        HashMap map = new HashMap();
        map.put("App-Id", b.f());
        map.put("App-Ver", b.g());
        map.put("Sdk-Name", "hianalytics");
        map.put("Sdk-Ver", "2.2.0.313");
        map.put("Device-Type", Build.MODEL);
        map.put("servicetag", str);
        y.a("hmsSdk", "sendData RequestId : %s", str2);
        map.put("Request-Id", str2);
        return map;
    }
}
