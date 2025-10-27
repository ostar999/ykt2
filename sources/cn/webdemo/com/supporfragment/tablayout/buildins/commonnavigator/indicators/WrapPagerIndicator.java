package cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import cn.webdemo.com.supporfragment.tablayout.FragmentContainerHelper;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.model.PositionData;
import java.util.List;

/* loaded from: classes.dex */
public class WrapPagerIndicator extends View implements IPagerIndicator {
    private Interpolator mEndInterpolator;
    private int mFillColor;
    private int mHorizontalPadding;
    private Paint mPaint;
    private List<PositionData> mPositionDataList;
    private RectF mRect;
    private float mRoundRadius;
    private boolean mRoundRadiusSet;
    private Interpolator mStartInterpolator;
    private int mVerticalPadding;

    public WrapPagerIndicator(Context context) {
        super(context);
        this.mStartInterpolator = new LinearInterpolator();
        this.mEndInterpolator = new LinearInterpolator();
        this.mRect = new RectF();
        init(context);
    }

    private void init(Context context) {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mVerticalPadding = UIUtil.dip2px(context, 6.0d);
        this.mHorizontalPadding = UIUtil.dip2px(context, 10.0d);
    }

    public Interpolator getEndInterpolator() {
        return this.mEndInterpolator;
    }

    public int getFillColor() {
        return this.mFillColor;
    }

    public int getHorizontalPadding() {
        return this.mHorizontalPadding;
    }

    public Paint getPaint() {
        return this.mPaint;
    }

    public float getRoundRadius() {
        return this.mRoundRadius;
    }

    public Interpolator getStartInterpolator() {
        return this.mStartInterpolator;
    }

    public int getVerticalPadding() {
        return this.mVerticalPadding;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        this.mPaint.setColor(this.mFillColor);
        RectF rectF = this.mRect;
        float f2 = this.mRoundRadius;
        canvas.drawRoundRect(rectF, f2, f2, this.mPaint);
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
        PositionData imitativePositionData = FragmentContainerHelper.getImitativePositionData(this.mPositionDataList, i2);
        PositionData imitativePositionData2 = FragmentContainerHelper.getImitativePositionData(this.mPositionDataList, i2 + 1);
        RectF rectF = this.mRect;
        int i4 = imitativePositionData.mContentLeft;
        rectF.left = (i4 - this.mHorizontalPadding) + ((imitativePositionData2.mContentLeft - i4) * this.mEndInterpolator.getInterpolation(f2));
        RectF rectF2 = this.mRect;
        rectF2.top = imitativePositionData.mContentTop - this.mVerticalPadding;
        int i5 = imitativePositionData.mContentRight;
        rectF2.right = this.mHorizontalPadding + i5 + ((imitativePositionData2.mContentRight - i5) * this.mStartInterpolator.getInterpolation(f2));
        RectF rectF3 = this.mRect;
        rectF3.bottom = imitativePositionData.mContentBottom + this.mVerticalPadding;
        if (!this.mRoundRadiusSet) {
            this.mRoundRadius = rectF3.height() / 2.0f;
        }
        invalidate();
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator
    public void onPageSelected(int i2) {
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator
    public void onPositionDataProvide(List<PositionData> list) {
        this.mPositionDataList = list;
    }

    public void setEndInterpolator(Interpolator interpolator) {
        this.mEndInterpolator = interpolator;
        if (interpolator == null) {
            this.mEndInterpolator = new LinearInterpolator();
        }
    }

    public void setFillColor(int i2) {
        this.mFillColor = i2;
    }

    public void setHorizontalPadding(int i2) {
        this.mHorizontalPadding = i2;
    }

    public void setRoundRadius(float f2) {
        this.mRoundRadius = f2;
        this.mRoundRadiusSet = true;
    }

    public void setStartInterpolator(Interpolator interpolator) {
        this.mStartInterpolator = interpolator;
        if (interpolator == null) {
            this.mStartInterpolator = new LinearInterpolator();
        }
    }

    public void setVerticalPadding(int i2) {
        this.mVerticalPadding = i2;
    }
}
