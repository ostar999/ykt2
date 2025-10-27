package com.meizu.cloud.pushsdk.notification;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class a implements c {

    /* renamed from: a, reason: collision with root package name */
    protected Context f9473a;

    /* renamed from: b, reason: collision with root package name */
    protected PushNotificationBuilder f9474b;

    /* renamed from: c, reason: collision with root package name */
    protected Handler f9475c;

    /* renamed from: d, reason: collision with root package name */
    private NotificationManager f9476d;

    public a(Context context, PushNotificationBuilder pushNotificationBuilder) {
        this.f9474b = pushNotificationBuilder;
        this.f9473a = context;
        this.f9475c = new Handler(context.getMainLooper());
        this.f9476d = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
    }

    private void a(Notification.Builder builder) {
        if (MinSdkChecker.isSupportNotificationChannel()) {
            DebugLogger.e("AbstractPushNotification", "support notification channel on non meizu device");
            NotificationChannel notificationChannel = new NotificationChannel("mz_push_notification_channel", "MEIZUPUSH", 3);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(-16711936);
            notificationChannel.setShowBadge(true);
            this.f9476d.createNotificationChannel(notificationChannel);
            builder.setChannelId("mz_push_notification_channel");
        }
    }

    @TargetApi(23)
    private Icon b(String str) {
        try {
            int identifier = this.f9473a.getPackageManager().getResourcesForApplication(str).getIdentifier(PushConstants.MZ_PUSH_NOTIFICATION_SMALL_ICON, "drawable", str);
            if (identifier == 0) {
                return null;
            }
            DebugLogger.i("AbstractPushNotification", "get " + str + " smallIcon success resId " + identifier);
            return Icon.createWithResource(str, identifier);
        } catch (Exception e2) {
            DebugLogger.e("AbstractPushNotification", "cannot load smallIcon form package " + str + " Error message " + e2.getMessage());
            return null;
        }
    }

    @SuppressLint({"NewApi"})
    private void c(Notification notification, MessageV3 messageV3) throws IllegalAccessException, IllegalArgumentException {
        com.meizu.cloud.pushsdk.notification.c.b.a(notification, true);
        com.meizu.cloud.pushsdk.notification.c.b.a(notification, c(messageV3));
        notification.extras.putString(PushConstants.EXTRA_ORIGINAL_NOTIFICATION_PACKAGE_NAME, messageV3.getUploadDataPackageName());
        notification.extras.putString(PushConstants.EXTRA_FLYME_GREEN_NOTIFICATION_SETTING, d(messageV3));
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_TASK_ID, messageV3.getTaskId());
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_SEQ_ID, messageV3.getSeqId());
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_DEVICE_ID, messageV3.getDeviceId());
        notification.extras.putString(PushConstants.NOTIFICATION_EXTRA_PUSH_TIMESTAMP, messageV3.getPushTimestamp());
    }

    public Notification a(MessageV3 messageV3, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws IllegalAccessException, IllegalArgumentException {
        Notification.Builder builder = new Notification.Builder(this.f9473a);
        a(builder, messageV3, pendingIntent, pendingIntent2);
        c(builder, messageV3);
        b(builder, messageV3);
        a(builder, messageV3);
        a(builder);
        Notification notificationBuild = MinSdkChecker.isSupportNotificationBuild() ? builder.build() : builder.getNotification();
        c(notificationBuild, messageV3);
        a(notificationBuild, messageV3);
        b(notificationBuild, messageV3);
        return notificationBuild;
    }

    public PendingIntent a(MessageV3 messageV3) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        intent.putExtra("method", PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE);
        intent.setClassName(messageV3.getUploadDataPackageName(), MzSystemUtils.findReceiver(this.f9473a, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, messageV3.getUploadDataPackageName()));
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        intent.setFlags(32);
        return PendingIntent.getBroadcast(this.f9473a, 0, intent, 1073741824);
    }

    public Bitmap a(Context context, String str) {
        try {
            return ((BitmapDrawable) context.getPackageManager().getApplicationIcon(str)).getBitmap();
        } catch (PackageManager.NameNotFoundException e2) {
            DebugLogger.i("AbstractPushNotification", "getappicon error " + e2.getMessage());
            return ((BitmapDrawable) context.getApplicationInfo().loadIcon(context.getPackageManager())).getBitmap();
        }
    }

    public Bitmap a(String str) {
        com.meizu.cloud.pushsdk.b.a.c cVarB = com.meizu.cloud.pushsdk.b.a.a(str).a().b();
        if (!cVarB.b() || cVarB.a() == null) {
            DebugLogger.i("AbstractPushNotification", "ANRequest On other Thread down load largeIcon " + str + "image fail");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("ANRequest On other Thread down load largeIcon ");
        sb.append(str);
        sb.append("image ");
        sb.append(cVarB.a() != null ? "success" : "fail");
        DebugLogger.i("AbstractPushNotification", sb.toString());
        return (Bitmap) cVarB.a();
    }

    public void a(Notification.Builder builder, MessageV3 messageV3) {
    }

    public void a(Notification.Builder builder, MessageV3 messageV3, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        int iL;
        builder.setContentTitle(messageV3.getTitle());
        builder.setContentText(messageV3.getContent());
        builder.setTicker(messageV3.getTitle());
        builder.setAutoCancel(true);
        if (MinSdkChecker.isSupportSendNotification()) {
            builder.setVisibility(1);
        }
        if (!MinSdkChecker.isSupportSetDrawableSmallIcon()) {
            PushNotificationBuilder pushNotificationBuilder = this.f9474b;
            if (pushNotificationBuilder != null && pushNotificationBuilder.getmStatusbarIcon() != 0) {
                iL = this.f9474b.getmStatusbarIcon();
            }
            builder.setSmallIcon(iL);
            builder.setContentIntent(pendingIntent);
            builder.setDeleteIntent(pendingIntent2);
        }
        Icon iconB = b(messageV3.getUploadDataPackageName());
        if (iconB != null) {
            builder.setSmallIcon(iconB);
            builder.setContentIntent(pendingIntent);
            builder.setDeleteIntent(pendingIntent2);
        } else {
            DebugLogger.e("AbstractPushNotification", "cannot get " + messageV3.getUploadDataPackageName() + " smallIcon");
        }
        iL = com.meizu.cloud.pushsdk.notification.c.c.l(this.f9473a);
        builder.setSmallIcon(iL);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(pendingIntent2);
    }

    public void a(Notification notification, MessageV3 messageV3) {
    }

    public void a(final NotificationManager notificationManager, final int i2, MessageV3 messageV3) throws IllegalAccessException, IllegalArgumentException {
        AdvanceSetting advanceSetting = messageV3.getmAdvanceSetting();
        if (advanceSetting != null) {
            boolean zIsHeadUpNotification = advanceSetting.isHeadUpNotification();
            boolean zIsClearNotification = advanceSetting.isClearNotification();
            if (!zIsHeadUpNotification || zIsClearNotification) {
                return;
            }
            messageV3.getmAdvanceSetting().setHeadUpNotification(false);
            messageV3.getmAdvanceSetting().getNotifyType().setSound(false);
            messageV3.getmAdvanceSetting().getNotifyType().setVibrate(false);
            final Notification notificationA = a(messageV3, a(messageV3), b(messageV3));
            this.f9475c.postDelayed(new Runnable() { // from class: com.meizu.cloud.pushsdk.notification.a.1
                @Override // java.lang.Runnable
                public void run() {
                    notificationManager.notify(i2, notificationA);
                }
            }, 5000L);
        }
    }

    public boolean a() {
        return Thread.currentThread() == this.f9473a.getMainLooper().getThread();
    }

    public PendingIntent b(MessageV3 messageV3) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
        intent.putExtra("method", PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_DELETE);
        intent.setClassName(messageV3.getPackageName(), MzSystemUtils.findReceiver(this.f9473a, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, messageV3.getPackageName()));
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        return PendingIntent.getBroadcast(this.f9473a, 0, intent, 1073741824);
    }

    public void b(Notification.Builder builder, MessageV3 messageV3) {
    }

    public void b(Notification notification, MessageV3 messageV3) {
    }

    public PendingIntent c(MessageV3 messageV3) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("custom://" + System.currentTimeMillis()));
        intent.putExtra(PushConstants.MZ_PUSH_NOTIFICATION_STATE_MESSAGE, messageV3.getNotificationMessage());
        intent.putExtra(PushConstants.NOTIFICATION_EXTRA_TASK_ID, messageV3.getTaskId());
        intent.putExtra(PushConstants.NOTIFICATION_EXTRA_SEQ_ID, messageV3.getSeqId());
        intent.putExtra(PushConstants.NOTIFICATION_EXTRA_DEVICE_ID, messageV3.getDeviceId());
        intent.putExtra(PushConstants.NOTIFICATION_EXTRA_PUSH_TIMESTAMP, messageV3.getPushTimestamp());
        intent.putExtra(PushConstants.NOTIFICATION_EXTRA_SHOW_PACKAGE_NAME, messageV3.getUploadDataPackageName());
        intent.putExtra("method", PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_STATE);
        intent.setClassName(messageV3.getPackageName(), MzSystemUtils.findReceiver(this.f9473a, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, messageV3.getPackageName()));
        intent.setAction(PushConstants.MZ_PUSH_ON_MESSAGE_ACTION);
        return PendingIntent.getBroadcast(this.f9473a, 0, intent, 1073741824);
    }

    public void c(Notification.Builder builder, MessageV3 messageV3) {
        AdvanceSetting advanceSetting = messageV3.getmAdvanceSetting();
        if (advanceSetting != null) {
            if (advanceSetting.getNotifyType() != null) {
                boolean zIsVibrate = advanceSetting.getNotifyType().isVibrate();
                boolean zIsLights = advanceSetting.getNotifyType().isLights();
                boolean zIsSound = advanceSetting.getNotifyType().isSound();
                if (zIsVibrate || zIsLights || zIsSound) {
                    int i2 = zIsVibrate ? 2 : 0;
                    if (zIsLights) {
                        i2 |= 4;
                    }
                    if (zIsSound) {
                        i2 |= 1;
                    }
                    DebugLogger.e("AbstractPushNotification", "current notification type is " + i2);
                    builder.setDefaults(i2);
                }
            }
            builder.setOngoing(!advanceSetting.isClearNotification());
            if (advanceSetting.isHeadUpNotification() && MinSdkChecker.isSupportNotificationBuild()) {
                builder.setPriority(2);
            }
        }
    }

    public String d(MessageV3 messageV3) throws JSONException {
        String string = null;
        try {
            if (!TextUtils.isEmpty(messageV3.getNotificationMessage())) {
                string = new JSONObject(messageV3.getNotificationMessage()).getJSONObject("data").getJSONObject(PushConstants.EXTRA).getString("fns");
            }
        } catch (Exception e2) {
            DebugLogger.e("AbstractPushNotification", "parse flyme notifification setting error " + e2.getMessage());
        }
        DebugLogger.i("AbstractPushNotification", "current FlymeGreen notification setting is " + string);
        return string;
    }

    @Override // com.meizu.cloud.pushsdk.notification.c
    @SuppressLint({"NewApi"})
    public void e(MessageV3 messageV3) throws IllegalAccessException, IllegalArgumentException {
        Notification notificationA = a(messageV3, a(messageV3), b(messageV3));
        int iAbs = Math.abs((int) System.currentTimeMillis());
        com.meizu.cloud.pushsdk.notification.model.a aVarA = com.meizu.cloud.pushsdk.notification.model.a.a(messageV3);
        if (aVarA != null && aVarA.a() != 0) {
            iAbs = aVarA.a();
            DebugLogger.e("AbstractPushNotification", "server notify id " + iAbs);
            if (!TextUtils.isEmpty(aVarA.b())) {
                int iH = com.meizu.cloud.pushsdk.util.b.h(this.f9473a, messageV3.getUploadDataPackageName(), aVarA.b());
                DebugLogger.e("AbstractPushNotification", "notifyKey " + aVarA.b() + " preference notifyId is " + iH);
                if (iH != 0) {
                    DebugLogger.e("AbstractPushNotification", "use preference notifyId " + iH + " and cancel it");
                    this.f9476d.cancel(iH);
                }
                DebugLogger.e("AbstractPushNotification", "store new notifyId " + iAbs + " by notifyKey " + aVarA.b());
                com.meizu.cloud.pushsdk.util.b.b(this.f9473a, messageV3.getUploadDataPackageName(), aVarA.b(), iAbs);
            }
        }
        DebugLogger.e("AbstractPushNotification", "current notify id " + iAbs);
        if (messageV3.isDiscard()) {
            if (com.meizu.cloud.pushsdk.util.b.c(this.f9473a, messageV3.getPackageName()) == 0) {
                com.meizu.cloud.pushsdk.util.b.a(this.f9473a, messageV3.getPackageName(), iAbs);
                DebugLogger.i("AbstractPushNotification", "no notification show so put notification id " + iAbs);
            }
            if (!TextUtils.isEmpty(messageV3.getTaskId())) {
                if (com.meizu.cloud.pushsdk.util.b.d(this.f9473a, messageV3.getPackageName()) == 0) {
                    com.meizu.cloud.pushsdk.util.b.b(this.f9473a, messageV3.getPackageName(), Integer.valueOf(messageV3.getTaskId()).intValue());
                } else {
                    if (Integer.valueOf(messageV3.getTaskId()).intValue() < com.meizu.cloud.pushsdk.util.b.d(this.f9473a, messageV3.getPackageName())) {
                        DebugLogger.i("AbstractPushNotification", "current package " + messageV3.getPackageName() + " taskid " + messageV3.getTaskId() + " dont show notification");
                        return;
                    }
                    com.meizu.cloud.pushsdk.util.b.b(this.f9473a, messageV3.getPackageName(), Integer.valueOf(messageV3.getTaskId()).intValue());
                    iAbs = com.meizu.cloud.pushsdk.util.b.c(this.f9473a, messageV3.getPackageName());
                }
            }
            DebugLogger.i("AbstractPushNotification", "current package " + messageV3.getPackageName() + " notificationId=" + iAbs + " taskId=" + messageV3.getTaskId());
        }
        this.f9476d.notify(iAbs, notificationA);
        a(this.f9476d, iAbs, messageV3);
    }
}
