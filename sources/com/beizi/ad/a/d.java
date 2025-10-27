package com.beizi.ad.a;

import android.content.Context;
import com.beizi.ad.a.a.i;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: c, reason: collision with root package name */
    private static d f3713c;

    /* renamed from: a, reason: collision with root package name */
    public e f3714a;

    /* renamed from: b, reason: collision with root package name */
    private String f3715b = "OnLineState";

    private d(Context context) {
        if (context == null) {
            i.c("OnLineState", "OnLineState init failed,because context cann't be null ");
            return;
        }
        e eVar = new e();
        this.f3714a = eVar;
        eVar.a(context);
    }

    public static d a(Context context) {
        if (f3713c == null) {
            synchronized (d.class) {
                if (f3713c == null) {
                    f3713c = new d(context);
                }
            }
        }
        return f3713c;
    }

    public void a(c cVar) {
        e eVar = this.f3714a;
        if (eVar != null) {
            eVar.a(cVar);
        } else {
            i.a(this.f3715b, "please init OnLineState first ,you can init it with 'OnLineState.init(context);' in you BaseApplication ");
        }
    }
}
