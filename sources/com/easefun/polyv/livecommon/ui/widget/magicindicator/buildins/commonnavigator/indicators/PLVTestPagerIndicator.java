package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import androidx.core.internal.view.SupportMenu;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVFragmentContainerHelper;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.model.PLVPositionData;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVTestPagerIndicator extends View implements IPLVPagerIndicator {
    private static final String TAG = "PLVTestPagerIndicator";
    private RectF mInnerRect;
    private int mInnerRectColor;
    private RectF mOutRect;
    private int mOutRectColor;
    private Paint mPaint;
    private List<PLVPositionData> mPositionDataList;

    public PLVTestPagerIndicator(Context context) {
        super(context);
        this.mOutRect = new RectF();
        this.mInnerRect = new RectF();
        init(context);
    }

    private void init(Context context) {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mOutRectColor = SupportMenu.CATEGORY_MASK;
        this.mInnerRectColor = -16711936;
    }

    public int getInnerRectColor() {
        return this.mInnerRectColor;
    }

    public int getOutRectColor() {
        return this.mOutRectColor;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        this.mPaint.setColor(this.mOutRectColor);
        canvas.drawRect(this.mOutRect, this.mPaint);
        this.mPaint.setColor(this.mInnerRectColor);
        canvas.drawRect(this.mInnerRect, this.mPaint);
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
        this.mOutRect.left = imitativePositionData.getLeft() + ((imitativePositionData2.getLeft() - imitativePositionData.getLeft()) * positionOffset);
        this.mOutRect.top = imitativePositionData.getTop() + ((imitativePositionData2.getTop() - imitativePositionData.getTop()) * positionOffset);
        this.mOutRect.right = imitativePositionData.getRight() + ((imitativePositionData2.getRight() - imitativePositionData.getRight()) * positionOffset);
        this.mOutRect.bottom = imitativePositionData.getBottom() + ((imitativePositionData2.getBottom() - imitativePositionData.getBottom()) * positionOffset);
        this.mInnerRect.left = imitativePositionData.getContentLeft() + ((imitativePositionData2.getContentLeft() - imitativePositionData.getContentLeft()) * positionOffset);
        this.mInnerRect.top = imitativePositionData.getContentTop() + ((imitativePositionData2.getContentTop() - imitativePositionData.getContentTop()) * positionOffset);
        this.mInnerRect.right = imitativePositionData.getContentRight() + ((imitativePositionData2.getContentRight() - imitativePositionData.getContentRight()) * positionOffset);
        this.mInnerRect.bottom = imitativePositionData.getContentBottom() + ((imitativePositionData2.getContentBottom() - imitativePositionData.getContentBottom()) * positionOffset);
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

    public void setInnerRectColor(int innerRectColor) {
        this.mInnerRectColor = innerRectColor;
    }

    public void setOutRectColor(int outRectColor) {
        this.mOutRectColor = outRectColor;
    }
}
