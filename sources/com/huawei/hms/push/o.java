package com.huawei.hms.push;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.huawei.hms.android.HwBuildEx;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.huawei.hms.support.api.push.TransActivity;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.ResourceLoaderUtil;

/* loaded from: classes4.dex */
public class o {

    /* renamed from: a, reason: collision with root package name */
    public static int f8034a;

    public static synchronized void a(Context context, k kVar) {
        int iHashCode;
        int iHashCode2;
        int i2;
        int iHashCode3;
        if (context != null) {
            if (!a(kVar)) {
                HMSLog.d("PushSelfShowLog", "showNotification, the msg id = " + kVar.p());
                if (f8034a == 0) {
                    f8034a = (context.getPackageName() + System.currentTimeMillis()).hashCode();
                }
                if (TextUtils.isEmpty(kVar.l())) {
                    String strQ = kVar.q();
                    if (!TextUtils.isEmpty(strQ)) {
                        int iHashCode4 = strQ.hashCode();
                        kVar.a(iHashCode4);
                        HMSLog.d("PushSelfShowLog", "notification msgTag = " + iHashCode4);
                    }
                    if (kVar.s() != -1) {
                        iHashCode = kVar.s();
                        iHashCode2 = (kVar.k() + System.currentTimeMillis()).hashCode();
                        i2 = iHashCode2 + 1;
                        iHashCode3 = (kVar.s() + kVar.k() + context.getPackageName()).hashCode();
                    } else {
                        iHashCode = f8034a + 1;
                        iHashCode2 = iHashCode + 1;
                        i2 = iHashCode2 + 1;
                        iHashCode3 = i2 + 1;
                        f8034a = iHashCode3;
                    }
                } else {
                    iHashCode = (kVar.l() + kVar.k()).hashCode();
                    iHashCode2 = f8034a + 1;
                    i2 = iHashCode2 + 1;
                    f8034a = i2;
                    iHashCode3 = (kVar.l() + kVar.k() + context.getPackageName()).hashCode();
                }
                HMSLog.d("PushSelfShowLog", "notifyId:" + iHashCode + ",openNotifyId:" + iHashCode2 + ",delNotifyId:" + i2 + ",alarmNotifyId:" + iHashCode3);
                int[] iArr = new int[4];
                iArr[0] = iHashCode;
                iArr[1] = iHashCode2;
                iArr[2] = i2;
                if (kVar.f() <= 0) {
                    iHashCode3 = 0;
                }
                iArr[3] = iHashCode3;
                Notification notificationA = q.e() ? a(context, kVar, iArr) : null;
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
                if (notificationManager != null && notificationA != null) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        notificationManager.createNotificationChannel(new NotificationChannel("HwPushChannelID", context.getString(ResourceLoaderUtil.getStringId("hms_push_channel")), 3));
                    }
                    notificationManager.notify(iHashCode, notificationA);
                    d(context, kVar, iArr);
                    e.a(context, kVar.p(), kVar.b(), "100");
                }
            }
        }
    }

    public static PendingIntent b(Context context, k kVar, int[] iArr) {
        Intent intentA = a(context, kVar, iArr, "2", 268435456);
        if (!a()) {
            return PendingIntent.getBroadcast(context, iArr[2], intentA, q.b());
        }
        intentA.setClass(context, TransActivity.class);
        intentA.setFlags(268468224);
        return PendingIntent.getActivity(context, iArr[2], intentA, q.b());
    }

    public static PendingIntent c(Context context, k kVar, int[] iArr) {
        Intent intentA = a(context, kVar, iArr, "1", 268435456);
        if (!a()) {
            return PendingIntent.getBroadcast(context, iArr[1], intentA, q.b());
        }
        intentA.setClass(context, TransActivity.class);
        intentA.setFlags(268468224);
        return PendingIntent.getActivity(context, iArr[1], intentA, q.b());
    }

    public static void d(Context context, k kVar, int[] iArr) {
        HMSLog.i("PushSelfShowLog", "setAutoClear time is: " + kVar.f());
        if (kVar.f() <= 0) {
            return;
        }
        a(context, a(context, kVar, iArr, "-1", 32), kVar.f(), iArr[3]);
    }

    public static void d(k kVar, Notification.Builder builder) {
        String strU = kVar.u();
        String strJ = kVar.j();
        if (TextUtils.isEmpty(strJ)) {
            builder.setContentText(strU);
            return;
        }
        builder.setContentText(strJ);
        if (TextUtils.isEmpty(strU)) {
            return;
        }
        builder.setContentTitle(strU);
    }

    public static void c(k kVar, Notification.Builder builder) {
        builder.setTicker(kVar.x());
    }

    @SuppressLint({"NewApi"})
    public static void b(Context context, Notification.Builder builder, k kVar) {
        if ("com.huawei.android.pushagent".equals(context.getPackageName())) {
            Bundle bundle = new Bundle();
            String strK = kVar.k();
            if (TextUtils.isEmpty(strK)) {
                return;
            }
            bundle.putString("hw_origin_sender_package_name", strK);
            builder.setExtras(bundle);
        }
    }

    public static void b(k kVar, Notification.Builder builder) {
        String strT = kVar.t();
        if (TextUtils.isEmpty(strT)) {
            return;
        }
        builder.setSubText(strT);
    }

    public static boolean a() {
        return Build.VERSION.SDK_INT >= 30;
    }

    public static Intent a(Context context, k kVar, int[] iArr, String str, int i2) {
        Intent intent = new Intent("com.huawei.intent.action.PUSH_DELAY_NOTIFY");
        intent.putExtra("selfshow_info", kVar.o()).putExtra("selfshow_token", kVar.y()).putExtra("selfshow_event_id", str).putExtra("selfshow_notify_id", iArr[0]).putExtra("selfshow_auto_clear_id", iArr[3]).setPackage(context.getPackageName()).setFlags(i2);
        return intent;
    }

    public static Notification a(Context context, k kVar, int[] iArr) {
        Notification.Builder builder = new Notification.Builder(context);
        if (m.a(kVar) == n.STYLE_BIGTEXT) {
            m.a(builder, kVar.g(), kVar);
        }
        l.a(context, builder, kVar);
        b(kVar, builder);
        d(kVar, builder);
        a(context, kVar, builder);
        a(builder);
        a(kVar, builder);
        c(kVar, builder);
        builder.setContentIntent(c(context, kVar, iArr));
        builder.setDeleteIntent(b(context, kVar, iArr));
        if (Build.VERSION.SDK_INT >= 26) {
            builder.setChannelId("HwPushChannelID");
        }
        b(context, builder, kVar);
        a(context, builder, kVar);
        return builder.build();
    }

    @SuppressLint({"NewApi"})
    public static void a(Context context, Notification.Builder builder, k kVar) {
        if (HwBuildEx.VERSION.EMUI_SDK_INT < 11 || !q.a(context)) {
            return;
        }
        Bundle bundle = new Bundle();
        String strK = kVar.k();
        HMSLog.i("PushSelfShowLog", "the package name of notification is:" + strK);
        if (!TextUtils.isEmpty(strK)) {
            String strA = q.a(context, strK);
            HMSLog.i("PushSelfShowLog", "the app name is:" + strA);
            if (strA != null) {
                bundle.putCharSequence("android.extraAppName", strA);
            }
        }
        builder.setExtras(bundle);
    }

    public static void a(Context context, Intent intent, long j2, int i2) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("enter setDelayAlarm(interval:");
            sb.append(j2);
            sb.append("ms.");
            HMSLog.d("PushSelfShowLog", sb.toString());
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
            if (alarmManager != null) {
                alarmManager.set(0, System.currentTimeMillis() + j2, PendingIntent.getBroadcast(context, i2, intent, q.b()));
            }
        } catch (Exception e2) {
            HMSLog.w("PushSelfShowLog", "set DelayAlarm error." + e2.toString());
        }
    }

    public static void a(Context context, k kVar, Notification.Builder builder) {
        Bitmap bitmapA = l.a(context, kVar);
        if (bitmapA != null) {
            builder.setLargeIcon(bitmapA);
        }
    }

    public static void a(Notification.Builder builder) {
        builder.setShowWhen(true);
        builder.setWhen(System.currentTimeMillis());
    }

    public static void a(k kVar, Notification.Builder builder) {
        builder.setAutoCancel(kVar.e() == 1);
        builder.setOngoing(false);
    }

    public static boolean a(k kVar) {
        return kVar == null || (TextUtils.isEmpty(kVar.u()) && TextUtils.isEmpty(kVar.j()));
    }
}
