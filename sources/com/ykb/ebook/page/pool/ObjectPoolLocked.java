package com.ykb.ebook.page.pool;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00028\u0000H\u0096\u0001¢\u0006\u0002\u0010\u0006J\r\u0010\u0007\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0006J\u0015\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/page/pool/ObjectPoolLocked;", ExifInterface.GPS_DIRECTION_TRUE, "Lcom/ykb/ebook/page/pool/ObjectPool;", "delegate", "(Lcom/ykb/ebook/page/pool/ObjectPool;)V", "create", "()Ljava/lang/Object;", "obtain", "recycle", "", TypedValues.AttributesType.S_TARGET, "(Ljava/lang/Object;)V", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ObjectPoolLocked<T> implements ObjectPool<T> {

    @NotNull
    private final ObjectPool<T> delegate;

    public ObjectPoolLocked(@NotNull ObjectPool<T> delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // com.ykb.ebook.page.pool.ObjectPool
    public T create() {
        return this.delegate.create();
    }

    @Override // com.ykb.ebook.page.pool.ObjectPool
    public synchronized T obtain() {
        return this.delegate.obtain();
    }

    @Override // com.ykb.ebook.page.pool.ObjectPool
    public synchronized void recycle(T target) {
        this.delegate.recycle(target);
    }
}
