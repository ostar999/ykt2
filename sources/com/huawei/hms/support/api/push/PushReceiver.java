package com.huawei.hms.support.api.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.adapter.internal.CommonCode;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.huawei.hms.push.h;
import com.huawei.hms.push.s;
import com.huawei.hms.push.utils.JsonUtil;
import com.huawei.hms.push.v;
import com.huawei.hms.push.w;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.ResourceLoaderUtil;
import com.xiaomi.mipush.sdk.MIPushNotificationHelper4Hybrid;
import java.util.concurrent.RejectedExecutionException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class PushReceiver extends BroadcastReceiver {

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public Context f8082a;

        /* renamed from: b, reason: collision with root package name */
        public Intent f8083b;

        @Override // java.lang.Runnable
        public void run() {
            Intent intent = new Intent("com.huawei.push.action.MESSAGING_EVENT");
            intent.setPackage(this.f8083b.getPackage());
            try {
                JSONObject jSONObjectB = PushReceiver.b(this.f8083b);
                String string = JsonUtil.getString(jSONObjectB, "moduleName", "");
                int i2 = JsonUtil.getInt(jSONObjectB, com.alipay.sdk.authjs.a.f3175h, 0);
                int internalCode = JsonUtil.getInt(jSONObjectB, "status", 0);
                if (ErrorEnum.SUCCESS.getInternalCode() != internalCode) {
                    internalCode = ErrorEnum.ERROR_APP_SERVER_NOT_ONLINE.getInternalCode();
                }
                Bundle bundle = new Bundle();
                if ("Push".equals(string) && i2 == 1) {
                    bundle.putString("message_type", "delivery");
                    bundle.putString(MIPushNotificationHelper4Hybrid.KEY_MESSAGE_ID, JsonUtil.getString(jSONObjectB, RemoteMessageConst.MSGID, ""));
                    bundle.putInt("error", internalCode);
                    bundle.putString(CommonCode.MapKey.TRANSACTION_ID, JsonUtil.getString(jSONObjectB, "transactionId", ""));
                } else {
                    if (this.f8083b.getExtras() != null) {
                        bundle.putAll(this.f8083b.getExtras());
                    }
                    bundle.putString("message_type", "received_message");
                    bundle.putString(MIPushNotificationHelper4Hybrid.KEY_MESSAGE_ID, this.f8083b.getStringExtra("msgIdStr"));
                    bundle.putByteArray(RemoteMessageConst.MSGBODY, this.f8083b.getByteArrayExtra("msg_data"));
                    bundle.putString(RemoteMessageConst.DEVICE_TOKEN, w.a(this.f8083b.getByteArrayExtra(RemoteMessageConst.DEVICE_TOKEN)));
                    bundle.putInt(RemoteMessageConst.INPUT_TYPE, 1);
                    bundle.putString("message_proxy_type", this.f8083b.getStringExtra("message_proxy_type"));
                }
                if (new h().a(this.f8082a, bundle, intent)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("receive ");
                    sb.append(this.f8083b.getAction());
                    sb.append(" and start service success");
                    HMSLog.i("PushReceiver", sb.toString());
                    return;
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append("receive ");
                sb2.append(this.f8083b.getAction());
                sb2.append(" and start service failed");
                HMSLog.e("PushReceiver", sb2.toString());
            } catch (RuntimeException unused) {
                HMSLog.e("PushReceiver", "handle push message occur exception.");
            }
        }

        public a(Context context, Intent intent) {
            this.f8082a = context;
            this.f8083b = intent;
        }
    }

    public static class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public Context f8084a;

        /* renamed from: b, reason: collision with root package name */
        public Intent f8085b;

        @Override // java.lang.Runnable
        public void run() {
            try {
                byte[] byteArrayExtra = this.f8085b.getByteArrayExtra(RemoteMessageConst.DEVICE_TOKEN);
                if (byteArrayExtra != null && byteArrayExtra.length != 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("receive a push token: ");
                    sb.append(this.f8084a.getPackageName());
                    HMSLog.i("PushReceiver", sb.toString());
                    Intent intent = new Intent("com.huawei.push.action.MESSAGING_EVENT");
                    intent.setPackage(this.f8085b.getPackage());
                    Bundle bundle = new Bundle();
                    bundle.putString("message_type", "new_token");
                    bundle.putString(RemoteMessageConst.DEVICE_TOKEN, w.a(byteArrayExtra));
                    bundle.putString(CommonCode.MapKey.TRANSACTION_ID, this.f8085b.getStringExtra(CommonCode.MapKey.TRANSACTION_ID));
                    bundle.putString("subjectId", this.f8085b.getStringExtra("subjectId"));
                    bundle.putInt("error", this.f8085b.getIntExtra("error", ErrorEnum.SUCCESS.getInternalCode()));
                    bundle.putString("belongId", this.f8085b.getStringExtra("belongId"));
                    if (new h().a(this.f8084a, bundle, intent)) {
                        return;
                    }
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("receive ");
                    sb2.append(this.f8085b.getAction());
                    sb2.append(" and start service failed");
                    HMSLog.e("PushReceiver", sb2.toString());
                    return;
                }
                HMSLog.i("PushReceiver", "get a deviceToken, but it is null or empty");
            } catch (RejectedExecutionException unused) {
                HMSLog.e("PushReceiver", "execute task error");
            } catch (Exception unused2) {
                HMSLog.e("PushReceiver", "handle push token error");
            }
        }

        public b(Context context, Intent intent) {
            this.f8084a = context;
            this.f8085b = intent;
        }
    }

    public final void b(Context context, Intent intent) {
        try {
            if (intent.hasExtra(RemoteMessageConst.DEVICE_TOKEN)) {
                v.a().execute(new b(context, intent));
            } else {
                HMSLog.i("PushReceiver", "This message dose not sent by hwpush.");
            }
        } catch (RuntimeException unused) {
            HMSLog.e("PushReceiver", "handlePushMessageEvent execute task runtime exception.");
        } catch (Exception unused2) {
            HMSLog.e("PushReceiver", "handlePushTokenEvent execute task error");
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || context == null) {
            return;
        }
        HMSLog.i("PushReceiver", "push receive broadcast message, Intent:" + intent.getAction() + " pkgName:" + context.getPackageName());
        try {
            intent.getStringExtra("TestIntent");
            String action = intent.getAction();
            if (ResourceLoaderUtil.getmContext() == null) {
                ResourceLoaderUtil.setmContext(context.getApplicationContext());
            }
            if ("com.huawei.android.push.intent.REGISTRATION".equals(action)) {
                b(context, intent);
            } else if ("com.huawei.android.push.intent.RECEIVE".equals(action)) {
                a(context, intent);
            } else {
                HMSLog.i("PushReceiver", "message can't be recognised.");
            }
        } catch (Exception unused) {
            HMSLog.e("PushReceiver", "intent has some error");
        }
    }

    public final void a(Context context, Intent intent) {
        try {
            if (intent.hasExtra("msg_data")) {
                v.a().execute(new a(context, intent));
            } else {
                HMSLog.i("PushReceiver", "This push message dose not sent by hwpush.");
            }
        } catch (RuntimeException unused) {
            HMSLog.e("PushReceiver", "handlePushMessageEvent execute task runtime exception.");
        } catch (Exception unused2) {
            HMSLog.e("PushReceiver", "handlePushMessageEvent execute task error");
        }
    }

    public static JSONObject b(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.PS_CONTENT);
        }
        return null;
    }

    public static JSONObject a(byte[] bArr) {
        try {
            return new JSONObject(w.a(bArr));
        } catch (JSONException unused) {
            HMSLog.w("PushReceiver", "JSONException:parse message body failed.");
            return null;
        }
    }

    public static JSONObject b(Intent intent) throws RuntimeException {
        JSONObject jSONObjectA = a(intent.getByteArrayExtra("msg_data"));
        JSONObject jSONObjectA2 = a(jSONObjectA);
        String string = JsonUtil.getString(jSONObjectA2, "data", null);
        if (s.a(jSONObjectA2, b(jSONObjectA2), string)) {
            return jSONObjectA;
        }
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return new JSONObject(string);
        } catch (JSONException unused) {
            return null;
        }
    }

    public static JSONObject a(JSONObject jSONObject) {
        if (jSONObject != null) {
            return jSONObject.optJSONObject(RemoteMessageConst.MessageBody.MSG_CONTENT);
        }
        return null;
    }
}
