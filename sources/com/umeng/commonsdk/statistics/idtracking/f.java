package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* loaded from: classes6.dex */
public class f extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23375a = "imei";

    /* renamed from: b, reason: collision with root package name */
    private Context f23376b;

    public f(Context context) {
        super("imei");
        this.f23376b = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        return DeviceConfig.getImeiNew(this.f23376b);
    }
}
