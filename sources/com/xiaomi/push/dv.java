package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/* loaded from: classes6.dex */
final class dv implements dz {
    private void a(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            return;
        }
        try {
            if (ag.a(context, String.valueOf(12), 1L)) {
                ig igVar = new ig();
                igVar.a(str + ":" + str2);
                igVar.a(System.currentTimeMillis());
                igVar.a(hz.BroadcastAction);
                ei.a(context, igVar);
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Context context, Intent intent) {
        int iA;
        try {
            String dataString = intent.getDataString();
            if (TextUtils.isEmpty(dataString)) {
                return;
            }
            String[] strArrSplit = dataString.split(":");
            if (strArrSplit.length >= 2 && !TextUtils.isEmpty(strArrSplit[1])) {
                String str = strArrSplit[1];
                long jCurrentTimeMillis = System.currentTimeMillis();
                boolean zA = com.xiaomi.push.service.ao.a(context).a(ic.BroadcastActionCollectionSwitch.a(), true);
                if (TextUtils.equals("android.intent.action.PACKAGE_RESTARTED", intent.getAction())) {
                    if (ag.a(context, String.valueOf(12), 1L) && zA) {
                        if (TextUtils.isEmpty(eh.f24753a)) {
                            eh.f24753a += dy.f337a + ":";
                        }
                        eh.f24753a += str + "(" + jCurrentTimeMillis + "),";
                        return;
                    }
                    return;
                }
                if (TextUtils.equals("android.intent.action.PACKAGE_CHANGED", intent.getAction())) {
                    if (ag.a(context, String.valueOf(12), 1L) && zA) {
                        if (TextUtils.isEmpty(eh.f24754b)) {
                            eh.f24754b += dy.f24750b + ":";
                        }
                        eh.f24754b += str + "(" + jCurrentTimeMillis + "),";
                        return;
                    }
                    return;
                }
                if (TextUtils.equals("android.intent.action.PACKAGE_ADDED", intent.getAction())) {
                    if (intent.getExtras().getBoolean("android.intent.extra.REPLACING") || !zA) {
                        return;
                    } else {
                        iA = hz.BroadcastActionAdded.a();
                    }
                } else if (TextUtils.equals("android.intent.action.PACKAGE_REMOVED", intent.getAction())) {
                    if (intent.getExtras().getBoolean("android.intent.extra.REPLACING") || !zA) {
                        return;
                    } else {
                        iA = hz.BroadcastActionRemoved.a();
                    }
                } else if (TextUtils.equals("android.intent.action.PACKAGE_REPLACED", intent.getAction())) {
                    if (!zA) {
                        return;
                    } else {
                        iA = hz.BroadcastActionReplaced.a();
                    }
                } else if (!TextUtils.equals("android.intent.action.PACKAGE_DATA_CLEARED", intent.getAction()) || !zA) {
                    return;
                } else {
                    iA = hz.BroadcastActionDataCleared.a();
                }
                a(context, String.valueOf(iA), str);
            }
        } catch (Throwable unused) {
        }
    }

    @Override // com.xiaomi.push.dz
    public void a(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        ai.a(context).a(new dw(this, context, intent));
    }
}
