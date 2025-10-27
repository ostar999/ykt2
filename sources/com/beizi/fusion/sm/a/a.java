package com.beizi.fusion.sm.a;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.beizi.fusion.sm.a.a.l;

/* loaded from: classes2.dex */
public final class a implements c {

    /* renamed from: a, reason: collision with root package name */
    private Application f5279a;

    /* renamed from: b, reason: collision with root package name */
    private String f5280b;

    /* renamed from: com.beizi.fusion.sm.a.a$a, reason: collision with other inner class name */
    public static class C0071a {

        /* renamed from: a, reason: collision with root package name */
        static final a f5281a = new a();
    }

    public static void a(Application application) {
        if (application == null) {
            return;
        }
        a aVar = C0071a.f5281a;
        aVar.f5279a = application;
        a(application, aVar);
    }

    @Override // com.beizi.fusion.sm.a.c
    public void a(Exception exc) {
    }

    private a() {
    }

    public static void a(Context context, c cVar) {
        l.a(context).a(cVar);
    }

    public static boolean a(Context context) {
        return l.a(context).a();
    }

    @Override // com.beizi.fusion.sm.a.c
    public void a(String str) {
        if (TextUtils.isEmpty(str)) {
            a(new e("OAID is empty"));
        } else {
            this.f5280b = str;
        }
    }
}
