package com.google.android.gms.internal.icing;

import cn.hutool.core.text.StrPool;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.VisibleForTesting;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.util.HashMap;
import java.util.Map;

@ShowFirstParty
@VisibleForTesting
/* loaded from: classes3.dex */
public final class zzq {

    @VisibleForTesting
    static final String[] zzy;
    private static final Map<String, Integer> zzz;

    static {
        String[] strArr = {"text1", "text2", RemoteMessageConst.Notification.ICON, "intent_action", "intent_data", "intent_data_id", "intent_extra_data", "suggest_large_icon", "intent_activity", "thing_proto"};
        zzy = strArr;
        zzz = new HashMap(strArr.length);
        int i2 = 0;
        while (true) {
            String[] strArr2 = zzy;
            if (i2 >= strArr2.length) {
                return;
            }
            zzz.put(strArr2[i2], Integer.valueOf(i2));
            i2++;
        }
    }

    public static String zza(int i2) {
        if (i2 < 0) {
            return null;
        }
        String[] strArr = zzy;
        if (i2 >= strArr.length) {
            return null;
        }
        return strArr[i2];
    }

    public static int zzb(String str) {
        Integer num = zzz.get(str);
        if (num != null) {
            return num.intValue();
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 44);
        sb.append(StrPool.BRACKET_START);
        sb.append(str);
        sb.append("] is not a valid global search section name");
        throw new IllegalArgumentException(sb.toString());
    }
}
