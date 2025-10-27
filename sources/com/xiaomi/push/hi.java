package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.al;
import java.util.ArrayList;

/* loaded from: classes6.dex */
final class hi extends al.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f25060a;

    public hi(Context context) {
        this.f25060a = context;
    }

    @Override // com.xiaomi.push.al.b
    public void b() {
        ArrayList arrayList;
        synchronized (hh.f522a) {
            arrayList = new ArrayList(hh.f524a);
            hh.f524a.clear();
        }
        hh.b(this.f25060a, arrayList);
    }
}
