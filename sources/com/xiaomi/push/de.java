package com.xiaomi.push;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
class de extends cy {

    /* renamed from: a, reason: collision with root package name */
    cy f24722a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ dc f306a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ cy f24723b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public de(dc dcVar, String str, cy cyVar) {
        super(str);
        this.f306a = dcVar;
        this.f24723b = cyVar;
        this.f24722a = cyVar;
        ((cy) this).f292b = ((cy) this).f292b;
        if (cyVar != null) {
            this.f24710f = cyVar.f24710f;
        }
    }

    @Override // com.xiaomi.push.cy
    public synchronized ArrayList<String> a(boolean z2) {
        ArrayList<String> arrayList;
        arrayList = new ArrayList<>();
        cy cyVar = this.f24722a;
        if (cyVar != null) {
            arrayList.addAll(cyVar.a(true));
        }
        synchronized (dc.f24718b) {
            cy cyVar2 = dc.f24718b.get(((cy) this).f292b);
            if (cyVar2 != null) {
                Iterator<String> it = cyVar2.a(true).iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    if (arrayList.indexOf(next) == -1) {
                        arrayList.add(next);
                    }
                }
                arrayList.remove(((cy) this).f292b);
                arrayList.add(((cy) this).f292b);
            }
        }
        return arrayList;
    }

    @Override // com.xiaomi.push.cy
    public synchronized void a(String str, cx cxVar) {
        cy cyVar = this.f24722a;
        if (cyVar != null) {
            cyVar.a(str, cxVar);
        }
    }

    @Override // com.xiaomi.push.cy
    public boolean b() {
        return false;
    }
}
