package com.google.android.gms.internal.icing;

import java.util.NoSuchElementException;

/* loaded from: classes3.dex */
final class zzcw extends zzcy {
    private final int limit;
    private int position = 0;
    private final /* synthetic */ zzct zzgl;

    public zzcw(zzct zzctVar) {
        this.zzgl = zzctVar;
        this.limit = zzctVar.size();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.position < this.limit;
    }

    @Override // com.google.android.gms.internal.icing.zzdc
    public final byte nextByte() {
        int i2 = this.position;
        if (i2 >= this.limit) {
            throw new NoSuchElementException();
        }
        this.position = i2 + 1;
        return this.zzgl.zzl(i2);
    }
}
