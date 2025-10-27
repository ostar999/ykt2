package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.common.zzg;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@KeepForSdk
/* loaded from: classes3.dex */
public class AndroidUtilsLight {
    private static volatile int zzgf = -1;

    @KeepForSdk
    @TargetApi(24)
    @Deprecated
    public static Context getDeviceProtectedStorageContext(Context context) {
        return zzg.zzam() ? zzg.getDeviceProtectedStorageContext(context) : context;
    }

    @KeepForSdk
    public static byte[] getPackageCertificateHashBytes(Context context, String str) throws PackageManager.NameNotFoundException {
        MessageDigest messageDigestZzj;
        PackageInfo packageInfo = Wrappers.packageManager(context).getPackageInfo(str, 64);
        Signature[] signatureArr = packageInfo.signatures;
        if (signatureArr == null || signatureArr.length != 1 || (messageDigestZzj = zzj("SHA1")) == null) {
            return null;
        }
        return messageDigestZzj.digest(packageInfo.signatures[0].toByteArray());
    }

    public static MessageDigest zzj(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        for (int i2 = 0; i2 < 2; i2++) {
            try {
                messageDigest = MessageDigest.getInstance(str);
            } catch (NoSuchAlgorithmException unused) {
            }
            if (messageDigest != null) {
                return messageDigest;
            }
        }
        return null;
    }
}
