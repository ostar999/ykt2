package com.psychiatrygarden.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import com.psychiatrygarden.event.NetworkEvent;
import de.greenrobot.event.EventBus;

/* loaded from: classes6.dex */
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null || !TextUtils.equals(intent.getAction(), "android.net.conn.CONNECTIVITY_CHANGE")) {
                return;
            }
            EventBus.getDefault().post(new NetworkEvent(connectivityManager.getNetworkInfo(0).isAvailable() || connectivityManager.getNetworkInfo(1).isAvailable()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
