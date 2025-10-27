package master.flame.danmaku.danmaku.model.objectpool;

import master.flame.danmaku.danmaku.model.objectpool.Poolable;

/* loaded from: classes8.dex */
public interface Pool<T extends Poolable<T>> {
    T acquire();

    void release(T t2);
}
