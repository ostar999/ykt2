package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.circlenavigator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.PLVUIUtil;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVCircleNavigator extends View implements IPLVPagerNavigator {
    private static final String TAG = "PLVCircleNavigator";
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
        void onClick(int index);
    }

    public PLVCircleNavigator(Context context) {
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
        if (this.mCirclePoints.isEmpty()) {
            return;
        }
        canvas.drawCircle(this.mIndicatorX, (int) ((getHeight() / 2.0f) + 0.5f), this.mRadius, this.mPaint);
    }

    private void init(Context context) {
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mRadius = PLVUIUtil.dip2px(context, 3.0d);
        this.mCircleSpacing = PLVUIUtil.dip2px(context, 8.0d);
        this.mStrokeWidth = PLVUIUtil.dip2px(context, 1.0d);
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = View.MeasureSpec.getMode(heightMeasureSpec);
        int size = View.MeasureSpec.getSize(heightMeasureSpec);
        if (mode == Integer.MIN_VALUE || mode == 0) {
            return (this.mRadius * 2) + (this.mStrokeWidth * 2) + getPaddingTop() + getPaddingBottom();
        }
        if (mode != 1073741824) {
            return 0;
        }
        return size;
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        if (mode == Integer.MIN_VALUE || mode == 0) {
            int i2 = this.mTotalCount;
            return (this.mStrokeWidth * 2) + (this.mRadius * i2 * 2) + ((i2 - 1) * this.mCircleSpacing) + getPaddingLeft() + getPaddingRight();
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

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void notifyDataSetChanged() {
        prepareCirclePoints();
        invalidate();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void onAttachToMagicIndicator() {
        PLVCommonLog.d(TAG, "onAttachToMagicIndicator:");
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void onDetachFromMagicIndicator() {
        PLVCommonLog.d(TAG, "onDetachFromMagicIndicator:");
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        this.mPaint.setColor(this.mCircleColor);
        drawCircles(canvas);
        drawIndicator(canvas);
    }

    @Override // android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        prepareCirclePoints();
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void onPageScrollStateChanged(int state) {
        PLVCommonLog.d(TAG, "onPageScrollStateChanged:" + state);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!this.mFollowTouch || this.mCirclePoints.isEmpty()) {
            return;
        }
        int iMin = Math.min(this.mCirclePoints.size() - 1, position);
        int iMin2 = Math.min(this.mCirclePoints.size() - 1, position + 1);
        PointF pointF = this.mCirclePoints.get(iMin);
        PointF pointF2 = this.mCirclePoints.get(iMin2);
        float f2 = pointF.x;
        this.mIndicatorX = f2 + ((pointF2.x - f2) * this.mStartInterpolator.getInterpolation(positionOffset));
        invalidate();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void onPageSelected(int position) {
        this.mCurrentIndex = position;
        if (this.mFollowTouch) {
            return;
        }
        this.mIndicatorX = this.mCirclePoints.get(position).x;
        invalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        float x2 = event.getX();
        float y2 = event.getY();
        int action = event.getAction();
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
        return super.onTouchEvent(event);
    }

    public void setCircleClickListener(OnCircleClickListener circleClickListener) {
        if (!this.mTouchable) {
            this.mTouchable = true;
        }
        this.mCircleClickListener = circleClickListener;
    }

    public void setCircleColor(int circleColor) {
        this.mCircleColor = circleColor;
        invalidate();
    }

    public void setCircleCount(int count) {
        this.mTotalCount = count;
    }

    public void setCircleSpacing(int circleSpacing) {
        this.mCircleSpacing = circleSpacing;
        prepareCirclePoints();
        invalidate();
    }

    public void setFollowTouch(boolean followTouch) {
        this.mFollowTouch = followTouch;
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
        prepareCirclePoints();
        invalidate();
    }

    public void setStartInterpolator(Interpolator startInterpolator) {
        this.mStartInterpolator = startInterpolator;
        if (startInterpolator == null) {
            this.mStartInterpolator = new LinearInterpolator();
        }
    }

    public void setStrokeWidth(int strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        invalidate();
    }

    public void setTouchable(boolean touchable) {
        this.mTouchable = touchable;
    }
}
