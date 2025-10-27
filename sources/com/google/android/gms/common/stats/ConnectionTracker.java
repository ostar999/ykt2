package com.google.android.gms.common.stats;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Collections;
import java.util.List;

@KeepForSdk
/* loaded from: classes3.dex */
public class ConnectionTracker {
    private static final Object zzdp = new Object();
    private static volatile ConnectionTracker zzfa = null;

    @VisibleForTesting
    private static boolean zzfb = false;
    private final List<String> zzfc;
    private final List<String> zzfd;
    private final List<String> zzfe;
    private final List<String> zzff;

    private ConnectionTracker() {
        List<String> list = Collections.EMPTY_LIST;
        this.zzfc = list;
        this.zzfd = list;
        this.zzfe = list;
        this.zzff = list;
    }

    @KeepForSdk
    public static ConnectionTracker getInstance() {
        if (zzfa == null) {
            synchronized (zzdp) {
                if (zzfa == null) {
                    zzfa = new ConnectionTracker();
                }
            }
        }
        return zzfa;
    }

    @KeepForSdk
    public boolean bindService(Context context, Intent intent, ServiceConnection serviceConnection, int i2) {
        return zza(context, context.getClass().getName(), intent, serviceConnection, i2);
    }

    @KeepForSdk
    @SuppressLint({"UntrackedBindService"})
    public void unbindService(Context context, ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);
    }

    public final boolean zza(Context context, String str, Intent intent, ServiceConnection serviceConnection, int i2) {
        ComponentName component = intent.getComponent();
        if (!(component == null ? false : ClientLibraryUtils.zzc(context, component.getPackageName()))) {
            return context.bindService(intent, serviceConnection, i2);
        }
        Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
        return false;
    }
}
