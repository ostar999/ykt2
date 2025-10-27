package com.google.android.gms.common.api.internal;

import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
final class zabc extends zabp {
    private WeakReference<zaaw> zahp;

    public zabc(zaaw zaawVar) {
        this.zahp = new WeakReference<>(zaawVar);
    }

    @Override // com.google.android.gms.common.api.internal.zabp
    public final void zas() {
        zaaw zaawVar = this.zahp.get();
        if (zaawVar == null) {
            return;
        }
        zaawVar.resume();
    }
}
