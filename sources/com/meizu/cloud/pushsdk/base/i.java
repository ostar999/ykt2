package com.meizu.cloud.pushsdk.base;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.aliyun.vod.log.core.AliyunLogCommon;

/* loaded from: classes4.dex */
public class i {
    /* JADX WARN: Multi-variable type inference failed */
    public static String a(Context context) {
        com.meizu.cloud.pushsdk.base.a.d dVarA = com.meizu.cloud.pushsdk.base.a.a.a("android.telephony.MzTelephonyManager").a("getDeviceId", new Class[0]).a(new Object[0]);
        return dVarA.f9230a ? (String) dVarA.f9231b : ((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getDeviceId();
    }
}
