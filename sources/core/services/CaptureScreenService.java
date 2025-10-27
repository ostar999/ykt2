package core.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;
import c.h;
import com.huawei.hms.push.constant.RemoteMessageConst;
import org.wrtca.api.ScreenCapturerAndroid;

@TargetApi(21)
/* loaded from: classes8.dex */
public class CaptureScreenService extends Service {
    private static final String TAG = "CaptureScreenService";
    private MediaProjection.Callback callback = null;
    private Intent mediaProjectionPermissionResultData = null;
    private ScreenCapturerAndroid screenCapturer = null;

    public class a extends MediaProjection.Callback {
        public a() {
        }

        @Override // android.media.projection.MediaProjection.Callback
        public void onStop() {
            h.a(CaptureScreenService.TAG, "CaptureScreenService onStop ");
            super.onStop();
        }
    }

    private Notification createDefalultNotification() {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentText("screen capture is running......").setWhen(System.currentTimeMillis());
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 26) {
            builder.setChannelId("notification_id");
        }
        if (i2 >= 26) {
            ((NotificationManager) getSystemService(RemoteMessageConst.NOTIFICATION)).createNotificationChannel(new NotificationChannel("notification_id", "notification_name", 2));
        }
        Notification notificationBuild = builder.build();
        notificationBuild.defaults = 1;
        return notificationBuild;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        h.a(TAG, "onDestroy");
        super.onDestroy();
        stopForeground(true);
        h.a(TAG, "onDestroy end");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        Intent intent2;
        h.a(TAG, "onStartCommand");
        this.callback = new a();
        int intExtra = intent.getIntExtra("width", 320);
        int intExtra2 = intent.getIntExtra("height", 240);
        int intExtra3 = intent.getIntExtra("frame_rate", 20);
        this.mediaProjectionPermissionResultData = (Intent) intent.getParcelableExtra("data");
        Notification notificationCreateDefalultNotification = (Notification) intent.getParcelableExtra(RemoteMessageConst.NOTIFICATION);
        if (notificationCreateDefalultNotification == null) {
            notificationCreateDefalultNotification = createDefalultNotification();
        }
        startForeground(110, notificationCreateDefalultNotification);
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getApplicationContext().getSystemService("media_projection");
        if (mediaProjectionManager != null && (intent2 = this.mediaProjectionPermissionResultData) != null) {
            MediaProjection mediaProjection = mediaProjectionManager.getMediaProjection(-1, intent2);
            ScreenCapturerAndroid screenCapturerAndroid = ScreenCapturerAndroid.getInstance(this.mediaProjectionPermissionResultData, this.callback);
            this.screenCapturer = screenCapturerAndroid;
            screenCapturerAndroid.startCapture(intExtra, intExtra2, intExtra3, mediaProjection);
        }
        return super.onStartCommand(intent, i2, i3);
    }
}
