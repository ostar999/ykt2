package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.utils.ObjectPool.Poolable;
import java.util.List;

/* loaded from: classes3.dex */
public class ObjectPool<T extends Poolable> {
    private static int ids;
    private int desiredCapacity;
    private T modelObject;
    private Object[] objects;
    private int objectsPointer;
    private int poolId;
    private float replenishPercentage;

    public static abstract class Poolable {
        public static int NO_OWNER = -1;
        int currentOwnerId = NO_OWNER;

        public abstract Poolable instantiate();
    }

    private ObjectPool(int i2, T t2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("Object Pool must be instantiated with a capacity greater than 0!");
        }
        this.desiredCapacity = i2;
        this.objects = new Object[i2];
        this.objectsPointer = 0;
        this.modelObject = t2;
        this.replenishPercentage = 1.0f;
        refillPool();
    }

    /* JADX WARN: In static synchronized method top region not synchronized by class const: (wrap:java.lang.Class:0x0000: CONST_CLASS  A[WRAPPED] (LINE:1) com.github.mikephil.charting.utils.ObjectPool.class) */
    public static synchronized ObjectPool create(int i2, Poolable poolable) {
        ObjectPool objectPool;
        synchronized (ObjectPool.class) {
            objectPool = new ObjectPool(i2, poolable);
            int i3 = ids;
            objectPool.poolId = i3;
            ids = i3 + 1;
        }
        return objectPool;
    }

    private void refillPool() {
        refillPool(this.replenishPercentage);
    }

    private void resizePool() {
        int i2 = this.desiredCapacity;
        int i3 = i2 * 2;
        this.desiredCapacity = i3;
        Object[] objArr = new Object[i3];
        for (int i4 = 0; i4 < i2; i4++) {
            objArr[i4] = this.objects[i4];
        }
        this.objects = objArr;
    }

    public synchronized T get() {
        T t2;
        if (this.objectsPointer == -1 && this.replenishPercentage > 0.0f) {
            refillPool();
        }
        Object[] objArr = this.objects;
        int i2 = this.objectsPointer;
        t2 = (T) objArr[i2];
        t2.currentOwnerId = Poolable.NO_OWNER;
        this.objectsPointer = i2 - 1;
        return t2;
    }

    public int getPoolCapacity() {
        return this.objects.length;
    }

    public int getPoolCount() {
        return this.objectsPointer + 1;
    }

    public int getPoolId() {
        return this.poolId;
    }

    public float getReplenishPercentage() {
        return this.replenishPercentage;
    }

    public synchronized void recycle(T t2) {
        int i2 = t2.currentOwnerId;
        if (i2 != Poolable.NO_OWNER) {
            if (i2 == this.poolId) {
                throw new IllegalArgumentException("The object passed is already stored in this pool!");
            }
            throw new IllegalArgumentException("The object to recycle already belongs to poolId " + t2.currentOwnerId + ".  Object cannot belong to two different pool instances simultaneously!");
        }
        int i3 = this.objectsPointer + 1;
        this.objectsPointer = i3;
        if (i3 >= this.objects.length) {
            resizePool();
        }
        t2.currentOwnerId = this.poolId;
        this.objects[this.objectsPointer] = t2;
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0006 A[PHI: r0
      0x0006: PHI (r0v2 float) = (r0v0 float), (r0v1 float) binds: [B:3:0x0004, B:6:0x000b] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setReplenishPercentage(float r3) {
        /*
            r2 = this;
            r0 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r1 <= 0) goto L8
        L6:
            r3 = r0
            goto Le
        L8:
            r0 = 0
            int r1 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r1 >= 0) goto Le
            goto L6
        Le:
            r2.replenishPercentage = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.utils.ObjectPool.setReplenishPercentage(float):void");
    }

    private void refillPool(float f2) {
        int i2 = this.desiredCapacity;
        int i3 = (int) (i2 * f2);
        if (i3 < 1) {
            i2 = 1;
        } else if (i3 <= i2) {
            i2 = i3;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            this.objects[i4] = this.modelObject.instantiate();
        }
        this.objectsPointer = i2 - 1;
    }

    public synchronized void recycle(List<T> list) {
        while (list.size() + this.objectsPointer + 1 > this.desiredCapacity) {
            resizePool();
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            T t2 = list.get(i2);
            int i3 = t2.currentOwnerId;
            if (i3 != Poolable.NO_OWNER) {
                if (i3 == this.poolId) {
                    throw new IllegalArgumentException("The object passed is already stored in this pool!");
                }
                throw new IllegalArgumentException("The object to recycle already belongs to poolId " + t2.currentOwnerId + ".  Object cannot belong to two different pool instances simultaneously!");
            }
            t2.currentOwnerId = this.poolId;
            this.objects[this.objectsPointer + 1 + i2] = t2;
        }
        this.objectsPointer += size;
    }
}
