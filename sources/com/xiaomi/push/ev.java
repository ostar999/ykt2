package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class ev {
    public static void a(Context context, String str, int i2, String str2) {
        ai.a(context).a(new ew(context, str, i2, str2));
    }

    private static void a(Context context, HashMap<String, String> map) {
        fe feVarM415a = fa.a(context).m415a();
        if (feVarM415a != null) {
            feVarM415a.a(context, map);
        }
    }

    private static void b(Context context, HashMap<String, String> map) {
        fe feVarM415a = fa.a(context).m415a();
        if (feVarM415a != null) {
            feVarM415a.c(context, map);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(Context context, String str, int i2, String str2) {
        if (context == null || TextUtils.isEmpty(str)) {
            return;
        }
        try {
            HashMap map = new HashMap();
            map.put("awake_info", str);
            map.put("event_type", String.valueOf(i2));
            map.put("description", str2);
            int iA = fa.a(context).a();
            if (iA == 1) {
                a(context, map);
            } else if (iA == 2) {
                c(context, map);
            } else if (iA == 3) {
                a(context, map);
                c(context, map);
            }
            b(context, map);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
    }

    private static void c(Context context, HashMap<String, String> map) {
        fe feVarM415a = fa.a(context).m415a();
        if (feVarM415a != null) {
            feVarM415a.b(context, map);
        }
    }
}
