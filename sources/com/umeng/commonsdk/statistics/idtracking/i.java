package com.umeng.commonsdk.statistics.idtracking;

import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* loaded from: classes6.dex */
public class i extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23384a = "serial";

    public i() {
        super(f23384a);
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        return DeviceConfig.getSerial();
    }
}
