package net.polyv.danmaku.danmaku.model.objectpool;

import net.polyv.danmaku.danmaku.model.objectpool.Poolable;

/* loaded from: classes9.dex */
public interface Pool<T extends Poolable<T>> {
    T acquire();

    void release(T t2);
}
