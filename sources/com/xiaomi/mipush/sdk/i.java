package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import com.aliyun.vod.common.utils.UriUtil;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static HashMap<String, String> f24568a = new HashMap<>();

    public static MiPushMessage a(String str) throws JSONException {
        MiPushMessage miPushMessage = new MiPushMessage();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("messageId")) {
                    miPushMessage.setMessageId(jSONObject.getString("messageId"));
                }
                if (jSONObject.has("description")) {
                    miPushMessage.setDescription(jSONObject.getString("description"));
                }
                if (jSONObject.has("title")) {
                    miPushMessage.setTitle(jSONObject.getString("title"));
                }
                if (jSONObject.has("content")) {
                    miPushMessage.setContent(jSONObject.getString("content"));
                }
                if (jSONObject.has("passThrough")) {
                    miPushMessage.setPassThrough(jSONObject.getInt("passThrough"));
                }
                if (jSONObject.has("notifyType")) {
                    miPushMessage.setNotifyType(jSONObject.getInt("notifyType"));
                }
                if (jSONObject.has("messageType")) {
                    miPushMessage.setMessageType(jSONObject.getInt("messageType"));
                }
                if (jSONObject.has("alias")) {
                    miPushMessage.setAlias(jSONObject.getString("alias"));
                }
                if (jSONObject.has("topic")) {
                    miPushMessage.setTopic(jSONObject.getString("topic"));
                }
                if (jSONObject.has("user_account")) {
                    miPushMessage.setUserAccount(jSONObject.getString("user_account"));
                }
                if (jSONObject.has(RemoteMessageConst.Notification.NOTIFY_ID)) {
                    miPushMessage.setNotifyId(jSONObject.getInt(RemoteMessageConst.Notification.NOTIFY_ID));
                }
                if (jSONObject.has(UriUtil.QUERY_CATEGORY)) {
                    miPushMessage.setCategory(jSONObject.getString(UriUtil.QUERY_CATEGORY));
                }
                if (jSONObject.has("isNotified")) {
                    miPushMessage.setNotified(jSONObject.getBoolean("isNotified"));
                }
                if (jSONObject.has(PushConstants.EXTRA)) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(PushConstants.EXTRA);
                    Iterator<String> itKeys = jSONObject2.keys();
                    HashMap map = new HashMap();
                    while (itKeys != null && itKeys.hasNext()) {
                        String next = itKeys.next();
                        map.put(next, jSONObject2.getString(next));
                    }
                    if (map.size() > 0) {
                        miPushMessage.setExtra(map);
                    }
                }
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            }
        }
        return miPushMessage;
    }

    public static PushMessageReceiver a(Context context) {
        ResolveInfo next;
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.setPackage(context.getPackageName());
        try {
            List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 32);
            if (listQueryBroadcastReceivers != null) {
                Iterator<ResolveInfo> it = listQueryBroadcastReceivers.iterator();
                while (it.hasNext()) {
                    next = it.next();
                    ActivityInfo activityInfo = next.activityInfo;
                    if (activityInfo != null && activityInfo.packageName.equals(context.getPackageName())) {
                        break;
                    }
                }
                next = null;
            } else {
                next = null;
            }
            if (next != null) {
                return (PushMessageReceiver) Class.forName(next.activityInfo.name).newInstance();
            }
            return null;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            return null;
        }
    }

    public static synchronized String a(Context context, String str) {
        String str2;
        str2 = f24568a.get(str);
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        return str2;
    }

    public static String a(f fVar) {
        int i2 = k.f24570a[fVar.ordinal()];
        if (i2 == 1) {
            return "hms_push_token";
        }
        if (i2 == 2) {
            return "fcm_push_token";
        }
        if (i2 != 3) {
            return null;
        }
        return "cos_push_token";
    }

    public static HashMap<String, String> a(Context context, f fVar) throws PackageManager.NameNotFoundException {
        StringBuilder sb;
        aq aqVar;
        HashMap<String, String> map = new HashMap<>();
        String strA = a(fVar);
        if (TextUtils.isEmpty(strA)) {
            return map;
        }
        int i2 = k.f24570a[fVar.ordinal()];
        String string = null;
        ApplicationInfo applicationInfo = null;
        if (i2 != 1) {
            if (i2 == 2) {
                sb = new StringBuilder();
                sb.append("brand:");
                aqVar = aq.FCM;
            } else if (i2 == 3) {
                sb = new StringBuilder();
                sb.append("brand:");
                aqVar = aq.OPPO;
            }
            sb.append(aqVar.name());
            sb.append(Constants.WAVE_SEPARATOR);
            sb.append("token");
            sb.append(":");
            sb.append(a(context, strA));
            sb.append(Constants.WAVE_SEPARATOR);
            sb.append("package_name");
            sb.append(":");
            sb.append(context.getPackageName());
            string = sb.toString();
        } else {
            try {
                applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
            }
            string = "brand:" + n.a(context).name() + Constants.WAVE_SEPARATOR + "token:" + a(context, strA) + Constants.WAVE_SEPARATOR + "package_name:" + context.getPackageName() + Constants.WAVE_SEPARATOR + "app_id:" + (applicationInfo != null ? applicationInfo.metaData.getInt(Constants.HUAWEI_HMS_CLIENT_APPID) : -1);
        }
        map.put(Constants.ASSEMBLE_PUSH_REG_INFO, string);
        return map;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m173a(Context context) {
        boolean z2 = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        String strA = a(f.ASSEMBLE_PUSH_HUAWEI);
        String strA2 = a(f.ASSEMBLE_PUSH_FCM);
        if (!TextUtils.isEmpty(sharedPreferences.getString(strA, "")) && TextUtils.isEmpty(sharedPreferences.getString(strA2, ""))) {
            z2 = true;
        }
        if (z2) {
            az.a(context).a(2, strA);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m174a(Context context, f fVar) {
        String strA = a(fVar);
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        com.xiaomi.push.t.a(context.getSharedPreferences("mipush_extra", 0).edit().putString(strA, ""));
    }

    public static void a(Context context, f fVar, String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        String strA = a(fVar);
        if (TextUtils.isEmpty(strA)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : can not find the key of token used in sp file");
            return;
        }
        String string = sharedPreferences.getString(strA, "");
        if (!TextUtils.isEmpty(string) && str.equals(string)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : do not need to send token");
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : send token upload");
        a(fVar, str);
        be beVarA = l.a(fVar);
        if (beVarA == null) {
            return;
        }
        az.a(context).a((String) null, beVarA, fVar);
    }

    public static void a(Intent intent) {
        Bundle extras;
        if (intent == null || (extras = intent.getExtras()) == null || !extras.containsKey("pushMsg")) {
            return;
        }
        intent.putExtra(PushMessageHelper.KEY_MESSAGE, a(extras.getString("pushMsg")));
    }

    private static synchronized void a(f fVar, String str) {
        String strA = a(fVar);
        if (TextUtils.isEmpty(strA)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : can not find the key of token used in sp file");
        } else if (TextUtils.isEmpty(str)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : token is null");
        } else {
            f24568a.put(strA, str);
        }
    }

    public static void a(String str, int i2) {
        MiTinyDataClient.upload("hms_push_error", str, 1L, "error code = " + i2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m175a(Context context) {
        if (context == null) {
            return false;
        }
        return com.xiaomi.push.as.b(context);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m176a(Context context, f fVar) {
        if (l.m178a(fVar) != null) {
            return com.xiaomi.push.service.ao.a(context).a(l.m178a(fVar).a(), true);
        }
        return false;
    }

    public static String b(f fVar) {
        int i2 = k.f24570a[fVar.ordinal()];
        if (i2 == 1) {
            return "hms_push_error";
        }
        if (i2 == 2) {
            return "fcm_push_error";
        }
        if (i2 != 3) {
            return null;
        }
        return "cos_push_error";
    }

    public static void b(Context context) {
        g.a(context).register();
    }

    public static void b(Context context, f fVar, String str) {
        com.xiaomi.push.ai.a(context).a(new j(str, context, fVar));
    }

    public static void c(Context context) {
        g.a(context).unregister();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void d(Context context, f fVar, String str) {
        String strA = a(fVar);
        if (TextUtils.isEmpty(strA)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : can not find the key of token used in sp file");
            return;
        }
        com.xiaomi.push.t.a(context.getSharedPreferences("mipush_extra", 0).edit().putString(strA, str));
        com.xiaomi.channel.commonutils.logger.b.m117a("ASSEMBLE_PUSH : update sp file success!  " + str);
    }
}
