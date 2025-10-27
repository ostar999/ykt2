package com.vivo.push.util;

import android.content.Context;
import java.util.HashMap;

/* loaded from: classes6.dex */
public final class y implements d {

    /* renamed from: a, reason: collision with root package name */
    private static final HashMap<String, Integer> f24477a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private static final HashMap<String, Long> f24478b = new HashMap<>();

    /* renamed from: c, reason: collision with root package name */
    private static final HashMap<String, String> f24479c = new HashMap<>();

    /* renamed from: d, reason: collision with root package name */
    private static y f24480d;

    /* renamed from: e, reason: collision with root package name */
    private Context f24481e;

    /* renamed from: f, reason: collision with root package name */
    private d f24482f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f24483g;

    private y(Context context) {
        this.f24483g = false;
        this.f24481e = context;
        this.f24483g = a(context);
        p.d("SystemCache", "init status is " + this.f24483g + ";  curCache is " + this.f24482f);
    }

    public static synchronized y b(Context context) {
        if (f24480d == null) {
            f24480d = new y(context.getApplicationContext());
        }
        return f24480d;
    }

    public final void a() {
        x xVar = new x();
        if (xVar.a(this.f24481e)) {
            xVar.a();
            p.d("SystemCache", "sp cache is cleared");
        }
    }

    @Override // com.vivo.push.util.d
    public final void b(String str, String str2) {
        d dVar;
        f24479c.put(str, str2);
        if (!this.f24483g || (dVar = this.f24482f) == null) {
            return;
        }
        dVar.b(str, str2);
    }

    @Override // com.vivo.push.util.d
    public final boolean a(Context context) {
        v vVar = new v();
        this.f24482f = vVar;
        boolean zA = vVar.a(context);
        if (!zA) {
            x xVar = new x();
            this.f24482f = xVar;
            zA = xVar.a(context);
        }
        if (!zA) {
            this.f24482f = null;
        }
        return zA;
    }

    @Override // com.vivo.push.util.d
    public final String a(String str, String str2) {
        d dVar;
        String str3 = f24479c.get(str);
        return (str3 != null || (dVar = this.f24482f) == null) ? str3 : dVar.a(str, str2);
    }
}
