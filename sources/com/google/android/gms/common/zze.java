package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/* loaded from: classes3.dex */
abstract class zze extends com.google.android.gms.common.internal.zzj {
    private int zzt;

    public zze(byte[] bArr) {
        Preconditions.checkArgument(bArr.length == 25);
        this.zzt = Arrays.hashCode(bArr);
    }

    public static byte[] zza(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e2) {
            throw new AssertionError(e2);
        }
    }

    public boolean equals(Object obj) {
        IObjectWrapper iObjectWrapperZzb;
        if (obj != null && (obj instanceof com.google.android.gms.common.internal.zzi)) {
            try {
                com.google.android.gms.common.internal.zzi zziVar = (com.google.android.gms.common.internal.zzi) obj;
                if (zziVar.zzc() == hashCode() && (iObjectWrapperZzb = zziVar.zzb()) != null) {
                    return Arrays.equals(getBytes(), (byte[]) ObjectWrapper.unwrap(iObjectWrapperZzb));
                }
                return false;
            } catch (RemoteException e2) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e2);
            }
        }
        return false;
    }

    public abstract byte[] getBytes();

    public int hashCode() {
        return this.zzt;
    }

    @Override // com.google.android.gms.common.internal.zzi
    public final IObjectWrapper zzb() {
        return ObjectWrapper.wrap(getBytes());
    }

    @Override // com.google.android.gms.common.internal.zzi
    public final int zzc() {
        return hashCode();
    }
}
