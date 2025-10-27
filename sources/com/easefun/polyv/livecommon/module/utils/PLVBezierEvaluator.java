package com.easefun.polyv.livecommon.module.utils;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/* loaded from: classes3.dex */
public class PLVBezierEvaluator implements TypeEvaluator<PointF> {
    private PointF point = new PointF();
    private PointF point1;
    private PointF point2;

    public PLVBezierEvaluator(PointF point1, PointF point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override // android.animation.TypeEvaluator
    public PointF evaluate(float t2, PointF startValue, PointF endValue) {
        PointF pointF = this.point;
        float f2 = 1.0f - t2;
        float f3 = startValue.x * f2 * f2 * f2;
        PointF pointF2 = this.point1;
        float f4 = f3 + (pointF2.x * 3.0f * t2 * f2 * f2);
        PointF pointF3 = this.point2;
        pointF.x = f4 + (pointF3.x * 3.0f * t2 * t2 * f2) + (endValue.x * t2 * t2 * t2);
        pointF.y = (startValue.y * f2 * f2 * f2) + (pointF2.y * 3.0f * t2 * f2 * f2) + (pointF3.y * 3.0f * t2 * t2 * f2) + (endValue.y * t2 * t2 * t2);
        return pointF;
    }
}
