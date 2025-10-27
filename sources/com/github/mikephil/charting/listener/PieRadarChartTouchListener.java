package com.github.mikephil.charting.listener;

import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PieRadarChartTouchListener extends ChartTouchListener<PieRadarChartBase<?>> {
    private ArrayList<AngularVelocitySample> _velocitySamples;
    private float mDecelerationAngularVelocity;
    private long mDecelerationLastTime;
    private float mStartAngle;
    private MPPointF mTouchStartPoint;

    public class AngularVelocitySample {
        public float angle;
        public long time;

        public AngularVelocitySample(long j2, float f2) {
            this.time = j2;
            this.angle = f2;
        }
    }

    public PieRadarChartTouchListener(PieRadarChartBase<?> pieRadarChartBase) {
        super(pieRadarChartBase);
        this.mTouchStartPoint = MPPointF.getInstance(0.0f, 0.0f);
        this.mStartAngle = 0.0f;
        this._velocitySamples = new ArrayList<>();
        this.mDecelerationLastTime = 0L;
        this.mDecelerationAngularVelocity = 0.0f;
    }

    private float calculateVelocity() {
        if (this._velocitySamples.isEmpty()) {
            return 0.0f;
        }
        AngularVelocitySample angularVelocitySample = this._velocitySamples.get(0);
        ArrayList<AngularVelocitySample> arrayList = this._velocitySamples;
        AngularVelocitySample angularVelocitySample2 = arrayList.get(arrayList.size() - 1);
        AngularVelocitySample angularVelocitySample3 = angularVelocitySample;
        for (int size = this._velocitySamples.size() - 1; size >= 0; size--) {
            angularVelocitySample3 = this._velocitySamples.get(size);
            if (angularVelocitySample3.angle != angularVelocitySample2.angle) {
                break;
            }
        }
        float f2 = (angularVelocitySample2.time - angularVelocitySample.time) / 1000.0f;
        if (f2 == 0.0f) {
            f2 = 0.1f;
        }
        boolean z2 = angularVelocitySample2.angle >= angularVelocitySample3.angle;
        if (Math.abs(r1 - r6) > 270.0d) {
            z2 = !z2;
        }
        float f3 = angularVelocitySample2.angle;
        float f4 = angularVelocitySample.angle;
        if (f3 - f4 > 180.0d) {
            angularVelocitySample.angle = (float) (f4 + 360.0d);
        } else if (f4 - f3 > 180.0d) {
            angularVelocitySample2.angle = (float) (f3 + 360.0d);
        }
        float fAbs = Math.abs((angularVelocitySample2.angle - angularVelocitySample.angle) / f2);
        return !z2 ? -fAbs : fAbs;
    }

    private void resetVelocity() {
        this._velocitySamples.clear();
    }

    private void sampleVelocity(float f2, float f3) {
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        this._velocitySamples.add(new AngularVelocitySample(jCurrentAnimationTimeMillis, ((PieRadarChartBase) this.mChart).getAngleForPoint(f2, f3)));
        for (int size = this._velocitySamples.size(); size - 2 > 0 && jCurrentAnimationTimeMillis - this._velocitySamples.get(0).time > 1000; size--) {
            this._velocitySamples.remove(0);
        }
    }

    public void computeScroll() {
        if (this.mDecelerationAngularVelocity == 0.0f) {
            return;
        }
        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        this.mDecelerationAngularVelocity *= ((PieRadarChartBase) this.mChart).getDragDecelerationFrictionCoef();
        T t2 = this.mChart;
        ((PieRadarChartBase) t2).setRotationAngle(((PieRadarChartBase) t2).getRotationAngle() + (this.mDecelerationAngularVelocity * ((jCurrentAnimationTimeMillis - this.mDecelerationLastTime) / 1000.0f)));
        this.mDecelerationLastTime = jCurrentAnimationTimeMillis;
        if (Math.abs(this.mDecelerationAngularVelocity) >= 0.001d) {
            Utils.postInvalidateOnAnimation(this.mChart);
        } else {
            stopDeceleration();
        }
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.LONG_PRESS;
        OnChartGestureListener onChartGestureListener = ((PieRadarChartBase) this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartLongPressed(motionEvent);
        }
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.SINGLE_TAP;
        OnChartGestureListener onChartGestureListener = ((PieRadarChartBase) this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartSingleTapped(motionEvent);
        }
        if (!((PieRadarChartBase) this.mChart).isHighlightPerTapEnabled()) {
            return false;
        }
        performHighlight(((PieRadarChartBase) this.mChart).getHighlightByTouchPoint(motionEvent.getX(), motionEvent.getY()), motionEvent);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005d  */
    @Override // android.view.View.OnTouchListener
    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouch(android.view.View r6, android.view.MotionEvent r7) {
        /*
            r5 = this;
            android.view.GestureDetector r6 = r5.mGestureDetector
            boolean r6 = r6.onTouchEvent(r7)
            r0 = 1
            if (r6 == 0) goto La
            return r0
        La:
            T extends com.github.mikephil.charting.charts.Chart<?> r6 = r5.mChart
            com.github.mikephil.charting.charts.PieRadarChartBase r6 = (com.github.mikephil.charting.charts.PieRadarChartBase) r6
            boolean r6 = r6.isRotationEnabled()
            if (r6 == 0) goto Lc2
            float r6 = r7.getX()
            float r1 = r7.getY()
            int r2 = r7.getAction()
            if (r2 == 0) goto La3
            if (r2 == r0) goto L6f
            r3 = 2
            if (r2 == r3) goto L29
            goto Lc2
        L29:
            T extends com.github.mikephil.charting.charts.Chart<?> r2 = r5.mChart
            com.github.mikephil.charting.charts.PieRadarChartBase r2 = (com.github.mikephil.charting.charts.PieRadarChartBase) r2
            boolean r2 = r2.isDragDecelerationEnabled()
            if (r2 == 0) goto L36
            r5.sampleVelocity(r6, r1)
        L36:
            int r2 = r5.mTouchMode
            r3 = 6
            if (r2 != 0) goto L5d
            com.github.mikephil.charting.utils.MPPointF r2 = r5.mTouchStartPoint
            float r4 = r2.f6566x
            float r2 = r2.f6567y
            float r2 = com.github.mikephil.charting.listener.ChartTouchListener.distance(r6, r4, r1, r2)
            r4 = 1090519040(0x41000000, float:8.0)
            float r4 = com.github.mikephil.charting.utils.Utils.convertDpToPixel(r4)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L5d
            com.github.mikephil.charting.listener.ChartTouchListener$ChartGesture r6 = com.github.mikephil.charting.listener.ChartTouchListener.ChartGesture.ROTATE
            r5.mLastGesture = r6
            r5.mTouchMode = r3
            T extends com.github.mikephil.charting.charts.Chart<?> r6 = r5.mChart
            com.github.mikephil.charting.charts.PieRadarChartBase r6 = (com.github.mikephil.charting.charts.PieRadarChartBase) r6
            r6.disableScroll()
            goto L6b
        L5d:
            int r2 = r5.mTouchMode
            if (r2 != r3) goto L6b
            r5.updateGestureRotation(r6, r1)
            T extends com.github.mikephil.charting.charts.Chart<?> r6 = r5.mChart
            com.github.mikephil.charting.charts.PieRadarChartBase r6 = (com.github.mikephil.charting.charts.PieRadarChartBase) r6
            r6.invalidate()
        L6b:
            r5.endAction(r7)
            goto Lc2
        L6f:
            T extends com.github.mikephil.charting.charts.Chart<?> r2 = r5.mChart
            com.github.mikephil.charting.charts.PieRadarChartBase r2 = (com.github.mikephil.charting.charts.PieRadarChartBase) r2
            boolean r2 = r2.isDragDecelerationEnabled()
            if (r2 == 0) goto L95
            r5.stopDeceleration()
            r5.sampleVelocity(r6, r1)
            float r6 = r5.calculateVelocity()
            r5.mDecelerationAngularVelocity = r6
            r1 = 0
            int r6 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r6 == 0) goto L95
            long r1 = android.view.animation.AnimationUtils.currentAnimationTimeMillis()
            r5.mDecelerationLastTime = r1
            T extends com.github.mikephil.charting.charts.Chart<?> r6 = r5.mChart
            com.github.mikephil.charting.utils.Utils.postInvalidateOnAnimation(r6)
        L95:
            T extends com.github.mikephil.charting.charts.Chart<?> r6 = r5.mChart
            com.github.mikephil.charting.charts.PieRadarChartBase r6 = (com.github.mikephil.charting.charts.PieRadarChartBase) r6
            r6.enableScroll()
            r6 = 0
            r5.mTouchMode = r6
            r5.endAction(r7)
            goto Lc2
        La3:
            r5.startAction(r7)
            r5.stopDeceleration()
            r5.resetVelocity()
            T extends com.github.mikephil.charting.charts.Chart<?> r7 = r5.mChart
            com.github.mikephil.charting.charts.PieRadarChartBase r7 = (com.github.mikephil.charting.charts.PieRadarChartBase) r7
            boolean r7 = r7.isDragDecelerationEnabled()
            if (r7 == 0) goto Lb9
            r5.sampleVelocity(r6, r1)
        Lb9:
            r5.setGestureStartAngle(r6, r1)
            com.github.mikephil.charting.utils.MPPointF r7 = r5.mTouchStartPoint
            r7.f6566x = r6
            r7.f6567y = r1
        Lc2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.listener.PieRadarChartTouchListener.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public void setGestureStartAngle(float f2, float f3) {
        this.mStartAngle = ((PieRadarChartBase) this.mChart).getAngleForPoint(f2, f3) - ((PieRadarChartBase) this.mChart).getRawRotationAngle();
    }

    public void stopDeceleration() {
        this.mDecelerationAngularVelocity = 0.0f;
    }

    public void updateGestureRotation(float f2, float f3) {
        T t2 = this.mChart;
        ((PieRadarChartBase) t2).setRotationAngle(((PieRadarChartBase) t2).getAngleForPoint(f2, f3) - this.mStartAngle);
    }
}
