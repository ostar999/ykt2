package com.aliyun.svideo.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* loaded from: classes2.dex */
public class NetWatchdogUtils {
    private boolean isReconnect;
    private Context mContext;
    private NetChangeListener mNetChangeListener;
    private IntentFilter mNetIntentFilter = new IntentFilter();
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.aliyun.svideo.common.utils.NetWatchdogUtils.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return;
            }
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
            NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            NetworkInfo.State state = NetworkInfo.State.UNKNOWN;
            NetworkInfo.State state2 = networkInfo != null ? networkInfo.getState() : state;
            if (networkInfo2 != null) {
                state = networkInfo2.getState();
            }
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
                if (activeNetworkInfo == null && NetWatchdogUtils.this.mNetChangeListener != null) {
                    NetWatchdogUtils.this.isReconnect = true;
                    NetWatchdogUtils.this.mNetChangeListener.onNetUnConnected();
                }
            } else if (NetWatchdogUtils.this.mNetChangeListener != null) {
                NetWatchdogUtils.this.mNetChangeListener.onReNetConnected(NetWatchdogUtils.this.isReconnect);
                NetWatchdogUtils.this.isReconnect = false;
            }
            NetworkInfo.State state3 = NetworkInfo.State.CONNECTED;
            if (state3 != state2 && state3 == state) {
                if (NetWatchdogUtils.this.mNetChangeListener != null) {
                    NetWatchdogUtils.this.mNetChangeListener.onWifiTo4G();
                }
            } else if (state3 == state2 && state3 != state) {
                if (NetWatchdogUtils.this.mNetChangeListener != null) {
                    NetWatchdogUtils.this.mNetChangeListener.on4GToWifi();
                }
            } else {
                if (state3 == state2 || NetWatchdogUtils.this.mNetChangeListener == null) {
                    return;
                }
                NetWatchdogUtils.this.mNetChangeListener.onNetUnConnected();
            }
        }
    };

    public interface NetChangeListener {
        void on4GToWifi();

        void onNetUnConnected();

        void onReNetConnected(boolean z2);

        void onWifiTo4G();
    }

    public NetWatchdogUtils(Context context) {
        this.mContext = context.getApplicationContext();
        this.mNetIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    }

    public static boolean hasNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        NetworkInfo.State state = NetworkInfo.State.UNKNOWN;
        NetworkInfo.State state2 = networkInfo != null ? networkInfo.getState() : state;
        if (networkInfo2 != null) {
            state = networkInfo2.getState();
        }
        NetworkInfo.State state3 = NetworkInfo.State.CONNECTED;
        return (state3 == state2 || state3 == state) && activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static boolean is4GConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
        NetworkInfo.State state = NetworkInfo.State.UNKNOWN;
        if (networkInfo != null) {
            state = networkInfo.getState();
        }
        return NetworkInfo.State.CONNECTED == state;
    }

    public void setNetChangeListener(NetChangeListener netChangeListener) {
        this.mNetChangeListener = netChangeListener;
    }

    public void startWatch() {
        try {
            this.mContext.registerReceiver(this.mReceiver, this.mNetIntentFilter);
        } catch (Exception unused) {
        }
    }

    public void stopWatch() {
        try {
            this.mContext.unregisterReceiver(this.mReceiver);
        } catch (Exception unused) {
        }
    }
}
