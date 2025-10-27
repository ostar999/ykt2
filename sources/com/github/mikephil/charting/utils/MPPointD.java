package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.utils.ObjectPool;
import java.util.List;

/* loaded from: classes3.dex */
public class MPPointD extends ObjectPool.Poolable {
    private static ObjectPool<MPPointD> pool;

    /* renamed from: x, reason: collision with root package name */
    public double f6564x;

    /* renamed from: y, reason: collision with root package name */
    public double f6565y;

    static {
        ObjectPool<MPPointD> objectPoolCreate = ObjectPool.create(64, new MPPointD(0.0d, 0.0d));
        pool = objectPoolCreate;
        objectPoolCreate.setReplenishPercentage(0.5f);
    }

    private MPPointD(double d3, double d4) {
        this.f6564x = d3;
        this.f6565y = d4;
    }

    public static MPPointD getInstance(double d3, double d4) {
        MPPointD mPPointD = (MPPointD) pool.get();
        mPPointD.f6564x = d3;
        mPPointD.f6565y = d4;
        return mPPointD;
    }

    public static void recycleInstance(MPPointD mPPointD) {
        pool.recycle((ObjectPool<MPPointD>) mPPointD);
    }

    public static void recycleInstances(List<MPPointD> list) {
        pool.recycle(list);
    }

    @Override // com.github.mikephil.charting.utils.ObjectPool.Poolable
    public ObjectPool.Poolable instantiate() {
        return new MPPointD(0.0d, 0.0d);
    }

    public String toString() {
        return "MPPointD, x: " + this.f6564x + ", y: " + this.f6565y;
    }
}
