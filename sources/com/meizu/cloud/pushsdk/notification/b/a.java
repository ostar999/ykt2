package com.meizu.cloud.pushsdk.notification.b;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;

/* loaded from: classes4.dex */
public class a extends c {
    public a(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    private void b(RemoteViews remoteViews, MessageV3 messageV3) {
        Bitmap bitmapA;
        if (messageV3.getmNotificationStyle() == null || a()) {
            return;
        }
        if (TextUtils.isEmpty(messageV3.getmNotificationStyle().getExpandableImageUrl()) || (bitmapA = a(messageV3.getmNotificationStyle().getExpandableImageUrl())) == null) {
            remoteViews.setViewVisibility(com.meizu.cloud.pushsdk.notification.c.c.g(this.f9473a), 8);
        } else {
            remoteViews.setViewVisibility(com.meizu.cloud.pushsdk.notification.c.c.g(this.f9473a), 0);
            remoteViews.setImageViewBitmap(com.meizu.cloud.pushsdk.notification.c.c.g(this.f9473a), bitmapA);
        }
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    public void b(Notification notification, MessageV3 messageV3) {
        if (MinSdkChecker.isSupportNotificationBuild()) {
            RemoteViews remoteViews = new RemoteViews(this.f9473a.getPackageName(), com.meizu.cloud.pushsdk.notification.c.c.a(this.f9473a));
            remoteViews.setTextViewText(com.meizu.cloud.pushsdk.notification.c.c.d(this.f9473a), messageV3.getTitle());
            remoteViews.setTextViewText(com.meizu.cloud.pushsdk.notification.c.c.e(this.f9473a), messageV3.getContent());
            remoteViews.setLong(com.meizu.cloud.pushsdk.notification.c.c.f(this.f9473a), "setTime", System.currentTimeMillis());
            a(remoteViews, messageV3);
            b(remoteViews, messageV3);
            notification.bigContentView = remoteViews;
        }
    }
}
