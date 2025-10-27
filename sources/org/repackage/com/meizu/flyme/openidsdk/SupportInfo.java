package org.repackage.com.meizu.flyme.openidsdk;

import android.text.TextUtils;

/* loaded from: classes9.dex */
class SupportInfo {

    /* renamed from: a, reason: collision with root package name */
    String f28000a;

    /* renamed from: b, reason: collision with root package name */
    Boolean f28001b;

    public void a(boolean z2) {
        this.f28001b = Boolean.valueOf(z2);
    }

    public boolean a() {
        return this.f28001b != null;
    }

    public boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return TextUtils.equals(this.f28000a, str);
    }

    public void b(String str) {
        this.f28000a = str;
    }

    public boolean b() {
        Boolean bool = this.f28001b;
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }
}
