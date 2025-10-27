package net.polyv.danmaku.danmaku.model.objectpool;

import net.polyv.danmaku.danmaku.model.objectpool.Poolable;

/* loaded from: classes9.dex */
class FinitePool<T extends Poolable<T>> implements Pool<T> {
    private final boolean mInfinite;
    private final int mLimit;
    private final PoolableManager<T> mManager;
    private int mPoolCount;
    private T mRoot;

    public FinitePool(PoolableManager<T> poolableManager) {
        this.mManager = poolableManager;
        this.mLimit = 0;
        this.mInfinite = true;
    }

    @Override // net.polyv.danmaku.danmaku.model.objectpool.Pool
    public T acquire() {
        T t2 = this.mRoot;
        if (t2 != null) {
            this.mRoot = (T) t2.getNextPoolable();
            this.mPoolCount--;
        } else {
            t2 = (T) this.mManager.newInstance();
        }
        if (t2 != null) {
            t2.setNextPoolable(null);
            t2.setPooled(false);
            this.mManager.onAcquired(t2);
        }
        return t2;
    }

    @Override // net.polyv.danmaku.danmaku.model.objectpool.Pool
    public void release(T t2) {
        if (t2.isPooled()) {
            System.out.print("[FinitePool] Element is already in pool: " + t2);
            return;
        }
        if (this.mInfinite || this.mPoolCount < this.mLimit) {
            this.mPoolCount++;
            t2.setNextPoolable(this.mRoot);
            t2.setPooled(true);
            this.mRoot = t2;
        }
        this.mManager.onReleased(t2);
    }

    public FinitePool(PoolableManager<T> poolableManager, int i2) {
        if (i2 > 0) {
            this.mManager = poolableManager;
            this.mLimit = i2;
            this.mInfinite = false;
            return;
        }
        throw new IllegalArgumentException("The pool limit must be > 0");
    }
}
