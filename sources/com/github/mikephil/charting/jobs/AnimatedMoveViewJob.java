package com.github.mikephil.charting.jobs;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import com.github.mikephil.charting.utils.ObjectPool;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

@SuppressLint({"NewApi"})
/* loaded from: classes3.dex */
public class AnimatedMoveViewJob extends AnimatedViewPortJob {
    private static ObjectPool<AnimatedMoveViewJob> pool;

    static {
        ObjectPool<AnimatedMoveViewJob> objectPoolCreate = ObjectPool.create(4, new AnimatedMoveViewJob(null, 0.0f, 0.0f, null, null, 0.0f, 0.0f, 0L));
        pool = objectPoolCreate;
        objectPoolCreate.setReplenishPercentage(0.5f);
    }

    public AnimatedMoveViewJob(ViewPortHandler viewPortHandler, float f2, float f3, Transformer transformer, View view, float f4, float f5, long j2) {
        super(viewPortHandler, f2, f3, transformer, view, f4, f5, j2);
    }

    public static AnimatedMoveViewJob getInstance(ViewPortHandler viewPortHandler, float f2, float f3, Transformer transformer, View view, float f4, float f5, long j2) {
        AnimatedMoveViewJob animatedMoveViewJob = (AnimatedMoveViewJob) pool.get();
        animatedMoveViewJob.mViewPortHandler = viewPortHandler;
        animatedMoveViewJob.xValue = f2;
        animatedMoveViewJob.yValue = f3;
        animatedMoveViewJob.mTrans = transformer;
        animatedMoveViewJob.view = view;
        animatedMoveViewJob.xOrigin = f4;
        animatedMoveViewJob.yOrigin = f5;
        animatedMoveViewJob.animator.setDuration(j2);
        return animatedMoveViewJob;
    }

    public static void recycleInstance(AnimatedMoveViewJob animatedMoveViewJob) {
        pool.recycle((ObjectPool<AnimatedMoveViewJob>) animatedMoveViewJob);
    }

    @Override // com.github.mikephil.charting.utils.ObjectPool.Poolable
    public ObjectPool.Poolable instantiate() {
        return new AnimatedMoveViewJob(null, 0.0f, 0.0f, null, null, 0.0f, 0.0f, 0L);
    }

    @Override // com.github.mikephil.charting.jobs.AnimatedViewPortJob, android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float[] fArr = this.pts;
        float f2 = this.xOrigin;
        float f3 = this.xValue - f2;
        float f4 = this.phase;
        fArr[0] = f2 + (f3 * f4);
        float f5 = this.yOrigin;
        fArr[1] = f5 + ((this.yValue - f5) * f4);
        this.mTrans.pointValuesToPixel(fArr);
        this.mViewPortHandler.centerViewPort(this.pts, this.view);
    }

    @Override // com.github.mikephil.charting.jobs.AnimatedViewPortJob
    public void recycleSelf() {
        recycleInstance(this);
    }
}
