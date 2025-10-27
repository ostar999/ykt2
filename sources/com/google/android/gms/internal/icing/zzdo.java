package com.google.android.gms.internal.icing;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class zzdo {
    private static volatile boolean zzhb = false;
    private static boolean zzhc = true;
    private static volatile zzdo zzhd;
    private static final zzdo zzhe = new zzdo(true);
    private final Map<Object, Object> zzhf;

    public zzdo() {
        this.zzhf = new HashMap();
    }

    public static zzdo zzaz() {
        zzdo zzdoVar = zzhd;
        if (zzdoVar == null) {
            synchronized (zzdo.class) {
                zzdoVar = zzhd;
                if (zzdoVar == null) {
                    zzdoVar = zzhe;
                    zzhd = zzdoVar;
                }
            }
        }
        return zzdoVar;
    }

    private zzdo(boolean z2) {
        this.zzhf = Collections.emptyMap();
    }
}
