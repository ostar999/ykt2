package cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.titles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView;

/* loaded from: classes.dex */
public class ClipPagerTitleView extends View implements IMeasurablePagerTitleView {
    private int mClipColor;
    private float mClipPercent;
    private int mItemWidth;
    private boolean mLeftToRight;
    private Paint mPaint;
    private String mText;
    private Rect mTextBounds;
    private int mTextColor;
    private int type;

    /* renamed from: x, reason: collision with root package name */
    int f2623x;

    public ClipPagerTitleView(Context context) {
        super(context);
        this.type = 1000;
        this.mTextBounds = new Rect();
        init(context);
    }

    public static int getPxByDp(Context context, int i2) {
        return (int) TypedValue.applyDimension(1, i2, context.getResources().getDisplayMetrics());
    }

    private void init(Context context) {
        int iDip2px = UIUtil.dip2px(context, 14.0d);
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setTextSize(iDip2px);
        int iDip2px2 = UIUtil.dip2px(context, 10.0d);
        setPadding(iDip2px2, 0, iDip2px2, 0);
    }

    private int measureHeight(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        return mode != Integer.MIN_VALUE ? mode != 0 ? size : this.mTextBounds.height() + getPaddingTop() + getPaddingBottom() : Math.min(this.mTextBounds.height() + getPaddingTop() + getPaddingBottom(), size);
    }

    private void measureTextBounds() {
        Paint paint = this.mPaint;
        String str = this.mText;
        paint.getTextBounds(str, 0, str == null ? 0 : str.length(), this.mTextBounds);
    }

    private int measureWidth(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        return mode != Integer.MIN_VALUE ? mode != 0 ? size : this.mItemWidth : Math.min(this.mTextBounds.width() + getPaddingLeft() + getPaddingRight(), size);
    }

    public int getClipColor() {
        return this.mClipColor;
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentBottom() {
        Paint.FontMetrics fontMetrics = this.mPaint.getFontMetrics();
        return (int) ((getHeight() / 2) + ((fontMetrics.bottom - fontMetrics.top) / 2.0f));
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentLeft() {
        return (getLeft() + (getWidth() / 2)) - (this.mTextBounds.width() / 2);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentRight() {
        return getLeft() + (getWidth() / 2) + (this.mTextBounds.width() / 2);
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView
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

    public int getType() {
        return this.type;
    }

    public int getmItemWidth() {
        return this.mItemWidth;
    }

    public Paint getmPaint() {
        if (this.mPaint == null) {
            this.mPaint = new Paint(1);
        }
        return this.mPaint;
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onDeselected(int i2, int i3) {
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        this.f2623x = (getWidth() - this.mTextBounds.width()) / 2;
        Paint.FontMetrics fontMetrics = this.mPaint.getFontMetrics();
        int height = (int) (((getHeight() - fontMetrics.bottom) - fontMetrics.top) / 2.0f);
        this.mPaint.setColor(this.mTextColor);
        if (this.mText.length() > 5) {
            this.f2623x += UIUtil.dip2px(getContext(), 10.0d);
        }
        float f2 = height;
        canvas.drawText(this.mText, this.f2623x, f2, this.mPaint);
        canvas.save();
        if (this.mLeftToRight) {
            canvas.clipRect(0.0f, 0.0f, getWidth() * this.mClipPercent, getHeight());
        } else {
            canvas.clipRect(getWidth() * (1.0f - this.mClipPercent), 0.0f, getWidth(), getHeight());
        }
        this.mPaint.setColor(this.mClipColor);
        canvas.drawText(this.mText, this.f2623x, f2, this.mPaint);
        canvas.restore();
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onEnter(int i2, int i3, float f2, boolean z2) {
        this.mLeftToRight = z2;
        this.mClipPercent = f2;
        invalidate();
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onLeave(int i2, int i3, float f2, boolean z2) {
        this.mLeftToRight = !z2;
        this.mClipPercent = 1.0f - f2;
        invalidate();
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        measureTextBounds();
        setMeasuredDimension(measureWidth(i2), measureHeight(i3));
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView
    public void onSelected(int i2, int i3) {
    }

    public void setClipColor(int i2) {
        this.mClipColor = i2;
        this.mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
        invalidate();
    }

    public void setText(String str) {
        this.mText = str;
        requestLayout();
    }

    public void setTextColor(int i2) {
        this.mTextColor = i2;
        this.mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
        invalidate();
    }

    public void setTextSize(float f2) {
        this.mPaint.setTextSize(f2);
        requestLayout();
    }

    public void setType(int i2) {
        this.type = i2;
    }

    public void setmItemWidth(int i2) {
        this.mItemWidth = i2;
    }
}
