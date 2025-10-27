package com.hyphenate.push.platform.vivo;

import android.content.Context;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

/* loaded from: classes4.dex */
public class EMVivoMsgReceiver extends OpenClientPushMessageReceiver {
    @Override // com.vivo.push.sdk.OpenClientPushMessageReceiver, com.vivo.push.sdk.PushMessageCallback
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage uPSNotificationMessage) {
    }

    @Override // com.vivo.push.sdk.OpenClientPushMessageReceiver, com.vivo.push.sdk.PushMessageCallback
    public void onReceiveRegId(Context context, String str) {
        EMPushHelper.getInstance().onReceiveToken(EMPushType.VIVOPUSH, str);
    }
}
