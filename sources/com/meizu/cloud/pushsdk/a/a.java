package com.meizu.cloud.pushsdk.a;

import android.content.Context;
import com.meizu.cloud.pushsdk.a.c;
import com.meizu.cloud.pushsdk.base.f;
import java.util.Map;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8914a = "a";

    public static com.meizu.cloud.pushsdk.c.a.c a(Context context) {
        com.meizu.cloud.pushsdk.c.a.c cVar = new com.meizu.cloud.pushsdk.c.a.c();
        c cVarC = c(context);
        Map<String, String> mapA = cVarC.a();
        Map<String, String> mapB = cVarC.b();
        Map<String, Object> mapC = cVarC.c();
        Map<String, Object> mapD = cVarC.d();
        if (mapA.size() > 0) {
            cVar.a(b.f8936a, mapA);
        }
        if (mapB.size() > 0) {
            cVar.a(b.f8941f, mapB);
        }
        if (mapC.size() > 0) {
            cVar.a(b.f8950o, mapC);
        }
        if (mapD.size() > 0) {
            cVar.a(b.f8957v, mapD);
        }
        return cVar;
    }

    public static void b(final Context context) {
        f.a().execute(new Runnable() { // from class: com.meizu.cloud.pushsdk.a.a.1
            @Override // java.lang.Runnable
            public void run() {
                com.meizu.cloud.pushsdk.a.a.b.a(context).a("POST", null, a.a(context).toString());
            }
        });
    }

    private static c c(Context context) {
        return new c.a().a(context).a();
    }
}
