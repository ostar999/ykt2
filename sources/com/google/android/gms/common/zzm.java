package com.google.android.gms.common;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;
import java.util.concurrent.Callable;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
/* loaded from: classes3.dex */
class zzm {
    private static final zzm zzac = new zzm(true, null, null);
    private final Throwable cause;
    final boolean zzad;
    private final String zzae;

    public zzm(boolean z2, @Nullable String str, @Nullable Throwable th) {
        this.zzad = z2;
        this.zzae = str;
        this.cause = th;
    }

    public static zzm zza(Callable<String> callable) {
        return new zzo(callable);
    }

    public static zzm zzb(@NonNull String str) {
        return new zzm(false, str, null);
    }

    public static String zzc(String str, zze zzeVar, boolean z2, boolean z3) {
        return String.format("%s: pkg=%s, sha1=%s, atk=%s, ver=%s", z3 ? "debug cert rejected" : "not whitelisted", str, Hex.bytesToStringLowercase(AndroidUtilsLight.zzj("SHA-1").digest(zzeVar.getBytes())), Boolean.valueOf(z2), "12451009.false");
    }

    public static zzm zze() {
        return zzac;
    }

    @Nullable
    public String getErrorMessage() {
        return this.zzae;
    }

    public final void zzf() {
        if (this.zzad || !Log.isLoggable("GoogleCertificatesRslt", 3)) {
            return;
        }
        if (this.cause != null) {
            Log.d("GoogleCertificatesRslt", getErrorMessage(), this.cause);
        } else {
            Log.d("GoogleCertificatesRslt", getErrorMessage());
        }
    }

    public static zzm zza(@NonNull String str, @NonNull Throwable th) {
        return new zzm(false, str, th);
    }
}
