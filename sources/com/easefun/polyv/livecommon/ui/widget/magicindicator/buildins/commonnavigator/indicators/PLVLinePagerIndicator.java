package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVFragmentContainerHelper;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.PLVArgbEvaluatorHolder;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.PLVUIUtil;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.model.PLVPositionData;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLinePagerIndicator extends View implements IPLVPagerIndicator {
    public static final int MODE_EXACTLY = 2;
    public static final int MODE_MATCH_EDGE = 0;
    public static final int MODE_WRAP_CONTENT = 1;
    private static final String TAG = "PLVLinePagerIndicator";
    private List<Integer> mColors;
    private Interpolator mEndInterpolator;
    private float mLineHeight;
    private RectF mLineRect;
    private float mLineWidth;
    private int mMode;
    private Paint mPaint;
    private List<PLVPositionData> mPositionDataList;
    private float mRoundRadius;
    private Interpolator mStartInterpolator;
    private float mXOffset;
    private float mYOffset;

    public PLVLinePagerIndicator(Context context) {
        super(context);
        this.mStartInterpolator = new LinearInterpolator();
        this.mEndInterpolator = new LinearInterpolator();
        this.mLineRect = new RectF();
        init(context);
    }

    private void init(Context context) {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mLineHeight = PLVUIUtil.dip2px(context, 3.0d);
        this.mLineWidth = PLVUIUtil.dip2px(context, 10.0d);
    }

    public List<Integer> getColors() {
        return this.mColors;
    }

    public Interpolator getEndInterpolator() {
        return this.mEndInterpolator;
    }

    public float getLineHeight() {
        return this.mLineHeight;
    }

    public float getLineWidth() {
        return this.mLineWidth;
    }

    public int getMode() {
        return this.mMode;
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

    public float getXOffset() {
        return this.mXOffset;
    }

    public float getYOffset() {
        return this.mYOffset;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        RectF rectF = this.mLineRect;
        float f2 = this.mRoundRadius;
        canvas.drawRoundRect(rectF, f2, f2, this.mPaint);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator
    public void onPageScrollStateChanged(int state) {
        PLVCommonLog.d(TAG, "onPageScrollStateChanged:" + state);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float left;
        float left2;
        float fWidth;
        float fWidth2;
        float contentRight;
        float f2;
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
        int i2 = this.mMode;
        if (i2 == 0) {
            left = imitativePositionData.getLeft() + this.mXOffset;
            left2 = imitativePositionData2.getLeft() + this.mXOffset;
            fWidth = imitativePositionData.getRight() - this.mXOffset;
            contentRight = imitativePositionData2.getRight();
            f2 = this.mXOffset;
        } else {
            if (i2 != 1) {
                left = imitativePositionData.getLeft() + ((imitativePositionData.width() - this.mLineWidth) / 2.0f);
                left2 = imitativePositionData2.getLeft() + ((imitativePositionData2.width() - this.mLineWidth) / 2.0f);
                fWidth = ((imitativePositionData.width() + this.mLineWidth) / 2.0f) + imitativePositionData.getLeft();
                fWidth2 = ((imitativePositionData2.width() + this.mLineWidth) / 2.0f) + imitativePositionData2.getLeft();
                this.mLineRect.left = left + ((left2 - left) * this.mStartInterpolator.getInterpolation(positionOffset));
                this.mLineRect.right = fWidth + ((fWidth2 - fWidth) * this.mEndInterpolator.getInterpolation(positionOffset));
                this.mLineRect.top = (getHeight() - this.mLineHeight) - this.mYOffset;
                this.mLineRect.bottom = getHeight() - this.mYOffset;
                invalidate();
            }
            left = imitativePositionData.getContentLeft() + this.mXOffset;
            left2 = imitativePositionData2.getContentLeft() + this.mXOffset;
            fWidth = imitativePositionData.getContentRight() - this.mXOffset;
            contentRight = imitativePositionData2.getContentRight();
            f2 = this.mXOffset;
        }
        fWidth2 = contentRight - f2;
        this.mLineRect.left = left + ((left2 - left) * this.mStartInterpolator.getInterpolation(positionOffset));
        this.mLineRect.right = fWidth + ((fWidth2 - fWidth) * this.mEndInterpolator.getInterpolation(positionOffset));
        this.mLineRect.top = (getHeight() - this.mLineHeight) - this.mYOffset;
        this.mLineRect.bottom = getHeight() - this.mYOffset;
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

    public void setColors(Integer... colors) {
        this.mColors = Arrays.asList(colors);
    }

    public void setEndInterpolator(Interpolator endInterpolator) {
        this.mEndInterpolator = endInterpolator;
        if (endInterpolator == null) {
            this.mEndInterpolator = new LinearInterpolator();
        }
    }

    public void setLineHeight(float lineHeight) {
        this.mLineHeight = lineHeight;
    }

    public void setLineWidth(float lineWidth) {
        this.mLineWidth = lineWidth;
    }

    public void setMode(int mode) {
        if (mode == 2 || mode == 0 || mode == 1) {
            this.mMode = mode;
            return;
        }
        throw new IllegalArgumentException("mode " + mode + " not supported.");
    }

    public void setRoundRadius(float roundRadius) {
        this.mRoundRadius = roundRadius;
    }

    public void setStartInterpolator(Interpolator startInterpolator) {
        this.mStartInterpolator = startInterpolator;
        if (startInterpolator == null) {
            this.mStartInterpolator = new LinearInterpolator();
        }
    }

    public void setXOffset(float xOffset) {
        this.mXOffset = xOffset;
    }

    public void setYOffset(float yOffset) {
        this.mYOffset = yOffset;
    }
}
