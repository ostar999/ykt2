package cn.webdemo.com.supporfragment.tablayout.buildins.circlenavigator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CircleNavigator extends View implements IPagerNavigator {
    private OnCircleClickListener mCircleClickListener;
    private int mCircleColor;
    private List<PointF> mCirclePoints;
    private int mCircleSpacing;
    private int mCurrentIndex;
    private float mDownX;
    private float mDownY;
    private boolean mFollowTouch;
    private float mIndicatorX;
    private Paint mPaint;
    private int mRadius;
    private Interpolator mStartInterpolator;
    private int mStrokeWidth;
    private int mTotalCount;
    private int mTouchSlop;
    private boolean mTouchable;

    public interface OnCircleClickListener {
        void onClick(int i2);
    }

    public CircleNavigator(Context context) {
        super(context);
        this.mStartInterpolator = new LinearInterpolator();
        this.mPaint = new Paint(1);
        this.mCirclePoints = new ArrayList();
        this.mFollowTouch = true;
        init(context);
    }

    private void drawCircles(Canvas canvas) {
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.mStrokeWidth);
        int size = this.mCirclePoints.size();
        for (int i2 = 0; i2 < size; i2++) {
            PointF pointF = this.mCirclePoints.get(i2);
            canvas.drawCircle(pointF.x, pointF.y, this.mRadius, this.mPaint);
        }
    }

    private void drawIndicator(Canvas canvas) {
        this.mPaint.setStyle(Paint.Style.FILL);
        if (this.mCirclePoints.size() > 0) {
            canvas.drawCircle(this.mIndicatorX, (int) ((getHeight() / 2.0f) + 0.5f), this.mRadius, this.mPaint);
        }
    }

    private void init(Context context) {
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mRadius = UIUtil.dip2px(context, 3.0d);
        this.mCircleSpacing = UIUtil.dip2px(context, 8.0d);
        this.mStrokeWidth = UIUtil.dip2px(context, 1.0d);
    }

    private int measureHeight(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == Integer.MIN_VALUE || mode == 0) {
            return (this.mRadius * 2) + (this.mStrokeWidth * 2) + getPaddingTop() + getPaddingBottom();
        }
        if (mode != 1073741824) {
            return 0;
        }
        return size;
    }

    private int measureWidth(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == Integer.MIN_VALUE || mode == 0) {
            int i3 = this.mTotalCount;
            return (this.mStrokeWidth * 2) + (this.mRadius * i3 * 2) + ((i3 - 1) * this.mCircleSpacing) + getPaddingLeft() + getPaddingRight();
        }
        if (mode != 1073741824) {
            return 0;
        }
        return size;
    }

    private void prepareCirclePoints() {
        this.mCirclePoints.clear();
        if (this.mTotalCount > 0) {
            int height = (int) ((getHeight() / 2.0f) + 0.5f);
            int i2 = this.mRadius;
            int i3 = (i2 * 2) + this.mCircleSpacing;
            int paddingLeft = i2 + ((int) ((this.mStrokeWidth / 2.0f) + 0.5f)) + getPaddingLeft();
            for (int i4 = 0; i4 < this.mTotalCount; i4++) {
                this.mCirclePoints.add(new PointF(paddingLeft, height));
                paddingLeft += i3;
            }
            this.mIndicatorX = this.mCirclePoints.get(this.mCurrentIndex).x;
        }
    }

    public OnCircleClickListener getCircleClickListener() {
        return this.mCircleClickListener;
    }

    public int getCircleColor() {
        return this.mCircleColor;
    }

    public int getCircleCount() {
        return this.mTotalCount;
    }

    public int getCircleSpacing() {
        return this.mCircleSpacing;
    }

    public int getRadius() {
        return this.mRadius;
    }

    public Interpolator getStartInterpolator() {
        return this.mStartInterpolator;
    }

    public int getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public boolean isFollowTouch() {
        return this.mFollowTouch;
    }

    public boolean isTouchable() {
        return this.mTouchable;
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void notifyDataSetChanged() {
        prepareCirclePoints();
        invalidate();
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void onAttachToMagicIndicator() {
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void onDetachFromMagicIndicator() {
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        this.mPaint.setColor(this.mCircleColor);
        drawCircles(canvas);
        drawIndicator(canvas);
    }

    @Override // android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        prepareCirclePoints();
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        setMeasuredDimension(measureWidth(i2), measureHeight(i3));
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void onPageScrollStateChanged(int i2) {
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void onPageScrolled(int i2, float f2, int i3) {
        if (!this.mFollowTouch || this.mCirclePoints.isEmpty()) {
            return;
        }
        int iMin = Math.min(this.mCirclePoints.size() - 1, i2);
        int iMin2 = Math.min(this.mCirclePoints.size() - 1, i2 + 1);
        PointF pointF = this.mCirclePoints.get(iMin);
        PointF pointF2 = this.mCirclePoints.get(iMin2);
        float f3 = pointF.x;
        this.mIndicatorX = f3 + ((pointF2.x - f3) * this.mStartInterpolator.getInterpolation(f2));
        invalidate();
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void onPageSelected(int i2) {
        this.mCurrentIndex = i2;
        if (this.mFollowTouch) {
            return;
        }
        this.mIndicatorX = this.mCirclePoints.get(i2).x;
        invalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1 && this.mCircleClickListener != null && Math.abs(x2 - this.mDownX) <= this.mTouchSlop && Math.abs(y2 - this.mDownY) <= this.mTouchSlop) {
                float f2 = Float.MAX_VALUE;
                int i2 = 0;
                for (int i3 = 0; i3 < this.mCirclePoints.size(); i3++) {
                    float fAbs = Math.abs(this.mCirclePoints.get(i3).x - x2);
                    if (fAbs < f2) {
                        i2 = i3;
                        f2 = fAbs;
                    }
                }
                this.mCircleClickListener.onClick(i2);
            }
        } else if (this.mTouchable) {
            this.mDownX = x2;
            this.mDownY = y2;
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setCircleClickListener(OnCircleClickListener onCircleClickListener) {
        if (!this.mTouchable) {
            this.mTouchable = true;
        }
        this.mCircleClickListener = onCircleClickListener;
    }

    public void setCircleColor(int i2) {
        this.mCircleColor = i2;
        invalidate();
    }

    public void setCircleCount(int i2) {
        this.mTotalCount = i2;
    }

    public void setCircleSpacing(int i2) {
        this.mCircleSpacing = i2;
        prepareCirclePoints();
        invalidate();
    }

    public void setFollowTouch(boolean z2) {
        this.mFollowTouch = z2;
    }

    public void setRadius(int i2) {
        this.mRadius = i2;
        prepareCirclePoints();
        invalidate();
    }

    public void setStartInterpolator(Interpolator interpolator) {
        this.mStartInterpolator = interpolator;
        if (interpolator == null) {
            this.mStartInterpolator = new LinearInterpolator();
        }
    }

    public void setStrokeWidth(int i2) {
        this.mStrokeWidth = i2;
        invalidate();
    }

    public void setTouchable(boolean z2) {
        this.mTouchable = z2;
    }
}
