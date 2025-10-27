package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* loaded from: classes6.dex */
public class d extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23361a = "idmd5";

    /* renamed from: b, reason: collision with root package name */
    private Context f23362b;

    public d(Context context) {
        super("idmd5");
        this.f23362b = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        return DeviceConfig.getDeviceIdUmengMD5(this.f23362b);
    }
}
