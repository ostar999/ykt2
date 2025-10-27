package com.google.android.gms.internal.icing;

import java.util.List;

/* loaded from: classes3.dex */
public final class zzgn extends RuntimeException {
    private final List<String> zzoe;

    public zzgn(zzfh zzfhVar) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
        this.zzoe = null;
    }
}
