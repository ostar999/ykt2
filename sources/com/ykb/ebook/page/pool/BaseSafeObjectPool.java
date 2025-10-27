package com.ykb.ebook.page.pool;

import androidx.core.util.Pools;
import androidx.exifinterface.media.ExifInterface;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/page/pool/BaseSafeObjectPool;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lcom/ykb/ebook/page/pool/BaseObjectPool;", DatabaseManager.SIZE, "", "(I)V", "pool", "Landroidx/core/util/Pools$SynchronizedPool;", "getPool", "()Landroidx/core/util/Pools$SynchronizedPool;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public abstract class BaseSafeObjectPool<T> extends BaseObjectPool<T> {

    @NotNull
    private final Pools.SynchronizedPool<T> pool;

    public BaseSafeObjectPool(int i2) {
        super(i2);
        this.pool = new Pools.SynchronizedPool<>(i2);
    }

    @Override // com.ykb.ebook.page.pool.BaseObjectPool
    @NotNull
    public Pools.SynchronizedPool<T> getPool() {
        return this.pool;
    }
}
