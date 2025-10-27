package com.google.android.gms.internal.icing;

import android.accounts.Account;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.common.internal.AccountType;
import com.google.android.gms.internal.icing.zzhm;

/* loaded from: classes3.dex */
public final class zzag {
    public static zzw zza(Action action, long j2, String str, int i2) {
        int i3;
        Bundle bundle = new Bundle();
        bundle.putAll(action.zze());
        Bundle bundle2 = bundle.getBundle("object");
        Uri uri = bundle2.containsKey("id") ? Uri.parse(bundle2.getString("id")) : null;
        String string = bundle2.getString("name");
        String string2 = bundle2.getString("type");
        Intent intentZza = zzaj.zza(str, Uri.parse(bundle2.getString("url")));
        zzg zzgVarZza = zzw.zza(intentZza, string, uri, string2, null);
        if (bundle.containsKey(".private:ssbContext")) {
            zzgVarZza.zza(zzk.zza(bundle.getByteArray(".private:ssbContext")));
            bundle.remove(".private:ssbContext");
        }
        if (bundle.containsKey(".private:accountName")) {
            zzgVarZza.zza(new Account(bundle.getString(".private:accountName"), AccountType.GOOGLE));
            bundle.remove(".private:accountName");
        }
        boolean z2 = false;
        if (bundle.containsKey(".private:isContextOnly") && bundle.getBoolean(".private:isContextOnly")) {
            bundle.remove(".private:isContextOnly");
            i3 = 4;
        } else {
            i3 = 0;
        }
        if (bundle.containsKey(".private:isDeviceOnly")) {
            z2 = bundle.getBoolean(".private:isDeviceOnly", false);
            bundle.remove(".private:isDeviceOnly");
        }
        zzgVarZza.zza(new zzk(zza(bundle).toByteArray(), new zzs(".private:action").zzb(true).zzd(".private:action").zzc("blob").zzc()));
        return new zzz().zza(zzw.zza(str, intentZza)).zza(j2).zzb(i3).zza(zzgVarZza.zzb()).zzd(z2).zzc(i2).zzd();
    }

    private static zzhm.zzb zza(Bundle bundle) {
        zzhm.zzb.zza zzaVarZzee = zzhm.zzb.zzee();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj instanceof String) {
                zzaVarZzee.zzb((zzhm.zza) ((zzdx) zzhm.zza.zzec().zzu(str).zzb((zzhm.zzc) ((zzdx) zzhm.zzc.zzeg().zzx((String) obj).zzbx())).zzbx()));
            } else if (obj instanceof Bundle) {
                zzaVarZzee.zzb((zzhm.zza) ((zzdx) zzhm.zza.zzec().zzu(str).zzb((zzhm.zzc) ((zzdx) zzhm.zzc.zzeg().zzb(zza((Bundle) obj)).zzbx())).zzbx()));
            } else {
                int i2 = 0;
                if (obj instanceof String[]) {
                    String[] strArr = (String[]) obj;
                    int length = strArr.length;
                    while (i2 < length) {
                        String str2 = strArr[i2];
                        if (str2 != null) {
                            zzaVarZzee.zzb((zzhm.zza) ((zzdx) zzhm.zza.zzec().zzu(str).zzb((zzhm.zzc) ((zzdx) zzhm.zzc.zzeg().zzx(str2).zzbx())).zzbx()));
                        }
                        i2++;
                    }
                } else if (obj instanceof Bundle[]) {
                    Bundle[] bundleArr = (Bundle[]) obj;
                    int length2 = bundleArr.length;
                    while (i2 < length2) {
                        Bundle bundle2 = bundleArr[i2];
                        if (bundle2 != null) {
                            zzaVarZzee.zzb((zzhm.zza) ((zzdx) zzhm.zza.zzec().zzu(str).zzb((zzhm.zzc) ((zzdx) zzhm.zzc.zzeg().zzb(zza(bundle2)).zzbx())).zzbx()));
                        }
                        i2++;
                    }
                } else if (obj instanceof Boolean) {
                    zzaVarZzee.zzb((zzhm.zza) ((zzdx) zzhm.zza.zzec().zzu(str).zzb((zzhm.zzc) ((zzdx) zzhm.zzc.zzeg().zzj(((Boolean) obj).booleanValue()).zzbx())).zzbx()));
                } else {
                    String strValueOf = String.valueOf(obj);
                    StringBuilder sb = new StringBuilder(strValueOf.length() + 19);
                    sb.append("Unsupported value: ");
                    sb.append(strValueOf);
                    Log.e("SearchIndex", sb.toString());
                }
            }
        }
        if (bundle.containsKey("type")) {
            zzaVarZzee.zzw(bundle.getString("type"));
        }
        return (zzhm.zzb) ((zzdx) zzaVarZzee.zzbx());
    }
}
