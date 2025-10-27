package com.google.android.gms.internal.base;

import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
final class zah extends Drawable.ConstantState {
    int mChangingConfigurations;
    int zaoa;

    public zah(zah zahVar) {
        if (zahVar != null) {
            this.mChangingConfigurations = zahVar.mChangingConfigurations;
            this.zaoa = zahVar.zaoa;
        }
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    @Override // android.graphics.drawable.Drawable.ConstantState
    public final Drawable newDrawable() {
        return new zae(this);
    }
}
