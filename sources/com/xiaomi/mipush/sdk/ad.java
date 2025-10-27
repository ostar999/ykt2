package com.xiaomi.mipush.sdk;

import android.text.TextUtils;

/* loaded from: classes6.dex */
class ad {

    /* renamed from: a, reason: collision with root package name */
    int f24517a = 0;

    /* renamed from: a, reason: collision with other field name */
    String f121a = "";

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ad)) {
            return false;
        }
        ad adVar = (ad) obj;
        return !TextUtils.isEmpty(adVar.f121a) && adVar.f121a.equals(this.f121a);
    }
}
