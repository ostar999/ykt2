package com.aliyun.player.aliyunplayerbase.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

/* loaded from: classes2.dex */
public class NetWatchdog {
    private static final String TAG = "NetWatchdog";
    private boolean isReconnect;
    private Context mContext;
    private NetChangeListener mNetChangeListener;
    private NetConnectedListener mNetConnectedListener;
    private IntentFilter mNetIntentFilter = new IntentFilter();
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.aliyun.player.aliyunplayerbase.util.NetWatchdog.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
            NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            NetworkInfo.State state = NetworkInfo.State.UNKNOWN;
            Bundle extras = intent.getExtras();
            NetworkInfo networkInfo3 = extras != null ? (NetworkInfo) extras.get("networkInfo") : null;
            NetworkInfo.State state2 = networkInfo != null ? networkInfo.getState() : state;
            if (networkInfo2 != null) {
                state = networkInfo2.getState();
            }
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
                if (activeNetworkInfo == null && NetWatchdog.this.mNetConnectedListener != null) {
                    NetWatchdog.this.isReconnect = true;
                    NetWatchdog.this.mNetConnectedListener.onNetUnConnected();
                }
            } else if (NetWatchdog.this.mNetConnectedListener != null) {
                NetWatchdog.this.mNetConnectedListener.onReNetConnected(NetWatchdog.this.isReconnect);
                NetWatchdog.this.isReconnect = false;
            }
            NetworkInfo.State state3 = NetworkInfo.State.CONNECTED;
            if (state3 != state2 && state3 == state) {
                Log.d(NetWatchdog.TAG, "onWifiTo4G()");
                if (NetWatchdog.this.mNetChangeListener != null) {
                    NetWatchdog.this.mNetChangeListener.onWifiTo4G();
                    return;
                }
                return;
            }
            if (state3 == state2 && state3 != state && networkInfo3 != null && networkInfo3.getType() == 1) {
                if (NetWatchdog.this.mNetChangeListener != null) {
                    NetWatchdog.this.mNetChangeListener.on4GToWifi();
                }
            } else {
                NetworkInfo.State state4 = NetworkInfo.State.CONNECTED;
                if (state4 == state2 || state4 == state || NetWatchdog.this.mNetChangeListener == null) {
                    return;
                }
                NetWatchdog.this.mNetChangeListener.onNetDisconnected();
            }
        }
    };

    public interface NetChangeListener {
        void on4GToWifi();

        void onNetDisconnected();

        void onWifiTo4G();
    }

    public interface NetConnectedListener {
        void onNetUnConnected();

        void onReNetConnected(boolean z2);
    }

    public NetWatchdog(Context context) {
        this.mContext = context.getApplicationContext();
        this.mNetIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    }

    public static boolean hasNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
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
        NetworkInfo networkInfo = ((ConnectivityManager) context.getApplicationContext().getSystemService("connectivity")).getNetworkInfo(0);
        NetworkInfo.State state = NetworkInfo.State.UNKNOWN;
        if (networkInfo != null) {
            state = networkInfo.getState();
        }
        return NetworkInfo.State.CONNECTED == state;
    }

    public void setNetChangeListener(NetChangeListener netChangeListener) {
        this.mNetChangeListener = netChangeListener;
    }

    public void setNetConnectedListener(NetConnectedListener netConnectedListener) {
        this.mNetConnectedListener = netConnectedListener;
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
