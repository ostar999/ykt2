package com.hyphenate.notification.core;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMPushManager;
import com.hyphenate.cloud.EMCloudOperationCallback;
import com.hyphenate.cloud.EMHttpClient;
import com.hyphenate.notification.EMNotificationBuilder;
import com.hyphenate.notification.EMNotificationMessage;
import com.hyphenate.util.EMLog;
import com.luck.picture.lib.config.PictureMimeType;
import com.umeng.analytics.pro.d;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class b {
    private static final String A = "ext";
    private static final String B = "message";
    private static final String C = "event_type";
    private static final String D = "badge";
    private static final String E = "add_num";
    private static final String F = "set_num";
    private static final String G = "activity";
    private static final String H = "need_notification";
    private static final String I = "com.hyphenate.notification.intent.RECEIVE_MESSAGE";
    private static final String J = "com.hyphenate.notification.cancel.";
    private static b L = null;

    /* renamed from: c, reason: collision with root package name */
    private static final String f8780c = "em_custom_notification";

    /* renamed from: d, reason: collision with root package name */
    private static final String f8781d = "em_notification";

    /* renamed from: e, reason: collision with root package name */
    private static final String f8782e = "title";

    /* renamed from: f, reason: collision with root package name */
    private static final String f8783f = "content";

    /* renamed from: g, reason: collision with root package name */
    private static final String f8784g = "icon_url";

    /* renamed from: h, reason: collision with root package name */
    private static final String f8785h = "operation";

    /* renamed from: i, reason: collision with root package name */
    private static final String f8786i = "type";

    /* renamed from: j, reason: collision with root package name */
    private static final String f8787j = "open_url";

    /* renamed from: k, reason: collision with root package name */
    private static final String f8788k = "open_action";

    /* renamed from: l, reason: collision with root package name */
    private static final String f8789l = "open_activity";

    /* renamed from: m, reason: collision with root package name */
    private static final String f8790m = "channel_id";

    /* renamed from: n, reason: collision with root package name */
    private static final String f8791n = "channel_name";

    /* renamed from: o, reason: collision with root package name */
    private static final String f8792o = "channel_level";

    /* renamed from: p, reason: collision with root package name */
    private static final String f8793p = "id";

    /* renamed from: q, reason: collision with root package name */
    private static final String f8794q = "expires_time";

    /* renamed from: r, reason: collision with root package name */
    private static final String f8795r = "cancel_time";

    /* renamed from: s, reason: collision with root package name */
    private static final String f8796s = "auto_cancel";

    /* renamed from: t, reason: collision with root package name */
    private static final String f8797t = "sound";

    /* renamed from: u, reason: collision with root package name */
    private static final String f8798u = "vibrate";

    /* renamed from: v, reason: collision with root package name */
    private static final String f8799v = "style";

    /* renamed from: w, reason: collision with root package name */
    private static final String f8800w = "big_picture";

    /* renamed from: x, reason: collision with root package name */
    private static final String f8801x = "big_txt";

    /* renamed from: y, reason: collision with root package name */
    private static final String f8802y = "report";

    /* renamed from: z, reason: collision with root package name */
    private static final String f8803z = "task_id";
    private Handler K;
    private BroadcastReceiver M;
    private AlarmManager O;
    private BroadcastReceiver P;

    /* renamed from: b, reason: collision with root package name */
    private Context f8805b;
    private int N = 1;

    /* renamed from: a, reason: collision with root package name */
    int f8804a = 85;
    private int Q = 1;

    private b() {
        HandlerThread handlerThread = new HandlerThread("notification-thread");
        handlerThread.start();
        this.K = new Handler(handlerThread.getLooper());
    }

    public static b a() {
        if (L == null) {
            L = new b();
        }
        return L;
    }

    private void a(int i2, long j2) {
        Intent intent = new Intent(J + EMClient.getInstance().getChatConfigPrivate().k());
        intent.setPackage(this.f8805b.getPackageName());
        intent.putExtra(RemoteMessageConst.Notification.NOTIFY_ID, i2);
        Context context = this.f8805b;
        int i3 = this.Q;
        this.Q = i3 + 1;
        PendingIntent broadcast = PendingIntent.getBroadcast(context, i3, intent, 335544320);
        if (this.P == null) {
            this.P = new a();
            this.f8805b.registerReceiver(this.P, new IntentFilter(J + EMClient.getInstance().getChatConfigPrivate().k()));
        }
        this.O.setExactAndAllowWhileIdle(0, j2, broadcast);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(EMNotificationMessage eMNotificationMessage, EMNotificationBuilder.EMNotificationDefaultStyle eMNotificationDefaultStyle, String str, String str2) throws Throwable {
        String packageName = this.f8805b.getPackageName();
        Intent intent = new Intent(I);
        intent.setPackage(packageName);
        intent.addCategory(packageName);
        List<ResolveInfo> listQueryBroadcastReceivers = this.f8805b.getPackageManager().queryBroadcastReceivers(intent, 0);
        if (listQueryBroadcastReceivers.size() > 0) {
            intent.setComponent(new ComponentName(this.f8805b, listQueryBroadcastReceivers.get(0).activityInfo.name));
        } else {
            intent = new Intent(I);
            intent.setPackage(packageName);
            if (this.M == null) {
                this.M = new EMNotificationIntentReceiver();
                this.f8805b.registerReceiver(this.M, new IntentFilter(I));
            }
        }
        intent.putExtra("message", eMNotificationMessage);
        intent.putExtra(C, 0);
        this.f8805b.sendBroadcast(intent);
        if (eMNotificationMessage.isNeedNotification()) {
            Intent intent2 = new Intent(I);
            intent2.setPackage(packageName);
            intent2.addCategory(packageName);
            if (listQueryBroadcastReceivers.size() > 0) {
                intent2.setComponent(new ComponentName(this.f8805b, listQueryBroadcastReceivers.get(0).activityInfo.name));
            } else {
                intent2 = new Intent(I);
                intent2.setPackage(packageName);
            }
            intent2.putExtra("task_id", str2);
            intent2.putExtra("message", eMNotificationMessage);
            intent2.putExtra(C, 1);
            Context context = this.f8805b;
            int i2 = this.N;
            this.N = i2 + 1;
            PendingIntent broadcast = PendingIntent.getBroadcast(context, i2, intent2, 335544320);
            int identifier = this.f8805b.getResources().getIdentifier("em_push_small_icon", "drawable", packageName);
            if (identifier <= 0) {
                identifier = this.f8805b.getResources().getIdentifier("em_push_small_icon", "mipmap", packageName);
            }
            if (identifier <= 0) {
                identifier = this.f8805b.getApplicationInfo().icon;
            }
            String notificationTitle = eMNotificationMessage.getNotificationTitle();
            String notificationContent = eMNotificationMessage.getNotificationContent();
            String notificationChannelId = eMNotificationMessage.getNotificationChannelId();
            String notificationChannelName = eMNotificationMessage.getNotificationChannelName();
            int notificationChannelLevel = eMNotificationMessage.getNotificationChannelLevel();
            boolean zIsNotificationAutoClear = eMNotificationMessage.isNotificationAutoClear();
            boolean zIsNotificationSound = eMNotificationMessage.isNotificationSound();
            boolean zIsNotificationVibrate = eMNotificationMessage.isNotificationVibrate();
            int notificationNotifyId = eMNotificationMessage.getNotificationNotifyId();
            int badgeAdd = eMNotificationMessage.getBadgeAdd();
            int badgeSet = eMNotificationMessage.getBadgeSet();
            String badgeClass = eMNotificationMessage.getBadgeClass();
            NotificationManager notificationManager = (NotificationManager) this.f8805b.getSystemService(RemoteMessageConst.NOTIFICATION);
            EMNotificationBuilder eMNotificationBuilder = new EMNotificationBuilder(this.f8805b);
            eMNotificationBuilder.setSmallIcon(identifier).setAutoCancel(zIsNotificationAutoClear).setSound(zIsNotificationSound).setVibrate(zIsNotificationVibrate).setTitle(notificationTitle).setContent(notificationContent).setChannelId(notificationChannelId).setChannelName(notificationChannelName).setLevel(notificationChannelLevel).setStyle(eMNotificationDefaultStyle).setPendingIntent(broadcast);
            if (!str.isEmpty()) {
                eMNotificationBuilder.setIcon(BitmapFactory.decodeFile(str));
            }
            if (badgeAdd > 0) {
                eMNotificationBuilder.setBadgeNum(badgeAdd);
            }
            Notification notificationBuild = eMNotificationBuilder.build();
            if (notificationBuild == null) {
                return;
            }
            String strA = com.hyphenate.push.a.a.a();
            if (badgeAdd > 0 && strA.contains("XIAOMI")) {
                try {
                    Object obj = notificationBuild.getClass().getDeclaredField("extraNotification").get(notificationBuild);
                    obj.getClass().getDeclaredMethod("setMessageCount", Integer.TYPE).invoke(obj, Integer.valueOf(badgeAdd));
                } catch (Exception e2) {
                    a("Failed to set badge for Xiaomi:" + e2.getMessage());
                }
            }
            if (badgeSet > 0 && (strA.contains("HUAWEI") || strA.contains("HONOR"))) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("package", packageName);
                    bundle.putString("class", badgeClass);
                    bundle.putInt("badgenumber", badgeSet);
                    this.f8805b.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", (String) null, bundle);
                } catch (Exception e3) {
                    a("Failed to set badge for Huawei:" + e3.getMessage());
                }
            }
            if (notificationNotifyId > 0) {
                this.f8804a = notificationNotifyId;
            } else {
                this.f8804a++;
            }
            long notificationCancelTime = eMNotificationMessage.getNotificationCancelTime();
            if (notificationCancelTime > 0) {
                a(this.f8804a, notificationCancelTime);
            }
            notificationManager.notify(String.valueOf(this.f8804a), this.f8804a, notificationBuild);
        }
    }

    private void a(final EMNotificationMessage eMNotificationMessage, final String str) {
        EMNotificationBuilder.EMNotificationDefaultStyle bigTxt;
        long notificationExpiresTime = eMNotificationMessage.getNotificationExpiresTime();
        if (notificationExpiresTime > 0 && System.currentTimeMillis() - notificationExpiresTime > 0) {
            a("out of range time: task_id:" + str);
            return;
        }
        final int notificationStyle = eMNotificationMessage.getNotificationStyle();
        String notificationIconUrl = eMNotificationMessage.getNotificationIconUrl();
        if (!TextUtils.isEmpty(notificationIconUrl)) {
            final String path = new File(this.f8805b.getCacheDir(), UUID.randomUUID().toString() + PictureMimeType.PNG).getPath();
            a(notificationIconUrl, path, new EMCallBack() { // from class: com.hyphenate.notification.core.b.2
                @Override // com.hyphenate.EMCallBack
                public void onError(int i2, String str2) {
                    int i3 = notificationStyle;
                    if (i3 == 0) {
                        b.this.b(eMNotificationMessage, new EMNotificationBuilder.EMNotificationDefaultStyle(), "", str);
                        return;
                    }
                    if (i3 == 1) {
                        b.this.b(eMNotificationMessage, new EMNotificationBuilder.EMNotificationBigTextStyle().setBigTxt(eMNotificationMessage.getNotificationBigText()), "", str);
                        return;
                    }
                    if (i3 == 2) {
                        String notificationBigPicPath = eMNotificationMessage.getNotificationBigPicPath();
                        if (TextUtils.isEmpty(notificationBigPicPath)) {
                            return;
                        }
                        final File file = new File(b.this.f8805b.getCacheDir(), UUID.randomUUID().toString() + PictureMimeType.PNG);
                        b.this.a(notificationBigPicPath, file.getPath(), new EMCallBack() { // from class: com.hyphenate.notification.core.b.2.2
                            @Override // com.hyphenate.EMCallBack
                            public void onError(int i4, String str3) {
                                AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                b.this.b(eMNotificationMessage, new EMNotificationBuilder.EMNotificationDefaultStyle(), "", str);
                            }

                            @Override // com.hyphenate.EMCallBack
                            public void onProgress(int i4, String str3) {
                            }

                            @Override // com.hyphenate.EMCallBack
                            public void onSuccess() {
                                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(file.getPath());
                                AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                b.this.b(eMNotificationMessage, new EMNotificationBuilder.EMNotificationBigPicStyle().setBigPic(bitmapDecodeFile), "", str);
                            }
                        });
                    }
                }

                @Override // com.hyphenate.EMCallBack
                public void onProgress(int i2, String str2) {
                }

                @Override // com.hyphenate.EMCallBack
                public void onSuccess() {
                    int i2 = notificationStyle;
                    if (i2 == 0) {
                        b.this.b(eMNotificationMessage, new EMNotificationBuilder.EMNotificationDefaultStyle(), path, str);
                        return;
                    }
                    if (i2 == 1) {
                        b.this.b(eMNotificationMessage, new EMNotificationBuilder.EMNotificationBigTextStyle().setBigTxt(eMNotificationMessage.getNotificationBigText()), path, str);
                        return;
                    }
                    if (i2 == 2) {
                        String notificationBigPicPath = eMNotificationMessage.getNotificationBigPicPath();
                        if (TextUtils.isEmpty(notificationBigPicPath)) {
                            return;
                        }
                        final File file = new File(b.this.f8805b.getCacheDir(), UUID.randomUUID().toString() + PictureMimeType.PNG);
                        b.this.a(notificationBigPicPath, file.getPath(), new EMCallBack() { // from class: com.hyphenate.notification.core.b.2.1
                            @Override // com.hyphenate.EMCallBack
                            public void onError(int i3, String str2) {
                                AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                b bVar = b.this;
                                EMNotificationMessage eMNotificationMessage2 = eMNotificationMessage;
                                EMNotificationBuilder.EMNotificationDefaultStyle eMNotificationDefaultStyle = new EMNotificationBuilder.EMNotificationDefaultStyle();
                                AnonymousClass2 anonymousClass22 = AnonymousClass2.this;
                                bVar.b(eMNotificationMessage2, eMNotificationDefaultStyle, path, str);
                            }

                            @Override // com.hyphenate.EMCallBack
                            public void onProgress(int i3, String str2) {
                            }

                            @Override // com.hyphenate.EMCallBack
                            public void onSuccess() {
                                Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(file.getPath());
                                AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                b bVar = b.this;
                                EMNotificationMessage eMNotificationMessage2 = eMNotificationMessage;
                                EMNotificationBuilder.EMNotificationBigPicStyle bigPic = new EMNotificationBuilder.EMNotificationBigPicStyle().setBigPic(bitmapDecodeFile);
                                AnonymousClass2 anonymousClass22 = AnonymousClass2.this;
                                bVar.b(eMNotificationMessage2, bigPic, path, str);
                            }
                        });
                    }
                }
            });
            return;
        }
        if (notificationStyle == 0) {
            bigTxt = new EMNotificationBuilder.EMNotificationDefaultStyle();
        } else {
            if (notificationStyle != 1) {
                if (notificationStyle == 2) {
                    String notificationBigPicPath = eMNotificationMessage.getNotificationBigPicPath();
                    if (TextUtils.isEmpty(notificationBigPicPath)) {
                        return;
                    }
                    final File file = new File(this.f8805b.getCacheDir(), UUID.randomUUID().toString() + PictureMimeType.PNG);
                    a(notificationBigPicPath, file.getPath(), new EMCallBack() { // from class: com.hyphenate.notification.core.b.3
                        @Override // com.hyphenate.EMCallBack
                        public void onError(int i2, String str2) {
                            b.this.b(eMNotificationMessage, new EMNotificationBuilder.EMNotificationDefaultStyle(), "", str);
                        }

                        @Override // com.hyphenate.EMCallBack
                        public void onProgress(int i2, String str2) {
                        }

                        @Override // com.hyphenate.EMCallBack
                        public void onSuccess() {
                            b.this.b(eMNotificationMessage, new EMNotificationBuilder.EMNotificationBigPicStyle().setBigPic(BitmapFactory.decodeFile(file.getPath())), "", str);
                        }
                    });
                    return;
                }
                return;
            }
            bigTxt = new EMNotificationBuilder.EMNotificationBigTextStyle().setBigTxt(eMNotificationMessage.getNotificationBigText());
        }
        b(eMNotificationMessage, bigTxt, "", str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        EMLog.d(f8781d, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, final EMCallBack eMCallBack) {
        EMHttpClient.getInstance().downloadFile(str, str2, new HashMap(), new EMCloudOperationCallback() { // from class: com.hyphenate.notification.core.b.4
            @Override // com.hyphenate.cloud.CloudOperationCallback
            public void onError(String str3) {
                EMCallBack eMCallBack2 = eMCallBack;
                if (eMCallBack2 != null) {
                    eMCallBack2.onError(403, str3);
                }
            }

            @Override // com.hyphenate.cloud.CloudOperationCallback
            public void onProgress(int i2) {
            }

            @Override // com.hyphenate.cloud.CloudOperationCallback
            public void onSuccess(String str3) {
                EMCallBack eMCallBack2 = eMCallBack;
                if (eMCallBack2 != null) {
                    eMCallBack2.onSuccess();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final EMNotificationMessage eMNotificationMessage, final EMNotificationBuilder.EMNotificationDefaultStyle eMNotificationDefaultStyle, final String str, final String str2) {
        this.K.post(new Runnable() { // from class: com.hyphenate.notification.core.b.5
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                b.this.a(eMNotificationMessage, eMNotificationDefaultStyle, str, str2);
            }
        });
    }

    private boolean b(Context context) {
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    public void a(Context context) {
        this.f8805b = context.getApplicationContext();
        this.O = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
    }

    public void a(EMMessage eMMessage) {
        if (!b(this.f8805b)) {
            a("notification is disabled!");
            return;
        }
        if (eMMessage.getType() == EMMessage.Type.CMD && f8780c.equalsIgnoreCase(((EMCmdMessageBody) eMMessage.getBody()).action())) {
            try {
                Map<String, Object> mapExt = eMMessage.ext();
                String strOptString = mapExt.containsKey(f8802y) ? new JSONObject(mapExt.get(f8802y).toString()).optString("task_id") : "";
                if (!mapExt.containsKey(f8781d)) {
                    a("em_notification is not found");
                    return;
                }
                JSONObject jSONObjectAttribute = eMMessage.getJSONObjectAttribute(f8781d);
                EMNotificationMessage eMNotificationMessage = new EMNotificationMessage();
                eMNotificationMessage.setNotificationTitle(jSONObjectAttribute.optString("title"));
                eMNotificationMessage.setNotificationContent(jSONObjectAttribute.optString("content"));
                eMNotificationMessage.setNotificationStyle(jSONObjectAttribute.optInt("style"));
                eMNotificationMessage.setNotificationIconUrl(jSONObjectAttribute.optString(f8784g));
                eMNotificationMessage.setNotificationBigPicPath(jSONObjectAttribute.optString(f8800w));
                eMNotificationMessage.setNotificationBigText(jSONObjectAttribute.optString(f8801x));
                eMNotificationMessage.setNotificationChannelName(jSONObjectAttribute.optString(f8791n));
                eMNotificationMessage.setNotificationChannelId(jSONObjectAttribute.optString(f8790m));
                eMNotificationMessage.setNotificationChannelLevel(jSONObjectAttribute.optInt(f8792o, 3));
                eMNotificationMessage.setNotificationNotifyId(jSONObjectAttribute.optInt("id"));
                eMNotificationMessage.setNotificationAutoClear(jSONObjectAttribute.optInt(f8796s, 1) == 1);
                eMNotificationMessage.setNotificationSound(jSONObjectAttribute.optInt("sound") == 1);
                eMNotificationMessage.setNotificationVibrate(jSONObjectAttribute.optInt(f8798u) == 1);
                eMNotificationMessage.setNotificationExpiresTime(jSONObjectAttribute.optLong("expires_time"));
                eMNotificationMessage.setNotificationCancelTime(jSONObjectAttribute.optLong(f8795r));
                JSONObject jSONObjectOptJSONObject = jSONObjectAttribute.optJSONObject(f8785h);
                if (jSONObjectOptJSONObject != null) {
                    eMNotificationMessage.setOpenType(jSONObjectOptJSONObject.optInt("type"));
                    eMNotificationMessage.setOpenUrl(jSONObjectOptJSONObject.optString(f8787j));
                    eMNotificationMessage.setOpenAction(jSONObjectOptJSONObject.optString(f8788k));
                    eMNotificationMessage.setOpenActivity(jSONObjectOptJSONObject.optString(f8789l));
                }
                eMNotificationMessage.setExtras(jSONObjectAttribute.optString("ext"));
                JSONObject jSONObjectOptJSONObject2 = jSONObjectAttribute.optJSONObject(D);
                if (jSONObjectOptJSONObject2 != null) {
                    eMNotificationMessage.setBadgeAdd(jSONObjectOptJSONObject2.optInt(E));
                    eMNotificationMessage.setBadgeSet(jSONObjectOptJSONObject2.optInt(F));
                    eMNotificationMessage.setBadgeClass(jSONObjectOptJSONObject2.optString("activity"));
                }
                eMNotificationMessage.setNeedNotification(jSONObjectAttribute.optBoolean(H, true));
                if (eMNotificationMessage.isNeedNotification()) {
                    a(eMNotificationMessage, strOptString);
                } else {
                    b(eMNotificationMessage, new EMNotificationBuilder.EMNotificationDefaultStyle(), "", strOptString);
                }
                JSONObject jSONObject = new JSONObject();
                if (!strOptString.isEmpty()) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("task_id", strOptString);
                    jSONObject.put(f8802y, jSONObject2);
                }
                jSONObject.put(d.M, "EASEMOB");
                EMClient.getInstance().pushManager().reportPushAction(jSONObject, EMPushManager.EMPushAction.ARRIVE, new EMCallBack() { // from class: com.hyphenate.notification.core.b.1
                    @Override // com.hyphenate.EMCallBack
                    public void onError(int i2, String str) {
                        b.this.a("report failed: " + i2 + " : " + str);
                    }

                    @Override // com.hyphenate.EMCallBack
                    public void onProgress(int i2, String str) {
                    }

                    @Override // com.hyphenate.EMCallBack
                    public void onSuccess() {
                        b.this.a("report success");
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
