package com.google.android.gms.common;

import android.content.Context;
import android.os.RemoteException;
import android.os.StrictMode;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import java.util.concurrent.Callable;
import javax.annotation.CheckReturnValue;

@CheckReturnValue
/* loaded from: classes3.dex */
final class zzc {
    private static volatile com.google.android.gms.common.internal.zzm zzn;
    private static final Object zzo = new Object();
    private static Context zzp;

    public static synchronized void zza(Context context) {
        if (zzp != null) {
            Log.w("GoogleCertificates", "GoogleCertificates has been initialized already");
        } else if (context != null) {
            zzp = context.getApplicationContext();
        }
    }

    private static zzm zzb(final String str, final zze zzeVar, final boolean z2, boolean z3) {
        try {
            if (zzn == null) {
                Preconditions.checkNotNull(zzp);
                synchronized (zzo) {
                    if (zzn == null) {
                        zzn = com.google.android.gms.common.internal.zzn.zzc(DynamiteModule.load(zzp, DynamiteModule.PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING, "com.google.android.gms.googlecertificates").instantiate("com.google.android.gms.common.GoogleCertificatesImpl"));
                    }
                }
            }
            Preconditions.checkNotNull(zzp);
            try {
                return zzn.zza(new zzk(str, zzeVar, z2, z3), ObjectWrapper.wrap(zzp.getPackageManager())) ? zzm.zze() : zzm.zza(new Callable(z2, str, zzeVar) { // from class: com.google.android.gms.common.zzd
                    private final boolean zzq;
                    private final String zzr;
                    private final zze zzs;

                    {
                        this.zzq = z2;
                        this.zzr = str;
                        this.zzs = zzeVar;
                    }

                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        return zzc.zza(this.zzq, this.zzr, this.zzs);
                    }
                });
            } catch (RemoteException e2) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e2);
                return zzm.zza("module call", e2);
            }
        } catch (DynamiteModule.LoadingException e3) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e3);
            String strValueOf = String.valueOf(e3.getMessage());
            return zzm.zza(strValueOf.length() != 0 ? "module init: ".concat(strValueOf) : new String("module init: "), e3);
        }
    }

    public static zzm zza(String str, zze zzeVar, boolean z2, boolean z3) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            return zzb(str, zzeVar, z2, z3);
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    public static final /* synthetic */ String zza(boolean z2, String str, zze zzeVar) throws Exception {
        boolean z3 = false;
        if (!z2 && zzb(str, zzeVar, true, false).zzad) {
            z3 = true;
        }
        return zzm.zzc(str, zzeVar, z2, z3);
    }
}
