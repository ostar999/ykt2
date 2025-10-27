package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/* loaded from: classes6.dex */
class ff implements fd {
    private void a(Context context, String str) {
        try {
            if (!TextUtils.isEmpty(str) && context != null) {
                String[] strArrSplit = str.split("/");
                if (strArrSplit.length > 0 && !TextUtils.isEmpty(strArrSplit[strArrSplit.length - 1])) {
                    String str2 = strArrSplit[strArrSplit.length - 1];
                    if (TextUtils.isEmpty(str2)) {
                        ev.a(context, com.umeng.analytics.pro.d.M, 1008, "B get a incorrect message");
                        return;
                    }
                    String strDecode = Uri.decode(str2);
                    if (TextUtils.isEmpty(strDecode)) {
                        ev.a(context, com.umeng.analytics.pro.d.M, 1008, "B get a incorrect message");
                        return;
                    }
                    String strB = eu.b(strDecode);
                    if (!TextUtils.isEmpty(strB)) {
                        ev.a(context, strB, 1007, "play with provider successfully");
                        return;
                    }
                }
            }
            ev.a(context, com.umeng.analytics.pro.d.M, 1008, "B get a incorrect message");
        } catch (Exception e2) {
            ev.a(context, com.umeng.analytics.pro.d.M, 1008, "B meet a exception" + e2.getMessage());
        }
    }

    private void a(Context context, String str, String str2) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (TextUtils.isEmpty(str2)) {
                ev.a(context, com.umeng.analytics.pro.d.M, 1008, "argument error");
                return;
            } else {
                ev.a(context, str2, 1008, "argument error");
                return;
            }
        }
        if (!ex.b(context, str)) {
            ev.a(context, str2, 1003, "B is not ready");
            return;
        }
        ev.a(context, str2, 1002, "B is ready");
        ev.a(context, str2, 1004, "A is ready");
        String strA = eu.a(str2);
        try {
            if (TextUtils.isEmpty(strA)) {
                ev.a(context, str2, 1008, "info is empty");
                return;
            }
            String type = context.getContentResolver().getType(eu.a(str, strA));
            if (TextUtils.isEmpty(type) || !"success".equals(type)) {
                ev.a(context, str2, 1008, "A is fail to help B's provider");
            } else {
                ev.a(context, str2, 1005, "A is successful");
                ev.a(context, str2, 1006, "The job is finished");
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            ev.a(context, str2, 1008, "A meet a exception when help B's provider");
        }
    }

    @Override // com.xiaomi.push.fd
    public void a(Context context, Intent intent, String str) {
        a(context, str);
    }

    @Override // com.xiaomi.push.fd
    public void a(Context context, ez ezVar) {
        if (ezVar != null) {
            a(context, ezVar.b(), ezVar.d());
        } else {
            ev.a(context, com.umeng.analytics.pro.d.M, 1008, "A receive incorrect message");
        }
    }
}
