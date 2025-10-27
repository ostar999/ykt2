package com.xiaomi.push;

import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes6.dex */
public enum fc {
    ACTIVITY(PushConstants.INTENT_ACTIVITY_NAME),
    SERVICE_ACTION("service_action"),
    SERVICE_COMPONENT("service_component"),
    PROVIDER(com.umeng.analytics.pro.d.M);


    /* renamed from: a, reason: collision with other field name */
    public String f419a;

    fc(String str) {
        this.f419a = str;
    }
}
