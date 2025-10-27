package com.google.android.exoplayer2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Log;

/* loaded from: classes3.dex */
final class WakeLockManager {
    private static final String TAG = "WakeLockManager";
    private static final String WAKE_LOCK_TAG = "ExoPlayer:WakeLockManager";
    private boolean enabled;

    @Nullable
    private final PowerManager powerManager;
    private boolean stayAwake;

    @Nullable
    private PowerManager.WakeLock wakeLock;

    public WakeLockManager(Context context) {
        this.powerManager = (PowerManager) context.getApplicationContext().getSystemService("power");
    }

    @SuppressLint({"WakelockTimeout"})
    private void updateWakeLock() {
        PowerManager.WakeLock wakeLock = this.wakeLock;
        if (wakeLock == null) {
            return;
        }
        if (this.enabled && this.stayAwake) {
            wakeLock.acquire();
        } else {
            wakeLock.release();
        }
    }

    public void setEnabled(boolean z2) {
        if (z2 && this.wakeLock == null) {
            PowerManager powerManager = this.powerManager;
            if (powerManager == null) {
                Log.w(TAG, "PowerManager is null, therefore not creating the WakeLock.");
                return;
            } else {
                PowerManager.WakeLock wakeLockNewWakeLock = powerManager.newWakeLock(1, WAKE_LOCK_TAG);
                this.wakeLock = wakeLockNewWakeLock;
                wakeLockNewWakeLock.setReferenceCounted(false);
            }
        }
        this.enabled = z2;
        updateWakeLock();
    }

    public void setStayAwake(boolean z2) {
        this.stayAwake = z2;
        updateWakeLock();
    }
}
