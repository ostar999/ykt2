package net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import java.util.List;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData;

/* loaded from: classes9.dex */
public class TriangularPagerIndicator extends View implements IPagerIndicator {
    private float mAnchorX;
    private int mLineColor;
    private int mLineHeight;
    private Paint mPaint;
    private Path mPath;
    private List<PositionData> mPositionDataList;
    private boolean mReverse;
    private Interpolator mStartInterpolator;
    private int mTriangleHeight;
    private int mTriangleWidth;
    private float mYOffset;

    public TriangularPagerIndicator(Context context) {
        super(context);
        this.mPath = new Path();
        this.mStartInterpolator = new LinearInterpolator();
        init(context);
    }

    private void init(Context context) {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mLineHeight = UIUtil.dip2px(context, 3.0d);
        this.mTriangleWidth = UIUtil.dip2px(context, 14.0d);
        this.mTriangleHeight = UIUtil.dip2px(context, 8.0d);
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
            this.mPath.moveTo(this.mAnchorX - (this.mTriangleWidth / 2), (getHeight() - this.mYOffset) - this.mTriangleHeight);
            this.mPath.lineTo(this.mAnchorX, getHeight() - this.mYOffset);
            this.mPath.lineTo(this.mAnchorX + (this.mTriangleWidth / 2), (getHeight() - this.mYOffset) - this.mTriangleHeight);
        } else {
            this.mPath.moveTo(this.mAnchorX - (this.mTriangleWidth / 2), getHeight() - this.mYOffset);
            this.mPath.lineTo(this.mAnchorX, (getHeight() - this.mTriangleHeight) - this.mYOffset);
            this.mPath.lineTo(this.mAnchorX + (this.mTriangleWidth / 2), getHeight() - this.mYOffset);
        }
        this.mPath.close();
        canvas.drawPath(this.mPath, this.mPaint);
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
    public void onPageScrollStateChanged(int i2) {
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
    public void onPageScrolled(int i2, float f2, int i3) {
        List<PositionData> list = this.mPositionDataList;
        if (list == null || list.isEmpty()) {
            return;
        }
        PositionData imitativePositionData = FragmentContainerHelper.getImitativePositionData(this.mPositionDataList, i2);
        PositionData imitativePositionData2 = FragmentContainerHelper.getImitativePositionData(this.mPositionDataList, i2 + 1);
        int i4 = imitativePositionData.mLeft;
        float f3 = i4 + ((imitativePositionData.mRight - i4) / 2);
        int i5 = imitativePositionData2.mLeft;
        this.mAnchorX = f3 + (((i5 + ((imitativePositionData2.mRight - i5) / 2)) - f3) * this.mStartInterpolator.getInterpolation(f2));
        invalidate();
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
    public void onPageSelected(int i2) {
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
    public void onPositionDataProvide(List<PositionData> list) {
        this.mPositionDataList = list;
    }

    public void setLineColor(int i2) {
        this.mLineColor = i2;
    }

    public void setLineHeight(int i2) {
        this.mLineHeight = i2;
    }

    public void setReverse(boolean z2) {
        this.mReverse = z2;
    }

    public void setStartInterpolator(Interpolator interpolator) {
        this.mStartInterpolator = interpolator;
        if (interpolator == null) {
            this.mStartInterpolator = new LinearInterpolator();
        }
    }

    public void setTriangleHeight(int i2) {
        this.mTriangleHeight = i2;
    }

    public void setTriangleWidth(int i2) {
        this.mTriangleWidth = i2;
    }

    public void setYOffset(float f2) {
        this.mYOffset = f2;
    }
}
