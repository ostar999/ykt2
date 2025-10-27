package com.meizu.cloud.pushsdk.notification.c;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static Field f9484a;

    /* renamed from: b, reason: collision with root package name */
    private static Field f9485b;

    /* renamed from: c, reason: collision with root package name */
    private static Field f9486c;

    /* renamed from: d, reason: collision with root package name */
    private static Object f9487d = new Object();

    /* renamed from: e, reason: collision with root package name */
    private static Map<String, Set<String>> f9488e = new ConcurrentHashMap();

    static {
        try {
            f9484a = Notification.class.getDeclaredField("mFlymeNotification");
            Field declaredField = Class.forName("android.app.NotificationExt").getDeclaredField("internalApp");
            f9485b = declaredField;
            declaredField.setAccessible(true);
            Field declaredField2 = Notification.class.getDeclaredField("replyIntent");
            f9486c = declaredField2;
            declaredField2.setAccessible(true);
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } catch (NoSuchFieldException e3) {
            DebugLogger.e("NotificationUtils", "init NotificationUtils error " + e3.getMessage());
        }
    }

    public static void a(Notification notification, PendingIntent pendingIntent) throws IllegalAccessException, IllegalArgumentException {
        Field field = f9486c;
        if (field != null) {
            try {
                field.set(notification, pendingIntent);
            } catch (IllegalAccessException e2) {
                DebugLogger.e("NotificationUtils", "setReplyIntent error " + e2.getMessage());
            }
        }
    }

    public static void a(Notification notification, boolean z2) throws IllegalAccessException, IllegalArgumentException {
        Field field = f9484a;
        if (field == null || f9485b == null) {
            return;
        }
        try {
            f9485b.set(field.get(notification), Integer.valueOf(z2 ? 1 : 0));
        } catch (IllegalAccessException e2) {
            DebugLogger.e("NotificationUtils", "setInternalApp error " + e2.getMessage());
        }
    }

    public static void a(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    public static void a(Context context, String str) {
        Set<String> set;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        if (notificationManager == null || TextUtils.isEmpty(str) || (set = f9488e.get(str)) == null) {
            return;
        }
        for (String str2 : set) {
            DebugLogger.i("NotificationUtils", "clear notifyId " + str2 + " notification");
            notificationManager.cancel(Integer.parseInt(str2));
        }
        set.clear();
    }

    public static void a(Context context, String str, int i2) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        if (notificationManager != null) {
            DebugLogger.i("NotificationUtils", "clear clearNotification notifyId " + i2);
            notificationManager.cancel(i2);
            Set<String> set = f9488e.get(str);
            if (set != null) {
                set.remove(String.valueOf(i2));
            }
        }
    }

    public static boolean a(Context context, String str, String str2) {
        synchronized (f9487d) {
            if (TextUtils.isEmpty(str2)) {
                return false;
            }
            int iH = com.meizu.cloud.pushsdk.util.b.h(context, str, str2);
            DebugLogger.e("NotificationUtils", "removeNotifyKey " + str2 + " notifyId " + iH);
            c(context, str, iH);
            return com.meizu.cloud.pushsdk.util.b.i(context, str, str2);
        }
    }

    public static void b(Context context, String str, int i2) {
        Set<String> set = f9488e.get(str);
        DebugLogger.i("NotificationUtils", "store notifyId " + i2);
        if (set != null) {
            set.add(String.valueOf(i2));
            return;
        }
        HashSet hashSet = new HashSet();
        hashSet.add(String.valueOf(i2));
        f9488e.put(str, hashSet);
    }

    public static void c(Context context, String str, int i2) {
        Set<String> set = f9488e.get(str);
        if (set != null) {
            set.remove(String.valueOf(i2));
            DebugLogger.i("NotificationUtils", "remove notifyId " + i2);
        }
    }
}
