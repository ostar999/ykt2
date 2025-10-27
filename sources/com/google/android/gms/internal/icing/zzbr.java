package com.google.android.gms.internal.icing;

import android.util.Log;

/* loaded from: classes3.dex */
final class zzbr extends zzbq<Boolean> {
    public zzbr(zzbu zzbuVar, String str, Boolean bool) {
        super(zzbuVar, str, bool, null);
    }

    @Override // com.google.android.gms.internal.icing.zzbq
    public final /* synthetic */ Boolean zza(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (zzax.zzbt.matcher(str).matches()) {
                return Boolean.TRUE;
            }
            if (zzax.zzbu.matcher(str).matches()) {
                return Boolean.FALSE;
            }
        }
        String strZzu = super.zzu();
        String strValueOf = String.valueOf(obj);
        StringBuilder sb = new StringBuilder(String.valueOf(strZzu).length() + 28 + strValueOf.length());
        sb.append("Invalid boolean value for ");
        sb.append(strZzu);
        sb.append(": ");
        sb.append(strValueOf);
        Log.e("PhenotypeFlag", sb.toString());
        return null;
    }
}
