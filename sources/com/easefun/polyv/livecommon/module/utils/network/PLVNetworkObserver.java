package com.easefun.polyv.livecommon.module.utils.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.Nullable;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.plv.foundationsdk.utils.PLVNetworkUtils;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVNetworkObserver {

    @Nullable
    private Context context;
    private final List<NetworkCallback> networkCallbacks = new ArrayList();
    private boolean started = false;
    private final BroadcastReceiver networkReceiver = new BroadcastReceiver() { // from class: com.easefun.polyv.livecommon.module.utils.network.PLVNetworkObserver.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                if (PLVNetworkUtils.isConnected(context)) {
                    PLVNetworkObserver.this.notifyNetworkConnected();
                } else {
                    PLVNetworkObserver.this.notifyNetworkDisconnected();
                }
            }
        }
    };

    public interface NetworkCallback {
        void onNetworkConnected();

        void onNetworkDisconnected();
    }

    private void autoStopByLifecycle(final LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(new GenericLifecycleObserver() { // from class: com.easefun.polyv.livecommon.module.utils.network.PLVNetworkObserver.2
            @Override // androidx.lifecycle.LifecycleEventObserver
            public void onStateChanged(LifecycleOwner owner, Lifecycle.Event event) {
                if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                    PLVNetworkObserver.this.stop();
                    lifecycleOwner.getLifecycle().removeObserver(this);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyNetworkConnected() {
        PLVSugarUtil.foreach(this.networkCallbacks, new PLVSugarUtil.Consumer<NetworkCallback>() { // from class: com.easefun.polyv.livecommon.module.utils.network.PLVNetworkObserver.3
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(NetworkCallback networkCallback) {
                if (networkCallback != null) {
                    networkCallback.onNetworkConnected();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyNetworkDisconnected() {
        PLVSugarUtil.foreach(this.networkCallbacks, new PLVSugarUtil.Consumer<NetworkCallback>() { // from class: com.easefun.polyv.livecommon.module.utils.network.PLVNetworkObserver.4
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(NetworkCallback networkCallback) {
                if (networkCallback != null) {
                    networkCallback.onNetworkDisconnected();
                }
            }
        });
    }

    private void startObserveNetwork() {
        Context context = this.context;
        if (context == null) {
            return;
        }
        context.registerReceiver(this.networkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private void stopObserveNetwork() {
        Context context = this.context;
        if (context == null) {
            return;
        }
        context.unregisterReceiver(this.networkReceiver);
    }

    public void addNetworkCallback(NetworkCallback callback) {
        if (callback == null) {
            return;
        }
        this.networkCallbacks.add(callback);
    }

    public void removeNetworkCallback(NetworkCallback callback) {
        this.networkCallbacks.remove(callback);
    }

    public void start(final Context context, final LifecycleOwner lifecycleOwner) {
        if (this.started) {
            return;
        }
        this.started = true;
        this.context = context;
        startObserveNetwork();
        autoStopByLifecycle(lifecycleOwner);
    }

    public void stop() {
        if (this.started) {
            this.networkCallbacks.clear();
            stopObserveNetwork();
            this.started = false;
            this.context = null;
        }
    }
}
