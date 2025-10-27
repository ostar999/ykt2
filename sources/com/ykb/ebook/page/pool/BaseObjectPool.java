package com.ykb.ebook.page.pool;

import androidx.annotation.CallSuper;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.util.Pools;
import androidx.exifinterface.media.ExifInterface;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u000b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\fJ\u0015\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000H\u0017¢\u0006\u0002\u0010\u0010R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lcom/ykb/ebook/page/pool/BaseObjectPool;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lcom/ykb/ebook/page/pool/ObjectPool;", DatabaseManager.SIZE, "", "(I)V", "pool", "Landroidx/core/util/Pools$SimplePool;", "getPool", "()Landroidx/core/util/Pools$SimplePool;", "obtain", "()Ljava/lang/Object;", "recycle", "", TypedValues.AttributesType.S_TARGET, "(Ljava/lang/Object;)V", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public abstract class BaseObjectPool<T> implements ObjectPool<T> {

    @NotNull
    private final Pools.SimplePool<T> pool;

    public BaseObjectPool(int i2) {
        this.pool = new Pools.SimplePool<>(i2);
    }

    @NotNull
    public Pools.SimplePool<T> getPool() {
        return this.pool;
    }

    @Override // com.ykb.ebook.page.pool.ObjectPool
    @NotNull
    public T obtain() {
        T tAcquire = getPool().acquire();
        return tAcquire == null ? create() : tAcquire;
    }

    @Override // com.ykb.ebook.page.pool.ObjectPool
    @CallSuper
    public void recycle(@NotNull T target) {
        Intrinsics.checkNotNullParameter(target, "target");
        getPool().release(target);
    }
}
