package com.hyphenate.chat;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.util.EMLog;

@SuppressLint({"Registered"})
/* loaded from: classes4.dex */
public class EMChatService extends Service {
    private static final String TAG = "chatservice";
    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public EMChatService getService() {
            return EMChatService.this;
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        EMLog.d(TAG, "onBind");
        return this.mBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        EMLog.i(TAG, "chat service created");
    }

    @Override // android.app.Service
    public void onDestroy() {
        EMLog.d(TAG, "onDestroy");
        if (EMPushHelper.getInstance().getPushType() == EMPushType.NORMAL && EMClient.getInstance().isLoggedInBefore()) {
            new Thread(new Runnable() { // from class: com.hyphenate.chat.EMChatService.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        EMChatService.this.startService(new Intent(EMChatService.this, (Class<?>) EMChatService.class));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        if (EMPushHelper.getInstance().getPushType() == EMPushType.FCM) {
            EMLog.d(TAG, "start not sticky!");
            return 2;
        }
        EMLog.d(TAG, "start sticky!");
        return 1;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        return true;
    }
}
