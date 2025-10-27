package com.xiaomi.mipush.sdk;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/* loaded from: classes6.dex */
public class MIPushNotificationHelper4Hybrid {
    public static final String KEY_CATEGORY = "miui.category";
    public static final String KEY_MESSAGE_ID = "message_id";
    public static final String KEY_SCORE_INFO = "score_info";
    public static final String KEY_SUBST_NAME = "miui.substName";

    /* renamed from: a, reason: collision with root package name */
    private static final LinkedList<a> f24503a = new LinkedList<>();

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        int f24504a;

        /* renamed from: a, reason: collision with other field name */
        MiPushMessage f103a;

        /* renamed from: a, reason: collision with other field name */
        String f104a;

        public a(int i2, String str, MiPushMessage miPushMessage) {
            this.f24504a = i2;
            this.f104a = str;
            this.f103a = miPushMessage;
        }
    }

    private static Notification a(Notification notification) {
        Object objA = com.xiaomi.push.at.a(notification, "extraNotification");
        if (objA != null) {
            com.xiaomi.push.at.a(objA, "setCustomizedIcon", Boolean.TRUE);
        }
        return notification;
    }

    @SuppressLint({"NewApi"})
    private static Notification a(Context context, MiPushMessage miPushMessage, PendingIntent pendingIntent, Bitmap bitmap) {
        boolean z2;
        Map<String, String> extra = miPushMessage.getExtra();
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(miPushMessage.getTitle());
        builder.setContentText(miPushMessage.getDescription());
        int i2 = Build.VERSION.SDK_INT;
        builder.setStyle(new Notification.BigTextStyle().bigText(miPushMessage.getDescription()));
        builder.setWhen(System.currentTimeMillis());
        String str = extra == null ? null : extra.get("notification_show_when");
        if (!TextUtils.isEmpty(str)) {
            builder.setShowWhen(Boolean.parseBoolean(str));
        } else if (i2 >= 24) {
            builder.setShowWhen(true);
        }
        builder.setContentIntent(pendingIntent);
        int i3 = context.getApplicationInfo().icon;
        if (i3 == 0) {
            i3 = context.getApplicationInfo().logo;
        }
        builder.setSmallIcon(i3);
        if (bitmap != null) {
            builder.setLargeIcon(bitmap);
            z2 = true;
        } else {
            z2 = false;
        }
        if (extra != null && i2 >= 24) {
            String str2 = extra.get("notification_group");
            boolean z3 = Boolean.parseBoolean(extra.get("notification_is_summary"));
            com.xiaomi.push.at.a(builder, "setGroup", str2);
            com.xiaomi.push.at.a(builder, "setGroupSummary", Boolean.valueOf(z3));
        }
        builder.setAutoCancel(true);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (extra != null && extra.containsKey(RemoteMessageConst.Notification.TICKER)) {
            builder.setTicker(extra.get(RemoteMessageConst.Notification.TICKER));
        }
        if (jCurrentTimeMillis - com.xiaomi.push.service.ai.f25574a > com.heytap.mcssdk.constant.a.f7153q) {
            com.xiaomi.push.service.ai.f25574a = jCurrentTimeMillis;
            builder.setDefaults(miPushMessage.getNotifyType());
        }
        Notification notification = builder.getNotification();
        if (z2 && com.xiaomi.push.n.m679a()) {
            a(notification);
        }
        return notification;
    }

    private static PendingIntent a(Context context, String str, MiPushMessage miPushMessage) {
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.setComponent(new ComponentName(context.getPackageName(), "com.xiaomi.mipush.sdk.PushMessageHandler"));
        intent.setAction("com.xiaomi.mipush.sdk.HYBRID_NOTIFICATION_CLICK");
        intent.putExtra("mipush_payload", miPushMessage);
        intent.putExtra("mipush_hybrid_app_pkg", str);
        intent.putExtra("mipush_notified", true);
        intent.addCategory(String.valueOf(miPushMessage.getNotifyId()));
        return PendingIntent.getService(context, 0, intent, 134217728);
    }

    private static void a(Notification notification, String str) {
        try {
            Object objA = com.xiaomi.push.at.a(notification, "extraNotification");
            if (objA != null) {
                com.xiaomi.push.at.b(objA, "setMessageClassName", str);
            } else {
                com.xiaomi.channel.commonutils.logger.b.d("Get null extraNotification, setShortcutId failed.");
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void clearNotification(Context context, String str) {
        clearNotification(context, str, -1);
    }

    public static void clearNotification(Context context, String str, int i2) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        int iHashCode = ((str.hashCode() / 10) * 10) + i2;
        LinkedList linkedList = new LinkedList();
        if (i2 >= 0) {
            notificationManager.cancel(iHashCode);
        }
        LinkedList<a> linkedList2 = f24503a;
        synchronized (linkedList2) {
            Iterator<a> it = linkedList2.iterator();
            while (it.hasNext()) {
                a next = it.next();
                if (i2 >= 0) {
                    if (iHashCode == next.f24504a && TextUtils.equals(str, next.f104a)) {
                        linkedList.add(next);
                    }
                } else if (i2 == -1 && TextUtils.equals(str, next.f104a)) {
                    notificationManager.cancel(next.f24504a);
                    linkedList.add(next);
                }
            }
            LinkedList<a> linkedList3 = f24503a;
            if (linkedList3 != null) {
                linkedList3.removeAll(linkedList);
                com.xiaomi.push.service.ai.a(context, (LinkedList<? extends Object>) linkedList);
            }
        }
    }

    public static void notifyPushMessage(Context context, MiPushMessage miPushMessage, Bitmap bitmap, String str, String str2, String str3) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        PendingIntent pendingIntentA = a(context, str, miPushMessage);
        if (pendingIntentA == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("The click PendingIntent is null. ");
            return;
        }
        Notification notificationA = a(context, miPushMessage, pendingIntentA, bitmap);
        if (com.xiaomi.push.n.m679a()) {
            if (!TextUtils.isEmpty(miPushMessage.getMessageId())) {
                notificationA.extras.putString(KEY_MESSAGE_ID, miPushMessage.getMessageId());
            }
            String str4 = miPushMessage.getExtra() == null ? null : miPushMessage.getExtra().get(KEY_SCORE_INFO);
            if (!TextUtils.isEmpty(str4)) {
                notificationA.extras.putString(KEY_SCORE_INFO, str4);
            }
            if (!TextUtils.isEmpty(str)) {
                notificationA.extras.putString(KEY_CATEGORY, str);
            }
            if (!TextUtils.isEmpty(str2)) {
                notificationA.extras.putString(KEY_SUBST_NAME, str2);
            }
            if (!TextUtils.isEmpty(str3)) {
                a(notificationA, str3);
            }
        }
        int notifyId = miPushMessage.getNotifyId();
        if (str != null) {
            notifyId += (str.hashCode() / 10) * 10;
        }
        notificationManager.notify(notifyId, notificationA);
        a aVar = new a(notifyId, str, miPushMessage);
        LinkedList<a> linkedList = f24503a;
        synchronized (linkedList) {
            linkedList.add(aVar);
            if (linkedList.size() > 100) {
                linkedList.remove();
            }
        }
    }
}
