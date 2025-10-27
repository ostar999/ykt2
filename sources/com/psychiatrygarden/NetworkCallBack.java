package com.psychiatrygarden;

import android.net.ConnectivityManager;
import android.net.Network;
import androidx.annotation.NonNull;
import com.psychiatrygarden.event.NetworkEvent;
import de.greenrobot.event.EventBus;

/* loaded from: classes5.dex */
public class NetworkCallBack extends ConnectivityManager.NetworkCallback {
    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        EventBus.getDefault().post(new NetworkEvent(true));
    }

    @Override // android.net.ConnectivityManager.NetworkCallback
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        EventBus.getDefault().post(new NetworkEvent(false));
    }
}
