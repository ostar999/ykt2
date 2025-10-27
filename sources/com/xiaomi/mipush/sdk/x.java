package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.push.ct;
import com.xiaomi.push.ii;
import com.xiaomi.push.ik;

/* loaded from: classes6.dex */
public class x {

    /* renamed from: a, reason: collision with other field name */
    private Context f169a;

    /* renamed from: a, reason: collision with other field name */
    private ct f170a;

    /* renamed from: a, reason: collision with other field name */
    private final int f168a = -1;

    /* renamed from: a, reason: collision with root package name */
    private final double f24583a = 0.0d;

    public x(Context context) {
        this.f169a = context;
        a();
    }

    private void a() {
        this.f170a = new ct(this.f169a);
    }

    public void a(String str) {
        this.f170a.a(this.f169a, "com.xiaomi.xmsf", str);
    }

    public boolean a(ii iiVar) {
        if (iiVar == null) {
            return false;
        }
        if (iiVar.m519a() == null || iiVar.a() <= 0.0d) {
            return true;
        }
        ik ikVarM519a = iiVar.m519a();
        this.f170a.a(this.f169a, ikVarM519a.b(), ikVarM519a.a(), (float) iiVar.a(), -1L, "com.xiaomi.xmsf", iiVar.m520a(), iiVar.m517a().name());
        return true;
    }
}
