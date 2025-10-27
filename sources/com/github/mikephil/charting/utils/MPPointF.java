package com.github.mikephil.charting.utils;

import android.os.Parcel;
import android.os.Parcelable;
import com.github.mikephil.charting.utils.ObjectPool;
import java.util.List;

/* loaded from: classes3.dex */
public class MPPointF extends ObjectPool.Poolable {
    public static final Parcelable.Creator<MPPointF> CREATOR;
    private static ObjectPool<MPPointF> pool;

    /* renamed from: x, reason: collision with root package name */
    public float f6566x;

    /* renamed from: y, reason: collision with root package name */
    public float f6567y;

    static {
        ObjectPool<MPPointF> objectPoolCreate = ObjectPool.create(32, new MPPointF(0.0f, 0.0f));
        pool = objectPoolCreate;
        objectPoolCreate.setReplenishPercentage(0.5f);
        CREATOR = new Parcelable.Creator<MPPointF>() { // from class: com.github.mikephil.charting.utils.MPPointF.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MPPointF createFromParcel(Parcel parcel) {
                MPPointF mPPointF = new MPPointF(0.0f, 0.0f);
                mPPointF.my_readFromParcel(parcel);
                return mPPointF;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public MPPointF[] newArray(int i2) {
                return new MPPointF[i2];
            }
        };
    }

    public MPPointF() {
    }

    public static MPPointF getInstance(float f2, float f3) {
        MPPointF mPPointF = (MPPointF) pool.get();
        mPPointF.f6566x = f2;
        mPPointF.f6567y = f3;
        return mPPointF;
    }

    public static void recycleInstance(MPPointF mPPointF) {
        pool.recycle((ObjectPool<MPPointF>) mPPointF);
    }

    public static void recycleInstances(List<MPPointF> list) {
        pool.recycle(list);
    }

    public float getX() {
        return this.f6566x;
    }

    public float getY() {
        return this.f6567y;
    }

    @Override // com.github.mikephil.charting.utils.ObjectPool.Poolable
    public ObjectPool.Poolable instantiate() {
        return new MPPointF(0.0f, 0.0f);
    }

    public void my_readFromParcel(Parcel parcel) {
        this.f6566x = parcel.readFloat();
        this.f6567y = parcel.readFloat();
    }

    public MPPointF(float f2, float f3) {
        this.f6566x = f2;
        this.f6567y = f3;
    }

    public static MPPointF getInstance() {
        return (MPPointF) pool.get();
    }

    public static MPPointF getInstance(MPPointF mPPointF) {
        MPPointF mPPointF2 = (MPPointF) pool.get();
        mPPointF2.f6566x = mPPointF.f6566x;
        mPPointF2.f6567y = mPPointF.f6567y;
        return mPPointF2;
    }
}
