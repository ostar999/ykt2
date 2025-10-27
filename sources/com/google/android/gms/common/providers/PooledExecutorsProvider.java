package com.google.android.gms.common.providers;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.concurrent.ScheduledExecutorService;

@KeepForSdk
/* loaded from: classes3.dex */
public class PooledExecutorsProvider {
    private static PooledExecutorFactory zzey;

    public interface PooledExecutorFactory {
        @KeepForSdk
        ScheduledExecutorService newSingleThreadScheduledExecutor();
    }

    private PooledExecutorsProvider() {
    }

    @KeepForSdk
    public static synchronized PooledExecutorFactory getInstance() {
        if (zzey == null) {
            zzey = new zza();
        }
        return zzey;
    }
}
