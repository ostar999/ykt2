package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

/* loaded from: classes6.dex */
public class g extends a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23377a = "mac";

    /* renamed from: b, reason: collision with root package name */
    private Context f23378b;

    public g(Context context) {
        super("mac");
        this.f23378b = context;
    }

    @Override // com.umeng.commonsdk.statistics.idtracking.a
    public String f() {
        try {
            return DeviceConfig.getMac(this.f23378b);
        } catch (Exception e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                e2.printStackTrace();
            }
            UMCrashManager.reportCrash(this.f23378b, e2);
            return null;
        }
    }
}
