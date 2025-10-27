package com.hyphenate.push.platform.b;

import android.content.Context;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;

/* loaded from: classes4.dex */
public class a extends com.hyphenate.push.platform.a {
    @Override // com.hyphenate.push.platform.a
    public String a(EMPushConfig eMPushConfig) {
        return eMPushConfig.getHwAppId();
    }

    @Override // com.hyphenate.push.platform.a
    public EMPushType b() {
        return EMPushType.HMSPUSH;
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context) {
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context, EMPushConfig eMPushConfig, PushListener pushListener) {
    }
}
