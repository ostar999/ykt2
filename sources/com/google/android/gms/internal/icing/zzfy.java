package com.google.android.gms.internal.icing;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzfy extends zzfz<Object, Object> {
    public zzfy(int i2) {
        super(i2, null);
    }

    @Override // com.google.android.gms.internal.icing.zzfz
    public final void zzai() {
        if (!isImmutable()) {
            for (int i2 = 0; i2 < zzdd(); i2++) {
                Map.Entry<Object, Object> entryZzaj = zzaj(i2);
                if (((zzdu) entryZzaj.getKey()).zzbi()) {
                    entryZzaj.setValue(Collections.unmodifiableList((List) entryZzaj.getValue()));
                }
            }
            for (Map.Entry<Object, Object> entry : zzde()) {
                if (((zzdu) entry.getKey()).zzbi()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzai();
    }
}
