package com.github.mikephil.charting.jobs;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.view.View;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.ObjectPool;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

@SuppressLint({"NewApi"})
/* loaded from: classes3.dex */
public class AnimatedZoomJob extends AnimatedViewPortJob implements Animator.AnimatorListener {
    private static ObjectPool<AnimatedZoomJob> pool = ObjectPool.create(8, new AnimatedZoomJob(null, null, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0));
    protected Matrix mOnAnimationUpdateMatrixBuffer;
    protected float xAxisRange;
    protected YAxis yAxis;
    protected float zoomCenterX;
    protected float zoomCenterY;
    protected float zoomOriginX;
    protected float zoomOriginY;

    @SuppressLint({"NewApi"})
    public AnimatedZoomJob(ViewPortHandler viewPortHandler, View view, Transformer transformer, YAxis yAxis, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, long j2) {
        super(viewPortHandler, f3, f4, transformer, view, f5, f6, j2);
        this.mOnAnimationUpdateMatrixBuffer = new Matrix();
        this.zoomCenterX = f7;
        this.zoomCenterY = f8;
        this.zoomOriginX = f9;
        this.zoomOriginY = f10;
        this.animator.addListener(this);
        this.yAxis = yAxis;
        this.xAxisRange = f2;
    }

    public static AnimatedZoomJob getInstance(ViewPortHandler viewPortHandler, View view, Transformer transformer, YAxis yAxis, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, long j2) {
        AnimatedZoomJob animatedZoomJob = (AnimatedZoomJob) pool.get();
        animatedZoomJob.mViewPortHandler = viewPortHandler;
        animatedZoomJob.xValue = f3;
        animatedZoomJob.yValue = f4;
        animatedZoomJob.mTrans = transformer;
        animatedZoomJob.view = view;
        animatedZoomJob.xOrigin = f5;
        animatedZoomJob.yOrigin = f6;
        animatedZoomJob.yAxis = yAxis;
        animatedZoomJob.xAxisRange = f2;
        animatedZoomJob.resetAnimator();
        animatedZoomJob.animator.setDuration(j2);
        return animatedZoomJob;
    }

    @Override // com.github.mikephil.charting.utils.ObjectPool.Poolable
    public ObjectPool.Poolable instantiate() {
        return new AnimatedZoomJob(null, null, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L);
    }

    @Override // com.github.mikephil.charting.jobs.AnimatedViewPortJob, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
    }

    @Override // com.github.mikephil.charting.jobs.AnimatedViewPortJob, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        ((BarLineChartBase) this.view).calculateOffsets();
        this.view.postInvalidate();
    }

    @Override // com.github.mikephil.charting.jobs.AnimatedViewPortJob, android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
    }

    @Override // com.github.mikephil.charting.jobs.AnimatedViewPortJob, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
    }

    @Override // com.github.mikephil.charting.jobs.AnimatedViewPortJob, android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f2 = this.xOrigin;
        float f3 = this.xValue - f2;
        float f4 = this.phase;
        float f5 = f2 + (f3 * f4);
        float f6 = this.yOrigin;
        float f7 = f6 + ((this.yValue - f6) * f4);
        Matrix matrix = this.mOnAnimationUpdateMatrixBuffer;
        this.mViewPortHandler.setZoom(f5, f7, matrix);
        this.mViewPortHandler.refresh(matrix, this.view, false);
        float scaleY = this.yAxis.mAxisRange / this.mViewPortHandler.getScaleY();
        float scaleX = this.xAxisRange / this.mViewPortHandler.getScaleX();
        float[] fArr = this.pts;
        float f8 = this.zoomOriginX;
        float f9 = (this.zoomCenterX - (scaleX / 2.0f)) - f8;
        float f10 = this.phase;
        fArr[0] = f8 + (f9 * f10);
        float f11 = this.zoomOriginY;
        fArr[1] = f11 + (((this.zoomCenterY + (scaleY / 2.0f)) - f11) * f10);
        this.mTrans.pointValuesToPixel(fArr);
        this.mViewPortHandler.translate(this.pts, matrix);
        this.mViewPortHandler.refresh(matrix, this.view, true);
    }

    @Override // com.github.mikephil.charting.jobs.AnimatedViewPortJob
    public void recycleSelf() {
    }
}
