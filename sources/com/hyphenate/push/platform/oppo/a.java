package com.hyphenate.push.platform.oppo;

import android.content.Context;
import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;
import com.hyphenate.util.EMLog;

/* loaded from: classes4.dex */
public class a extends com.hyphenate.push.platform.a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8879a = "EMOppoPush";

    @Override // com.hyphenate.push.platform.a
    public String a(EMPushConfig eMPushConfig) {
        return eMPushConfig.getOppoAppKey();
    }

    @Override // com.hyphenate.push.platform.a
    public EMPushType b() {
        return EMPushType.OPPOPUSH;
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context) {
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context, EMPushConfig eMPushConfig, PushListener pushListener) {
        if (HeytapPushManager.isSupportPush(context)) {
            HeytapPushManager.register(context, eMPushConfig.getOppoAppKey(), eMPushConfig.getOppoAppSecret(), new ICallBackResultService() { // from class: com.hyphenate.push.platform.oppo.EMOppoPush$1
                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onError(int i2, String str) {
                    EMLog.e("EMOppoPush", "Oppo init failed: " + i2 + " " + str);
                }

                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onGetNotificationStatus(int i2, int i3) {
                }

                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onGetPushStatus(int i2, int i3) {
                }

                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onRegister(int i2, String str) {
                    if (i2 == 0) {
                        EMPushHelper.getInstance().onReceiveToken(this.this$0.b(), str);
                    } else {
                        EMPushHelper.getInstance().onErrorResponse(this.this$0.b(), i2);
                    }
                }

                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onSetPushTime(int i2, String str) {
                }

                @Override // com.heytap.msp.push.callback.ICallBackResultService
                public void onUnRegister(int i2) {
                }
            });
        } else {
            EMPushHelper.getInstance().onErrorResponse(b(), 1500L);
        }
    }
}
