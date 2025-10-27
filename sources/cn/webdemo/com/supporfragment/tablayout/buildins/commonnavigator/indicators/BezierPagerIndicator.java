package cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import cn.webdemo.com.supporfragment.tablayout.FragmentContainerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.ArgbEvaluatorHolder;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.model.PositionData;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class BezierPagerIndicator extends View implements IPagerIndicator {
    private List<Integer> mColors;
    private Interpolator mEndInterpolator;
    private float mLeftCircleRadius;
    private float mLeftCircleX;
    private float mMaxCircleRadius;
    private float mMinCircleRadius;
    private Paint mPaint;
    private Path mPath;
    private List<PositionData> mPositionDataList;
    private float mRightCircleRadius;
    private float mRightCircleX;
    private Interpolator mStartInterpolator;
    private float mYOffset;

    public BezierPagerIndicator(Context context) {
        super(context);
        this.mPath = new Path();
        this.mStartInterpolator = new AccelerateInterpolator();
        this.mEndInterpolator = new DecelerateInterpolator();
        init(context);
    }

    private void drawBezierCurve(Canvas canvas) {
        this.mPath.reset();
        float height = (getHeight() - this.mYOffset) - this.mMaxCircleRadius;
        this.mPath.moveTo(this.mRightCircleX, height);
        this.mPath.lineTo(this.mRightCircleX, height - this.mRightCircleRadius);
        Path path = this.mPath;
        float f2 = this.mRightCircleX;
        float f3 = this.mLeftCircleX;
        path.quadTo(f2 + ((f3 - f2) / 2.0f), height, f3, height - this.mLeftCircleRadius);
        this.mPath.lineTo(this.mLeftCircleX, this.mLeftCircleRadius + height);
        Path path2 = this.mPath;
        float f4 = this.mRightCircleX;
        path2.quadTo(((this.mLeftCircleX - f4) / 2.0f) + f4, height, f4, this.mRightCircleRadius + height);
        this.mPath.close();
        canvas.drawPath(this.mPath, this.mPaint);
    }

    private void init(Context context) {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mMaxCircleRadius = UIUtil.dip2px(context, 3.5d);
        this.mMinCircleRadius = UIUtil.dip2px(context, 2.0d);
        this.mYOffset = UIUtil.dip2px(context, 1.5d);
    }

    public float getMaxCircleRadius() {
        return this.mMaxCircleRadius;
    }

    public float getMinCircleRadius() {
        return this.mMinCircleRadius;
    }

    public float getYOffset() {
        return this.mYOffset;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        canvas.drawCircle(this.mLeftCircleX, (getHeight() - this.mYOffset) - this.mMaxCircleRadius, this.mLeftCircleRadius, this.mPaint);
        canvas.drawCircle(this.mRightCircleX, (getHeight() - this.mYOffset) - this.mMaxCircleRadius, this.mRightCircleRadius, this.mPaint);
        drawBezierCurve(canvas);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator
    public void onPageScrollStateChanged(int i2) {
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator
    public void onPageScrolled(int i2, float f2, int i3) {
        List<PositionData> list = this.mPositionDataList;
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Integer> list2 = this.mColors;
        if (list2 != null && list2.size() > 0) {
            this.mPaint.setColor(ArgbEvaluatorHolder.eval(f2, this.mColors.get(Math.abs(i2) % this.mColors.size()).intValue(), this.mColors.get(Math.abs(i2 + 1) % this.mColors.size()).intValue()));
        }
        PositionData imitativePositionData = FragmentContainerHelper.getImitativePositionData(this.mPositionDataList, i2);
        PositionData imitativePositionData2 = FragmentContainerHelper.getImitativePositionData(this.mPositionDataList, i2 + 1);
        int i4 = imitativePositionData.mLeft;
        float f3 = i4 + ((imitativePositionData.mRight - i4) / 2);
        int i5 = imitativePositionData2.mLeft;
        float f4 = (i5 + ((imitativePositionData2.mRight - i5) / 2)) - f3;
        this.mLeftCircleX = (this.mStartInterpolator.getInterpolation(f2) * f4) + f3;
        this.mRightCircleX = f3 + (f4 * this.mEndInterpolator.getInterpolation(f2));
        float f5 = this.mMaxCircleRadius;
        this.mLeftCircleRadius = f5 + ((this.mMinCircleRadius - f5) * this.mEndInterpolator.getInterpolation(f2));
        float f6 = this.mMinCircleRadius;
        this.mRightCircleRadius = f6 + ((this.mMaxCircleRadius - f6) * this.mStartInterpolator.getInterpolation(f2));
        invalidate();
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator
    public void onPageSelected(int i2) {
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator
    public void onPositionDataProvide(List<PositionData> list) {
        this.mPositionDataList = list;
    }

    public void setColors(Integer... numArr) {
        this.mColors = Arrays.asList(numArr);
    }

    public void setEndInterpolator(Interpolator interpolator) {
        this.mEndInterpolator = interpolator;
        if (interpolator == null) {
            this.mEndInterpolator = new DecelerateInterpolator();
        }
    }

    public void setMaxCircleRadius(float f2) {
        this.mMaxCircleRadius = f2;
    }

    public void setMinCircleRadius(float f2) {
        this.mMinCircleRadius = f2;
    }

    public void setStartInterpolator(Interpolator interpolator) {
        this.mStartInterpolator = interpolator;
        if (interpolator == null) {
            this.mStartInterpolator = new AccelerateInterpolator();
        }
    }

    public void setYOffset(float f2) {
        this.mYOffset = f2;
    }
}
