package com.google.android.gms.internal.icing;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class zzbt implements zzbg {

    @GuardedBy("SharedPreferencesLoader.class")
    private static final Map<String, zzbt> zzdh = new ArrayMap();
    private final Object zzcm;
    private volatile Map<String, ?> zzcn;

    @GuardedBy("this")
    private final List<zzbd> zzco;
    private final SharedPreferences zzdi;
    private final SharedPreferences.OnSharedPreferenceChangeListener zzdj;

    private zzbt(SharedPreferences sharedPreferences) {
        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener(this) { // from class: com.google.android.gms.internal.icing.zzbw
            private final zzbt zzdu;

            {
                this.zzdu = this;
            }

            @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
            public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences2, String str) {
                this.zzdu.zza(sharedPreferences2, str);
            }
        };
        this.zzdj = onSharedPreferenceChangeListener;
        this.zzcm = new Object();
        this.zzco = new ArrayList();
        this.zzdi = sharedPreferences;
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public static zzbt zza(Context context, String str) {
        zzbt zzbtVar;
        if (!((!zzaz.zzk() || str.startsWith("direct_boot:")) ? true : zzaz.isUserUnlocked(context))) {
            return null;
        }
        synchronized (zzbt.class) {
            Map<String, zzbt> map = zzdh;
            zzbtVar = map.get(str);
            if (zzbtVar == null) {
                zzbtVar = new zzbt(zzb(context, str));
                map.put(str, zzbtVar);
            }
        }
        return zzbtVar;
    }

    private static SharedPreferences zzb(Context context, String str) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            if (!str.startsWith("direct_boot:")) {
                return context.getSharedPreferences(str, 0);
            }
            if (zzaz.zzk()) {
                context = context.createDeviceProtectedStorageContext();
            }
            return context.getSharedPreferences(str.substring(12), 0);
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    public static synchronized void zzp() {
        for (zzbt zzbtVar : zzdh.values()) {
            zzbtVar.zzdi.unregisterOnSharedPreferenceChangeListener(zzbtVar.zzdj);
        }
        zzdh.clear();
    }

    @Override // com.google.android.gms.internal.icing.zzbg
    public final Object zzi(String str) {
        Map<String, ?> map = this.zzcn;
        if (map == null) {
            synchronized (this.zzcm) {
                map = this.zzcn;
                if (map == null) {
                    StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
                    try {
                        Map<String, ?> all = this.zzdi.getAll();
                        this.zzcn = all;
                        StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                        map = all;
                    } catch (Throwable th) {
                        StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                        throw th;
                    }
                }
            }
        }
        if (map != null) {
            return map.get(str);
        }
        return null;
    }

    public final /* synthetic */ void zza(SharedPreferences sharedPreferences, String str) {
        synchronized (this.zzcm) {
            this.zzcn = null;
            zzbq.zzt();
        }
        synchronized (this) {
            Iterator<zzbd> it = this.zzco.iterator();
            while (it.hasNext()) {
                it.next().zzr();
            }
        }
    }
}
