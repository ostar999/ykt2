package com.hyphenate.push.platform.a;

import android.content.Context;
import android.text.TextUtils;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;

/* loaded from: classes4.dex */
public class a extends com.hyphenate.push.platform.a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8873a = "EMFCMPush";

    @Override // com.hyphenate.push.platform.a
    public String a(EMPushConfig eMPushConfig) {
        return eMPushConfig.getFcmSenderId();
    }

    @Override // com.hyphenate.push.platform.a
    public EMPushType b() {
        return EMPushType.FCM;
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context) {
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context, EMPushConfig eMPushConfig, PushListener pushListener) {
        if (!(pushListener != null ? pushListener.isSupportPush(b(), eMPushConfig) : true)) {
            EMPushHelper.getInstance().onErrorResponse(b(), 1500L);
            return;
        }
        String fCMPushToken = EMPushHelper.getInstance().getFCMPushToken();
        if (TextUtils.isEmpty(fCMPushToken) && pushListener != null) {
            fCMPushToken = pushListener.getPushToken(b(), eMPushConfig);
            if (!TextUtils.isEmpty(fCMPushToken)) {
                try {
                    EMPushHelper.getInstance().setFCMPushToken(fCMPushToken);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        if (TextUtils.isEmpty(fCMPushToken)) {
            return;
        }
        EMPushHelper.getInstance().onReceiveToken(b(), fCMPushToken);
    }
}
