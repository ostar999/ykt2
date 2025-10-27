package com.hyphenate.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.hyphenate.util.EMLog;

/* loaded from: classes4.dex */
public class EMMonitorReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
            intent.getBooleanExtra("android.intent.extra.REPLACING", false);
            return;
        }
        try {
            context.startService(new Intent(context, (Class<?>) EMChatService.class));
        } catch (Exception e2) {
            EMLog.d("EMMonitorReceiver", "exception in start service, e: " + e2.getMessage());
        }
    }
}
