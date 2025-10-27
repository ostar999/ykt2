package com.google.android.exoplayer2.util;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressLint({"InlinedApi"})
/* loaded from: classes3.dex */
public final class NotificationUtil {
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Importance {
    }

    private NotificationUtil() {
    }

    public static void createNotificationChannel(Context context, String str, @StringRes int i2, @StringRes int i3, int i4) {
        if (Util.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) Assertions.checkNotNull((NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION));
            NotificationChannel notificationChannel = new NotificationChannel(str, context.getString(i2), i4);
            if (i3 != 0) {
                notificationChannel.setDescription(context.getString(i3));
            }
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static void setNotification(Context context, int i2, @Nullable Notification notification) {
        NotificationManager notificationManager = (NotificationManager) Assertions.checkNotNull((NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION));
        if (notification != null) {
            notificationManager.notify(i2, notification);
        } else {
            notificationManager.cancel(i2);
        }
    }
}
