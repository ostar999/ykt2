package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.wrappers.Wrappers;
import javax.annotation.CheckReturnValue;

@ShowFirstParty
@KeepForSdk
@CheckReturnValue
/* loaded from: classes3.dex */
public class GoogleSignatureVerifier {
    private static GoogleSignatureVerifier zzam;
    private final Context mContext;
    private volatile String zzan;

    private GoogleSignatureVerifier(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @KeepForSdk
    public static GoogleSignatureVerifier getInstance(Context context) {
        Preconditions.checkNotNull(context);
        synchronized (GoogleSignatureVerifier.class) {
            if (zzam == null) {
                zzc.zza(context);
                zzam = new GoogleSignatureVerifier(context);
            }
        }
        return zzam;
    }

    public static boolean zza(PackageInfo packageInfo, boolean z2) {
        if (packageInfo != null && packageInfo.signatures != null) {
            if ((z2 ? zza(packageInfo, zzh.zzx) : zza(packageInfo, zzh.zzx[0])) != null) {
                return true;
            }
        }
        return false;
    }

    private final zzm zzc(String str) {
        zzm zzmVarZzb;
        ApplicationInfo applicationInfo;
        if (str == null) {
            return zzm.zzb("null pkg");
        }
        if (str.equals(this.zzan)) {
            return zzm.zze();
        }
        try {
            PackageInfo packageInfo = Wrappers.packageManager(this.mContext).getPackageInfo(str, 64);
            boolean zHonorsDebugCertificates = GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext);
            if (packageInfo == null) {
                zzmVarZzb = zzm.zzb("null pkg");
            } else if (packageInfo.signatures.length != 1) {
                zzmVarZzb = zzm.zzb("single cert required");
            } else {
                zzf zzfVar = new zzf(packageInfo.signatures[0].toByteArray());
                String str2 = packageInfo.packageName;
                zzm zzmVarZza = zzc.zza(str2, zzfVar, zHonorsDebugCertificates, false);
                zzmVarZzb = (!zzmVarZza.zzad || (applicationInfo = packageInfo.applicationInfo) == null || (applicationInfo.flags & 2) == 0 || !zzc.zza(str2, zzfVar, false, true).zzad) ? zzmVarZza : zzm.zzb("debuggable release cert app rejected");
            }
            if (zzmVarZzb.zzad) {
                this.zzan = str;
            }
            return zzmVarZzb;
        } catch (PackageManager.NameNotFoundException unused) {
            return zzm.zzb(str.length() != 0 ? "no pkg ".concat(str) : new String("no pkg "));
        }
    }

    @KeepForSdk
    public boolean isGooglePublicSignedPackage(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zza(packageInfo, false)) {
            return true;
        }
        if (zza(packageInfo, true)) {
            if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext)) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isPackageGoogleSigned(String str) {
        zzm zzmVarZzc = zzc(str);
        zzmVarZzc.zzf();
        return zzmVarZzc.zzad;
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isUidGoogleSigned(int i2) {
        zzm zzmVarZzb;
        String[] packagesForUid = Wrappers.packageManager(this.mContext).getPackagesForUid(i2);
        if (packagesForUid == null || packagesForUid.length == 0) {
            zzmVarZzb = zzm.zzb("no pkgs");
        } else {
            zzmVarZzb = null;
            for (String str : packagesForUid) {
                zzmVarZzb = zza(str, i2);
                if (zzmVarZzb.zzad) {
                    break;
                }
            }
        }
        zzmVarZzb.zzf();
        return zzmVarZzb.zzad;
    }

    private final zzm zza(String str, int i2) {
        ApplicationInfo applicationInfo;
        try {
            PackageInfo packageInfoZza = Wrappers.packageManager(this.mContext).zza(str, 64, i2);
            boolean zHonorsDebugCertificates = GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext);
            if (packageInfoZza == null) {
                return zzm.zzb("null pkg");
            }
            if (packageInfoZza.signatures.length != 1) {
                return zzm.zzb("single cert required");
            }
            zzf zzfVar = new zzf(packageInfoZza.signatures[0].toByteArray());
            String str2 = packageInfoZza.packageName;
            zzm zzmVarZza = zzc.zza(str2, zzfVar, zHonorsDebugCertificates, false);
            return (!zzmVarZza.zzad || (applicationInfo = packageInfoZza.applicationInfo) == null || (applicationInfo.flags & 2) == 0 || !zzc.zza(str2, zzfVar, false, true).zzad) ? zzmVarZza : zzm.zzb("debuggable release cert app rejected");
        } catch (PackageManager.NameNotFoundException unused) {
            String strValueOf = String.valueOf(str);
            return zzm.zzb(strValueOf.length() != 0 ? "no pkg ".concat(strValueOf) : new String("no pkg "));
        }
    }

    private static zze zza(PackageInfo packageInfo, zze... zzeVarArr) {
        Signature[] signatureArr = packageInfo.signatures;
        if (signatureArr == null) {
            return null;
        }
        if (signatureArr.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzf zzfVar = new zzf(packageInfo.signatures[0].toByteArray());
        for (int i2 = 0; i2 < zzeVarArr.length; i2++) {
            if (zzeVarArr[i2].equals(zzfVar)) {
                return zzeVarArr[i2];
            }
        }
        return null;
    }
}
