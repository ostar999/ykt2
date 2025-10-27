package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.push.ic;
import com.xiaomi.push.je;
import java.security.NoSuchAlgorithmException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: classes6.dex */
public class bf {
    public static void a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        long j2 = sharedPreferences.getLong("last_sync_info", -1L);
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        long jA = com.xiaomi.push.service.ao.a(context).a(ic.SyncInfoFrequency.a(), 1209600);
        if (j2 != -1) {
            if (Math.abs(jCurrentTimeMillis - j2) <= jA) {
                return;
            } else {
                a(context, true);
            }
        }
        sharedPreferences.edit().putLong("last_sync_info", jCurrentTimeMillis).commit();
    }

    public static void a(Context context, je jeVar) {
        com.xiaomi.channel.commonutils.logger.b.m117a("need to update local info with: " + jeVar.m610a());
        String str = jeVar.m610a().get(Constants.EXTRA_KEY_ACCEPT_TIME);
        if (str != null) {
            MiPushClient.removeAcceptTime(context);
            String[] strArrSplit = str.split("-");
            if (strArrSplit.length == 2) {
                MiPushClient.addAcceptTime(context, strArrSplit[0], strArrSplit[1]);
                if ("00:00".equals(strArrSplit[0]) && "00:00".equals(strArrSplit[1])) {
                    d.m156a(context).a(true);
                } else {
                    d.m156a(context).a(false);
                }
            }
        }
        String str2 = jeVar.m610a().get(Constants.EXTRA_KEY_ALIASES);
        if (str2 != null) {
            MiPushClient.removeAllAliases(context);
            if (!"".equals(str2)) {
                for (String str3 : str2.split(",")) {
                    MiPushClient.addAlias(context, str3);
                }
            }
        }
        String str4 = jeVar.m610a().get(Constants.EXTRA_KEY_TOPICS);
        if (str4 != null) {
            MiPushClient.removeAllTopics(context);
            if (!"".equals(str4)) {
                for (String str5 : str4.split(",")) {
                    MiPushClient.addTopic(context, str5);
                }
            }
        }
        String str6 = jeVar.m610a().get(Constants.EXTRA_KEY_ACCOUNTS);
        if (str6 != null) {
            MiPushClient.removeAllAccounts(context);
            if ("".equals(str6)) {
                return;
            }
            for (String str7 : str6.split(",")) {
                MiPushClient.addAccount(context, str7);
            }
        }
    }

    public static void a(Context context, boolean z2) {
        com.xiaomi.push.ai.a(context).a(new bg(context, z2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(List<String> list) throws NoSuchAlgorithmException {
        String strA = com.xiaomi.push.ay.a(d(list));
        return (TextUtils.isEmpty(strA) || strA.length() <= 4) ? "" : strA.substring(0, 4).toLowerCase();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String d(List<String> list) {
        String str = "";
        if (com.xiaomi.push.ad.a(list)) {
            return "";
        }
        ArrayList<String> arrayList = new ArrayList(list);
        Collections.sort(arrayList, Collator.getInstance(Locale.CHINA));
        for (String str2 : arrayList) {
            if (!TextUtils.isEmpty(str)) {
                str = str + ",";
            }
            str = str + str2;
        }
        return str;
    }
}
