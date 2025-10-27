package com.tencent.liteav.basic.license;

import android.content.Context;

/* loaded from: classes6.dex */
public class g implements e {

    /* renamed from: a, reason: collision with root package name */
    private Context f18470a;

    public g(Context context) {
        this.f18470a = context;
    }

    @Override // com.tencent.liteav.basic.license.e
    public boolean a() {
        if (this.f18470a != null) {
            LicenceCheck.a().a((f) null, this.f18470a);
        }
        return LicenceCheck.a().b() >= 1;
    }
}
