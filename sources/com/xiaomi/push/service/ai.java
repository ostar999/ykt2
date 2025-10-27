package com.xiaomi.push.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.RemoteViews;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.xiaomi.mipush.sdk.MIPushNotificationHelper4Hybrid;
import com.xiaomi.push.fl;
import com.xiaomi.push.g;
import com.xiaomi.push.iq;
import com.xiaomi.push.jb;
import com.xiaomi.push.service.an;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class ai {

    /* renamed from: a, reason: collision with root package name */
    public static long f25574a;

    /* renamed from: a, reason: collision with other field name */
    private static final LinkedList<Pair<Integer, jb>> f987a = new LinkedList<>();

    /* renamed from: a, reason: collision with other field name */
    private static ExecutorService f988a = Executors.newCachedThreadPool();

    public static class a implements Callable<Bitmap> {

        /* renamed from: a, reason: collision with root package name */
        private Context f25575a;

        /* renamed from: a, reason: collision with other field name */
        private String f989a;

        /* renamed from: a, reason: collision with other field name */
        private boolean f990a;

        public a(String str, Context context, boolean z2) {
            this.f25575a = context;
            this.f989a = str;
            this.f990a = z2;
        }

        @Override // java.util.concurrent.Callable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Bitmap call() throws Throwable {
            Bitmap bitmapA = null;
            if (TextUtils.isEmpty(this.f989a)) {
                com.xiaomi.channel.commonutils.logger.b.m117a("Failed get online picture/icon resource cause picUrl is empty");
                return null;
            }
            if (this.f989a.startsWith("http")) {
                an.b bVarA = an.a(this.f25575a, this.f989a, this.f990a);
                if (bVarA != null) {
                    return bVarA.f999a;
                }
            } else {
                bitmapA = an.a(this.f25575a, this.f989a);
                if (bitmapA != null) {
                    return bitmapA;
                }
            }
            com.xiaomi.channel.commonutils.logger.b.m117a("Failed get online picture/icon resource");
            return bitmapA;
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        long f25576a = 0;

        /* renamed from: a, reason: collision with other field name */
        Notification f991a;
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        public long f25577a = 0;

        /* renamed from: a, reason: collision with other field name */
        public String f992a;
    }

    public static int a(Context context, String str) {
        return context.getSharedPreferences("pref_notify_type", 0).getInt(str, Integer.MAX_VALUE);
    }

    private static int a(Context context, String str, String str2) {
        if (str.equals(context.getPackageName())) {
            return context.getResources().getIdentifier(str2, "drawable", str);
        }
        return 0;
    }

    private static int a(Map<String, String> map) {
        String str = map == null ? null : map.get("timeout");
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    private static Notification.Builder a(Notification.Builder builder, Context context, String str, Map<String, String> map) {
        PendingIntent pendingIntentA = a(context, str, 1, map);
        if (pendingIntentA != null && !TextUtils.isEmpty(map.get("notification_style_button_left_name"))) {
            builder.addAction(0, map.get("notification_style_button_left_name"), pendingIntentA);
        }
        PendingIntent pendingIntentA2 = a(context, str, 2, map);
        if (pendingIntentA2 != null && !TextUtils.isEmpty(map.get("notification_style_button_mid_name"))) {
            builder.addAction(0, map.get("notification_style_button_mid_name"), pendingIntentA2);
        }
        PendingIntent pendingIntentA3 = a(context, str, 3, map);
        if (pendingIntentA3 != null && !TextUtils.isEmpty(map.get("notification_style_button_right_name"))) {
            builder.addAction(0, map.get("notification_style_button_right_name"), pendingIntentA3);
        }
        return builder;
    }

    private static Notification.Builder a(Context context, Map<String, String> map, Notification.Builder builder, String str) {
        if ("2".equals(map.get("notification_style_type"))) {
            Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle(builder);
            Bitmap bitmapA = a(context, map.get("notification_bigPic_uri"), false);
            if (bitmapA == null) {
                return builder;
            }
            bigPictureStyle.bigPicture(bitmapA);
            bigPictureStyle.bigLargeIcon((Bitmap) null);
            builder.setStyle(bigPictureStyle);
        } else if ("1".equals(map.get("notification_style_type"))) {
            builder.setStyle(new Notification.BigTextStyle().bigText(str));
        }
        return builder;
    }

    private static Notification a(Notification notification) {
        Object objA = com.xiaomi.push.at.a(notification, "extraNotification");
        if (objA != null) {
            com.xiaomi.push.at.a(objA, "setCustomizedIcon", Boolean.TRUE);
        }
        return notification;
    }

    private static Notification a(Notification notification, String str) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Field declaredField = Notification.class.getDeclaredField("extraNotification");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(notification);
            Method declaredMethod = obj.getClass().getDeclaredMethod("setTargetPkg", CharSequence.class);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(obj, str);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
        return notification;
    }

    private static PendingIntent a(Context context, jb jbVar, iq iqVar, byte[] bArr) {
        Intent intent;
        int i2 = c(jbVar) ? 1000 : m709a(jbVar) ? 3000 : -1;
        String strM554a = iqVar != null ? iqVar.m554a() : "";
        if (iqVar != null && !TextUtils.isEmpty(iqVar.f666e)) {
            intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(iqVar.f666e));
            intent.addFlags(268435456);
        } else if (m709a(jbVar)) {
            intent = new Intent();
            intent.setComponent(new ComponentName("com.xiaomi.xmsf", "com.xiaomi.mipush.sdk.PushMessageHandler"));
            intent.putExtra("mipush_payload", bArr);
            intent.putExtra("mipush_notified", true);
            intent.addCategory(String.valueOf(iqVar.c()));
        } else {
            Intent intent2 = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
            intent2.setComponent(new ComponentName(jbVar.f757b, "com.xiaomi.mipush.sdk.PushMessageHandler"));
            intent2.putExtra("mipush_payload", bArr);
            intent2.putExtra("mipush_notified", true);
            intent2.addCategory(String.valueOf(iqVar.c()));
            intent = intent2;
        }
        intent.putExtra("messageId", strM554a);
        intent.putExtra("eventMessageType", i2);
        return PendingIntent.getService(context, 0, intent, 134217728);
    }

    private static PendingIntent a(Context context, String str, int i2, Map<String, String> map) {
        Intent intentM705a;
        if (map == null || (intentM705a = m705a(context, str, i2, map)) == null) {
            return null;
        }
        return PendingIntent.getActivity(context, 0, intentM705a, 0);
    }

    private static Intent a(Context context, Intent intent) {
        try {
            if (context.getPackageManager().getPackageInfo("com.android.browser", 4) != null) {
                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            }
            return intent;
        } catch (PackageManager.NameNotFoundException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return intent;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0140  */
    /* renamed from: a, reason: collision with other method in class */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.content.Intent m705a(android.content.Context r6, java.lang.String r7, int r8, java.util.Map<java.lang.String, java.lang.String> r9) throws java.net.URISyntaxException {
        /*
            Method dump skipped, instructions count: 362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.ai.m705a(android.content.Context, java.lang.String, int, java.util.Map):android.content.Intent");
    }

    private static Bitmap a(Context context, int i2) {
        return a(context.getResources().getDrawable(i2));
    }

    private static Bitmap a(Context context, String str, boolean z2) {
        Bitmap bitmap;
        Future futureSubmit = f988a.submit(new a(str, context, z2));
        try {
            try {
                bitmap = (Bitmap) futureSubmit.get(180L, TimeUnit.SECONDS);
                if (bitmap == null) {
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
                futureSubmit.cancel(true);
                bitmap = null;
            }
            return bitmap;
        } finally {
            futureSubmit.cancel(true);
        }
    }

    public static Bitmap a(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        if (intrinsicWidth <= 0) {
            intrinsicWidth = 1;
        }
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight > 0 ? intrinsicHeight : 1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    private static RemoteViews a(Context context, jb jbVar, byte[] bArr) throws JSONException, PackageManager.NameNotFoundException {
        iq iqVarM595a = jbVar.m595a();
        String strA = a(jbVar);
        Map<String, String> mapM555a = iqVarM595a.m555a();
        if (mapM555a == null) {
            return null;
        }
        String str = mapM555a.get("layout_name");
        String str2 = mapM555a.get("layout_value");
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(strA);
                int identifier = resourcesForApplication.getIdentifier(str, TtmlNode.TAG_LAYOUT, strA);
                if (identifier == 0) {
                    return null;
                }
                RemoteViews remoteViews = new RemoteViews(strA, identifier);
                try {
                    JSONObject jSONObject = new JSONObject(str2);
                    if (jSONObject.has("text")) {
                        JSONObject jSONObject2 = jSONObject.getJSONObject("text");
                        Iterator<String> itKeys = jSONObject2.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            String string = jSONObject2.getString(next);
                            int identifier2 = resourcesForApplication.getIdentifier(next, "id", strA);
                            if (identifier2 > 0) {
                                remoteViews.setTextViewText(identifier2, string);
                            }
                        }
                    }
                    if (jSONObject.has("image")) {
                        JSONObject jSONObject3 = jSONObject.getJSONObject("image");
                        Iterator<String> itKeys2 = jSONObject3.keys();
                        while (itKeys2.hasNext()) {
                            String next2 = itKeys2.next();
                            String string2 = jSONObject3.getString(next2);
                            int identifier3 = resourcesForApplication.getIdentifier(next2, "id", strA);
                            int identifier4 = resourcesForApplication.getIdentifier(string2, "drawable", strA);
                            if (identifier3 > 0) {
                                remoteViews.setImageViewResource(identifier3, identifier4);
                            }
                        }
                    }
                    if (jSONObject.has(CrashHianalyticsData.TIME)) {
                        JSONObject jSONObject4 = jSONObject.getJSONObject(CrashHianalyticsData.TIME);
                        Iterator<String> itKeys3 = jSONObject4.keys();
                        while (itKeys3.hasNext()) {
                            String next3 = itKeys3.next();
                            String string3 = jSONObject4.getString(next3);
                            if (string3.length() == 0) {
                                string3 = "yy-MM-dd hh:mm";
                            }
                            int identifier5 = resourcesForApplication.getIdentifier(next3, "id", strA);
                            if (identifier5 > 0) {
                                remoteViews.setTextViewText(identifier5, new SimpleDateFormat(string3).format(new Date(System.currentTimeMillis())));
                            }
                        }
                    }
                    return remoteViews;
                } catch (JSONException e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                    return null;
                }
            } catch (PackageManager.NameNotFoundException e3) {
                com.xiaomi.channel.commonutils.logger.b.a(e3);
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02b3  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02ed A[Catch: Exception -> 0x037a, TryCatch #0 {Exception -> 0x037a, blocks: (B:131:0x02e1, B:133:0x02ed, B:135:0x031c, B:139:0x032f, B:141:0x033b, B:142:0x0347, B:146:0x0351, B:150:0x0364, B:151:0x036d), top: B:182:0x02e1 }] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x03f6  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x041c  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0112 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x038c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x017a  */
    @android.annotation.SuppressLint({"NewApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.xiaomi.push.service.ai.b a(android.content.Context r18, com.xiaomi.push.jb r19, byte[] r20, android.widget.RemoteViews r21, android.app.PendingIntent r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1079
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.ai.a(android.content.Context, com.xiaomi.push.jb, byte[], android.widget.RemoteViews, android.app.PendingIntent):com.xiaomi.push.service.ai$b");
    }

    /* renamed from: a, reason: collision with other method in class */
    public static c m706a(Context context, jb jbVar, byte[] bArr) {
        String string;
        StringBuilder sb;
        c cVar = new c();
        if (com.xiaomi.push.g.m438a(context, a(jbVar)) == g.a.NOT_ALLOWED) {
            iq iqVarM595a = jbVar.m595a();
            if (iqVarM595a != null) {
                fl.a(context.getApplicationContext()).a(jbVar.b(), b(jbVar), iqVarM595a.m554a(), "Do not notify because user block " + a(jbVar) + "‘s notification");
            }
            sb = new StringBuilder();
            sb.append("Do not notify because user block ");
            sb.append(a(jbVar));
        } else {
            if (!bo.a(context, jbVar)) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
                iq iqVarM595a2 = jbVar.m595a();
                RemoteViews remoteViewsA = a(context, jbVar, bArr);
                PendingIntent pendingIntentA = a(context, jbVar, iqVarM595a2, bArr);
                if (pendingIntentA == null) {
                    if (iqVarM595a2 != null) {
                        fl.a(context.getApplicationContext()).a(jbVar.b(), b(jbVar), iqVarM595a2.m554a(), "The click PendingIntent is null. ");
                    }
                    string = "The click PendingIntent is null. ";
                    com.xiaomi.channel.commonutils.logger.b.m117a(string);
                    return cVar;
                }
                b bVarA = a(context, jbVar, bArr, remoteViewsA, pendingIntentA);
                cVar.f25577a = bVarA.f25576a;
                cVar.f992a = a(jbVar);
                Notification notification = bVarA.f991a;
                if (com.xiaomi.push.n.m679a()) {
                    if (!TextUtils.isEmpty(iqVarM595a2.m554a())) {
                        notification.extras.putString(MIPushNotificationHelper4Hybrid.KEY_MESSAGE_ID, iqVarM595a2.m554a());
                    }
                    String str = iqVarM595a2.m560b() == null ? null : iqVarM595a2.m560b().get(MIPushNotificationHelper4Hybrid.KEY_SCORE_INFO);
                    if (!TextUtils.isEmpty(str)) {
                        notification.extras.putString(MIPushNotificationHelper4Hybrid.KEY_SCORE_INFO, str);
                    }
                    notification.extras.putString("eventMessageType", String.valueOf(c(jbVar) ? 1000 : m709a(jbVar) ? 3000 : -1));
                }
                String str2 = iqVarM595a2.m555a() != null ? iqVarM595a2.m555a().get("message_count") : null;
                if (com.xiaomi.push.n.m679a() && str2 != null) {
                    try {
                        a(notification, Integer.parseInt(str2));
                    } catch (NumberFormatException e2) {
                        fl.a(context.getApplicationContext()).a(jbVar.b(), b(jbVar), iqVarM595a2.m554a(), e2);
                        com.xiaomi.channel.commonutils.logger.b.a(e2);
                    }
                }
                if (!com.xiaomi.push.n.c() && "com.xiaomi.xmsf".equals(context.getPackageName())) {
                    a(notification, a(jbVar));
                }
                if ("com.xiaomi.xmsf".equals(a(jbVar))) {
                    bo.a(context, jbVar, notification);
                }
                int iHashCode = ((a(jbVar).hashCode() / 10) * 10) + iqVarM595a2.c();
                notificationManager.notify(iHashCode, notification);
                if (m709a(jbVar)) {
                    fl.a(context.getApplicationContext()).a(jbVar.b(), b(jbVar), iqVarM595a2.m554a(), 3002, "try show business message");
                }
                if (c(jbVar)) {
                    fl.a(context.getApplicationContext()).a(jbVar.b(), b(jbVar), iqVarM595a2.m554a(), 1002, "try show notification message");
                }
                if (Build.VERSION.SDK_INT < 26) {
                    com.xiaomi.push.ai aiVarA = com.xiaomi.push.ai.a(context);
                    aiVarA.m196a(iHashCode);
                    int iA = a(iqVarM595a2.m555a());
                    if (iA > 0) {
                        aiVarA.b(new aj(iHashCode, notificationManager), iA);
                    }
                }
                Pair<Integer, jb> pair = new Pair<>(Integer.valueOf(iHashCode), jbVar);
                LinkedList<Pair<Integer, jb>> linkedList = f987a;
                synchronized (linkedList) {
                    linkedList.add(pair);
                    if (linkedList.size() > 100) {
                        linkedList.remove();
                    }
                }
                return cVar;
            }
            String strA = bo.a(jbVar);
            iq iqVarM595a3 = jbVar.m595a();
            if (iqVarM595a3 != null) {
                fl.a(context.getApplicationContext()).a(jbVar.b(), b(jbVar), iqVarM595a3.m554a(), "Do not notify because user block " + strA + "‘s notification");
            }
            sb = new StringBuilder();
            sb.append("Do not notify because user block ");
            sb.append(strA);
        }
        sb.append("‘s notification");
        string = sb.toString();
        com.xiaomi.channel.commonutils.logger.b.m117a(string);
        return cVar;
    }

    private static String a(Context context, String str, Map<String, String> map) {
        return (map == null || TextUtils.isEmpty(map.get("channel_name"))) ? com.xiaomi.push.g.c(context, str) : map.get("channel_name");
    }

    public static String a(jb jbVar) {
        iq iqVarM595a;
        if ("com.xiaomi.xmsf".equals(jbVar.f757b) && (iqVarM595a = jbVar.m595a()) != null && iqVarM595a.m555a() != null) {
            String str = iqVarM595a.m555a().get("miui_package_name");
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        }
        return jbVar.f757b;
    }

    private static void a(Notification notification, int i2) {
        Object objA = com.xiaomi.push.at.a(notification, "extraNotification");
        if (objA != null) {
            com.xiaomi.push.at.a(objA, "setMessageCount", Integer.valueOf(i2));
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m707a(Context context, String str) {
        a(context, str, -1);
    }

    public static void a(Context context, String str, int i2) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        int iHashCode = ((str.hashCode() / 10) * 10) + i2;
        LinkedList linkedList = new LinkedList();
        if (i2 >= 0) {
            notificationManager.cancel(iHashCode);
        }
        LinkedList<Pair<Integer, jb>> linkedList2 = f987a;
        synchronized (linkedList2) {
            Iterator<Pair<Integer, jb>> it = linkedList2.iterator();
            while (it.hasNext()) {
                Pair<Integer, jb> next = it.next();
                jb jbVar = (jb) next.second;
                if (jbVar != null) {
                    String strA = a(jbVar);
                    if (i2 >= 0) {
                        if (iHashCode == ((Integer) next.first).intValue() && TextUtils.equals(strA, str)) {
                            linkedList.add(next);
                        }
                    } else if (i2 == -1 && TextUtils.equals(strA, str)) {
                        notificationManager.cancel(((Integer) next.first).intValue());
                        linkedList.add(next);
                    }
                }
            }
            LinkedList<Pair<Integer, jb>> linkedList3 = f987a;
            if (linkedList3 != null) {
                linkedList3.removeAll(linkedList);
                a(context, (LinkedList<? extends Object>) linkedList);
            }
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            return;
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(RemoteMessageConst.NOTIFICATION);
        LinkedList linkedList = new LinkedList();
        LinkedList<Pair<Integer, jb>> linkedList2 = f987a;
        synchronized (linkedList2) {
            Iterator<Pair<Integer, jb>> it = linkedList2.iterator();
            while (it.hasNext()) {
                Pair<Integer, jb> next = it.next();
                jb jbVar = (jb) next.second;
                if (jbVar != null) {
                    String strA = a(jbVar);
                    iq iqVarM595a = jbVar.m595a();
                    if (iqVarM595a != null && TextUtils.equals(strA, str)) {
                        String strM562c = iqVarM595a.m562c();
                        String strD = iqVarM595a.d();
                        if (!TextUtils.isEmpty(strM562c) && !TextUtils.isEmpty(strD) && a(str2, strM562c) && a(str3, strD)) {
                            notificationManager.cancel(((Integer) next.first).intValue());
                            linkedList.add(next);
                        }
                    }
                }
            }
            LinkedList<Pair<Integer, jb>> linkedList3 = f987a;
            if (linkedList3 != null) {
                linkedList3.removeAll(linkedList);
                a(context, (LinkedList<? extends Object>) linkedList);
            }
        }
    }

    public static void a(Context context, LinkedList<? extends Object> linkedList) {
        if (linkedList == null || linkedList.size() <= 0) {
            return;
        }
        bl.a(context, "category_clear_notification", "clear_notification", linkedList.size(), "");
    }

    private static void a(Object obj, Map<String, String> map) {
        if (map == null || TextUtils.isEmpty(map.get("channel_description"))) {
            return;
        }
        com.xiaomi.push.at.a(obj, "setDescription", map.get("channel_description"));
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m708a(Context context, String str) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100 && Arrays.asList(runningAppProcessInfo.pkgList).contains(str)) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(iq iqVar) {
        if (iqVar == null) {
            return false;
        }
        String strM554a = iqVar.m554a();
        return !TextUtils.isEmpty(strM554a) && strM554a.length() == 22 && "satuigm".indexOf(strM554a.charAt(0)) >= 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m709a(jb jbVar) {
        iq iqVarM595a = jbVar.m595a();
        return a(iqVarM595a) && iqVarM595a.l();
    }

    private static boolean a(String str, String str2) {
        return TextUtils.isEmpty(str) || str2.contains(str);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m710a(Map<String, String> map) {
        if (map == null || !map.containsKey("notify_foreground")) {
            return true;
        }
        return "1".equals(map.get("notify_foreground"));
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0074 A[PHI: r0 r3
      0x0074: PHI (r0v4 java.lang.String) = (r0v2 java.lang.String), (r0v5 java.lang.String) binds: [B:18:0x0072, B:10:0x004f] A[DONT_GENERATE, DONT_INLINE]
      0x0074: PHI (r3v15 java.lang.String) = (r3v14 java.lang.String), (r3v21 java.lang.String) binds: [B:18:0x0072, B:10:0x004f] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String[] a(android.content.Context r3, com.xiaomi.push.iq r4) {
        /*
            java.lang.String r0 = r4.m562c()
            java.lang.String r1 = r4.d()
            java.util.Map r4 = r4.m555a()
            if (r4 == 0) goto L75
            android.content.res.Resources r2 = r3.getResources()
            android.util.DisplayMetrics r2 = r2.getDisplayMetrics()
            int r2 = r2.widthPixels
            android.content.res.Resources r3 = r3.getResources()
            android.util.DisplayMetrics r3 = r3.getDisplayMetrics()
            float r3 = r3.density
            float r2 = (float) r2
            float r2 = r2 / r3
            r3 = 1056964608(0x3f000000, float:0.5)
            float r2 = r2 + r3
            java.lang.Float r3 = java.lang.Float.valueOf(r2)
            int r3 = r3.intValue()
            r2 = 320(0x140, float:4.48E-43)
            if (r3 > r2) goto L52
            java.lang.String r3 = "title_short"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L43
            r0 = r3
        L43:
            java.lang.String r3 = "description_short"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L75
            goto L74
        L52:
            r2 = 360(0x168, float:5.04E-43)
            if (r3 <= r2) goto L75
            java.lang.String r3 = "title_long"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L66
            r0 = r3
        L66:
            java.lang.String r3 = "description_long"
            java.lang.Object r3 = r4.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L75
        L74:
            r1 = r3
        L75:
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]
            r4 = 0
            r3[r4] = r0
            r4 = 1
            r3[r4] = r1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.ai.a(android.content.Context, com.xiaomi.push.iq):java.lang.String[]");
    }

    private static int b(Context context, String str) {
        int iA = a(context, str, "mipush_notification");
        int iA2 = a(context, str, "mipush_small_notification");
        if (iA <= 0) {
            iA = iA2 > 0 ? iA2 : context.getApplicationInfo().icon;
        }
        return iA == 0 ? context.getApplicationInfo().logo : iA;
    }

    public static String b(jb jbVar) {
        return m709a(jbVar) ? "E100002" : c(jbVar) ? "E100000" : m713b(jbVar) ? "E100001" : "";
    }

    /* renamed from: b, reason: collision with other method in class */
    public static void m711b(Context context, String str) {
        context.getSharedPreferences("pref_notify_type", 0).edit().remove(str).commit();
    }

    public static void b(Context context, String str, int i2) {
        context.getSharedPreferences("pref_notify_type", 0).edit().putInt(str, i2).commit();
    }

    /* renamed from: b, reason: collision with other method in class */
    public static boolean m712b(Context context, String str) {
        return context.getSharedPreferences("pref_notify_type", 0).contains(str);
    }

    /* renamed from: b, reason: collision with other method in class */
    public static boolean m713b(jb jbVar) {
        iq iqVarM595a = jbVar.m595a();
        return a(iqVarM595a) && iqVarM595a.f659b == 1 && !m709a(jbVar);
    }

    public static boolean c(jb jbVar) {
        iq iqVarM595a = jbVar.m595a();
        return a(iqVarM595a) && iqVarM595a.f659b == 0 && !m709a(jbVar);
    }

    public static boolean d(jb jbVar) {
        return m709a(jbVar) || c(jbVar) || m713b(jbVar);
    }
}
