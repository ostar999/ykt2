package net.polyv.danmaku.danmaku.model.objectpool;

import net.polyv.danmaku.danmaku.model.objectpool.Poolable;

/* loaded from: classes9.dex */
public interface PoolableManager<T extends Poolable<T>> {
    T newInstance();

    void onAcquired(T t2);

    void onReleased(T t2);
}
