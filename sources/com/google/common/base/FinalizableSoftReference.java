package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.lang.ref.SoftReference;

@GwtIncompatible
/* loaded from: classes3.dex */
public abstract class FinalizableSoftReference<T> extends SoftReference<T> implements FinalizableReference {
    public FinalizableSoftReference(T t2, FinalizableReferenceQueue finalizableReferenceQueue) {
        super(t2, finalizableReferenceQueue.queue);
        finalizableReferenceQueue.cleanUp();
    }
}
