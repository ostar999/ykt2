package com.meizu.cloud.pushsdk.handler.a.d;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;

/* loaded from: classes4.dex */
public class a extends com.meizu.cloud.pushsdk.handler.a.a<PushSwitchStatus> {
    public a(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 256;
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void a(PushSwitchStatus pushSwitchStatus, com.meizu.cloud.pushsdk.notification.c cVar) {
        if (b() == null || pushSwitchStatus == null) {
            return;
        }
        b().a(c(), pushSwitchStatus);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start PushSwitchStatusHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PUSH_STATUS.equals(i(intent));
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public PushSwitchStatus c(Intent intent) {
        PushSwitchStatus pushSwitchStatus = (PushSwitchStatus) intent.getSerializableExtra(PushConstants.EXTRA_APP_PUSH_SWITCH_STATUS);
        if ("200".equals(pushSwitchStatus.getCode())) {
            String strG = g(intent);
            DebugLogger.e("AbstractMessageHandler", "PushSwitchStatusHandler update local " + strG + " switch status " + pushSwitchStatus);
            com.meizu.cloud.pushsdk.util.b.a(c(), strG, pushSwitchStatus.isSwitchNotificationMessage());
            com.meizu.cloud.pushsdk.util.b.b(c(), strG, pushSwitchStatus.isSwitchThroughMessage());
        }
        return pushSwitchStatus;
    }
}
