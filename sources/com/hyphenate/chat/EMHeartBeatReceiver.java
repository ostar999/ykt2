package com.hyphenate.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.hyphenate.util.EMLog;

/* loaded from: classes4.dex */
class EMHeartBeatReceiver extends BroadcastReceiver {
    private static final String TAG = "EMHeartBeatReceiver";
    EMSmartHeartBeat smartHeartbeat;

    public EMHeartBeatReceiver(EMSmartHeartBeat eMSmartHeartBeat) {
        this.smartHeartbeat = eMSmartHeartBeat;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        EMLog.d(TAG, "onReceive EMHeartBeatReceiver");
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMHeartBeatReceiver.1
            @Override // java.lang.Runnable
            public void run() {
                EMSmartHeartBeat eMSmartHeartBeat = EMHeartBeatReceiver.this.smartHeartbeat;
                if (eMSmartHeartBeat != null) {
                    eMSmartHeartBeat.start();
                }
            }
        });
    }
}
