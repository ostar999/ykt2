package com.vivo.push.c;

import android.content.Context;
import com.vivo.push.util.ContextDelegate;

/* loaded from: classes6.dex */
public class d {

    /* renamed from: d, reason: collision with root package name */
    private static volatile d f24288d;

    /* renamed from: a, reason: collision with root package name */
    private b f24289a;

    /* renamed from: b, reason: collision with root package name */
    private c f24290b;

    /* renamed from: c, reason: collision with root package name */
    private Context f24291c;

    private d(Context context) {
        if (this.f24289a == null) {
            this.f24291c = ContextDelegate.getContext(context.getApplicationContext());
            this.f24289a = new e(this.f24291c);
        }
        if (this.f24290b == null) {
            this.f24290b = new a();
        }
    }

    public static d a(Context context) {
        if (f24288d == null) {
            synchronized (d.class) {
                if (f24288d == null && context != null) {
                    f24288d = new d(context);
                }
            }
        }
        return f24288d;
    }

    public final b a() {
        return this.f24289a;
    }
}
