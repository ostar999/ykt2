package com.hyphenate.push.platform.vivo;

import android.content.Context;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;
import com.hyphenate.util.EMLog;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.vivo.push.util.VivoPushException;

/* loaded from: classes4.dex */
public class a extends com.hyphenate.push.platform.a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8880a = "EMVivoPush";

    @Override // com.hyphenate.push.platform.a
    public String a(EMPushConfig eMPushConfig) {
        return eMPushConfig.getVivoAppId() + DictionaryFactory.SHARP + eMPushConfig.getVivoAppKey();
    }

    @Override // com.hyphenate.push.platform.a
    public EMPushType b() {
        return EMPushType.VIVOPUSH;
    }

    @Override // com.hyphenate.push.platform.a
    public void b(Context context) {
    }

    @Override // com.hyphenate.push.platform.a
    public void b(final Context context, EMPushConfig eMPushConfig, PushListener pushListener) {
        if (!PushClient.getInstance(context).isSupport()) {
            EMPushHelper.getInstance().onErrorResponse(b(), 1500L);
            return;
        }
        try {
            PushClient.getInstance(context).initialize();
            PushClient.getInstance(context).turnOnPush(new IPushActionListener() { // from class: com.hyphenate.push.platform.vivo.EMVivoPush$1
                @Override // com.vivo.push.IPushActionListener
                public void onStateChanged(int i2) {
                    if (i2 != 0) {
                        EMPushHelper.getInstance().onErrorResponse(this.this$0.b(), i2);
                    } else {
                        EMPushHelper.getInstance().onReceiveToken(this.this$0.b(), PushClient.getInstance(context).getRegId());
                    }
                }
            });
        } catch (VivoPushException e2) {
            EMLog.e(f8880a, "Vivo init failed: " + e2.getCode() + " " + e2.getMessage());
        }
    }
}
