package com.psychiatrygarden.aliPlayer.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/* loaded from: classes5.dex */
public class ScreenStatusController {
    private Context mContext;
    private IntentFilter mScreenStatusFilter;
    private String TAG = ScreenStatusController.class.getSimpleName();
    private ScreenStatusListener mScreenStatusListener = null;
    private BroadcastReceiver mScreenStatusReceiver = new BroadcastReceiver() { // from class: com.psychiatrygarden.aliPlayer.utils.ScreenStatusController.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                Log.d(ScreenStatusController.this.TAG, "ACTION_SCREEN_ON");
                if (ScreenStatusController.this.mScreenStatusListener != null) {
                    ScreenStatusController.this.mScreenStatusListener.onScreenOn();
                    return;
                }
                return;
            }
            if (!"android.intent.action.SCREEN_OFF".equals(action)) {
                "android.intent.action.USER_PRESENT".equals(action);
                return;
            }
            Log.d(ScreenStatusController.this.TAG, "ACTION_SCREEN_OFF");
            if (ScreenStatusController.this.mScreenStatusListener != null) {
                ScreenStatusController.this.mScreenStatusListener.onScreenOff();
            }
        }
    };

    public interface ScreenStatusListener {
        void onScreenOff();

        void onScreenOn();
    }

    public ScreenStatusController(Context context) {
        this.mScreenStatusFilter = null;
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        this.mScreenStatusFilter = intentFilter;
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        this.mScreenStatusFilter.addAction("android.intent.action.SCREEN_OFF");
        this.mScreenStatusFilter.addAction("android.intent.action.USER_PRESENT");
    }

    public void setScreenStatusListener(ScreenStatusListener l2) {
        this.mScreenStatusListener = l2;
    }

    public void startListen() {
        Context context = this.mContext;
        if (context != null) {
            context.registerReceiver(this.mScreenStatusReceiver, this.mScreenStatusFilter);
        }
    }

    public void stopListen() {
        Context context = this.mContext;
        if (context != null) {
            context.unregisterReceiver(this.mScreenStatusReceiver);
        }
    }
}
