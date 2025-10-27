package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVFragmentContainerHelper;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.PLVUIUtil;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.model.PLVPositionData;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVWrapPagerIndicator extends View implements IPLVPagerIndicator {
    private static final String TAG = "PLVWrapPagerIndicator";
    private Interpolator mEndInterpolator;
    private int mFillColor;
    private int mHorizontalPadding;
    private Paint mPaint;
    private List<PLVPositionData> mPositionDataList;
    private RectF mRect;
    private float mRoundRadius;
    private boolean mRoundRadiusSet;
    private Interpolator mStartInterpolator;
    private int mVerticalPadding;

    public PLVWrapPagerIndicator(Context context) {
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
        this.mVerticalPadding = PLVUIUtil.dip2px(context, 6.0d);
        this.mHorizontalPadding = PLVUIUtil.dip2px(context, 10.0d);
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

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator
    public void onPageScrollStateChanged(int state) {
        PLVCommonLog.d(TAG, "onPageScrollStateChanged:" + state);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        List<PLVPositionData> list = this.mPositionDataList;
        if (list == null || list.isEmpty()) {
            return;
        }
        PLVPositionData imitativePositionData = PLVFragmentContainerHelper.getImitativePositionData(this.mPositionDataList, position);
        PLVPositionData imitativePositionData2 = PLVFragmentContainerHelper.getImitativePositionData(this.mPositionDataList, position + 1);
        this.mRect.left = (imitativePositionData.getContentLeft() - this.mHorizontalPadding) + ((imitativePositionData2.getContentLeft() - imitativePositionData.getContentLeft()) * this.mEndInterpolator.getInterpolation(positionOffset));
        this.mRect.top = imitativePositionData.getContentTop() - this.mVerticalPadding;
        this.mRect.right = imitativePositionData.getContentRight() + this.mHorizontalPadding + ((imitativePositionData2.getContentRight() - imitativePositionData.getContentRight()) * this.mStartInterpolator.getInterpolation(positionOffset));
        this.mRect.bottom = imitativePositionData.getContentBottom() + this.mVerticalPadding;
        if (!this.mRoundRadiusSet) {
            this.mRoundRadius = this.mRect.height() / 2.0f;
        }
        invalidate();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator
    public void onPageSelected(int position) {
        PLVCommonLog.d(TAG, "onPageSelected:" + position);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator
    public void onPositionDataProvide(List<PLVPositionData> dataList) {
        this.mPositionDataList = dataList;
    }

    public void setEndInterpolator(Interpolator endInterpolator) {
        this.mEndInterpolator = endInterpolator;
        if (endInterpolator == null) {
            this.mEndInterpolator = new LinearInterpolator();
        }
    }

    public void setFillColor(int fillColor) {
        this.mFillColor = fillColor;
    }

    public void setHorizontalPadding(int horizontalPadding) {
        this.mHorizontalPadding = horizontalPadding;
    }

    public void setRoundRadius(float roundRadius) {
        this.mRoundRadius = roundRadius;
        this.mRoundRadiusSet = true;
    }

    public void setStartInterpolator(Interpolator startInterpolator) {
        this.mStartInterpolator = startInterpolator;
        if (startInterpolator == null) {
            this.mStartInterpolator = new LinearInterpolator();
        }
    }

    public void setVerticalPadding(int verticalPadding) {
        this.mVerticalPadding = verticalPadding;
    }
}
