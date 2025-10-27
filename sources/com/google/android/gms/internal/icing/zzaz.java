package com.google.android.gms.internal.icing;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.os.UserManager;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.RequiresApi;

/* loaded from: classes3.dex */
public class zzaz {

    @GuardedBy("DirectBootUtils.class")
    private static UserManager zzcf;
    private static volatile boolean zzcg = !zzk();

    @GuardedBy("DirectBootUtils.class")
    private static boolean zzch = false;

    private zzaz() {
    }

    public static boolean isUserUnlocked(Context context) {
        return !zzk() || zzb(context);
    }

    @RequiresApi(24)
    @TargetApi(24)
    @GuardedBy("DirectBootUtils.class")
    private static boolean zza(Context context) {
        boolean z2;
        boolean z3 = true;
        int i2 = 1;
        while (true) {
            z2 = false;
            if (i2 > 2) {
                break;
            }
            if (zzcf == null) {
                zzcf = (UserManager) context.getSystemService(UserManager.class);
            }
            UserManager userManager = zzcf;
            if (userManager == null) {
                return true;
            }
            try {
                if (userManager.isUserUnlocked()) {
                    break;
                }
                if (userManager.isUserRunning(Process.myUserHandle())) {
                    z3 = false;
                }
            } catch (NullPointerException e2) {
                Log.w("DirectBootUtils", "Failed to check if user is unlocked.", e2);
                zzcf = null;
                i2++;
            }
        }
        z2 = z3;
        if (z2) {
            zzcf = null;
        }
        return z2;
    }

    @RequiresApi(24)
    @TargetApi(24)
    private static boolean zzb(Context context) {
        if (zzcg) {
            return true;
        }
        synchronized (zzaz.class) {
            if (zzcg) {
                return true;
            }
            boolean zZza = zza(context);
            if (zZza) {
                zzcg = zZza;
            }
            return zZza;
        }
    }

    public static boolean zzk() {
        return Build.VERSION.SDK_INT >= 24;
    }
}
