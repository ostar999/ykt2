package master.flame.danmaku.danmaku.model.objectpool;

import master.flame.danmaku.danmaku.model.objectpool.Poolable;

/* loaded from: classes8.dex */
class SynchronizedPool<T extends Poolable<T>> implements Pool<T> {
    private final Object mLock;
    private final Pool<T> mPool;

    public SynchronizedPool(Pool<T> pool) {
        this.mPool = pool;
        this.mLock = this;
    }

    @Override // master.flame.danmaku.danmaku.model.objectpool.Pool
    public T acquire() {
        T t2;
        synchronized (this.mLock) {
            t2 = (T) this.mPool.acquire();
        }
        return t2;
    }

    @Override // master.flame.danmaku.danmaku.model.objectpool.Pool
    public void release(T t2) {
        synchronized (this.mLock) {
            this.mPool.release(t2);
        }
    }

    public SynchronizedPool(Pool<T> pool, Object obj) {
        this.mPool = pool;
        this.mLock = obj;
    }
}
