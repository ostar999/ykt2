package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.SystemClock;
import com.nirvana.tools.logger.cache.db.DBHelpTool;

/* loaded from: classes3.dex */
public final class zza {
    private static long zzgv;
    private static final IntentFilter filter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
    private static float zzgw = Float.NaN;

    @TargetApi(20)
    public static int zzg(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            return -1;
        }
        Intent intentRegisterReceiver = context.getApplicationContext().registerReceiver(null, filter);
        int i2 = ((intentRegisterReceiver == null ? 0 : intentRegisterReceiver.getIntExtra("plugged", 0)) & 7) != 0 ? 1 : 0;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return -1;
        }
        return (PlatformVersion.isAtLeastKitKatWatch() ? powerManager.isInteractive() : powerManager.isScreenOn() ? 2 : 0) | i2;
    }

    public static synchronized float zzh(Context context) {
        if (SystemClock.elapsedRealtime() - zzgv < 60000 && !Float.isNaN(zzgw)) {
            return zzgw;
        }
        if (context.getApplicationContext().registerReceiver(null, filter) != null) {
            zzgw = r5.getIntExtra(DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, -1) / r5.getIntExtra("scale", -1);
        }
        zzgv = SystemClock.elapsedRealtime();
        return zzgw;
    }
}
