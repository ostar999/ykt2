package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* loaded from: classes6.dex */
public class c extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23359a = "idfa";

    /* renamed from: b, reason: collision with root package name */
    private Context f23360b;

    public c(Context context) {
        super(f23359a);
        this.f23360b = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        return DeviceConfig.getIdfa(this.f23360b);
    }
}
