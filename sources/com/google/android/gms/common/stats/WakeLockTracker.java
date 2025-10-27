package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Arrays;
import java.util.List;

@KeepForSdk
/* loaded from: classes3.dex */
public class WakeLockTracker {
    private static Boolean zzgd;
    private static WakeLockTracker zzgc = new WakeLockTracker();

    @VisibleForTesting
    private static boolean zzge = false;

    @KeepForSdk
    public static WakeLockTracker getInstance() {
        return zzgc;
    }

    private static void zza(Context context, WakeLockEvent wakeLockEvent) {
        try {
            context.startService(new Intent().setComponent(LoggingConstants.zzfg).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", wakeLockEvent));
        } catch (Exception e2) {
            Log.wtf("WakeLockTracker", e2);
        }
    }

    private static boolean zzw() {
        if (zzgd == null) {
            zzgd = Boolean.FALSE;
        }
        return zzgd.booleanValue();
    }

    @KeepForSdk
    public void registerAcquireEvent(Context context, Intent intent, String str, String str2, String str3, int i2, String str4) {
        registerEvent(context, intent.getStringExtra(LoggingConstants.EXTRA_WAKE_LOCK_KEY), 7, str, str2, str3, i2, Arrays.asList(str4));
    }

    @KeepForSdk
    public void registerDeadlineEvent(Context context, String str, String str2, String str3, int i2, List<String> list, boolean z2, long j2) {
        if (zzw()) {
            zza(context, new WakeLockEvent(System.currentTimeMillis(), 16, str, i2, StatsUtils.zza(list), null, j2, com.google.android.gms.common.util.zza.zzg(context), str2, StatsUtils.zzi(context.getPackageName()), com.google.android.gms.common.util.zza.zzh(context), 0L, str3, z2));
        }
    }

    @KeepForSdk
    public void registerEvent(Context context, String str, int i2, String str2, String str3, String str4, int i3, List<String> list) {
        registerEvent(context, str, i2, str2, str3, str4, i3, list, 0L);
    }

    @KeepForSdk
    public void registerReleaseEvent(Context context, Intent intent) {
        registerEvent(context, intent.getStringExtra(LoggingConstants.EXTRA_WAKE_LOCK_KEY), 8, null, null, null, 0, null);
    }

    @KeepForSdk
    public void registerEvent(Context context, String str, int i2, String str2, String str3, String str4, int i3, List<String> list, long j2) {
        if (zzw()) {
            if (TextUtils.isEmpty(str)) {
                String strValueOf = String.valueOf(str);
                Log.e("WakeLockTracker", strValueOf.length() != 0 ? "missing wakeLock key. ".concat(strValueOf) : new String("missing wakeLock key. "));
            } else if (7 == i2 || 8 == i2 || 10 == i2 || 11 == i2) {
                zza(context, new WakeLockEvent(System.currentTimeMillis(), i2, str2, i3, StatsUtils.zza(list), str, SystemClock.elapsedRealtime(), com.google.android.gms.common.util.zza.zzg(context), str3, StatsUtils.zzi(context.getPackageName()), com.google.android.gms.common.util.zza.zzh(context), j2, str4, false));
            }
        }
    }
}
