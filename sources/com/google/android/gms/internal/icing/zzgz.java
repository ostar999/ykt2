package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzgz extends IllegalArgumentException {
    public zzgz(int i2, int i3) {
        StringBuilder sb = new StringBuilder(54);
        sb.append("Unpaired surrogate at index ");
        sb.append(i2);
        sb.append(" of ");
        sb.append(i3);
        super(sb.toString());
    }
}
