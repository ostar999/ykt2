package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.List;

@KeepForSdk
/* loaded from: classes3.dex */
public class StatsUtils {
    @KeepForSdk
    public static String getEventKey(Context context, Intent intent) {
        return String.valueOf(System.identityHashCode(intent) | (System.identityHashCode(context) << 32));
    }

    @Nullable
    public static List<String> zza(@Nullable List<String> list) {
        if (list != null && list.size() == 1 && "com.google.android.gms".equals(list.get(0))) {
            return null;
        }
        return list;
    }

    @Nullable
    public static String zzi(String str) {
        if ("com.google.android.gms".equals(str)) {
            return null;
        }
        return str;
    }

    @KeepForSdk
    public static String getEventKey(PowerManager.WakeLock wakeLock, String str) {
        String strValueOf = String.valueOf(String.valueOf((Process.myPid() << 32) | System.identityHashCode(wakeLock)));
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        String strValueOf2 = String.valueOf(str);
        return strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
    }
}
