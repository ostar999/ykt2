package com.google.android.gms.internal.icing;

import java.util.Comparator;

/* loaded from: classes3.dex */
final class zzcv implements Comparator<zzct> {
    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzct zzctVar, zzct zzctVar2) {
        zzct zzctVar3 = zzctVar;
        zzct zzctVar4 = zzctVar2;
        zzdc zzdcVar = (zzdc) zzctVar3.iterator();
        zzdc zzdcVar2 = (zzdc) zzctVar4.iterator();
        while (zzdcVar.hasNext() && zzdcVar2.hasNext()) {
            int iCompare = Integer.compare(zzct.zza(zzdcVar.nextByte()), zzct.zza(zzdcVar2.nextByte()));
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return Integer.compare(zzctVar3.size(), zzctVar4.size());
    }
}
