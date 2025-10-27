package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.vivo.push.PushClientConstants;
import java.util.HashMap;

/* loaded from: classes6.dex */
class al {
    public static HashMap<String, String> a(Context context, String str) {
        HashMap<String, String> map = new HashMap<>();
        try {
            map.put("appToken", d.m156a(context).b());
            map.put("regId", MiPushClient.getRegId(context));
            map.put("appId", d.m156a(context).m157a());
            map.put("regResource", d.m156a(context).e());
            if (!com.xiaomi.push.n.d()) {
                String strG = com.xiaomi.push.j.g(context);
                if (!TextUtils.isEmpty(strG)) {
                    map.put("imeiMd5", com.xiaomi.push.ay.a(strG));
                }
            }
            map.put("isMIUI", String.valueOf(com.xiaomi.push.n.m679a()));
            map.put("miuiVersion", com.xiaomi.push.n.m676a());
            map.put("devId", com.xiaomi.push.j.a(context, true));
            map.put("model", Build.MODEL);
            map.put(PushClientConstants.TAG_PKG_NAME, context.getPackageName());
            map.put(com.heytap.mcssdk.constant.b.C, "3_6_12");
            map.put("androidVersion", String.valueOf(Build.VERSION.SDK_INT));
            map.put("os", Build.VERSION.RELEASE + "-" + Build.VERSION.INCREMENTAL);
            map.put("andId", com.xiaomi.push.j.e(context));
            if (!TextUtils.isEmpty(str)) {
                map.put("clientInterfaceId", str);
            }
        } catch (Throwable unused) {
        }
        return map;
    }
}
