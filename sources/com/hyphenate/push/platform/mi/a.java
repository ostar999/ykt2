package com.hyphenate.push.platform.mi;

import android.content.Context;
import android.text.TextUtils;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;
import com.xiaomi.mipush.sdk.MiPushClient;

/* loaded from: classes4.dex */
public class a extends com.hyphenate.push.platform.a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8878a = "EMMiPush";

    @Override // com.hyphenate.push.platform.a
    public String a(EMPushConfig eMPushConfig) {
        return eMPushConfig.getMiAppId();
    }

    @Override // com.hyphenate.push.platform.a
    public EMPushType b() {
        return EMPushType.MIPUSH;
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context) {
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context, EMPushConfig eMPushConfig, PushListener pushListener) {
        String regId = MiPushClient.getRegId(context);
        if (TextUtils.isEmpty(regId)) {
            MiPushClient.registerPush(context, eMPushConfig.getMiAppId(), eMPushConfig.getMiAppKey());
        } else {
            EMPushHelper.getInstance().onReceiveToken(b(), regId);
        }
    }
}
