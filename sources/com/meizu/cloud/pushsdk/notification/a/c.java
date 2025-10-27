package com.meizu.cloud.pushsdk.notification.a;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;

/* loaded from: classes4.dex */
public class c extends com.meizu.cloud.pushsdk.notification.a {
    public c(Context context, PushNotificationBuilder pushNotificationBuilder) {
        super(context, pushNotificationBuilder);
    }

    @Override // com.meizu.cloud.pushsdk.notification.a
    public void b(Notification.Builder builder, MessageV3 messageV3) {
        Bitmap bitmapA;
        AppIconSetting appIconSetting = messageV3.getmAppIconSetting();
        if (appIconSetting != null) {
            if (appIconSetting.isDefaultLargeIcon()) {
                PushNotificationBuilder pushNotificationBuilder = this.f9474b;
                if (pushNotificationBuilder != null && pushNotificationBuilder.getmLargIcon() != 0) {
                    bitmapA = BitmapFactory.decodeResource(this.f9473a.getResources(), this.f9474b.getmLargIcon());
                }
                builder.setLargeIcon(bitmapA);
            }
            if (Thread.currentThread() == this.f9473a.getMainLooper().getThread()) {
                return;
            }
            Bitmap bitmapA2 = a(appIconSetting.getLargeIconUrl());
            if (bitmapA2 != null) {
                DebugLogger.i("AbstractPushNotification", "On other Thread down load largeIcon image success");
                builder.setLargeIcon(bitmapA2);
                return;
            }
            bitmapA = a(this.f9473a, messageV3.getUploadDataPackageName());
            builder.setLargeIcon(bitmapA);
        }
    }
}
