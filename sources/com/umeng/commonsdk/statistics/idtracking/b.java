package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* loaded from: classes6.dex */
public class b extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23357a = "android_id";

    /* renamed from: b, reason: collision with root package name */
    private Context f23358b;

    public b(Context context) {
        super("android_id");
        this.f23358b = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        return DeviceConfig.getAndroidId(this.f23358b);
    }
}
