package com.google.android.gms.internal.icing;

import android.content.Context;
import android.database.ContentObserver;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.core.content.PermissionChecker;
import javax.annotation.Nullable;

/* loaded from: classes3.dex */
final class zzbh implements zzbg {

    @GuardedBy("GservicesLoader.class")
    private static zzbh zzcr;

    @Nullable
    private final ContentObserver zzcl;

    @Nullable
    private final Context zzcs;

    private zzbh(Context context) {
        this.zzcs = context;
        zzbj zzbjVar = new zzbj(this, null);
        this.zzcl = zzbjVar;
        context.getContentResolver().registerContentObserver(zzax.CONTENT_URI, true, zzbjVar);
    }

    public static zzbh zzc(Context context) {
        zzbh zzbhVar;
        synchronized (zzbh.class) {
            if (zzcr == null) {
                zzcr = PermissionChecker.checkSelfPermission(context, "com.google.android.providers.gsf.permission.READ_GSERVICES") == 0 ? new zzbh(context) : new zzbh();
            }
            zzbhVar = zzcr;
        }
        return zzbhVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.icing.zzbg
    /* renamed from: zzj, reason: merged with bridge method [inline-methods] */
    public final String zzi(final String str) {
        if (this.zzcs == null) {
            return null;
        }
        try {
            return (String) zzbf.zza(new zzbi(this, str) { // from class: com.google.android.gms.internal.icing.zzbk
                private final zzbh zzct;
                private final String zzcu;

                {
                    this.zzct = this;
                    this.zzcu = str;
                }

                @Override // com.google.android.gms.internal.icing.zzbi
                public final Object zzl() {
                    return this.zzct.zzk(this.zzcu);
                }
            });
        } catch (IllegalStateException | SecurityException e2) {
            String strValueOf = String.valueOf(str);
            Log.e("GservicesLoader", strValueOf.length() != 0 ? "Unable to read GServices for: ".concat(strValueOf) : new String("Unable to read GServices for: "), e2);
            return null;
        }
    }

    public static synchronized void zzs() {
        Context context;
        zzbh zzbhVar = zzcr;
        if (zzbhVar != null && (context = zzbhVar.zzcs) != null && zzbhVar.zzcl != null) {
            context.getContentResolver().unregisterContentObserver(zzcr.zzcl);
        }
        zzcr = null;
    }

    public final /* synthetic */ String zzk(String str) {
        return zzax.zza(this.zzcs.getContentResolver(), str, (String) null);
    }

    private zzbh() {
        this.zzcs = null;
        this.zzcl = null;
    }
}
