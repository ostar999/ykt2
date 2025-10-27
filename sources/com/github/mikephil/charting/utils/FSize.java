package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.utils.ObjectPool;
import java.util.List;

/* loaded from: classes3.dex */
public final class FSize extends ObjectPool.Poolable {
    private static ObjectPool<FSize> pool;
    public float height;
    public float width;

    static {
        ObjectPool<FSize> objectPoolCreate = ObjectPool.create(256, new FSize(0.0f, 0.0f));
        pool = objectPoolCreate;
        objectPoolCreate.setReplenishPercentage(0.5f);
    }

    public FSize() {
    }

    public static FSize getInstance(float f2, float f3) {
        FSize fSize = (FSize) pool.get();
        fSize.width = f2;
        fSize.height = f3;
        return fSize;
    }

    public static void recycleInstance(FSize fSize) {
        pool.recycle((ObjectPool<FSize>) fSize);
    }

    public static void recycleInstances(List<FSize> list) {
        pool.recycle(list);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FSize)) {
            return false;
        }
        FSize fSize = (FSize) obj;
        return this.width == fSize.width && this.height == fSize.height;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.width) ^ Float.floatToIntBits(this.height);
    }

    @Override // com.github.mikephil.charting.utils.ObjectPool.Poolable
    public ObjectPool.Poolable instantiate() {
        return new FSize(0.0f, 0.0f);
    }

    public String toString() {
        return this.width + "x" + this.height;
    }

    public FSize(float f2, float f3) {
        this.width = f2;
        this.height = f3;
    }
}
