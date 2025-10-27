package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.PLVUIUtil;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes3.dex */
public class PLVClipPagerTitleView extends View implements IPLVMeasurablePagerTitleView {
    private static final String TAG = "PLVClipPagerTitleView";
    private int mClipColor;
    private float mClipPercent;
    private boolean mLeftToRight;
    private Paint mPaint;
    private String mText;
    private Rect mTextBounds;
    private int mTextColor;

    public PLVClipPagerTitleView(Context context) {
        super(context);
        this.mTextBounds = new Rect();
        init(context);
    }

    private void init(Context context) {
        int iDip2px = PLVUIUtil.dip2px(context, 16.0d);
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setTextSize(iDip2px);
        int iDip2px2 = PLVUIUtil.dip2px(context, 10.0d);
        setPadding(iDip2px2, 0, iDip2px2, 0);
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = View.MeasureSpec.getMode(heightMeasureSpec);
        int size = View.MeasureSpec.getSize(heightMeasureSpec);
        return mode != Integer.MIN_VALUE ? mode != 0 ? size : this.mTextBounds.height() + getPaddingTop() + getPaddingBottom() : Math.min(this.mTextBounds.height() + getPaddingTop() + getPaddingBottom(), size);
    }

    private void measureTextBounds() {
        Paint paint = this.mPaint;
        String str = this.mText;
        paint.getTextBounds(str, 0, str == null ? 0 : str.length(), this.mTextBounds);
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        return mode != Integer.MIN_VALUE ? mode != 0 ? size : this.mTextBounds.width() + getPaddingLeft() + getPaddingRight() : Math.min(this.mTextBounds.width() + getPaddingLeft() + getPaddingRight(), size);
    }

    public int getClipColor() {
        return this.mClipColor;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentBottom() {
        Paint.FontMetrics fontMetrics = this.mPaint.getFontMetrics();
        return (int) ((getHeight() / 2) + ((fontMetrics.bottom - fontMetrics.top) / 2.0f));
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentLeft() {
        return (getLeft() + (getWidth() / 2)) - (this.mTextBounds.width() / 2);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentRight() {
        return getLeft() + (getWidth() / 2) + (this.mTextBounds.width() / 2);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentTop() {
        Paint.FontMetrics fontMetrics = this.mPaint.getFontMetrics();
        return (int) ((getHeight() / 2) - ((fontMetrics.bottom - fontMetrics.top) / 2.0f));
    }

    public String getText() {
        return this.mText;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public float getTextSize() {
        return this.mPaint.getTextSize();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onDeselected(int index, int totalCount) {
        PLVCommonLog.d(TAG, "onDeselected index:" + index + " totalCount:" + totalCount);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int width = (getWidth() - this.mTextBounds.width()) / 2;
        Paint.FontMetrics fontMetrics = this.mPaint.getFontMetrics();
        int height = (int) (((getHeight() - fontMetrics.bottom) - fontMetrics.top) / 2.0f);
        this.mPaint.setColor(this.mTextColor);
        float f2 = width;
        float f3 = height;
        canvas.drawText(this.mText, f2, f3, this.mPaint);
        canvas.save();
        if (this.mLeftToRight) {
            canvas.clipRect(0.0f, 0.0f, getWidth() * this.mClipPercent, getHeight());
        } else {
            canvas.clipRect(getWidth() * (1.0f - this.mClipPercent), 0.0f, getWidth(), getHeight());
        }
        this.mPaint.setColor(this.mClipColor);
        canvas.drawText(this.mText, f2, f3, this.mPaint);
        canvas.restore();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        this.mLeftToRight = leftToRight;
        this.mClipPercent = enterPercent;
        invalidate();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        this.mLeftToRight = !leftToRight;
        this.mClipPercent = 1.0f - leavePercent;
        invalidate();
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureTextBounds();
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onSelected(int index, int totalCount) {
        PLVCommonLog.d(TAG, "onSelected index:" + index + " totalCount:" + totalCount);
    }

    public void setClipColor(int clipColor) {
        this.mClipColor = clipColor;
        invalidate();
    }

    public void setText(String text) {
        this.mText = text;
        requestLayout();
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
        invalidate();
    }

    public void setTextSize(float textSize) {
        this.mPaint.setTextSize(textSize);
        requestLayout();
    }
}
