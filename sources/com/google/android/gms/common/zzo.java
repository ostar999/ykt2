package com.google.android.gms.common;

import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
final class zzo extends zzm {
    private final Callable<String> zzaf;

    private zzo(Callable<String> callable) {
        super(false, null, null);
        this.zzaf = callable;
    }

    @Override // com.google.android.gms.common.zzm
    public final String getErrorMessage() {
        try {
            return this.zzaf.call();
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
}
