package com.hyphenate.push;

import com.hyphenate.push.a.a;

/* loaded from: classes4.dex */
public abstract class PushListener {
    public String getPushToken(EMPushType eMPushType, EMPushConfig eMPushConfig) {
        return "";
    }

    public boolean isSupportPush(EMPushType eMPushType, EMPushConfig eMPushConfig) {
        return a.a(eMPushType, eMPushConfig);
    }

    public abstract void onError(EMPushType eMPushType, long j2);
}
