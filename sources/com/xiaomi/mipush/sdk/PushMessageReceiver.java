package com.xiaomi.mipush.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.mipush.sdk.MessageHandleService;
import com.xiaomi.push.fl;

/* loaded from: classes6.dex */
public abstract class PushMessageReceiver extends BroadcastReceiver {
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
    }

    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
    }

    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        MessageHandleService.addJob(context.getApplicationContext(), new MessageHandleService.a(intent, this));
        try {
            if (intent.getIntExtra("eventMessageType", -1) == 2000) {
                fl.a(context.getApplicationContext()).a(context.getPackageName(), intent, 2003, "receive passThough message broadcast");
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    @Deprecated
    public void onReceiveMessage(Context context, MiPushMessage miPushMessage) {
    }

    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
    }

    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
    }

    public void onRequirePermissions(Context context, String[] strArr) {
    }
}
