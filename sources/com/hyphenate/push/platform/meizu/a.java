package com.hyphenate.push.platform.meizu;

import android.content.Context;
import android.text.TextUtils;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;

/* loaded from: classes4.dex */
public class a extends com.hyphenate.push.platform.a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8877a = "EMMzPush";

    @Override // com.hyphenate.push.platform.a
    public String a(EMPushConfig eMPushConfig) {
        return eMPushConfig.getMzAppId();
    }

    @Override // com.hyphenate.push.platform.a
    public EMPushType b() {
        return EMPushType.MEIZUPUSH;
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context) {
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context, EMPushConfig eMPushConfig, PushListener pushListener) {
        if (!MzSystemUtils.isBrandMeizu(context)) {
            EMPushHelper.getInstance().onErrorResponse(b(), 1500L);
            return;
        }
        String pushId = PushManager.getPushId(context);
        if (TextUtils.isEmpty(pushId)) {
            PushManager.register(context, eMPushConfig.getMzAppId(), eMPushConfig.getMzAppKey());
        } else {
            EMPushHelper.getInstance().onReceiveToken(b(), pushId);
        }
    }
}
