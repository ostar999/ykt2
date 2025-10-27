package com.tencent.smtt.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.tbs.video.interfaces.IUserStateChangedListener;

/* loaded from: classes6.dex */
class r {

    /* renamed from: e, reason: collision with root package name */
    private static r f21264e;

    /* renamed from: a, reason: collision with root package name */
    t f21265a;

    /* renamed from: b, reason: collision with root package name */
    Context f21266b;

    /* renamed from: c, reason: collision with root package name */
    com.tencent.tbs.video.interfaces.a f21267c;

    /* renamed from: d, reason: collision with root package name */
    IUserStateChangedListener f21268d;

    private r(Context context) {
        this.f21265a = null;
        this.f21266b = context.getApplicationContext();
        this.f21265a = new t(this.f21266b);
    }

    public static synchronized r a(Context context) {
        if (f21264e == null) {
            f21264e = new r(context);
        }
        return f21264e;
    }

    public void a(int i2, int i3, Intent intent) {
        com.tencent.tbs.video.interfaces.a aVar = this.f21267c;
        if (aVar != null) {
            aVar.a(i2, i3, intent);
        }
    }

    public void a(Activity activity, int i2) {
        this.f21265a.a(activity, i2);
    }

    public boolean a() {
        this.f21265a.a();
        return this.f21265a.b();
    }

    public boolean a(String str, Bundle bundle, com.tencent.tbs.video.interfaces.a aVar) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (!TextUtils.isEmpty(str)) {
            bundle.putString("videoUrl", str);
        }
        if (aVar != null) {
            this.f21265a.a();
            if (!this.f21265a.b()) {
                return false;
            }
            this.f21267c = aVar;
            IUserStateChangedListener iUserStateChangedListener = new IUserStateChangedListener() { // from class: com.tencent.smtt.sdk.r.1
                @Override // com.tencent.tbs.video.interfaces.IUserStateChangedListener
                public void onUserStateChanged() {
                    r.this.f21265a.c();
                }
            };
            this.f21268d = iUserStateChangedListener;
            this.f21267c.a(iUserStateChangedListener);
            bundle.putInt("callMode", 3);
        } else {
            bundle.putInt("callMode", 1);
        }
        this.f21265a.a(bundle, aVar == null ? null : this);
        return true;
    }
}
