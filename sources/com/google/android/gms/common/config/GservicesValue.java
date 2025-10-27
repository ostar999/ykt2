package com.google.android.gms.common.config;

import android.content.Context;
import android.os.Binder;
import android.os.StrictMode;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.HashSet;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
/* loaded from: classes3.dex */
public abstract class GservicesValue<T> {
    private static final Object sLock = new Object();
    private static zza zzbm;
    private static int zzbn;
    private static Context zzbo;

    @GuardedBy("sLock")
    private static HashSet<String> zzbp;
    protected final String mKey;
    protected final T zzbq;
    private T zzbr = null;

    public interface zza {
        Long getLong(String str, Long l2);

        String getString(String str, String str2);

        Boolean zza(String str, Boolean bool);

        Float zza(String str, Float f2);

        Integer zza(String str, Integer num);
    }

    public GservicesValue(String str, T t2) {
        this.mKey = str;
        this.zzbq = t2;
    }

    @KeepForSdk
    public static boolean isInitialized() {
        synchronized (sLock) {
        }
        return false;
    }

    @KeepForSdk
    public static GservicesValue<Boolean> value(String str, boolean z2) {
        return new com.google.android.gms.common.config.zza(str, Boolean.valueOf(z2));
    }

    private static boolean zzi() {
        synchronized (sLock) {
        }
        return false;
    }

    @KeepForSdk
    public final T get() {
        T t2 = this.zzbr;
        if (t2 != null) {
            return t2;
        }
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        Object obj = sLock;
        synchronized (obj) {
        }
        synchronized (obj) {
            zzbp = null;
            zzbo = null;
            try {
            } catch (Throwable th) {
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                throw th;
            }
        }
        try {
            T tZzd = zzd(this.mKey);
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
            return tZzd;
        } catch (SecurityException unused) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                T tZzd2 = zzd(this.mKey);
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                return tZzd2;
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    @KeepForSdk
    @Deprecated
    public final T getBinderSafe() {
        return get();
    }

    @VisibleForTesting
    @KeepForSdk
    public void override(T t2) {
        Log.w("GservicesValue", "GservicesValue.override(): test should probably call initForTests() first");
        this.zzbr = t2;
        synchronized (sLock) {
            zzi();
        }
    }

    @VisibleForTesting
    @KeepForSdk
    public void resetOverride() {
        this.zzbr = null;
    }

    public abstract T zzd(String str);

    @KeepForSdk
    public static GservicesValue<Long> value(String str, Long l2) {
        return new zzb(str, l2);
    }

    @KeepForSdk
    public static GservicesValue<Integer> value(String str, Integer num) {
        return new zzc(str, num);
    }

    @KeepForSdk
    public static GservicesValue<Float> value(String str, Float f2) {
        return new zzd(str, f2);
    }

    @KeepForSdk
    public static GservicesValue<String> value(String str, String str2) {
        return new zze(str, str2);
    }
}
