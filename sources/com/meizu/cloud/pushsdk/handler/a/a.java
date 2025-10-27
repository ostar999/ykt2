package com.meizu.cloud.pushsdk.handler.a;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.MessageV3;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.util.MinSdkChecker;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class a<T> implements com.meizu.cloud.pushsdk.handler.c {

    /* renamed from: a, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.handler.a f9438a;

    /* renamed from: b, reason: collision with root package name */
    private Context f9439b;

    /* renamed from: c, reason: collision with root package name */
    private Map<Integer, String> f9440c;

    public a(Context context, com.meizu.cloud.pushsdk.handler.a aVar) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        this.f9439b = context.getApplicationContext();
        this.f9438a = aVar;
        HashMap map = new HashMap();
        this.f9440c = map;
        map.put(2, "MESSAGE_TYPE_PUSH_SERVICE_V2");
        this.f9440c.put(4, "MESSAGE_TYPE_PUSH_SERVICE_V3");
        this.f9440c.put(16, "MESSAGE_TYPE_REGISTER");
        this.f9440c.put(32, "MESSAGE_TYPE_UNREGISTER");
        this.f9440c.put(8, "MESSAGE_TYPE_THROUGH");
        this.f9440c.put(64, "MESSAGE_TYPE_NOTIFICATION_CLICK");
        this.f9440c.put(128, "MESSAGE_TYPE_NOTIFICATION_DELETE");
        this.f9440c.put(256, "MESSAGE_TYPE_PUSH_SWITCH_STATUS");
        this.f9440c.put(512, "MESSAGE_TYPE_PUSH_REGISTER_STATUS");
        this.f9440c.put(2048, "MESSAGE_TYPE_PUSH_SUBTAGS_STATUS");
        this.f9440c.put(1024, "MESSAGE_TYPE_PUSH_UNREGISTER_STATUS");
        this.f9440c.put(4096, "MESSAGE_TYPE_PUSH_SUBALIAS_STATUS");
        this.f9440c.put(8192, "MESSAGE_TYPE_SCHEDULE_NOTIFICATION");
        this.f9440c.put(16384, "MESSAGE_TYPE_RECEIVE_NOTIFY_MESSAGE");
        this.f9440c.put(32768, "MESSAGE_TYPE_NOTIFICATION_STATE");
        this.f9440c.put(65536, "MESSAGE_TYPE_UPLOAD_FILE_LOG");
        this.f9440c.put(131072, "MESSAGE_TYPE_NOTIFICATION_ARRIVED");
        this.f9440c.put(262144, "MESSAGE_TYPE_NOTIFICATION_WITHDRAW");
    }

    private String a(int i2) {
        return this.f9440c.get(Integer.valueOf(i2));
    }

    public com.meizu.cloud.pushsdk.notification.c a(T t2) {
        return null;
    }

    public void a(MessageV3 messageV3) {
        com.meizu.cloud.pushsdk.notification.model.a aVarA = com.meizu.cloud.pushsdk.notification.model.a.a(messageV3);
        if (aVarA != null) {
            DebugLogger.e("AbstractMessageHandler", "delete notifyKey " + aVarA.b() + " notifyId " + aVarA.a());
            if (TextUtils.isEmpty(aVarA.b())) {
                com.meizu.cloud.pushsdk.notification.c.b.c(c(), messageV3.getUploadDataPackageName(), aVarA.a());
            } else {
                com.meizu.cloud.pushsdk.notification.c.b.a(c(), messageV3.getUploadDataPackageName(), aVarA.b());
            }
        }
    }

    public abstract void a(T t2, com.meizu.cloud.pushsdk.notification.c cVar);

    public boolean a(int i2, String str) {
        boolean zH;
        if (i2 == 0) {
            zH = com.meizu.cloud.pushsdk.util.b.e(c(), str);
        } else {
            zH = true;
            if (i2 == 1) {
                zH = com.meizu.cloud.pushsdk.util.b.h(c(), str);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(i2 == 0 ? " canNotificationMessage " : " canThroughMessage ");
        sb.append(zH);
        DebugLogger.e("AbstractMessageHandler", sb.toString());
        return zH;
    }

    public boolean a(String str) {
        try {
            return c().getPackageName().equals(new JSONObject(str).getString("appId"));
        } catch (Exception unused) {
            DebugLogger.e("AbstractMessageHandler", "parse notification error");
            return false;
        }
    }

    public com.meizu.cloud.pushsdk.handler.a b() {
        return this.f9438a;
    }

    public String b(String str) throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject(str).getJSONObject("launcher");
            return (!jSONObject.has("pkg") || TextUtils.isEmpty(jSONObject.getString("pkg"))) ? "" : jSONObject.getString("pkg");
        } catch (Exception unused) {
            DebugLogger.e("AbstractMessageHandler", "parse desk top json error");
            return "";
        }
    }

    public void b(MessageV3 messageV3) {
        if (!MinSdkChecker.isSupportSetDrawableSmallIcon()) {
            b().b(c(), MzPushMessage.fromMessageV3(messageV3));
            return;
        }
        if (MzSystemUtils.isRunningProcess(c(), messageV3.getUploadDataPackageName())) {
            DebugLogger.i("AbstractMessageHandler", "send notification arrived message to " + messageV3.getUploadDataPackageName());
            Intent intent = new Intent();
            intent.putExtra(PushConstants.MZ_PUSH_PRIVATE_MESSAGE, messageV3);
            intent.putExtra("method", PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_NOTIFICATION_ARRIVED);
            MzSystemUtils.sendMessageFromBroadcast(c(), intent, PushConstants.MZ_PUSH_ON_MESSAGE_ACTION, messageV3.getUploadDataPackageName());
        }
    }

    public void b(T t2) {
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0086 A[ADDED_TO_REGION] */
    @Override // com.meizu.cloud.pushsdk.handler.c
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean b(android.content.Intent r8) {
        /*
            r7 = this;
            boolean r0 = r7.a(r8)
            r1 = 0
            if (r0 == 0) goto L99
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "current message Type "
            r0.append(r2)
            int r2 = r7.a()
            java.lang.String r2 = r7.a(r2)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            java.lang.String r2 = "AbstractMessageHandler"
            com.meizu.cloud.pushinternal.DebugLogger.e(r2, r0)
            java.lang.Object r8 = r7.c(r8)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "current Handler message "
            r0.append(r3)
            r0.append(r8)
            java.lang.String r0 = r0.toString()
            com.meizu.cloud.pushinternal.DebugLogger.e(r2, r0)
            r7.b(r8)
            int r0 = r7.d(r8)
            r3 = 1
            if (r0 == 0) goto L66
            if (r0 == r3) goto L60
            r4 = 2
            if (r0 == r4) goto L5d
            r4 = 3
            if (r0 == r4) goto L51
        L4f:
            r3 = r1
            goto L6c
        L51:
            java.lang.String r0 = "schedule notification"
            com.meizu.cloud.pushinternal.DebugLogger.e(r2, r0)
            r7.e(r8)
            r6 = r3
            r3 = r1
            r1 = r6
            goto L6c
        L5d:
            java.lang.String r0 = "notification on time ,show message"
            goto L68
        L60:
            java.lang.String r0 = "expire notification, dont show message"
            com.meizu.cloud.pushinternal.DebugLogger.e(r2, r0)
            goto L4f
        L66:
            java.lang.String r0 = "schedule send message off, send message directly"
        L68:
            com.meizu.cloud.pushinternal.DebugLogger.e(r2, r0)
            r1 = r3
        L6c:
            boolean r0 = r7.f(r8)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "can send message "
            r4.append(r5)
            r4.append(r0)
            java.lang.String r4 = r4.toString()
            com.meizu.cloud.pushinternal.DebugLogger.e(r2, r4)
            if (r1 == 0) goto L99
            if (r3 == 0) goto L99
            if (r0 == 0) goto L99
            com.meizu.cloud.pushsdk.notification.c r0 = r7.a(r8)
            r7.a(r8, r0)
            r7.c(r8)
            java.lang.String r8 = "send message end "
            com.meizu.cloud.pushinternal.DebugLogger.e(r2, r8)
        L99:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.handler.a.a.b(android.content.Intent):boolean");
    }

    public Context c() {
        return this.f9439b;
    }

    public abstract T c(Intent intent);

    public void c(T t2) {
    }

    public int d(T t2) {
        return 0;
    }

    public String d(Intent intent) {
        String stringExtra = intent != null ? intent.getStringExtra(PushConstants.MZ_PUSH_MESSAGE_STATISTICS_IMEI_KEY) : null;
        if (!TextUtils.isEmpty(stringExtra)) {
            return stringExtra;
        }
        String deviceId = MzSystemUtils.getDeviceId(c());
        DebugLogger.e("AbstractMessageHandler", "force get deviceId " + deviceId);
        return deviceId;
    }

    public String e(Intent intent) {
        return intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_ID);
    }

    public void e(T t2) {
    }

    public String f(Intent intent) {
        return intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_SEQ_ID);
    }

    public boolean f(T t2) {
        return true;
    }

    public String g(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_SERVICE_DEFAULT_PACKAGE_NAME);
        return TextUtils.isEmpty(stringExtra) ? c().getPackageName() : stringExtra;
    }

    public String h(Intent intent) {
        String stringExtra = intent.getStringExtra(PushConstants.EXTRA_APP_PUSH_TASK_TIMES_TAMP);
        DebugLogger.e("AbstractMessageHandler", "receive push timestamp from pushservice " + stringExtra);
        return TextUtils.isEmpty(stringExtra) ? String.valueOf(System.currentTimeMillis() / 1000) : stringExtra;
    }

    public String i(Intent intent) {
        return intent.getStringExtra("method");
    }
}
