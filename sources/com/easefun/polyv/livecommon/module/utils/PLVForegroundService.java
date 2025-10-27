package com.easefun.polyv.livecommon.module.utils;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import javax.xml.transform.TransformerException;
import org.json.JSONException;

/* loaded from: classes3.dex */
public class PLVForegroundService extends Service {
    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final String TAG = "PLVLCLinkMicForegroundService";
    private static Class<? extends Activity> activityToJump;
    private static int icon;
    private static String title;

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Foreground Service Channel", 3);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }

    public static void startForegroundService(Class<? extends Activity> activityToJump2, String title2, int icon2) {
        activityToJump = activityToJump2;
        title = title2;
        icon = icon2;
        final Application app = Utils.getApp();
        final Intent intent = new Intent(app, (Class<?>) PLVForegroundService.class);
        new Handler().postDelayed(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.PLVForegroundService.1
            @Override // java.lang.Runnable
            public void run() {
                ContextCompat.startForegroundService(app, intent);
            }
        }, 500L);
    }

    public static void stopForegroundService() {
        Utils.getApp().stopService(new Intent(ActivityUtils.getTopActivity(), (Class<?>) PLVForegroundService.class));
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() throws JSONException, TransformerException, IllegalArgumentException {
        super.onCreate();
        LogUtils.d("onCreate");
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) throws JSONException, TransformerException, IllegalArgumentException {
        if (activityToJump == null) {
            PLVCommonLog.e(TAG, "activityToJump = null");
            return 2;
        }
        createNotificationChannel();
        startForeground(1, new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle(title).setSmallIcon(icon).setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, activityToJump), 0)).build());
        LogUtils.d("onStartCommand");
        return 2;
    }
}
