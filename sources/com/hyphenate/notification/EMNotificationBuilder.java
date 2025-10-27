package com.hyphenate.notification;

import android.R;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.IdRes;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.lang.ref.SoftReference;

/* loaded from: classes4.dex */
public class EMNotificationBuilder {
    private Context appContext;
    private int badgeNum;
    private String channelId;
    private int channelLevel;
    private String channelName;
    private String content;
    private PendingIntent fullScreenIntent;
    private SoftReference<Bitmap> iconBitmapRef;
    private PendingIntent pendingIntent;
    private boolean sound;
    private String ticker;
    private String title;
    private boolean vibrate;
    private boolean autoCancel = true;
    private EMNotificationDefaultStyle style = new EMNotificationDefaultStyle();
    private int smallIcon = R.drawable.sym_def_app_icon;

    public static class EMNotificationBigPicStyle extends EMNotificationDefaultStyle {
        private SoftReference<Bitmap> bitmapRef;

        public Bitmap getBigPic() {
            SoftReference<Bitmap> softReference = this.bitmapRef;
            if (softReference == null || softReference.get() == null) {
                return null;
            }
            return this.bitmapRef.get();
        }

        public EMNotificationBigPicStyle setBigPic(Bitmap bitmap) {
            if (bitmap != null) {
                this.bitmapRef = new SoftReference<>(bitmap);
            }
            return this;
        }
    }

    public static class EMNotificationBigTextStyle extends EMNotificationDefaultStyle {
        private String bigTxt;

        public String getBigTxt() {
            return this.bigTxt;
        }

        public EMNotificationBigTextStyle setBigTxt(String str) {
            this.bigTxt = str;
            return this;
        }
    }

    public static class EMNotificationDefaultStyle {
    }

    public EMNotificationBuilder(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot is null");
        }
        this.appContext = context.getApplicationContext();
    }

    private void createNotificationChannel(Context context, boolean z2, String str, String str2, int i2) {
        if (Build.VERSION.SDK_INT >= 26) {
            if (i2 < 0 || i2 > 5) {
                i2 = 3;
            }
            NotificationChannel notificationChannel = new NotificationChannel(str, str2, i2);
            if (!this.sound) {
                notificationChannel.setSound(null, null);
            }
            if (z2) {
                notificationChannel.enableVibration(true);
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            } else {
                notificationChannel.enableVibration(false);
            }
            ((NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION)).createNotificationChannel(notificationChannel);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00f1  */
    /* JADX WARN: Type inference failed for: r0v3, types: [androidx.core.app.NotificationCompat$Builder, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Type inference failed for: r2v3, types: [int] */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.lang.reflect.AccessibleObject, java.lang.reflect.Method] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.app.Notification build() {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.notification.EMNotificationBuilder.build():android.app.Notification");
    }

    public EMNotificationBuilder setAutoCancel(boolean z2) {
        this.autoCancel = z2;
        return this;
    }

    public EMNotificationBuilder setBadgeNum(int i2) {
        this.badgeNum = i2;
        return this;
    }

    public EMNotificationBuilder setChannelId(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.channelId = str;
        }
        return this;
    }

    public EMNotificationBuilder setChannelName(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.channelName = str;
        }
        return this;
    }

    public EMNotificationBuilder setContent(String str) {
        this.content = str;
        return this;
    }

    public EMNotificationBuilder setFullScreenIntent(PendingIntent pendingIntent) {
        this.fullScreenIntent = pendingIntent;
        return this;
    }

    public EMNotificationBuilder setIcon(@IdRes int i2) {
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(this.appContext.getResources(), i2);
        if (bitmapDecodeResource != null) {
            this.iconBitmapRef = new SoftReference<>(bitmapDecodeResource);
        }
        return this;
    }

    public EMNotificationBuilder setIcon(Bitmap bitmap) {
        if (bitmap != null) {
            this.iconBitmapRef = new SoftReference<>(bitmap);
        }
        return this;
    }

    public EMNotificationBuilder setLevel(int i2) {
        this.channelLevel = i2;
        return this;
    }

    public EMNotificationBuilder setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
        return this;
    }

    public EMNotificationBuilder setSmallIcon(@IdRes int i2) {
        this.smallIcon = i2;
        return this;
    }

    public EMNotificationBuilder setSound(boolean z2) {
        this.sound = z2;
        return this;
    }

    public EMNotificationBuilder setStyle(EMNotificationDefaultStyle eMNotificationDefaultStyle) {
        this.style = eMNotificationDefaultStyle;
        return this;
    }

    public EMNotificationBuilder setTicker(String str) {
        this.ticker = str;
        return this;
    }

    public EMNotificationBuilder setTitle(String str) {
        this.title = str;
        return this;
    }

    public EMNotificationBuilder setVibrate(boolean z2) {
        this.vibrate = z2;
        return this;
    }
}
