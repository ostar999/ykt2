package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVFragmentContainerHelper;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.PLVArgbEvaluatorHolder;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.PLVUIUtil;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.model.PLVPositionData;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVBezierPagerIndicator extends View implements IPLVPagerIndicator {
    private List<Integer> mColors;
    private Interpolator mEndInterpolator;
    private float mLeftCircleRadius;
    private float mLeftCircleX;
    private float mMaxCircleRadius;
    private float mMinCircleRadius;
    private Paint mPaint;
    private Path mPath;
    private List<PLVPositionData> mPositionDataList;
    private float mRightCircleRadius;
    private float mRightCircleX;
    private Interpolator mStartInterpolator;
    private float mYOffset;

    public PLVBezierPagerIndicator(Context context) {
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
        this.mMaxCircleRadius = PLVUIUtil.dip2px(context, 3.5d);
        this.mMinCircleRadius = PLVUIUtil.dip2px(context, 2.0d);
        this.mYOffset = PLVUIUtil.dip2px(context, 1.5d);
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

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator
    public void onPageScrollStateChanged(int state) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        List<PLVPositionData> list = this.mPositionDataList;
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Integer> list2 = this.mColors;
        if (list2 != null && !list2.isEmpty()) {
            this.mPaint.setColor(PLVArgbEvaluatorHolder.eval(positionOffset, this.mColors.get(Math.abs(position) % this.mColors.size()).intValue(), this.mColors.get(Math.abs(position + 1) % this.mColors.size()).intValue()));
        }
        PLVPositionData imitativePositionData = PLVFragmentContainerHelper.getImitativePositionData(this.mPositionDataList, position);
        PLVPositionData imitativePositionData2 = PLVFragmentContainerHelper.getImitativePositionData(this.mPositionDataList, position + 1);
        float left = imitativePositionData.getLeft() + ((imitativePositionData.getRight() - imitativePositionData.getLeft()) / 2.0f);
        float left2 = (imitativePositionData2.getLeft() + ((imitativePositionData2.getRight() - imitativePositionData2.getLeft()) / 2.0f)) - left;
        this.mLeftCircleX = (this.mStartInterpolator.getInterpolation(positionOffset) * left2) + left;
        this.mRightCircleX = left + (left2 * this.mEndInterpolator.getInterpolation(positionOffset));
        float f2 = this.mMaxCircleRadius;
        this.mLeftCircleRadius = f2 + ((this.mMinCircleRadius - f2) * this.mEndInterpolator.getInterpolation(positionOffset));
        float f3 = this.mMinCircleRadius;
        this.mRightCircleRadius = f3 + ((this.mMaxCircleRadius - f3) * this.mStartInterpolator.getInterpolation(positionOffset));
        invalidate();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator
    public void onPageSelected(int position) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator
    public void onPositionDataProvide(List<PLVPositionData> dataList) {
        this.mPositionDataList = dataList;
    }

    public void setColors(Integer... colors) {
        this.mColors = Arrays.asList(colors);
    }

    public void setEndInterpolator(Interpolator endInterpolator) {
        this.mEndInterpolator = endInterpolator;
        if (endInterpolator == null) {
            this.mEndInterpolator = new DecelerateInterpolator();
        }
    }

    public void setMaxCircleRadius(float maxCircleRadius) {
        this.mMaxCircleRadius = maxCircleRadius;
    }

    public void setMinCircleRadius(float minCircleRadius) {
        this.mMinCircleRadius = minCircleRadius;
    }

    public void setStartInterpolator(Interpolator startInterpolator) {
        this.mStartInterpolator = startInterpolator;
        if (startInterpolator == null) {
            this.mStartInterpolator = new AccelerateInterpolator();
        }
    }

    public void setYOffset(float yOffset) {
        this.mYOffset = yOffset;
    }
}
