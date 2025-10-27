package com.meizu.cloud.pushsdk.handler.a.c;

import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;

/* loaded from: classes4.dex */
public class d extends com.meizu.cloud.pushsdk.handler.a.a<com.meizu.cloud.pushsdk.handler.a.b.c> {
    public d(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        super(context, aVar);
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public int a() {
        return 32768;
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(com.meizu.cloud.pushsdk.handler.a.b.c cVar) {
        String str;
        int iC = cVar.c();
        if (iC == -2) {
            DebugLogger.e("AbstractMessageHandler", "notification STATE_NOTIFICATION_SHOW_ACCESS_DENY");
            com.meizu.cloud.pushsdk.util.c.g(c(), cVar.a().getUploadDataPackageName(), cVar.a().getDeviceId(), cVar.a().getTaskId(), cVar.a().getSeqId(), cVar.a().getPushTimestamp());
        } else {
            if (iC == -1) {
                DebugLogger.e("AbstractMessageHandler", "notification STATE_NOTIFICATION_SHOW_INBOX");
                com.meizu.cloud.pushsdk.util.c.f(c(), cVar.a().getUploadDataPackageName(), cVar.a().getDeviceId(), cVar.a().getTaskId(), cVar.a().getSeqId(), cVar.a().getPushTimestamp());
                return;
            }
            if (iC == 0) {
                str = "notification STATE_NOTIFICATION_SHOW_NORMAL";
            } else if (iC != 1) {
                return;
            } else {
                str = "notification STATE_NOTIFICATION_SHOW_FLOAT";
            }
            DebugLogger.e("AbstractMessageHandler", str);
        }
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.a
    public void a(com.meizu.cloud.pushsdk.handler.a.b.c cVar, com.meizu.cloud.pushsdk.notification.c cVar2) {
        DebugLogger.e("AbstractMessageHandler", "store notification id " + cVar.b());
        com.meizu.cloud.pushsdk.notification.c.b.b(c(), cVar.a().getUploadDataPackageName(), cVar.b());
    }

    @Override // com.meizu.cloud.pushsdk.handler.c
    public boolean a(Intent intent) {
        DebugLogger.i("AbstractMessageHandler", "start NotificationStateMessageHandler match");
        return PushConstants.MZ_PUSH_ON_MESSAGE_ACTION.equals(intent.getAction()) && PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_STATE.equals(i(intent));
    }

    @Override // com.meizu.cloud.pushsdk.handler.a.a
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public com.meizu.cloud.pushsdk.handler.a.b.c c(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.NOTIFICATION_EXTRA_SHOW_PACKAGE_NAME);
        String stringExtra2 = intent.getStringExtra(PushConstants.NOTIFICATION_EXTRA_TASK_ID);
        String stringExtra3 = intent.getStringExtra(PushConstants.NOTIFICATION_EXTRA_SEQ_ID);
        String stringExtra4 = intent.getStringExtra(PushConstants.NOTIFICATION_EXTRA_DEVICE_ID);
        String stringExtra5 = intent.getStringExtra(PushConstants.NOTIFICATION_EXTRA_PUSH_TIMESTAMP);
        String stringExtra6 = intent.getStringExtra(PushConstants.MZ_PUSH_NOTIFICATION_STATE_MESSAGE);
        DebugLogger.i("AbstractMessageHandler", "current taskId " + stringExtra2 + " seqId " + stringExtra3 + " deviceId " + stringExtra4 + " packageName " + stringExtra);
        com.meizu.cloud.pushsdk.handler.a.b.c cVar = new com.meizu.cloud.pushsdk.handler.a.b.c(MessageV3.parse(c().getPackageName(), stringExtra, stringExtra5, stringExtra4, stringExtra2, stringExtra3, stringExtra6));
        String stringExtra7 = intent.getStringExtra("flyme:notification_pkg");
        int intExtra = intent.getIntExtra("flyme:notification_id", 0);
        int intExtra2 = intent.getIntExtra("flyme:notification_state", 0);
        cVar.a(intExtra);
        cVar.a(stringExtra7);
        cVar.b(intExtra2);
        return cVar;
    }
}
