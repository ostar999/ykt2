package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
abstract class zzcy implements zzdc {
    @Override // java.util.Iterator
    public /* synthetic */ Byte next() {
        return Byte.valueOf(nextByte());
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
