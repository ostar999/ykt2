package com.meizu.cloud.pushsdk.handler.a.e;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.a.c;

/* loaded from: classes4.dex */
public class a extends c {
    public a(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 8192;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.a.a
    public void a(MessageV3 messageV3, com.meizu.cloud.pushsdk.notification.c cVar) {
        if (cVar != null) {
            cVar.e(messageV3);
            b(messageV3);
        }
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start ScheduleNotificationHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_SCHEDULE_NOTIFICATION.equals(i(intent));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.a.a
    /* renamed from: c */
    public void b(MessageV3 messageV3) {
        DebugLogger.e("AbstractMessageHandler", "ScheduleNotificationHandler dont repeat upload receiver push event");
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.a.a
    /* renamed from: d, reason: avoid collision after fix types in other method */
    public void c(MessageV3 messageV3) {
        com.meizu.cloud.pushsdk.util.c.a(c(), messageV3.getUploadDataPackageName(), messageV3.getDeviceId(), messageV3.getTaskId(), messageV3.getSeqId(), messageV3.getPushTimestamp());
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.a.a
    /* renamed from: g */
    public int d(MessageV3 messageV3) {
        return 0;
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.c, com.meizu.cloud.pushsdk.handler.a.a
    /* renamed from: j */
    public MessageV3 c(Intent intent) {
        return (MessageV3) intent.getParcelableExtra(PushConstants.EXTRA_APP_PUSH_SCHEDULE_NOTIFICATION_MESSAGE);
    }
}
