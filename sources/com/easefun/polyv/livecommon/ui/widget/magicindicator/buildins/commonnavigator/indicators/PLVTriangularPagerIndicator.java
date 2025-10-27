package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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
public class PLVTriangularPagerIndicator extends View implements IPLVPagerIndicator {
    private static final String TAG = "PLVTriangularPagerIndic";
    private float mAnchorX;
    private int mLineColor;
    private int mLineHeight;
    private Paint mPaint;
    private Path mPath;
    private List<PLVPositionData> mPositionDataList;
    private boolean mReverse;
    private Interpolator mStartInterpolator;
    private int mTriangleHeight;
    private int mTriangleWidth;
    private float mYOffset;

    public PLVTriangularPagerIndicator(Context context) {
        super(context);
        this.mPath = new Path();
        this.mStartInterpolator = new LinearInterpolator();
        init(context);
    }

    private void init(Context context) {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mLineHeight = PLVUIUtil.dip2px(context, 3.0d);
        this.mTriangleWidth = PLVUIUtil.dip2px(context, 14.0d);
        this.mTriangleHeight = PLVUIUtil.dip2px(context, 8.0d);
    }

    public int getLineColor() {
        return this.mLineColor;
    }

    public int getLineHeight() {
        return this.mLineHeight;
    }

    public Interpolator getStartInterpolator() {
        return this.mStartInterpolator;
    }

    public int getTriangleHeight() {
        return this.mTriangleHeight;
    }

    public int getTriangleWidth() {
        return this.mTriangleWidth;
    }

    public float getYOffset() {
        return this.mYOffset;
    }

    public boolean isReverse() {
        return this.mReverse;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        this.mPaint.setColor(this.mLineColor);
        if (this.mReverse) {
            canvas.drawRect(0.0f, (getHeight() - this.mYOffset) - this.mTriangleHeight, getWidth(), ((getHeight() - this.mYOffset) - this.mTriangleHeight) + this.mLineHeight, this.mPaint);
        } else {
            canvas.drawRect(0.0f, (getHeight() - this.mLineHeight) - this.mYOffset, getWidth(), getHeight() - this.mYOffset, this.mPaint);
        }
        this.mPath.reset();
        if (this.mReverse) {
            this.mPath.moveTo(this.mAnchorX - (this.mTriangleWidth / 2.0f), (getHeight() - this.mYOffset) - this.mTriangleHeight);
            this.mPath.lineTo(this.mAnchorX, getHeight() - this.mYOffset);
            this.mPath.lineTo(this.mAnchorX + (this.mTriangleWidth / 2.0f), (getHeight() - this.mYOffset) - this.mTriangleHeight);
        } else {
            this.mPath.moveTo(this.mAnchorX - (this.mTriangleWidth / 2.0f), getHeight() - this.mYOffset);
            this.mPath.lineTo(this.mAnchorX, (getHeight() - this.mTriangleHeight) - this.mYOffset);
            this.mPath.lineTo(this.mAnchorX + (this.mTriangleWidth / 2.0f), getHeight() - this.mYOffset);
        }
        this.mPath.close();
        canvas.drawPath(this.mPath, this.mPaint);
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
        float left = imitativePositionData.getLeft() + ((imitativePositionData.getRight() - imitativePositionData.getLeft()) / 2.0f);
        this.mAnchorX = left + (((imitativePositionData2.getLeft() + ((imitativePositionData2.getRight() - imitativePositionData2.getLeft()) / 2.0f)) - left) * this.mStartInterpolator.getInterpolation(positionOffset));
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

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
    }

    public void setLineHeight(int lineHeight) {
        this.mLineHeight = lineHeight;
    }

    public void setReverse(boolean reverse) {
        this.mReverse = reverse;
    }

    public void setStartInterpolator(Interpolator startInterpolator) {
        this.mStartInterpolator = startInterpolator;
        if (startInterpolator == null) {
            this.mStartInterpolator = new LinearInterpolator();
        }
    }

    public void setTriangleHeight(int triangleHeight) {
        this.mTriangleHeight = triangleHeight;
    }

    public void setTriangleWidth(int triangleWidth) {
        this.mTriangleWidth = triangleWidth;
    }

    public void setYOffset(float yOffset) {
        this.mYOffset = yOffset;
    }
}
