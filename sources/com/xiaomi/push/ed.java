package com.xiaomi.push;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes6.dex */
public class ed extends ei {
    public ed(Context context, int i2) {
        super(context, i2);
    }

    @Override // com.xiaomi.push.ai.a
    /* renamed from: a */
    public int mo185a() {
        return 5;
    }

    @Override // com.xiaomi.push.ei
    public hz a() {
        return hz.AppActiveList;
    }

    @Override // com.xiaomi.push.ei
    /* renamed from: a */
    public String mo336a() {
        StringBuilder sb = new StringBuilder();
        try {
            for (ActivityManager.RunningTaskInfo runningTaskInfo : ((ActivityManager) ((ei) this).f339a.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningTasks(10)) {
                if (runningTaskInfo != null && runningTaskInfo.topActivity != null) {
                    if (sb.length() > 0) {
                        sb.append(com.alipay.sdk.util.h.f3376b);
                    }
                    sb.append(runningTaskInfo.topActivity.getPackageName());
                }
            }
        } catch (Throwable unused) {
        }
        if (TextUtils.isEmpty(sb)) {
            return null;
        }
        return sb.toString();
    }
}
