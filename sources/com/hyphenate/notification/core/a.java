package com.hyphenate.notification.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationManagerCompat;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.hyphenate.util.EMLog;

/* loaded from: classes4.dex */
class a extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int intExtra;
        EMLog.d("cancelReceiver", "onReceive");
        if (intent != null && (intExtra = intent.getIntExtra(RemoteMessageConst.Notification.NOTIFY_ID, -1)) > -1) {
            NotificationManagerCompat.from(context.getApplicationContext()).cancel(String.valueOf(intExtra), intExtra);
        }
    }
}
