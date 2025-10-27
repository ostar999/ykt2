package com.umeng.analytics.pro;

import android.content.Context;
import org.repackage.com.zui.opendeviceidlibrary.OpenDeviceId;

/* loaded from: classes6.dex */
public class ae implements z {

    /* renamed from: a, reason: collision with root package name */
    private static final int f22428a = 1;

    /* renamed from: b, reason: collision with root package name */
    private OpenDeviceId f22429b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f22430c = false;

    /* renamed from: d, reason: collision with root package name */
    private boolean f22431d = false;

    @Override // com.umeng.analytics.pro.z
    public String a(Context context) {
        if (context == null) {
            return null;
        }
        if (!this.f22430c) {
            OpenDeviceId openDeviceId = new OpenDeviceId();
            this.f22429b = openDeviceId;
            this.f22431d = openDeviceId.a(context, (OpenDeviceId.CallBack<String>) null) == 1;
            this.f22430c = true;
        }
        al.a("getOAID", "isSupported", Boolean.valueOf(this.f22431d));
        if (this.f22431d && this.f22429b.c()) {
            return this.f22429b.a();
        }
        return null;
    }
}
