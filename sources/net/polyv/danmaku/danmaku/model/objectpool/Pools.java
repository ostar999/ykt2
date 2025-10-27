package net.polyv.danmaku.danmaku.model.objectpool;

/* loaded from: classes9.dex */
public class Pools {
    private Pools() {
    }

    public static <T extends Poolable<T>> Pool<T> finitePool(PoolableManager<T> poolableManager, int i2) {
        return new FinitePool(poolableManager, i2);
    }

    public static <T extends Poolable<T>> Pool<T> simplePool(PoolableManager<T> poolableManager) {
        return new FinitePool(poolableManager);
    }

    public static <T extends Poolable<T>> Pool<T> synchronizedPool(Pool<T> pool) {
        return new SynchronizedPool(pool);
    }

    public static <T extends Poolable<T>> Pool<T> synchronizedPool(Pool<T> pool, Object obj) {
        return new SynchronizedPool(pool, obj);
    }
}
